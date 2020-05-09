package com.newtask;

import com.vedeng.authorization.dao.OrganizationMapper;
import com.vedeng.authorization.dao.PositionMapper;
import com.vedeng.authorization.model.Position;
import com.vedeng.system.service.OrgService;
import com.vedeng.trader.dao.WebAccountMapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Daniel
 * @date created in 2020/3/5 14:20
 */

@JobHandler(value = "updateAccountBelongPlatform")
@Component
public class UpdateWebAccountBelongPlatformTask extends IJobHandler {

    @Autowired
    private WebAccountMapper webAccountMapper;

    @Autowired
    private OrgService orgService;

    @Autowired
    private PositionMapper positionMapper;

    @Value("${b2b_business_division_id}")
    private Integer b2bBusinessDivisionId;

    @Value("${yi_xie_purchase_id}")
    private Integer yiXiePurchaseId;

    @Value("${scientific_research_training_id}")
    private Integer scientificResearchTrainingId;


    @Override
    public ReturnT<String> execute(String s) throws Exception {

        updateWebAccountBelongPlatform();

        XxlJobLogger.log("更新WebAccount的归属平台属性完成！");
        return null;
    }


    private void updateWebAccountBelongPlatform(){
        //若已关联公司，显示关联的公司的归属平台
        webAccountMapper.updateBelongPlatformOfHasTrader();

        //若未关联公司但有归属销售，显示归属销售的所属平台；
        updateBelongPlatformByUserId();

        //若未关联公司且无归属销售，显示注册账号的注册平台;
        webAccountMapper.updateBelongPlatformOfNoUserIdAndNoTrader();
    }


    public void updateBelongPlatformByUserId(){
        //将部门节点转换为Map数据结构
        Set<Integer> b2bSet = getChildrenSetOfOrg(b2bBusinessDivisionId,1);
        Set<Integer> yxgSet = getChildrenSetOfOrg(yiXiePurchaseId,1);
        Set<Integer> kygSet = getChildrenSetOfOrg(scientificResearchTrainingId,1);

        List<Integer> usersOfB2b = new ArrayList<>();
        List<Integer> usersOfYxg = new ArrayList<>();
        List<Integer> usersOfKyg = new ArrayList<>();
        List<Integer> usersOfOthers = new ArrayList<>();

        //获取webaccount表中关联的销售，但是没有关联企业
        List<Integer> userList = webAccountMapper.getSalerIdListOfNoTrader();

        //获取这些销售对应的部门id，如果存在多部门，那么以逗号分隔，取第一个部门
        List<Position> userOrgInfo = positionMapper.getOrgListOfUsers(userList);

        for (Position item : userOrgInfo){
            Integer orgId = Integer.valueOf(item.getPositionName().split(",")[0]);
            if (b2bSet.contains(orgId)){
                usersOfB2b.add(item.getOrgId());
            } else if (yxgSet.contains(orgId)){
                usersOfYxg.add(item.getOrgId());
            } else if (kygSet.contains(orgId)){
                usersOfKyg.add(item.getOrgId());
            } else {
                usersOfOthers.add(item.getOrgId());
            }
        }

        webAccountMapper.updateBelongPlatformByUserIdList(usersOfB2b,1);
        webAccountMapper.updateBelongPlatformByUserIdList(usersOfYxg,2);
        webAccountMapper.updateBelongPlatformByUserIdList(usersOfKyg,3);
        webAccountMapper.updateBelongPlatformByUserIdList(usersOfOthers,5);
    }


    private Set<Integer> getChildrenSetOfOrg(Integer parentOrgId, Integer companyId){
        List<Integer> childrenOrg = orgService.getChildrenByParentId(parentOrgId,companyId);
        //将父节点加入到相应子节点集合中
        childrenOrg.add(parentOrgId);
        //将部门节点转换为Map数据结构
        return new HashSet<>(childrenOrg);
    }
}

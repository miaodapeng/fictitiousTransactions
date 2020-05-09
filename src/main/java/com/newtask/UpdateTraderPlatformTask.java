package com.newtask;

import com.vedeng.authorization.dao.OrganizationMapper;
import com.vedeng.authorization.dao.PositionMapper;
import com.vedeng.authorization.model.Position;
import com.vedeng.system.service.OrgService;
import com.vedeng.trader.dao.TraderMapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * @author Daniel
 * @date created in 2020/3/5 9:26
 */
@Component
@JobHandler(value = "updateTraderBelongPlatform")
public class UpdateTraderPlatformTask extends IJobHandler {

    @Autowired
    private OrgService orgService;

    @Autowired
    private TraderMapper traderMapper;

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

        //将部门节点转换为Map数据结构
        Set<Integer> b2bSet = getChildrenSetOfOrg(b2bBusinessDivisionId,1);
        Set<Integer> yxgSet = getChildrenSetOfOrg(yiXiePurchaseId,1);
        Set<Integer> kygSet = getChildrenSetOfOrg(scientificResearchTrainingId,1);

        List<Integer> usersOfB2b = new ArrayList<>();
        List<Integer> usersOfYxg = new ArrayList<>();
        List<Integer> usersOfKyg = new ArrayList<>();
        List<Integer> usersOfOthers = new ArrayList<>();

        //获取所有trader的归属销售
        List<Integer> userList = traderMapper.getAllUserId();

        //获取这些销售对应的部门id，如果存在多部门，那么以逗号分隔
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

        //批量更新销售负责的企业客户的归属平台信息
        traderMapper.updateTraderPlatformByUserList(1,usersOfB2b);
        traderMapper.updateTraderPlatformByUserList(2,usersOfYxg);
        traderMapper.updateTraderPlatformByUserList(3,usersOfKyg);
        traderMapper.updateTraderPlatformByUserList(5,usersOfOthers);

        return null;
    }


    private Set<Integer> getChildrenSetOfOrg(Integer parentOrgId, Integer companyId){
        List<Integer> childrenOrg = orgService.getChildrenByParentId(parentOrgId,companyId);
        //将父节点加入到相应子节点集合中
        childrenOrg.add(parentOrgId);
        //将部门节点转换为Map数据结构
        return new HashSet<>(childrenOrg);
    }




}

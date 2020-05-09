package com.smallhospital.service.impl.remote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smallhospital.dao.ElTraderMapper;
import com.smallhospital.model.vo.ElTraderVo;
import com.smallhospital.service.ELTraderService;
import com.smallhospital.service.impl.remote.AbstractELThirdService;
import com.vedeng.authorization.model.Region;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.constant.SysOptionConstant;
import com.vedeng.common.shiro.JedisUtils;
import com.vedeng.common.util.JsonUtils;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.common.util.StringUtil;
import com.vedeng.trader.dao.TraderAddressMapper;
import com.vedeng.trader.dao.TraderCertificateMapper;
import com.vedeng.trader.dao.TraderContactGenerateMapper;
import com.vedeng.trader.model.TraderAddress;
import com.vedeng.trader.model.TraderCertificate;
import com.vedeng.trader.model.TraderContactGenerate;
import com.vedeng.trader.model.TraderCustomer;
import com.vedeng.trader.model.vo.TraderCustomerVo;
import com.vedeng.trader.service.TraderCustomerService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户信息同步接口
 */
@Service
public class SynTradeInfoService extends AbstractELThirdService<Integer> {

    public static final String TRADER_UIR = "/api/pushHospital.action";

    @Autowired
    private TraderCustomerService traderCustomerService;

    @Autowired
    private ElTraderMapper elTraderMapper;

    @Autowired
    @Qualifier("traderCertificateMapper")
    private TraderCertificateMapper traderCertificateMapper;

    @Autowired
    private TraderContactGenerateMapper traderContactGenerateMapper;

    @Autowired
    private TraderAddressMapper traderAddressMapper;

    @Override
    protected String encapsulaeRequestBodyParam(Integer traderId) {
        TraderCustomerVo traderCustomerVo = traderCustomerService.getTraderCustomerInfo(traderId);
        ElTraderVo elTraderVo = this.elTraderMapper.getElTraderByTraderId(traderId);

        JSONObject dataObj = new JSONObject();
        dataObj.put("hospitalId",traderId);
        dataObj.put("hospitalName",traderCustomerVo.getTraderName());
        //组织机构代码
        dataObj.put("organizationalCode","/");

        TraderContactGenerate traderContract = getTraderDefaultContact(traderId);
        if(traderContract != null){
            dataObj.put("contacts",traderContract.getName());
            dataObj.put("contactNumber1",traderContract.getTelephone());
            dataObj.put("contactNumber2",traderContract.getMobile());
            dataObj.put("email",traderContract.getEmail());
        }

        TraderAddress traderAddress = this.getTraderDefaultAddress(traderId);
        if(traderAddress != null){
            dataObj.put("address",super.getAddressByAreaId(traderAddress.getAreaId()) + " " + traderAddress.getAddress());
        }
        dataObj.put("remark","");
        return dataObj.toJSONString();
    }

    private String getOrgaCode(Integer traderId){

        TraderCustomer customer = new TraderCustomer();
        customer.setTraderId(traderId);

        List<TraderCertificate> certificates=traderCertificateMapper.getTraderCertificatesByTraderId(customer);
        if(CollectionUtils.isEmpty(certificates)){
            return "组织结构代码";
        }

        return certificates.stream()
                .filter(e->e.getSysOptionDefinitionId() == SysOptionConstant.ID_27)
                .findFirst()
                .map(el->el.getDomain()+el.getUri())
                .orElse("组织结构代码");
    }

    @Override
    protected String getRequestUIR() {
        return TRADER_UIR;
    }

    /**
     * 获取客户默认的联系人
     * @param traderId
     */
    public TraderContactGenerate getTraderDefaultContact(Integer traderId){
        if(traderId == null){
            return null;
        }

        return this.traderContactGenerateMapper.getTraderDefaultContact(traderId);
    }

    /**
     * 获取客户默认的联系人
     * @param traderId
     */
    public TraderAddress getTraderDefaultAddress(Integer traderId){
        if(traderId == null){
            return null;
        }

        return this.traderAddressMapper.getTraderDefaultAdressInfo(traderId);
    }
}

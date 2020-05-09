package com.smallhospital.service.impl.build;

import com.smallhospital.service.impl.visitor.CreateOrderAddressInfoVisitor;
import com.smallhospital.service.impl.visitor.CreateOrderContactVistor;
import com.smallhospital.service.impl.visitor.CreteOrderBasicInfoVisitor;
import com.vedeng.order.model.SaleorderGenerate;

/**
 * 销售单的构造器
 */
public class SaleorderGenerateBuild {

    public SaleorderGenerate saleorder = new SaleorderGenerate();

    public static SaleorderGenerateBuild newBuild(){
        SaleorderGenerateBuild build = new SaleorderGenerateBuild();
        return build;
    }

    //设置基本信息
    public SaleorderGenerateBuild setBasicInfO(CreteOrderBasicInfoVisitor visitor) throws Exception{
        visitor.accept(this);
        return this;
    }

    //设置地址信息
    public SaleorderGenerateBuild setAddressInfo(CreateOrderAddressInfoVisitor visitor) throws Exception{
        visitor.accept(this);
        return this;

    }

    public SaleorderGenerateBuild setContractInfo(CreateOrderContactVistor vistor) throws Exception{
        vistor.accept(this);
        return this;
    }

    public SaleorderGenerate getSaleOrder(){
        return saleorder;
    }

    public SaleorderGenerate create(){
        return saleorder;
    }


}

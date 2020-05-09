package com.smallhospital.service.impl.visitor;

import com.smallhospital.service.impl.build.SaleorderGenerateBuild;

/**
 * 访问器模式->处理构造器对象
 */
public interface ELSaleOrderBuildVisitor {

    public void accept(SaleorderGenerateBuild saleOrderBuild) throws Exception;

}

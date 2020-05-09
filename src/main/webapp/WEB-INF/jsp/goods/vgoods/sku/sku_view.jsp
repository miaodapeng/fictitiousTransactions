<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>

<!DOCTYPE html">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看SKU</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/vgoods/spu/spu_view.css?rnd=<%=Math.random()%>">
</head>

<body>
    <div class="detail-wrap">
        <input type="hidden" name="spuLevel" value="${coreSpuDto.spuLevel}">
        <input type="hidden" name="skuType" value="${command.skuType}">
        <input type="hidden" id="isSupplyAssistant" value="${isSupplyAssistant}">
        <input type="hidden" id="checkStatus" value="${skuGenerate.checkStatus}">
        <div class="detail-title">查看SKU：${skuGenerate.showName}
        </div>
        <c:if test="${coreSpuDto.checkStatus==2 }">
            <div class="vd-tip tip-red">
                <i class="vd-tip-icon vd-icon icon-error2"></i>
                <div class="vd-tip-cnt">当前SPU信息审核不通过，请暂停对该SKU进行审核。</div>
            </div>
        </c:if>
        <c:if test="${coreSpuDto.checkStatus==1}">
            <div class="vd-tip tip-red">
                <i class="vd-tip-icon vd-icon icon-error2"></i>
                <div class="vd-tip-cnt">当前SPU信息审核中，请暂停对该SKU进行审核。</div>
            </div>
        </c:if>
        <!-- 后台报错的区域 -->
        <c:forEach var="error" items="${command.errors}" varStatus="status">
            <div class="vd-tip tip-red">
                <i class="vd-tip-icon vd-icon icon-error2"></i>
                <div class="vd-tip-cnt">${error}</div>
            </div>
        </c:forEach>
        <c:forEach var="tip" items="${command.tips}" varStatus="status">
            <div class="vd-tip tip-green">
                <i class="vd-tip-icon vd-icon icon-yes1"></i>
                <div class="vd-tip-cnt">${tip}</div>
            </div>
        </c:forEach>
        <!-- end -->
        <div class="tab-nav">
            <a class="tab-item current" href="#">基本信息</a>
            <c:if test="${coreSpuDto.spuLevel!=2 && showType ne 'op'}"><%--showType ne 'op'运营后台连接跳转无需这些操作--%>
                <c:if test="${empty skuGenerate.operateInfoId or skuGenerate.operateInfoId==0 and skuGenerate.checkStatus==3}">
                <a class="tab-item"   href="/vgoods/operate/openOperate.do?skuId=${skuGenerate.skuId}">运营信息</a>
                </c:if>
                <c:if test="${not empty skuGenerate.operateInfoId and skuGenerate.operateInfoId>0}">
                    <a class="tab-item"   href="/vgoods/operate/viewOperate.do?skuId=${skuGenerate.skuId}">运营信息</a>
                </c:if>
            </c:if>
        </div>
        <div class="detail-option-wrap">
            <c:if test="${showType ne 'op'}"><%--showType ne 'op'运营后台连接跳转无需这些操作--%>
                <div class="option-btns">
                    <c:if test="${ coreSpuDto.spuLevel!=2  && command.hasEditAuth && showType ne 'op'}">
                         <a id = 'editSkuBtn' class="btn btn-small btn-blue" href="/goods/vgoods/addSku.do?skuId=${skuGenerate.skuId}&spuId=${coreSpuDto.spuId}"> 编辑</a>
                    </c:if>
                    <c:if test="${coreSpuDto.spuLevel==2 && command.hasEditTempAuth}">
                            <a id = 'editSkuBtn' class="btn btn-small btn-blue J-sku-edit" data-spuid="${skuGenerate.spuId}" data-type="${command.skuType}" data-spuname="${coreSpuDto.spuShowName}" data-skuid="${skuGenerate.skuId}">编辑</a>
                    </c:if>
                    <c:if test="${ coreSpuDto.checkStatus==3 && skuGenerate.checkStatus!=3 && command.hasCheckAuth}">
                        <c:if test="${ empty logCheckGenerateList  }">
                            <a id = 'approveBtn' class="btn btn-small btn-blue J-sku-option" data-dlg='0' data-href="/goods/vgoods/checkSku.do" data-params='{"skuId":${skuGenerate.skuId},"checkStatus": 3, "spuId":${skuGenerate.spuId} }'>
                                审核通过
                            </a>
                        </c:if>
                        <c:if test="${logCheckGenerateList.size()>0}">
                            <a id = 'approveBtn' class="btn btn-small btn-blue J-sku-option" data-href="/goods/vgoods/checkSku.do" data-params='{"skuId":${skuGenerate.skuId},"checkStatus": 3, "spuId":${skuGenerate.spuId} }'>
                                重审通过
                            </a>
                        </c:if>
                    </c:if>
                    <c:if test="${ coreSpuDto.checkStatus==3&& skuGenerate.checkStatus!=2 && command.hasCheckAuth}">
                        <a id = 'vetoBtn' class="btn btn-small btn-blue J-sku-option" data-href="/goods/vgoods/checkSku.do" data-params='{"skuId":${skuGenerate.skuId},"checkStatus": 2, "spuId":${skuGenerate.spuId} }'>
                            <c:if test="${empty logCheckGenerateList }"> 审核不通过 </c:if>
                            <c:if test="${logCheckGenerateList.size()>0}"> 重审不通过 </c:if>
                        </a>
                    </c:if>
                    <a id = 'deleteSkuBtn' class="btn btn-small J-sku-del" data-spuid="${skuGenerate.spuId}" data-id="${skuGenerate.skuId}">删除</a>
                </div>
            </c:if>
        </div>
        <div class="detail-block">
            <div class="block-title">审核记录
                <c:if test="${skuGenerate.checkStatus==2}">
                    <span class="title-status status-red">审核不通过</span>
                </c:if>
                <c:if test="${skuGenerate.checkStatus==1}">
                    <span class="title-status status-yellow">审核中</span>
                </c:if>
                <c:if test="${skuGenerate.checkStatus==3}">
                    <span class="title-status status-green">审核通过</span>
                </c:if>
                <c:if test="${skuGenerate.checkStatus==0}">
                    <span class="title-status status-yellow">待完善</span>
                </c:if>
            </div>
            <div class="status-title">
                <div class="status-item">更新时间</div>
                <div class="status-item">审核人</div>
                <div class="status-item">审核状态</div>
                <div class="status-item">原因</div>
            </div>
            <div class="status-list">
                <c:forEach items="${logCheckGenerateList}" var="log" varStatus="index">
                    <c:if test="${index.count==6}">
                        <div class="status-more J-optional-more">
                    </c:if>
                    <div class="status-cnt">
                        <div class="status-item">
                            <fmt:formatDate value="${log.addTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </div>
                        <div class="status-item">${log.creatorName}</div>
                        <div class="status-item">${log.logStatusName}</div>
                        <div class="status-item">${log.logMessage}</div>
                    </div>
                    <c:if test="${logCheckGenerateList.size()== index.count && index.count>5 }">
            </div>
            </c:if>
            </c:forEach>
        </div>
<c:if test="${logCheckGenerateList.size() > 5 }">
        <div class="detail-optional J-toggle-show">
            <span class="toggle-txt J-more">展开更多<i class="vd-icon icon-down"></i></span>
            <span class="toggle-txt J-less" style="display: none;">收起<i class="vd-icon icon-up"></i></span>
        </div>
</c:if>
    </div>
    <div class="detail-block block-nohidden">
        <div class="block-title">SPU信息</div>
        <div class="detail-table">
            <table class="table table-base">
                <colgroup>
                    <col width="">
                    <col width="">
                    <col width="">
                    <col width="">
                    <col width="">
                    <col width="">
                    <col width="">
                </colgroup>
                <tbody>
                    <tr>
                        <th>商品名称</th>
                        <th>商品类型</th>
                        <th>商品等级</th>
                        <th>注册证号</th>
                        <th>审核状态</th>
                        <th>更新时间</th>
                        <th>Wiki资料</th>
                    </tr>
                    <tr>
                        <td>
                            <div class="line-clamp2">
                                <a href="javascript:void(0);" tabTitle='{"num":"vgoodsview${coreSpuDto.spuId}","link":"/goods/vgoods/viewSpu.do?spuId=${coreSpuDto.spuId}","title":"查看SPU"}'>${coreSpuDto.spuShowName}</a>
                            </div>
                        </td>
                        <td>

<c:forEach var="spuType" items="${spuTypeList}" varStatus="status">
  <c:if test="${coreSpuDto.spuType == spuType.sysOptionDefinitionId}"> ${spuType.title} </c:if>
</c:forEach>

                        </td>
                        <td>${coreSpuDto.spuLevelShow}</td>
                        <td>
                            ${coreSpuDto.registrationNumber}
                        </td>
                        <td>
<c:if test="${coreSpuDto.checkStatus==0}">
    <span class="title-status status-yellow">待完善</span>
</c:if>
                            <c:if test="${coreSpuDto.checkStatus==2}">
                                <span class="title-status status-red">审核不通过</span>
                            </c:if>
                            <c:if test="${coreSpuDto.checkStatus==1}">
                                <span class="title-status status-yellow">审核中</span>
                            </c:if>
                            <c:if test="${coreSpuDto.checkStatus==3}">
                                <span class="title-status status-green">审核通过</span>
                            </c:if>
                        </td>
                        <td>${coreSpuDto.modTimeShow}</td>
                        <td>
                            <c:if test="${not empty coreSpuDto.wikiHref}">
                            <a href="${coreSpuDto.wikiHref}" target="_blank">SPU Wiki</a>
                            </c:if>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="detail-block">
        <div class="block-title">基本信息</div>


        <div class="detail-table">
            <div class="table-item">
                <div class="table-th">订货号：</div>
                <div class="table-td">
                    ${skuGenerate.skuNo}
                </div>
            </div>
<c:if test="${not empty skuGenerate.model}">
                <div class="table-item">
                    <div class="table-th">制造商型号：</div>
                    <div class="table-td">
                        ${skuGenerate.model}
                    </div>
                </div>
</c:if>
<c:if test="${not empty skuGenerate.spec}">
                <div class="table-item">
                    <div class="table-th">规格：</div>
                    <div class="table-td">
                        ${skuGenerate.spec}
                    </div>
                </div>
</c:if>
            <c:if test="${coreSpuDto.spuLevel!=2}">
                <div class="table-item">
                    <div class="table-th">商品名称：</div>
                    <div class="table-td">
                        ${skuGenerate.skuName}
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">物料编码：</div>
                    <div class="table-td">
                        ${skuGenerate.materialCode}
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">供应商型号：</div>
                    <div class="table-td">
                        ${skuGenerate.supplyModel}
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">是否备货：</div>
                    <div class="table-td">
                <c:if test="${skuGenerate.isStockup==1}">
                    是
                </c:if>
                <c:if test="${skuGenerate.isStockup!=1}">
                    否
                </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">wiki链接：</div>
                    <div class="table-td">
                        <a href="${skuGenerate.wikiHref}" target="_blank">${skuGenerate.wikiHref}</a>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">检测报告：</div>
                    <div class="table-td">
                        <div class="info-pic">
                            <c:forEach items="${command.skuCheck}" var="item">
                                <c:if test="${not item.pdfFlag}">
                                <div class="info-pic-item J-show-big" data-src="${item.fullPath}">
                                    <img src="${item.fullPath}">
                                </div>
                                </c:if>
                                <c:if test="${item.pdfFlag}">
                                    <a target="_blank" href="${item.fullPath}">检测报告</a>&nbsp;&nbsp;
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">专利文件：</div>
                    <div class="table-td">
                        <div class="info-pic">
                            <c:forEach items="${command.skuPatent}" var="item">
                                <c:if test="${not item.pdfFlag}">
                                <div class="info-pic-item J-show-big" data-src="${item.fullPath}">
                                    <img src="${item.fullPath}">
                                </div>
                                </c:if>
                                <c:if test="${item.pdfFlag}">
                                    <a target="_blank" href="${item.fullPath}">专利文件</a>&nbsp;&nbsp;
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
    <div class="detail-block" <c:if test="${categoryType!=1}"> style="display:none" </c:if>  >
        <div class="block-title">
        首营信息

        </div>
        <div class="detail-table">
        <div class="table-item">
        <div class="table-th">注册证号/备案凭证号：</div>
        <div class="table-td">
        <a href="javascript:void(0);" tabTitle='{"num":"firstengage${firstEngage.firstEngageId}","link":"/firstengage/baseinfo/getFirstSearchDetail.do?firstEngageId=${firstEngage.firstEngageId}","title":"查看首营"}' >${firstEngage.registrationNumber}</a>
        </div>
        </div>

        <div class="table-item">
        <div class="table-th">注册证附件/备案凭证附件：</div>
        <div class="table-td">
            <div class="info-pic">
            <c:if test="${not empty firstEngage.registration.zczAttachments }">
                <c:forEach var="attachments" items="${firstEngage.registration.zczAttachments }">
                    <div class="info-pic-item J-show-big" data-src="${api_http}${attachments.domain }${attachments.uri }">
                    <img style="width:100%;height:100%" src="${api_http}${attachments.domain }${attachments.uri }" alt="">
                    </div><a class="printAtta" href="javascript:;">打印</a>
                </c:forEach>
            </c:if>
            </div>
        </div>
        </div>

        <div class="table-item">
        <div class="table-th">证件有效期：</div>
        <div class="table-td">
        ${firstEngage.effectStartDate} 至 ${firstEngage.effectEndDate}
        </div>
        </div>
        <div class="table-item">
        <div class="table-th">管理类别：</div>
        <div class="table-td">
        ${firstEngage.manageCategoryLevelShow}
        </div>
        </div>
        <div class="table-item">
        <div class="table-th">生产厂商：</div>
        <div class="table-td">
        ${firstEngage.productCompanyChineseName}
        </div>
        </div>
        <div class="table-item">
        <div class="table-th">旧国标分类：</div>
        <div class="table-td">
        ${firstEngage.oldStandardCategoryName}
        </div>
        </div>
        <div class="table-item">
        <div class="table-th">新国标分类：</div>
        <div class="table-td">
        ${firstEngage.newStandardCategoryName}
        </div>
        </div>
        <div class="table-item  ">
        <div class="table-th">审核状态：</div>
        <div class="table-td">
        <c:if test="${firstEngage.status==2}">
            <span class="title-status status-red">审核不通过</span>
        </c:if>
        <c:if test="${firstEngage.status==1}">
            <span class="title-status status-yellow">审核中</span>
        </c:if>
        <c:if test="${firstEngage.status==3}">
            <span class="title-status status-green">审核通过</span>
        </c:if>
        </div>
        </div>
        </div>

    </div>

    <c:if test="${coreSpuDto.spuLevel!='2'}">
        <div class="detail-block">
            <div class="block-title">商品属性</div>
            <div class="detail-table">

                    <div class="table-item">
                        <div class="table-th">选择带入属性：</div>
                        <div class="table-td J-spring-filter">
                            <c:forEach items="${baseAttributeVoList}" var="attr">
                                ${attr.baseAttributeName}、
                            </c:forEach>
                        </div>
                    </div>
        <c:forEach items="${baseAttributeVoList}" var="attr">
                    <div class="table-item">
                    <div class="table-th">${attr.baseAttributeName}：</div>
                    <div class="table-td">
                        <c:forEach items="${attr.attrValue}" var="attrVal">
                            <c:if test="${attrVal.selected}">
                                ${attrVal.attrValue}${attrVal.unitName}
                            </c:if>
                        </c:forEach>

                    </div>
                    </div>
        </c:forEach>
            </div>
        </div>

        <c:if test="${command.skuType==1 && showType ne 'op'}">
            <div class="detail-block">
                <div class="block-title">参数信息</div>
                <div class="detail-table">
                    <div class="table-item">
                        <div class="table-th">技术参数：</div>
                        <div class="table-td">
                            <c:if test="${not empty command.paramsName1}">
                                <c:forEach items="${command.paramsName1}" var="params" varStatus="status">
                                    ${command.paramsName1[status.index]} : ${command.paramsValue1[status.index]} <br>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                    <div class="table-item">
                        <div class="table-th">规格参数：</div>
                        <div class="table-td">
                            <c:if test="${not empty command.paramsName2}">
                                <c:forEach items="${command.paramsName2}" var="params" varStatus="status">
                                    ${command.paramsName2[status.index]} : ${command.paramsValue2[status.index]} <br>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                    <div class="table-item item-col">
                        <div class="table-th">性能参数：</div>
                        <div class="table-td">
                            <c:if test="${not empty command.paramsName3}">
                                <c:forEach items="${command.paramsName3}" var="params" varStatus="status">
                                    ${command.paramsName3[status.index]} : ${command.paramsValue3[status.index]} <br>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <div class="detail-block">
            <div class="block-title">物流和包装</div>
            <div class="detail-table">
                <div class="table-item">
                    <div class="table-th">SKU商品单位：</div>
                    <div class="table-td">
                        <c:if test="${not empty unitList }">
                            <c:forEach var="unit" items="${unitList}">
                                <c:if test="${ skuGenerate.baseUnitId ==unit.unitId  }"> ${unit.unitName} </c:if>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>

                <%--VDERP-2212 ERP新商品流-新增/编辑SKU（器械设备、配件），增加最小起订量字段--%>
                <c:if test="${coreSpuDto.spuType == 316 || coreSpuDto.spuType == 1008}">
                    <div class="table-item">
                        <div class="table-th">最小起订量：</div>
                        <div class="table-td">
                        <c:if test="${not empty unitList }">
                            <c:forEach var="unit" items="${unitList}">
                                <c:if test="${ skuGenerate.baseUnitId == unit.unitId  }">
                                    <fmt:formatNumber value="${ skuGenerate.minOrder}"  pattern="0"/> ${unit.unitName}
                                </c:if>
                            </c:forEach>
                        </c:if>
                        </div>
                    </div>
                </c:if>

                <c:if test="${command.skuType!=1}">
                    <div class="table-item">
                        <div class="table-th">商品最小单位：</div>
                        <div class="table-td">
                            <c:if test="${not empty unitList }">
                                <c:forEach var="unit" items="${unitList}">
                                    <c:if test="${ skuGenerate.unitId ==unit.unitId  }"> ${unit.unitName} </c:if>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                    <div class="table-item">
                        <div class="table-th">内含最小商品数量：</div>
                        <div class="table-td">
                            <c:if test="${not empty unitList }">
                                <c:forEach var="unit" items="${unitList}">
                                    <c:if test="${ skuGenerate.unitId ==unit.unitId  }"> ${ skuGenerate.changeNum} ${unit.unitName} </c:if>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                    <div class="table-item">
                        <div class="table-th">最小起订量：</div>
                        <div class="table-td">

                            <c:if test="${not empty unitList }">
                                <c:forEach var="unit" items="${unitList}">
                                    <c:if test="${ skuGenerate.baseUnitId ==unit.unitId  }">
                                        <fmt:formatNumber value="${ skuGenerate.minOrder}"  pattern="0"/>
                                        ${unit.unitName}
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>

                </c:if>

                <div class="table-item">
                    <div class="table-th">包装体积：</div>
                    <div class="table-td">
                        长度 ${skuGenerate.packageLength}cm 宽度 ${skuGenerate.packageWidth}cm 高度 ${skuGenerate.packageHeight}cm
                    </div>
                </div>
                <c:if test="${command.skuType!=1}">
                    <div class="table-item">
                        <div class="table-th">商品体积：</div>
                        <div class="table-td">
                            长度 ${skuGenerate.goodsLength}cm 宽度 ${skuGenerate.goodsWidth}cm 高度 ${skuGenerate.goodsHeight}cm

                        </div>
                    </div>
                </c:if>
                <div class="table-item">
                    <div class="table-th">毛重：</div>
                    <div class="table-td">
                        ${skuGenerate.grossWeight}kg
                    </div>
                </div>
                <c:if test="${command.skuType!=1}">
                    <div class="table-item">
                        <div class="table-th">净重：</div>
                        <div class="table-td">
                            ${skuGenerate.netWeight}kg
                        </div>
                    </div>
                </c:if>
                <c:if test="${command.skuType==1}">
                    <div class="table-item">
                        <div class="table-th">包装清单：</div>
                        <div class="table-td">
                            ${skuGenerate.packingList}
                        </div>
                    </div>
                </c:if>
            </div>

        </div>
        <c:if test="${command.skuType!=1}">
            <div class="detail-block">
                <div class="block-title">存储与效期</div>
                <div class="detail-table">
                    <div class="table-item item-col">
                        <div class="table-th">存储条件1：</div>
                        <div class="table-td">
                            <c:if test="${ skuGenerate.storageConditionOne ==1 }"> 常温 </c:if>
                            <c:if test="${ skuGenerate.storageConditionOne ==2 }"> 冷冻 </c:if>
                            <c:if test="${ skuGenerate.storageConditionOne ==3 }"> 冷藏 </c:if>
                        </div>
                    </div>
                    <div class="table-item item-col">
                        <div class="table-th">存储条件2：</div>
                        <div class="table-td">
                            <c:if test="${fn:contains(skuGenerate.storageConditionTwo, '1') }"> 阴凉 </c:if>
                            <c:if test="${ fn:contains(skuGenerate.storageConditionTwo, '2')}"> 干燥 </c:if>
                            <c:if test="${ fn:contains(skuGenerate.storageConditionTwo, '3') }"> 避光 </c:if>
                            <c:if test="${ fn:contains(skuGenerate.storageConditionTwo, '4') }"> 防潮 </c:if>
                        </div>
                    </div>
                    <div class="table-item item-col">
                        <div class="table-th">有效期：</div>
                        <div class="table-td">
                            <c:if test="${ skuGenerate.effectiveDayUnit==1 }"> ${skuGenerate.effectiveDays}天 </c:if>
                            <c:if test="${ skuGenerate.effectiveDayUnit==2 }"> ${skuGenerate.effectiveDays}月 </c:if>
                            <c:if test="${ skuGenerate.effectiveDayUnit==3 }"> ${skuGenerate.effectiveDays}年 </c:if>
                        </div>
                    </div>

                </div>
            </div>
        </c:if>


        <div class="detail-block">
            <div class="block-title">售后说明</div>
            <div class="detail-table">
                <div class="table-item">
                    <div class="table-th">售后内容：</div>
                    <div class="table-td">

                        <c:if test="${fn:contains(skuGenerate.afterSaleContent, '1')}"> 包安装； </c:if>
                        <c:if test="${fn:contains(skuGenerate.afterSaleContent, '2')}"> 包培训； </c:if>
                        <c:if test="${fn:contains(skuGenerate.afterSaleContent, '3')}"> 物流点自提； </c:if>
                        <c:if test="${fn:contains(skuGenerate.afterSaleContent, '4')}"> 送货上门，含卸货上楼； </c:if>
                        <c:if test="${fn:contains(skuGenerate.afterSaleContent, '5')}"> 送货上门，含卸货到楼下 ；</c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">质保年限：</div>
                    <div class="table-td">
                        ${skuGenerate.qaYears}年
                    </div>
                </div>
                <c:if test="${command.skuType==1}">
                    <div class="table-item">
                        <div class="table-th">质保期限规则：</div>
                        <div class="table-td">
                            ${skuGenerate.qaRule}
                        </div>
                    </div>

                    <div class="table-item">
                        <div class="table-th">质保外维修价格：</div>
                        <div class="table-td">
                            ${skuGenerate.qaOutPrice}元
                        </div>
                    </div>
                    <div class="table-item">
                        <div class="table-th">响应时间：</div>
                        <div class="table-td">
                            ${skuGenerate.qaResponseTime}小时
                        </div>
                    </div>
                    <div class="table-item">
                        <div class="table-th">有无备用机：</div>
                        <div class="table-td">
                            <c:if test="${ skuGenerate.hasBackupMachine ==1 }"> 有 </c:if>
                            <c:if test="${ skuGenerate.hasBackupMachine !=1 }"> 无 </c:if>
                        </div>
                    </div>
                    <div class="table-item">
                        <div class="table-th">供应商延保价格：</div>
                        <div class="table-td">
                            ${skuGenerate.supplierExtendGuaranteePrice}元/年
                        </div>
                    </div>
                    <div class="table-item">
                        <div class="table-th">核心零部件价格：</div>
                        <div class="table-td">
                            <c:forEach items="${command.skuPart}" var="item" varStatus="status">
                                <a href="${item.fullPath}" target="_blank">下载${status.count}</a>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
        <div class="detail-block">
            <div class="block-title">退换货说明</div>
            <div class="detail-table">
                <div class="table-item">
                    <div class="table-th">退货条件：</div>
                    <div class="table-td">
                        <c:if test="${ skuGenerate.returnGoodsConditions ==1 }"> 允许退货 </c:if>
                        <c:if test="${ skuGenerate.returnGoodsConditions ==0 }"> 不允许退货 </c:if>
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">运费说明：</div>
                    <div class="table-td">
                        ${skuGenerate.freightIntroductions}
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">换货条件：</div>
                    <div class="table-td">
                        ${skuGenerate.exchangeGoodsConditions}
                    </div>
                </div>
                <div class="table-item">
                    <div class="table-th">换货方式：</div>
                    <div class="table-td">
                        ${skuGenerate.exchangeGoodsMethod}
                    </div>
                </div>
            </div>
        </div>
        <div class="detail-block">
            <div class="block-title">商品说明</div>
            <div class="detail-table">
                <div class="table-item item-col">
                    <div class="table-th">商品备注：</div>
                    <div class="table-td">
                        ${skuGenerate.goodsComments}
                    </div>
                </div>
            </div>
        </div>

        <c:if test="${showType ne 'op'}">
            <div class="detail-block block-nohidden">
                <div class="block-title">商品授权与定价</div>
                <div class="detail-table">
                    <table class="table table-base">
                        <colgroup>
                            <col width="">
                            <col width="">
                            <col width="">
                            <col width="">
                            <col width="">
                            <col width="">
                            <col width="">
                            <col width="">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>授权地区</th>
                                <th>授权客户类型</th>
                                <th>批量政策</th>
                                <th>定价信息</th>
                                <th>终端报备与授权书</th>
                                <th>成本价/批量价</th>
                                <th>核价有效期</th>
                                <th>更新时间</th>
                                <th>操作人</th>
                            </tr>
                            <c:if test="${ not empty goodsChannelPriceList}">
                                <c:forEach var="list" items="${goodsChannelPriceList}" varStatus="status">
                                    <tr>
                                        <td>${list.provinceName}&nbsp;${list.cityName}</td>
                                        <td>${list.customerTypeComments}</td>
                                        <td>${list.batchPolicy}</td>
                                        <td>
                                    VIP价:${list.vipPrice}<br>
                                    经销商指导价:${list.distributionPrice}<br>
                                    非公机构终端价:${list.privatePrice}<br>
                                    公立终端价:${list.publicPrice}<br>
                                    市场指导价:${list.marketPrice}<br>
                                    <c:if test="${goodsChannelPriceList[0].minNum > 0 }">
                                        最小起订量:${list.minNum}<c:if test="${not empty unitList }">
                                        <c:forEach var="unit" items="${unitList}">
                                            <c:if test="${ skuGenerate.baseUnitId ==unit.unitId  }"> ${unit.unitName} </c:if>
                                        </c:forEach>
                                    </c:if>
                                    </c:if>
                                    <c:if test="${goodsChannelPriceList[0].minNum == 0 }">
                                        最小起订价:<fmt:formatNumber type="number" value="${list.minAmount}" pattern="0.00" maxFractionDigits="2" />元
                                    </c:if>
                                        </td>
                                        <td>
                                            <span class="status-red">
                                                需要终端报备：<c:choose>
                                                    <c:when test="${list.isReportTerminal eq 1}">是</c:when>
                                                    <c:otherwise>否</c:otherwise>
                                                </c:choose><br>
                                                可出厂家授权书：<c:choose>
                                                    <c:when test="${list.isManufacturerAuthorization eq 1}">
                                                        是
                                                    </c:when>
                                                    <c:when test="${list.isManufacturerAuthorization eq 2}">
                                                        是，需保证金
                                                    </c:when>
                                                    <c:otherwise>
                                                        否
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                        </td>
                                        <td>
                                            批量价：<a class="J-price-more" data-href="/goods/goods/showPriceList.do?goodsChannelPriceId=${list.goodsChannelPriceId}&priceType=2">点击查看</a>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${list.periodDate}" type="date" pattern="yyyy-MM-dd" />
                                        </td>
                                        <td>
                                            <date:date value="${list.addTime}" format="yyyy-MM-dd HH:mm:ss" />
                                        </td>
                                        <td>${list.username}</td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            <c:if test="${   empty goodsChannelPriceList}">
                                <tr>
                                    <td class="list-no-data" colspan="9">
                                        <i class="vd-icon icon-info1"></i>没有匹配数据
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
        <div class="detail-block block-nohidden">
            <div class="block-title">库存信息</div>
            <div class="detail-table">
                <table class="table table-base">
                    <colgroup>
                        <col width="">
                        <col width="">
                        <col width="">
                        <col width="">
                        <col width="">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>在线库存</th>
                            <th>订单占用</th>
                            <th>可调剂</th>
                            <th>在途（订单/数量/预计到达）</th>
                            <th>近期入库日期</th>
                            <th>近期出库日期</th>
                        </tr>
                        <tr>
                            <td>${goods.stockNum}</td>
                            <td>${goods.orderOccupy}</td>
                            <td>${goods.adjustableNum}</td>
                            <td>${goods.onWayNum}</td>
                            <td>
<%--                                <date:date value="${goods.goodsLastInTime}" format="yyyy-MM-dd HH:mm:ss" />--%>
                            </td>
                            <td>
<%--                                <date:date value="${goods.goodsLastOutTime}" format="yyyy-MM-dd HH:mm:ss" />--%>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <c:if test="${showType ne 'op'}">
            <div class="detail-block block-nohidden">
                <div class="block-title">采购价格</div>
                <div class="detail-table">
                    <table class="table table-base">
                        <colgroup>
                            <col width="">
                            <col width="">
                            <col width="">
                            <col width="">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>定价信息</th>
                                <th>成本价/批量价</th>
                                <th>核价有效期</th>
                                <th>更新时间</th>
                                <th>操作人</th>
                            </tr>
                            <c:forEach var="list" items="${goodsChannelPriceList2}" varStatus="statu">
                                <tr>
                                    <td>
                                        <c:if test="${goodsChannelPriceList2[0].minNum > 0 }">
                                            最小起订量 ：
                                        </c:if>
                                        <c:if test="${goodsChannelPriceList2[0].minNum == 0 }">
                                            最小起订价 ：
                                        </c:if>
                                        <c:if test="${goodsChannelPriceList2[0].minNum > 0 }">
                                            ${list.minNum}
                                            <c:if test="${not empty unitList }">
                                            <c:forEach var="unit" items="${unitList}">
                                                <c:if test="${ skuGenerate.baseUnitId ==unit.unitId  }"> ${unit.unitName} </c:if>
                                            </c:forEach>
                                        </c:if>
                                        </c:if>
                                        <c:if test="${goodsChannelPriceList2[0].minNum == 0 }">
                                            <fmt:formatNumber type="number" value="${list.minAmount}" pattern="0.00" maxFractionDigits="2" />元
                                        </c:if>
                                    </td>
                                    <td>
                                        成本价：<a class="J-price-one" data-href="/goods/goods/showPriceList.do?goodsChannelPriceId=${list.goodsChannelPriceId}&priceType=1">点击查看</a><br>
                                        批量价：<a class="J-price-more" data-href="/goods/goods/showPriceList.do?goodsChannelPriceId=${list.goodsChannelPriceId}&priceType=2">点击查看</a>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${list.periodDate}" type="date" pattern="yyyy-MM-dd" />
                                    </td>
                                    <td>
                                        <date:date value="${list.addTime}" format="yyyy-MM-dd HH:mm:ss" />
                                    </td>
                                    <td>${list.username}</td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty goodsChannelPriceList2}">
                                <td class="list-no-data" colspan="5">
                                    <i class="vd-icon icon-info1"></i>没有匹配数据
                                </td>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="detail-block block-nohidden">
                <div class="block-title">运营</div>
                <div class="detail-table">
                    <table class="table table-base">
                        <colgroup>
                            <col width="">
                            <col width="">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>结算价</th>
                                <th>更新时间</th>
                                <th>操作人</th>
                            </tr>
                            <tr>
                                <td>
                                    <shiro:hasPermission name="/order/quote/settlementPrice.do">
                                        <fmt:formatNumber type="number" value="${goodsSettlementPriceInfo.settlementPrice}" pattern="0.00" maxFractionDigits="2" />
                                    </shiro:hasPermission>
                                </td>
                                <td>
                                    <date:date value="${goodsSettlementPriceInfo.modTime}" />
                                </td>
                                <td>${goodsSettlementPriceInfo.username}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="detail-block block-nohidden">
                <div class="block-title">主要供应商</div>
                <div class="detail-table">
                    <table class="table table-base">
                        <colgroup>
                            <col width="">
                            <col width="">
                            <col width="">
                            <col width="">
                            <col width="">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>供应商名称</th>
                                <th>供应次数</th>
                                <th>供应数量</th>
                                <th>近期供应价格</th>
                                <th>最近供应时间</th>
                                <th>操作</th>
                            </tr>
                            <c:forEach var="list" items="${mainSupplyList}" varStatus="statu">
                                <tr>
                                    <td>${list.traderSupplierName}</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty mainSupplyList}">
                                <td class="list-no-data" colspan="6">
                                    <i class="vd-icon icon-info1"></i>没有匹配数据
                                </td>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
    </c:if>
    </div>
    <script type="text/tmpl" class="J-dlg-tmpl">
        <div class="del-wrap">
            <div class="del-tip">
                <i class="vd-icon icon-caution2"></i><span class="J-dlg-tip"></span>
            </div>
            <form class="J-dlg-form">
                <div class="del-cnt base-form">
                    <textarea name="content" id="" cols="30" rows="10" class="input-textarea J-dlg-cnt" placeholder=""></textarea>
                </div>
            </form>
        </div>
    </script>
    <script type="text/tmpl" class="J-sku-tmpl">
        <div class="edit-sku-wrap base-form form-span-6">
            <form class="J-sku-form" style="display: none;">
                <div class="form-item">
                    <div class="form-label">SPU：</div>
                    <div class="form-fields form-label-txt">{{=spuName}}</div>
                </div>
                <div class="form-item">
                    <div class="form-label">
                        <span class="must">*</span>
                        {{if(type == '1'){ }}
                            制造商型号：
                        {{ }else{ }}
                            规格：
                        {{ } }}
                    </div>
                    <div class="form-fields">
                        <div class="form-col col-10">
                            <input class="input-text J-cnt" name="content" type="text">
                        </div>
                    </div>
                </div>
            </form>
            <div class="dlg-loading J-sku-loading">
                <img src="${pageContext.request.contextPath}/static/new/img/loading.gif" alt="">
            </div>
        </div>
    </script>

    <script type="text/tmpl" class="J-price-one-tmpl">
        <div class="dlg-price-wrap">
            <div class="dlg-loading J-dlg-loading">
                <img src="${pageContext.request.contextPath}/static/new/img/loading.gif" alt="">
            </div>
            <div class="dlg-cnt J-dlg-cnt" style="display: none;">
                <table class="table table-normal">
                    <colgroup>
                        <col width="50%">
                        <col width="50%">
                    </colgroup>
                    <tbody class="J-dlg-list">
                        <tr>
                            <th>时间</th>
                            <th>价格</th>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </script>

    <script type="text/tmpl" class="J-price-more-tmpl">
        <div class="dlg-price-wrap">
            <div class="dlg-loading J-dlg-loading">
                <img src="${pageContext.request.contextPath}/static/new/img/loading.gif" alt="">
            </div>
            <div class="dlg-cnt J-dlg-cnt" style="display: none;">
                <table class="table table-normal">
                    <colgroup>
                        <col width="50%">
                        <col width="50%">
                    </colgroup>
                    <tbody class="J-dlg-list">
                        <tr>
                            <th>数量</th>
                            <th>价格</th>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </script>

    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/goods/vgoods/sku_view.js?rnd=<%=Math.random()%>"></script>

<script type="text/javascript"
src='${pageContext.request.contextPath}/static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
<script>
    $(function(){
        $(".printAtta").each(function(){
        $(this).click(function(){
            $(this).hide();
            $(this).prev().printArea();
            $(this).show()
        })
    })
})
</script>

</body>
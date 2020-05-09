<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>新商品流列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/vgoods/list.css?rnd=<%=Math.random()%>">
</head>
<%--Anna.liu  编辑所有--%>
<%--Ethan.lin  编辑临时--%>
<%--Ted.dong  审核--%>

<body>
    <div class="erp-wrap">
        <div class="erp-title">
            <div class="erp-title-txt">商品管理 </div>
        </div>
        <div class="tab-nav J-list-tab" data-name="">
            <a class=" tab-item <c:if  test="${empty command.spuCheckStatus or command.spuCheckStatus==-1 }"> current </c:if>"
                href="/goods/vgoods/list.do?spuCheckStatus=-1" data-value="">全部<span class="J-allCount"></span></a>
            <a class="tab-item  <c:if test="${command.spuCheckStatus == 1}"> current </c:if>"
                href="/goods/vgoods/list.do?spuCheckStatus=1" data-value="">审核中<span class="J-preCount"></span></a>
            <a class="tab-item  <c:if test="${ command.spuCheckStatus ==3}"> current </c:if>" href="/goods/vgoods/list.do?spuCheckStatus=3" data-value="">审核通过<span class="J-approveCount"></span></a>
            <a class="tab-item  <c:if test="${command.spuCheckStatus ==2}"> current </c:if>" href="/goods/vgoods/list.do?spuCheckStatus=2" data-value="">审核不通过<span class="J-rejectCount"></span></a>
            <a class="tab-item  <c:if test="${command.spuCheckStatus ==0}"> current </c:if>" href="/goods/vgoods/list.do?spuCheckStatus=0" data-value="">待完善</a>
        </div>
        <div class="erp-top-option">
            <div class="option-btn-wrap">
                <c:if test="${command.hasEditAuth}">
                    <a class="btn btn-blue btn-small" tabTitle='{"num":"addnewcorespu","link":"/goods/vgoods/addSpu.do","title":"新增SPU"}'>新增SPU</a>
                </c:if>
                <c:if test="${command.hasEditTempAuth}">
                    <a class="btn btn-blue btn-small" tabTitle='{"num":"addnewtempspu","link":"/goods/vgoods/addTempSpu.do","title":"新增临时SPU", "random": "1"}'>新增SPU(临时)</a>
                </c:if>
                <!-- a class="btn btn-blue btn-small" layerparams='{"width":"500px","height":"200px","title":"批量商品授权与定价","link":"./batchAuthorizationPricing.do"}'>批量商品授权与定价</a> -->
            </div>
        </div>
        <div class="erp-block base-form search-wrap J-search-wrap">
            <div class="search-list">
                <div class="search-item item-search-select">
                    <div class="item-label">
                        <select name="searchType" class="J-select J-search-select">
                            <option value="1" <c:if test="${command.searchType == 1}"> selected </c:if> data-place="请输入商品名称/订货号/SPU ID/物料编号/注册证号">关键词</option>
                            <option value="2" <c:if test="${command.searchType == 2}"> selected </c:if> data-place="请输入商品名称">商品名称</option>
                            <option value="3" <c:if test="${command.searchType == 3}"> selected </c:if> data-place="请输入订货号">订货号</option>
                            <option value="4" <c:if test="${command.searchType == 4}"> selected </c:if> data-place="请输入物料编号">物料编号</option>
                            <option value="5" <c:if test="${command.searchType == 5}"> selected </c:if> data-place="请输入注册证号">注册证号</option>
                            <option value="6" <c:if test="${command.searchType == 6}"> selected </c:if> data-place="请输入SPU ID">SPU ID</option>
                        </select>
                    </div>
                    <div class="item-fields">
                        <div class="search-input-wrap item-input">
                            <input type="text" name="searchValue" autocomplete="off" class="input-text J-search-word" value="${command.searchValue}">
                            <ul class="search-history-wrap J-search-history" style="display:none;"></ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="search-list J-search-more-cnt" style="display: none;">
                <div class="search-item">
                    <div class="item-label">商品分类：</div>
                    <div class="item-fields">
                        <input type="hidden" class="J-category-value" name="categoryId" value="${command.categoryId}">
                        <div class="select-lv-wrap J-category-wrap"></div>
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">商品品牌：</div>
                    <div class="item-fields fields-suggest">
                        <input type="text" class="input-text J-suggest-input" data-url="/firstengage/brand/brandName.do" name="brandName" value="${command.brandName}">
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">商品等级：</div>
                    <div class="item-fields">
                        <select name="spuLevel" class="J-select">
                            <option value="-1">全部</option>
                            <option value="0" <c:if test="${command.spuLevel == 0}"> selected </c:if> >其他产品</option>
                            <option value="1" <c:if test="${command.spuLevel == 1}"> selected </c:if> >核心产品</option>
                            <option value="2" <c:if test="${command.spuLevel == 2}"> selected </c:if> >临时产品</option>
                        </select>
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">商品类型：</div>
                    <div class="item-fields">
                        <select name="spuType" class="J-select">
                            <option value="-1">全部</option>
                            <c:forEach var="spuType" items="${spuTypeList}" varStatus="status">
                                <option value="${spuType.sysOptionDefinitionId }" <c:if test="${command.spuType == spuType.sysOptionDefinitionId}"> selected </c:if>
                                    >
                                    ${spuType.title}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">管理类别：</div>
                    <div class="item-fields">
                        <select name="manageCategoryLevel" class="J-select">
                            <option value="-1">全部</option>
                            <option value="968" <c:if test="${command.manageCategoryLevel == 968}"> selected </c:if>>一类</option>
                            <option value="969" <c:if test="${command.manageCategoryLevel == 969}"> selected </c:if>>二类</option>
                            <option value="970" <c:if test="${command.manageCategoryLevel == 970}"> selected </c:if>>三类</option>
                        </select>
                    </div>
                </div>
                <div class="search-item item-lv-right">
                    <div class="item-label">新国标分类：</div>
                    <div class="item-fields">
                        <input type="hidden" name="newStandardCategoryId" class="J-stand-value" value="${command.newStandardCategoryId}">
                        <div class="select-lv-wrap J-stand-wrap"></div>
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">厂商：</div>
                    <div class="item-fields fields-suggest">
                        <input type="text" class="input-text J-suggest-input" data-url="/goods/vgoods/productCompany.do" name="productCompanyName" value="${command.productCompanyName}">
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">归属（经理）：</div>
                    <div class="item-fields">
                        <select name="assignmentManagerId" class="J-select">
                            <option value="-1">全部</option>
                            <c:forEach var="user" items="${assUser}">
                                <option value="${user.userId}" <c:if test="${command.assignmentManagerId == user.userId}"> selected </c:if>>${user.username}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">归属（助理）：</div>
                    <div class="item-fields">
                        <select name="assignmentAssistantId" class="J-select">
                            <option value="-1">全部</option>
                            <c:forEach var="user" items="${assUser}">
                                <option value="${user.userId}" <c:if test="${command.assignmentAssistantId == user.userId}"> selected </c:if>>${user.username}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">科室：</div>
                    <div class="item-fields fields-suggest">
                        <input type="text" class="input-text J-suggest-input" data-url="/goods/vgoods/departmentsHospital.do" name="departmentName"  value="${command.departmentName}">
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">SPU审核状态：</div>
                    <div class="item-fields">
                        <select name="spuCheckStatus" class="J-select">
                            <option value="-1">全部</option>
                            <c:forEach var="checkStatus" items="${command.checkStatus}" varStatus="status">
                                <c:if test="${checkStatus.status != 4&&checkStatus.status != 0}">
                                <option value="${checkStatus.status}" <c:if test="${command.spuCheckStatus == checkStatus.status}"> selected </c:if> >${checkStatus.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">SKU审核状态：</div>
                    <div class="item-fields">
                        <select name="skuCheckStatus" class="J-select">
                            <option value="-1">全部</option>
                            <c:forEach var="checkStatus" items="${command.checkStatus}" varStatus="status">
                                <c:if test="${checkStatus.status != 4}">
                                <option value="${checkStatus.status}" <c:if test="${command.skuCheckStatus == checkStatus.status}"> selected </c:if>>${checkStatus.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">更新时间：</div>
                    <div class="item-fields">
                        <div class="item-fields J-date-range">
                            <div class="input-date item-input">
                                <input type="text" name="modTimeStart" class="input-text" placeholder="请选择日期" readonly value="${command.modTimeStart}">
                            </div>
                            <div class="search-item-gap">-</div>
                            <div class="input-date item-input">
                                <input type="text" name="modTimeEnd" class="input-text" placeholder="请选择日期" readonly value="${command.modTimeEnd}">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">运营信息：</div>
                    <div class="item-fields">
                        <select name="operateInfoFlag" class="J-select">
                            <option value="-1">全部</option>
                            <option value="0" <c:if test="${command.operateInfoFlag == 0}"> selected </c:if> >未添加</option>
                            <option value="1" <c:if test="${command.operateInfoFlag == 1 }"> selected </c:if>>已添加</option>

                        </select>
                    </div>
                </div>
                <div class="search-item">
                    <div class="item-label">是否备货：</div>
                    <div class="item-fields">
                        <select name="isStockup" class="J-select">
                            <option value="-1">全部</option>
                            <option value="0" <c:if test="${command.isStockup == 0}"> selected </c:if> >否</option>
                            <option value="1" <c:if test="${command.isStockup == 1}"> selected </c:if> >是</option>
                        </select>
                    </div>
                </div>

                <div class="search-item">
                    <div class="item-label">推送状态：</div>
                    <div class="item-fields">
                        <select name="pushStatus" class="J-select">
                            <option value="-1">全部</option>
                            <option value="3" <c:if test="${command.pushStatus == 3}"> selected </c:if> >全部推送</option>
                            <option value="1" <c:if test="${command.pushStatus == 1}"> selected </c:if> >贝登推送</option>
                            <option value="2" <c:if test="${command.pushStatus == 2}"> selected </c:if> >医械购推送</option>
                            <!-- add by Tomcat.Hui 2020/3/11 10:50 上午 .Desc: VDERP-2140 erp-新商品流-推送状态筛选项无科研购推送. start -->
                            <option value="4" <c:if test="${command.pushStatus == 4}"> selected </c:if> >科研购推送</option>
                            <!-- add by Tomcat.Hui 2020/3/11 10:50 上午 .Desc: VDERP-2140 erp-新商品流-推送状态筛选项无科研购推送. end -->
                            <option value="0" <c:if test="${command.pushStatus == 0}"> selected </c:if> >未推送</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="search-btns">
                <div class="btn btn-small btn-blue-bd J-search">搜索</div>
                <div class="btn btn-small J-reset">重置</div>
            </div>
            <div class="search-more J-search-show-toggle">
                <span class="J-more">更多筛选条件<i class="vd-icon icon-down"></i></span>
                <span class="J-less" style="display: none;">精选筛选条件<i class="vd-icon icon-up"></i></span>
            </div>
        </div>
        <form autocomplete="off" onsubmit="return false;">
            <div class="erp-block base-form erp-block-list">
                <div class="option-wrap J-fix-wrap">
                    <div class="option-fix-wrap cf J-fix-cnt">
                        <button class="btn btn-small J-prod-stock btn-disabled">批量设置备货</button>
    <%--                    <button class="btn btn-small  ">上传税收分类编码</button>--%>
    <%--                    <button class="btn btn-small">批量商品授权与定价</button>--%>
                        <div class="option-r"></div>
                    </div>
                </div>
                <div class="list-table">
                    <div class="table-th">
                        <div class="th">
                            <div class="input-checkbox">
                                <label class="input-wrap">
                                    <input type="checkbox" class="J-select-list-all">
                                    <span class="input-ctnr"></span>
                                </label>
                            </div>
                        </div>
                        <div class="th">商品信息</div>
                        <div class="th">商品类型</div>
                        <div class="th">商品等级</div>
                        <div class="th">注册证号</div>
                        <div class="th">审核状态</div>
                        <div class="th">更新时间</div>
                        <div class="th">Wiki资料</div>
                        <div class="th">运营信息</div>
                        <div class="th">操作</div>
                    </div>
                    <c:if test="${not empty list}">
                    <c:forEach var="spuVO" items="${list}" varStatus="status">
                        <div class="table-tr">
                            <div class="tr-lv1 J-item-wrap J-list">
                                <div class="tr-list">
                                    <div class="tr-item">
                                        <div class="input-checkbox">
                                            <label class="input-wrap">
                                                <input type="checkbox" class="J-select-spu" value="${spuVO.spuId}">
                                                <span class="input-ctnr"></span>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="tr-item item-icon-wrap">
                                        <div class="line-clamp2">
                                            <a tabTitle='{"num":"vgoodsview${spuVO.spuId}","link":"/goods/vgoods/viewSpu.do?spuId=${spuVO.spuId}","title":"查看SPU"}' href="#" class="">${spuVO.spuShowName}</a>

                                        </div>
                                        <span>${spuVO.spuId}</span>
                                        <c:if test="${spuVO.firstEngageStatus==2}">
                                            <div class="tip-wrap">
                                                <i class="vd-icon icon-info2">
                                                    <div class="tip arrow-left">
                                                        <div class="tip-con">
                                                            首营信息审核不通过，请及时修改，或通知提交人。
                                                        </div>
                                                        <span class="arrow arrow-out">
                                                            <span class="arrow arrow-in"></span>
                                                        </span>
                                                    </div>
                                                </i>
                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="tr-item J-spu-type" data-type="${spuVO.spuType}">
                                        <c:forEach var="spuType" items="${spuTypeList}" varStatus="status">
                                            <c:if test="${spuVO.spuType == spuType.sysOptionDefinitionId}"> ${spuType.title} </c:if>
                                        </c:forEach>
                                    </div>
                                    <div class="tr-item">${spuVO.spuLevelShow}</div>
                                    <div class="tr-item"><a href="javascript:void(0);" tabTitle='{"num":"firstengage${spuVO.firstEngageId}","link":"/firstengage/baseinfo/getFirstSearchDetail.do?firstEngageId=${spuVO.firstEngageId}","title":"查看首营"}' >${spuVO.registrationNumber}</a></div>
                                    <div class="tr-item">
                                        <c:if test="${spuVO.checkStatus==2}">
                                            <span class="  status-red">审核不通过</span>
                                        </c:if>
                                        <c:if test="${spuVO.checkStatus==1}">
                                            <span class="  status-yellow">审核中</span>
                                        </c:if>
                        <c:if test="${spuVO.checkStatus==0}">
                            <span class="  status-yellow">待完善</span>
                        </c:if>
                                        <c:if test="${spuVO.checkStatus==3}">
                                            <span class="  status-green">审核通过</span>
                                        </c:if>
                                    </div>
                                    <div class="tr-item"> ${spuVO.modTimeShow}</div>
                                    <div class="tr-item">
                        <c:if test="${not empty spuVO.wikiHref}">
                                        <a href="${spuVO.wikiHref}" target="_blank">SPU Wiki</a>
                        </c:if>
                                    </div>
                                    <div class="tr-item">${spuVO.operateInfoIdShow}</div>
                                    <div class="tr-item">
                                        <c:if test="${spuVO.spuLevel!=2 && command.hasEditAuth }" >
                                            <div class="option-select-wrap J-option-select-wrap">
                                                <div class="option-select-btn" tabtitle={"num":"vgoodseditspu${spuVO.spuId}","link":"/goods/vgoods/addSpu.do?spuId=${spuVO.spuId}","title":"编辑SPU"}>编辑 </div> <div class="option-select-icon J-option-select-icon">
                                                    <i class="vd-icon icon-down"></i>
                                                </div>
                                                <div class="option-select-list">
                                                    <div class="option-select-item" tabtitle={"num":"vgoodsaddsku${spuVO.spuId}","link":"/goods/vgoods/addSku.do?spuId=${spuVO.spuId}","title":"新增SKU"}>新增SKU </div>
                                                    <div class="option-select-item" tabtitle={"num":"changeTempToCoreSpu${spuVO.spuId}","link":"/vgoods/operate/openOperate.do?spuId=${spuVO.spuId}","title":"添加运营信息"}>添加运营信息</div>
                                                    <div class="option-select-item J-del-spu" data-id="${spuVO.spuId}" href="/goods/vgoods/deleteSpu.do?spuId=${spuVO.spuId}">删除</div>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${spuVO.spuLevel==2 && command.hasEditTempAuth }" >
                                            <div class="option-select-wrap J-option-select-wrap">
                                                <div class="option-select-btn" tabtitle={"num":"vgoodseditspu${spuVO.spuId}","link":"/goods/vgoods/addTempSpu.do?spuId=${spuVO.spuId}","title":"编辑SPU"}>编辑 </div>
                                                <div class="option-select-icon J-option-select-icon">
                                                <i class="vd-icon icon-down"></i>
                                                </div>
                                                <div class="option-select-list">
                                                    <div class="option-select-item J-sku-edit" data-spuid="${spuVO.spuId}" data-spuname="${spuVO.spuShowName}">新增SKU </div>
                                                    <div class="option-select-item J-del-spu" data-id="${spuVO.spuId}" href="/goods/vgoods/deleteSpu.do?spuId=${spuVO.spuId}">删除</div>
                                                    <c:if test="${command.hasEditAuth}">
                                                        <div class="option-select-item" tabtitle={"num":"changeTempToCoreSpu${spuVO.spuId}","link":"/goods/vgoods/changeTempToCoreSpu.do?spuId=${spuVO.spuId}","title":"转为普通商品"}  >转为普通商品</div>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test="${spuVO.spuLevel==2 && command.hasEditAuth && !command.hasEditTempAuth }" >
                                            <div class="option-select-wrap J-option-select-wrap">
                                            <div class="option-select-btn" tabtitle={"num":"changeTempToCoreSpu${spuVO.spuId}","link":"/goods/vgoods/changeTempToCoreSpu.do?spuId=${spuVO.spuId}","title":"转为普通商品"}>转为普通商品 </div>
                                            </div>
                                        </c:if>

                        </div>
                                </div>

                                <div class="tr-lv2 J-item-wrap">
                                    <c:forEach var="skuVO" items="${spuVO.coreSkuBaseVOList}" varStatus="status">
                                        <div class="tr-list J-sku-item">
                                            <div class="tr-item">
                                                <div class="input-checkbox">
                                                    <label class="input-wrap">
                                                        <input type="checkbox" class="J-select-sku" value="${skuVO.skuId}">
                                                        <span class="input-ctnr"></span>
                                                    </label>
                                                </div>
                                            </div>
                                            <div class="tr-item">
                                                <div class="line-clamp2">
                                                    <a tabtitle={"num":"vgoodsviewsku${skuVO.skuId}","link":"/goods/vgoods/viewSku.do?skuId=${skuVO.skuId}&spuId=${skuVO.spuId}","title":"查看SKU"} href="#" class="">
                                                     <c:if test="${skuVO.showName != null }">${skuVO.showName}</c:if><c:if test="${empty skuVO.showName}">${spuVO.spuShowName}</c:if>
                                                    </a>
                                                </div>
                                            </div>
                                            <div class="tr-item">
                                                <span class="item-label">订货号：</span>
                                                <span class="item-value">${skuVO.skuNo}</span>
                                            </div>
                                            <div class="tr-item">
                                                <span class="item-label">归属：</span>
                                                <span class="item-value">${skuVO.assignment}</span>
                                            </div>
                                            <div class="tr-item">
                                                <span class="item-label">备货：</span>
                                                <span class="item-value">${skuVO.isStockupShow}</span>
                                            </div>
                                            <div class="tr-item">
                                        <c:if test="${skuVO.checkStatus==0}">
                                            <span class="  status-yellow">待完善</span>
                                        </c:if>
                                                <c:if test="${skuVO.checkStatus==2}">
                                                    <span class="  status-red">审核不通过</span>
                                                </c:if>

                                                <c:if test="${skuVO.checkStatus==1}">
                                                    <span class="  status-yellow">审核中</span>
                                                </c:if>
                                                <c:if test="${skuVO.checkStatus==3}">
                                                    <span class="  status-green">审核通过</span>
                                                </c:if>
                                            </div>
                                            <div class="tr-item"><fmt:formatDate value="${skuVO.modTime}" pattern="yyyy-MM-dd HH:mm:ss" /></div>
                                            <div class="tr-item">
                                                <c:if test="${not empty spuVO.wikiHref}">
                                                <a href="${spuVO.wikiHref}" target="_blank">SPU Wiki</a><br>
                                                </c:if>
                                                <c:if test="${not empty skuVO.wikiHref}">
                                                        <a href="${skuVO.wikiHref}" target="_blank">SKU Wiki</a>
                                                </c:if>
                                            </div>
                                            <div class="tr-item">${skuVO.operateInfoIdShow}</div>
                                            <div class="tr-item"><!--没有权限就不展示按钮-->
                                                <c:if test="${spuVO.spuLevel!=2 && command.hasEditAuth }" >
                                                    <div class="option-select-wrap J-option-select-wrap">
                                                        <div class="option-select-btn" tabtitle={"num":"vgoodseditsku${skuVO.skuId}","link":"/goods/vgoods/addSku.do?skuId=${skuVO.skuId}&spuId=${spuVO.spuId}","title":"编辑SKU"}>编辑 </div>
                                                            <div class="option-select-icon J-option-select-icon">
                                                                <i class="vd-icon icon-down"></i>
                                                            </div>
                                                        <div class="option-select-list">
                                                    <div class="option-select-item" tabtitle={"num":"changeTempToCoreSku${skuVO.skuId}","link":"/vgoods/operate/openOperate.do?skuId=${skuVO.skuId}","title":"添加运营信息"}>添加运营信息</div>
                                                            <div class="option-select-item" tabtitle={"num":"vgoodscopysku${skuVO.skuId}","link":"/goods/vgoods/copySku.do?skuId=${skuVO.skuId}&spuId=${spuVO.spuId}","title":"复制SKU"}>复制SKU </div>
                                                            <div class="option-select-item J-del-sku" data-spuid="${spuVO.spuId}" data-id="${skuVO.skuId}">删除</div>
                                                        </div>
                                                    </div>
                                                </c:if>

                                                <c:if test="${spuVO.spuLevel==2 && command.hasEditTempAuth }" >
                                                    <div class="option-select-wrap J-option-select-wrap">
                                                        <div class="option-select-btn J-sku-edit" data-spuid="${spuVO.spuId}" data-spuname="${spuVO.spuShowName}" data-skuid="${skuVO.skuId}">编辑</div>
                                                        <div class="option-select-icon J-option-select-icon">
                                                        <i class="vd-icon icon-down"></i>
                                                        </div>
                                                        <div class="option-select-list">
                                                            <div class="option-select-item J-del-sku" data-spuid="${spuVO.spuId}" data-id="${skuVO.skuId}">删除</div>
                                                        </div>
                                                    </div>
                                                </c:if>


                                            </div>
                                        </div>
                                    </c:forEach>
                                   
                                    <div class="list-pager-wrap cf J-page-wrap"  <c:if test="${spuVO.skuTotalSize < 6}">style="display: none;"</c:if> data-auth="${command.hasEditAuth}" data-tempauth="${command.hasEditTempAuth}" data-spuid="${spuVO.spuId}" data-spuname="${spuVO.spuShowName}" data-spuwiki="${spuVO.wikiHref}" data-lv="${spuVO.spuLevel}" data-total="${spuVO.skuTotalSize}">
                                        <div class="list-pager">
                                            <div class="pager-txt"><span class="J-page-txt">1</span>/<span class="J-page-txt-total"></span></div>
                                            <a class="pager-btn pager-l disabled J-page-prev">
                                                <i class="vd-icon icon-left"></i>
                                            </a>
                                            <a class="pager-btn pager-r J-page-next">
                                                <i class="vd-icon icon-right"></i>
                                            </a>
                                        </div>
                                    </div>
                                    
                                    <c:if test="${ empty spuVO.coreSkuBaseVOList}">
                                        <!--查不到sku数据-->

                                    </c:if>
                                </div>

                            </div>
                        </div>
                    </c:forEach>
                    </c:if>
                    <c:if test="${ empty list}">
                    <div class="table-tr no-data">
                        <div><i class="vd-icon icon-caution1"></i></div>
                        没有匹配的数据
                    </div> 
                    </c:if>
                </div>


                <c:if test="${page.totalPage > 1}">
                    <tags:pageNew page="${page}" />
                </c:if>
            </div>
        </form>
    </div>
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
                        {{if(isHaocai){ }}
                            规格：
                        {{ }else{ }}
                            制造商型号：     
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
    <script type="text/tmpl" class="J-sku-list-tmpl">
        {{ $.each(list, function(i, item){ }}
            <div class="tr-list J-sku-item">
                <div class="tr-item">
                    <div class="input-checkbox">
                        <label class="input-wrap">
                            <input type="checkbox" class="J-select-sku" value="{{=item.skuId}}">
                            <span class="input-ctnr"></span>
                        </label>
                    </div>
                </div>
                <div class="tr-item">
                    <div class="line-clamp2">
                        <a tabtitle={"num":"vgoodsviewsku{{=item.skuId}}","link":"/goods/vgoods/viewSku.do?skuId={{=item.skuId}}","title":"查看SKU"} href="javascript:void(0);" class="">{{=item.showName}}</a>
                    </div>
                </div>
                <div class="tr-item">
                    <span class="item-label">订货号：</span>
                    <span class="item-value">{{=item.skuNo}}</span>
                </div>
                <div class="tr-item">
                    <span class="item-label">归属：</span>
                    <span class="item-value">{{=item.assignment}}</span>
                </div>
                <div class="tr-item">
                    <span class="item-label">备货：</span>
                    <span class="item-value">{{=item.isStockupShow}}</span>
                </div>
                <div class="tr-item">
                    {{ if(item.checkStatus==2){ }}
                        <span class="status-red">审核不通过</span>
                    {{ }else if(item.checkStatus==1){ }}
                        <span class="status-yellow">审核中</span>

                    {{ }else if(item.checkStatus==3){ }}
                    <span class="status-green">审核通过</span>
                    {{ } }}
                </div>
                <div class="tr-item">{{=item.modTimeShow}}</div>
                <div class="tr-item">
                    {{ if(spuWiki){ }}
                    <a href="{{=spuWiki}}" target="_blank">SPU Wiki</a><br>
                    {{ } }}

                    {{ if(item.wikiHref){ }}
                    <a href="{{=item.wikiHref}}" target="_blank">SKU Wiki</a>
                    {{ } }}
                </div>
                <div class="tr-item">{{=item.operateInfoIdShow}}</div>
                <div class="tr-item">
                    <div class="option-select-wrap J-option-select-wrap">
                        {{ if(spulv != 2 && auth){ }}
                            <div class="option-select-btn" tabtitle={"num":"vgoodseditsku{{=item.skuId}}","link":"/goods/vgoods/addSku.do?skuId={{=item.skuId}}&spuId={{=item.spuId}}","title":"编辑SKU"}>编辑</div>
                            <div class="option-select-icon J-option-select-icon">
                                <i class="vd-icon icon-down"></i>
                            </div>
                            <div class="option-select-list">
                                <div class="option-select-item" tabtitle={"num":"changeTempToCoreSku{{=item.skuId}}","link":"/vgoods/operate/openOperate.do?skuId={{=item.skuId}}&spuId={{=item.spuId}}","title":"添加运营信息"}>添加运营信息</div>
                                <div class="option-select-item" tabtitle={"num":"vgoodscopysku{{=item.skuId}}","link":"/goods/vgoods/copySku.do?skuId={{=item.skuId}}&spuId={{=item.spuId}}","title":"复制SKU"}>复制SKU</div>
                                <div class="option-select-item J-del-sku" data-spuid="{{=item.spuId}}" data-id="{{=item.skuId}}">删除</div>
                            </div>
                        {{ } }}

                        {{ if(spulv == 2 && tempauth){ }}
                            <div class="option-select-btn J-sku-edit" data-spuid="{{=item.spuId}}" data-spuname="{{=spuName}}" data-skuid="{{=item.skuId}}">编辑</div>
                            <div class="option-select-icon J-option-select-icon">
                                <i class="vd-icon icon-down"></i>
                            </div>
                            <div class="option-select-list">
                                <div class="option-select-item J-del-sku" data-spuid="{{=item.spuId}}" data-id="{{=item.skuId}}">删除</div>
                            </div>
                        {{ } }}

                    </div>
                </div>
            </div>
        {{ }) }}
   </script>
    <script type="text/tmpl" class="J-dlg-tmpl">
        <div class="del-wrap">
            <div class="del-tip">
                <i class="vd-icon icon-caution2"></i><span class="J-dlg-tip"></span>
            </div>
            <form class="J-dlg-form">
                <div class="del-cnt base-form">
                    <textarea name="content" id="" cols="30" rows="10" class="input-textarea J-dlg-cnt" placeholder="必填：请填写删除原因，最少10个字，最多300个字"></textarea>
                </div>
            </form>
        </div>
    </script>
    <script type="text/tmpl" class="J-stock-tmpl">
        <div class="del-wrap">
            <div class="del-tip">
                <i class="vd-icon icon-caution2"></i><span>批量设置会改变原有的设置项，请确认您的设置。</span>
            </div>
            <form class="J-stock-form stock-form base-form form-span-5">
                <div class="form-item">
                    <div class="form-label">设置备货：</div>
                    <div class="form-fields">
                         <div class="input-radio">
                            <label class="input-wrap">
                                <input type="radio" name="setGoods" value="1">
                                <span class="input-ctnr"></span>是
                            </label>
                            <label class="input-wrap">
                                <input type="radio" name="setGoods" value="0">
                                <span class="input-ctnr"></span>否
                            </label>
                        </div>
                        <div class="feedback-block" wrapfor="setGoods"></div>
                    </div>
                </div>
            </form>
        </div>
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/util.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/select.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/pikaday.2.1.0.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/lv-select.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/inputSuggest.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/modules/list.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/goods/vgoods/list.js?rnd=<%=Math.random()%>"></script>
</body>
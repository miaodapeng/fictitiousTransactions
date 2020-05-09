<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>查看SPU</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/vgoods/spu/spu_view.css?rnd=<%=Math.random()%>">
</head>

<body>
    <div class="detail-wrap">
        <div class="detail-title">查看SPU：${command.showName}
        </div>
<c:if test="${command.categoryType==1  and  firstEngage.status==2 }">
    <div class="vd-tip tip-red">
    <i class="vd-tip-icon vd-icon icon-info2"></i>
    <div class="vd-tip-cnt">当前首营信息审核不通过，请暂停对该SPU进行审核。<br>请通知提交人对首营信息进行编辑后，重新提交审核。</div>
    </div>
</c:if>
    <div class="tab-nav">
        <a class="tab-item current" href="#">基本信息</a>
        <c:if test="${command.spuLevel!=2 && showType ne 'op'}"><%--showType ne 'op'运营后台连接跳转无需这些操作--%>
            <c:if test="${empty command.operateInfoId or command.operateInfoId==0 and command.checkStatus==3}">
                <a class="tab-item"   href="/vgoods/operate/openOperate.do?spuId=${command.spuId}">运营信息</a>
            </c:if>
            <c:if test="${not empty command.operateInfoId and command.operateInfoId>0}">
                <a class="tab-item"   href="/vgoods/operate/viewOperate.do?spuId=${command.spuId}">运营信息</a>
            </c:if>
        </c:if>
        </div>
            <div class="detail-option-wrap">
                <c:if test="${showType ne 'op'}"><%--showType ne 'op'运营后台连接跳转无需这些操作--%>
                    <div class="option-btns">
                        <c:if test="${command.spuLevel==2 && command.hasEditTempAuth}">
                            <a class="btn btn-small btn-blue" href="/goods/vgoods/addTempSpu.do?spuId=${command.spuId}"> 编辑</a>
                        </c:if>
                        <c:if test="${command.spuLevel!=2 && command.hasEditAuth }">
                            <a class="btn btn-small btn-blue" href="/goods/vgoods/addSpu.do?spuId=${command.spuId}"> 编辑</a>
                        </c:if>
                        <c:if test="${command.checkStatus!=3 && command.hasCheckAuth}">
                            <a class="btn btn-small btn-blue J-spu-verify" href="javascript:void(0);" data-id="${command.spuId}">
                                <c:if test="${logCheckGenerateList.size()==0}"> 审核通过 </c:if>
                                <c:if test="${logCheckGenerateList.size()>0}"> 重审通过 </c:if>
                            </a>
                        </c:if>
                        <c:if test="${command.checkStatus!=2 && command.hasCheckAuth}">
                            <a class="btn btn-small btn-blue J-spu-noverify" data-id="${command.spuId}" href="javascript:void(0);">
                                <c:if test="${logCheckGenerateList.size()==0}"> 审核不通过 </c:if>
                                <c:if test="${logCheckGenerateList.size()>0}"> 重审不通过 </c:if>
                            </a>
                        </c:if>
                        <c:if test="${command.spuLevel==2 && command.hasEditAuth }">
                            <a class="btn btn-small btn-blue J-change-spu" data-href="./changeTempToCoreSpu.do?spuId=${command.spuId}"> 转为普通商品</a>
                        </c:if>
                        <a class="btn btn-small J-del-spu" data-id="${command.spuId}">删除</a>
                    </div>
                </c:if>
            </div>
        <div class="detail-block">
            <div class="block-title">审核记录
                <c:if test="${command.checkStatus==0}">
                    <span class="title-status status-yellow">待完善</span>
                </c:if>
                <c:if test="${command.checkStatus==2}">
                    <span class="title-status status-red">审核不通过</span>
                </c:if>
                <c:if test="${command.checkStatus==1}">
                    <span class="title-status status-yellow">审核中</span>
                </c:if>
                <c:if test="${command.checkStatus==3}">
                    <span class="title-status status-green">审核通过</span>
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
        <div class="block-title">SKU信息</div>
        <div class="detail-block-tip vd-tip tip-blue">
            <i class="vd-tip-icon vd-icon icon-info2"></i>
            <div class="vd-tip-cnt">已添加<span class="J-sku-num">0</span>条，最多添加200条。</div>
        </div>
        <div class="detail-block-option">
            <c:if test="${command.spuLevel!=2  && command.hasEditAuth && showType ne 'op'}">
                <a href="javascript:void(0);" tabtitle={"num":"vgoodsaddsku${command.spuId}","link":"/goods/vgoods/addSku.do?spuId=${command.spuId}","title":"新增SKU"} class="btn btn-small">新增SKU</a>
            </c:if>
            <c:if test="${command.spuLevel==2 && command.hasEditTempAuth && showType ne 'op'}">
                <a class="btn btn-small J-sku-edit" data-spuid="${command.spuId}" data-spuname="${command.showName}" data-type="${command.skuType}">新增SKU</a>
            </c:if>
        </div>
        <div class="detail-table J-sku-list" data-spuid="${command.spuId}" data-spulv="${command.spuLevel}" data-spuname="${command.showName}">
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
                    <tr class="J-th-item">
                        <th>订货号</th>
                        <th>商品名称</th>
                        <th>规格</th>
                        <th>制造商型号</th>
                        <th>审核状态</th>
                        <th>操作</th>
                    </tr>
                    <tr class="J-tr-nodata" style="display: none;">
                        <td class="list-no-data" colspan="6">
                            <i class="vd-icon icon-info1"></i>没有匹配数据
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="J-pager pager-list-wrap"></div>
        </div>
    </div>
    <div class="detail-block">
        <div class="block-title">分类信息</div>
        <div class="detail-table">
            <div class="table-item item-col">
                <div class="table-th">分类：</div>
                <div class="table-td">
                    ${cateName}
                </div>
            </div>
        </div>
    </div>
    <div class="detail-block">
        <div class="block-title">基本信息</div>
        <div class="detail-table">
            <div class="table-item">
                <div class="table-th">商品等级：</div>
                <div class="table-td">
                    <c:if test="${command.spuLevel==2}"> 临时商品 </c:if>
                    <c:if test="${command.spuLevel==1}"> 核心商品 </c:if>
                    <c:if test="${command.spuLevel==0}"> 其他商品 </c:if>
                </div>
            </div>
            <div class="table-item">
                <div class="table-th">品牌：</div>
                <div class="table-td">
                    ${brand.brandName}
                </div>
            </div>
            <div class="table-item">
                <div class="table-th">商品类型：</div>
                <div class="table-td">
                    <c:forEach var="spuType" items="${command.spuTypeList}" varStatus="status">
                        <c:if test="${command.spuType == spuType.sysOptionDefinitionId }"> ${spuType.title } </c:if>
                    </c:forEach>
                </div>
            </div>
<c:if test="${command.spuLevel!=2}">
            <div class="table-item">
                <div class="table-th">通用名：</div>
                <div class="table-td">
                    ${command.spuName}
                </div>
            </div>
    </c:if>
            <div class="table-item">
                <div class="table-th">商品名称：</div>
                <div class="table-td">
                ${command.showName}
                </div>
            </div>
<c:if test="${command.spuLevel!=2}">
            <div class="table-item">
                <div class="table-th">注册商标：</div>
                <div class="table-td">
                ${command.registrationIcon}
                </div>
            </div>
            <div class="table-item">
                <div class="table-th">wiki链接：</div>
                <div class="table-td">
                   <a href="${command.wikiHref}" target="_blank">${command.wikiHref}</a> 
                </div>
            </div>
            <div class="table-item">
                <div class="table-th">检测报告：</div>
                <div class="table-td">
                    <div class="info-pic">
                        <c:forEach items="${command.skuCheck}" var="item">
                            <div class="info-pic-item J-show-big" data-src="${item.fullPath}">
                            <img src="${item.fullPath}">
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="table-item">
                <div class="table-th">专利文件：</div>
                <div class="table-td">
                    <div class="info-pic">
                        <c:forEach items="${command.skuPatent}" var="item">
                            <div class="info-pic-item J-show-big" data-src="${item.fullPath}">
                            <img src="${item.fullPath}">
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
    </c:if>
        </div>
    </div>
    <div class="detail-block" <c:if test="${command.categoryType!=1}"> style="display:none" </c:if>  >
        <div class="block-title">
            首营信息
            <c:if test="${command.hasCheckAuth && showType ne 'op'}" >
                  <a href="javascript:void(0);" class="btn btn-blue btn-small btn-title J-first-pass" data-id="${firstEngage.firstEngageId}">审核通过</a>
                  <a href="javascript:void(0);" class="btn btn-blue btn-small btn-title J-first-refuse" data-id="${firstEngage.firstEngageId}">审核不通过</a>
            </c:if>
        </div>
        <div class="detail-table">
            <div class="table-item">
                <div class="table-th">注册证号/备案凭证号：</div>
                <div class="table-td">

<a href="javascript:void(0);" tabTitle='{"num":"firstengage${firstEngage.firstEngageId}","link":"/firstengage/baseinfo/getFirstSearchDetail.do?firstEngageId=${firstEngage.firstEngageId}","title":"查看首营"}' >${firstEngage.registrationNumber}</a>


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
            <div class="table-item item-col">
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

<c:if test="${command.spuLevel!=2}">
<div class="detail-block">
    <div class="block-title">应用信息</div>
    <div class="detail-table">
        <div class="table-item  ">
            <div class="table-th">科室：</div>
            <div class="table-td J-spring-filter">
            <c:forEach var="departmentsHospital" items="${command.departmentsHospitalList}" varStatus="status">
                 <c:if test="${departmentsHospital.selected}"> ${departmentsHospital.departmentName} 、 </c:if>
            </c:forEach>
            </div>
        </div>
        <div class="table-item  ">
            <div class="table-th">收费项目：</div>
            <div class="table-td">
${command.hospitalTagsShow}
            </div>
        </div>
    </div>
</div>
<div class="detail-block">
    <div class="block-title">属性信息</div>
    <div class="detail-table">
        <div class="table-item item-col">
            <div class="table-th">选择带入分类属性：</div>
            <div class="table-td J-spring-filter">
<c:forEach var="baseAttribute" items="${command.baseAttributes}" varStatus="status">
     <c:if test="${baseAttribute.selected}"> ${baseAttribute.baseAttributeName} 、 </c:if>
</c:forEach>
            </div>
        </div>
    </div>
</div>
</c:if>
    </div>
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
                        {{if(type == 1){ }}
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
    <script type="text/tmpl" class="J-sku-list-tmpl">
        {{ $.each(list, function(i, item){ }}
            <tr class="J-tr-item">
                <td>{{=item.skuNo}}</td>
                <td>
                    <div class="line-clamp2">
                        <a tabtitle={"num":"vgoodsviewsku{{=item.skuId}}","link":"/goods/vgoods/viewSku.do?skuId={{=item.skuId}}&spuId={{=item.spuId}}","title":"查看SKU"} href="javascript:void(0);" class="">{{=item.showName}}</a>
                    </div>
                </td>
                <td>{{=item.spec || '--'}}</td>
                <td>{{=item.model || '--'}}</td>
                <td>
                     {{ if(item.checkStatus==2){ }}
                        <span class="status-red">审核不通过</span>
                    {{ }else if(item.checkStatus==1){ }}
                        <span class="status-yellow">审核中</span>
                    {{ }else if(item.checkStatus==3){ }}
                        <span class="status-green">审核通过</span>
                    {{ }else if(item.checkStatus==0){ }}
                    <span class="status-yellow">待完善</span>
                    {{ } }}
                </td>
                <td>

                        <div class="option-select-wrap J-option-select-wrap">
                            <c:if test="${showType ne 'op'}" >
                                {{ if(spulv != 2){ }}
                                <div class="option-select-btn" tabtitle={"num":"vgoodseditsku{{=item.skuId}}","link":"/goods/vgoods/addSku.do?skuId={{=item.skuId}}&spuId={{=item.spuId}}","title":"编辑SKU"}>编辑</div>
                                {{ } }}
                                {{ if(spulv == 2){ }}
                                <div class="option-select-btn J-sku-edit" data-type="${command.skuType}" data-spuid="{{=item.spuId}}" data-spuname="{{=spuName}}" data-skuid="{{=item.skuId}}">编辑</div>
                                {{ } }}
                                <div class="option-select-icon J-option-select-icon">
                                    <i class="vd-icon icon-down"></i>
                                </div>
                                <div class="option-select-list">
                                    <c:if test="${command.spuLevel!=2 && command.hasEditAuth }" >
                                    <div class="option-select-item" tabtitle={"num":"vgoodscopysku{{=item.skuId}}","link":"/goods/vgoods/copySku.do?skuId={{=item.skuId}}&spuId={{=item.spuId}}","title":"复制SKU"}>复制SKU </div>
                                    </c:if>
                                    <div class="option-select-item J-del-sku" data-spuid="{{=item.spuId}}" data-id="{{=item.skuId}}">删除</div>
                                </div>
                            </c:if>
                        </div>

                </td>
            </tr>
        {{ }) }}
    </script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/pager.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/goods/vgoods/spu_view.js?rnd=<%=Math.random()%>"></script>
</body>
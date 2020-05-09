<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>运营信息维护</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/vgoods/viewOperate.css?rnd=<%=Math.random()%>">
</head>

<body>
  <div class="detail-wrap">
    <div class="detail-title">
      <c:if test="${coreOperateInfoGenerateVo.operateInfoType == 1}">
        查看SPU：${coreOperateInfoGenerateVo.productName}
      </c:if>
      <c:if test="${coreOperateInfoGenerateVo.operateInfoType == 2}">
        查看SKU：${coreOperateInfoGenerateVo.productName}
      </c:if>

    </div>
    <div class="tab-nav">
      <c:if test="${coreOperateInfoGenerateVo.operateInfoType == 1}">
        <a class="tab-item" href="${pageContext.request.contextPath}/goods/vgoods/viewSpu.do?spuId=${coreOperateInfoGenerateVo.spuId}">基本信息</a>
        <a class="tab-item current" href="./viewOperate.do?spuId=${coreOperateInfoGenerateVo.spuId}">运营信息</a>
      </c:if>
      <c:if test="${coreOperateInfoGenerateVo.operateInfoType == 2}">
        <a class="tab-item" href="${pageContext.request.contextPath}/goods/vgoods/viewSku.do?skuId=${coreOperateInfoGenerateVo.skuId}&spuId=${coreOperateInfoGenerateVo.upSpuId}">基本信息</a>
        <a class="tab-item current" href="./viewOperate.do?skuId=${coreOperateInfoGenerateVo.skuId}">运营信息</a>
      </c:if>

    </div>
    <div class="detail-option-wrap">
      <div class="option-btns">
        <c:if test="${not empty coreOperateInfoGenerateVo.skuId}">
          <a class="btn btn-small J-set">推送各平台</a>
        </c:if>
        <c:choose>
          <c:when test="${not empty coreOperateInfoGenerateVo.operateInfoId}">
            <a href="./openOperate.do?operateInfoId=${coreOperateInfoGenerateVo.operateInfoId}" class="btn btn-blue btn-small">编辑</a>
          </c:when>
          <c:otherwise>
            <c:if test="${coreOperateInfoGenerateVo.operateInfoType == 1}">
              <a href="./openOperate.do?spuId=${coreOperateInfoGenerateVo.spuId}" class="btn btn-blue btn-small">编辑</a>
            </c:if>
            <c:if test="${coreOperateInfoGenerateVo.operateInfoType == 2}">
              <a href="./openOperate.do?skuId=${coreOperateInfoGenerateVo.skuId}" class="btn btn-blue btn-small">编辑</a>
            </c:if>
          </c:otherwise>
        </c:choose>
        <%-- <a class="btn btn-small J-del" data-id="">删除</a>--%>
      </div>
    </div>
    <div class="detail-block">
      <c:if test="${coreOperateInfoGenerateVo.skuId !=null}">
      <div class="detail-table">
        <div class="table-item item-col">
          <div class="table-th block-title" style="color: #000000">已推送平台</div>
          <div class="table-td" style="padding-top: 20px">
            <!-- add by Tomcat.Hui 2020/2/21 4:26 下午 .Desc: VDERP-2045 erp商品推送指南针增加科研购平台. start -->
            <c:if test="${pushStatus == 4}">科研购；</c:if>
            <c:if test="${pushStatus == 7}">贝登；医械购；科研购；</c:if>
            <!-- add by Tomcat.Hui 2020/2/21 4:26 下午 .Desc: VDERP-2045 erp商品推送指南针增加科研购平台. end -->
            <c:if test="${pushStatus ==3}">贝登；医械购；</c:if>
            <c:if test="${pushStatus ==2}">医械购；</c:if>
            <c:if test="${pushStatus ==1}">贝登；</c:if>
          </div>

        </div>
      </div>
      </c:if>
      <div class="block-title">运营图文信息</div>
      <div class="detail-table">
        <div class="table-item">
          <div class="table-th">商品标题：</div>
          <div class="table-td">${coreOperateInfoGenerateVo.goodsName}</div>
        </div>
        <div class="table-item">
          <div class="table-th">商品图片：</div>
          <div class="table-td">
            <div class="info-pic">
              <c:forEach items="${coreOperateInfoGenerateVo.goodsAttachmentList}" var="goodsImage">
                <div class="info-pic-item J-show-big" data-src="http://${goodsImage.domain}${goodsImage.uri}">
                  <img src="http://${goodsImage.domain}${goodsImage.uri}" alt="">
                </div>
              </c:forEach>
            </div>
          </div>
        </div>
        <div class="table-item item-col">
          <div class="table-th">商品详情：</div>
          <div class="table-td">
            ${coreOperateInfoGenerateVo.oprateInfoHtml}
          </div>
        </div>
      </div>
    </div>
    <%--<div class="detail-block">
      <div class="block-title">SEO信息</div>
      <div class="detail-table">
        <div class="table-item">
          <div class="table-th">SEO关键词</div>
          <div class="table-td">
            <c:forEach items="${coreOperateInfoGenerateVo.seoKeyWordsArray}" var="seoKeyWords">
              ${seoKeyWords} <br>
            </c:forEach>
          </div>
        </div>
        <div class="table-item">
          <div class="table-th">SEO标题</div>
          <div class="table-td">
            ${coreOperateInfoGenerateVo.seoTitle}
          </div>
        </div>
        <div class="table-item item-col">
          <div class="table-th">SEO描述</div>
          <div class="table-td">
            ${coreOperateInfoGenerateVo.seoDescript}
          </div>
        </div>
      </div>
    </div>--%>
    <%--<div class="detail-block">
      <div class="block-title">平台推送</div>
      <div class="detail-table">
        <div class="table-item item-col">
          <div class="table-th">平台推送</div>
          <div class="table-td">
            平台1 <br>
            平台2
          </div>
        </div>
      </div>
    </div>--%>
  </div>
  <script type="text/tmpl" class="J-dlg-tmpl">
    <div class="dlg-form-wrap">
      <form class="base-form form-span-5 J-dlg-form">
        <input type="hidden" name="spuId" value="${coreOperateInfoGenerateVo.spuId}"/>
        <input type="hidden" name="skuId" value="${coreOperateInfoGenerateVo.skuId}"/>
        <div class="form-item">
          <div class="form-label">选择推送的平台：</div>
          <div class="form-fields">
            <div class="input-checkbox">
              <c:forEach items="${platfromList}" var="platfrom" >
                <label class="input-wrap">
                <input type="checkbox" checked name="platfromIds" value="${platfrom.platfromId}" >
                <span class="input-ctnr"></span>${platfrom.platfromName}
                </label>
              </c:forEach>
              <div class="feedback-block" wrapfor="platfromIds"></div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/pages/goods/vgoods/viewOperate.js?rnd=<%=Math.random()%>"></script>
</body>

</html>
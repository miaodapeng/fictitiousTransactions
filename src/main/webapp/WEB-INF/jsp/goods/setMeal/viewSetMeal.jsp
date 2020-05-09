<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>套餐详情</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/setMeal/viewSetMeal.css?rnd=<%=Math.random()%>">
</head>

<body>
  <div class="detail-wrap">
    <div class="detail-title">查看套餐：${setMeal.setMealName}</div>
    <div class="detail-option-wrap">
      <div class="option-btns">
        <a href="./openSetMeal.do?setMealId=${setMeal.setMealId}" class="btn btn-blue btn-small">编辑</a>
        <a class="btn btn-small J-one-del" data-id="${setMeal.setMealId}">删除</a>
      </div>
    </div>
    <div class="detail-block">
      <div class="block-title">基本信息</div>
      <div class="detail-table">
        <div class="table-item">
          <div class="table-th">套餐名称：</div>
          <div class="table-td">${setMeal.setMealName}</div>
        </div>
        <div class="table-item">
          <div class="table-th">套餐类型：</div>
          <div class="table-td">
            <c:if test="${setMeal.setMealType == 1}">商品配餐信息（展示在商品详情）</c:if>
            <c:if test="${setMeal.setMealType == 2}">根据科室（显示在解决方案）</c:if>
            <c:if test="${setMeal.setMealType == 3}">根据疾病（显示在解决方案）</c:if>
            <c:if test="${setMeal.setMealType == 4}">其他</c:if>

          </div>
        </div>
        <div class="table-item item-col">
          <div class="table-th">套餐说明：</div>
          <div class="table-td">
            ${setMeal.setMealDescript}
          </div>
        </div>
      </div>
    </div>
    <div class="detail-block">
      <div class="block-title">套餐明细</div>
      <div class="combo-list">
        <div class="combo-tr th-wrap">
          <div class="th-item">商品标记</div>
          <div class="th-item">科室</div>
          <div class="th-item">商品名称</div>
          <div class="th-item">用途</div>
          <div class="th-item">状态</div>
          <div class="th-item">备注</div>
          <div class="th-item">数量</div>
        </div>
        <c:forEach items="${setMealSkuMappingVoList}" var="setMealSkuMappingVo">
          <div class="combo-tr">
            <div class="td-item">
              <c:if test="${setMealSkuMappingVo.skuSign == 1}">主推</c:if>
              <c:if test="${setMealSkuMappingVo.skuSign == 2}">备选</c:if>
              <c:if test="${setMealSkuMappingVo.skuSign == 3}">配套</c:if>
            </div>
            <div class="td-item">${setMealSkuMappingVo.departmentNames}</div>
            <div class="td-item">${setMealSkuMappingVo.skuName}</div>
            <div class="td-item">${setMealSkuMappingVo.purpose}</div>
            <div class="td-item">
              <c:if test="${setMealSkuMappingVo.status == 1}">
                <c:if test="${setMealSkuMappingVo.checkStatus == 1}">
                  <span class="status-yellow">审核中</span>
                </c:if>
                <c:if test="${setMealSkuMappingVo.checkStatus == 2}">
                  <span class="status-red">审核不通过</span>
                </c:if>
                <c:if test="${setMealSkuMappingVo.checkStatus == 3}">
                  <span class="status-green">审核通过</span>
                </c:if>
                <c:if test="${setMealSkuMappingVo.checkStatus == 4}">
                  <span class="status-red">已删除</span>
                </c:if>
              </c:if>
              <c:if test="${setMealSkuMappingVo.status == 0}">
                <span class="status-red">已删除</span>
              </c:if>
            </div>
            <div class="td-item">${setMealSkuMappingVo.remark}</div>
            <div class="td-item">${setMealSkuMappingVo.quantity}  ${setMealSkuMappingVo.skuUnitName}</div>
          </div>
        </c:forEach>
      </div>
    </div>
  </div>
  <script type="text/tmpl" class="J-dlg-tmpl">
      <div class="del-wrap">
          <div class="del-tip">
              <i class="vd-icon icon-caution2"></i>确认删除该套餐么？
          </div>
          <form class="J-dlg-form">
              <div class="del-cnt base-form">
                  <textarea name="content" id="" cols="30" rows="10" class="input-textarea" placeholder="必填：请填写删除原因，最少10个字，最多300个字"></textarea>
              </div>
          </form>
      </div>
  </script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/pages/goods/setMeal/viewSetMeal.js?rnd=<%=Math.random()%>"></script>
</body>

</html>
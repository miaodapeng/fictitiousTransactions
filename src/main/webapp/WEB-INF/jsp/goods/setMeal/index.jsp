<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://com.vedeng.common.util/tags" prefix="date"%>

<!DOCTYPE html>
<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>套餐管理</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/setMeal/index.css?rnd=<%=Math.random()%>">
</head>

<body>
  <div class="erp-wrap">
    <div class="erp-title">
      <div class="erp-title-txt">套餐管理</div>
    </div>
    <div class="erp-top-option">
      <div class="option-btn-wrap">
        <a class="btn btn-blue btn-small" tabTitle='{"num":"addSetMeal","link":"./vgoods/setMeal/openSetMeal.do","title":"新增套餐", "random": "1"}'>新增套餐</a>
      </div>
    </div>
    <div class="erp-block base-form search-wrap J-search-wrap">
      <div class="search-list">
        <div class="search-item">
          <div class="item-label">
            套餐名称：
          </div>
          <div class="item-fields">
            <input type="text" name="setMealName" autocomplete="off" maxlength="64" class="input-text" value="${setMealVo.setMealName}" placeholder="请输入套餐名称">
          </div>
        </div>
        <div class="search-item">
          <div class="item-label">套餐类型：</div>
          <div class="item-fields">
            <select class="J-select" name="setMealType" id="setMealType">
              <option value="" <c:if test="${setMealVo.setMealType == null || setMealVo.setMealType == 0}">selected</c:if>>全部</option>
              <option value="1" <c:if test="${setMealVo.setMealType == 1}">selected</c:if>>商品配套信息（展示在商品详情）</option>
              <option value="2" <c:if test="${setMealVo.setMealType == 2}">selected</c:if>>根据科室（显示在解决方案）</option>
              <option value="3" <c:if test="${setMealVo.setMealType == 3}">selected</c:if>>根据疾病（显示在解决方案）</option>
              <option value="4" <c:if test="${setMealVo.setMealType == 4}">selected</c:if>>其他</option>
            </select>
          </div>
        </div>
        <div class="search-item">
          <div class="item-label">创建时间：</div>
          <div class="item-fields J-date-range">
            <div class="input-date item-input">
              <input type="text" name="addBeginTime" class="input-text" placeholder="请选择日期" readonly value="${setMealVo.addBeginTime}">
            </div>
            <div class="search-item-gap">-</div>
            <div class="input-date item-input">
              <input type="text" name="addEndTime" class="input-text" placeholder="请选择日期" readonly value="${setMealVo.addEndTime}">
            </div>
          </div>
        </div>
        <div class="search-item">
          <div class="item-label">更新时间：</div>
          <div class="item-fields J-date-range">
            <div class="input-date item-input">
              <input type="text" name="modBeginTime" class="input-text" placeholder="请选择日期" readonly value="${setMealVo.modBeginTime}">
            </div>
            <div class="search-item-gap">-</div>
            <div class="input-date item-input">
              <input type="text" name="modEndTime" class="input-text" placeholder="请选择日期" readonly value="${setMealVo.modEndTime}">
            </div>
          </div>
        </div>
      </div>
      <div class="search-btns">
        <div class="btn btn-small btn-blue-bd J-search">搜索</div>
        <div class="btn btn-small J-reset">重置</div>
      </div>
    </div>
    <div class="erp-block erp-block-list">
      <div class="option-wrap J-fix-wrap">
        <div class="option-fix-wrap cf J-fix-cnt">
          <button class="btn btn-small J-multi-del">批量删除</button>
          <div class="option-r">
            <div class="sort-wrap J-sort-wrap">
              <div class="sort-item">
                <span class="item-label">排序：</span>
                <select name="orderType" class="J-select">
                  <optgroup>
                    <option value="1" <c:if test="${setMealVo.orderType == null || setMealVo.orderType == 1}">selected</c:if>>更新时间：由近到远</option>
                    <option value="2" <c:if test="${setMealVo.orderType == 2}">selected</c:if>>更新时间：由远到近</option>
                  </optgroup>
                  <optgroup>
                    <option value="3" <c:if test="${setMealVo.orderType == 3}">selected</c:if>>创建时间：由近到远</option>
                    <option value="4" <c:if test="${setMealVo.orderType == 4}">selected</c:if>>创建时间：由远到近</option>
                  </optgroup>
                </select>
              </div>
            </div>
            <div class="option-gap"></div>
            <c:if test="${page.totalPage > 1}">
              <div class="option-pager">
                <div class="option-pager-wrap"></div>
                <tags:pageNewSimple page="${page}" />
              </div>
            </c:if>
          </div>
        </div>
      </div>
      <table class="table table-base table-hover base-form J-table-wrap">
        <colgroup>
          <col width="7%">
          <col width="">
          <col width="25%">
          <col width="15%">
          <col width="15%">
          <col width="12%">
        </colgroup>
        <tbody>
          <tr>
            <th>
              <div class="input-checkbox">
                <label class="input-wrap">
                  <input type="checkbox" class="J-select-all" value="">
                  <span class="input-ctnr"></span>
                </label>
              </div>
            </th>
            <th>套餐名称</th>
            <th>套餐类型</th>
            <th>创建时间</th>
            <th>更新时间</th>
            <th>操作</th>
          </tr>
          <c:if test="${empty setMealList}">
            <tr>
              <td class="no-data" colspan="6">
                <div><i class="vd-icon icon-caution1"></i></div>
                没有匹配的数据
              </td>
            </tr>
          </c:if>
          <c:if test="${not empty setMealList}">
            <c:forEach items="${setMealList}" var="setMeal">
              <tr>
                <td>
                  <div class="input-checkbox">
                    <label class="input-wrap">
                      <input type="checkbox" class="J-select-item" value="${setMeal.setMealId}">
                      <span class="input-ctnr"></span>
                    </label>
                  </div>
                </td>
                <td>
                  <a href="javascript:void(0);" tabTitle='{"num":"viewSetMeal${setMeal.setMealId}","link":"./vgoods/setMeal/viewSetMeal.do?setMealId=${setMeal.setMealId}","title":"编辑套餐"}'>${setMeal.setMealName}</a>
                </td>
                <td>
                    <c:if test="${setMeal.setMealType == 1}">商品配套信息（展示在商品详情）</c:if>
                    <c:if test="${setMeal.setMealType == 2}">根据科室（显示在解决方案）</c:if>
                    <c:if test="${setMeal.setMealType == 3}">根据疾病（显示在解决方案）</c:if>
                    <c:if test="${setMeal.setMealType == 4}">其他</c:if>
                </td>
                <td>
                    <fmt:formatDate value="${setMeal.addTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                    <fmt:formatDate value="${setMeal.modTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
                <td>
                  <div class="option-select-wrap J-option-select-wrap">
                    <div class="option-select-btn" tabTitle='{"num":"editSetMeal${setMeal.setMealId}","link":"./vgoods/setMeal/openSetMeal.do?setMealId=${setMeal.setMealId}","title":"编辑套餐"}'>编辑</div>
                    <div class="option-select-icon J-option-select-icon">
                      <i class="vd-icon icon-down"></i>
                    </div>
                    <div class="option-select-list">
                      <div class="option-select-item J-one-del" data-id="${setMeal.setMealId}">删除</div>
                    </div>
                  </div>
                </td>
              </tr>
            </c:forEach>
          </c:if>
        </tbody>
      </table>
      <c:if test="${page.totalPage > 1}">
        <tags:pageNew page="${page}" />
      </c:if>
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
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/util.js?rnd=<%=Math.random()%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/select.js?rnd=<%=Math.random()%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/pikaday.2.1.0.js?rnd=<%=Math.random()%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/modules/list.js?rnd=<%=Math.random()%>"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/pages/goods/setMeal/index.js?rnd=<%=Math.random()%>"></script>
</body>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pos_abs customernameshow">
    物料编码：<%--${list.goods.materialCode}--%> ${newSkuInfosMap[skuNo].MATERIAL_CODE} <br>
    注册证号： <%--${list.goods.registrationNumber}--%> ${newSkuInfosMap[skuNo].REGISTRATION_NUMBER}<br>
    管理类别：<%--${list.manageCategoryName}--%> ${newSkuInfosMap[skuNo].MANAGE_CATEGORY_LEVEL}<br>
    产品负责人：
    <%--<c:if test="${not empty list.goods.userList }">
        <c:forEach items="${list.goods.userList }" var="user" varStatus="st">
            ${user.username }
            <c:if test="${st.count != list.goods.userList.size() }">、</c:if>
        </c:forEach>
    </c:if>--%>
    ${newSkuInfosMap[skuNo].PRODUCTMANAGER}<br>

    <%--采购提醒：${list.goods.purchaseRemind} <br>--%>
    包装清单：<%--${list.goods.packingList}--%> ${newSkuInfosMap[skuNo].PACKING_LIST}<br>
    <%--服务条款：${list.goods.tos} <br>--%>
    质保年限：${newSkuInfosMap[skuNo].QA_YEARS} <br>
    <%--库存：<span id="kc_${list.saleorderGoodsId}">${list.goods.stockNum}</span> <br>
    可用库存：<span id="kykc_${list.saleorderGoodsId}">${(list.goods.stockNum-list.goods.orderOccupy) < 0 ? 0 : (list.goods.stockNum-list.goods.orderOccupy)}</span><br>
    订单占用：<span id="dzzy_${list.saleorderGoodsId}">${list.goods.orderOccupy}</span><br>--%>
    库存：<span>${newSkuInfosMap[skuNo].STOCKNUM}</span> <br>
    可用库存：<span>${newSkuInfosMap[skuNo].AVAILABLESTOCKNUM}</span><br>
    订单占用：<span>${newSkuInfosMap[skuNo].OCCUPYNUM}</span><br>
    <%--可调剂：<span>${list.goods.adjustableNum}</span> <br>--%>
    审核状态： ${newSkuInfosMap[skuNo].CHECK_STATUS}
</div>
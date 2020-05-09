<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="打印订货号条码" scope="application" />
<%@ include file="../../common/common.jsp"%>
<link rel="stylesheet" href="<%=basePath%>static/css/qrcode.css?rnd=<%=Math.random()%>" />
<div class="main-container logistics-warehousein-addWarehouseIn">

         <div class="parts bar-code-infor">
            <table class="table">
                <thead>
                    <tr>
                        <th class="wid5">选择</th>
                        <th class="wid5">序号</th>
                        <th>条形码内容</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="goodsId" items="${goodsIdList}" varStatus="num">
                 	<tr>
                        <td>
                             <input type="checkbox" class="${goodsId}" name="b_checknox" autocomplete="off"  value="${goodsId}">
                        </td>
                        <td>${num.count}</td>
                        <td class="text-left overflow-hidden">
                            <c:if test="${empty map[goodsId]}">
                                <span class="bt-bg-style bt-smaller bg-light-blue" onclick="addSkuBarcode(${goodsId})">生成</span>
                            </c:if>
                            <c:if test="${!empty map[goodsId]}">
                            <div id="pr_${goodsId}" class="pr_${goodsId}">
                            	<div class="qr-code-box">
                                        <div id="pr_${goodsId}" class="pr_${goodsId}">
                                            <img height="90px" width="90px" src="http://${map[goodsId].domain}/${map[goodsId].uri}">
                                            <div class="img-num" style="font-family:'黑体';font-size:40px;">V${goodsId}</div>
                                        </div>
                                </div>
                           </div>
                            </c:if>
                        </td>
                    </tr>
				</c:forEach>
                </tbody>
            </table>
             <div class="table-style4">
                <div class="allchose">
                    <input type="checkbox" name="" onclick="selectall(this);" autocomplete="off">
                    <span>全选</span>
                </div>
                <div class="print-record">
                    <c:if test="${isprint}">
                   <span class="bt-bg-style bg-light-blue" onclick="printall()">批量打印</span>
                    </c:if>
                </div>
            </div>
        </div>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseIn/addBarcode.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

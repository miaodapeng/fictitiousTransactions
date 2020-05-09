<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../common/common.jsp"%>
 		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">入库记录</div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <td>序号</td>
                        <td>入库时间</td>
                        <td>产品名称</td>
                        <td>品牌</td>
                        <td>型号</td>
                        <td>数量</td>
                        <td>单位</td>
                        <td>效期截止日期</td>
                        <td>批次号</td>
                        <td>入库操作人</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${not empty warehouseGoodsOperateLogVoList}">
                		<c:forEach items="${warehouseGoodsOperateLogVoList}" var="agolv" varStatus="status">
                			<tr>
                				<td>${status.count}</td>
		                        <td><date:date value ="${agolv.addTime} "/></td>
		                        <td class="text-left">
		                        	<span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${agolv.goodsId}","title":"产品信息"}'>${agolv.goodsName}</span> 
		                    				</br>${agolv.sku}</br>${agolv.materialCode}
		                    	</td>
		                        <td>${agolv.brandName}</td>
		                        <td>${agolv.model}</td>
		                        <td>${agolv.num}</td>
		                        <td>${agolv.unitName}</td>
		                        <td><date:date value ="${agolv.expirationDate} "/></td>
		                        <td>${agolv.batchNumber}</td>
		                        <td>${agolv.operaterName}</td>
		                    </tr>
                		</c:forEach>
                	</c:if>
                	<c:if test="${empty buyorderVo.warehouseGoodsOperateLogVoList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='10'>暂无记录！</td>
					    </tr>
		        	</c:if>
                </tbody>
            </table>
        </div>
<%@ include file="../../common/footer.jsp"%>
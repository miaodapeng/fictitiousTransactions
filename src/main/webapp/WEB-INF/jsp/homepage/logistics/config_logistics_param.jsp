<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="参数设置" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/home/page/config_sale_goal.js?rnd=<%=Math.random()%>'></script>
<div class="form-list  form-tips8">
     <div class="main-container">
       	 <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	快递公司设置
                </div>
                <div class="title-click nobor  pop-new-data" layerParams='{"width":"500px","height":"200px","title":"新增快递","link":"./addLogisticsPage.do"}'>新增快递</div>
            	
            </div>
            <table class="table">
            	<thead>
                   <tr>
						<th class="wid20">快递公司</th>
						<th class="wid8">是否启用</th>
						<th class="wid8">是否默认</th>
						<th class="wid6">操作</th>
					</tr>
                </thead>
                <tbody>
                <c:if test="${not empty logisticsList}">
                	<c:forEach items="${logisticsList}" var="log">
                		<tr>
	                        <td>${log.name }</td>
	                        <td>
	                        	<c:if test="${log.isEnable eq 0 }">禁用</c:if>
	                        	<c:if test="${log.isEnable eq 1 }">启用</c:if>
	                        </td>
	                        <td>
	                        	<c:if test="${log.isDefault eq 0 }">否</c:if>
	                        	<c:if test="${log.isDefault eq 1 }">是</c:if>
	                        </td>
	                        <td>
	                        	 <span class="edit-user pop-new-data"
										layerParams='{"width":"500px","height":"250px","title":"编辑快递",
											"link":"./editLogisticsPage.do?logisticsId=${log.logisticsId}&name=${log.name}&isEnable=${log.isEnable}"}'>编辑</span>
								<c:if test="${log.isDefault eq 0 }">			
									<span class="edit-user " onclick="setDefault(${log.logisticsId});">设为默认 </span>
								</c:if>
	                        </td>
	                    </tr>
                	</c:forEach>
                </c:if>
                <c:if test="${empty logisticsList}">
                	<tr>
                		<td colspan="4">暂无数据</td>
                	</tr>
                </c:if>
                	
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
               		 发货信息设置
                </div>
                <c:if test="${empty delivery }">
                	<div class="title-click nobor  pop-new-data" layerParams='{"width":"650px","height":"350px","title":"新增发货信息",
		                  "link":"./addDeliveryAddressPage.do"}'>新增发货信息</div>
                </c:if>
            </div>
            <table class="table">
               <thead>
                   <tr>
						<th class="wid13">发货名称</th>
						<th class="wid8">发货地区</th>
						<th class="wid18">发货地址</th>
						<th class="wid8">发货联系人</th>
						<th class="wid8">联系人手机</th>
						<th class="wid10">操作</th>
					</tr>
                </thead>
                <tbody>
                	<c:if test="${not empty delivery }">
	                	<tr>
	                        <td>${delivery.companyName}</td>
	                        <td>${delivery.areas}</td>
	                        <td>${delivery.address}</td>
	                        <td>${delivery.contactName}</td>
	                        <td>${delivery.mobile}</td>
	                        <td>
	                        	<span class="edit-user pop-new-data" 
	                        		layerParams='{"width":"650px","height":"350px","title":"编辑发货信息","link":"./editDeliveryAddressPage.do?addressId=${delivery.addressId}"}'>编辑</span>
	                        </td>
	                    </tr>
                    </c:if>
                    <c:if test="${empty delivery }">
                    	<tr>
                    		<td colspan="6">暂无数据！</td>
                    	</tr>
                    </c:if>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
               		 收货信息设置
                </div>
                <div class="title-click nobor  pop-new-data" layerParams='{"width":"650px","height":"350px","title":"新增收货信息",
		                  "link":"./addReceiveAddressPage.do"}'>新增收货信息</div>
            	
            </div>
            <table class="table">
               <thead>
                   <tr>
						<th class="wid13">收货名称</th>
						<th class="wid8">收货地区</th>
						<th class="wid18">收货地址</th>
						<th class="wid8">收货联系人</th>
						<th class="wid8">联系人手机</th>
						<th class="wid10">操作</th>
					</tr>
                </thead>
                <tbody>
	                <c:if test="${not empty buyList }" >
	                	<c:forEach items="${buyList }" var="buy">
                			<tr>
		                        <td>${buy.companyName}</td>
		                        <td>${buy.areas}</td>
		                        <td>${buy.address}</td>
		                        <td>${buy.contactName}</td>
		                        <td>${buy.mobile}</td>
		                        <td>
		                        	<span class="edit-user pop-new-data" layerParams='{"width":"650px","height":"350px","title":"编辑退货信息",
													"link":"./editReceiveAddressPage.do?addressId=${buy.addressId}"}'>编辑</span>
									<c:if test="${empty buy.paramsConfigValueId}">			
										<span class="edit-user " onclick="setBuyDefault(${buy.addressId});">设为默认 </span>
									</c:if>
		                        </td>
		                    </tr>
                		</c:forEach>
	                	
                    </c:if>
                    <c:if test="${empty buyList }">
                    	<tr>
                    		<td colspan="6">暂无数据！</td>
                    	</tr>
                    </c:if>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
               		 退货信息设置
                </div>
                <c:if test="${empty ret }">
                	<div class="title-click nobor  pop-new-data" layerParams='{"width":"650px","height":"350px","title":"新增退货信息",
		                  "link":"./addAfterSalesReceiveAddressPage.do"}'>新增退货信息</div>
            	</c:if>
            </div>
            <table class="table">
               <thead>
                   <tr>
						<th class="wid13">收货名称</th>
						<th class="wid8">收货地区</th>
						<th class="wid18">收货地址</th>
						<th class="wid8">收货联系人</th>
						<th class="wid8">联系人手机</th>
						<th class="wid10">操作</th>
					</tr>
                </thead>
                <tbody>
                	<c:if test="${not empty ret }">
	                	<tr>
	                        <td>${ret.companyName}</td>
	                        <td>${ret.areas}</td>
	                        <td>${ret.address}</td>
	                        <td>${ret.contactName}</td>
	                        <td>${ret.mobile}</td>
	                        <td>
	                        	<span class="edit-user pop-new-data" layerParams='{"width":"650px","height":"350px","title":"编辑退货信息",
												"link":"./editAfterSalesReceiveAddressPage.do?addressId=${ret.addressId}"}'>编辑</span>
												
	                        </td>
	                    </tr>
                    </c:if>
                    <c:if test="${empty ret }">
                    	<tr>
                    		<td colspan="6">暂无数据！</td>
                    	</tr>
                    </c:if>
                </tbody>
            </table>
        </div>
        
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
               		 其他参数
                </div>
                <div class="title-click nobor  pop-new-data" layerParams='{"width":"600px","height":"200px","title":"编辑","link":"./editParamsConfigPage.do"}'>编辑</div>
            </div>
            <table class="table">
                <tbody>
                	<tr>
                        <td>快递超时天数</td>
                        <td>${logistics.paramsValue}天</td>
                        <td></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div>
   	</div>
</div>
<%@ include file="../../common/footer.jsp"%>
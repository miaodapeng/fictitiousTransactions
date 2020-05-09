<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="管理信息" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<%@ include file="supplier_tag.jsp"%>
<div class="content">
	<div class="parts">
		<div class="title-container">
			<div class="table-title">管理信息</div>
			<c:choose>
				<c:when test="${traderSupplier.isEnable == 1 && ((traderSupplier.verifyStatus != null && traderSupplier.verifyStatus != 0 )|| traderSupplier.verifyStatus == null)}">
					<div class="font-red f_right mr8 pop-new-data"
						layerParams='{"width":"600px","height":"200px","title":"禁用","link":"./initDisabledPage.do?id=${traderSupplier.traderSupplierId}"}'>禁用</div>
				</c:when>
				<c:when test="${traderSupplier.isEnable != 1}">
					<div class="title-click f_right mr8"
						onclick="setDisabled(${traderSupplier.traderSupplierId},1);">启用</div>
				</c:when>
			</c:choose>
			<c:if test="${traderSupplier.isEnable == 1 && ((traderSupplier.verifyStatus != null && traderSupplier.verifyStatus != 0 )|| traderSupplier.verifyStatus == null) }">
			<div class="title-click"
				onclick="goUrl('${pageContext.request.contextPath}/trader/supplier/editmanageinfo.do?traderSupplierId=${traderSupplier.traderSupplierId}');">编辑</div>
			</c:if>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
				<tr>
					<td class="table-smallest">创建时间</td>
					<td class="table-middle"><date:date
							value="${traderSupplier.addTime} " /></td>
					<td class="table-smallest">归属采购</td>
					<td class="table-middle">${traderSupplier.ownerSale }</td>
				</tr>
				<tr>
					<td>供应商等级</td>
					<td>${traderSupplier.sysOptionDefinition.title}</td>
					<td>上次沟通时间</td>
					<td><date:date value="${traderSupplier.lastCommuncateTime} " /></td>
				</tr>
				<tr>
					<td>采购次数</td>
					<td>${traderSupplier.buyCount }</td>
					<td>采购金额</td>
					<td>${traderSupplier.buyMoney }</td>
				</tr>
				<tr>
					<td>均单价</td>
					<td>${traderSupplier.averagePrice }</td>
					<td>采购频次</td>
					<td>${traderSupplier.buyFrequency }</td>
				</tr>
				<tr>
					<td>沟通次数</td>
					<td>${traderSupplier.communcateCount }</td>
					<td>禁用状态</td>
					<td><c:choose>
							<c:when test="${traderSupplier.isEnable == 1}">
	                        	未禁用
	                        	</c:when>
							<c:otherwise>
								<span class="font-red">已禁用</span>
							</c:otherwise>
						</c:choose></td>
				</tr>
				<tr>
					<td>禁用时间</td>
					<td><date:date value="${traderSupplier.disableTime} " /></td>
					<td>禁用原因</td>
					<td>${traderSupplier.disableReason}</td>
				</tr>
				<tr>
					<td>标签</td>
					<td colspan="3" class="text-left"><c:if
							test="${not empty traderSupplier.tag }">
							<ul class="communicatecontent ml0">
								<c:forEach items="${traderSupplier.tag }" var="tag">
									<li class="bluetag">${tag.tagName}</li>
								</c:forEach>
							</ul>
						</c:if></td>
				</tr>
				<tr>
					<td>备注</td>
					<td colspan="3" class="text-left">${traderSupplier.comments}</td>
				</tr>
				<tr>
					<td>订单覆盖品类</td>
					<td colspan="3" class="text-left" id="orderCoverCategory"></td>
				</tr>
				<tr>
					<td>订单覆盖品牌</td>
					<td colspan="3" class="text-left" id="orderCoverBrand"></td>
				</tr>
				<tr>
					<td>订单覆盖产品</td>
					<td colspan="3" class="text-left" id="orderCoverArea"></td>
				</tr>
				<tr>
					<td>简介</td>
					<td colspan="3" class="text-left">${traderSupplier.brief}</td>
				</tr>
			</tbody>
		</table>
	</div>
	<%-- <div class="parts">
		<div class="title-container">
			<div class="table-title nobor">审核记录</div>
			<c:if test="${traderSupplier.isEnable == 1}">
			<div class="title-click nobor  pop-new-data"
				layerParams='{"width":"35%","height":"28%","title":"提示","link":"editpages/applychecktip.html"}'>
				申请审核</div>
			</c:if>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="table-smallest">操作人</th>
					<th class="table-smaller">操作时间</th>
					<th class="table-smallest">操作事项</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty verifiesList }">
						<c:forEach items="${verifiesList }" var="verifies">
							<tr>
								<td>${verifies.username }</td>
								<td><date:date value="${verifies.addTime} " /></td>
								<td><c:choose>
										<c:when test="${verifies.status == 1 }">
			                        	审核通过
			                        	</c:when>
										<c:when test="${verifies.status == 1 }">
			                        	审核不通过
			                        	</c:when>
										<c:otherwise>
			                        	待审核
			                        	</c:otherwise>
									</c:choose></td>
								<td>${verifies.comments }</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
	            			<td colspan="4">审核记录暂无信息</td>
	            		</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div> --%>
</div>

<script type="text/javascript">

	//订单覆盖品类，订单覆盖品牌，订单覆盖区域
	$(function(){
		
		var traderId = '${traderSupplier.traderId}';
		
		$.ajax({
			async:true,
			url:page_url + "/trader/supplier/ordercoverinfo.do",
			data:{"traderId":traderId , "traderType":2},
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data){
					var orderType = "";
					var orderBrand = "";
					var orderArea = "";
					//订单覆盖区域
					if(data.goodNameList != null && data.goodNameList.length > 0){
						for (var i=0; i<data.goodNameList.length; i++) {
							if(data.goodNameList[i] != null){
								if(i == data.goodNameList.length-1){
									orderArea += data.goodNameList[i];
								}else{
									orderArea += data.goodNameList[i] + "、";
								}
							}
						}
					}
					
					//订单覆盖品牌
					if(data.brandNameList != null && data.brandNameList.length > 0){
						for (var i=0; i<data.brandNameList.length; i++) {
							if(data.brandNameList[i] != null){
								if(i == data.brandNameList.length-1){
									orderBrand += data.brandNameList[i];
								}else{
									orderBrand += data.brandNameList[i] + "、";
								}
							}
						}
					}
					
					//订单覆盖品类
					if(data.categoryNameList != null && data.categoryNameList.length > 0){
						for (var i=0; i<data.categoryNameList.length; i++) {
							if(data.categoryNameList[i] != null){
								if(i == data.categoryNameList.length-1){
									orderType += data.categoryNameList[i];
								}else{
									orderType += data.categoryNameList[i] + "、";
								}
							}
						}
					}
					$("#orderCoverCategory").html(orderType);
					$("#orderCoverBrand").html(orderBrand);
					$("#orderCoverArea").html(orderArea);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限");
				}
			}
		})
	});
	
</script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/supplier/view_manageinfo.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>
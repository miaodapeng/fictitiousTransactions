<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新品宣传列表" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript"></script>
<script type="text/javascript" >
	function grounding(adId,status,companyId){
		checkLogin();
		index = layer.confirm("是否继续当前操作", {
			  btn: ['确定','取消'] //按钮
			}, function(){
				$.ajax({
					url:page_url + "/system/ad/saveAdGrounding.do",
					data:{"adId":adId,"isShow":status,"companyId":companyId},
					type:"POST",
					dataType : "json",
					async: false,
					success:function(data)
					{
						if(data.code==0){
							window.self.location.reload();
						}else{
							layer.alert(data.message);
						}
						
					},
					error:function(data){
						if(data.status ==1001){
							layer.alert("当前操作无权限")
						}
					}
				});
				return false;
		layer.close(index);
			}, function(){
			});

		
		
	}
</script>
	<div class="searchfunc">
		<form method="post" id="search" action="<%= basePath %>/aftersales/order/getAfterSalesPage.do">
			<%-- <ul>
				<li>
					<label class="infor_name">售后单号</label>
					<input type="text" class="input-middle" name="afterSalesNo" id="afterSalesNo" value="${afterSalesVo.afterSalesNo}"/>
				</li>
				<li>
					<label class="infor_name">对应订单号</label>
					<input type="text" class="input-middle" name="orderNo" id="orderNo" value="${afterSalesVo.orderNo}"/>
				</li>
				<li>
					<label class="infor_name">客户名称</label>
					<input type="text" class="input-middle" name="traderName" id="traderName" value="${afterSalesVo.traderName}"/>
				</li>
				<li>
					<label class="infor_name">订单状态</label>
					<select class="input-middle" name="atferSalesStatus" id="atferSalesStatus">
						<option value="">全部</option>
						<option <c:if test="${afterSalesVo.atferSalesStatus eq 0}">selected</c:if> value="0">待确认</option>
						<option <c:if test="${afterSalesVo.atferSalesStatus eq 1}">selected</c:if> value="1">进行中</option>
						<option <c:if test="${afterSalesVo.atferSalesStatus eq 2}">selected</c:if> value="2">已完结</option>
						<option <c:if test="${afterSalesVo.atferSalesStatus eq 3}">selected</c:if> value="3">已关闭</option>
					</select>
				</li>
				<li>
					<label class="infor_name">审核状态</label>
					<select class="input-middle" name="status" id="status">
						<option value="">全部</option>
						<option <c:if test="${afterSalesVo.status eq 0}">selected</c:if> value="0">待审核</option>
						<option <c:if test="${afterSalesVo.status eq 1}">selected</c:if> value="1">审核中</option>
						<option <c:if test="${afterSalesVo.status eq 2}">selected</c:if> value="2">审核通过</option>
						<option <c:if test="${afterSalesVo.status eq 3}">selected</c:if> value="3">审核不通过</option>
					</select>
				</li>
				<li>
					<label class="infor_name">业务类型</label>
					<select class="input-middle" name="type" id="type">
						<option value="">全部</option>
						<c:forEach items="${sysList }" var="sys">
							<c:if test="${empty sys.relatedField }">
								<option value="${sys.sysOptionDefinitionId }"
								<c:if test="${sys.sysOptionDefinitionId == afterSalesVo.type}">selected="selected"</c:if>>${sys.title }</option>
							</c:if>
							
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">售后人员</label>
					<select class="input-middle" name="serviceUserId" id="serviceUserId">
						<option value="">全部</option>
						<c:forEach items="${serviceUserList }" var="su">
							<c:if test="${sys.relatedField == null}">
							<option value="${su.userId }"
								<c:if test="${su.userId == afterSalesVo.serviceUserId}">selected="selected"</c:if>>${su.username}</option>
								</c:if>
						</c:forEach>
					</select>
				</li>
				<li>
					<div class="infor_name specialinfor">
						<select name="timeType">
							<option value="1" <c:if test="${afterSalesVo.timeType == 1 }">selected="selected"</c:if>>申请时间</option>
							<option value="2" <c:if test="${afterSalesVo.timeType == 2 }">selected="selected"</c:if>>生效时间</option>
						</select>
					</div> 
					<input type="hidden" name="search" value="click">
					<input type="hidden" name="nowDate" value="${nowDate}">
					<input type="hidden" name="pre1MonthDate" value="${pre1MonthDate}">
					
					<input class="Wdate f_left input-smaller96 m0" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtime\')}'})"
						name="starttime" id="starttime"  type="text" placeholder="请选择日期" value='<c:choose>
							<c:when test="${afterSalesVo.starttime != ''}">
								<date:date value ="${afterSalesVo.searchStartTime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:when test="${afterSalesVo.starttime == ''}">
							</c:when>
							<c:otherwise>
								${pre1MonthDate}
							</c:otherwise>
						</c:choose>'>
					
					<div class="f_left ml1 mr1 mt4">-</div> 
					<input class="Wdate f_left input-smaller96" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'starttime\')}'})"
						name="endtime" id="endtime" type="text" placeholder="请选择日期" value='<c:choose>
							<c:when test="${afterSalesVo.endtime != ''}">
								<date:date value ="${afterSalesVo.searchEndTime}" format="yyyy-MM-dd"/>
							</c:when>
							<c:when test="${afterSalesVo.endtime == ''}">
							</c:when>
							<c:otherwise>
								${nowDate}
							</c:otherwise>
						</c:choose>'>
				</li>
				
			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="searchReset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
						"link":"./aftersales/order/addAfterSalesPage.do?flag=at","title":"新增售后"}'>新增售后</span>
				<!-- <span class="bt-small bg-light-blue bt-bg-style" onclick="exportList();">导出列表</span> -->
			</div> --%>
			<div class="tcenter">
				<span class="pop-new-data bt-small bt-bg-style bg-light-blue" layerParams='{"width":"600px","height":"400px","title":"新增","link":"./toAddPage.do"}'>新增</span>
			</div> 
		</form>
	</div>
	<div class="content">
		<div class="normal-list-page">
			<div >
				<table
					class="table">
					<thead>
						<tr>
							<th class="wid2">序号</th>
							<th class="wid10">名称</th>
							<th class="wid5">是否上架</th>
							<th class="wid5">排序</th>
							<th class="wid15">备注</th>
							<th class="wid8">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="ad" items="${list}" varStatus="num">
							<tr>
								<td>${num.count}</td>
								<td>${ad.title}</td>
								<td>
									<c:if test="${ad.isShow eq 0}">否</c:if>
									<c:if test="${ad.isShow eq 1}">是</c:if>
								</td>
								<td>${ad.sort}</td>
								<td>${ad.comments}</td>
								<td>
									<span class="forbid clcforbid pop-new-data"
												layerParams='{"width":"600px","height":"400px","title":"编辑","link":"./toEditPage.do?adId=${ad.adId}"}'>编辑</span>
									<c:choose>
										<c:when test="${ad.isShow eq 1}">
											<span class="edit-user" onclick="grounding(${ad.adId},0,${sessionScope.curr_user.companyId});">下架</span>
										</c:when>
										<c:otherwise>
											<span class="edit-user" onclick="grounding(${ad.adId},1,${sessionScope.curr_user.companyId});">上架</span>
										</c:otherwise>
									</c:choose> 
									
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty list}">
			      			<tr>
			      				<td colspan="6">查询无结果！请尝试使用其他搜索条件。</td>
			      			</tr>
				       	</c:if>
					</tbody>
				</table>
			</div>
		</div>
       	<tags:page page="${page}"/>
	</div>
</body>

</html>

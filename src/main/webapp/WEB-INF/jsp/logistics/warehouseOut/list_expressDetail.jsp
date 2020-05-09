<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="快递单列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content logistics-warehousein-index">
	<div class="searchfunc">
		<form method="post" id="search" action="<%= basePath %>warehouse/warehousesout/getExpressListPage.do">
			<ul>
				<li><label class="infor_name">快递单号</label> <input type="text"
					class="input-middle" name="logisticsNo" id="logisticsNo"
					value="${express.logisticsNo}" /></li>
				<li><label class="infor_name">快递公司</label>
					<select class="input-middle f_left" name="logisticsId" id="logisticsId">
						<option value="-1">请选择</option>
						<c:forEach items="${logisticsList }" var="logistics">
							<option value="${logistics.logisticsId }" <c:if test="${express.logisticsId eq logistics.logisticsId }">selected</c:if>>${logistics.name }</option>
						</c:forEach>
					</select>
				</li>
				<li><label class="infor_name">业务单据</label> <input type="text"
					class="input-middle" name="xsNo" id="xsNo" value="${express.xsNo}" />
				</li>
				<li><label class="infor_name">发件人</label> 
					<select class="input-middle f_left" name="creator" id="creator">
						<option value="-1">请选择</option>
						<c:forEach items="${userList }" var="user">
							<option value="${user.userId }" <c:if test="${express.creator eq user.userId }">selected</c:if>>${user.username }</option>
						</c:forEach>
					</select>
				</li>
				<li><label class="infor_name">收件名称</label> <input type="text"
					class="input-middle" name="sjName" id="sjName"
					value="${express.sjName}" /></li>
				<li><label class="infor_name">签收状态</label> <select
					class="input-middle f_left" name="arrivalStatus" id="arrivalStatus">
						<option selected="selected" value="">全部</option>
						<option value="0"
							<c:if test="${express.arrivalStatus != null and express.arrivalStatus=='0'}">selected="selected"</c:if>>未签收</option>
						<option value="2"
							<c:if test="${express.arrivalStatus != null and express.arrivalStatus=='2'}">selected="selected"</c:if>>已签收</option>
				</select></li>
				<li><label class="infor_name">发货日期</label>
				    <input type="hidden" name="searchDateType" value="second"/><!-- 标记是否新打开查询页 -->
				     <input type="hidden" id="de_startTime" value="${(empty searchDateType)?_startTime:de_startTime}"/>
					<input class="Wdate f_left input-smaller96 m0" style="width:90px;" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="_startTime"	id="_startTime" value="${_startTime}">
					<div class="f_left ml1 mr1 mt4">-</div> 
					<input type="hidden" id="de_endTime" value="${(empty searchDateType)?_endTime:de_endTime}"/>
					<input class="Wdate f_left input-smaller96" style="width:90px;" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="_endTime" id="_endTime" value="${_endTime}">
				</li>
				<li><label class="infor_name">超时未签收</label> <select
					class="input-middle f_left" name="csArrivalStatus" id="csArrivalStatus">
						<option selected="selected" value="">请选择</option>
						<%-- <option value="0"
							<c:if test="${express.csArrivalStatus != null and express.csArrivalStatus=='0'}">selected="selected"</c:if>>否</option> --%>
						<option value="1"
							<c:if test="${express.csArrivalStatus != null and express.csArrivalStatus=='1'}">selected="selected"</c:if>>是</option>
				</select></li>
				<li><label class="infor_name">发票是否开具</label> <select
						class="input-middle f_left" name="isInvoicing" id="">
					<option selected="selected" value="">全部</option>
					<option value="0"
							<c:if test="${express.isInvoicing != null and express.isInvoicing=='0'}">selected="selected"</c:if>>已开票</option>

					<option value="1"
							<c:if test="${express.isInvoicing != null and express.isInvoicing=='1'}">selected="selected"</c:if>>待开票</option>
					<option value="2"
							<c:if test="${express.isInvoicing != null and express.isInvoicing=='2'}">selected="selected"</c:if>>无需开票</option>
				</select></li>

			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue"
					onclick="search();" id="searchSpan">搜索</span> <span
					class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="exportExpresslist();">导出列表</span> <span
					class="bt-small bg-light-blue bt-bg-style mr20" onclick="queryState()">查询状态</span>
			</div>
		</form>
	</div>
	<div class="list-page">
		<!-- normal-list-page -->
		<div class="">
			<div class="">
				<table class="table">
					<thead>
						<tr>
							<th class="wid5">选择</th>
							<th class="wid5">序号</th>
							<th class="wid15">快递单号</th>
							<th class="wid8">快递公司</th>
							<th class="wid10">运费</th>
							<th class="">业务单据</th>
							<th class="wid12">发件人</th>
							<th class="wid10">发货日期</th>
							<th>收件名称</th>
							<th>备注</th>
							<th>签收状态</th>
							<th>签收时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="wlist" items="${list}" varStatus="num">
							<tr>
							    <td> 
                            		<c:if test="${wlist.arrivalStatus eq 2}">
		                        	</c:if>
			                        <c:if test="${wlist.arrivalStatus eq 0}">
				                        <c:if test="${(wlist.businessType eq 496)or (wlist.businessType eq 515)}">
				                        <input type="checkbox" style="margin-right:0px;" name="b_checknox" value="${wlist.expressId}&${wlist.ywId}&${wlist.businessType}" onclick="canelAll(this,'b_checknox');"/>
				                        </c:if>
				                        <c:if test="${(wlist.businessType != 496) and (wlist.businessType != 515)}">
				                        <input type="checkbox" style="margin-right:0px;" name="b_checknox" value="${wlist.expressId}" onclick="canelAll(this,'b_checknox');"/>
				                        </c:if>
			                        	
			                        </c:if>
                           		</td>
								<td>${num.count}</td>
								<td>
									<c:choose>
										<c:when test="${wlist.isovertime eq 1}">
											<font color="red">${wlist.logisticsNo}</font>
										</c:when>
										<c:otherwise>${wlist.logisticsNo}</c:otherwise>
									</c:choose>
								</td>
								<td>${wlist.logisticsCompanyName}</td>
								<td>${wlist.amount}</td>
								
								<td>
								<c:if test="${wlist.businessType eq 496}">
								 	<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder_496_${wlist.ywId}","link":"./order/saleorder/view.do?saleorderId=${wlist.ywId}","title":"销售订单信息"}'>${wlist.xsNo}</a>
		                        </c:if>
		                        <c:if test="${wlist.businessType eq 497}">
								  <%-- <a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewsaleorder${wlist.ywId}","link":"./finance/invoice/viewSaleorder.do?saleorderId=${wlist.ywId}","title":"发票号"}'>${wlist.xsNo}</a> --%>
								  ${wlist.xsNo}
		                        </c:if>
		                        <c:if test="${wlist.businessType eq 498}">
								  	<a class="addtitle" tabtitle="{&quot;num&quot;:&quot;getFileDeliveryDetail_498_${wlist.ywId}&quot;,&quot;link&quot;:&quot;./logistics/filedelivery/getFileDeliveryDetail.do?fileDeliveryId=${wlist.ywId}&quot;,&quot;title&quot;:&quot;文件寄送详情&quot;}">${wlist.xsNo}</a>
		                        </c:if>
		                        <c:if test="${wlist.businessType eq 515}">
								  	<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewbuyorder_515_<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./order/buyorder/viewBuyorder.do?buyorderId=${wlist.ywId}","title":"采购订单信息"}'>${wlist.xsNo}</a>
		                        </c:if>
		                        <c:if test="${wlist.businessType eq 582}">
		                            <c:if test="${(wlist.ywType eq 535)}">
		                            	<span class="font-blue addtitle" tabTitle='{"num":"viewaftersales_535_<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${wlist.ywId}&traderType=1","title":"售后详情"}'>${wlist.xsNo}</span>
		                            </c:if>
		                             <c:if test="${(wlist.ywType eq 536)}">
		                            	<span class="font-blue addtitle" tabTitle='{"num":"viewaftersales_536_<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${wlist.ywId}&traderType=2","title":"售后详情"}'>${wlist.xsNo}</span>
		                            </c:if>
		                        </c:if>
								</td>
								
								<td>${wlist.creatName}</td>
								<td><date:date value="${wlist.fhTime}" format="yyyy-MM-dd" /></td>
								<td>${wlist.sjName}</td>
								<td>${wlist.logisticsComments}</td>
								<td><c:choose>
										<c:when test="${wlist.arrivalStatus eq 0}">
											未签收
										</c:when>
										<c:when test="${wlist.arrivalStatus eq 2}">
											已签收
										</c:when>
									</c:choose></td>
								<td><date:date value="${wlist.arrivalTime}"
										format="yyyy-MM-dd" /></td>
							</tr>
						</c:forEach>
						<c:if test="${empty list}">
							<tr>
								<td colspan="12">暂无物流记录</td>
							</tr>
						</c:if>
					</tbody>
				</table>

			</div>
		</div>
		<c:if test="${not empty list}">
			<div>
				<div class="inputfloat f_left"
					>
					<div class="allchose">
					<input type="checkbox" name="all_b_checknox" onclick="selectall(this);" style='margin:6px 3px 0 0;'/>
					<label
						class="mr10 mt4" >全选</label>
						
						 <!-- <span
						class="bt-middle bt-border-style border-blue"
						onclick="delBatchRecode();">修改运费</span>  -->
						<span
						class="bt-middle bt-border-style border-blue"
						onclick="changeSt();">改为已签收</span>
						<label class="mt4">${cnt}件快递未签收</label>
					</div>
				</div>
				<tags:page page="${page}" />
			</div>
		</c:if>
	</div>

</div>
<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/edit_express.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="出库记录" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content logistics-warehousein-index">
	<div class="searchfunc">
		<form method="post" id="search" action="<%=basePath%>warehouse/warehousesout/warehouseOutLogList.do">
			<ul>
				<li>
					<label class="infor_name">产品名称</label>
					<input type="text" class="input-middle"  name="goodsName"  value="${warehouseGoodsOperateLog.goodsName}"/>
				</li>
				<li>
					<label class="infor_name">品牌</label>
					<input type="text" class="input-middle"  name="brandName"  value="${warehouseGoodsOperateLog.brandName}"/>
				</li>
				<li>
					<label class="infor_name">型号</label>
					<input type="text" class="input-middle"  name="model"  value="${warehouseGoodsOperateLog.model}"/>
				</li>
				<li>
					<label class="infor_name">物料编码</label>
					<input type="text" class="input-middle"  name="materialCode"  value="${warehouseGoodsOperateLog.materialCode}"/>
				</li>
				<li>
					<label class="infor_name">订货号</label>
					<input type="text" class="input-middle" name="sku"  value="${warehouseGoodsOperateLog.sku}"/>
				</li>
				<li>
					<label class="infor_name">贝登条码</label>
					<input type="text" class="input-middle"  name="barcode"  value="${warehouseGoodsOperateLog.barcode}"/>
				</li>
				<li>
					<label class="infor_name">厂商条码</label>
					<input type="text" class="input-middle"  name="barcodeFactory"  value="${warehouseGoodsOperateLog.barcodeFactory}"/>
				</li>
				<li>
					<label class="infor_name">出库单据</label>
					<input type="text" class="input-middle"  name="saleorderNo"  value="${warehouseGoodsOperateLog.saleorderNo}"/>
				</li>
				<li>
					<label class="infor_name">收货方</label>
					<input type="text" class="input-middle"  name="saletraderName"  value="${warehouseGoodsOperateLog.saletraderName}"/>
				</li>
				<li>
					<label class="infor_name">批次号</label>
					<input type="text" class="input-middle"  name="batchNumber"  value="${warehouseGoodsOperateLog.batchNumber}"/>
				</li>
				<li>
					<label class="infor_name">复核结果</label>
					<select class="input-middle" name="checkStatus" id="checkStatus">
						<option value="">全部</option>
						<option value="0" <c:if test="${warehouseGoodsOperateLog.checkStatus == 0}">selected</c:if>>未复核</option>
						<option value="1" <c:if test="${warehouseGoodsOperateLog.checkStatus == 1}">selected</c:if>>复核通过</option>
						<option value="2" <c:if test="${warehouseGoodsOperateLog.checkStatus == 2}">selected</c:if>>复核不通过</option>
					</select>
				</li>
				<li>
					<label class="infor_name">出库操作人</label>
					<select class="input-middle" name="creator" id="creator">
						<option value="">全部</option>
						<c:forEach var="list" items="${logisticsUserList}" varStatus="status">
							<option value="${list.userId}" <c:if test="${warehouseGoodsOperateLog.creator ==list.userId}">selected</c:if>>${list.username}</option>
						</c:forEach>
					</select>
				</li>
				
				    <li>
				    <input type="hidden" name="searchDateType" value="second"/><!-- 标记是否新打开查询页 -->
                    <label class="infor_name">出库时间</label>
                    <input type="hidden" id="de_startTime" value="${(empty searchDateType)?_startTime:de_startTime}"/>
					<input class="Wdate f_left input-smaller96 m0" style="width:90px;" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="_startTime"	id="_startTime" value="${_startTime}">
					<div class="f_left ml1 mr1 mt4">-</div> 
					<input type="hidden" id="de_endTime" value="${(empty searchDateType)?_endTime:de_endTime}"/>
					<input class="Wdate f_left input-smaller96" style="width:90px;" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="_endTime" id="_endTime" value="${_endTime}">
                    </li>
			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="exportWareHouseOutList();">导出列表</span>
			</div>
		</form>
	</div>
	<div class="list-page">
            <!-- normal-list-page -->
            <div class="fixdiv">
                <div class="superdiv">
                    <table class="table">
                        <thead>
                            <tr>
                               <th class="wid5">序号</th>
		                       <th class="wid18">出库时间</th>
		                       <th class="wid11">出库单据</th>
		                       <th class="wid30">产品名称</th>
		                       <th class="wid10">品牌</th>
		                       <th class="wid12">型号</th>
		                       <th class="wid8">数量</th>
		                       <th class="wid8">单位</th>
		                       <th>单价</th>
		                       <th>总价</th>
		                       <th class="wid15">收货方</th>
		                       <th>贝登条码</th>
		                       <th>厂商条码</th>
		                       <th class="wid15">生产日期</th>
		                       <th class="wid15">效期截止日期</th>
		                       <th>批次号</th>
		                       <th >出库位置</th>
		                       <th>仓存备注</th>
		                       <th class="wid8">出库操作人</th>
		                       <th>复合结果</th>
		                       <th>复核人</th>
                            </tr>
                        </thead>
                        <tbody>
                         <c:if test="${empty list}">
							<tr>
								<td colspan="20">暂无记录</td>
							</tr>
						 </c:if>
						 <c:if test="${not empty list}">
                         <c:forEach var="wlist" items="${list}" varStatus="num">
                            <c:set var="status" value="0"></c:set>
							<c:set var="color" value=""></c:set>
							<c:if test="${0 != wlist.expirationDate &&(wlist.expirationDate-time < 3600*24*90*1000)}">
								<c:set var="status" value="2"></c:set>
								<c:set var="color" value="orange"></c:set>
							</c:if>
							<c:if test="${0 != wlist.expirationDate && (wlist.expirationDate<time)}">
								<c:set var="status" value="1"></c:set>
								<c:set var="color" value="red"></c:set>
							</c:if>
                            <tr>
                                	<td>${num.count}</td>
                                	<td><date:date value ="${wlist.addTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
                                	<td>
                                	<c:if test="${wlist.operateType == 2}">
                                	<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"warehousesout<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./warehouse/warehousesout/detailJump.do?saleorderId=${wlist.saleorderId}","title":"出库详情"}'>${wlist.saleorderNo}</a>
                                	</c:if>
                                	<c:if test="${wlist.operateType == 4 }">
                                	<span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./warehouse/businessWarehouseOut/viewBusinessWdetail.do?afterSalesId=${wlist.saleorderId}&businessType=1","title":"出库详情"}'>${wlist.saleorderNo}</span>
                                	</c:if>
                                	<c:if test="${ wlist.operateType == 6 or wlist.operateType == 7}">
                                	<span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./warehouse/businessWarehouseOut/viewBusinessWdetail.do?afterSalesId=${wlist.saleorderId}&businessType=2","title":"出库详情"}'>${wlist.saleorderNo}</span>
                                	</c:if>
										<%--<c:if test="${wlist.operateType==10}">--%>
											<%--<span class="font-blue addtitle" tabTitle='{"num":"lendOutdetailJump${wlist.lendOutId}",--%>
											<%--"link":"./warehouse/warehousesout/lendOutdetailJump.do?lendOutId=${wlist.lendOutId}","title":"外借详情页"}'>${wlist.lendOutNo}</span>--%>
										<%--</c:if>--%>
										<c:if test="${wlist.operateType==10}">
											<span class="font-blue addtitle" tabTitle='{"num":"lendOutdetailJump${wlist.saleorderId}",
											"link":"./warehouse/warehousesout/lendOutdetailJump.do?lendOutId=${wlist.saleorderId}","title":"外借详情页"}'>${wlist.saleorderNo}</span>
										</c:if>
                                	</td>
                                	<td class="text-left">
				                        <div >
				                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${wlist.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${wlist.goodsId}","title":"产品信息"}'>${wlist.goodsName}</a>
				                        </div>
				                        <div>${wlist.sku}</div>
				                        <div>${wlist.materialCode}</div>
				                    </td>
                                    <td>${wlist.brandName}</td>
                                    <td>${wlist.model}</td>
                                    <td>${0-wlist.num}</td>
                                    <td>${wlist.unitName}</td>
                                    <td>
										<c:if test="${wlist.operateType!=10}">
											${wlist.price}
										</c:if>
									</td>
                                    <td>
										<c:if test="${wlist.operateType != 10}">
											${0-wlist.num*wlist.price}
										</c:if>
									</td>
                                    <td>${wlist.saletraderName}</td>
                                    <td>${wlist.barcode}</td>
                                    <td>${wlist.barcodeFactory}</td>
                                    <td><font color="${color}"><date:date value ="${wlist.productDate}" format="yyyy-MM-dd"/></font></td>
                                    <td><font color="${color}"><date:date value ="${wlist.expirationDate}" format="yyyy-MM-dd"/></font></td>
                                    <td>${wlist.batchNumber}</td>
                                    <td>${wlist.warehouseArea}</td>
                                    <td>${wlist.comments}</td>
                                    <td>${wlist.operator}</td>
                                    <td>
                                    	<c:if test="${wlist.checkStatus == 0}">
                                    		<font color="red">未复核</font>
                                    	</c:if>
                                    	<c:if test="${wlist.checkStatus == 1}">
                                    		复核通过
                                    	</c:if>
                                    	<c:if test="${wlist.checkStatus == 2}">
                                    		复核不通过
                                    	</c:if>
                                    </td>
                                    <td>
                                    <c:if test="${wlist.companyId == 1 && wlist.checkStatus!=0}">
                                    ${wlist.checkUserName}
                                    </c:if>
                                    <%--<c:if test="${wlist.companyId != 1}">--%>
                                                                                                             <%--陆毅--%>
                                    <%--</c:if>--%>
                                    </td>
                            </tr>
							</c:forEach>
							</c:if>
                        </tbody>
                    </table>
                </div>               
            </div>
            <div class="parts">
      			<tags:page page="${page}" />
         </div>
       </div>
    	
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseIn/viewWarehouseIn.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

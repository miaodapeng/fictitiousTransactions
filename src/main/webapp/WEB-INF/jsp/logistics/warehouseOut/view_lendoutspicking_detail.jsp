<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="拣货详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<div class="main-container">
		<div class="begin-jianhuo-container">
			<div class="begin-jianhuo">
				<ul>
					<li>
						<div class="iconidentity ">
							<div class="arriveThisStep hasArrive">
								<i class="iconconfirmnumber"></i>
							</div>
							<div>确认拣货数量</div>
						</div>
					</li>
					<li>
						<div class="iconidentity">
							<div class="arriveThisStep hasArrive">
								<i class="iconwarehousecheck"></i>
							</div>
							<div>仓库拣货</div>
						</div>
					</li>
					<li>
						<div class="iconidentity">
							<div class="arriveThisStep">
								<i class="iconpackage"></i>
							</div>
							<div>打包发货</div>
						</div>
					</li>
					<li>
						<div class="iconidentity">
							<div class="arriveThisStep">
								<i class="iconprintoutwarehouse"></i>
							</div>
							<div>打印出库单、新增快递</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<div class="parts">
		 <div class="title-container">
			<div class="table-title nobor">基本信息</div>
		</div>
			<table class="table">
			<thead>
				<tr>
					<th class="wid10">业务单号</th>
					<th class="wid15">生效时间</th>
					<th>客户名称</th>
					<th class="wid8">创建者</th>
					<th>商品总数</th>
				</tr>
			</thead>
			<tbody>
				<tr>
				    <td><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"lendOutdetailJump${lendout.lendOutId}","link":"./warehouse/warehousesout/lendOutdetailJump.do?lendOutId=${lendout.lendOutId}","title":"外借详情页"}'>${lendout.lendOutNo}</a></td>
					<td><date:date value="${lendout.modTime}" /></td>
					<td>
					<c:if test="${lendout.traderType eq 1 }">
					<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewcustomer${lendout.traderId}", "link":"./trader/customer/baseinfo.do?traderId=${lendout.traderId}", "title":"客户信息"}'>${lendout.traderName}</a>
					</c:if>
					<c:if test="${lendout.traderType eq 2 }">
					<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewsupplier${lendout.traderId}", "link":"./trader/supplier/baseinfo.do?traderId=${lendout.traderId}", "title":"供应商信息"}'>${lendout.traderName}</a>
					</c:if>
					</td>
					<td>${user.username}</td>
					<td>${lendout.lendOutNum }</td>
				</tr>
			</tbody>
		</table>
	</div>

		<div class="parts table-style10-1 ">
			<div class="title-container">
				<div class="table-title ">产品信息</div>
			</div>
		    <!----------------------- 用于打印拣货单的div开始----------------------------- -->
		    <div id="jh_mdiv" class="none">
			<div class="pick-list-title">
				<c:if test="${companyName != null}">
				 	${companyName}拣货单
				 </c:if>
				 <c:if test="${companyName == null}">				 
                                                           贝登医疗拣货单
				 </c:if>
            </div>
            <div class="pick-pro-list">
               <div class="f_left">合同单号：${lendout.lendOutNo}</div> 
               <div class="f_right">拣货时间：<date:date value="${time }" /></div> 
            </div>
			
			<table class="table  table-style10" >
				<thead>
					<tr>
					    <th>序号</th>
						<th>订货号</th>
						<th>产品</th>
						<th>型号/物料</th>
						<th>单位</th>
						<th class="wid10">生产日期</th>
						<th class="wid10">效期</th>
						<th>保质期产品</th>
						<th class="wid10">入库时间</th>
						<th class="">批次</th>
						<th class="">库存量</th>
						<th class="wid5">拣货数量</th>
						<th class="">储位</th>
						<th class="">备注</th>
					</tr>
				</thead>
				<tbody id="print_jh_id">
				<c:set var="lNum" value="0"/>
				  	<c:forEach var="list" items="${lendout.goodslist}" varStatus="num">
						<c:forEach var="listw" items="${lendout.wlist}" varStatus="num2">
							<c:set var="lNum" value="${lNum+1}"/>
							<tr>
							    <td>${lNum}</td>
								<td>${list.sku}</td>
								<td>${list.goodsName}</td>
								<td>${list.model}/<br>${list.materialCode}</td>
								<td>${list.unitName}</td>
								<td><date:date value="${listw.productDate }" format="yyyy-MM-dd"/></td>
								<td><date:date value="${listw.expirationDate }" format="yyyy-MM-dd"/></td>
								<c:choose>
									<c:when test="${listw.productDateStr eq 1}">
										<td>否</td>
									</c:when>
									<c:otherwise>
										<td>是</td>
									</c:otherwise>
								</c:choose>
								<td><date:date value="${listw.addTime }" format="yyyy-MM-dd"/></td>
								<td>${listw.batchNumber }</td>
								<td>${list.totalNum}</td>
								<td>${listw.pCtn}</td>
								<td>${listw.storageAreaName }</td>
								<td>${listw.comments }</td>
							</tr>
		       			 </c:forEach>
					</c:forEach>
				</tbody>
			</table>
			 <div class="pick-list-name">
                                     制单：${userName}
            </div>
			</div>
			<!----------------------- 用于打印拣货单的div结束----------------------------- -->
			<div id="sdiv">
			 <c:forEach var="list" items="${lendout.goodslist}" varStatus="num">

			<table class="table  table-style10">
				<thead>
					<tr>
						<th class="wid5">序号</th>
						<th>产品名称</th>
						<th class="wid15">品牌/型号</th>
						<th class="wid5">单位</th>
						
						<th class="wid8">已发/总数</th>
						<th class="wid8">拣货未发</th>
						<th class="">需拣货量/可拣货库存</th>
						<th class="wid8">本次拣货</th>
					</tr>
				</thead>
				<tbody>

					<tr>
						<td>${num.count}</td>
						<td class="text-left">
	                        <div >
	                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${list.goodsName}</a>
	                          <input type="hidden" id="goodsId${num.count}" value="${list.goodsId}"/>
	                        </div>
	                        <div>${list.sku}</div>
	                    </td>
						<td>${list.brandName}${list.model}</td>
						<td>${list.unitName}</td>
						
						<td>${list.deliveryNum}/${list.num}</td>
						<td class="warning-color1" >${list.pickCnt - list.deliveryNum}</td>
						<td>${list.num - list.pickCnt  }/${list.totalNum }</td>
						<td><input type="text" disabled="disabled" name=""  value="${list.nowNum}"></td>
					</tr>
					<tr>
						<td colspan="8" class="table-container">
							<table class="table">
								<thead>
									<tr>
										<th class="wid12">拣货/总数</th>
										<th class="wid10">生产日期</th>
										<th class="wid15">效期截止日期</th>
										<th class="wid15">入库时间</th>
										<th>批次号</th>
										<th>存储位置</th>
										<th>仓存备注</th>
										<th class="wid10">关联单号</th>
									</tr>
								</thead>
								<tbody>
								   <c:forEach var="listw" items="${lendout.wlist}" varStatus="num2">
									<tr>
										<td>${listw.pCtn }/${listw.cnt }</td>
										<td><date:date value="${listw.productDate }" format="yyyy-MM-dd"/></td>
										<td><date:date value="${listw.expirationDate }" format="yyyy-MM-dd"/></td>
										<td><date:date value="${listw.addTime }" format="yyyy-MM-dd"/></td>
										<td>${listw.batchNumber }</td>
										<td>${listw.warehouseArea }</td>
										<td>${listw.comments }</td>
										<td>
										<c:if test="${ listw.operateType == 1}">
										<a class="addtitle" href="javascript:void(0);"
												tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/buyorder/viewBuyorder.do?buyorderId=${listw.buyorderId}","title":"订单信息"}'>${listw.buyorderNo}</a>
										</c:if>
										<c:if test="${ listw.operateType == 3 or listw.operateType == 5}">
										<span class="font-blue addtitle" tabTitle='{"num":"viewsaleorder${listw.buyorderId}","link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${listw.buyorderId}&traderType=1","title":"售后详情"}'>${listw.buyorderNo}</span>
										</c:if>
										<c:if test="${ listw.operateType == 8}">
										<span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${listw.buyorderId}&traderType=2","title":"售后详情"}'>${listw.buyorderNo}</span>
										</c:if>
										<c:if test="${ listw.operateType == 9}">
										<span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./warehouse/warehousesout/lendOutdetailJump.do?lendOutId=${listw.buyorderId}","title":"外借详情页"}'>${listw.buyorderNo}</span>
										</c:if>
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>

				</tbody>
			</table>
			</c:forEach>
			</div>
			 <div class="table-friend-tip">
                                                友情提示
                <br/>1、如果拣货结果与预拣货数量不一致，请撤销拣货后重新设定数量并拣货；
            </div>
			 <div class="table-buttons">
                <span id="dy_jhd_span" class=" bt-border-style border-blue bt-small" onclick="preview('jh_mdiv')">打印拣货单</span>
                 <span id="sm_db_span" class="bg-light-blue bt-bg-style bt-small addtitle"
					tabTitle='{"num":"lendout_lendoutId_${lendout.lendOutId }","link":"./warehouse/warehousesout/warehouseSmLendOut.do?lendOutId=${lendout.lendOutId }&businessType=${businessType}","title":"扫码打包发货"}'>拣货完成，扫码打包发货</span>
					
              	<span id="jh_span" class="bg-light-blue bt-bg-style bt-small addtitle none"
					tabTitle='{"num":"lendout_lendoutId_${lendout.lendOutId}","link":"./warehouse/warehousesout/viewLendOutPicking.do?lendOutId=${lendout.lendOutId }&businessType=${businessType}","title":"开始拣货"}'>拣货</span>
               <%--  <span class="bg-light-blue bt-bg-style bt-small addtitle"
					tabTitle='{"num":"warehousesout_viewOutDetail_${afterSales.afterSalesId }","link":"./warehouse/businessWarehouseOut/viewBsOutDetail.do?afterSalesId=${afterSales.afterSalesId}&businessType=${businessType }","title":"手动打包发货"}'>拣货完成，手动打包发货</span> --%>
            </div>
		</div>
		<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">拣货记录</div>
		</div>
		<table class="table" id="jh_table">
			<thead>
				<tr>
					<th class="wid5">选择</th>
					<th class="wid5">序号</th>
					<th>产品名称</th>
					<th>品牌/型号</th>
					<th class="wid10">物料编码</th>
					<th class="wid4">单位</th>
					<th>当次拣货数量</th>
					<th>拣货时间</th>
					<th class="wid8">操作人</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="listpick" items="${warehousePickList}"
					varStatus="num2">
					<tr>
						<td>
						    <c:if test="${listpick.cnt ==0}">
                        		<input type="checkbox" name="b_checknox" alt="<date:date value ="${listpick.addTime}" format="yyyy-MM-dd HH:mm:ss"/>" value="${listpick.warehousePickingId},${listpick.warehousePickingDetailId}" onclick="canelAll(this,'b_checknox')">
                        	</c:if>
                        	<c:if test="${ listpick.cnt >0}">
                        	</c:if>
                        </td>
						<td>${num2.count}</td>
						<td class="text-left">
	                        <div >
	                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${listpick.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${listpick.goodsId}","title":"产品信息"}'>${listpick.goodsName}</a>
	                        </div>
	                        <div>${listpick.sku}</div>
	                    </td>
						<td>${listpick.brandName}<br />${listpick.model}</td>
						<td>${listpick.materialCode}</td>
						<td>${listpick.unitName}</td>
						<td>${listpick.num}</td>
						<td><date:date value ="${listpick.addTime}" format="yyyy-MM-dd hh:mm:ss"/></td>
						<td>${listpick.operator}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty warehousePickList}">
					<tr>
						<td colspan="9">暂无拣货记录</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<c:if test="${not empty warehousePickList}">
		<div class="table-style4 ">
				<div class="allchose">
					<input type="checkbox" name="all_b_checknox" onclick="selectall(this,'pick_checkbox');" value="b_checknox"> <span>全选</span>
				</div>
				<div class="times">
					<span class="mr10">请选择批次</span>
					<c:forEach var="tlist" items="${timeArray}" varStatus="num">
                     <input type="checkbox" name="${tlist}" id="pick_checkbox" onclick="checkbarcode('${tlist}', this.checked,'b_checknox');">
                     <span class="mr20">${tlist}</span>
                    </c:forEach>
				</div>
				<div class="print-record">
					<span class="bt-border-style border-red" onclick="cancelWlogAll('b_checknox')">撤销拣货</span>
				</div>
		</div>
		</c:if>
	</div> <input type="hidden" id="_pickNums" value="${pickNums}"/>
	</div>

<script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/lendpickDetail.js?rnd=<%=Math.random()%>'></script>
	<script type="text/javascript" src='<%= basePath %>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
	<%@ include file="../../common/footer.jsp"%>
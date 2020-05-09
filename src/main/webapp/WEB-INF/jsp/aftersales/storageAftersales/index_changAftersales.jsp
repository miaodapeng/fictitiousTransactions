<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="采购换货入库" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<div class="customer">
	<ul>
		<li><a href="/logistics/warehousein/index.do" >采购入库</a></li>
		<li><a href="/aftersales/order/getChangeAftersales.do" class="customer-color">采购售后入库</a>
		</li>
		<li><a href="/aftersales/order/getStorageAftersales.do">销售售后入库</a>
		</li>
		<li><a href="/logistics/warehousein/lendOutIndex.do">商品归还入库</a></li>
	</ul>
</div>
	<div class="main-container logistics-warehousein-index">
		<div class="searchfunc">
			<form method="post" id="search" action="<%= basePath %>/aftersales/order/getChangeAftersales.do">
				<ul>
					<li>
						<label class="infor_name">客户名称</label>
						<input type="text" class="input-middle" name="traderName" id="traderName" value="${afterSalesVo.traderName}"/>
					</li>
					<li>
						<label class="infor_name">产品名称</label>
						<input type="text" class="input-middle" name="goodsName" id="goodsName" value="${afterSalesVo.goodsName}"/>
					</li>
					<li>
						<label class="infor_name">品牌</label>
						<input type="text" class="input-middle" name="brandName" id="brandName" value="${afterSalesVo.brandName}"/>
					</li>
					<li>
						<label class="infor_name">型号</label>
						<input type="text" class="input-middle" name="model" id="model" value="${afterSalesVo.model}"/>
					</li>
					<li>
						<label class="infor_name">物料编码</label>
						<input type="text" class="input-middle" name="materialCode" id="materialCode" value="${afterSalesVo.materialCode}"/>
					</li>
					<li>
						<label class="infor_name">单号</label>
						<input type="text" class="input-middle" name="afterSalesNo" id="afterSalesNo" value="${afterSalesVo.afterSalesNo}"/>
					</li>
					<li>
						<label class="infor_name">采购单号</label>
						<input type="text" class="input-middle" name="orderNo" id="orderNo" value="${afterSalesVo.orderNo}"/>
					</li>
					 <li>
	                   <label class="infor_name">生效时间</label>
	                   <input type="hidden" name="searchDateType" value="second"/><!-- 标记是否新打开查询页 -->
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
				</div>
			</form>
		</div>
          <div class="table-style5">
          <c:forEach var="list" items="${afterSalesList}" varStatus="num">
            <table class="table">
                <thead>
                    <tr>
                       <th class="wid7">序号</th>
                        <th class="wid20">单号</th>
						<th class="wid20">生效时间</th>
						<th class="wid20">对应采购单号</th>
						<th class="">客户名称</th>
						<th class="wid14">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${num.count}</td>
                    	<td>
                    	<span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${list.afterSalesId}&traderType=2","title":"售后详情"}'>${list.afterSalesNo}</span>
                    	</td>
                        <td><date:date value ="${list.validTime}"/></td>
                        <td>
                        <a class="addtitle" href="javascript:void(0);" 
												tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/buyorder/viewBuyorder.do?buyorderId=${list.orderId}","title":"订单信息"}'>${list.orderNo}</a>
                       </td>
						<td>${list.traderName}</td>
						<td  class="begin-enter-lib  caozuo">
                                               <span class="bt-smaller bt-border-style border-blue addtitle"
					tabTitle='{"num":"aftersales_returnGoodsDetail_${list.afterSalesId}","link":"./aftersales/order/returnGoodsDetail.do?afterSalesId=${list.afterSalesId}&type=${list.type }&orderId=${list.orderId }&businessType=2","title":"退货入库详情"}'>开始入库</span>
                                            </td>
                    </tr>
                    <tr>
                        <td colspan="6" class="tdpadding">
                            <table class="table">
                                    <tbody>
                                        <tr>
	                                        <th class="wid25">产品名称</th>
	                                        <th class="">订货号</th>
	                                        <th class=" wid10">品牌</th>
	                                        <th class=" wid10">型号</th>
	                                        <th class=" wid15">物料编码</th>
	                                        <th class=" ">单位</th>
	                                        <th class=" ">换货总数</th>
	                                        <th class=" ">未入库数量</th>
	                                        <th>状态</th>
                                        </tr>
                                      <c:forEach var="afterSalesGoodsVo" items="${list.afterSalesGoodsList}">
	                                    <tr>
	                                        <td class="text-left">
						                        <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${afterSalesGoodsVo.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${afterSalesGoodsVo.goodsId}","title":"产品信息"}'>${newSkuInfosMap[afterSalesGoodsVo.sku].SHOW_NAME}</a>
						                    </td>
											<td>${newSkuInfosMap[afterSalesGoodsVo.sku].SKU_NO}</td>
											<td>${newSkuInfosMap[afterSalesGoodsVo.sku].BRAND_NAME}</td>
											<td>${newSkuInfosMap[afterSalesGoodsVo.sku].MODEL}</td>
											<td>${newSkuInfosMap[afterSalesGoodsVo.sku].MATERIAL_CODE}</td>
											<td>
												${newSkuInfosMap[afterSalesGoodsVo.sku].UNIT_NAME}
											</td>
											<td>${afterSalesGoodsVo.num}</td>
											<td>${afterSalesGoodsVo.num-afterSalesGoodsVo.arrivalNum}</td>
											   <c:choose>
												<c:when test="${afterSalesGoodsVo.arrivalStatus eq 0}">
													<td class="warning-color1">未收货</td>
												</c:when>
												<c:when test="${afterSalesGoodsVo.arrivalStatus eq 1}">
													<td class="warning-color1">部分收货</td>
												</c:when>
												<c:when test="${afterSalesGoodsVo.arrivalStatus eq 2}">
													<td>全部收货</td>
												</c:when>
												<c:otherwise>
												<td></td>
												</c:otherwise>
											  </c:choose>
	                                    </tr>
                                    </c:forEach>
                                    </tbody>
                             </table>
                             
                        </td>
                    </tr>
                     <c:if test="${empty list.afterSalesGoodsList}">
	                    <tr>
	                        <td colspan="6">暂无商品记录</td>
	                    </tr>
                    </c:if>
                </tbody>
            </table>
             </c:forEach>
             
			<c:if test="${empty afterSalesList}">
				<!-- 查询无结果弹出 -->
				 <table class="table">
				 	<tbody>
						<tr>
                        	<td colspan="6">查询无结果！请尝试使用其他搜索条件。</td>
                    	</tr>
                     </tbody>
           		</table>
			  </c:if>
           <tags:page page="${page}" />
        </div>
	</div>
	<script type="text/javascript" src='<%= basePath %>static/js/aftersales/storageAftersales/index_storageAftersales.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

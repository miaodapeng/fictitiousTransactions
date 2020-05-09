
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="已忽略订单列表" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src='<%= basePath %>static/js/order/buyorder/list_ignore.js?rnd=<%=Math.random()%>'></script>
<div class='content'>
	<div class="searchfunc">
		<form method="post" id="search" action="<%= basePath %>/order/buyorder/getIgnoreSaleorderPage.do">
			<ul>
				<li>
					<label class="infor_name">产品名称</label>
					<input type="text" class="input-middle" name="goodsName" id="goodsName" value="${saleorderGoodsVo.goodsName}"/>
				</li>
				<li>
					<label class="infor_name">品牌</label>
					<input type="text" class="input-middle" name="brandName" id="brandName" value="${saleorderGoodsVo.brandName}"/>
				</li>
				<li>
					<label class="infor_name">型号</label>
					<input type="text" class="input-middle" name="model" id="model" value="${saleorderGoodsVo.model}"/>
				</li>
				<li>
					<label class="infor_name">订货号</label>
					<input type="text" class="input-middle" name="sku" id="sku" value="${saleorderGoodsVo.sku}"/>
				</li>
				<li>
					<label class="infor_name">单号</label>
					<input type="text" class="input-middle" name="saleorderNo" id="saleorderNo" value="${saleorderGoodsVo.saleorderNo}"/>
				</li>
				<li>
					<label class="infor_name">申请人</label>
					<select class="input-middle" name="applicantId" id="">
						<option value="">全部</option>
						<c:forEach items="${applicantList}" var="user">
							<option value="${user.userId}" <c:if test="${saleorderGoodsVo.applicantId eq user.userId}">selected="selected"</c:if>>${user.username}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">是否直发</label>
					<select class="input-middle" name="deliveryDirect" id="deliveryDirect">
						<option value="">全部</option>
						<option <c:if test="${saleorderGoodsVo.deliveryDirect eq 0}">selected</c:if> value="0">普发</option>
						<option <c:if test="${saleorderGoodsVo.deliveryDirect eq 1}">selected</c:if> value="1">直发</option>
					</select>
				</li>
				
			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
			</div>
		</form>
	</div>
	<div class="list-page">
		<div class="fixdiv">
			<div class="superdiv">
				<table
					class="table table-bordered table-striped table-condensed table-centered">
					<thead>
						<tr>
							<th class="wid4">选择</th>
							<th class="table-smallest6">单号</th>
							<th class="wid8">申请人</th>
							<th class="wid8">订货号</th>
							<th>产品名称</th>
							<th class="table-smallest6">品牌</th>
							<th class="table-smallest5">型号</th>
							<th class="table-smallest6">物料编码</th>
							<th class="wid4">单位</th>
							<th >产品级别</th>
							<th class="wid12">库存</th>
							<th class="wid10">可采购时间</th>
							<th class="table-smallest5">是否直发</th>
							<th class="table-smallest5">未采购数量</th>
							<th class="table-smallest5">已采购数量</th>
							<th class="table-smallest5">内部备注</th>
							<th class="table-smallest5">产品备注</th>
							<th class="table-smallest5">终端名称</th>
							<th class="table-smallest5">销售区域</th>
							<th class="table-smallest5">忽略时间</th>
							<th class="table-smallest5">忽略人</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="saleorderGoods" items="${list}" varStatus="num">
							<tr>
								<td>
									<c:if test="${saleorderGoods.lockedStatus ne 1 }">
										<input type="checkbox" name="oneSelect" autocomplete="off"/>
										<input type="hidden" name="saleorderGoodId" value="${saleorderGoods.goodsId}|${saleorderGoods.saleorderGoodsId}"/>
										<input type="hidden" name="goodsId" value="${saleorderGoods.goodsId}"/>
										<input type="hidden" name="noBuyNum" value="${saleorderGoods.num-saleorderGoods.buyNum}">
										<input type="hidden" name="saleorderNo" value="${saleorderGoods.saleorderNo}">
										<input type="hidden" name="saleorderId" value="${saleorderGoods.saleorderId}">
										<input type="hidden" name="deliveryDirect" value="${saleorderGoods.deliveryDirect}">
									</c:if>
								</td>
								<td>${saleorderGoods.saleorderNo}</td>
								<td>${saleorderGoods.applicantName}</td>
								<td>${newSkuInfosMap[saleorderGoods.sku].SKU_NO}</td>
								<td class="text-left">
									<span class="font-blue cursor-pointer addtitle" 
		                    			tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                    					"link":"./goods/goods/viewbaseinfo.do?goodsId=${saleorderGoods.goodsId}","title":"产品信息"}'>${newSkuInfosMap[saleorderGoods.sku].SHOW_NAME}</span>
								</td>
								<td>${newSkuInfosMap[saleorderGoods.sku].BRAND_NAME}</td>
								<td>${newSkuInfosMap[saleorderGoods.sku].MODEL}</td>
								<td>${newSkuInfosMap[saleorderGoods.sku].MATERIAL_CODE}</td>
								<td>${newSkuInfosMap[saleorderGoods.sku].UNIT_NAME}</td>
								<td>${newSkuInfosMap[saleorderGoods.sku].GOODS_LEVEL_NAME}</td>
								<td>
                                    <div class="customername pos_rel">
                                       <span class="brand-color1">${saleorderGoods.goodsStock}</span> 
                                       <c:if test="${saleorderGoods.goodsStock ne 0}">
                                             	<i class="iconbluesigh "></i>
                                         </c:if>
                                   <div class="pos_abs customernameshow" style="display: none;">
                                   
                                          <c:forEach items="${saleorderGoods.goodsStockList}" var="gs">
	                                         	${gs.wareHouseName}库存数量：${gs.goodsNum}<br>
	                                         </c:forEach>
                                    </div>
                           			 </div>
                                    
                                    
								</td>
								<td></td>
								<td>
									<c:if test="${saleorderGoods.deliveryDirect eq 0}">普发</c:if>
									<c:if test="${saleorderGoods.deliveryDirect eq 1}">
									 	
                                       <div class="customername pos_rel">
		                                     <span class="warning-color1">
													直发
		                                      </span>                                    
                                             	<i class="iconbluesigh "></i>                                   
                                   <div class="pos_abs customernameshow" style="display: none;">
                                        	直发原因：${saleorderGoods.deliveryDirectComments}
                                    </div>
                           			 </div>
                                    </c:if>
                                    
								</td>
								<td>${saleorderGoods.num-saleorderGoods.buyNum}</td>
								<td>${saleorderGoods.buyNum}</td>
								<td>${saleorderGoods.insideComments}</td>
								<td>${saleorderGoods.goodsComments}</td>
								<td>${saleorderGoods.terminalTraderName}</td>
								<td>${saleorderGoods.salesArea}</td>
								<td><date:date value ="${saleorderGoods.ignoreTime}"/></td>
								<td>${saleorderGoods.ignoreName}</td>
							</tr>
						</c:forEach>
						<c:if test="${empty list}">
	      					<!-- 查询无结果弹出 -->
			          		<tr>
			          			<td colspan='21'>查询无结果！请尝试使用其他搜索条件。</td>
			          		</tr>
		       			</c:if>
					</tbody>
				</table>
				
			</div>
		</div>
       	
       	<div>
      <div class='parts'>
		 <div class="table-friend-tip f_left">
		           友情提示：<br/> 1、备货单、销售订单不允许同时被选择；
		           <br/> 2、直发与普发不允许同时被选择；
		           <br/> 3、直发仅允许选择同订单产品；
		           <br/>4、异常状态、已完结、已关闭的订单的采购需求不允许重新采购；
		</div>
      <tags:page page="${page}"/>
      <div class='clear'></div>
       <div class="table-buttons special-table-bottom">
          <button type="button" class="bt-bg-style bg-light-green bt-small mr10" id="buyorder">生成采购订单</button>
          <span style="display:none;">
				<!-- 弹框 -->
				<div class="title-click nobor addtitle2" id="addpf"></div>
		  </span>
          <button type="button" class="bt-bg-style bg-light-green bt-small mr10" id="addbuy">加入采购订单</button>
          <span style="display:none;"><div class="title-click nobor addtitle2" id="addbuyorder"></div></span>
      </div>
	</div>
		
       	</div>
	</div>
	
	
 </div>
</body>

</html>

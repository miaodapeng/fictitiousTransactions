<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="加入采购订单" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/buyorder/add_saleorderToBuyorder.js?rnd=<%=Math.random()%>'></script>
	<form action="" method="post" id="myform">
	<div class="content"> 
		<div class="parts content1 ">
			<div class="formtitle mt10">选择采购订单</div>
				<ul class="payplan" style="margin-bottom:2px;">
					<li>
						<div class="infor_name">
							<span>*</span>
							<label>请输入采购单号</label>
						</div>
						<div class="f_left">
							<div class="inputfloat">
								<input type="text" placeholder="请输入采购单号" class="input-largest" name="searchNo" id="searchNo" />
								<input type="hidden" id="validStatus"/>
								<input type="hidden" id="status"/>
								<input type="hidden" id="lockedStatus"/>
								<input type="hidden" id="verifyStatus"/>
								<input type="hidden" id="deliveryDirect"/>
								<input type="hidden" id="saleorderNoType"/>
								<input type="hidden" id="buyorderId" name="buyorderId" />
							</div>
							<div id="searchNoError"></div>
						</div>
					</li>
				</ul>
		</div>
		<!-- ----------------------------------产品信息 ------------------------------------- -->
		<div class='parts'>
		<input type="hidden" name="beforeParams" value=""/>
	 	<c:forEach var="gv" items="${gvList}" varStatus="num">
	 	
            <table class="table table-style7">
                <thead>
                    <tr>
                    	<th class="wid4">序号</th>
                    	<th class="wid8">订货号</th>
                        <th class="wid10">产品名称</th>
						<th class="wid8">品牌</th>
						<th class="wid8">型号</th>
						<th class="wid9">物料编码</th>
						<th class="wid6">单位</th>
						<th class="wid6">产品级别</th>
						<th class="wid11">可用库存 /库存量</th>
						<th class="wid8">采购数量</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                    
                    	<td>${num.count}</td>
                    	<td>${newSkuInfosMap[gv.sku].SKU_NO}</td>
                    	<td class="text-left">
		                    <span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                    "link":"./goods/goods/viewbaseinfo.do?goodsId=${gv.goodsId}","title":"产品信息"}'>${newSkuInfosMap[gv.sku].SHOW_NAME}</span>
                        </td>
                        <td>${newSkuInfosMap[gv.sku].BRAND_NAME}</td>
                        <td>${newSkuInfosMap[gv.sku].MODEL}</td>
                        <td>${newSkuInfosMap[gv.sku].MATERIAL_CODE}</td>
                        <td>${newSkuInfosMap[gv.sku].UNIT_NAME}</td>
                        <td>${newSkuInfosMap[gv.sku].GOODS_LEVEL_NAME}</td>
                        <td>${gv.canUseGoodsStock > 0 ? gv.canUseGoodsStock : 0}/${gv.inventory}</td>
                        <td><span alt="${gv.goodsId}" class="buyNum">${gv.proBuySum}</span>
                        	<input type="hidden" name="buySum" alt="${gv.goodsId}" value="${gv.sku}|${gv.goodsId}|${gv.proBuySum}"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="10" class="table-container ">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th class="wid8">关联单号</th>
                                        <th class="wid10 ">申请人</th>
                                        <th class="wid15 ">数量</th>
                                        <th class="wid11 ">销售价</th>
                                        <th class="wid10">销售货期</th>
                                        <th class="wid12">内部备注</th>
                                        <th class="wid12">产品备注</th>
                                        <th class="wid12 ">终端客户名称</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach var="saleorderGoods" items="${gv.sgvList}">
	                                    <tr>
											<td>${saleorderGoods.saleorderNo}
												<input type="hidden" name="saleorderNo" value="${saleorderGoods.saleorderNo}">
												<input type="hidden" name="saleorderNos" value="${gv.goodsId}|${saleorderGoods.saleorderNo}">
												<input type="hidden" name="deliveryDirect" value="${saleorderGoods.deliveryDirect}">
											</td>
											<td>${saleorderGoods.applicantName}</td>
											<td>
											<%-- <input type="hidden" name="dbBuyNum" value="${gv.sku}|${saleorderGoods.saleorderGoodsId}|${saleorderGoods.num - saleorderGoods.saleAfterNum - saleorderGoods.buyNum + saleorderGoods.buyAfterNum}|${saleorderGoods.saleorderNo}">
											<input type="text" style="width: 60px" class='mr4' alt1="${saleorderGoods.goodsId}" name="saleorderGoodsNum" 
												value="${saleorderGoods.num - saleorderGoods.saleAfterNum - saleorderGoods.buyNum + saleorderGoods.buyAfterNum}"
												onchange="addNum(this,${saleorderGoods.num - saleorderGoods.saleAfterNum - saleorderGoods.buyNum + saleorderGoods.buyAfterNum},${saleorderGoods.saleorderGoodsId},'${gv.sku}','${saleorderGoods.saleorderNo}')">/
											${saleorderGoods.num - saleorderGoods.saleAfterNum - saleorderGoods.buyNum + saleorderGoods.buyAfterNum} --%>
												<c:if test="${saleorderGoods.deliveryDirect eq 1}">
													<input type="hidden" name="dbBuyNum" 
														value="${gv.sku}|${saleorderGoods.saleorderGoodsId}|${saleorderGoods.num-saleorderGoods.buyNum}|${saleorderGoods.saleorderNo}">
													<input type="text" style="width: 60px;" class='mr4 warning-color1' alt1="${saleorderGoods.goodsId}" name="saleorderGoodsNum"
														value="${saleorderGoods.num-saleorderGoods.buyNum}" autocomplete="off"
														onchange="addNum(this,${saleorderGoods.num-saleorderGoods.buyNum},${saleorderGoods.saleorderGoodsId},'${gv.sku}','${saleorderGoods.saleorderNo}')">/
													${saleorderGoods.num - saleorderGoods.buyNum}
												</c:if>
												<c:if test="${saleorderGoods.deliveryDirect eq 0}">
													<input type="hidden" name="dbBuyNum" 
														value="${gv.sku}|${saleorderGoods.saleorderGoodsId}|${saleorderGoods.needBuyNum}|${saleorderGoods.saleorderNo}">
													<input type="text" style="width: 60px;" class='mr4 warning-color1' alt1="${saleorderGoods.goodsId}" name="saleorderGoodsNum"
														value="${saleorderGoods.needBuyNum}" autocomplete="off"
														onchange="addNum(this,${saleorderGoods.needBuyNum},${saleorderGoods.saleorderGoodsId},'${gv.sku}','${saleorderGoods.saleorderNo}')">/
													${saleorderGoods.needBuyNum}
												</c:if>
											</td>
											<td><fmt:formatNumber type="number" value="${saleorderGoods.price}" pattern="0.00" maxFractionDigits="2" /></td>
											<td>${saleorderGoods.deliveryCycle}</td>
											<td><span class="warning-color1">${saleorderGoods.insideComments}</span></td>
											<td>${saleorderGoods.goodsComments}</td>
											<td>${saleorderGoods.terminalTraderName}</td>
	                                    </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
        </c:forEach>
         <div class="tablelastline">
               	 总件数<span class="warning-color1" id="zSum">${sum}</span>
            </div>
        <div class="table-friend-tip">
			友情提示<br/>
			1、不允许添加到非待采购订单中（即不允许添加到已生效、已完结、已关闭、锁定、审核中等）的订单中；<br/>
			2、不允许将备货单、销售订单的商品添加到同一采购单；<br/>
			3、不允许将直发与普发的商品添加到同一采购单；<br/>
			4、不允许将非同一销售订单的直发商品添加到同一采购单；<br/>
			5、同订货号产品添加到同一采购单以增加数量的方式添加；<br/>
			6、添加商品不改变目标采购单的权限；<br/>
        </div>
        </div>
		<div class="tcenter mb10">
	         <button type="button" class="bt-bg-style bg-light-green bt-small mr10" id="sub">确定</button>
	     </div>
	     </div>
		</form>
	</body>
	
</html>
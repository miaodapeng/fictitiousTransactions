
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="待采购订单" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src='<%= basePath %>static/js/order/buyorder/index_pendingPurchase.js?rnd=<%=Math.random()%>'></script>
	<div class="main-container">
		<div class="list-pages-search">
			<form method="post" id="search" action="<%= basePath %>/order/buyorder/indexPendingPurchase.do">
				<ul>
					<li>
						<label class="infor_name">产品名称</label>
						<input type="text" class="input-middle" name="goodsName" id="goodsName" value="${goodsVo.goodsName}"/>
					</li>
					<li>
						<label class="infor_name">品牌</label>
						<input type="text" class="input-middle" name="brandName" id="brandName" value="${goodsVo.brandName}"/>
					</li>
					<li>
						<label class="infor_name">型号</label>
						<input type="text" class="input-middle" name="model" id="model" value="${goodsVo.model}"/>
					</li>
					<li>
						<label class="infor_name">订货号</label>
						<input type="text" class="input-middle" name="sku" id="sku" value="${goodsVo.sku}"/>
					</li>
					<li>
						<label class="infor_name">单号</label>
						<input type="text" class="input-middle" name="saleorderNo" id="saleorderNo" value="${goodsVo.saleorderNo}"/>
					</li>
					<li>
						<label class="infor_name">申请人</label>
						<select class="input-middle" name="userId" id="">
							<option value="">全部</option>
							<c:forEach items="${applicantList}" var="user">
								<option value="${user.userId}" <c:if test="${goodsVo.userId eq user.userId}">selected="selected"</c:if>>${user.username}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<label class="infor_name">产品部门</label>
						<select class="input-middle" name="proOrgtId" id="" onchange="changeOrg(this);">
							<option value="">全部</option>
							<c:forEach items="${productOrgList}" var="org" varStatus="sttaus">
								<%--<c:if test="${sttaus.count gt 1}">--%>
									<option value="${org.orgId}" <c:if test="${goodsVo.proOrgtId eq org.orgId}">selected="selected"</c:if>>${org.orgName}</option>
								<%--</c:if>--%>
							</c:forEach>
						</select>
					</li>
					<li>
						<label class="infor_name">产品归属</label>
						<select class="input-middle" name="proUserId" id="proUserId">
							<option value="">全部</option>
							<c:forEach items="${productUserList}" var="user">
								<option value="${user.userId}" <c:if test="${goodsVo.proUserId eq user.userId}">selected="selected"</c:if>>${user.username}</option>
							</c:forEach>
						</select>
					</li>
					<li>
						<label class="infor_name">是否直发</label>
						<select class="input-middle" name="deliveryDirect" id="deliveryDirect">
							<option value="-1">全部</option>
							<option <c:if test="${goodsVo.deliveryDirect eq 0}">selected</c:if> value="0">普发</option>
							<option <c:if test="${goodsVo.deliveryDirect eq 1}">selected</c:if> value="1">直发</option>
						</select>
					</li>
					<li>
						<label class="infor_name">订单类型</label>
						<!-- 订单类型:0销售订单/2备货订单/3订货订单/4经销商订单 -->
						<select class="input-middle" name="orderType" id="">
							<!-- 对应库里ORDER_TYPE 0,3,4 -->
							<option <c:if test="${goodsVo.orderType eq 1}">selected</c:if> value="1">非备货单</option>
							<!-- 对应库里ORDER_TYPE 2 -->
							<option <c:if test="${goodsVo.orderType eq 2}">selected</c:if> value="2">备货单</option>
							<!-- 订货单，JX订单都归属订货单 对应库里ORDER_TYPE 4 -->
							<option <c:if test="${goodsVo.orderType eq 4}">selected</c:if> value="4">经销商订单</option>
							<option <c:if test="${goodsVo.orderType eq 5}">selected</c:if> value="5">耗材商城订单</option>
							<option <c:if test="${goodsVo.orderType eq -1}">selected</c:if> value="-1">全部</option>
						</select>
					</li>
				</ul>
				<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				</div>
			</form>
		</div>
	 <div class='parts'>
	 	<c:forEach var="gv" items="${list}" >
	 		<table class="table table-style7">
                <thead>
                    <tr>
                    	<th class="wid10">订单号</th>
                        <th class="wid8">申请人</th>
						<th class="wid15">可采购时间</th>
						<th class="wid15">终端名称</th>
						<th class="wid15">销售区域</th>
						<th class="wid15">订单内部备注</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                    	<td>${gv.saleorderNo}</td>
                    	<td>${gv.applicantName}</td>
                        <c:if test="${gv.orderType eq 2}">
                        	<td>--</td>
                        	<td>--</td>
                        	<td>--</td>
                        	<td>--</td>
                        </c:if>
                       <c:if test="${gv.orderType ne 2}">
                       		<td><date:date value ="${gv.satisfyDeliveryTime}"/></td>
                        	<td>${gv.terminalTraderName}</td>
	                        <td>${gv.salesArea}</td>
	                        <td>${gv.comments}</td>
                        </c:if>
                    </tr>
                    <tr>
                        <td colspan="6" class="table-container ">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th class="wid3">选择</th>
                                        <th class="wid5">订货号</th>
                                        <th class="wid15">产品名称</th>
                                        <th class="wid10">品牌/型号</th>
                                        <th class="wid5">物料编码</th>
                                        <th class="wid4">单位</th>
                                        <th class="wid6">产品级别</th>
                                        <%--<th class="wid8">产品负责人</th>--%>
										<th class="wid8">归属产品经理</th>
										<th class="wid8">归属产品助理</th>
                                        <th class="wid7">可用库存/库存量</th>
                                        <th class="wid7">在途数量</th>
                                        <th class="wid5">是否直发</th>
                                        <th class="wid5">需采数量</th>
                                        <th class="wid5">商品总数</th>
                                        <th class="wid8">内部备注</th>
                                        <th class="wid8">产品备注</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach var="sgv" items="${gv.sgvList}" varStatus="num">
	                                    <tr>
	                                        <td>
	                                        	<c:if test="${sgv.lockedStatus eq 0}">
	                                        		<input type="checkbox" name="oneSelect" value="" autocomplete="off" onclick="oneSelect(this);" alt="${sgv.saleorderId}"/>
	                                        	</c:if>
												<c:if test="${sgv.lockedStatus eq 1}">
													<span class="warning-color1 ">锁</span>
												</c:if>
												<input type="hidden" name="saleorderGoodId" value="${sgv.goodsId}|${sgv.saleorderGoodsId}"/>
												<input type="hidden" name="goodsId" value="${sgv.goodsId}"/>
												<c:if test="${sgv.deliveryDirect eq 1}">
													<input type="hidden" name="noBuyNum" value="${sgv.num-(sgv.buyNum > 0 ? sgv.buyNum : 0)}" alt3="${sgv.saleorderId}">
												</c:if>
												<c:if test="${sgv.deliveryDirect eq 0}">
													<input type="hidden" name="noBuyNum" value="${sgv.needBuyNum}" alt3="${sgv.saleorderId}">
												</c:if>
												<input type="hidden" name="saleorderNo" value="${gv.saleorderNo}">
												<input type="hidden" name="saleorderId" value="${gv.saleorderId}">
												<input type="hidden" name="deliveryDirect" value="${sgv.deliveryDirect}">
												
											</td>
											<td>${sgv.sku}</td>
											<td>
												<span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                    						"link":"./goods/goods/viewbaseinfo.do?goodsId=${sgv.goodsId}","title":"产品信息"}'>${newSkuInfosMap[sgv.sku].SHOW_NAME}</span>
		                    				</td>
											<td>${newSkuInfosMap[sgv.sku].BRAND_NAME}/${newSkuInfosMap[sgv.sku].MODEL}</td>
											<td>${newSkuInfosMap[sgv.sku].MATERIAL_CODE}</td>
											<td>${newSkuInfosMap[sgv.sku].UNIT_NAME}</td>
											<td>${newSkuInfosMap[sgv.sku].GOODS_LEVEL_NAME}</td>
											<%--<td>
					                        	<c:if test="${not empty sgv.userList }">
													<c:forEach items="${sgv.userList }" var="user" varStatus="st">
														${user.username } <c:if test="${st.count != sgv.userList.size() }">、</c:if>
													</c:forEach>
												</c:if>
											</td>--%>
											<td>${sgv.assignmentManagerId}</td>
											<td>${sgv.assignmentAssistantId}</td>
											<td>${sgv.canUseGoodsStock > 0 ? sgv.canUseGoodsStock : 0}/${sgv.goodsStock == null ? 0 : sgv.goodsStock}</td>
											<td>${sgv.onWayNum > 0 ? sgv.onWayNum : 0}</td>
											<td>
												<c:if test="${sgv.deliveryDirect eq 0}">普发</c:if>
												<c:if test="${sgv.deliveryDirect eq 1}">
												 	<div class="customername pos_rel ">
		                                                <div>
		                                                    <span class="warning-color1">
																直发
		                                                    </span>
		                                                    <i class="iconbluesigh "></i>
		                                                </div>
		                                                <div class="pos_abs customernameshow " style="display: none; ">
															${sgv.deliveryDirectComments}
		                                                </div>
	                                            	</div>
                                            	</c:if>
											</td>
											<td>
												<c:if test="${sgv.deliveryDirect eq 0}">
												${sgv.needBuyNum}
												</c:if>
												<c:if test="${sgv.deliveryDirect eq 1}">
												${sgv.num-(sgv.buyNum > 0 ? sgv.buyNum : 0)}
												</c:if>
											</td>
											<td>${sgv.num}</td>
											<td><span class="warning-color1">${sgv.insideComments}</span></td>
											<td>${sgv.goodsComments}</td>
	                                    </tr>
                                    </c:forEach>
                                    <tr>
                                        <td colspan="16" class="text-left">
                                            <input type="checkbox" style="margin-top: 1px" autocomplete="off" name="oneAllSelect" onclick="oneAllSelect(this);" alt2="${gv.saleorderId}">
                                            <span class="mr20">全选</span>
                                            <span>共</span>
                                            <span class="warning-color1 ">${gv.proBuySum}</span>
                                            <span>件</span>
                                            <span>已选择</span>
                                            <span alt1="${gv.saleorderId}" class="warning-color1 oneTableSelectNum">0</span>
                                            <span>件</span>
                                            <span class="warning-color1 ml4 cursor-pointer bt-middle bt-border-style border-red" onclick="ignore(${gv.saleorderId});">忽略</span>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
            <%-- <table class="table table-style7">
                <thead>
                    <tr>
                    	<th class="wid10">订货号</th>
                        <th class="">产品名称</th>
						<th class="">品牌</th>
						<th class="">型号</th>
						<th class="">物料编码</th>
						<th class="">单位</th>
						<th class="">产品级别</th>
						<th class="">产品归属</th>
						<th class="">可用库存 /库存量</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                    	<td>${gv.sku}</td>
                    	<td class="text-left">
		                    <span class="font-blue cursor-pointer addtitle" 
		                    	tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${gv.goodsId}","title":"产品信息"}'>${gv.goodsName}</span> 
                        </td>
                        <td>${gv.brandName}</td>
                        <td>${gv.model}</td>
                        <td>${gv.materialCode}</td>
                        <td>${gv.unitName}</td>
                        <td>${gv.goodsLevelName}</td>
                        <td>
                        	<c:if test="${not empty gv.userList }">
								<c:forEach items="${gv.userList }" var="user" varStatus="st">
									${user.username } <c:if test="${st.count != gv.userList.size() }">、</c:if>
								</c:forEach>
							</c:if>
						</td>
                        <td>${gv.canUseGoodsStock > 0 ? gv.canUseGoodsStock : 0}/${gv.goodsStock == null ? 0 : gv.goodsStock}</td>
                    </tr>
                    <tr>
                        <td colspan="9" class="table-container ">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th class="wid6">选择</th>
                                        <th class="wid12 ">单号</th>
                                        <th class="wid10 ">申请人</th>
                                        <th class="wid11 ">可采购时间</th>
                                        <th class="wid6 ">是否直发</th>
                                        <th class="wid8 ">需采数量</th>
                                        <th class="wid8 ">商品总数</th>
                                        <th>内部备注</th>
                                        <th>产品备注</th>
                                        <th class="wid11 ">终端名称</th>
                                        <th>销售区域</th>
                                    </tr>
                                </thead>
                                <tbody>
                                	<c:forEach var="saleorder" items="${gv.sgvList}" varStatus="num">
	                                    <tr>
	                                        <td>
												<input type="checkbox" name="oneSelect" value="" autocomplete="off" onclick="oneSelect(this);" alt="${gv.goodsId}"/>
												<input type="hidden" name="saleorderGoodId" value="${gv.goodsId}|${saleorder.saleorderGoodsId}"/>
												<input type="hidden" name="goodsId" value="${gv.goodsId}"/>
												<input type="hidden" name="noBuyNum" value="${saleorder.num-saleorder.buyNum}" alt3="${gv.goodsId}">
												<input type="hidden" name="saleorderNo" value="${saleorder.saleorderNo}">
												<input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
												<input type="hidden" name="deliveryDirect" value="${saleorder.deliveryDirect}">
												
											</td>
											<td>${saleorder.saleorderNo}</td>
											<td>${saleorder.applicantName}</td>
											<td><date:date value =""/></td>
											<td>
												<c:if test="${saleorder.deliveryDirect eq 0}">普发</c:if>
												<c:if test="${saleorder.deliveryDirect eq 1}">
												 	<div class="customername pos_rel ">
		                                                <div>
		                                                    <span class="warning-color1">
																直发
		                                                    </span>
		                                                    <i class="iconbluesigh "></i>
		                                                </div>
		                                                <div class="pos_abs customernameshow " style="display: none; ">
															${saleorder.deliveryDirectComments}
		                                                </div>
	                                            	</div>
                                            	</c:if>
											</td>
											<td>
												${saleorder.num-saleorder.buyNum}
											</td>
											<td>${saleorder.num}</td>
											<td><span class="warning-color1">${saleorder.insideComments}</span></td>
											<td>${saleorder.goodsComments}</td>
											<td>${saleorder.terminalTraderName}</td>
											<td>${saleorder.traderArea}</td>
	                                    </tr>
                                    </c:forEach>
                                    <tr>
                                        <td colspan="11" class="text-left">
                                            <input type="checkbox" style="margin-top: 1px" autocomplete="off" 
                                            		name="oneAllSelect" onclick="oneAllSelect(this);" alt2="${gv.goodsId}">
                                            <span class="mr20">全选</span>
                                            <span>共</span>
                                            <span class="warning-color1 ">${gv.proBuySum}</span>
                                            <span>件</span>
                                            <span>已选择</span>
                                            <span alt1="${gv.goodsId}" class="warning-color1 oneTableSelectNum">0</span>
                                            <span>件</span>
                                            <span class="warning-color1 ml4 cursor-pointer" onclick="ignore(${gv.goodsId});">忽略</span>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table> --%>
        </c:forEach>
         <c:if test="${not empty list }">
            <div class="tablelastline">
	            <input type="checkbox" autocomplete="off" name="allSelect" onclick="allSelect(this);">
	            <span class="mr20">全选</span>当前全部待采购商品共<span class="warning-color1">${buySum}</span>件，已选择<span class="warning-color1" id="selected">0</span>件
            </div>
       </c:if>
       <c:if test="${empty list }">
       		 <table class="table table-style7">
                <thead>
                    <tr>
                    	<th class="wid10">订单号</th>
                        <th class="wid8">申请人</th>
						<th class="wid15">可采购时间</th>
						<th class="wid15">终端名称</th>
						<th class="wid15">销售区域</th>
						<th class="wid15">订单内部备注</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                    	<td colspan='6'>未搜索到满足条件的结果！请尝试使用其他搜索条件。</td>
                    </tr>
                </tbody>
             </table>    
       </c:if>
            <div class="table-friend-tip">
                友情提示 <br/>1、备货单、销售订单不允许同时被选择；<br/> 2、直发与普发不允许同时被选择； <br/>3、直发仅允许选择同订单产品；
            </div>
            <div class="table-buttons">
	            <button type="button" class="bt-bg-style bg-light-green bt-small mr10" id="buyorder">生成采购订单</button>
	            <span style="display:none;"><div class="title-click nobor addtitle2" id="addpf"></div></span>
	            <button type="button" class="bt-bg-style bg-light-green bt-small mr10" id="addbuy">加入采购订单</button>
	            <span style="display:none;"><div class="title-click nobor addtitle2" id="addbuyorder"></div></span>
	        </div>
        </div>
        <tags:page page="${page}"/>
	</div>
</body>

</html>

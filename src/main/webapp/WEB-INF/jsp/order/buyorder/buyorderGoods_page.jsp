<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../common/common.jsp"%>
 		 <div class="parts table-style77">
		<div class="title-container mb10 title-container-blue">
            <div class="table-title nobor">产品信息</div>
        </div>
		<c:forEach var="bgv" items="${buyorderVo.buyorderGoodsVoList}" varStatus="num">
		 
	            <table class="table table-style7">
	                <thead>
	                    <tr>
	                    	<th class="wid4">序号</th>
	                        <th class="wid10">产品名称</th>
							<th class="wid8">品牌</th>
							<th class="wid8">型号</th>
							<th class="wid9">物料编码</th>
							<th class="wid8">采购数量</th>
							<th class="wid6">单位</th>
							<th class="wid10">单价</th>
							<th class="wid8">总额</th>
							<th class="wid8">到货数量</th>
							<th class="wid8">收票数量</th>
							<th class="wid25">采购备注</th>
							
	                    </tr>
	                </thead>
	                <tbody>
	                    <tr>
	                    	<input type="hidden" name="buyorderGoodsId" value="${bgv.buyorderGoodsId}"/>
	                    	<td>${num.count}</td>
	                    	<td class="text-left">
			                    <div class="customername pos_rel">
		                    		<span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${bgv.goodsId}","title":"产品信息"}'>${bgv.goodsName}
		                    				&nbsp;<i class="iconbluemouth contorlIcon"></i><br/></span> 
                    				<div class="pos_abs customernameshow">
									物料编码： ${bgv.materialCode}<br> 
									注册证号： ${bgv.registrationNumber}<br>
									管理类别：  ${bgv.manageCategoryName}<br>
									产品归属：<c:if test="${not empty bgv.userList }">
												<c:forEach items="${bgv.userList }" var="user"
													varStatus="st">
											${user.username } <c:if
														test="${st.count != bgv.userList.size() }">、</c:if>
												</c:forEach>
											</c:if>  <br> 
									库存：${bgv.goodsStock}<br> 
									可用库存：${bgv.canUseGoodsStock > 0 ? bgv.canUseGoodsStock : 0}<br> 
									订单占用：${bgv.orderOccupy}<br>
									</div>
									<div>${bgv.sku}</div>
								</div>
	                        </td>
	                        <td>${bgv.brandName}</td>
	                        <td>${bgv.model}</td>
	                        <td>${bgv.materialCode}</td>
	                        <td>
	                        	<div class="customername pos_rel">
		                        	<span alt="${bgv.goodsId}">
		                        		${bgv.num - bgv.afterSaleUpLimitNum}
		                        		<i class="iconredsigh ml4 contorlIcon"></i>
		                        	</span>
		                        	<div class="pos_abs customernameshow">原值：${bgv.num}</div>
	                        	</div>
	                        	<input type="hidden" name="buySum" alt="${bgv.buyorderGoodsId}" value="${bgv.buyorderGoodsId}|${bgv.num}"/>
	                        </td>
	                        <td>${bgv.unitName}</td>
	                        <td><fmt:formatNumber type="number" value="${bgv.price}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td>
	                        	<span class="oneAllMoney" alt="${bgv.buyorderGoodsId}"><fmt:formatNumber type="number" value="${bgv.oneBuyorderGoodsAmount}" pattern="0.00" maxFractionDigits="2" /></span>
	                        </td>
	                        <td class="warning-color1">${bgv.arrivalNum}</td>
	                        <td>
	                        	<div class="customername pos_rel ">
	                        	<div>
		                        ${bgv.invoiceNum}
		                        <c:if test="${bgv.invoiceNum > 0}">
		                        	<i class="iconbluesigh "></i>
		                        </c:if>
		                        
		                        </div>
		                        <div class="pos_abs customernameshow " style="display: none; ">
									<c:forEach items="${bgv.invoiceList}" var="inv">
									发票号：${inv.invoiceNo }</br>
									票种：
			                        	<c:if test="${inv.invoiceType eq 429}">17%增值税专用发票</c:if>
			                        	<c:if test="${inv.invoiceType eq 430}">17%增值税普通发票</c:if>
			                        	<c:if test="${inv.invoiceType eq 681}">16%增值税普通发票</c:if>
			                        	<c:if test="${inv.invoiceType eq 682}">16%增值税专用发票</c:if>
			                        	
			                        	<c:if test="${inv.invoiceType eq 971}">13%增值税普通发票</c:if>
			                        	<c:if test="${inv.invoiceType eq 972}">13%增值税专用发票</c:if>
			                        	<c:if test="${inv.invoiceType eq 683}">6%增值税普通发票</c:if>
			                        	<c:if test="${inv.invoiceType eq 684}">6%增值税专用发票</c:if>
			                        	<c:if test="${inv.invoiceType eq 685}">3%增值税普通发票</c:if>
			                        	<c:if test="${inv.invoiceType eq 686}">3%增值税专用发票</c:if>
			                        	<c:if test="${inv.invoiceType eq 687}">0%增值税普通发票</c:if>
			                        	<c:if test="${inv.invoiceType eq 688}">0%增值税专用发票</c:if>
									</br>
									红蓝字：
										<c:choose>
										<c:when test="${inv.colorType eq 1}">
											<c:choose>
												<c:when test="${inv.isEnable eq 0}">
													<span style="color: red">红字作废</span>
												</c:when>
												<c:otherwise>
													<span style="color: red">红字有效</span>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${inv.isEnable eq 0}">
													<span style="color: red">蓝字作废</span>
												</c:when>
												<c:otherwise>
													蓝字有效
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose></br>
									金额：${inv.amount }</br>
									开票数量：${inv.num}</br>
									日期：<date:date value="${inv.addTime}" />
									
									</c:forEach>
                                </div>
                                </div>
	                        </td>
	                        <td>${bgv.insideComments}</td>
	                    </tr>
	                    <c:if test="${bgv.goodsId ne 127063}"> </c:if>
	                    <tr>
	                        <td colspan="12" class="table-container ">
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
	                                	<c:forEach var="saleorderGoods" items="${bgv.saleorderGoodsVoList}">
		                                    <tr>
												<td>
													<c:if test="${saleorderGoods.orderType ne 2}">
														<a class="addtitle" href="javascript:void(0);" 
														tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","title":"订单信息",
														"link":"./order/saleorder/view.do?saleorderId=${saleorderGoods.saleorderId}"}'>${saleorderGoods.saleorderNo}</a>
													</c:if>
													<c:if test="${saleorderGoods.orderType eq 2}">
														<a class="addtitle" href="javascript:void(0);" 
														tabTitle='{"num":"viewsaleorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","title":"订单信息",
														"link":"./order/saleorder/viewBhSaleorder.do?saleorderId=${saleorderGoods.saleorderId}"}'>${saleorderGoods.saleorderNo}</a>
													</c:if>
												</td>
												<td>${saleorderGoods.applicantName}</td>
												<td>
													${saleorderGoods.saleBuyNum}/${saleorderGoods.num - saleorderGoods.saleAfterNum - saleorderGoods.buyNum + saleorderGoods.buyAfterNum+saleorderGoods.saleBuyNum}
												</td>
												<td>${saleorderGoods.price}</td>
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
               	 总件数<span class="warning-color1" id="zSum">${buyorderVo.buyorderSum}</span>，总金额<span class="warning-color1" id="zMoney"><fmt:formatNumber type="number" value="${buyorderVo.buyorderAmount}" pattern="0.00" maxFractionDigits="2" /></span>
            </div>
        
		</div>
<%@ include file="../../common/footer.jsp"%>
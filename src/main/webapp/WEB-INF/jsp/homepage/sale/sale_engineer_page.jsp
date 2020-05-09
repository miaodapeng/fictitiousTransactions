<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="今日任务" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/home/page/home_page.js?rnd=<%=Math.random()%>'></script>
	<div class="main-container">
        <%@ include file="sale_engineer_tag.jsp"%>
        <!-- 
        <div class="showing-card">
            <ul>
                <li>
                    <div class="card-container">
                        <div class="card-title blue-title">
                            今日待办
                        </div>
                        <div class="card-content blue-content">
                            <ul>
	                            <c:if test="${not empty saleEngineerDataVo && not empty saleEngineerDataVo.todayDoList }">
	                            	<c:forEach items="${saleEngineerDataVo.todayDoList}" var="num" varStatus="status">
	                            		<c:if test="${status.count eq 1}">
	                            			<li>
			                                    <span>沟通客户</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 2}">
	                            			<li>
			                                    <span>完成</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 3}">
	                            			<li>
			                                    <span>报价跟单</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 4}">
	                            			<li>
			                                    <span>完成</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 5}">
	                            			<li>
			                                    <span>订单跟单</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 6}">
	                            			<li>
			                                    <span>完成</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            	</c:forEach>
	                            </c:if>
                            </ul>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="card-container month-data">
                        <div class="card-title yellow-title">
                            本月数据
                        </div>
                        <div class="card-content yellow-content">
                            <ul>
                            	<c:if test="${not empty saleEngineerDataVo && not empty saleEngineerDataVo.thisMonthDataList }">
	                            	<c:forEach items="${saleEngineerDataVo.thisMonthDataList}" var="num" varStatus="status">
	                            		<c:if test="${status.count eq 1}">
	                            			<li>
			                                    <span >销售目标</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 2}">
	                            			<li>
			                                    <span >到款额</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 3}">
	                            			<li>
			                                    <span >商机数量</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 4}">
	                            			<li>
			                                    <span>报价数量</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 5}">
	                            			<li>
			                                    <span>订单数量</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 6}">
	                            			<li>
			                                    <span>新增合作客户</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 7}">
	                            			<li>
			                                    <span>报价金额</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 8}">
	                            			<li>
			                                    <span>订单金额</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            	</c:forEach>
	                            </c:if>
                            </ul>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="card-container personal-data">
                        <div class="card-title green-title">
                            销售个人数据
                        </div>
                        <div class="card-content green-content">
                            <ul>
                                <c:if test="${not empty saleEngineerDataVo && not empty saleEngineerDataVo.salePersonalList }">
	                            	<c:forEach items="${saleEngineerDataVo.salePersonalList}" var="num" varStatus="status">
	                            		<c:if test="${status.count eq 1}">
	                            			<li>
			                                    <span>当前客户总数</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 2}">
	                            			<li>
			                                    <span>历史成交客户</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 3}">
	                            			<li>
			                                    <span>多次成交占比</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 4}">
	                            			<li>
			                                    <span>客单价</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            		<c:if test="${status.count eq 5}">
	                            			<li>
			                                    <span>到款总额</span>
			                                    <span class="num">${num}</span>
			                                </li>
	                            		</c:if>
	                            	</c:forEach>
	                            </c:if>
                            </ul>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
         -->
        <div class="daily-event">
            <ul>
            	<c:if test="${not empty saleEngineerDataVo && not empty saleEngineerDataVo.traderCustomerVoList }">
	               	<c:forEach items="${saleEngineerDataVo.traderCustomerVoList}" var="tc" >
	               		<li>
		                   <div class="daily-title">客户沟通</div>
		                    <div class="daily-content">
		                        <div class="daily-one">
		                            <div class="daily-icon">
		                                <i class="iconcustmer"></i>
		                            </div>
		                            <div class="daily-detail">
		                                <p class="detail-title">
		                                <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewcustomer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
													"link":"./trader/customer/baseinfo.do?traderId=${tc.traderId}","title":"客户信息"}'>${tc.traderName}</a>
		                                </p>
		                                <p>
		                                    <span>
		                                    	<c:if test="${tc.customerNature eq 465 }">分销</c:if>
		                                    	<c:if test="${tc.customerNature eq 466 }">终端</c:if>
		                                    </span>
		                                    <span class="font-grey6 ml10">交易次数 </span> <span>${tc.buyCount} </span>
		                                    <span class="font-grey6 ml10">交易金额 </span> <span>${tc.buyMoney/10000} 万 </span>
		                                </p>
		                            </div>
		                        </div>
		                        <div class="daily-two">
		                            <span>${tc.traderContactName} </span>
		                            <span class="tel">
		                                <i class="icon-tel"></i>
		                                <span class="tel-num">
			                                <c:if test="${tc.traderContactMobile == null}">${tc.phone}</c:if>
			                                <c:if test="${tc.traderContactMobile != null}">${tc.traderContactMobile}</c:if>
		                                </span>
		                            </span>
		                        </div>
		                        <div class="daily-three">
		                            <span class="mr4">上次联系</span>
		                            <span class="mr4"> <date:date value ="${tc.addTime}"/></span>
		                            <span>${tc.personal}</span>
		                        </div>
		                        <div class="daily-four mt7">
		                            <c:if test="${not empty tc.tag }">
		                            <ul>
										<c:forEach items="${tc.tag }" var="tag">
											<li class="bluetag">${tag.tagName}</li>
										</c:forEach>
										</ul>
									</c:if>
		                        </div>
		                    </div>
		                </li>
	               	</c:forEach>
	            </c:if>
	            <c:if test="${empty saleEngineerDataVo || empty saleEngineerDataVo.traderCustomerVoList }">
	            	<span style="font-size: 50px;color: #3384EF;">今日无待沟通客户</span>
	            </c:if>
            </ul>
        </div>
        <!--  
        <div class="daily-event">
            <ul>
            	<c:if test="${not empty saleEngineerDataVo && not empty saleEngineerDataVo.bussinessChanceVoList }">
	               	<c:forEach items="${saleEngineerDataVo.bussinessChanceVoList}" var="bc" >
	               		<li>
		                   <div class="daily-title">商机跟进</div>
		                    <div class="daily-content">
		                        <div class="daily-one">
		                            <div class="daily-icon">
		                                <i class="iconbusichance"></i>
		                            </div>
		                            <div class="daily-detail">
		                                <p class="detail-title">
		                                    <span>
		                                    	<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"view<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"${pageContext.request.contextPath}/order/bussinesschance/toSalesDetailPage.do?bussinessChanceId=${bc.bussinessChanceId}&traderId=${bc.traderId }",
												"title":"销售商机详情"}'>${bc.bussinessChanceNo }</a>
		                                    </span>
		                                    <span class="font-grey9 ml6">
		                                    	<c:if test="${bc.status eq 0 }">未处理</c:if>
		                                    	<c:if test="${bc.status eq 1 }">报价中</c:if>
		                                    	<c:if test="${bc.status eq 2 }">已报价</c:if>
		                                    	<c:if test="${bc.status eq 3 }">已订单</c:if>
		                                    	<c:if test="${bc.status eq 4 }">已关闭</c:if>
		                                    </span>
		                                </p>
		                                <p>${bc.checkTraderName}</p>
		                            </div>
		                        </div>
		                        <div class="daily-two">
		                            <span>${bc.traderContactName}</span>
		                            <c:if test="${not empty bc.mobile}">
		                             	<span class="tel">
			                                <i class="icon-tel cursor-pointer" title="点击拨号" onclick="callout('${bc.mobile}',${bc.traderId},1,0,0,0);"></i>
		                                	<span class="tel-num">${bc.mobile}</span>
		                            	</span>
		                            </c:if>
		                        </div>
		                        <div class="daily-three">
		                            <span class="mr4">分配时间</span>
		                            <span class="mr4"><date:date value ="${bc.assignTime}"/></span>
		                        </div>
		                        <div class="daily-four mt7">
		                               <p>${bc.goodsName}</p>
		                        </div>
		                    </div>
		                </li>
	               	</c:forEach>
	            </c:if>
            </ul>
        </div>
        <div class="daily-event quote-continue">
            <ul>
                <c:if test="${not empty saleEngineerDataVo && not empty saleEngineerDataVo.quoteorderVoList }">
	               	<c:forEach items="${saleEngineerDataVo.quoteorderVoList}" var="qu" >
	               		<li>
		                   <div class="daily-title">报价跟进</div>
		                    <div class="daily-content">
		                        <div class="daily-one">
		                            <div class="daily-icon">
		                                <i class="iconmoney"></i>
		                            </div>
		                            <div class="daily-detail">
		                                <p class="detail-title">
		                                    <span>
		                                    	<a class="addtitle"
													tabtitle="{&quot;num&quot;:&quot;viewQuote2<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>&quot;,&quot;link&quot;:&quot;${pageContext.request.contextPath}/order/quote/getQuoteDetail.do?quoteorderId=${qu.quoteorderId}&viewType=2&quot;,&quot;title&quot;:&quot;编辑报价&quot;}">${qu.quoteorderNo}</a>
		                                    </span>
		                                    <span class="font-black ml6">
		                                    	<c:if test="${qu.followOrderStatus eq 0}">跟单中</c:if>
		                                    	<c:if test="${qu.followOrderStatus eq 1}">成单</c:if>
		                                    	<c:if test="${qu.followOrderStatus eq 2}">失单</c:if>
		                                    </span>
		                                    <span class="font-black ml6">
		                                    	${qu.totalAmount}
		                                    </span>
		                                </p>
		                                <div class="customername pos_rel">
		                                    <span class="brand-color1">
		                                    	<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewcustomer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
													"link":"./trader/customer/baseinfo.do?traderId=${qu.traderId}","title":"客户信息"}'>${qu.traderName}</a>
		                                    </span><i class="iconbluemouth"></i>
		                                    <div class="pos_abs customernameshow" style="display: none; left: 390px; top: 1px;">
		                                       	报价次数：${qu.quoteNum}
		                                        <br> 交易次数：${qu.buyCount}
		                                        <br> 交易金额：${qu.buyAmount}
		                                        <br> 上次交易日期：<date:date value ="${qu.lastBuyTime}"/>
		                                        <br> 归属销售：${qu.belongSales}
		                                    </div>
		                                </div>
		                            </div>
		                        </div>
		                        <div class="daily-two">
		                            <span>${qu.traderContactName}</span>
		                            <span class="tel">
		                             	<c:if test="${not empty qu.mobile}">
			                                <i class="icon-tel cursor-pointer" title="点击拨号" onclick="callout('${qu.mobile}',${qu.traderId},1,0,0,0);"></i>
					                    </c:if>
		                                <span class="tel-num">${qu.mobile}</span>
		                            </span>
		                        </div>
		                        <div class="daily-three">
		                            <span class="mr4">创建日期</span>
		                            <span class="mr4"><date:date value ="${qu.addTime}"/></span>
		                        </div>
		                        <div class="daily-three">
		                            <span class="mr4">下次联系</span>
		                            <span class="mr4">${qu.nextContactDate}</span>
		                        </div>
		                    </div>
		                </li>
	               	</c:forEach>
	            </c:if>
            </ul>
        </div>-->
    </div>
<%@ include file="../../common/footer.jsp"%>
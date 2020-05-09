<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="订单编辑" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/edit.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/region/index.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
	$(function(){
		var saleorderId = $("input[name='saleorderId']").val();

		//补订单产品详情相关数据
		$.ajax({
			async:false,
			url:page_url+'/order/saleorder/getsaleordergoodsextrainfo.do',
			data:{"saleorderId":saleorderId, "extraType":"order_saleorder"},//销售订单详情（占用，库存，采购状态，到库状态，发货状态，收货状态）
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code==0){
					for (var i = 0; i < data.data.length; i++) {
						//alert(data.data[i].saleorderGoodsId);
						$("#orderOccupy_stockNum_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.orderOccupy+"/"+data.data[i].goods.stockNum);
						$("#kc_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.stockNum);
						$("#kykc_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.stockNum-data.data[i].goods.orderOccupy);
						$("#dzzy_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.orderOccupy);
						$("#ktj_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.adjustableNum);
					}
				}else{
					layer.alert(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	})
</script>
	<div class="content mt10 ">
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">基本信息</div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">订单号</td>
                        <td>${saleorder.saleorderNo}</td>
                        <td class="table-smaller">订单状态</td>
                        <td>
                            <c:choose>
								<c:when test="${saleorder.status eq 0}">待确认</c:when>
								<c:when test="${saleorder.status eq 1}">进行中</c:when>
								<c:when test="${saleorder.status eq 2}">已完结</c:when>
								<c:when test="${saleorder.status eq 3}">已关闭</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>创建者</td>
                        <td>${saleorder.creatorName}</td>
                        <td>创建时间</td>
                        <td><date:date value ="${saleorder.addTime}"/></td>
                    </tr>
                    <tr>
                        <td>销售部门</td>
                        <td>${saleorder.salesDeptName}</td>
                        <td>归属销售</td>
                        <td>${saleorder.optUserName}</td>
                    </tr>
                    <tr>
                        <td>生效状态</td>
                        <td>
                            <c:choose>
								<c:when test="${saleorder.validStatus eq 0}">未生效</c:when>
								<c:when test="${saleorder.validStatus eq 1}">已生效</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                        <td>生效时间</td>
                        <td><date:date value ="${list.validTime}"/></td>
                    </tr>
                    <tr>
                        <td>审核状态</td>
                        <td>
                        	<c:choose>
								<c:when test="${saleorder.verifyStatus == null}">待审核</c:when>
								<c:when test="${saleorder.verifyStatus eq 0}">审核中</c:when>
								<c:when test="${saleorder.verifyStatus eq 1}">审核通过</c:when>
								<c:when test="${saleorder.verifyStatus eq 2}">审核不通过</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                        <td>锁定状态</td>
                        <td>
                            <c:choose>
								<c:when test="${saleorder.lockedStatus eq 0}">未锁定</c:when>
								<c:when test="${saleorder.lockedStatus eq 1}">已锁定</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>商机编号</td>
                        <td>
                            <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${saleorder.bussinessChanceId}","link":"./order/bussinesschance/toSalesDetailPage.do?bussinessChanceId=${saleorder.bussinessChanceId}","title":"商机信息"}'>${saleorder.bussinessChanceNo}</a>
                        </td>
                        <td>报价单号</td>
                        <td>
                            <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewquoteorder${saleorder.quoteorderId}","link":"./order/quote/getQuoteDetail.do?quoteorderId=${saleorder.quoteorderId}&viewType=2","title":"报价信息"}'>${saleorder.quoteorderNo}</a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <!-- div class="parts">
            <div class="title-container">
                <div class="table-title nobor">终端信息</div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td>终端名称</td>
                        <td>${saleorder.terminalTraderName}</td>
                        <td>终端类型</td>
                        <td>${saleorder.terminalTraderTypeStr}</td>
                    </tr>
                    <tr>
                        <td class="table-smaller">销售区域</td>
                        <td>${saleorder.salesArea}</td>
                        <td class="table-smaller"></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div-->
        <form action="${pageContext.request.contextPath}/order/saleorder/saveeditsaleorderinfo.do" method="post" id="editForm">
        <c:if test="${saleorder.customerNature eq 465}"><!-- 分销 -->
        	<div id="quotePayMoneForm">
        		<input type="hidden" value="${saleorder.customerNature}" id="saleCustomerNature">
	        	<input type="hidden" name="terminalTraderName" id="terminalTraderName" class="terminal" value="${saleorder.terminalTraderName}"/> 
				<input type="hidden" name="terminalTraderId" id="terminalTraderId" class="terminal" value="${saleorder.terminalTraderId}"/> 
				<input type="hidden" name="terminalTraderType" id="terminalTraderType" class="terminal" value="${saleorder.terminalTraderType}"/>
				<input type="hidden" name="salesArea" id="salesArea" class="terminal" value="${saleorder.salesArea}"/> 
				<input type="hidden" name="salesAreaId" id="salesAreaId" class="terminal" value="${saleorder.salesAreaId}"/> 
        	</div>
			<div class="parts" id="updateTerminalInfo">
				<div class="formtitle">终端信息</div>
				<ul class="payplan">
					<c:choose>
						<c:when test="${not empty saleorder.terminalTraderName}"><!-- 客户名称存在，则默认不显示选择框 -->
							<li id="terminalNameCheck" style="display: none;">
						</c:when>
						<c:otherwise><!-- 客户名称不存在，默认显示选择框 -->
							<li id="terminalNameCheck">
						</c:otherwise>
					</c:choose>
						<div class="infor_name">
							<label>终端名称</label>
						</div>
						<div class="f_left">
							<div class="inputfloat" id="errorTxtMsg">
								<!-- 客户为终端 --> 
								<input type="text" placeholder="请输入终端名称" class="input-largest" name="searchTraderName" id="searchTraderName"> 
								<label class="bt-bg-style bg-light-blue bt-small" onclick="searchTerminal();" id="errorMes">搜索</label> 
								<span style="display: none;"> <!-- 弹框 -->
									<div class="title-click nobor  pop-new-data" id="terminalDiv"></div>
								</span>
							</div>
						</div>
					</li>
					<c:choose>
						<c:when test="${empty saleorder.terminalTraderName}"><!-- 客户名称不存在，默认显示选择框 -->
							<li id="terminalNameDetail" style="display: none;">
						</c:when>
						<c:otherwise><!-- 客户名称不存在，默认显示选择框 -->
							<li id="terminalNameDetail">
						</c:otherwise>
					</c:choose>
						<div class="infor_name">
							<label>终端名称</label>
						</div>
						<div class="f_left">
							<div class=" inputfloat" id="errorTxtMsg">
								<span class="mr8 mt3" id="terminalTraderNameDiv">${saleorder.terminalTraderName}</span> 
								<label class="bt-bg-style bg-light-blue bt-small" onclick="agingSearchTerminal();">重新搜索</label>
							</div>
						</div>
					</li>
					<li>
						<div class="infor_name ">
							<span>*</span>
							<label>销售区域</label>
						</div> 
						<div class="f_left">
							<c:choose>
								<c:when test="${empty saleorder.salesAreaId}">
									<select class="input-small f_left mr10" name="province" id="province">
										<option value="0">请选择</option>
										<c:if test="${not empty provinceList }">
											<c:forEach items="${provinceList }" var="prov">
												<option value="${prov.regionId }" <c:if test="${province eq prov.regionId }">selected="selected"</c:if>>${prov.regionName }</option>
											</c:forEach>
										</c:if>
									</select> 
									<select class="input-small f_left mr10" name="city" id="city">
										<option value="0">请选择</option>
									</select> 
									<select class="input-small f_left" name="zone" id="zone">
										<option value="0">请选择</option>
									</select>
								</c:when>
								<c:otherwise>
									<select class="input-small f_left mr10" name="province" id="province">
										<option value="0">请选择</option>
										<c:if test="${not empty provinceList }">
											<c:forEach items="${provinceList }" var="province">
												<option value="${province.regionId }"
													<c:if test="${ not empty provinceRegion &&  province.regionId == provinceRegion.regionId }">selected="selected"</c:if>>${province.regionName }</option>
											</c:forEach>
										</c:if>
									</select> 
									<select class="input-small f_left mr10" name="city" id="city">
										<option value="0">请选择</option>
										<c:if test="${not empty cityList }">
											<c:forEach items="${cityList }" var="city">
												<option value="${city.regionId }"
													<c:if test="${ not empty cityRegion &&  city.regionId == cityRegion.regionId }">selected="selected"</c:if>>${city.regionName }</option>
											</c:forEach>
										</c:if>
									</select> 
									<select class="input-small f_left" name="zone" id="zone">
										<option value="0">请选择</option>
										<c:if test="${not empty zoneList }">
											<c:forEach items="${zoneList }" var="zone">
												<option value="${zone.regionId }"
													<c:if test="${ not empty zoneRegion &&  zone.regionId == zoneRegion.regionId }">selected="selected"</c:if>>${zone.regionName }</option>
											</c:forEach>
										</c:if>
									</select>
								</c:otherwise>
							</c:choose>
							<div id="sales_area_msg_div" style="clear:both"></div>
						</div>
					</li>
				</ul>
			</div>
		</c:if>
			
			
			<div class="parts">
                <div class="title-container">
                    <div class="table-title nobor">产品信息</div>
                    <div class="title-click nobor  pop-new-data" layerParams='{"width":"700px","height":"480px","title":"添加产品","link":"./addSaleorderGoods.do?saleorderId=${saleorder.saleorderId}"}'>添加</div>
                    <c:if test="${saleorder.quoteorderId eq 0}">
	                    <div class="title-click nobor  pop-new-data" layerparams='{"width":"500px","height":"200px","title":"批量新增","link":"./batchAddSaleGoodsInit.do?saleorderId=${saleorder.saleorderId}"}'>批量新增</div>
                    </c:if>
                </div>
                <table class="table  table-bordered table-striped table-condensed table-centered">
                    <thead>
                        <tr>
                            <th style="width:50px">序号</th>
	                        <th>产品名称</th>
	                        <th style="width:80px">品牌</th>
	                        <th style="width:70px">型号</th>
	                        <th style="width:80px">单价</th>
	                        <th style="width:35px">数量</th>
	                        <th style="width:35px">单位</th>
	                        <th style="width:80px">总额</th>
	                        <th style="width:70px">货期</th>
	                        <th style="width:50px">直发</th>
	                        <th style="width:150px">核价参考</th>
	                        <th style="width:80px">占用/库存</th>
	                        <th style="width:50px">含安调</th>
	                        <th style="width:60px ">类别管制</th>
	                        <th>产品备注</th>
	                        <th>内部备注</th>
                            <th style="width:110px">操作</th>
                        </tr>
                    </thead>
                    <tbody>
	                	<c:set var="num" value="0"></c:set>
						<c:set var="totleMoney" value="0.00"></c:set>
	                	<c:forEach var="list" items="${saleorderGoodsList}" varStatus="staut">
	                		<c:if test="${list.isDelete eq 0}">
								<c:set var="num" value="${num + list.num}"></c:set>
								<c:set var="totleMoney" value="${totleMoney + (list.price * list.num)}"></c:set>
							</c:if>
		                    <tr <c:if test="${list.isDelete eq 1}">class="caozuo-grey"</c:if>>
		                        <td style="overflow:hidden;padding-left:12px;">
		                        	<input type="checkbox" name="goodsCheckName" onclick="goodsCheckClick(this);" value="${list.saleorderGoodsId}" class="f_left" autocomplete="off"/>
		                        	<span class="f_left">${staut.count}</span>
		                        </td>
		                        <td class="text-left">
		                            <div class="customername pos_rel">
		                            	<c:choose>
											<c:when test="${list.isDelete eq 1}">
												<span>${list.goodsName}<br/></span>
				                                <span>${list.sku} <br>${list.goods.materialCode}</span>
											</c:when>
											<c:otherwise>
												<span class="font-blue"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${list.goodsName}</a>&nbsp;<i class="iconbluemouth contorlIcon"></i><br/></span>
				                                <span>${list.sku} <br>${list.goods.materialCode}</span>
				                                <div class="pos_abs customernameshow">
												物料编码：${list.goods.materialCode} <br> 
												注册证号：${list.goods.registrationNumber} <br>
												管理类别：${list.manageCategoryName} <br>
												产品负责人：<c:if test="${not empty list.goods.userList }">
																<c:forEach items="${list.goods.userList }" var="user" varStatus="st">
																	${user.username } 
																	<c:if test="${st.count != list.goods.userList.size() }">、</c:if>
																</c:forEach>
															</c:if> 
															<br> 
												采购提醒：${list.goods.purchaseRemind} <br>
												包装清单：${list.goods.packingList} <br> 
												服务条款：${list.goods.tos} <br>
												库存：<span id="kc_${list.saleorderGoodsId}">${list.goods.stockNum}</span> <br>
												可用库存：<span id="kykc_${list.saleorderGoodsId}">${list.goods.stockNum-list.goods.orderOccupy}</span><br>
												订单占用：<span id="dzzy_${list.saleorderGoodsId}">${list.goods.orderOccupy}</span><br> 
												可调剂：<span id="ktj_${list.saleorderGoodsId}">${list.goods.adjustableNum}</span> <br> 
												审核状态：<c:choose>
														 	<c:when test="${list.goods.verifyStatus eq 0}">审核中</c:when>
														 	<c:when test="${list.goods.verifyStatus eq 1}">审核通过</c:when>
														 	<c:when test="${list.goods.verifyStatus eq 2}">审核不通过</c:when>
														 	<c:otherwise>待审核</c:otherwise>
														 </c:choose>
												</div>
											</c:otherwise>
										</c:choose>
		                            </div>
		                        </td>
		                        <td>${list.brandName}</td>
		                        <td>${list.model}</td>
		                        <td>${list.price}</td>
		                        <td>${list.num}</td>
		                        <td>${list.unitName}</td>
		                        <td><fmt:formatNumber type="number" value="${list.price * list.num}" pattern="0.00" maxFractionDigits="2" /></td>
		                        <td>${list.deliveryCycle}</td>
		                        <td>
		                            <div class="customername pos_rel">
		                                <span>
		                                <c:choose>
											<c:when test="${list.deliveryDirect eq 0}">否</c:when>
											<c:when test="${list.deliveryDirect eq 3}"></c:when>
											<c:when test="${list.deliveryDirect eq 1}">
											是
											<i class="iconbluesigh ml4 contorlIcon"></i></span>
			                                <div class="pos_abs customernameshow">直发原因：${list.deliveryDirectComments}</div>
											</c:when>
										</c:choose>
		                            </div>
		                        </td>
		                        <td>
		                        	<div class="customername pos_rel">
		                        		核价参考价格：<fmt:formatNumber type="number" value="${list.channelPrice}" pattern="0.00" maxFractionDigits="2" /><br/>
										参考价格：${list.referencePrice} <br/>
										参考货期：${list.referenceDeliveryCycle} <br/>
										<shiro:hasPermission name="/order/quote/settlementPrice.do">
										结算价：${list.settlePrice} <br/>
										</shiro:hasPermission>
										<c:choose>
											<c:when test="${list.reportStatus eq 2}">
												报备成功<i class="iconbluesigh ml4"></i>
											</c:when>
											<c:when test="${list.reportStatus eq 3}">
												报备失败<i class="iconredsigh ml4"></i>
											</c:when>
											<c:otherwise>
												<i class="iconbluesigh ml4"></i>
											</c:otherwise>
										</c:choose>
										<div class="pos_abs customernameshow">
											<c:set var="goodsUserNm" value=""/>
											<c:forEach var="user" items="${userList}">
												<c:if test="${user.userId eq list.lastReferenceUser}"><c:set var="goodsUserNm" value="${user.username}"/></c:if>
											</c:forEach>
											核价参考价格：<fmt:formatNumber type="number" value="${list.channelPrice}" pattern="0.00" maxFractionDigits="2" /><br/>
											客户上次购买价格：<fmt:formatNumber type="number" value="${list.lastOrderPrice}" pattern="0.00" maxFractionDigits="2" /><br/>
											参考价格（${goodsUserNm}）：${list.referencePrice} <br/>
											期货交货期：${list.channelDeliveryCycle} <br> 
											现货交货期：${list.delivery} <br>
											参考货期（${goodsUserNm}）：${list.referenceDeliveryCycle} <br/>
											报备结果：
												<c:if test="${list.reportStatus eq 2}">
													成功 <br/>
													报备失败原因：<%-- ${list.reportComments} --%>
												</c:if>
												<c:if test="${list.reportStatus eq 3}">
													失败 <br/>
													报备失败原因：${list.reportComments}
												</c:if>
										</div>
									</div>
		                        </td>
		                        <td><span id="orderOccupy_stockNum_${list.saleorderGoodsId}">${list.goods.orderOccupy}/${list.goods.stockNum}</span></td>
		                        <td>
		                        	<c:choose>
										<c:when test="${list.haveInstallation eq 0}">否</c:when>
										<c:otherwise>是</c:otherwise>
									</c:choose>
		                        </td>
		                        <td>---</td>
		                        <td>${list.goodsComments}</td>
		                        <td>${list.insideComments}</td>
		                        <td>
	                                <div class="caozuo">
										<c:choose>
											<c:when test="${list.isDelete eq 0}">
												<span class="caozuo-blue pop-new-data" layerParams='{"width":"700px","height":"480px","title":"编辑产品信息","link":"./editSaleorderGoods.do?saleorderGoodsId=${list.saleorderGoodsId}"}'>编辑</span>
		                                    	<span class="caozuo-red" onclick="delSaleorderGoods(${list.saleorderGoodsId},${list.saleorderId});">删除</span>
											</c:when>
											<c:otherwise>
												<span class="caozuo-grey cursor-normal">已删除</span>
											</c:otherwise>
										</c:choose>
	                                </div>
	                            </td>
		                    </tr>
	                    </c:forEach>
	                    <tr style="background: #eaf2fd;">
	                        <td colspan="17" class="text-left">
		                    	<input type="checkbox" name="goodsCheckAllName" id="goodsCheckAllName" onclick="goodsCheckAllClick(this);" autocomplete="off"/><span>全选</span>
		                    	<span style="display: none;"> <!-- 弹框 -->
									<div class="title-click nobor  pop-new-data" id="saleGoodsDeliveryDirect"></div>
								</span>
		                    	<a onclick="updateSaleGoodsInit(${saleorder.saleorderId});">批量修改</a>
		                    	&nbsp;&nbsp;
	                                                                  总件数<span class="font-red">${num}</span>， 总金额
	                            <span class="font-red"><fmt:formatNumber type="number" value="${totleMoney}" pattern="0.00" maxFractionDigits="2" /></span>
	                            <input type="hidden" id="goodsTotleMoney"  value="${totleMoney}">
	                        </td>
	                    </tr>
	                </tbody>
                </table>
            </div>
        
            <div class="parts content1 ">
                <div class="formtitle mt10">客户信息</div>
                <ul class="payplan visible">
                    <li class="visible">
                        <div class="infor_name infor_name72 mt0">
                            <span>*</span>
                            <label>客户名称</label>
                        </div>
                        <div class="f_left  customername pos_rel">
                            <span class="font-blue">
                            <a class="addtitle" href="javascript:void(0);" 
										tabTitle='{"num":"viewcustomer${customer.traderCustomerId}",
										"link":"./trader/customer/baseinfo.do?traderCustomerId=${customer.traderCustomerId}&traderId=${saleorder.traderId}",
										"title":"客户信息"}'>
                            ${customer.name}</a></span>
                            <i class="iconbluemouth contorlIcon"></i>
                            <div class="pos_abs customernameshow">
                                    报价次数：${customer.quoteCount} <br>
                                    交易次数：${customer.buyCount} <br>
                                    交易金额：${customer.buyMoney} <br>
                                    上次交易日期：<date:date value="${customer.lastBussinessTime}" format="yyyy-MM-dd" /> <br>
                                    归属销售：${customer.ownerSale}
                            </div>
                            <input type="hidden" name="traderName" value="${customer.name}">
                        </div>
                        <div class="clear"></div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72 mt0">
                            <span>*</span>
                            <label>客户类型</label>
                        </div>
                        <div class="f_left">
                            <label>${saleorder.customerTypeStr} ${saleorder.customerNatureStr}</label>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                            <span>*</span>
                            <label>联系人</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select class="input-xx mr10" name="traderContactId" id="contact_3">
                                <option value="0">请选择</option>
                                <c:if test="${not empty traderContactList}">
									<c:forEach items="${traderContactList}" var="list" varStatus="status">
									<option value="${list.traderContactId}" <c:if test="${list.traderContactId eq saleorder.traderContactId}">selected="selected"</c:if>>${list.name}/${list.telephone}/${list.mobile}</option>
									</c:forEach>
				                </c:if>
                            </select>
                            <input type="hidden" name="traderContactName">
                            <input type="hidden" name="traderContactTelephone">
                            <input type="hidden" name="traderContactMobile">
                            <span class="mt4 pop-new-data font-blue" layerParams='{"width":"430px","height":"220px","title":"添加联系人","link":"./addContact.do?traderId=${saleorder.traderId}&traderCustomerId=${traderCustomerInfo.traderCustomerId}&indexId=3"}'>添加联系人</span>
                        	<div id="traderContactIdMsg" style="clear:both"></div>
                        </div>
                    </li>
                    <!-- <li>
                        <div class="infor_name infor_name72 mt0">
                            <label>注册账号</label>
                        </div>
                        <div class="f_left inputfloat mt0">
                            <span>未绑定注册账号，请检查客户是否注册，未注册的客户可以邀请客户注册账号体验在线服务</span>
                            <span class=" pop-new-data font-blue ml10" layerParams='{"width":"700px","height":"400px","title":"注册账号","link":"popups/checkcount.html"}'>检查</span>
                        </div>
                    </li> -->
                    <li>
                        <div class="infor_name infor_name72">
                            <span>*</span>
                            <label>联系地址</label>
                        </div>
                        <div class="f_left">
                            <div>
                                <select class="input-xx mr10" name="traderAddressId" id="address_3">
                                    <option value="0">请选择</option>
                                    <c:if test="${not empty traderAddressList}">
										<c:forEach items="${traderAddressList}" var="list" varStatus="status">
										<option value="${list.traderAddress.traderAddressId}" <c:if test="${list.traderAddress.traderAddressId eq saleorder.traderAddressId}">selected="selected"</c:if>>${list.area}/${list.traderAddress.address}</option>
										</c:forEach>
					                </c:if>
                                </select>
                                <input type="hidden" name="traderArea">
                            	<input type="hidden" name="traderAddress">
                                <span class="mt4 pop-new-data font-blue" layerParams='{"width":"600px","height":"200px","title":"添加地址","link":"./addAddress.do?traderId=${saleorder.traderId}&indexId=3"}'>添加地址</span>
                            	<div id="traderAddressIdMsg" style="clear:both"></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72 mt0">
                            <label>客户备注</label>
                        </div>
                        <div class="f_left" style="max-width:1000px;">
                            <span>${customer.comments}</span>
                            <input type="hidden" name="traderComments" value="${customer.comments}">
                        </div>
                    </li>
                    <div class="clear"></div>
                </ul>
            </div>
            <div class="parts content1 ">
                <div class="formtitle mt10">
                    收货信息
                </div>
                <ul class="payplan">
                    <li>
                        <div class="infor_name infor_name72">
                            <span>*</span>
                            <label>收货客户</label>
                        </div>
                        <div class="f_left inputfloat">
                            <span class=" mr10 mt3" id="trader_name_span_1">${saleorder.takeTraderName}</span>
                            <input type="hidden" name="takeTraderId" id="trader_id_1" value="${saleorder.takeTraderId}">
                            <input type="hidden" name="takeTraderName" id="trader_name_1" value="${saleorder.takeTraderName}">
                            <span class="bt-bg-style bt-small bg-light-blue pop-new-data" layerParams='{"width":"800px","height":"300px","title":"编辑收货信息","link":"${pageContext.request.contextPath}/trader/customer/searchCustomerList.do?indexId=1&searchTraderName="}'>重新搜索</span>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                            <span>*</span>
                            <label>收货联系人</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select class="input-xx" id="contact_1" name="takeTraderContactId">
                                <option value="0">请选择</option>
                                
							<%-- 	<c:if test="${saleorder.orderType !=1 }"> --%>
                                <c:if test="${not empty takeTraderContactList}">
									<c:forEach items="${takeTraderContactList}" var="list" varStatus="status">
									<option value="${list.traderContactId}" <c:if test="${list.traderContactId eq saleorder.takeTraderContactId}">selected="selected"</c:if>>
									${list.name}/${list.telephone}/${list.mobile}
									</option>
									</c:forEach>
				                </c:if>
								<%-- </c:if> --%>
				               <%--  <c:if test="${saleorder.orderType ==1 }">
				                <c:if test="${not empty takeTraderContactList}">
									<c:forEach items="${takeTraderContactList}" var="list" varStatus="status">
									<option value="${list.traderContactId}" <c:if test="${list.traderContactId eq saleorder.takeTraderContactId}">selected="selected"</c:if>>
									${saleorder.takeTraderContactName}/${saleorder.takeTraderContactTelephone}/${saleorder.takeTraderContactMobile}
									</option>
									</c:forEach>
				                </c:if>
								</c:if> --%>
				                 
				                 
				               <%--  <c:if test="${takeTraderContactList ==null}">
									<c:forEach items="${takeTraderContactList}" var="list" varStatus="status">
									<option value="${list.traderContactId}" <c:if test="${list.traderContactId eq saleorder.takeTraderContactId}">selected="selected"</c:if>>
									${saleTrader.takeTraderContactName}/${saleTrader.takeTraderContactTelephone}/${saleTrader.takeTraderContactMobile}</option>
									</c:forEach>
				                </c:if> --%>
                            </select>
                            <input type="hidden" name="takeTraderContactName">
                            <input type="hidden" name="takeTraderContactTelephone">
                            <input type="hidden" name="takeTraderContactMobile">
                            <span class="mt4 pop-new-data font-blue" id="add_contact_1" layerParams='{"width":"430px","height":"220px","title":"添加联系人","link":"./addContact.do?traderId=${saleorder.takeTraderId}&traderCustomerId=${takeTraderCustomerInfo.traderCustomerId}&indexId=1"}'>添加联系人</span>
                        	<div id="takeTraderContactIdMsg" style="clear:both"></div>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                            <span>*</span>
                            <label>收货地址</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select class="input-xx" id="address_1" name="takeTraderAddressId">
                                <option value="0">请选择</option>
                                <c:if test="${not empty takeTraderAddressList}">
									<c:forEach items="${takeTraderAddressList}" var="list" varStatus="status">
									<option value="${list.traderAddress.traderAddressId}" <c:if test="${list.traderAddress.traderAddressId eq saleorder.takeTraderAddressId}">selected="selected"</c:if>>
									<%-- <c:if test="${saleorder.orderType !=1 }"> --%>
									${list.area}/${list.traderAddress.address}
									<%-- </c:if> --%>
									<%-- <c:if test="${saleorder.orderType ==1 }">
									${saleorder.takeTraderArea}/${saleorder.takeTraderAddress}
									</c:if> --%>
									</option>
									</c:forEach>
				                </c:if>
                               
                            </select>
                            <input type="hidden" name="takeTraderArea">
                           	<input type="hidden" name="takeTraderAddress">
                            <span class="mt4 pop-new-data font-blue" id="add_address_1" layerParams='{"width":"600px","height":"200px","title":"添加地址","link":"./addAddress.do?traderId=${saleorder.takeTraderId}&indexId=1"}'>添加地址</span>
                        	<div id="takeTraderAddressIdMsg" style="clear:both"></div>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                            <label>发货方式</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select class="input-middle" name="deliveryType">
                                <option value="0">请选择</option>
                                <c:forEach var="list" items="${deliveryTypes}">
			                    	<option value="${list.sysOptionDefinitionId}" <c:if test="${saleorder.deliveryType == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
			                    </c:forEach>
                            </select>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                            <label>指定物流公司</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select class="input-middle" name="logisticsId">
                                <option value="0">请选择</option>
                                <c:forEach var="list" items="${logisticsList}">
                                	<c:if test="${list.isEnable == 1}">
			                    		<option value="${list.logisticsId}" <c:if test="${saleorder.logisticsId == list.logisticsId}">selected="selected"</c:if> >${list.name}</option>
			                    	</c:if>
			                    </c:forEach>
                            </select>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                            <label>运费说明</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select class="input-middle" name="freightDescription">
                                <option value="0">请选择</option>
                                <c:forEach var="list" items="${freightDescriptions}">
			                    	<option value="${list.sysOptionDefinitionId}" <c:if test="${saleorder.freightDescription == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
			                    </c:forEach>
                            </select>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                            <label>物流备注</label>
                        </div>
                        <div class="f_left inputfloat">
                            <input type="text" name="logisticsComments" id="logisticsComments" value="${saleorder.logisticsComments}" placeholder="对内使用，适用于向物流部同事告知发货要求，默认同步客户信息中物流备注" class="input-xx" />
                        </div>
                    </li>
                </ul>
            </div>
            <div class="parts content1 ">
                <div class="formtitle mt10">
                    收票信息
                </div>
                <ul class="payplan">
                    <li>
                        <div class="infor_name infor_name72">
                            <span>*</span>
                            <label>收票客户</label>
                        </div>
                        <div class="f_left inputfloat">
                            <span class="mr10 mt3" id="trader_name_span_2">${saleorder.invoiceTraderName}</span>
                            <input type="hidden" name="invoiceTraderId" id="trader_id_2" value="${saleorder.invoiceTraderId}">
                            <input type="hidden" name="invoiceTraderName" id="trader_name_2" value="${saleorder.invoiceTraderName}">
                            <span class="bt-bg-style bt-small bg-light-blue pop-new-data" layerParams='{"width":"800px","height":"300px","title":"编辑收票信息","link":"${pageContext.request.contextPath}/trader/customer/searchCustomerList.do?indexId=2&searchTraderName="}'>重新搜索</span>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                            <span>*</span>
                            <label>收票联系人</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select class="input-xx" id="contact_2" name="invoiceTraderContactId">
                                <option value="0">请选择</option>
                                <c:if test="${not empty invoiceTraderContactList}">
									<c:forEach items="${invoiceTraderContactList}" var="list" varStatus="status">
									<option value="${list.traderContactId}" <c:if test="${list.traderContactId eq saleorder.invoiceTraderContactId}">selected="selected"</c:if>>${list.name}/${list.telephone}/${list.mobile}</option>
									</c:forEach>
				                </c:if>
                            </select>
                            <input type="hidden" name="invoiceTraderContactName">
                            <input type="hidden" name="invoiceTraderContactTelephone">
                            <input type="hidden" name="invoiceTraderContactMobile">
                            <span class="mt4 pop-new-data font-blue" id="add_contact_2" layerParams='{"width":"430px","height":"220px","title":"添加联系人","link":"./addContact.do?traderId=${saleorder.invoiceTraderId}&traderCustomerId=${invoiceTraderCustomerInfo.traderCustomerId}&indexId=2"}'>添加联系人</span>
                        	<div id="invoiceTraderContactIdMsg" style="clear:both"></div>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                            <span>*</span>
                            <label>收票地址</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select class="input-xx" id="address_2" name="invoiceTraderAddressId">
                                <option value="0">请选择</option>
                                <c:if test="${not empty invoiceTraderAddressList}">
									<c:forEach items="${invoiceTraderAddressList}" var="list" varStatus="status">
									<option value="${list.traderAddress.traderAddressId}" <c:if test="${list.traderAddress.traderAddressId eq saleorder.invoiceTraderAddressId}">selected="selected"</c:if>>${list.area}/${list.traderAddress.address}</option>
									</c:forEach>
				                </c:if>
                            </select>
                            <input type="hidden" name="invoiceTraderArea">
                           	<input type="hidden" name="invoiceTraderAddress">
                            <span class="mt4 pop-new-data font-blue" id="add_address_2" layerParams='{"width":"600px","height":"200px","title":"添加地址","link":"./addAddress.do?traderId=${saleorder.invoiceTraderId}&indexId=2"}'>添加地址</span>
                        	<div id="invoiceTraderAddressIdMsg" style="clear:both"></div>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                        	<span>*</span>
                            <label>发票类型</label>
                        </div>
                        <!-- 获取当前日期 -->
						<jsp:useBean id="now" class="java.util.Date" />
						<fmt:formatDate value="${now}" type="both" dateStyle="long" var="today" pattern="yyyy-MM-dd"/>
                        <div class="f_left inputfloat">
                            <select class="input-middle" name="invoiceType" onchange="updateInvoiceType(this);">
                                <option value="0">请选择</option>
                                <!-- 4月1号后税率只有13% -->
								<c:choose>
									<c:when test="${today >= '2019-04-01'}">
		                                <c:forEach var="list" items="${invoiceTypes}">
		                                	<c:if test="${list.sysOptionDefinitionId eq 971 or list.sysOptionDefinitionId eq 972}">
						                    	<option value="${list.sysOptionDefinitionId}" <c:if test="${saleorder.invoiceType == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
		                                	</c:if>
					                    </c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach var="list" items="${invoiceTypes}">
		                                	<c:if test="${list.sysOptionDefinitionId eq 681 or list.sysOptionDefinitionId eq 682 or list.sysOptionDefinitionId eq 971 or list.sysOptionDefinitionId eq 972}">
						                    	<option value="${list.sysOptionDefinitionId}" <c:if test="${saleorder.invoiceType == list.sysOptionDefinitionId}">selected="selected"</c:if> >${list.title}</option>
		                                	</c:if>
					                    </c:forEach>
									</c:otherwise>
								</c:choose>
                            </select>
                            <input type="checkbox" name="isSendInvoiceCheckbox" class="mt5" onclick="isSendInvoiceChecked();" <c:if test="${saleorder.isSendInvoice == 0}">checked="checked"</c:if> >
                            <input type="hidden" name="isSendInvoice" value="${saleorder.isSendInvoice}">
                            <span class="mt3">不寄送</span>
                            <div id="invoiceTypeMsg" style="clear:both"></div>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                        	<span>*</span>
                            <label>开票方式</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select class="input-middle" name="invoiceMethod">
                                <option value="0">请选择</option>
                                <c:if test="${saleorder.invoiceType == 681 or saleorder.invoiceType == 971}">
	                                <option value="1" <c:if test="${saleorder.invoiceMethod == 1}">selected</c:if>>手动纸质开票</option>
	                                <option value="2" <c:if test="${saleorder.invoiceMethod == 2}">selected</c:if>>自动纸质开票</option>
	                                <option value="3" <c:if test="${saleorder.invoiceMethod == 3}">selected</c:if>>自动电子发票</option>
                                </c:if>
                                <c:if test="${saleorder.invoiceType == 682 or saleorder.invoiceType == 972}">
	                                <option value="1" <c:if test="${saleorder.invoiceMethod == 1}">selected</c:if>>手动纸质开票</option>
	                                <option value="2" <c:if test="${saleorder.invoiceMethod == 2}">selected</c:if>>自动纸质开票</option>
                                </c:if>
                            </select>
                            <div id="invoiceMethodMsg" style="clear:both"></div>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                            <label>开票备注</label>
                        </div>
                        <div class="f_left inputfloat">
                            <input type="text" name="invoiceComments" id="invoiceComments" value="${saleorder.invoiceComments}" placeholder="对内使用，适用于向财务部同事告知开票要求" class="input-xx" />
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
							<label>暂缓开票</label>
						</div>
                        <div class="f_left inputfloat">
                            <input type="checkbox" name="isDelayInvoiceCheckbox" class="mt5" onclick="isDelayInvoiceChecked();" <c:if test="${saleorder.isDelayInvoice == 1}">checked="checked"</c:if> >
                            <input type="hidden" name="isDelayInvoice" value="${saleorder.isDelayInvoice}">
                        </div>
                    </li>
                </ul>
            </div>
            
            <div class="parts content1">
				<div class="formtitle mt10">付款计划</div>
				<ul class="payplan" id="payplan">
					<li>
						<div class="infor_name infor_name72">
							<label>付款方式</label>
						</div>
						<div class="f_left inputfloat">
							<select id="paymentType" autocomplete="off" name="paymentType" onchange="updatePayment(this,${totleMoney});" class="input-middle">
								<c:forEach var="list" items="${paymentTermList}" varStatus="status">
									<option value="${list.sysOptionDefinitionId}" <c:if test="${list.sysOptionDefinitionId eq saleorder.paymentType}">selected</c:if>>${list.title}</option>
								</c:forEach>
							</select>
						</div>
					</li>
					<li>
						<div class="infor_name infor_name72 ">
							<label>预付金额</label>
						</div>
						
						<div class="f_left">
							<div><input type="text" autocomplete="off" class="input-middle" id="prepaidAmount" name="prepaidAmount" 
								<c:if test="${saleorder.paymentType ne 424}">
									readonly
								</c:if>
								<c:choose>
									<c:when test="${saleorder.prepaidAmount == '0.00' && saleorder.paymentType == 419}">
										value="${totleMoney}"
									</c:when>
									<c:otherwise>
										value="${saleorder.prepaidAmount}"
									</c:otherwise>
								</c:choose>
							> </div>
							<div id="prepaidAmountError"></div>
						</div>
						
					</li>
					<li id="accountPeriodLi" <c:if test="${(saleorder.paymentType eq 419) or (saleorder.paymentType eq 0)}">style="display:none"</c:if>><!-- 419先款后货100%预付:0默认不显示 -->
						<div class="infor_name infor_name72 ">
							<label>账期支付</label>
						</div>
						<div class="f_left inputfloat">
							<!-- 账期支付最大限额（剩余账期额度） -->
							<input type="hidden" id="accountPeriodLeft" value="<fmt:formatNumber type="number" value="${customer.accountPeriodLeft}" pattern="0.00" maxFractionDigits="2" />">
							<input type="text" class="input-middle" name="accountPeriodAmount" id="accountPeriodAmount" value="<fmt:formatNumber type="number" value="${saleorder.accountPeriodAmount}" pattern="0.00" maxFractionDigits="2" />"> 
							<input type="checkbox" style="margin-top: 7px" name="logisticsCheckBox" id="logisticsCheckBox" <c:if test="${saleorder.logisticsCollection eq 1}">checked</c:if>>
							<label class="mt4">物流代收账期款</label> 
							<input type="hidden" name="logisticsCollection" id="logisticsCollection" />
							
							<div id="accountPeriodAmountError"></div>
						</div>
						
					</li>
					<li id="retainageLi" <c:if test="${saleorder.paymentType ne 424}">style="display:none"</c:if>><!-- 424先货后款自定义 -->
						<div class="infor_name infor_name72">
							<label>尾款</label>
						</div>
						<div class="f_left">
							<div>
								<input type="text" class="input-middle" name="retainageAmount" id="retainageAmount" value="<fmt:formatNumber type="number" value="${saleorder.retainageAmount}" pattern="0.00" maxFractionDigits="2" />"> 
								<label class="ml10 mr10">尾款期限</label> 
								<input type="text" class="input-smaller" name="retainageAmountMonth" id="retainageAmountMonth" value="${saleorder.retainageAmountMonth==0?'':saleorder.retainageAmountMonth}"> 
								<label>个月</label>&nbsp;&nbsp;
							</div>
							<!-- <div class="font-red">账期支付金额超过账期剩余额度</div> -->
							<div id="retainageAmountError"></div>
						</div>
						
					</li>
					<li>
                        <div class="infor_name infor_name72">
                            <label>收款备注</label>
                        </div>
                        <div class="f_left">
                            <input type="text" placeholder="对内使用，适用于向财务部同事告知收款相关特殊要求，默认同步客户信息中财务备注" class="input-xx" name="paymentComments" id="paymentComments" value="${saleorder.paymentComments}" />
                        	<div class="font-grey9 mt10" id="error_div">客户当前账期剩余额度<fmt:formatNumber type="number" value="${customer.accountPeriodLeft}" pattern="0.00" maxFractionDigits="2" />元，账期天数${customer.periodDay}天；如需更改账期，您需要在客户详情财务信息中申请账期；	</div>
                        </div>
                    </li>
				
				</ul>
			</div>
            <div>
                <div class="formtitle mt10">
                    其他信息
                </div>
                <ul class="payplan">
                    <li>
                        <div class="infor_name infor_name72">
                            <label>附加条款</label>
                        </div>
                        <div class="f_left">
                            <input type="text" class="input-xx" name="additionalClause" id="additionalClause" value="${saleorder.additionalClause}" placeholder="面向客户条款，客户可见">
                        </div>
                    </li>
                    <li>
                        <div class="infor_name infor_name72">
                            <label>内部备注</label>
                        </div>
                        <div class="f_left inputfloat">
                            <input type="text" class="input-xx" name="comments" id="comments" value="${saleorder.comments}" placeholder="对内使用，客户不可见,可用作自己的备注">
                        </div>
                    </li>
                </ul>
            </div>
            <div class="add-tijiao">
            	<input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
            	<input type="hidden" name="beforeParams" value='${beforeParams}'>
            	<input type="hidden" name="oldAccountPeriodAmount" id="oldAccountPeriodAmount" value='${saleorder.accountPeriodAmount}'>
                <button type="button" class="bt-bg-style bg-deep-green" onclick="editSubmit();">确定</button>
            </div>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>
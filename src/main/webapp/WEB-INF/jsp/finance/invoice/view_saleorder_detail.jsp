<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="销售详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/view.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
	$(function(){
		var saleorderId = $("input[name='saleorderId']").val();
		$(function(){
			//$(".hidden-temp").hide()
			$(".J-skuPriceInfo-tr").each(function(){
				var tr=$(this)
				$.getJSON("/goods/vgoods/static/getCostPrice.do?skuId="+$(this).attr("detailgoodsid"),function(result){
					console.log(result.data)
					var data=result.data

					var buyorder=data.BUYORDERNO;

					tr.find(".jbuyorder").html(data.BUYNOLINK);
					tr.find(".jbuyorderprice").html(data.PRICE);
					loadMoreAddTitle();
				})
			})
		})

		//补订单产品详情相关数据
		$.ajax({
			async:true,
			url:page_url+'/order/saleorder/getsaleordergoodsextrainfo.do',
			data:{"saleorderId":saleorderId, "extraType":"finance_saleorder"},//财务销售订单详情（占用，库存，采购状态，到库状态，发货状态，收货状态，采购单，成本价）
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code==0){
					var allCost = 0;
					var allPrice = 0;
					var allCostSale= 0;
					for (var i = 0; i < data.data.length; i++) {
						//alert(data.data[i].saleorderGoodsId);
						//$("#orderOccupy_stockNum_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.orderOccupy+"/"+data.data[i].goods.stockNum);
						$("#kc_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.stockNum);
						$("#kykc_"+data.data[i].saleorderGoodsId).html((data.data[i].goods.stockNum-data.data[i].goods.orderOccupy) < 0 ? 0 : (data.data[i].goods.stockNum-data.data[i].goods.orderOccupy));
						$("#dzzy_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.orderOccupy);
						$("#ktj_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.adjustableNum);
						
						//采购单号
						var buyorderListStr = '';var buyorderStr = '';
						if(data.data[i].buyorderList != undefined){	
							for (var b = 0; b < data.data[i].buyorderList.length; b++) {
								if(buyorderStr.indexOf(data.data[i].buyorderList[b].buyorderNo)==-1){
									buyorderListStr += '<a class="loadMoreAddtitle" href="javascript:void(0);" tabTitle=\'{"num":"viewfinancebuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>'+ b +'", "link":"./finance/buyorder/viewBuyorder.do?buyorderId='+data.data[i].buyorderList[b].buyorderId+'","title":"订单信息"}\'>'+data.data[i].buyorderList[b].buyorderNo+'</a><br>'
								}
								buyorderStr = buyorderStr + data.data[i].buyorderList[b].buyorderNo;
								//data.data[i].buyorderList[b].buyorderNo + '<br>';
							}
						}
						$("#buyorderListStr_"+data.data[i].saleorderGoodsId).html(buyorderListStr);
						loadMoreAddTitle();
						//成本价
						if(data.data[i].costPrice != 0 && data.data[i].costPrice != null){
							$("#costPrice_"+data.data[i].saleorderGoodsId).html((data.data[i].costPrice).toFixed(2));
						}
						//商品采购总价
						if(data.data[i].costPrice != null && data.data[i].costPrice != 0){
						    //订单商品采购总额计算方式变更 2018-12-27 产品Ada 采购总额 = 采购单价*(订单商品数量-订单商品售后数量)
							$("#totalCost_"+data.data[i].saleorderGoodsId).html((data.data[i].costPrice * (data.data[i].num - data.data[i].afterReturnNum)).toFixed(2));
						}
						
						//订单采购总价
						if(data.data[i].costPrice != 0 && data.data[i].costPrice != null){
							allCost = allCost + data.data[i].costPrice * (data.data[i].num - data.data[i].afterReturnNum);
							// 有采购价的销售价
							if(data.data[i].price != 0 && data.data[i].price != null){
								allCostSale = allCostSale + data.data[i].price * (data.data[i].num - data.data[i].afterReturnNum);
							}
						}
						
						//订单销售总价
						if(data.data[i].price != 0 && data.data[i].price != null){
							allPrice = allPrice + data.data[i].price * (data.data[i].num - data.data[i].afterReturnNum);
						}
						
						//毛利率
						if (data.data[i].costPrice != 0 && data.data[i].price != 0 && data.data[i].costPrice != null) {
							$("#mll_"+data.data[i].saleorderGoodsId).html(((data.data[i].price*100-data.data[i].costPrice*100)/data.data[i].price).toFixed(2)+'%');
						}
						//采购状态
						var cgztStr = '';
						if (data.data[i].buyNum == undefined || data.data[i].buyNum == 0) {
							cgztStr = "未采购";
						} else if (data.data[i].buyNum < data.data[i].num) {
							cgztStr = "部分采购";
						} else if (data.data[i].buyNum == data.data[i].num) {
							cgztStr = "已采购";
						}
						$("#cgztStr_"+data.data[i].saleorderGoodsId).html(cgztStr);
						//到库状态
						var dkztStr = '';
						if (data.data[i].warehouseNum == undefined || data.data[i].warehouseNum == 0) {
							dkztStr = "未到库";
						} else if (data.data[i].warehouseNum < data.data[i].num) {
							dkztStr = "部分到库";
						} else if (data.data[i].warehouseNum == data.data[i].num) {
							dkztStr = "已到库";
						}
						$("#dkztStr_"+data.data[i].saleorderGoodsId).html(dkztStr);
					}
					//订单采购总价
					if(allCost != 0 && allCost != null){
					$("#allCost").html(allCost.toFixed(2));							
					}else{
						$("#allCost").html("0.00");	
					}
					
					//毛利率
					if (allCost != 0 && allPrice != 0 && allCost != null) {
						$("#all_mll").html(((allPrice*100-allCost*100)/allPrice).toFixed(2)+'%');
					}else{
						$("#all_mll").html("0.00%")
					}
					
					if(allCost != null && allCost != 0 && allCostSale != null && allCostSale != 0){
						$("#all_cost_sale").html(((allCostSale*100-allCost*100)/allCostSale).toFixed(2)+'%');
					}else{
						$("#all_cost_sale").html("0.00%")
					}
					
					$(".addtitle").bind("click",function(){
						var frontPageId = $(window.parent.document).find('.active').eq(1).attr('id');
				        var newPageId;
				        var tabTitle = $(this).attr('tabTitle');
				        if (typeof(tabTitle) == 'undefined') {
				            alert('参数错误');
				        } else {
				            tabTitle = $.parseJSON(tabTitle);
				        }
				        var id = tabTitle.num;
				        // var id = 'index' + Date.parse(new Date()) + Math.floor(Math.random()*1000);
				        var name = tabTitle.title;
				        var uri = tabTitle.link;
				        var closable = 1;
				        var item = { 'id': id, 'name': name, 'url': uri, 'closable': closable == 1 ? true : false };
				        self.parent.closableTab.addTab(item);
				        self.parent.closableTab.resizeMove();
				        $(window.parent.document).find('.active').eq(1).children('iframe').attr('data-frontpageid', frontPageId);
					});
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

	function toWarehouseDetail(){
		$.ajax({
			async:true,
			url:page_url+'/order/saleorder/toWarehouseDetail.do',
			data:{"saleorderId":${saleorder.saleorderId}},
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code==0){
					var frontPageId = $(window.parent.document).find('.active').eq(1).attr('id');
					var id = 0;
					var uri = page_url+'/warehouse/warehousesout/detailJump.do?saleorderId=${saleorder.saleorderId}';
					var item = { 'id': id, 'name': "查看出库记录", 'url': uri, 'closable': true };
					self.parent.closableTab.addTab(item);
					self.parent.closableTab.resizeMove();
					$(window.parent.document).find('.active').eq(1).children('iframe').attr('data-frontpageid', frontPageId);
				}else{
					layer.alert("出库记录为空,无法查看")
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	}
</script>
	<div class="content mt10 ">
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">基本信息</div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">订单号</td>
                        <td>${saleorder.saleorderNo}</td>
                        <td class="table-smaller" ${saleorder.status}>订单状态</td>
                        <td>
                            <c:choose>
								<c:when test="${saleorder.status eq 0}">待确认</c:when>
								<c:when test="${saleorder.status eq 1}">进行中</c:when>
								<c:when test="${saleorder.status eq 2}">已完结</c:when>
								<c:when test="${saleorder.status eq 3}">已关闭</c:when>
								<c:when test="${saleorder.status eq 4}">待用户确认</c:when>
							</c:choose>
                        </td>
                    </tr>
                   <%--  <tr>
                        <td>创建者</td>
                        <td>${saleorder.creatorName}</td>
                        <td>创建时间</td>
                        <td><date:date value ="${saleorder.addTime}"/></td>
                    </tr> --%>
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
                        <td></td>
                        <td></td>
                        <%-- <td>生效时间</td>
                        <td><date:date value ="${saleorder.validTime}"/></td> --%>
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
								<c:when test="${saleorder.lockedStatus eq 1}">已锁定(<span class="font-red">${saleorder.lockedReason}</span>)</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>收款状态</td>
                        <td>
                        	<c:choose>
								<c:when test="${saleorder.paymentStatus eq 0}">未收款</c:when>
								<c:when test="${saleorder.paymentStatus eq 1}">部分收款</c:when>
								<c:when test="${saleorder.paymentStatus eq 2}">全部收款</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                        <td>开票状态</td>
                        <td>
                            <c:choose>
								<c:when test="${saleorder.invoiceStatus eq 0}">未开票</c:when>
								<c:when test="${saleorder.invoiceStatus eq 1}">部分开票</c:when>
								<c:when test="${saleorder.invoiceStatus eq 2}">全部开票</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>发货状态</td>
                        <td>
                        	<c:choose>
								<c:when test="${saleorder.deliveryStatus eq 0}">未发货</c:when>
								<c:when test="${saleorder.deliveryStatus eq 1}">部分发货</c:when>
								<c:when test="${saleorder.deliveryStatus eq 2}">全部发货</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                        <td>到货状态</td>
                        <td>
                            <c:choose>
								<c:when test="${saleorder.arrivalStatus eq 0}">未到货</c:when>
								<c:when test="${saleorder.arrivalStatus eq 1}">部分到货</c:when>
								<c:when test="${saleorder.arrivalStatus eq 2}">全部到货</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
                        </td>
                    </tr>
					<tr>
						<td>是否打印随货出库单</td>
						<td>
							<c:choose>
								<c:when test="${saleorder.isPrintout ne 0}">是</c:when>
								<c:otherwise>否</c:otherwise>
							</c:choose>
						</td>
						<td>随货出库单是否带价格</td>
						<td>
							<c:choose>
								<c:when test="${saleorder.isPrintout eq 1}">是</c:when>
								<c:when test="${saleorder.isPrintout eq 2}">否</c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
						</td>
					</tr>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
					客户信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">客户名称</td>
                        <td>
                            <div class="customername pos_rel">
                                <span class="font-blue mr4">
	                                <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewcustomer${customer.traderCustomerId}",
											"link":"./trader/customer/baseinfo.do?traderCustomerId=${customer.traderCustomerId}&traderId=${saleorder.traderId}","title":"客户信息"}'>
	                            		${saleorder.traderName}
	                            	</a>
                                </span>
                                <i class="iconbluemouth"></i><div class="pos_abs customernameshow mouthControlPos">
					                                    报价次数：${customer.quoteCount} <br>
					                                    交易次数：${customer.buyCount} <br>
					                                    交易金额：${customer.buyMoney} <br>
					                                    上次交易日期：<date:date value="${customer.lastBussinessTime}" format="yyyy-MM-dd" /> <br>
					                                    归属销售：${customer.ownerSale}
                                </div>
                            </div>
                        </td>
                       <%--  <td class="table-smaller">地区</td>
                        <td>${saleorder.traderArea}</td>
                    </tr>
                    <tr>
                        <td>客户类型</td>
                        <td>${saleorder.customerTypeStr}</td> --%>
                        <td>客户性质</td>
                        <td>${saleorder.customerNatureStr}</td>
                    </tr>
                    <%-- <tr>
                        <td>联系人</td>
                        <td>${saleorder.traderContactName}</td>
                        <td>电话</td>
                        <td>
	                        ${saleorder.traderContactTelephone}
                        </td>
                    </tr>
                    <tr>
                        <td>手机</td>
                        <td>
                        	${saleorder.traderContactMobile}
                        </td>
                        <td>注册帐号</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>联系地址</td>
                        <td colspan="3">${saleorder.traderAddress}</td>
                    </tr> --%>
                    <tr>
                        <td>客户备注</td>
                        <td colspan="3">${saleorder.traderComments}</td>
                    </tr>
                    <tr>
						<td class="wid30">营业执照</td>
						<td class="text-left" colspan="3">
							<c:choose>
								<c:when test="${business ne null && business.uri ne null}">
									<a href="http://${business.domain}${business.uri}" target="_blank">营业执照</a>
								</c:when>
								<c:otherwise>
							   		营业执照
							   </c:otherwise>
							</c:choose>
							&nbsp;&nbsp;&nbsp;&nbsp; 有效期： 
							<date:date value="${business.begintime}" format="yyyy-MM-dd" /> 
							<c:if test="${business ne null && business.endtime eq null}">-无限期</c:if>
							<c:if test="${business.endtime ne null}">-<date:date value="${business.endtime}" format="yyyy-MM-dd" /></c:if>
							&nbsp;&nbsp;&nbsp;&nbsp; 
							<c:if test="${business.endtime ne null && business.endtime lt now }"><span style="color: red">（已过期）</span></c:if> 
							<c:if test="${business.isMedical eq 1}">含有医疗器械</c:if>
						</td>
					</tr>
					<tr>
						<td class="wid30">税务登记证</td>
						<td class="text-left" colspan="3">
							<c:choose> 
							   <c:when test="${tax ne null && tax.uri ne null}">
							   		 <a href="http://${tax.domain}${tax.uri}" target="_blank">税务登记证</a>
							   </c:when>
							   <c:otherwise>
							   		税务登记证
							   </c:otherwise>  
							</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;
                        	有效期：  <date:date value ="${tax.begintime}" format="yyyy-MM-dd"/>
		                        	<c:if test="${tax ne null && tax.endtime eq null}">-无限期</c:if>
			                        <c:if test="${tax.endtime ne null}">-<date:date value ="${tax.endtime}" format="yyyy-MM-dd"/></c:if>
			                        <c:if test="${tax.endtime ne null && tax.endtime lt now }"><span style="color: red">（已过期）</span></c:if>
						</td>
					</tr>
					<tr>
						<td class="wid30">组织机构代码证</td>
						<td class="text-left" colspan="3">
							<c:choose> 
							   <c:when test="${orga ne null && orga.uri ne null}">
							   		 <a href="http://${orga.domain}${orga.uri}" target="_blank">组织机构代码证</a>
							   </c:when>
							   <c:otherwise>
							   		组织机构代码证
							   </c:otherwise>  
							</c:choose> &nbsp;&nbsp;&nbsp;&nbsp;
                       		有效期：<date:date value ="${orga.begintime}" format="yyyy-MM-dd"/>
	                        		<c:if test="${orga ne null && orga.endtime eq null}">-无限期</c:if>
		                        	<c:if test="${orga.endtime ne null}">-<date:date value ="${orga.endtime}" format="yyyy-MM-dd"/></c:if>
		                        	<c:if test="${orga.endtime ne null && orga.endtime lt now }"><span style="color: red">（已过期）</span></c:if> 
						</td>
					</tr>
					<c:if test="${customerProperty eq 465}"><!-- 分销 -->
						<tr>
							<td class="wid30">医疗器械二类备案凭证</td>
							<td class="text-left" colspan="3">
								<c:choose>
									<c:when test="${twoMedicalList ne null}">
										<c:forEach items="${twoMedicalList }" var="twoMedical" varStatus="st">
											   <c:if test="${st.index == 0}">
											   	<c:set var="twoBeginTime" value="${twoMedical.begintime}"></c:set>
											   	<c:set var="twoEndTime" value="${twoMedical.endtime}"></c:set>
											   	<c:set var="sn" value="${twoMedical.sn}"></c:set>
											   </c:if>
										   	<c:if test="${twoMedical.uri ne null && not empty twoMedical.uri}">
										   		 <a href="http://${twoMedical.domain}${twoMedical.uri}" target="_blank">医疗器械二类备案凭证 - ${st.index + 1}</a>&nbsp;&nbsp;
										   	</c:if>
								   		</c:forEach>
									</c:when>
									<c:otherwise>
								   		医疗器械二类备案凭证
								   </c:otherwise>
								</c:choose>&nbsp;&nbsp;&nbsp;&nbsp; 有效期：
									<date:date value="${twoBeginTime} " format="yyyy-MM-dd" /> 
								<c:if test="${twoMedicalList ne null and twoEndTime eq null and not empty twoMedicalList}">-无限期</c:if>
								<c:if test="${twoEndTime ne null}">-<date:date value="${twoEndTime}" format="yyyy-MM-dd" /></c:if>
								&nbsp;&nbsp;&nbsp;&nbsp; 许可证编号：${sn} 
								<c:if test="${twoEndTime ne null && twoEndTime lt now }"><span style="color: red">（已过期）</span></c:if>
							</td>
						</tr>
						
						<tr>
							<td class="wid30">二类医疗资质详情</td>
							<td class="text-left" colspan="3">
								<c:if test="${not empty medicalCertificates }">
									<c:forEach items="${medicalCertificates }" var="mc">
										<c:if test="${mc.medicalCategoryId eq 194}">${mc.title}&nbsp;&nbsp;</c:if>
										<c:if test="${mc.medicalCategoryLevel eq 239 ||mc.medicalCategoryLevel eq 241}">${mc.title}（二类）&nbsp;&nbsp;</c:if>
									</c:forEach>
								</c:if></td>
						</tr>
						<tr>
							<td class="wid30">三类医疗资质</td>
							<td class="text-left" colspan="3">
								<c:choose>
									<c:when test="${threeMedical ne null && threeMedical.uri ne null}">
										<a href="http://${threeMedical.domain}${threeMedical.uri}" target="_blank">三类医疗资质</a>
									</c:when>
									<c:otherwise>
								   		三类医疗资质
								   </c:otherwise>
								</c:choose>
								&nbsp;&nbsp;&nbsp;&nbsp; 有效期：<date:date value="${threeMedical.begintime} " format="yyyy-MM-dd" /> 
								<c:if test="${threeMedical ne null && threeMedical.endtime eq null}">-无限期</c:if>
								<c:if test="${threeMedical.endtime ne null}">-<date:date value="${threeMedical.endtime} " format="yyyy-MM-dd" /></c:if>
								&nbsp;&nbsp;&nbsp;&nbsp;许可证编号：${threeMedical.sn} <c:if test="${threeMedical.endtime ne null && threeMedical.endtime lt now }">
								<span style="color: red">（已过期）</span>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="wid30">三类医疗资质详情</td>
							<td class="text-left" colspan="3">
								<c:if test="${not empty medicalCertificates }">
									<c:forEach items="${medicalCertificates }" var="mc">
										<c:if test="${mc.medicalCategoryId eq 194}">${mc.title}&nbsp;&nbsp;</c:if>
										<c:if
											test="${mc.medicalCategoryLevel eq 240 || mc.medicalCategoryLevel eq 241}">${mc.title}（三类）&nbsp;&nbsp;</c:if>
									</c:forEach>
								</c:if>
							</td>
						</tr>
					</c:if>
					<c:if test="${customerProperty eq 466}"><!-- 终端 -->
						<tr>
	                        <td class="table-smallest">医疗机构执业许可证</td>
	                        <td style="text-align: left;" colspan="3">
		                        <c:choose> 
								   <c:when test="${practiceList ne null }">
								   <c:forEach items="${practiceList }" var="practice" varStatus="st">
								   <c:if test="${st.index == 0}">
								   	<c:set var="beginTime" value="${practice.begintime}"></c:set>
								   	<c:set var="endTime" value="${practice.endtime}"></c:set>
								   	<c:set var="sn" value="${practice.sn}"></c:set>
								   </c:if>
								   	<c:if test="${practice.uri ne null && not empty practice.uri}">
								   		 <a href="http://${practice.domain}${practice.uri}" target="_blank">医疗机构执业许可证 - ${st.index + 1}</a>&nbsp;&nbsp;
								   	</c:if>
								   </c:forEach>
								   </c:when>
								   <c:otherwise>
								   		医疗机构执业许可证&nbsp;
								   </c:otherwise>  
								</c:choose>&nbsp;&nbsp;&nbsp;
	                        	有效期：<date:date value ="${beginTime} " format="yyyy-MM-dd"/>
	                        			<c:if test="${practiceList ne null  && endTime eq null && not empty practiceList}">-无限期</c:if>
	                        			<c:if test="${endTime ne null}">
	                        				-<date:date value ="${endTime} " format="yyyy-MM-dd"/>
	                        			</c:if>&nbsp;&nbsp;&nbsp;&nbsp; 许可证编号：${sn}
	                        			 <c:if test="${endTime ne null && endTime ne 0 && endTime lt now }"><span style="color: red">（已过期）</span></c:if>
	                        </td>
	                    </tr>
					</c:if>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">产品信息</div>
            </div>
            <table class="table  table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="wid4">序号</th>
                        <th class="wid12">产品名称</th>
                        <th>品牌</th>
                        <th class="wid8">型号</th>
                        <th class="wid5">单价</th>
                        <th class="wid4">数量</th>
                        <th class="wid4">单位</th>
                        <th >销售总额</th>
						<th class="hidden-temp">采购总额</th>
						<th >货期</th>
						<th class="wid4">直发</th>
						<th class="hidden-temp">采购单</th>
						<th class="hidden-temp">成本价</th>
						<th class="hidden-temp">毛利率</th>

						<th >含安调</th>
                        <th >类别管制</th>
                        <th>产品备注</th>
                        <th>内部备注</th>
                        <th >采购状态</th>
                        <th >到库状态</th>
						<!-- add by Tomcat.Hui 2019/12/5 13:50 .Desc: VDERP-1325 分批开票 发货数量 收货数量. start -->
                        <th >发货数量</th>
                        <th >收货数量</th>
						<!-- add by Tomcat.Hui 2019/12/5 13:50 .Desc: VDERP-1325 分批开票 发货数量 收货数量. end -->
                        <!-- add by Tomcat.Hui 2019/11/28 15:34 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. start -->
                        <th class="wid10">已开票/开票中数量</th>
						<th  class="hidden-temp">采购单(新)</th>
						<th  class="hidden-temp">成本价(新)</th>

						<!-- add by Tomcat.Hui 2019/11/28 15:34 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. end -->
                    </tr>
                </thead>
                <tbody>
                	<c:set var="num" value="0"></c:set>
					<c:set var="totleMoney" value="0.00"></c:set>
					<c:set var="ordermll" value="0.00"></c:set>
					<!-- 总运费 -->
                    <c:set var="allamount" value="0.00"></c:set>
                    <!-- 总数量 -->
               		<c:set var="allarrivalnum" value="0"></c:set>
	                	<c:forEach var="express" items="${expressList}">
	                		<c:set var="amount" value="0.00"></c:set>
	                		<c:set var="arrivalnum" value="0"></c:set>
	                 	<c:forEach var="expressDetails" items="${express.expressDetail}">
	                 		<c:set var="amount" value="${amount + expressDetails.amount}"></c:set>
	                 		<c:set var="arrivalnum" value="${arrivalnum + expressDetails.num}"></c:set>
	                 	</c:forEach>
	                 	<c:set var="allamount" value="${allamount + amount}"></c:set>
	                 	<c:set var="allarrivalnum" value="${allarrivalnum + arrivalnum}"></c:set>
	                	</c:forEach>
	                	
                	<c:set var="allnum" value="0"></c:set>
	                	<c:forEach var="bgv" items="${saleorderGoodsList}" varStatus="num">
	                 		<c:set var="allnum" value="${allnum + bgv.num}"></c:set>
	                 		<c:set var="allDeliveryNum" value="${allDeliveryNum + bgv.deliveryNum}"></c:set>
	                 	</c:forEach>	
	                 					
                	<c:forEach var="list" items="${saleorderGoodsList}" varStatus="staut">
                		<c:if test="${list.isDelete eq 0}">
							<c:set var="num" value="${num + list.num}"></c:set>
							<!-- 耗材订单 总额计算变更 2018-02-22    begin -->
							<c:choose>
								<c:when test="${saleorder.orderType eq 5}">
									<c:set var="totleMoney" value="${totleMoney + list.maxSkuRefundAmount - list.afterReturnAmount}" />
								</c:when>
								<c:otherwise>
									<!-- 总额计算方式变更 2018-12-27 产品Ada 总额 = 单价*(订单商品数量-订单商品售后数量) -->
									<c:set var="totleMoney" value="${totleMoney + (list.price * (list.num - list.afterReturnNum))}" />
								</c:otherwise>
							</c:choose>
							<!-- 总额计算变更 2018-02-22 耗材订单/非耗材订单    end -->
						</c:if>
	                    <tr class=" <c:if test="${list.isDelete eq 1}"> caozuo-grey</c:if>  J-skuPriceInfo-tr"    detailgoodsid="${list.saleorderGoodsId}">
	                        <td>${staut.count}</td>
	                        <td class="text-left">
	                            <div class="customername pos_rel">
	                                <c:choose>
										<c:when test="${list.isDelete eq 1}">
											<span>${newSkuInfosMap[list.sku].SHOW_NAME}<br/></span>
			                                <span>${newSkuInfosMap[list.sku].SKU_NO} <br>${newSkuInfosMap[list.sku].MATERIAL_CODE}</span>
										</c:when>
										<c:otherwise>
											<span class="font-blue"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${newSkuInfosMap[list.sku].SHOW_NAME}</a>&nbsp;<i class="iconbluemouth contorlIcon"></i><br/></span>
			                                <span>
			                                	${newSkuInfosMap[list.sku].SKU_NO} <br>
			                                	${newSkuInfosMap[list.sku].MATERIAL_CODE}
			                                </span>

											<c:set var="skuNo" value="${list.sku}"></c:set>
											<%@ include file="../../common/new_sku_common_tip.jsp" %>

										</c:otherwise>
									</c:choose>
	                            </div>
	                        </td>
	                        <td>${newSkuInfosMap[list.sku].BRAND_NAME}</td>
	                        <td>${newSkuInfosMap[list.sku].MODEL}</td>
	                        <td>${list.price}</td>
							<td>
	                        	<c:choose>
	                        		<c:when test="${empty list.afterReturnNum or list.afterReturnNum eq 0}">
	                        			${list.num}
	                        		</c:when>
	                        		<c:otherwise>
			                        	<div class="customername pos_rel">
				                        	<span>
				                        		${list.num - list.afterReturnNum}
				                        		<i class="iconredsigh ml4 contorlIcon"></i>
				                        	</span>
				                        	<div class="pos_abs customernameshow">原值：${list.num}</div>
			                        	</div>
	                        		</c:otherwise>
	                        	</c:choose>
	                        </td>
	                        <td>${newSkuInfosMap[list.sku].UNIT_NAME}</td>
	                        <td>
	                        	<c:choose>
									<c:when test="${saleorder.orderType eq 5}">
										<fmt:formatNumber type="number" value="${list.maxSkuRefundAmount - list.afterReturnAmount}" pattern="0.00" maxFractionDigits="2" />
									</c:when>
									<c:otherwise>
										<!-- 总额计算方式变更 2018-12-27 产品Ada 总额 = 单价*(订单商品数量-订单商品售后数量) -->
										<fmt:formatNumber type="number" value="${list.price * (list.num - list.afterReturnNum)}" pattern="0.00" maxFractionDigits="2" />
									</c:otherwise>
								</c:choose>	
	                        </td>
	                        
	                        <td  class="hidden-temp"><span id="totalCost_${list.saleorderGoodsId}"></span></td>
	                        <td>${list.deliveryCycle}</td>
	                        <td>
	                            <div class="customername pos_rel">
	                                <span>
	                                <c:choose>
										<c:when test="${list.deliveryDirect eq 0}">否</c:when>
										<c:otherwise>
										是
										<i class="iconbluesigh ml4 contorlIcon"></i></span>
		                                <div class="pos_abs customernameshow">直发原因：${list.deliveryDirectComments}</div>
										</c:otherwise>
									</c:choose>
	                            </div>
	                        
	                        <td  class="hidden-temp">
	                            <span id="buyorderListStr_${list.saleorderGoodsId}"></span>
	                        </td>
	                        <td  class="hidden-temp"><span id="costPrice_${list.saleorderGoodsId}"></span></td>
	                        <td  class="hidden-temp"><span id="mll_${list.saleorderGoodsId}"></span></td>
	                        <td>
	                        	<c:choose>
									<c:when test="${list.haveInstallation eq 0}">否</c:when>
									<c:otherwise>是</c:otherwise>
								</c:choose>
	                        </td>
	                        <td>---</td>
	                        <td>${list.goodsComments}</td>
	                        <td>${list.insideComments}</td>
	                        <td><span id="cgztStr_${list.saleorderGoodsId}"></span></td>
	                        <td><span id="dkztStr_${list.saleorderGoodsId}"></span></td>
							<!-- add by Tomcat.Hui 2019/12/5 13:50 .Desc: VDERP-1325 分批开票 发货数量 收货数量. start -->
	                        <td>
								<c:forEach var="express" items="${expresseList}" varStatus="staut">
									<c:if test="${list.saleorderGoodsId eq express.saleOrderGoodsId}">${express.sendNum}</c:if>
								</c:forEach>
	                        </td>
							<td>
								 <c:forEach var="express" items="${expresseList}" varStatus="staut">
									 <c:if test="${list.saleorderGoodsId eq express.saleOrderGoodsId}">${express.arriveNum}</c:if>
								 </c:forEach>
	                        </td>
							<!-- add by Tomcat.Hui 2019/12/5 13:50 .Desc: VDERP-1325 分批开票 发货数量 收货数量. end -->
                            <!-- add by Tomcat.Hui 2019/11/28 15:34 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. start -->
                            <td>${list.invoicedNum}/${list.appliedNum}</td>
							<td class="jbuyorder   hidden-temp "> </td>
							<td class="jbuyorderprice   hidden-temp "> </td>
                            <!-- add by Tomcat.Hui 2019/11/28 15:34 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. end -->
	                    </tr>
                    </c:forEach>
                    <tr style="background: #eaf2fd;">
                        <td colspan="25" class="text-left">
                                                                  总件数
                            <span class="font-red">${num}</span>，
                                                                   订单原金额
                            <span class="font-red"><fmt:formatNumber type="number" value="${saleorder.totalAmount}" pattern="0.00" maxFractionDigits="2" /></span> ,
                                                                   订单实际金额
                            <span class="font-red"><fmt:formatNumber type="number" value="${saleorderDataInfo.realAmount}" pattern="0.00" maxFractionDigits="2" /></span>
							<span  class="hidden-temp">，
                                                                   采购总额
                            <span class="font-red" id="allCost">0.00</span>，
                                                                   运费总额
                            <span class="font-red"><fmt:formatNumber type="number" value="${allamount}" pattern="0.00" maxFractionDigits="2" /></span>，
                                                                   订单毛利率
                            <span class="font-red" id="all_mll">0%</span>，
                            	已采购产品累计毛利率
                            <span class="font-red" id="all_cost_sale">0%</span>
							</span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts content1">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
					付款计划
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th style="width:150px">计划</th>
                        <th style="width:150px">计划内容</th>
                        <th style="width:150px">支付金额</th>
                        <th>备注</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${saleorder.paymentType eq 419}">
						<tr>
							<td>第一期</td>
							<td>预付款</td>
							<td>${saleorder.prepaidAmount}</td>
							<td>
								<c:forEach var="list" items="${paymentTermList}" varStatus="status">
									<c:if test="${list.sysOptionDefinitionId eq saleorder.paymentType}">${list.title}</c:if>
								</c:forEach>
							</td>
						</tr>
					</c:if>
					<c:if
						test="${saleorder.paymentType ne 419 and saleorder.paymentType ne 424}">
						<tr>
							<td>第一期</td>
							<td>预付款</td>
							<td>${saleorder.prepaidAmount}</td>
							<td>
								<c:forEach var="list" items="${paymentTermList}" varStatus="status">
									<c:if test="${list.sysOptionDefinitionId eq saleorder.paymentType}">${list.title}</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td>第二期</td>
							<td>帐期付款</td>
							<td>${saleorder.accountPeriodAmount}</td>
							<td>到货后${customer.periodDay}天内支付 <c:if test="${quote.logisticsCollection eq 1}"> / 物流代收</c:if></td>
						</tr>
					</c:if>
					<c:if test="${saleorder.paymentType eq 424}">
						<tr>
							<td>第一期</td>
							<td>预付款</td>
							<td>${saleorder.prepaidAmount}</td>
							<td>
								<c:forEach var="list" items="${paymentTermList}" varStatus="status">
									<c:if test="${list.sysOptionDefinitionId eq saleorder.paymentType}">${list.title}</c:if>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td>第二期</td>
							<td>帐期付款</td>
							<td>${saleorder.accountPeriodAmount}</td>
							<td>到货后${customer.periodDay}天内支付 
								<c:if test="${saleorder.logisticsCollection eq 1}">
		                        	 / 物流代收
	                        	</c:if>
							</td>
						</tr>
						<tr>
							<td>第三期</td>
							<td>尾款</td>
							<td>${saleorder.retainageAmount}</td>
							<td>到货后${saleorder.retainageAmountMonth}个月内支付</td>
						</tr>
					</c:if>
					<tr>
						<td>收款备注</td>
						<td colspan="3"><span style="color:red;">${saleorder.paymentComments}</span></td>
					</tr>
                    <!-- <tr style="background: #eaf2fd;">
                        <td colspan="4" class="text-left">帐期付款：帐期付款是我司向客户提供的信用付款方式，您需要在约定时间内支付帐期额度的金额。本合同中帐期以合同开始发货为结算开始时间。</td>
                    </tr> -->
                </tbody>
            </table>
        </div>
		<div class="parts content1">
            <div class="title-container title-container-green">
                <div class="table-title nobor">
					交易信息
                </div>
                <c:if test="${saleorder.lockedStatus eq 0 && saleorder.validStatus eq 1 }"><!-- 未锁定 且 已生效-->

	                <div class="title-click nobor pop-new-data" layerParams='{"width":"650px","height":"400px","title":"新增交易记录","link":"${pageContext.request.contextPath}/finance/capitalbill/addCapitalBill.do?saleorderId=${saleorder.saleorderId}"}'>
						新增交易记录
	                </div>
                	<div class="title-click nobor pop-new-data" layerParams='{"width":"600px","height":"300px","title":"新增交易记录","link":"${pageContext.request.contextPath}/finance/capitalbill/addAlipayCapitalBill.do?saleorderId=${saleorder.saleorderId}"}'>
						对私提现
	                </div>

                </c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th style="width: 180px">记账编号</th>
                        <th style="width: 80px">业务类型</th>
                        <th>交易金额</th>
                        <th style="width: 130px">交易时间</th>
                        <th>交易主体</th>
                        <th>交易方式</th>
                        <th style="width: 240px">交易名称</th>
                        <th>交易备注</th>
                        <th>操作人</th>
                        <th style="width: 130px">操作时间</th>
                    </tr>
                </thead>
                <tbody>
                	<c:if test="${not empty capitalBillList}">
                		<c:forEach items="${capitalBillList}" var="list" varStatus="">
	                    <tr>
	                        <td>${list.capitalBillNo}</td>
	                        <td>
	                        	<c:forEach var="typeList" items="${bussinessTypes}" varStatus="">
									<c:if test="${typeList.sysOptionDefinitionId eq list.capitalBillDetail.bussinessType}">${typeList.title}</c:if>
								</c:forEach>
	                        </td>
	                        <td><fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" /></td>
	                        <td>
	                        	<c:if test="${list.traderTime != 0}">
	                        	<date:date value="${list.traderTime}" />
	                        	</c:if>
	                        </td>
	                        <td>
	                        	<c:if test="${list.traderSubject == 1}">
	                        	对公
	                        	</c:if>
	                        	<c:if test="${list.traderSubject == 2}">
	                        	对私
	                        	</c:if>
	                        </td>
	                        <td>
	                        	<c:forEach var="modeList" items="${traderModes}" varStatus="">
									<c:if test="${modeList.sysOptionDefinitionId eq list.traderMode}">${modeList.title}</c:if>
								</c:forEach>
	                        </td>
	                        <td>${list.payer}</td>
	                        <td class="text-left">${list.comments}</td>
	                        <td>${list.creatorName}</td>
	                        <td>
	                        	<c:if test="${list.addTime != 0}">
	                        	<date:date value="${list.addTime}" />
	                        	</c:if>
	                        </td>
	                    </tr>
	                    </c:forEach>
                    </c:if>
                	<c:if test="${empty capitalBillList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='10'>暂无交易信息</td>
					    </tr>
		        	</c:if>
		        	<tr style="background: #eaf2fd;">
                        <td colspan="10" style="text-align: left;">
                        	订单原金额：<fmt:formatNumber type="number" value="${saleorder.totalAmount}" pattern="0.00" maxFractionDigits="2" />
                        	&nbsp;&nbsp;&nbsp;&nbsp;
							订单实际金额：<fmt:formatNumber type="number" value='${saleorderDataInfo["realAmount"]}' pattern="0.00" maxFractionDigits="2" />
							&nbsp;&nbsp;
                        	<%-- 客户已付款金额：<fmt:formatNumber type="number" value='${saleorderDataInfo["receivedAmount"]}' pattern="0.00" maxFractionDigits="2" />
                        	&nbsp;&nbsp;&nbsp;&nbsp; --%>
                        	<span style="color:red">未收金额：<fmt:formatNumber type="number" value='${saleorderDataInfo["realAmount"] - (saleorderDataInfo["paymentAmount"] + saleorderDataInfo["periodAmount"]  - saleorderDataInfo["lackAccountPeriodAmount"] - saleorderDataInfo["refundBalanceAmount"])}' pattern="0.00" maxFractionDigits="2" /></span>
                        	&nbsp;=&nbsp;
                        	订单实际金额：<fmt:formatNumber type="number" value='${saleorderDataInfo["realAmount"]}' pattern="0.00" maxFractionDigits="2" />
                        	&nbsp;-&nbsp;
                        	客户实付金额：<fmt:formatNumber type="number" value='${saleorderDataInfo["paymentAmount"] + saleorderDataInfo["periodAmount"] - saleorderDataInfo["lackAccountPeriodAmount"] - saleorderDataInfo["refundBalanceAmount"]}' pattern="0.00" maxFractionDigits="2" />
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-yellow">
                <div class="table-title nobor">
                   	 收票信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">收票客户</td>
                        <td>
                        	<span style="color: red">${saleorder.invoiceTraderName}</span>
                        </td>
                        <td class="table-smaller">收票联系人</td>
                        <td>${saleorder.invoiceTraderContactName}</td>
                    </tr>
                    <tr>
                        <td>电话</td>
                        <td>
	                        <%-- <c:if test="${not empty saleorder.invoiceTraderContactTelephone}">
		                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${saleorder.invoiceTraderContactTelephone}',${saleorder.traderId},1,2,${saleorder.saleorderId},${saleorder.invoiceTraderContactId});"></i>
	                        </c:if> --%>
	                        ${saleorder.invoiceTraderContactTelephone}
                        </td>
                        <td>手机</td>
                        <td>
	                        <%-- <c:if test="${not empty saleorder.invoiceTraderContactMobile}">
		                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${saleorder.invoiceTraderContactMobile}',${saleorder.traderId},1,2,${saleorder.saleorderId},${saleorder.invoiceTraderContactId});"></i>
	                        </c:if> --%>
	                        ${saleorder.invoiceTraderContactMobile}
                        </td>
                    </tr>
                    <tr>
                        <td>发票类型</td>
                        <td>
                        	<span style="color:red;">
	                        	<c:forEach var="list" items="${invoiceTypes}">
			                    	<c:if test="${saleorder.invoiceType == list.sysOptionDefinitionId}">${list.title}</c:if>
			                    </c:forEach>
			                    <c:choose>
			                    	<c:when test="${saleorder.isSendInvoice eq 0}">（不寄送）</c:when>
			                    	<c:otherwise>（寄送）</c:otherwise>
			                    </c:choose>
                        	</span>
                        </td>
                        <td>税务登记号</td>
                        <td><span style="color:red;">${traderFinance.taxNum}</span></td>
                    </tr>
                    <tr>
                    	<td>注册地址</td>
                        <td><span style="color:red;">${traderFinance.regAddress}</span></td>
                        <td>注册电话</td>
                        <td><span style="color:red;">${traderFinance.regTel}</span></td>
                    </tr>
                    <tr>
                    	<td>开户银行</td>
                        <td><span style="color:red;">${traderFinance.bank}</span></td>
                        <td>银行账号</td>
                        <td><span style="color:red;">${traderFinance.bankAccount}</span></td>
                    </tr>
                    <tr>
                    	<td>收票地区</td>
                        <td><span style="color:red;">${saleorder.invoiceTraderArea}</span></td>
                        <td>发票状态</td>
                        <td>
                        	<span style="color:green;">
	                        	<c:choose>
	                        		<c:when test="${saleorder.invoiceStatus eq 0}">未开票</c:when>
	                        		<c:when test="${saleorder.invoiceStatus eq 1}">部分开票</c:when>
	                        		<c:otherwise>已开票</c:otherwise>
	                        	</c:choose>
                        	</span>
                        </td>
                    </tr>
                    <tr>
                    	<td>收票地址</td>
                        <td colspan="3"><span style="color:red;">${saleorder.invoiceTraderAddress}</td>
                    </tr>
                    <tr>
                        <td>开票方式</td>
                        <td>
                        	<c:choose>
		                    	<c:when test="${saleorder.invoiceMethod eq 1}">手动纸质开票</c:when>
		                    	<c:when test="${saleorder.invoiceMethod eq 2}">自动纸质开票</c:when>
		                    	<c:when test="${saleorder.invoiceMethod eq 3}">自动电子发票</c:when>
		                    </c:choose>
                        </td>
                        <td>延缓开票</td>
                        <td><span style="color:red;">
		                    <c:choose>
		                    	<c:when test="${saleorder.isDelayInvoice eq 1}">是</c:when>
		                    	<c:otherwise>否</c:otherwise>
		                    </c:choose>
		                    </span>
                        </td>
                    </tr>
                    <tr>
                        <td>开票备注</td>
                        <td colspan="3"><span style="color:red;">${saleorder.invoiceComments}</span></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <c:if test="${saleorder.companyId ne 1}">
        <div class="parts">
			<div class="title-container">
				<div class="table-title nobor">开票申请</div>
			</div>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th>操作人</th>
						<th>操作时间</th>
						<th>操作事项</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${null!=historicActivityInstance}">
						<c:forEach var="hi" items="${historicActivityInstance}" varStatus="status">
							<c:if test="${not empty  hi.activityName}">
								<tr>
									<td>
										<c:choose>
											<c:when test="${hi.activityType == 'startEvent'}"> 
												${startUser}
											</c:when>
											<c:when test="${hi.activityType == 'intermediateThrowEvent'}">
											</c:when>
											<c:otherwise>
												<c:if test="${historicActivityInstance.size() == status.count}">
													${verifyUsers}
												</c:if>
												<c:if test="${historicActivityInstance.size() != status.count}">
													${hi.assignee}  
												</c:if>
											</c:otherwise>
										</c:choose>
									</td>
									<td><fmt:formatDate value="${hi.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td>
										<c:choose>
											<c:when test="${hi.activityType == 'startEvent'}"> 
												开始
											</c:when>
											<c:when test="${hi.activityType == 'intermediateThrowEvent'}"> 
												结束
											</c:when>
											<c:otherwise>   
												${hi.activityName}  
											</c:otherwise>
										</c:choose>
									</td>
									<td class="font-red">${commentMap[hi.taskId]}</td>
								</tr>
							</c:if>
						</c:forEach>
					</c:if>
					<!-- 查询无结果弹出 -->
	
					<c:if test="${empty historicActivityInstance}">
						<!-- 查询无结果弹出 -->
						<tr>
							<td colspan="4">暂无审核记录。</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		</c:if>
		<c:if test="${saleorder.companyId eq 1}">
        <div class="parts">
            <div class="title-container title-container-yellow">
                <div class="table-title nobor">
					开票申请
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <!-- add by Tomcat.Hui 2019/11/21 11:48 .Desc: VDERP-1325 分批开票 开票申请模块 start. -->
                        <th>申请人</th>
                        <th>申请时间</th>
                        <th>申请方式</th>
                        <th>开票方式</th>
                        <th>是否提前开票</th>
                        <th>提前开票原因</th>
                        <th>操作事项</th>
                        <th>备注</th>
                        <!-- add by Tomcat.Hui 2019/11/21 11:48 .Desc: VDERP-1325 分批开票 开票申请模块 end. -->
						<!-- add by Tomcat.Hui 2019/11/21 11:48 .Desc: VDERP-1325 分批开票 查看已申请发票详情信息 start. -->
						<th>操作</th>
						<!-- add by Tomcat.Hui 2019/11/21 11:48 .Desc: VDERP-1325 分批开票 查看已申请发票详情信息 start. -->
                    </tr>
                </thead>
                <tbody>
                    <c:set var="invoiceApplyAmount" value="0"></c:set>
                	<c:forEach var="list" items="${saleInvoiceApplyList}" varStatus="num">
                        <c:if test="${list.advanceValidStatus ne 2 and list.validStatus ne 2}">
                            <c:forEach var="detail" items="${list.invoiceApplyDetails}" varStatus="num">
                                <c:set var="invoiceApplyAmount" value="${invoiceApplyAmount + detail.totalAmount}"></c:set>
                            </c:forEach>
                        </c:if>
                		<tr>
	                        <td>${list.creatorNm}
	                        	<c:if test="${list.creator eq 0}">
	                        	自动申请
	                        	</c:if>
	                        </td>
	                        <td><date:date value="${list.addTime}" format="yyyy.MM.dd HH:mm:ss"/></td>
                            <!-- add by Tomcat.Hui 2019/11/21 11:48 .Desc: VDERP-1325 分批开票 开票申请模块 start. -->
                            <td>
								<c:if test="${list.applyMethod eq 0}">手动申请触发</c:if>
								<c:if test="${list.applyMethod eq 1}">定时任务触发</c:if>
								<!-- add by Tomcat.Hui 2019/12/30 16:29 .Desc: VDERP-1039 票货同行 start. -->
								<c:if test="${list.applyMethod eq 2}">票货同行物流部申请</c:if>
								<!-- add by Tomcat.Hui 2019/12/30 16:29 .Desc: VDERP-1039 票货同行 end. -->
                            </td>
                            <td>
								<c:if test="${list.isAuto eq 1}">手动纸质票</c:if>
								<c:if test="${list.isAuto eq 2}">自动纸质票</c:if>
								<c:if test="${list.isAuto eq 3}">自动电子票</c:if>
                            </td>
                            <!-- add by Tomcat.Hui 2019/11/21 11:48 .Desc: VDERP-1325 分批开票 开票申请模块 ends. -->
                            <td>
								<c:choose>
	                        		<c:when test="${list.isAdvance eq 0}">否</c:when>
	                        		<c:otherwise>是</c:otherwise>
	                        	</c:choose>
							</td>
							<td>${list.comments}</td>
	                        <td>
	                        	<c:choose>
	                        		<c:when test="${list.isAdvance eq 1}">
			                        	<c:choose>
			                        		<c:when test="${list.advanceValidStatus eq 0}">提前开票审核中</c:when>
			                        		<c:when test="${list.advanceValidStatus eq 1}">提前开票审核通过<br/>
			                        			<c:choose>
					                        		<c:when test="${list.validStatus eq 0}">开票申请审核中</c:when>
					                        		<c:when test="${list.validStatus eq 1}">开票申请审核通过</c:when>
					                        		<c:when test="${list.validStatus eq 2}">开票申请审核不通过</c:when>
					                        	</c:choose>
			                        		</c:when>
			                        		<c:when test="${list.advanceValidStatus eq 2}">提前开票审核不通过</c:when>
			                        	</c:choose>
	                        		</c:when>
	                        		<c:otherwise>
	                        			<c:choose>
			                        		<c:when test="${list.validStatus eq 0}">开票申请审核中</c:when>
			                        		<c:when test="${list.validStatus eq 1}">开票申请审核通过</c:when>
			                        		<c:when test="${list.validStatus eq 2}">开票申请审核不通过</c:when>
			                        	</c:choose>
	                        		</c:otherwise>
	                        	</c:choose>
							</td>
	                        <td>
	                        	<c:choose>
	                        		<c:when test="${list.isAdvance eq 1}">
	                        			${list.advanceValidComments}
	                        			<c:if test="${list.yyValidStatus ne 0}">
	                        				<br/>${list.yyValidComments}
	                        			</c:if>
	                        			<c:if test="${list.validStatus ne 0}">
	                        				<br/>${list.validComments}
	                        			</c:if>
	                        		</c:when>
	                        		<c:otherwise>
	                        			<c:if test="${list.yyValidStatus ne 0}">
	                        				<br/>${list.yyValidComments}
	                        			</c:if>
	                        			<c:if test="${list.validStatus ne 0}">
	                        				<br/>${list.validComments}
	                        			</c:if>
	                        		</c:otherwise>
	                        	</c:choose>
	                        </td>

							<!-- add by Tomcat.Hui 2019/11/21 11:48 .Desc: VDERP-1325 分批开票 查看已申请发票详情信息 start. -->
							<td>
								<span class="pop-new-data edit-user" layerParams='{"width":"80%","height":"600px","title":"查看申请","link":"../../finance/invoice/selectInvoiceItems.do?relatedId=${saleorder.saleorderId}&invoiceApplyId=${list.invoiceApplyId}&editFlag=false"}' >查看开票申请</span>
								<c:if test="${saleorder.lockedStatus eq 0 and list.invoiceStStatus eq 1}"><!-- 未锁定,未获取 -->
								    <!-- add by Tomcat.Hui 2019/12/5 18:18 .Desc: VDERP-1325 分批开票 新增发票展示逻辑. start -->
									<!-- add by Tomcat.Hui 2019/12/5 18:18 .Desc: VDERP-1325 分批开票 非提前开票且在审核中的才允许开票.  -->
									<c:if test="${saleorder.companyId eq 1 and list.validStatus eq 0 and list.isAdvance eq 0 }">
										<span class="pop-new-data edit-user" layerParams='{"width":"80%","height":"600px","title":"新增发票","link":"../../finance/invoice/addSaleInvoice.do?relatedId=${saleorder.saleorderId}&invoiceType=${saleorder.invoiceType}&invoiceApplyId=${list.invoiceApplyId}&editFlag=false"}' >新增发票</span>
									</c:if>
									<!-- add by Tomcat.Hui 2019/12/5 18:18 .Desc: VDERP-1325 分批开票 提前开票通过且在审核中的才允许开票.  -->
									<c:if test="${saleorder.companyId eq 1 and list.validStatus eq 0 and list.isAdvance eq 1 and list.advanceValidStatus eq 1 }">
										<span class="pop-new-data edit-user" layerParams='{"width":"80%","height":"600px","title":"新增发票","link":"../../finance/invoice/addSaleInvoice.do?relatedId=${saleorder.saleorderId}&invoiceType=${saleorder.invoiceType}&invoiceApplyId=${list.invoiceApplyId}&editFlag=false"}' >新增发票</span>
									</c:if>
									<!-- add by Tomcat.Hui 2019/12/5 18:18 .Desc: VDERP-1325 分批开票 新增发票展示逻辑. end -->
								</c:if>
							</td>
							<!-- add by Tomcat.Hui 2019/11/21 11:48 .Desc: VDERP-1325 分批开票 查看已申请发票详情信息 start. -->
	                    </tr>
                	</c:forEach>
					<!-- add by Tomcat.Hui 2019/11/21 11:48 .Desc: VDERP-1325 分批开票 开票申请模块 start. -->
                    <tr>
                        <td colspan="9" style="text-align: left; background: #eaf2fd;">
                            已申请开票金额：<fmt:formatNumber type="number" value="${invoiceApplyAmount}" pattern="0.00" maxFractionDigits="2" />
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <span style="color:red">未申请开票金额：<fmt:formatNumber type="number" value="${saleorderDataInfo['realAmount'] - invoiceApplyAmount}" pattern="0.00" maxFractionDigits="2" /></span>
                        </td>
                    </tr>
                    <!-- add by Tomcat.Hui 2019/11/21 11:48 .Desc: VDERP-1325 分批开票 开票申请模块 end. -->
                	<c:if test="${empty saleInvoiceApplyList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='9'>暂无开票申请记录</td>
					    </tr>
		        	</c:if>
                </tbody>
            </table>
        </div>
        </c:if>
        <div class="parts content1">
            <div class="title-container title-container-yellow">
                <div class="table-title nobor">
					发票信息
                </div>

            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="wid10">发票号</th>
                        <th>发票代码</th>
                        <th>票种</th>
                        <th>红蓝字</th>
                        <th class="wid10">发票金额</th>
                        <th>操作人</th>
                        <th class="wid14">操作时间</th>
                        <th>快递公司</th>
                        <th class="wid14">快递单号</th>
                        <th class="wid8">快递状态</th>
                        <th>备注</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:set var="invoiceAmount" value="0.00"></c:set><!-- 已开票总额 -->
                	<c:forEach var="list" items="${saleInvoiceList}" varStatus="num">
                		<tr>
	                        <td class="caozuo">
	                        	<c:if test="${empty list.invoiceNo}">
	                        		<span class="edit-user" onclick="batchDownEInvoice()">下载电子票</span>
	                        	</c:if>
	                        	<c:if test="${not empty list.invoiceNo}">${list.invoiceNo}</c:if>
	                        </td>
	                        <td>${list.invoiceCode}</td>
	                        <td>
								<c:forEach var="invoiceList" items="${invoiceTypes}" varStatus="status">
									<c:if test="${invoiceList.sysOptionDefinitionId eq list.invoiceType}">${invoiceList.title}</c:if>
								</c:forEach>
							</td>
	                        <td>
	                        	<c:choose>
									<c:when test="${list.colorType eq 1}">
										<c:choose>
											<c:when test="${list.isEnable eq 0}">
												<span style="color: red">红字作废</span>
											</c:when>
											<c:otherwise>
												<span style="color: red">红字有效</span>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${list.isEnable eq 0}">
												<span style="color: red">蓝字作废</span>
											</c:when>
											<c:otherwise>
												蓝字有效
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</td>
	                        <td>
	                        	<fmt:formatNumber type="number" value="${list.amount}" pattern="0.00" maxFractionDigits="2" />
	                        	<c:set var="invoiceAmount" value="${invoiceAmount + list.amount}"></c:set>
	                        </td>
	                        <td>${list.creatorName}</td>
	                        <td><date:date value="${list.addTime}" format="yyyy.MM.dd HH:mm:ss"/></td>
	                        <td>${list.express.logisticsCompanyName}</td>
	                        <td>${list.express.logisticsNo}</td>
	                        <td>
								<c:choose>
	                        		<c:when test="${list.express.arrivalStatus eq 0}">未签收</c:when>
	                        		<c:when test="${list.express.arrivalStatus eq 1}">部分签收</c:when>
	                        		<c:when test="${list.express.arrivalStatus eq 2}">已签收</c:when>
	                        	</c:choose>
							</td>
	                        <td>${list.express.logisticsComments}</td>
	                        <td>
								<!-- add by Tomcat.Hui 2019/11/21 11:48 .Desc: VDERP-1325 分批开票 查看已开发票详情信息 start. -->
								<a class="pop-new-data" layerParams='{"width":"850px","height":"460px","title":"查看已开发票详情","link":"<%=basePath%>finance/invoice/viewInvoicedItems.do?invoiceId=${list.invoiceId}"}' >查看</a>
								<!-- add by Tomcat.Hui 2019/11/21 11:48 .Desc: VDERP-1325 分批开票 查看已开发票详情信息 end. -->
	                        	<c:if test="${list.invoiceProperty eq 2 and not empty list.invoiceNo}">
	                        		<a href= "${list.invoiceHref}" target="_blank">下载发票</a>
	                        		<%-- <a href="./cancelEInvoicePush.do?invoiceId=${list.invoiceId}&afterSalesId=99999">作废电子票</a> --%>
	                        	</c:if>
	                        	<c:if test="${list.invoiceProperty eq 1}">
		                        	<c:choose>
		                        		<c:when test="${empty list.expressId}">
		                        			<c:if test="${saleorder.lockedStatus eq 0}"><!-- 未锁定 -->
		                        				<a class="pop-new-data" layerParams='{"width":"500px","height":"280px","title":"寄送发票","link":"./sendSaleInvoice.do?invoiceId=${list.invoiceId}"}'>寄送发票</a>
		                        				<%-- <c:choose>
													<c:when test="${list.type eq 504}"><!-- 售后发票 -->
														<!-- 售后单2：已完结，3：已关闭 -->
														<c:if test="${(list.atferStatus eq 2) or (list.atferStatus eq 3)}">
								                        	<a class="pop-new-data" layerParams='{"width":"500px","height":"280px","title":"寄送发票","link":"./sendSaleInvoice.do?invoiceId=${list.invoiceId}"}'>寄送发票</a>
														</c:if>
													</c:when>
													<c:otherwise>
														<a class="pop-new-data" layerParams='{"width":"500px","height":"280px","title":"寄送发票","link":"./sendSaleInvoice.do?invoiceId=${list.invoiceId}"}'>寄送发票</a>
													</c:otherwise>
												</c:choose> --%>
		                        			</c:if>
		                        		</c:when>
		                        		<c:otherwise>
		                        			<!-- 未签收,则可以编辑 -->
		                        			<c:choose>
		                        				<c:when test="${list.express.arrivalStatus != 2}">
		                        					<a class="pop-new-data" layerParams='{"width":"570px","height":"300px","link":"./editExpressView.do?expressId=${list.expressId}&invoiceId=${list.invoiceId}&invoiceNo=${list.invoiceNo}","title":" 编辑快递"}'>编辑</a>
		                        				</c:when>
		                        				<c:otherwise>
		                        					已寄送
		                        				</c:otherwise>
		                        			</c:choose>
		                        		</c:otherwise>
		                        	</c:choose>
	                        	</c:if>
	                        </td>
	                    </tr>
                	</c:forEach>
                	<c:if test="${empty saleInvoiceList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='12'>暂无发票信息</td>
					    </tr>
		        	</c:if>
                	<tr style="background: #eaf2fd;">
                        <td colspan="12" style="text-align: left;">
                        	已开票总额：<fmt:formatNumber type="number" value="${invoiceAmount}" pattern="0.00" maxFractionDigits="2" />
                        	&nbsp;&nbsp;&nbsp;&nbsp;
                        	<span style="color:red">未开票总额：<fmt:formatNumber type="number" value="${saleorderDataInfo['realAmount'] - invoiceAmount}" pattern="0.00" maxFractionDigits="2" /></span>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
         <div class="tcenter mb15 mt-5">
        		<c:choose>
				<c:when test="${invoiceApply.validStatus == null || invoiceApply.validStatus != 1}">
				<c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
					<c:choose>
						<c:when test="${taskInfo.assignee == curr_user.username or candidateUserMap['belong']}">
						<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=true&type=1"}'>审核通过</button>
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=false&type=1"}'>审核不通过</button>
						</c:when>
						<c:otherwise>
        						<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
						</c:otherwise>
					</c:choose>
				</c:if>
				</c:when>
			</c:choose>
        </div>
      <div class="parts">
            <div class="title-container title-container-green">
                <div class="table-title nobor">
                   	 物流信息
                </div>
				<a class="title-click" href="javascript:void(0);"  onclick="toWarehouseDetail()">查看出库记录</a>
            </div>
            <table class="table  table-style6">
                <thead>
                    <tr>
                        <th class="wid8">快递公司</th>
                        <th class="">快递单号</th>
                        <th class="wid10">发货时间</th>
                        <th class="wid8">运费</th>
                        <th>商品</th>
                        <th>备注</th>
                        <th class="wid10">操作人</th>
                        <th class="wid10">快递状态</th>
                    </tr>
                </thead>
                <tbody id="wl">
                	<c:forEach var="express" items="${expressList}">
                     <tr>
                        <td>${express.logisticsName}</td>
                        <td>${express.logisticsNo}</td>
                        <td><date:date value ="${express.deliveryTime}" format="yyyy-MM-dd"/></td>
                        <td>
                        	<c:set var="amount" value="0.00"></c:set>
                        	<c:forEach var="expressDetails" items="${express.expressDetail}">
                        		<c:set var="amount" value="${amount + expressDetails.amount}"></c:set>
                        	</c:forEach>
                        	${amount}
                        </td>
                        <td class="text-left">
                        	<c:forEach var="expressDetails" items="${express.expressDetail}">
                            	<div>${expressDetails.goodName}&nbsp;&nbsp;&nbsp;&nbsp;${expressDetails.num} ${expressDetails.unitName}</div>
                            </c:forEach>
                        </td>
                        <td>${express.logisticsComments}</td>
                        <td>${express.updaterUsername}</td>
                        <td>
                        	<c:if test="${express.arrivalStatus == 0}">
                        		未收货
                        	</c:if>
                        	<c:if test="${express.arrivalStatus == 1}">
                        		部分收货
                        	</c:if>
                        	<c:if test="${express.arrivalStatus == 2}">
                        		全部收货
                        	</c:if>
                        </td>
                    </tr>
                     </c:forEach>
                     <c:if test="${!empty expressList}">
                    <tr>
                        <td colspan="8" class="allchosetr text-left">
        
                            	 运费总额：<span class="mr10">${allamount}</span>商品总数：<span class="">${allnum}</span>
                            	 已发货总数：<span class="mr10">${allDeliveryNum}</span><span class="warning-color1">待发货数量：${allnum-allDeliveryNum}</span>
                        </td>
                    </tr>
                   </c:if>
                    <c:if test="${empty expressList}">
                     <tr>
                        <td colspan="8">暂无物流信息记录</td>
                    </tr>
                   </c:if>
                   
                </tbody>
            </table>
	</div>
	<div class="parts">
		<%--<div class="title-container">--%>
			<%--<div class="table-title nobor">出库记录</div>--%>
		<%--</div>--%>
		<%--<table class="table">--%>
			<%--<thead>--%>
				<%--<tr>--%>
					<%--<th class="wid5">序号</th>--%>
					<%--<th>产品名称</th>--%>
					<%--<th>品牌/型号</th>--%>
					<%--<th class="wid10">物料编码</th>--%>
					<%--<th class="wid4">单位</th>--%>
					<%--<th>贝登条码</th>--%>
					<%--<th>厂商条码</th>--%>
					<%--<th>出库数量</th>--%>
					<%--<th>出库时间</th>--%>
					<%--<th class="wid12">操作人</th>--%>
				<%--</tr>--%>
			<%--</thead>--%>
			<%--<tbody>--%>
				<%--<c:forEach var="listout" items="${warehouseOutList}"--%>
					<%--varStatus="num3">--%>
					<%--<tr>--%>
						<%--<td>${num3.count}</td>--%>
						<%--<td class="text-left">--%>
	                        <%--<div >--%>
	                          <%--<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${listout.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${listout.goodsId}","title":"产品信息"}'>${listout.goodsName}</a>--%>
	                        <%--</div>--%>
	                        <%--<div>${listout.sku}</div>--%>
	                    <%--</td>--%>
						<%--<td>${listout.brandName}<br />${listout.model}</td>--%>
						<%--<td>${listout.materialCode}</td>--%>
						<%--<td>${listout.unitName}</td>--%>
						<%--<td>${listout.barcode}</td>--%>
						<%--<td>${listout.barcodeFactory}</td>--%>
						<%--<td>${0-listout.num}</td>--%>
						<%--<td><date:date value ="${listout.addTime}" format="yyyy-MM-dd HH:mm:ss"/></td>--%>
						<%--<td>${ listout.opName}</td>--%>
					<%--</tr>--%>
				<%--</c:forEach>--%>
				<%--<c:if test="${empty warehouseOutList}">--%>
					<%--<tr>--%>
						<%--<td colspan="10">暂无出库记录</td>--%>
					<%--</tr>--%>
				<%--</c:if>--%>
			<%--</tbody>--%>
		<%--</table>     --%>
	<div class="parts content1">
		<div class="title-container title-container-blue">
			<div class="table-title nobor">售后列表</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th class="wid15">售后单号</th>
					<th class="wid15">售后类型</th>
					<th class="wid20">创建时间</th>
					<th class="wid10">创建人</th>
					<th class="wid10">订单状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${asList}" var="aftersales" varStatus="status">
					<tr>
						<td><span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
									"link":"./order/saleorder/viewAfterSalesDetail.do?afterSalesId=${aftersales.afterSalesId}","title":"售后详情"}'>
								${aftersales.afterSalesNo}
							</span>
						</td>
						<td><c:choose>
								<c:when test="${aftersales.type eq 539}">销售退货</c:when>
                        		<c:when test="${aftersales.type eq 540}">销售换货</c:when>
                        		<c:when test="${aftersales.type eq 541}">销售安调</c:when>
                        		<c:when test="${aftersales.type eq 584}">销售维修</c:when>
                        		<c:when test="${aftersales.type eq 542}">销售退票</c:when>
                        		<c:when test="${aftersales.type eq 543}">销售退款</c:when>
                        		<c:when test="${aftersales.type eq 544}">销售订单技术咨询</c:when>
                        		<c:when test="${aftersales.type eq 545}">销售订单其他</c:when>
								<c:when test="${aftersales.type eq 546}">采购退货</c:when>
								<c:when test="${aftersales.type eq 547}">采购换货</c:when>
								<c:when test="${aftersales.type eq 550}">第三方安调</c:when>
								<c:when test="${aftersales.type eq 551}">第三方退款</c:when>
								<c:otherwise>--</c:otherwise>
							</c:choose></td>
						<td><date:date value="${aftersales.addTime}" /></td>
						<td>${aftersales.creatorName}</td>
						<td>
							<c:if test="${aftersales.atferSalesStatus eq 0}">待确认</c:if>
							<c:if test="${aftersales.atferSalesStatus eq 1}">进行中</c:if> 
							<c:if test="${aftersales.atferSalesStatus eq 2}">已完结</c:if> 
							<c:if test="${aftersales.atferSalesStatus eq 3}">已关闭</c:if>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${empty asList}">
					<tr>
						<td colspan="5">查询无结果！</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<div class="parts">
		<div class="title-container title-container-blue">
			<div class="table-title nobor">其他信息</div>
		</div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<tbody>
				<tr>
					<td class="table-smaller">附加条款</td>
					<td colspan="3" class="text-left">${saleorder.additionalClause}</td>
				</tr>
				<tr>
					<td class="table-smaller">内部备注</td>
					<td colspan="3" class="text-left">${saleorder.comments}</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">提前采购申请</div>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>操作人</th>
					<th>操作时间</th>
					<th>操作事项</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${null!=historicActivityInstanceEarly}">
					<c:forEach var="hie" items="${historicActivityInstanceEarly}" varStatus="status">
						<c:if test="${not empty  hie.activityName}">
							<tr>
								<td>
									<c:choose>
										<c:when test="${hie.activityType == 'startEvent'}"> 
											${startUserEarly}
										</c:when>
										<c:when test="${hie.activityType == 'intermediateThrowEvent'}">
										</c:when>
										<c:otherwise>
											<c:if test="${historicActivityInstanceEarly.size() == status.count}">
												${verifyUsersEarly}
											</c:if>
											<c:if test="${historicActivityInstanceEarly.size() != status.count}">
												${hie.assignee}  
											</c:if>
										</c:otherwise>
									</c:choose>
								</td>
								<td><fmt:formatDate value="${hie.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>
									<c:choose>
										<c:when test="${hie.activityType == 'startEvent'}"> 
											开始
										</c:when>
										<c:when test="${hie.activityType == 'intermediateThrowEvent'}"> 
											结束
										</c:when>
										<c:otherwise>   
											${hie.activityName}  
										</c:otherwise>
									</c:choose>
								</td>
								<td class="font-red">${commentMapEarly[hie.taskId]}</td>
							</tr>
						</c:if>
					</c:forEach>
				</c:if>
				<!-- 查询无结果弹出 -->

				<c:if test="${empty historicActivityInstanceEarly}">
					<!-- 查询无结果弹出 -->
					<tr>
						<td colspan="4">暂无审核记录。</td>
					</tr>
				</c:if>

			</tbody>
		</table>
	</div>
</div>
    <input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
<%@ include file="../../common/footer.jsp"%>

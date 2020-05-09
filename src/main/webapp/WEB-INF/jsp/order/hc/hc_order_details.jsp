<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="耗材订单详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/hc_order_details.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
	$(function()
	{
		var saleorderId = $("input[name='saleorderId']").val();
		
		var	url = page_url + '/order/hc/hcOrderDetailsPage.do?saleorderId='+saleorderId;
		if($(window.frameElement).attr('src').indexOf("hc/hcOrderDetailsPage")<0){
			$(window.frameElement).attr('data-url',url);
		}

		//补订单产品详情相关数据
		$.ajax({
			async:true,
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
						$("#kykc_"+data.data[i].saleorderGoodsId).html((data.data[i].goods.stockNum-data.data[i].goods.orderOccupy) < 0 ? 0 : (data.data[i].goods.stockNum-data.data[i].goods.orderOccupy));
						$("#dzzy_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.orderOccupy);
						$("#ktj_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.adjustableNum);
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
				}else{
					//layer.alert(data.message);
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
                        <td>
                        	<c:if test="${saleorder.validTime > 0}">
	                        <date:date value ="${saleorder.validTime}"/>
	                        </c:if>
                        </td>
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
                        <td>商机编号</td>
                        <td>
                            <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${saleorder.bussinessChanceId}","link":"./order/bussinesschance/toSalesDetailPage.do?bussinessChanceId=${saleorder.bussinessChanceId}","title":"商机信息"}'>${saleorder.bussinessChanceNo}</a>
                        </td>
                        <td>报价单号</td>
                        <td>
                            <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewquoteorder${saleorder.quoteorderId}","link":"./order/quote/getQuoteDetail.do?quoteorderId=${saleorder.quoteorderId}&viewType=3","title":"报价信息"}'>${saleorder.quoteorderNo}</a>
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
                        <td>计入业绩</td>
                        <td id="perf_flag">
	                        <c:choose>
								<c:when test="${saleorder.isSalesPerformance eq 1}">已计入</c:when>
								<c:otherwise>未计入</c:otherwise>
							</c:choose>
                        </td>
                        <td>计入业绩时间</td>
                        <td id="perf_time">
	                        <c:choose>
								<c:when test="${saleorder.isSalesPerformance eq 1}"><date:date value ="${saleorder.salesPerformanceTime}"/></c:when>
								<c:otherwise>-</c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>订单归属</td>
                        <td>${saleorder.ownerUserName}</td>
                        <td>优惠券</td>
                        <td>类型 : <span class="font-red">
                        		<!-- 1-满减券 | 2-折扣券 | 3-现金券 -->	
                        		<c:choose>
                                    <c:when test="${saleorder.couponType eq 1}">满减券</c:when>
                                    <c:when test="${saleorder.couponType eq 2}">折扣券</c:when>
                                    <c:when test="${saleorder.couponType eq 3}">现金券</c:when>
                                    <c:otherwise>无</c:otherwise>
                                </c:choose></span> , 金额 : <span class="font-red"><fmt:formatNumber type="number" value="${null == saleorder.couponAmount ? 0 : saleorder.couponAmount}" pattern="0.00" maxFractionDigits="2" /></span></td>
                    </tr>
                    <tr>
                        <td>用户是否申请开票</td>
                        <td>
                         <c:if test="${saleorder.isApplyInvoice eq 0}">否</c:if>
                         <c:if test="${saleorder.isApplyInvoice eq 1}">是</c:if>
                        </td>
                        <td>申请开票时间</td>
                        <td><date:date value="${saleorder.applyInvoiceTime}" format="yyyy-MM-dd" /> </td>
                    </tr>
                    <tr>
                    	<td>用户确认收货时间</td>
                    	<td><date:date value ="${saleorder.webTakeDeliveryTime}"/></td>
                    	<td colspan="2"></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
                                                         客户信息
                </div>
                <c:if test="${saleorder.status!=3 and saleorder.traderId != null and saleorder.traderId > 0}">
                <div class="title-click nobor" onclick="syncNewTraderName(${saleorder.saleorderId},${saleorder.invoiceStatus},${saleorder.traderId})">
                    名称同步
                </div>
                </c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">客户名称</td>
                        <td>
                            <div class="customername pos_rel">
                                <span class="font-blue mr4">
                                <a class="addtitle" href="javascript:void(0);" 
										tabTitle='{"num":"viewcustomer${customer.traderCustomerId}",
										"link":"./trader/customer/baseinfo.do?traderCustomerId=${customer.traderCustomerId}&traderId=${saleorder.traderId}",
										"title":"客户信息"}'>
                            			${saleorder.traderName}</a>
                                </span>
                                <i class="iconbluemouth"></i>
                                <div class="pos_abs customernameshow mouthControlPos">
                                    报价次数：${customer.quoteCount} <br>
                                    交易次数：${customer.buyCount} <br>
                                    交易金额：${customer.buyMoney} <br>
                                    上次交易日期：<date:date value="${customer.lastBussinessTime}" format="yyyy-MM-dd" /> <br>
                                    归属销售：${customer.ownerSale}
                                </div>
                            </div>
                        </td>
                        <td class="table-smaller">地区</td>
                        <td>${saleorder.traderArea}</td>
                    </tr>
                    <tr>
                        <td>客户类型</td>
                        <td>${saleorder.customerTypeStr}</td>
                        <td>客户性质</td>
                        <td>${saleorder.customerNatureStr}</td>
                    </tr>
                    <tr>
                        <td>联系人</td>
                        <td>${saleorder.traderContactName}</td>
                        <td>电话</td>
                        <td>
                        <c:if test="${not empty saleorder.traderContactTelephone}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${saleorder.traderContactTelephone}',${saleorder.traderId},1,2,${saleorder.saleorderId},${saleorder.traderContactId});"></i>
                        </c:if>
                        ${saleorder.traderContactTelephone}
                        </td>
                    </tr>
                    <tr>
                        <td>手机</td>
                        <td>
                        <c:if test="${not empty saleorder.traderContactMobile}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${saleorder.traderContactMobile}',${saleorder.traderId},1,2,${saleorder.saleorderId},${saleorder.traderContactId});"></i>
                        </c:if>
                        ${saleorder.traderContactMobile}
                        </td>
                        <td>注册帐号</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>联系地址</td>
                        <td colspan="3">${saleorder.traderAddress}</td>
                    </tr>
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
								<c:if test="${twoMedicalList ne null and twoEndTime eq null && not empty twoMedicalList}">-无限期</c:if>
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
                <div class="table-title nobor">
                                                    收货信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">收货客户</td>
                        <td>${saleorder.takeTraderName}</td>
                        <td class="table-smaller">收货联系人</td>
                        <td>${saleorder.takeTraderContactName}</td>
                    </tr>
                    <tr>
                        <td>电话</td>
                        <td>
                        <c:if test="${not empty saleorder.takeTraderContactTelephone}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${saleorder.takeTraderContactTelephone}',${saleorder.takeTraderId},1,2,${saleorder.saleorderId},${saleorder.takeTraderContactId});"></i>
                        </c:if>
                        ${saleorder.takeTraderContactTelephone}
                        </td>
                        <td>手机</td>
                        <td>
                        <c:if test="${not empty saleorder.takeTraderContactMobile}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${saleorder.takeTraderContactMobile}',${saleorder.takeTraderId},1,2,${saleorder.saleorderId},${saleorder.takeTraderContactId});"></i>
                        </c:if>
                        ${saleorder.takeTraderContactMobile}
                        </td>
                    </tr>
                    <tr>
                        <td>收货地区</td>
                        <td>${saleorder.takeTraderArea}</td>
                        <td>发货方式</td>
                        <td>
                        	<c:forEach var="list" items="${deliveryTypes}">
		                    	<c:if test="${saleorder.deliveryType == list.sysOptionDefinitionId}">${list.title}</c:if>
		                    </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>收货地址</td>
                        <td colspan="3">${saleorder.takeTraderAddress}</td>
                    </tr>
                    <tr>
                        <td>指定物流公司</td>
                        <td>
                        	<c:forEach var="list" items="${logisticsList}">
		                    	<c:if test="${saleorder.logisticsId == list.logisticsId}">${list.name}</c:if>
		                    </c:forEach>
                        </td>
                        <td>运费说明</td>
                        <td>
                        	<c:forEach var="list" items="${freightDescriptions}">
		                    	<c:if test="${saleorder.freightDescription == list.sysOptionDefinitionId}">${list.title}</c:if>
		                    </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <td>物流备注</td>
                        <td colspan="3">${saleorder.logisticsComments}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
                                                         收票信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">收票客户</td>
                        <td>${saleorder.invoiceTraderName}</td>
                        <td class="table-smaller">收票联系人</td>
                        <td>${saleorder.invoiceTraderContactName}</td>
                    </tr>
                    <tr>
                        <td>电话</td>
                        <td>
                        <c:if test="${not empty saleorder.invoiceTraderContactTelephone}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${saleorder.invoiceTraderContactTelephone}',${saleorder.invoiceTraderId},1,2,${saleorder.saleorderId},${saleorder.invoiceTraderContactId});"></i>
                        </c:if>
                        ${saleorder.invoiceTraderContactTelephone}
                        </td>
                        <td>手机</td>
                        <td>
                        <c:if test="${not empty saleorder.invoiceTraderContactMobile}">
	                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${saleorder.invoiceTraderContactMobile}',${saleorder.invoiceTraderId},1,2,${saleorder.saleorderId},${saleorder.invoiceTraderContactId});"></i>
                        </c:if>
                        ${saleorder.invoiceTraderContactMobile}
                        </td>
                    </tr>
                    <tr>
                        <td>收票地区</td>
                        <td>${saleorder.invoiceTraderArea}</td>
                        <td>发票类型</td>
                        <td>
                        	<c:forEach var="list" items="${invoiceTypes}">
		                    	<c:if test="${saleorder.invoiceType == list.sysOptionDefinitionId}">${list.title}</c:if>
		                    </c:forEach>
		                    <c:choose>
		                    	<c:when test="${saleorder.isSendInvoice eq 0}">（不寄送）</c:when>
		                    	<c:otherwise>（寄送）</c:otherwise>
		                    </c:choose>
                        </td>
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
                        <!-- add by Tomcat.Hui 2020/1/2 16:04 .Desc: VDERP-1827 票货同行. start -->
                        <input type="hidden" id="phtxFlag" value="${phtxFlag}">
                        <!-- add by Tomcat.Hui 2020/1/2 16:04 .Desc: VDERP-1827 票货同行. end -->

                        <td>延缓开票</td>
                        <td>
		                    <c:choose>
		                    	<c:when test="${saleorder.isDelayInvoice eq 1}">是</c:when>
		                    	<c:otherwise>否</c:otherwise>
		                    </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td>收票地址</td>
                        <td colspan="3">${saleorder.invoiceTraderAddress}</td>
                    </tr>
                    <tr>
                        <td>开票备注</td>
                        <td colspan="3">${saleorder.invoiceComments}</td>
                    </tr>
                    <tr>
                        <td>收票邮箱</td>
                        <td colspan="3">${saleorder.invoiceEmail}</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts ">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
                                                         终端信息
                </div>
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
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">产品信息</div>
                <c:set var="referenceCostPrice" value="0"></c:set>
                <shiro:hasPermission name="/order/saleorder/referenceCostPrice.do">
                	<c:set var="referenceCostPrice" value="1"></c:set>
                </shiro:hasPermission>
                <c:set var="settlementPrice" value="0"></c:set>
                <shiro:hasPermission name="/order/quote/settlementPrice.do">
                	<c:set var="settlementPrice" value="1"></c:set>
                </shiro:hasPermission>
            </div>
            <table class="table  table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="wid4">序号</th>
                        <th class="wid15">产品名称</th>
                        <th >品牌</th>
                        <th class="wid8">型号</th>
                        <th class="wid8">单价</th>
                        <th class="wid8">原单价</th>
                        <c:if test="${referenceCostPrice eq 1 }">
                        	<th class="wid8">参考成本</th>
                        </c:if>
                        <th class="wid4">数量</th>
                        <th class="wid4">单位</th>
                        <th >总额</th>
                        <th >货期</th>
                        <th class="wid5">直发</th>
                        <th  class="wid10">核价参考</th>
                        <th >占用/库存</th>
                        <th >含安调</th>
                        <th>类别管制</th>
                        <th>产品备注</th>
                        <th>内部备注</th>
                        <th>锁定状态</th>
                        <th>采购状态</th>
                        <th>到库状态</th>
                        <th>发货状态</th>
                        <th>收货状态</th>
                        <td>发货数量</td>
                        <td>收货数量</td>
                        <!-- add by Tomcat.Hui 2019/11/28 15:34 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. start -->
                        <th class="wid10">已开票/开票中数量</th>
                        <!-- add by Tomcat.Hui 2019/11/28 15:34 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. end -->
                    </tr>
                </thead>
                <tbody>
                	<c:set var="num" value="0"></c:set>
                	<!-- 原订单总金额 -->
					<c:set var="frTotleMoney" value="0.00"></c:set>
					<c:set var="totleMoney" value="0.00"></c:set>
					<c:set var="isNotDelPriceZero" value="0"></c:set>
					<c:set var="isUrgent" value="0"></c:set>
					<c:set var="isCold" value="0"></c:set>
                	<c:forEach var="list" items="${saleorderGoodsList}" varStatus="staut">
                		<c:if test="${list.isDelete eq 0}">
							<c:set var="num" value="${num + list.num}"></c:set>
							<c:set var="totleMoney" value="${totleMoney + list.maxSkuRefundAmount}"></c:set>
							<c:set var="frTotleMoney" value="${frTotleMoney + (list.realPrice * list.num)}"></c:set>
							<c:if test="${list.price == '0.00'}">
								<c:set var="isNotDelPriceZero" value="1"></c:set>
							</c:if>
						</c:if>
						<c:if test="${list.goodsId == '251526'}">
								<c:set var="isUrgent" value="1"></c:set>
						</c:if>
						<c:if test="${list.goodsId == '256675'}">
								<c:set var="isCold" value="1"></c:set>
						</c:if>
						<!-- 判断该商品是不是归属于当前登陆人 -->
						<c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
							<c:set var="shenhe" value="0"></c:set>
							<c:forEach items="${verifyUserList}" var="verifyUsernameInfo">
								<c:if test="${verifyUsernameInfo == curr_user.username}">
									<c:set var="shenhe" value="1"></c:set>
								</c:if>
							</c:forEach>
								<c:if test="${(taskInfo.assignee == curr_user.username or candidateUserMap['belong']) and shenhe!=1 and taskInfo.name == '产品线归属人审核'}">
									<c:choose>
									<c:when test="${fn:contains(list.goodsUserIdStr, loginUserId)}">
										<c:set var="goodsCategoryUser" value="y"></c:set>
									</c:when>
									<c:otherwise>
										<c:set var="goodsCategoryUser" value="n"></c:set>
									</c:otherwise>
									</c:choose>
								</c:if>
						</c:if>
						<c:if test="${saleorder.verifyStatus != null and curr_user.positType == 311 and isCrossMonth == 0}">
									<c:choose>
									<c:when test="${fn:contains(list.goodsUserIdStr, loginUserId)}">
										<c:set var="goodsCategoryUser" value="y"></c:set>
									</c:when>
									<c:otherwise>
										<c:set var="goodsCategoryUser" value="n"></c:set>
									</c:otherwise>
									</c:choose>
						</c:if>
	                    <tr <c:if test="${list.isDelete eq 1 or goodsCategoryUser eq 'n'}">class="caozuo-grey"</c:if>>
	                        <td>${staut.count}</td>
	                        <td class="text-left">
	                            <div class="customername pos_rel">
	                                <c:choose>
										<c:when test="${list.isDelete eq 1}">
                                            <span>${newSkuInfosMap[list.sku].SHOW_NAME}。。。<br/></span>
			                                <span>${list.sku} <br>${list.goods.materialCode}</span>
										</c:when>
										<c:otherwise>
                                            <span class="font-blue"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>
                                                    <c:if test="${list.isActionGoods eq 1}"><span style="color:red;">【活动】</span></c:if>
                                                    ${newSkuInfosMap[list.sku].SHOW_NAME}</a>&nbsp;<i class="iconbluemouth contorlIcon"></i><br/>
                                            </span>
			                                <span>${newSkuInfosMap[list.sku].SKU_NO} <br>${newSkuInfosMap[list.sku].MATERIAL_CODE}</span>

                                            <c:set var="skuNo" value="${list.sku}"></c:set>
                                            <%@ include file="../../common/new_sku_common_tip.jsp" %>

                                            <%-- <div class="pos_abs customernameshow">

                                             物料编码：${list.goods.materialCode} <br>--%>
											<%--注册证号：${list.goods.registrationNumber} <br>--%>
											<%--管理类别：${list.manageCategoryName} <br>--%>
											<%--产品负责人：<c:if test="${not empty list.goods.userList }">--%>
															<%--<c:forEach items="${list.goods.userList }" var="user" varStatus="st">--%>
																<%--${user.username }--%>
																<%--<c:if test="${st.count != list.goods.userList.size() }">、</c:if>--%>
															<%--</c:forEach>--%>
														<%--</c:if>--%>
													<%--<br>--%>
											<%--采购提醒：${list.goods.purchaseRemind} <br>--%>
											<%--包装清单：${list.goods.packingList} <br>--%>
											<%--服务条款：${list.goods.tos} <br>--%>
											<%--库存：<span id="kc_${list.saleorderGoodsId}">${list.goods.stockNum}</span> <br>--%>
											<%--可用库存：<span id="kykc_${list.saleorderGoodsId}">${(list.goods.stockNum-list.goods.orderOccupy) < 0 ? 0 : (list.goods.stockNum-list.goods.orderOccupy)}</span><br>--%>
											<%--订单占用：<span id="dzzy_${list.saleorderGoodsId}">${list.goods.orderOccupy}</span><br>--%>
											<%--可调剂：<span id="ktj_${list.saleorderGoodsId}">${list.goods.adjustableNum}</span> <br>--%>
											<%--审核状态：<c:choose>--%>
											 	<%--<c:when test="${list.goods.verifyStatus eq 0}">审核中</c:when>--%>
											 	<%--<c:when test="${list.goods.verifyStatus eq 1}">审核通过</c:when>--%>
											 	<%--<c:when test="${list.goods.verifyStatus eq 2}">审核不通过</c:when>--%>
											 	<%--<c:otherwise>待审核</c:otherwise>--%>
											 <%--</c:choose>
											</div>--%>
										</c:otherwise>
									</c:choose>
	                            </div>
	                        </td>
	                        <td>${newSkuInfosMap[list.sku].BRAND_NAME}</td>
	                        <td>${newSkuInfosMap[list.sku].MODEL}</td>
	                        <!-- 优惠单价-->
	                        <td>${list.price}</td>
	                        <!-- 实际单价 -->
	                        <td>
	                           <fmt:formatNumber type="number" value="${null == list.realPrice ? 0 : list.realPrice}" pattern="0.00" maxFractionDigits="2" />
	                        </td>
	                        <c:if test="${referenceCostPrice eq 1 }">
	                        			<!-- 如果是采购部并且非待审核的 -->
	                        			<c:choose>
	                        			<c:when test="${saleorder.validStatus != null and curr_user.positType == 311 and isCrossMonth == 0}">
			                        		<td>
			                        			<input type="text" name="referenceCostPrice_${staut.count}" value="${list.referenceCostPrice == null || list.referenceCostPrice == '0.00' ?(list.costPrice == null ?'0.00':list.costPrice):list.referenceCostPrice}" alt="${list.referenceCostPrice == null || list.referenceCostPrice == '0.00'?'0.00':list.referenceCostPrice}" goodsCategoryUser="${goodsCategoryUser}">
				                       		 	<input type="hidden" name="saleorderGoodsId" value="${list.saleorderGoodsId}" />
											</td>
	                        			</c:when>
	                        			<c:otherwise>
	                        				<td>${list.referenceCostPrice}</td>
	                        			</c:otherwise>
	                        			</c:choose>
                       		 </c:if>
	                        <td>
	                        	<c:choose>
	                        		<c:when test="${((list.afterReturnNum==''||list.afterReturnNum==null) ? 0 : list.afterReturnNum) eq 0}">
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
	                        <!-- 订单实际金额 -->
	                        <td>
	                        	<fmt:formatNumber type="number" value="${list.maxSkuRefundAmount - list.afterReturnAmount}" pattern="0.00" maxFractionDigits="2" />
	                        </td>
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
	                        </td>
	                        <td>
	                        	<div class="customername pos_rel">
	                        		核价参考价格：<fmt:formatNumber type="number" value="${list.channelPrice}" pattern="0.00" maxFractionDigits="2" /><br/>
									参考价格：${list.referencePrice} <br/>
									参考货期：${list.referenceDeliveryCycle} <br/>
									<c:if test="${settlementPrice eq 1 }">
										结算价：${list.settlePrice} <br/>
									</c:if>
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
										报备结果：${list.reportStatus}
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
	                        	<c:choose>
									<c:when test="${list.lockedStatus eq 0}"><span>-</span></c:when>
									<c:otherwise><span style="color: red;">锁</span></c:otherwise>
								</c:choose>
							</td>

	                        <td><span id="cgztStr_${list.saleorderGoodsId}"></span></td>
	                        <td><span id="dkztStr_${list.saleorderGoodsId}"></span></td>
	                        <td>
	                        		<c:choose>
		                        		<c:when test="${list.deliveryStatus eq 0}">未发货</c:when>
		                        		<c:when test="${list.deliveryStatus eq 1}"><span style="color:green;">部分发货</span></c:when>
		                        		<c:when test="${list.deliveryStatus eq 2}"><span style="color:green;">已发货</span></c:when>
		                        	</c:choose>
	                        </td>
	                         <td>
		                        	<c:choose>
		                        		<c:when test="${list.arrivalStatus eq 0}">未收货</c:when>
		                        		<c:when test="${list.arrivalStatus eq 1}"><span style="color:green;">部分收货</span></c:when>
		                        		<c:when test="${list.arrivalStatus eq 2}"><span style="color:green;">已收货</span></c:when>
		                        	</c:choose>
	                        </td>
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
                            <!-- add by Tomcat.Hui 2019/11/28 15:34 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. start -->
                            <td>${list.invoicedNum}/${list.appliedNum}</td>
                            <!-- add by Tomcat.Hui 2019/11/28 15:34 .Desc: VDERP-1325 分批开票 已开票数量/开票中数量/该订单该sku的数量. end -->
	                    </tr>
                    </c:forEach>
                    <tr style="background: #eaf2fd;">
                    	<c:choose>
                    	<c:when test="${referenceCostPrice eq 1}">
                    		 	<td colspan="26" class="text-left">
                        </c:when>
                        <c:otherwise>
                        		<td colspan="25" class="text-left">
						</c:otherwise>
                        </c:choose>
                        	<input type="hidden" value="${isNotDelPriceZero}" id="isNotDelPriceZero">
                        	总件数 <span class="font-red"> ${num}</span>，订单原金额
                            <span class="font-red">
                               <fmt:formatNumber type="number" value="${saleorder.totalAmount}" pattern="0.00" maxFractionDigits="2" />
                            </span> ，订单实际金额
                            <span class="font-red"><fmt:formatNumber type="number" value="${saleorderDataInfo['realAmount']}" pattern="0.00" maxFractionDigits="2" /></span>
                            <!-- 优惠金额 -->
                                                                                     ，优惠金额 <span class="font-red"><fmt:formatNumber type="number" value="${null == saleorder.couponAmount ? 0 : saleorder.couponAmount}" pattern="0.00" maxFractionDigits="2" /></span>
                            <shiro:hasPermission name="/order/saleorder/showCostAmountAndRate.do">
								，（五行）手填总成本 <span class="font-red"><fmt:formatNumber type="number" value="${totalReferenceCostPrice}" pattern="0.00" maxFractionDigits="2" /></span>
                        	，（五行）毛利率 <span class="font-red">
                        	<c:choose>
                        		<c:when test="${totleMoney*1 <=0  }">-100%</c:when>
                        		<c:otherwise>
	                        	<fmt:formatNumber type="number" value="${(totleMoney - totalReferenceCostPrice ) / totleMoney * 100}" pattern="0.00" maxFractionDigits="2" />%
                        		</c:otherwise>
                        	</c:choose>
                        	</span>
							</shiro:hasPermission>
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
							<td><fmt:formatNumber type="number" value="${saleorder.prepaidAmount}" pattern="0.00" maxFractionDigits="2" /></td>
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
							<td>账期付款</td>
							<td>${saleorder.accountPeriodAmount}</td>
							<td>到货后${customer.periodDay}天内支付
								 <c:if test="${saleorder.logisticsCollection eq 1}">
		                        	 / 物流代收
	                        	</c:if>
							</td>
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
							<td>账期付款</td>
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
						<td colspan="3">${saleorder.paymentComments}</td>
					</tr>
                    <!-- tr style="background: #eaf2fd;">
                        <td colspan="4" class="text-left">账期付款：账期付款是我司向客户提供的信用付款方式，您需要在约定时间内支付账期额度的金额。本合同中账期以合同开始发货为结算开始时间。</td>
                    </tr-->
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
					其他信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">附加条款</td>
                        <td colspan="3" class="text-left">${saleorder.additionalClause}</td>
                    </tr>
                    <tr>
                        <td class="table-smaller">内部备注</td>
                        <td colspan="3" class="text-left">${saleorder.comments}</td>
                    </tr>
                    <c:if test="${saleorder.orderType ==3}">
                    <tr>
                        <td class="table-smaller">订货备注</td>
                        <td colspan="3" class="text-left">
                        <c:if test="${isUrgent == 1}">
                        	加急&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${isCold == 1}">
                        	使用冷链&nbsp;&nbsp;
                        </c:if>
                        <c:if test="${saleorder.freightDescription == 474}">
                        	快递到付&nbsp;&nbsp;
                        </c:if>
                        </td>
                    </tr>
                    </c:if>
                    <!-- tr>
                        <td class="table-smaller">审核项</td>
                        <td colspan="3" class="text-left">
                            <span class="font-red">---类别管制、价格超限---</span>
                        </td>
                    </tr-->
                </tbody>
            </table>
        </div>
       	<div class="text-center mb15">
       		<c:if test="${saleorder.validStatus !=null and curr_user.positType == 311 and isCrossMonth == 0}">
        	<button type="button" class="bt-bg-style bg-light-green bt-small mr10" onclick="checkCostPrice(${saleorder.saleorderId})">提交</button>
       		</c:if>
      		<c:if test="${saleorder.validStatus == 0 && saleorder.status!=3}">
	        <span class="bg-light-green bt-bg-style bt-small addtitle"
								tabTitle='{"num":"warehousesout_viewOutDetail_${saleorder.saleorderId }",
								"link":"./order/saleorder/printOrder.do?saleorderId=${saleorder.saleorderId}","title":"打印预览"}'>打印预览</span>
	        </c:if>
      
        <c:if test="${saleorder.status == 2}">
	        	<input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
	        	<span class="bg-light-green bt-bg-style bt-small addtitle"
							tabTitle='{"num":"warehousesout_viewOutDetail_${saleorder.saleorderId }","link":"./order/saleorder/printOrder.do?saleorderId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}","title":"打印"}'>打印</span>
				<c:if test="${saleorder.companyId != 1}">
				<span class="bg-light-green bt-bg-style bt-small addtitle"
					tabTitle='{"num":"warehousesout_saleorder_${saleorder.saleorderId }","link":"./order/saleorder/printSaleOrder.do?saleorderId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}","title":"打印订单"}'>打印订单</span>
				</c:if>
				<!-- 未锁定 -->
				<c:if test="${saleorder.lockedStatus eq 0 and saleorder.validStatus eq 1}">
					<!-- 1允许开票2存在开票申请待审核-不允许;;;;:3允许提前开票，4存在提前开票待审核-不允许::::5全部开票----0全部售后 -->
                    <!-- modified by Tomcat.Hui 2020/1/2 13:09 .Desc: VDERP-1039 票货同行. start -->
					<c:if test="${saleorder.isOpenInvoice eq 1}">
                        <c:choose>
                            <c:when test="${phtxFlag eq true and allSendFlag eq false}">
                                <button type="button" class="bt-bg-style bt-small bg-light-greybe mr10" title="“票货同行”订单，物流部发货后开具发票。" disabled="disabled">申请开票</button>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="bt-bg-style bg-light-green bt-small  mr10" onclick="isTraderAllowInvoice(${saleorder.traderId},'${saleorder.traderName}',${saleorder.invoiceType},1)">申请开票</button>
                                <span id="invoiceApply" class="pop-new-data" layerParams='{"width":"80%","height":"600px","title":"申请开票","link":"../../finance/invoice/selectInvoiceItems.do?relatedId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}&isAuto=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&isAdvance=0&editFlag=true"}'></span>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
					<c:if test="${saleorder.isOpenInvoice eq 3}">
                        <c:choose>
                            <c:when test="${phtxFlag eq true and allSendFlag eq false}">
                                <button type="button" class="bt-bg-style bt-small bg-light-greybe mr10" title="“票货同行”订单，物流部发货后开具发票。" disabled="disabled">申请提前开票</button>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="bt-bg-style bg-light-orange bt-small  mr10"  onclick="isTraderAllowInvoice(${saleorder.traderId},'${saleorder.traderName}',${saleorder.invoiceType},2)">申请提前开票</button>
                                <span id="advanceInvoiceApply" class="pop-new-data" layerParams='{"width":"80%","height":"600px","title":"申请提前开票","link":"../../finance/invoice/selectInvoiceItems.do?relatedId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}&isAuto=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&isAdvance=1&editFlag=true"}'></span>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <!-- modified by Tomcat.Hui 2020/1/2 13:09 .Desc: VDERP-1039 票货同行. end -->
					<c:if test="${saleorder.isOpenInvoice eq 5 or saleorder.isOpenInvoice eq 2 or saleorder.isOpenInvoice eq 4}">
			            <button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请开票</button>
					</c:if>

                    <!-- add by Tomcat.Hui 2019/12/5 13:31 .Desc: VDERP-1325 分批开票 若订单状态为已完成，但开票状态为“未开票”或“部分开票”，仍然显示“申请修改”的按钮. start -->
                    <c:if test="${saleorder.invoiceStatus eq 0 or saleorder.invoiceStatus eq 1}" >
                        <button type="button" class="bt-bg-style bg-light-orange bt-small mr10" onclick="applyToModifyHcSaleorderForTrader(${saleorder.saleorderId}, '<%= basePath %>')">申请修改</button>
                    </c:if>
                    <!-- add by Tomcat.Hui 2019/12/5 13:31 .Desc: VDERP-1325 分批开票 若订单状态为已完成，但开票状态为“未开票”或“部分开票”，仍然显示“申请修改”的按钮. end -->
				</c:if>
        </c:if>
        
        <c:if test="${saleorder.status != 3 &&  saleorder.status != 2}"><!-- 订单状态为未关闭 -->
       
        	<input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
        	<c:choose>
				<c:when test="${saleorder.validStatus eq 0}">
					<c:if test="${(null==taskInfo and null == taskInfo.getProcessInstanceId() and endStatus != '审核完成')or (null!=taskInfo and taskInfo.assignee==null and empty candidateUserMap[taskInfo.id])}">
						<button type="button" class="bt-bg-style bg-light-green bt-small mr10" onclick="applyValidSaleorder(${saleorder.saleorderId},${taskInfo.id == null ?0: taskInfo.id},${isNotDelPriceZero})">申请审核</button>
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 addtitle" tabTitle='{"num":"order_saleorder_edit<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./order/hc/edit.do?saleorderId=${saleorder.saleorderId}&orderType=5","title":"编辑订单"}'>编辑订单</button>
					</c:if>
					<c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
						<c:set var="shenhe" value="0"></c:set>
						<c:forEach items="${verifyUserList}" var="verifyUsernameInfo">
							<c:if test="${verifyUsernameInfo == curr_user.username}">
								<c:set var="shenhe" value="1"></c:set>
							</c:if>
						</c:forEach>
						<c:choose>
							<c:when test="${(taskInfo.assignee == curr_user.username or candidateUserMap['belong']) and shenhe!=1 and taskInfo.name != '产品线归属人审核'}">
                                <button type="button" onclick="operationCheck(${saleorder.saleorderId})" class="bt-bg-style bg-light-green bt-small mr10">审核通过</button>
                                <button type="button" id="orderComplement" class=" pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&saleorderId=${saleorder.saleorderId}&pass=true&type=1003"}'></button>
                                <button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&saleorderId=${saleorder.saleorderId}&pass=false&type=1003"}'>审核不通过</button>
							</c:when>
							<c:when test="${(taskInfo.assignee == curr_user.username or candidateUserMap['belong']) and shenhe!=1 and taskInfo.name == '产品线归属人审核'}">
							<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data"  layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=true&type=1"}'>审核通过</button>
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=false&type=1"}'>审核不通过</button>
							</c:when>
							<c:otherwise>
							
	        				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:when>
				<c:otherwise>
					<!-- button type="button" class="bt-bg-style bg-light-orange bt-small mr10" onclick="noValidSaleorder(${saleorder.saleorderId})">撤销生效</button-->
					<span class="bg-light-green bt-bg-style bt-small addtitle"
						tabTitle='{"num":"warehousesout_viewOutDetail_${saleorder.saleorderId }","link":"./order/saleorder/printOrder.do?saleorderId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}","title":"打印"}'>打印</span>
					<c:if test="${saleorder.companyId != 1}">
					<span class="bg-light-green bt-bg-style bt-small addtitle"
					tabTitle='{"num":"warehousesout_saleorder_${saleorder.saleorderId }","link":"./order/saleorder/printSaleOrder.do?saleorderId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}","title":"打印订单"}'>打印订单</span>
					</c:if>
					
					<!-- 未锁定 -->
					<c:if test="${saleorder.lockedStatus eq 0}">
						<c:if test="${customer.amount ne '0.00'}">
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"600px","height":"350px","title":"余额支付","link":"./initBalancePayment.do?saleorderId=${saleorder.saleorderId}"}'>余额支付</button>
						</c:if>
						<!-- 1允许开票2存在开票申请待审核-不允许;;;;:3允许提前开票，4存在提前开票待审核-不允许::::5全部开票----0全部售后 -->
                        <!-- modified by Tomcat.Hui 2020/1/2 13:09 .Desc: VDERP-1039 票货同行. start -->
                        <c:if test="${saleorder.isOpenInvoice eq 1}">
                            <c:choose>
                                <c:when test="${phtxFlag eq true and allSendFlag eq false}">
                                    <button type="button" class="bt-bg-style bt-small bg-light-greybe mr10" title="“票货同行”订单，物流部发货后开具发票。" disabled="disabled">申请开票</button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="bt-bg-style bg-light-green bt-small  mr10" onclick="isTraderAllowInvoice(${saleorder.traderId},'${saleorder.traderName}',${saleorder.invoiceType},1)">申请开票</button>
                                    <span id="invoiceApply" class="pop-new-data" layerParams='{"width":"80%","height":"600px","title":"申请开票","link":"../../finance/invoice/selectInvoiceItems.do?relatedId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}&isAuto=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&isAdvance=0&editFlag=true"}'></span>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <c:if test="${saleorder.isOpenInvoice eq 3}">
                            <c:choose>
                                <c:when test="${phtxFlag eq true and allSendFlag eq false}">
                                    <button type="button" class="bt-bg-style bt-small bg-light-greybe mr10" title="“票货同行”订单，物流部发货后开具发票。" disabled="disabled">申请提前开票</button>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="bt-bg-style bg-light-orange bt-small mr10"  onclick="isTraderAllowInvoice(${saleorder.traderId},'${saleorder.traderName}',${saleorder.invoiceType},2)">申请提前开票</button>
                                    <span id="advanceInvoiceApply" class="pop-new-data" layerParams='{"width":"80%","height":"600px","title":"申请提前开票","link":"../../finance/invoice/selectInvoiceItems.do?relatedId=${saleorder.saleorderId}&comment=${saleorder.invoiceComments}&isAuto=${saleorder.invoiceMethod}&invoiceType=${saleorder.invoiceType}&isAdvance=1&editFlag=true"}'></span>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <!-- modified by Tomcat.Hui 2020/1/2 13:09 .Desc: VDERP-1039 票货同行. end -->
						<c:if test="${saleorder.isOpenInvoice eq 5 or saleorder.isOpenInvoice eq 2 or saleorder.isOpenInvoice eq 4}">
				            <button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请开票</button>
						</c:if>
						<!-- 	1、订单状态为进行中，必要条件；
								2、订单商品（不含有特殊类别商品如运费等）数量>0，必要条件；
								3、订单无异常状态（锁定等），必要条件；
								4、订单商品数量>已采购数量 && 订单商品数量>已发货数量，必要条件；
								5、订单发货状态不是已发货，必要条件；
								6、已到款额（不分交易方式）<预付款；
								7、销售订单含有直发商品时，不允许申请提前采购；
						 -->
						<c:if test="${saleorder.status eq 1 && saleorder.lockedStatus eq 0 && saleorder.deliveryStatus ne 2 && saleorder.deliveryDirect eq 0 && saleorder.purchase eq 1 && ((null==taskInfoEarly and null == taskInfoEarly.getProcessInstanceId() and endStatusEarly != '审核完成')or (null!=taskInfoEarly and taskInfoEarly.assignee==null and empty candidateUserMapEarly[taskInfoEarly.id]))}">
			           		<button type="button" class="bt-bg-style bg-light-orange bt-small pop-new-data mr10" 
			           				layerParams='{"width":"600px","height":"200px","title":"申请提前采购","link":"../saleorder/applyPurchasePage.do?saleorderId=${saleorder.saleorderId}&taskId=${taskInfoEarly.id == null ?0: taskInfoEarly.id}"}' >申请提前采购</button>
			            </c:if>
			            
			            <c:if test="${saleorder.deliveryDirect == 1}" >
							<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 addtitle" tabTitle='{"num":"confirm_arrival<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"order/saleorder/confirmArrivalInit.do?saleorderId=${saleorder.saleorderId}","title":"确认收货"}'>确认收货</button>
						</c:if>
						
						<c:if test="${saleorder.status == 1 && saleorder.lockedStatus == 0}" >
							<!-- 
			            	<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 addtitle" tabTitle='{"num":"modify_apply<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"order/saleorder/modifyApplyInit.do?saleorderId=${saleorder.saleorderId}","title":"申请修改"}'>申请修改</button>
							 -->
			            	<button type="button" class="bt-bg-style bg-light-orange bt-small mr10" onclick="applyToModifyHcSaleorderForTrader(${saleorder.saleorderId}, '<%= basePath %>')">申请修改</button>
						</c:if>
					</c:if>
				</c:otherwise>
			</c:choose>
            
            <c:if test="${((saleorder.status == 0 or saleorder.status == 1) and saleorder.invoiceStatus == 0 and saleorder.deliveryStatus == 0 and saleorder.paymentStatus == 0  and saleorder.lockedStatus eq 0) || (saleorder.isCloseSale eq 1)}">
            	<button type="button" class="bt-bg-style bg-light-orange bt-small mr10" onclick="closeSaleorder(${saleorder.saleorderId})">关闭订单</button>
            </c:if>
    
        
         <c:if test="${(null!=taskInfoEarly and null!=taskInfoEarly.getProcessInstanceId() and null!=taskInfoEarly.assignee) or !empty candidateUserMapEarly[taskInfoEarly.id]}">
	
			<c:set var="shenhe" value="0"></c:set>
			<c:forEach items="${verifyUserListEarly}" var="verifyUsernameInfo">
				<c:if test="${verifyUsernameInfo == curr_user.username}">
					<c:set var="shenhe" value="1"></c:set>
				</c:if>
			</c:forEach>
			<c:choose>
				<c:when test="${(taskInfoEarly.assignee == curr_user.username or candidateUserMapEarly['belong']) and shenhe!=1}">
				<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoEarly.id}&pass=true&type=1"}'>审核通过</button>
				<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoEarly.id}&pass=false&type=1"}'>审核不通过</button>
				</c:when>
				<c:otherwise>
      				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请提前采购</button>
				</c:otherwise>
			</c:choose>
			
		</c:if>
        </c:if>
        
       </div>
        <div class="parts">
            <div class="title-container title-container-blue">
                <div class="table-title nobor">
					沟通记录
                </div>
                <c:if test="${saleorder.status != 3}">
                <div class="title-click nobor  pop-new-data" layerParams='{"width":"850px","height":"460px","title":"新增沟通记录","link":"./addComrecord.do?saleorderId=${saleorder.saleorderId}&traderId=${saleorder.traderId}"}'>
					新增
                </div>
                </c:if>
		            </div>
		            <table class="table">
		                <thead>
		            	    <tr>
		                        <th class="wid9">沟通时间</th>
		                        <th class="wid8">录音</th>
		                        <th class="wid9">联系人</th>
		                        <th class="wid11">联系方式</th>
		                        <th class="wid9">沟通方式</th>
		                        <th class="wid9">沟通目的</th>
		                        <th>沟通内容</th>
		                        <th class="wid11">操作人</th>
		                        <th class="wid9">下次联系日期</th>
		                        <th>下次沟通内容</th>
		                        <th>备注</th>
		                        <!-- <th>创建时间</th> -->
		                        <th class="wid9">操作</th>
		                    </tr>
		            </thead>
		                <tbody>
		               
		                    <c:forEach var="list" items="${communicateList}" varStatus="status">
								<tr>
									<td><date:date value ="${list.begintime}" format="yyyy-MM-dd"/><br>
										<date:date value ="${list.begintime}" format="HH:mm"/>~<date:date value ="${list.endtime}" format="HH:mm"/>
									</td>
									<%-- <td>
									<c:choose>
				                    	<c:when test="${list.communicateType == 244 }">
				                    		<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"view${list.relatedId}",
												"link":"./order/bussinesschance/toSalesDetailPage.do?bussinessChanceId=${list.relatedId}&traderId=${list.traderId }&traderCustomerId=${list.traderCustomerId }",
												"title":"销售商机详情"}'>${list.bussinessChanceNo }</a>
				                    	</c:when>
				                    	<c:when test="${list.communicateType == 245 }">
				                    		<a class="addtitle" tabtitle="{&quot;num&quot;:&quot;viewQuote${list.relatedId}&quot;,&quot;link&quot;:&quot;./order/quote/getQuoteDetail.do?quoteorderId=${list.relatedId}&viewType=3&quot;,&quot;title&quot;:&quot;报价详情&quot;}">${list.quoteorderNo}</a>
				                    	</c:when>
				                    	<c:when test="${list.communicateType == 246 }">
				                    		<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${list.relatedId}","link":"./order/saleorder/view.do?saleorderId=${list.relatedId}","title":"订单信息"}'>${list.saleorderNo}</a>
				                    	</c:when>
				                    	<c:otherwise></c:otherwise>
				                    </c:choose>							
									</td> --%>
									<td><c:if test="${not empty list.coidUri }">${list.communicateRecordId}</c:if></td>
									<td>${list.contactName}</td>
									<td>${list.phone}</td>
									<td>${list.communicateModeName}</td>
									<td>${list.communicateGoalName}</td>
									<td>
										<ul class="communicatecontent ml0">
											<c:if test="${not empty list.tag }">
												<c:forEach items="${list.tag }" var="tag">
													<li class="bluetag" title="${tag.tagName}">${tag.tagName}</li>
												</c:forEach>
											</c:if>
										</ul>
									</td>
									<td>${list.user.username}</td>
									<c:choose>
				                    	<c:when test="${list.isDone == 0 }">
					                   		<td class="font-red">${list.nextContactDate }</td>
				                    	</c:when>
				                    	<c:otherwise>
					                    	<td>${list.nextContactDate }</td>
				                    	</c:otherwise>
				                    </c:choose>
									<td>${list.nextContactContent}</td>
									<td>${list.comments}</td>
									<%-- <td><date:date value ="${list.addTime}"/></td> --%>
									<td class="caozuo">
										<c:if test="${saleorder.status != 3}">
				                        <span class="border-blue pop-new-data" layerParams='{"width":"850px","height":"460px","title":"编辑沟通记录","link":"./editCommunicate.do?communicateRecordId=${list.communicateRecordId}&traderId=${saleorder.traderId}"}'>编辑</span>
										</c:if>
									</td>
								</tr>
							</c:forEach>
							  <c:if test="${empty communicateList}">
		       			<!-- 查询无结果弹出 -->
		           		<tr>
		           			<td colspan="12">暂无沟通记录。</td>
		           		</tr>
		        	</c:if>
                </tbody>
            </table>
        	<div class="clear"></div>
        </div>
        <!-- 非南京总部公司 -->
        <c:if test="${invoiceApply != null && companyId != 1}">
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	开票审核记录 
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>操作人</th>
                        <th>操作时间</th>
                        <th>操作事项</th>
                        <th>备注</th>
                    </tr>
                 </thead>
                 <tbody>   
					<c:if test="${null!=historicActivityInstanceInvoice}">
                    <c:forEach var="hii" items="${historicActivityInstanceInvoice}" varStatus="status">
                    <c:if test="${not empty  hii.activityName}">
                    <tr>
                    	<td>
                    		<c:choose>
							<c:when test="${hii.activityType == 'startEvent'}"> 
							${startUserInvoice}
							</c:when>
							<c:when test="${hii.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstanceInvoice.size() == status.count}">
									${verifyUsersInvoice}
								</c:if>
								<c:if test="${historicActivityInstanceInvoice.size() != status.count}">
									${hii.assignee}  
								</c:if>   
							</c:otherwise>
						</c:choose>
                    	
                    	
                    	</td>
                        <td><fmt:formatDate value="${hii.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                        <c:choose>
									<c:when test="${hii.activityType == 'startEvent'}"> 
							开始
							</c:when>
									<c:when test="${hii.activityType == 'intermediateThrowEvent'}"> 
							结束
							</c:when>
							<c:otherwise>   
							${hii.activityName}  
							</c:otherwise>
						</c:choose>
						</td>
                        <td class="font-red">${commentMapInvoice[hii.taskId]}</td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                	<c:if test="${null==historicActivityInstanceInvoice}">
                		<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
                	</c:if>
		       			
                </tbody>
            </table>
        </div>
		</c:if>
        <!-- 南京总部展示开票申请 -->
        <c:if test="${curr_user.companyId == 1}">
        <div class="parts">
            <div class="title-container title-container-yellow">
                <div class="table-title nobor">
					开票申请
                </div>
            </div>
            <table class="table">
            
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
                                <c:if test="${list.isAuto eq 1}">手动纸质开票</c:if>
                                <c:if test="${list.isAuto eq 2}">自动纸质开票</c:if>
                                <c:if test="${list.isAuto eq 3}">自动电子发票</c:if>
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
                                <!-- add by Tomcat.Hui 2019/12/4 15:55 .Desc: VDERP-1325 分批开票 改为与财务订单详情页一致. start -->
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
                                <!-- add by Tomcat.Hui 2019/12/4 15:55 .Desc: VDERP-1325 分批开票 改为与财务订单详情页一致. end -->
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
                                <button type="button" class="bt-bg-style bg-light-orange bt-small  pop-new-data mr10" layerParams='{"width":"80%","height":"600px","title":"查看开票申请","link":"../../finance/invoice/selectInvoiceItems.do?relatedId=${list.relatedId}&invoiceApplyId=${list.invoiceApplyId}&editFlag=false"}' >查看开票申请</button>
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
	                	<tr>
	           			<td colspan="9">暂无开票申请。</td>
	           			</tr>
	           		</c:if>
                </tbody>
            </table>
        </div>
        </c:if>
        <div class="parts content1">
            <div class="title-container title-container-yellow">
                <div class="table-title nobor">
					交易信息
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th style="width: 100px">财务流水号</th>
                        <th style="width: 80px">业务类型</th>
                        <th style="width: 100px">交易金额</th>
                        <th style="width: 130px">交易时间</th>
                        <th style="width: 80px">交易主体</th>
                        <th style="width: 80px">交易方式</th>
                        <th>交易名称</th>
                        <th style="width: 200px">交易备注</th>
                        <th style="width: 100px">操作人</th>
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
		        	<tr>
                        <td colspan="10" style="text-align: left; background: #eaf2fd;">
                        	<!-- 判断客户是否触发账期 -->
                        	<c:set var="accountPeriodAmount" value="0"></c:set>
                        	<c:if test="${saleorderDataInfo['paymentAmount'] >= saleorder.accountPeriodAmount}">
                        		<c:set var="accountPeriodAmount" value="${saleorder.accountPeriodAmount}"></c:set>
                        	</c:if>
                        	订单原金额：<fmt:formatNumber type="number" value="${saleorder.totalAmount}" pattern="0.00" maxFractionDigits="2" />
                        	&nbsp;&nbsp;&nbsp;&nbsp;
                            订单实际金额：<fmt:formatNumber type="number" value="${saleorderDataInfo['realAmount']}" pattern="0.00" maxFractionDigits="2" />
                        	&nbsp;&nbsp;&nbsp;&nbsp;
                        	<%-- 客户已付款金额：<fmt:formatNumber type="number" value='${saleorderDataInfo["receivedAmount"]}' pattern="0.00" maxFractionDigits="2" />
                        	&nbsp;&nbsp;&nbsp;&nbsp; --%>
                        	<span style="color:red">未收金额：<fmt:formatNumber type="number" value='${saleorderDataInfo["realAmount"] - (saleorderDataInfo["paymentAmount"] + saleorderDataInfo["periodAmount"]  - saleorderDataInfo["lackAccountPeriodAmount"] - saleorderDataInfo["refundBalanceAmount"])}' pattern="0.00" maxFractionDigits="2" /></span>
                        	&nbsp;=&nbsp;
                        	订单实际金额：<fmt:formatNumber type="number" value='${saleorderDataInfo["realAmount"] }' pattern="0.00" maxFractionDigits="2" />
                        	&nbsp;-&nbsp;
                        	客户实付金额：<fmt:formatNumber type="number" value='${saleorderDataInfo["paymentAmount"] + saleorderDataInfo["periodAmount"] - saleorderDataInfo["lackAccountPeriodAmount"] - saleorderDataInfo["refundBalanceAmount"]}' pattern="0.00" maxFractionDigits="2" />
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="parts content1">
            <div class="title-container title-container-yellow">
                <div class="table-title nobor">
					发票信息
                </div>
                <c:set var="exitFor" value="0"></c:set>
				<c:forEach var="list" items="${saleInvoiceList}" varStatus="num">
					<c:if test="${list.invoiceProperty eq 2 and exitFor eq 0}">
						<c:set var="exitFor" value="1"></c:set>
						<%-- <span class="title-click nobor" onclick="sendInvoiceSms(${list.invoiceId})">推送</span> --%>
						<div class="title-click nobor pop-new-data" layerparams='{"width":"650px","height":"360px","title":"电子票推送","link":"<%=basePath%>finance/invoice/invoiceSmsAndMailInit.do?relatedId=${saleorder.saleorderId}"}'>电子票推送</div>
					</c:if>
				</c:forEach>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>发票号</th>
                        <th>票种</th>
                        <th style="width: 80px">红蓝字</th>
                        <th style="width: 80px">发票金额</th>
                        <th style="width: 130px">操作人</th>
                        <th style="width: 130px">操作时间</th>
                        <th>快递公司</th>
                        <th style="width: 120px">快递单号</th>
                        <th style="width: 80px">快递状态</th>
                        <th style="width: 120px">操作</th>
                    </tr>
                </thead>
				<tbody>
                	<c:set var="invoiceAmount" value="0.00"></c:set><!-- 已开票总额 -->
                	<c:forEach var="list" items="${saleInvoiceList}" varStatus="num">
                		<tr>
	                        <td>
	                        	<c:if test="${empty list.invoiceNo}">
	                        		<span class="bt-small bg-light-blue bt-bg-style" onclick="batchDownEInvoice()">下载电子发票</span>
	                        	</c:if>
	                        	<c:if test="${not empty list.invoiceNo}">
	                        		${list.invoiceNo}
	                        		<c:if test="${list.invoiceProperty eq 2}">
										<font color='red'>[电]</font>
									</c:if>
	                        	</c:if>
	                        </td>
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
												红字有效
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
	                        		<c:when test="${list.express.arrivalStatus eq 0}">未收货</c:when>
	                        		<c:when test="${list.express.arrivalStatus eq 1}">部分收货</c:when>
	                        		<c:when test="${list.express.arrivalStatus eq 2}">全部收货</c:when>
	                        	</c:choose>
							</td>
	                        <td>
	                        	<c:if test="${list.invoiceProperty eq 2 and not empty list.invoiceNo}">
	                        		<a href= "${list.invoiceHref}" target="_blank">下载</a>
		                        	<%-- &nbsp;&nbsp;
		                        	<span class="edit-user clcforbid" onclick="sendInvoiceSms(${list.invoiceId})">推送</span> --%>
	                        	</c:if>
                                <!-- add by Tomcat.Hui 2019/11/21 11:48 .Desc: VDERP-1325 分批开票 查看已开发票详情信息 start. -->
                                <a class="pop-new-data" layerParams='{"width":"850px","height":"460px","title":"查看已开发票详情","link":"<%=basePath%>finance/invoice/viewInvoicedItems.do?invoiceId=${list.invoiceId}"}' >查看</a>
                                <!-- add by Tomcat.Hui 2019/11/21 11:48 .Desc: VDERP-1325 分批开票 查看已开发票详情信息 end. -->
	                        </td>
	                    </tr>
                	</c:forEach>
                	<tr>
                        <td colspan="10" style="text-align: left; background: #eaf2fd;">
                        	已开票总额：<fmt:formatNumber type="number" value="${invoiceAmount}" pattern="0.00" maxFractionDigits="2" />
                        	&nbsp;&nbsp;&nbsp;&nbsp;
                        	<span style="color:red">未开票总额：<fmt:formatNumber type="number" value="${saleorderDataInfo['realAmount'] - invoiceAmount  }" pattern="0.00" maxFractionDigits="2" /></span>
                        </td>
                    </tr>
                </tbody>
            </table>
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
                        <th class="">快递公司</th>
                        <th class="">快递单号</th>
                        <th class="wid10">发货时间</th>
                        <th class="wid8">运费</th>
                        <th>商品</th>
                        <th>备注</th>
                        <th class="wid10">操作人</th>
                        <th class="wid10">快递状态</th>
                        <th>操作</th>
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
                            	<div>${spuMap[expressDetails.goodsId]}&nbsp;&nbsp;&nbsp;&nbsp;${expressDetails.num} ${expressDetails.unitName}</div>
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
                        <td>
                          <div class="print-record">
								<form method="post" id="searchSh" action="<%= basePath %>warehouse/warehousesout/printShOutOrder.do">
									 <input type="hidden"  name="orderId" id="orderId" value="${saleorder.saleorderId }"/>
									 <input type="hidden"  name="bussinessNo" id="bussinessNo" value="${saleorder.saleorderNo }"/>
									 <input type="hidden"  name="btype_sh" id="btype_sh" value=""/>
									 <input type="hidden"  name="expressId_xs" id="expressId_xs" value=""/>
									 <c:if test="${express.business_Type == 496}">
									   <span class="bt-border-style border-blue" onclick="printSHOutOrder('${express.expressId}',496,${saleorder.saleorderId});" id="searchSpan">打印送货单</span>
									 </c:if>
								</form>
						   </div>
						   <div class="customername pos_rel">
	                            <div class="brand-color1">
	                            <i class="bt-smaller bt-border-style border-blue pop-new-data" layerParams='{"width":"40%","height":"420px","title":"查看物流","link":"<%=basePath%>warehouse/warehousesout/queryExpressInfo.do?logisticsNo=${express.logisticsNo}"}'>查看物流</i>
	                            	
	                            </div>
	                             <div class="pos_abs customernameshow mouthControlPos">
										最新信息：${express.contentNew}
								</div>
							</div>
                        </td>
                    </tr>
                     </c:forEach>
                     <c:if test="${!empty expressList}">
                    <tr>
                        <td colspan="9" class="allchosetr text-left">
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
                            	 运费总额：<span class="mr10">${allamount}</span>商品总数：<span class="">${allnum}</span>
                            	 已发货总数：<span class="mr10">${allDeliveryNum}</span><span class="warning-color1">待发货数量：${allnum-allDeliveryNum}</span>
                        </td>
                    </tr>
                   </c:if>
                    <c:if test="${empty expressList}">
                     <tr>
                        <td colspan="9">暂无物流信息记录</td>
                    </tr>
                   </c:if>
                   
                </tbody>
            </table>
	</div>
	<%--<div class="parts">--%>
		<%--<div class="title-container">--%>
			<%--<div class="table-title nobor">出库记录</div>--%>
		<%--</div>--%>
		<%--<table class="table">--%>
			<%--<thead>--%>
				<%--<tr>--%>
					<%--<th class="wid5">选择</th>--%>
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
						<%--<td>--%>
                            <%--<input type="checkbox" name="c_checknox" alt="<date:date value ="${listout.addTime}" format="yyyy-MM-dd HH:mm:ss"/>" value="${listout.warehouseGoodsOperateLogId}" onclick="canelAll(this,'c_checknox')">--%>
                        <%--</td>--%>
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
						<%--<td>${ listout.barcode}</td>--%>
						<%--<td>${ listout.barcodeFactory}</td>--%>
						<%--<td>${0-listout.num}</td>--%>
						<%--<td><date:date value ="${listout.addTime}" format="yyyy-MM-dd HH:mm:ss"/></td>--%>
						<%--<td>${ listout.opName}</td>--%>
					<%--</tr>--%>
				<%--</c:forEach>--%>
				<%--<c:if test="${empty warehouseOutList}">--%>
					<%--<tr>--%>
						<%--<td colspan="11">暂无出库记录</td>--%>
					<%--</tr>--%>
				<%--</c:if>--%>
			<%--</tbody>--%>
		<%--</table>--%>
		<%--<c:if test="${not empty warehouseOutList}">--%>
		<%--<div class="table-style4">--%>
			<%--<div class="allchose">--%>
			<%--<input type="checkbox" name="all_c_checknox" onclick="selectall(this,'out_checkbox');" value="c_checknox"> <span>全选</span>--%>
			<%--</div>--%>
			<%--<div class="times">--%>
				<%--<span class="mr10">请选择批次</span>--%>
				<%--<c:forEach var="wtlist" items="${timeArrayWl}" varStatus="num">--%>
                 <%--<input type="checkbox" name="${wtlist}" id="out_checkbox" onclick="checkbarcode('${wtlist}', this.checked,'c_checknox');">--%>
                 <%--<span class="mr20">${wtlist}</span>--%>
                <%--</c:forEach>--%>
			<%--</div>--%>
			<%--<div class="print-record">--%>
				<%--<form method="post" id="searchc" action="<%= basePath %>warehouse/warehousesout/printOutOrder.do">--%>
					    <%--<input type="hidden"  name="orderId" id="orderId" value="${saleorder.saleorderId }"/>--%>
						<%--<input type="hidden"  name="bussinessNo" id="bussinessNo" value="${saleorder.saleorderNo }"/>--%>
						<%--<input type="hidden"  name="bussinessType" id="bussinessType" value="496"/>--%>
					    <%--<input type="hidden"  name="wdlIds" id="wdlIds" value=""/>--%>
					    <%--<input type="hidden"  name="type_f" id="type_f" value=""/>--%>
					    <%--<span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','0');" >打印出库单</span>--%>
					    <%--<span class="bt-border-style border-blue" onclick="printOutOrder('c_checknox','1');" >打印带效期出库单</span>--%>
				<%--</form>--%>
			    <%----%>
			<%--</div>--%>
		<%--</div>--%>
		<%--</c:if>--%>
	<%--</div>--%>
	<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	 审核记录
                </div>
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
									 <c:forEach var="vs" items="${verifyUsersList}" varStatus="status">
									 	<c:if test="${fn:contains(verifyUserList, vs)}">
									 		<span class="font-green">${vs}</span>&nbsp;
									 	</c:if>
									 	<c:if test="${!fn:contains(verifyUserList, vs)}">
									 		<span>${vs}</span>&nbsp;
									 	</c:if>
									 </c:forEach>
								
									 <c:if test="${empty verifyUsersList && empty hi.assignee}">
										${verifyUsers}
									</c:if>
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
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    订单修改申请
                </div>
            </div>
            <table class="table">
                <thead>
            	    <tr>
                        <th>订单修改申请单</th>
                        <th>申请人</th>
                        <th>审核状态</th>
                    </tr>
            	</thead>
                <tbody>
                	<c:forEach var="list" items="${saleorderModifyApplyList}" varStatus="num3">
						<tr>
							<td>
							<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleordermodifyapply${list.saleorderModifyApplyId}","link":"./order/saleorder/viewModifyApply.do?saleorderModifyApplyId=${list.saleorderModifyApplyId}&saleorderId=${list.saleorderId}","title":"订单信息"}'>${list.saleorderModifyApplyNo}</a>
	                        </td>
							<td>${list.createName}</td>
							<td>
								<c:choose>
									<c:when test="${list.verifyStatus eq 0}">
										审核中
									</c:when>
									<c:when test="${list.verifyStatus eq 1}">
										审核通过
									</c:when>
									<c:when test="${list.verifyStatus eq 2}">
										审核未通过
									</c:when>
									<c:otherwise>
										待审核
									</c:otherwise>
								</c:choose>
		                    </td>
						</tr>
					</c:forEach>
					<c:if test="${empty saleorderModifyApplyList}">
						<tr>
							<td colspan="3">暂无订单修改申请。</td>
						</tr>
					</c:if>
                </tbody>
            </table>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    提前采购申请
                </div>
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
									 <c:forEach var="vse" items="${verifyUsersListEarly}" varStatus="status">
									 	<c:if test="${fn:contains(verifyUserListEarly, vse)}">
									 		<span class="font-green">${vse}</span>&nbsp;
									 	</c:if>
									 	<c:if test="${!fn:contains(verifyUserListEarly, vse)}">
									 		<span>${vse}</span>&nbsp;
									 	</c:if>
									 </c:forEach>
									 
									 <c:if test="${empty verifyUsersListEarly && empty hi.assignee}">
										${verifyUsersEarly}
									</c:if>
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
                        <td>
                        	<c:choose>
	                        	<c:when test="${hie.activityName == '申请人'}"> 
									${saleorder.advancePurchaseComments}
								</c:when>
								<c:otherwise>   
									<span class="font-red">${commentMapEarly[hie.taskId]}</span>
								</c:otherwise>
                        	</c:choose>
                        </td>
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
        
        <div class="parts content1">
            <div class="title-container">
                <div class="table-title nobor">
                    合同回传
                </div>
                <c:set var="sizeCount" value="0"></c:set>
                	<c:forEach items="${saleorderAttachmentList}" var="list" varStatus="status">
                    	<c:if test="${list.attachmentFunction == 492}">
                    		<c:set var="sizeCount" value="${sizeCount+1}"></c:set>
                    	</c:if>
                </c:forEach>
                <c:if test="${saleorder.status != 3}">
                	<c:if test="${(saleorder.contractStatus == null || saleorder.contractStatus == 2)}">
                	<div class="title-click nobor pop-new-data" layerParams='{"width":"520px","height":"200px","title":"合同回传","link":"./contractReturnInit.do?saleorderId=${saleorder.saleorderId}"}'>上传</div>
                	</c:if>
                	<input type="hidden" id="alertCount" value="${saleorder.contractStatus == 2?0:1}">
                	<c:if test="${sizeCount>0 && (saleorder.contractStatus == null || saleorder.contractStatus == 2)}">
	                	<div class="title-click nobor" onclick="requestCheck(${saleorder.saleorderId},${taskInfoContractReturn.id == null ?0: taskInfoContractReturn.id})">申请审核</div>
                	</c:if>
            	</c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>合同回传</th>
                        <th>操作人</th>
                        <th>时间</th>
                        <th>审核状态</th>
                        <th>备注</th>
                        <th class="table-smallest2"> 操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:set var="contractReturnNum" value="0"></c:set>
                	<c:set var="statusCount" value="0"></c:set>
                	<c:set var="contractReturnRemark" value=""></c:set>
                	<c:if test="${null!=historicActivityInstanceContractReturn}">
                    <c:forEach var="hio" items="${historicActivityInstanceContractReturn}" varStatus="status">
                    <c:if test="${not empty  hio.activityName}">
                    	<c:if test="${hio.activityType != 'endEvent'}">
                    		<c:set var="contractReturnRemark" value="${commentMapContractReturn[hio.taskId]}"></c:set>
                    	</c:if>
                    </c:if>
                    </c:forEach>
                    </c:if>
                	<c:forEach items="${saleorderAttachmentList}" var="list" varStatus="status">
                    	<c:if test="${list.attachmentFunction == 492}">
                    		<c:set var="contractReturnNum" value="1"></c:set>
                    		<c:set var="statusCount" value="${statusCount+1}"></c:set>
							<tr>
		                        <td class="font-blue"><a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a></td>
		                        <td>${list.username}</td>
		                        <td><date:date value ="${list.addTime}"/></td>
                        		<c:if test="${statusCount==1}">
		                        	<td rowspan="${sizeCount}">
		                        		<c:if test="${saleorder.contractStatus == null}">
		                        			待审核
		                        		</c:if>
		                        		<c:if test="${saleorder.contractStatus == 0}">
		                        			审核中
		                        		</c:if>
		                        		<c:if test="${saleorder.contractStatus == 1}">
		                        			审核通过
		                        		</c:if>
		                        		<c:if test="${saleorder.contractStatus == 2}">
		                        			<span class="font-red">审核不通过</span>
		                        		</c:if>
									</td>
		                        	<td rowspan="${sizeCount}">${contractReturnRemark}</td>
		                        </c:if>
		                        <td>
		                            <div class="caozuo">
		                            	<c:if test="${saleorder.status != 3 && (saleorder.contractStatus == null || saleorder.contractStatus == 2)}">
		                                	<span class="caozuo-red" onclick="contractReturnDel(${list.attachmentId})">删除</span>
		                            	</c:if>
		                            </div>
		                        </td> 
		                        
		                    </tr>
                   		</c:if>
                   	</c:forEach>
                   	<c:if test="${contractReturnNum == 0}">
                   	<tr>
	           			<td colspan="6">暂无合同回传。</td>
	           		</tr>
	           		</c:if>
                </tbody>
            </table>
        </div>
        <div class="text-center mb15">
		<c:if test="${(null!=taskInfoContractReturn and null!=taskInfoContractReturn.getProcessInstanceId() and null!=taskInfoContractReturn.assignee) or !empty candidateUserMapContractReturn[taskInfoContractReturn.id]}">
	
			<c:set var="shenhe" value="0"></c:set>
			<c:forEach items="${verifyUserListContractReturn}" var="verifyUsernameInfo">
				<c:if test="${verifyUsernameInfo == curr_user.username}">
					<c:set var="shenhe" value="1"></c:set>
				</c:if>
			</c:forEach>
			<c:choose>
				<c:when test="${(taskInfoContractReturn.assignee == curr_user.username or candidateUserMapContractReturn['belong']) and shenhe!=1}">
				<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoContractReturn.id}&pass=true&type=1"}'>审核通过</button>
				<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfoContractReturn.id}&pass=false&type=1"}'>审核不通过</button>
				</c:when>
				<c:otherwise>
      				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请合同审核</button>
				</c:otherwise>
			</c:choose>
			
		</c:if>
		</div>
		<div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                   	 合同回传审核记录
                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>操作人</th>
                        <th>操作时间</th>
                        <th>操作事项</th>
                        <th>备注</th>
                    </tr>
                 </thead>
                 <tbody>   
                    <c:if test="${null!=historicActivityInstanceContractReturn}">
                    <c:forEach var="hio" items="${historicActivityInstanceContractReturn}" varStatus="status">
                    <c:if test="${not empty  hio.activityName}">
                    <tr>
                    	<td>
                    	<c:choose>
							<c:when test="${hio.activityType == 'startEvent'}"> 
							${startUserContractReturn}
							</c:when>
							<c:when test="${hio.activityType == 'intermediateThrowEvent'}">
							</c:when>
							<c:otherwise>
								<c:if test="${historicActivityInstanceContractReturn.size() == status.count}">
									${verifyUsersContractReturn}
								</c:if>
								<c:if test="${historicActivityInstanceContractReturn.size() != status.count}">
									${hio.assignee}  
								</c:if>   
							</c:otherwise>
						</c:choose>
                    	
                    	
                    	</td>
                        <td><fmt:formatDate value="${hio.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <td>
                        <c:choose>
									<c:when test="${hio.activityType == 'startEvent'}"> 
							开始
							</c:when>
									<c:when test="${hio.activityType == 'intermediateThrowEvent'}"> 
							结束
							</c:when>
							<c:otherwise>   
							${hio.activityName}  
							</c:otherwise>
						</c:choose>
						</td>
                        <td class="font-red">${commentMapContractReturn[hio.taskId]}</td>
                    </tr>
                    </c:if>
                    </c:forEach>
                    </c:if>
                	<c:if test="${null==historicActivityInstanceContractReturn}">
                		<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无记录！</td>
					    </tr>
                	</c:if>
                </tbody>
            </table>
        </div>
		
        <div class="parts content1">
            <div class="title-container">
                <div class="table-title nobor">
                    送货单回传
                </div>
                <c:if test="${saleorder.status != 3}">
                <div class="title-click nobor pop-new-data" layerParams='{"width":"520px","height":"200px","title":"送货单回传","link":"./deliveryOrderReturnInit.do?saleorderId=${saleorder.saleorderId}"}'>上传</div>
            	</c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th>送货单</th>
                        <th class="table-small">操作人</th>
                        <th class="table-small">时间</th>
                        <th class="table-smallest5">操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:set var="deliveryOrderReturnNum" value="0"></c:set>
                	<c:forEach items="${saleorderAttachmentList}" var="list" varStatus="status">
                    	<c:if test="${list.attachmentFunction == 493}">
                    		<c:set var="deliveryOrderReturnNum" value="1"></c:set>
							<tr>
		                        <td class="font-blue"><a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a></td>
		                        <td>${list.username}</td>
		                        <td><date:date value ="${list.addTime}"/></td>
		                        <td>
		                            <div class="caozuo">
		                            	<c:if test="${saleorder.status != 3}">
		                                <span class="caozuo-red" onclick="deliveryOrderReturnDel(${list.attachmentId})">删除</span>
		                            	</c:if>
		                            </div>
		                        </td>
		                    </tr>
                   		</c:if>
                   	</c:forEach>
                   	<c:if test="${deliveryOrderReturnNum == 0}">
                   	<tr>
	           			<td colspan="4">暂无送货单回传。</td>
	           		</tr>
	           		</c:if>
                </tbody>
                
            </table>
        </div>
        <div class="parts content1">
            <div class="title-container">
                <div class="table-title nobor">
                    录保卡
                </div>
                <c:if test="${saleorder.status != 3}">
                	 <div class="title-click nobor addtitle" tabTitle='{"num":"allgoodswarranty<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
						"link":"./order/saleorder/allgoodswarranty.do?saleorderId=${saleorder.saleorderId}","title":"全部"}'>全部</div>					
                </c:if>
              
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="table-small">产品名称</th>
                        <th class="table-small">品牌</th>
                        <th class="table-small">型号</th>
                        <th class="table-small">贝登条码</th>
                        <th class="table-small">厂商条码</th>
                        <th class="table-small">录保卡时间</th>
                        <th class="table-smallest10">操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:choose>
                		<c:when test="${not empty goodsWarrantys }">
                			<c:forEach items="${goodsWarrantys }" var="goodsWarranty">
                			<tr>
                				<td class="text-left">
                					<div class="brand-color1"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${goodsWarranty.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${goodsWarranty.goodsId}","title":"产品信息"}'>${goodsWarranty.goodsName }</a></div>
									<div>${goodsWarranty.sku }</div>
									<div>${goodsWarranty.materialCode }</div>
                				</td>
                				<td>${goodsWarranty.brandName }</td>
                				<td>${goodsWarranty.model }</td>
                				<td>${goodsWarranty.barcode }</td>
                				<td>${goodsWarranty.barcodeFactory }</td>
                				<td><date:date value ="${goodsWarranty.addTime}"/></td>
                				<td>
                					<span class="edit-user addtitle"
										tabTitle='{"num":"viewwarranty${goodsWarranty.saleorderGoodsWarrantyId }","link":"./order/saleorder/viewgoodswarranty.do?saleorderGoodsWarrantyId=${goodsWarranty.saleorderGoodsWarrantyId}","title":"查看保卡"}'>查看</span>
                				</td>
                			</tr>
                			</c:forEach>
                		</c:when>
                		<c:otherwise>
                			<tr>
		                        <td colspan="7">查询无结果！</td>
		                    </tr>
                		</c:otherwise>
                	</c:choose>
                </tbody>
            </table>
        </div>
        <div class="parts content1">
            <div class="title-container">
                <div class="table-title nobor">售后列表</div>
                <c:if test="${saleorder.status != 3 and saleorder.status != 0}">
					<c:if test="${saleorder.deliveryDirect eq 1}">
						<div class="title-click nobor" onclick="confirmMsg(${saleorder.saleorderId});">新增售后</div>
						<span style="display:none;"><div class="title-click nobor addtitle2" id="addAfter"></div></span>
					</c:if>
					<c:if test="${saleorder.deliveryDirect eq 0}">
						<div class="title-click nobor addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
									"link":"./order/saleorder/addAfterSalesPage.do?flag=th&&saleorderId=${saleorder.saleorderId}","title":"售后详情"}'>新增售后</div> 
					</c:if>
										
                </c:if>

            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
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
	                        <td>
	                        	<span class="font-blue addtitle" 
										tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./order/saleorder/viewAfterSalesDetail.do?afterSalesId=${aftersales.afterSalesId}","title":"售后详情"}'>${aftersales.afterSalesNo}</span>
	                        </td>
	                        <td>
	                        	<c:choose>
	                        		<c:when test="${aftersales.type eq 539}">销售退货</c:when>
	                        		<c:when test="${aftersales.type eq 540}">销售换货</c:when>
	                        		<c:when test="${aftersales.type eq 541}">销售安调</c:when>
	                        		<c:when test="${aftersales.type eq 584}">销售维修</c:when>
	                        		<c:when test="${aftersales.type eq 542}">销售退票</c:when>
	                        		<c:when test="${aftersales.type eq 543}">销售退款</c:when>
	                        		<c:when test="${aftersales.type eq 544}">销售订单技术咨询</c:when>
	                        		<c:when test="${aftersales.type eq 545}">销售订单其他</c:when>
	                        	</c:choose>
	                        </td>
	                        <td><date:date value ="${aftersales.addTime}"/></td>
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
       <input type="hidden" name="formToken" value="${formToken}"/>
        <input type="hidden" id="taskName" name="taskName" value="${taskInfo.name}"/>
    </div>
    <script type="text/javascript"
	src='<%= basePath %>static/js/logistics/warehouseOut/viewWarehouseOut.js?rnd=<%=Math.random()%>'></script>

    <%--<script type="text/javascript"--%>
        <%--src='<%= basePath %>static/new/js/pages/goods/goodinfoajax.js?rnd=<%=Math.random()%>'></script>--%>
<%@ include file="../../common/footer.jsp"%>

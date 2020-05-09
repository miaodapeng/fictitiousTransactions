<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后详情-换货" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/aftersales/order/hc_afterSales.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
    $(function(){
        var	url = page_url + '/order/hc/hcOrderDetailsPage.do?afterSalesId='+$("#afterSalesId").val();
        if($(window.frameElement).attr('src').indexOf("viewAfterSalesDetail")<0){
            $(window.frameElement).attr('data-url',url);
        }
    });
</script>
<div class="main-container">
    <div class="parts">
        <div class="title-container">
            <div class="table-title nobor">
                基本信息
            </div>
        </div>
        <table class="table">
            <tbody>
            <tr>
                <td class="wid20">订单号</td>
                <td>${afterSalesVo.afterSalesNo}</td>
                <td class="wid20">订单状态</td>
                <td>
                    <c:if test="${afterSalesVo.atferSalesStatus eq 0}">待确认</c:if>
                    <c:if test="${afterSalesVo.atferSalesStatus eq 1}">进行中</c:if>
                    <c:if test="${afterSalesVo.atferSalesStatus eq 2}">已完结</c:if>
                    <c:if test="${afterSalesVo.atferSalesStatus eq 3}">已关闭</c:if>
                </td>
            </tr>
            <tr>
                <td>创建者</td>
                <td>${afterSalesVo.creatorName}</td>
                <td>创建时间</td>
                <td><date:date value ="${afterSalesVo.addTime}"/></td>
            </tr>
            <tr>
                <td>生效状态</td>
                <td>
                    <c:if test="${afterSalesVo.validStatus eq 0}">未生效</c:if>
                    <c:if test="${afterSalesVo.validStatus eq 1}">已生效</c:if>
                </td>
                <td>生效时间</td>
                <td><date:date value ="${afterSalesVo.validTime}"/></td>
            </tr>
            <tr>
                <td>审核状态</td>
                <td>
                    <c:if test="${afterSalesVo.status eq 0}">待审核</c:if>
                    <c:if test="${afterSalesVo.status eq 1}">审核中</c:if>
                    <c:if test="${afterSalesVo.status eq 2}">审核通过</c:if>
                    <c:if test="${afterSalesVo.status eq 3}">审核不通过</c:if>
                </td>
                <td>售后类型</td>
                <td class="warning-color1">${afterSalesVo.typeName}</td>
            </tr>
            </tbody>
        </table>
    </div>

    <input type="hidden" id="afterSalesId" value="${afterSalesVo.afterSalesId}"/>
    <div class="parts">
        <div class="title-container">
            <div class="table-title nobor">
                售后申请
            </div>
        </div>
        <table class="table">
            <tbody>
            <tr>
                <td class="wid20">售后原因</td>
                <td>${afterSalesVo.reasonName}</td>
                <td class="wid20">联系人</td>
                <td>${afterSalesVo.traderContactName}</td>
            </tr>
            <tr>
                <td>电话</td>
                <td>
                    ${afterSalesVo.traderContactTelephone}
                    <c:if test="${not empty afterSalesVo.traderContactTelephone}">
                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${afterSalesVo.traderContactTelephone}',${afterSalesVo.traderId},1,4,${afterSalesVo.afterSalesId},${afterSalesVo.traderContactId});"></i>
                    </c:if>
                </td>
                <td>手机</td>
                <td>
                    ${afterSalesVo.traderContactMobile}
                    <c:if test="${not empty afterSalesVo.traderContactMobile}">
                        <i class="icontel cursor-pointer" title="点击拨号" onclick="callout('${afterSalesVo.traderContactMobile}',${afterSalesVo.traderId},1,4,${afterSalesVo.afterSalesId},${afterSalesVo.traderContactId});"></i>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>收货地区</td>
                <td>${afterSalesVo.area}</td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>收货地址</td>
                <td colspan="3" class="text-left">${afterSalesVo.address}</td>

            </tr>
            <tr>
                <td>详情说明</td>
                <td colspan="3" class="text-left">${afterSalesVo.comments}</td>

            </tr>
            <tr>
                <td>附件</td>
                <td colspan="3" class="text-left">
                    <c:if test="${not empty afterSalesVo.attachmentList }">
                        <c:forEach items="${afterSalesVo.attachmentList }" var="att">
                            <a href="http://${att.domain}${att.uri}" target="_blank">${att.name}</a>&nbsp;&nbsp;
                        </c:forEach>
                    </c:if>
                </td>

            </tr>
            </tbody>
        </table>
    </div>
    <div class="parts">
        <div class="title-container">
            <div class="table-title nobor">
                所属订单
            </div>
        </div>
        <table class="table">
            <tbody>
            <tr>
                <td class="wid20">订单号</td>
                <td >
                    <div class="customername pos_rel">
                               <span class="brand-color1 addtitle" tabTitle='{"num":"viewsaleorder${afterSalesVo.orderNo}","title":"订单信息",
                               		"link":"./order/saleorder/view.do?saleorderId=${afterSalesVo.orderId}"}'>${afterSalesVo.orderNo}</span><i class="iconbluemouth"></i>
                        <div class="pos_abs customernameshow" style="display: none;">
                            付款状态：<c:if test="${afterSalesVo.paymentStatus eq 0}">未付款</c:if>
                            <c:if test="${afterSalesVo.paymentStatus eq 1}">部分付款</c:if>
                            <c:if test="${afterSalesVo.paymentStatus eq 2}">全部付款</c:if><br>
                            发货状态：<c:if test="${afterSalesVo.deliveryStatus eq 0}">未发货</c:if>
                            <c:if test="${afterSalesVo.deliveryStatus eq 1}">部分发货</c:if>
                            <c:if test="${afterSalesVo.deliveryStatus eq 2}">全部发货</c:if><br>
                            开票状态：<c:if test="${afterSalesVo.invoiceStatus eq 0}">未开票</c:if>
                            <c:if test="${afterSalesVo.invoiceStatus eq 1}">部分开票</c:if>
                            <c:if test="${afterSalesVo.invoiceStatus eq 2}">全部开票</c:if><br>
                            收货状态：<c:if test="${afterSalesVo.arrivalStatus eq 0}">未收货</c:if>
                            <c:if test="${afterSalesVo.arrivalStatus eq 1}">部分收货</c:if>
                            <c:if test="${afterSalesVo.arrivalStatus eq 2}">全部收货</c:if>
                        </div>
                    </div>
                </td>
                <td class="wid20">订单金额</td>
                <td><fmt:formatNumber type="number" value="${afterSalesVo.totalAmount}" pattern="0.00" maxFractionDigits="2" /></td>
            </tr>
            <tr>
                <td>部门</td>
                <td>${afterSalesVo.orgName}</td>
                <td>归属销售</td>
                <td>${afterSalesVo.userName}</td>
            </tr>
            <tr>
                <td>订单状态</td>
                <td>
                    <c:if test="${afterSalesVo.saleorderStatus eq 0}">待确认</c:if>
                    <c:if test="${afterSalesVo.saleorderStatus eq 1}">进行中</c:if>
                    <c:if test="${afterSalesVo.saleorderStatus eq 2}">已完结</c:if>
                    <c:if test="${afterSalesVo.saleorderStatus eq 3}">已关闭</c:if>
                </td>
                <td>生效时间</td>
                <td><date:date value ="${afterSalesVo.saleorderValidTime}"/></td>
            </tr>
            <tr>
                <td>客户名称</td>
                <td>
                    <div class="customername pos_rel">
                                  <span class="brand-color1 addtitle" tabTitle='{"num":"viewcustomer${afterSalesVo.traderId}","title":"客户信息",
										"link":"./trader/customer/baseinfo.do?traderId=${afterSalesVo.traderId}"}'>${afterSalesVo.traderName}</span><i class="iconbluemouth"></i>
                        <div class="pos_abs customernameshow" style="display: none;">
                            客户性质：<c:if test="${afterSalesVo.customerNature eq 465}">分销</c:if>
                            <c:if test="${afterSalesVo.customerNature eq 466}">终端</c:if><br>
                            交易次数：${afterSalesVo.orderCount}<br>
                            交易金额：<fmt:formatNumber type="number" value="${afterSalesVo.orderTotalAmount}" pattern="0.00" maxFractionDigits="2" /><br>
                            上次交易日期：<date:date value ="${afterSalesVo.lastOrderTime}"/>
                        </div>
                    </div>
                </td>
                <td>客户等级</td>
                <td>
                    <c:if test="${afterSalesVo.customerLevel eq 146}">A</c:if>
                    <c:if test="${afterSalesVo.customerLevel eq 147}">B</c:if>
                    <c:if test="${afterSalesVo.customerLevel eq 148}">C</c:if>
                    <c:if test="${afterSalesVo.customerLevel eq 149}">D</c:if>
                    <c:if test="${afterSalesVo.customerLevel eq 150}">E</c:if>
                    <c:if test="${afterSalesVo.customerLevel eq 931}">S</c:if>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="parts">
        <div class="title-container">
            <div class="table-title nobor">换货信息</div>
        </div>
        <table class="table  table-style6">
            <thead>
            <tr>
                <th class="wid6">序号</th>
                <th class="wid18">产品名称</th>
                <th >品牌</th>
                <th class="wid8">型号</th>
                <th class="wid8">单价</th>
                <th class="wid8">数量</th>
                <th class="wid5">单位</th>
                <th class="wid5">直发</th>
                <th>已采购数量</th>
                <th>已发货数量</th>
                <th>已收货数量</th>
                <th>换货数量</th>
                <th>换货方式</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${not empty afterSalesVo.afterSalesGoodsList}">
                <c:forEach items="${afterSalesVo.afterSalesGoodsList}" var="asg" varStatus="sttaus">
                    <tr>
                        <td>${sttaus.count }</td>
                        <td class="text-left">
                            <div class="customername pos_rel">
		                                       <span class="brand-color1 addtitle" tabTitle='{"num":"viewgoods<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                                       		"link":"./goods/goods/viewbaseinfo.do?goodsId=${asg.goodsId}","title":"产品信息"}'>${asg.goodsName}</span><i class="iconbluemouth"></i>
                                <br>${asg.sku}<br>${asg.materialCode}
                                <div class="pos_abs customernameshow" style="display: none;">
                                    注册证：${asg.registrationNumber}<br>
                                    管理类别：${asg.manageCategoryName}<br>
                                    产品归属：<c:if test="${not empty asg.userList }">
                                    <c:forEach items="${asg.userList }" var="user" varStatus="st">
                                        ${user.username } <c:if test="${st.count != asg.userList.size() }">、</c:if>
                                    </c:forEach>
                                </c:if> <br>
                                    采购提醒：${asg.purchaseRemind}<br>
                                    包装清单：${asg.packingList}<br>
                                    服务条款：${asg.tos}<br>
                                    订单占用：${asg.orderOccupy}<br>
                                    可调剂：${asg.adjustableNum}<br>
                                    库存：${asg.goodsStock}
                                </div>
                            </div>
                        </td>
                        <td>${asg.brandName}</td>
                        <td>
                                ${asg.model}
                        </td>
                        <td>${asg.saleorderPrice}</td>
                        <td>${asg.saleorderNum}</td>
                        <td>${asg.unitName}</td>
                        <td>
                            <c:if test="${asg.saleorderDeliveryDirect eq 0}">否</c:if>
                            <c:if test="${asg.saleorderDeliveryDirect eq 1}">是</c:if>
                        </td>
                        <td>${asg.buyNum}</td>
                        <td>${asg.deliveryNum}</td>
                        <td>${asg.receiveNum}</td>
                        <td class="warning-color1">${asg.num}</td>
                        <td class="warning-color1">
                            <c:if test="${asg.deliveryDirect eq 0}">普发</c:if>
                            <c:if test="${asg.deliveryDirect eq 1}">直发</c:if>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="13" class="allchosetr text-left">
                        <c:set var="sum" value="0"></c:set>
                        <c:forEach items="${afterSalesVo.afterSalesGoodsList}" var="asg">
                            <c:set var="sum" value="${sum+asg.num}"></c:set>
                        </c:forEach>
                        换货总件数:<span class="warning-color1 mr10">${sum}</span>
                    </td>
                </tr>
            </c:if>
            <c:if test="${empty afterSalesVo.afterSalesGoodsList}">
                <tr>
                    <td colspan="13">暂无记录</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>

    <div class="parts">
        <div class="title-container">
            <div class="table-title nobor">
                售后服务费
            </div>
        </div>
        <table class="table">
            <tbody>
            <tr>
                <td>退换货手续费</td>
                <td>${afterSalesVo.serviceAmount }</td>
                <td>票种</td>
                <td>
                    <c:if test="${afterSalesVo.invoiceType eq 429}">17%增值税专用发票
                        <c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        <c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                    </c:if>
                    <c:if test="${afterSalesVo.invoiceType eq 430}">17%增值税普通发票
                        <c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        <c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                    </c:if>
                    <c:if test="${afterSalesVo.invoiceType eq 682}">16%增值税专用发票
                        <c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        <c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                    </c:if>
                    <c:if test="${afterSalesVo.invoiceType eq 681}">16%增值税普通发票
                        <c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        <c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                    </c:if>
					<c:if test="${afterSalesVo.invoiceType eq 972}">13%增值税专用发票
                        <c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        <c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                    </c:if>
                    <c:if test="${afterSalesVo.invoiceType eq 971}">13%增值税普通发票
                        <c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        <c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                    </c:if>
                    <c:if test="${afterSalesVo.invoiceType eq 683}">6%增值税普通发票
                        <c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        <c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                    </c:if>
                    <c:if test="${afterSalesVo.invoiceType eq 684}">6%增值税专用发票
                        <c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        <c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                    </c:if>
                    <c:if test="${afterSalesVo.invoiceType eq 685}">3%增值税普用发票
                        <c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        <c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                    </c:if>
                    <c:if test="${afterSalesVo.invoiceType eq 686}">3%增值税专用发票
                        <c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        <c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                    </c:if>
                    <c:if test="${afterSalesVo.invoiceType eq 687}">0%增值税普通发票
                        <c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        <c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                    </c:if>
                    <c:if test="${afterSalesVo.invoiceType eq 688}">0%增值税专用发票
                        <c:if test="${afterSalesVo.isSendInvoice eq 1}">（寄送）</c:if>
                        <c:if test="${afterSalesVo.isSendInvoice eq 0}">（不寄送）</c:if>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td>收票客户</td>
                <td>${afterSalesVo.invoiceTraderName }</td>
                <td>收票联系人</td>
                <td>${afterSalesVo.invoiceTraderContactName }</td>
            </tr>
            <tr>
                <td>电话</td>
                <td>${afterSalesVo.invoiceTraderContactTelephone }</td>
                <td>手机</td>
                <td>${afterSalesVo.invoiceTraderContactMobile }</td>
            </tr>
            <tr>
                <td>收票地区</td>
                <td>${afterSalesVo.invoiceTraderArea }</td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>收票地址</td>
                <td colspan="3">${afterSalesVo.invoiceTraderAddress }</td>
            </tr>
            <tr>
                <td>开票备注</td>
                <td colspan="3">${afterSalesVo.invoiceComments }</td>
            </tr>
            </tbody>
        </table>
    </div>

    <c:if test="${afterSalesVo.status eq 2}">
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">交易信息</div>
            </div>
            <table class="table">
                <thead>
                <tr>
                    <th>记账编号</th>
                    <th>业务类型</th>
                    <th>交易金额</th>
                    <th>交易时间</th>
                    <th>交易主体</th>
                    <th>交易方式</th>
                    <th>交易名称</th>
                    <th>交易备注</th>
                    <th>操作人</th>
                    <th>操作时间</th>
                    <th>电子回执单</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty afterSalesVo.afterCapitalBillList}">
                    <c:forEach items="${afterSalesVo.afterCapitalBillList}" var="acb">
                        <tr>
                            <td>${acb.capitalBillNo}</td>
                            <td>
                                <c:if test="${acb.capitalBillDetail.bussinessType eq 525}">订单付款</c:if>
                                <c:if test="${acb.capitalBillDetail.bussinessType eq 526}">订单收款</c:if>
                                <c:if test="${acb.capitalBillDetail.bussinessType eq 531}">退款</c:if>
                                <c:if test="${acb.capitalBillDetail.bussinessType eq 532}">资金转移</c:if>
                                <c:if test="${acb.capitalBillDetail.bussinessType eq 533}">信用还款</c:if>
                            </td>
                            <td><fmt:formatNumber type="number" value="${acb.amount}" pattern="0.00" maxFractionDigits="2" /></td>
                            <td>
                                <c:if test="${acb.traderTime != 0}">
                                    <date:date value="${acb.traderTime}" />
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${acb.traderSubject == 1}">
                                    对公
                                </c:if>
                                <c:if test="${acb.traderSubject == 2}">
                                    对私
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${acb.traderMode eq 520}">支付宝</c:if>
                                <c:if test="${acb.traderMode eq 521}">银行</c:if>
                                <c:if test="${acb.traderMode eq 522}">微信</c:if>
                                <c:if test="${acb.traderMode eq 522}">现金</c:if>
                                <c:if test="${acb.traderMode eq 527}">信用支付</c:if>
                                <c:if test="${acb.traderMode eq 528}">余额支付</c:if>
                                <c:if test="${acb.traderMode eq 529}">退还信用</c:if>
                                <c:if test="${acb.traderMode eq 530}">退还余额</c:if>
                            </td>
                            <td>${acb.payer}</td>
                            <td class="text-left">${acb.comments}</td>
                            <td>${acb.creatorName}</td>
                            <td>
                                <c:if test="${acb.addTime != 0}">
                                    <date:date value="${acb.addTime}" />
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${(acb.traderType == 2 || acb.traderType == 5) && acb.bankBillId != 0}">
                                    <div class="caozuo">
                                        <span class="caozuo-blue addtitle"   tabTitle='{"num":"credentials${acb.bankBillId}", "link":"<%=basePath%>finance/capitalbill/credentials.do?bankBillId=${acb.bankBillId}","title":"电子回执单"}'>查看</span>
                                    </div>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
        </div>

        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">换货入库记录</div>
            </div>
            <table class="table  table-style6">
                <thead>
                <tr>
                    <th class="wid6">序号</th>
                    <th class="wid18">产品名称</th>
                    <th class="wid12">品牌</th>
                    <th class="wid8">型号</th>
                    <th class="wid8">数量</th>
                    <th class="wid5">单位</th>
                    <th class="wid10">贝登条码</th>
                    <th class="wid10">厂商条码</th>
                    <th class="wid12">入库时间</th>
                    <th class="wid6">操作人</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty afterSalesVo.afterReturnInstockList}">
                    <c:forEach items="${afterSalesVo.afterReturnInstockList}" var="arg" varStatus="num_index">
                        <tr>
                            <td>${num_index.count }</td>
                            <td class="text-left">
                                <div class="customername pos_rel">
										<span class="brand-color1 addtitle"
                                              tabTitle='{"num":"viewgoods${arg.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${arg.goodsId}", "title":"产品信息"}'>
                                                ${arg.goodsName}
                                        </span>
                                    <br>${arg.sku}
                                    <br>${arg.materialCode}
                                </div>
                            </td>
                            <td>${arg.brandName}</td>
                            <td>${arg.model}</td>
                            <td>${arg.num}</td>
                            <td>${arg.unitName}</td>
                            <td>${arg.barcode}</td>
                            <td>${arg.barcodeFactory}</td>
                            <td><date:date value="${arg.addTime}" /></td>
                            <td>${arg.operator}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty afterSalesVo.afterReturnInstockList}">
                    <tr>
                        <td colspan="10">暂无记录</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>

        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">换货出库记录</div>
            </div>
            <table class="table  table-style6">
                <thead>
                <tr>
                    <th class="wid6">序号</th>
                    <th class="wid18">产品名称</th>
                    <th class="wid12">品牌</th>
                    <th class="wid8">型号</th>
                    <th class="wid8">数量</th>
                    <th class="wid5">单位</th>
                    <th class="wid10">贝登条码</th>
                    <th class="wid10">厂商条码</th>
                    <th class="wid12">出库时间</th>
                    <th class="wid6">操作人</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty afterSalesVo.afterReturnOutstockList}">
                    <c:forEach items="${afterSalesVo.afterReturnOutstockList}" var="arg" varStatus="num_index">
                        <tr>
                            <td>${num_index.count }</td>
                            <td class="text-left">
                                <div class="customername pos_rel">
										<span class="brand-color1 addtitle"
                                              tabTitle='{"num":"viewgoods${arg.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${arg.goodsId}", "title":"产品信息"}'>
                                                ${arg.goodsName}
                                        </span>
                                    <br>${arg.sku}
                                    <br>${arg.materialCode}
                                </div>
                            </td>
                            <td>${arg.brandName}</td>
                            <td>${arg.model}</td>
                            <td>${arg.num < 0?-arg.num:arg.num}</td>
                            <td>${arg.unitName}</td>
                            <td>${arg.barcode}</td>
                            <td>${arg.barcodeFactory}</td>
                            <td><date:date value="${arg.addTime}" /></td>
                            <td>${arg.operator}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty afterSalesVo.afterReturnOutstockList}">
                    <tr>
                        <td colspan="10">暂无记录</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>

        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    物流信息
                </div>
            </div>
            <table class="table  table-style6">
                <thead>
                <tr>
                    <th class="wid10">快递单号</th>
                    <th class="wid10">快递公司</th>
                    <th class="wid10">发货时间</th>
                    <th class="wid8">运费</th>
                    <th>商品</th>
                    <th class="wid15">备注</th>
                    <th class="wid10">操作人</th>
                    <th class="wid8">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="express" items="${afterSalesVo.expresseList}">
                    <tr>
                        <td>${express.logisticsNo}</td>
                        <td>${express.logisticsName}</td>
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
                                <div>${expressDetails.goodName}&nbsp;&nbsp;&nbsp;&nbsp;${expressDetails.num} ${expressDetails.unitName}</div><br/>
                            </c:forEach>
                        </td>
                        <td>${express.logisticsComments}</td>
                        <td>${express.updaterUsername}</td>
                        <td>
                            <span class="bt-smaller bt-border-style border-blue addtitle" tabTitle='{"num":"editExpress","link":"./order/buyorder/editExpress.do?expressId=${express.expressId}&buyorderId=${buyorderVo.buyorderId}","title":" 编辑快递"}'>编辑</span>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${!empty afterSalesVo.expresseList}">
                    <tr>
                        <td colspan="8" class="allchosetr text-left">
                            <!-- 总运费 -->
                            <c:set var="allamount" value="0.00"></c:set>
                            <!-- 总数量 -->
                            <c:set var="allarrivalnum" value="0"></c:set>
                            <c:forEach var="express" items="${afterSalesVo.expresseList}">
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
                            <c:forEach var="bgv" items="${afterSalesVo.afterSalesGoodsList}" varStatus="num">
                                <c:set var="allnum" value="${allnum + bgv.num}"></c:set>
                            </c:forEach>
                            运费总额：<span class="warning-color1 mr10">${allamount}</span>已发/商品总数:<span class="warning-color1">${allarrivalnum}/${allnum}</span>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${empty afterSalesVo.expresseList}">
                    <tr>
                        <td colspan="8">暂无记录！</td>
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
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                <tr>
                    <th>合同</th>
                    <th class="table-small">操作人</th>
                    <th class="table-small">时间</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${afterSalesVo.afterContractAttachmentList}" var="list" varStatus="status">
                    <tr>
                        <td class="font-blue"><a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a></td>
                        <td>${list.username}</td>
                        <td><date:date value ="${list.addTime}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
    <!-- 沟通记录只能让售后看 -->
    <c:if test="${sessionScope.curr_user.positType eq 312}">
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    沟通记录
                </div>
                <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2 && afterSalesVo.verifyStatus ne 0}">
                    <div class="title-click nobor  pop-new-data" layerParams='{"width":"850px","height":"460px","title":"新增沟通记录",
	                		"link":"<%= basePath %>/aftersales/order/addCommunicatePage.do?afterSalesId=${afterSalesVo.afterSalesId}&&traderId=${afterSalesVo.traderId }&&traderType=1"}'>
                        新增
                    </div>
                </c:if>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                <tr>
                    <th class="wid10">沟通时间</th>
                    <th class="">录音</th>
                    <th class="">联系人</th>
                    <th class="">联系方式</th>
                    <th class="">沟通方式</th>
                    <th class="wid30">沟通内容</th>
                    <th class="">操作人</th>
                    <th class="wid8">下次联系日期</th>
                    <th class="wid15">下次沟通内容</th>
                    <th class="">备注</th>
                    <th class="wid10">创建时间</th>
                    <th class="wid6">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty communicateList}">
                    <c:forEach items="${communicateList}" var="communicateRecord" varStatus="">
                        <tr>
                            <td><date:date value ="${communicateRecord.begintime} "/>~<date:date value ="${communicateRecord.endtime}" format="HH:mm:ss"/></td>
                            <td><c:if test="${not empty communicateRecord.coidUri }">${communicateRecord.communicateRecordId }</c:if></td>
                            <td>${communicateRecord.contactName}</td>
                            <td>${communicateRecord.phone}</td>
                            <td>${communicateRecord.communicateModeName}</td>
                            <td>
                                <ul class="communicatecontent ml0">
                                    <c:choose>
                                        <c:when test="${not empty communicateRecord.tag }">
                                            <c:forEach items="${communicateRecord.tag }" var="tag">
                                                <li class="bluetag" title="${tag.tagName}">${tag.tagName}</li>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <li>${communicateRecord.contactContent }</li>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </td>
                            <td>${communicateRecord.user.username}</td>
                            <c:choose>
                                <c:when test="${communicateRecord.isDone == 0 }">
                                    <td class="font-red">${communicateRecord.nextContactDate }</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${communicateRecord.nextContactDate }</td>
                                </c:otherwise>
                            </c:choose>
                            <td>${communicateRecord.nextContactContent}</td>
                            <td>${communicateRecord.comments}</td>
                            <td><date:date value ="${communicateRecord.addTime} "/></td>

                            <td class="caozuo">
                                <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2 && afterSalesVo.verifyStatus ne 0}">
		                        	<span class="border-blue pop-new-data" layerParams='{"width":"60%","height":"63%","title":"编辑沟通记录",
		                        		"link":"<%= basePath %>/aftersales/order/editcommunicate.do?orderFlag=${afterSalesVo.atferSalesStatus }&flag=${afterSalesVo.status }&communicateRecordId=${communicateRecord.communicateRecordId}&afterSalesId=${afterSalesVo.afterSalesId}&&traderId=${afterSalesVo.traderId }&&traderType=1"}'>编辑</span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty communicateList}">
                    <!-- 查询无结果弹出 -->
                    <tr>
                        <td colspan='12'>暂无记录！</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </c:if>
    <div class="parts">
        <div class="title-container">
            <div class="table-title nobor">
                售后过程
            </div>
        </div>
        <table class="table">
            <thead>
            <tr>
                <th>沟通时间</th>
                <th>操作人</th>
                <th>售后内容</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${not empty afterSalesVo.afterSalesRecordVoList}">
                <c:forEach items="${afterSalesVo.afterSalesRecordVoList}" var="asi" >
                    <tr>
                        <td><date:date value ="${asi.addTime} "/></td>
                        <td>${asi.optName}</td>
                        <td>${asi.content}</td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty afterSalesVo.afterSalesRecordVoList}">
                <!-- 查询无结果弹出 -->
                <tr>
                    <td colspan='3'>暂无记录！</td>
                </tr>
            </c:if>
            </tbody>
        </table>
        <div class="table-buttons">
            <c:if test="${afterSalesVo.atferSalesStatus ne 3 && afterSalesVo.atferSalesStatus ne 2 && (curr_user.positType eq 310 || curr_user.positType eq 312 || curr_user.positType eq 589)}">
                <form action="" method="post" id="myform">
                    <input type="hidden" name="afterSalesId" value="${afterSalesVo.afterSalesId}"/>
                    <input type="hidden" name="orderId" value="${afterSalesVo.orderId}"/>
                    <input type="hidden" name="subjectType" value="${afterSalesVo.subjectType}"/>
                    <c:forEach items="${afterSalesVo.afterSalesGoodsList}" var="asg" varStatus="sttaus">
                        <input type="hidden" name="sku" value="${asg.sku}" />
                    </c:forEach>
                    <input type="hidden" name="type" value="${afterSalesVo.type}"/>
                    <input type="hidden" name="formToken" value="${formToken}"/>
                    <c:if test="${afterSalesVo.status eq 0 || afterSalesVo.status eq 3}">
                        <input type="hidden" name="taskId" value="${taskInfo.id == null ?0: taskInfo.id}"/>
                        <button type="button" class="bt-bg-style bg-light-green bt-small" onclick="applyAudit();">申请审核</button>
                        <button type="button" class="bt-bg-style bg-light-green bt-small" onclick="editAfterSales(1);">编辑</button>
                    </c:if>
                    <c:if test="${afterSalesVo.status ne 1 && afterSalesVo.closeStatus eq 1}">
                        <button type="button" class="bt-bg-style bg-light-orange bt-small" onclick="colse();">关闭订单</button>
                    </c:if>
                </form>
            </c:if>
        </div>
        <div class="table-buttons">
            <form action="" method="post" id="myform">
                <input type="hidden" name="afterSalesId" value="${afterSalesVo.afterSalesId}"/>
                <input type="hidden" name="subjectType" value="${afterSalesVo.subjectType}"/>
                <input type="hidden" name="type" value="${afterSalesVo.type}"/>
                <c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
                    <c:choose>
                        <c:when test="${taskInfo.assignee == curr_user.username or candidateUserMap['belong']}">
                            <button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=true&type=2"}'>审核通过</button>
                            <button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&pass=false&type=2"}'>审核不通过</button>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </form>
        </div>
    </div>

    <div class="parts">
        <div class="title-container">
            <div class="table-title nobor">
                审核记录
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
            <c:if test="${null==historicActivityInstance}">
                <!-- 查询无结果弹出 -->
                <tr>
                    <td colspan='4'>暂无记录！</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>

</div>
</body>

</html>

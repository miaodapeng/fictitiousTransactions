
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="客户详情" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript">
$(function() {
    $('.customer ul li').click(function() {
        $(this).children('a').addClass('customer-color');
        $(this).siblings().children('a').removeClass('customer-color');
    })
    $('.salespages').click(function() {
        var srcs = $(this).attr('srcs');
        if (typeof(srcs) == 'undefined') {
            alert('参数错误');
        } else {
            srcs = $.parseJSON(srcs);
        }
        var link=srcs.src;
        $('#tab_frame_1').attr('src',link);
    })
})
</script>


	<div class="closable-tab  closable-tab-page">
        <div>
            <!-- 此处是相关代码 -->
            <div class="customer">
                <ul role="tablist">
                    <li role="presentation" class="active" id="sale1" title="">
                        <a href="#sale11" data-toggle="tab" class="salespages" srcs='{ "src":"${pageContext.request.contextPath}/trader/customer/baseinfo.do?traderCustomerId=${traderCustomer.traderCustomerId}"}'>基本信息</a>
                    </li>
                    <li role="presentation" class="" id="sale2" title="">
                        <a href="#sale22" role="tab" data-toggle="tab" class="salespages" srcs='{ "src":"${pageContext.request.contextPath}/trader/customer/manageinfo.do?traderCustomerId=${traderCustomer.traderCustomerId}"}'>管理信息</a>
                    </li>
                    <li role="presentation" class="" id="#sale3" title="">
                        <a href="#sale33" role="tab" data-toggle="tab" class="salespages" srcs='{ "src":"${pageContext.request.contextPath}/trader/customer/getFinanceAndAptitude.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}"}'>财务与资质信息</a>
                    </li>
                    <li role="presentation" class="" id="#sale4" title="">
                        <a href="#sale44" role="tab" data-toggle="tab" class="salespages" srcs='{ "src":"${pageContext.request.contextPath}/trader/customer/getContactsAddress.do?traderId=${traderCustomer.traderId}"}'>联系人与地址</a>
                    </li>
                    <li role="presentation" class="" id="#sale5" title="">
                        <a href="#sale55" role="tab" data-toggle="tab" class="salespages" srcs='{ "src":"salesmanage/businessofferorder.html"}'>商机/报价/订单</a>
                    </li>
                    <li role="presentation" class="" id="#sale6" title="">
                        <a href="#sale66" role="tab" data-toggle="tab" class="salespages" srcs='{ "src":"salesmanage/transactionrecords.html"}'>交易记录</a>
                    </li>
                    <li role="presentation" class="" id="#sale7" title="">
                        <a href="#sale77" role="tab" data-toggle="tab" class="salespages" srcs='{ "src":"${pageContext.request.contextPath}/trader/customer/communicaterecord.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}"}'>沟通记录</a>
                    </li>
                    <li role="presentation" class="" id="#sale8" title="">
                        <a href="#sale88" role="tab" data-toggle="tab" class="nobor salespages" srcs='{ "src":"salesmanage/customeranalysis.html"}'>客户分析</a>
                    </li>
                </ul>
            </div>
            <div class="tab-content" style="width:100%;">
                <div role="tabpanel" class="tab-pane active" id="sale11" style="width: 100%;">
                    <iframe src="${pageContext.request.contextPath}/trader/customer/baseinfo.do?traderCustomerId=${traderCustomer.traderCustomerId}" id="tab_frame_1" frameborder="0" class="iframestyle">
                    </iframe>
                </div>
            </div>
            <!-- 相关代码结束 -->
        </div>
    </div>
</body>

</html>

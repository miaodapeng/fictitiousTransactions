<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="产品详情" scope="application" />
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
                        <a href="#sale11" data-toggle="tab" class="salespages customer-color" srcs='{ "src":"${pageContext.request.contextPath}/goods/goods/viewbaseinfo.do?goodsId=${goods.goodsId}"}'>基本信息</a>
                    </li>
                    <li role="presentation" class="" id="sale2" title="">
                        <a href="#sale22" role="tab" data-toggle="tab" class="salespages" srcs='{ "src":"${pageContext.request.contextPath}/goods/goods/viewsaleinfo.do?goodsId=${goods.goodsId}"}'>销售信息</a>
                    </li>
                    <li role="presentation" class="" id="#sale3" title="">
                        <a href="#sale33" role="tab" data-toggle="tab" class="salespages" srcs='{ "src":"${pageContext.request.contextPath}/goods/goods/viewoperateinfo.do?goodsId=${goods.goodsId}"}'>运营信息</a>
                    </li>
                </ul>
            </div>
            <div class="tab-content" style="width:100%;">
                <div role="tabpanel" class="tab-pane active" id="sale11" style="width: 100%;">
                    <iframe src="${pageContext.request.contextPath}/goods/goods/viewbaseinfo.do?goodsId=${goods.goodsId}" id="tab_frame_1" frameborder="0" class="iframestyle">
                    </iframe>
                </div>
            </div>
            <!-- 相关代码结束 -->
        </div>
    </div>
<%@ include file="../../common/footer.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="沟通记录" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
        // 头部滚动
$(function() {
    var thead = $('.table thead');
    var tbodyTr = $('.table tbody tr');
    var movingTop, setWid, setLastLiWid, replaceThead;
    var theadThs = thead.find('th');
    var thLeng = theadThs.length;
    var tbodyTrLeng = tbodyTr.length;
    // 创建DOM 
    var replaceTheadDiv = $('<div class="replaceThead"><ul style="margin-bottom:0;"></ul></div>');
    var whiteSpace = $('<div class="white-space"></div>');

    var fixdiv = $('.fixdiv');
    var superdiv = $('.superdiv');
var   fixdivPosTop = $('.customer').outerHeight()+$('.title-container').outerHeight();
    //创建滑动表头里面的元素；公用
    // 1
    function createReplaceTheadLi() {
        for (var i = 0; i < thLeng; i++) {
            var liHtml = theadThs.eq(i).text();
            setWid = theadThs.eq(i).outerWidth();
            $('.replaceThead ul').append('<li style="width:' + setWid + 'px;">' + liHtml + '</li>');
        }
        setLastLiWid = $('.replaceThead ul li:last-child').width() - 1;
        $('.replaceThead ul li:last-child').width(setLastLiWid);
        replaceThead = $('.replaceThead');
    }
    // 2
    // movingHeadSpecial：有横向滚动条的表格使用，表头跟着滑动；
    function movingHeadSpecial() {
        var superdivWid0 = superdiv.width();
        var superdivWid1 = superdiv.width() + 21;
        var windowHeight = $(window).height();
        var fixdivPosTo, setFixHeit0, setFixHeit1, nextSiblingHeit, windowTop, fixdivScrollLeft;
        var nextSibling = $(fixdiv).next();
        var fixHeight = fixdiv.outerHeight() + 20;
        var listenNum0 = [0],
            listenNum1 = 0;
        var tableWidth = superdiv.find('table').outerWidth() + 11; //
            fixdivPosTop = $('.customer').outerHeight()+$('.title-container').outerHeight();
        // 控制superdiv的宽度；
        // 这里判断页面是否有分页，控制住表格的高度
        if (nextSibling.html() != undefined) {
            nextSiblingHeit = nextSibling.outerHeight();
            setFixHeit0 = windowHeight - nextSiblingHeit;
            if ($(fixdiv).hasClass('fixdivfix')) {
                $(fixdiv).css({ 'height': setFixHeit0 + 'px', 'top': '0px' });
            }
        } else {
            setFixHeit0 = windowHeight;
        }
       
        $(window).scroll(function() {
            windowTop = $(window).scrollTop();
            if (windowTop > fixdivPosTop && ($(fixdiv).outerHeight() > ($(window).height() - nextSiblingHeit))) {
                if (nextSibling.html() != undefined) {
                    nextSibling.addClass('fixPage');
                    nextSibling.hasClass('pages_container')? (nextSiblingHeit = nextSibling.outerHeight()+19):( nextSiblingHeit = nextSibling.outerHeight()+10); 
                   setFixHeit0 = $(window).height() - nextSiblingHeit;
                } else {
                    setFixHeit0 = windowHeight;
                }
                $(fixdiv).addClass('fixdivfix').css({ 'height': setFixHeit0 + 'px', 'top': '0px' });
                superdiv.addClass('pt35').width(tableWidth).prepend(whiteSpace).prepend(replaceTheadDiv);
                createReplaceTheadLi();
                $(fixdiv).scrollTop(30);
                $(fixdiv).scroll(function() {
                    var windowHeight = $(window).height();
                    fixdivScrollTop = $(this).scrollTop();
                    fixdivScrollLeft = $(this).scrollLeft();
                    if (listenNum1 < 2) {
                        listenNum0.push(fixdivScrollTop);
                        listenNum1++;
                    }
                    if (fixdivScrollTop > 0) {
                        replaceThead.show().addClass('replaceTheadMody').css('top', fixdivScrollTop + 'px');
                        $('.white-space').show();
                        superdiv.css({ 'padding-right': '10px', 'width': superdivWid1 + 'px' });
                    }
                    if (fixdivScrollTop < 20) {
                        $('.replaceThead ul').empty();
                        $('.superdiv').removeClass('pt35');
                        // nextSibling.removeClass('fixPage').addClass('list-bottom');
                        nextSibling.removeClass('fixPage');
                        replaceThead.hide();
                        $(fixdiv).removeClass('fixdivfix').css({ 'height': fixHeight + 'px' });
                        $(window).scrollTop(fixdivPosTop);
                        $('.white-space').hide();
                        superdiv.css({ 'padding-right': '0px', 'width': superdivWid0 + 'px' });
                    }
                })
            }
        })
        if ($(window.parent.document).find('.tab-pane').hasClass('active')) {
            $('.replaceThead').hide();
        }
    }
    // 3
    
    movingHeadSpecial() ;
    $(window).resize(function() {
        $('.replaceThead ul').empty();
        movingHeadSpecial() ;
    })
})

    </script>
<%@ include file="customer_tag.jsp"%>
 
<div class="content parts">
	<div class="title-container">
		<div class="table-title nobor">沟通记录</div>
		<div class="title-click nobor">
		<c:if test="${customerInfoByTraderCustomer.isEnable == 1  && ((customerInfoByTraderCustomer.verifyStatus != null && customerInfoByTraderCustomer.verifyStatus != 0 )|| customerInfoByTraderCustomer.verifyStatus == null)}">
			<a
				href="${pageContext.request.contextPath}/trader/customer/addcommunicate.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}">新增</a>
		</c:if>
		</div>
	</div>
	<div class="fixdiv">
		<div class="superdiv">
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="table-smallestx">时间</th>
						<th class="wid12">单号</th>
						<th>录音</th>
						<th>录音内容</th>
						<th>联系人</th>
						<th>联系方式</th>
						<th>沟通方式</th>
						<th>沟通目的</th>
						<th class="linebreak table-smaller">沟通内容</th>
						<th>操作人</th>
						<th class="table-smallestx">下次联系日期</th>
						<th class="linebreak table-smallest">下次沟通内容</th>
						<th class="linebreak table-smallest">备注</th>
						<th class="table-smallestx">创建时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty communicateRecordList}">
						<c:forEach items="${communicateRecordList }"
							var="communicateRecord">
							<tr>
								<td><date:date value="${communicateRecord.begintime} " />~<date:date
										value="${communicateRecord.endtime}" format="HH:mm:ss" /></td>
								<td>
									<c:choose>
										<c:when test="${communicateRecord.communicateType == 244 }">
											<a class="addtitle" href="javascript:void(0);"
												tabTitle='{"num":"viewbussiness${communicateRecord.relatedId}",
										"link":"./order/bussinesschance/toSalesDetailPage.do?bussinessChanceId=${communicateRecord.relatedId}&traderId=${communicateRecord.traderId }&traderCustomerId=${communicateRecord.traderCustomerId }",
										"title":"销售商机详情"}'>${communicateRecord.bussinessChanceNo }</a>
										</c:when>
										<c:when test="${communicateRecord.communicateType == 245 }">
											<a class="addtitle"
												tabtitle="{&quot;num&quot;:&quot;viewQuote${communicateRecord.relatedId}&quot;,&quot;link&quot;:&quot;./order/quote/getQuoteDetail.do?quoteorderId=${communicateRecord.relatedId}&viewType=3&quot;,&quot;title&quot;:&quot;报价详情&quot;}">${communicateRecord.quoteorderNo}</a>
										</c:when>
										<c:when test="${communicateRecord.communicateType == 246 }">
											<a class="addtitle" href="javascript:void(0);"
												tabTitle='{"num":"viewsaleorder${communicateRecord.relatedId}","link":"./order/saleorder/view.do?saleorderId=${communicateRecord.relatedId}","title":"订单信息"}'>${communicateRecord.saleorderNo}</a>
										</c:when>
										<c:when test="${communicateRecord.communicateType == 247 }">
											<a class="addtitle" href="javascript:void(0);" 
												tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./order/buyorder/viewBuyordersh.do?buyorderId=${communicateRecord.relatedId}","title":"订单信息"}'>${communicateRecord.buyorderNo}</a>
										</c:when>
										<c:when test="${communicateRecord.communicateType == 248 }">
											<a class="addtitle" href="javascript:void(0);"
												tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>","link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${communicateRecord.relatedId}&traderType=1","title":"售后"}'>${communicateRecord.aftersalesNo}</a>
										</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</td>
								<%--<td><c:if test="${not empty communicateRecord.coidUri }">${communicateRecord.communicateRecordId }</c:if></td>--%>
								<td><c:if test="${not empty communicateRecord.coidUri}">
										${communicateRecord.communicateRecordId }
									</c:if>
								</td>
								<td><c:if test="${not empty communicateRecord.coidUri}">
									<c:if test="${communicateRecord.isTranslation eq 1}">
									  <span class="edit-user pop-new-data"
											layerParams='{"width":"90%","height":"90%","title":"查看详情","link":"${pageContext.request.contextPath}/phoneticTranscription/phonetic/viewContent.do?communicateRecordId=${communicateRecord.communicateRecordId}"}'>查看</span>
									</c:if>
									<span class="edit-user"
										  onclick="playrecord('${communicateRecord.coidUri}');">播放</span>
								</c:if></td>
								<td>${communicateRecord.contactName}</td>
								<td>${communicateRecord.phone}</td>
								<td>
									<c:choose>
										<c:when test="${communicateRecord.coidType == 2}">
											呼出
										</c:when>
										<c:otherwise>
											呼入
										</c:otherwise>
									</c:choose>
								</td>
								<td>${communicateRecord.communicateGoalName}</td>
								<td class="linebreak ">
									<ul class="communicatecontent ml0">
										<c:if test="${not empty communicateRecord.tag }">
											<c:forEach items="${communicateRecord.tag }" var="tag">
												<li class="bluetag" title="${tag.tagName}">${tag.tagName}</li>
											</c:forEach>
										</c:if>
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
								<td>${communicateRecord.nextContactContent }</td>
								<td>${communicateRecord.comments}</td>
								<td><date:date value="${communicateRecord.addTime} " /></td>
								<td class="caozuo">
								<c:if test="${customerInfoByTraderCustomer.isEnable == 1  && ((customerInfoByTraderCustomer.verifyStatus != null && customerInfoByTraderCustomer.verifyStatus != 0 )|| customerInfoByTraderCustomer.verifyStatus == null)}">
								<span class="caozuo-blue"
									onclick="goUrl('${pageContext.request.contextPath}/trader/customer/editcommunicate.do?communicateRecordId=${communicateRecord.communicateRecordId}&traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}')">编辑</span>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty communicateRecordList }">
						<!-- 查询无结果弹出 -->
						<td class="tcenter" colspan="15">查询无结果！</td>
					</c:if>
				</tbody>

			</table>

		</div>
	</div>
	<tags:page page="${page}" />
</div>
<%@ include file="../../common/footer.jsp"%>

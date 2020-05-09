<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="page" value="${sessionScope.page}" />
<c:set var="path" value="${pageContext.request.contextPath}" />
<c:set var="url" value="${param.url}" />
<c:set var="urlParams" value="${param.urlParams}" />
<c:set var="pathurl" value="${path}/${url}" />
<script type="text/javascript">
$(function(){
	$('.pages_num li').hover(function(){
		$(this).siblings().removeClass('pageslihover');
		$(this).addClass('pageslihover');
		if($(this).hasClass('num_active')){
			$(this).addClass('num_active');
		}
	});
	$('.pages_num li').mouseout(function(){
		$(this).removeClass('pageslihover');
	});
})
 </script>
<div>
	<div class="pages">
		<div class="numbers">共<span>${page.totalCount}</span>条记录 共<span>${page.totalPage}</span>页 每页显示<span>${page.everyPage}</span>条</div>
		<ul class="pages_num">
			<c:choose>
				<c:when test="${page.currentPage==1}">
					<div class="nobor li_wid">首页</div>
				</c:when>
				<c:otherwise>
					<div class="nobor li_wid firstpage canclick" onclick="goAction('first');">首页</div>
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${page.totalPage <=8+5}">
					<c:forEach var="pageCount" begin="1" end="${page.totalPage}" step="1">
						<li ${page.currentPage eq pageCount ? "class='num_active'" : ""} <c:if test="${page.currentPage != pageCount }">onclick="goTo(${pageCount});" </c:if>>${pageCount}</li>
					</c:forEach>
				</c:when>
				<c:when test="${page.totalPage > 8+5}">
					<c:choose>
						<c:when test="${page.currentPage<8}">
							<c:forEach var="pageCount" begin="1" end="13" step="1">
								<li ${page.currentPage eq pageCount ? "class='num_active'" : ""} <c:if test="${page.currentPage != pageCount }">onclick="goTo(${pageCount});" </c:if>>${pageCount}</li>
							</c:forEach>
							<li>...</li>
						</c:when>
						<c:when test="${page.currentPage>=8}">
							<li>...</li>
								<c:choose>
									<c:when test="${page.currentPage+5 < page.totalPage}">
										<c:forEach var="pageCount" begin="${page.currentPage-5 }" end="${page.currentPage+5}" step="1">
											<li ${page.currentPage eq pageCount ? "class='num_active'" : ""} <c:if test="${page.currentPage != pageCount }">onclick="goTo(${pageCount});" </c:if>>${pageCount}</li>
										</c:forEach>
										<li>...</li>
									</c:when>
									<c:otherwise>
										<c:forEach var="pageCount" begin="${page.currentPage-5 }" end="${page.totalPage}" step="1">
											<li ${page.currentPage eq pageCount ? "class='num_active'" : ""} <c:if test="${page.currentPage != pageCount }">onclick="goTo(${pageCount});" </c:if>>${pageCount}</li>
										</c:forEach>
									</c:otherwise>
								</c:choose>
						</c:when>
					</c:choose>
				</c:when>
			</c:choose>
			
			<c:choose>
				<c:when test="${page.currentPage==page.totalPage}">
					<div class="li_wid lastpage canotclick canclick">尾页</div>
				</c:when>
				<c:otherwise>
					<div class="li_wid lastpage" onclick="goAction('last');">尾页</div>
				</c:otherwise>
			</c:choose>
			<div class="clear"></div>
		</ul>
		<ul class="pages_fenye">				
			<li ${page.everyPage eq 10 ? "class='first'" : ""} <c:if test="${page.everyPage != 10}"> onclick="setEveryPage(10);" </c:if>>10/页<i class="down"></i></li>
			<li ${page.everyPage eq 20 ? "class='first'" : ""} <c:if test="${page.everyPage != 20}"> onclick="setEveryPage(20);" </c:if>>20/页</li>
			<li ${page.everyPage eq 1 ? "class='first'" : ""} <c:if test="${page.everyPage != 1}"> onclick="setEveryPage(1);" </c:if>>1/页</li>
			<li ${page.everyPage eq 50 ? "class='first'" : ""} <c:if test="${page.everyPage != 50}"> onclick="setEveryPage(50);" </c:if>>50/页</li>
			<li ${page.everyPage eq 100 ? "class='first'" : ""} <c:if test="${page.everyPage != 100}"> class="nobor" onclick="setEveryPage(100);" </c:if>>100/页</li>			
		</ul>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div style='display: none'>
	<a class=listlink id="indexPageHref" href='#'></a>
</div>
<script>

/* 	var formId = "";
var pageId = "";
$(function() {
	formId = '${formId}';
	pageId = 'page' + '${formId}';
	var ht = "<span id='"+pageId+"' style='display:none'>" + "</span>";
	$("#" + formId).append(ht);
}); */
function goTo(page) {
	/* var $obj = $("#"+formId).find("#"+pageId);
	$obj.html("<input type='hidden' name='currentPage' id='currentPage' value='"+page+"'>"//跳转到第几页
			+ "<input type='hidden' name='everyPage' id='everyPage' value='"+${page.everyPage}+"'>"//每页显示条数
			+ "<input type='hidden' name='beginIndex' id='beginIndex' value='"+${page.everyPage}*page+"'>");//从第几条开始
	$("#" + formId).submit();
*/
	var a = document.getElementById("indexPageHref");
	a.href = '${pathurl}?pageAction=gopage&pageKey='+page+'${urlParams}';       
    a.setAttribute("onclick",'');          
    a.click("return false");  
}

function goAction(action){
	var a = document.getElementById("indexPageHref");
	a.href = '${pathurl}?pageAction='+action+'${urlParams}';       
    a.setAttribute("onclick",'');          
    a.click("return false");  
}
function setEveryPage(everyPage){	
	var a = document.getElementById("indexPageHref");
	var currentPage = 1;
	a.href = '${pathurl}?pageAction=setpage&pageKey='+everyPage+'${urlParams}';       
    a.setAttribute("onclick",'');          
    a.click("return false");   
}
</script>
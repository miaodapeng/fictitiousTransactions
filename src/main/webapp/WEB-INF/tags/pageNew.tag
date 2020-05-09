<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="page" type="com.vedeng.common.page.Page" required="true"%>
<%@ attribute name="optpage" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:if test="${page.totalPage > 0 && page.totalPage <= 9}">
 <div class="pager-wrap J-pager-wrap">
  	<div class="page-total">共${page.totalRecord }条结果</div>
    <div class="page-num">
		<c:if test="${page.noUpPage(page.pageNo) == true}">
    			<a href="javascript:void(0)" class="page-prev page-disabled" rel="nofollow"><i class="vd-icon icon-left"></i>上一页</a>
    		</c:if>
   		<c:if test="${page.noUpPage(page.pageNo) == false}">
   			<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${page.pageNo-1}" class="page-prev" rel="nofollow"><i class="vd-icon icon-left"></i>上一页</a>
   		</c:if>

    	<c:forEach var="pageCount" begin="1" end="${page.totalPage}" step="1">
			<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${pageCount}" <c:if test="${page.pageNo eq pageCount}">class="page-current"</c:if> rel="nofollow">${pageCount}</a>
		</c:forEach>
		
		  <c:if test="${page.noNextPage(page.pageNo) == true}">
   			<a href="javascript:void(0)" class="page-next page-disabled" rel="nofollow">下一页<i class="vd-icon icon-right"></i></a>
   		</c:if>
   		<c:if test="${page.noNextPage(page.pageNo) == false}">
   			<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${page.pageNo+1}" class="page-next" rel="nofollow">下一页<i class="vd-icon icon-right"></i></a>
   		</c:if>
    </div>
</div>
</c:if>
<c:if test="${page.totalPage > 0 && page.totalPage > 9}">
 <div class="pager-wrap J-pager-wrap">
	  <div class="page-total">共${page.totalRecord}条结果</div>
    <div class="page-num"> 
      	<c:if test="${page.noUpPage(page.pageNo) == true}">
    			<a href="javascript:void(0)" class="page-prev page-disabled" rel="nofollow"><i class="vd-icon icon-left"></i>上一页</a>
    		</c:if>
   		<c:if test="${page.noUpPage(page.pageNo) == false}">
   			<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${page.pageNo-1}" class="page-prev" rel="nofollow"><i class="vd-icon icon-left"></i>上一页</a>
   		</c:if>
   		
        <a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=1" <c:if test="${page.pageNo eq 1}">class="page-current"</c:if> rel="nofollow">1</a>
      	
      	<c:if test="${page.upEllipsis(page.pageNo)}"><strong class="omit">...</strong></c:if>
			
		<c:forEach var="pageCount" begin="${page.upThree(page.pageNo)}" end="${page.downThree(page.pageNo)}" step="1">
			<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${pageCount}" <c:if test="${page.pageNo eq pageCount}">class="page-current"</c:if> rel="nofollow">${pageCount}</a>
		</c:forEach>
		<c:if test="${page.downEllipsis(page.pageNo)}"><strong class="omit">...</strong></c:if>
		
        <a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${page.totalPage}" <c:if test="${page.pageNo eq page.totalPage}">class="page-current"</c:if> rel="nofollow">${page.totalPage}</a>
        
        <c:if test="${page.noNextPage(page.pageNo) == true}">
   			<a href="javascript:void(0)" class="page-next page-disabled" rel="nofollow">下一页<i class="vd-icon icon-right"></i></a>
   		</c:if>
   		<c:if test="${page.noNextPage(page.pageNo) == false}">
   			<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${page.pageNo+1}" class="page-next" rel="nofollow">下一页<i class="vd-icon icon-right"></i></a>
   		</c:if>
        <div class="page-jump">
            <span class="page-jump-txt">到第：</span>
            <input type="text" class="page-input J-pager-input">
            <a href="javascript:void(0)" class="page-jump-btn J-pager-jump" data-link="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=">确定</a>
        </div>
    </div>
</div>
</c:if>
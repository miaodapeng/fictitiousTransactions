<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="page" type="com.vedeng.common.page.Page" required="true"%>
<%@ attribute name="optpage" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="option-pager">
    <div class="option-pager-wrap">
        <div class="pager-txt">${page.pageNo}/${page.totalPage}</div>
        <div class="pager-btn pager-l <c:if test='${page.noUpPage(page.pageNo)}'>disabled</c:if>">
       		<c:if test="${page.noUpPage(page.pageNo) == true}">
       			<i class="vd-icon icon-left"></i>
       		</c:if>
    		<c:if test="${page.noUpPage(page.pageNo) == false}">
    			<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${page.pageNo-1}" class="page-prev" rel="nofollow"><i class="vd-icon icon-left"></i></a>
    		</c:if>
    	</div>
        <div class="pager-btn pager-r <c:if test='${page.noNextPage(page.pageNo)}'>disabled</c:if>">
        	<c:if test="${page.noNextPage(page.pageNo) == true}">
       			<i class="vd-icon icon-right"></i>
       		</c:if>
    		<c:if test="${page.noNextPage(page.pageNo) == false}">
    			<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${page.pageNo+1}" class="page-prev" rel="nofollow"><i class="vd-icon icon-right"></i></a>
    		</c:if>
        
        
        </div>
    </div>
</div>
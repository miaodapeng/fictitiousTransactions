<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="page" type="com.vedeng.common.page.Page" required="true"%>
<%@ attribute name="optpage" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script type="text/javascript" src='${pageContext.request.contextPath}/static/js/pages.js'></script>
<script type="text/javascript">
	function jumpPage(url){
		var j_no = (document.getElementById("jumpPageNo").value)*1;
		if(j_no*1>0){
			window.location.href=(url + j_no);
		}
	}
</script>
<c:if test="${page.totalRecord > 0}">
<div class="pages_container">
	<div class="pages">
		<div class="numbers">
			共<span>${page.totalRecord}</span>条记录
			<span class="ml8">共</span><span>${page.totalPage}</span>页
			<span class="ml8">跳转</span><input type="text" style="width:30px;" id="jumpPageNo" class="jump-to"
					onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" value="${page.pageNo}" 
					onkeydown="javascript:if(event.keyCode==13){jumpPage('${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageSize=${page.pageSize}&pageNo=');return false;}">页
			<span class="bt-middle bt-bg-style bg-light-blue ml8" onclick="jumpPage('${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageSize=${page.pageSize}&pageNo=');">跳转</span>
			<%-- 每页显示<span>${page.pageSize}</span>条 --%>
		</div>
		<ul class="pages_num">
			<c:choose>
				<c:when test="${page.pageNo==1}">
					<div class="li_wid  firstpage">首页</div>
				</c:when>
				<c:otherwise>
					<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=1&pageSize=${page.pageSize}">
						<div class="li_wid firstpage canclick">首页</div>
					</a>
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="${page.totalPage <=8+5}">
					<c:forEach var="pageCount" begin="1" end="${page.totalPage}" step="1">
						<c:choose>
							<c:when test="${page.pageNo != pageCount }">
								<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${pageCount}&pageSize=${page.pageSize}">
									<li>${pageCount}</li>
								</a>
							</c:when>
							<c:otherwise>
								<li class='num_active'>${pageCount}</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:when>
				<c:when test="${page.totalPage > 8+5}">
					<c:choose>
						<c:when test="${page.pageNo<8}">
							<c:forEach var="pageCount" begin="1" end="13" step="1">
								<c:choose>
									<c:when test="${page.pageNo != pageCount}">
										<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${pageCount}&pageSize=${page.pageSize}">
											<li>${pageCount}</li>
										</a>
									</c:when>
									<c:otherwise>
										<li class='num_active'>${pageCount}</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<!-- <li>...</li> -->
						</c:when>
						<c:when test="${page.pageNo>=8}">
							<!-- <li>...</li> -->
							<c:choose>
								<c:when test="${page.pageNo+5 < page.totalPage}">
									<c:forEach var="pageCount" begin="${page.pageNo-5 }" end="${page.pageNo+5}" step="1">
										<c:choose>
											<c:when test="${page.pageNo != pageCount}">
												<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${pageCount}&pageSize=${page.pageSize}">
													<li>${pageCount}</li>
												</a>
											</c:when>
											<c:otherwise>
												<li class='num_active'>${pageCount}</li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<!-- <li>...</li> -->
								</c:when>
								<c:otherwise>
									<c:forEach var="pageCount" begin="${page.pageNo-5 }" end="${page.totalPage}" step="1">
										<c:choose>
											<c:when test="${page.pageNo != pageCount}">
												<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${pageCount}&pageSize=${page.pageSize}">
													<li>${pageCount}</li>
												</a>
											</c:when>
											<c:otherwise>
												<li class='num_active'>${pageCount}</li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</c:when>
					</c:choose>
				</c:when>
			</c:choose>

			<c:choose>
				<c:when test="${page.pageNo==page.totalPage || page.totalPage == 1 || page.totalPage == 0}">
					<div class="li_wid lastpage canclick">尾页</div>
				</c:when>
				<c:otherwise>
					<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${page.totalPage}&pageSize=${page.pageSize}">
						<div class="li_wid lastpage canclick">尾页</div>
					</a>
				</c:otherwise>
			</c:choose>
			<div class="clear"></div>
		</ul>
		<c:if test="${optpage ne 'n'}"><!-- optpage等于n时不显示 -->
			<ul class="pages_fenye">
				<c:choose>
					<c:when test="${page.pageSize != 10}">
						<a href="${page.searchUrl}${fn:contains(page.searchUrl,'.do?')==true?'&':'?'}pageNo=${page.totalPage}&pageSize=10">
							<li>10/页<i class="down"></i></li>
						</a>
					</c:when>
					<c:otherwise>
						<li ${page.pageSize eq 10 ? "class='first'" : ""}>10/页</li>
					</c:otherwise>
				</c:choose>
			</ul>
		</c:if>
		<input type="hidden" id="choosePageSize" value="${page.pageSize}">
		<input type="hidden" id="winPageNo" value="${page.pageNo}">
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
</c:if>
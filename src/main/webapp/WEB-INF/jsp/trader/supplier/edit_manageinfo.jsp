<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑管理信息" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<%@ include file="supplier_tag.jsp"%>
<form method="post"
	action="${pageContext.request.contextPath}/trader/supplier/saveeditmanageinfo.do"
	id="supplierForm">
	<div class="baseinforcontainer" style="padding-bottom: 15px;">
		<div class="border overflow-hidden">
			<div class="baseinfor f_left">管理信息</div>
		</div>
		<div class="baseinforeditform">
			<ul>
				<li>
					<div class="infor_name mt4">
						<lable>供应商标签</lable>
					</div>
					<div class="f_left table-largest">
						<div class="inputfloat manageaddtag">
							<label class="mt4 mr8 ">您可以从这些标签中选择</label>
							<c:if test="${not empty tagList }">
								<c:forEach items="${tagList }" var="tag">
									<span onclick="addTag(${tag.tagId},'${tag.tagName }',this);">${tag.tagName }</span>
								</c:forEach>
							</c:if>
							<c:if test="${page.totalPage > 1}">
								<div class="change"
									onclick="changeTag(${page.totalPage},10,this,31);">
									<span class="m0">换一批(</span><span class="m0" id="leftNum">${page.totalPage}</span><span
										class="m0">)</span> <input type="hidden" id="pageNo"
										value="${page.pageNo}">
								</div>
							</c:if>
						</div>
						<div class="inputfloat <c:if test="${empty tagList }">mt8</c:if>">
							<input type="text" id="defineTag"
								placeholder="如果标签中没有您所需要的，请自行填写" class="input-large">
							<div class="f_left bt-bg-style bg-light-blue  addbrand"
								onclick="addDefineTag(this);" style="height:25px;line-height:22px">添加</div>
						</div>
						<div
							class="addtags mt6 <c:if test="${empty traderSupplier.tag }">none</c:if>">
							<ul id="tag_show_ul">
								<c:if test="${not empty traderSupplier.tag}">
									<c:forEach items="${traderSupplier.tag }" var="tag">
										<li class="bluetag">${tag.tagName}<input type="hidden"
											name="tagId" alt="${tag.tagName}" value="${tag.tagId }"><i
											class="iconbluecha" onclick="delTag(this);"></i></li>
									</c:forEach>
								</c:if>
							</ul>
						</div>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>备注</lable>
					</div>
					<div class="f_left ">
						<input type="text" class="input-xxx" name="comments" id="comments"
							value="${traderSupplier.comments }" />
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>简介</lable>
					</div>
					<div class="f_left ">
						<textarea class="input-xxx" name="brief" id="brief">${traderSupplier.brief }</textarea>
					</div>
				</li>
			</ul>
		</div>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="traderId"
				value="${traderSupplier.traderId }"> <input type="hidden"
				name="traderSupplierId" value="${traderSupplier.traderSupplierId }">
			<input type="hidden" name="beforeParams" value='${beforeParams}'/>				
			<button type="submit">提交</button>
			<button class="dele" id="close-layer" type="button"
				onclick="goUrl('${pageContext.request.contextPath}/trader/supplier/manageinfo.do?traderSupplierId=${traderSupplier.traderSupplierId}');">取消</button>
		</div>
	</div>
</form>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/supplier/edit_manageinfo.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/tag.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>

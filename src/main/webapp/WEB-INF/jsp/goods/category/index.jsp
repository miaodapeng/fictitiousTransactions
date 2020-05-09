<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="产品分类管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/firstengage/category/index.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
	$(document).ready(function()
	{
		// 等待0.5s
		setTimeout(function()
		{	
			// 产品分类 1级分类
			var one_value = $("#spanId0 #categoryOpt").find("option:selected").val();
			var one_text = $("#spanId0 #categoryOpt").find("option:selected").text();
			// 产品分类 2级分类
			var two_value = $("#spanId1 #categoryOpt").find("option:selected").val();
			var two_text = $("#spanId1 #categoryOpt").find("option:selected").text();
			$.each($("#category_tb tr"),function()
			{
				// 获取categoryId
				var cid = $(this).find("td:eq(1)").text().trim();
				var new_text = '';
				var old_text = $("#cid_"+cid).text();
				// 1级判断是否存在
				if(-1 != one_value && undefined != one_value)
				{
					new_text = one_text + '-->';
				}
				// 2级判断是否存在
				if(-1 != two_value && undefined != two_value)
				{
					new_text += two_text + '-->'; 
				}
				new_text += old_text;
				$("#cid_"+cid).text(new_text);
			});
		}, 1000*0.5);
    })
</script>
    <div class="main-container">
    	<div class="list-pages-search">
			<form method="post" id="search" action="<%=basePath%>goods/category/index.do">
				<ul>
	                <%-- <li>
	            		<label class="infor_name">分类编号</label>
	            		<input type="text" class="input-middle" name="categoryId" id="categoryId" value="${category.categoryId}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxLength="10">
            		</li> --%>
            		<li>
						<label class="infor_name">分类名称</label>
						<input type="text" class="input-middle" name="categoryName" id="categoryName" value="${category.categoryName}" >
            		</li>
            		<li>
						<label class="infor_name">产品分类</label>
						<!-- 谷歌不支持onmouseup事件 -->
						<c:if test="${empty category.parentId or category.parentId eq 0}">
							<select class="input-small" id='categoryOpt' name='categoryOpt'>
								<option value="-1" id="1" onclick="triCategory(this)">请选择</option>
								<c:forEach var="list" items="${cateList}" varStatus="status">
									<option value="${list.categoryId}" id="${list.level}" onclick="triCategory(this)">${list.categoryName}</option>
								</c:forEach>
							</select>
						</c:if>
						<span id="spanId">
							<span id="spanHtmlId"></span>
						</span>
						<input type="hidden" name="parentId" id="parentId" value="${category.parentId}"/>
            		</li>
            		<li>
            			<label class="infor_name">归属</label>
            			<select class="input-small" name='userId'>
            				<option value="0">请选择</option>
            				<c:forEach var="user" items="${productUserList}" varStatus="status">
								<option value="${user.userId}" <c:if test="${category.userId == user.userId }">selected="selected"</c:if>>${user.username}</option>
							</c:forEach>
            			</select>
            		</li>
           		</ul>
           		<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="resetCate();">重置</span>
					<span class="pop-new-data bt-small bt-bg-style bg-light-blue" layerParams='{"width":"500px","height":"180px","title":"新增分类","link":"./addCategory.do"}'>新增分类</span>
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="exportCategory();">导出</span> 
				</div>
			</form>
		</div>
        <div class='normal-list-page'>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr class="sort">
                    	<th class="wid4">选择</th>
                        <th abbr="CATEGORY_ID" class="wid15">分类ID</th>
                        <th abbr="CATEGORY_NAME">分类名称</th>
                        <th class="wid15">产品数量</th>
                        <th class="wid25">创建时间</th>
                        <th class="wid15">归属</th>
                        <th style="width: 15%;">操作</th>
                    </tr>
                </thead>
                <tbody id="category_tb" class="category">
        		<c:if test="${not empty categoryList}">
                	<c:forEach items="${categoryList }" var="list" varStatus="status">
	                    <tr>
	                    	<td><input class="cid_input" type="checkbox" name="checkOne" value="${list.categoryId}" autocomplete="off"></td>
	                        <td>${list.categoryId}</td>
	                        <td class="textindent8">
	                        <c:if test="${list.level<3}">
	                        	<a href="<%=basePath%>goods/category/index.do?parentId=${list.categoryId}" onclick="waitWindowNew('no');">
	                        		<span id="cid_${list.categoryId}">${list.categoryName}</span>
	                        	</a>
	                        </c:if>
	                        <c:if test="${list.level>=3}">
	                        	<span id="cid_${list.categoryId}" >${list.categoryName}</span>
	                        </c:if>
	                        </td>
							<td>${list.goodsNum}</td>
							<td><date:date value ="${list.addTime}"/></td>
							<td>
								<c:if test="${not empty list.userList }">
									<c:forEach items="${list.userList }" var="owner" varStatus="st">
										${owner.username } 
										<c:if test="${st.count != list.userList.size() }">、</c:if>
									</c:forEach>
								</c:if>
							</td>
	                        <td>
	                        	<span class="edit-user pop-new-data" layerParams='{"width":"500px","height":"180px","title":"编辑分类","link":"./getCategoryById.do?categoryId=${list.categoryId}"}'>
									编辑
								</span>
								<span class="delete" onclick="delCategory(${list.categoryId});">删除</span>
	                        </td>
	                    </tr>
                	</c:forEach>
        		</c:if>
                </tbody>
            </table>
        	<c:if test="${empty categoryList}">
       			<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
        	</c:if>
        	<div class="inputfloat f_left" style='margin:0px 0 15px 0;'>
			<input type="checkbox" class="mt6 mr4" name="checkAll" autocomplete="off"> <label
				class="mr10 mt4">全选</label> <span
				class="bt-bg-style bg-light-blue bt-small mr10" onclick="editAll(this);" layerParams='{"width":"500px","height":"210px","title":"设置归属","link":"./editcategoryowner.do"}'>设置归属</span>
				<c:if test="${pcategory!=null and pcategory.parentId !=null}">
					<a href="<%=basePath%>firstengage/category/index.do?parentId=${pcategory.parentId}"><span class="bt-bg-style bg-light-blue bt-small mr10" onclick="waitWindowNew('no');">返回</span></a>
				</c:if>
		</div>
        </div>
        
    </div>
<%@ include file="../../common/footer.jsp"%>


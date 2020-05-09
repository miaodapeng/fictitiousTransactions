<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="品牌管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/goods/brand/index.js?rnd=<%=Math.random()%>'></script>
    <div class="content">
    	<div class="searchfunc">
			<c:if test="${brand.source==0 }"> 				
				<form method="post" id="search" action="<%=basePath%>goods/brand/index.do">
			</c:if>
			<c:if test="${brand.source==1 }">
				<form method="post" id="search" action="<%=basePath%>goods/brand/brandList.do">
			</c:if>
				<ul>
            		<li>
						<label class="infor_name">品牌名称</label>
						<input type="text" class="input-middle" name="brandName" id="brandName" value="${brand.brandName}" >
						<input type="hidden" name="search" value="sgh" >
            		</li>
            		<li>
						<label class="infor_name">商品品牌</label>
						<select name="brandNature">
							<option value="1" <c:if test="${brand.brandNature eq 1}">selected="selected"</c:if>>国产品牌</option>
							<option value="2" <c:if test="${brand.brandNature eq 2}">selected="selected"</c:if>>进口品牌</option>
							<option value="0" <c:if test="${brand.brandNature eq 0}">selected="selected"</c:if>>全部</option>
						</select>
            		</li>
           		</ul>
           		<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
					<c:if test="${brand.source==0 }">
						<span class="bg-light-blue bt-bg-style bt-small addtitle"
									tabTitle='{"num":"customer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./goods/brand/addBrand.do","title":"新增品牌"}'>新增品牌</span>
						<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="exportBrand();">导出品牌</span>
					</c:if>
					<!-- <span class="pop-new-data bt-small bt-bg-style bg-light-blue" layerParams='{"width":"500px","height":"260px","title":"服务端验证","link":"./testVailInit.do"}'>服务端验证</span> -->
					
				</div>
			</form>
		</div>
		<div class='normal-list-page list-page'>
			<table
				class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr class="sort">
						<th class="wid5">品牌ID</th>
						<th class="wid6">商品品牌</th>
						<th class="wid10">品牌名称</th>
						<th class="wid10">品牌LOGO</th>
						<th class="wid17">品牌网址</th>
						<th class="wid6">商品数量</th>
						<th class="wid30">品牌故事</th>
						<!-- <th>创建人</th> -->
						<th class="wid10">创建时间</th>
						<th class="wid15">操作</th>
					</tr>
				</thead>
				<tbody class="brand">
					<c:if test="${not empty brandList}">
						<c:forEach items="${brandList }" var="list" varStatus="status">
							<tr>
								<td>${list.brandId}</td>
								<td>
									<c:if test="${list.brandNature eq 1}">国产品牌</c:if>
                    				<c:if test="${list.brandNature eq 2}">进口品牌</c:if>
                    			</td>	
								<td>${list.brandName}</td>
								<td>
									<a href="http://${list.logoDomain}${list.logoUri}" target="_blank">${list.logoUriName}</a>
								</td>
								<td>${list.brandWebsite}</td>
								<td>${list.goodsNum}</td>
								<td>
									<c:if test="${fn:length(list.description)>30 }">  
				                         ${fn:substring(list.description, 0, 30)}...  
				                   	</c:if>  
				                  	<c:if test="${fn:length(list.description)<=30 }">  
				                         ${list.description }  
				                   	</c:if>
								</td>
								<%-- <td>${list.creator}</td> --%>
								<td><date:date value="${list.addTime}" /></td>
								<td>
								<span class="edit-user addtitle"
									tabTitle='{"num":"customer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./goods/brand/viewBrandDetail.do?brandId=${list.brandId}","title":"查看"}'>查看</span>
								<span class="edit-user addtitle"
									tabTitle='{"num":"customer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
												"link":"./goods/brand/getBrandByKey.do?brandId=${list.brandId}","title":"编辑品牌"}'>编辑</span>
								<c:if test="${list.source==0 }">
									<span class="delete"
										onclick="delBrand(${list.brandId});">删除</span></td>
								</c:if>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<c:if test="${empty brandList}">
				<!-- 查询无结果弹出 -->
				<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
			</c:if>
			<tags:page page="${page}" />
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>

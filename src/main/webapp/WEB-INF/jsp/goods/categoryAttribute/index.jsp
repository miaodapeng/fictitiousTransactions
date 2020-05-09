<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="分类属性" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/goods/categoryAttribute/index.js?rnd=<%=Math.random()%>'></script>
    <div class="main-container">
    	<div class="list-pages-search">	
			<form method="post" id="search" action="<%=basePath%>goods/categoryattribute/index.do">
				<ul>
            		<li>
						<label class="infor_name">属性名称</label>
						<input type="text" class="input-middle" name="categoryAttributeName" id="categoryAttributeName" value="${categoryAttribute.categoryAttributeName}" >
            		</li>
            		<li>
						<label class="infor_name">数据类型</label>
						<select class="input-middle" id='inputType' name='inputType'>
							<option value="">全部</option>
							<option value="0" <c:if test="${categoryAttribute.inputType eq 0}">selected</c:if>>固定值单选</option>
							<option value="1" <c:if test="${categoryAttribute.inputType eq 1}">selected</c:if>>固定复选值</option>
							<%-- <option value="2" <c:if test="${categoryAttribute.inputType eq 2}">selected</c:if>>文本输入</option> --%>
						</select>
            		</li>
            		<li>
						<label class="infor_name">是否必填</label>
						<select class="input-middle" id='isSelected' name='isSelected'>
							<option value="">全部</option>
							<option value="0" <c:if test="${categoryAttribute.isSelected eq 0}">selected</c:if>>否</option>
							<option value="1" <c:if test="${categoryAttribute.isSelected eq 1}">selected</c:if>>是</option>
						</select>
            		</li>
            		<li>
						<label class="infor_name">检索条件</label>
						<select class="input-middle" id='isFilter' name='isFilter'>
							<option value="">全部</option>
							<option value="0" <c:if test="${categoryAttribute.isFilter eq 0}">selected</c:if>>否</option>
							<option value="1" <c:if test="${categoryAttribute.isFilter eq 1}">selected</c:if>>是</option>
						</select>
            		</li>
            		<li>
						<label class="infor_name">产品分类</label>
						<!-- 谷歌不支持onmouseup事件 -->
						<c:if test="${empty categoryAttribute.categoryId}">
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
						<input type="hidden" name="categoryId" id="categoryId" value="${categoryAttribute.categoryId}"/>
            		</li>
           		</ul>
           		<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
					<span class="pop-new-data bt-small bt-bg-style bg-light-blue" layerParams='{"width":"500px","height":"280px","title":"新增属性","link":"./addCateAttribute.do"}'>新增属性</span>
				</div>
			</form>
		</div>
        <div class="normal-list-page list-page">
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="wid5">属性ID</th>
                        <th>属性名称</th>
                        <th>产品分类</th>
                        <th class="wid10">数据类型</th>
                        <th class="wid6">是否必填</th>
                        <th class="wid6">检索条件</th>
                        <!-- <th>创建人</th> -->
                        <!-- <th>创建时间</th> -->
                        <th class="wid20">操作</th>
                    </tr>
                </thead>
                <tbody class="categoryAttribute">
        		<c:if test="${not empty categoryAttributeList}">
                	<c:forEach items="${categoryAttributeList}" var="list" varStatus="status">
	                    <tr>
	                        <td>${list.categoryAttributeId}</td>
	                        <td>${list.categoryAttributeName}</td>
							<td>${list.category==null?"":list.category.categoryNameArr}
							</td>
							<td>
								<c:choose>
									<c:when test="${list.inputType eq 0}">
										固定值单选
									</c:when>
									<c:when test="${list.inputType eq 1}">
										固定值复选
									</c:when>
									<c:otherwise>
										文本输入
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${list.isSelected eq 0}">
										否
									</c:when>
									<c:otherwise>
										是
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<c:choose>
									<c:when test="${list.isFilter eq 0}">
										否
									</c:when>
									<c:otherwise>
										是
									</c:otherwise>
								</c:choose>
							</td>
							<%-- <td>${list.creator}</td> --%>
							<%-- <td><date:date value ="${list.addTime}"/></td> --%>
	                        <td>
	                        	<span class="edit-user pop-new-data" layerParams='{"width":"500px","height":"280px","title":"编辑属性","link":"./getCateAttributeByKey.do?categoryAttributeId=${list.categoryAttributeId}"}'>
									编辑
								</span>
								<span class="edit-user pop-new-data" layerParams='{"width":"550px","height":"350px","title":"编辑属性值","link":"./editCateAttributeValue.do?categoryAttributeId=${list.categoryAttributeId}&categoryAttributeName=${list.categoryAttributeName}"}'>
									编辑属性值
								</span>
								<span class="delete" onclick="delCateAttribute(${list.categoryAttributeId});">删除</span>
	                        </td>
	                    </tr>
                	</c:forEach>
        		</c:if>
                </tbody>
            </table>
        	<c:if test="${empty categoryAttributeList}">
       			<!-- 查询无结果弹出 -->
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
        	</c:if>
        	<tags:page page="${page}"/>
        </div>
    </div>
<%@ include file="../../common/footer.jsp"%>

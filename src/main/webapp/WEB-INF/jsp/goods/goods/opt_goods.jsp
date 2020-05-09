<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增配件" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/goods/goods/opt_goods.js?rnd=<%=Math.random()%>'></script>
    <div class="">
    	<div class="form-list form-tips4">		
			<form method="post" id="search">
				<c:if test="${goodsOpt.optType eq 'pj'}">
					<ul>
	            		<li>
							<div class='form-tips'>
							<label>配件种类</label>
						</div>
						<div class=' f_left'>
							<div class='form-blanks'>
							<input type="radio" id='standard_y' name="isStandard" value="1" <c:if test="${goodsOpt.isStandard eq 1}">checked</c:if> />
							<label class='mr10'>标配</label>
							<input type="radio" id='standard_n' name="isStandard" value="0" <c:if test="${goodsOpt.isStandard eq 0}">checked</c:if> />
							<label>非标配</label>
							</div>
						</div>
	            		</li>
	           		</ul>
				</c:if>
				<ul>
            		<li>
						<div class='form-tips'>
							<label>关键词</label>
						</div>
						<div class=' f_left'>
						<div class='form-blanks'>
							<input type="text" class="input-middle" name="searchData" id="searchData" value="${goodsOpt.searchData}" placeholder="请输入产品名称/品牌/型号等关键词">
							<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
						</div>
						</div>
					
            		</li>
           		</ul>
           		<input type="hidden" id="optType" value="${goodsOpt.optType}"/>
				<input type="hidden" id="goodsId" name="goodsId" value="${goodsOpt.goodsId}"/>
				<table class="table">
                <thead>
                    <tr>
                    	<th class='wid5'><!-- input type="checkbox" name="checkAll"-->选择</th>
                        <th class='wid5'>序号</th>
                        <th>订货号</th>
                        <th>产品名称</th>
                        <th>品牌</th>
                        <th>型号</th>
                        <th>物料编码</th>
                        <th>单位</th>
                    </tr>
                </thead>
                <tbody class="goodsOpt">
        		<c:if test="${not empty goodsOptList}">
                	<c:forEach items="${goodsOptList}" var="list" varStatus="status">
	                    <tr>
	                    	<th width="5%" style="text-align:center"><input type="checkbox" name="checkOne" value="${list.goodsId}"></th>
	                        <td>${status.count}</td>
	                        <td>${list.sku}</td>
	                        <td>${list.goodsName}</td>
							<td>${list.brandName}</td>
							<td>${list.model}</td>
							<td>${list.materialCode}</td>
							<td>${list.unitName}</td>
	                    </tr>
                	</c:forEach>
        		</c:if>
        		<c:if test="${empty goodsOptList}">
       				<!-- 查询无结果弹出 -->
       				<tr>
       					<td colspan="8">查询无结果！请尝试使用其他搜索条件。</td>
       				</tr>
        		</c:if>
                </tbody>
            </table>
        	
        	<div>
        	<c:if test="${not empty goodsOptList}">
      			<div class="table-style4 f_left">
	                <div class="allchose ">
	                    <input type="checkbox" name="checkAll">
	                    <span>全选</span>
	                </div>
            	</div>
       		</c:if>
        	
        	<tags:page page="${page}" optpage="n"/>
        	</div>
			</form>
		</div>
     
        <div class="add-tijiao tcenter clear">
			<button type="button" class="bt-bg-style bg-deep-green" onclick="saveOpt()">提交</button>
			<button type="button" class="dele" id="cancle">取消</button>
		</div>
    </div><br>
<%@ include file="../../common/footer.jsp"%>
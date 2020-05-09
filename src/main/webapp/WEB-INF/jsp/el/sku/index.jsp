<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="SKU管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
    <div class="content">
        <div class="searchfunc">
        	<form action="${pageContext.request.contextPath}/el/sku/index.do" method="post" id="search">
        		<ul>
	                <li>
	            		<label class="infor_name">SKU名称</label>
	            		<input type="text" class="input-middle" name="skuName" id="skuName" value="${sku.skuName }"/>
            		</li>
           		</ul>
           		<div class="tcenter">
	            	<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
	            	<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
					<span class="pop-new-data bt-small bt-bg-style bg-light-blue" layerParams='{"width":"550px","height":"250px","title":"批量新增","link":"./batchAddELSkuInit.do"}'>批量新增</span>
            	</div>
            </form>
        </div>
        <div  class="normal-list-page">
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                    <tr>
                        <th class="sorts">序号</th>
						<%--<th class="wid15">SKUID</th>--%>
						<th class="wid10">订货号</th>
                        <th class="wid15">SKU名称</th>

                        <th class="wid10">添加时间</th>
						<th style="width:5%;">操作</th>
                    </tr>
                </thead>
                <tbody class="company">
					<c:if test="${not empty skuList}">
						<c:forEach items="${skuList}" var="sku" varStatus="status">
							<tr>
								<td>${status.count}</td>
								<%--<td>--%>
								<%--${sku.skuId}--%>
								<%--</td>--%>
								<td>
									${sku.skuNo}
								</td>
								<td>
									${sku.skuName}
								</td>
								<td>
									<date:date value="${sku.addTime} " />
								</td>
								<td>
									<span class="delete" onclick="delElSku(${sku.elSkuId});">删除</span>
								</td>
							</tr>
						</c:forEach>
					</c:if>
                </tbody>
            </table>
        	<c:if test="${empty skuList}">
           		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
        	</c:if>
	        <tags:page page="${page}"/>
        </div>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/el/sku/index.js?rnd=<%=Math.random()%>"></script>
	<%@ include file="../../common/footer.jsp"%>
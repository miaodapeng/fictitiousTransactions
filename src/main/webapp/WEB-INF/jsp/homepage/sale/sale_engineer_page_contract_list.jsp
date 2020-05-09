<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="合同上传列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/home/page/sale_engineer_page_contract_list.js?rnd=<%=Math.random()%>'></script>
</head>
<body>
	<div class="formpublic formpublic1">
		 <input type="hidden" name="formToken" value="${formToken}"/>
		<div>
			<!-- ------------产品数据列表--------start------- -->
			<div class="controlled" id="goodsListDiv">
				<!-- 搜索表格出来 -->
				<ul class="searchTable">
					<li>
						<div>
							<table class="table table-bordered table-striped table-condensed table-centered mb10">
								<thead>
									    <th class="wid20">合同回传</th>
				                        <th class="wid10">操作人</th>
				                        <th class="wid15">时间</th>
				                        <th class="wid10">审核状态</th>
				                        <th class="wid10"> 操作</th>
								</thead>
								<tbody>
									  <c:if test="${null!=saleorderAttachmentList}">
					                    <c:forEach var="list" items="${saleorderAttachmentList}" varStatus="status">
					                  
					                    <tr>
					                    	<td class="font-blue"><a href="http://${list.domain}${list.uri}" target="_blank">${list.name}</a></td>
					                        <td>${list.username}</td>
					                        <td><date:date value ="${list.addTime}"/></td>
					                        <td>
												<c:if test="${saleorder.contractStatus == null}">
				                        			待审核
				                        		</c:if>
				                        		<c:if test="${saleorder.contractStatus == 0}">
				                        			审核中
				                        		</c:if>
				                        		<c:if test="${saleorder.contractStatus == 1}">
				                        			审核通过
				                        		</c:if>
				                        		<c:if test="${saleorder.contractStatus == 2}">
				                        			<span class="font-red">审核不通过</span>
				                        		</c:if>	
											</td>
					                         <td>
												 <div class="caozuo">
					                            	<c:if test="${saleorder.status != 3 && (saleorder.contractStatus == null || saleorder.contractStatus == 2)}">
					                                	<span class="caozuo-red" onclick="contractReturnDel(${list.attachmentId})">删除</span>
					                            	</c:if>
					                            </div>
											</td>	
					                    </tr>
					                    </c:forEach>
					                    </c:if>
									<c:if test="${null==saleorderAttachmentList or empty saleorderAttachmentList}">
									<!-- 查询无结果弹出 -->
									<tr>
										<td colspan='5'>
											数据为空
										</td>
									</tr>
									</c:if>
								</tbody>
							</table>
						</div>
					</li>
					
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
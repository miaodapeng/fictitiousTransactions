<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="分配客户" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="content pt10">
	<div class="">
		<form action="" method="post" id="form">
			<input type="hidden" name="formToken" value="${formToken}"/>
			<ul style="margin-bottom:6px;">
				<li class="mb7">
					<div class="infor_name mt0">请选择分配方式</div>
					<div class="f_left inputfloat">
						<ul>
							<li><input type="radio" name="type" value="1" <c:if test="${type eq 1 or empty type}">checked="checked"</c:if> > <label class="mr4">单个</label>
							</li>
							<li><input type="radio" name="type" value="2" <c:if test="${type eq 2 }">checked="checked"</c:if> > <label>批量</label></li>
							<li><input type="radio" name="type" value="3" <c:if test="${type eq 3 }">checked="checked"</c:if> > <label>导入</label></li>
						</ul>

						<span class=" pop-new-data"  id="excelBatchImport" layerParams='{"width":"500px","height":"200px","title":"批量划拨","link":"./changeTraderOwnerPage.do"}'></span>
					</div>
				</li>
				<li class="single_assign <c:if test="${type eq 2 }">none</c:if>">
					<div class="infor_name">请输入客户名称</div>
					<div class="f_left inputfloat">
						<ul>
							<li><input type="text" class="input-middle" name="traderName" value="${traderName }"> <span
								class=" bt-bg-style bg-light-blue bt-small" onclick="search(this);">搜索</span></li>
						</ul>
					</div>
				</li>
				<li class="batch_assign  mb7 <c:if test="${type eq 1 or empty type}">none</c:if> ">
                    <div class="infor_name">
                        请选择归属销售
                    </div>
                    <div class="f_left inputfloat">
                        <ul>
                            <li>
                            	<select name="from_user">
									<option value="0">请选择销售人员</option>
									<c:if test="${not empty userList}">
										<c:forEach items="${userList }" var="user">
										<option value="${user.userId }" <c:if test="${from_user eq user.userId }">selected="selected"</c:if>>${user.username}</option>
										</c:forEach>
									</c:if>
								</select>
                            </li>
                        </ul>
                    </div>
                </li>
                <li class="batch_assign <c:if test="${type eq 1 or empty type}">none</c:if> ">
                	<div class="infor_name">
                	请选择地区
                    </div>
                    <div class="f_left inputfloat">
                        <ul>
                            <li>
                            	<select class="input-middle mr6" name="province">
			                    	<option value="0">请选择</option>
			                    	<c:if test="${not empty provinceList }">
			                    		<c:forEach items="${provinceList }" var="province">
			                    			<option value="${province.regionId }" <c:if test="${ not empty provinceId &&  province.regionId == provinceId }">selected="selected"</c:if>>${province.regionName }</option>
			                    		</c:forEach>
			                    	</c:if>
			                    </select>
			                    <select class="input-middle mr6" name="city">
			                        <option value="0">请选择</option>
			                        <c:if test="${not empty cityList }">
			                        	<c:forEach items="${cityList }" var="city">
			                    			<option value="${city.regionId }" <c:if test="${ not empty cityId &&  city.regionId == cityId }">selected="selected"</c:if>>${city.regionName }</option>
			                    		</c:forEach>
			                        </c:if>
			                    </select>
			                    <select class="input-middle mr6" name="zone" id="zone">
			                    	<option value="0">请选择</option>
			                    	<c:if test="${not empty zoneList }">
			                        	<c:forEach items="${zoneList }" var="zone">
			                    			<option value="${zone.regionId }" <c:if test="${ not empty zoneId &&  zone.regionId == zoneId }">selected="selected"</c:if>>${zone.regionName }</option>
			                    		</c:forEach>
			                        </c:if>
			                    </select>
			                    <span class=" bt-bg-style bg-light-blue bt-small" onclick="search(this);">搜索</span>
                            </li>
                        </ul>
                    </div>
                </li>
			</ul>
		</form>
		<c:if test="${type eq 1}">
		<div class="single_assign normal-list-page">
			<table
				class="table ">
				<thead>
					<tr>
						<th class="wid4">选择</th>
						<th>客户名称</th>
						<th>地区</th>
						<th>归属销售</th>
						<th>合作次数</th>
						<th>客户等级</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty traderList }">
					<c:forEach items="${traderList }" var="trader">
					<tr>
						<td><input type="radio" name="tarderId" value="${trader.traderId }"></td>
						<td class="text-ellipsis" title="${trader.traderName }">${trader.traderName }</td>
						<td>${trader.areaStr }</td>
						<td>${trader.ownerUser }</td>
						<td>${trader.changeTimes }</td>
						<td>${trader.level }</td>
					</tr>
					</c:forEach>
					</c:if>
				</tbody>
			</table>
			<c:if test="${empty traderList}">
				<!-- 查询无结果弹出 -->
				<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
			</c:if>
			<c:if test="${not empty traderList }">
			<div class="single_to_user">
				<div class="f_left">
					<div class="infor_name infor_name1">请选择新的归属销售</div>
					<div class="f_left">
                        <select class="input-middle" name="orgId" onchange="getUserList();">
                            <option value="-1">全部</option>
                            <c:forEach items="${orgList}" var="org">
                                <option value="${org.orgId}">${org.orgName}</option>
                            </c:forEach>
                        </select>
						<select name="single_to_user">
							<option selected="selected" value="0">请选择销售人员</option>
							<c:if test="${not empty userList }">
								<c:forEach items="${userList }" var="user">
								<option value="${user.userId }">${user.username}</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</div>
				
				<div class="pages_container" style="margin-top:0px;">
					<tags:page page="${page}"/>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="add-tijiao tcenter">
                <button type="submit" onclick="return formSub();">提交</button>
            </div>
            </c:if>
		</div>
		</c:if>
		<c:if test="${type eq 2}">
		<div class="batch_assign normal-list-page">
		<table
			class="table">
			<thead>
				<tr>
					<th>销售人员</th>
					<th>客户数量</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>${user.username }</td>
					<td>${userSupplierNum }</td>
				</tr>
			</tbody>
		</table>
		<c:if test="${userSupplierNum > 0 }">
		<div class="overflow-hidden">
			<div class="infor_name infor_name1">请选择新的归属销售</div>
			<div class="f_left">
                <select class="input-middle" name="orgId" onchange="getUserList();">
                    <option value="-1">全部</option>
                    <c:forEach items="${orgList}" var="org">
                        <option value="${org.orgId}">${org.orgName}</option>
                    </c:forEach>
                </select>
				<select name="batch_to_user">
					<option selected="selected" value="0">请选择销售人员</option>
					<c:if test="${not empty userList }">
						<c:forEach items="${userList }" var="user">
						<option value="${user.userId }">${user.username}</option>
						</c:forEach>
					</c:if>
				</select>
			</div>
		</div>
		<div class="add-tijiao tcenter mt10">
			<button type="submit" onclick="return formSub();">提交</button>
		</div>
		</c:if>
		</div>
		</c:if>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/customer/assign.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>
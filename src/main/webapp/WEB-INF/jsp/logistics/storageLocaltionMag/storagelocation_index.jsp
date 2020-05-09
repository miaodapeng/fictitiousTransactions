<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="库位管理" scope="application" />
<%@ include file="../../common/common.jsp"%>
    <div class="searchfunc">
        <form action="${pageContext.request.contextPath}/warehouse/storageLocationMag/index.do" method="post" id="search" >
            <ul>
                <li>
                    <label class="infor_name">库位名称</label>
                    <input type="text" class="input-middle" name="storageLocationName" id="storageLocationName" value="${storageLocation.storageLocationName}"/>
                </li>
                <li>
                    <label class="infor_name">库位状态</label>
                    <select class="input-middle f_left" name="isEnable" id="isEnable">
						<option selected="selected" value="">全部</option>
						<option value="0" <c:if test="${storageLocation.isEnable != null and storageLocation.isEnable=='0'}">selected="selected"</c:if>>已禁用</option>
						<option value="1" <c:if test="${storageLocation.isEnable != null and storageLocation.isEnable=='1'}">selected="selected"</c:if>>未禁用</option>
					</select>
                </li>
                <li>
                    <label class="infor_name">所属货架</label>
                      <select class="input-middle" id="storageRackId" name="storageRackId">
                        <option value="0">全部</option>
                        <c:forEach items="${listRack }" var="type">
                        	<option value="${type.storageRackId }" 
                        	  <c:if test="${not empty storageLocation &&  storageLocation.storageRackId == type.storageRackId}">selected="selected"</c:if>>${type.storageRackName }</option>
                        </c:forEach>
                    </select>
                </li>
                </ul>
            <div class="tcenter">
                <span class="bg-light-blue bt-bg-style bt-small" onclick="search();" id="searchSpan">搜索</span>
	            <span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
	            <span class="bg-light-blue bt-bg-style bt-small addtitle" tabTitle='{"num":"warehouse_storageLocationMag_addStorageLocation_storageLocationId","link":"./warehouse/storageLocationMag/addStorageLocation.do","title":"新增库位"}'>新增库位</span>
				<button type="button" class="bt-bg-style bg-light-blue bt-small pop-new-data"
						layerparams='{"width":"500px","height":"200px","title":"批量新增库位","link":"./batchAddStorageLocation.do"}'>批量新增库位</button>

			</div>
        </form>
    </div>
    <div class="content">
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th style="width:60px">序号</th>
                    <th style="width:200px">库位名称</th>
                    <th>库位备注</th>
                    <th style="width:150px">库存商品数量</th>
                    <th >所属货架</th>
                    <th style="width: 100px">库位状态</th>
                 </tr>
	            </thead>
	           <tbody>
				<c:forEach var="list" items="${storageLocationList}" varStatus="num">
					<tr>
						<td>${num.count}</td>
						<td>
                    	 <div class="font-blue"><a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"warehouse_storageLocationMag_addStorageLocation_storageLocationId${list.storageLocationId}",
									"link":"./warehouse/storageLocationMag/toStorageLocationDetailPage.do?storageLocationId=${list.storageLocationId}",
									"title":"库位详情"}'>${list.storageLocationName}</a>
						</div>
					    </td>
						<td>${list.comments}</td>
						<td>${list.cnt}</td>
						<td>
						  <div class="font-blue">
							<a class="addtitle" href="javascript:void(0);"
								tabTitle='{"num":"warehouse_storageRackMag_toStorageRackDetailPage_storageRackId${list.storageRackId}",
									"link":"./warehouse/storageRackMag/toStorageRackDetailPage.do?storageRackId=${list.storageRackId}",
									"title":"货架详情"}'>${list.storageRackName}</a>
						</div>
						</td>
						<c:choose>
							<c:when test="${list.isEnable eq 1}"><td>未禁用</td></c:when>
							<c:otherwise><td class="font-red">已禁用</td></c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</tbody>
				</table>
			
		<c:if test="${empty storageLocationList}">
      			<!-- 查询无结果弹出 -->
          		<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
       	</c:if>
       	<tags:page page="${page}"/>
       	</div>
       	<script type="text/javascript" src='<%= basePath %>static/js/logistics/storageLocationMag/index_storagelocation_mag.js?rnd=<%=Math.random()%>'></script>
        <%@ include file="../../common/footer.jsp"%>
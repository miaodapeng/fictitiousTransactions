<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="备货列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<!-- 这里注意最外层的main-container -->
<div class="main-container"> 
   <!--列表页搜索框  " searchfunc " begin-->
   <div class="list-pages-search">
       <form action="${pageContext.request.contextPath}/order/saleorder/bhIndex.do" method="post" id="search">
           <ul>
               <li>
                   <label class="infor_name">备货单号</label>
                   <input type="text" class="input-middle" name="saleorderNo" value="${saleorder.saleorderNo}"/>
               </li>
               <li>
                   <label class="infor_name">申请人</label>
                   <select class="input-middle" name="creator">
                       <option value="-1">全部</option>
                       <c:if test="${not empty buyerList }">
							<c:forEach items="${buyerList }" var="user">
								<option value="${user.userId }"
									<c:if test="${saleorder.creator eq user.userId }">selected="selected"</c:if>>${user.username }</option>
							</c:forEach>
						</c:if>
                   </select>
               </li>
               <li>
                   <label class="infor_name">备货单状态</label>
                   <select class="input-middle" name="status">
                       <option value="-1">全部</option>
                       <option <c:if test="${saleorder.status eq 0}">selected</c:if> value="0">待确认</option>
					   <option <c:if test="${saleorder.status eq 1}">selected</c:if> value="1">进行中</option>
					   <option <c:if test="${saleorder.status eq 2}">selected</c:if> value="2">已完结</option>
					   <option <c:if test="${saleorder.status eq 3}">selected</c:if> value="3">已关闭</option>
                   </select>
               </li>
               <li>
                   <label class="infor_name">产品名称</label>
                   <input type="text" class="input-middle" name="goodsName" value="${saleorder.goodsName}" />
               </li>
               <li>
                   <label class="infor_name">品牌</label>
                   <input type="text" class="input-middle" name="brandName" value="${saleorder.brandName}" />
               </li>
               <li>
                   <label class="infor_name">型号</label>
                   <input type="text" class="input-middle" name="model" value="${saleorder.model}" />
               </li>
               <li>
                   <label class="infor_name">订货号</label>
                   <input type="text" class="input-middle" name="sku" value="${saleorder.sku}" />
               </li>
               <li>
                   <label class="infor_name">审核状态</label>
                   <select class="input-middle" name="verifyStatus">
                       <option value="">全部</option>
                       <option <c:if test="${saleorder.verifyStatus eq 3}">selected</c:if> value="3">待审核</option>
					   <option <c:if test="${saleorder.verifyStatus eq 0}">selected</c:if> value="0">审核中</option>
					   <option <c:if test="${saleorder.verifyStatus eq 1}">selected</c:if> value="1">审核通过</option>
					   <option <c:if test="${saleorder.verifyStatus eq 2}">selected</c:if> value="2">审核未通过</option>
                   </select>
               </li>
               <li>
                   <label class="infor_name">采购状态</label>
                   <select class="input-middle" name="purchaseStatus">
                       <option value="-1">全部</option>
                       <option <c:if test="${saleorder.purchaseStatus eq 0}">selected</c:if> value="0">未采购</option>
					   <option <c:if test="${saleorder.purchaseStatus eq 1}">selected</c:if> value="1">部分采购</option>
					   <option <c:if test="${saleorder.purchaseStatus eq 2}">selected</c:if> value="2">已采购</option>
                   </select>
               </li>
               <li>
                   <label class="infor_name">创建时间</label>
                   <input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'searchEndtimeStr\')}'})" name="searchBegintimeStr" id="searchBegintimeStr" value='<date:date value ="${saleorder.searchBegintime}" format="yyyy-MM-dd"/>'>
                   <div class="gang">-</div>
                   <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'searchBegintimeStr\')}'})" name="searchEndtimeStr" id="searchEndtimeStr" value='<date:date value ="${saleorder.searchEndtime}" format="yyyy-MM-dd"/>'>
               </li>
           </ul>
           <div class="tcenter">
           	   <input type="hidden" name="searchDateType" value="1">
               <span class="bt-middle bg-light-blue bt-bg-style mr20" onclick="search();">搜索</span>
               <span class="bt-middle bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
               <span class="bt-middle bg-light-blue bt-bg-style mr20  addtitle" tabTitle='{"num":"addBhSaleorder","link":"./order/saleorder/addBhSaleorder.do","title":"新增备货"}'>新增备货</span>
           </div>
       </form>
   </div>
   <!--搜索框  " searchfunc " end-->
   <!-- 结合业务中不确定性的内容条目和计算机差异化的屏幕分辨率 ，表格的宽度有的明显知道内容多少的，就要定宽 ，如wid1。。。。-->
   <!--列表页表格  -->
   <div class="normal-list-page">
       <table class="table">
           <thead>
               <tr>
                   <th class="wid4">序号</th>
                   <th class="wid12">备货单号</th>
                   <th class="wid16">创建时间</th>
                   <th class="wid20">部门</th>
                   <th class="wid12">申请人</th>
                   <th >总额</th>
                   <th class="wid14">备货单状态</th>
                   <th class="wid14">审核状态</th>
                   <th class="wid14">采购状态</th>
               </tr>
           </thead>
           <tbody>
           	   <c:forEach var="list" items="${saleorderList}" varStatus="num">
           	   <c:if test="${list.orderType eq 2}">
               <tr>
                   <td>${num.count}</td>
                   <td >
                   		<c:choose>
							<c:when test="${list.status eq 0}">
								<span class="orangecircle"></span>
							</c:when>
							<c:when test="${list.status eq 1}">
								<span class="greencircle"></span>
							</c:when>
							<c:when test="${list.status eq 2}">
								<span class="bluecircle"></span>
							</c:when>
							<c:when test="${list.status eq 3}">
								<span class="greycircle"></span>
							</c:when>
						</c:choose>
                   		<c:if test="${list.validStatus ne 1}">
							<c:set var="shenhe" value="0"></c:set>
							<c:if test="${list.verifyStatus!=null && null!=list.verifyUsernameList}">
								<c:forEach items="${list.verifyUsernameList}" var="verifyUsernameInfo">
									<c:if test="${verifyUsernameInfo == loginUser.username}">
										<c:set var="shenhe" value="1"></c:set>
									</c:if>
								</c:forEach>
							</c:if>
						</c:if>
                       <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"order_saleorder_viewbhsaleorder${list.saleorderId}","link":"./order/saleorder/viewBhSaleorder.do?saleorderId=${list.saleorderId}","title":"备货信息"}'>${list.saleorderNo}</a>${list.validStatus != 1 && list.verifyStatus != 1 && shenhe == 1 ?"<font color='red'>[审]</font>":""}
                   </td>
                   <td><date:date value ="${list.addTime}"/></td>
                   <td>${list.salesDeptName}</td>
                   <td>${list.salesName}</td>
                   <td>${list.totalAmount}</td>
                   <td>
                    <c:choose>
						<c:when test="${list.status eq 0}">
							待确认
						</c:when>
						<c:when test="${list.status eq 1}">
							进行中
						</c:when>
						<c:when test="${list.status eq 2}">
							已完结
						</c:when>
						<c:when test="${list.status eq 3}">
							已关闭
						</c:when>
					</c:choose>
                   </td>

                   <td>
                       <c:choose>
                       <c:when test="${list.bhVerifyStatus eq 0}">
                           审核中
                       </c:when>
                       <c:when test="${list.bhVerifyStatus eq 1}">
                           审核通过
                       </c:when>
                       <c:when test="${list.bhVerifyStatus eq 2}">
                           审核未通过
                       </c:when>
                       <c:otherwise>
                           待审核
                       </c:otherwise>
                       </c:choose>
                   </td>
                   <td>
                    <c:choose>
						<c:when test="${list.purchaseStatus eq 0}">
							未采购
						</c:when>
						<c:when test="${list.purchaseStatus eq 1}">
							部分采购
						</c:when>
						<c:when test="${list.purchaseStatus eq 2}">
							已采购
						</c:when>
					</c:choose>
                   </td>
               </tr>
               </c:if>
               </c:forEach>
               <c:if test="${empty saleorderList}">
               <tr>
               	   <td colspan="9">查询无结果！请尝试使用其他搜索条件。</td>
               </tr>
               </c:if>
           </tbody>
       </table>
   </div>
   <tags:page page="${page}"/>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/bh_index.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
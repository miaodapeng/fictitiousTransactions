<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑发货地址" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="formpublic">
	<form method="post" action="" id="myform">
		<ul>
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable>发货地区</lable>
				</div>
				<div class="f_left">
					<div class="form-blanks">
						<select class="input-small mr6" name="province" id="province">
							<option value="0">请选择</option>
							<c:if test="${not empty provinceList }">
								<c:forEach items="${provinceList }" var="prov">
									<option value="${prov.regionId }"
										<c:if test="${province eq prov.regionId }">selected="selected"</c:if>>${prov.regionName }</option>
								</c:forEach>
							</c:if>
						</select> <select class="input-small mr6" name="city" id="city">
							<option value="0">请选择</option>
							<c:if test="${not empty cityList }">
								<c:forEach items="${cityList }" var="cy">
									<option value="${cy.regionId }"
										<c:if test="${city eq cy.regionId }">selected="selected"</c:if>>${cy.regionName }</option>
								</c:forEach>
							</c:if>
						</select> <select class="input-small mr6" name="zone" id="zone">
							<option value="0">请选择</option>
							<c:if test="${not empty zoneList }">
								<c:forEach items="${zoneList }" var="zo">
									<option value="${zo.regionId }"
										<c:if test="${zone eq zo.regionId }">selected="selected"</c:if>>${zo.regionName }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
					<div id="proError"></div>
				</div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable>发货地址</lable>
				</div>
				<div class="f_left">
					<div class="form-blanks">
	               		<input type="text" class="input-largest" name="address" id="address" value="${address.address}"/>
	               	</div>
                  <div id="addressError"></div>
				</div>
			</li>
			<li>
               <div class="infor_name">
                   <span>*</span>
                   <lable>发货联系人</lable>
               </div>
               <div class="f_left">
                   <div class="form-blanks">
	               		<input type="text" class="input-largest" name="contactName" id="contactName" value="${address.contactName}"/>
	               	</div>
                  <div id="contactNameError"></div>
               </div>
           </li>
           <li>
              <div class="infor_name">
                  <span>*</span>
                  <lable>联系人手机</lable>
              </div>
              <div class="f_left">
	           		<div class="form-blanks">
	               		<input type="text" class="input-largest" name="mobile" id="mobile" value="${address.mobile}"/>
	               	</div>
                  	<div id="mobileError"></div>
                	<div class="pop-friend-tips mt5">
				友情提示：<br/>
				1、发货信息应用于物流出库快递单打印；
	            </div>
              </div>
          </li>
		</ul>
		<div class="add-tijiao tcenter mt20 mb20">
			<input type="hidden" name="beforeParams" value='${beforeParams}'>
			<input type="hidden" name="addressId" value='${address.addressId}'/>
			<input type="hidden" name="beforeParams" value='${beforeParams}'/>
			<button type="submit">提交</button>
			<button type="button" class="dele" id="close-layer">取消</button>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/home/page/add_receive_address.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>

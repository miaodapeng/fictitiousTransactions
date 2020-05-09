<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="添加地址" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src='<%=basePath%>/static/js/order/saleorder/add_address.js?rnd=<%=Math.random()%>'></script>
	<div class="addElement">
        <div class="add-main">
            <form action="" method="post" id="myform">
                <ul class="add-detail">
                    <li>
                        <div class="infor_name">
                            <span>*</span>
                            <label>地区</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select name="province" id="province">
	                        	<option value="0">请选择</option>
		                    	<c:if test="${not empty provinceList }">
		                    		<c:forEach items="${provinceList }" var="prov">
		                    			<option value="${prov.regionId }" <c:if test="${province eq prov.regionId }">selected="selected"</c:if>>${prov.regionName }</option>
		                    		</c:forEach>
		                    	</c:if>
							</select>
	                        <select name="city" id ="city">
	                        	<option value="0">请选择</option>
	                        	<c:if test="${not empty cityList }">
		                    		<c:forEach items="${cityList }" var="cy">
		                    			<option value="${cy.regionId }" <c:if test="${city eq cy.regionId }">selected="selected"</c:if>>${cy.regionName }</option>
		                    		</c:forEach>
		                    	</c:if>
							</select>
	                        <select name="zone" id="zone">
	                        	<option value="0">请选择</option>
	                        	<c:if test="${not empty zoneList }">
		                    		<c:forEach items="${zoneList }" var="zo">
		                    			<option value="${zo.regionId }" <c:if test="${zone eq zo.regionId }">selected="selected"</c:if>>${zo.regionName }</option>
		                    		</c:forEach>
		                    	</c:if>
							</select>
							<span id="area_div"></span>
                            <div class="clear"></div>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name">
                            <span>*</span>
                            <label>地址</label>
                        </div>
                        <div class="f_left">
                            <input type="text" class="input-largest" name="address" id="address" />
                        </div>
                    </li>
                </ul>
                <div class="add-tijiao tcenter">
                	<input type="hidden" name="traderId" value="${traderId}" id="traderId">
                	<input type="hidden" name="isDefault" value="0"/>
		        	<input type="hidden" name="traderType" value="1"/>
		        	<input type="hidden" name="indexId" value="${indexId}" id="indexId">
		        	<input type="hidden" name="formToken" value="${formToken}"/>
                    <button type="submit" id="add_submit">提交</button>
                    <button class="dele" type="button" id="close-layer">取消</button>
                </div>
            </form>
        </div>
    </div>
<%@ include file="../../common/footer.jsp"%>
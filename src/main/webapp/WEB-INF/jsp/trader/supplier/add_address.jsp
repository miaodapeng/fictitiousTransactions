<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增地址" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/supplier/add_address.js?rnd=<%=Math.random()%>"></script>

    <div class="formpublic">
        <form method="post" action="" id="myform">
        	<input type="hidden" id="traderId" name="traderId" value="${traderId}">
        	<input type="hidden" id="traderSupplierId" name="traderSupplierId" value="${traderSupplierId}">
        	<input type="hidden" name="traderAddressId" value="${traderAddress.traderAddressId}">
        	<input type="hidden" id="add" name="op" value="add"/>
        	<input type="hidden" name="traderType" value="2"/>
            <ul>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>地区</lable>
                    </div>
                    <div class="f_left">
                    	<div>
	                        <select class="input-middle mr6" name="province" id="province">
	                        	<option value="0">请选择</option>
		                    	<c:if test="${not empty provinceList }">
		                    		<c:forEach items="${provinceList }" var="prov">
		                    			<option value="${prov.regionId }" <c:if test="${province eq prov.regionId }">selected="selected"</c:if>>${prov.regionName }</option>
		                    		</c:forEach>
		                    	</c:if>
							</select>
	                        <select class="input-middle mr6" name="city" id ="city">
	                        	<option value="0">请选择</option>
	                        	<c:if test="${not empty cityList }">
		                    		<c:forEach items="${cityList }" var="cy">
		                    			<option value="${cy.regionId }" <c:if test="${city eq cy.regionId }">selected="selected"</c:if>>${cy.regionName }</option>
		                    		</c:forEach>
		                    	</c:if>
							</select>
	                        <select class="input-middle mr6" name="zone" id="zone">
	                        	<option value="0">请选择</option>
	                        	<c:if test="${not empty zoneList }">
		                    		<c:forEach items="${zoneList }" var="zo">
		                    			<option value="${zo.regionId }" <c:if test="${zone eq zo.regionId }">selected="selected"</c:if>>${zo.regionName }</option>
		                    		</c:forEach>
		                    	</c:if>
							</select>
						</div>
						<div>
	                        <span class="font-red pr" style="display: none;">省份不能为空</span>
	                        <span class="font-red ci" style="display: none;">地市不能为空</span>
	                        <span class="font-red zo" style="display: none;">区县不能为空</span>
                        </div>
                    </div>
                </li>
                 <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>地址</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest errobor" name="address" id="address" value="${traderAddress.address}"/>

                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>邮编</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest errobor" name="zipCode" id="zipCode" value="${traderAddress.zipCode}"/>

                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>备注</lable>
                    </div>
                     <div class="f_left commonuse table-largest">
                     	<div>
	                        <input type="text" name="comments" id="comments" value="${traderAddress.comments}" class="input-largest" placeholder="您可以点击常用备注进行选择" />
	                        <label>常用备注:</label>
	                        <span>合同地址</span>
	                        <span>收货地址</span>
	                        <span>收票地址</span>
                        </div>
                        <div id="commentsError"></div>
                    </div>
                </li>
            </ul>
            <div class="add-tijiao tcenter mt2">
            	<input type="hidden" name="formToken" value="${formToken}"/>
                <button type="submit">提交</button>
                <button type="button" class="dele" id="close-layer">取消</button>
            </div>
        </form>
    </div>
</body>

</html>

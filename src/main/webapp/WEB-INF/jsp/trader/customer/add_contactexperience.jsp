
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增联系人行业背景" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/customer/add_contactexperience.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/customer/edit_baseinfo.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
    <div class="formpublic">
        <form method="post" action="" id="myform">
        	
            <ul>
                <li>
                    <div class="infor_name">
                        <lable>工作年份</lable>
                    </div>
                    <div class="f_left inputfloat">
                    	<div>
                        <input class="Wdate input-smaller96 mr4" placeholder="请选择日期" onFocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'end\')}'})" name="start" 
	                    	id="start"  /><div class="gang">-</div>
                        <input class="Wdate input-smaller96" placeholder="请选择日期" onFocus="WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'start\')}'})" name="end" 
	                    	id="end" />
	                    	</div>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>企业名称</lable>
                    </div>
                    <div class="f_left commonuse table-largest" id="dept">
                        <input type="text" name="company" id="company" class="input-middle" placeholder="请输入企业名称"/>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>职位</lable>
                    </div>
                     <div class="f_left commonuse table-largest" id="posi">
                        <input type="text" name="position" id="position" value="${traderContact.position}" class="input-middle mr6" placeholder="您可以点击常用职位进行选择" />
                        <label>常用职位:</label>
                        <span>经理</span>
                        <span>老板</span>
                        <span>工程师</span>
                        <span>主管</span>
                        <span>专员</span>
                        <span>会计 </span>
                        <span>教授</span>
                        <span>研究生</span>
                        <span>研究员 </span>
                        <span>主任 </span>
                    </div>
                </li>

				<li alt="fenxiao">
					<div class="infor_name ">
						<label>经营品牌</label>
					</div>
					<div class="f_left inputradio">
						<div class="career-one">
							<input class="input-middle f_left mr8 " style="height: 26px;" placeholder="请输入关键词查询" name="bussinessBrandKey"/>
							<div class="f_left bt-bg-style bg-light-blue bt-small mr8 searchbrand" onclick="searchBussinessBrand()">搜索</div>
							<select class="input-middle f_left mr8 " name="bussinessBrands"></select>
							<div class="f_left bt-bg-style bg-light-blue bt-small mr8 addbrand" onclick="addBussinessBrand()" id="addBussinessBrand">添加</div>
						</div>
						<div class="addtags mt8 addbrandtags <c:if test="${empty traderCustomer.traderCustomerBussinessBrands }">none</c:if>">
							<ul id="bussinessBrand_show">
								<c:if test="${not empty traderCustomer.traderCustomerBussinessBrands }">
									<c:forEach items="${traderCustomer.traderCustomerBussinessBrands }" var="bussinessBrand">
										<li class="bluetag">
											${bussinessBrand.brand.brandName }<input type="hidden" name="bussinessBrandId" value="${bussinessBrand.brandId }"><i class="iconbluecha" onclick="delTag(this);"></i>
										</li>
									</c:forEach>
								</c:if>
							</ul>
						</div>
					</div>
				</li>
				<li class="specialli" alt="fenxiao">
					<div class="infor_name">
						<label>经营区域</label>
					</div>
					<div class="readyadd f_left">
						<div class="career-one">
							<select class="input-smaller f_left mr8" name="bussiness_province">
		                    	<option value="0">请选择</option>
		                    	<c:if test="${not empty provinceList }">
		                    		<c:forEach items="${provinceList }" var="province">
		                    			<option value="${province.regionId }">${province.regionName }</option>
		                    		</c:forEach>
		                    	</c:if>
		                    </select>
		                    <select class="input-smaller f_left mr8" name="bussiness_city">
		                        <option value="0">请选择</option>
		                    </select>
		                    <select class="input-smaller f_left mr8" name="bussiness_zone">
		                    	<option value="0">请选择</option>
		                    </select>
							<div
								class="f_left bt-bg-style bg-light-blue bt-smaller mr8 addaddress" onclick="addBussinessArea();" id="addBussinessArea">添加</div>
						</div>
						<div class="addtags mt8 addaddresstags <c:if test="${empty bussinessMap }">none</c:if>">
							<ul id="bussinessArea_show">
								<c:if test="${not empty bussinessMap }">
									<c:forEach items="${bussinessMap }" var="bussinessArea">
										<li class="bluetag">
										${bussinessArea.value.get("areaStr") }<input type="hidden" name="bussinessAreaId" value="${bussinessArea.key}"><input type="hidden" name="bussinessAreaIds" value="${bussinessArea.value.get('areaIds') }"><i class="iconbluecha" onclick="delTag(this);"></i>
										</li>
									</c:forEach>
								</c:if>
							</ul>
						</div>
					</div>
				</li>
            </ul>
            <div class="add-tijiao tcenter">
                <button type="submit">提交</button>
                <button type="button" class="dele" id="close-layer">取消</button>
            </div>
            <input type="hidden" name="formToken" value="${formToken}"/>
            <input type="hidden" name="opt" id="opt" value="add"/>
            <input type="hidden" name="traderContactId" value="${traderContact.traderContactId}"/>
            <input type="hidden" id="traderCustomerId" value="${traderCustomer.traderCustomerId }"/>
            <input type="hidden" id="traderId" value="${traderCustomer.traderId }"/>
        </form>
    </div>
</body>

</html>

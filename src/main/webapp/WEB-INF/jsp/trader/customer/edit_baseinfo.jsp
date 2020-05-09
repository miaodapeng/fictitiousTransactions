<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑基本信息" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<%@ include file="customer_tag.jsp"%>
<style type="text/css">
    .submit{
		margin-right: 6px;
		background: rgb(92, 184, 92);
		border-width: 1px;
		border-style: solid;
		border-color: rgb(76, 174, 76);
		border-image: initial;
	}
</style>
<div class="baseinforcontainer" style="padding-bottom: 15px;">
	<div class="border">
		<div class="baseinfor f_left">基本信息</div>
		<div class="clear"></div>
	</div>
	<div class="baseinforeditform">
		<form method="post"
			action="${pageContext.request.contextPath}/trader/customer/saveeditbaseinfo.do"
			id="customerForm">
			<ul>
				<li>
					<div class="infor_name">
						<span>*</span>
						<lable>客户名称</lable>
					</div>
					<div class="f_left">
						<input <c:if test="${1 eq traderCustomer.source }">disabled="disabled"</c:if> type="text" class="input-largest errobor" name="traderName"
							id="traderName" value="${traderCustomer.trader.traderName }" />
					</div>
				</li>
				<li>
					<div class="infor_name">
						<span>*</span>
						<lable>地区</lable>
					</div>
					<div class="f_left">
						<select class="input-middle mr6" name="province" <c:if test="${1 eq traderCustomer.source }">disabled="disabled"</c:if>  >
							<option value="0">请选择</option>
							<c:if test="${not empty provinceList }">
								<c:forEach items="${provinceList }" var="province">
									<option value="${province.regionId }"
										<c:if test="${ not empty provinceRegion &&  province.regionId == provinceRegion.regionId }">selected="selected"</c:if>>${province.regionName }</option>
								</c:forEach>
							</c:if>
						</select> <select class="input-middle mr6" name="city" <c:if test="${1 eq traderCustomer.source }">disabled="disabled"</c:if> >
							<option value="0">请选择</option>
							<c:if test="${not empty cityList }">
								<c:forEach items="${cityList }" var="city">
									<option value="${city.regionId }"
										<c:if test="${ not empty cityRegion &&  city.regionId == cityRegion.regionId }">selected="selected"</c:if>>${city.regionName }</option>
								</c:forEach>
							</c:if>
						</select> <select class="input-middle mr6" name="zone" id="zone" <c:if test="${1 eq traderCustomer.source }">disabled="disabled"</c:if> >
							<option value="0">请选择</option>
							<c:if test="${not empty zoneList }">
								<c:forEach items="${zoneList }" var="zone">
									<option value="${zone.regionId }"
										<c:if test="${ not empty zoneRegion &&  zone.regionId == zoneRegion.regionId }">selected="selected"</c:if>>${zone.regionName }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</li>
				<li id="attr_brfore_li">
					<div class="infor_name">
						<span>*</span>
						<lable>客户类型</lable>
					</div>
					<div class="f_left">
						<div id="customer_category_div">
						<c:forEach items="${traderCustomer.customerCategoriesMap }"
							var="category">
							<select class="input-middle mr6" <c:if test="${1 eq traderCustomer.source }">disabled="disabled"</c:if>
								name="customer_category${category.key }"
								onchange="changeCate(this);" id="customer_category">
								<option selected="selected" value="0">请选择</option>
								<c:forEach items="${category.value }" var="cates">
									<option
										<c:if test="${cates.traderCustomerCategoryId==category.key}"> selected="selected" </c:if>
										value="${cates.traderCustomerCategoryId}">${cates.customerCategoryName }</option>
								</c:forEach>
							</select>
						</c:forEach>
						</div>
					</div>
				</li>


				<c:if test="${not empty traderCustomer.ownershipStr }" >
					<li class="customerAttribute bt">
						<div class="infor_name">
							<span>*</span>
							<lable>所有制</lable>
						</div>
						<div class="f_left">
							<select class="input-middle" name="traderCustomer.ownership" <c:if test="${1 eq traderCustomer.source }">disabled="disabled"</c:if> >
								<option value="0">请选择</option>
								<c:forEach items="${ownership }" var="ownership">
									<option value="${ownership.sysOptionDefinitionId }"
										<c:if test="${ownership.sysOptionDefinitionId == traderCustomer.ownership}">selected="selected"</c:if>>${ownership.title }</option>
								</c:forEach>
							</select>
						</div>
					</li>
				</c:if>
				<c:if test="${not empty traderCustomer.medicalTypeStr }">
					<li class="customerAttribute bt">
						<div class="infor_name">
							<span>*</span>
							<lable>医学类型</lable>
						</div>
						<div class="f_left">
							<select class="input-middle" name="traderCustomer.medicalType" <c:if test="${1 eq traderCustomer.source }">disabled="disabled"</c:if> >
								<option value="0">请选择</option>
								<c:forEach items="${medicalType }" var="medicalType">
									<option value="${medicalType.sysOptionDefinitionId }"
										<c:if test="${medicalType.sysOptionDefinitionId == traderCustomer.medicalType}">selected="selected"</c:if>>${medicalType.title }</option>
								</c:forEach>
							</select>
						</div>
					</li>
				</c:if>
				<c:if test="${not empty traderCustomer.hospitalLevelStr }">
					<li class="customerAttribute bt">
						<div class="infor_name">
							<span>*</span>
							<lable>医院等级</lable>
						</div>
						<div class="f_left">
							<select class="input-middle" name="traderCustomer.hospitalLevel" <c:if test="${1 eq traderCustomer.source }">disabled="disabled"</c:if> >
								<option value="0">请选择</option>
								<c:forEach items="${hospitalLevel }" var="hospitalLevel">
									<option value="${hospitalLevel.sysOptionDefinitionId }"
										<c:if test="${hospitalLevel.sysOptionDefinitionId == traderCustomer.hospitalLevel}">selected="selected"</c:if>>${hospitalLevel.title }</option>
								</c:forEach>
							</select>
						</div>
					</li>
				</c:if>

				<c:if test="${not empty attributes}">
					<c:forEach items="${attributes }" var="listData">
						<li
							class="customerAttribute <c:if test="${listData.isSelected==1 }">bt</c:if>"
							<c:if test="${listData.parentId ==5 }">alt="${listData.parentId }"</c:if>>
							<div class="infor_name mt0">
								<c:if test="${listData.isSelected==1 }">
									<span>*</span>
								</c:if>
								<lable>${listData.categoryName }</lable>
							</div> <c:if test="${listData.inputType == 0 }">
								<div class="f_left inputfloat inputradio">
									<ul>
							</c:if> <c:if test="${listData.inputType == 1 }">
								<div class="f_left inputradio">
									<ul>
							</c:if> <c:forEach items="${listData.sysOptionDefinitions }"
								var="option">
								<c:if test="${listData.inputType == 0 }">
									<li><input type="checkbox" onclick="attrCheck(this);"
										name="attributeId"
										value="${listData.attributeCategoryId}_${option.sysOptionDefinitionId}"
										<c:if test="${listData.attributeCategoryId == 5 && option.sysOptionDefinitionId == 93 }">onclick="getChildAttr(this,5)"</c:if>
										<c:if test="${not empty traderCustomer.attributeMap}">
											<c:forEach items="${traderCustomer.attributeMap}" var="attibute">
												<c:forEach items="${attibute.value }" var="attri">
													<c:if test="${attri.attributeId == option.sysOptionDefinitionId}">
													checked="checked"
													</c:if>
							</c:forEach>
					</c:forEach>
				</c:if>


				/>
				<label>${option.title }</label>
				</li>
				</c:if>
				<c:if test="${listData.inputType == 1 }">
					<li><input type="checkbox" name="attributeId"
						value="${listData.attributeCategoryId}_${option.sysOptionDefinitionId}"
						<c:if test="${listData.attributeCategoryId == 5 && option.sysOptionDefinitionId == 93 }">onclick="getChildAttr(this,5)"</c:if>
						<c:if test="${not empty traderCustomer.attributeMap}">
											<c:forEach items="${traderCustomer.attributeMap}" var="attibute">
												<c:forEach items="${attibute.value }" var="attri">
													<c:if test="${attri.attributeId == option.sysOptionDefinitionId}">
													checked="checked"
													</c:if>
					</c:forEach> </c:forEach>
				</c:if>


				/>
				<label>${option.title }</label>
				<c:if
					test="${(listData.scope == 1027 && option.sysOptionDefinitionId==109)
										 			|| (listData.scope == 1028 && option.sysOptionDefinitionId==115)
										 			|| (listData.scope == 1032 && option.sysOptionDefinitionId==302)}">
					<input type="text" class="input-small mt0 heit18"
						name="attributedesc_${listData.attributeCategoryId}_${option.sysOptionDefinitionId}"
						<c:if test="${not empty traderCustomer.attributeMap}">
											<c:forEach items="${traderCustomer.attributeMap}" var="attibute">
												<c:forEach items="${attibute.value }" var="attri">
													<c:if test="${attri.attributeId == option.sysOptionDefinitionId}">
												 	value="${attri.attributeOther }"
												 	</c:if>
					</c:forEach>
					</c:forEach>
				</c:if>
				/>
				</c:if>
				</li>
				</c:if>
				</c:forEach>
				<c:if test="${listData.inputType == 1 }">
			</ul>
	</div>
	</c:if>
	<c:if test="${listData.inputType == 0 }">
		</ul>
</div>
</c:if>
</li>
</c:forEach>
</c:if>

<li class="specialli" alt="fenxiao">
	<div class="infor_name">
		<label>经营区域</label>
	</div>
	<div class="readyadd f_left">
		<div class="career-one">
			<select class="input-middle f_left mr8" name="bussiness_province">
				<option value="0">请选择</option>
				<c:if test="${not empty provinceList }">
					<c:forEach items="${provinceList }" var="province">
						<option value="${province.regionId }">${province.regionName }</option>
					</c:forEach>
				</c:if>
			</select> <select class="input-middle f_left mr8" name="bussiness_city">
				<option value="0">请选择</option>
			</select> <select class="input-middle f_left mr8" name="bussiness_zone">
				<option value="0">请选择</option>
			</select>
			<div
				class="f_left bt-bg-style bg-light-blue bt-smaller mr8 addaddress"
				onclick="addBussinessArea();" id="addBussinessArea">添加</div>
		</div>
		<div
			class="addtags mt8 addaddresstags <c:if test="${empty bussinessMap }">none</c:if>">
			<ul id="bussinessArea_show">
				<c:if test="${not empty bussinessMap }">
					<c:forEach items="${bussinessMap }" var="bussinessArea">
						<li class="bluetag">${bussinessArea.value.get("areaStr") }<input
							type="hidden" name="bussinessAreaId" value="${bussinessArea.key}"><input
							type="hidden" name="bussinessAreaIds"
							value="${bussinessArea.value.get('areaIds') }"><i
							class="iconbluecha" onclick="delTag(this);"></i>
						</li>
					</c:forEach>
				</c:if>
			</ul>
		</div>
	</div>
</li>
<li alt="fenxiao">
	<div class="infor_name ">
		<label>经营品牌</label>
	</div>
	<div class="f_left inputradio">
		<div class="career-one">
			<input class="input-middle f_left mr8 mb8 mt0" placeholder="请输入关键词查询"
				name="bussinessBrandKey" />
			<div
				class="f_left bt-bg-style bg-light-blue bt-small mr8 searchbrand"
				onclick="searchBussinessBrand()">搜索</div>
			<select class="input-middle f_left mr8 mb8 mt0"
				name="bussinessBrands">
			</select>
			<div class="f_left bt-bg-style bg-light-blue bt-small mr8 addbrand"
				onclick="addBussinessBrand()" id="addBussinessBrand">添加</div>
		</div>
		<div
			class="addtags addbrandtags <c:if test="${empty traderCustomer.traderCustomerBussinessBrands }">none</c:if>">
			<ul id="bussinessBrand_show">
				<c:if
					test="${not empty traderCustomer.traderCustomerBussinessBrands }">
					<c:forEach items="${traderCustomer.traderCustomerBussinessBrands }"
						var="bussinessBrand">
						<li class="bluetag">${bussinessBrand.brand.brandName }<input
							type="hidden" name="bussinessBrandId"
							value="${bussinessBrand.brandId }"><i class="iconbluecha"
							onclick="delTag(this);"></i></li>
</li>
</c:forEach>
</c:if>
</ul>
</div>
</div>
</li>
<li alt="fenxiao">
	<div class="infor_name ">
		<label>代理品牌</label>
	</div>
	<div class="f_left inputradio">
		<div class="career-one">
			<input class="input-middle f_left mr8 mt0" placeholder="请输入关键词查询"
				name="agentBrandKey" />
			<div
				class="f_left bt-bg-style bg-light-blue bt-small mr8 searchbrand"
				onclick="searchAgentBrand()">搜索</div>
			<select class="input-middle f_left mr8" name="agentBrands">
			</select>
			<div class="f_left bt-bg-style bg-light-blue bt-small mr8 addbrand"
				onclick="addAgentBrand()" id="addAgentBrand">添加</div>
		</div>
		<div
			class="addtags addbrandtags mt8 <c:if test="${empty traderCustomer.traderCustomerAgentBrands }">none</c:if>">
			<ul id="agentBrand_show">
				<c:if test="${not empty traderCustomer.traderCustomerAgentBrands }">
					<c:forEach items="${traderCustomer.traderCustomerAgentBrands }"
						var="agentBrand">
						<li class="bluetag">${agentBrand.brand.brandName }<input
							type="hidden" name="agentBrandId" value="${agentBrand.brandId }"><i
							class="iconbluecha" onclick="delTag(this);"></i></li>
</li>
</c:forEach>
</c:if>
</ul>
</div>
</div>
</li>
<li alt="fenxiao">
	<div class="infor_name mt3">
		<label>业务模式</label>
	</div>
	<div class="f_left">
		<span class="mr4">直销比例 </span> <input type="text"
			class="input-smallest mr8 " name="traderCustomer.directSelling"
			<c:if test="${traderCustomer.directSelling > 0 }"> value="${traderCustomer.directSelling }"</c:if> /><span
			class="mr8">%</span> <span class="mr4">批发比例</span> <input type="text"
			class="input-smallest mr8" name="traderCustomer.wholesale"
			/ <c:if test="${traderCustomer.wholesale > 0 }"> value="${traderCustomer.wholesale }"</c:if>><span
			id="wholesale">%</span>
	</div>
</li>
<li alt="fenxiao">
	<div class="infor_name mt0">
		<label>销售模式</label>
	</div>
	<div class="f_left inputradio" id="salesModel_div">
		<c:forEach items="${salesModel }" var="model">
			<input type='radio' name="traderCustomer.salesModel"
				value="${model.sysOptionDefinitionId }"
				<c:if test="${model.sysOptionDefinitionId == traderCustomer.salesModel}">checked</c:if>>
			<label class='mr8'>${model.title }</label>
		</c:forEach>
	</div>
</li>
<li>
	<div class="infor_name ">
		<label>注册年份</label>
	</div>
	<div class="f_left inputradio">
		<input type="text" name="traderCustomer.registeredDateStr" <c:if test="${1 eq traderCustomer.source }">disabled="disabled"</c:if>
			class="input-middle Wdate"
			onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			value="<fmt:formatDate value="${traderCustomer.registeredDate }" pattern="yyyy-MM-dd" />" />
	</div>
</li>
<li>
	<div class="infor_name ">
		<label>注册资金</label>
	</div>
	<div class="f_left inputradio">
		<input <c:if test="${1 eq traderCustomer.source }">disabled="disabled"</c:if> type="text" name="traderCustomer.registeredCapital"
			id="registeredCapital" class="input-middle"
			value="${traderCustomer.registeredCapital }" /><span
			class="mt6" id="registeredCapitalSpan"></span>
	</div>
</li>
<li>
	<div class="infor_name mt0">
		<label>员工人数</label>
	</div>
	<div class="f_left inputradio">
		<select class="input-middle f_left mr8" <c:if test="${1 eq traderCustomer.source }">disabled="disabled"</c:if>
			name="traderCustomer.employees">
			<option selected="selected" value="0">请选择</option>
			<c:forEach items="${employees}" var="employee">
				<option value="${employee.sysOptionDefinitionId }"
					<c:if test="${employee.sysOptionDefinitionId == traderCustomer.employees}">selected="selected"</c:if>>${employee.title }</option>
			</c:forEach>
		</select>
	</div>
</li>
<li>
	<div class="infor_name mt0">
		<label>年销售额</label>
	</div>
	<div class="f_left inputradio">
		<select class="input-middle f_left mr8"
			name="traderCustomer.annualSales">
			<option value="0">请选择</option>
			<c:forEach items="${annualSales}" var="annualSale">
				<option value="${annualSale.sysOptionDefinitionId }"
					<c:if test="${annualSale.sysOptionDefinitionId == traderCustomer.annualSales}">selected="selected"</c:if>>${annualSale.title }</option>
			</c:forEach>
		</select>
	</div>
</li>
</ul>
<div class="add-tijiao tcenter">
	<input type="hidden" name="traderNameBefore"
							id="traderNameBefore" value="${traderCustomer.trader.traderName }" />
	<input type="hidden" name="traderId" value="${traderCustomer.traderId }">
	<input type="hidden" name="isLcfx" value="0">
	<input type="hidden" id="traderCustomerId" name="traderCustomer.traderCustomerId" value="${traderCustomer.traderCustomerId }">
	<input type="hidden" name="traderCustomer.traderCustomerCategoryId" id="traderCustomerCategoryId" value="${traderCustomer.traderCustomerCategoryId }">
	<input type="hidden" name="traderCustomerCategoryIds" id="traderCustomerCategoryIds">
	<input type="hidden" id="show_fenxiao" value="${show_fenxiao }">
	<input type="hidden" name="beforeParams" value='${beforeParams}'/>
	<input type="hidden" name="customerNatureBefore" id="customerNatureBefore" value="0"/>
	<input type="hidden" id="isCheckAptitudeStatus" name="isCheckAptitudeStatus" value="0"/>
	<button id="save" style="display: none" type="submit"></button>
	<button  type="button" class="submit" onclick="saveBaseInfo()">提交</button>
	<button class="dele" id="close-layer" type="button"
		onclick="goUrl('${pageContext.request.contextPath}/trader/customer/baseinfo.do?traderCustomerId=${traderCustomer.traderCustomerId}')">取消</button>
</div>
</form>
</div>
<script type="text/javascript">
	$(function() {
		$("li[alt='fenxiao']").hide();

		if ($("#show_fenxiao").val() == "true") {
			$("li[alt='fenxiao']").show();
		}
	});
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/customer/edit_baseinfo.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>

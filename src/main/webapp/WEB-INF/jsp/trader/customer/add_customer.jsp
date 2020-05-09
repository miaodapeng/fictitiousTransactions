<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增客户" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="pt10">
	<form method="post"
		action="${pageContext.request.contextPath}/trader/customer/saveadd.do" id="customerForm">
		<div class="parts">
			<div class="baseinforcontainer pb20">
				<div class="border overflow-hidden">
					<div class="baseinfor f_left">基本信息</div>
				</div>
				<div class="baseinforeditform">
					<ul>
						<li>
							<div class="infor_name">
								<span>*</span>
								<lable>客户名称</lable>
							</div>
							<div class="f_left">
								<input type="hidden" name="formToken" value="${formToken}"/>
								<input type="text" class="input-largest errobor"
									name="traderName" id="traderName" value="${traderName }"/>
									<span  id="eye" style="line-height:24px;" onclick="upperCase();" class="bt-small bt-border-style border-blue "
							layerParams='{"width":"40%","height":"320px","title":"客户列表","link":"<%= basePath %>trader/customer/queryEyeCheck.do?type=1"}'>天眼查接口查询</span>
							</div>
						</li>
						<li>
							<div class="infor_name">
								<span>*</span>
								<lable>地区</lable>
							</div>
							<div class="f_left">
								<select class="input-middle mr6" name="province">
									<option value="0">请选择</option>
									<c:if test="${not empty provinceList }">
										<c:forEach items="${provinceList }" var="province">
											<option value="${province.regionId }">${province.regionName }</option>
										</c:forEach>
									</c:if>
								</select> <select class="input-middle mr6" name="city">
									<option value="0">请选择</option>
								</select> <select class="input-middle mr6" name="zone" id="zone">
									<option value="0">请选择</option>
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
									<select class="input-middle mr6" name="customer_category"
										onchange="changeCate(this);" id="customer_category">
										<option selected="selected" value="0">请选择</option>
									</select>
								</div>
							</div>
						</li>
						<li class="specialli" alt="fenxiao">
							<div class="infor_name">
								<label>经营区域</label>
							</div>
							<div class="readyadd f_left">
								<div class="career-one">
									<select class="input-middle f_left mr8"
										name="bussiness_province">
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
								<div class="addtags mt8 addaddresstags ">
									<ul id="bussinessArea_show">
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
									<input class="input-middle f_left mr8 mb8 mt0"
										placeholder="请输入关键词查询" name="bussinessBrandKey" />
									<div
										class="f_left bt-bg-style bg-light-blue bt-small mr8 searchbrand"
										onclick="searchBussinessBrand()">搜索</div>
									<select class="input-middle f_left mr8 mb8 mt0"
										name="bussinessBrands">
									</select>
									<div
										class="f_left bt-bg-style bg-light-blue bt-small mr8 addbrand"
										onclick="addBussinessBrand()" id="addBussinessBrand">添加</div>
								</div>
								<div class="addtags addbrandtags">
									<ul id="bussinessBrand_show">
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
									<input class="input-middle f_left mr8 mt0"
										placeholder="请输入关键词查询" name="agentBrandKey" />
									<div
										class="f_left bt-bg-style bg-light-blue bt-small mr8 searchbrand"
										onclick="searchAgentBrand()">搜索</div>
									<select class="input-middle f_left mr8" name="agentBrands">
									</select>
									<div
										class="f_left bt-bg-style bg-light-blue bt-small mr8 addbrand"
										onclick="addAgentBrand()" id="addAgentBrand">添加</div>
								</div>
								<div class="addtags addbrandtags mt8">
									<ul id="agentBrand_show">
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
									class="input-smallest mr8 " name="traderCustomer.directSelling" /><span
									class="mr8">%</span> <span class="mr4">批发比例</span> <input
									type="text" class="input-smallest mr8"
									name="traderCustomer.wholesale" /><span id="wholesale">%</span>
							</div>
						</li>
						<li alt="fenxiao">
							<div class="infor_name mt0">
								<label>销售模式</label>
							</div>
							<div class="f_left inputradio" id="salesModel_div"></div>
						</li>
						<!-- <li>
							<div class="infor_name ">
								<label>注册年份</label>
							</div>
							<div class="f_left inputradio">
								<input type="text" name="traderCustomer.registeredDateStr"
									class="input-middle Wdate"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
							</div>
						</li>
						<li>
							<div class="infor_name ">
								<label>注册资金</label>
							</div>
							<div class="f_left inputradio">
								<input type="text" name="traderCustomer.registeredCapital"
									id="registeredCapital" class="input-middle"
									onkeyup="value=value.replace(/[^\-?\d.]/g,'')" /><span
									class="mt6" id="registeredCapitalSpan">万</span>
							</div>
						</li> -->
						<li>
							<div class="infor_name mt0">
								<label>员工人数</label>
							</div>
							<div class="f_left inputradio">
								<select class="input-middle f_left mr8"
									name="traderCustomer.employees">
									<option selected="selected" value="0">请选择</option>
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
									<option selected="selected" value="0">请选择</option>
								</select>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
<c:if test="${not empty tycInfo }">
<div class="parts ">
	<div class="title-container">
		<div class="table-title nobor">天眼查同步信息</div>
	</div>
	<table class="table">
		<tbody>
			<tr>
				<td>
					<div class="form-list form-tips7">
						<form method="post" action="">
							<ul>
								<%-- <li>
									<div class="form-tips">
										<lable>营业期限</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks" style="width: 400px;">
											<input class="Wdate" type="text" placeholder="请选择日期"
												onClick="WdatePicker()" style="width: 140px;" value=<date:date value="${tycInfo.fromTime}" format="yyyy-MM-dd" />>
											<div style="margin: 1px 1px 0 -8px; float: left;">-</div>
											<input class="Wdate" type="text" placeholder="请选择日期"
												onClick="WdatePicker()" style="width: 140px;" value=<date:date value="${tycInfo.toTime}" format="yyyy-MM-dd" />>
										</div>
									</div>
								</li> --%>
								<li>
									<div class="form-tips">
										<lable>注册地址</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<input type="text" class="input-largest" name="traderFinance.regAddress" value="${tycInfo.regLocation }">
										</div>
									</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>税务登记号</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<input type="text" class="input-largest" name="traderFinance.taxNum" value="${tycInfo.taxNumber }">
										</div>
									</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>组织机构代码</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<input type="text" class="input-largest" name="traderCustomer.orgNumber"  value="${tycInfo.orgNumber }">
										</div>
									</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>统一社会信用代码</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<input type="text" class="input-largest" name="traderCustomer.creditCode" value="${tycInfo.creditCode }">
										</div>
									</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>注册日期</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<input class="Wdate" type="text" placeholder="请选择日期"
												onClick="WdatePicker()" style="width: 140px;" name="traderCustomer.registeredDateStr" value=<date:date value="${tycInfo.estiblishTime}" format="yyyy-MM-dd" />>
										</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>注册资金</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<input type="text" class="input-middle" name="traderCustomer.registeredCapital" value="${tycInfo.regCapital }"> <!-- <span>万</span> -->
										</div>
									</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>经营范围</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<textarea class="input-largest"
												rows="6"  name="traderCustomer.businessScope">${tycInfo.businessScope }</textarea>
										</div>
									</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>历史名称</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<span name="traderCustomer.businessScope">
											<c:if test="${not empty tycInfo.historyNames }">
											${tycInfo.historyNames }
											</c:if>
											<c:if test="${ empty tycInfo.historyNames }">
											--
											</c:if>
											</span>
										</div>
									</div>
								</li>
								<li>
									<div class="form-tips">
										<lable>天眼查更新时间</lable>
									</div>
									<div class="f_left">
										<div class="form-blanks">
											<span><date:date value ="${tycInfo.updateTime}"/></span>
										</div>
									</div>
								</li>
							</ul>
						</form>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>
</c:if>
		<div class="baseinforcontainer mt10 pb20">
			<div class="border overflow-hidden">
				<div class="baseinfor f_left">管理信息</div>
			</div>
			<div class="baseinforeditform ">
				<ul>
					<li class="bt">
						<div class="infor_name mt0">
							<span>*</span>
							<lable>客户来源</lable>
						</div>
						<div class="f_left inputradio" id="customerFrom_div"></div>
					</li>
					<li class="bt">
						<div class="infor_name mt0">
							<span>*</span>
							<lable>首次询价方式</lable>
						</div>
						<div class="f_left inputradio" id="firstEnquiryType"></div>
					</li>
					<li>
						<div class="infor_name mt0">
							<lable>战略合作伙伴</lable>
						</div>
						<div class="f_left inputradio">
							<ul id="zlhzhb_ul">
							</ul>
						</div>
					</li>
					<li>
						<div class="infor_name mt0">
							<lable>贝登VIP</lable>
						</div>
						<div class="f_left inputfloat">
							<input type="radio" name="traderCustomer.isVip" value="1" /> <label
								class="mr8">是</label> <input type="radio"
								name="traderCustomer.isVip" value="0" checked="checked" /> <label
								class="mr8">否</label>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="isLcfx" value="0">
		    <input type="hidden" name="traderCustomerCategoryIds" id="traderCustomerCategoryIds"> 
		    <input type="hidden" name="traderCustomer.traderCustomerCategoryId" id="traderCustomerCategoryId" value="0">
			<button type="submit">提交</button>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/customer/add_customer.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>
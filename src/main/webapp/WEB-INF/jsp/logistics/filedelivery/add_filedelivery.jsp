<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增寄送文件" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class="formpublic">
		<form method="post" id="addFileDeliveryForm" action="${pageContext.request.contextPath}/logistics/filedelivery/saveFileDelivery.do">
			<ul>
				<li>
					<div class="infor_name mt0">
					    <span>*</span>
						<label>发放形式</label>
					</div>
					<div class="f_left inputfloat">
						<ul>
							<c:forEach var="sendType" items="${sendTypeList}" >
								<li>
									<input type="radio" name="sendType" value="${sendType.sysOptionDefinitionId}" <c:if test="${sendType.sysOptionDefinitionId eq 489}">checked</c:if>> <label class="mr4">${sendType.title}</label>
								</li>
						    </c:forEach>
						</ul>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
					    <span>*</span>
						<label>客户类型</label>
					</div>
					<div class="f_left inputfloat">
						<ul>
							<c:choose>
								<c:when test="${positType == 1}">
									<li>
										<input type="radio" name="traderTyped" value="1" disabled checked> <label class="mr4">销售客户</label>
									</li>
										<input type="hidden" name="traderType" value="1" >
									<li>
										<input type="radio" name="traderTyped" value="2" disabled> <label class="mr4">供应商</label>
									</li>
								</c:when>
								<c:when test="${positType == 2}">
									<li>
										<input type="radio" name="traderTyped" value="1" disabled> <label class="mr4">销售客户</label>
									</li>
										<input type="hidden" name="traderType" value="2" >
									<li>
										<input type="radio" name="traderTyped" value="2" checked disabled> <label class="mr4">供应商</label>
									</li>
								</c:when>
								<c:otherwise>
									<li>
										<input type="radio" name="traderType" value="1" checked> <label class="mr4">销售客户</label>
									</li>
								
									<li>
										<input type="radio" name="traderType" value="2"> <label class="mr4">供应商</label>
									</li>
								</c:otherwise>
							</c:choose>
								
						</ul>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
					    <span>*</span>
						<label>寄送文件类型</label>
					</div>
					<div class="f_left inputfloat">
						<ul>
							<li>
							   <input type="radio" name="delievryType" value="1" onclick="changeDelievryType(this)" > <label class="mr4">商品样册</label>
							</li>
							<li>
								<input type="radio" name="delievryType" value="2" onclick="changeDelievryType(this)"> <label class="mr4">非样册文件</label>
								<div id="delievryTypeWarn"></div>
							</li>
						</ul>
					</div>
				</li>
				<li class="single_assign" id="traderNameLi">
					<div class="infor_name">
						<span>*</span>
						<label>客户名称</label>
					</div>
					<div class="f_left inputfloat">
						<ul>
							<li><input type="text" class="input-middle" name="searchTraderName" value="" id="searchTraderName"> 
							<span class="ml4 bt-bg-style bg-light-blue bt-small" onclick="search(${positType});">搜索</span>
							<div style="display:none;">
								<!-- 弹框 -->
								<div class="title-click nobor  pop-new-data" id="terminalDiv"></div>
							</div>
							<div id="traderNameWarn"></div>
							</li>
						</ul>
					</div>
				</li>
				<div id="showTraderInfo" style="display:none">
						<li class="single_assign">
							<div class="infor_name">
								<span>*</span>
								<label>客户名称</label>
							</div>
							<div class="f_left">
								<span class="mr8" id="TraderNameDiv"></span>
		                        <label class="bt-bg-style bg-light-blue bt-small" onclick="agingSearchTrader();">重新搜索</label>
							</div>
						</li>
						<li class="single_assign" >
							<div class="infor_name">
								<span>*</span>
								<label>联系人</label>
							</div>
							<div class="f_left">
								<select name="traderContactId" id="traderContactId" onchange="changeContact(this)">
								</select>
								<div id="traderContactWarn" style="margin-left: 110px;"></div>
							</div>
						</li>
						<li class="single_assign" >
							<div class="infor_name">
								<span>*</span>
								<label>地址</label>
							</div>
							<div class="f_left">
								<select name="traderAddressId" id="traderAddressId" onchange="changeAddress(this)">
								</select>
								<div id="traderAddressWarn" style="margin-left: 110px;"></div>
							</div>
						</li>
						<input type="hidden" name="traderId" id="traderId"/>
						<input type="hidden" name="traderName" id="traderName"/>
						<input type="hidden" name="traderContactName" id="traderContactName"/>
						<input type="hidden" name="mobile" id="mobile"/>
						<input type="hidden" name="telephone" id="telephone"/>
						<input type="hidden" name="area" id="area"/>
						<input type="hidden" name="address" id="address"/>
				</div>
				<input type="hidden" name="formToken" value="${formToken}"/>
		          <li>
                      <div class="infor_name">
                          <span>*</span>
                          <label>寄送内容</label>
                      </div>
                      <div class="f_left">
                      	<div>
                          <textarea class="askprice" name="content" id="content" placeholder="请填写需要寄送的文件、数量以及要求 ">${bussinessChanceVo.content}</textarea>
							<div id="content1" class="font-red " style="display: none">询价产品不允许为空</div>
							<div id="content2" class="font-red " style="display: none">询价产品内容不能大于512字符</div>
                      	</div>
                      </div>
                  </li>
				<li>
					<div class="infor_name mt0">
					    <span>*</span>
						<label>承运部门</label>
					</div>
					<div class="f_left inputfloat">
						<ul>
							<li>
							    <input type="radio" name="deliveryDept" id="cw" value="1"  > <label class="mr4">财务部门</label>
							</li>
							<li>
								<input type="radio" name="deliveryDept" id="wl" value="2" > <label class="mr4">物流部门</label>
								<div id="deliveryDeptWarn"></div>
							</li>
						</ul>
					</div>
				</li>
			</ul>
			<div id="InputTerminalInfo" class="formpublic formpublic1" >
			 <div class="add-tijiao tcenter">
				<button type="submit" class="bt-bg-style bg-deep-green" id="sub">提交</button>
			</div>
			</div>
		</form>
		
		
	</div>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/fileDelivery/add_filedelivery.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增商机" scope="application" />
<%@ include file="./common/header.jsp"%>
<div class="layer-content call-layer-content">
	<!-- 标题 -->
	<div class="callcenter-title">
		<div class="left-title">
			<i class="iconcalltele"></i>
			<span>新增商机</span>
		</div>
		<div class="right-title">
			<i class="iconclosetitle" id="close-title"></i>
		</div>
	</div>
	<form method="post" action="" id="myform">
		<!-- 表格信息 -->
		<div class="content-box">
			<div class="content-colum content-colum2">
				<div class="content-item call-add-linker">
					<div class="title">请填写商机信息</div>
					<table class="table table-td-border1 ">
						<tbody>
							<tr>
								<td class="wid10"><span class="font-red">*</span>商机时间</td>
								<td><input class="Wdate input-large" name="time"
									id="receiveTime" type="text" placeholder="请选择日期"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									value='<date:date value ="${bussinessChanceVo.receiveTime} " format="yyyy-MM-dd HH:mm:ss"/>'></td>
							</tr>
							<tr>
								<td class="wid10"><span class="font-red">*</span>商机来源</td>
								<td>
									<div class="inputfloat">
										<ul id="sourceUl">
											<c:if test="${not empty scoureList }">
												<c:forEach items="${scoureList}" var="sl">
													<li><input type="radio" name="source" 
													<c:if test="${sl.sysOptionDefinitionId eq 375}">
													checked="checked"
													</c:if>
														value="${sl.sysOptionDefinitionId}"><label>${sl.title}</label>
													</li>
												</c:forEach>
											</c:if>
										</ul>
									</div>
								</td>
							</tr>
							<tr>
								<td class="wid10"><span class="font-red">*</span>询价方式</td>
								<td>
									<div class="inputfloat">
										<ul>
											<c:if test="${not empty inquiryList }">
												<c:forEach items="${inquiryList}" var="il">
													<c:if test="${il.sysOptionDefinitionId == 377 }">
														<li><input type="radio" name="communication"
															value="${il.sysOptionDefinitionId}" checked="checked"><label>${il.title}</label>
														</li>
													</c:if>
												</c:forEach>
											</c:if>
										</ul>
									</div>
								</td>
							</tr>
							<tr>
								<td class="wid10"><span class="font-red">*</span>询价产品</td>
								<td><textarea class="askprice" name="content" id="content"
										placeholder="询价产品录入格式：品牌+名称+型号
如：奥林巴斯显微镜CX23
1、如果是多个产品询价，每个产品请换行输入
2、如果是综合询价，尽量输入完整，如果询价产品达到10个以上，
可选择主要产入录，同时在最后备注：综合询价 四个字
3、关于综合询价的定义:单次询问不同类型的产品5个以上 "></textarea>
								</td>
							</tr>
							<tr>
								<td class="wid10"><span class="font-red">*</span>产品分类</td>
								<td><select name="goodsCategory" id="goodsCategory">
										<option value="">请选择</option>
										<c:if test="${not empty goodsTypeList }">
											<c:forEach items="${goodsTypeList}" var="gyl">
												<option value="${gyl.sysOptionDefinitionId}">${gyl.title}</option>
											</c:forEach>
										</c:if>
								</select></td>
							</tr>
							<tr>
								<td class="wid10">产品品牌</td>
								<td><input type="text" class="wid60" name="goodsBrand"
									id="goodsBrand" placeholder="请输入客户咨询的第一个产品的品牌" value="">
								</td>
							</tr>
							<tr>
								<td class="wid10">产品名称</td>
								<td><input type="text" class="wid60" name="goodsName"
									id="goodsName" value="" placeholder="请输入客户咨询的第一个产品的名称">
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="content-item call-add-linker">
					<div class="title">请填写客户信息</div>
					<table class="table table-td-border1 ">
						<tbody>
							<tr>
								<td class="wid10">客户名称</td>
								<td><input type="text" class="input-large"
									value="${traderCustomer.name}" name="traderName"
									id="traderName"></td>
							</tr>
							<tr>
								<td class="wid10">客户地区</td>
								<td><select name="province" id="province">
										<option value="0">请选择</option>
										<c:if test="${not empty provinceList }">
											<c:forEach items="${provinceList }" var="prov">
												<option value="${prov.regionId }"
													<c:if test="${province eq prov.regionId }">selected="selected"</c:if>>${prov.regionName }</option>
											</c:forEach>
										</c:if>
								</select> <select name="city" id="city">
										<option value="0">请选择</option>
										<c:if test="${not empty cityList }">
											<c:forEach items="${cityList }" var="ci">
												<option value="${ci.regionId }"
													<c:if test="${city eq ci.regionId }">selected="selected"</c:if>>${ci.regionName }</option>
											</c:forEach>
										</c:if>
								</select> <select name="zone" id="zone">
										<option value="0">请选择</option>
										<c:if test="${not empty zoneList }">
											<c:forEach items="${zoneList }" var="zo">
												<option value="${zo.regionId }"
													<c:if test="${zone eq zo.regionId }">selected="selected"</c:if>>${zo.regionName }</option>
											</c:forEach>
										</c:if>
								</select></td>
							</tr>
							<tr>
								<td class="wid10">联系人</td>
								<td><input type="text" class="input-middle" value=""
									name="traderContactName" id="traderContactName"></td>
							</tr>
							<tr>
								<td class="wid10">手机号</td>
								<td><input type="text" class="input-middle"
									value="${mobile}" name="mobile" id="mobile"></td>
							</tr>
							<tr>
								<td class="wid10">电话</td>
								<td><input type="text" class="input-middle" value="${tel}"
									name="telephone" id="telephone"></td>
							</tr>
							<tr>
								<td class="wid10">其他联系方式</td>
								<td><input type="text" class="input-middle" value=""
									name="otherContact" id="otherContact"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="content-item call-add-linker">
					<div class="title">请分配商机</div>
					<table class="table table-td-border1 ">
						<tbody>
							<tr>
								<td class="wid10"><span class="font-red">*</span>分配销售</td>
								<td><select name="userId" id="userId">
										<option value="">请选择</option>
										<c:if test="${not empty userList }">
											<c:forEach items="${userList}" var="user">
												<option value="${user.userId}">${user.username}</option>
											</c:forEach>
										</c:if>
								</select></td>
							</tr>
							<tr>
								<td class="wid10">备注</td>
								<td><input type="text" class="wid60" name="comments"
									id="comments"></td>
							</tr>

							<tr>
								<td colspan="2">
									<button class="bt-bg-style bt-small bg-light-green"
										type="submit" id="submit">提交</button>
									<button class="bt-bg-style bt-small bg-light-red"
										id="close-layer" type="button">取消</button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/common.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/call/add_bussinesschance.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="./common/footer.jsp"%>

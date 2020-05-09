<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="商机信息" scope="application" />
<%@ include file="./common/header.jsp"%>
<div class="layer-content call-layer-content">
	<!-- 标题 -->
	<div class="callcenter-title">
		<div class="left-title">
			<i class="iconcalltele"></i> <span>商机信息</span>
		</div>
		<div class="right-title">
			<i class="iconclosetitle" id="close-title"></i>
		</div>
	</div>
	<!-- 表格信息 -->
	<div class="content-box">
		<c:choose>
			<c:when test="${empty bussinessChance }">
				<div class="content-colum content-colum2">
					<div class="content-item call-add-linker">
						<div class="title">商机信息</div>
						<table class="table table-td-border1 ">
							<tbody>
								<tr>
									<td colspan="2">暂无商机</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div class="content-colum content-colum2">
					<div class="content-item call-add-linker">
						<div class="title">商机信息</div>
						<table class="table table-td-border1 ">
							<tbody>
								<tr>
									<td class="wid10">商机时间</td>
									<td><date:date value="${bussinessChance.addTime}" /></td>
								</tr>
								<tr>
									<td class="wid10">商机来源</td>
									<td>${bussinessChance.sourceName}</td>
								</tr>
								<tr>
									<td class="wid10">询价方式</td>
									<td>${bussinessChance.communicationName}</td>
								</tr>
								<tr>
									<td class="wid10">询价产品</td>
									<td>${bussinessChance.content}</td>
								</tr>
								<tr>
									<td class="wid10">产品分类</td>
									<td>${bussinessChance.goodsCategoryName}</td>
								</tr>
								<tr>
									<td class="wid10">产品品牌</td>
									<td>${bussinessChance.goodsBrand}</td>
								</tr>
								<tr>
									<td class="wid10">产品名称</td>
									<td>${bussinessChance.goodsName}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="content-item call-add-linker">
						<div class="title">客户信息</div>
						<table class="table table-td-border1 ">
							<tbody>
								<tr>
									<td class="wid10">客户名称</td>
									<td>${bussinessChance.traderName}</td>
								</tr>
								<tr>
									<td class="wid10">客户地区</td>
									<td>${bussinessChance.areas}</td>
								</tr>
								<tr>
									<td class="wid10">联系人</td>
									<td>${bussinessChance.traderContactName}</td>
								</tr>
								<tr>
									<td class="wid10">手机号</td>
									<td>${bussinessChance.mobile}</td>
								</tr>
								<tr>
									<td class="wid10">电话</td>
									<td>${bussinessChance.telephone}</td>
								</tr>
								<tr>
									<td class="wid10">其他联系方式</td>
									<td>${bussinessChance.otherContact}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="content-item call-add-linker">
						<div class="title">分配商机</div>
						<table class="table table-td-border1 ">
							<tbody>
								<tr>
									<td class="wid10">分配销售</td>
									<td>${bussinessChance.creatorName}</td>
								</tr>
								<tr>
									<td class="wid10">备注</td>
									<td>${bussinessChance.comments}</td>
								</tr>

								<tr>
									<td colspan="2">
										<button class="bt-bg-style bt-small bg-light-red"
											id="close-layer" type="button">取消</button>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="clear"></div>
	</div>
</div>
<%@ include file="./common/footer.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="查看保卡" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container">
	<div class="parts">
		<div class="title-container">
			<div class="table-title nobor">保卡信息</div>
			<div class="title-click nobor" onclick="goUrl('${pageContext.request.contextPath}/order/saleorder/editgoodswarranty.do?saleorderGoodsWarrantyId=${goodsWarrantyInfo.saleorderGoodsWarrantyId}');">编辑</div>
		</div>
		<table class="table">
			<tbody>
				<tr>
					<td class="wid20">创建者</td>
					<td class="text-left">${goodsWarrantyInfo.createName }</td>
				</tr>
				<tr>
					<td>录保卡时间</td>
					<td class="text-left"><date:date
							value="${goodsWarrantyInfo.addTime}" /></td>
				</tr>
				<tr>
					<td>销售单号</td>
					<td class="text-left">${goodsWarrantyInfo.saleorderNo}</td>
				</tr>
				<tr>
					<td>服务单号</td>
					<td class="text-left">${goodsWarrantyInfo.serviceNo}</td>
				</tr>
				<tr>
					<td>订货号</td>
					<td class="text-left">${goodsWarrantyInfo.sku}</td>
				</tr>
				<tr>
					<td>产品名称</td>
					<td class="text-left">${goodsWarrantyInfo.goodsName}</td>
				</tr>
				<tr>
					<td>品牌</td>
					<td class="text-left">${goodsWarrantyInfo.brandName}</td>
				</tr>
				<tr>
					<td>型号</td>
					<td class="text-left">${goodsWarrantyInfo.model}</td>
				</tr>
				<tr>
					<td>厂商条码/设备系列号（SN）</td>
					<td class="text-left">${goodsWarrantyInfo.goodsSn}</td>
				</tr>
				<tr>
					<td>重要配件系列号</td>
					<td class="text-left">${goodsWarrantyInfo.importantPartsSn}</td>
				</tr>
				<tr>
					<td>终端名称</td>
					<td class="text-left">${goodsWarrantyInfo.terminalName}</td>
				</tr>
				<tr>
					<td>终端地区</td>
					<td class="text-left">${goodsWarrantyInfo.area}</td>
				</tr>
				<tr>
					<td>终端地址</td>
					<td class="text-left">${goodsWarrantyInfo.address}</td>
				</tr>
				<tr>
					<td>使用科室</td>
					<td class="text-left">${goodsWarrantyInfo.usedDepartment}</td>
				</tr>
				<tr>
					<td>负责人</td>
					<td class="text-left">${goodsWarrantyInfo.personLiable}</td>
				</tr>
				<tr>
					<td>联系电话</td>
					<td class="text-left">${goodsWarrantyInfo.phone}</td>
				</tr>
				<tr>
					<td>安装验收日期</td>
					<td class="text-left"><fmt:formatDate type="date"  value="${goodsWarrantyInfo.checkTime}" /></td>
				</tr>
				<tr>
					<td>设备科负责人</td>
					<td class="text-left">${goodsWarrantyInfo.personLiableEquipment}</td>
				</tr>
				<tr>
					<td>联系电话</td>
					<td class="text-left">${goodsWarrantyInfo.equipmentPhone}</td>
				</tr>
				<tr>
					<td>装机机构</td>
					<td class="text-left">${goodsWarrantyInfo.installAgency}</td>
				</tr>
				<tr>
					<td>验收人</td>
					<td class="text-left">${goodsWarrantyInfo.checkPerson}</td>
				</tr>
				<tr>
					<td>日标本量</td>
					<td class="text-left">${goodsWarrantyInfo.dailyVolume}</td>
				</tr>
				<tr>
					<td>备注</td>
					<td class="text-left">${goodsWarrantyInfo.comments}</td>
				</tr>
				<tr>
					<td>录保卡图片</td>
					<td class="text-left">
					<c:if test="${not empty goodsWarrantyInfo.uri and not empty goodsWarrantyInfo.domain}">
						<span class="edit-user addtitle"
							tabTitle='{"num":"viewwarrantyimg${goodsWarrantyInfo.saleorderGoodsWarrantyId }","link":"http://${goodsWarrantyInfo.domain}${goodsWarrantyInfo.uri}","title":"查看保卡图片"}'>查看保卡图片</span>
					</c:if>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<%@ include file="../../common/footer.jsp"%>
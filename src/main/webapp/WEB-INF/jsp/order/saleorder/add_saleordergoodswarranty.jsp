<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增保卡" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips9">
	<form method="post" id="myform" action="${pageContext.request.contextPath}/order/saleorder/saveaddgoodswarranty.do">
		<ul>
			<li>
				<div class="form-tips">
					<lable>产品名称</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						${goodsWarrantyInfo.goodsName }
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>订货号</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						${goodsWarrantyInfo.sku }
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>品牌</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						${goodsWarrantyInfo.brandName }
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>设备型号</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						${goodsWarrantyInfo.model }
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>销售单号</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						${goodsWarrantyInfo.saleorderNo }
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>经销商名称</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<c:choose>
							<c:when test="${goodsWarrantyInfo.customerNature eq 466 }">
								<input type="hidden" name="distributorName" value="${user.companyName }" />
								${user.companyName }
							</c:when>
							<c:otherwise>
								${goodsWarrantyInfo.traderName }
								<input type="hidden" name="distributorName" value="${goodsWarrantyInfo.traderName }" />
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>服务单号</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large" name="serviceNo" id="serviceNo" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>设备系列号（SN）</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large" name="goodsSn" id="goodsSn" value="${goodsWarrantyInfo.barcodeFactory }"/>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>重要配件系列号</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large" name="importantPartsSn" id="importantPartsSn" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>终端名称</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<c:choose>
							<c:when test="${goodsWarrantyInfo.customerNature eq 466 }">
							${goodsWarrantyInfo.traderName }
							<input type="hidden" class=" input-large" name="terminalName" id="terminalName" value="${goodsWarrantyInfo.traderName }"/>
							</c:when>
							<c:otherwise>
							<input type="text" class=" input-large" name="terminalName" id="terminalName" />
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>终端地区</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<select class="input-middle mr6" name="province">
							<option value="0">请选择</option>
							<c:if test="${not empty provinceList }">
								<c:forEach items="${provinceList }" var="province">
									<option value="${province.regionId }"
										<c:if test="${ not empty provinceRegion &&  province.regionId == provinceRegion.regionId }">selected="selected"</c:if>>${province.regionName }</option>
								</c:forEach>
							</c:if>
						</select> <select class="input-middle mr6" name="city">
							<option value="0">请选择</option>
							<c:if test="${not empty cityList }">
								<c:forEach items="${cityList }" var="city">
									<option value="${city.regionId }"
										<c:if test="${ not empty cityRegion &&  city.regionId == cityRegion.regionId }">selected="selected"</c:if>>${city.regionName }</option>
								</c:forEach>
							</c:if>
						</select> <select class="input-middle mr6" name="zone" id="zone">
							<option value="0">请选择</option>
							<c:if test="${not empty zoneList }">
								<c:forEach items="${zoneList }" var="zone">
									<option value="${zone.regionId }"
										<c:if test="${ not empty zoneRegion &&  zone.regionId == zoneRegion.regionId }">selected="selected"</c:if>>${zone.regionName }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>终端地址</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-largest " name="address"
							id="address" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>邮政编码</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large " name="zipCode"
							id="zipCode" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>使用科室</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large " name="usedDepartment"
							id="usedDepartment" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>负责人</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large " name="personLiable"
							id="personLiable" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>联系电话</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large " name="phone"
							id="phone" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>设备科负责人</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large " name="personLiableEquipment"
							id="personLiableEquipment" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>联系电话</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large " name="equipmentPhone"
							id="equipmentPhone" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>装机机构</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large " name="installAgency"
							id="installAgency" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>装机人员</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large " name="installPerson"
							id="installPerson" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>安装验收日期</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<jsp:useBean id="now" class="java.util.Date" /> 
						<input class="Wdate input-large" type="text"
							placeholder="请选择日期"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							name="checkTime" id="checkTime" value="<fmt:formatDate value="${now }" pattern="yyyy-MM-dd"/>">
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>验收人</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large " name="checkPerson"
							id="checkPerson" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>日标本量</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<input type="text" class=" input-large " name="dailyVolume"
							id="dailyVolume" />
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>备注</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<textarea rows="" cols="98" name="comments" id="comments"></textarea>
					</div>
				</div>
			</li>
			<li>
                 <div class="form-tips">
                 	 <span>*</span>
                     <lable>录保卡图片</lable>
                 </div>
                 <input type="hidden" id="domain" name="domain" value="${domain}">
                 <div class="f_left ">
                     <div class="form-blanks">
                     	<div class="f_left">
                          <input type="file" class="upload_file" id='file_1' name="lwfile" style="display: none;" onchange="uploadFile(this,1);">
                          <input type="text" class="input-largest" id="name_1" readonly="readonly"
                          	placeholder="请上传附件" name="fileName" onclick="file_1.click();" > 
                          <input type="hidden" id="uri_1" name="fileUri" >
    					</div>
                         <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="file_1.click();">浏览</label>
                         <!-- 上传成功出现 -->
                         <i class="iconsuccesss mt3 none" id="img_icon_1"></i>
                 		<a href="" target="_blank" class="font-blue cursor-pointer  mt3 none" id="img_view_1">查看</a>
                  		<span class="font-red cursor-pointer  mt3 none" onclick="del(1)" id="img_del_1">删除</span>
                     </div>
                     <div class="pop-friend-tips mt6">
                       	  友情提示：
                         <br/> 1、设备系列号即厂商条码； 
                     </div>
					<div class="add-tijiao text-left">
						<input type="hidden" name="formToken" value="${formToken}"/>
						<input type="hidden" name="saleorderGoodsId" value="${goodsWarrantyInfo.saleorderGoodsId }">
						<input type="hidden" name="saleorderId" value="${goodsWarrantyInfo.saleorderId }">
						<input type="hidden" name="warehouseGoodsOperateLogId" value="${goodsWarrantyInfo.warehouseGoodsOperateLogId }">
						<button type="submit">提交</button>
					</div>
                 </div>
             </li>
		</ul>
	</form>
</div>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/order/saleorder/add_saleordergoodswarranty.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>
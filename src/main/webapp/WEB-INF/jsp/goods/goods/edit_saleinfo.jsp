<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑销售信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src='<%= basePath %>static/js/goods/goods/editsaleinfo.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
	<div class="customer">
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/goods/goods/viewbaseinfo.do?goodsId=${goods.goodsId}">产品信息</a>
            </li>
            <li>
                <a class="customer-color" href="${pageContext.request.contextPath}/goods/goods/viewsaleinfo.do?goodsId=${goods.goodsId}">销售信息</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/goods/goods/viewpurchaseinfo.do?goodsId=${goods.goodsId}">采购信息</a>
            </li>
            <li>
                <a href="javascript:void(0)">运营信息</a>
            </li>
        </ul>
    </div>
    <div class="formpublic " style="margin-top:-10px;">
        <form action="${pageContext.request.contextPath}/goods/goods/savesaleinfo.do" method="post" id="saveSaleInfoForm">
            <ul>
                <li>
                    <div class="infor_name mt0">
                        <span>*</span>
                        <label>产品级别</label>
                    </div>
                    <div class="f_left inputfloat">
                        <ul id="goods_level_div">
                        </ul>
                        <input type="hidden" id="goodsLevelId" value="${goods.goodsLevel}" />
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>旧国标分类</label>
                    </div>
                    <div class="f_left management-types">
                        <ul class="f_left">
                            <li>
                                <select name="manageCategory" id="manageCategory"  onchange="managerCategoryLevel(this.value)">
                                    <option value="0">请选择管理类别</option>
                                </select>
                            </li>
                        </ul>
                        <div class="f_left font-grey9 mt4">
                                （若不是医疗器械，请选择非医疗器械）
                        </div>
                        <input type="hidden" id="manageCategoryId" value="${goods.manageCategory}" />
                        <input type="hidden" id="manageCategoryLevelId" value="${goods.manageCategoryLevel}" />
                    </div>
                </li>
                <li>
                    <div class="infor_name mt0">
                        <label>应用科室</label>
                    </div>
                    <div class="f_left inputfloat">
                        <ul id="used_department_div">
                        </ul>
                        <input type="hidden" value="${goodsSysOptionAttributeIds}" id="goodsSysOptionAttributeIds">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>采购提醒</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-xxx" placeholder="指需要销售注意事项，如离心机配不同的转子；需要报单等一切需要让销售注意的事项" name="purchaseRemind" id="purchaseRemind" value="${goods.purchaseRemind}">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>批准文号</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-larger" name="licenseNumber" id="licenseNumber" value="${goods.licenseNumber}">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>注册证号</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-larger" name="registrationNumber" id="registrationNumber" value="${goods.registrationNumber}">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>注册证</label>
                    </div>
                    <div class="f_left">
                    	<c:set var="uri_344" value=""></c:set>
	                    <c:set var="domain_344" value=""></c:set>
                    	<c:forEach items="${attachmentList}" var="list" varStatus="status">
	                    	<c:if test="${list.attachmentType == 344}">
			                    <c:set var="uri_344" value="${list.uri}"></c:set>
			                    <c:set var="domain_344" value="${list.domain}"></c:set>
                    		</c:if>
                    	</c:forEach>
                    	
                        <input type="file" class="upload_file" style="display: none;" name="lwfile" id="lwfile_344" onchange="uploadFile(this, 344)">
                        <input type="text" class="input-middle" id="uri_344" placeholder="请上传注册证" name="uri_344" value="${uri_344}" onclick="lwfile_344.click();">
                        <label class="bt-bg-style bt-middle bg-light-blue ml4" type="file" onclick="lwfile_344.click();">浏览</label>
                        <input type="hidden" id="domain_344" name="domain_344" value="${domain_344}">
                        
                        <c:choose>
							<c:when test="${domain_344 != ''}">
								<i class="iconsuccesss mt5" id="img_icon_344"></i>
		                    	<a href="http://${domain_344}${uri_344}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_344">查看</a>
		                    	<span class="font-red cursor-pointer mt4" onclick="delProductImg(344)" id="img_del_344">删除</span>
							</c:when>
							<c:otherwise>
								<i class="iconsuccesss mt5 none" id="img_icon_344"></i>
	                    		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_344">查看</a>
		                    	<span class="font-red cursor-pointer mt4 none" onclick="delProductImg(344)" id="img_del_344">删除</span>
							</c:otherwise>
						</c:choose>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>注册证有效期</label>
                    </div>
                    <div class="f_left">
                        <input class="Wdate input-smaller mr4" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtimeStr\')}'})" id="begintimeStr" name="begintimeStr" value='<date:date value ="${goods.begintime}" format="yyyy-MM-dd"/>' />
                        <input class="Wdate input-smaller" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'begintimeStr\')}'})" id="endtimeStr" name="endtimeStr" value='<date:date value ="${goods.endtime}" format="yyyy-MM-dd"/>' />
                    	<span id="register_span"></span>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>注册标准</label>
                    </div>
                    <div class="f_left">
                    	<c:set var="uri_345" value=""></c:set>
	                    <c:set var="domain_345" value=""></c:set>
                    	<c:forEach items="${attachmentList}" var="list" varStatus="status">
	                    	<c:if test="${list.attachmentType == 345}">
			                    <c:set var="uri_345" value="${list.uri}"></c:set>
			                    <c:set var="domain_345" value="${list.domain}"></c:set>
                    		</c:if>
                    	</c:forEach>
                    	
                        <input type="file" class="upload_file" style="display: none;" name="lwfile" id="lwfile_345" onchange="uploadFile(this, 345)">
                        <input type="text" class="input-middle" id="uri_345" placeholder="请上传注册标准" name="uri_345" value="${uri_345}" onclick="lwfile_345.click();">
                        <label class="bt-bg-style bt-middle bg-light-blue ml4" type="file" onclick="lwfile_345.click();">浏览</label>
                        <input type="hidden" id="domain_345" name="domain_345" value="${domain_345}">
                        
                        <c:choose>
							<c:when test="${domain_345 != ''}">
								<i class="iconsuccesss mt5" id="img_icon_345"></i>
		                    	<a href="http://${domain_345}${uri_345}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_345">查看</a>
		                    	<span class="font-red cursor-pointer mt4" onclick="delProductImg(345)" id="img_del_345">删除</span>
							</c:when>
							<c:otherwise>
								<i class="iconsuccesss mt5 none" id="img_icon_345"></i>
	                    		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_345">查看</a>
		                    	<span class="font-red cursor-pointer mt4 none" onclick="delProductImg(345)" id="img_del_345">删除</span>
							</c:otherwise>
						</c:choose>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>授权证书</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-xxx" placeholder="请填写链接地址" name="authorizationCertificateUrl" id="authorizationCertificateUrl" value="${goods.authorizationCertificateUrl}">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>其他资质</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-xxx" placeholder="请填写链接地址" name="otherQualificationUrl" id="otherQualificationUrl" value="${goods.otherQualificationUrl}">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>彩页地址</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-xxx" placeholder="请填写链接地址" name="colorPageUrl" id="colorPageUrl" value="${goods.colorPageUrl}">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>技术参数地址</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-xxx" placeholder="请填写链接地址" name="technicalParameterUrl" id="technicalParameterUrl" value="${goods.technicalParameterUrl}">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>产品说明书地址</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-xxx" placeholder="请填写链接地址" name="instructionsUrl" id="instructionsUrl" value="${goods.instructionsUrl}">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>陪标资料地址</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-xxx" placeholder="请填写链接地址" name="biddingDataUrl" id="biddingDataUrl" value="${goods.biddingDataUrl}">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>包装清单</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-xxx" name="packingList" id="packingList" value="${goods.packingList}">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>服务条款</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-xxx" name="tos" id="tos" value="${goods.tos}">
                    </div>
                </li>
            </ul>
            <div class="add-tijiao tcenter mb5">
            	<input type="hidden" name="goodsId" value="${goods.goodsId}">
            	<input type="hidden" name="beforeParams" value='${beforeParams}'>
                <button type="submit" id='submit'>提交</button>
                <button class="dele" id="close-layer" type="button" onclick="goUrl('${pageContext.request.contextPath}/goods/goods/viewsaleinfo.do?goodsId=${goods.goodsId}')">取消</button>
            </div>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增供应商" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<form method="post"
	action="${pageContext.request.contextPath}/trader/supplier/saveadd.do"
	class="mt10" id="supplierForm">
	<div class="baseinforcontainer" style="padding-bottom: 15px;">
		<div class="border overflow-hidden">
			<div class="baseinfor f_left">基本信息</div>
		</div>
		<div class="baseinforeditform">
			<ul>
				<li>
					<div class="infor_name">
						<span>*</span>
						<lable>供应商名称</lable>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest errobor" name="traderName"
							id="traderName" oninput="checkTraderName();"/>
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
				<li>
	                <div class="form-tips">
	                    <span>*</span>
	                    <lable>热线电话</lable>
	                </div>
	                <div class="f_left">
	                    <div class="form-blanks">
	                        <input type="text" name='hotTelephone' id='hotTelephone' class="input-large" />
	                    </div>
	                    <div id="hotTelephoneError"></div>
	                </div>
	            </li>
	            <li>
	                <div class="form-tips">
	                    <span>*</span>
	                    <lable>售后电话</lable>
	                </div>
	                <div class="f_left">
	                    <div class="form-blanks">
	                        <input type="text" name='serviceTelephone' id='serviceTelephone' class="input-large" />
	                    </div>
	                    <div id="serviceTelephoneError"></div>
	                </div>
	            </li>
				<li>
					<div class="infor_name ">
						<label>供应品牌</label>
					</div>
					<div class="f_left inputradio">
						<div class="career-one">
							<input class="input-middle f_left mb4 mt0" placeholder="请输入关键词查询"
								name="bussinessBrandKey" />
							<div
								class="f_left bt-bg-style bg-light-blue bt-small  searchbrand"
								onclick="searchBussinessBrand()">搜索</div>
							<select class="input-middle f_left mr10 mt0"
								name="bussinessBrands">
							</select>
							<div class="f_left bt-bg-style bg-light-blue bt-small addbrand"
								onclick="addBussinessBrand()" id="addBussinessBrand">添加</div>
						</div>
						<div class="addtags addbrandtags mt4">
							<ul id="bussinessBrand_show">
							</ul>
						</div>
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>供应商品</lable>
					</div>
					<div class="f_left ">
						<input type="text" class="input-xxx"
							name="traderSupplier.supplyProduct" id="supplyProduct"
							placeholder="不同关键词之间以逗号分隔" />
					</div>
				</li>
				<li>
					<div class="infor_name mt4">
						<lable>供应商标签</lable>
					</div>
					<div class="f_left table-largest">
						<div class="inputfloat manageaddtag">
							<label class="mt4 mr8">您可以从这些标签中选择</label>
							<c:if test="${not empty tagList }">
								<c:forEach items="${tagList }" var="tag">
									<span onclick="addTag(${tag.tagId},'${tag.tagName }',this);">${tag.tagName }</span>
								</c:forEach>
							</c:if>
							<c:if test="${page.totalPage > 1}">
								<div class="change"
									onclick="changeTag(${page.totalPage},10,this,31);">
									<span class="m0">换一批(</span><span class="m0" id="leftNum">${page.totalPage}</span><span
										class="m0">)</span> <input type="hidden" id="pageNo"
										value="${page.pageNo}">
								</div>
							</c:if>
						</div>
						<div class="inputfloat <c:if test="${empty tagList }">mt8</c:if>">
							<input type="text" id="defineTag"
								placeholder="如果标签中没有您所需要的，请自行填写" class="input-large">
							<div class="f_left bt-bg-style bg-light-blue bt-small  addbrand"
								onclick="addDefineTag(this);">添加</div>
						</div>
						<div class="addtags mt6">
							<ul id="tag_show_ul">
							</ul>
						</div>
					</div>
				</li>
				<li>
	                <div class="form-tips">
	                    <lable>承运商名称</lable>
	                </div>
	                <div class="f_left">
	                    <div class="form-blanks">
	                        <input type="text" name='logisticsName' id='logisticsName' class="input-large" value="${traderSupplier.logisticsName }"/>
	                    </div>
	                    <div id="logisticsNameError"></div>
	                </div>
	            </li>
	            <li>
	                <div class="form-tips">
	                    <lable>企业宣传片</lable>
	                </div>
	                <div class="f_left">
	                	<c:if test="${not empty traderSupplier.companyUriList}">
	                		<c:forEach items="${traderSupplier.companyUriList}" var="cmu" varStatus="status">
	                			<c:if test = "${status.count == 1}">
	                				<div class="form-blanks">
				                        <input type="text" name='companyUri' id='companyUri_1' class="input-largest" value="${cmu.uri}"/>
				                    </div>
	                			</c:if>
	                			<c:if test = "${status.count > 1}">
				                    <div class="form-blanks mt10">
						            	<div class="pos_rel f_left">
						                    <input type="text" class="input-largest" id="name_${status.count}" name="companyUri" value="${cmu.uri}" />
						    			</div>
						            	<div class="f_left">
						            		<span class="font-red cursor-pointer ml10 mt4" onclick="delBase(${status.count})" id="img_del_${status.count}">删除</span>
						            	</div>
						            	<div class="clear"></div>
						            </div>
	                			</c:if>
							</c:forEach>
	                	</c:if>
	                	<c:if test="${empty traderSupplier.companyUriList}">
		                    <div class="form-blanks">
		                        <input type="text" name='companyUri' id='companyUri_1' class="input-largest" placeholder="请填写链接地址"/>
		                    </div>
	                    </c:if>
	                    <div class="mt8" id="conadd">
                            <span class="bt-border-style bt-small border-blue" onclick="conaddBase();">继续添加</span>
                        </div>
	                    <div id="logisticsNameError"></div>
	                </div>
	            </li>
	            <li>
	                <div class="form-tips">
	                    <lable>官方网址</lable>
	                </div>
	                <div class="f_left">
	                    <div class="form-blanks">
	                        <input type="text" name='website' id='website' class="input-large" value="${traderSupplier.website}"/>
	                    </div>
	                    <div id="websiteError"></div>
	                </div>
	            </li>
				<li>
					<div class="infor_name mt0">
						<lable>备注</lable>
					</div>
					<div class="f_left ">
						<input type="text" class="input-xxx"
							name="traderSupplier.comments" id="comments" />
					</div>
				</li>
				<li>
					<div class="infor_name mt0">
						<lable>简介</lable>
					</div>
					<div class="f_left ">
						<textarea class="input-xxx" name="traderSupplier.brief" id="brief"></textarea>
					</div>
				</li>
			</ul>
		</div>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="formToken" value="${formToken}"/>
			<%--<button type="submit">提交</button>--%>
		</div>
	</div>
	<div class="baseinforcontainer" style="padding-bottom: 15px;">
		<div class="border overflow-hidden">
			<div class="baseinfor f_left">资质</div>
		</div>

		<div class="addElement">
			<div class="add-main">
				<input type="hidden" name="domain" value="${domain}" id="domain">
				<ul class="add-detail add-detail1">
					<li class="table-large">
						<div class="infor_name" style="width: 155px;">
							<label>三证合一</label>
						</div>
						<div class="f_left inputfloat mt3">
							<input type="radio" name="threeInOne" id="one" value="1" onclick="thOne();"
								   <c:if test="${business.threeInOne eq 1}">checked="checked"</c:if> />
							<label class="mr8">是</label>
							<input type="radio" name="threeInOne" id="zero" value="0" onclick="thZero();"
								   <c:if test="${business.threeInOne ne 1}">checked="checked"</c:if>/>
							<label>否</label>
						</div>
					</li>
					<li>
						<div class="infor_name sex_name" style="width: 155px;">
							<label>营业执照</label>
						</div>
						<div class="f_left insertli insertli1">
							<ul>
								<li style="margin-bottom:0px;">
									<div class="f_left">
										<input type="file" class="upload_file" id='file_1' name="lwfile"
											   style="display: none;" onchange="uploadFile(this,1);">
										<input type="text" class="input-middle" id="name_1" readonly="readonly"
											   placeholder="请上传营业执照" name="busName" onclick="file_1.click();"
											   value="${business.name}">
										<input type="hidden" id="uri_1" name="busUri" value="${business.uri}">
										<div class="font-red " style="display: none;">请上传营业执照</div>
									</div>
									<label class="bt-bg-style bt-middle bg-light-blue ml10" type="file" id="busUpload"
										   onclick="return $('#file_1').click();">浏览</label>
									<!-- 上传成功出现 -->
									<c:choose>
										<c:when test="${!empty business.uri}">
											<i class="iconsuccesss ml7" id="img_icon_1"></i>
											<a href="http://${business.domain}${business.uri}" target="_blank"
											   class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_1">查看</a>
											<span class="font-red cursor-pointer mt4" onclick="del(1)"
												  id="img_del_1">删除</span>
										</c:when>
										<c:otherwise>
											<i class="iconsuccesss ml7 none" id="img_icon_1"></i>
											<a href="" target="_blank"
											   class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_1">查看</a>
											<span class="font-red cursor-pointer mt4 none" onclick="del(1)"
												  id="img_del_1">删除</span>
										</c:otherwise>
									</c:choose>
								</li>
								<li style="margin-bottom:0px;">
									<label class="f_left mt4 mr10">设置有效期</label>
									<div class="f_left">
										<div class='inputfloat'>
											<input type="text" class="Wdate input-smaller" placeholder="请选择日期"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'busEndTime\')}'})"
												   name="busStartTime"
												   id="busStartTime"
												   value='<date:date value ="${business.begintime} " format="yyyy-MM-dd"/>'/>
											<input type="text" class="Wdate input-smaller" placeholder="请选择日期"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'busStartTime\')}'})"
												   name="busEndTime"
												   id="busEndTime"
												   value='<date:date value ="${business.endtime} " format="yyyy-MM-dd"/>'/>
										</div>
										<div class="font-red " style="display: none;">开始时间不能为空</div>
									</div>
								</li>
								<li class="inputfloat" style="margin-bottom:0px;">
									<input class="mt8" type="checkbox" name="isMedical"
										   <c:if test="${business.isMedical eq 1}">checked="checked"</c:if> value="1">
									<label class="mt5">含有医疗器械</label>
								</li>
							</ul>
						</div>
					</li>
					<li>
						<div class="infor_name sex_name" style="width: 155px;">
							<label>税务登记证</label>
						</div>
						<div class="f_left insertli insertli1" id="tax">
							<ul>
								<li>
									<div class="f_left">
										<input type="file" class="upload_file" id='file_2' name="lwfile"
											   style="display: none;" onchange="uploadFile(this,2);">
										<input type="text" class="input-middle" id="name_2" readonly="readonly"
											   <c:if test="${business.threeInOne eq 1}">disabled="disabled"</c:if>
											   placeholder="请上传税务登记证" name="taxName" onclick="file_2.click();"
											   value="${tax.name}">
										<input type="hidden" id="uri_2" name="taxUri" value="${tax.uri}">
										<div class="font-red " style="display: none;">请上传税务登记证</div>
									</div>
									<label class="bt-bg-style bt-middle bg-light-blue ml10 <c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>"
										   type="file" id="taxUpload"
										   <c:if test='${business eq null || business.threeInOne ne 1}'>onclick="return $('#file_2').click();"</c:if>>浏览</label>
									<!-- 上传成功出现 -->
									<c:choose>
										<c:when test="${!empty tax.uri}">
											<i class="iconsuccesss ml7" id="img_icon_2"></i>
											<a href="http://${tax.domain}${tax.uri}" target="_blank"
											   class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_2">查看</a>
											<span class="font-red <c:if test="${business eq null || business.threeInOne eq 0}">cursor-pointer</c:if> mt4"
												  <c:if test="${business eq null || business.threeInOne eq 0}">onclick="del(2)"</c:if>
												  id="img_del_2">删除</span>
										</c:when>
										<c:otherwise>
											<i class="iconsuccesss ml7 none" id="img_icon_2"></i>
											<a href="" target="_blank"
											   class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_2">查看</a>
											<span class="font-red <c:if test="${business eq null || business.threeInOne eq 0}">cursor-pointer</c:if> mt4 none"
												  <c:if test="${business eq null || business.threeInOne eq 0}">onclick="del(2)"</c:if>
												  id="img_del_2">删除</span>
										</c:otherwise>
									</c:choose>

								</li>
								<li>
									<label class="f_left mt4 mr10 <c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>">设置有效期</label>
									<div class="f_left">
										<div class='inputfloat'>
											<input type="text"
												   class="Wdate input-smaller <c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>"
												   placeholder="请选择日期"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'taxEndTime\')}'})"
												   name="taxStartTime"
												   id="taxStartTime"
												   value='<date:date value ="${tax.begintime} " format="yyyy-MM-dd"/>'
												   <c:if test="${business.threeInOne eq 1}">disabled="disabled"</c:if>/>
											<input type="text"
												   class="Wdate input-smaller <c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>"
												   placeholder="请选择日期"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'taxStartTime\')}'})"
												   name="taxEndTime"
												   id="taxEndTime"
												   value='<date:date value ="${tax.endtime} " format="yyyy-MM-dd"/>'
												   <c:if test="${business.threeInOne eq 1}">disabled="disabled"</c:if>/>
										</div>
										<div class="font-red " style="display: none;">开始时间不能为空</div>
									</div>
								</li>
							</ul>
						</div>
					</li>
					<li>
						<div class="infor_name sex_name" style="width: 155px;">
							<label>组织机构代码证</label>
						</div>
						<div class="f_left insertli " id="org">
							<ul>
								<li style="margin-bottom:0px;">
									<div class="f_left">
										<input type="file" class="upload_file" id='file_3' name="lwfile"
											   style="display: none;" onchange="uploadFile(this,3);">
										<input type="text" class="input-middle" id="name_3" readonly="readonly"
											   <c:if test="${business.threeInOne eq 1}">disabled="disabled"</c:if>
											   placeholder="请上传组织机构代码证" name="orgName" onclick="file_3.click();"
											   value="${orga.name}">
										<input type="hidden" id="uri_3" name="orgaUri" value="${orga.uri}">
										<div class="font-red " style="display: none;">请上传组织机构代码证</div>
									</div>
									<label class="bt-bg-style bt-middle bg-light-blue ml10
                                    	<c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>" type="file"
										   id="orgUpload"
										   <c:if test='${business eq null || business.threeInOne ne 1}'>onclick="return $('#file_3').click();"</c:if>>浏览</label>
									<!-- 上传成功出现 -->
									<c:choose>
										<c:when test="${!empty orga.uri}">
											<i class="iconsuccesss ml7" id="img_icon_3"></i>
											<a href="http://${orga.domain}${orga.uri}" target="_blank"
											   class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_3">查看</a>
											<span class="font-red <c:if test="${business eq null || business.threeInOne eq 0}">cursor-pointer</c:if> mt4"
												  <c:if test="${business eq null || business.threeInOne eq 0}">onclick="del(3)"</c:if>
												  id="img_del_3">删除</span>
										</c:when>
										<c:otherwise>
											<i class="iconsuccesss ml7 none" id="img_icon_3"></i>
											<a href="" target="_blank"
											   class="font-blue <c:if test="${business.threeInOne eq 0}">cursor-pointer</c:if> mr5 ml10 mt4 none"
											   id="img_view_3">查看</a>
											<span class="font-red cursor-pointer mt4 none"
												  <c:if test="${business eq null || business.threeInOne eq 0}">onclick="del(3)"</c:if>
												  id="img_del_3">删除</span>
										</c:otherwise>
									</c:choose>

								</li>
								<li style="margin-bottom:0px;">
									<label class="f_left mt4 mr10 <c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>">设置有效期</label>
									<div class="f_left">
										<div class='inputfloat'>
											<input class="Wdate input-smaller <c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>"
												   type="text" placeholder="请选择日期"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'orgaEndTime\')}'})"
												   name="orgaStartTime"
												   id="orgaStartTime"
												   value='<date:date value ="${orga.begintime} " format="yyyy-MM-dd"/>'
												   <c:if test="${business.threeInOne eq 1}">disabled="disabled"</c:if>/>
											<input class="Wdate input-smaller <c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>"
												   type="text" placeholder="请选择日期"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'orgaStartTime\')}'})"
												   name="orgaEndTime"
												   id="orgaEndTime"
												   value='<date:date value ="${orga.endtime} " format="yyyy-MM-dd"/>'
												   <c:if test="${business.threeInOne eq 1}">disabled="disabled"</c:if>/>
										</div>
										<div class="font-red" style="display: none;">开始时间不能为空</div>
									</div>
								</li>
							</ul>
						</div>
					</li>
					<li class="table-large">
						<div class="infor_name mt0" style="width: 155px;">
							<label>医疗资质合一</label>
						</div>
						<div class="f_left inputfloat">
							<input type="radio" name="medicalQualification" id="med1" value="1" onclick="medOne();"
								   <c:if test="${twoMedical.medicalQualification eq 1}">checked="checked"</c:if>/>
							<label class="mr8">是</label>
							<input type="radio" name="medicalQualification" id="med0" value="0" onclick="medZero();"
								   <c:if test="${twoMedical.medicalQualification ne 1}">checked="checked"</c:if>/>
							<label>否</label>
						</div>
					</li>
					<li>
						<div class="infor_name sex_name" style="width: 155px;">
							<label>医疗器械二类备案凭证</label>
						</div>
						<div class="f_left insertli ">
							<ul id="two_medical">
								<li style="margin-bottom:0;">
									<c:choose>
										<c:when test="${!empty twoMedicalList }">
											<c:forEach items="${twoMedicalList }" var="twoMedical" varStatus="st">
												<c:if test="${st.index == 0}">
													<c:set var="beginTime" value="${twoMedical.begintime}"></c:set>
													<c:set var="endTime" value="${twoMedical.endtime}"></c:set>
													<c:set var="sn" value="${twoMedical.sn}"></c:set>
												</c:if>
												<div class="mb8">
													<div class="pos_rel f_left ">
														<input type="file" class="upload_file" name="lwfile"
															   id="file_4_${st.index}" style="display: none;"
															   onchange="uploadFile(this,4);"/>
														<c:choose>
															<c:when test="${st.index == 0 }">
																<input type="text" class="input-middle" id="name_4"
																	   readonly="readonly"
																	   placeholder="请上传二类备案凭证" name="twoName"
																	   onclick="file_4_${st.index}.click();"
																	   value="${twoMedical.name}">
																<input type="hidden" id="uri_4_${st.index}"
																	   name="twoUri" value="${twoMedical.uri}">
															</c:when>
															<c:otherwise>
																<input type="text" class="input-middle"
																	   id="name_4_${st.index}" readonly="readonly"
																	   placeholder="请上传二类备案凭证" name="name_4"
																	   onclick="file_4_${st.index}.click();"
																	   value="${twoMedical.name}">
																<input type="hidden" id="uri_4_${st.index}" name="uri_4"
																	   value="${twoMedical.uri}">
															</c:otherwise>
														</c:choose>
														<label class="bt-bg-style bt-middle bg-light-blue ml10"
															   id="twoUpload"
															   onclick="return $('#file_4_${st.index}').click();">浏览</label>
													</div>

													<c:choose>
														<c:when test="${twoMedical.uri ne null && twoMedical.uri ne ''}">
															<div class="f_left ">
																<i class="iconsuccesss ml7" id="img_icon_4"></i>
																<a href="http://${twoMedical.domain}${twoMedical.uri}"
																   target="_blank"
																   class="font-blue cursor-pointer mr5 ml10 mt4"
																   id="img_view_4">查看</a>
																<c:choose>
																	<c:when test="${st.index == 0 }">
                                                                        <span class="font-red cursor-pointer mt4"
																			  onclick="del(4)" id="img_del_4">删除</span>
																	</c:when>
																	<c:otherwise>
                                                                        <span class="font-red cursor-pointer mt4"
																			  onclick="delAttachment(this)"
																			  id="img_del_4">删除</span>
																	</c:otherwise>
																</c:choose>
															</div>
														</c:when>
														<c:otherwise>
															<div class="f_left ">
																<i class="iconsuccesss ml7 none" id="img_icon_4"></i>
																<a href="http://${twoMedical.domain}${twoMedical.uri}"
																   target="_blank"
																   class="font-blue cursor-pointer mr5 ml10 mt4 none"
																   id="img_view_4">查看</a>
																<c:choose>
																	<c:when test="${st.index == 0 }">
                                                                        <span class="font-red cursor-pointer mt4 none"
																			  onclick="del(4)" id="img_del_4">删除</span>
																	</c:when>
																	<c:otherwise>
                                                                        <span class="font-red cursor-pointer mt4 none"
																			  onclick="delAttachment(this)"
																			  id="img_del_4">删除</span>
																	</c:otherwise>
																</c:choose>
															</div>
														</c:otherwise>
													</c:choose>
													<div class="clear"></div>
												</div>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<div class="mb8">
												<div class="f_left">
													<input type="file" class="upload_file" name="lwfile" id="file_4"
														   style="display: none;" onchange="uploadFile(this,4);"/>
													<input type="text" class="input-middle" id="name_4"
														   readonly="readonly"
														   placeholder="请上传二类备案凭证" name="twoName"
														   onclick="file_4.click();" value="${twoMedical.name}">
													<input type="hidden" id="uri_4" name="twoUri"
														   value="${twoMedical.uri}">
													<div class="font-red " style="display: none;">请上传二类备案凭证</div>
												</div>
												<label class="bt-bg-style bt-middle bg-light-blue ml10" id="twoUpload"
													   onclick="return $('#file_4').click();">浏览</label>
												<!-- 上传成功出现 -->
												<c:choose>
													<c:when test="${!empty twoMedical.uri}">
														<i class="iconsuccesss ml7" id="img_icon_4"></i>
														<a href="http://${twoMedical.domain}${twoMedical.uri}"
														   target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4"
														   id="img_view_4">查看</a>
														<span class="font-red cursor-pointer mt4" onclick="del(4)"
															  id="img_del_4">删除</span>
													</c:when>
													<c:otherwise>
														<i class="iconsuccesss ml7 none" id="img_icon_4"></i>
														<a href="" target="_blank"
														   class="font-blue cursor-pointer mr5 ml10 mt4 none"
														   id="img_view_4">查看</a>
														<span class="font-red cursor-pointer mt4 none" onclick="del(4)"
															  id="img_del_4">删除</span>
													</c:otherwise>
												</c:choose>
												<div class="clear"></div>
											</div>
										</c:otherwise>
									</c:choose>

									<div class=" clear" id="conadd4">
                                        <span class="bt-border-style bt-small border-blue "
											  onclick="con_add(4);">继续添加</span>
									</div>

									<div class="font-grey9" style="margin-top:7px;">
										1，图片格式只能JPG、PNG、JPEG、BMP等格式<br>
										2，图片大小不超过5M
									</div>
								</li>
								<li style="margin-bottom:0;">
									<label class='f_left mt4 mr10'>设置有效期</label>
									<div class="f_left ">
										<div class='inputfloat'>
											<input class="Wdate input-smaller" type="text" placeholder="请选择日期"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'twoEndTime\')}'})"
												   name="twoStartTime"
												   id="twoStartTime"
												   value='<date:date value ="${beginTime} " format="yyyy-MM-dd"/>'/>
											<input class="Wdate input-smaller" type="text" placeholder="请选择日期"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'twoStartTime\')}'})"
												   name="twoEndTime"
												   id="twoEndTime"
												   value='<date:date value ="${endTime} " format="yyyy-MM-dd"/>'/>
										</div>
										<div class="font-red " style="display: none;">开始时间不能为空</div>
									</div>
								</li>
								<li class="specialli" style="margin-bottom:0;">
									<label class="f_left mt4 mr10">许可证编号</label>
									<div class="f_left ">
										<input type="text" name="twoSn" class="input-middle" value="${sn}" id="twoSn"/>
										<div class="font-red " style="display: none;"></div>
									</div>
								</li>
							</ul>
						</div>
					</li>
					<li class=''>
						<div class="infor_name sex_name" style="width: 155px;">
							<label class="mt1">选择医疗器械二类备案凭证</label>
						</div>
						<div class="f_left medicalquality overflow-hidden" style='width:84%;'>
							<input type="checkbox" name="twoMedicalTypeAll" onclick="clickAll('twoMedicalType')">
							<label>全选</label>
							<ul class="f_left" id="medical_ul">
								<c:forEach items="${medicalTypes }" var="mt">
									<c:set var="contains" value="false"/>
									<c:if test="${fn:contains(mt.relatedField,2)}">
										<c:if test="${not empty two}">
											<c:forEach items="${two }" var="mc" varStatus="status">
												<c:if test="${mt.sysOptionDefinitionId eq mc.medicalCategoryId}">
													<c:set var="contains" value="true"/>
												</c:if>
											</c:forEach>
										</c:if>
										<c:choose>
											<c:when test="${contains == true }">
												<li style="width:25%;float:left;">
													<input type="checkbox" name="twoMedicalType"
														   value="${mt.sysOptionDefinitionId }" checked="checked"
														   onclick="clickOne('twoMedicalType')">
													<label>${mt.title}</label>
												</li>
											</c:when>
											<c:otherwise>
												<li style="width:25%;float:left;">
													<input type="checkbox" name="twoMedicalType"
														   value="${mt.sysOptionDefinitionId }"
														   onclick="clickOne('twoMedicalType')">
													<label>${mt.title}</label>
												</li>
											</c:otherwise>
										</c:choose>
									</c:if>
								</c:forEach>
							</ul>
						</div>
					</li>

					<li>
						<div class="infor_name sex_name" style="width: 155px;">
							<label>三类医疗资质</label>
						</div>
						<div class="f_left insertli ">
							<ul id="three_medical">
								<li>
									<div class="f_left">
										<input type="file" class="upload_file" name="lwfile" id="file_5"
											   style="display: none;" onchange="uploadFile(this,5);"/>
										<input type="text" class="input-middle upload_file_tmp" id="name_5"
											   readonly="readonly"
											   <c:if test="${twoMedical.medicalQualification eq 1}">disabled="disabled"</c:if>
											   placeholder="请上传三类医疗资质" name="threeName" onclick="file_5.click();"
											   value="${threeMedical.name}">
										<input type="hidden" id="uri_5" name="threeUri" value="${threeMedical.uri}">
										<div class="font-red " style="display: none;">请上传三类医疗资质</div>
									</div>
									<label class="bt-bg-style bt-middle bg-light-blue ml10
	                                    	<c:if test="${twoMedical.medicalQualification eq 1}">bg-opcity</c:if>"
										   id="threeUpload"
										   <c:if test="${twoMedical.medicalQualification ne 1}">onclick="return $('#file_5').click();"</c:if>>浏览</label>
									<!-- 上传成功出现 -->
									<c:choose>
										<c:when test="${!empty threeMedical.uri}">
											<i class="iconsuccesss ml7" id="img_icon_5"></i>
											<a href="http://${threeMedical.domain}${threeMedical.uri}" target="_blank"
											   class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_5">查看</a>
											<span class="font-red <c:if test="${twoMedical eq null ||twoMedical.medicalQualification eq 0}">cursor-pointer</c:if> mt4"
												  <c:if test="${twoMedical eq null || twoMedical.medicalQualification ne 1}">onclick="del(5)"</c:if>
												  id="img_del_5">删除</span>
										</c:when>
										<c:otherwise>
											<i class="iconsuccesss ml7 none" id="img_icon_5"></i>
											<a href="" target="_blank"
											   class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_5">查看</a>
											<span class="font-red <c:if test="${twoMedical eq null ||twoMedical.medicalQualification eq 0}">cursor-pointer</c:if> mt4 none"
												  <c:if test="${twoMedical eq null ||twoMedical.medicalQualification eq 0}">onclick="del(5)"</c:if>
												  id="img_del_5">删除</span>
										</c:otherwise>
									</c:choose>
								</li>
								<li>
									<label class="f_left  mt4 mr10 <c:if test="${twoMedical.medicalQualification eq 1}">bg-opcity</c:if>">设置有效期</label>
									<div class="f_left ">
										<div class='inputfloat'>
											<input class="Wdate input-smaller <c:if test="${twoMedical.medicalQualification eq 1}">bg-opcity</c:if>"
												   type="text" placeholder="请选择日期"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'threeEndTime\')}'})"
												   name="threeStartTime"
												   id="threeStartTime"
												   value='<date:date value ="${threeMedical.begintime} " format="yyyy-MM-dd"/>'
												   <c:if test="${twoMedical.medicalQualification eq 1}">disabled="disabled"</c:if>/>
											<input class="Wdate input-smaller <c:if test="${twoMedical.medicalQualification eq 1}">bg-opcity</c:if>"
												   type="text" placeholder="请选择日期"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'threeStartTime\')}'})"
												   name="threeEndTime"
												   id="threeEndTime"
												   value='<date:date value ="${threeMedical.endtime} " format="yyyy-MM-dd"/>'
												   <c:if test="${twoMedical.medicalQualification eq 1}">disabled="disabled"</c:if>/>
										</div>
										<div class="font-red" style="display: none;">开始时间不能为空</div>
									</div>
								</li>
								<li class="specialli">
									<label class="f_left mt4 mr10 <c:if test="${twoMedical.medicalQualification eq 1}">bg-opcity</c:if>">许可证编号</label>
									<div class="f_left ">
										<input type="text" name="threeSn" id="threeSn" class="input-middle"
											   value="${threeMedical.sn}"
											   <c:if test="${twoMedical.medicalQualification eq 1}">disabled="disabled"</c:if>/>
										<div class="font-red" style="display: none;">许可证编号不可为空</div>
									</div>
									<div class="clear"></div>
								</li>
							</ul>
						</div>
					</li>
					<li class=''>
						<div class="infor_name sex_name" style="width: 155px;">
							<label class="mt1">选择三类医疗资质</label>
						</div>
						<div class="f_left medicalquality overflow-hidden" style="width:84%;">
							<input type="checkbox" name="threeMedicalTypeAll" onclick="clickAll('threeMedicalType')">
							<label>全选</label>
							<ul class="f_left" id="medical_ul">
								<c:forEach items="${medicalTypes }" var="mt">
									<c:set var="contains" value="false"/>
									<c:if test="${fn:contains(mt.relatedField,3)}">
										<c:if test="${not empty three}">
											<c:forEach items="${three }" var="mc" varStatus="status">
												<c:if test="${mt.sysOptionDefinitionId eq mc.medicalCategoryId}">
													<c:set var="contains" value="true"/>
												</c:if>
											</c:forEach>
										</c:if>
										<c:choose>
											<c:when test="${contains == true }">
												<li style="width:25%;float:left;">
													<input type="checkbox" name="threeMedicalType"
														   value="${mt.sysOptionDefinitionId }" checked="checked"
														   onclick="clickOne('threeMedicalType')">
													<label>${mt.title}</label>
												</li>
											</c:when>
											<c:otherwise>
												<li style="width:25%;float:left;">
													<input type="checkbox" name="threeMedicalType"
														   value="${mt.sysOptionDefinitionId }"
														   onclick="clickOne('threeMedicalType')">
													<label>${mt.title}</label>
												</li>
											</c:otherwise>
										</c:choose>
									</c:if>
								</c:forEach>
							</ul>


						</div>
					</li>
					<li class="">
						<div class="infor_name sex_name" style="width: 155px;">
							<label>医疗器械生产许可证</label>
						</div>
						<div class="f_left insertli">
							<ul id="product">
								<li>
									<div class="f_left">
										<input type="file" class="upload_file" name="lwfile" id="file_6"
											   style="display: none;" onchange="uploadFile(this,6);"/>
										<input type="text" class="input-middle" id="name_6" readonly="readonly"
											   placeholder="请上传医疗机构执业许可证" name="productName" onclick="file_6.click();"
											   value="${product.name}">
										<input type="hidden" id="uri_6" name="productUri" value="${product.uri}">
										<div class="font-red " style="display: none;">请上传医疗器械生产许可证</div>
									</div>
									<label class="bt-bg-style bt-middle bg-light-blue ml10"
										   onclick="return $('#file_6').click();">浏览</label>
									<!-- 上传成功出现 -->
									<c:choose>
										<c:when test="${!empty product.uri}">
											<i class="iconsuccesss ml7" id="img_icon_6"></i>
											<a href="http://${product.domain}${product.uri}" target="_blank"
											   class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_6">查看</a>
											<span class="font-red cursor-pointer mt4" onclick="del(6)"
												  id="img_del_6">删除</span>
										</c:when>
										<c:otherwise>
											<i class="iconsuccesss ml7 none" id="img_icon_6"></i>
											<a href="" target="_blank"
											   class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_6">查看</a>
											<span class="font-red cursor-pointer mt4 none" onclick="del(6)"
												  id="img_del_6">删除</span>
										</c:otherwise>
									</c:choose>
								</li>
								<li>
									<label class="f_left mt4 mr10">设置有效期</label>
									<div class="f_left">
										<div class='inputfloat'>
											<input class="Wdate input-smaller" type="text" placeholder="请选择日期"
												   id="productStartTime"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'productEndTime\')}'})"
												   name="productStartTime"
												   value='<date:date value ="${product.begintime} " format="yyyy-MM-dd"/>'/>
											<input class="Wdate input-smaller" type="text" placeholder="请选择日期"
												   id="productEndTime"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'productStartTime\')}'})"
												   name="productEndTime"
												   value='<date:date value ="${product.endtime} " format="yyyy-MM-dd"/>'/>
										</div>
										<div class="font-red " style="display: none;">开始时间不能为空</div>
									</div>

								</li>
								<li class="specialli">
									<label class="f_left mt4 mr10">许可证编号</label>
									<div class="f_left ">
										<input type="text" name="productSn" class="input-middle" value="${product.sn}"
											   id="productSn"/>
										<div class="font-red" style="display: none;"></div>
									</div>
									<div class="clear"></div>
								</li>
							</ul>
						</div>
					</li>
					<li class="">
						<div class="infor_name sex_name" style="width: 155px;">
							<label>医疗器械经营许可证</label>
						</div>
						<div class="f_left insertli">
							<ul id="operate">
								<li>
									<div class="f_left">
										<input type="file" class="upload_file" name="lwfile" id="file_7"
											   style="display: none;" onchange="uploadFile(this,7);"/>
										<input type="text" class="input-middle" id="name_7" readonly="readonly"
											   placeholder="请上传医疗机构经营许可证" name="operateName" onclick="file_7.click();"
											   value="${operate.name}">
										<input type="hidden" id="uri_7" name="operateUri" value="${operate.uri}">
										<div class="font-red " style="display: none;">请上传医疗器械经营许可证</div>
									</div>
									<label class="bt-bg-style bt-middle bg-light-blue ml10"
										   onclick="return $('#file_7').click();">浏览</label>
									<!-- 上传成功出现 -->
									<c:choose>
										<c:when test="${!empty operate.uri}">
											<i class="iconsuccesss ml7" id="img_icon_7"></i>
											<a href="http://${operate.domain}${operate.uri}" target="_blank"
											   class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_7">查看</a>
											<span class="font-red cursor-pointer mt4" onclick="del(7)"
												  id="img_del_7">删除</span>
										</c:when>
										<c:otherwise>
											<i class="iconsuccesss ml7 none" id="img_icon_7"></i>
											<a href="" target="_blank"
											   class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_7">查看</a>
											<span class="font-red cursor-pointer mt4 none" onclick="del(7)"
												  id="img_del_7">删除</span>
										</c:otherwise>
									</c:choose>
								</li>
								<li>
									<label class="f_left mt4 mr10">设置有效期</label>
									<div class="f_left">
										<div class='inputfloat'>
											<input class="Wdate input-smaller" type="text" placeholder="请选择日期"
												   id="operateStartTime"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'operateEndTime\')}'})"
												   name="operateStartTime"
												   value='<date:date value ="${operate.begintime} " format="yyyy-MM-dd"/>'/>
											<input class="Wdate input-smaller" type="text" placeholder="请选择日期"
												   id="operateEndTime"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'operateStartTime\')}'})"
												   name="operateEndTime"
												   value='<date:date value ="${operate.endtime} " format="yyyy-MM-dd"/>'/>
										</div>
										<div class="font-red " style="display: none;">开始时间不能为空</div>
									</div>

								</li>
								<li class="specialli">
									<label class="f_left mt4 mr10">许可证编号</label>
									<div class="f_left ">
										<input type="text" name="operateSn" class="input-middle" value="${operate.sn}"
											   id="operateSn"/>
										<div class="font-red" style="display: none;"></div>
									</div>
									<div class="clear"></div>
								</li>

							</ul>
						</div>
					</li>
					<!-- begin by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21  -->
					<li class="">
						<div class="infor_name sex_name" style="width: 155px;">
							<label>销售人员授权书</label>
						</div>
						<div class="f_left insertli">
							<ul id="saleAuth_1">
								<li>
									<div class="f_left">
										<input type="file" class="upload_file" name="lwfile" id="file_8"
											   style="display: none;" onchange="uploadFile(this,8);"/>
										<input type="text" class="input-middle" id="name_8" readonly="readonly"
											   placeholder="请上传销售人员授权书" name="saleAuthBookName"
											   onclick="file_8.click();" value="${saleAuth.name}">
										<input type="hidden" id="uri_8" name="saleAuthBookUri" value="${saleAuth.uri}">
										<div id="sale_auto_book" class="font-red " style="display: none;">请上传销售人员授权书
										</div>
									</div>
									<label class="bt-bg-style bt-middle bg-light-blue ml10"
										   onclick="return $('#file_8').click();">浏览</label>
									<!-- 上传成功出现 -->
									<c:choose>
										<c:when test="${!empty saleAuth.uri}">
											<i class="iconsuccesss ml7" id="img_icon_8"></i>
											<a href="http://${saleAuth.domain}${saleAuth.uri}" target="_blank"
											   class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_8">查看</a>
											<span class="font-red cursor-pointer mt4" onclick="del(8)"
												  id="img_del_8">删除</span>
										</c:when>
										<c:otherwise>
											<i class="iconsuccesss ml7 none" id="img_icon_8"></i>
											<a href="" target="_blank"
											   class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_8">查看</a>
											<span class="font-red cursor-pointer mt4 none" onclick="del(8)"
												  id="img_del_8">删除</span>
										</c:otherwise>
									</c:choose>
								</li>
								<li>
									<label class="f_left mt4 mr10">设置有效期</label>
									<div class="f_left">
										<div class='inputfloat'>
											<input class="Wdate input-smaller" type="text" placeholder="请选择日期"
												   id="saleAuthBookStartTime"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'saleAuthBookEndTime\')}'})"
												   name="saleAuthBookStartTime"
												   value='<date:date value ="${saleAuth.begintime} " format="yyyy-MM-dd"/>'/>
											<input class="Wdate input-smaller" type="text" placeholder="请选择日期"
												   id="saleAuthBookEndTime"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'saleAuthBookStartTime\')}'})"
												   name="saleAuthBookEndTime"
												   value='<date:date value ="${saleAuth.endtime} " format="yyyy-MM-dd"/>'/>
										</div>
										<div id="sale_time_div" class="font-red" style="display: none;">开始时间不能为空</div>
									</div>

								</li>
							</ul>
						</div>
					</li>
					<li class="" style="margin-bottom:14px;">
						<div class="infor_name sex_name" style="width: 155px;">
							<label>授权销售人信息</label>
						</div>
						<div class="f_left insertli">
							<ul id="saleAuth_2">
								<li class="specialli" style="margin-bottom:0px;">
									<label class="f_left mt4 mr10">职位</label>
									<div class="f_left ">
										<input type="text" name="authPost" class="input-middle"
											   value="${saleAuth.authPost}" maxlength="100"/>
										<div class="font-red" style="display: none;"></div>
									</div>
									<div class="clear"></div>
								</li>
								<li class="specialli" style="margin-bottom:0px;">
									<label class="f_left mt4 mr10">姓名</label>
									<div class="f_left ">
										<input type="text" name="authUserName" class="input-middle"
											   value="${saleAuth.authUserName}" maxlength="100"/>
										<div class="font-red" style="display: none;"></div>
									</div>
									<div class="clear"></div>
								</li>
								<li class="specialli" style="margin-bottom:0px;">
									<label class="f_left mt4 mr10">联系方式</label>
									<div class="f_left ">
										<input type="text" name="authContactInfo" class="input-middle"
											   value="${saleAuth.authContactInfo}" maxlength="200"/>
										<div class="font-red" style="display: none;"></div>
									</div>
									<div class="clear"></div>
								</li>
							</ul>
						</div>
					</li>
					<li class="">
						<div class="infor_name sex_name" style="width: 155px;">
							<label>品牌授权书</label>
						</div>
						<div class="f_left insertli">
							<ul id="brandBook">
								<li>
									<c:choose>
										<c:when test="${!empty brandBookList}">
											<c:forEach items="${brandBookList }" var="brandBook" varStatus="st">
												<c:if test="${st.index == 0}">
													<c:set var="brankBeginTime" value="${brandBook.begintime}"></c:set>
													<c:set var="brankEndTime" value="${brandBook.endtime}"></c:set>
												</c:if>
												<div class="mb8">
													<div class="pos_rel f_left ">

														<input type="file" class="upload_file" name="lwfile"
															   id="file_9_${st.index}" style="display: none;"
															   onchange="uploadFile(this,9);"/>
														<c:choose>
															<c:when test="${st.index == 0}">
																<input type="text" class="input-middle" id="name_9"
																	   readonly="readonly"
																	   placeholder="请上传品牌授权书" name="brandBookName"
																	   onclick="file_9_${st.index}.click();"
																	   value="${brandBook.name}">
																<input type="hidden" id="uri_9_${st.index}"
																	   name="brandBookUri" value="${brandBook.uri}">
															</c:when>
															<c:otherwise>
																<input type="text" class="input-middle"
																	   id="name_9_${st.index}" readonly="readonly"
																	   placeholder="请上传品牌授权书" name="name_9"
																	   onclick="file_9_${st.index}.click();"
																	   value="${brandBook.name}">
																<input type="hidden" id="uri_9_${st.index}" name="uri_9"
																	   value="${brandBook.uri}">
															</c:otherwise>
														</c:choose>
														<label class="bt-bg-style bt-middle bg-light-blue ml10"
															   onclick="return $('#file_9_${st.index}').click();"
															   style="margin:0 12px 0 10px">浏览</label>

														<div><span id="brand_book" class="font-red "
																   style="display: none;">请上传品牌授权书</span></div>
													</div>

													<c:choose>
														<c:when test="${brandBook.uri ne null && brandBook.uri ne ''}">
															<div class="f_left ">
																<i class="iconsuccesss ml7" id="img_icon_9"></i>
																<a href="http://${brandBook.domain}${brandBook.uri}"
																   target="_blank"
																   class="font-blue cursor-pointer mr5 ml10 mt4"
																   id="img_view_9">查看</a>
																<c:choose>
																	<c:when test="${st.index == 0}">
                                                                        <span class="font-red cursor-pointer mt4"
																			  onclick="del(9)" id="img_del_9">删除</span>
																	</c:when>
																	<c:otherwise>
                                                                        <span class="font-red cursor-pointer mt4"
																			  onclick="delAttachment(this)"
																			  id="img_del_9">删除</span>
																	</c:otherwise>
																</c:choose>
															</div>
														</c:when>
														<c:otherwise>
															<div class="f_left ">
																<i class="iconsuccesss ml7 none" id="img_icon_9"></i>
																<a href="http://${brandBook.domain}${brandBook.uri}"
																   target="_blank"
																   class="font-blue cursor-pointer mr5 ml10 mt4 none"
																   id="img_view_9">查看</a>
																<c:choose>
																	<c:when test="${st.index == 0}">
                                                                        <span class="font-red cursor-pointer mt4 none"
																			  onclick="del(9)" id="img_del_9">删除</span>
																	</c:when>
																	<c:otherwise>
                                                                        <span class="font-red cursor-pointer mt4 none"
																			  onclick="delAttachment(this)"
																			  id="img_del_9">删除</span>
																	</c:otherwise>
																</c:choose>
															</div>
														</c:otherwise>
													</c:choose>
													<div class="clear"></div>
												</div>
											</c:forEach>
										</c:when>

										<c:otherwise>
											<div class=" mb8">
												<div class="f_left">

													<input type="file" class="upload_file" name="lwfile" id="file_9"
														   style="display: none;" onchange="uploadFile(this,9);"/>
													<input type="text" class="input-middle" id="name_9"
														   readonly="readonly"
														   placeholder="请上传品牌授权书" name="brandBookName"
														   onclick="file_9.click();" value="${brandBook.name}">
													<input type="hidden" id="uri_9" name="brandBookUri"
														   value="${brandBook.uri}">
													<label class="bt-bg-style bt-middle bg-light-blue ml10"
														   onclick="return $('#file_9').click();">浏览</label>

													<div><span id="brand_book" class="font-red " style="display: none;">请上传品牌授权书</span>
													</div>

												</div>
												<!-- 上传成功出现 -->
												<div class="f_left">
													<i class="iconsuccesss ml7 none" id="img_icon_9"></i>
													<a href="" target="_blank"
													   class="font-blue cursor-pointer mr5 ml10 mt4 none"
													   id="img_view_9">查看</a>
													<span class="font-red cursor-pointer mt4 none" onclick="del(9)"
														  id="img_del_9">删除</span>
												</div>

												<div class="clear"></div>
											</div>
										</c:otherwise>
									</c:choose>

									<div class=" clear" id="conadd9">
                                        <span class="bt-border-style bt-small border-blue "
											  onclick="con_add(9);">继续添加</span>
									</div>

									<div class="font-grey9" style="margin-top:7px;">
										1，图片格式只能JPG、PNG、JPEG、BMP、PDF等格式<br>
										2，图片大小不超过5M
									</div>
								</li>
								<li>
									<label class="f_left mt4 mr10">设置有效期</label>
									<div class="f_left">
										<div class='inputfloat'>
											<input class="Wdate input-smaller" type="text" placeholder="请选择日期"
												   id="brandBookStartTime"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'brandBookEndTime\')}'})"
												   name="brandBookStartTime"
												   value='<date:date value ="${brankBeginTime} " format="yyyy-MM-dd"/>'/>

											<input class="Wdate input-smaller" type="text" placeholder="请选择日期"
												   id="brandBookEndTime"
												   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'brandBookStartTime\')}'})"
												   name="brandBookEndTime"
												   value='<date:date value ="${brankEndTime} " format="yyyy-MM-dd"/>'/>
										</div>
										<div id="brandBook_time_div" class="font-red" style="display: none;">开始时间不能为空
										</div>
									</div>
								</li>
							</ul>
						</div>
					</li>

					<li class="">
						<div class="infor_name sex_name" style="width: 155px;">
							<label>其他</label>
						</div>
						<div class="f_left insertli">
							<ul id="theOther">
								<li>
									<c:choose>
										<c:when test="${!empty otherList}">
											<c:forEach items="${otherList }" var="other" varStatus="st">
												<div class="clear mb8">
													<div class="pos_rel f_left mb8">
														<input type="file" class="upload_file" name="lwfile"
															   id="file_10_${st.index}" style="display: none;"
															   onchange="uploadFile(this,10);"/>
														<c:choose>
															<c:when test="${st.index == 0}">
																<input type="text" class="input-middle" id="name_10"
																	   readonly="readonly"
																	   placeholder="请上传其他资质图片" name="otherName"
																	   onclick="file_10_${st.index}.click();"
																	   value="${other.name}">
																<input type="hidden" id="uri_10_${st.index}"
																	   name="otherUri" value="${other.uri}">
															</c:when>
															<c:otherwise>
																<input type="text" class="input-middle"
																	   id="name_10_${st.index}" readonly="readonly"
																	   placeholder="请上传其他资质图片" name="name_10"
																	   onclick="file_10_${st.index}.click();"
																	   value="${other.name}">
																<input type="hidden" id="uri_10_${st.index}"
																	   name="uri_10" value="${other.uri}">
															</c:otherwise>
														</c:choose>
														<!-- 														 <div id="brand_book" class="font-red " style="display: none;">请上传品牌授权书</div> -->
														<label class="bt-bg-style bt-middle bg-light-blue ml10"
															   onclick="return $('#file_10_${st.index}').click();">浏览</label>
													</div>
													<c:choose>
														<c:when test="${other.uri ne null && other.uri ne ''}">
															<div class="f_left ">
																<i class="iconsuccesss ml7" id="img_icon_10"></i>
																<a href="http://${other.domain}${other.uri}"
																   target="_blank"
																   class="font-blue cursor-pointer mr5 ml10 mt4"
																   id="img_view_10">查看</a>
																<c:choose>
																	<c:when test="${st.index == 0}">
                                                                        <span class="font-red cursor-pointer mt4"
																			  onclick="del(10)"
																			  id="img_del_10">删除</span>
																	</c:when>
																	<c:otherwise>
                                                                        <span class="font-red cursor-pointer mt4"
																			  onclick="delAttachment(this)"
																			  id="img_del_10">删除</span>
																	</c:otherwise>
																</c:choose>
															</div>
														</c:when>
														<c:otherwise>
															<div class="f_left ">
																<i class="iconsuccesss ml7 none" id="img_icon_10"></i>
																<a href="http://${other.domain}${other.uri}"
																   target="_blank"
																   class="font-blue cursor-pointer mr5 ml10 mt4 none"
																   id="img_view_10">查看</a>
																<c:choose>
																	<c:when test="${st.index == 0}">
                                                                        <span class="font-red cursor-pointer mt4 none"
																			  onclick="del(10)"
																			  id="img_del_10">删除</span>
																	</c:when>
																	<c:otherwise>
                                                                        <span class="font-red cursor-pointer mt4 none"
																			  onclick="delAttachment(this)"
																			  id="img_del_10">删除</span>
																	</c:otherwise>
																</c:choose>
															</div>
														</c:otherwise>
													</c:choose>
												</div>
											</c:forEach>
										</c:when>

										<c:otherwise>
											<div class="mb8">
												<div class="f_left">
													<input type="file" class="upload_file" name="lwfile" id="file_10"
														   style="display: none;" onchange="uploadFile(this,10);"/>
													<input type="text" class="input-middle" id="name_10"
														   readonly="readonly"
														   placeholder="请上传其他资质图片" name="otherName"
														   onclick="file_10.click();" value="${other.name}">
													<input type="hidden" id="uri_10" name="otherUri"
														   value="${other.uri}">
													<!-- 					                                    <div id="brand_book" class="font-red " style="display: none;">请上传品牌授权书</div> -->
													<label class="bt-bg-style bt-middle bg-light-blue ml10"
														   onclick="return $('#file_10').click();">浏览</label>
												</div>
												<!-- 上传成功出现 -->
												<c:choose>
													<c:when test="${!empty other.uri}">
														<i class="iconsuccesss ml7" id="img_icon_10"></i>
														<a href="http://${other.domain}${other.uri}" target="_blank"
														   class="font-blue cursor-pointer mr5 ml10 mt4"
														   id="img_view_10">查看</a>
														<span class="font-red cursor-pointer mt4" onclick="del(10)"
															  id="img_del_10">删除</span>
													</c:when>
													<c:otherwise>
														<i class="iconsuccesss ml7 none" id="img_icon_10"></i>
														<a href="" target="_blank"
														   class="font-blue cursor-pointer mr5 ml10 mt4 none"
														   id="img_view_10">查看</a>
														<span class="font-red cursor-pointer mt4 none" onclick="del(10)"
															  id="img_del_10">删除</span>
													</c:otherwise>
												</c:choose>
												<div class="clear"></div>
											</div>
										</c:otherwise>
									</c:choose>

									<div class="mt8 clear" id="conadd10">
                                        <span class="bt-border-style bt-small border-blue "
											  onclick="con_add(10);">继续添加</span>
									</div>

									<div class="font-grey9" style="margin-top:7px;">
										1，图片格式只能JPG、PNG、JPEG、BMP、PDF等格式<br>
										2，图片大小不超过5M
									</div>
								</li>
							</ul>
						</div>
					</li>

					<!-- end by franlin for[3865 供应商资质中，增加销售人授权书，销售人信息]  at 2018-06-21 -->
				</ul>
				<div class="font-grey9 ml120 line20">
					友情提醒
					<br/>1、结束日期可以不填写，代表资质没有结束日期；
					<br/>2、三证合一和医疗资质合一的客户无需再设置其他信息；
				</div>

				<%--<input type="hidden" name="busTraderCertificateId" value="${business.traderCertificateId}">--%>
				<%--<input type="hidden" name="taxTraderCertificateId" value="${tax.traderCertificateId}">--%>
				<%--<input type="hidden" name="orgaTraderCertificateId" value="${orga.traderCertificateId}">--%>
				<%--<input type="hidden" name="twoTraderCertificateId" value="${twoMedical.traderCertificateId}">--%>
				<%--<input type="hidden" name="threeTraderCertificateId" value="${threeMedical.traderCertificateId}">--%>
				<%--<input type="hidden" name="productTraderCertificateId" value="${product.traderCertificateId}">--%>
				<%--<input type="hidden" name="traderId" value="${traderSupplier1.traderId}">--%>
				<%--<input type="hidden" name="traderSupplierId" value="${traderSupplier1.traderSupplierId}">--%>
				<%--<input type="hidden" name="traderType" value="2">--%>
				<%--<input type="hidden" name="beforeParams" value='${beforeParams}'>--%>
			</div>
		</div>

	</div>
	<div class="content">
		<div class="parts">
			<div class="title-container">
				<div class="table-title nobor">
					联系人信息
				</div>
				<div class="title-click nobor J-add-contact">
					新增
				</div>
			</div>
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
					<tr>
						<th class="wid6">姓名</th>
						<th class="wid4">性别</th>
						<th>部门</th>
						<th class="wid14">电话</th>
						<th class="wid14">手机</th>
						<th>邮箱</th>
						<th>QQ</th>
						<th> 备注</th>
						<th class="wid17">操作</th>
					</tr>
				</thead>
				<tbody class="J-contact-list">
					<tr class="J-no-contact" style="display: none;">
						<td colspan="9">暂无记录</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="parts">
			<div class="title-container">
				<div class="table-title ">
					地址信息
				</div>
				<div class="title-click J-add-address">
					新增
				</div>
			</div>
			<table class="table table-bordered table-striped table-condensed table-centered">
				<thead>
				<tr>
					<th>地区</th>
					<th>地址</th>
					<th>邮编</th>
					<th>备注</th>
					<th class="wid15">操作</th>
				</tr>
				</thead>
				<tbody class="J-address-list">
					<tr class="J-no-address" style="display: none;">
						<td colspan="5">暂无记录</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<input type="hidden" name="addressList" class="J-address-data">
	<input type="hidden" name="contactList" class="J-contact-data">
	<div class="add-tijiao tcenter mt20">
		<button type="submit" id='submit'>提交</button>
	</div>
</form>
<div style="height: 30px;"></div>
<script type="text/tmpl" class="J-contact-tmpl">
	<tr class="J-contact-item" data-json='{{=JSON.stringify(data)}}'>
		<td>{{=data.name}}</td>
		<td>{{=['女','男','未知'][data.sex]}}</td>
		<td>{{=data.department}}</td>
		<td>{{=data.telephone}}</td>
		<td>{{=data.mobile}}</td>
		<td>{{=data.email}}</td>
		<td>{{=data.qq}}</td>
		<td>{{=data.comments}}</td>
		<td class="caozuo">
			<a class="J-contact-edit">编辑</a>
			<a class="J-contact-del">删除</a>
		</td>
	</tr>
</script>
<script type="text/tmpl" class="J-add-contact-tmpl">
	 <div class="formpublic J-form">
		<ul>
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable>姓名</lable>
				</div>
				<div class="f_left">
					<input type="text" class="input-largest J-name" name="name" value=""/>
				</div>
			</li>
			<li>
				<div class="infor_name mt0">
					<lable>性别</lable>
				</div>
				<div class="f_left inputfloat">
					<label class="mr8"><input type="radio" name="sex" value="1" />
					男</label>
					<label class="mr8"><input type="radio" name="sex" value="0" >
					女</label>
					<label><input type="radio" name="sex" value="2" checked>
					未知</label>
				</div>
			</li>
			<li>
				<div class="infor_name">
					<lable>部门</lable>
				</div>
				<div class="f_left commonuse table-largest J-department-wrap" id="dept">
					<div>
						<input type="text" name="department" value="" class="input-largest J-department" placeholder="您可以点击常用部门进行选择"/>
						<label>常用部门:</label>
						<span>采购部</span>
						<span>销售部</span>
						<span>生产部</span>
						<span>财务部</span>
						<span>仓储部</span>
						<span>研发部</span>
						<span>商务部</span>
						<span>研究院</span>
					</div>
					<div id="departmentError"></div>
				</div>
			</li>
			<li>
				<div class="infor_name">
					<lable>职位</lable>
				</div>
				 <div class="f_left commonuse table-largest  J-position-wrap" id="posi">
					<div>
						<input type="text" name="position" value="" class="input-largest J-position" placeholder="您可以点击常用职位进行选择" />
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
					<div id="positionError"></div>
				</div>
			</li>
			<li>
				<div class="infor_name">
					<lable>电话</lable>
				</div>
				<div class="f_left">
					<input type="text" class="input-largest errobor J-telephone" name="telephone" value=""/>

				</div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable>手机</lable>
				</div>
				<div class="f_left">
					<input type="text" class="input-largest errobor J-mobile" name="mobile" value=""/>
				</div>
			</li>
			<li>
				<div class="infor_name">
					<lable>手机2</lable>
				</div>
				<div class="f_left">
					<input type="text" class="input-largest errobor J-mobile2"  name="mobile2" value=""/>

				</div>
			</li>
			<li>
				<div class="infor_name">
					<lable>邮件</lable>
				</div>
				<div class="f_left">
					<input type="text" class="input-largest errobor J-email"  name="email" value=""/>
				</div>
			</li>

			  <li>
				<div class="infor_name">
					<lable>QQ</lable>
				</div>
				<div class="f_left">
					<input type="text" class="input-largest errobor J-qq"  name="qq"  value=""/>
				</div>
			</li>
			 <li>
				<div class="infor_name">
					<lable>备注</lable>
				</div>
				<div class="f_left">
					<textarea class="input-largest textarea-smallest J-comments" name="comments"></textarea>
				</div>
			</li>
		</ul>
		<div class="add-tijiao tcenter">
			<button type="submit" class="J-contact-add">提交</button>
			<button type="button" class="dele J-layer-close">取消</button>
		</div>
    </div>
</script>
<script type="text/tmpl" class="J-address-tmpl">
	<tr class="J-address-item" data-json='{{=JSON.stringify(data)}}'>
		<td>{{=data.areaName }}</td>
		<td>{{=data.address}}</td>
		<td>{{=data.zipCode}}</td>
		<td>{{=data.comments}}</td>
		<td class="caozuo">
		    <a class="J-address-edit">编辑</a>
			<a class="J-address-del">删除</a>
		</td>
	</tr>
</script>
<script type="text/tmpl" class="J-add-address-tmpl">
	<div class="formpublic J-form">
		<ul>
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable>地区</lable>
				</div>
				<div class="f_left">
					<div>
						<select class="input-middle mr6 J-province" name="province" id="">
							<option value="0">请选择</option>
							<c:if test="${not empty provinceList }">
								<c:forEach items="${provinceList }" var="prov">
									<option value="${prov.regionId }" <c:if test="${province eq prov.regionId }">selected="selected"</c:if>>${prov.regionName }</option>
								</c:forEach>
							</c:if>
					</select>
						<select class="input-middle mr6 J-city" name="city" id ="">
							<option value="0">请选择</option>
							<c:if test="${not empty cityList }">
								<c:forEach items="${cityList }" var="cy">
									<option value="${cy.regionId }" <c:if test="${city eq cy.regionId }">selected="selected"</c:if>>${cy.regionName }</option>
								</c:forEach>
							</c:if>
						</select>
						<select class="input-middle mr6 J-zone" name="zone" id="">
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
					<input type="text" class="input-largest errobor J-address" name="address" id="" value="${traderAddress.address}"/>

				</div>
			</li>
			<li>
				<div class="infor_name">
					<lable>邮编</lable>
				</div>
				<div class="f_left">
					<input type="text" class="input-largest errobor J-zipCode" name="zipCode" id="" value="${traderAddress.zipCode}"/>

				</div>
			</li>
			<li>
				<div class="infor_name">
					<lable>备注</lable>
				</div>
				 <div class="f_left commonuse table-largest J-address-tip">
					<div>
						<input type="text" name="comments" id="" value="${traderAddress.comments}" class="input-largest J-comments" placeholder="您可以点击常用备注进行选择" />
						<label>常用备注:</label>
						<span>合同地址</span>
						<span>收货地址</span>
						<span>收票地址</span>
					</div>
					<div id="commentsError2"></div>
				</div>
			</li>
		</ul>
		<div class="add-tijiao tcenter mt2">
			<button type="submit" class="J-address-add">提交</button>
			<button type="button" class="dele J-layer-close">取消</button>
		</div>
    </div>
</script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/new/js/common/template.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
		src="${pageContext.request.contextPath}/static/js/supplier/add_supplier.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/tag.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp"%>

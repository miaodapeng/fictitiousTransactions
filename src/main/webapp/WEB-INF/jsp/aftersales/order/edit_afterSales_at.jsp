<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑售后-安调" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/add_afterSales_dsf.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/controlRadio.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<div class="form-list  form-tips5">
    <form method="post" action="<%= basePath %>/aftersales/order/saveEditAfterSales.do">
        <ul>
            <li>
                <div class="form-tips">
                    <lable>售后类型</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <ul class="aftersales">
                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" checked="true" name="type" value="550" readonly="readonly">
                                    <label>安调</label>
                                </a>
                            </li>
                             <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="585" readonly="readonly">
                                    <label>维修</label>
                                </a>
                            </li>
                            <!-- 
                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="551" readonly="readonly">
                                    <label>退款</label>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="552" readonly="readonly">
                                    <label>技术咨询</label>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="553" readonly="readonly">
                                    <label>其他</label>
                                </a>
                            </li> -->
                        </ul>
                    </div>
                </div>
            </li>
            <input type="hidden" id="shtype" value="aw" />
            <input type="hidden" name="afterSalesId" value="${afterSales.afterSalesId}" />
            <input type="hidden" name="afterSalesDetailId" value="${afterSales.afterSalesDetailId}" />
            <input type="hidden" name="subjectType" value="${afterSales.subjectType}" />
            <input type="hidden" name="serviceAmount" value="${afterSales.serviceAmount}" />
            <input type="hidden" name="invoiceType" value="${afterSales.invoiceType}" />
            <input type="hidden" name="isSendInvoice" value="${afterSales.isSendInvoice}" />
            <input type="hidden" name="type" value="${afterSales.type}" />
            <input type="hidden" id="" name="thGoodsId" value="251462" />
            <input type="hidden" name="beforeParams" value='${beforeParams}'/>
            	<li>
	            	<div class="form-tips">
	                    <span>*</span>
	                    <lable>客户名称</lable>
	                </div>
	                <div class="f_left ">
	                    <div class="form-blanks mb10">
	                    	<span id="selname">${afterSales.traderName}</span>
	                    	<div class="none f_left" id="cus">
	                    		<input type="text" placeholder="请输入客户名称" class="input-largest none" name="searchName" id="searchName" value="${afterSales.traderName}">
	                    	</div>
							<label class="bt-bg-style bg-light-blue bt-small none" onclick="search();" id="search1" style='margin-top:-3px;'>搜索</label>
							<label class="bt-bg-style bg-light-blue bt-small" onclick="research();" id="search2" style='margin-top:-3px;'>重新搜索</label>
							<span style="display:none;">
								<!-- 弹框 -->
								<div class="title-click nobor  pop-new-data" id="popEngineer"></div>
							</span>
	                       	<input type="hidden" name="traderId" id="traderId" value="${afterSales.traderId}">
	                    </div>
	                    <div id="searchNameError"></div>
	                </div>
	            </li>
            	<li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>详情说明</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks ">
                            <textarea name="comments" id="comments" placeholder="请详述客户需求及安调维修要求以便售后同事联系" rows="5" class="wid90">${afterSales.comments}</textarea>
                        </div>
                        <div id="commentsError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>联系人</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <select class="input-largest" name="traderContactId" id="traderContactId">
		                          <c:forEach items="${afterSales.traderContactList}" var="tc">
		                          	<option value="${tc.traderContactId}" 
		                          		<c:if test="${afterSales.traderContactId eq tc.traderContactId}">selected="selected"</c:if>>${tc.name}/${tc.mobile}<c:if test="${tc.telephone eq null}">/${tc.telephone}</c:if></option>
		                          </c:forEach> 
                            </select>
                        </div>
                        <div id="traderContactIdError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>地区</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <ul> 
								<li>
									<select class="wid9" name="province" id="province">
										<option value="0">全部</option>
										<c:if test="${not empty provinceList }">
											<c:forEach items="${provinceList }" var="prov">
												<option value="${prov.regionId }"
													<c:if test="${province eq prov.regionId }">selected="selected"</c:if>>${prov.regionName }</option>
											</c:forEach>
										</c:if>
									</select>
								</li>
								<li>
									<select class="wid9" name="city" id="city">
										<option value="0">全部</option>
										<c:if test="${not empty cityList }">
											<c:forEach items="${cityList }" var="cy">
												<option value="${cy.regionId }"
													<c:if test="${city eq cy.regionId }">selected="selected"</c:if>>${cy.regionName }</option>
											</c:forEach>
										</c:if>
									</select> 
								</li>
								<li>	
									<select class="wid9" name="zone" id="zone">
										<option value="0">全部</option>
										<c:if test="${not empty zoneList }">
											<c:forEach items="${zoneList }" var="zo">
												<option value="${zo.regionId }"
													<c:if test="${zone eq zo.regionId }">selected="selected"</c:if>>${zo.regionName }</option>
											</c:forEach>
										</c:if>
									</select>
								</li>
							</ul>
                        </div>
                        <div id="areaError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>详细地址</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                        	<c:if test="${empty afterSales.address }">
                        		<input type="text" class="input-largest" id="address" name="address" value="${saleorder.takeTraderAddress}"> 
                        	</c:if>
                             <c:if test="${not empty afterSales.address }">
                        		<input type="text" class="input-largest" id="address" name="address" value="${afterSales.address}"> 
                        	</c:if>
                        </div>
                        <div id="addressError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>上传附件</lable>
                    </div>
                    <input type="hidden" id="domain" name="domain" value="${domain}">
                    <div class="f_left ">
                        <c:if test="${empty afterSales.attachmentList }">
	                		<div class="form-blanks">
		                    	<div class="pos_rel f_left">
	                            <input type="file" class="uploadErp" id='file_1' name="lwfile" onchange="uploadFile(this,1);">
	                            <input type="text" class="input-largest" id="name_1" readonly="readonly"
	                            	placeholder="请上传附件" name="fileName" onclick="file_1.click();" > 
	                            <input type="hidden" id="uri_1" name="fileUri" >
			    			</div>
                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_1').click();">浏览</label>
                            <!-- 上传成功出现 -->
                            <div class="f_left">
	                            <i class="iconsuccesss mt3 none" id="img_icon_1"></i>
	                    		<a href="" target="_blank" class="font-blue cursor-pointer  mt3 none" id="img_view_1">查看</a>
		                    	<span class="font-red cursor-pointer  mt3 none" onclick="del(1)" id="img_del_1">删除</span>
	                    	</div>
	                    	<div class='clear'></div>
		                    </div>
	                	</c:if>
		                <c:if test="${not empty afterSales.attachmentList}">
		                	<c:forEach items="${afterSales.attachmentList}" var="att" varStatus="status">
		                		<div class="form-blanks <c:if test='${status.count ne 1}'>mt10</c:if>">
			                    	<div class="pos_rel f_left">
				                         <input type="file" class="uploadErp" id='file_${status.count}' name="lwfile" onchange="uploadFile(this,${status.count});">
				                         <input type="text" class="input-largest" id="name_${status.count}" readonly="readonly"
				                         	placeholder="请上传附件" name="fileName" onclick="file_${status.count}.click();" value="${att.name}"> 
				                         <input type="hidden" id="uri_${status.count}" name="fileUri" value="${att.uri}">
					   				</div>
					                <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_${status.count}').click();">浏览</label>
			                        <!-- 上传成功出现 -->
			                        <div class="f_left">
			                        <i class="iconsuccesss mt3" id="img_icon_${status.count}"></i>
			                		<a href="http://${att.domain}${att.uri}" target="_blank" class="font-blue cursor-pointer mt3" id="img_view_${status.count}">查看</a>
			                 		<span class="font-red cursor-pointer mt3" onclick="del(${status.count})" id="img_del_${status.count}">删除</span>
			                 		</div>
			                 		<div class='clear'></div>
			                    </div>
		                	</c:forEach>
		                </c:if>
                        <div class="mt8" id="conadd">
                            <span class="bt-border-style bt-small border-blue" onclick="conadd();">继续添加</span>
                        </div>
                        <div class="pop-friend-tips mt6">
                            <div class="add-tijiao text-left mt8">
                                <button type="submit" id="submit">提交</button>
                            </div>
                        </div>
                    </div>
                </li>
        </ul>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>
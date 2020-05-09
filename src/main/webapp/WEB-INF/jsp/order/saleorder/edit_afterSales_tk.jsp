<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑售后-退款" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/add_afterSales_th.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/controlRadio.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<div class="form-list  form-tips8">
    <form method="post" action="<%= basePath %>/order/saleorder/saveEditAfterSales.do">
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
                                    <input type="radio" name="type" value="539" disabled="disabled">
                                    <label>退货</label>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="540" disabled="disabled">
                                    <label>换货</label>
                                </a>
                            </li>
                            <!-- 耗材订单不展示 安调与维修 -->
                            <c:if test="${saleorder.orderType ne 5}">
	                            <li>
	                                <a href="javascript:void(0);">
	                                    <input type="radio" name="type" value="541" disabled="disabled">
	                                    <label>安调</label>
	                                </a>
	                            </li>
	                            <li>
	                                <a href="javascript:void(0);">
	                                    <input type="radio" name="type" value="584" disabled="disabled">
	                                    <label>维修</label>
	                                </a>
	                            </li>
                            </c:if>
                            <li>
                                <a href="javascript:void(0);" >
                                    <input type="radio" name="type" value="542" disabled="disabled">
                                    <label>退票</label>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="543" disabled="disabled" checked="true">
                                    <label>退款</label>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="544" disabled="disabled">
                                    <label>技术咨询</label>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="545" disabled="disabled">
                                    <label>其他</label>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
             <input type="hidden" id="" name="orderId" value="${saleorder.saleorderId}" />
            <input type="hidden" id="" name="orderNo" value="${saleorder.saleorderNo}" />
            <input type="hidden" name="afterSalesId" value="${afterSales.afterSalesId}" />
            <input type="hidden" name="afterSalesDetailId" value="${afterSales.afterSalesDetailId}" />
            <input type="hidden" id="shtype" value="tk" />
            <input type="hidden" name="subjectType" value="${afterSales.subjectType}" />
            <input type="hidden" name="type" value="${afterSales.type}" />
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>详情说明</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks ">
                        <textarea name="comments" id="comments" placeholder="请详述客户退款要求以便售后同事联系" rows="5" class="wid90">${afterSales.comments}</textarea>
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
                        <c:if test="${empty afterSales.traderContactId}">
                          <c:forEach items="${saleorder.tcList}" var="tc">
                          	<option value="${tc.traderContactId}" 
                          		<c:if test="${saleorder.traderContactId eq tc.traderContactId}">selected="selected"</c:if>>${tc.name}/${tc.mobile}<c:if test="${tc.telephone eq null}">/${tc.telephone}</c:if></option>
                          </c:forEach> 
                        </c:if>
                        <c:if test="${not empty afterSales.traderContactId}">
                          <c:forEach items="${saleorder.tcList}" var="tc">
                          	<option value="${tc.traderContactId}" 
                          		<c:if test="${afterSales.traderContactId eq tc.traderContactId}">selected="selected"</c:if>>${tc.name}/${tc.mobile}<c:if test="${tc.telephone eq null}">/${tc.telephone}</c:if></option>
                          </c:forEach> 
                        </c:if>
                       </select>
                   </div>
                   <div id="traderContactIdError"></div>
               </div>
           </li>
           <li>
               <div class="form-tips">
                   <span>*</span>
                   <lable>退款金额</lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks">
                       <input type="text" name="refundAmount" id="refundAmount" value="${afterSales.refundAmount}" class="input-middle"/><span class="font-grey9">退款金额不允许大于已付款金额${saleorder.paid}</span>
                       <input type="hidden" name="paid" id="paid" value="${saleorder.paid}" class="input-middle"/>
                   </div>
                   <div id="refundAmountError"></div>
               </div>
           </li>
           <li>
               <div class="form-tips">
                   <span>*</span>
                   <lable>交易方式</lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks">
                       <ul>
                            <li>
                                <input type="radio" name="traderMode" <c:if test="${afterSales.traderMode eq 521}">checked="checked"</c:if> value="521">
                                <label>银行</label>
                            </li>
                            <li>
                               <input type="radio" name="traderMode" <c:if test="${afterSales.traderMode eq 520}">checked="checked"</c:if> value="520">
                               <label>支付宝</label>
                            </li>
                       </ul>    
                   </div>
                   <div id="traderModeError" ></div>
               </div>
           </li>
           <li>
               <div class="form-tips">
                   <span>*</span>
                   <lable>交易主体</lable>
               </div>
              <div class="f_left ">
                   <div class="form-blanks">
                       <ul>
                            <li>
                                <input type="radio" name="traderSubject" <c:if test="${afterSales.traderSubject eq 1}">checked="checked"</c:if> value="1">
                                <label>对公</label>
                            </li>
                            <li>
                               <input type="radio" name="traderSubject" <c:if test="${afterSales.traderSubject eq 2}">checked="checked"</c:if> value="2">
                               <label>对私</label>
                            </li>
                       </ul>    
                   </div>
                   <div id="traderSubjectError" ></div>
               </div>
           </li>
			<li>
               <div class="form-tips">
                   <span>*</span>
                   <lable>收款名称</lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks">
                   		<input type="text" name="payee" id="payee"  class="input-largest" value="${afterSales.payee}"/>
                   		<input type="hidden"  id="payee1"  value="${afterSales.payee}"/>
                   </div>
                   <div id="payeeError"></div>
               </div>
            </li>
            <li>
               <div class="form-tips">
                   <span>*</span>
                   <lable>开户银行</lable>
               </div>
              <div class="f_left ">
                   <div class="form-blanks">
                       <input type="text" name="bank" id="bank"  class="input-largest" value="${afterSales.bank}"/>
                       <input type="hidden"  id="bank1"  value="${afterSales.bank}"/>
                   </div>
                   <div id="bankError"></div>
               </div>
            </li>
            <li>
               <div class="form-tips">
                   <span>*</span>
                   <lable>银行账号 </lable>
               </div>
              <div class="f_left ">
                   <div class="form-blanks">
                       <input type="text" name="bankAccount" id="bankAccount"  class="input-largest" value="${afterSales.bankAccount}"/>
                       <input type="hidden" id="bankAccount1" value="${afterSales.bankAccount}"/>
                   </div>
                   <div id="bankAccountError"></div>
               </div>
            </li>
            <li>
               <div class="form-tips">
                   <lable>开户行支付联行号</lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks">
                       <input type="text" name="bankCode" id="bankCode" value="${afterSales.bankCode}" class="input-largest"/>
                       <input type="hidden" id="bankCode1" value="${afterSales.bankCode}" />
                   </div>
                   <div id="bankCodeError"></div>
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
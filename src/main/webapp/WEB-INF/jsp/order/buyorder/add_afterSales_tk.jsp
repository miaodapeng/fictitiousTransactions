<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增售后-退款" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/add_afterSales_th.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/controlRadio.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<div class="form-list  form-tips8">
    <form method="post" action="<%= basePath %>/order/buyorder/saveAddAfterSales.do">
        <ul>
            <li>
                <div class="form-tips">
                    <lable>售后类型</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <ul class="aftersales">
                            <li>
                                <a href="${pageContext.request.contextPath}/order/buyorder/addAfterSalesPage.do?flag=th&&buyorderId=${buyorder.buyorderId}">
                                    <input type="radio" name="type" value="546">
                                    <label>退货</label>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/order/buyorder/addAfterSalesPage.do?flag=hh&&buyorderId=${buyorder.buyorderId}">
                                    <input type="radio" name="type" value="547">
                                    <label>换货</label>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/order/buyorder/addAfterSalesPage.do?flag=tp&&buyorderId=${buyorder.buyorderId}" >
                                    <input type="radio" name="type" value="548">
                                    <label>退票</label>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/order/buyorder/addAfterSalesPage.do?flag=tk&&buyorderId=${buyorder.buyorderId}">
                                    <input type="radio" checked="true" name="type" value="549">
                                    <label>退款</label>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
            <input type="hidden" id="" name="orderId" value="${buyorder.buyorderId}" />
            <input type="hidden" id="" name="orderNo" value="${buyorder.buyorderNo}" />
            <input type="hidden" id="" name="traderId" value="${buyorder.traderId}" />
            <input type="hidden" id="shtype" value="tk" />
            <input type="hidden" name="formToken" value="${formToken}"/>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>详情说明</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks ">
                        <textarea name="comments" id="comments" placeholder="请详述客户退款要求以便售后同事联系" rows="5" class="wid90"></textarea>
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
                        <c:forEach items="${buyorder.tcList}" var="tc">
                        	<option value="${tc.traderContactId}" 
                        		<c:if test="${buyorder.traderContactId eq tc.traderContactId}">selected="selected"</c:if>>${tc.name}/${tc.mobile}<c:if test="${tc.telephone eq null}">/${tc.telephone}</c:if></option>
                        </c:forEach> 
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
                       <input type="text" name="refundAmount" id="refundAmount" value="" class="input-middle"/>
                       <input type="hidden" name="paid" id="paid" value="${buyorder.paid}" class="input-middle"/>
                   </div>
                   <div id="refundAmountError"></div>
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
                                <input type="radio" name="traderSubject" checked="checked" value="1">
                                <label>对公</label>
                            </li>
                            <li>
                               <input type="radio" name="traderSubject" value="2">
                               <label>对私</label>
                            </li>
                       </ul>    
                   </div>
                   <div id="traderSubjectError" class="font-red " style="display: none">交易主体不允许为空</div>
               </div>
           </li>
			<li>
               <div class="form-tips">
                   <span>*</span>
                   <lable>收款名称</lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks">
                   		<span>${buyorder.payee}</span>
                   		<span class="none">
                   			<input type="text" name="payee" id="payee"  class="input-largest"/>
                   		</span>
                       
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
                   		<span>${buyorder.bank}</span>
                   		<span class="none">
                       <input type="text" name="bank" id="bank"  class="input-largest"/>
                       </span>
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
                   		<span>${buyorder.bankAccount}</span>
                   		<span class="none">
                       <input type="text" name="bankAccount" id="bankAccount"  class="input-largest"/>
                       </span>
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
                   		<span>${buyorder.bankCode}</span>
                   		<span class="none">
                       <input type="text" name="bankCode" id="bankCode" value="" class="input-largest"/>
                       </span>
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
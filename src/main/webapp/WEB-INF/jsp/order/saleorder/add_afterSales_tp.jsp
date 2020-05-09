<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增售后-退票" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/add_afterSales_th.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/controlRadio.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<div class="form-list  form-tips5">
    <form method="post" action="<%= basePath %>/order/saleorder/saveAddAfterSales.do">
        <ul>
            <li>
                <div class="form-tips">
                    <lable>售后类型</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <ul class="aftersales">
                        <c:if test="${saleorder.thk eq 0 }">
                            <li>
                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=th&&saleorderId=${saleorder.saleorderId}">
                                    <input type="radio" name="type" value="539">
                                    <label>退货</label>
                                </a>
                            </li>
                            
                            <li>
                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=hh&&saleorderId=${saleorder.saleorderId}">
                                    <input type="radio" name="type" value="540">
                                    <label>换货</label>
                                </a>
                            </li>
                            </c:if>
                            <!-- 耗材订单不展示 安调与维修 -->
                            <c:if test="${saleorder.orderType ne 5}">
                            <li>
                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=at&&saleorderId=${saleorder.saleorderId}">
                                    <input type="radio" name="type" value="541">
                                    <label>安调</label>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=wx&&saleorderId=${saleorder.saleorderId}">
                                    <input type="radio" name="type" value="584">
                                    <label>维修</label>
                                </a>
                            </li>
                            </c:if>
                            <li>
                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=tp&&saleorderId=${saleorder.saleorderId}" >
                                    <input type="radio" checked="true" name="type" value="542">
                                    <label>退票</label>
                                </a>
                            </li>
                            <c:if test="${saleorder.thk eq 0 }">
                            <li>
                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=tk&&saleorderId=${saleorder.saleorderId}">
                                    <input type="radio" name="type" value="543">
                                    <label>退款</label>
                                </a>
                            </li>
                            </c:if>
                            <li>
                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=jz&&saleorderId=${saleorder.saleorderId}">
                                    <input type="radio" name="type" value="544">
                                    <label>技术咨询</label>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/order/saleorder/addAfterSalesPage.do?flag=qt&&saleorderId=${saleorder.saleorderId}">
                                    <input type="radio" name="type" value="545">
                                    <label>其他</label>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
            <input type="hidden" id="" name="orderId" value="${saleorder.saleorderId}" />
            <input type="hidden" id="" name="orderNo" value="${saleorder.saleorderNo}" />
            <input type="hidden" id="" name="traderId" value="${saleorder.traderId}" />
            <input type="hidden" id="shtype" value="tp" />
            <input type="hidden" name="formToken" value="${formToken}"/>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>售后原因</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <ul>
                        	<c:forEach items="${sysList}" var="sys" >
                        		<c:if test="${sys.relatedField eq 542 }">
                        			<li>
		                                <input type="radio" name="reason" value="${sys.sysOptionDefinitionId}">
		                                <label>${sys.title}</label>
		                            </li>
                        		</c:if>
                        	</c:forEach>
                        </ul>
                    </div>
                    <div id="reasonError" class="font-red " style="display: none">售后原因不允许为空</div>
                </div>
            </li>
            <li>
                <div class="parts">
                    <div class="title-container">
                        <div class="table-title nobor">
                            选择发票
                        </div>
                    </div>
                    <table class="table">
                        <thead>
                            <tr>
                                <th class="wid5">选择</th>
                                <th >发票号</th>
                                <th class="wid12">票种</th>
                                <th >红蓝字</th>
                                <th >发票金额</th>
                                <th >开票日期</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${saleorder.invoiceList}" var="invoice">
                       			<tr>
	                                <td>
	                                	<input type="checkbox" name="oneSelect" alt="${invoice.invoiceId}">
	                                	<input type="hidden" name="invoiceIds" >
	                                </td>
	                                 <td class=""> ${invoice.invoiceNo}</td>
	                                <td>
	                                	<c:if test="${invoice.invoiceType eq 429}">17%增值税专用发票</c:if>
	                                	<c:if test="${invoice.invoiceType eq 430}">17%增值税普通发票</c:if>
	                                	<c:if test="${invoice.invoiceType eq 682}">16%增值税专用发票</c:if>
										<c:if test="${invoice.invoiceType eq 681}">16%增值税普通发票</c:if>
										
										<c:if test="${invoice.invoiceType eq 972}">13%增值税专用发票</c:if>
										<c:if test="${invoice.invoiceType eq 971}">13%增值税普通发票</c:if>
										<c:if test="${invoice.invoiceType eq 683}">6%增值税普通发票</c:if>
			                        	<c:if test="${invoice.invoiceType eq 684}">6%增值税专用发票</c:if>
			                        	<c:if test="${invoice.invoiceType eq 685}">3%增值税普用发票</c:if>
			                        	<c:if test="${invoice.invoiceType eq 686}">3%增值税专用发票</c:if>
			                        	<c:if test="${invoice.invoiceType eq 687}">0%增值税普通发票</c:if>
			                        	<c:if test="${invoice.invoiceType eq 688}">0%增值税专用发票</c:if>
	                                </td>
	                                <td>
	                                	<c:if test="${invoice.colorType eq 1}">红字
	                                		<c:if test="${invoice.isEnable eq 1}">有效</c:if>
	                                		<c:if test="${invoice.isEnable eq 0}">作废</c:if>
	                                	</c:if>
	                                	<c:if test="${invoice.colorType eq 2}">蓝字
	                                		<c:if test="${invoice.isEnable eq 1}">有效</c:if>
	                                		<c:if test="${invoice.isEnable eq 0}">作废</c:if>
	                                	</c:if>
	                                </td>
	                                <td><fmt:formatNumber type="number" value="${invoice.amount}" pattern="0.00" maxFractionDigits="2" /></td>
	                                <td><date:date value ="${invoice.addTime}"/></td>
	                            </tr>
                        	</c:forEach>
                        </tbody>
                    </table>
                </div>
            </li>
            <li style="margin-top:-13px;">
                <div class="form-tips">
                    <span>*</span>
                    <lable>详情说明</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks ">
                        <textarea name="comments" id="comments" placeholder="请详述客户需求及安调维修要求以便售后同事联系" rows="5" class="wid90"></textarea>
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
                        <c:forEach items="${saleorder.tcList}" var="tc">
                        	<option value="${tc.traderContactId}" 
                        		<c:if test="${saleorder.traderContactId eq tc.traderContactId}">selected="selected"</c:if>>${tc.name}/${tc.mobile}<c:if test="${tc.telephone eq null}">/${tc.telephone}</c:if></option>
                        </c:forEach> 
                       </select>
                   </div>
                   <div id="traderContactIdError"></div>
               </div>
           </li>
            <li>
                <div class="form-tips">
                    <lable>上传附件</lable>
                </div>
                <input type="hidden" id="domain" name="domain" value="${domain}">
                <div class="f_left ">
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
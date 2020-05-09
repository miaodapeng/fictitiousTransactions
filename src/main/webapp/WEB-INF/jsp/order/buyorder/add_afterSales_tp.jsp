<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增售后-退票" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/add_afterSales_th.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/controlRadio.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<div class="form-list  form-tips5">
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
                                    <input type="radio" checked="true" name="type" value="548">
                                    <label>退票</label>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/order/buyorder/addAfterSalesPage.do?flag=tk&&buyorderId=${buyorder.buyorderId}">
                                    <input type="radio" name="type" value="549">
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
                        		<c:if test="${sys.relatedField eq 548 }">
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
                        	<c:forEach items="${buyorder.invoiceList}" var="invoice">
                        		 <tr>
	                                <td>
	                                	<input type="checkbox" name="oneSelect" alt="${invoice.invoiceId}">
	                                	<input type="hidden" name="invoiceId" >
	                                </td>
	                                 <td class=""> ${invoice.invoiceNo}</td>
	                                <td>
	                                	<c:if test="${invoice.invoiceType eq 429}">17%增值税专用发票</c:if>
	                                	<c:if test="${invoice.invoiceType eq 430}">17%增值税普通发票</c:if>
	                                </td>
	                                <td>蓝字有效</td>
	                                <td>${invoice.amount}</td>
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
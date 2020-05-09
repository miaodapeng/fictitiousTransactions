<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑财务信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/customer/edit_yxg_trader_finance.js?rnd=<%=Math.random()%>"></script>
<div class="formpublic">
    <form method="post" action="" id="myform">
        <input type="hidden" name="domain" value="${domain}"/>
        <ul>
            <li>
                <div class="infor_name">
                    <lable>注册地址</lable>
                </div>
                <div class="f_left">
                    <input type="text" <c:if test="${1 eq traderCustomer.source or 0 eq traderFinance.checkStatus}">disabled="disabled"</c:if> class="input-largest errobor" name="regAddress" id="regAddress" value="${traderFinance.regAddress}"/>
                </div>
            </li>
            <li>
                <div class="infor_name">
                    <lable>注册电话</lable>
                </div>
                <div class="f_left">
                    <input type="text" <c:if test="${1 eq traderCustomer.source or 0 eq traderFinance.checkStatus}">disabled="disabled"</c:if> class="input-largest errobor"  name="regTel" id="regTel" value="${traderFinance.regTel}"/>
                </div>
            </li>
            <li>
                <div class="infor_name">
                    <lable>税务登记号</lable>
                </div>
                <div class="f_left">
                    <input type="text" <c:if test="${0 eq traderFinance.checkStatus}">disabled="disabled"</c:if> class="input-largest errobor"  name="taxNum" id="taxNum" value="${traderFinance.taxNum}"/>
                </div>
            </li>
            <li>
                <div class="infor_name">
                    <lable>一般纳税人资质</lable>
                </div>
                <div class="f_left">
                    <input <c:if test="${1 eq traderCustomer.source or 0 eq traderFinance.checkStatus}">disabled="disabled"</c:if>  type="file" class="upload_file" id='file_1' name="lwfile" style="display: none;" onchange="uploadFile(this,1);">
                    <input <c:if test="${1 eq traderCustomer.source or 0 eq traderFinance.checkStatus}">disabled="disabled"</c:if> type="text" class="input-middle" id="uri_1" readonly="readonly"
                           placeholder="请上传一般纳税人资质" name="averageTaxpayerUri" onclick="file_1.click();" value ="${traderFinance.averageTaxpayerUri}">
                </div>
                <label class="bt-bg-style bt-middle bg-light-blue ml10" type="file" id="busUpload" onclick="return $('#file_1').click();">浏览</label>
                <!-- 上传成功出现 -->
                <c:choose>
                    <c:when test="${!empty traderFinance.averageTaxpayerUri}">
                        <i class="iconsuccesss ml7" id="img_icon_1"></i>
                        <a href="http://${traderFinance.averageTaxpayerDomain}${traderFinance.averageTaxpayerUri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_1">查看</a>
                        <span <c:if test="${1 eq traderCustomer.source }">disabled="disabled"</c:if> class="font-red cursor-pointer mt4" onclick="del(1)" id="img_del_1">删除</span>
                    </c:when>
                    <c:otherwise>
                        <i class="iconsuccesss ml7 none" id="img_icon_1"></i>
                        <a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_1">查看</a>
                        <span <c:if test="${1 eq traderCustomer.source or 0 eq traderFinance.checkStatus}">disabled="disabled"</c:if> class="font-red cursor-pointer mt4 none" onclick="del(1)" id="img_del_1">删除</span>
                    </c:otherwise>
                </c:choose>

            </li>
            <li>
                <div class="infor_name">
                    <lable>开户银行</lable>
                </div>
                <div class="f_left">
                    <input <c:if test="${1 eq traderCustomer.source or 0 eq traderFinance.checkStatus}">disabled="disabled"</c:if> type="text" class="input-largest errobor"  name="bank" id="bank" value="${traderFinance.bank}"/>
                </div>
            </li>
            <li>
                <div class="infor_name">
                    <lable>开户行支付联行号</lable>
                </div>
                <div class="f_left">
                    <input <c:if test="${1 eq traderCustomer.source or 0 eq traderFinance.checkStatus}">disabled="disabled"</c:if> type="text" class="input-largest errobor" onkeyup="this.value=this.value.replace(/[^\d]/g,'') " onafterpaste="this.value=this.value.replace(/[^\d]/g,'')"
                           placeholder="账号只允许输入阿拉伯数字" name="bankCode" id="bankCode" value="${traderFinance.bankCode}"/>
                </div>
            </li>
            <li>
                <div class="infor_name">
                    <lable>银行帐号</lable>
                </div>
                <div class="f_left">
                    <input <c:if test="${1 eq traderCustomer.source or 0 eq traderFinance.checkStatus}">disabled="disabled"</c:if> type="text" class="input-largest errobor" onkeyup="this.value=this.value.replace(/[^\d]/g,'') " onafterpaste="this.value=this.value.replace(/[^\d]/g,'')"
                           placeholder="账号只允许输入阿拉伯数字" name="bankAccount" id="bankAccount" value="${traderFinance.bankAccount}"/>
                    <div class="font-grey9 line20 mt4">
                        友情提醒
                        <br/> 1、申请增值税专用发票需要将以上信息全部正确地填写完整；
                    </div>
                </div>
            </li>
        </ul>

        <div class="add-tijiao tcenter">

            <c:if test="${isbelong and (traderFinance==null||traderFinance.checkStatus==null or traderFinance.checkStatus!=0)}">
                <button type="submit" id="submit">提交</button>
                <button type="button" class="dele" id="close-layer">取消</button>
            </c:if>
            <c:if test="${traderFinance.checkStatus==0 and financeCandidateUserMap['belong']}">

                <span class="bt-bg-style bg-light-green bt-small mr10 J-layer" onclick="checkOfYes()">审核通过</span>
                <span class="pop-new-data bt-bg-style bg-light-orange bt-small mr10 "
                      layerParams='{"width":"500px","height":"180px","title":"操作确认",
                      "link":"./yxgTraderFinanceComplement.do?traderId=${traderCustomer.traderId}&traderCustomerId=${traderCustomer.traderCustomerId}&traderFinanceId=${traderFinance.traderFinanceId}&taskId=${financeTaskInfo.id}&pass=false"}'>审核不通过</span>
            </c:if>
        </div>
        <input type="hidden" name="formToken" value="${formToken}"/>
        <input type="hidden" name="beforeParams" value='${beforeParams}'>
        <input type="hidden" name="traderFinanceId" value="${traderFinance.traderFinanceId}"/>
        <input type="hidden" id="preTaxNum" name="preTaxNum" value="${traderFinance.taxNum}"/>
        <input type="hidden" id="checkStatus" name="checkStatus" value="${traderFinance.checkStatus}"/>
        <input type="hidden" name="traderId" value="${traderCustomer.traderId}"/>
        <input type="hidden" name="traderCustomerId" value="${traderCustomer.traderCustomerId}"/>
        <input type="hidden" id="source" name="source" value="${traderCustomer.source}"/>
        <input type="hidden" name="traderType" id="traderType" value="1"/>
        <input type="hidden" name="pass" id="pass" value="true"/>
        <input type="hidden" name="taskId" id="taskId" value="${financeTaskInfo.id}"/>
    </form>
</div>
</body>

</html>
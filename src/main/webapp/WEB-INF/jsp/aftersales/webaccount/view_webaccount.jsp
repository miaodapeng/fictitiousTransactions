<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" value="注册用户信息" scope="application"/>
<%@ include file="../../common/common.jsp" %>
<style type="text/css">
    .transfer-title{
        color: #3384ef;
        padding-left: 10px;
        cursor: pointer;
        height: 34px;
        line-height: 34px;
    }
</style>
<div class="main-container">
    <div class="parts">
        <div class="title-container">
            <div class="table-title nobor">基本信息</div>
        </div>
        <table class="table">
            <tbody>
                <tr>
                    <td class="table-smallest">用户ID</td>
                    <td class="text-left">${webAccountVo.webAccountId }</td>
                    <td class="table-smallest">账户状态</td>
                    <td class="text-left">
                        <c:if test="${webAccountVo.isEnable eq 1}"><span class="onstate">正常</span></c:if>
                        <c:if test="${webAccountVo.isEnable eq 0}"><span class="offstate">禁用</span></c:if>
                    </td>
                </tr>
                <tr>
                    <td class="table-smallest">手机</td>
                    <td class="text-left" id="mobilePhone">${webAccountVo.mobile }</td>
                    <td class="table-smallest">注册来源</td>
                    <td class="text-left">
                        <c:choose>
                            <c:when test="${webaccount.from==3 or webaccount.from==4}">APP端</c:when>
                            <c:when test="${webaccount.from==1}">PC端</c:when>
                            <c:when test="${webaccount.from==2}">M端</c:when>
                            <c:when test="${webaccount.from==5}">其他</c:when>
                            <c:otherwise></c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td class="table-smallest">注册时间</td>
                    <td class="text-left"><date:date value="${webAccountVo.addTime} "/></td>
                    <td class="table-smallest">绑定微信ID</td>
                    <td class="text-left">${webAccountVo.weixinOpenid }</td>
                </tr>
                <tr>
                    <td class="table-smallest">真实姓名</td>
                    <td class="text-left">${webAccountVo.name }</td>
                    <td class="table-smallest">性别</td>
                    <td class="text-left">
                        <c:choose>
                            <c:when test="${webAccountVo.sex==0}">女</c:when>
                            <c:when test="${webAccountVo.sex==1}">男</c:when>
                            <c:otherwise>保密</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td class="table-smallest">公司名称</td>
                    <td class="text-left" colspan="3">${webAccountVo.companyName }</td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="parts">
        <div class="title-container">
            <div class="table-title nobor">资质信息</div>
            <c:choose>
                <c:when test="${traderCustomer.traderId==null||traderCustomer.traderCustomerId==null}">
                    <div class="title-click" onclick="layer.alert('未关联客户,无法转移。')">批量转移</div>
                </c:when>
                <c:when test="${bussinessCertificate == null and  secondCertificate == null and thirdCertificate == null }">
                    <div class="title-click" onclick="layer.alert('资质图片为空，无法操作。')">批量转移</div>
                </c:when>
                <c:otherwise>
                    <div class="title-click"
                         onclick="transferCertificate(${webAccountVo.erpAccountId},${traderCustomer.traderId},${traderCustomer.traderCustomerId},4)">
                        批量转移
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
        <table class="table">
            <tbody>
            <tr>
                <td class="table-smallest">营业执照</td>
                <td class="text-left" colspan="3">
                    <c:if test="${bussinessCertificate ne null }">
                        <c:forEach items="${bussinessCertificate}" var="business" varStatus="st">
                            <a href="http://${business.domain}${business.uri}" target="_blank">营业执照-${st.index + 1}</a>&nbsp;&nbsp;
                        </c:forEach>
                    </c:if>
                </td>
                <td class="table-smallest16 text-left">
                    <c:if test="${bussinessCertificate ne null }">
                        <div class="transfer-title" onclick="transferCertificate(${webAccountVo.erpAccountId},${traderCustomer.traderId},${traderCustomer.traderCustomerId},1)">转移</div>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="table-smallest">二类医疗资质</td>
                <td class="text-left" colspan="3">
                    <c:if test="${secondCertificate ne null }">
                        <c:forEach items="${secondCertificate}" var="second" varStatus="st">
                        <a href="http://${second.domain}${second.uri}" target="_blank">二类医疗资质-${st.index + 1}</a>&nbsp;&nbsp;
                        </c:forEach>
                    </c:if>
                </td>
                <td class="table-smallest16 text-left">
                    <c:if test="${secondCertificate ne null }">
                        <div class="transfer-title" onclick="transferCertificate(${webAccountVo.erpAccountId},${traderCustomer.traderId},${traderCustomer.traderCustomerId},2)">转移</div>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td class="table-smallest">三类医疗资质</td>
                <td class="text-left" colspan="3">
                    <c:if test="${thirdCertificate ne null }">
                        <c:forEach items="${thirdCertificate}" var="third" varStatus="st">
                            <a href="http://${third.domain}${third.uri}" target="_blank">三类医疗资质-${st.index + 1}</a>&nbsp;&nbsp;
                        </c:forEach>
                    </c:if>
                </td>
                <td class="table-smallest16 text-left">
                    <c:if test="${thirdCertificate ne null }">
                        <div class="transfer-title" onclick="transferCertificate(${webAccountVo.erpAccountId},${traderCustomer.traderId},${traderCustomer.traderCustomerId},3)">转移</div>
                    </c:if>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <form method="post" action="${pageContext.request.contextPath}/aftersales/webaccount/saveedit.do" id="myform">
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">关联信息</div>
            </div>
            <table class="table">
                <tbody>
                <tr>
                    <td class="table-smallest">关联客户</td>
                    <td class="text-left" colspan="3">
                        <span id="traderName">${traderCustomer.traderName }</span>
                        <span class="bt-small bt-bg-style bg-light-blue" onclick="searchCustomer(${webAccountVo.registerPlatform})">搜索</span>
                        <span id="searchTrader" class="pop-new-data"
                              layerParams='{"width":"900px","height":"500px","title":"搜索客户","link":"${pageContext.request.contextPath}/ordergoods/manage/searchtradercustomer.do"}'></span>
                    </td>
                </tr>
                <tr>
                    <td class="table-smallest">联系人</td>
                    <td class="text-left" colspan="3">
                        <select name="traderContactId" id="traderContactId" style="width: 300px;">
                            <option value="0" title="请选择">请选择</option>
                            <c:if test="${not empty  contactList}">
                                <c:forEach items="${contactList }" var="contact">
                                    <option value="${contact.traderContactId }" <c:if test="${contact.traderContactId eq webAccountVo.traderContactId}">selected="selected"</c:if>
                                            title="${contact.mobile }">${contact.name }|${contact.mobile }</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="table-smallest">联系地址</td>
                    <td class="text-left" colspan="3">
                        <select name="traderAddressId" id="traderAddressId" style="width: 500px;">
                            <option value="0">请选择</option>
                            <c:if test="${not empty  addressList}">
                                <c:forEach items="${addressList}" var="address">
                                    <option value="${address.traderAddress.traderAddressId }"
                                            <c:if test="${address.traderAddress.traderAddressId eq webAccountVo.traderAddressId}">selected="selected"</c:if>>${address.area }&nbsp;${address.traderAddress.address }</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="table-smallest">备注</td>
                    <td class="text-left" colspan="3">
                        <c:if test="${not empty webAccountVo.maybeSaler }">${webAccountVo.maybeSaler } (根据ERP中信息判断可能归属，仅供参考)</c:if>
                    </td>
                </tr>
                <tr>
                    <td class="table-smallest"><span class="warning-color1">*</span>分配</td>
                    <td class="text-left" colspan="3">
                        <select name="userId" id="userId" style="width: 150px;">
                            <option value="0">请选择</option>
                            <c:forEach items="${userList}" var="list">
                                <option value="${list.userId}" <c:if test="${webAccountVo.userId eq list.userId}">selected="selected"</c:if>>${list.username}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <%--<tr>--%>
                    <%--<td class="table-smallest"><span class="warning-color1">*</span>贝登精选会员</td>--%>
                    <%--<td class="text-left" colspan="3">--%>
                        <%--<input type="radio" name="isVedengJx" id="isVedengJx_y" value="1" <c:if test="${webAccountVo.isVedengJx eq 1}">checked</c:if>><label>是</label>--%>
                        <%--&nbsp;&nbsp;&nbsp;&nbsp;--%>
                        <%--<input type="radio" name="isVedengJx" id="isVedengJx_n" value="0" <c:if test="${webAccountVo.isVedengJx eq 0}">checked</c:if>><label>否</label>--%>
                    <%--</td>--%>
                <%--</tr>--%>
                </tbody>
            </table>
        </div>
        <div class="add-tijiao tcenter">
            <input type="hidden" name="beforeParams" value='${beforeParams}'/>
            <input type="hidden" name="traderId" id="traderId" value="${traderCustomer.traderId}">
            <input type="hidden" name="traderCustomerId" id="traderCustomerId" value="${traderCustomer.traderCustomerId}"/>
            <input type="hidden" name="erpAccountId" id="erpAccountId" value="${webAccountVo.erpAccountId }"/>
            <input type="hidden" name="belongPlatform" id="belongPlatform" value="${webAccountVo.belongPlatform}"/>
            <input type="hidden" name="mobile" id="mobile" value="${webAccountVo.mobile }"/>
            <button type="submit">提交</button>
        </div>
    </form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/aftersales/webaccount/view_webaccount.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp" %>

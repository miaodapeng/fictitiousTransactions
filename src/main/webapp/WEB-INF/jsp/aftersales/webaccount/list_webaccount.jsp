<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" value="注册用户列表" scope="application"/>
<%@ include file="../../common/common.jsp" %>
<div class="searchfunc">
    <form method="post" action="${pageContext.request.contextPath}/aftersales/webaccount/list.do" id="search">
        <ul>
            <li><label class="infor_name">用户ID</label>
                <input type="text" class="input-middle" name="webAccountId" id="webAccountId" value="${webAccountVo.webAccountId }" maxlength="8" onkeyup="value=value.replace(/[^\d]/g,'')"/>
            </li>
            <li><label class="infor_name">手机号码</label>
                <input type="text" class="input-middle" name="mobile" id="mobile" value="${webAccountVo.mobile }"/>
            </li>
            <li><label class="infor_name">公司名称</label>
                <input type="text" class="input-middle" name="companyName" id="companyName" value="${webAccountVo.companyName }"/>
            </li>
            <li><label class="infor_name">创建时间</label>
                <input class="Wdate f_left input-smaller96 m0" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="startAddDateStr" id="startAddDateStr" value="${startAddDateStr}">
                <div class="f_left ml1 mr1 mt4">-</div>
                <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="endAddDateStr" id="endAddDateStr" value="${endAddDateStr}">
            </li>
            <li><label class="infor_name" style="overflow: visible">贝登会员开通时间</label>
                <input class="Wdate f_left input-smaller96 m0" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="startMemberDateStr" id="startMemberDateStr" value="${startMemberDateStr}">
                <div class="f_left ml1 mr1 mt4">-</div>
                <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="endMemberDateStr" id="endMemberDateStr" value="${endMemberDateStr}">
            </li>
            <li><label class="infor_name">账号状态</label>
                <select class="input-middle f_left" name="isEnable">
                    <option value="-1">请选择</option>
                    <option value="1" <c:if test="${webAccountVo.isEnable eq 1}">selected="selected"</c:if>>正常</option>
                    <option value="0" <c:if test="${webAccountVo.isEnable eq 0}">selected="selected"</c:if>>禁用</option>
                </select>
            </li>
            <li><label class="infor_name">归属销售</label>
                <select class="input-middle f_left" name="userId">
                    <option value="0">请选择</option>
                    <c:forEach items="${userList}" var="list">
                        <option value="${list.userId}" <c:if test="${webAccountVo.userId eq list.userId}">selected="selected"</c:if>>${list.username}</option>
                    </c:forEach>
                </select>
            </li>
            <li><label class="infor_name">关联状态</label>
                <select class="input-middle f_left" name="relateStatus">
                    <option value="-1">请选择</option>
                    <option value="1" <c:if test="${webAccountVo.relateStatus eq 1}">selected="selected"</c:if>>是</option>
                    <option value="0" <c:if test="${webAccountVo.relateStatus eq 0}">selected="selected"</c:if>>否</option>
                </select>
            </li>
            <li><label class="infor_name">分配状态</label>
                <select name="assignStatus">
                    <option value="-1">请选择</option>
                    <option value="1" <c:if test="${webAccountVo.assignStatus eq 1}">selected="selected"</c:if>>已分配</option>
                    <option value="0" <c:if test="${webAccountVo.assignStatus eq 0}">selected="selected"</c:if>>未分配</option>
                </select>
            </li>
            <li><label class="infor_name">贝登商城会员</label>
                <select class="input-middle f_left" name="isVedengMember">
                    <option value="-1">请选择</option>
                    <option value="1" <c:if test="${webAccountVo.isVedengMember eq 1}">selected="selected"</c:if>>是</option>
                    <option value="0" <c:if test="${webAccountVo.isVedengMember eq 0}">selected="selected"</c:if>>否</option>
                </select>
            </li>

            <li><label class="infor_name">归属平台</label>
                <select class="input-middle f_left" name="belongPlatform">
                    <option value="">请选择</option>
                    <option value="1" <c:if test="${webAccountVo.belongPlatform eq 1}">selected="selected"</c:if>>贝登医疗</option>
                    <option value="2" <c:if test="${webAccountVo.belongPlatform eq 2}">selected="selected"</c:if>>医械购</option>
                    <option value="3" <c:if test="${webAccountVo.belongPlatform eq 3}">selected="selected"</c:if>>科研购</option>
                    <option value="5" <c:if test="${webAccountVo.belongPlatform eq 5}">selected="selected"</c:if>>其他</option>
                </select>
            </li>
            <li><label class="infor_name">注册平台</label>
                <select class="input-middle f_left" name="registerPlatform">
                    <option value="">请选择</option>
                    <option value="1" <c:if test="${webAccountVo.registerPlatform eq 1}">selected="selected"</c:if>>贝登医疗</option>
                    <option value="2" <c:if test="${webAccountVo.registerPlatform eq 2}">selected="selected"</c:if>>医械购</option>
                    <option value="3" <c:if test="${webAccountVo.registerPlatform eq 3}">selected="selected"</c:if>>科研购</option>
                    <option value="5" <c:if test="${webAccountVo.registerPlatform eq 5}">selected="selected"</c:if>>其他</option>
                </select>
            </li>
            <li><label class="infor_name">注册来源</label>
                <select class="input-middle f_left" name="from">
                    <option value="">请选择</option>
                    <option value="6" <c:if test="${webAccountVo.from eq 6}">selected="selected"</c:if>>APP端</option>
                    <option value="1" <c:if test="${webAccountVo.from eq 1}">selected="selected"</c:if>>PC端</option>
                    <option value="2" <c:if test="${webAccountVo.from eq 2}">selected="selected"</c:if>>M端</option>
                    <option value="5" <c:if test="${webAccountVo.from eq 5}">selected="selected"</c:if>>其他</option>
                </select>
            </li>
        </ul>
        <div class="tcenter">
			<span class="bt-small bg-light-blue bt-bg-style mr20 " onclick="search();" id="searchSpan">搜索</span> <span
                class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
        </div>
    </form>
</div>
<div class="content">
    <div class="list-page normal-list-page">
        <div class="fixdiv">
            <table class="table table-bordered table-striped table-condensed table-centered">
                <thead>
                <tr>
                    <th class="wid8">用户ID</th>
                    <th class="wid10">手机</th>
                    <th class="wid15">创建时间</th>
                    <th class="wid8">帐户状态</th>
                    <th class="wid22">公司名称</th>
                    <th class="wid22">关联公司名</th>
                    <th class="wid8">分配状态</th>
                    <th class="wid10">归属销售</th>
                    <th class="wid10">贝登商城会员</th>
                    <th class="wid10">贝登会员开通时间</th>
                    <th class="wid10">归属平台</th>
                    <th class="wid10">注册平台</th>
                    <th class="wid10">注册来源</th>
                    <th class="wid15">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty list}">
                    <c:forEach items="${list }" var="webaccount" varStatus="status">
                        <tr>
                            <td>${webaccount.erpAccountId }</td>
                            <td>${webaccount.mobile }</td>
                            <td><date:date value="${webaccount.addTime}"/></td>
                            <td>
                                <c:if test="${webaccount.isEnable eq 1}"><span class="onstate">正常</span></c:if>
                                <c:if test="${webaccount.isEnable eq 0}"><span class="offstate">禁用</span></c:if>
                            </td>
                            <td>${webaccount.companyName }</td>
                            <td><a class="addtitle" href="javascript:void(0);"
                                   tabTitle='{"num":"viewcustomer<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"${pageContext.request.contextPath}/trader/customer/getFinanceAndAptitude.do?traderId=${webaccount.traderCustomerVo.traderId}&traderCustomerId=${webaccount.traderCustomerVo.traderCustomerId}&customerNature=${webaccount.traderCustomerVo.customerNature}&aptitudeStatus=${webaccount.traderCustomerVo.aptitudeStatus}",
											"title":"财务与资质信息"}'>${webaccount.relateComapnyName }</a></td>
                            <td>
                                <c:choose>
                                    <c:when test="${webaccount.userId > 0}">
                                        <span class="onstate">已分配</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="offstate">未分配</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${webaccount.owner }</td>
                            <td>${webaccount.isVedengMember==1?"是":"否"}</td>
                            <td> <fmt:formatDate value="${webaccount.vedengMemberTime}" type="date" pattern='yyyy-MM-dd HH:mm:ss'/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${webaccount.belongPlatform==1}">贝登医疗</c:when>
                                    <c:when test="${webaccount.belongPlatform==2}">医械购</c:when>
                                    <c:when test="${webaccount.belongPlatform==3}">科研购</c:when>
                                    <c:when test="${webaccount.belongPlatform==5}">其他</c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${webaccount.registerPlatform==1}">贝登医疗</c:when>
                                    <c:when test="${webaccount.registerPlatform==2}">医械购</c:when>
                                    <c:when test="${webaccount.registerPlatform==3}">科研购</c:when>
                                    <c:when test="${webaccount.registerPlatform==5}">其他</c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${webaccount.from==3 or webaccount.from==4}">APP端</c:when>
                                    <c:when test="${webaccount.from==1}">PC端</c:when>
                                    <c:when test="${webaccount.from==2}">M端</c:when>
                                    <c:when test="${webaccount.from==5}">其他</c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <span class="edit-user addtitle" tabTitle='{"num":"viewwebaccount${webaccount.erpAccountId}","link":"./aftersales/webaccount/view.do?erpAccountId=${webaccount.erpAccountId}","title":"查看"}'>查看</span>
                                <span class="edit-user" onclick="resetPassword(${webaccount.erpAccountId});">重置密码</span>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
        </div>
        <c:if test="${empty list }">
            <!-- 查询无结果弹出 -->
            <div class="noresult">查询无结果！</div>
        </c:if>
    </div>
    <div>
        <tags:page page="${page}"/>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/aftersales/webaccount/list_webaccount.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../../common/footer.jsp" %>

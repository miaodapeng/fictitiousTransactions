<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" value="注册用户列表" scope="application"/>
<%@ include file="../../common/common.jsp" %>
<div class="searchfunc">
    <form method="post" action="${pageContext.request.contextPath}/trader/accountweb/list.do" id="search">
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
            <li><label class="infor_name">申请加入时间</label>
                <input class="Wdate f_left input-smaller96 m0" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="startJoinDateStr" id="startJoinDateStr" value="${startJoinDateStr}">
                <div class="f_left ml1 mr1 mt4">-</div>
                <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="endJoinDateStr" id="endJoinDateStr" value="${endJoinDateStr}">
            </li>
            <li><label class="infor_name" style="overflow: visible">贝登会员开通时间</label>
                <input class="Wdate f_left input-smaller96 m0" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="startMemberDateStr" id="startMemberDateStr" value="${startMemberDateStr}">
                <div class="f_left ml1 mr1 mt4">-</div>
                <input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker()" name="endMemberDateStr" id="endMemberDateStr" value="${endMemberDateStr}">
            </li>
            <li><label class="infor_name">账号状态</label>
                <select name="isEnable">
                    <option value="-1">请选择</option>
                    <option value="1" <c:if test="${webAccountVo.isEnable eq 1}">selected="selected"</c:if>>正常</option>
                    <option value="0" <c:if test="${webAccountVo.isEnable eq 0}">selected="selected"</c:if>>禁用</option>
                </select>
            </li>
            <li><label class="infor_name">关联状态</label>
                <select name="relateStatus">
                    <option value="-1">请选择</option>
                    <option value="1" <c:if test="${webAccountVo.relateStatus eq 1}">selected="selected"</c:if>>是</option>
                    <option value="0" <c:if test="${webAccountVo.relateStatus eq 0}">selected="selected"</c:if>>否</option>
                </select>
            </li>
            <li><label class="infor_name">贝登商城会员</label>
                <select class="input-middle f_left" name="isVedengMember">
                    <option value="-1">请选择</option>
                    <option value="1" <c:if test="${webAccountVo.isVedengMember eq 1}">selected="selected"</c:if>>是</option>
                    <option value="0" <c:if test="${webAccountVo.isVedengMember eq 0}">selected="selected"</c:if>>否</option>
                </select>
            </li>
        </ul>
        <div class="tcenter">
			<span class="bt-small bg-light-blue bt-bg-style mr20 " onclick="search();" id="searchSpan">搜索</span>
            <span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
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
                    <th class="wid15">申请加入时间</th>
                    <th class="wid8">帐户状态</th>
                    <th class="wid22">公司名称</th>
                    <th class="wid22">关联公司名</th>
                    <th class="wid10">贝登商城会员</th>
                    <th class="wid10">贝登会员开通时间</th>
                    <th class="wid15">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:if test="${not empty list}">
                    <c:forEach items="${list }" var="webaccount" varStatus="status">
                        <tr>
                            <td>${webaccount.webAccountId }</td>
                            <td>${webaccount.mobile }</td>
                            <td><date:date value="${webaccount.addTime}"/></td>
                            <td><date:date value="${webaccount.modTimeJoin}"/></td>
                            <td>
                                <c:if test="${webaccount.isEnable eq 1}"><span class="onstate">正常</span></c:if>
                                <c:if test="${webaccount.isEnable eq 0}"><span class="offstate">禁用</span></c:if>
                            </td>
                            <td>${webaccount.companyName }</td>
                            <td>${webaccount.relateComapnyName }</td>
                            <td>${webaccount.isVedengMember==1?"是":"否"}</td>
                            <td> <fmt:formatDate value="${webaccount.vedengMemberTime}" pattern='yyyy-MM-dd HH:mm:ss'/></td>
                            <td>
                                <span class="edit-user addtitle" tabTitle='{"num":"viewaccountweb${webaccount.erpAccountId}","link":"./trader/accountweb/view.do?erpAccountId=${webaccount.erpAccountId}","title":"查看"}'>查看</span>
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

<%@ include file="../../common/footer.jsp" %>

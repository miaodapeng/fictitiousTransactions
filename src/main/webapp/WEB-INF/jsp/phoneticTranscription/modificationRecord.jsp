<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="全局录音修正管理" scope="application" />
<%@ include file="../common/common.jsp"%>
<div class="main-container" >

    <div class="list-pages-search">
        <form action="${pageContext.request.contextPath}/phoneticTranscription/phonetic/viewRecordList.do" method="post" id="search" >

            <ul>
                <li>
                    <label class="label_info">来源时间：</label>
                    <input type="text" class="input-middle f_left Wdate" name="addTimeStartStr" id="addTimeStartStr" value="${modificationRecordVo.addTimeStartStr}"
                           placeholder="请输入起始时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'addTimeEndStr\')}'})" >
                    <label style="width: 10px;text-align: center;">-</label>
                    <input style="float: right;" type="text" class="input-middle f_left Wdate" name="addTimeEndStr" id="addTimeEndStr" value="${modificationRecordVo.addTimeEndStr}"
                           placeholder="请输入结束时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'addTimeStartStr\')}'})" >
                </li>

                <li>
                    <label class="label_info">修改时间：</label>
                    <input type="text" class="input-middle f_left Wdate" name="modTimeStartStr" id="modTimeStartStr" value="${modificationRecordVo.modTimeStartStr}"
                           placeholder="请输入起始时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'modTimeEndStr\')}'})" >
                    <label style="width: 10px;text-align: center;">-</label>
                    <input style="float: right;" type="text" class="input-middle f_left Wdate" name="modTimeEndStr" id="modTimeEndStr" value="${modificationRecordVo.modTimeEndStr}"
                           placeholder="请输入结束时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'modTimeStartStr\')}'})" >
                </li>

                <li>
                    <label class="label_info">来源人：</label>
                    <select class="input-middle selectpicker" name="creator" data-live-search="true" onchange="">
                        <option value="-1">全部</option>
                        <c:if test="${not empty userList }">
                            <c:forEach items="${userList }" var="user">
                                <option value="${user.userId }" <c:if test="${modificationRecordVo.creator eq user.userId }">selected="selected"</c:if>>${user.username }</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </li>

                <li>
                    <label class="label_info">修正前：</label>
                    <input type="text" class="input-middle f_left" name="controversialContent" placeholder="" maxlength="20" value="${modificationRecordVo.controversialContent}">
                </li>

                <li>
                    <label class="label_info">修正后：</label>
                    <input type="text" class="input-middle f_left" name="modifyContent" placeholder="" maxlength="20" value="${modificationRecordVo.modifyContent}">
                </li>

                <li>
                    <label class="label_info">修改人：</label>
                    <select class="input-middle selectpicker" name="updater" data-live-search="true" onchange="">
                        <option value="-1">全部</option>
                        <c:if test="${not empty userList }">
                            <c:forEach items="${userList }" var="user">
                                <option value="${user.userId }" <c:if test="${modificationRecordVo.updater eq user.userId }">selected="selected"</c:if>>${user.username }</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </li>

            </ul>








           <%-- <div class="searchContent" style="width: 468px;">
                <label class="label_info">来源时间：</label>
                <input type="text" class="input-middle f_left Wdate" name="addTimeStartStr" id="addTimeStartStr" value="${modificationRecordVo.addTimeStartStr}"
                       placeholder="请输入起始时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'addTimeEndStr\')}'})" >
                <label style="width: 10px;text-align: center;">-</label>
                <input style="float: right;" type="text" class="input-middle f_left Wdate" name="addTimeEndStr" id="addTimeEndStr" value="${modificationRecordVo.addTimeEndStr}"
                       placeholder="请输入结束时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'addTimeStartStr\')}'})" >
            </div>

            <div class="searchContent" style="width: 468px;">
                <label class="label_info">修改时间：</label>
                <input type="text" class="input-middle f_left Wdate" name="modTimeStartStr" id="modTimeStartStr" value="${modificationRecordVo.modTimeStartStr}"
                       placeholder="请输入起始时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'modTimeEndStr\')}'})" >
                <label style="width: 10px;text-align: center;">-</label>
                <input style="float: right;" type="text" class="input-middle f_left Wdate" name="modTimeEndStr" id="modTimeEndStr" value="${modificationRecordVo.modTimeEndStr}"
                       placeholder="请输入结束时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'modTimeStartStr\')}'})" >
            </div>

            <div class="searchContent">
                <label class="label_info">来源人：</label>
                <select class="input-middle selectpicker" name="creator" data-live-search="true" onchange="">
                    <option value="-1">全部</option>
                    <c:if test="${not empty userList }">
                        <c:forEach items="${userList }" var="user">
                            <option value="${user.userId }" <c:if test="${modificationRecordVo.creator eq user.userId }">selected="selected"</c:if>>${user.username }</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>

            <div class="searchContent">
                <label class="label_info">修正前：</label>
                <input type="text" class="input-middle f_left" name="controversialContent" placeholder="" maxlength="20" value="${modificationRecordVo.controversialContent}">
            </div>

            <div class="searchContent">
                <label class="label_info">修正后：</label>
                <input type="text" class="input-middle f_left" name="modifyContent" placeholder="" maxlength="20" value="${modificationRecordVo.modifyContent}">
            </div>

            <div class="searchContent">
                <label class="label_info">修改人：</label>
                <select class="input-middle selectpicker" name="updater" data-live-search="true" onchange="">
                    <option value="-1">全部</option>
                    <c:if test="${not empty userList }">
                        <c:forEach items="${userList }" var="user">
                            <option value="${user.userId }" <c:if test="${modificationRecordVo.updater eq user.userId }">selected="selected"</c:if>>${user.username }</option>
                        </c:forEach>
                    </c:if>
                </select>
            </div>


            <br>--%>
            <div class="tcenter">
                <span class="bg-light-blue bt-bg-style bt-small" onclick="search();" id="searchSpan">搜索</span>
                <span class="bt-small bg-light-blue bt-bg-style" onclick="resetForm();">重置</span>
            </div>
        </form>
    </div>

    <div class='list-page'>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
            <tr class="sort">
                <th class="wid10">序号</th>
                <th class="wid10">修正前</th>
                <th class="wid10">修正后</th>
                <th class="wid10">来源录音</th>
                <th class="wid10">来源人</th>
                <th class="wid10">来源时间</th>
                <th class="wid10">操作</th>
                <th class="wid10">更新人</th>
                <th class="wid10">更新时间</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${not empty recordList}">
                <c:forEach items="${recordList}" var="list" varStatus="s">
                    <tr>
                        <td>${s.count}</td>
                        <td>
                            <span>${list.controversialContent}</span>
                        </td>
                        <td>
                            <span>${list.modifyContent}</span>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${list.relatedId eq 0}">
                                    无
                                </c:when>
                                <c:otherwise>
                                    <span class="font-blue pop-new-data"
                                          layerParams='{"width":"90%","height":"90%","title":"查看详情","link":"${pageContext.request.contextPath}/phoneticTranscription/phonetic/viewContent.do?communicateRecordId=${list.relatedId}"}'>${list.relatedId}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            ${list.creatorName}
                        </td>
                        <td>
                            ${list.addTimeStr}
                        </td>
                        <td>
                           <span class="edit-user pop-new-data" layerParams='{"width":"450px","height":"200px","title":"编辑记录","link":"./showRecord.do?id=${list.modificationRecordId}"}'>修改</span>
                            <span class="delete" onclick="del(${list.modificationRecordId});">删除</span>
                        </td>
                        <td>
                            ${list.updaterName}
                        </td>
                        <td>
                            ${list.modTimeStr}
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
        <c:if test="${empty recordList}">
            <!-- 查询无结果弹出 -->
            <div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
        </c:if>
    </div>
    <div style="float:left;">
        <span class="bt-small bt-bg-style bg-light-blue f_right pop-new-data" layerparams='{"width":"450px","height":"200px","title":"新增修正记录","link":"./addRecordFromList.do"}'>新增</span>
    </div>
    <tags:page page="${page}"/>
</div>
<style>
    .label_info{
        float: left;
        margin-top: 4px;
    }
    .bootstrap-select .dropdown-toggle .filter-option{
        padding-top: 0 !important;
        padding-right: 0 !important;
        padding-bottom: 0 !important;
        padding-left: inherit !important;
        top: 2px !important;
    }
    .bootstrap-select>.dropdown-toggle {
        height: 26px;
        width: 92% !important;
    }
    .bootstrap-select .dropdown-menu{
        min-width: 90% !important;
    }
</style>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/static/js/phoneticTranscription/recordList.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../common/footer.jsp"%>

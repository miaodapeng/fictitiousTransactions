<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="title" value="通话记录" scope="application" />	
<%@ include file="../common/common.jsp"%>
<style>
	.commentList{
		position:relative;

		height:32px;

		overflow:hidden;
	}

</style>
<div class="content">
	<div class="searchfunc">
		<form
			action="${pageContext.request.contextPath}/system/call/getrecord.do"
			method="post" id="search">
			<ul>
				<li><label class="infor_name">录音ID</label> <input type="text" maxlength="10"
					class="input-middle" name="communicateRecordId"
					id="communicateRecordId"
					value="${communicateRecord.communicateRecordId }"></li>
				<li><label class="infor_name">号码</label> <input type="text"
					class="input-middle" name="phone" id="phone"
					value="${communicateRecord.phone }"></li>
				<li><label class="infor_name">公司名称</label> <input type="text"
					class="input-middle" name="traderName" id="traderName"
					value="${communicateRecord.traderName }"></li>
				<li><label class="infor_name">话务人员</label> <select
					class="input-middle" name=creator>
						<option value="0">全部</option>
						<c:forEach items="${userList}" var="user">
							<option value="${user.userId}"
								<c:if test="${communicateRecord.creator eq user.userId}">selected="selected"</c:if>>${user.username}</option>
						</c:forEach>
				</select></li>
				<li><label class="infor_name">通话方式</label> <select
					class="input-middle" name="coidType">
						<option value="0">全部</option>
						<option value="1"
							<c:if test="${communicateRecord.coidType == 1}">selected="selected"</c:if>>呼入</option>
						<option value="2"
							<c:if test="${communicateRecord.coidType == 2}">selected="selected"</c:if>>呼出</option>
				</select></li>
				<li><label class="infor_name">通话结果</label> <select
					class="input-middle" name="result">
						<option value="0">全部</option>
						<option value="1"
							<c:if test="${communicateRecord.result == 1}">selected="selected"</c:if>>接通</option>
						<option value="2"
							<c:if test="${communicateRecord.result == 2}">selected="selected"</c:if>>未接通</option>
				</select></li>
				<li><label class="infor_name">通话时间</label> <input
					class="Wdate f_left input-smaller96 m0" type="text"
					placeholder="请选择日期"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddate\')}'})"
					name="begindate" id="begindate"
					value="${communicateRecord.begindate }">
					<div class="f_left ml1 mr1 mt4">-</div> <input
					class="Wdate f_left input-smaller96" type="text"
					placeholder="请选择日期"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'begindate\')}'})"
					name="enddate" id="enddate" value="${communicateRecord.enddate }"></li>
				<li><label class="infor_name">通话时长(秒)</label>
					<input type="text" name="beginValue" id="beginValue" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')" placeholder="请输入通话时长" class="f_left input-smaller96 m0" value="${communicateRecord.beginValue }"/>
					<div class="f_left ml1 mr1 mt4">-</div>
					<input type="text"  name="endValue" id="endValue" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')" placeholder="请输入通话时长" class="f_left input-smaller96 m0" value="${communicateRecord.endValue }"/>
				</li>
				<li><label class="infor_name">是否点评</label> <select
						class="input-middle" name="isComment">
					<option value="-1">全部</option>
					<option value="1"
							<c:if test="${communicateRecord.isComment == 1}">selected="selected"</c:if>>已点评</option>
					<option value="0"
							<c:if test="${communicateRecord.isComment == 0}">selected="selected"</c:if>>未点评</option>
				</select></li>
			</ul>
			<div class="tcenter">
				<span class="bg-light-blue bt-bg-style bt-small" onclick="search();"
					id="searchSpan">搜索</span> <span
					class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
			</div>
		</form>
	</div>
	<div>
		<table
			class="table table-bordered table-striped table-condensed table-centered">
			<thead>
				<tr>
					<th>录音ID</th>
					<th>通话时间</th>
					<th>通话方式</th>
					<th>号码</th>
					<th>号码归属地</th>
					<th>公司名称</th>
					<th>所属人员</th>
					<th>通话结果</th>
					<th>话务人员</th>
					<th>通话时长(秒)</th>
					<th>点评内容</th>
					<th>录音（点击播放）</th>
				</tr>
			</thead>

			<tbody>
				<c:if test="${not empty recordList}">
					<c:forEach items="${recordList }" var="list">
						<tr>
							<td>${list.communicateRecordId }</td>
							<td><date:date value="${list.addTime} " /></td>
							<td><c:choose>
									<c:when test="${list.coidType == 2}">
									呼出
									</c:when>
									<c:otherwise>
									呼入
									</c:otherwise>
								</c:choose></td>
							<td>${list.phone}</td>
							<td>${list.phoneArea}</td>
							<td>${list.traderName}</td>
							<td>${list.ownerUsername}</td>
							<td><c:choose>
									<c:when test="${list.coidLength > 0}">
										<span class="font-green">接通</span>
									</c:when>
									<c:otherwise>
										<sapn class="font-red">未接通</sapn>
									</c:otherwise>
								</c:choose></td>
							<td>${list.creatorName}</td>
							<td>${list.coidLength}</td>
							<td>
							<c:if test="${not empty list.commentList}">
								<div class="customername pos_rel">
									<div class=""><span>
										<div class="commentList">
											<c:set var="startIndex" value="${fn:length(list.commentList)-1 }"></c:set>
												${list.commentList[startIndex].content}<br>
											<date:date value ="${list.commentList[startIndex].addTime}" format="yyyy-MM-dd HH:mm:ss"/><br>
									</div>
                              <i class="iconbluemouth"></i> <br>
                            </span></div>

									<div class="pos_abs customernameshow mouthControlPos">
										<c:forEach items="${list.commentList }" var="comment">
											${comment.content}<br>
											<date:date value ="${comment.addTime}" format="yyyy-MM-dd HH:mm:ss"/><br>
										</c:forEach>
									</div>
								</div>

							</c:if>
							</td>
							<td><c:if test="${not empty list.coidUri}">
								  <c:if test="${list.isTranslation eq 1}">
									  <span class="edit-user pop-new-data"
											layerParams='{"width":"90%","height":"90%","title":"查看详情","link":"${pageContext.request.contextPath}/phoneticTranscription/phonetic/viewContent.do?communicateRecordId=${list.communicateRecordId}"}'>查看</span>
								  </c:if>
									<span class="edit-user"
										onclick="playrecord('${list.coidUri}');">播放</span>
								</c:if></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<c:if test="${empty recordList }">
			<!-- 查询无结果弹出 -->
			<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
		</c:if>
		<tags:page page="${page}" />
	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/call/record_list.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../common/footer.jsp"%>
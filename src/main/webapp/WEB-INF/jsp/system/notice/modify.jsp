<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增编辑公告" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="content">
	<div class='newemployee  formpublic'>
		<form method="post"
			action='<c:choose><c:when test="${not empty notice and notice.noticeId > 0 }">${pageContext.request.contextPath}/system/notice/saveedit.do</c:when><c:otherwise>${pageContext.request.contextPath}/system/notice/saveadd.do</c:otherwise></c:choose>'
			id="myform">
			<ul>
				<li>
					<div class="infor_name">
						<span>*</span> <label>公告类型</label>
					</div>
					<div>
						<select class="input-middle" name="noticeCategory"
							id="noticeCategory">
							<option value="0">请选择</option>
							<c:forEach items="${categoryList }" var="cate">
								<option value="${cate.sysOptionDefinitionId }"
									<c:if test="${notice.noticeCategory != null and notice.noticeCategory == cate.sysOptionDefinitionId}">selected="selected"</c:if>>
									${cate.title }</option>
							</c:forEach>
						</select>
					</div>
					<div class="clear"></div>
				</li>
				<li>
					<div class="infor_name">
						<span>*</span> <label>公告标题</label>
					</div>
					<div>
						<input type="text" class="input-largest" name="title" id="title"
							value="${notice.title}" />
					</div>
					<div class="clear"></div>
				</li>
				<li class="mt5">
					<div class="infor_name mt0">
						<span>*</span> <label>公告内容</label>
					</div>
					<div class="f_left table-largest">
						<script id="content" name="content" type="text/plain"
							style="width: 100%; height: 500px;">${notice.content}</script>
					</div>
				</li>
			</ul>
			<div class="add-tijiao employeesubmit">
				<c:if test="${not empty notice and notice.noticeId > 0 }">
					<input type="hidden" name="noticeId" value="${notice.noticeId }">
					<input type="hidden" name="beforeParams" value='${beforeParams}'/>
				</c:if>
				<input type="hidden" name="formToken" value="${formToken}"/>
				<button id="submit" type="submit">提交</button>
			</div>
		</form>
	</div>
</div>
<!-- 以下ueditor编辑器需要引用的文件 -->
<script type="text/javascript" charset="utf-8" src="<%= basePath %>static/libs/textmodify/ueditor.config.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" charset="utf-8" src="<%= basePath %>static/libs/textmodify/ueditor.all.min.js?rnd=<%=Math.random()%>"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="<%= basePath %>static/libs/textmodify/lang/zh-cn/zh-cn.js?rnd=<%=Math.random()%>"></script>
<style type="text/css">
    div{
        width:100%;
    }
</style>
<!-- 以上ueditor编辑器需要引用的文件 -->
<!-- 以下为异步上传附件要引用的文件 -->
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<!-- 以上为异步上传附件要引用的文件 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/notice/modify.js?rnd=<%=Math.random()%>"></script>
<!-- 以下为UEditor插件要引用的文件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/file/edit_uedit.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript">
	UE.getEditor('content');
	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	UE.Editor.prototype.getActionUrl = function(action) {
		if (action == 'uploadimage' || action == 'uploadscrawl'
				|| action == 'uploadimage' || action == 'uploadfile'
				|| action == 'uploadvideo') {//上传附件类型
			return '${path}fileUpload/ueditFileUpload.do?uploadType=uedit';//自定义编辑器中附件上传路径
		} else {
			return this._bkGetActionUrl.call(this, action);//插件默认上传
		}
	}
</script>
<%@ include file="../../common/footer.jsp"%>
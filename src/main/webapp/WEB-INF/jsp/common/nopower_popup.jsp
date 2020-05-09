<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/base.css?rnd=<%=Math.random()%>">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/general.css?rnd=<%=Math.random()%>">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/manage.css?rnd=<%=Math.random()%>">
<script type="text/javascript">
function closewin(){
	var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    parent.layer.close(index);
}
</script>
<div class="tips-frame">
   <div class="tips-content pt20">
       <i class="icongantanhao" style="margin-bottom:-9px;"></i>
       <span>您没有操作权限</span>
   </div>
   <div class="tips-confirm pt25">
       <button class="bt-small bt-bg-style  bg-light-green" type="button" onclick="closewin();" id='close-layer'>好的</button>
   </div>
</div>
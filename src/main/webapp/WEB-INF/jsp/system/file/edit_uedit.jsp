<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>员工管理</title>
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
    <%-- <script type="text/javascript" src="<%= basePath %>static/libs/textmodify/ueditor.base.js?rnd=<%=Math.random()%>"></script> --%>
	<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
	<!-- 以上为异步上传附件要引用的文件 -->
	
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/file/edit_uedit.js?rnd=<%=Math.random()%>"></script>
</head>
<body>
    <div class='newemployee formpublic'>
        <form method="post" enctype="multipart/form-data" id="userform">
        	<input type="hidden" id="filePath" name="filePath"/>
            <ul>
                <li>
                    <div class="infor_name">
                    	<span>*</span>
                        <label>上传ftp</label>
                    </div>
                    <div class="f_left">
                    	<input type="file" name="lwfile" id="lwfile1" onchange="uploadFileFtp(this)"/>
                    </div>             
                    <div class="clear"></div>
                </li>
                <li>
                    <div class="infor_name">
                    	<span>*</span>
                        <label>上传ftp</label>
                    </div>
                    <div class="f_left">
                    	<input type="file" name="lwfile" id="lwfile2" onchange="uploadFileFtp(this)"/>
                    </div>             
                    <div class="clear"></div>
                </li>
                <li>
                    <div class="infor_name">
                    	<span>*</span>
                        <label>下载</label>
                    </div>   
                    <div class="f_left">        
                    	<input type="text" name="yeFile" id="yeFile" />
                    	<input type="button" value="下载" onclick="downFile();" />
                    	<input type="button" value="删除" onclick="deleteFile();" />
                    </div>    
                    <div class="clear"></div>
                </li>
                <span> ---------------------------------------------------------------------------------------------</span>
                <li>
                    <div class="infor_name">
                    	<span>*</span>
                        <label>上传服务器</label>
                    </div>
                    <div class="f_left">
                    	<input type="file" name="lwfile" id="lwfile3" onchange="uploadFileServe(this)"/>
                    </div>             
                    <div class="clear"></div>
                </li>
            </ul>
            <script id="editor" type="text/plain" style="width:800px;height:500px;"></script>
            <script id="editor1" type="text/plain" style="width:800px;height:500px;"></script>
            <div class="add-tijiao employeesubmit">
                <button id="submit" type="submit">提交</button>
                <button class="dele" type="button">取消</button>
            </div>
        </form>
    </div>
</body>
<!-- 以下为UEditor插件要引用的文件 -->
<script type="text/javascript">
	UE.getEditor('editor');
	UE.getEditor('editor1');
	
	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	UE.Editor.prototype.getActionUrl = function(action) {
		if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage' || action == 'uploadfile' || action == 'uploadvideo') {//上传附件类型
			return '${path}fileUpload/ueditFileUpload.do?uploadType=uedit';//自定义编辑器中附件上传路径
		} else {
			return this._bkGetActionUrl.call(this, action);//插件默认上传
		}
	}
</script>
</html>
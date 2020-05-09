<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="批量新增库位" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
	$(document).ready(function(){
    	$("input[name='safile']").on('change', function( e ){
            //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
            var name = e.currentTarget.files[0].name;
            $("#uri").val(name);
        });
    })
    function subForm(){
		$("#errorTite").hide();
		if($("#safile").val() == undefined || $("#safile").val() == ""){
			layer.alert("请选择需要上传的文件！");$("#uri").focus();
			return;
		}
		$("#batchAddStorageLocation").ajaxSubmit({
			async : false,
			url : './batchSaveStorageLocation.do',
			data : $("#batchAddStorageLocation").serialize(),
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.code==0){
					layer.alert(
			                '操作成功', 
			                {icon: 1,closeBtn: 0 },
			                function () {
			                	window.parent.location.reload();
			             	});
					
				}else{
					$("#errorTite").html(data.message);
					$("#errorTite").show();
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
	}
</script>
<div class="form-list  form-tips4 ml7">
	<form id="batchAddStorageLocation" method="post" enctype="multipart/form-data">
		<ul>
			<li style="margin-bottom:8px;">
				<div class="form-tips">
					<lable>请上传表格</lable>
				</div>
				<div class='f_left'>
					<div class="form-blanks">
						<div class="pos_rel">
							<input type="file" class="upload_file" style="display: none;" name="safile" id="safile">
							<input type="text" class="input-middle" id="uri" placeholder="请上传excel" name="uri" readonly="readonly"> 
							<label class="bt-bg-style bt-middle bg-light-blue" type="file" onclick="return $('#safile').click();">浏览</label>
						</div>
					</div>
					<div class="pop-friend-tips" style="margin: 7px 0 0 0;">
						如果您没有标准模板，请<a href="<%= basePath %>static/template/批量新增库位模板.xlsx">下载标准模板</a>
					</div>
				</div>
			</li>
		</ul>
		<div class="font-red ml47 mb20" id="errorTite" style="display: none">表格项错误，第x行x列...</div>
		<div class="add-tijiao tcenter">
			<button type="button" class="bt-bg-style bg-deep-green" onclick="subForm();">提交</button>
			<button class="dele" type="button" id="close-layer">取消</button>
		</div>
	</form>
</div>
<%@ include file="../../common/footer.jsp"%>
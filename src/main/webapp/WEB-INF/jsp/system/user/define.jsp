<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="列表配置" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8 trader-customer-accountperiodapply">
	<form method="post" action="" id="myform">
		<ul>
			<li>
				<div class="form-tips">
					<label>选择列表</label>
				</div>
				<div class="f_left" style="width: 75%">
					<div class="form-blanks" id="roleDiv">
						<ul>
							<li><input type="checkbox" name="field" value="orgName"> <label>所属部门</label></li>
							<li><input type="checkbox" name="field" value="positionName"> <label>职位</label></li>
							<li><input type="checkbox" name="field" value="pUsername"> <label>直接上级</label></li>
							<li><input type="checkbox" name="field" value="number"> <label>工号</label></li>
							<li><input type="checkbox" name="field" value="ccNumber"> <label>分机号</label></li>
							<li><input type="checkbox" name="field" value="isDisabled"> <label>状态</label></li>
							<li><input type="checkbox" name="field" value="addTime"> <label>创建时间</label></li>
							<li><input type="checkbox" name="field" value="lastLoginTime"> <label>上次登录时间</label></li>
						</ul>
					</div>
				</div>
			</li>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="bussiness" value="/system/user/index.do">
			<input type="hidden" name="fields" value="">
			<input type="hidden" name="unfields" value="">
			<button type="submit" id="submit">提交</button>
			<button class="dele" id="close-layer" type="button">取消</button>
		</div>
	</form>
</div>

<script type="text/javascript">
$(function(){
	$("input[name='field']").click(function(){
		//集合
		var fields = '';
		var unfields = '';
		//$.each($("input[name='field']:checked"),function(i,n){
		//	fields += $(this).val()+',';
		//});
		//集合
		$("input[name='field']").each(function(){
			if($(this).is(':checked')){
				fields += $(this).val()+',';
			}else{
				unfields += $(this).val()+',';
			}
		});
		$("input[name='fields']").val(fields);
		$("input[name='unfields']").val(unfields);
	});
	
	$('#myform').submit(function() {
		checkLogin();
		jQuery.ajax({
			url:page_url+'/define/savemydefine.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			beforeSend:function()
			{  
				if($("input[name='field']:checked").length <= 0){
					layer.alert("列表不能为空");
					return  false;
				}
			},
			success:function(data)
			{
				refreshPageList(data);
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		});
		return false;
	});
});
</script>
<%@ include file="../../common/footer.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="formpublic">
	<form method="post" action="" id="myform">
		
		<ul>
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable>地区名</lable>
				</div>
				<div class="f_left">
					<div>
						<input type="text" class="input-middle" name="regionName" id="regionName" value="${region.regionName }"/>
					</div>
					<div>
						<span class="font-red ci" style="display: none;"></span>
					</div>
				</div>
			</li>
			
		</ul>
		<div class="add-tijiao tcenter mt2">
			<input type="hidden" name="regionId" value='${region.regionId}'/>
			<input type="hidden" name="parentId" value='${region.parentId}'/>
			<button type="submit" id="sub">提交</button>
			<button type="button" class="dele" id="close-layer">取消</button>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript">
	$(function(){
		$("#sub").click(function(){
			if($("#regionName").val() == ''){
				$("#regionName").addClass("errorbor");
				$("#regionName").parent('div').siblings("div").find(".ci").css("display","").html("地区名不允许为空");
				return false;
			}else{
				$("#regionName").removeClass("errorbor");
				$("#regionName").parent('div').siblings("div").find(".ci").css("display","none");
			}
			if($("#regionName").val().length > 120){
				$("#regionName").addClass("errorbor");
				$("#regionName").parent('div').siblings("div").find(".ci").css("display","").html("地区名不允许超过120个字符");
				return false;
			}else{
				$("#regionName").removeClass("errorbor");
				$("#regionName").parent('div').siblings("div").find(".ci").css("display","none");
			}
			$.ajax({
				url:page_url+'/system/region/saveEditRegion.do',
				data:$('#myform').serialize(),
				type:"POST",
				dataType : "json",
				async: false,
				success:function(data)
				{
					if(data.code==0){
						window.parent.location.reload();
					}else{
						layer.alert(data.message);
					}
					
				},
				error:function(data){
					if(data.status ==1001){
						layer.alert("当前操作无权限")
					}
				}
			});
			return false;
			
			
			
		})
		
		
	})

</script>
<%@ include file="../../common/footer.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增地区" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="formpublic">
	<form method="post" action="" id="myform">
		<input type="hidden" name="formToken" value="${formToken}"/>
		<ul>
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable>添加级别</lable>
				</div>
				<div class="f_left">
					<div>
						<input type="radio"  name="grade" value="1" checked="checked"/>区县
						<input type="radio"  name="grade" value="2"/>市
					</div>
					<div>
					</div>
				</div>
			</li>
			<li>
				<div class="infor_name">
					<span>*</span>
					<lable>省</lable>
				</div>
				<div class="f_left">
					<div>
						<select class="input-small mr6" name="province" id="province">
							<option value="0">请选择</option>
							<c:if test="${not empty provinceList }">
								<c:forEach items="${provinceList }" var="prov">
									<option value="${prov.regionId }"
										<c:if test="${province eq prov.regionId }">selected="selected"</c:if>>${prov.regionName }</option>
								</c:forEach>
							</c:if>
						</select>
					</div>
					<div>
						<span class="font-red pr" style="display: none;"></span>
					</div>
				</div>
			</li>
			<li id="city1">
				<div class="infor_name">
					<span>*</span>
					<lable>市</lable>
				</div>
				<div class="f_left">
					<div>
						<select class="input-small mr6" name="city" id="city">
							<option value="0">请选择</option>
						</select>
					</div>
					<div>
						<span class="font-red ci" style="display: none;"></span>
					</div>
				</div>
			</li>
			<li id="city2" class="none">
				<div class="infor_name">
					<span>*</span>
					<lable>市</lable>
				</div>
				<div class="f_left">
					<div>
						<input type="text" class="input-middle" name="cityName" id="cityName"/>
					</div>
					<div>
						<span class="font-red ci" style="display: none;"></span>
					</div>
				</div>
			</li>
			<li id="c3">
				<div class="infor_name">
					<span>*</span>
					<lable>区/县</lable>
				</div>
				<div class="f_left">
					<div>
						 <input type="text" class="input-middle" name="zone" id="zone"/>
					</div>
					<div>
						<span class="font-red zo" style="display: none;"></span>
					</div>
				</div>
			</li>
		</ul>
		<div class="add-tijiao tcenter mt2">
			<input type="hidden" name="beforeParams" value='${beforeParams}'/>
			<button type="submit" id="sub">提交</button>
			<button type="button" class="dele" id="close-layer">取消</button>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript">
	$(function(){
		$("input:radio[name='grade']").click(function(){
			var grade=$('input:radio[name="grade"]:checked').val();
			if(grade == 1){
				$("#city1").show();
				$("#city2").hide();
				$("#c3").show();
			}else{
				$("#city1").hide();
				$("#city2").show();
				$("#c3").hide();
			}
		})
		
		$("#sub").click(function(){
			var grade=$('input:radio[name="grade"]:checked').val();
			if($("#province").val() == 0){
				$("#province").addClass("errorbor");
				$("#province").parent('div').siblings("div").find(".pr").css("display","").html("省份不允许为空");
				
				return false;
			}else{
				$("#province").removeClass("errorbor");
				$("#province").parent('div').siblings("div").find(".pr").css("display","none");
			}
			if(grade == 1){
				if($("#city").val() == 0){
					$("#city").addClass("errorbor");
					$("#city").parent('div').siblings("div").find(".ci").css("display","").html("地市不允许为空");
					return false;
				}else{
					$("#city").removeClass("errorbor");
					$("#city").parent('div').siblings("div").find(".ci").css("display","none");
				}
				if($("#zone").val() == ''){
					$("#zone").addClass("errorbor");
					$("#zone").parent('div').siblings("div").find(".zo").css("display","").html("区县不允许为空");
					return false;
				}else{
					$("#zone").removeClass("errorbor");
					$("#zone").parent('div').siblings("div").find(".zo").css("display","none");
				}
			}else{
				if($("#cityName").val() == ''){
					$("#cityName").addClass("errorbor");
					$("#cityName").parent('div').siblings("div").find(".ci").css("display","").html("地市不允许为空");
					return false;
				}else{
					$("#cityName").removeClass("errorbor");
					$("#cityName").parent('div').siblings("div").find(".ci").css("display","none");
				}
			}
			
			$.ajax({
				url:page_url+'/system/region/saveAddRegion.do',
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

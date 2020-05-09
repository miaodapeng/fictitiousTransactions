<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑参数配置" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
<!--

//-->
$(document).ready(function(){
	$('#submit').click(function() {
		checkLogin();
		$.each($(".one"),function(i,n){
			var orgId = $(this).attr("name");
			var username = $(this).val();
			var name = $(this).attr("alt");
			$(this).siblings("input").val(orgId+"|"+username+"|"+name);
		});
		$.ajax({
			url:page_url+'/home/page/saveEditConfigParams.do',
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
	});
});

</script>
<div class="form-list form-tips11">
    <form method="post" action="" id="myform">
        <ul>
        	<c:forEach items="${orgList}" var="org">
        		<li>
	                <div class="form-tips">
	                    <span>*</span>
	                    <lable>${org.orgName}</lable>
	                </div>
	                <div class="f_left ">
	                    <div class="form-blanks">
	                        <select name="${org.orgId}" alt="${org.orgName}" class="one">
	                        	<c:forEach items="${serviceUserList }" var="user">
	                        		<c:set var="contains" value="false" /> 
	                        			<c:forEach items="${saledefaultList}" var ="pa">
			                        		 <c:if test="${user.username eq pa.paramsValue && org.orgId eq pa.title}">
			                        		 	<c:set var="contains" value="true" /> 
			                        		 </c:if>
			                        	</c:forEach>
		                        		 <c:choose>  
							    			<c:when test="${contains == true }">
							    				<option value="${user.username }" selected="selected">${user.username}</option>
							    			</c:when>
			                        		<c:otherwise>
			                        		 	<option value="${user.username }">${user.username}</option>
			                        		</c:otherwise> 
		                        		</c:choose>
		                        		 
	                        	</c:forEach>
	                        </select>
	                        
	                        <input type="hidden" name="orgCharges" />
	                    </div>
	                </div>
	            </li>
        	</c:forEach>
        	<c:forEach items="${saledefaultList}" var ="pa">
         		<input type="hidden" name="paramsConfigIds" value="${pa.paramsConfigId}" >
         	</c:forEach>
        	<li>
               <div class="form-tips">
                   <span>*</span>
                   <lable>默认负责人</lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks">
                       <select name="defaultCharge">
                       	<c:forEach items="${serviceUserList }" var="user">
                       		<option value="${user.username}" <c:if test="${user.username eq defaults.paramsValue}">selected="selected"</c:if> >${user.username}</option>
                       	</c:forEach>
                       </select>
                       <c:if test="${not empty defaults.paramsConfigId}">
                        	<input type="hidden" name="paramsConfigIds" value="${defaults.paramsConfigId}" >
                       </c:if>
                   </div>
               </div>
           </li>
        </ul>
        <input type="hidden" name="beforeParams" value='${beforeParams}'>
        <div class="add-tijiao tcenter">
            <button type="submit" id="submit">提交</button>
            <button type="button" class="dele" id="close-layer">取消</button>
        </div>
    </form>
</div>
</body>
</html>
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
		$(".warning").remove();
		$("select").removeClass("errorbor");
		if($("#paramsValue").val() == ''){
			warnErrorTips("paramsValue","paramsValueError","报价咨询默认负责人不允许为空");//文本框ID和提示用语
			return false;
		}
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
<div class="form-list form-tips9">
    <form method="post" action="" id="myform">
        <ul>
        	<li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>报价咨询默认负责人</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <select name="paramsValue" id="paramsValue">
                        	<c:forEach items="${userList }" var="user">
                        		<option value="${user.userId }" <c:if test="${user.userId eq params.paramsValue}">selected="selected"</c:if> >${user.username}</option>
                        	</c:forEach>
                        </select>
                        <c:if test="${not empty params.paramsConfigId}">
                        	<input type="hidden" name="paramsConfigIds" value="${params.paramsConfigId}" >
                        </c:if>
                    </div>
                    <div id="paramsValueError"></div>
                    <div class="pop-friend-tips mt5">
						友情提示：<br/>
						1、当销售咨询临时产品时，发送给报价咨询默认负责人；
		            </div>
                </div>
            </li>
        </ul>
        
        <div class="add-tijiao tcenter">
        	<input type="hidden" name="beforeParams" value='${beforeParams}'>
            <button type="submit" id="submit">提交</button>
            <button type="button" class="dele" id="close-layer">取消</button>
        </div>
    </form>
</div>
</body>
</html>
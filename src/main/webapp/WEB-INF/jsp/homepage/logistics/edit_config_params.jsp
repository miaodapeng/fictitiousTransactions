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
		$("input").removeClass("errorbor");
		var numReg = /^[1-9]\d*$/;
		if($("#logistics").val() == ''){
			warnErrorTips("logistics","logisticsError","快递超时天数不允许为空");//文本框ID和提示用语
			return false;
		}
		if(!numReg.test($("#logistics").val())){
			warnErrorTips("logistics","logisticsError","快递超时天数不符合规则");//文本框ID和提示用语
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
<div class="form-list form-tips8">
    <form method="post" action="" id="myform">
        <ul>
        	<li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>快递超时天数</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" name="logistics" id="logistics" value="${logistics.paramsValue}" class="input-middle">天
                        <c:if test="${not empty logistics.paramsConfigId}">
                        	<input type="hidden" name="paramsConfigIds" value="${logistics.paramsConfigId}" >
                        </c:if>
                        
                    </div>
                    <div id="logisticsError"></div>
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
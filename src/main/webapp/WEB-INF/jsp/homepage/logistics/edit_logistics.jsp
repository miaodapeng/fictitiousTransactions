<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑快递" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
<!--

//-->
$(document).ready(function(){
	$('#submit').click(function() {
		checkLogin();
		$(".warning").remove();
		$("input").removeClass("errorbor");
		var numReg = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
		if($("#name").val() == ''){
			warnErrorTips("name","nameError","快递公司名称不允许为空");//文本框ID和提示用语
			return false;
		}
		if(numReg.test($("#name").val())){
			warnErrorTips("name","nameError","快递公司名称不允许使用特殊字符");//文本框ID和提示用语
			return false;
		}
		if($("#name").val().length > 16){
			warnErrorTips("name","nameError","快递公司名称长度在1-16个字符之间");//文本框ID和提示用语
			return false;
		}
		$.ajax({
			url:page_url+'/home/page/saveEditLogistics.do',
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
                    <lable>新增快递公司</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                    	<input type="hidden" name="logisticsId" id="logisticsId" class="input-middle" value="${logistics.logisticsId }">
                        <input type="text" name="name" id="name" class="input-middle" value="${logistics.name }">
                    </div>
                    <div id="nameError"></div>
                </div>
            </li>
        	<li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>是否启用</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
                            <li>
                                <input type="radio" name="isEnable" <c:if test="${logistics.isEnable eq 1}">checked="checked"</c:if> value="1" >
                                <label>启用</label>
                            </li>
                            <li>
                               <input type="radio" name="isEnable" value="0" <c:if test="${logistics.isEnable eq 0}">checked="checked"</c:if>>
                               <label>禁用</label>
                            </li>
                       </ul>   
                    </div>
                    <div id="isEnableError"></div>
                    <div class="pop-friend-tips mt5">
						友情提示：<br/>
						1、快递公司应用于销售订单指定快递公司的下拉项列表；
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
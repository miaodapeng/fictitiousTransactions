<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑售后对象" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" >
	$(function(){
		$("#add_submit").click(function(){
			checkLogin();
			$(".warning").remove();
			$("textarea").removeClass("errorbor");
			if($("#comments").val().length > 128){
				warnErrorTips("comments","commentsError","备注不允许超过128个字符");//文本框ID和提示用语
				return false;
			}
			$.ajax({
				url:page_url+'/aftersales/order/saveEditAfterTrader.do',
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
		})
	})
	
	
</script>
<div class="formpublic">
	<div class="form-list form-tips8">
    <form method="post" action="" id="myform">
        <ul>
        	<li>
            	<div class="form-tips">
                    <lable>售后对象</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                    	<input type="hidden" name="afterSalesTraderId" value="${ast.afterSalesTraderId}">
                    	<span>${ast.traderName}</span>
                    </div>
                </div>
            </li>
           	<li>
               <div class="form-tips">
                   <lable>对象类型</lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks">
                   		<select id="" name="traderType"  class="input-large">
							<option value="1" <c:if test="${ast.traderType eq 1 }">selected="selected" </c:if>>客户</option>
							<option value="2" <c:if test="${ast.traderType eq 2 }">selected="selected" </c:if>>供应商</option>
						</select>
                   </div>
               </div>
           </li>
           	<li>
                <div class="form-tips">
                    <lable>备注</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks ">
                        <textarea style="margin:0px;width: 400px;height: 300px" name="comments" id="comments">${ast.comments}</textarea>
                    </div>
                    <div id="commentsError"></div>
                </div>
            </li>  
        </ul>
        <div class="add-tijiao tcenter">
            <button type="button" class="bt-bg-style bg-light-green bt-small" id="add_submit">确定</button>
            <button class="dele" type="button" id="close-layer">取消</button>
		</div>
        </form>
    </div>
</div>
<%@ include file="../../common/footer.jsp"%>

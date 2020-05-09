<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增售后对象" scope="application" />	
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
				url:page_url+'/aftersales/order/saveAddAfterTrader.do',
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
                    	<input type="hidden" name="traderId" value="${ast.traderId}">
                    	<input type="hidden" name="realTraderType" value="${ast.realTraderType}">
                    	<input type="hidden" name="traderName" value="${ast.traderName}">
                    	<input type="hidden" name="afterSalesId" value="${ast.afterSalesId}">
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
							<option value="1" <c:if test="${ast.realTraderType eq 1 }">selected="selected"</c:if>>客户</option>
							<option value="2" <c:if test="${ast.realTraderType eq 2 }">selected="selected"</c:if>>供应商</option>
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
                        <textarea style="margin:0px;width: 400px;height: 300px" name="comments" id="comments"></textarea>
                    </div>
                    <div id="commentsError"></div>
                </div>
            </li>  
        </ul>
        <div class="add-tijiao tcenter">
        	<input type="hidden" name="formToken" value="${formToken}"/>
            <button type="button" class="bt-bg-style bg-light-green bt-small" id="add_submit">确定</button>
            <button class="dele" type="button" id="close-layer">取消</button>
		</div>
        </form>
    </div>
</div>
<%@ include file="../../common/footer.jsp"%>

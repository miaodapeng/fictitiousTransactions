<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增售后过程" scope="application" />
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" >
	$(function(){
		$("#add_submit").click(function(){
			checkLogin();
			var content = $("#content").val();
			if(content ==''){
				warnErrorTips("content","contentError","沟通内容不允许为空");//文本框ID和提示用语
				return false;
			}
			if(content.length > 1024){
				warnErrorTips("content","contentError","沟通内容不允许超过1024个字符");//文本框ID和提示用语
				return false;
			}
			$.ajax({
				url:page_url+'/aftersales/order/saveAddAfterSalesRecord.do',
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
    <div class="form-list  form-tips4">
        <form method="post" action="" id="myform">
            <ul>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>沟通内容</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks ">
                         	<textarea name="content" id="content" placeholder="请详述售后过程内容" rows="5" class="wid60"></textarea>
	                     </div>
	                     <div id="contentError"></div>
                    </div>
                </li>
            </ul>
           <div class="add-tijiao tcenter">
           		<input type="hidden" name="formToken" value="${formToken}"/>
               	<input type="hidden" name="afterSalesId" value="${afterSalesRecord.afterSalesId}" >
               	<input type="hidden" name="afterSalesRecordId" value="${afterSalesRecord.afterSalesRecordId}"/>
                <button type="submit" id="add_submit">提交</button>
                <button class="dele" type="button" id="close-layer">取消</button>
			</div>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>
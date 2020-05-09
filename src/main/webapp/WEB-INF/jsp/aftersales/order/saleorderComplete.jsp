<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="售后原因" scope="application" />
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" >
	$(function(){
		$("#add_submit").click(function(){
			checkLogin();
			var comments = $("#comments").val();

			if(comments.length > 256){
				warnErrorTips("comments","commentsError","备注内容不允许超过256个字符");//文本框ID和提示用语
				return false;
			}
			$.ajax({
				url:page_url+'/aftersales/order/editConfirmComplete.do',
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

	<div class="form-list  form-tips5">
		<form action="" method="post" id="myform" enctype="multipart/form-data">
			<ul>
				<li>
					<div class="infor_name">
                        <span>*</span>
                        <lable>售后原因：</lable>
                    </div>
                    <div class="f_left ">
                    	<div class="form-blanks " style= "width:600px;">
                    	<ul>
                           <c:forEach var="list" items="${afterSalesTypes}" varStatus="status">
                           		<li>
                           		<c:choose>
                           			<c:when test="${status.first }">
                           				<input name="afterSalesStatusReson" type="radio" value="${list.sysOptionDefinitionId }" checked="checked"/>
                               			<span>${list.comments }</span>
                           			</c:when>
                           			<c:otherwise>
                               			<input name="afterSalesStatusReson" type="radio" value="${list.sysOptionDefinitionId }" />
                               			<span>${list.comments }</span>
                               		</c:otherwise>
                           		</c:choose>
                               </li>
                           </c:forEach>
                         </ul> 
                    	</div>
                    	<div id="typeError"></div>
                    </div>
				</li>
				
				<li>
					<div class="infor_name">
                        <lable>备注</lable>
                    </div>
					
					<div class="f_left ">
                        <div class="form-blanks ">
                         	<textarea name="afterSalesStatusComments" id="comments" placeholder="" rows="5" class="wid60"></textarea>
	                     </div>
	                     <div id="commentsError"></div>
                    </div>
				</li>
			</ul>
			
			<div class="add-tijiao tcenter">
				<input type="hidden" name="afterSalesId" value="${afterSalesRecord.afterSalesId }">
				<input type="hidden" name="subjectType" value="${subjectType }">
				<input type="hidden" name="orderId" value="${orderId }">
				<input type="hidden" name="traderId" value="${traderId }">
				<input type="hidden" name="type" value="${type }">
				<input type="hidden" name="sku" value="${sku }">
				<input type="hidden" name="formToken" value="${formToken }">
				<button type="submit" id="add_submit">确认</button>
                <button class="dele" type="button" id="close-layer">取消</button>
			</div>
		</form>
	</div>
	<%@ include file="../../common/footer.jsp"%>
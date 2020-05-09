<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="提前采购申请" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
	$(function(){
		$("#sub").click(function(){
			checkLogin();
			if($("#advancePurchaseComments").val()==''){
				warnErrorTips("advancePurchaseComments","advancePurchaseCommentsError","提前采购原因不允许为空");//文本框ID和提示用语
				return false;
			}
			if($("#advancePurchaseComments").val().length > 512){
				warnErrorTips("advancePurchaseComments","advancePurchaseCommentsError","提前采购原因不允许超过512个字符");//文本框ID和提示用语
				return false;
			}
			$.ajax({
				url:page_url+'/order/saleorder/saveApplyPurchase.do',
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
	<div class="form-list form-tips7">
		<form method="post" id="myform">
			<ul>
				
				<li>
					<div class="form-tips">
						<span>*</span>
						<lable>提前采购原因</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<!-- onkeydown="if(event.keyCode==13)return false;" -->
							<input type="text" class="input-largest" name="advancePurchaseComments" id="advancePurchaseComments" />
							<!-- onkeypress="javascript:return gosumit('${invoiceApply.relatedId}');" -->
						</div>
						<div id="advancePurchaseCommentsError"></div>
					</div>
				</li>
				
			</ul>
			<div class="pop-friend-tips">
				友情提醒<br />
				1、提前采购需经过审核，请关注审核进度。
			</div>
			<div class="add-tijiao tcenter">
				<input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
				<input type="hidden" name="formToken" value="${formToken}"/>
				<input type="hidden" name="taskId" value="${taskId}">
				<button type="button" class="bt-bg-style bg-light-green bt-small" id="sub">确定</button>
				<button class="dele" type="button" id="close-layer">取消</button>
			</div>
		</form>
	</div>
<%@ include file="../../common/footer.jsp"%>
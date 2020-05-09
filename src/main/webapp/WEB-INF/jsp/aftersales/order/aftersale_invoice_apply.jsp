<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="开票申请" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" >
$(function(){
	$("#add_submit").click(function(){
		checkLogin();
		var content = $("#comments").val();
		
		if(content !='' && content.length > 512){
			warnErrorTips("comments","commentsError","备注不允许超过512个字符");//文本框ID和提示用语
			return false;
		}
		$.ajax({
			url:page_url+'/aftersales/order/saveOpenInvoiceApply.do',
			data:$('#myform').serialize(),
			type:"POST",
			dataType : "json",
			async: false,
			success:function(data)
			{
				if(data.code==0){
					var div = '<div class="tip-loadingNewData" style="position:fixed;width:100%;height:100%; z-index:100; background:rgba(0,0,0,0.3)"><i class="iconloadingblue" style="position:absolute;left:50%;top:32%;"></i></div>';
					parent.$("body").prepend(div); //jq获取上一页框架的父级框架；
					window.parent.location.reload();
				}else{
					warn2Tips("commentsError",data.message);//文本框ID和提示用语
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
						<lable>开票方式</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<ul id="errTitle">
								<li>
									<input type="radio" name="isAuto" id="autoY" value="2"/><label>自动开票</label>
								</li>
								&nbsp;&nbsp;
								<li>
									<input type="radio" name="isAuto" id="autoN" value="1" checked readonly="readonly"/><label>手动开票</label>
								</li>
								<input type="hidden" name="type" value="504"/>
								<input type="hidden" name="yyValidStatus" value="1"/>
								<input type="hidden" name="relatedId" value="${invoiceApply.relatedId}"/>
							</ul>
						</div>
					</div>
				</li>
				<li>
					<div class="form-tips">
						<lable>开票备注</lable>
					</div>
					<div class="f_left ">
						<div class="form-blanks">
							<input type="text" id="comments" name="comments"  class="input-large">
						</div>
						<div id="commentsError"></div>
					</div>
				</li>
			</ul>
			<div class="pop-friend-tips">
				友情提醒<br />
				1、请与客户确认开票资料是否变化？开票的产品名称是否有特殊要求？如果几个订单合并开票，是否可以？收票人信息是否无变化？
				<br />
				2、与客户确认开票相关问题，是维护客户关系最自然的途径，所以，借此机会多问问题，多寒暄。
			</div>
			<div class="add-tijiao tcenter">
				<input type="hidden" name="formToken" value="${formToken}"/>
				<button type="button" class="bt-bg-style bg-light-green bt-small" id="add_submit">确定</button>
				<button class="dele" type="button" id="close-layer">取消</button>
			</div>
		</form>
	</div>
<%@ include file="../../common/footer.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="发送派单通知" scope="application" />
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" >
	$(function(){
		$("#add_submit").click(function(){
			checkLogin();
			var terminal = $("#terminal").val();
			if(terminal ==''){
				warnErrorTips("terminal","terminalError","概况-终端不允许为空");//文本框ID和提示用语
				return false;
			}
			if(terminal.length > 10){
				warnErrorTips("terminal","terminalError","概况-终端不允许超过10个字符");//文本框ID和提示用语
				return false;
			}
			var equipment = $("#equipment").val();
			if(equipment ==''){
				warnErrorTips("equipment","equipmentError","概况-设备不允许为空");//文本框ID和提示用语
				return false;
			}
			if(equipment.length > 10){
				warnErrorTips("equipment","equipmentError","概况-设备不允许超过10个字符");//文本框ID和提示用语
				return false;
			}
			var comments = $("#comments").val();
			if(comments ==''){
				warnErrorTips("comments","commentsError","概况-备注不允许为空");//文本框ID和提示用语
				return false;
			}
			if(comments.length > 10){
				warnErrorTips("comments","commentsError","概况-备注不允许超过10个字符");//文本框ID和提示用语
				return false;
			}
			$.ajax({
				url:page_url+'/aftersales/order/sendinstallstionsms.do',
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
    <div class="form-list  form-tips6">
        <form method="post" id="myform">
            <ul>
            	<li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>单号</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks ">
                         	${asiv.afterSalesNo }
	                     </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>类型</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks ">
                         	${asiv.typeName }
	                     </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>联系人</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks ">
                         	${asiv.name }
	                     </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>联系人手机号</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks ">
                         	${asiv.mobile }
	                     </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>概况-终端</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks ">
                         	<input type="text" class="input-largest" id="terminal" name="terminal">
	                     </div>
	                     <div id="terminalError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>概况-设备</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks ">
                         	<input type="text" class="input-largest" id="equipment" name="equipment">
	                     </div>
	                     <div id="equipmentError"></div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>概况-备注</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks ">
                         	<input type="text" class="input-largest" id="comments" name="comments">
	                     </div>
	                     <div id="commentsError"></div>
                    </div>
                </li>
            </ul>
           <div class="add-tijiao tcenter">
           		<input type="hidden" name="formToken" value="${formToken}"/>
               	<input type="hidden" name="afterSalesInstallstionId" value="${asiv.afterSalesInstallstionId}" >
               	<input type="hidden" name="noticeTimes" value="${asiv.noticeTimes}" >
                <button type="submit" id="add_submit">提交</button>
                <button class="dele" type="button" id="close-layer">取消</button>
			</div>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>
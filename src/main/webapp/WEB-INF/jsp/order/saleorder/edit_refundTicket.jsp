<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑退票" scope="application" />
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" >
	$(function(){
		$("#myform").submit(function(){
			checkLogin();
			var reason=$('input:radio[name="isSendInvoice"]:checked').val();
			if(reason==undefined||reason==""){
				$("#isRefundInvoiceError").css("display","");
				return false;
			}else{
				$("#isRefundInvoiceError").css("display","none");
			}
			$.ajax({
				url:page_url+'/aftersales/order/saveEditRefundTicket.do',
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
	<div class="addElement">
        <div class="add-main">
            <form action="" method="post" id="myform">
                <ul class="add-detail">
                    <li>
                        <div class="infor_name">
                            <span>*</span>
                            <label>是否需寄回</label>
                        </div>
                        <div class="f_left">
                        	<div class="form-blanks">
	                            <ul>
				                     <li>
			                             <input type="radio" name="isSendInvoice" value="1" <c:if test="${afterSalesInvoice.isSendInvoice eq 1}">checked="checked"</c:if>>
			                             <label>是</label>
				                         
				                     </li>
				                     <li>
			                             <input type="radio" name="isSendInvoice" value="0" <c:if test="${afterSalesInvoice.isSendInvoice eq 0}">checked="checked"</c:if>>
			                             <label>否</label>
				                     </li>
			                    </ul>
		                    </div>
		                    <div id="isRefundInvoiceError" class="font-red " style="display: none">是否需寄回不允许为空</div>
		                    <div class="pop-friend-tips mt5">
                        	友情提示：
                     	<br/> 1、请与客户确认是否需要寄回发票；
                     </div>
                        </div>
                    </li>
                </ul>
                <div class="add-tijiao tcenter">
					
                	<input type="hidden" name="afterSalesInvoiceId" value="${afterSalesInvoice.afterSalesInvoiceId}" >
                    <button type="submit" id="add_submit">提交</button>
                    <button class="dele" type="button" id="close-layer">取消</button>
                </div>
            </form>
        </div>
    </div>
<%@ include file="../../common/footer.jsp"%>
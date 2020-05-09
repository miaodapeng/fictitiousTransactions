<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="禁用" scope="application" />    
<%@ include file="../../common/common.jsp"%>
    
    <script type="text/javascript">
    	$(function(){
    		/* $("#disabledReason").blur(function(){
    			var dr=$("#disabledReason").val();
    			if(dr==""){
    				warnTips("disabledReason","禁用原因不能为空");
    				return false;
    			}else if(dr.length>200){
    				warnTips("disabledReason","禁用原因不能超过200个字符");
    				return false;
    			}
    		}) */
    		$("#myform").submit(function(){
    			checkLogin();
    			var dr=$("#disabledReason").val();
    			if(dr==""){
    				warnTips("disabledReason","禁用原因不能为空");
    				return false;
    			}else if(dr.length>200){
    				warnTips("disabledReason","禁用原因不能超过200个字符");
    				return false;
    			}
    			$.ajax({
    				url:page_url+'/trader/customer/isDisabledCustomer.do',
    				data:$('#myform').serialize(),
    				type:"POST",
    				dataType : "json",
    				success:function(data)
    				{
    					if(parent.$("#searchSpan") == undefined || parent.$("#searchSpan").html() == undefined){
    						refreshPageList(data);
    					}else{
	    					//var str=page_url+"/trader/customer/index.do";
	    					//parent.location.href=str;
    						parent.location.reload();
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
    
    <div class="formpublic">
        <form method="post" action="" id="myform">
        	<input type="hidden" name="traderCustomerId" value="${traderCustomer.traderCustomerId}"/>
        	<input type="hidden" name="isEnable" value="0"/>
            <ul>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>禁用</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" name="disableReason" id="disabledReason" class="input-largest">
                        <div class="font-grey9 mt10">
                    友情提示
                    <br/> 1、禁用后客户无法被添加到报价单和订单，但不影响历史单据记录；
                        </div>
                    </div>
                </li>
            </ul>
            <div class="add-tijiao tcenter ">
                <button type="submit" id="submit">提交</butston>
                    <button type="button" class="dele" id="close-layer">取消</button>
            </div>
        </form>
    </div>
</body>

</html>

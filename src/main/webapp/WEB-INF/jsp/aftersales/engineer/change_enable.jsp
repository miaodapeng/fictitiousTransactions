<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="禁用" scope="application" />    
<%@ include file="../../common/common.jsp"%>
    
    <script type="text/javascript">
    	$(function(){
    		$("#myform").submit(function(){
    			checkLogin();
    			var dr=$("#disableReason").val();
    			if(dr==""){
    				warnTips("disableReason","禁用原因不能为空");
    				return false;
    			}else if(dr.length>256){
    				warnTips("disableReason","禁用原因不能超过256个字符");
    				return false;
    			}
    			$.ajax({
    				url:page_url+'/aftersales/engineer/savechangeenable.do',
    				data:$('#myform').serialize(),
    				type:"POST",
    				dataType : "json",
    				success:function(data)
    				{
    					refreshPageList(data);
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
        	<input type="hidden" name="engineerId" value="${engineer.engineerId}"/>
        	<input type="hidden" name="isEnable" value="0"/>
            <ul>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>禁用</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" name="disableReason" id="disableReason" class="input-largest">
                        <div class="font-grey9 mt10">
                    友情提示
                    <br/> 1、禁用后工程师无法在订单中被选择；
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

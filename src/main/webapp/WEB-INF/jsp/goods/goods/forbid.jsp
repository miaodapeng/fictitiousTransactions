<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="产品禁用" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
   	$(function(){
   		/*$("#discardReason").blur(function(){
   			var dr=$("#discardReason").val();
   			if(dr==""){
   				warnTips("discardReason","禁用原因不能为空");
   				return false;
   			}else if(dr.length>200){
   				warnTips("discardReason","禁用原因不能超过200个字符");
   				return false;
   			}
   		})*/
   		$("#myform").submit(function(){
   			var dr=$("#discardReason").val();
   			if(dr==""){
   				warnTips("discardReason","禁用原因不能为空");
   				return false;
   			}else if(dr.length>200){
   				warnTips("discardReason","禁用原因不能超过200个字符");
   				return false;
   			}
   			$.ajax({
   				url:page_url+'/goods/goods/isDiscardById.do',
   				data:$('#myform').serialize(),
   				type:"POST",
   				dataType : "json",
   				success:function(data)
   				{
   					refreshPageList(data);
   				}
   			});
   			return false;
   		})
   	})
   </script>
    <div class="mt10 ml20">
        <form method="post" action="" id="myform">
        	<input type="hidden" name="goodsId" value="${id}"/>
        	<input type="hidden" name="isDiscard" value="1"/>
            <ul>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>禁用原因</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" name="discardReason" id="discardReason" class="input-largest">
                        <div class="font-grey9 mt10">
                   		友情提示
                    <br/> 1、禁用后商品自动下架，请与运营人员确认；
					<br/> 2、禁用后无法被添加到报价单和订单，但不影响历史单据记录；
                </div>
                    </div>
                </li>
                
            </ul>
            <div class="add-tijiao tcenter mt20 ">
                <button type="submit" id="submit">提交</butston>
                <button class="dele" type="button" id="close-layer">取消</button>
            </div>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>
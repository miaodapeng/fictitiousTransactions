<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="产品禁用" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
   	$(function(){
   		$("#myform").submit(function(){
   			var dr=$("#validComments").val();
   			if(dr==""){
   				warnTips("validComments","原因不允许为空");
   				return false;
   			}else if(dr.length>512){
   				warnTips("validComments","原因不能超过512个字符");
   				return false;
   			}
   			$.ajax({
   				url:page_url+'/finance/buyorder/payApplyNoPass.do',
   				data:$('#myform').serialize(),
   				type:"POST",
   				dataType : "json",
   				success:function(data)
   				{
   					if (data.code == -1) {
   						layer.alert(data.message);
   					} else {
   						refreshPageList(data);
   					}
   				}
   			});
   			return false;
   		})
   	})
   </script>
</head>
<body>
    <div class="mt10 ml20">
        <form method="post" action="" id="myform">
            <ul>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>审核不通过原因</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" name="validComments" id="validComments" class="input-largest">
                    </div>
                </li>
                
            </ul>
            <div class="add-tijiao tcenter mt20 ">
	            <input type="hidden" name="payApplyId" value="${id}"/>
                <button type="submit" id="submit">提交</butston>
                <button class="dele" type="button" id="close-layer">取消</button>
            </div>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>
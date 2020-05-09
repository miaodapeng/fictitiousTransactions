<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>职位选择</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/content.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/general.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/manage.css?rnd=<%=Math.random()%>" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src='${pageContext.request.contextPath}/static/js/form.js?rnd=<%=Math.random()%>'></script>
    <script type="text/javascript">
    $(function() {
    	$("#myform").submit(function(){
    		jQuery.ajax({
    			url:'./savechangeorg.do',
    			data:$('#myform').serialize(),
    			type:"POST",
    			dataType : "json",
    			beforeSend:function()
    			{  
    				if($("input[name='orgId']:checked").length <= 0){
    	    			return false;
    	    		}
    			},
    			success:function(data)
    			{
    				if(data.code == 0){
    					var url = self.parent.document.getElementById('side-bar-frame').src;
    					self.parent.document.getElementById('side-bar-frame').src = url;
    					
    					if(self.parent.document.getElementById('callcenter')){
	    					var callurl = self.parent.document.getElementById('callcenter').src;
	    					self.parent.document.getElementById('callcenter').src = callurl;
    					}
    					
    					changecareerlayer.closeParentLayer();
    				}else{
    					layer.alert(data.message);
    				}
    			}
    		});
	   		return false;
    	});
    	
    });
    </script>
</head>

<body>
    <div class="addElement">
        <div class="add-main adddepart">
            <form action="" method="post" id="myform">
                <ul class="add-detail">
                    <li>
                        <div class="infor_name mt0">
                            <lable for='name'>选择职位</lable>
                        </div>
                        <div class="f_left inputfloat">
                            <c:forEach items="${user.positions }" var="list">
                            <div class="overflow-hidden mb10">
								<input type="radio" name="orgId" value="${list.orgId }" <c:if test="${user.orgId == list.orgId}">checked="checked"</c:if>>
                                <label>
                                <c:forEach items="${orgList }" var="org">
		                        	<c:if test="${org.orgId eq list.orgId}">
			                        	${org.orgNames}<%-- ${position.orgName} --%>
		                        	</c:if>
		                        </c:forEach> 
		                        ${list.positionName }</label>
                            </div>
							</c:forEach>
                            <div class="font-grey9 mt10">
                                友情提示:
                                <br> 职位的切换不影响账号权限的使用；
                                <br> 职位影响该账号操作单据时保存的部门和职位，请谨慎选择；
                            </div>
                        </div>
                    </li>
                </ul>
                <div class="add-tijiao tcenter">
                    <button type="submit">提交</button>
                    <button class="dele" id="changecareer-close" type="button">取消</button>
                </div>
            </form>
        </div>
    </div>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/changecareerlayer.js?rnd=<%=Math.random()%>"></script>
</body>
</html>

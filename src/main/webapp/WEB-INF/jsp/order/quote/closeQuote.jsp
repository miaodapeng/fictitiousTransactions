<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="确认审核" scope="application" />
<%@ include file="../../common/common.jsp"%>
 <div class="formpublic">
            <form method="post" action="" id="complement">
                <ul>
                   <li>
                   	 <div class="infor_name">
						<span>*</span>
						<lable for='name'>关闭原因</lable>
					 </div>
                   	 <div class="f_left">
                        <select name="reason">
                            <option value="">请选择关闭原因</option>
                            <option value="1">产品原因</option>
                            <option value="2">客户原因</option>
                        </select>
                        <div class="font-red " style="display: none">关闭原因不允许为空</div>
                    </div>
                   </li>
                </ul>
                <div class="add-tijiao tcenter">
                	<input type="hidden" name="formToken" value="${formToken}"/>
                	<input type="hidden" value="${quoteorderId}" name="quoteorderId">
                    <button type="button" class="bg-light-green" onclick="closeQuoteVerify()">提交</button>
                    <button class="dele" type="button" id="close-layer">取消</button>
                </div>
           </form>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/order/quote/complete.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增联系人" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/customer/add_contact.js?rnd=<%=Math.random()%>"></script>
    <div class="formpublic">
        <form method="post" action="" id="myform">
        	
            <ul>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>姓名</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest" name="name" id="name" value="${traderContact.name}"/>
                    </div>
                </li>
                <li>
                    <div class="infor_name mt0">
                        <lable>性别</lable>
                    </div>
                    <div class="f_left inputfloat">
                        <input type="radio" name="sex" value="1" <c:if test="${traderContact.sex eq 1}">checked="checked"</c:if>/>
                        <label class="mr8">男</label>
                        <input type="radio" name="sex" value="0" <c:if test="${traderContact.sex eq 0}">checked="checked"</c:if>>
                        <label class="mr8">女</label>
                        <input type="radio" name="sex" value="2" <c:if test="${traderContact.sex eq 2}">checked="checked"</c:if>>
                        <label>未知</label>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>部门</lable>
                    </div>
                    <div class="f_left commonuse table-largest" id="dept">
                    	<div>
	                        <input type="text" name="department" id="department" value="${traderContact.department}" class="input-largest" placeholder="您可以点击常用部门进行选择"/>
	                        <label>常用部门:</label>
	                        <span>采购部</span>
	                        <span>销售部</span>
	                        <span>生产部</span>
	                        <span>财务部</span>
	                        <span>仓储部</span>
	                        <span>研发部</span>
	                        <span>商务部</span>
	                        <span>研究院</span>
                        </div>
                        <div id="departmentError"></div>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>职位</lable>
                    </div>
                     <div class="f_left commonuse table-largest" id="posi">
                     	<div>
                        <input type="text" name="position" id="position" value="${traderContact.position}" class="input-largest" placeholder="您可以点击常用职位进行选择" />
                        <label>常用职位:</label>
                        <span>经理</span>
                        <span>老板</span>
                        <span>工程师</span>
                        <span>主管</span>
                        <span>专员</span>
                        <span>会计 </span>
                        <span>教授</span>
                        <span>研究生</span>
                        <span>研究员 </span>
                        <span>主任 </span>
                        </div>
                        <div id="positionError"></div>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>电话</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest errobor" name="telephone" id="telephone" value="${traderContact.telephone}"/>
                        <input type="hidden" name="formToken" value="${formToken}"/>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>手机</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest errobor" name="mobile" id="mobile" value="${traderContact.mobile}"/>
                        
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>手机2</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest errobor"  name="mobile2" id="mobile2" value="${traderContact.mobile2}"/>
                        
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>邮箱</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest errobor"  name="email" id="email" value="${traderContact.email}"/>
                        
                    </div>
                </li>
                 
                  <li>
                    <div class="infor_name">
                        <lable>QQ</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest errobor"  name="qq" id="qq" value="${traderContact.qq}"/>
                    </div>
                </li>
                 <li>
                    <div class="infor_name">
                        <lable>备注</lable>
                    </div>
                    <div class="f_left">
                        <textarea class="input-largest textarea-smallest" name="comments" id="comments">${traderContact.comments}</textarea> 
                    </div>
                </li>
            </ul>
            <div class="add-tijiao tcenter">
                <button type="submit">提交</button>
                <button type="button" class="dele" id="close-layer">取消</button>
            </div>
            <input type="hidden" name="traderId" value="${traderCustomer.traderId}">
            <input type="hidden" name="traderCustomerId" value="${traderCustomer.traderCustomerId}">
        	<input type="hidden" name="traderType" value="1">
        	<input type="hidden" id="add" name="op" value="add"/>
        </form>
    </div>
</body>

</html>

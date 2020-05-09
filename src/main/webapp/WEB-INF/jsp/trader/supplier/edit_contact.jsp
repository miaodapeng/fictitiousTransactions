<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑联系人" scope="application" />	
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/customer/add_contact.js?rnd=<%=Math.random()%>"></script>
	<%--<%@ include file="supplier_tag.jsp"%>--%>
    <div class="baseinforeditform content">
    	<c:if test="${not empty allErrors }">
		<div class="service-return-error">
            <ul>
				<c:forEach items="${allErrors }" var="error" varStatus="status">
                <li>${status.count}、${error.defaultMessage }</li>
				</c:forEach>
            </ul>
        </div>
		</c:if>
        <form method="post" action="${pageContext.request.contextPath}/trader/supplier/editSaveContact.do" id="myform">
        	<input type="hidden" name="traderContactId" value="${traderContact.traderContactId}">
        	<input type="hidden" name="traderId" value="${traderContact.traderId}">
        	<input type="hidden" id="mod" name="op" value="mod"/>
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
                </li>
                <li>
                    <div class="infor_name">
                        <lable>职位</lable>
                    </div>
                     <div class="f_left commonuse table-largest" id="posi">
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
                </li>
                <li>
                    <div class="infor_name">
                        <lable>电话</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest errobor" name="telephone" id="telephone" value="${traderContact.telephone}"/>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>传真</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest errobor"  name="fax" id="fax" value="${traderContact.fax}"/>
                        
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
                        <lable>邮件</lable>
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
                    <div class="infor_name mt0">
                        <lable>在职状态</lable>
                    </div>
                    <div class="f_left inputfloat">
                        <input type="radio" name="isOnJob" value="1" <c:if test="${traderContact.isOnJob eq 1}">checked="checked"</c:if>/>
                        <label class="mr8">在职</label>
                        <input type="radio" name="isOnJob" value="0" <c:if test="${traderContact.isOnJob eq 0}">checked="checked"</c:if>>
                        <label class="mr8">离职</label>
                    </div>
                </li>
                 <li>
                    <div class="infor_name">
                        <lable>备注</lable>
                    </div>
                    <div class="f_left">
                        <textarea class="input-largest textarea-smallest" name="comments">${traderContact.comments}</textarea> 
                    </div>
                </li>
                <li>
                	<div class="infor_name">
                        <lable>性格</lable>
                    </div>
                    <div class="f_left inputradio">
                    	<ul>
                    		<c:forEach items="${xgList}" var="xg">
                    		<li>
                    			<input type="checkbox" name="xg" value="${xg.sysOptionDefinitionId}" <c:if test="${fn:contains(traderContact.character, xg.sysOptionDefinitionId)}">checked="checked"</c:if>/>${xg.title}
                    		</li>
                    		</c:forEach>
						</ul>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>出生日期</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" name="birthday" class="input-smaller Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
                        		value="<fmt:formatDate value='${traderContact.birthday}' pattern='yyyy-MM-dd'/> "/>
                    </div>
                </li>
                <li>
                	<div class="infor_name">
                        <lable>婚否</lable>
                    </div>
                    <div class="f_left">
                    	<input type="radio" name="isMarried" value="0" <c:if test="${traderContact.isMarried eq 0}">checked="checked"</c:if>/>
                        <label class="mr8">未知</label>
                        <input type="radio" name="isMarried" value="1" <c:if test="${traderContact.isMarried eq 1}">checked="checked"</c:if>/>
                        <label class="mr8">是</label>
                        <input type="radio" name="isMarried" value="2" <c:if test="${traderContact.isMarried eq 2}">checked="checked"</c:if>>
                        <label class="mr8">否</label>
                    </div>
                </li>
                <li>
                	<div class="infor_name">
                        <lable>子女</lable>
                    </div>
                    <div class="f_left">
                    	<input type="radio" name="haveChildren" value="0" <c:if test="${traderContact.haveChildren eq 0}">checked="checked"</c:if>/>
                        <label class="mr8">未知</label>
                        <input type="radio" name="haveChildren" value="1" <c:if test="${traderContact.haveChildren eq 1}">checked="checked"</c:if>/>
                        <label class="mr8">是</label>
                        <input type="radio" name="haveChildren" value="2" <c:if test="${traderContact.haveChildren eq 2}">checked="checked"</c:if>>
                        <label class="mr8">否</label>
                    </div>
                </li>
                <li>
                	<div class="infor_name">
                        <lable>学历</lable>
                    </div>
                    <div class="f_left">
                        <c:forEach items="${xlList}" var="xl">
                        	<input type="radio" name="education" value="${xl.sysOptionDefinitionId}" 
                        			<c:if test="${traderContact.education eq xl.sysOptionDefinitionId}">checked="checked"</c:if>/>
                        	<label class="mr8">${xl.title}</label>
                        </c:forEach> 
                    </div>
                </li>
            </ul>
            <div class="add-tijiao tcenter mt20 mb20">
            	<input type="hidden" name="beforeParams" value='${beforeParams}'>
            	<input type="hidden" name="traderId" value="${traderSupplier.traderId }" >
            	<input type="hidden" name="traderSupplierId" value="${traderSupplier.traderSupplierId }" >
                <button type="submit">提交</button>
                <button type="button" class="dele" onclick="goUrl('${pageContext.request.contextPath}/trader/supplier/getContactsAddress.do?traderId=${traderSupplier.traderId}&traderSupplierId=${traderSupplier.traderSupplierId}')">取消</button>
            </div>
        </form>
    </div>
</body>

</html>

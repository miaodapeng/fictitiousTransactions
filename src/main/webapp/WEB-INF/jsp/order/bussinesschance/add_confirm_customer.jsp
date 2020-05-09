
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="确认客户" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/order/bussinesschance/confirm_customer.js?rnd=<%=Math.random()%>"></script>
<div class="formpublic">
        <form method="post" action="" id="myform3">
            <ul>
                <li>
                    <div class="infor_name ">
                        <span>*</span>
                        <lable>客户名称</lable>
                        <input type="hidden" name="traderCustomerId" value="${traderCustomer.traderCustomerId}">
                    	<input type="hidden" name="traderId" value="${traderCustomer.traderId}">
                        <input type="hidden" name="bussinessChanceId" value="${bussinessChance.bussinessChanceId}">
                        <input type="hidden" name="checkTraderName" value="${traderCustomer.name}">
                        <input type="hidden" name="checkTraderArea" value="${traderCustomer.address}">
                    </div>
                    <div class="f_left  ">
                        <label class="mr10">${traderCustomer.name}</label>
                        <a href="${pageContext.request.contextPath}/order/bussinesschance/confirmCustomer.do?bussinessChanceId=${bussinessChance.bussinessChanceId}&traderCustomerId=${traderCustomer.traderCustomerId}&traderId=${traderCustomer.traderId}" 
                        	class="setLayerWidth" setwidth='{"width":"580","left":"30"}' >
                        	<span class="bt-bg-style bg-light-blue bt-small">重新搜索</span>
                        </a>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>姓名</lable>
                    </div>
                    <div class="f_left inputfloat ">
                        <input type="text" name="name" id="name" class="input-largest">
                    </div>
                    <input type="hidden" name="traderCustomerId" value="${traderCustomer.traderCustomerId}">
                    <input type="hidden" name="traderId" value="${traderCustomer.traderId}">
                    <input type="hidden" name="bussinessChanceId" value="${bussinessChance.bussinessChanceId}">
                </li>
                <li>
                    <div class="infor_name mt0">
                        <lable>性别</lable>
                    </div>
                    <div class="f_left inputfloat ">
                        <ul>
                            <li>
                                <input type="radio" name="sex" value="1">
                                <label>先生</label>
                            </li>
                            <li>
                                <input type="radio" name="sex" value="0">
                                <label>女士</label>
                            </li>
                            <li>
                                <input type="radio" name="sex"  value="2" checked="checked">
                                <label>未知</label>
                            </li>
                        </ul>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>部门</lable>
                    </div>
                    <div class="f_left  commonuse" id="dept">
                        <input type="text" name="department" id="department" class="input-middle" placeholder="您可以点击常用部门进行选择">
                        <label>常用部门：</label>
                        <span>采购部  </span>
                        <span>销售部   </span>
                        <span>生产部   </span>
                        <span>财务部   </span>
                        <span>仓储部   </span>
                        <span>研发部   </span>
                        <span>商务部  </span>
                        <span>研究院  </span>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>职位</lable>
                    </div>
                    <div class="f_left  commonuse" id="posi">
                        <input type="text" name="position" id="position" class="input-middle" placeholder="您可以点击常用职位进行选择">
                        <label>常用职位：</label>
                        <span>经理  </span>
                        <span>老板   </span>
                        <span>工程师  </span>
                        <span>主管  </span>
                        <span>专员  </span>
                        <span>会计  </span>
                        <span>教授  </span>
                        <span>研究生  </span>
                        <span>研究员  </span>
                        <span>主任  </span>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>电话</lable>
                    </div>
                    <div class="f_left inputfloat ">
                        <input type="text" name="telephone" id="telephone" class="input-largest">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                    	<span>*</span>
                        <lable>手机</lable>
                    </div>
                    <div class="f_left inputfloat ">
                        <input type="text" name="mobile" id="mobile" class="input-largest">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>邮件</lable>
                    </div>
                    <div class="f_left inputfloat ">
                        <input type="text" name="email" id="email" class="input-largest">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>QQ</lable>
                    </div>
                    <div class="f_left inputfloat ">
                        <input type="text" name="qq" id="qq" class="input-largest">
                    </div>
                </li>
            </ul>
            <div class="add-tijiao tcenter mt20 ">
                <button type="submit" id="sub2">提交</button>
                <button type="button" class="dele" id="close-layer">取消</button>
            </div>
        </form>
    </div>
</body>

</html>

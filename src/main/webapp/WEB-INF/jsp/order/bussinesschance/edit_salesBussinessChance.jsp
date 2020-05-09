
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑销售商机" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/order/bussinesschance/add_salesBussinessChance.js?rnd=<%=Math.random()%>"></script>
		<div class="formpublic formpublic1 pt0">
        <form method="post" id="myform" action="${pageContext.request.contextPath}/order/bussinesschance/saveEditSalesBussinessChance.do">
            <div class="formtitle">请填写商机信息</div>
            <input type="hidden" name="bussinessChanceId" value="${bussinessChanceVo.bussinessChanceId}">
            <input type="hidden" name="type" value="392">
            <input type="hidden" name="traderId" value="${traderCustomer.traderId }">
            <input type="hidden" name="traderCustomerId" value="${traderCustomer.traderCustomerId }">
            <div class="line">
                <ul>
                    <li>
                        <div class="infor_name">
                            <span>*</span>
                            <label>商机时间</label>
                        </div>
                        <div class="f_left">
                            <input class="Wdate m0 input-middle Wdate2" name ="time" id="receiveTime" type="text" placeholder="请选择日期" 
                            	onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
                            	value='<date:date value ="${bussinessChanceVo.receiveTime} " format="yyyy-MM-dd HH:mm:ss"/>'>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name mt0">
                            <span>*</span>
                            <label>询价方式</label>
                        </div>
                        <div class="f_left inputfloat">
                            <ul>
                           		 <c:if test="${not empty inquiryList }">
                            		<c:forEach items="${inquiryList}" var ="il">
                            			<li>
	                                    	<input type="radio" name="communication" value="${il.sysOptionDefinitionId}"
	                                    		<c:if test="${il.sysOptionDefinitionId eq bussinessChanceVo.communication}">checked="checked"</c:if>><label>${il.title}</label>
	                                	</li>
                            		</c:forEach>
                            	</c:if>
                            </ul>
                            <div id="communication" class="font-red " style="display: none">询价方式不允许为空</div>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name">
                            <span>*</span>
                            <label>询价产品</label>
                        </div>
                        <div class="f_left">
                            <textarea class="askprice" name="content" id="content" placeholder="询价产品录入格式：品牌+名称+型号
如：奥林巴斯显微镜CX23
1、如果是多个产品询价，每个产品请换行输入
2、如果是综合询价，尽量输入完整，如果询价产品达到10个以上，
可选择主要产入录，同时在最后备注：综合询价 四个字
3、关于综合询价的定义:单次询问不同类型的产品5个以上 ">${bussinessChanceVo.content}</textarea>
							<div id="content1" class="font-red " style="display: none">询价产品不允许为空</div>
							<div id="content2" class="font-red " style="display: none">询价产品内容不能大于512字符</div>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name">
                            <span>*</span>
                            <label>产品分类</label>
                        </div>
                        <div class="f_left">
                            <select name="goodsCategory" id="goodsCategory">
                            	<option value="">请选择</option>
	                             <c:if test="${not empty goodsTypeList }">
	                            	<c:forEach items="${goodsTypeList}" var ="gyl">
	                            		<option value="${gyl.sysOptionDefinitionId}" <c:if test="${gyl.sysOptionDefinitionId eq bussinessChanceVo.goodsCategory}">selected="selected"</c:if> >${gyl.title}</option>
                                	</c:forEach>
                                </c:if>
                            </select>
                            <div id="goodsCategory1" class="font-red " style="display: none">产品分类不允许为空</div>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name">
                            <label>产品品牌</label>
                        </div>
                        <div class="f_left">
                            <input type="text" class="input-large" name="goodsBrand" id="goodsBrand" 
                            	placeholder="请输入客户咨询的第一个产品的品牌" value="${bussinessChanceVo.goodsBrand}">
                        </div>
                    </li>
                    <li>
                        <div class="infor_name">
                            <label>产品名称</label>
                        </div>
                        <div class="f_left">
                            <input type="text" class="input-large" name="goodsName" id="goodsName" 
                            	value="${bussinessChanceVo.goodsName}" placeholder="请输入客户咨询的第一个产品的名称">
                        </div>
                    </li>
                </ul>
            </div>
            <div class="formtitle">请填写客户信息</div>
            <div>
               <ul>
                    <li>
                        <div class="infor_name mt0">
                            <label>客户名称</label>
                        </div>
                        <div class="f_left">
                        	<c:if test="${bussinessChanceVo.traderId ne null}">
                        		
                            	${bussinessChanceVo.traderName}
                        	</c:if>
                        	<c:if test="${bussinessChanceVo.traderId eq null}">
                        	<input type="hidden" class="input-large" value="${traderCustomer.name}" name="traderName" >
                        		<input type="hidden" class="input-large" value="${traderCustomer.name}" name="checkTraderName" >
                        		<input type="hidden" class="input-large" value="${traderCustomer.traderId}" name="traderId" >
                            	${traderCustomer.name}
                        	</c:if>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name">
                           <span>*</span>
                           <label>客户联系人</label>
                        </div>
                        <div class="f_left">
                            <select name="traderContactId" id="traderContactId" class="input-large">
                                <option value="">请选择</option>
                                <c:if test="${not empty contactList }">
		                    		<c:forEach items="${contactList }" var="ca">
		                    			<option value="${ca.traderContactId }" <c:if test="${bussinessChanceVo.traderContactId eq ca.traderContactId }">selected="selected"</c:if>>${ca.name}/${ca.mobile}/${ca.telephone}</option>
		                    		</c:forEach>
		                    	</c:if>
                            </select>
                            <div id="traderContactId1" class="font-red " style="display: none">客户联系人不允许为空</div>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name mt0">
                            <label>客户区域</label>
                        </div>
                        <div class="f_left">
                        	<c:if test="${bussinessChanceVo.traderId ne null}">
	                        	<input type="hidden" class="input-large" value="${bussinessChanceVo.areaId}" name="areaId" >
	                        	<input type="hidden" class="input-large" value="${bussinessChanceVo.areaIds}" name="areaIds" >
	                        	<input type="hidden" class="input-large" value="${bussinessChanceVo.areas}" name="checkTraderArea" >
	                           ${bussinessChanceVo.areas}
                            </c:if>
                        	<c:if test="${bussinessChanceVo.traderId eq null}">
                        		<input type="hidden" class="input-large" value="${traderCustomer.areaId}" name="areaId" >
	                        	<input type="hidden" class="input-large" value="${traderCustomer.areaIds}" name="areaIds" >
	                        	<input type="hidden" class="input-large" value="${traderCustomer.address}" name="checkTraderArea" >
                            	${traderCustomer.address}
                        	</c:if>
                        </div>
                    </li>
                </ul>
            </div>
            
            <div class="add-tijiao">
            	<input type="hidden" name="beforeParams" value='${beforeParams}'>
                <button id="submit" type="submit">提交</button>
                <!-- 
                <button type="button" class="dele" >取消</button> -->
            </div>
        </form>
    </div>
</body>

</html>

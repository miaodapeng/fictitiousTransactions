
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑售后商机" scope="application" />	
<%@ include file="../../common/common.jsp"%>

<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/order/bussinesschance/add_afterAalesBussinessChance.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src='<%= basePath %>static/js/region/index.js?rnd=<%=Math.random()%>'></script>
	<div class="formpublic formpublic1 pt0">
        <form method="post" id="myform" action="${pageContext.request.contextPath}/order/bussinesschance/saveEditServiceBussinessChance.do">
            <div class="formtitle">请填写商机信息</div>
            <input type="hidden" name="bussinessChanceId" value="${bussinessChanceVo.bussinessChanceId}">
            <input type="hidden" name="type" value="391">
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
                            <label>商机来源</label>
                        </div>
                        <div class="f_left inputfloat">
                            <ul>
                            	<c:if test="${not empty scoureList }">
                            		<c:forEach items="${scoureList}" var ="sl">
                            		<c:if test="${sl.sysOptionDefinitionId!=366 && sl.sysOptionDefinitionId != 477}">
                            			<li>
                            				<input type="radio" name="source" <c:if test="${sl.sysOptionDefinitionId eq bussinessChanceVo.source }">checked="checked"</c:if> 
	                                    			value="${sl.sysOptionDefinitionId}"><label>${sl.title}</label>
	                                	</li>
	                                	</c:if>
                            		</c:forEach>
                            	</c:if>
                            </ul>
                            <div id="source" class="font-red " style="display: none">商机来源不允许为空</div>
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
							<div id="content2" class="font-red " style="display: none">询价产品内容不允许超过512字符</div>
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
                            <input type="text" class="input-larger" name="goodsBrand" id="goodsBrand" 
                            	placeholder="请输入客户咨询的第一个产品的品牌" value="${bussinessChanceVo.goodsBrand}">
                        </div>
                    </li>
                    <li>
                        <div class="infor_name">
                            <label>产品名称</label>
                        </div>
                        <div class="f_left">
                            <input type="text" class="input-larger" name="goodsName" id="goodsName" 
                            	value="${bussinessChanceVo.goodsName}" placeholder="请输入客户咨询的第一个产品的名称">
                        </div>
                    </li>
                    <li>
                        <div class="infor_name">
                            <label>附件</label>
                        </div>
                        <div class="f_left">
	                        	<input type="file" class="upload_file" id='file_1' name="lwfile" style="display: none;" onchange="uploadFile(this,1);">
				                <input type="text" class="input-larger" id="name_1" name="name" readonly="readonly" value="${bussinessChanceVo.attachmentName}"
				                       placeholder="使用pdf、jpg、word或excel等文件，不允许超过2MB"  onclick="file_1.click();">
				                <input type="hidden" name="uri" id="uri_1" value="${bussinessChanceVo.attachmentUri}"/>
				                <input type="hidden" name="" id="domain" value="${bussinessChanceVo.attachmentDomain}"/>
	                            <label class="bt-bg-style bt-small bg-light-blue" onclick="file_1.click();">浏览</label>
	                            <c:choose>
									<c:when test="${!empty bussinessChanceVo.attachmentUri}">
										<i class="iconsuccesss mt5" id="img_icon_1"></i>
				                    	<a href="http://${bussinessChanceVo.attachmentDomain}${bussinessChanceVo.attachmentUri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_1">查看</a>
				                    	<span class="font-red cursor-pointer mt4" onclick="del(1)" id="img_del_1">删除</span>
									</c:when>
									<c:otherwise>
										<i class="iconsuccesss mt5 none" id="img_icon_1"></i>
			                    		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_1">查看</a>
				                    	<span class="font-red cursor-pointer mt4 none" onclick="del(1)" id="img_del_1">删除</span>
									</c:otherwise>
								</c:choose>
	                        <div style="clear:both"></div>    
                            <div id="upload1" class="font-red " style="display: none">请选择正确文件格式</div>
                            <div id="upload2" class="font-red " style="display: none">上传内容不允许超过2MB</div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="formtitle ">请填写客户信息</div>
            <div class='line'>
                <ul>
                    <li>
                        <div class="infor_name">
                            <label>客户名称</label>
                        </div>
                        <div class="f_left">
                            <input type="text" class="input-large" value="${bussinessChanceVo.traderName}" name="traderName" id="traderName">
                        </div>
                    </li>
                    <li>
                        <div class="infor_name">
                            <label>客户地区</label>
                        </div>
                        <div class="f_left inputfloat">
                            <select name="province" id="province">
                                <option value="0">请选择</option>
		                    	<c:if test="${not empty provinceList }">
		                    		<c:forEach items="${provinceList }" var="prov">
		                    			<option value="${prov.regionId }" <c:if test="${province eq prov.regionId }">selected="selected"</c:if>>${prov.regionName }</option>
		                    		</c:forEach>
		                    	</c:if>
                            </select>
                            <select name="city" id ="city">
                                <option value="0">请选择</option>
                                <c:if test="${not empty cityList }">
		                    		<c:forEach items="${cityList }" var="ci">
		                    			<option value="${ci.regionId }" <c:if test="${city eq ci.regionId }">selected="selected"</c:if>>${ci.regionName }</option>
		                    		</c:forEach>
		                    	</c:if>
                            </select>
                            <select name="zone" id="zone">
                                <option value="0">请选择</option>
                                <c:if test="${not empty zoneList }">
		                    		<c:forEach items="${zoneList }" var="zo">
		                    			<option value="${zo.regionId }" <c:if test="${zone eq zo.regionId }">selected="selected"</c:if>>${zo.regionName }</option>
		                    		</c:forEach>
		                    	</c:if>
                            </select>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name">
                            <label>联系人</label>
                        </div>
                        <div class="f_left">
                            <input type="text" class="input-middle" value="${bussinessChanceVo.traderContactName}"  name="traderContactName" id="traderContactName">
                        </div>
                    </li>
                    <li>
                        <div class="infor_name">
                            <label>手机号</label>
                        </div>
                        <div class="f_left">
                            <input type="text" class="input-middle" value="${bussinessChanceVo.mobile}"  name="mobile" id="mobile">
                        </div>
                    </li>
                    <li>
                        <div class="infor_name">
                            <label>电话</label>
                        </div>
                        <div class="f_left">
                            <input type="text" class="input-middle" value="${bussinessChanceVo.telephone}"  name="telephone" id="telephone">
                        </div>
                    </li>
                    <li>
                        <div class="infor_name">
                            <label>其他联系方式</label>
                        </div>
                        <div class="f_left">
                            <input type="text" class="input-middle" value="${bussinessChanceVo.otherContact}"  name="otherContact" id="otherContact">
                        </div>
                    </li>
                </ul>
            </div>
            <div class="formtitle">请分配商机</div>
            <div>
                <ul>
                    <li>
                        <div class="infor_name">
                        <span>*</span>
                            <label>分配销售</label>
                        </div>
                        <div class="f_left">
                            <select name="userId" id="userId">
                                <option value="">请选择</option>
                                <c:if test="${not empty userList }">
                                	<c:forEach items="${userList}" var="user">
                                		<option value="${user.userId}" <c:if test="${bussinessChanceVo.userId eq user.userId }">selected="selected"</c:if>>${user.username}</option>
                                	</c:forEach>
                                </c:if>
                            </select>
                        </div>
                    </li>
                     <li>
                        <div class="infor_name">
                            <label>备注</label>
                        </div>
                        <div class="f_left">
                            <input type="text" class="input-large" value="${bussinessChanceVo.comments}"  name="comments" id="comments">
                        </div>
                    </li>
                </ul>
            </div>
            <div class="add-tijiao">
            	<input type="hidden" name="beforeParams" value='${beforeParams}'>
            	<input type="hidden" name="isRest" value="1">
                <button id="submit" type="submit">提交</button>
            </div>
        </form>
    </div>
</body>

</html>

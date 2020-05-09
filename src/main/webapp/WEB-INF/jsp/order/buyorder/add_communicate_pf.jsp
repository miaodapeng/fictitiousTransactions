
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增沟通记录" scope="application" />    
<%@ include file="../../common/common.jsp"%>    

	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/order/buyorder/add_communicate.js?rnd=<%=Math.random()%>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/tag.js?rnd=<%=Math.random()%>"></script>
<div class="formpublic">
        <form method="post" action="" id="myform">
            <ul>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>联系人</lable>
                    </div>
                    <div class="f_left  ">
                    	<div>
                        <select class="mr5" name="traderContactId" id="traderContactId">
                            <option selected="selected" value="0">请选择</option>
                            <c:if test="${not empty contactList }">
                            	<c:forEach	items="${contactList }" var="contact">
                            		<option value="${contact.traderContactId }">
                            		${contact.name }
                            		<c:if test="${contact.telephone !='' and contact.telephone != null }">|${contact.telephone }</c:if>
                            		<c:if test="${contact.mobile !='' and contact.mobile != null }">|${contact.mobile }</c:if>
                            		<c:if test="${contact.mobile2 !=''and contact.mobile2 != null}">|${contact.mobile2 }</c:if>
                            		</option>
                            	</c:forEach>
                            </c:if>
                        </select>
                        </div>
                        <div id="traderContactIdError"></div>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>沟通时间</lable>
                    </div>
                    <div class="f_left inputfloat ">
                    	<div>
	                    	<input class="Wdate input-small mr0" type="text" placeholder="请选择时间" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'end\')}'})" 
	                    			name="begin" id="begin" value="<date:date value ="${communicateRecord.begintime} "/>"/><div class="gang">-</div>
	                        <input class="Wdate input-small ml5" type="text" placeholder="请选择时间" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'begin\')}'})" 
	                        		name="end" id="end" onchange="changeDate(this);" value="<date:date value ="${communicateRecord.endtime} "/>"/>
                        </div>
                        <div id="timeError"></div>
                    </div>
                </li>
                <li class="bt">
                    <div class="infor_name mt0">
                        <span>*</span>
                        <lable>沟通方式</lable>
                    </div>
                    <div class="f_left inputfloat table-largest">
                    	<div>
	                        <ul>
	                            <c:if test="${not empty communicateList }">
	                        		<c:forEach items="${communicateList }" var="cm">
			                            <li>
			                                <input type="radio" name="communicateMode" value="${cm.sysOptionDefinitionId }"/>
			                                <label class="mr8">${cm.title }</label>
			                            </li>
	                        		</c:forEach>
	                        	</c:if>
	                        </ul>
                        </div>
                        <div id="communicateError"></div>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>沟通内容</lable>
                    </div>
                    <div class="f_left table-largest">
                    	<div>
							<div class="inputfloat manageaddtag">
								<label class="mt4 mr8">您可以从这些标签中选择</label>
								<c:if test="${not empty tagList }">
									<c:forEach items="${tagList }" var="tag">
										<span onclick="addTag(${tag.tagId},'${tag.tagName }',this);">${tag.tagName }</span>
									</c:forEach>
								</c:if>
								<c:if test="${page.totalPage > 1}">
								<div class="change" onclick="changeTag(${page.totalPage},10,this,32);"><span class="m0">换一批(</span><span class="m0" id="leftNum">${page.totalPage}</span><span class="m0">)</span>
								<input type="hidden" id="pageNo" value="${page.pageNo}">
								</div>
								</c:if>
							</div>
							<div class="inputfloat <c:if test="${empty tagList }">mt8</c:if>">
								<input type="text" id="defineTag" placeholder="如果标签中没有您所需要的，请自行填写"
									class="input-large" style='height:26px;'>
								<div class="f_left bt-bg-style bg-light-blue bt-small  addbrand" onclick="addDefineTag(this);">添加</div>
							</div>
							<div class="addtags mt6 none">
								<ul id="tag_show_ul">
								</ul>
							</div>
						</div>
						<div id="tagError"></div>
					</div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>下次沟通时间</lable>
                    </div>
                    <div class="f_left inputfloat ">
                    	<input class="Wdate input-small " type="hidden" placeholder="请选择时间" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'nextDate\')}'})" name="endHidden" id="endHidden"/>
                        <input class="Wdate input-small" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'endHidden\')}'})" name="nextDate" id="nextDate"/>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>下次沟通内容</lable>
                    </div>
                    <div class="f_left  ">
                        <input type="text" class="input-largest" name="nextContactContent" id="nextContactContent">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>备注</lable>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest" name="comments" id="comments">
                    </div>
                </li>
            </ul>
            <div class="add-tijiao tcenter">
            	<input type="hidden" name="formToken" value="${formToken}"/>
            	<input type="hidden" name="traderId" value="${traderSupplier.traderId }" >
            	<input type="hidden" name="buyorderId" value="${buyorder.buyorderId}">
            	<input type="hidden" name="flag" value="${flag}">
                <button type="submit" id="submit">提交</button>
                <button class="dele" id="close-layer" type="button" >取消</button>
            </div>
        </form>
    </div>
</body>

</html>

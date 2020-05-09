
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增沟通记录" scope="application" />    
<%@ include file="../../common/common.jsp"%>    

	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/order/bussinesschance/add_communicate.js?rnd=<%=Math.random()%>"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/tag.js?rnd=<%=Math.random()%>"></script>
<div class="formpublic">
       <form method="post" action="" id="myform">
            <ul>
                <li>
                    <div class="infor_name">
                        <lable>联系人</lable>
                    </div>
                    <div class="f_left  ">
	                    <div>
	                        <select class="mr5" name="traderContactId" id="traderContactId">
	                            <option selected="selected" value="0">请选择</option>
                                <c:if test="${not empty contactList }">
                                    <c:forEach	items="${contactList }" var="contact">
                                        <c:if test="${contact.traderContactId!=0}">
                                            <option value="${contact.traderContactId }" <c:if test="${contact.traderContactId == communicateRecord.traderContactId }">selected="selected" </c:if>>
                                                ${contact.name }
                                            <c:if test="${contact.telephone !='' and contact.telephone != null }">|${contact.telephone }</c:if>
                                            <c:if test="${contact.mobile !='' and contact.mobile != null }">|${contact.mobile }</c:if>
                                            <c:if test="${contact.mobile2 !=''and contact.mobile2 != null}">|${contact.mobile2 }</c:if>
                                        </c:if>
                                        <c:if test="${contact.traderContactId==0}">
                                            <option value="0" selected="selected" >
                                                ${contact.name }|${contact.mobile }
                                        </c:if>
                                    </c:forEach>
                                </c:if>
	                        </select>
                        </div>
                        <div id="traderContactIdError"></div>
                    </div>
                    <div class="title-click   pop-new-data" layerParams='{"width":"70%","height":"73%","title":"新增联系人","link":"${pageContext.request.contextPath}/order/bussinesschance/addTraderContact.do?traderId=${traderCustomer.traderId }&traderCustomerId=${traderCustomer.traderCustomerId }"}'>
	                                           添加联系人
	                </div>
                </li>
               <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>沟通时间</lable>
                    </div>
                    <div class="f_left inputfloat ">
	                    <div>
	                    <input id="begin" class="Wdate input-small mr0" type="text" name="begin" placeholder="请选择日期" value="<date:date value ='${communicateRecord.begintime} '/>" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"   />
	                        <%-- <input class="Wdate input-small mr0" type="text" placeholder="请选择时间" 
	                        		onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'end\')}'})" name="begin" id="begin" value="<date:date value ="${communicateRecord.begintime} "/>" />
	                        <div class="gang">-</div>
	                        <input class="Wdate input-small ml5" type="text" placeholder="请选择时间" 
	                        		onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'begin\')}'})" name="end" id="end" value="<date:date value ="${communicateRecord.endtime} "/>"/> --%>
	                    </div>
                    	<div id="timeError"></div>
                    </div>
                </li>
                <li class="bt">
                    <div class="infor_name mt0">
                        <span>*</span>
                        <lable>沟通目的</lable>
                    </div>
                    <div class="f_left inputfloat table-largest">
                        <input type="radio" name="communicateGoal" value="468" checked="checked"/>
                        <label class="mr8">商机跟进</label> 
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
                        		 <c:if test="${(cm.sysOptionDefinitionId != 251) and (cm.sysOptionDefinitionId!=252) }">
		                            <li> 
		                                <c:if test="${cm.sysOptionDefinitionId == 250}">
		                                <input type="radio" name="communicateMode" value="${cm.sysOptionDefinitionId }" checked="checked"/>
		                                <label class="mr8">${cm.title }</label>
		                                </c:if>
		                                <c:if test="${cm.sysOptionDefinitionId != 250}">
		                                <input type="radio" name="communicateMode" value="${cm.sysOptionDefinitionId }"/>
		                                <label class="mr8">${cm.title }</label>
		                                </c:if>
		                            </li>
		                            </c:if>
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
							<%-- <div class="inputfloat manageaddtag">
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
								<input type="text" id="defineTag" placeholder="如果标签中没有您所需要的，请自行填写" class="input-large" style='height:26px;'>
								<div class="f_left bt-bg-style bg-light-blue bt-small  addbrand" onclick="addDefineTag(this);">添加</div>
							</div>
							<div class="addtags mt6 none">
								<ul id="tag_show_ul">
								</ul>
							</div> --%>
							<textarea rows="5%" cols="80%" id="content" name="content" >${communicateRecord.contactContent}</textarea>
						</div>
						<div id="tagError"></div>
					</div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <lable>下次沟通时间</lable>
                    </div>
                    <div class="f_left inputfloat ">
                        <input class="Wdate input-small " type="hidden" placeholder="15天后时间" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="15Date" id="15Date" value="<date:date value ='${hideDate} ' format="yyyy-MM-dd"/>"  />
                        <input class="Wdate input-small " type="hidden" placeholder="当前时间" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="nowDate" id="nowDate" value="<date:date value ='${nowDate} ' format="yyyy-MM-dd"/>"  />
                        <input class="Wdate input-small " type="text" placeholder="请选择时间" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'15Date\')}',minDate:'#F{$dp.$D(\'nowDate\')}'})" name="nextDate" id="nextDate" value="<date:date value ='${communicateRecord.nextContactDate} '/>"/>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>下次沟通内容</lable>
                    </div>
                    <div class="f_left  ">
                        <input type="text" class="input-xxx" name="nextContactContent" id="nextContactContent" value="${communicateRecord.nextContactContent }">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <lable>备注</lable>
                    </div>
                    <div class="f_left  ">
                        <input type="text" class="input-xxx" name="comments" id="comments" value="${communicateRecord.comments }">
                    </div>
                </li>
            </ul>
            <div class="add-tijiao tcenter ">
                <input type="hidden" name="name" id="name" value="" >
                <input type="hidden" name="telephone" id="telephone" value="">
            	<input type="hidden" name="beforeParams" value='${beforeParams}'/>
            	<input type="hidden" name="communicateRecordId" value="${communicateRecord.communicateRecordId }" >
            	<input type="hidden" name="traderId" value="${communicateRecord.traderId }" >
            	<input type="hidden" name="traderCustomerId" value="${communicateRecord.traderCustomerId }" >
            	<input type="hidden" name="bussinessChanceId" value="${bussinessChance.bussinessChanceId}">
                <button type="submit" id="submit">提交</button>
                <button class="dele" id="close-layer" type="button">取消</button>
            </div>
        </form>
    </div>
</body>

</html>

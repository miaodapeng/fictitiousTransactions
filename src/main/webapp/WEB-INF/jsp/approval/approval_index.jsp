<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="查询批准公示列表" scope="application" />	
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/approval/approval_index.js?rnd=<%=Math.random()%>"></script>
<style>
	.table tr td{
		height:62px;
		overflow:hidden;
	}
	.table tr td .title{
		height:24px;
		overflow:hidden;
		white-space:nowrap;
		text-overflow:ellipsis;
		line-height:22px;
	}
</style>
<div class="content">
	<div class="searchfunc">
		<form action="${pageContext.request.contextPath}/approval/approval/index.do" method="post" id="search">
			<ul>
				<li><label class="infor_name">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题:</label> <input type="text" placeholder='请输入关键字'
					class="input-middle" name="title" id="title" value="${approvalEntity.title}" />
				</li>
				
                <%-- <div class="infor_name" style="width: 468px;">
	               	<label class="label_info">发布时间：</label>
	                <input type="text" class="input-middle f_left Wdate" name="addTime" id="addTime" value="${addTime}" 
	                placeholder="请选择起始时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}',onpicked:function(){search()}})" oninput="search();">
	                <label style="width: 10px;text-align: center;">-</label>
	                <input style="float: right;" type="text" class="input-middle f_left Wdate" name="endTime" id="endTime" value="${endTime}" 
	                placeholder="请选择结束时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'addTime\')}',onpicked:function(){search()}})" oninput="search();">
				</div>  --%>
				
				<li><label class="infor_name">关注状态:</label>
				   <select class="input-middle" name="focusState" id="focusState" onchange="submitForm('search')">
				      <option value="0">请选择</option>
                      <option value="1" <c:if test="${approvalEntity.focusState eq 1}">selected="selected"</c:if>>未关注</option>
                      <option value="2" <c:if test="${approvalEntity.focusState eq 2}">selected="selected"</c:if>>已关注</option>
                   </select>
                </li>
                
				<label class="infor_name">发布时间:</label>
				<input readonly="readonly" placeholder="请选择起始时间" class="Wdate f_left input-smaller105 mr5" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})"
						name="addTime" id="addTime" value=<date:date value ="${approvalEntity.addTime} "/>><div class="gang">-</div>
					<input readonly="readonly" placeholder="请选择结束时间" class="Wdate f_left input-smaller105" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'addTime\')}'})"
						name="endTime" id="endTime" value='<date:date value ="${approvalEntity.endTime} "/>' > 
				</ul>
			
			<ul>
				 <li><label class="infor_name">跟进状态:</label>
				   <select class="input-middle" name="followState" id="followState" onchange="submitForm('search')">
				   	  <option value="0">请选择</option>
                      <option value="1" <c:if test="${approvalEntity.followState eq 1}">selected="selected"</c:if>>未跟进</option>
                      <option value="2" <c:if test="${approvalEntity.followState eq 2}">selected="selected"</c:if>>跟进中</option>
                      <option value="3" <c:if test="${approvalEntity.followState eq 3}">selected="selected"</c:if>>已完结</option>
                      <option value="4" <c:if test="${approvalEntity.followState eq 4}">selected="selected"</c:if>>纳入商机</option>
                   </select>
                </li>
                 
               <%--  <li><label class="infor_name">跟进人员:</label>
				   <select class="input-middle" name="focusState" id="focusState" onchange="submitForm('search')">
				      <option value="0">人员来源待确定--</option>
                      <option value="1" <c:if test="${approvalEntity.focusState eq 1}">selected="selected"</c:if>>未关注</option>
                      <option value="2" <c:if test="${approvalEntity.focusState eq 2}">selected="selected"</c:if>>已关注</option>
                   </select>
                </li> --%>
                
               <%--  <li><label class="infor_name">跟进人员:</label> <input type="text" placeholder='请输入跟进人员'
					class="input-middle" name="createName" id="createName" value="${approvalEntity.createName}" />
				</li> --%>
				
				 <li><label class="infor_name">跟进人员:</label> <input type="text" placeholder='请输入跟进人员'
					class="input-middle" name="updateName" id="updateName" value="${approvalEntity.updateName}" />
				</li>
				
				
				 <li><label class="infor_name">收录状态:</label>
				   <select class="input-middle" name="isInclude" id="isInclude" onchange="submitForm('search')">
				   <option value="2">请选择</option>
                      <option value="1" <c:if test="${approvalEntity.isInclude eq 1}">selected="selected"</c:if>>不再收录</option>
                   </select>
                </li>
                
                
			</ul>
			
			<div class="tcenter">
				<span class="bt-small bg-light-blue bt-bg-style" onclick="search();" id="searchSpan">查询</span> 
				<span class="bt-small bg-light-blue bt-bg-style" onclick="reset();">重置</span>
				<!-- <span class="bt-small bg-light-blue bt-bg-style addtitle" tabTitle='{"num":"add","link":"./approval/approval/add.do","title":"跟进记录"}'>跟进记录</span> -->
			</div>
		</form>
		
	</div>
	
	<div>
		<table
			class="table">
			<thead>
				<tr>
					<th class="wid5">请选择</th>
					<th class="wid4">序号</th>
					<th class="wid15">标题</th>
					<th class="wid8">发布时间</th>
					<th class="wid10">链接地址</th>
					<th class="wid7">机构名称</th>
					<th class="wid7">机构类别</th>
					<th class="wid7">机构科目</th>
					<th class="wid7">跟进人员</th>
					<th class="wid8">跟进状态</th>
					<th class="wid8">关注状态</th>
					<th class="wid15">操作</th>
				</tr>
			</thead>
			<tbody class="company">
				<c:if test="${not empty approvalList}">
					<c:forEach items="${approvalList}" var="approvalList" varStatus="status">
						<tr>
						
							<td><input class="cid_input" type="checkbox" name="checkOne" value="${approvalList.approvalId}" autocomplete="off"></td>
						
							<td>${status.count}</td>
							
							<td class="text-center">
	                            <div class="title" title='${approvalList.title }'>${approvalList.title }</div>
                            </td>
                            
                            <td class="text-center">
	                            <div class="title">
	                             ${approvalList.releaseTime }
								</div>
                            </td>
                            
                              <td class="text-center">
	                            <div class="title" >
	                            <span class="edit-user addtitle mb5"
									tabTitle='{"num":"click_info_${approvalList.approvalId}","link":"${approvalList.linkAddress }","title":"批准公示详情"}'>点击查看详情</span>
								</div>
                            </td>
                            
                             <td class="text-center">
	                            <div class="title" title='${approvalList.organizeName }'>
	                            	${approvalList.organizeName }
								</div>
                            </td>
                            
                             <td class="text-center">
	                            <div class="title" title='${approvalList.organizeType }'>
	                            ${approvalList.organizeType } 
								</div>
                            </td>
                            
                             <td class="text-center">
	                            <div class="title" title='${approvalList.organizeSubject }'>
	                            ${approvalList.organizeSubject }
								</div>
                            </td>
                            
                             <td class="text-center">
	                            <div class="title">
	                            <div>${approvalList.updateName } <br></div>
								</div>
                            </td>
                            
                            
                            <!-- 跟进状态 -->
                          <td>
                            <c:if test="${approvalList.followState == 1}">
                                <span style="color: black;">未跟进</span>
                            </c:if>
                            <c:if test="${approvalList.followState == 2}">
                                <span style="color: black;">跟进中</span>
                            </c:if>
                            <c:if test="${approvalList.followState == 3}">
                                <span style="color: black;">已完结</span>
                            </c:if>
                            <c:if test="${approvalList.followState == 4}">
                                <span style="color: black;">纳入商机</span>
                            </c:if>
                         </td>
                            
                            <!-- 关注状态 -->
                         <td>
                            <c:if test="${approvalList.focusState == 1}">
                                <span style="color: black;">未关注</span>
                            </c:if>
                            <c:if test="${approvalList.focusState == 2}">
                                <span style="color: black;">已关注</span>
                            </c:if>
                         </td>
                            
   <td>
   
	<!-- 跟进记录 按钮 ， 如果没数据，则新增。如果有数据，则修改 -->
	<span  class="edit-user addtitle mb5" 
		  tabTitle='{"num":"modifys${approvalList.approvalId}","link":"./approval/approval/tomodify.do?approvalId=${approvalList.approvalId}","title":"跟进记录"}'> 跟进记录 </span>
	
	 <span class="edit-user addtitle mb5"
					tabTitle='{"num":"approval_info_${approvalList.approvalId}","link":"./approval/approval/approval_info.do?approvalId=${approvalList.approvalId}","title":"批准公示详情"}'>查看详情</span>
					
	<%-- <span class="edit-user pop-new-data" 
		  layerParams='{"width":"700px","height":"450px","title":"查看详情","link":"./approval_info.do?approvalId=${approvalList.approvalId}"}'>查看详情</span> --%> </br>
	
    				<span style=''>
                         <c:if test="${approvalList.focusState == 1 }">
                             <span class="edit-user " onclick="changeFocusState(2,${approvalList.approvalId})">关注</span>
                         </c:if>
                         <c:if test="${approvalList.focusState == 2 }">
                             <span class="forbid clcforbid " onclick="changeFocusState(1,${approvalList.approvalId})">取消关注</span>
                         </c:if>
                     </span>
                     
					 <span >
                         <c:if test="${approvalList.isInclude == 1 }">
                             <span class="edit-user" onclick="changeIsInclude(2,${approvalList.approvalId})">重新收录</span>
                         </c:if>
                         <c:if test="${approvalList.isInclude == 2 }">
                             <span class="forbid clcforbid" onclick="changeIsInclude(1,${approvalList.approvalId})">不再收录</span>
                         </c:if>
                     	</span>
                     	 
   </td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		
		<c:if test="${empty approvalList}">
			<div class="noresult">查询无结果！请尝试使用其他搜索条件。</div>
		</c:if>
		
			<div class="inputfloat f_left" style='margin:0px 0 15px 0;'>
					<!-- //isLimit 表示当查询不再收录的数据时，不允许左下角的"不再收录"点击 -->
					<c:if test="${!empty approvalList and isLimit eq 0}">
					      <input type="checkbox" class="mt6 mr4" name="checkAll" autocomplete="off"> <label class="mr10 mt4">全选</label>
					      <span class="bt-bg-style bg-light-blue bt-small mr10" onclick="delApprovalBatch();">不再收录</span> 
				    </c:if>
				    
				    <c:if test="${!empty approvalList and isLimit eq 1}">
					      <input type="checkbox" class="mt6 mr4" name="checkAll" autocomplete="off"> <label class="mr10 mt4">全选</label>
					      <span class="bt-bg-style bg-light-blue bt-small mr10" onclick="no();">不再收录</span> 
				    </c:if> 
				    
			</div>
			
		
		<tags:page page="${page}" />
	</div>
</div>

<%@ include file="../common/footer.jsp"%>

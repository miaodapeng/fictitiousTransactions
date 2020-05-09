<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="跟进记录" scope="application" />	
<%@ include file="../common/common.jsp"%>
<style>
	.record{
		width:490px;
		margin-left:70px;
	}
	.record-page{
		width:490px;
		margin-left:55px;
	}
	.record-tit{
	  overflow:hidden;
	  text-align:center;
	  margin-bottom:5px;
	}
	.record-user{
		margin-top:2px;
	}
	.record-date{
		font-weight:700;
		font-size:13px;
	}
	.record-edit,
	.record-dlt{
	 color:#999;
	 cursor:pointer;
	}
	.record-edit:hover,.record-dlt:hover{
	 color:#3384ef;
	}
	.record-dlt{	
		 margin-left:10px;
	}
	h1{
	  font-size:18px;
	  font-weight:700;
	  margin-top:0;
	}
	form .infor_name{
		width:145px;
	}
	.ml70{
		margin-left:70px;
	}
	.employeesubmit{
		text-align:left;
		margin-left:250px;
	}
	.edit-ensure{
		margin-left:185px;
	}
	textarea{
		width:483px;
		resize: vertical;
	}
	.record-cont{
	    line-height: 20px;
	    position:relative;
		
		overflow: hidden;
		/* text-overflow: ellipsis;
		text-overflow: -o-ellipsis-lastline;
		display: -webkit-box;
		-webkit-line-clamp: 2;
		line-clamp: 2;
		-webkit-box-orient: vertical;   */
		word-break:break-all; 
    }
    .record-cont.record-cont-height{
    	max-height: none!important;
    	-webkit-line-clamp: initial;
    	
    }
    .open{
     	margin-bottom: 10px;
     	width:30px;
     	/* margin-right: 1000px; */ 
     	float:right;
    }
      .open J-open{
     	margin-bottom: 10px;
     	width:30px;
     	float:right;
    }
    .open:hover{
      cursor:pointer;
    }
    .edit-record{
    	display:none;
    }
    .edit-li .edit-record{
    	display:block;
    }
    .edit-li .record-shows,
    .edit-li .record-edit{
    	display:none;
    }
    
    .edit-ensure button{
      background:#ddd;
      display:inline-block;
      width:40px;
      text-align:center;
      height:26px;
      line-height:26px;
    }
    .edit-ensure .edit-sure{
     margin-right:7px;
    }
    .close{
     display:none;
     cursor:pointer;
     float:right;
     width:30px;
    }
	
</style>
<div class="content">
	<div class='newemployee  formpublic'>
		<form action="${pageContext.request.contextPath}/approval/approval/modify.do" method="post" id="addApprovalForm">
			<div class="main-container">
				<h1>关键信息</h1>
			<ul>
				<li>
					<div class="infor_name">
						 <label>医疗机构名称:</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest" name=organizeName id="organizeName" placeholder='请输入机构名称'
							value="${approvalEntity.organizeName}" />
					</div>
					<div class="clear"></div>
				</li>
				
				<li>
					<div class="infor_name">
						 <label>医疗机构类别:</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest" name=organizeType id="organizeType" placeholder='请输入机构类别' 
							value="${approvalEntity.organizeType}" />
					</div>
					<div class="clear"></div>
				</li>
				
				<li>
					<div class="infor_name">
						 <label>医疗机构科目:</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest" name=organizeSubject id="organizeSubject" placeholder='请输入机构科目' 
							value="${approvalEntity.organizeSubject}" />
					</div>
					<div class="clear"></div>
				</li>
				
				<li>
					<div class="infor_name">
						 <label>联系地址:</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest" name=contactAddress id="contactAddress" placeholder='请输入地址信息' 
							value="${approvalEntity.contactAddress}" />
					</div>
					<div class="clear"></div>
				</li>
				
				<li>
					<div class="infor_name">
						 <label>联系电话:</label>
					</div>
					<div class="f_left">
						<input type="text" class="input-largest" name=contactPhone id="contactPhone" placeholder='请输入联系电话' 
							value="${approvalEntity.contactPhone}" />
					</div>
					<div class="clear"></div>
				</li>
				
					<%-- <li>
		            	<div class="infor_name">
						<lable>关注状态</lable>
						</div>
						<div class="f_left">
						<c:if test="${not empty approvalEntity and approvalEntity.focusState eq 2 }">
							<input type="checkbox" name="focusState" value="${approvalEntity.focusState }" checked>关注
						</c:if>
						
						<c:if test="${not empty approvalEntity and approvalEntity.focusState eq 1 }">
							<input type="checkbox" name="focusState" value="${approvalEntity.focusState }">关注
						</c:if>
						
						</div>
		            </li> --%>
		            
		            
		            
		            <li>
		            	<div class="infor_name">
						<lable>关注状态:</lable>
						</div>
						
						<!--  <div class="f_left">
						<input type="checkbox" value="2" name="focusState">关注
						</div> -->
						
						    <c:if test="${not empty approvalEntity and approvalEntity.focusState eq 2 }">
								<input type="checkbox" name="focusState" checked="checked" value="2">关注
						    </c:if>
						    
						    <c:if test="${ (empty approvalEntity.focusState) or (approvalEntity.focusState eq 1) or (approvalEntity.focusState eq null) }">
								<input type="checkbox" name="focusState" value="2">关注
						    </c:if> 
						    
		            </li>
		            
		            
		            	
                   <li>
                       <div class="infor_name">
                          <label>跟进状态:</label>
                      </div>
                      <div class="f_left">
						<select class="input-middle" name="followState" id="followState">
							<option value="0">请选择</option>
                            <option value="1" <c:if test="${approvalEntity.followState eq 1}">selected="selected"</c:if>>未跟进</option>
                            <option value="2" <c:if test="${approvalEntity.followState eq 2}">selected="selected"</c:if>>跟进中</option>
                            <option value="3" <c:if test="${approvalEntity.followState eq 3}">selected="selected"</c:if>>已完结</option>
                            <option value="4" <c:if test="${approvalEntity.followState eq 4}">selected="selected"</c:if>>纳入商机</option>
                        </select>
					</div>
                  </li>
                  
           			 <!-- 子表数据   -->	
		            <h1>跟进记录</h1>
           			<div class='ml70 follow-record'>
           				<textarea cols="f_left" id="recordContent" name="approvalRecordList[0].recordContent" placeholder="请输入跟进内容,最多不超过500字" rows=3 >${approvalRecordEntity.recordContent}</textarea>
           			</div>
			</ul>
		</div>
			
			<!-- 保存按钮的div -->
			 <div class="add-tijiao employeesubmit">
				<c:if test="${not empty approvalEntity and approvalEntity.approvalId > 0 }">
					<input type="hidden" name="approvalId" value="${approvalEntity.approvalId }">
				</c:if>
				<button id="submit" type="submit">保存</button>
				<button type="button" class="dele" id="cancle" onclick='pagesContrlpages(true,false,true)'>返回</button>
				<!-- 返回最新的数据 -->
				<%-- <button type="submit" class="edit-user addt itle mb5"
					tabTitle='{"num":"index_${approvalEntity.approvalId}","link":"./approval/approval/reloadindex.do?approvalId=${approvalEntity.approvalId}","title":"批准公示"}'>返回</button> --%>
			</div> 
			
		</form>
		
		
			<!-- 子表数据   -->	
			<!-- 第二个 form 表单 -->
			<form action="${pageContext.request.contextPath}/approval/approval/tomodifyrecord.do" method="post" id="modifyApprovalForm">
            <h1 >记录详情</h1>
     		<div class='record'>
     		  <ul class="J-wrap">
     		 <c:if test="${not empty approvalRecordList }">
                  <c:forEach items="${approvalRecordList }" var="approvalRecordList">
                  <li class="J-item">
                  		<input type="hidden" value="${approvalRecordList.approvalId}" class="J-appId">
	      				<div class='record-tit'>
		      				<div class='font-blue f_left record-user'>${approvalRecordList.createName} </div>
		      				<span class='record-date'><date:date value ="${approvalRecordList.modTime}"/></span>
		      				<div class='f_right'>
		      				
		      				<!-- 如果isLimit为1，则允许编辑。为1指的是userId相等时 -->
		      				<c:choose>
			      				<c:when test="${approvalRecordList.isLimit eq 1}">  
			      				      <span class='record-edit' onclick='editRecord(this)'>编辑</span>
			      			          <span class='record-dlt'  onclick='dltRecord(${approvalRecordList.recordId })'>删除</span>
			      			    </c:when>
			      			    <c:otherwise>
		      			    		  <span class='record-edit' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			      			          <span class='record-dlt' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			      			    </c:otherwise>
		      				</c:choose>
		      			   
		      			   </div>
		      			</div>
		      			
	      				<div class='record-shows'>
	      					<div class='record-cont'>${approvalRecordList.recordContent}</div>
	      				    <div float:right; class='open J-open' onclick='openMore(this)'><span  class="font-blue" >展开</span></div>
	      				    <div class='close ' onclick='closeMore(this)'><span class="font-blue" >关闭</span></div>
	      				</div>
	      				
	      				<div class='edit-record'>
	      					<textarea name="content" class="J-cnt"  rows=3 ></textarea>
							 <div class=" employeesubmit edit-ensure">
								<button  type="submit" class='edit-sure J-edit' data-record="${approvalRecordList.recordId}" onclick="heightCtl(this);">确定</button>
								<button  type="button" class="edit-dele" onclick='cancleEdit(this)'>取消</button>
							</div> 
	      				</div>
	      				
      				</li>
      		      </c:forEach>
             </c:if>
             </ul>
      		</div>
      		
		 </form>
		 
		<div>
		
		<!-- 小分页 -->
		<div class="record-page">
			<c:if test="${isPage eq 1 }">  <!-- 如果查出的数据不满10条,则不显示分页器 -->
				<tags:page page="${page}" />
			</c:if>
			
		</div>
		
	</div>
</div>

<script type="text/javascript" src='<%= basePath %>static/js/approval/approval_modify.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../common/footer.jsp"%>






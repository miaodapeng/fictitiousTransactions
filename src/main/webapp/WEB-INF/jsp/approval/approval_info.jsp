<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="批准公示详情" scope="application" />	
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
    .open:hover{
      cursor:pointer;
    }
    .edit-record{
    	display:none;
    }
    .edit-li .edit-record{
    	display:block;
    }
   /*  .edit-li .record-shows,
    .edit-li .record-edit{
    	display:none;
    } */
    
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
			
			    <!-- 子表数据   -->	
	            <h1>基本信息</h1>
	            <ul>
          			<li>
					<div class="infor_name">
						 <label>标题名称:</label>
					</div>
					<div class="f_left">
						${approvalEntity.title}
					</div>
					<div class="clear"></div>
				</li>
				
				<li>
					<div class="infor_name">
						 <label>发布时间:</label>
					</div>
					<div class="f_left">
						${approvalEntity.releaseTime}
					</div>
					<div class="clear"></div>
				</li>
				
				<li>
					<div class="infor_name">
						 <label>链接地址:</label>
					</div>
					
					<div class="f_left" style="width:calc(100% - 500px);">
						<td>${approvalEntity.linkAddress}</td>
					</div>
					
					
					<div class="clear"></div>
				</li>
				
				
				</ul>
           			
				<h1>关键信息</h1>
			<ul>
				<li>
					<div class="infor_name">
						 <label>医疗机构名称:</label>
					</div>
					<div class="f_left">
						${approvalEntity.organizeName}
					</div>
					<div class="clear"></div>
				</li>
				
				<li>
					<div class="infor_name">
						 <label>医疗机构类别:</label>
					</div>
					<div class="f_left">
						${approvalEntity.organizeType}
					</div>
					<div class="clear"></div>
				</li>
				
				<li>
					<div class="infor_name">
						 <label>医疗机构科目:</label>
					</div>
					<div class="f_left">
						${approvalEntity.organizeSubject}
					</div>
					<div class="clear"></div>
				</li>
				
				<li>
					<div class="infor_name">
						 <label>联系地址:</label>
					</div>
					<div class="f_left">
						${approvalEntity.contactAddress}
					</div>
					<div class="clear"></div>
				</li>
				
				<li>
					<div class="infor_name">
						 <label>联系电话:</label>
					</div>
					<div class="f_left">
						${approvalEntity.contactPhone}
					</div>
					<div class="clear"></div>
				</li>
				
		            <li>
		            	<div class="infor_name">
							  <lable>关注状态:</lable>
						</div>
						      <c:if test="${approvalEntity.focusState eq 1}">未关注</c:if>
                              <c:if test="${approvalEntity.focusState eq 2}">已关注</c:if>
		            </li>
		            
		            	
                   <li>
                       <div class="infor_name">
                          <label>跟进状态:</label>
                      </div>
                      <div class="f_left">
                           <c:if test="${approvalEntity.followState eq 1}">未跟进</c:if>
                           <c:if test="${approvalEntity.followState eq 2}">跟进中</c:if>
                           <c:if test="${approvalEntity.followState eq 3}">已完结</c:if>
                           <c:if test="${approvalEntity.followState eq 4}">纳入商机</c:if>
					</div>
                  </li>
                  
			</ul>
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
		      			</div>
		      			
	      				<div class='record-shows'>
	      					<div class='record-cont'>${approvalRecordList.recordContent}</div>
	      				</div>
	      				
      				</li>
      		      </c:forEach>
             </c:if>
             </ul>
      		</div>
      		
		 </form>
		 
			<!-- 保存按钮的div , 详情页'返回'按钮置于底部 -->
			 <div class="add-tijiao employeesubmit">
				<c:if test="${not empty approvalEntity and approvalEntity.approvalId > 0 }">
					<input type="hidden" name="approvalId" value="${approvalEntity.approvalId }">
				</c:if>
				<!-- <button id="submit" type="submit">保存</button> -->
				<button type="button" class="dele" id="cancle" onclick='pagesContrlpages(true,false,false)'>返回</button>
			</div> </br>
			
		
			<!-- 小分页 -->
			<div class="record-page"> 
				<c:if test="${isPage == 1 }">  <!-- 如果查出的数据不满10条,则不显示分页器 -->
					<tags:page page="${page}" />
				</c:if>
			</div>
		
</div>

<script type="text/javascript" src='<%= basePath %>static/js/approval/approval_info.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<%@ include file="../common/footer.jsp"%>





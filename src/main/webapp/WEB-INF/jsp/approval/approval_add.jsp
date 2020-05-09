<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增批准公示" scope="application" />	
<%@ include file="../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/approval/approval_add.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<div class="content">
	<div class='newemployee  formpublic'>
		<form action="${pageContext.request.contextPath}/approval/approval/add.do" method="post" id="addApprovalForm">
			<!-- 包一个大div -->
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
				
					<li>
		            	  <div class="infor_name">
						<lable>关注状态</lable>
						</div>
						<div class="f_left">
						<input type="checkbox" value="2" name="focusState">关注
						</div>
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
           			<td style="text-align: left;">
           				<textarea rows="" cols="f_left" id="recordContent" name="approvalRecordList[0].recordContent" placeholder="请输入跟进内容,最多不超过500字" style="width: 30%;height: 198px;">${approvalRecordEntity.recordContent}</textarea>
           			</td>

			</ul>
		</div>
			
			
			<!-- 提交按钮的div -->
			
			<%-- <div class="add-tijiao employeesubmit">
				<c:if test="${not empty approvalEntity and approvalEntity.approvalId > 0 }">
					<input type="hidden" name="approvalId" value="${approvalEntity.approvalId }">
				</c:if>
				<button id="submit" type="submit">提交</button>
			</div> --%>
			
			<div class="add-tijiao tcenter">
				<input type="hidden" name="formToken" value="${formToken}"/>
				<button type="submit">保存</button>
				<button type="button" class="dele" id="cancle" onclick='pagesContrlpages(true,false,false)'>返回</button>
			</div>
			
		</form>
		
	</div>
</div>

<c:if test="${type != 1}"> 
<!-- 以下ueditor编辑器需要引用的文件 -->
<script type="text/javascript" charset="utf-8" src="<%= basePath %>static/libs/textmodify/ueditor.config.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" charset="utf-8" src="<%= basePath %>static/libs/textmodify/ueditor.all.min.js?rnd=<%=Math.random()%>"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="<%= basePath %>static/libs/textmodify/lang/zh-cn/zh-cn.js?rnd=<%=Math.random()%>"></script>
<!-- 以上ueditor编辑器需要引用的文件 -->

<!-- 以下为异步上传附件要引用的文件 -->
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<!-- 以上为异步上传附件要引用的文件 -->

<!-- 以下为UEditor插件要引用的文件 -->
<script type="text/javascript">
	UE.getEditor('content',{wordCount:true,maximumWords:512});
	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	UE.Editor.prototype.getActionUrl = function(action) {
		if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage' || action == 'uploadfile' || action == 'uploadvideo') {//上传附件类型
			return '${path}fileUpload/ueditFileUpload.do?uploadType=uedit';//自定义编辑器中附件上传路径
		} else {
			return this._bkGetActionUrl.call(this, action);//插件默认上传
		}
	}
</script>
</c:if>

<%@ include file="../common/footer.jsp"%>


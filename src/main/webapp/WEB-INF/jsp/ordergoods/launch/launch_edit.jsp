<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${ordergoodsStore.name }编辑投放"
	scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8">
	<form method="post"  action="${pageContext.request.contextPath}/ordergoods/manage/saveeditordergoodslaunch.do" id="myform">
		<ul>
			<li>
				<div class="form-tips">
					<lable>客户名称</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">${ordergoodsLaunchVo.traderName }</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>设备名称</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<span id="goodsName">${ordergoodsLaunchVo.goodsName } [${ordergoodsLaunchVo.sku }]</span>
						<span class="bt-small bt-bg-style bg-light-blue pop-new-data" layerParams='{"width":"900px","height":"500px","title":"搜索产品","link":"./searchgoods.do"}'>搜索</span>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>设备对应试剂分类</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks" id="categoryNames">${ordergoodsLaunchVo.categoryNames }</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>任务指标</lable>
				</div>
				<div class="f_left" style="width: 50%">
					<div class="form-blanks">
						<table class="table">
							<thead>
								<tr>
									<th class="wid6"></th>
									<th class="wid11">开始时间</th>
									<th class="wid11">结束时间</th>
									<th class="wid11">指标额（万）</th>
									
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${goalList }" var="goal" varStatus="status">
									<tr>
										<td>第${status.count }年</td>
										<td><input class="Wdate" type="text"
			                    		onClick="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddate${status.count }\')}'})"
			                    		id="startdate${status.count }" name="startdate"
			                    		value="<date:date value="${goal.startTime} " format="yyyy-MM-dd"/>"></td>
										<td><input class="Wdate" type="text"
			                    		onClick="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startdate${status.count }\',{M:+12,d:-1})}'})"
			                    		id="enddate${status.count }" name="enddate"
			                    		value="<date:date value="${goal.endTime} " format="yyyy-MM-dd"/>"></td>
										<td><input name="goal" value="${goal.goalAmount}" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" ></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</li>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="rOrdergoodsLaunchGoodsJCategoryId" id="rOrdergoodsLaunchGoodsJCategoryId" value="${ordergoodsLaunchVo.rOrdergoodsLaunchGoodsJCategoryId }"/>
			<input type="hidden" name="ordergoodsLaunchId" id="ordergoodsLaunchId" value="${ordergoodsLaunchVo.ordergoodsLaunchId }"/>
			<input type="hidden" name="beforeParams" value='${beforeParams}'/>
			<button type="submit">提交</button>
		</div>
	</form>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/ordergoods/launch/launch_edit.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
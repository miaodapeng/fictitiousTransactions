<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="${ordergoodsStore.name }添加投放"
	scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list  form-tips8">
	<form method="post"  action="${pageContext.request.contextPath}/ordergoods/manage/saveaddordergoodslaunch.do" id="myform">
		<ul>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>客户名称</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<span id="traderName"></span>
						<span class="bt-small bt-bg-style bg-light-blue pop-new-data" layerParams='{"width":"900px","height":"500px","title":"搜索客户","link":"./searchtradercustomer.do"}'>搜索</span>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<span>*</span>
					<lable>设备名称</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks">
						<span id="goodsName"></span>
						<span class="bt-small bt-bg-style bg-light-blue pop-new-data" layerParams='{"width":"900px","height":"500px","title":"搜索产品","link":"./searchgoods.do"}'>搜索</span>
					</div>
				</div>
			</li>
			<li>
				<div class="form-tips">
					<lable>设备对应试剂分类</lable>
				</div>
				<div class="f_left ">
					<div class="form-blanks" id="categoryNames"></div>
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
								<tr>
									<td>第1年</td>
									<td><input class="Wdate" type="text"
		                    		onClick="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddate1\')}'})"
		                    		id="startdate1" name="startdate"
		                    		value=""></td>
									<td><input class="Wdate" type="text"
		                    		onClick="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startdate1\',{M:+12,d:-1})}'})"
		                    		id="enddate1" name="enddate"
		                    		value=""></td>
									<td><input name="goal" value="" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" ></td>
								</tr>
								<tr>
									<td>第2年</td>
									<td><input class="Wdate" type="text"
		                    		onClick="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddate2\')}',minDate:'#F{$dp.$D(\'enddate1\',{d:+1})}'})"
		                    		id="startdate2" name="startdate"
		                    		value=""></td>
									<td><input class="Wdate" type="text"
		                    		onClick="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startdate2\',{M:+12,d:-1})}'})"
		                    		id="enddate2" name="enddate"
		                    		value=""></td>
									<td><input name="goal" value="" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" ></td>
								</tr>
								<tr>
									<td>第3年</td>
									<td><input class="Wdate" type="text"
		                    		onClick="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddate3\')}',minDate:'#F{$dp.$D(\'enddate2\',{d:+1})}'})"
		                    		id="startdate3" name="startdate"
		                    		value=""></td>
									<td><input class="Wdate" type="text"
		                    		onClick="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startdate3\',{M:+12,d:-1})}'})"
		                    		id="enddate3" name="enddate"
		                    		value=""></td>
									<td><input name="goal" value="" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" ></td>
								</tr>
								<tr>
									<td>第4年</td>
									<td><input class="Wdate" type="text"
		                    		onClick="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddate4\')}',minDate:'#F{$dp.$D(\'enddate3\',{d:+1})}'})"
		                    		id="startdate4" name="startdate"
		                    		value=""></td>
									<td><input class="Wdate" type="text"
		                    		onClick="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startdate4\',{M:+12,d:-1})}'})"
		                    		id="enddate4" name="enddate"
		                    		value=""></td>
									<td><input name="goal" value="" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" ></td>
								</tr>
								<tr>
									<td>第5年</td>
									<td><input class="Wdate" type="text"
		                    		onClick="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddate5\')}',minDate:'#F{$dp.$D(\'enddate4\',{d:+1})}'})"
		                    		id="startdate5" name="startdate"
		                    		value=""></td>
									<td><input class="Wdate" type="text"
		                    		onClick="WdatePicker({isShowClear:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startdate5\',{M:+12,d:-1})}'})"
		                    		id="enddate5" name="enddate"
		                    		value=""></td>
									<td><input name="goal" value="" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" ></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</li>
		</ul>
		<div class="add-tijiao tcenter">
			<input type="hidden" name="traderCustomerId" id="traderCustomerId" value=""/>
			<input type="hidden" name="rOrdergoodsLaunchGoodsJCategoryId" id="rOrdergoodsLaunchGoodsJCategoryId" value=""/>
			<button type="submit">提交</button>
		</div>
	</form>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/ordergoods/launch/launch_add.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
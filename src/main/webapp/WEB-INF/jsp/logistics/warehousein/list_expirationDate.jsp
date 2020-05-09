<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="效期管理列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container logistics-warehousein-index">
	<div class="searchfunc">
		<form method="post" id="search" action="<%=basePath%>logistics/warehousein/expirationDateList.do">
			<ul>
				<li>
					<label class="infor_name">产品名称</label>
					<input type="text" class="input-middle" placeholder="" name="goodsName"  value="${warehouseGoodsOperateLog.goodsName}"/>
				</li>
				<li>
					<label class="infor_name">产品品牌</label>
					<input type="text" class="input-middle" placeholder="" name="brandName"  value="${warehouseGoodsOperateLog.brandName}"/>
				</li>
				<li>
					<label class="infor_name">产品型号</label>
					<input type="text" class="input-middle" placeholder="" name="model"  value="${warehouseGoodsOperateLog.model}"/>
				</li>
				<li>
					<label class="infor_name">订货号</label>
					<input type="text" class="input-middle" placeholder="" name="sku"  value="${warehouseGoodsOperateLog.sku}"/>
				</li>
				<li>
					<label class="infor_name">物料编码</label>
					<input type="text" class="input-middle" placeholder="" name="materialCode"  value="${warehouseGoodsOperateLog.materialCode}"/>
				</li>
				<li>
					<label class="infor_name">采购订单</label>
					<input type="text" class="input-middle" placeholder="" name="buyorderNo"  value="${warehouseGoodsOperateLog.buyorderNo}"/>
				</li>
				<li>
					<label class="infor_name">批次号</label>
					<input type="text" class="input-middle" placeholder="" name="batchNumber"  value="${warehouseGoodsOperateLog.batchNumber}"/>
				</li>
				<li>
                    <label class="infor_name">商品归属</label>
                    <select class="input-middle f_left" name="goodsUserId">
	                    <option value="-1">全部</option>
	                    <c:if test="${not empty buyerList }">
							<c:forEach items="${buyerList }" var="user">
								<option value="${user.userId }"
									<c:if test="${warehouseGoodsOperateLog.goodsUserId eq user.userId }">selected="selected"</c:if>>${user.username }</option>
							</c:forEach>
						</c:if>
                    </select>
                   </li>
				<li>
					<label class="infor_name">效期截止日期</label>
					<select class="input-middle" name="expirationDateStatus" >
						<option value="1" <c:if test="${expirationDateStatus==1}">selected="selected"</c:if>>存在</option>
						<option value="2" <c:if test="${expirationDateStatus==2}">selected="selected"</c:if>>不存在</option>
						<option value="3" <c:if test="${expirationDateStatus==3}">selected="selected"</c:if>>全部</option>
						<c:forEach items="${productOrgList}" var="org">
							<option value="${org.parentId}" <c:if test="${buyorderVo.proOrgtId eq org.orgId}">selected="selected"</c:if>>${org.orgName}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">效期状态</label>
					<select class="input-middle" name="expirationDateWarnStatus" id="">
						<option value="4" <c:if test="${warehouseGoodsOperateLog.expirationDateWarnStatus==4}">selected="selected"</c:if>>全部</option>
						<option value="1" <c:if test="${warehouseGoodsOperateLog.expirationDateWarnStatus==1}">selected="selected"</c:if>>有效</option>
						<option value="2" <c:if test="${warehouseGoodsOperateLog.expirationDateWarnStatus==2}">selected="selected"</c:if>>临期</option>
						<option value="3" <c:if test="${warehouseGoodsOperateLog.expirationDateWarnStatus==3}">selected="selected"</c:if>>失效</option>
						
					</select>
				</li>
			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				<span class="bg-light-blue bt-bg-style bt-small pop-new-data" layerParams='{"width":"400px","height":"200px","title":"设置效期预警天数","link":"./uploadexpirationday.do"}'>设置效期预警天数</span>
			</div>
		</form>
	</div>
	<div class="list-page">
            <!-- normal-list-page -->
            <div class="fixdiv">
                <div class="superdiv">
                    <table class="table">
                        <thead>
                            <tr>
                               <th class="wid5">序号</th>
		                       <th>产品名称</th>
		                       <th class="wid10">品牌</th>
		                       <th class="wid10">型号</th>
		                       <th class="wid8">物料编码</th>
		                       <th>单位</th>
		                       <th>效期截止日期</th>
		                       <th>批次号</th>
		                       <th>商品归属</th>
		                       <th>库位</th>
		                       <th>库位备注</th>
		                       <th>入库日期</th>
		                       <th>入库单据</th>
		                       <th>更新时间</th>
		                       <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                         <c:forEach var="wlist" items="${list}" varStatus="num">
							<c:set var="status" value="0"></c:set>
							<c:set var="color" value=""></c:set>
							<c:if test="${0 != wlist.expirationDate &&(wlist.expirationDate-time < 3600*24*90*1000)}">
								<c:set var="status" value="2"></c:set>
								<c:set var="color" value="orange"></c:set>
							</c:if>
							<c:if test="${0 != wlist.expirationDate && (wlist.expirationDate<time)}">
								<c:set var="status" value="1"></c:set>
								<c:set var="color" value="red"></c:set>
							</c:if>
                            <tr class="J-skuInfo-tr" skuId="${wlist.goodsId}">
                                	<td>${num.count}</td>
                                	<td>
                                		<span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewgoods${wlist.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${wlist.goodsId}","title":"产品信息"}'>${wlist.goodsName}</span><br/>
                                		<span>${wlist.sku}</span> 
                                	</td>
                                	<td CLASS="JbrandName">${wlist.brandName}</td>
                                	<td CLASS="JskuModel">${wlist.model}</td>
                                    <td class="JmaterialCode">${wlist.materialCode}</td>
                                    <td class="JskuUnit">${wlist.unitName}</td>
                                    <td><font color="${color}"><date:date value ="${wlist.expirationDate}" format="yyyy-MM-dd"/></font></td>
                                    <td>${wlist.batchNumber}</td>
                                    <!-- 商品归属 -->
			                        <td class="Jproductmanager">
<%--			                        	<c:if test="${not empty wlist.userList }">--%>
<%--											<c:forEach items="${wlist.userList }" var="user" varStatus="st">--%>
<%--												${user.username } <c:if test="${st.count != wlist.userList.size() }">、</c:if>--%>
<%--											</c:forEach>--%>
<%--										</c:if>--%>
									</td>
                                    <td>${wlist.warehouseArea}</td>
                                    <td>${wlist.comments}</td>
                                    <td><date:date value ="${wlist.addTime}" format="yyyy-MM-dd"/></td>
                                    <td>
	                                    <a class="addtitle" href="javascript:void(0);" 
												tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
														"link":"./order/buyorder/viewBuyorder.do?buyorderId=${wlist.buyorderId}","title":"采购单信息"}'>${wlist.buyorderNo}
										</a>
                                    </td>
                                    <td><date:date value ="${wlist.modTime}" format="yyyy-MM-dd"/></td>
                                    <td>
                                    	<c:if test="${status!= 1}">
                                    	<a class="pop-new-data" layerparams='{"width":"400px","height":"320px","title":"编辑效期信息","link":"./editExpirationDate.do?&warehouseGoodsOperateLogId=${wlist.warehouseGoodsOperateLogId}"}'>编辑</a>
                                    	</c:if>
                                    	<c:if test="${status == 1}">
                                    		<font color="${color}">
                                    		已失效
                                    		</font>
                                    	</c:if>
                                    </td>
                            </tr>
							</c:forEach>
                        </tbody>
                    </table>
                </div>               
            </div>
            <div class="parts">
             <div class="f_left table-friend-tip">
				友情提示
                <br/>1、临近效期以红色优先展示，失效后不允许编辑
                </div>
      			<tags:page page="${page}" />
         </div>
       </div>
    	
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseIn/index.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript"
		src='<%= basePath %>static/new/js/pages/goods/goodinfoajax.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

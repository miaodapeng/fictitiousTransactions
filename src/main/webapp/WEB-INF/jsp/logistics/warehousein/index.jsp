<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="采购入库列表" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="customer">
	<ul>
		<li><a href="/logistics/warehousein/index.do" class="customer-color">采购入库</a></li>
		<li><a href="/aftersales/order/getChangeAftersales.do">采购售后入库</a>
		</li>
		<li><a href="/aftersales/order/getStorageAftersales.do">销售售后入库</a>
		</li>
		<li><a href="/logistics/warehousein/lendOutIndex.do">商品归还入库</a></li>
	</ul>
</div>
<div class="content logistics-warehousein-index">
	<div class="searchfunc">
		<form method="post" id="search" action="<%=basePath%>logistics/warehousein/index.do">
			<ul>
				<li>
					<label class="infor_name">供应商</label>
					<input type="text" class="input-middle" placeholder="" name="traderName"  value="${buyorderVo.traderName}"/>
				</li>
				<li>
					<label class="infor_name">产品名称</label>
					<input type="text" class="input-middle" placeholder="" name="goodsName"  value="${buyorderVo.goodsName}"/>
				</li>
				<li>
					<label class="infor_name">品牌</label>
					<input type="text" class="input-middle" placeholder="" name="brandName"  value="${buyorderVo.brandName}"/>
				</li>
				<li>
					<label class="infor_name">型号</label>
					<input type="text" class="input-middle" placeholder="" name="model"  value="${buyorderVo.model}"/>
				</li>
				<li>
					<label class="infor_name">订货号</label>
					<input type="text" class="input-middle" placeholder="" name="sku"  value="${buyorderVo.sku}"/>
				</li>
				<li>
					<label class="infor_name">物料编码</label>
					<input type="text" class="input-middle" placeholder="" name="materialCode"  value="${buyorderVo.materialCode}"/>
				</li>
				<li>
					<label class="infor_name">采购单号</label>
					<input type="text" class="input-middle" placeholder="" name="buyorderNo"  value="${buyorderVo.buyorderNo}"/>
				</li>
				<li>
					<label class="infor_name">产品部门</label>
					<select class="input-middle" name="proOrgtId" id="">
						<option value="">全部</option>
						<c:forEach items="${productOrgList}" var="org">
							<option value="${org.parentId}" <c:if test="${buyorderVo.proOrgtId eq org.orgId}">selected="selected"</c:if>>${org.orgName}</option>
						</c:forEach>
					</select>
				</li>
				<li>
					<label class="infor_name">产品人员</label>
					<select class="input-middle" name="creator" id="creator">
						<option value="">全部</option>
						<c:forEach var="list" items="${productUserList}" varStatus="status">
							<option value="${list.userId}">${list.username}</option>
						</c:forEach>
					</select>
				</li>
				
				   <li>
                    <label class="infor_name">生效时间</label>
                    <input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="beginTime" value="${beginTime}">
					<div class="gang">-</div> 
					<input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="endTime" value="${endTime}">
                </li>
			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" >导出列表</span>
			</div>
		</form>
	</div>
	<div class="table-style5">
 <c:forEach var="buylist" items="${list}" varStatus="num">
           <table class="table">
               <thead>
                   <tr>
                       <th class="wid5">序号</th>
                       <th class="wid12">采购单号</th>
                       <th class="wid8">生效时间</th>
                       <th>供应商</th>
                       <th class="wid10">产品部门</th>
                       <th class="wid10">产品负责人</th>
                       <th class="wid10">订单总额</th>
                       <th class="wid8">到货状态</th>
                       <th>物流备注</th>
                       <th>操作</th>
                   </tr>
               </thead>
               <tbody>
                   <tr>
                       <td>${num.count}</td>
                       <td class="brand-color1">
                       	<a class="addtitle" href="javascript:void(0);" 
											tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
													"link":"./order/buyorder/viewBuyordersh.do?buyorderId=${buylist.buyorderId}","title":"订单信息"}'>${buylist.buyorderNo}</a>
                       		
                       </td>
                       <td><date:date value ="${buylist.validTime}" format="yyyy.MM.dd"/></td>
                       <td>
                       		${buylist.traderName}
                       </td>
                       <td>${buylist.buyDepartmentName}</td>
                       <td>${buylist.buyPerson}</td>
                       <td>${buylist.totalAmount}</td>
                       <c:choose>
                       	<c:when test="${buylist.arrivalStatus == 0}">
                       		<td class="warning-color1">未到货</td>
                       	</c:when>
                       	<c:otherwise>
                       		<c:choose>
                       			<c:when test="${buylist.arrivalStatus == 1}">
                       				<td class="warning-color1">部分到货</td>
                       			</c:when>
                       			<c:when test="${buylist.arrivalStatus == 2}">
                       				<td>全部到货</td>
                       			</c:when>
                       		</c:choose>
                       	</c:otherwise>
                       </c:choose>
                       <td><font color="red">${buylist.logisticsComments}</font></td>
                       <td>
                       		<span class="bt-smaller bt-border-style border-blue addtitle" tabTitle='{"num":"getWarehouseIn${buylist.buyorderId}","link":"./logistics/warehousein/getWarehouseIn.do?buyorderId=${buylist.buyorderId}&buyorderNo=${buylist.buyorderNo}","title":"采购入库"}'>开始入库</span>
                       </td>
                   </tr>
                   <tr>
                       <td colspan="10" class="tdpadding">
                           <table class="table">
                                   <tbody>
                                       <tr>
                                           <td>产品名称</td>
                                           <td class="wid15">品牌</td>
                                           <td class="wid15">型号</td>
                                           <td>物料编码</td>
                                           <td class="wid6">单位</td>
                                           <td class="wid8">采购总数</td>
                                           <td class="wid8">未入库数量</td>
                                           <td class="wid7" >状态</td>
                                       </tr>
                                     <c:forEach var="goodsList" items="${buylist.buyorderGoodsVoList}">
                                     <tr>
                                        <td class="text-left">
                                            <div class="brand-color1">
                                             <span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewgoods${goodsList.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${goodsList.goodsId}","title":"产品信息"}'>
													${newSkuInfosMap[goodsList.sku].SHOW_NAME}
											 </span>
                                            </div>
                                            <div>${newSkuInfosMap[goodsList.sku].SKU_NO}</div>
                                        </td>
                                        <td>
												${newSkuInfosMap[goodsList.sku].BRAND_NAME}
                                        </td>
                                        <td>${newSkuInfosMap[goodsList.sku].MODEL} </td>
                                        <td>${newSkuInfosMap[goodsList.sku].MATERIAL_CODE}</td>
                                        <td>${newSkuInfosMap[goodsList.sku].UNIT_NAME}</td>
                                        <td>${goodsList.num}</td>
                                        <td>${goodsList.num-goodsList.arrivalNum}</td>
                                        <td>
                                        	<c:choose>
                                        		<c:when test="${goodsList.arrivalStatus == 0}"><font color="red">未到货</font></c:when>
                                        		<c:when test="${goodsList.arrivalStatus == 1}"><font color="red">部分到货</font></c:when>
                                        		<c:when test="${goodsList.arrivalStatus == 2}">全部收货</c:when>
                                        	</c:choose>
                                        </td>
                                     </tr>
                                     </c:forEach>
                                   </tbody>
                            </table>
                       </td>
                   </tr>
               </tbody>
               
           </table>
           </c:forEach>	
           	  <c:if test="${empty list}">
				<!-- 查询无结果弹出 -->
				 <table class="table">
				 	<tbody>
						<tr>
                        	<td colspan="10">查询无结果！请尝试使用其他搜索条件。</td>
                    	</tr>
                     </tbody>
           		</table>
			  </c:if>
			  
		<div class="mt-5">
           <tags:page page="${page}" />
       	</div>
       </div>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseIn/index.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

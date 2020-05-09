<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="销售业务出库" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="customer">
    <ul>
        <li>
            <a href="/warehouse/warehousesout/index.do" >销售出库</a>
        </li>
        <li>
            <a href="/warehouse/businessWarehouseOut/changeIndex.do" >采购售后出库</a>
        </li>
        <li>
            <a href="/warehouse/businessWarehouseOut/index.do" class="customer-color">销售售后出库</a>
        </li>
        <li>
            <a href="/warehouse/warehousesout/lendOutIndex.do">商品外借出库</a>
        </li>
    </ul>
</div>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
	<div class="main-container logistics-warehousein-index">
		<div class="searchfunc">
			<form method="post" id="search" action="<%= basePath %>/warehouse/businessWarehouseOut/index.do">
				<ul>
					<li>
						<label class="infor_name">业务单号</label>
						<input type="text" class="input-middle" name="afterSalesNo" id="afterSalesNo" value="${afterSalesVo.afterSalesNo}"/>
					</li>
					<li>
						<label class="infor_name">产品名称</label>
						<input type="text" class="input-middle" name="goodsName" id="goodsName" value="${afterSalesVo.goodsName}"/>
					</li>
					<li>
						<label class="infor_name">订货号</label>
						<input type="text" class="input-middle" name="sku" id="sku" value="${afterSalesVo.sku}"/>
					</li>
					<li>
						<label class="infor_name">品牌</label>
						<input type="text" class="input-middle" name="brandName" id="brandName" value="${afterSalesVo.brandName}"/>
					</li>
					<li>
						<label class="infor_name">型号</label>
						<input type="text" class="input-middle" name="model" id="model" value="${afterSalesVo.model}"/>
					</li>
					<li>
						<label class="infor_name">物料编码</label>
						<input type="text" class="input-middle" name="materialCode" id="materialCode" value="${afterSalesVo.materialCode}"/>
					</li>
                    <li>
                        <label class="infor_name">库存允许出库</label>
                        <select class="input-middle" name="isOutAfter" id="isOutAfter">
                            <option value="">全部</option>
                            <option value="1" <c:if test="${afterSalesVo.isOutAfter==1}">selected="selected"</c:if>>是</option>
                            <option value="2" <c:if test="${afterSalesVo.isOutAfter==2}">selected="selected"</c:if>>否</option>
                        </select>
                    </li>
				</ul>
				<div class="tcenter">
					<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
					<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				</div>
			</form>
		</div>
		<input id="Ids" type="hidden" value="${Ids}"/>
		<c:if test="${saleorder.flag eq 0}">
		<div class="tablelastline">
                        当前待出库业务单数：<span class="warning-color1">${page.totalRecord}</span>
        </div>
        </c:if>
          <div class="table-style5">
          <c:forEach var="list" items="${afterSalesList}" varStatus="num">
            <table class="table">
                <thead>
                    <tr>
                       <th class="wid5">序号</th>
                        <th class="wid25">业务号</th>
						<th class="wid25">生效时间</th>
						<th class="">创建人</th>
                        <th class="">库存允许出库</th>
						<th class="">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${num.count}</td>
                    	<td><a id="${list.afterSalesId}_flag" class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewsaleorder${list.afterSalesId}","link":"./aftersales/order/viewAfterSalesDetail.do?afterSalesId=${list.afterSalesId}&traderType=1","title":"售后详情"}'>${list.afterSalesNo}</a></td>
                        <td><date:date value ="${list.validTime}"/></td>
                         <td>${list.creatorName}</td>
                        <c:if test="${list.isOutAfter eq 1}">
                            <td>是</td>
                        </c:if>
                        <c:if test="${list.isOutAfter eq 2}">
                            <td>否</td>
                        </c:if>
                         <td  class="begin-enter-lib wid12">
                                               <span class="bt-smaller bt-border-style border-blue addtitle"
					tabTitle='{"num":"warehousesout_index_${list.afterSalesId}","link":"./warehouse/businessWarehouseOut/viewBusinessWdetail.do?afterSalesId=${list.afterSalesId}&businessType=1","title":"出库详情"}'>查看详情</span>
                                            </td>
                    </tr>
                    <tr>
                        <td colspan="6" class="tdpadding">
                            <table class="table">
                                    <tbody id="${list.afterSalesId}_goods">
                                        <tr>
                                        <th class="wid25">产品名称</th>
                                        <th class=" ">订货号</th>
                                        <th class=" wid10">品牌</th>
                                        <th class=" wid10">型号</th>
                                        <th class=" wid15">物料编码</th>
                                        <th class=" ">单位</th>
                                        <th class=" ">总数</th>
                                        <th class=" ">未出库数量</th>
                                        <th class="">库存量</th>
                                        <th>状态</th>
                                        <th class="wid20">存储位置</th>
                                        </tr>
                                     <%--  <c:forEach var="afterSalesGoodsVo" items="${list.afterSalesGoodsList}">
	                                    <tr>
	                                        <td class="text-left">
						                        <div >
						                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${afterSalesGoodsVo.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${afterSalesGoodsVo.goodsId}","title":"产品信息"}'>${afterSalesGoodsVo.goodsName}</a>
						                        </div>
						                        <div>${afterSalesGoodsVo.sku}</div>
						                    </td>
											<td>${afterSalesGoodsVo.brandName}</td>
											<td>${afterSalesGoodsVo.model}</td>
											<td>${afterSalesGoodsVo.materialCode}</td>
											<td>
												${afterSalesGoodsVo.unitName}
											</td>
											<td>${afterSalesGoodsVo.num}</td>
											<td>${afterSalesGoodsVo.num-afterSalesGoodsVo.deliveryNum }</td>
											   <c:choose>
												<c:when test="${afterSalesGoodsVo.deliveryStatus eq 0}">
													<td class="warning-color1">未发货</td>
												</c:when>
												<c:when test="${afterSalesGoodsVo.deliveryStatus eq 1}">
													<td class="warning-color1">部分发货</td>
												</c:when>
												<c:when test="${afterSalesGoodsVo.deliveryStatus eq 2}">
													<td>全部发货</td>
												</c:when>
												<c:otherwise>
												<td></td>
												</c:otherwise>
											  </c:choose>
											<td>
											  <c:forEach items="${afterSalesGoodsVo.whList}" var="addr" begin="0" 
												  end="${fn:length(afterSalesGoodsVo.whList)}" varStatus="stat">
												${addr}<br/>
												</c:forEach>
											</td>
	                                    </tr>
                                    </c:forEach> --%>
                                    </tbody>
                             </table>
                             
                        </td>
                    </tr>
                     <%-- <c:if test="${empty list.afterSalesGoodsList}">
	                    <tr>
	                        <td colspan="5">暂无商品记录</td>
	                    </tr>
                    </c:if> --%>
                </tbody>
            </table>
             </c:forEach>
             
			<c:if test="${empty afterSalesList}">
				<!-- 查询无结果弹出 -->
				 <table class="table">
				 	<tbody>
						<tr>
                        	<td colspan="5">查询无结果！请尝试使用其他搜索条件。</td>
                    	</tr>
                     </tbody>
           		</table>
			  </c:if>
           <tags:page page="${page}" />
        </div>
	</div>
	<script type="text/javascript" src='<%= basePath %>static/js/logistics/businessWarehouseOut/index_business.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

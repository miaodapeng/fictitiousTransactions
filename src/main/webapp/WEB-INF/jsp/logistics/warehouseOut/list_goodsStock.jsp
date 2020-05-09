<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="商品库存" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="content logistics-warehousein-index">
	<div class="searchfunc">
		<form method="post" id="search"
		   action="<%= basePath %>/warehouse/warehousesout/listGoodsStock.do">
			<ul>
				<li>
					<label class="infor_name">产品名称</label>
					<input type="text" class="input-middle"  name="goodsName" id="goodsName" value="${goods.goodsName}"/>
				</li>
				<li>
					<label class="infor_name">品牌</label>
					<input type="text" class="input-middle"  name="brandName" id="brandName" value="${goods.brandName}"/>
				</li>
				<li>
					<label class="infor_name">型号</label>
					<input type="text" class="input-middle" name="model" id="model" value="${goods.model}"/>
				</li>
				<li>
					<label class="infor_name">订货号</label>
					<input type="text" class="input-middle"  name="sku" id="sku" value="${goods.sku}"/>
				</li>
				<li>
					<label class="infor_name">物料编码</label>
					<input type="text" class="input-middle"  name="materialCode" id="materialCode" value="${goods.materialCode}"/>
				</li>
				<li>
					<label class="infor_name">问题库存</label>
					<select name="isProblem" class="input-middle">
                        <option value="">全部</option>
						<option value="1" <c:if test="${goods.isProblem eq 1}">selected="selected"</c:if>>包含</option>
						<option value="0" <c:if test="${goods.isProblem eq 0}">selected="selected"</c:if>>不包含</option>
					</select>
				</li>
				
			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="location.href='./exportOutRlist.do'">导出列表</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20"  onclick="addAllSkuBarcode()">生成订货号条码</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20"  onclick="printSkuBarcode(${logisticeFlag})">打印订货号条码</span>
			</div>
		</form>
		<form method="post" id="printSkuBarcode"
			  action="<%= basePath %>/logistics/warehousein/printSkuBarcode.do">
			<input type="hidden"  name="goodsIds" id="goodsIds" value=""/>
		</form>
	</div>
	<div class="  list-page">
            <!-- normal-list-page -->
            <div class="">
                <div class="">
                    <table class="table">
                        <thead>
                            <tr>
								<th class="wid5">选择</th>
                               <th class="wid5">序号</th>
		                       <th class="wid30">产品名称</th>
		                       <th class="">品牌</th>
		                       <th class="wid12">型号</th>
		                       <th class="wid10">物料编码</th>
		                       <th class="">单位</th>
		                       <th>可用库存/库存量</th>
								<th>占用库存</th>
								<th>活动锁定库存</th>
		                       <th class="wid25">在途（订单|数量|预计到达）</th>
		                       <th>查看</th>
                            </tr>
                        </thead>
                        <tbody>
                         <c:forEach var="wlist" items="${list}" varStatus="num">
                            <tr>
								<td>
									<input type="checkbox"  name="b_checknox" autocomplete="off" alt="<date:date value ="${wlist.addTime}" format="yyyy.MM.dd"/>" value="${wlist.goodsId}">
								</td>
                                	<td>${num.count}</td>
                                	<td class="text-left">
				                        <div >
				                          <a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${wlist.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${wlist.goodsId}","title":"产品信息"}'>
											  <c:if test="${wlist.source eq 1}"><span style="color: red">【医械购】</span></c:if>${wlist.goodsName}</a>
				                        </div>
				                         <div>${wlist.sku}</div>
				                    </td>
                                	<td>${wlist.brandName}</td>
                                	<td>${wlist.model}</td>
                                    <td>${wlist.materialCode}</td>
                                    <td>${wlist.unitName}</td>
									<c:if test="${wlist.canUseGoodsStock < 0 }">
                                    	<td>0/${wlist.goodsStock}</td>
                                    </c:if>
                                    <c:if test="${wlist.canUseGoodsStock >= 0 }">
                                    	<td>${wlist.canUseGoodsStock}/${wlist.goodsStock}</td>
                                    </c:if>
									<c:if test="${wlist.occupyNum eq null}">
										<td>0</td>
									</c:if>
									<c:if test="${wlist.occupyNum ne null}">
										<td>${wlist.occupyNum}</td>
									</c:if>
								<td>
									<c:choose>
										<c:when test="${wlist.actionLockCount != null}">
											${wlist.actionLockCount}
										</c:when>
										<c:otherwise>
											0
										</c:otherwise>
									</c:choose>
								</td>
								<td>
                                    <c:forEach var="listw" items="${wlist.bvList}" varStatus="n">
                                     ${listw.buyorderNo}&nbsp;/&nbsp;${listw.onWayNum}&nbsp;/&nbsp;
                                     <c:if test="${listw.estimateArrivalTime eq 0 }">--<br/></c:if>
                                     <c:if test="${listw.estimateArrivalTime != 0 }">
                                     <date:date value ="${listw.estimateArrivalTime}" format="yyyy-MM-dd"/><br/>
                                     </c:if>
                                    </c:forEach>
                                    </td>
                                    <td>
								<a class="addtitle" href="javascript:void(0);" tabtitle='{"num":"viewGoodsStock${wlist.goodsId}", "link":"./warehouse/warehousesout/viewGoodsStock.do?goodsId=${wlist.goodsId}", "title":"库存详情"}'>查看</a>
                                    </td>
                            </tr>
							</c:forEach>
							<c:if test="${empty list}">
							<tr>
								<td colspan="10">暂无商品记录</td>
							</tr>
						</c:if>
                        </tbody>
                    </table>
					<div class="allchose">
						<input type="checkbox" name="" onclick="selectall(this);" autocomplete="off">
						<span>全选</span>
					</div>
                </div>
            </div>
            <div class="parts">
      			<tags:page page="${page}" />
         </div>
       </div>
    	
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseIn/viewWarehouseIn.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseIn/addBarcode.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

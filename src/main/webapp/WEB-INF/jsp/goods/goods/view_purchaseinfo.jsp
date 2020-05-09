<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="商品采购信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/goods/goods/view_saleinfo.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript">
	$(document).ready(function()
	{
		// 产品ID
		var goodId = '${goods.goodsId}';
		// 无数据,则 隐藏 重置 按钮
		if(0 == $("#tb_spsqydj").find("tr").length)
		{
			$(".title-click").remove();
		}
		else
		{
			$(".title-click").remove();
			var goodsChannelPriceIdList = '';
			// 显示 重置按钮 
			$.each($("#tb_spsqydj tr"),function()
			{
				goodsChannelPriceIdList += $(this).find("td:eq(0)").text().trim() + "|";
			});
			var div = '<div class="title-click nobor" onclick=delGoodsChannelPrice("'+goodId+'","'+goodsChannelPriceIdList+'") >';
			
			div += '重置</div> '; 
			
			$("#div_append_sj_dj").append(div);
		}
    })
</script>
	<div class="customer">
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/goods/goods/viewbaseinfo.do?goodsId=${goods.goodsId}">商品信息</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/goods/goods/viewsaleinfo.do?goodsId=${goods.goodsId}">销售信息</a>
            </li>
            <li>
                <a class="customer-color" href="${pageContext.request.contextPath}/goods/goods/viewpurchaseinfo.do?goodsId=${goods.goodsId}">采购信息</a>
            </li>
            <!-- <li>
                <a href="javascript:void(0)">运营信息</a>
            </li> -->
        </ul>
    </div>
    <div class=" content">
         <div class="parts content1" id="authorization">
        <div id="div_append_sj_dj" class="title-container">
            <div class="table-title nobor">
               	 商品授权与定价
            </div>
            <!--  <div class="title-click nobor none" onclick="delGoodsChannelPrice(${goods.goodsId}, '');">
                                                 重置
            </div>--> 
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th class="wid5">序号</th>
                    <th>授权地区</th>
                    <th>授权客户的类型</th>
                     <c:if test="${goodsChannelPriceList[0].minNum > 0 }">
                    <th >最小起订量</th>
                    </c:if>
                    <c:if test="${goodsChannelPriceList[0].minNum == 0 }">
                    <th >最小起订价</th>
                    </c:if>
                    <th>批量政策</th>
                    <!-- <th>市场价</th> -->
                    <th>经销商指导价</th>
                    <th>非公机构终端价</th>
                    <th>公立终端价</th>
                    <th>是否需要终端报备</th>
                    <th>是否可出厂家授权书</th>
                    <th class="wid8">成本价</th>
                    <th class="wid8">批量价</th>
                    <th>核价有效期</th>
                    <th class="wid14">更新时间</th>
                    <th>操作人</th>
                </tr>
            </thead>
            <tbody id="tb_spsqydj">
                <c:forEach var="list" items="${goodsChannelPriceList}" varStatus="statu">
	                <tr>
	                	<td class='none'>${list.goodsChannelPriceId}</td>
	                    <td>${statu.count}</td>
	                    <td>${list.provinceName}&nbsp;${list.cityName}</td>
	                    <td>${list.customerTypeComments}</td>
	                    <c:if test="${goodsChannelPriceList[0].minNum > 0 }">
	                     <td>${list.minNum}${goods.unitName}</td>
	                     </c:if>
	                      <c:if test="${goodsChannelPriceList[0].minNum == 0 }">
	                      <td> <fmt:formatNumber type="number" value="${list.minAmount}" pattern="0.00" maxFractionDigits="2" />元</td>
	                     </c:if>
	                    <td>${list.batchPolicy}</td>
	                    <%-- <td><fmt:formatNumber type="number" value="${list.marketPrice}" pattern="0.00" maxFractionDigits="2" /></td> --%>
	                    <td><fmt:formatNumber type="number" value="${list.distributionPrice}" pattern="0.00" maxFractionDigits="2" /></td>
	                    <td><fmt:formatNumber type="number" value="${list.privatePrice}" pattern="0.00" maxFractionDigits="2" /></td>
	                    <td><fmt:formatNumber type="number" value="${list.publicPrice}" pattern="0.00" maxFractionDigits="2" /></td>
	                    <td>
	                    	<c:choose>
								<c:when test="${list.isReportTerminal eq 1}">
									是
								</c:when>
								<c:otherwise>
									否
								</c:otherwise>
							</c:choose>
	                    </td>
	                    <td>
	                    	<c:choose>
								<c:when test="${list.isManufacturerAuthorization eq 1}">
									是
								</c:when>
								<c:when test="${list.isManufacturerAuthorization eq 2}">
									是，需保证金
								</c:when>
								<c:otherwise>
									否
								</c:otherwise>
							</c:choose>
	                    </td>
	                    <td  class="begin-enter-lib caozuo">
                           <span class="bt-smaller  border-blue" onclick="showPriceList(${list.goodsChannelPriceId},1)">点击查看</span>
                        </td>
                         <td  class="begin-enter-lib caozuo">
                           <span class="bt-smaller  border-blue" onclick="showPriceList(${list.goodsChannelPriceId},2,'${goods.unitName}')">点击查看</span>
                        </td>
	                    <td><fmt:formatDate value="${list.periodDate}" type="date" pattern="yyyy-MM-dd"/></td>
	                    <td><date:date value ="${list.addTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
	                    <td>${list.username}</td>
	                </tr>
            	</c:forEach>
            	<c:if test="${empty goodsChannelPriceList}">
	            	<tr>
	                	<td colspan="14">暂无商品授权与定价信息!</td>
	                </tr>
       			</c:if>
            </tbody>
        </table>
    </div>
        
        <div class="parts">
	        <div class="title-container">
	                <div class="table-title nobor">
	                    运营
	                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">结算价</td>
                        <td>
                        <shiro:hasPermission name="/order/quote/settlementPrice.do">
                        <fmt:formatNumber type="number" value="${goodsSettlementPriceInfo.settlementPrice}" pattern="0.00" maxFractionDigits="2" />
                        </shiro:hasPermission>
                        </td>
                        <td class="table-smaller">更新时间</td>
                        <td><date:date value ="${goodsSettlementPriceInfo.modTime}"/></td>
                    </tr>
                    <tr>
                        <td>操作人</td>
                        <td>${goodsSettlementPriceInfo.username}</td>
                        <td></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div>
    
    <div class="parts content1">
        <div class="title-container">
            <div class="table-title nobor">
                主要供应商
            </div>
            <div class="title-click nobor  pop-new-data" layerParams='{"width":"70%","height":"80%","title":"新增供应商","link":"./getSupplierByName.do?goodsId=${goods.goodsId}&supplierName="}'>
                新增
            </div>
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th>供应商名称</th>
                    <th>供应次数</th>
                    <th>供应数量</th>
                    <th>近期供应价格</th>
                    <th>最近供应时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            	<c:forEach var="list" items="${mainSupplyList}" varStatus="statu">
                <tr>
                    <td>${list.traderSupplierName}</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td><span class="delete" onclick="delMainSupply(${goods.goodsId}, ${list.traderSupplierId});">删除</span></td>
                </tr>
                </c:forEach>
                <c:if test="${empty mainSupplyList}">
	            	<tr>
	                	<td colspan="6">暂无主要供应商信息!</td>
	                </tr>
       			</c:if>
            </tbody>
        </table>
    </div>
    
    <div class="parts content1">
        <div class="title-container">
            <div class="table-title nobor">
                交易量统计
            </div>
        </div>
        <table class="table table-bordered table-striped table-condensed table-centered">
            <thead>
                <tr>
                    <th>项目</th>
                    <th>总量</th>
                    <th>总额</th>
                    <th>均价</th>
                    <th>近一年交易量</th>
                    <th>近一年均价</th>
                    <th>近半年交易量</th>
                    <th>近半年均价</th>
                    <th>近一月交易量</th>
                    <th>近一月均价</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>采购</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>销售</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </tbody>
        </table>
    </div>
    
    
    <div class="parts">
	        <div class="title-container">
	                <div class="table-title nobor">
	                    其他
	                </div>
            </div>
            <table class="table table-bordered table-striped table-condensed table-centered">
                <tbody>
                    <tr>
                        <td class="table-smaller">毛利率</td>
                        <td>
                        </td>
                        <td class="table-smaller">采购平均到库周期</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>销售转化率</td>
                        <td>
                        </td>
                        <td>销售平均货期</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>退货数量</td>
                        <td>
                        </td>
                        <td>退货率</td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div>
    
    </div>
<%@ include file="../../common/footer.jsp"%>
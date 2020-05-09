<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑备货" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
	$(function(){
		var saleorderId = $("input[name='saleorderId']").val();

		//补订单产品详情相关数据
		$.ajax({
			async:true,
			url:page_url+'/order/saleorder/getsaleordergoodsextrainfo.do',
			data:{"saleorderId":saleorderId, "extraType":"bh_saleorder"},//
			type:"POST",
			dataType : "json",
			success:function(data){
				if(data.code==0){
					for (var i = 0; i < data.data.length; i++) {
						//alert(data.data[i].saleorderGoodsId);
						//$("#orderOccupy_stockNum_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.orderOccupy+"/"+data.data[i].goods.stockNum);
						$(".kc_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.stockNum);
						$("#kykc_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.stockNum-data.data[i].goods.orderOccupy);
						$("#dzzy_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.orderOccupy);
						$("#ktj_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.adjustableNum);
						
						$("#saleNum90_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.saleNum90);
						$("#saleNum30_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.saleNum30);
						$("#quoteNum90_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.quoteNum90);
						$("#quoteNum30_"+data.data[i].saleorderGoodsId).html(data.data[i].goods.quoteNum30);
					}
				}else{
					layer.alert(data.message);
				}
			},
			error:function(data){
				if(data.status ==1001){
					layer.alert("当前操作无权限")
				}
			}
		})
	})
</script>
<!-- 这里注意最外层的main-container -->
<div class="main-container"> 
   <div class="parts">
       <div class="title-container">
           <div class="table-title nobor">
               基本信息
           </div>
           <div class="title-click nobor">
               <span class="pop-new-data" layerParams='{"width":"600px","height":"160px","title":"编辑基本信息","link":"./editBhSaleorderBaseInfo.do?saleorderId=${saleorder.saleorderId}"}'>编辑</span>
           </div>
       </div>
       <table class="table">
           <tbody>
               <tr>
                   <td class="wid20">备货单号</td>
                   <td>${saleorder.saleorderNo}</td>
                   <td class="wid20">备货单状态</td>
                   <td>
                   	<c:choose>
			<c:when test="${saleorder.status eq 0}">待确认</c:when>
			<c:when test="${saleorder.status eq 1}">进行中</c:when>
			<c:when test="${saleorder.status eq 2}">已完结</c:when>
			<c:when test="${saleorder.status eq 3}">已关闭</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
                   </td>
               </tr>
               <tr>
                   <td>部门</td>
                   <td>${saleorder.salesDeptName}</td>
                   <td>申请人</td>
                   <td>${saleorder.creatorName}</td>
               </tr>
               <tr>
                   <td>审核状态</td>
                   <td></td>
                   <td>创建时间</td>
                   <td><date:date value ="${saleorder.addTime}"/></td>
               </tr>
               <tr>
                   <td>采购状态</td>
                   <td>
                   	<c:choose>
			<c:when test="${saleorder.purchaseStatus eq 0}">未采购</c:when>
			<c:when test="${saleorder.purchaseStatus eq 1}">部分采购</c:when>
			<c:when test="${saleorder.purchaseStatus eq 2}">已采购</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>
                   </td>
                   <td></td>
                   <td></td>
               </tr>
               <tr>
                   <td>申请原因</td>
                   <td colspan="3">${saleorder.prepareComments}</td>
               </tr>
               <tr>
                   <td>后期销售方案</td>
                   <td colspan="3">${saleorder.marketingPlan}</td>
               </tr>
           </tbody>
       </table>
   </div>
   
   <div class="parts">
       <div class="title-container">
           <div class="table-title nobor">产品信息</div>
           <div class="title-click nobor">
			<span class="pop-new-data" layerParams='{"width":"800px","height":"480px","title":"添加产品","link":"./addBhSaleorderGoods.do?saleorderId=${saleorder.saleorderId}"}'>添加产品</span>
			&nbsp;
			<span class="pop-new-data" layerParams='{"width":"550px","height":"240px","title":"批量添加产品","link":"./batchAddBhSaleorderGoods.do?saleorderId=${saleorder.saleorderId}"}'>批量添加</span>
           </div>
       </div>
       <table class="table  table-style6">
           <thead>
               <tr>
               	   <th class="wid5">序号</th>
                   <th class="wid10">产品名称</th>
                   <th class="wid8">品牌</th>
                   <th class="wid8">型号</th>
                   <th class="wid5">数量</th>
                   <th class="wid5">单位</th>
                   <th class="wid6">备货价</th>
                   <th class="wid6">总额</th>
                   <th class="wid5">库存</th>
                   <th>前90天销量</th>
                   <th>前30天销量</th>
                   <th>前90天报价量</th>
                   <th>前30天报价量</th>
                   <th>内部备注</th>
                   <th>供应商</th>
                   <th class="wid12">操作</th>
               </tr>
           </thead>
           <tbody>
           		<c:set var="num" value="0"></c:set>
					<c:set var="totleMoney" value="0.00"></c:set>
                	<c:forEach var="list" items="${saleorderGoodsList}" varStatus="staut">
                		<c:if test="${list.isDelete eq 0}">
							<c:set var="num" value="${num + list.num}"></c:set>
							<c:set var="totleMoney" value="${totleMoney + (list.price * list.num)}"></c:set>
						</c:if>
		                <tr <c:if test="${list.isDelete eq 1}">class="caozuo-grey"</c:if>>
		                   <td>${staut.count}</td>
		                   <td class="text-left">
		                   	   <div class="customername pos_rel">

		                   	   <c:choose>
									<c:when test="${list.isDelete eq 1}">
                                        <c:if test="${list.goods.source == 1}"><span style="color: red">【医械购】</span></c:if>${newSkuInfosMap[list.sku].SHOW_NAME}<br>
                                        ${newSkuInfosMap[list.sku].SKU_NO}<br>
                                        ${newSkuInfosMap[list.sku].MATERIAL_CODE}
									</c:when>
									<c:otherwise>
										<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'><c:if test="${list.goods.source == 1}"><span style="color: red">【医械购】</span></c:if>${newSkuInfosMap[list.sku].SHOW_NAME}</a> <i class="iconbluemouth"></i><br>
                                        ${newSkuInfosMap[list.sku].SKU_NO}<br>
                                        ${newSkuInfosMap[list.sku].MATERIAL_CODE}

                                        <c:set var="skuNo" value="${list.sku}"></c:set>
                                        <%@ include file="../../common/new_sku_common_tip.jsp" %>
									</c:otherwise>
								</c:choose>
								</div>
		                   </td>
		                   <td>${newSkuInfosMap[list.sku].BRAND_NAME}</td>
		                   <td>${newSkuInfosMap[list.sku].MODEL}</td>
		                   <td>${list.num}</td>
		                   <td>${newSkuInfosMap[list.sku].UNIT_NAME}</td>
		                   <td>${list.price}</td>
		                   <td><fmt:formatNumber type="number" value="${list.price * list.num}" pattern="0.00" maxFractionDigits="2" /></td>
		                   <td><span class="kc_${list.saleorderGoodsId}">${list.goods.stockNum}</span></td>
		                   <td><span id="saleNum90_${list.saleorderGoodsId}">${list.goods.saleNum90}</span></td>
		                   <td><span id="saleNum30_${list.saleorderGoodsId}">${list.goods.saleNum30}</span></td>
		                   <td><span id="quoteNum90_${list.saleorderGoodsId}">${list.goods.quoteNum90}</span></td>
		                   <td><span id="quoteNum30_${list.saleorderGoodsId}">${list.goods.quoteNum30}</span></td>
		                   <td>${list.insideComments}</td>
		                   <td>${list.supplierName}</td>
		                   <td>
		                   		<c:choose>
									<c:when test="${list.isDelete eq 0}">
		                       			<span class="bt-smaller bt-border-style border-blue pop-new-data" layerParams='{"width":"700px","height":"480px","title":"编辑产品信息","link":"./editBhSaleorderGoods.do?saleorderGoodsId=${list.saleorderGoodsId}"}'>编辑</span>
		                       			<span class="bt-smaller bt-border-style border-red mr0" onclick="delSaleorderGoods(${list.saleorderGoodsId},${list.saleorderId});">删除</span>
		                       		</c:when>
									<c:otherwise>
										<span class="bt-smaller bt-border-style caozuo-grey">已删除</span>
									</c:otherwise>
								</c:choose>
		                   </td>
		               </tr>
               </c:forEach>
               <tr>
                   <td colspan="16" class="allchosetr text-left">
                        总件数：<span class="warning-color1 mr10">${num}</span>  总金额：<span class="warning-color1"><fmt:formatNumber type="number" value="${totleMoney}" pattern="0.00" maxFractionDigits="2" /></span>
                   </td>
               </tr>
           </tbody>
       </table>
   </div>
   <div class="tcenter mb15 mt-5">
		<button type="button" class="bt-bg-style bg-light-green bt-small mr10 addtitle" href="javascript:void(0);" tabTitle='{"num":"order_saleorder_viewbhsaleorder${saleorder.saleorderId}","link":"./order/saleorder/viewBhSaleorder.do?saleorderId=${saleorder.saleorderId}","title":"备货信息"}'>确定</button>
   </div>
   <input type="hidden" name="saleorderId" value="${saleorder.saleorderId}"/>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/bh_edit_saleorder.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="备货详情" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
	$(function()
	{
		
		var saleorderId = $("input[name='saleorderId']").val();
		
		queryOrderAndProductConnectOrderNo(saleorderId);

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
                   <td>
                   		<c:choose>
							<c:when test="${saleorder.bhVerifyStatus == null}">待审核</c:when>
							<c:when test="${saleorder.bhVerifyStatus eq 0}">审核中</c:when>
							<c:when test="${saleorder.bhVerifyStatus eq 1}">审核通过</c:when>
							<c:when test="${saleorder.bhVerifyStatus eq 2}">审核不通过</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
                   </td>
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
   <!-- 备货订单号 -->
   <input type="hidden" name="saleorderNo" id="bh_saleorderNo_input" value="${saleorder.saleorderNo}">
   <div class="parts table-style77 product">
       <div class="title-container">
           <div class="table-title nobor">产品信息</div>
       </div>
       <table class="table  table-style7">
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
               </tr>
           </thead>
           <tbody >
           		<c:set var="num" value="0"></c:set>
					<c:set var="totleMoney" value="0.00"></c:set>
                	<c:forEach var="list" items="${saleorderGoodsList}" varStatus="staut">
                		<c:if test="${list.isDelete eq 0}">
							<c:set var="num" value="${num + list.num}"></c:set>
							<c:set var="totleMoney" value="${totleMoney + (list.price * list.num)}"></c:set>
						</c:if>
		                <tr <c:if test="${list.isDelete eq 1}">class="caozuo-grey"</c:if> >
		                   <td>${staut.count}</td>
		                   <td class="text-left">
		                   	   <div class="customername pos_rel">
								   <c:if test="${list.goods.source == 1}"><span style="color: red">【医械购】</span></c:if>
		                   	   <c:choose>
									<c:when test="${list.isDelete eq 1}">
										${newSkuInfosMap[list.sku].SHOW_NAME}<br>
				                        <span id="bh_sku_${list.sku}">${newSkuInfosMap[list.sku].SKU_NO}</span><br>
										${newSkuInfosMap[list.sku].MATERIAL_CODE}
									</c:when>
									<c:otherwise>
										<a class="addtitle" href="javascript:void(0);" tabTitle='{"num":"viewgoods${list.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${list.goodsId}","title":"产品信息"}'>${newSkuInfosMap[list.sku].SHOW_NAME}</a> <i class="iconbluemouth"></i><br>
				                        <span id="bh_sku_${list.sku}">${newSkuInfosMap[list.sku].SKU_NO}</span><br>
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
		               </tr>
		               <tr id="bh_product_tr_${list.sku}" class="none ">
		               		<td colspan="15" class="table-container " style="margin:0;padding:0;">
	                            <table class="table" >
	                                <thead>
	                                    <tr>
	                                        <th class="wid8">关联单号</th>
	                                        <th class="wid10 ">申请人</th>
	                                        <th class="wid15 ">采购数量/需采数量</th>
	                                        <th class="wid11 ">销售价</th>
	                                        <th class="wid10">销售货期</th>
	                                        <th class="wid12">内部备注</th>
	                                        <th class="wid12">产品备注</th>
	                                        <th class="wid12 ">终端客户名称</th>
	                                    </tr>
	                                </thead>
	                                <tbody id="bh_product_apend_order_${list.sku}">
	                                	
	                                </tbody>
	                            </table>
	                        </td>
		               </tr>
               </c:forEach>
               <tr>
                   <td colspan="15" class="allchosetr text-left">
                        总件数：<span class="warning-color1 mr10">${num}</span>  总金额：<span class="warning-color1"><fmt:formatNumber type="number" value="${totleMoney}" pattern="0.00" maxFractionDigits="2" /></span>
                   </td>
               </tr>
           </tbody>
       </table>
   </div>
   
   <c:if test="${saleorder.status != 3 && saleorder.status != 2 && saleorder.validStatus != 1}">
   <div class="table-buttons">
      	<c:choose>
		<c:when test="${saleorder.status eq 0}">
			<c:if test="${(null==taskInfo and null==taskInfo.getProcessInstanceId() )or (null!=taskInfo and taskInfo.assignee==null and empty candidateUserMap[taskInfo.id])}">
						<button type="button" class="bt-bg-style bg-light-green bt-small mr10" onclick="applyValidSaleorder(${saleorder.saleorderId},${taskInfo.id == null ?0: taskInfo.id})">申请审核</button>
						<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 addtitle" tabTitle='{"num":"order_saleorder_editbhsaleorder${saleorder.saleorderId}","link":"order/saleorder/editBhSaleorder.do?saleorderId=${saleorder.saleorderId}","title":"修改备货订单"}'>修改备货订单</button>
			</c:if>
			<c:if test="${(null!=taskInfo and null!=taskInfo.getProcessInstanceId() and null!=taskInfo.assignee) or !empty candidateUserMap[taskInfo.id]}">
				<c:choose>
					<c:when test="${taskInfo.assignee == curr_user.username or candidateUserMap['belong']}">
					<button type="button" class="bt-bg-style bg-light-green bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&saleorderId=${saleorder.saleorderId}&pass=true&type=2"}'>审核通过</button>
					<button type="button" class="bt-bg-style bg-light-orange bt-small mr10 pop-new-data" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?taskId=${taskInfo.id}&saleorderId=${saleorder.saleorderId}&pass=false&type=2"}'>审核不通过</button>
					</c:when>
					<c:otherwise>
       				<button type="button" class="bt-bg-style bt-small bg-light-greybe mr10">已申请审核</button>
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:when>
		<c:otherwise>
		</c:otherwise>
		</c:choose>
		<c:if test="${saleorder.verifyStatus ne 0}">
			<button type="button" class="bt-bg-style bg-light-orange bt-small mr10" onclick="closeBhSaleorder(${saleorder.saleorderId})">关闭订单</button>
		</c:if>
   </div>
   </c:if>
   
           <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    审核记录
                </div>
            </div>
            <table class="table">
            	<thead>
            		 <tr>
                        <th>操作人</th>
                        <th>操作时间</th>
                        <th>操作事项</th>
                        <th>备注</th>
                    </tr>
            	</thead>
                <tbody>
                    <c:if test="${not empty historicActivityInstance}">
	                    <c:forEach var="hi" items="${historicActivityInstance}" varStatus="status">
	                    <c:if test="${not empty  hi.activityName}">
	                    <tr>
	                    	<td>
	                    	<c:choose>
								<c:when test="${hi.activityType == 'startEvent'}"> 
								${startUser}
								</c:when>
								<c:when test="${hi.activityType == 'intermediateThrowEvent'}">
								</c:when>
								<c:otherwise>
									<c:if test="${historicActivityInstance.size() == status.count}">
										${verifyUsers}
									</c:if>
									<c:if test="${historicActivityInstance.size() != status.count}">
										${hi.assignee}  
									</c:if>   
								</c:otherwise>
							</c:choose>
	                    	
	                    	
	                    	</td>
	                        <td><fmt:formatDate value="${hi.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                        <td>
	                        <c:choose>
										<c:when test="${hi.activityType == 'startEvent'}"> 
								开始
								</c:when>
										<c:when test="${hi.activityType == 'intermediateThrowEvent'}"> 
								结束
								</c:when>
								<c:otherwise>   
								${hi.activityName}  
								</c:otherwise>
							</c:choose>
							</td>
	                        <td class="font-red">${commentMap[hi.taskId]}</td>
	                    </tr>
	                    </c:if>
	                    </c:forEach>
                    </c:if>
                    <c:if test="${empty historicActivityInstance}">
       					<!-- 查询无结果弹出 -->
		           		<tr>
					       <td colspan='4'>暂无审核记录！</td>
					    </tr>
        			</c:if>
                </tbody>
            </table>
            
        	<div class="clear"></div>
        </div>
        <input type="hidden" name="formToken" value="${formToken}"/>
        <input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
</div>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/bh_view_saleorder.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

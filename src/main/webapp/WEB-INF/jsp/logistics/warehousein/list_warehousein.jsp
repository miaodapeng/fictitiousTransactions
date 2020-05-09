<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="入库记录" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="main-container logistics-warehousein-index">
	<div class="searchfunc">
		<form method="post" id="search"  action="<%=basePath%>logistics/warehousein/warehouseInLogList.do">
			<ul>
				<li>
					<label class="infor_name">产品名称</label>
					<input type="text" class="input-middle" placeholder="" name="goodsName"  value="${warehouseGoodsOperateLog.goodsName}"/>
				</li>
				<li>
					<label class="infor_name">品牌</label>
					<input type="text" class="input-middle" placeholder="" name="brandName"  value="${warehouseGoodsOperateLog.brandName}"/>
				</li>
				<li>
					<label class="infor_name">型号</label>
					<input type="text" class="input-middle" placeholder="" name="model"  value="${warehouseGoodsOperateLog.model}"/>
				</li>
				<li>
					<label class="infor_name">物料编码</label>
					<input type="text" class="input-middle" placeholder="" name="materialCode"  value="${warehouseGoodsOperateLog.materialCode}"/>
				</li>
				<li>
					<label class="infor_name">订货号</label>
					<input type="text" class="input-middle" placeholder="" name="sku"  value="${warehouseGoodsOperateLog.sku}"/>
				</li>
				<li>
					<label class="infor_name">贝登条码</label>
					<input type="text" class="input-middle" placeholder="" name="barcode"  value="${warehouseGoodsOperateLog.barcode}"/>
				</li>
				<li>
					<label class="infor_name">厂商条码</label>
					<input type="text" class="input-middle" placeholder="" name="barcodeFactory"  value="${warehouseGoodsOperateLog.barcodeFactory}"/>
				</li>
				<li>
					<label class="infor_name">入库单据</label>
					<input type="text" class="input-middle" placeholder="" name="buyorderNo"  value="${warehouseGoodsOperateLog.buyorderNo}"/>
				</li>
				<li>
					<label class="infor_name">发货方</label>
					<input type="text" class="input-middle" placeholder="" name="buytraderName"  value="${warehouseGoodsOperateLog.buytraderName}"/>
				</li>
				<li>
					<label class="infor_name">批次号</label>
					<input type="text" class="input-middle" placeholder="" name="batchNumber"  value="${warehouseGoodsOperateLog.batchNumber}"/>
				</li>
				<li>
					<label class="infor_name">验收结果</label>
					<select class="input-middle" name="checkStatus" id="checkStatus">
						<option value="">全部</option>
						<option value="0" <c:if test="${warehouseGoodsOperateLog.checkStatus == 0}">selected</c:if>>未验收</option>
						<option value="1" <c:if test="${warehouseGoodsOperateLog.checkStatus == 1}">selected</c:if>>验收通过</option>
						<option value="2" <c:if test="${warehouseGoodsOperateLog.checkStatus == 2}">selected</c:if>>不通过</option>
					</select>
				</li>
				<li>
					<label class="infor_name">入库操作人</label>
					<select class="input-middle" name="creator" id="creator">
						<option value="">全部</option>
						<c:forEach var="list" items="${logisticsUserList}" varStatus="status">
							<option value="${list.userId}" <c:if test="${warehouseGoodsOperateLog.creator ==list.userId}">selected</c:if>>${list.username}</option>
						</c:forEach>
					</select>
				</li>
				
				   <li>
                    <label class="infor_name">入库时间</label>
                    <input class="Wdate f_left input-smaller96 mr5" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="searchbeginTime" value="${searchbeginTime}">
					<div class="gang">-</div> 
					<input class="Wdate f_left input-smaller96" type="text" placeholder="请选择日期" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="searchendTime" value="${searchendTime}">
                </li>
			</ul>
			<div class="tcenter">
				<span class="confSearch bt-small bt-bg-style bg-light-blue" onclick="search();" id="searchSpan">搜索</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="reset();">重置</span>
				<span class="bt-small bg-light-blue bt-bg-style mr20" onclick="exportWareHouseInList();">导出列表</span>
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
                               <th class="wid4">选择</th>
                               <th class="wid4">序号</th>
		                       <th class="wid10">入库时间</th>
		                       <th class="wid10">入库单据</th>
		                       <th class="wid20">产品名称</th>
		                       <th class="wid10">品牌</th>
		                       <th class="wid10">型号</th>
		                       <th class="wid4">数量</th>
		                       <th class="wid4">单位</th>
		                       <th>采购价</th>
		                       <th>总价</th>
		                       <th>发货方</th>
		                       <th>贝登条码</th>
		                       <th>厂商条码</th>
		                       <th>生产日期</th>
		                       <th>效期截止日期</th>
		                       <th>批次号</th>
		                       <th>存储位置</th>
		                       <th>仓存备注</th>
		                       <th>入库操作人</th>
		                       <th>检验结果</th>
		                       <th>检验人</th>
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
                            <tr>
                            		<td> 
	                            		<c:if test="${wlist.checkStatus == 1}">
			                        	</c:if>
				                        <c:if test="${wlist.checkStatus == 0}">
				                        	<input type="checkbox" name="warehouseGoodsOperateLogId" value="${wlist.warehouseGoodsOperateLogId}" onclick="canelAllCheck(this,'warehouseGoodsOperateLogId')"/>
				                            
				                        </c:if>
                            		
                            		</td>
                                	<td>${num.count}</td>
                                	<td><date:date value ="${wlist.addTime}" format="yyyy-MM-dd HH:mm:ss"/></td>
                                	<td>
                                	<c:if test="${wlist.operateType == 1}">
									<a class="addtitle" href="javascript:void(0);" 
									tabTitle='{"num":"viewbuyorder<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./logistics/warehousein/getWarehouseIn.do?buyorderId=${wlist.buyorderId}&buyorderNo=${wlist.buyorderNo}","title":"采购入库"}'>${wlist.buyorderNo}</a>
									</c:if>
									<c:if test="${wlist.operateType == 3 or wlist.operateType == 5 }">
                                	<span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./aftersales/order/returnGoodsDetail.do?afterSalesId=${wlist.buyorderId}&type=${wlist.operateType }&businessType=1","title":"入库详情"}'>${wlist.buyorderNo}</span>
                                	</c:if>
                                	<c:if test="${wlist.operateType == 8}">
                                	<span class="font-blue addtitle" tabTitle='{"num":"viewaftersales<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
											"link":"./aftersales/order/returnGoodsDetail.do?afterSalesId=${wlist.buyorderId}&type=${wlist.operateType }&businessType=2","title":"入库详情"}'>${wlist.buyorderNo}</span>
                                	</c:if>
										<%--<c:if test="${wlist.operateType==9}">--%>
											<%--<span class="font-blue addtitle" tabTitle='{"num":"lendOutdetailJump${wlist.lendOutId}",--%>
											<%--"link":"./warehouse/warehousesout/lendOutdetailJump.do?lendOutId=${wlist.lendOutId}","title":"外借详情页"}'>${wlist.lendOutNo}</span>--%>
										<%--</c:if>--%>

										<c:if test="${wlist.operateType==9}">
											<span class="font-blue addtitle" tabTitle='{"num":"lendOutdetailJump${wlist.buyorderId}",
											"link":"./warehouse/warehousesout/lendOutdetailJump.do?lendOutId=${wlist.buyorderId}","title":"外借详情页"}'>${wlist.buyorderNo}</span>
										</c:if>
							        </td>
                                	<td class="text-left"><span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewgoods${wlist.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${wlist.goodsId}","title":"产品信息"}'>${newSkuInfosMap[wlist.sku].SHOW_NAME}</span>
                                	<br/>${wlist.sku}<br/>${newSkuInfosMap[wlist.sku].MATERIAL_CODE}
                                	</td>
                                    <td>${newSkuInfosMap[wlist.sku].BRAND_NAME}</td>
                                    <td>${newSkuInfosMap[wlist.sku].MODEL}</td>
                                    <td>${wlist.num}</td>
                                    <td>${newSkuInfosMap[wlist.sku].UNIT_NAME}</td>
                                    <td>
										<c:if test="${wlist.operateType!=9}">
											${wlist.cgprice}
										</c:if>
											</td>
                                    <td>

										<c:if test="${wlist.operateType != 9}">${wlist.num*wlist.cgprice}</c:if>
									</td>
                                    <td>${wlist.saletraderName}</td>
                                    <td>${wlist.barcode}</td>
                                    <td>${wlist.barcodeFactory}</td>
                                    <td><font color="${color}"><date:date value ="${wlist.productDate}" format="yyyy-MM-dd"/></font></td>
                                    <td><font color="${color}"><date:date value ="${wlist.expirationDate}" format="yyyy-MM-dd"/></font></td>
                                    <td>${wlist.batchNumber}</td>
                                    <td>${wlist.warehouseArea}</td>
                                    <td>${wlist.comments}</td>
                                    <td>${wlist.operator}</td>
                                    <td>
                                    	<c:if test="${wlist.checkStatus == 0}">
                                    		<font color="red">未验收</font>
                                    	</c:if>
                                    	<c:if test="${wlist.checkStatus == 1}">
                                    		验收通过
                                    	</c:if>
                                    	<c:if test="${wlist.checkStatus == 2}">
                                    		不通过
                                    	</c:if>
                                    </td>
                                    <td>${wlist.checkUserName}</td>
                            </tr>
							</c:forEach>
                        </tbody>
                    </table>
                </div>               
            </div>
            <c:if test="${list.size() ne 0}">
            <div class="parts">
             <div class="f_left">
				<div class="allchose">
					<input type="checkbox" name="allcheck" onclick="selectall(this,'warehouseGoodsOperateLogId');" class="f_left" style="margin-top:5px;"> <span>全选</span>
					<span class=" bt-border-style border-blue bt-small ml10" onclick="checkPass()">
						验收通过
					</span>
					<div class="clear"></div>
				</div>
             </div>
      			<tags:page page="${page}" />
         </div>
         </c:if>
       </div>
    	
</div>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseIn/viewWarehouseIn.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

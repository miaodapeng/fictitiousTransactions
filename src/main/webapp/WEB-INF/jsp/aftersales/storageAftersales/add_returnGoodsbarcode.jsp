<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="生成退货二维码" scope="application" />
<%@ include file="../../common/common.jsp"%>
<link rel="stylesheet" href="<%=basePath%>static/css/qrcode.css?rnd=<%=Math.random()%>" />
<div class="main-container logistics-warehousein-addWarehouseIn">
	  <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">        
					产品信息
                </div>
            </div>
            <table class="table">
                <tbody>
                    <tr>
                        <td class="wid20">产品名称</td>
                        <td><span class="font-blue cursor-pointer addtitle" tabTitle='{"num":"viewgoods${afterSalesGoodsInfo.goodsId}","link":"./goods/goods/viewbaseinfo.do?goodsId=${afterSalesGoodsInfo.goodsId}","title":"产品信息"}'>${newSkuInfo.SHOW_NAME}</span></td>
                        <td class="wid20">订货号</td>
                        <td>${newSkuInfo.SKU_NO}</td>
                    </tr>
                    <tr>
                    	<td class="wid20">品牌</td>
                        <td>${newSkuInfo.BRAND_NAME}</td>
                        <td class="wid20">型号</td>
                        <td>${newSkuInfo.MODEL}</td>
                    </tr>
                     <tr>
                    	<td class="wid20">单位</td>
                        <td>${newSkuInfo.UNIT_NAME}</td>
                        <td class="wid20">数量</td>
                        <td>${afterSalesGoodsInfo.num}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    <div>
        <table class="table">
            <tbody>
            <tr>
                <td class="wid20">订货号条形码</td>
                <td>
                    <c:if test="${attachment == null}">
                        <span class="bt-bg-style bt-smaller bg-light-blue" onclick="addSkuBarcode(${afterSalesGoodsInfo.goodsId})">生成</span>
                    </c:if>
                    <c:if test="${attachment != null}">
                        <div id="pr_${afterSalesGoodsInfo.goodsId}" class="pr_${afterSalesGoodsInfo.goodsId}">
                            <img height="90px" width="90px" src="http://${attachment.domain}/${attachment.uri}">
                            <div class="img-num" style="font-family:'黑体';font-size:40px;">${afterSalesGoodsInfo.sku}</div>
                        </div>
                        <c:if test="${logisticeFlag}">
                            <span class="bt-smaller bt-border-style border-blue"  onclick="preview('pr_${afterSalesGoodsInfo.goodsId}');">打印</span>
                        </c:if>
                        <c:if test="${!logisticeFlag}">
                            <input type="button" class="bt-border-style border-gray" onclick="alert('非物流账户不可打印')" value="打印"/>
                        </c:if>
                    </c:if>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
         <div class="parts bar-code-infor">
                <div class="title-container">
                <div class="table-title nobor">
                   <div class="f_left">条形码信息</div>
                   <div class="f_left  bar-code-produce">
                       <span>已生成${barcodeEnableList.size()}个有效条形码，还能生成</span>
                       <input type="text" name="addBarcodeNum" class="input-smallest bar-code-num" value="${afterSalesGoodsInfo.num-barcodeEnableList.size()}">
                       <span>个</span>
                       <span class="bt-bg-style bt-smaller bg-light-blue" onclick="addBarcode(${afterSalesGoodsInfo.afterSalesGoodsId},${afterSalesGoodsInfo.goodsId},${afterSalesGoodsInfo.num},${barcodeEnableList.size()},2)">生成</span>
                   </div>
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th class="wid5">选择</th>
                        <th class="wid5">序号</th>
                        <th>条形码内容</th>
                        <th class="wid10">生成时间</th>
                        <th class="wid5">状态</th>
                        <th class="wid8">操作人</th>
                        <th class="wid15">操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="list" items="${barcodeList}" varStatus="num">
                 	<tr>
                        <td>
                        <c:if test="${list.isEnable == 1}">
                        <c:if test="${list.isIn == 0}">
                            <input type="checkbox" name="b_checknox" alt="<date:date value ="${list.addTime}" format="yyyy.MM.dd"/>" value="${list.barcodeId}" onclick="canelAllsh(this,'b_checknox')">
                        </c:if>
                        </c:if>
                        <c:if test="${list.isEnable == 0}">
                        </c:if>
                        </td>
                        <td>${num.count}</td>
                        <td class="text-left overflow-hidden">
                            <div id="pr_${list.sequence}" class="pr_${list.barcodeId}">
                            	<div class="qr-code-box">
                            		<div class="img">
                                        <img src="http://${list.domain}/${list.ftpPath}">
                                        <div class="img-num" style="font-family:'黑体';font-size:12px;">${list.barcode}</div>
										<div class="" style="font-family:'黑体';font-size:  12px;"><date:date value ="${list.addTime}" format="yyyy.MM.dd"/>（${list.sequence}）</div>
                                    </div>
                                    <div class="message">
                                        <div class="item product_name" style="font-family:'黑体'; font-size:12px; white-space:normal;">产品：${barcodesMap[list.goodsId].SHOW_NAME}</div>
                                        <div class="item" style="font-family:'黑体'; font-size:12px;white-space:normal;">型号：${barcodesMap[list.goodsId].MODEL}</div>
                                        <div class="item" style="font-family:'黑体'; font-size:12px;white-space:normal;">物料编码：${barcodesMap[list.goodsId].MATERIAL_CODE}</div>
                                        <div class="item" style="font-family:'黑体'; font-size:12px;white-space:normal;">货仓备注：${list.comment}</div>
										<div class="item" style="font-family:'黑体';font-size:12px;white-space:normal;">位置：${list.storageAddress}</div>
                                    </div>
                                    <div class="clear"></div>
                            		<!--  
                                    <div class="img">
                                        <img src="http://${list.domain}/${list.ftpPath}">
                                        <div class="img-num">${list.barcode}</div>
                                    </div>
                                    <div class="message">
                                        <div class="item">时间：<date:date value ="${list.addTime}" format="yyyy.MM.dd"/>（${list.sequence}）</div>
                                        <div class="item product_name">产品：${list.buyorderGood.goodsName}</div>
                                        <div class="item">型号：${list.buyorderGood.model}</div>
                                    </div>
                                    -->
                                </div>
                        	</div>
                        </td>
                        <td><date:date value ="${list.addTime}" format="yyyy.MM.dd"/></td>
                        <td>
                        	<c:if test="${list.isEnable == 1}">
                        		有效
                        	</c:if>
                        	<c:if test="${list.isEnable == 0}">
                        		无效
                        	</c:if>
						</td>
                        <td>${list.creatorName}</td>
                        <td>
                        	<c:if test="${list.isEnable == 1}">
                        		<span class="bt-smaller bt-border-style border-blue"  onclick="preview('pr_${list.sequence}');">打印</span>
                            	<c:if test="${list.isIn == 0}">
                            	<span class="bt-smaller bt-border-style border-red" onclick="cancelBarcode('${list.barcodeId}')">作废</span>
                        	    </c:if>
                        	</c:if>
                        </td>
                     
                    </tr>
				</c:forEach>
                </tbody>
            </table>
             <div class="table-style4">
                <div class="allchose">
                   <input type="checkbox" name="all_b_checknox" onclick="selectallsh(this,'pick_checkbox');" value="b_checknox"> <span>全选</span>
                </div>
               <div class="times">
					<span class="mr10">请选择批次</span>
					<c:forEach var="tlist" items="${timeArray}" varStatus="num">
                     <input type="checkbox" name="${tlist}" id="pick_checkbox" onclick="checkbarcodesh('${tlist}', this.checked,'b_checknox');">
                     <span class="mr20">${tlist}</span>
                    </c:forEach>
				</div>
                <div class="print-record">
                   <span class="bt-bg-style bg-light-blue" onclick="printall()">批量打印</span>
                    <span class="bt-bg-style bg-light-red" onclick="cancelBarcodeAll()">批量作废</span>
                </div>
            </div>
            <div class="table-buttons">
                <input type="hidden" name="rknum" value="${rknum}">
                <button type="button" class="bt-bg-style bg-light-blue bt-small addtitle" tabTitle='{"num":"addWarehouseIn${afterSalesGoodsInfo.afterSalesId}_${afterSalesGoodsInfo.goodsId}","link":"./logistics/warehousein/addWarehouseIn.do?afterSalesId=${afterSalesGoodsInfo.afterSalesId}&goodsId=${afterSalesGoodsInfo.goodsId}&afterSalesGoodsId=${afterSalesGoodsInfo.afterSalesGoodsId}&type=2&businessType=${businessType }&rknum=${rknum }","title":"入库"}'>生成完毕,开始入库</button>
         	</div>
        </div>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseIn/addBarcode.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

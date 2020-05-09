<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="已入库条码" scope="application" />
<%@ include file="../../common/common.jsp"%>
<link rel="stylesheet" href="<%=basePath%>static/css/qrcode.css?rnd=<%=Math.random()%>" />
<div class="main-container logistics-warehousein-addWarehouseIn" style="padding-bottom: 40px;">
         <div style="text-align: center;">
	     <div class="infor_name" style="width: 110px;">
			<lable>入库时间</lable>
		 </div>
		 <div style="text-align: center;">
		 <form method="post" action="${pageContext.request.contextPath}/logistics/warehousein/getWarehouseInBarcode.do">
			 <div class="f_left">
				<input class="Wdate f_left input-small200 mr5" style="width:146px;" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'searchEndTime\')}'})" placeholder="请选择时间" 
					name="searchBeginTime" id="searchBeginTime"  value ="${searchBeginTime}"/><div class="gang">-</div>
				<input class="Wdate f_left input-small200" style="width:146px;" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'searchBeginTime\')}'})"  placeholder="请选择时间"
					name="searchEndTime" id="searchEndTime" value ="${searchEndTime}"/>
			 </div>
			 <input type="hidden" id="buyorderId" name="buyorderId" value="${buyorderId }">
			 <input type="hidden" id="type" name="type" value="${type }">
			 <div class="table-buttons center">
			 <button type="submit" class="bt-bg-style bg-light-blue bt-small ">搜索</button>
		     </div>
	     </form>
	     </div>
		 </div>
         <div class="parts bar-code-infor">
               <!--  <div class="title-container">
           		 </div> -->
            <table class="table">
                <thead>
                    <tr>
                        <th class="wid5">选择</th>
                        <th class="wid5">序号</th>
                        <th>条形码内容</th>
                        <th class="wid10">生成时间</th>
                        <th class="wid10">入库时间</th>
                        <th class="wid5">状态</th>
                        <th class="wid8">操作人</th>
                        <th class="wid15">操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="list" items="${barcodeList}" varStatus="num">
                 	<tr>
                        <td>
                            <input type="checkbox" name="b_checknox" autocomplete="off" alt="<date:date value ="${list.warehouseInTime}" format="yyyy.MM.dd"/>" value="${list.barcodeId}">
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
                                        <div class="item product_name" style="font-family:'黑体'; font-size:12px; white-space:normal;">产品：${list.buyorderGood.goodsName}</div>
                                        <div class="item" style="font-family:'黑体'; font-size:12px;white-space:normal;">型号：${list.buyorderGood.model}</div>
                                        <div class="item" style="font-family:'黑体'; font-size:12px;white-space:normal;">物料编码：${list.buyorderGood.materialCode}</div>
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
                                        <div class="item product_name">产品：${fn:substring(list.buyorderGood.goodsName, 0, 30)}</div>
                                        <div class="item">型号：${list.buyorderGood.model}</div>
                                        <div class="item">物料编码：${list.buyorderGood.materialCode}</div>
                                        <div class="item">货仓备注：${list.comment}</div>
                                    </div>
                                    -->
                                </div>
                           </div>
                        </td>
                        <td><date:date value ="${list.addTime}" format="yyyy.MM.dd"/></td>
                        <td><date:date value ="${list.warehouseInTime}" format="yyyy.MM.dd"/></td>
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
                        	</c:if>
                        </td>
                     
                    </tr>
				</c:forEach>
				<c:if test="${empty barcodeList}">
					<tr>
						<td colspan="8">暂无匹配数据</td>
					</tr>
				</c:if>
                </tbody>
            </table>
             
           
        </div>
</div>
<c:if test="${not empty barcodeList}">
<div class="table-style4" style="position:fixed;bottom:0;width:100%;background: #fff;border-top: 1px solid #ccc;margin-bottom:0;
padding:10px 27px;">
    <div class="allchose">
        <input type="checkbox" name="" onclick="selectall(this);" autocomplete="off">
        <span>全选</span>
    </div>
    <div class="times">
        <span class="mr10">请选择批次</span>
        <c:forEach var="tlist" items="${timeArray}" varStatus="num">
         <input type="checkbox" name="" onclick="checkbarcode('${tlist}', this.checked);" autocomplete="off">
         <span class="mr20">${tlist}</span>
        </c:forEach>
    </div>
    <div class="print-record">
       <span class="bt-bg-style bg-light-blue" onclick="printall()">批量打印</span>
    </div>
</div>
</c:if>
<script type="text/javascript" src='<%= basePath %>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseIn/addBarcode.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>

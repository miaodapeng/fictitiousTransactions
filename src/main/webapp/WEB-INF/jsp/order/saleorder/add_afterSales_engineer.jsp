<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增售后产品与工程师" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/add_afterSales_engineer.js?rnd=<%=Math.random()%>'></script>

<div class="form-list  form-tips5">
    <form method="post" action="<%= basePath %>/aftersales/order/saveAddAfterSalesEngineer.do">
        <ul>
            <li>
            	<div class="form-tips">
                    <span>*</span>
                    <lable>工程师</lable>
                </div>
                <div class="f_left f_left_wid90">
                    <div class="form-blanks mb10">
                    	<span class="none" id="selname"></span>
						<input type="text" placeholder="请输入工程师名称" class="input-small" name="searchName" id="searchName" >
						<label class="bt-bg-style bg-light-blue bt-small" onclick="search();" id="search1" style='margin-top:-3px;'>搜索</label>
						<label class="bt-bg-style bg-light-blue bt-small none" onclick="research();" id="search2" style='margin-top:-3px;'>重新搜索</label>
						<span style="display:none;">
							<!-- 弹框 -->
							<div class="title-click nobor  pop-new-data" id="popEngineer"></div>
						</span>
                       	<input type="hidden" name="name" id="name">
                       	<input type="hidden" name="engineerId" id="engineerId">
                       	<input type="hidden" name="type" id="engineerId" value="541">
                    </div>
                    <div id="searchNameError"></div>
                </div>
            </li>
            <li style="margin-top:-10px;">
            	<div class="form-tips">
                     <span>*</span>
                     <lable>维修产品</lable>
                </div>
                <div class="f_left f_left_wid90">
                    
                    <table class="table">
                        <thead>
                            <tr>
                            	<th class="wid3">选择</th>
                                <th class="wid16">产品名称</th>
                                <th class="wid10">品牌</th>
                                <th class="wid10">型号</th>
                                <th class="wid8">物料编码</th>
                                <th class="wid8">销售价</th>
                                <th class="wid8">数量</th>
                                <th class="wid7">单位</th>
                                <th class="wid8">售后数量</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${list}" var="sgv">
                        		 <tr>
                        		 	<td>
                        		 		<input type="checkbox" name="oneSelect" alt="${sgv.afterSalesGoodsId}">
		                                <input type="hidden" name="afterSaleNums" >
		                            </td>    
	                                 <td class="text-left">
	                                    <span class="font-blue cursor-pointer addtitle" 
	                                    	tabTitle='{"num":"viewgoods<%=System.currentTimeMillis() + (int)(Math.random()*10000) %>",
		                    				"link":"./goods/goods/viewbaseinfo.do?goodsId=${sgv.goodsId}","title":"产品信息"}'>${sgv.goodsName}</span>
	                                    <div>${sgv.sku}</div>
	                                </td>
	                                <td>${sgv.model}</td>
	                                <td>${sgv.brandName}</td>
	                                <td>${sgv.materialCode}</td>
	                                <td><fmt:formatNumber type="number" value="${sgv.saleorderPrice}" pattern="0.00" maxFractionDigits="2" /></td>
	                                <td>${sgv.saleorderNum}</td>
	                                <td>${sgv.unitName}</td>
	                                <td>
	                                	<input type="text" style="width:50px;" alt1="${sgv.afterSalesGoodsId}" value="">
	                                	<input type="hidden" value="${sgv.num}">
	                                </td>
	                            </tr>
                        	</c:forEach>
                        </tbody>
                    </table>
                </div>
             </li>
             <li style="margin-top:-15px;">
                <div class="form-tips">
                    <span>*</span>
                    <lable>服务时间</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <div class="form-blanks">
                         	<input class="Wdate input-small input-smaller96 mr5" type="text" placeholder="请选择日期" 
                         		onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'end\')}'})" 
                  				name="start" id="serviceTime" value="<date:date value ='${start}' format="yyyy-MM-dd"/>"/>
                  			<input type="hidden" name="end" id="end" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'serviceTime\')}'})"
                  				value="<date:date value ='${end}' format="yyyy-MM-dd"/>">
                     	</div>
                     	<div id="serviceTimeError"></div>
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>工程师酬金</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                         <input class="input-small" type="text" name="engineerAmount" id="engineerAmount" value="">
                         <input type="hidden" name="afterSalesId" value="${afterSalesInstallstionVo.afterSalesId}">
                         <input type="hidden" name="areaId" id="areaId" value="${afterSalesInstallstionVo.areaId}">
                         <input type="hidden" name="formToken" value="${formToken}"/>
                    </div>
                    <div id="engineerAmountError"></div>
                      <div class="pop-friend-tips mt5">
                        	友情提示：
                     	<br/> 1、工程师酬金指我方支付给工程师的费用； 
                     	<br/> 2、售后服务费指我方向客户收取的费用；
                     </div>
                </div>
            </li>
        </ul>
        <div class="add-tijiao tcenter">
            <button type="submit" id="submit">提交</button>
        </div>
   </form>
</div>
<%@ include file="../../common/footer.jsp"%>
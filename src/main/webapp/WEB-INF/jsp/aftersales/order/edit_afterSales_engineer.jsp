<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑售后产品与工程师" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/add_afterSales_engineer_dsf.js?rnd=<%=Math.random()%>'></script>

<div class="form-list  form-tips5">
    <form method="post" action="<%= basePath %>/aftersales/order/saveEditAfterSalesEngineer.do">
        <ul>
            <li>
            	<div class="form-tips">
                    <span>*</span>
                    <lable>工程师</lable>
                </div>
                <div class="f_left f_left_wid90">
                    <div class="form-blanks">
                    	<span class="" id="selname">${afterSalesInstallstionVo.name}/${afterSalesInstallstionVo.mobile}</span>
                    	<span class="none" id="inp">
                    		<input type="text" placeholder="请输入工程师名称" class="input-small" name="searchName" id="searchName" value="${afterSalesInstallstionVo.name}">
                    	</span>
						<label class="bt-bg-style bg-light-blue bt-small none" onclick="search();" id="search1" style='margin-top:-3px;'>搜索</label>
						<label class="bt-bg-style bg-light-blue bt-small" onclick="research1();" id="search2" style='margin-top:-3px;'>重新搜索</label>
						<span style="display:none;">
							<!-- 弹框 -->
							<div class="title-click nobor  pop-new-data" id="popEngineer"></div>
						</span>
                       	<input type="hidden" name="name" id="name" value="${afterSalesInstallstionVo.name}">
                       	<input type="hidden" name="engineerId" id="engineerId" value="${afterSalesInstallstionVo.engineerId}">
                       	<input type="hidden" name="beforeParams" value='${beforeParams}'/>
                       	<input type="hidden" name="type"  value="550">
                    </div>
                    <div id="searchNameError"></div>
                </div>
            </li>
            
             <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>服务时间</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <div class="form-blanks">
                         	<input class="Wdate input-small input-smaller96 mr5" type="text" placeholder="请选择日期" 
                         		onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'end\')}'})" 
                  				name="start" id="serviceTime" value="<date:date value ='${afterSalesInstallstionVo.serviceTime}' format="yyyy-MM-dd"/>"/>
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
                         <input class="input-small" type="text" name="engineerAmount" id="engineerAmount" value="${afterSalesInstallstionVo.engineerAmount}">
                         <input type="hidden" name="afterSalesId" value="${afterSalesInstallstionVo.afterSalesId}">
                         <input type="hidden" name="areaId" id="areaId" value="${afterSalesInstallstionVo.areaId}">
                         <input type="hidden" name="afterSalesInstallstionId"  value="${afterSalesInstallstionVo.afterSalesInstallstionId}">
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑备货" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list">
    <form method="post" id="editBhSaleorderForm" action="./saveEditBhSaleorder.do" onsubmit="return checkEditBhSaleorderForm();">
        <ul>
            <li>
                <div class="form-tips">
                    <lable>申请原因</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-largest" name="prepareComments" id="prepareComments" value="${saleorder.prepareComments}" />
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>后期销售方案</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-largest" name="marketingPlan" id="marketingPlan" value="${saleorder.marketingPlan}" />
                    </div>
                </div>
            </li>
        </ul>
        <div class="add-tijiao tcenter">
        	<input type="hidden" name="saleorderId" value="${saleorder.saleorderId}"/>
        	<input type="hidden" name="beforeParams" value='${beforeParams}'>
            <button type="submit">提交</button>
            <button class="dele" type="button" id="close-layer">取消</button>
        </div>
    </form>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/bh_edit_saleorder_base_info.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
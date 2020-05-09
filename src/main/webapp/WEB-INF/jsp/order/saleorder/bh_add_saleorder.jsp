<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增备货" scope="application" />
<%@ include file="../../common/common.jsp"%>
<div class="form-list">
    <form method="post" id="addBhSaleorderForm" action="./saveAddBhSaleorder.do" onsubmit="return checkAddBhSaleorderForm();">
        <ul>
            <li>
                <div class="form-tips">
                    <lable>申请人</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                    ${username}
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>申请原因</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-largest" name="prepareComments" id="prepareComments" />
                    </div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <lable>后期销售方案</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" class="input-largest" name="marketingPlan" id="marketingPlan" />
                    </div>
                </div>
            </li>
        </ul>
        <div class="add-tijiao tcenter">
            <button type="submit">下一步</button>
        </div>
    </form>
</div>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/bh_add_saleorder.js?rnd=<%=Math.random()%>'></script>
<%@ include file="../../common/footer.jsp"%>
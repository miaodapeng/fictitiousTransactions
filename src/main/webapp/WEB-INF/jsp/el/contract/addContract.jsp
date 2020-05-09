<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增合同" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
    function addSubmit(){
        checkLogin();
        var traderId = $("#tradeId").val();
        if(traderId == '0'){
            layer.alert("请选择客户!");
            return;
        }

        var contractNumber = $("#contractNumber").val();
        if(contractNumber == ''){
            layer.alert("请填写合同号!");
            return;
        }

        var signDateStr = $("#signDateStr").val();
        if(signDateStr == ''){
            layer.alert("请选择签约时间!");
            return;
        }

        var contractValidityDateStartStr = $("#contractValidityDateStartStr").val();
        if(contractValidityDateStartStr == ''){
            layer.alert("请选择合同开始时间!");
            return;
        }

        var contractValidityDateEndStr = $("#contractValidityDateEndStr").val();
        if(contractValidityDateEndStr == ''){
            layer.alert("请选择合同结束时间!");
            return;
        }

        $("#addForm").submit();
    }
</script>
<div class="content">
    <div class="formtitle">合同信息</div>
    <div  id="desc_div">
        <form method="post" id="addForm" action="${pageContext.request.contextPath}/el/contract/addContract.do">
            <ul class="payplan">
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>客户</label>
                    </div>
                    <div class="f_left inputfloat">
                        <select class="input-xx" id="traderId" name="traderId" style="width:240px">
                            <option value="0">--请选择--</option>
                            <c:forEach items="${tradeList}" var="trader">
                                <option value="${trader.traderId}">${trader.traderName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>合同号</label>
                    </div>
                    <div class="f_left inputfloat">
                        <input type="text" name="contractNumber" id="contractNumber" style="width:240px"/>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>签约时间</label>
                    </div>
                    <div class="f_left inputfloat">
                        <input class="Wdate f_left input-smaller96 mr5" autocomplete="off" type="text" placeholder="请选择日期"
                               onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
                               name="signDateStr" id="signDateStr" value='<date:date value ="${contract.signDate}" format="yyyy-MM-dd"/>' style="width:240px;">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>合同开始时间</label>
                    </div>
                    <div class="f_left inputfloat">
                        <input class="Wdate f_left input-smaller96 mr5" autocomplete="off" type="text" placeholder="请选择日期"
                               onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'signDateStr\')}',maxDate:'#F{$dp.$D(\'contractValidityDateEndStr\')}'})"
                               name="contractValidityDateStartStr" id="contractValidityDateStartStr"
                               value='<date:date value ="${contract.contractValidityDateStart}" format="yyyy-MM-dd"/>' style="width:240px;">
                    </div>
                </li>
                <li>
                    <div class="infor_name ">
                        <span>*</span>
                        <label>合同结束时间</label>
                    </div>
                    <div class="f_left inputfloat">
                        <input class="Wdate f_left input-smaller96 mr5" autocomplete="off" type="text" placeholder="请选择日期"
                               onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'contractValidityDateStartStr\')}'})"
                               name="contractValidityDateEndStr" id="contractValidityDateEndStr"
                               value='<date:date value ="${contract.contractValidityDateEnd}" format="yyyy-MM-dd"/>' style="width:240px;">
                    </div>
                </li>
            </ul>
            <div class="add-tijiao tcenter mt10">
                <button type="button" class="bt-bg-style bg-deep-green" onclick="addSubmit();">下一步</button>
            </div>
        </form>
    </div>
</div>

<%@ include file="../../common/footer.jsp"%>
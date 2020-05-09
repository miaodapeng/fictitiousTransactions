<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="合同编辑" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
    function addSubmit(){
        checkLogin();
        var traderId = $("#traderId").val();
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
    
    function delContractSku(contactSkuId) {
        checkLogin();
        layer.confirm("您是否确认删除？", {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.ajax({
                type: "POST",
                url: "./delContractSku.do",
                data: {'contactSkuId':contactSkuId},
                dataType:'json',
                success: function(data){
                    window.location.reload();
                },
                error:function(data){
                    if(data.status ==1001){
                        layer.alert("当前操作无权限");
                    }
                }
            });
        }, function(){
        });
    }
</script>
<div class="content">
    <div class="formtitle">合同信息</div>
    <div  id="desc_div">
        <form method="post" id="addForm" action="${pageContext.request.contextPath}/el/contract/editContract.do">
            <input type="hidden" name="elContractId" id="elContractId" value="${contractInfo.elContractId}"/>
            <ul class="payplan">
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>客户</label>
                    </div>
                    <div class="f_left inputfloat">
                        <select class="input-xx" id="traderId" name="traderId" style="width:240px;">
                            <option value="0">--请选择--</option>
                            <c:forEach items="${tradeList}" var="trader">
                                <option value="${trader.traderId}" <c:if test="${trader.traderId == contractInfo.traderId}">selected</c:if> >${trader.traderName}</option>
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
                        <input type="text" name="contractNumber" id="contractNumber" value="${contractInfo.contractNumber}" style="width:240px;"/>
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
                               name="signDateStr" id="signDateStr" value='<date:date value ="${contractInfo.signDate}" format="yyyy-MM-dd"/>' style="width:240px;">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>合同开始时间</label>
                    </div>
                    <div class="f_left inputfloat">
                        <input class="Wdate f_left input-smaller96 mr5" autocomplete="off" type="text" placeholder="请选择日期"
                               onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'contractValidityDateEndStr\')}'})"
                               name="contractValidityDateStartStr" id="contractValidityDateStartStr"
                               value='<date:date value ="${contractInfo.contractValidityDateStart}" format="yyyy-MM-dd"/>' style="width:240px;">
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
                               value='<date:date value ="${contractInfo.contractValidityDateEnd}" format="yyyy-MM-dd"/>' style="width:240px;">
                    </div>
                </li>
                <li>
                    <div class="parts">
                        <div class="title-container">
                            <div class="table-title nobor">产品信息</div>
                            <div class="title-click nobor  pop-new-data" layerparams='{"width":"500px","height":"200px","title":"批量新增","link":"./toBatchAddContractSku.do?contractId=${contractInfo.elContractId}"}'>批量新增</div>
                        </div>
                        <table class="table  table-bordered table-striped table-condensed table-centered">
                            <thead>
                            <tr>
                                <th style="width:50px">序号</th>
                                <th style="width:80px">订货号</th>
                                <th style="width:80px">合同价格</th>
                                <th style="width:80px">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="sku" items="${skuList}" varStatus="staut">
                                <tr>
                                    <td>${staut.count}</td>
                                    <td>V${sku.skuId}</td>
                                    <td>${sku.contractPrice}</td>
                                    <td>
                                        <span class="delete" onclick="delContractSku(${sku.elContractSkuId});">删除</span>
                                        <span class="edit-user pop-new-data" layerParams='{"width":"500px","height":"250px","title":"编辑合同","link":"./toContractSkuEdit.do?contactSkuId=${sku.elContractSkuId}"}'>
                                            编辑
                                        </span>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </li>
            </ul>
            <div class="add-tijiao tcenter mt10">
                <button type="button" class="bt-bg-style bg-deep-green" onclick="addSubmit();">保存合同</button>
            </div>
        </form>
    </div>
</div>

<%@ include file="../../common/footer.jsp"%>
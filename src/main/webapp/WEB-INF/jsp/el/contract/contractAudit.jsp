<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="合同编辑" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
    function audit(){

        if($("#auditStatus").val() == '3' && $("#auditDesc").val() == ''){
            layer.alert("请填写审批意见");
            return;
        }

        $.ajax({
            url:'./contractAudit.do',
            data:$('#addForm').serialize(),
            type:"POST",
            dataType : "json",
            success:function(data){
                refreshNowPageList(data);//刷新父页面列表数据（保留在当前页）
            },
            error:function(data){
                if(data.status ==1001){
                    layer.alert("当前操作无权限")
                }
            }
        });
    }
</script>
<div class="content">
    <div  id="desc_div">
        <form method="post" id="addForm" action="${pageContext.request.contextPath}/el/contract/contractAudit.do">
            <input type="hidden" name="elContractId" id="elContractId" value="${contractInfo.elContractId}"/>
            <ul class="payplan">
                <li></li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>审批结果</label>
                    </div>
                    <div class="f_left inputfloat">
                        <select name="auditStatus" id="auditStatus" style="width: 100px;">
                            <option value = "2" select="selected">通过</option>
                            <option value = "3">不通过</option>
                        </select>
                    </div>
                </li>

                <li>
                    <div class="infor_name">
                        <label>审批意见</label>
                    </div>
                    <div class="f_left inputfloat">
                        <input type="textarea"  name="auditDesc" id="auditDesc" style="width:240px;"/>
                    </div>
                </li>

            </ul>
            <div class="add-tijiao tcenter mt10">
                <button type="button" class="bt-bg-style bg-deep-green" onclick="audit();">审批</button>
            </div>
        </form>
    </div>
</div>

<%@ include file="../../common/footer.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑sku" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
    $(function() {
        var $form = $("#editform");
        $form.submit(function() {
            checkLogin();
            $.ajax({
                async:false,
                url:'./contractSkuEdit.do',
                data:$form.serialize(),
                type:"POST",
                dataType : "json",
                beforeSend:function(){
                    var priceReg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
                    var contractPrice = $("#contractPrice").val();
                    if(contractPrice ==''){
                        layer.alert("合同金额不能为空!");
                        return false;
                    }
                    if(!priceReg.test(contractPrice)){
                        layer.alert("合同金额输入错误！仅允许使用数字，最多精确到小数后两位");
                        return false;
                    }
                },
                success:function(data){
                    refreshPageList(data);
                },
                error:function(data){
                    if(data.status ==1001){
                        layer.alert("当前操作无权限")
                    }
                }
            })
            return false;
        });
    });

</script>
<div class="addElement">
    <form action="" method="post" id="editform">
        <ul class="add-detail">
            <li>
                <div class="infor_name">
                    <span>*</span>
                    <lable for='unitName'>SKU_ID</lable>
                </div>
                <div class="f_left">
                    <input type="text"  id='skuId' class="input-middle" value="${sku.skuId}" readonly/>
                </div>
                <div class="clear"></div>
            </li>
            <li>
                <div class="infor_name">
                    <lable for='unitNameEn'>价格</lable>
                </div>
                <div class="f_left">
                    <input type="text" name='contractPrice' id='contractPrice' class="input-middle" value="${sku.contractPrice}" />
                </div>
                <div class="clear"></div>
            </li>
            <div class="clear"></div>
        </ul>
        <div class="add-tijiao tcenter">
            <button type="submit">提交</button>
            <input type="hidden" name='elContractSkuId' value="${sku.elContractSkuId}" />
            <button type="button" class="dele" id="cancle">取消</button>
        </div>
    </form>
</div>
<%@ include file="../../common/footer.jsp"%>
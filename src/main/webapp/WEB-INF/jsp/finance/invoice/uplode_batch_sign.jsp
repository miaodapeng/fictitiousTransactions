<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="上传结款信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript">
    $(function(){
        $("input[name='lwfile']").on('change', function( e ){
            //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
            var name = e.currentTarget.files[0].name;
            $("#uri").val(name);
        });
    })


    function subForm() {
        $("#errorTit").hide();
        if ($("#lwfile").val() == undefined || $("#lwfile").val() == "") {
            layer.alert("请选择需要上传的文件！");
            $("#uri").focus();
            return;
        }

        $("#batchTaxCateNo").ajaxSubmit({
            async : false,
            url : './saveuplodebatchsign.do',
            data : $("#batchTaxCateNo").serialize(),
            type : "POST",
            dataType : "json",
            success : function(data)
            {
                layer.confirm(data.message,{
                    btn: ['确定'] //按钮
                }, function(){
                    parent.location.reload();
                }, function(){
                })
            },
            error : function(XmlHttpRequest, textStatus, errorThrown)
            {
                //console.log(XmlHttpRequest.status);
                //console.log(textStatus);
                //console.log(errorThrown);
                if(XmlHttpRequest.status == 1001)
                {
                    layer.alert("您没有操作权限哦");
                }
                else
                {
                    layer.alert("操作失败");
                }
            }
        });
    }
</script>

<div class="form-list   form-tips4 ml7">
    <form id="batchTaxCateNo" method="post" enctype="multipart/form-data">
        <ul>
            <li style="margin-bottom:8px;">
                <div class="form-tips">
                    <lable>请上传表格</lable>
                </div>
                <div style="height: 25px"></div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <div class="pos_rel">
                            <input type="file" class="upload_file" style="display: none;" name="lwfile" id="lwfile">
                            <input type="text" class="input-large" id="uri" placeholder="请上传excel" name="uri" readonly="readonly">
                            <label class="bt-bg-style bt-middle bg-light-blue" type="file" onclick="return $('#lwfile').click();">浏览</label>
                            <div class="clear"></div>
                        </div>
                    </div>

                    <div class="pop-friend-tips" style="margin: 7px 0 0 0;">
                        如果您没有标准模板，请<a href="<%= basePath %>static/template/批量标记表格.xls">下载标准模板</a>
                    </div>
                </div>
            </li>
        </ul>
        <div class="font-red ml47 mb15" id="errorTit" style="display: none">表格项错误，第x行x列...</div>
        <div class="add-tijiao tcenter">
            <button type="button" class="bt-bg-style bg-deep-green" onclick="subForm();">提交</button>
            <button class="dele" type="button" id="close-layer">取消</button>
        </div>
    </form>
</div>

<%@ include file="../../common/footer.jsp"%>
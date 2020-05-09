<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="批量添加产品" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript">
	$(document).ready(function(){
		$("input[name='lwfile']").on('change', function( e ){
	        //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
	        var name = e.currentTarget.files[0].name;
	        $("#uri").val(name);
	    });
	})
	function subForm(){
		if($("#lwfile").val() == undefined || $("#lwfile").val() == ""){
			layer.alert("请选择需要上传的文件！");$("#uri").focus();
			return;
		}
		$("#batchAddGoods").ajaxSubmit({
			async : false,
			url : './batchSaveBhSaleorderGoods.do',
			data : $("#batchAddGoods").serialize(),
			type : "POST",
			dataType : "json",
			success : function(data) {
				/* layer.alert(data.message,{ icon: data.code==0?1:2 })
				if(data.code==0){
					if(parent.layer!=undefined){
						parent.layer.close(index);
					}
				} */
				if(data.code==0){
					refreshPageList(data);
				}else{
					$("#errorTit").html(data.message);
					$("#errorTit").show();
				}
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				console.log(XmlHttpRequest);
				console.log(textStatus);
				console.log(errorThrown);
				layer.alert("操作失败");
			}
		});
	}
</script>
	<div class="addElement">
		<div class="add-main">
			<form action="" method="post" id="batchAddGoods">
				<input type="hidden" name="saleorderId" value="${saleorder.saleorderId}">
				<div class="mt0 overflow-hidden">
	                <div class="infor_name ">
						请上传excle数据
	                </div>
	                <div class='f_left'>
	                    <input type="file" class="upload_file" style="display: none;" name="lwfile" id="lwfile" onchange="updateFileName(this)">
	                    <input type="text" class="input-middle" id="uri" placeholder="请上传excel" name="uri" readonly="readonly">
	                    <label class="bt-bg-style bt-middle bg-light-blue" type="file" onclick="lwfile.click();">浏览</label>
		                <div class="font-grey9  mb4">
							如果您没有标准模板，请<a href="<%= basePath %>static/template/备货批量添加产品模板.xlsx">下载标准模板</a>
		            	</div>
		           		 <div class="font-red  mb10" id="errorTit" style="display: none">表格项错误，第x行x列...</div>
		            		<div class="add-tijiao tcenter mt30">
		                    	<button type="button" class="bt-bg-style bg-deep-green" onclick="subForm();">提交</button>
		                    	<button class="dele" type="button" id="close-layer">取消</button>
		            		</div>
		            </div>
	            </div>
			</form>
		</div>
	</div>
<%@ include file="../../common/footer.jsp"%>
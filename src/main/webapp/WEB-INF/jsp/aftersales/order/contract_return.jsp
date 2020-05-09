<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="合同回传-售后" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src='<%= basePath %>static/js/aftersales/order/contract_return.js?rnd=<%=Math.random()%>'></script>
	 <div class="addElement">
        <div class="add-main adddepart">
        <form id="contract_return" method="post" enctype="multipart/form-data">
            <div class="mt20 overflow-hidden">
                <div class="infor_name ml47 mb4">
					合同回传
                </div>
                <div class='f_left'>
	                <div class='pos_rel f_left'>
	                    <input type="file" class="uploadErp" name="lwfile" id="lwfile" onchange="uploadFile(this, 583)">
	                    <input type="text" class="input-middle" id="uri_583" placeholder="" name="uri" onclick="lwfile.click();">
	                    <label class="bt-bg-style bt-middle bg-light-blue ml4" type="file" onclick="return $('#lwfile').click();">浏览</label>
	                    <input type="hidden" id="domain_583" name="domain" value="">
	                    <input type="hidden" id="file_name_583" name="name" value="">
	                    <input type="hidden"  name="relatedId" value="${afterSalesId }">
	                    <input type="hidden"  name="attachmentFunction" value="583">
                    </div>
                    <div class="f_left">
	                    <i class="iconsuccesss mt5 none" id="img_icon_583"></i>
	              		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_583">查看</a>
	                   	<span class="font-red cursor-pointer mt4 none" onclick="delProductImg(583)" id="img_del_583">删除</span>
                   	</div>
                   	<div class='clear'></div>
                	     <div class="font-grey9  mb4">
							友情提示：<br/>
							1、上传文件格式可以是jpg、png、pdf等格式；<br>
							2、上传文件不要超过2MB；
			            </div>
                </div>
            </div>
       
            <div class="add-tijiao tcenter mt30">
                <button type="submit" class="bt-bg-style bg-deep-green" onclick="contractReturnSubmit()">提交</button>
                <button class="dele" type="button" id="close-layer">取消</button>
            </div>
        </form>
    </div>
      </div>
<%@ include file="../../common/footer.jsp"%>
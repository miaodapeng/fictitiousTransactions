<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="入库附件上传" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src='<%= basePath %>static/js/logistics/warehouseIn/contract_return.js?rnd=<%=Math.random()%>'></script>
	 <div class="addElement">
        <div class="add-main adddepart">
        <form id="contract_return" method="post" enctype="multipart/form-data">
            <div class=" overflow-hidden">
                <div class='f_left'>
                	<div class='pos_rel f_left'>
                		<div class="infor_name ml47 mb4">
							入库附件上传：
	                	</div>
	                    <input type="file" class="uploadErp" name="lwfile" id="lwfile" onchange="uploadFile(this, 837)">
	                    <input type="text" class="input-middle" id="uri_837" placeholder="" name="uri" >
	                    <label class="bt-bg-style bt-middle bg-light-blue ml4" type="file" >浏览</label>
	                    <input type="hidden" id="domain_837" name="domain" value="">
	                    <input type="hidden" id="file_name_837" name="name" value="">
                    </div>
                    <div class="f_left">
                    	<i class="iconsuccesss mt5 none" id="img_icon_837"></i>
	              		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_837">查看</a>
	                   	<span class="font-red cursor-pointer mt4 none" onclick="delProductImg(837)" id="img_del_837">删除</span>
                    </div>
                    <div class='clear'></div>
                	     <div class="font-grey9  mb10 mt7" style="margin-left: 18px;">
							友情提示：<br/>
							1、上传文件格式可以是jpg、png、pdf等格式；<br>
							2、上传文件不要超过2MB；
			            </div>
               		</div>
            </div>
       
            <div class="add-tijiao tcenter">
            	<input type="hidden" value="${buyorderId}" name="relatedId" id="relatedId">
                <button type="button" class="bt-bg-style bg-deep-green" id="contract_return_submit" onclick="contractReturnSubmit(837)">提交</button>
                <button class="dele" type="button" id="close-layer">取消</button>
            </div>
        </form>
    </div>
      </div>
<%@ include file="../../common/footer.jsp"%>
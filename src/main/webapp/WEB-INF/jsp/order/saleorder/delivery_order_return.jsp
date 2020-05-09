<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="送货单回传" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src='<%= basePath %>static/js/order/saleorder/delivery_order_return.js?rnd=<%=Math.random()%>'></script>
	 <div class="addElement">
        <div class="add-main adddepart">
        <form id="delivery_order_return" method="post" enctype="multipart/form-data">
            <div class="overflow-hidden">
                <div class="infor_name ml47 mb4">
					送货单回传
                </div>
                <div class='f_left'>
                	<div class='pos_rel f_left'>
	                    <input type="file" class="uploadErp" name="lwfile" id="lwfile" onchange="uploadFile(this, 493)">
	                    <input type="text" class="input-middle" id="uri_493" placeholder="" name="uri">
	                    <label class="bt-bg-style bt-middle bg-light-blue ml4" type="file">浏览</label>
	                    <input type="hidden" id="domain_493" name="domain" value="">
	                    <input type="hidden" id="file_name_493" name="name" value="">
                    </div>
                    
                    <div class="f_left">
	                    <i class="iconsuccesss mt5 none" id="img_icon_493"></i>
	              		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_493">查看</a>
	                   	<span class="font-red cursor-pointer mt4 none" onclick="delProductImg(493)" id="img_del_493">删除</span>
                    </div>
                    <div class='clear'></div>
                    
                	     <div class="font-grey9  mb10 mt7">
							友情提示：<br/>
							1、上传文件格式可以是jpg、png、pdf等格式；<br>
							2、上传文件不要超过10MB；
			            </div>
                </div>
            </div>
       
            <div class="add-tijiao tcenter">
            	<input type="hidden" value="${saleorderId}" name="relatedId" id="saleorderId">
            	<input type="hidden" name="formToken" value="${formToken}"/>
                <button type="button" class="bt-bg-style bg-deep-green" id="delivery_order_return_submit" onclick="deliveryOrderReturnSubmit(493)">提交</button>
                <button class="dele" type="button" id="close-layer">取消</button>
            </div>
        </form>
    </div>
      </div>
<%@ include file="../../common/footer.jsp"%>
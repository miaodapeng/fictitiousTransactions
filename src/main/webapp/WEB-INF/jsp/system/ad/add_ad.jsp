<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/ad/add_ad.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<div class="form-list  form-tips8">
	<form action="" method="post" id="myform">
		<ul>
			<li>
                 <div class="form-tips">
                     <span>*</span>
                     <lable>名称</lable>
                 </div>
                 <div class="f_left ">
                     <div class="form-blanks ">
                     	<input type="text" placeholder="请输入广告名称" class="input-large" name="title" id="title" >
                     </div>
                     <div id="titleError"></div>
                 </div>
             </li>
			<li>
                 <div class="form-tips">
                 	<span>*</span> 
                     <lable>图片</lable>
                 </div>
                 <input type="hidden" id="domain" name="domain" value="${domain}">
                 <div class="f_left ">
                     <div class="form-blanks">
                     	<div class="pos_rel f_left">
                            <input type="file" class="uploadErp" id='file_1' name="lwfile" onchange="uploadFile(this,1);">
                            <input type="text" class="input-large" id="name_1" readonly="readonly"
                            	placeholder="请上传附件" name="fileName" onclick="file_1.click();" > 
                            <input type="hidden" id="uri_1" name="fileUri" >
                            <input type="hidden"  name="url" id="url">
		    			</div>
                           <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_1').click();">浏览</label>
                           <!-- 上传成功出现 -->
                        <div class="f_left">
                            <i class="iconsuccesss mt3 none" id="img_icon_1"></i>
                    		<a href="" target="_blank" class="font-blue cursor-pointer  mt3 none" id="img_view_1">查看</a>
	                    	<span class="font-red cursor-pointer  mt3 none" onclick="del(1)" id="img_del_1">删除</span>
                    	</div>
                     </div>
                     <div id="urlError"></div>
                     <div id="uri_343_div"></div>
                        <div class="mt5">
                        1，图片格式只能JPG、PNG、JPEG格式<br>
                        2，图片建议尺寸：800*500px<br>
                        3，图片大小不超过5M<br>
                        </div>
                 </div>
             </li>
             <li>
               <div class="form-tips">
                   <span>*</span>
                   <lable>是否上架</lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks">
                       <ul>
                            <li>
                                <input type="radio" name="isShow"  value="1">
                                <label>是</label>
                            </li>
                            <li>
                               <input type="radio" name="isShow" checked="checked" value="0">
                               <label>否</label>
                            </li>
                       </ul>    
                   </div>
               </div>
           </li>
			<li>
                 <div class="form-tips">
                     <span>*</span>
                     <lable>排序</lable>
                 </div>
                 <div class="f_left ">
                     <div class="form-blanks ">
                     	<input type="text" placeholder="数值越大排在最前面" class="input-large" name="sort" id="sort" >
                     </div>
                     <div id="sortError"></div>
                 </div>
             </li>
			<li>
				<div class="form-tips">
					<lable>备注</lable>
				</div>
				<div class="f_left ">
                    <div class="form-blanks ">
                        <textarea name="comments" id="comments"  rows="5" class="input-large"></textarea>
                    </div>
                    <div id="commentsError"></div>
                </div>
			</li>
			<div class="add-tijiao tcenter">
				<input type="hidden" id="subType" value="add">
				<button type="submit" id="sub">提交</button>
				<button type="button" class="dele" id="cancle">取消</button>
			</div>
		</ul>
		
	</form>
</div>

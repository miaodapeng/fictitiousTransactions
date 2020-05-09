<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="新增品牌" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/goods/brand/add_brand.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<div class="form-list  form-tips8">
	<form action="./saveBrand.do" method="post" id="addBrandForm">
		<input type="hidden" name="formToken" value="${formToken}"/>
		<ul >
			<li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>商品品牌</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <ul>
                   			<li>
	                             <input type="radio" name="brandNature" value="1">
	                             <label>国产品牌</label>
                            </li>
                            <li>
	                             <input type="radio" name="brandNature" value="2">
	                             <label>进口品牌</label>
                            </li>
                        </ul>
                    </div>
                    <div id="brandNatureError"></div>
                </div>
            </li>
            <li>
                <div class="form-tips">
                    <span>*</span>
                    <lable>品牌名称</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <input type="text" name='brandName' id='brandName' placeholder="请按标准规范填写品牌名称" class="input-large"/>
                        <p><span style="font-size:12px;color:#999;line-height: 18px;">国外品牌：英文名【必须】+中文名（英文大小写符合品牌商官方定义），示例：BioTek伯腾；<br/>国产品牌：中文名【必须】+英文名（英文大小写符合品牌商官方定义），示例：六六</span></p>
                      
                    </div>
                    <div id="brandNameError"></div>
                </div>
            </li>
            <li>
                 <div class="form-tips">
                     <lable>品牌LOGO</lable>
                 </div>
                 <input type="hidden" id="domain" name="logoDomain" value="${domain}">
                 <div class="f_left ">
                     <div class="form-blanks">
                     	<div class="pos_rel f_left">
	                            <input type="file" class="uploadErp" id='file_1' name="lwfile" onchange="uploadFile(this,1);">
	                            <input type="text" class="input-largest" id="name_1" readonly="readonly"
	                            	placeholder="请上传附件" name="fileName" onclick="file_1.click();" > 
	                            <input type="hidden" id="uri_1" name="logoUri" >
			    			</div>
                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_1').click();">浏览</label>
                            <!-- 上传成功出现 -->
                            <div class="f_left">
	                            <i class="iconsuccesss mt3 none" id="img_icon_1"></i>
	                    		<a href="" target="_blank" class="font-blue cursor-pointer mt3 none" id="img_view_1">查看</a>
		                    	<span class="font-red cursor-pointer  mt3 none" onclick="del(1)" id="img_del_1">删除</span>
	                    	</div>
	                    	<div class='clear'></div>
                     </div>
                     <p><span style="font-size:12px;color:#999;line-height: 18px;">此处上传品牌图标，图片格式需使用JPG、PNG、JPEG格式；
                     <br/>图片尺寸建议112*45或其他等比尺寸；
                     <br/>图片大小不能超过2M；
                     <br/>图片底纹需为白色</span></p>
                 </div>
             </li>
            <li>
                <div class="form-tips">
                    <lable>品牌网址</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <input type="text" name='brandWebsite' placeholder="范例：www.mindray.com" id='brandWebsite' class="input-large"/>
                    </div>
                    <div id="brandWebsiteError"></div>
                </div>
            </li>
            <li>
                 <div class="form-tips">
                     <lable>品牌故事</lable>
                 </div>
                 <div class="f_left ">
                     <div class="form-blanks ">
                         <textarea name="description" id="description" placeholder="格式统一：以时间线的方式，通过品牌的发展史来说明品牌的故事
荣誉体系：获得过哪些荣誉
生产规模：目前的发展规模
信誉体系：公司目前享有的信誉
专利体系：公司拥有并受到国家认可的专利技术合集。
" rows="8" class="wid90"></textarea>
                     </div>
                     <div id="descriptionError"></div>
                     <div class="pop-friend-tips mt6">
	                     <div class="add-tijiao tcenter">
							<button type="submit" id="sub">提交</button>
						</div>
					 </div>
                 </div>
             </li>
		</ul>
		
	</form>
</div>
<%@ include file="../../common/footer.jsp"%>
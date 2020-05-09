<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑基本信息" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src='<%= basePath %>static/js/goods/goods/editbaseinfo.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%= basePath %>static/js/goods/goods/commonGoods.js?rnd=<%=Math.random()%>'></script>
<style>
<!--
.browser {
	overflow:hidden;
	margin-top:8px;
}
.browser:first-child{
	margin-top:0;
}
-->
</style>
	<div class="customer">
        <ul>
            <li>
                <a class="customer-color" href="${pageContext.request.contextPath}/goods/goods/viewbaseinfo.do?goodsId=${goods.goodsId}">商品信息</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/goods/goods/viewsaleinfo.do?goodsId=${goods.goodsId}">销售信息</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/goods/goods/viewpurchaseinfo.do?goodsId=${goods.goodsId}">采购信息</a>
            </li>
            <li>
                <a href="javascript:void(0)">运营信息</a>
            </li>
        </ul>
    </div>
    <div class="formpublic" style="margin-top:-10px;">
        <form action="${pageContext.request.contextPath}/goods/goods/savebaseinfo.do" method="post" id="saveBaseInfoForm">
            <ul>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>商品名称</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest" placeholder='请按照"品牌+商品名+型号+商品属性+商品别名"格式进行填写' name="goodsName" id="goodsName" value="${goods.goodsName}" onkeyup="checkProductName();"/>
                    </div>
                  	<div class="clear" >
	                  	<div  class="text" style="visibility: visible; margin-left:7%">
	          				<p><span>示例：</span><span class="font-grey9">迈瑞Mindray三分类血液细胞分析仪BC-1800 民营医院&nbsp; 诊所用全自动血球仪</span></p>
	        			</div>
                    </div>
			        
                </li>
                <li>
                    <div class="infor_name">
                        <label>别名</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-large"  name="aliasName" id="aliasName" value="${goods.aliasName}"/>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>品牌名称</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-middle mr5" name="brandKey" placeholder="请输入品牌关键词进行筛选" />
                        <span class="bt-small bg-light-blue bt-bg-style mr5" onclick="searchBrand()">检索</span>
                        <select  class="input-small" name='brandId' id="brandId">
                            <option value='0'>请选择品牌</option>
                            <c:forEach items="${brandList}" var="list" varStatus="status">
                            <option value="${list.brandId}" <c:if test="${not empty goods &&  goods.brandId == list.brandId}">selected="selected"</c:if>>${list.brandName}</option>
                            </c:forEach>
                        </select>
                        <span class="font-grey9">&nbsp;若品牌缺失请与品牌管理员联系</span>
                        <div id="brandIdMsg" style="color: #fc5151; padding-top: 4px; clear: both;"></div>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>制造商型号</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest" name="model" id="model" placeholder="例如: URIT-5181" value="${goods.model}"/>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>厂家名称</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest" name="manufacturer" id="manufacturer" placeholder="请输入厂家的全称"  value="${goods.manufacturer}"/>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>供应商型号</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest" name="supplyModel" id="supplyModel" value="${goods.supplyModel}"/>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>物料编码</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest" name="materialCode" id="materialCode" placeholder="示例: 105-002876-00" value="${goods.materialCode}"/>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>Wiki链接</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-largest" placeholder="请输入正确的链接地址" name="href" id="wikihref"  value="${goods.href}"/>
                    </div>
                </li>
                 <li>
                    <div class="infor_name mt0">
                    	<span>*</span>
                        <label>商品类型</label>
                    </div>
                    <div class="f_left inputfloat">
                        <ul id="goods_type_div">
                        </ul>
                        <input type="hidden" id="goodsTypeId" value="${goods.goodsType}" />
                    </div>
                </li>
                <li id="new_national_standard">
                    <div class="infor_name">
                        <span>*</span>
                        <label>新国标分类</label>
                    </div>
                    <!--  
                    <div class="f_left" id="standard_category_div">
                    	<c:if test="${goods.standardCategoryId == 0 || goods.standardCategoryId == null}">
	                    <select id='standardCategoryOpt' name='standardCategoryOpt' onchange="updateStandardCategory(this);">
							<option value="-1" id="1">请选择</option>
							<c:forEach var="list" items="${standardCategoryList}" varStatus="status">
								<option value="${list.standardCategoryId}" id="${list.level}">${list.categoryName}</option>
							</c:forEach>
						</select>
						</c:if>
                    </div>
                    <div class="clear" style="margin-left:110px;">
                 
                 	</div> -->
                 	
                 	<div class="f_left inputfloat">
                    	<input type="text" class="input-largest mr5 mb7" name="standardCategoryKey" id="new_standar_input" placeholder="请输入您要搜索的分类" />
                        <span class="bt-small bg-light-blue bt-bg-style mb7" onclick="searchStandardCategory('new_standar_input')">搜索</span>
                        <div class="f_left font-grey9 mt4">
                             <span>( 空搜索展示分级列表 )</span>
                        </div>
                        <div class="clear f_left" id="new_stand_div_show_1">
                        	<div id="new_stand_div_show_1_div">
	                        	<div id="u1661" class="text" style="visibility: visible;">
	                  				<p><span class='mb5'>一级分类</span></p>
	                			</div>
		                        <select style="display:block;height:108px; padding:4px 2px;" multiple="multiple" name='standardCategory_Opt' onclick="loadStandardCategory(this, 1);" >
									<c:forEach var="list" items="${standardCategoryList}" varStatus="status">
										<option value="${list.standardCategoryId}" id="${list.level}">${list.categoryName}</option>
									</c:forEach>
								</select>
							</div>
                        </div>
                      	<div class=" f_left ml10" id="new_stand_div_show_2" >
                        	
                        </div>
                      	<div class=" f_left ml10" id="new_stand_div_show_3" >
                        	
                        </div>
                        <!--  
                        <span id="stand_span_id" class="bt-small bg-light-blue bt-bg-style none" onclick="sureStandOk('new_standar_input')" style="margin: 68px 0 0 10px;">确定</span>
                        -->
                        <input type="hidden" name="standardCategoryId" id="standardCategoryId" value="${goods.standardCategoryId}" />
                        <!-- 中间值id -->
                        <input type="hidden" id="input_center_id_val" value="" />
                        <input type="hidden" id="input_stand_center_name_val_one" value="" />
                        <input type="hidden" id="input_stand_center_name_val_two" value="" />
                        <input type="hidden" id="input_stand_center_name_val_three" value="" />
                    </div>
                 	
                </li>
                <li class="manager_category_level <c:if test="${goods.standardCategoryId == null || goods.standardCategoryId == 0 || goods.standardCategoryId == 1388}">none</c:if>">
                    <div class="infor_name mt0">
                    	<span>*</span>
                        <label>管理类别</label>
                    </div>
                    <div class="f_left inputfloat">
                        <ul id="manage_category_level_div">
                        </ul>
                    </div>
                    <input type="hidden" id="manageCategoryLevelId" value="${goods.manageCategoryLevel}" />
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>旧国标分类</label>
                    </div>
                    <div class="f_left management-types">
                        <ul class="f_left">
                            <li>
                                <select name="manageCategory" id="manageCategory">
                                    <option value="0">请选择管理类别</option>
                                </select>
                            </li>
                        </ul>
                        <div class="f_left font-grey9 mt4">
                                	（若不是医疗器械，请选择非医疗器械）
                        </div>
                        <input type="hidden" id="manageCategoryId" value="${goods.manageCategory}" />
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>商品运营分类</label>
                    </div>
                    <!--  
                    <div class="f_left" id="category_div">
                    	<c:if test="${goods.categoryId == 0 || goods.categoryId == null}">
                    	<select id='categoryOpt' name='categoryOpt' onchange="updateCategory(this);">
							<option value="-1" id="1">请选择</option>
							<c:forEach var="list" items="${categoryList}" varStatus="status">
								<option value="${list.categoryId}" id="${list.level}">${list.categoryName}</option>
							</c:forEach>
						</select>
						</c:if>
                    </div>
                    <div class="clear" style="margin-left:110px;">
                    <input type="hidden" name="categoryId" id="categoryId" value="${goods.categoryId}" />
                    </div>-->
                    
                    <div class="f_left inputfloat">
                    	<input type="text" class="input-largest mr5 mb7" name="categoryKey" id="category_inpu_id" placeholder="请输入您要搜索的分类" />
                        <span class="bt-small bg-light-blue bt-bg-style mb7" onclick="searchCategoryById('category_inpu_id', 1)">搜索</span>
                        <div class="f_left font-grey9 mt4">
                             <span>( 空搜索展示分级列表 )</span> 
                        </div>
                        <div class="clear f_left" id="new_category_div_show_1">
                        	<div id="new_category_div_show_1_div">
	                        	<div id="u1661" class="text" style="visibility: visible;">
	                  				<p><span class='mb5'>一级分类</span></p>
	                			</div>
		                        <select style="display:block;height:108px; padding:4px 2px;" name='category_Opt' multiple="multiple" onclick="loadCategory(this, 1);" >
									<c:forEach var="list" items="${categoryList}" varStatus="status">
										<option value="${list.categoryId}" id="${list.level}">${list.categoryName}</option>
									</c:forEach>
								</select>
							</div>
                        </div>
                      	<div class=" f_left ml10" id="new_category_div_show_2" >
                        	
                        </div>
                      	<div class=" f_left ml10" id="new_category_div_show_3" >
                        	
                        </div>
                        <!--  
                        <span id="category_span_id" class="bt-small bg-light-blue bt-bg-style none" onclick="sureCategoryOkById('category_inpu_id')" style="margin: 68px 0 0 10px;">确定</span>
                        -->
                        <input type="hidden" name="categoryId" id="categoryId" value="${goods.categoryId}" />
                        <!-- 中间值id -->
                        <input type="hidden" id="input_category_center_name_val_one" value="" />
                        <input type="hidden" id="input_categor_center_name_val_two" value="" />
                        <input type="hidden" id="input_categor_center_name_val_three" value="" />
                    </div>
                    
                </li>
                <li>
                    <div class="infor_name">
                        <span>*</span>
                        <label>单位</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-middle mr5" placeholder="请输入品牌关键词进行筛选" name="unitKey" />
                        <span class="bt-small bg-light-blue bt-bg-style mr5" onclick="searchUnit()">检索</span>
                        <select class="input-small" name="unitId" id="unitId">
                            <option value='0'>请选择单位</option>
                            <c:forEach items="${unitList}" var="list" varStatus="status">
                            <option value="${list.unitId}" <c:if test="${not empty goods &&  goods.unitId == list.unitId}">selected="selected"</c:if>>${list.unitName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                    	<!-- <span>*</span> -->
                        <label>商品图片</label>
                    </div>
                    <div class="f_left ">
                    	<c:set var="is_have_343" value="0"></c:set>
                    	<c:set var="domain_343" value=""></c:set>
                    	<c:forEach items="${attachmentList}" var="list" varStatus="status">
	                    	<c:if test="${list.attachmentType == 343}">
			                    <c:set var="is_have_343" value="1"></c:set>
			                    <c:set var="domain_343" value="${list.domain}"></c:set>
                    		</c:if>
                    	</c:forEach>
                    	
                    	<c:if test="${is_have_343 == 1}">
                    		<c:forEach items="${attachmentList}" var="list" varStatus="status">
		                    	<c:if test="${list.attachmentType == 343}">
									<div class="clear mt8"> 
				                        <div class='pos_rel f_left'>
				                        	<input type="file" class=" uploadErp"  name="lwfile" id="lwfile_343" onchange="uploadFile(this, 343)">
					                        <input value="${list.uri}" type="text" class="input-middle mr5" id="uri_343" placeholder="请上传商品图片" name="uri_343">
					                        <label class="bt-bg-style bt-middle bg-light-blue" type="file" >浏览</label>
				                        </div>
				                        
				                      <div class="f_left">
				                        <i class="iconsuccesss mt5 " id="img_icon_343"></i>
				                   		<a href="http://${list.domain}${list.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 " id="img_view_343">查看</a>
				                    	<span class="font-red cursor-pointer mt4 " onclick="delAttachment(this)" id="img_del_343">删除</span>
				                      </div>
			                      	</div>
	                    		</c:if>
	                    	</c:forEach>
                    	</c:if>
                    	
                    	<c:if test="${is_have_343 == 0}">
	                    	<div class="clear mt8"> 
		                        <div class='pos_rel f_left'>
		                        	<input type="file" class=" uploadErp"  name="lwfile" id="lwfile_343" onchange="uploadFile(this, 343)">
			                        <input value="" type="text" class="input-middle mr5" id="uri_343" placeholder="请上传商品图片" name="uri_343">
			                        <label class="bt-bg-style bt-middle bg-light-blue" type="file" >浏览</label>
			                        <input type="hidden" id="domain_343" name="domain_343">
		                        </div>
		                        
		                      <div class="f_left">
		                        <i class="iconsuccesss mt5 none" id="img_icon_343"></i>
		                   		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_343">查看</a>
		                    	<span class="font-red cursor-pointer mt4 none" onclick="delAttachment(this)" id="img_del_343">删除</span>
		                      </div>
	                      	</div>
                      	</c:if>
                      	<input type="hidden" id="domain_343" name="domain_343" value="${domain_343}">
                      	<div class="mt8 clear" id="conadd343">
                            <span class="bt-border-style bt-small border-blue mt8" onclick="con_add(343);">继续添加</span>
                        </div>
                        <div id="uri_343_div"></div>
                        <div class="mt5">
                        1，图片格式只能JPG、PNG、JPEG格式<br>
                        2，使用白底等纯素色背景<br>
                        3，商品外观一定要清晰<br>
                        4，商品图片禁止出现文字、水印等“牛皮癣”信息<br>
                        5，品牌logo一律显示在右上方<br>
                        6，图片建议尺寸：800*800px<br>
                        7，图片大小不超过5M
                        </div>
                    </div>
                </li>
                <li>
                    <div class="infor_name mt3">
                        <label>商品体积</label>
                    </div>
                    <div class="f_left size">
                        <label class="mt3 mr4">长</label>
                        <input type="text" class="input-smallest bt-small mr2" name="goodsLength" value="${goods.goodsLength}" />
                        <label class="mt3 mr10">cm</label>
                        <label class="mt3 mr4">宽</label>
                        <input type="text" class="input-smallest bt-small mr2" name="goodsWidth" value="${goods.goodsWidth}"/>
                        <label class="mt3 mr10">cm</label>
                        <label class="mt3 mr4">高</label>
                        <input type="text" class="input-smallest bt-small mr2" name="goodsHeight" value="${goods.goodsHeight}"/>
                        <label class="mt3 mr10">cm</label>
                        <label class="mt3 mr4">净重</label>
                        <input type="text" class="input-smallest bt-small mr2" name="netWeight" value="${goods.netWeight}"/>
                        <label class="mt3">kg</label>
                        <div id="size_span" style="clear:both;"></div>
                    </div>
                </li>
                <li class="line">
                    <div class="infor_name mt3">
                        <label>运输/包装体积</label>
                    </div>
                    <div class="f_left size">
                        <label class="mt3 mr4">长</label>
                        <input type="text" class="input-smallest bt-small mr2" name="packageLength" value="${goods.packageLength}"/>
                        <label class="mt3 mr10">cm</label>
                        <label class="mt3 mr4">宽</label>
                        <input type="text" class="input-smallest bt-small mr2" name="packageWidth" value="${goods.packageWidth}"/>
                        <label class="mt3 mr10">cm</label>
                        <label class="mt3 mr4">高</label>
                        <input type="text" class="input-smallest bt-small mr2" name="packageHeight" value="${goods.packageHeight}"/>
                        <label class="mt3 mr10">cm</label>
                        <label class="mt3 mr4">毛重</label>
                        <input type="text" class="input-smallest bt-small mr2" name="grossWeight" value="${goods.grossWeight}"/>
                        <label class="mt3">kg</label>
                        <div id="package_span" style="clear:both;"></div>
                    </div>
                    <div class="clear"></div>
                </li>
                <li>
                    <input type="hidden" value="${goodsAttributeIds}" id="goodsAttributeIds">
                    <input type="hidden" id="require_attr_id_str" value="">
                    <input type="hidden" id="attr_id_str" value="" name="attr_id_str">
                </li>
                <li class="line" id="attribute_list_div">
                </li>
                <li>
                    <div class="infor_name">
                    	<span>*</span>
                        <label>技术参数</label>
                    </div>
                    <div class="f_left">
                    <textarea class="input-largest" placeholder="格式如下（参数名+冒号+参数值+分号）：
参数名1：参数值1；参数名2：参数值2；……参数名N：参数值N" name="technicalParameter" id="technicalParameter">${goods.technicalParameter}</textarea>
					</div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>性能参数</label>
                    </div>
                    <div class="f_left">
                    <textarea class="input-largest" placeholder="格式如下（参数名+冒号+参数值+分号）：
参数名1：参数值1；参数名2：参数值2；……参数名N：参数值N" name="performanceParameter" id="performanceParameter">${goods.performanceParameter}</textarea>
					</div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>规格参数</label>
                    </div>
                    <div class="f_left">
                    <textarea class="input-largest" placeholder="格式如下（参数名+冒号+参数值+分号）：
参数名1：参数值1；参数名2：参数值2；……参数名N：参数值N" name="specParameter" id="specParameter">${goods.specParameter}</textarea>
					</div>
                </li>
                <!--  
                <li class="licenseNumber <c:if test="${goods.manageCategoryLevel != 340 && goods.manageCategoryLevel != 341}">none</c:if>">
                    <div class="infor_name">
                    	<span>*</span>
                        <label>批准文号</label>
                    </div>
                    <div class="f_left">
                        <input type="textarea" class="input-larger" name="licenseNumber" id="licenseNumber" value="${goods.licenseNumber}">
                    </div>
                </li>-->
                <li class="recordNumber <c:if test="${goods.manageCategoryLevel != 339}">none</c:if>">
                    <div class="infor_name">
                    	<span>*</span>
                        <label>备案编号</label>
                    </div>
                    <div class="f_left">
                        <input type="textarea" class="input-larger" name="recordNumber" id="recordNumber" value="${goods.recordNumber}">
                    </div>
                </li>
                <%-- <li id="registrationNumberName_li" class="hide">
                    <div class="infor_name">
						<span>*</span>
                        <label>注册证名称</label>
                    </div>
                    <div class="f_left">
                        <input type="textarea" class="input-larger" name="registrationNumberName" id="registrationNumberName" value="${goods.registrationNumberName}">
                    </div>
                </li> --%>
                
                <li id="registrationNumber_li">
                    <div class="infor_name">
						<span>*</span>
                        <label>注册证号</label>
                    </div>
                    <div class="f_left">
                        <input type="textarea" class="input-larger" name="registrationNumber" id="registrationNumber" value="${goods.registrationNumber}">
                    </div>
                </li>
                
                
                
                <li class="atta_344 <c:if test="${goods.standardCategoryId == null || goods.standardCategoryId == 0 || goods.standardCategoryId == 1388}">none</c:if>">
                    <div class="infor_name">
                    	<span>*</span>
                        <label>注册证</label>
                    </div>
                    
                    <div class="f_left ">
                    	<c:set var="is_have_344" value="0"></c:set>
	                    <c:set var="domain_344" value=""></c:set>
                    	<c:forEach items="${attachmentList}" var="list" varStatus="status">
	                    	<c:if test="${list.attachmentType == 344}">
			                    <c:set var="is_have_344" value="1"></c:set>
			                    <c:set var="domain_344" value="${list.domain}"></c:set>
                    		</c:if>
	                    </c:forEach>
	                    
	                    <!-- 如果list有值-->
	                    <c:if test="${is_have_344 == 1}">
	                    	<c:forEach items="${attachmentList}" var="list" varStatus="status">	
		                        <c:if test="${list.attachmentType == 344}">
									<div class="browser upload_count" > 
				                        <div class='pos_rel f_left'>
				                        	<input type="file" class=" uploadErp"  name="lwfile" id="lwfile_344" onchange="uploadFile(this, 344)">
					                        <input value="${list.uri}" type="text" class="input-middle mr5 uri_344" placeholder="请上传注册证" name="uri_344" onclick="lwfile_344.click();">
					                        <label class="bt-bg-style bt-middle bg-light-blue" type="file" onclick="lwfile_344.click();">浏览</label>
				                        </div>
					                        
					                    <div class="f_left">
					                        <i class="iconsuccesss mt5 " id="img_icon_344"></i>
					                   		<a href="http://${domain_344}${list.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 " id="img_view_344">查看</a>
					                    	<span class="font-red cursor-pointer mt4 " onclick="delAttachment(this)" id="img_del_344">删除</span>
					                    </div>
			                      	</div>
	                    		</c:if>
							</c:forEach>
                    	</c:if>
                    	
                    	
                    	<div class="mt8 clear" id="conadd344">
                            <span class="bt-border-style bt-small border-blue mt8" onclick="con_add(344);">继续添加</span>
                      	</div>
                      	
						<input type="hidden" id="domain_344" name="domain_344" value="${domain_344}">
						
						<div class="clear mt5">
                       		格式：JPG、PNG、BMP等</br>
                        </div>
						<div id="uri_344_div"></div>
                    </div>
                    
                    <%-- <div class="f_left ">
                    	<c:set var="is_have_659" value="0"></c:set>
                    	<c:set var="domain_659" value=""></c:set>
                    	<c:forEach items="${attachmentList}" var="list" varStatus="status">
	                    	<c:if test="${list.attachmentType == 659}">
			                    <c:set var="is_have_659" value="1"></c:set>
			                    <c:set var="domain_659" value="${list.domain}"></c:set>
                    		</c:if>
                    	</c:forEach>
                    	<c:if test="${is_have_659 == 1}">
                    		<c:forEach items="${attachmentList}" var="list" varStatus="status">	
		                    	<c:if test="${list.attachmentType == 659}">
									<div class="clear mt8"> 
				                        <div class='pos_rel f_left'>
				                        	<input type="file" class=" uploadErp"  name="lwfile" id="lwfile_659" onchange="uploadFile(this, 659)">
					                        <input value="${list.uri}" type="text" class="input-middle mr5" id="uri_659" placeholder="请上传商品的专利文件" name="uri_659">
					                        <label class="bt-bg-style bt-middle bg-light-blue" type="file" >浏览</label>
				                        </div>
				                        
				                      <div class="f_left">
				                        <i class="iconsuccesss mt5 " id="img_icon_659"></i>
				                   		<a href="http://${list.domain}${list.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 " id="img_view_659">查看</a>
				                    	<span class="font-red cursor-pointer mt4 " onclick="delAttachment(this)" id="img_del_659">删除</span>
				                      </div>
			                      	</div>
	                    		</c:if>
	                    	</c:forEach>
                    	</c:if>
                    </div> --%>
                    
                </li>
                
                
                
                
                
                
                
				<li class="bawj_class none">
                    <div class="infor_name">
                    	<span>*</span>
                        <label>备案文件</label>
                    </div>
                    <div class="f_left">
                    	<c:set var="uri_680" value=""></c:set>
	                    <c:set var="domain_680" value=""></c:set>
                    	<c:forEach items="${attachmentList}" var="list" varStatus="status">
	                    	<c:if test="${list.attachmentType == 680}">
			                    <c:set var="uri_680" value="${list.uri}"></c:set>
			                    <c:set var="domain_680" value="${list.domain}"></c:set>
                    		</c:if>
                    	</c:forEach>
                        <input type="file" class="upload_file" style="display: none;" name="lwfile" id="lwfile_680" onchange="uploadFile(this, 680)">
                        <input type="text" class="input-middle" id="uri_680" placeholder="请上传注册证原件扫描件" name="uri_680" value="${uri_680}" onclick="lwfile_680.click();">
                        <label class="bt-bg-style bt-middle bg-light-blue ml4" type="file" onclick="lwfile_680.click();">浏览</label>
                        
                        <c:choose>
							<c:when test="${domain_680 != ''}">
								<i class="iconsuccesss mt5" id="img_icon_680"></i>
		                    	<a href="http://${domain_680}${uri_680}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_680">查看</a>
		                    	<span class="font-red cursor-pointer mt4" onclick="delProductImg(680)" id="img_del_344">删除</span>
							</c:when>
							<c:otherwise>
								<i class="iconsuccesss mt5 none" id="img_icon_680"></i>
	                    		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_680">查看</a>
		                    	<span class="font-red cursor-pointer mt4 none" onclick="delProductImg(680)" id="img_del_680">删除</span>
							</c:otherwise>
						</c:choose>
						<input type="hidden" id="domain_680" name="domain_680" value="${domain_680}">
						<div id="uri_680_div"></div>
						<div class="clear mt5">
                       		格式：JPG、PNG、BMP等</br>
                        </div>
                    </div>
                </li>
                <li class="zcz_div <c:if test="${goods.standardCategoryId == null || goods.standardCategoryId == 0 || goods.standardCategoryId == 1388}">none</c:if>">
                    <div class="infor_name">
                    	<span>*</span>
                        <label>注册证有效期</label>
                    </div>
                    <div class="f_left">
                        <input class="Wdate input-smaller mr4" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endtimeStr\')}'})" id="begintimeStr" name="begintimeStr" value='<date:date value ="${goods.begintime}" format="yyyy-MM-dd"/>' />
                        <input class="Wdate input-smaller" type="text" placeholder="请选择日期" onClick="WdatePicker()" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'begintimeStr\')}'})" id="endtimeStr" name="endtimeStr" value='<date:date value ="${goods.endtime}" format="yyyy-MM-dd"/>' />
                    	<span id="register_span"></span>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>包装清单</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-xxx" name="packingList" id="packingList" value="${goods.packingList}">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>商品规格</label>
                    </div>
                    <div class="f_left">
                        <input type="textarea" class="input-xxx" name="spec" id="spec" value="${goods.spec}">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>商品产地</label>
                    </div>
                    <div class="f_left">
                        <input type="textarea" class="input-xxx" placeholder="详细地址" name="productAddress" id="productAddress" value="${goods.productAddress}">
                    </div>
                </li>
                <li>
                    <div class="infor_name mt0">
                        <span>*</span>
                        <label>商品级别</label>
                    </div>
                    <div class="f_left inputfloat">
                        <ul id="goods_level_div">
                        </ul>
                        <input type="hidden" id="goodsLevelId" value="${goods.goodsLevel}" />
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>检测报告</label>
                    </div>
                    <div class="f_left ">
                    	<c:set var="is_have_658" value="0"></c:set>
                    	<c:set var="domain_658" value=""></c:set>
                    	<c:forEach items="${attachmentList}" var="list" varStatus="status">
	                    	<c:if test="${list.attachmentType == 658}">
			                    <c:set var="is_have_658" value="1"></c:set>
			                    <c:set var="domain_658" value="${list.domain}"></c:set>
                    		</c:if>
                    	</c:forEach>
                    	
                    	<c:if test="${is_have_658 == 1}">
                    		<c:forEach items="${attachmentList}" var="list" varStatus="status">
		                    	<c:if test="${list.attachmentType == 658}">
									<div class="clear mt8"> 
				                        <div class='pos_rel f_left'>
				                        	<input type="file" class=" uploadErp"  name="lwfile" id="lwfile_658" onchange="uploadFile(this, 658)">
					                        <input value="${list.uri}" type="text" class="input-middle mr5" id="uri_658" placeholder="请上传商品的测试报告" name="uri_658">
					                        <label class="bt-bg-style bt-middle bg-light-blue" type="file" >浏览</label>
				                        </div>
				                        
				                      <div class="f_left">
				                        <i class="iconsuccesss mt5 " id="img_icon_658"></i>
				                   		<a href="http://${list.domain}${list.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 " id="img_view_658">查看</a>
				                    	<span class="font-red cursor-pointer mt4 " onclick="delAttachment(this)" id="img_del_658">删除</span>
				                      </div>
			                      	</div>
	                    		</c:if>
	                    	</c:forEach>
                    	</c:if>
                    	<c:if test="${is_have_658 == 0}">
	                    	<div class="clear mt8"> 
		                        <div class='pos_rel f_left'>
		                        	<input type="file" class=" uploadErp"  name="lwfile" id="lwfile_658" onchange="uploadFile(this, 658)">
			                        <input value="" type="text" class="input-middle mr5" id="uri_658" placeholder="请上传商品的测试报告" name="uri_658">
			                        <label class="bt-bg-style bt-middle bg-light-blue" type="file" >浏览</label>
			                        <input type="hidden" id="domain_658" name="domain_658">
		                        </div>
		                        
		                      <div class="f_left">
		                        <i class="iconsuccesss mt5 none" id="img_icon_658"></i>
		                   		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_658">查看</a>
		                    	<span class="font-red cursor-pointer mt4 none" onclick="delAttachment(this)" id="img_del_658">删除</span>
		                      </div>
	                      	</div>
                      	</c:if>
                      	
                      	<div class="mt8 clear" id="conadd658">
                            <span class="bt-border-style bt-small border-blue mt8" onclick="con_add(658);">继续添加</span>
                        </div>
                        <input type="hidden" id="domain_658" name="domain_658" value="${domain_658}">
                        <div class="mt5">
                        1，检测报告原件扫描件<br>
                        2，格式：JPG、PNG、BMP等图片格式<br>
                        3，图片大小不超过5M<br>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>专利文件</label>
                    </div>

                    <div class="f_left ">
                    	<c:set var="is_have_659" value="0"></c:set>
                    	<c:set var="domain_659" value=""></c:set>
                    	<c:forEach items="${attachmentList}" var="list" varStatus="status">
	                    	<c:if test="${list.attachmentType == 659}">
			                    <c:set var="is_have_659" value="1"></c:set>
			                    <c:set var="domain_659" value="${list.domain}"></c:set>
                    		</c:if>
                    	</c:forEach>
                    	<c:if test="${is_have_659 == 1}">
                    		<c:forEach items="${attachmentList}" var="list" varStatus="status">	
		                    	<c:if test="${list.attachmentType == 659}">
									<div class="clear mt8"> 
				                        <div class='pos_rel f_left'>
				                        	<input type="file" class=" uploadErp"  name="lwfile" id="lwfile_659" onchange="uploadFile(this, 659)">
					                        <input value="${list.uri}" type="text" class="input-middle mr5" id="uri_659" placeholder="请上传商品的专利文件" name="uri_659">
					                        <label class="bt-bg-style bt-middle bg-light-blue" type="file" >浏览</label>
				                        </div>
				                        
				                      <div class="f_left">
				                        <i class="iconsuccesss mt5 " id="img_icon_659"></i>
				                   		<a href="http://${list.domain}${list.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 " id="img_view_659">查看</a>
				                    	<span class="font-red cursor-pointer mt4 " onclick="delAttachment(this)" id="img_del_659">删除</span>
				                      </div>
			                      	</div>
	                    		</c:if>
	                    	</c:forEach>
                    	</c:if>

                    	<c:if test="${is_have_659 == 0}">
	                    	<div class="clear mt8"> 
		                        <div class='pos_rel f_left'>
		                        	<input type="file" class=" uploadErp"  name="lwfile" id="lwfile_659" onchange="uploadFile(this, 659)">
			                        <input value="" type="text" class="input-middle mr5" id="uri_659" placeholder="请上传商品的专利文件" name="uri_659">
			                        <label class="bt-bg-style bt-middle bg-light-blue" type="file" >浏览</label>
			                        <input type="hidden" id="domain_659" name="domain_659">
		                        </div>
		                        
		                      <div class="f_left">
		                        <i class="iconsuccesss mt5 none" id="img_icon_659"></i>
		                   		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_659">查看</a>
		                    	<span class="font-red cursor-pointer mt4 none" onclick="delAttachment(this)" id="img_del_659">删除</span>
		                      </div>
	                      	</div>
                      	</c:if>
                      	
                      	<div class="mt8 clear" id="conadd659">
                            <span class="bt-border-style bt-small border-blue mt8" onclick="con_add(659);">继续添加</span>
                        </div>
                        <input type="hidden" id="domain_659" name="domain_659" value="${domain_659}">
                        <div class="mt5">
                        1，专利文件原件扫描件<br>
                        2，格式：JPG、PNG、BMP等图片格式<br>
                        3，格式：图片大小不超过5M<br>
                        </div>
                    </div>

                </li>
                <li>
                    <div class="infor_name">
                        <label>采购提醒</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-xxx" placeholder="指需要销售注意事项，如离心机配不同的转子；需要报单等一切需要让销售注意的事项" name="purchaseRemind" id="purchaseRemind" value="${goods.purchaseRemind}">
                    </div>
                </li>
                <li>
                    <div class="infor_name">
                        <label>服务条款</label>
                    </div>
                    <div class="f_left">
                        <input type="text" class="input-xxx" name="tos" id="tos" value="${goods.tos}">
                    </div>
                </li>
                <li>
                    <div class="infor_name mt0">
                        <label>应用科室</label>
                    </div>
                    <div class="f_left inputfloat">
                    </div>
                    <ul id="used_department_div">
                    </ul>
                    <input type="hidden" value="${goodsSysOptionAttributeIds}" id="goodsSysOptionAttributeIds">
                </li>
                <li>
                    <div class="infor_name mt0">
                        <label>7天无理由退换货</label>
                    </div>
                    <div class="f_left inputfloat"></div>
                    <ul id="isNoReasonReturn_div">
                        <input type="radio"  name="isNoReasonReturnGoods" value='0' <c:if test="${goods.isNoReasonReturn eq 0}">checked</c:if> />&nbsp;<label>否</label>
                        &nbsp;&nbsp;&nbsp;
                        <input type="radio"  name="isNoReasonReturnGoods" value='1' <c:if test="${goods.isNoReasonReturn eq 1}">checked</c:if> />&nbsp;<label>是</label>
                    </ul>
                    <input type="hidden" name="isNoReasonReturn" id="isNoReasonReturn" value="${goods.isNoReasonReturn}"/>
                </li>
            </ul>
            <div class="add-tijiao tcenter">
            	<input type="hidden" name="goodsId" id="goodsId" value="${goods.goodsId}">
            	<input type="hidden" name="beforeParams" value='${beforeParams}'>
            	<input type="hidden" id="domain" name="domain" value="${domain}">
                <button type="submit" id='submit'>提交</button>
                <button class="dele" id="close-layer" type="button" onclick="goUrl('${pageContext.request.contextPath}/goods/goods/viewbaseinfo.do?goodsId=${goods.goodsId}')">取消</button>
            </div>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>
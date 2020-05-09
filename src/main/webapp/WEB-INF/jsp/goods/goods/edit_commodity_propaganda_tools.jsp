<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑商品工具" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/add_afterSales_dsf.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/controlRadio.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/goods/goods/edit_commodity_propaganda_tools.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
<!-- 以下ueditor编辑器需要引用的文件 -->
<script type="text/javascript" charset="utf-8" src="<%= basePath %>static/libs/textmodify/ueditor.config.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" charset="utf-8" src="<%= basePath %>static/libs/textmodify/ueditor.all.min.js?rnd=<%=Math.random()%>"> </script>
<script type="text/javascript" charset="utf-8" src="<%= basePath %>static/js/file/edit_uedit.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" charset="utf-8" src="<%= basePath %>static/libs/textmodify/lang/zh-cn/zh-cn.js?rnd=<%=Math.random()%>"></script>
    <div class="form-list  form-tips7">
        <form method="post" action="<%= basePath %>/goods/goods/saveCommodityPropaganda.do" id="myform">
        	<input type="hidden" id="filePath" name="filePath"/>
            <input type="hidden" id="goodsExtendId" name="goodsExtendId" value="${goodsExtend.goodsExtendId }">
            <input type="hidden" id="goodsId" name="goodsId" value="${goods.goodsId }" >
            <ul>
                 <div style="font-weight:bold ">
                     <lable>商品宣传工具</lable>
                 </div><br>
             <li>
                    <div class="form-tips ">
                        <lable>宣传彩页</lable>
                    </div>
                   <input type="hidden" id="domain" name="domain" value="${domain}">
                   <c:if test="${!empty goodsExtend.getBrochureList()}">
	                    	 <div class="f_left ">
	                    	 <c:forEach items="${goodsExtend.getBrochureList()}" var="list" varStatus="num">
		                       <c:if test="${num.count==1}">
		                          <div class="form-blanks ">
		                        </c:if>
		                        <c:if test="${num.count!=1}">
		                           <div class="form-blanks mt10">
		                        </c:if>
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_1${num.count}' name="lwfile" onchange="uploadFile(this,1${num.count});" placeholder="请上传商品的宣传彩页">
			                            <input type="text" class="input-largest" id="name_1${num.count}" readonly="readonly"
			                            	 name="fileName11" onclick="file_1${num.count}.click();" value ="${list.name}"> 
			                            <input type="hidden" id="uri_1${num.count}" name="fileUri11"  value="${list.uri}">
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_1${num.count}').click();">浏览</label>
		                            <!-- 上传成功出现 -->
		                             <div class="f_left">
		                             <c:choose>
										<c:when test="${!empty list.uri}">
											<i class="iconsuccesss ml7" id="img_icon_1${num.count}"></i>
					                    	<a href="http://${list.domain}${list.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_1${num.count}">查看</a>
					                    	<span class="font-red cursor-pointer mt4" onclick="del(1${num.count},${num.count})" id="img_del_1${num.count}">删除</span>
										</c:when>
										<c:otherwise>
											<i class="iconsuccesss ml7 none" id="img_icon_1${num.count}"></i>
				                    		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_1${num.count}">查看</a>
					                    	<span class="font-red cursor-pointer mt4 none" onclick="del(1${num.count},${num.count})" id="img_del_1${num.count}">删除</span>
										</c:otherwise>
									</c:choose>
									 </div>
		                        </div>
		                         </c:forEach>
		                          <div class="mt8" id="conadd11">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add(11);">继续添加</span>
		                        </div>
		                        <div class="mt5">
		                        1，格式要求：图片或PDF格式<br>
		                        2，内容要求：清晰、排版合理、包含产品性能和参数<br>
		                        </div>
		                    </div>
                   </c:if>
                    <c:if test="${empty goodsExtend.getBrochureList()}">
	                    	 <div class="f_left ">
		                        <div class="form-blanks">
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_11' name="lwfile" onchange="uploadFile(this,11);">
			                            <input type="text" class="input-largest" id="name_11" readonly="readonly"
			                            	placeholder="请上传商品的宣传彩页" name="fileName11" onclick="file_11.click();" > 
			                            <input type="hidden" id="uri_11" name="fileUri11" >
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_11').click();">浏览</label>
		                            <!--  上传成功出现 -->
		                            <div class="f_left">
			                            <i class="iconsuccesss mt3 none" id="img_icon_11"></i>
			                    		<a href="" target="_blank" class="font-blue cursor-pointer  mt3 none" id="img_view_11">查看</a>
				                    	<span class="font-red cursor-pointer  mt3 none" onclick="del(11,1)" id="img_del_11">删除</span>
			                    	</div>
			                    	<div class='clear'></div>
		                        </div>
		                        <div class="mt8" id="conadd11">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add(11);">继续添加</span>
		                        </div>
		                        <div class="mt5">
		                        1，格式要求：图片或PDF格式<br>
		                        2，内容要求：清晰、排版合理、包含产品性能和参数<br>
		                        </div>
		                    </div>
                   </c:if>
                </li>
                 <li>
                    <div class="form-tips">
                        <lable>销售授权书</lable>
                    </div>
                    <c:if test="${!empty goodsExtend.getSalesEmpowermentList()}">
	                    	 <div class="f_left ">
	                    	 <c:forEach items="${goodsExtend.getSalesEmpowermentList()}" var="list" varStatus="num">
		                        <c:if test="${num.count==1}">
		                          <div class="form-blanks ">
		                        </c:if>
		                        <c:if test="${num.count!=1}">
		                           <div class="form-blanks mt10">
		                        </c:if>
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_2${num.count}' name="lwfile" onchange="uploadFile(this,2${num.count});">
			                            <input type="text" class="input-largest" id="name_2${num.count}" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName21" onclick="file_2${num.count}.click();" value ="${list.name}"> 
			                            <input type="hidden" id="uri_2${num.count}" name="fileUri21"  value="${list.uri}">
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_2${num.count}').click();">浏览</label>
		                            <!-- 上传成功出现 -->
		                             <div class="f_left">
		                             <c:choose>
										<c:when test="${!empty list.uri}">
											<i class="iconsuccesss ml7" id="img_icon_2${num.count}"></i>
					                    	<a href="http://${list.domain}${list.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_2${num.count}">查看</a>
					                    	<span class="font-red cursor-pointer mt4" onclick="del(2${num.count},${num.count})" id="img_del_2${num.count}">删除</span>
										</c:when>
										<c:otherwise>
											<i class="iconsuccesss ml7 none" id="img_icon_2${num.count}"></i>
				                    		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_2${num.count}">查看</a>
					                    	<span class="font-red cursor-pointer mt4 none" onclick="del(2${num.count},${num.count})" id="img_del_2${num.count}">删除</span>
										</c:otherwise>
									</c:choose>
									 </div>
		                        </div>
		                         </c:forEach>
		                          <div class="mt8" id="conadd21">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add(21);">继续添加</span>
		                        </div>
		                    </div>
                   </c:if>
                    <c:if test="${empty goodsExtend.getSalesEmpowermentList()}">
	                    	 <div class="f_left ">
		                        <div class="form-blanks">
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_21' name="lwfile" onchange="uploadFile(this,21);">
			                            <input type="text" class="input-largest" id="name_21" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName21" onclick="file_21.click();" > 
			                            <input type="hidden" id="uri_21" name="fileUri21" >
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_21').click();">浏览</label>
		                            <!--  上传成功出现 -->
		                            <div class="f_left">
			                            <i class="iconsuccesss mt3 none" id="img_icon_21"></i>
			                    		<a href="" target="_blank" class="font-blue cursor-pointer  mt3 none" id="img_view_21">查看</a>
				                    	<span class="font-red cursor-pointer  mt3 none" onclick="del(21,1)" id="img_del_21">删除</span>
			                    	</div>
			                    	<div class='clear'></div>
		                        </div>
		                        <div class="mt8" id="conadd21">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add(21);">继续添加</span>
		                        </div>
		                    </div>
                   </c:if>
                </li>
                 <li>
                 
                  <div class="form-tips">
                        <label>宣传视频</label>
                    </div>
                    <c:if test="${!empty goodsExtend.getPropagandistVideoList()}">
	                    	 <div class="f_left ">
	                    	 <c:forEach items="${goodsExtend.getPropagandistVideoList()}" var="list" varStatus="num">
		                        <c:if test="${num.count==1}">
		                          <div class="form-blanks ">
		                        </c:if>
		                        <c:if test="${num.count!=1}">
		                           <div class="form-blanks mt10">
		                        </c:if>
		                        	<div class="pos_rel f_left">
			                            <input type="text" class="input-largest" id="name_4${num.count}"  name="fileName41" value ="${list.uri}" placeholder="请填写链接地址"> 
			                            <input type="hidden" id="uri_4${num.count}" name="fileUri41"  value="${list.uri}">
					    			</div>
					    			 <div class="f_left">
				                    	<span class="font-red cursor-pointer  mt3" onclick="del_vedio(4${num.count},${num.count})" id="img_del_4${num.count}">删除</span>
			                    	</div>
			                    	<div class='clear'></div>
		                        </div>
		                      </c:forEach>
		                        <div class="mt8" id="conadd41">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add_vedio();">继续添加</span>
		                        </div>
		                        <div class="mt5">
		                        1，视频内容：禁止出现联系方式、二维码、网址等影响观看效果的内容<br>
		                        2，上传视频格式只支持MP4、WMV两种格式（需对视频格式进行验证）<br>
		                        3，上传视频大小不能超过200M（需对视频大小进行验证）<br>
		                        4，视频播放长度不可超过2分钟（需对视频播放时长进行验证）<br>
		                        5，建议使用4：3比例的视频（只做提示，不做强制规定）<br>
		                        6，“视频名称”为非必填，可后期进行修改。<br>
		                        </div>
		                    </div>
                   </c:if>
                    <c:if test="${empty goodsExtend.getPropagandistVideoList()}">
	                    	 <div class="f_left ">
		                        <div class="form-blanks">
		                        	<div class="pos_rel f_left">
			                            <input type="text" class="input-largest" id="name_41"  name="fileName41"  placeholder="请填写链接地址"> 
			                            <input type="hidden" id="uri_41" name="fileUri41" >
					    			</div>
		                            <div class="f_left">
				                    	<span class="font-red cursor-pointer  mt3 none" onclick="del_vedio(41,1)" id="img_del_41">删除</span>
			                    	</div><br/>
			                    	<div class='clear'></div>
		                        </div>
		                        <div class="mt8" id="conadd41">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add_vedio();">继续添加</span>
		                        </div>
		                        <div class="mt5">
		                        1，视频内容：禁止出现联系方式、二维码、网址等影响观看效果的内容<br>
		                        2，上传视频格式只支持MP4、WMV两种格式（需对视频格式进行验证）<br>
		                        3，上传视频大小不能超过200M（需对视频大小进行验证）<br>
		                        4，视频播放长度不可超过2分钟（需对视频播放时长进行验证）<br>
		                        5，建议使用4：3比例的视频（只做提示，不做强制规定）<br>
		                        6，“视频名称”为非必填，可后期进行修改。<br>
		                        </div>
		                    </div>
                   </c:if>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>客户名单</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <textarea class="input-largest" id="customerNames" placeholder="请填写医院或终端医疗机构名称，用'分号'隔开，例如：美年大健康；爱康国宾；" name="customerNames">${goodsExtend.customerNames} </textarea>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>其他资质</lable>
                    </div>
                    <c:if test="${!empty goodsExtend.getOtherQualificationsList()}">
	                    	 <div class="f_left ">
	                    	 <c:forEach items="${goodsExtend.getOtherQualificationsList()}" var="list" varStatus="num">
		                        <c:if test="${num.count==1}">
		                          <div class="form-blanks ">
		                        </c:if>
		                        <c:if test="${num.count!=1}">
		                           <div class="form-blanks mt10">
		                        </c:if>
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_5${num.count}' name="lwfile" onchange="uploadFile(this,5${num.count});">
			                            <input type="text" class="input-largest" id="name_5${num.count}" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName51" onclick="file_5${num.count}.click();" value ="${list.name}"> 
			                            <input type="hidden" id="uri_5${num.count}" name="fileUri51"  value="${list.uri}">
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_5${num.count}').click();">浏览</label>
		                            <!-- 上传成功出现 -->
		                             <div class="f_left">
		                             <c:choose>
										<c:when test="${!empty list.uri}">
											<i class="iconsuccesss ml7" id="img_icon_5${num.count}"></i>
					                    	<a href="http://${list.domain}${list.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_5${num.count}">查看</a>
					                    	<span class="font-red cursor-pointer mt4" onclick="del(5${num.count},${num.count})" id="img_del_5${num.count}">删除</span>
										</c:when>
										<c:otherwise>
											<i class="iconsuccesss ml7 none" id="img_icon_5${num.count}"></i>
				                    		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_5${num.count}">查看</a>
					                    	<span class="font-red cursor-pointer mt4 none" onclick="del(5${num.count},${num.count})" id="img_del_5${num.count}">删除</span>
										</c:otherwise>
									</c:choose>
									 </div>
		                        </div>
		                         </c:forEach>
		                          <div class="mt8" id="conadd51">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add(51);">继续添加</span>
		                        </div>
		                    </div>
                   </c:if>
                    <c:if test="${empty goodsExtend.getOtherQualificationsList()}">
	                    	 <div class="f_left ">
		                        <div class="form-blanks">
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_51' name="lwfile" onchange="uploadFile(this,51);">
			                            <input type="text" class="input-largest" id="name_51" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName51" onclick="file_51.click();" > 
			                            <input type="hidden" id="uri_51" name="fileUri51" >
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_51').click();">浏览</label>
		                            <!--  上传成功出现 -->
		                            <div class="f_left">
			                            <i class="iconsuccesss mt3 none" id="img_icon_51"></i>
			                    		<a href="" target="_blank" class="font-blue cursor-pointer  mt3 none" id="img_view_51">查看</a>
				                    	<span class="font-red cursor-pointer  mt3 none" onclick="del(51,1)" id="img_del_51">删除</span>
			                    	</div>
			                    	<div class='clear'></div>
		                        </div>
		                        <div class="mt8" id="conadd51">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add(51);">继续添加</span>
		                        </div>
		                    </div>
                   </c:if>
                </li>
               
               <li>
                    <div class="form-tips">
                        <lable>技术参数</lable>
                    </div>
                    <c:if test="${!empty goodsExtend.getTechnicalParameterList()}">
	                    	 <div class="f_left ">
	                    	 <c:forEach items="${goodsExtend.getTechnicalParameterList()}" var="list" varStatus="num">
		                        <c:if test="${num.count==1}">
		                          <div class="form-blanks ">
		                        </c:if>
		                        <c:if test="${num.count!=1}">
		                           <div class="form-blanks mt10">
		                        </c:if>
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_6${num.count}' name="lwfile" onchange="uploadFile(this,6${num.count});">
			                            <input type="text" class="input-largest" id="name_6${num.count}" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName61" onclick="file_6${num.count}.click();" value ="${list.name}"> 
			                            <input type="hidden" id="uri_6${num.count}" name="fileUri61"  value="${list.uri}">
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_6${num.count}').click();">浏览</label>
		                            <!-- 上传成功出现 -->
		                             <div class="f_left">
		                             <c:choose>
										<c:when test="${!empty list.uri}">
											<i class="iconsuccesss ml7" id="img_icon_6${num.count}"></i>
					                    	<a href="http://${list.domain}${list.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_6${num.count}">查看</a>
					                    	<span class="font-red cursor-pointer mt4" onclick="del(6${num.count},${num.count})" id="img_del_6${num.count}">删除</span>
										</c:when>
										<c:otherwise>
											<i class="iconsuccesss ml7 none" id="img_icon_6${num.count}"></i>
				                    		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_6${num.count}">查看</a>
					                    	<span class="font-red cursor-pointer mt4 none" onclick="del(6${num.count},${num.count})" id="img_del_6${num.count}">删除</span>
										</c:otherwise>
									</c:choose>
									 </div>
		                        </div>
		                         </c:forEach>
		                          <div class="mt8" id="conadd61">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add(61);">继续添加</span>
		                        </div>
		                    </div>
                   </c:if>
                    <c:if test="${empty goodsExtend.getTechnicalParameterList()}">
	                    	 <div class="f_left ">
		                        <div class="form-blanks">
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_61' name="lwfile" onchange="uploadFile(this,61);">
			                            <input type="text" class="input-largest" id="name_61" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName61" onclick="file_61.click();" > 
			                            <input type="hidden" id="uri_61" name="fileUri61" >
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_61').click();">浏览</label>
		                            <!--  上传成功出现 -->
		                            <div class="f_left">
			                            <i class="iconsuccesss mt3 none" id="img_icon_61"></i>
			                    		<a href="" target="_blank" class="font-blue cursor-pointer  mt3 none" id="img_view_61">查看</a>
				                    	<span class="font-red cursor-pointer  mt3 none" onclick="del(61,1)" id="img_del_61">删除</span>
			                    	</div>
			                    	<div class='clear'></div>
		                        </div>
		                        <div class="mt8" id="conadd61">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add(61);">继续添加</span>
		                        </div>
		                    </div>
                   </c:if>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>商品说明书</lable>
                    </div>
                    <c:if test="${!empty goodsExtend.getCommodityInstructionsList()}">
	                    	 <div class="f_left ">
	                    	 <c:forEach items="${goodsExtend.getCommodityInstructionsList()}" var="list" varStatus="num">
		                        <c:if test="${num.count==1}">
		                          <div class="form-blanks ">
		                        </c:if>
		                        <c:if test="${num.count!=1}">
		                           <div class="form-blanks mt10">
		                        </c:if>
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_7${num.count}' name="lwfile" onchange="uploadFile(this,7${num.count});">
			                            <input type="text" class="input-largest" id="name_7${num.count}" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName71" onclick="file_7${num.count}.click();" value ="${list.name}"> 
			                            <input type="hidden" id="uri_7${num.count}" name="fileUri71"  value="${list.uri}">
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_7${num.count}').click();">浏览</label>
		                            <!-- 上传成功出现 -->
		                             <div class="f_left">
		                             <c:choose>
										<c:when test="${!empty list.uri}">
											<i class="iconsuccesss ml7" id="img_icon_7${num.count}"></i>
					                    	<a href="http://${list.domain}${list.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_7${num.count}">查看</a>
					                    	<span class="font-red cursor-pointer mt4" onclick="del(7${num.count},${num.count})" id="img_del_7${num.count}">删除</span>
										</c:when>
										<c:otherwise>
											<i class="iconsuccesss ml7 none" id="img_icon_7${num.count}"></i>
				                    		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_7${num.count}">查看</a>
					                    	<span class="font-red cursor-pointer mt4 none" onclick="del(7${num.count},${num.count})" id="img_del_7${num.count}">删除</span>
										</c:otherwise>
									</c:choose>
									 </div>
		                        </div>
		                         </c:forEach>
		                          <div class="mt8" id="conadd71">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add(71);">继续添加</span>
		                        </div>
		                    </div>
                   </c:if>
                    <c:if test="${empty goodsExtend.getCommodityInstructionsList()}">
	                    	 <div class="f_left ">
		                        <div class="form-blanks">
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_71' name="lwfile" onchange="uploadFile(this,71);">
			                            <input type="text" class="input-largest" id="name_71" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName71" onclick="file_71.click();" > 
			                            <input type="hidden" id="uri_71" name="fileUri71" >
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_71').click();">浏览</label>
		                            <!--  上传成功出现 -->
		                            <div class="f_left">
			                            <i class="iconsuccesss mt3 none" id="img_icon_71"></i>
			                    		<a href="" target="_blank" class="font-blue cursor-pointer  mt3 none" id="img_view_71">查看</a>
				                    	<span class="font-red cursor-pointer  mt3 none" onclick="del(71,1)" id="img_del_71">删除</span>
			                    	</div>
			                    	<div class='clear'></div>
		                        </div>
		                        <div class="mt8" id="conadd71">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add(71);">继续添加</span>
		                        </div>
		                    </div>
                   </c:if>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>陪标资料</lable>
                    </div>
                    <c:if test="${!empty goodsExtend.getStandardDataList()}">
	                    	 <div class="f_left ">
	                    	 <c:forEach items="${goodsExtend.getStandardDataList()}" var="list" varStatus="num">
		                        <c:if test="${num.count==1}">
		                          <div class="form-blanks ">
		                        </c:if>
		                        <c:if test="${num.count!=1}">
		                           <div class="form-blanks mt10">
		                        </c:if>
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_8${num.count}' name="lwfile" onchange="uploadFile(this,8${num.count});">
			                            <input type="text" class="input-largest" id="name_8${num.count}" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName81" onclick="file_8${num.count}.click();" value ="${list.name}"> 
			                            <input type="hidden" id="uri_8${num.count}" name="fileUri81"  value="${list.uri}">
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_8${num.count}').click();">浏览</label>
		                            <!-- 上传成功出现 -->
		                             <div class="f_left">
		                             <c:choose>
										<c:when test="${!empty list.uri}">
											<i class="iconsuccesss ml7" id="img_icon_8${num.count}"></i>
					                    	<a href="http://${list.domain}${list.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_8${num.count}">查看</a>
					                    	<span class="font-red cursor-pointer mt4" onclick="del(8${num.count},${num.count})" id="img_del_8${num.count}">删除</span>
										</c:when>
										<c:otherwise>
											<i class="iconsuccesss ml7 none" id="img_icon_7${num.count}"></i>
				                    		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_8${num.count}">查看</a>
					                    	<span class="font-red cursor-pointer mt4 none" onclick="del(8${num.count},${num.count})" id="img_del_8${num.count}">删除</span>
										</c:otherwise>
									</c:choose>
									 </div>
		                        </div>
		                         </c:forEach>
		                          <div class="mt8" id="conadd81">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add(81);">继续添加</span>
		                        </div>
		                    </div>
                   </c:if>
                    <c:if test="${empty goodsExtend.getStandardDataList()}">
	                    	 <div class="f_left ">
		                        <div class="form-blanks">
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_81' name="lwfile" onchange="uploadFile(this,81);">
			                            <input type="text" class="input-largest" id="name_81" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName81" onclick="file_81.click();" > 
			                            <input type="hidden" id="uri_81" name="fileUri81" >
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_81').click();">浏览</label>
		                            <!--  上传成功出现 -->
		                            <div class="f_left">
			                            <i class="iconsuccesss mt3 none" id="img_icon_81"></i>
			                    		<a href="" target="_blank" class="font-blue cursor-pointer  mt3 none" id="img_view_81">查看</a>
				                    	<span class="font-red cursor-pointer  mt3 none" onclick="del(81,1)" id="img_del_81">删除</span>
			                    	</div>
			                    	<div class='clear'></div>
		                        </div>
		                        <div class="mt8" id="conadd81">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add(81);">继续添加</span>
		                        </div>
		                    </div>
                   </c:if>
                </li>
                 <div style="font-weight:bold ">
                     <lable>商品培训工具</lable>
                 </div><br>
                 <li>
                    <div class="form-tips">
                        <lable>销售话术</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <textarea placeholder="请概括产品优势（200字以内）" class="input-largest" id="sellingWords" name="sellingWords">${ goodsExtend.sellingWords}</textarea>
                        </div>
                    </div>
                </li>
                 <li>
                    <div class="form-tips">
                        <lable>培训资料</lable>
                    </div>
                    <c:if test="${!empty goodsExtend.getTrainingMaterialsList()}">
	                    	 <div class="f_left ">
	                    	 <c:forEach items="${goodsExtend.getTrainingMaterialsList()}" var="list" varStatus="num">
		                       <c:if test="${num.count==1}">
		                          <div class="form-blanks ">
		                        </c:if>
		                        <c:if test="${num.count!=1}">
		                           <div class="form-blanks mt10">
		                        </c:if>
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_3${num.count}' name="lwfile" onchange="uploadFile(this,3${num.count});">
			                            <input type="text" class="input-largest" id="name_3${num.count}" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName31" onclick="file_3${num.count}.click();" value ="${list.name}"> 
			                            <input type="hidden" id="uri_3${num.count}" name="fileUri31"  value="${list.uri}">
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_3${num.count}').click();">浏览</label>
		                            <!-- 上传成功出现 -->
		                             <div class="f_left">
		                             <c:choose>
										<c:when test="${!empty list.uri}">
											<i class="iconsuccesss ml7" id="img_icon_3${num.count}"></i>
					                    	<a href="http://${list.domain}${list.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_3${num.count}">查看</a>
					                    	<span class="font-red cursor-pointer mt4" onclick="del(3${num.count},${num.count})" id="img_del_3${num.count}">删除</span>
										</c:when>
										<c:otherwise>
											<i class="iconsuccesss ml7 none" id="img_icon_3${num.count}"></i>
				                    		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_3${num.count}">查看</a>
					                    	<span class="font-red cursor-pointer mt4 none" onclick="del(3${num.count},${num.count})" id="img_del_3${num.count}">删除</span>
										</c:otherwise>
									</c:choose>
									 </div>
		                        </div>
		                         </c:forEach>
		                          <div class="mt8" id="conadd31">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add(31);">继续添加</span>
		                        </div>
		                        <div class="mt5">
		                        1，格式要求：图片或PDF格式<br>
		                        2，资料包含：可以上传培训PPT、选型指南、培训视频、清单要求<br>
		                        3，内容要求：清晰、排版合理、包含产品性能和参数<br>
		                        </div>
		                    </div>
                   </c:if>
                    <c:if test="${empty goodsExtend.getTrainingMaterialsList()}">
	                    	 <div class="f_left ">
		                        <div class="form-blanks">
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_31' name="lwfile" onchange="uploadFile(this,31);">
			                            <input type="text" class="input-largest" id="name_31" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName31" onclick="file_31.click();" > 
			                            <input type="hidden" id="uri_31" name="fileUri31" >
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_31').click();">浏览</label>
		                            <!--  上传成功出现 -->
		                            <div class="f_left">
			                            <i class="iconsuccesss mt3 none" id="img_icon_31"></i>
			                    		<a href="" target="_blank" class="font-blue cursor-pointer  mt3 none" id="img_view_31">查看</a>
				                    	<span class="font-red cursor-pointer  mt3 none" onclick="del(31,1)" id="img_del_31">删除</span>
			                    	</div>
			                    	<div class='clear'></div>
		                        </div>
		                        <div class="mt8" id="conadd31">
		                            <span class="bt-border-style bt-small border-blue" onclick="con_add(31);">继续添加</span>
		                        </div>
		                        <div class="mt5">
		                        1，格式要求：图片或PDF格式<br>
		                        2，资料包含：可以上传培训PPT、选型指南、培训视频、清单要求<br>
		                        3，内容要求：清晰、排版合理、包含产品性能和参数<br>
		                        </div>
		                    </div>
                   </c:if>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>市场策略</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <textarea class="input-largest" placeholder="描述主要客户群、应用场景、价格竞争策略等（500字以内）" id="marketStrategy" name="marketStrategy">${ goodsExtend.marketStrategy}</textarea>
                        </div>
                    </div>
                </li>
                 <li>
                    <div class="form-tips">
                        <lable>促销政策</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <textarea class="input-largest" placeholder="可选填" id="promotionPolicy" name="promotionPolicy">${ goodsExtend.promotionPolicy}</textarea>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>竞品分析</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <%-- <textarea class="input-largest" name="competitiveAnalysis">${ goodsExtend.competitiveAnalysis}</textarea> --%>
                             <script id="editor" type="text/plain" name="competitiveAnalysis" style="width:800px;height:500px;">${ goodsExtend.competitiveAnalysis}</script>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>产品亮点</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <textarea class="input-largest" placeholder="产品优点和缺点（支持1000个字符）" id="advantage" name="advantage">${ goodsExtend.advantage}</textarea>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>选型辅助</lable>
                    </div>
                    <c:if test="${!empty goodsExtend.assistantAttachment}">
	                    <div class="f_left ">
	                    	<div class="form-blanks ">
                        	<div class="pos_rel f_left">
	                            <input type="file" class="uploadErp" id='file_9' name="lwfile" onchange="uploadFile(this,9);">
	                            <input type="text" class="input-largest" id="name_9" readonly="readonly"
	                            	placeholder="请上传附件" name="fileName9" onclick="file_9.click();" value ="${goodsExtend.assistantAttachment.name}"> 
	                            <input type="hidden" id="uri_9" name="fileUri9"  value="${goodsExtend.assistantAttachment.uri}">
			    			</div>
                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_9').click();">浏览</label>
                            <!-- 上传成功出现 -->
                             <div class="f_left">
	                             <c:choose>
									<c:when test="${!empty goodsExtend.assistantAttachment.uri}">
										<i class="iconsuccesss ml7" id="img_icon_9"></i>
				                    	<a href="http://${goodsExtend.assistantAttachment.domain}${goodsExtend.assistantAttachment.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_9">查看</a>
				                    	<span class="font-red cursor-pointer mt4" onclick="del(9,1)" id="img_del_9">删除</span>
									</c:when>
									<c:otherwise>
										<i class="iconsuccesss ml7 none" id="img_icon_9"></i>
			                    		<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_9">查看</a>
				                    	<span class="font-red cursor-pointer mt4 none" onclick="del(9,1)" id="img_del_9">删除</span>
									</c:otherwise>
								</c:choose>
							 </div>
							 </div>
		                 </div>   
                    </c:if>
                    <c:if test="${empty goodsExtend.assistantAttachment}">
	                    	 <div class="f_left ">
		                        <div class="form-blanks">
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_9' name="lwfile" onchange="uploadFile(this,9);">
			                            <input type="text" class="input-largest" id="name_9" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName9" onclick="file_9.click();" > 
			                            <input type="hidden" id="uri_9" name="fileUri9" >
					    			</div>
		                            <label class="bt-bg-style bt-small bg-light-blue" type="file" id="busUpload" onclick="return $('#file_9').click();">浏览</label>
		                            <!--  上传成功出现 -->
		                            <div class="f_left">
			                            <i class="iconsuccesss mt3 none" id="img_icon_9"></i>
			                    		<a href="" target="_blank" class="font-blue cursor-pointer  mt3 none" id="img_view_9">查看</a>
				                    	<span class="font-red cursor-pointer  mt3 none" onclick="del(9,1)" id="img_del_9">删除</span>
			                    	</div>
			                    	<div class='clear'></div>
		                        </div>
		                    </div>
                   </c:if>
                </li>
            </ul>
             <div class="add-tijiao ">
                  <button type="submit">提交</button>
                  <button class="dele" id="close-layer" type="button" onclick="goUrl('${pageContext.request.contextPath}/goods/goods/viewsaleinfo.do?goodsId=${goods.goodsId}')">取消</button>
              </div>
        </form>
    </div>
<!-- 以下为UEditor插件要引用的文件 -->
<script type="text/javascript">
	UE.getEditor('editor');
	UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	UE.Editor.prototype.getActionUrl = function(action) {
		if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage' || action == 'uploadfile' || action == 'uploadvideo') {//上传附件类型
			return '${path}fileUpload/ueditFileUpload.do?uploadType=uedit';//自定义编辑器中附件上传路径
		} else {
			return this._bkGetActionUrl.call(this, action);//插件默认上传
		}
	}
</script>
<%@ include file="../../common/footer.jsp"%>
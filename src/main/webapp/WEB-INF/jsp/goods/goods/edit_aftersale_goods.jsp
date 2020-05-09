<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑售后商品" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/add_afterSales_dsf.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/controlRadio.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%= basePath %>static/js/goods/goods/edit_aftersale_goods.js?rnd=<%=Math.random()%>"></script>
<script type="text/javascript" src="<%=basePath%>/static/js/region/index.js?rnd=<%=Math.random()%>"></script>
    <div class="form-list  form-tips7">
        <form method="post" action="<%= basePath %>/goods/goods/saveCommodityPropaganda.do" id="search">
            <input type="hidden" id="goodsExtendId" name="goodsExtendId" value="${goodsExtend.goodsExtendId }">
            <input type="hidden" id="goodsId" name="goodsId" value="${goods.goodsId }" >
            <ul>
                 <div style="font-weight:bold ">
                     <lable>标准售后商品</lable>
                 </div><br>
                  <li>
                    <div class="form-tips">
                        <lable>售后内容</lable>
                    </div>
                    <div class="f_left ">
                         <c:forEach items="${goodsExtend.getSysList()}" var="list" varStatus="status">
	                    	<c:choose>
								<c:when test="${list.attributeId eq 655}">
									<input type="hidden" name="655" value="1">
								</c:when>
								<c:when test="${list.attributeId eq 656}">
									<input type="hidden" name="656" value="1">
								</c:when>
								<c:when test="${list.attributeId eq 657}">
									<input type="hidden" name="657" value="1">
								</c:when>
							</c:choose>
	                      </c:forEach>
                        <input type="checkbox" id="655" name="check" value="655" />包安装
                        <input type="checkbox" id="656" name="check" value="656" />包运输
                        <input type="checkbox" id="657" name="check" value="657" />包培训
                        <input type="hidden" id="aftersaleContent" name="aftersaleContent" value="" />
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <label>质保年限</label>
                    </div>
                    <div class="f_left">
                       <span id="zb_nx"> <input type="text" class="input-largest" onkeyup="this.value=this.value.replace(/[^\d]/g,'') " onafterpaste="this.value=this.value.replace(/[^\d]/g,'') " name="warrantyPeriod" id="warrantyPeriod" value="${goodsExtend.warrantyPeriod}">年</span>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <lable>质保期限规则</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <textarea class="input-largest" id="warrantyPeriodRule" name="warrantyPeriodRule">${ goodsExtend.warrantyPeriodRule}</textarea>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <label>保外维修</label>
                    </div>
                    <div class="f_left">
                        <span id="bw_wx"><input type="text" placeholder="质保外的维修价格" class="input-largest"  name="warrantyRepairFee" id="warrantyRepairFee" value="${goodsExtend.warrantyRepairFee}">元</span>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <label>响应时间</label>
                    </div>
                    <div class="f_left">
                       <span id="xy_sj"> <input type="text" class="input-largest"  name="responseTime" id="responseTime" 
                       <%--  value="<date:date value ="${goodsExtend.responseTime} format="yyyy-MM-dd HH:mm:ss"" --%>value="${goodsExtend.responseTime}" />小时</span>
                    </div>
                </li>
                <li>
                    <div class="form-tips">
                        <label>是否有备用机</label>
                    </div>
                    <div class="f_left">
                       <input id="haveStandbyMachine" name="haveStandbyMachine" value="${goodsExtend.haveStandbyMachine}" style="display: none;">
                       <input type="radio" id="isby" value="1" />是
                       <input type="radio" id="noby" value="0" />否
                       <div class="form-blanks mt10" id="isbyj">
                         <textarea class="input-largest" placeholder="请输入使用条件" id="conditions" name="conditions">${ goodsExtend.conditions}</textarea>
                       </div>
                    </div>
                    
                </li>
                <li>
                    <div class="form-tips">
                        <label>供应商延保价格</label>
                    </div>
                    <div class="f_left">
                        <span id="yb_jg"><input type="text" class="input-largest"  name="extendedWarrantyFee" id="extendedWarrantyFee" value="${goodsExtend.extendedWarrantyFee}">元/年</span>
                    </div>
                </li>
             <li>
                    <div class="form-tips">
                        <lable>核心零部件价格</lable>
                    </div>
                   <input type="hidden" id="domain" name="domain" value="${domain}">
                   <c:if test="${!empty goodsExtend.getSparePartsPriceList()}">
	                    	 <div class="f_left ">
	                    	 <c:forEach items="${goodsExtend.getSparePartsPriceList()}" var="list" varStatus="num">
		                         <c:if test="${num.count==1}">
		                          <div class="form-blanks ">
		                        </c:if>
		                        <c:if test="${num.count!=1}">
		                           <div class="form-blanks mt10">
		                        </c:if>
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_1${num.count}' name="lwfile" onchange="uploadFile(this,1${num.count});">
			                            <input type="text" class="input-largest" id="name_1${num.count}" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName11" onclick="file_1${num.count}.click();" value ="${list.name}"> 
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
		                    </div>
                   </c:if>
                    <c:if test="${empty goodsExtend.getSparePartsPriceList()}">
	                    	 <div class="f_left ">
		                        <div class="form-blanks">
		                        	<div class="pos_rel f_left">
			                            <input type="file" class="uploadErp" id='file_11' name="lwfile" onchange="uploadFile(this,11);">
			                            <input type="text" class="input-largest" id="name_11" readonly="readonly"
			                            	placeholder="请上传附件" name="fileName11" onclick="file_11.click();" > 
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
		                    </div>
                   </c:if>
                </li>
               
               
                 <div style="font-weight:bold ">
                     <lable>非标售后商品</lable>
                 </div><br>
                 <li>
                    <div class="form-tips">
                        <label>退货条件</label>
                    </div>
                    <div class="f_left">
                       <input id="isRefund" name="isRefund" value="${goodsExtend.isRefund}" style="display: none;">
                       <input type="radio" id="isth" value="1"  />允许退货
                       <input type="radio" id="noth" value="0" />不允许退货
                    </div>
                </li>
                <li id="hhtj">
                    <div class="form-tips">
                        <lable>换货条件</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <textarea class="input-largest" id="exchangeConditions" name="exchangeConditions">${ goodsExtend.exchangeConditions}</textarea>
                        </div>
                    </div>
                </li>
                <li id="hhfs">
                    <div class="form-tips">
                        <lable>换货方式</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <textarea class="input-largest" id="exchangeMode" name="exchangeMode">${ goodsExtend.exchangeMode}</textarea>
                        </div>
                    </div>
                </li>
                 <li id="yfsm">
                    <div class="form-tips">
                        <lable>运费说明</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <textarea class="input-largest" placeholder="请说明由哪方承担运费" id="freightDescription" name="freightDescription">${ goodsExtend.freightDescription}</textarea>
                        </div>
                    </div>
                </li>
            </ul>
             <div class="add-tijiao ">
                  <span class="confSearch bt-small bt-bg-style bg-light-green" onclick="search();" >提交</span>
                  <button class="dele" id="close-layer" type="button" onclick="goUrl('${pageContext.request.contextPath}/goods/goods/viewsaleinfo.do?goodsId=${goods.goodsId}')">取消</button>
              </div>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑售后-技术咨询" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/add_afterSales_dsf.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/controlRadio.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>
<div class="form-list  form-tips5">
    <form method="post" action="<%= basePath %>/aftersales/order/saveEditAfterSales.do">
        <ul>
            <li>
                <div class="form-tips">
                    <lable>售后类型</lable>
                </div>
                <div class="f_left">
                    <div class="form-blanks">
                        <ul class="aftersales">
                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="550" readonly="readonly">
                                    <label>安调</label>
                                </a>
                            </li>
                             <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="585" readonly="readonly" >
                                    <label>维修</label>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="551" readonly="readonly">
                                    <label>退款</label>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="552" readonly="readonly" checked="true">
                                    <label>技术咨询</label>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0);">
                                    <input type="radio" name="type" value="553" readonly="readonly">
                                    <label>其他</label>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
            <input type="hidden" id="shtype" value="jz" />
            <input type="hidden" name="afterSalesId" value="${afterSales.afterSalesId}" />
            <input type="hidden" name="afterSalesDetailId" value="${afterSales.afterSalesDetailId}" />
            <input type="hidden" name="subjectType" value="${afterSales.subjectType}" />
            <input type="hidden" name="type" value="${afterSales.type}" />
            <input type="hidden" name="beforeParams" value='${beforeParams}'/>
            <li>
               <div class="form-tips">
                   <span>*</span>
                   <lable>详情说明</lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks ">
                       <textarea name="comments" id="comments" placeholder="请详述客户技术咨询内容以便售后同事联系" rows="5" class="wid90">${afterSales.comments}</textarea>
                   </div>
                   <div id="commentsError"></div>
               </div>
            </li>
            <li>
               <div class="form-tips">
                   <span>*</span>
                   <lable>联系人姓名</lable>
               </div>
               <div class="f_left ">
                   <div class="form-blanks ">
                       <input type="text" name="traderContactName" id="traderContactName" value="${afterSales.traderContactName}"/>
                   </div>
                   <div id="traderContactNameError"></div>
               </div>
            </li>
                <li>
                    <div class="form-tips">
                        <span>*</span>
                        <lable>联系人手机</lable>
                    </div>
                    <div class="f_left ">
                        <div class="form-blanks">
                            <input type="text" name="traderContactMobile" id="traderContactMobile" value="${afterSales.traderContactMobile}" />
                        </div>
                        <div id="traderContactMobileError"></div>
                         <div class="add-tijiao text-left mt15">
                                <button type="submit" id="submit">提交</button>
                         </div>
                    </div>
                </li>
        	</ul>
        </form>
    </div>
<%@ include file="../../common/footer.jsp"%>
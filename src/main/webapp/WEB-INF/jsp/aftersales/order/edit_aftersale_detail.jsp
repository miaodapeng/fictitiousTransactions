<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="编辑售后申请" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%=basePath%>/static/js/aftersales/order/edit_afterSales_detail.js?rnd=<%=Math.random()%>'></script>

<div class="form-list form-tips8">
    <form method="post" id="myformChild" >
        <ul>
            <li id="li0">
                <div class="form-tips">
                    <span>*</span>
                    <lable>交易方式</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
                            <li>
                                <input type="radio" name="traderMode" <c:if test="${afterSales.traderMode eq 521}">checked="checked"</c:if> value="521">
                                <label>银行</label>
                            </li>
                            <li>
                                <input type="radio" name="traderMode" <c:if test="${afterSales.traderMode eq 520}">checked="checked"</c:if> value="520">
                                <label>支付宝</label>
                            </li>
                        </ul>
                    </div>
                    <div id="traderModeError" ></div>
                </div>
            </li>
            <li id="li1">
                <div class="form-tips">
                    <span>*</span>
                    <lable>交易主体</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <ul>
                            <li>
                                <input type="radio" name="traderSubject" <c:if test="${afterSales.traderSubject eq 1}">checked="checked"</c:if> value="1">
                                <label>对公</label>
                            </li>
                            <li>
                                <input type="radio" name="traderSubject" <c:if test="${afterSales.traderSubject eq 2}">checked="checked"</c:if> value="2">
                                <label>对私</label>
                            </li>
                        </ul>
                    </div>
                    <div id="traderSubjectError" ></div>
                </div>
            </li>
            <li  id="li2">
                <div class="form-tips">
                    <span>*</span>
                    <lable>收款名称</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" name="payee" id="payee"  class="input-largest" value="${afterSales.payee}"/>
                        <input type="hidden"  id="payee1"  value="${afterSales.payee}"/>
                    </div>
                    <div id="payeeError"></div>
                </div>
            </li>
            <li  id="li3">
                <div class="form-tips">
                    <span>*</span>
                    <lable>开户银行</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" name="bank" id="bank"  class="input-largest" value="${afterSales.bank}"/>
                        <input type="hidden"  id="bank1"  value="${afterSales.bank}"/>
                    </div>
                    <div id="bankError"></div>
                </div>
            </li>
            <li id="li4">
                <div class="form-tips">
                    <span>*</span>
                    <lable>银行/支付宝账号 </lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" name="bankAccount" id="bankAccount"  class="input-largest" value="${afterSales.bankAccount}"/>
                        <input type="hidden" id="bankAccount1" value="${afterSales.bankAccount}"/>
                    </div>
                    <div id="bankAccountError"></div>
                </div>
            </li>
            <li  id="li5">
                <div class="form-tips">
                    <lable>开户行支付联行号</lable>
                </div>
                <div class="f_left ">
                    <div class="form-blanks">
                        <input type="text" name="bankCode" id="bankCode" value="${afterSales.bankCode}" class="input-largest"/>
                        <input type="hidden" id="bankCode1" value="${afterSales.bankCode}" />
                    </div>
                    <div id="bankCodeError"></div>
                </div>
            </li>

        </ul>
        <div class="add-tijiao tcenter">
           <%-- <input type="hidden" name="beforeParams" value='${beforeParams}'/>--%>
            <input type="hidden" name="afterSalesDetailId" value="${afterSales.afterSalesDetailId}" />
            <button type="submit" id="sub">提交</button>
            <button type="button" class="dele" onclick="closeLayer()">取消</button>
        </div>
    </form>
</div>
<%@ include file="../../common/footer.jsp"%>
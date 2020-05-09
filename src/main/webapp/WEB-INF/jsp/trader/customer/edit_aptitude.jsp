<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="title" value="编辑资质" scope="application"/>
<%@ include file="../../common/common.jsp" %>

<script type="text/javascript" src="<%= basePath %>static/js/jquery/ajaxfileupload.js?rnd=<%=Math.random()%>"></script>

<style>
    .add-detail .border-blue {
        color: #3384ef;
    }

    .add-detail .border-blue:hover {
        color: #fff;
    }
</style>
<div class="addElement">
    <div class="add-main">
        <form action="<%= basePath %>/trader/customer/saveAptitude.do" method="post" id="myform">
            <input type="hidden" name="domain" value="${domain}" id="domain">
            <ul class="add-detail add-detail1">
                <c:if test="${traderCustomer.customerNature eq 466}">
                    <li class="table-large">
                        <div class="infor_name" style="width: 150px">
                            <label>是否为营利机构</label>
                        </div>
                        <div class="f_left inputfloat mt3">
                            <input type="radio" name="isProfit" id="profit" value="0"
                                   <c:if test="${traderCustomer.isProfit ne 1}">checked="checked"</c:if> />
                            <label class="mr8">是</label>
                            <input type="radio" name="isProfit" id="profit0" value="1"
                                   <c:if test="${traderCustomer.isProfit eq 1}">checked="checked"</c:if>/>
                            <label>否</label>
                        </div>
                    </li>
                </c:if>
                <li class="table-large">
                    <div class="infor_name" style="width: 150px">
                        <label>三证合一</label>
                    </div>
                    <div class="f_left inputfloat mt3">
                        <c:forEach items="${bussinessList }" var="bus" varStatus="st">
                            <c:if test="${st.index == 0}">
                                <c:set var="threeInOne" value="${bus.threeInOne}"></c:set>
                            </c:if>
                        </c:forEach>
                        <input type="radio" name="threeInOne" id="one" value="1" onclick="thOne();"
                               <c:if test="${threeInOne eq 1}">checked="checked"</c:if> />
                        <label class="mr8">是</label>
                        <input type="radio" name="threeInOne" id="zero" value="0" onclick="thZero();"
                               <c:if test="${threeInOne ne 1}">checked="checked"</c:if>/>
                        <label>否</label>
                    </div>
                </li>
                <li>
                    <div class="infor_name sex_name" style="width: 150px">
                        <label>营业执照</label>
                    </div>
                    <div class="f_left insertli insertli1">
                        <ul>
                            <%--<li>--%>
                            <%--<div class="f_left">--%>
                            <%--<input type="file" class="upload_file" id='file_1' name="lwfile" style="display: none;" onchange="uploadFile(this,1);">--%>
                            <%--<input type="text" class="input-middle" id="name_1" readonly="readonly"--%>
                            <%--placeholder="请上传营业执照" name="busName" onclick="file_1.click();" value ="${business.name}"> --%>
                            <%--<input type="hidden" id="uri_1" name="busUri" value="${business.uri}">--%>
                            <%--<div class="font-red " style="display: none;">请上传营业执照</div>--%>
                            <%--<label class="bt-bg-style bt-middle bg-light-blue ml10" id="busUpload" onclick="return $('#file_1').click();">浏览</label>--%>
                            <%--</div>--%>
                            <%--<!-- 上传成功出现 -->--%>
                            <%--<c:choose>--%>
                            <%--<c:when test="${!empty business.uri}">--%>
                            <%--<i class="iconsuccesss ml7" id="img_icon_1"></i>--%>
                            <%--<a href="http://${business.domain}${business.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_1">查看</a>--%>
                            <%--<span class="font-red cursor-pointer mt4" onclick="del(1)" id="img_del_1">删除</span>--%>
                            <%--</c:when>--%>
                            <%--<c:otherwise>--%>
                            <%--<i class="iconsuccesss ml7 none" id="img_icon_1"></i>--%>
                            <%--<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_1">查看</a>--%>
                            <%--<span class="font-red cursor-pointer mt4 none" onclick="del(1)" id="img_del_1">删除</span>--%>
                            <%--</c:otherwise>--%>
                            <%--</c:choose>--%>
                            <%--</li>--%>
                            <li style="margin-bottom:0;">
                                <c:choose>
                                    <c:when test="${!empty bussinessList }">
                                        <c:forEach items="${bussinessList }" var="bus" varStatus="st">
                                            <c:if test="${st.index == 0}">
                                                <c:set var="beginTimeB" value="${bus.begintime}"></c:set>
                                                <c:set var="endTimeB" value="${bus.endtime}"></c:set>
                                                <c:set var="isMedical" value="${bus.isMedical}" />
                                            </c:if>
                                            <div class="mb8 c_1">
                                                <div class="pos_rel f_left ">
                                                    <input type="file" class="upload_file" name="lwfile"
                                                           id="file_1_${st.index}" style="display: none;"
                                                           onchange="uploadFile(this,1);"/>
                                                    <c:choose>
                                                        <c:when test="${st.index == 0 }">
                                                            <input type="text" class="input-middle" id="name_1"
                                                                   readonly="readonly"
                                                                   placeholder="请上传营业执照" name="busName"
                                                                   onclick="file_1_${st.index}.click();"
                                                                   value="${bus.name}">
                                                            <input type="hidden" id="uri_1_${st.index}" name="busUri"
                                                                   value="${bus.uri}">

                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="text" class="input-middle"
                                                                   id="name_1_${st.index}" readonly="readonly"
                                                                   placeholder="请上传营业执照" name="name_1"
                                                                   onclick="file_1_${st.index}.click();"
                                                                   value="${bus.name}">
                                                            <input type="hidden" id="uri_1_${st.index}" name="uri_1"
                                                                   value="${bus.uri}">
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <label class="bt-bg-style bt-middle bg-light-blue ml10"
                                                           id="busUpload"
                                                           onclick="return $('#file_1_${st.index}').click();">浏览</label>
                                                    <div class="font-red " style="display: none;">请选择营业执照</div>
                                                </div>

                                                <c:choose>
                                                    <c:when test="${bus.uri ne null && bus.uri ne ''}">
                                                        <div class="f_left ">
                                                            <i class="iconsuccesss ml7" id="img_icon_4"></i>
                                                            <a href="http://${bus.domain}${bus.uri}" target="_blank"
                                                               class="font-blue cursor-pointer mr5 ml10 mt4"
                                                               id="img_view_4">查看</a>
                                                            <c:choose>
                                                                <c:when test="${st.index == 0 }">
                                                                    <span class="font-red cursor-pointer mt4"
                                                                          onclick="del(1)" id="img_del_1">删除</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="font-red cursor-pointer mt4"
                                                                          onclick="delAttachment(this)" id="img_del_1">删除</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="f_left ">
                                                            <i class="iconsuccesss ml7 none" id="img_icon_4"></i>
                                                            <a href="http://${bus.domain}${bus.uri}" target="_blank"
                                                               class="font-blue cursor-pointer mr5 ml10 mt4 none"
                                                               id="img_view_4">查看</a>
                                                            <c:choose>
                                                                <c:when test="${st.index == 0 }">
                                                                    <span class="font-red cursor-pointer mt4 none"
                                                                          onclick="del(1)" id="img_del_4">删除</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="font-red cursor-pointer mt4 none"
                                                                          onclick="delAttachment(this)" id="img_del_4">删除</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                                <div class="clear"></div>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="mb8 c_1">
                                            <div class="f_left">
                                                <input type="file" class="upload_file" name="lwfile" id="file_1"
                                                       style="display: none;" onchange="uploadFile(this,1);"/>
                                                <input type="text" class="input-middle" id="name_1" readonly="readonly"
                                                       placeholder="请上传营业执照" name="busName" onclick="file_1.click();"
                                                       value="${bus.name}">
                                                <input type="hidden" id="busUri" name="busUri" value="${bus.uri}">
                                                <div class="font-red " style="display: none;">请上传营业执照</div>
                                            </div>
                                            <label class="bt-bg-style bt-middle bg-light-blue ml10" id="busUpload"
                                                   onclick="return $('#file_1').click();">浏览</label>
                                            <!-- 上传成功出现 -->
                                            <c:choose>
                                                <c:when test="${!empty bus.uri}">
                                                    <i class="iconsuccesss ml7" id="img_icon_4"></i>
                                                    <a href="http://${bus.domain}${bus.uri}" target="_blank"
                                                       class="font-blue cursor-pointer mr5 ml10 mt4"
                                                       id="img_view_1">查看</a>
                                                    <span class="font-red cursor-pointer mt4" onclick="del(1)"
                                                          id="img_del_1">删除</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="iconsuccesss ml7 none" id="img_icon_4"></i>
                                                    <a href="" target="_blank"
                                                       class="font-blue cursor-pointer mr5 ml10 mt4 none"
                                                       id="img_view_1">查看</a>
                                                    <span class="font-red cursor-pointer mt4 none" onclick="del(1)"
                                                          id="img_del_1">删除</span>
                                                </c:otherwise>
                                            </c:choose>
                                            <div class="clear"></div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                                <div class="clear" id="conadd1">
                                    <span class="bt-border-style bt-small border-blue mt8"
                                          onclick="con_add(1,'请上传营业执照');">继续添加</span>
                                </div>
                            </li>
                            <li>
                                <label class="f_left mt4 mr10">设置有效期</label>
                                <div class="f_left">
                                    <div class='inputfloat'>
                                        <input type="text" class="Wdate input-smaller" placeholder="请选择日期"
                                               onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'busEndTime\')}'})"
                                               name="busStartTime"
                                               id="busStartTime"
                                               value='<date:date value ="${beginTimeB} " format="yyyy-MM-dd"/>'/>
                                        <input type="text" class="Wdate input-smaller" placeholder="请选择日期"
                                               onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'busStartTime\')}'})"
                                               name="busEndTime"
                                               id="busEndTime"
                                               value='<date:date value ="${endTimeB} " format="yyyy-MM-dd"/>'/>
                                    </div>
                                    <div class="font-red " style="display: none;">开始时间不能为空</div>
                                </div>
                            </li>
                            <li class="inputfloat">
                                <label style="margin-top: 8px">截止日期为空表示无限期</label>
                                <input class="mt8" type="checkbox" name="isMedical"
                                       <c:if test="${isMedical eq 1}">checked="checked"</c:if> value="1">
                                <label class="mt5">含有医疗器械</label>
                            </li>
                        </ul>
                    </div>
                </li>
                <li>
                    <div class="infor_name sex_name" style="width: 150px">
                        <label>税务登记证</label>
                    </div>
                    <div class="f_left insertli insertli1" id="tax">
                        <ul>
                            <li>
                                <div class="f_left">
                                    <input type="file" class="upload_file" id='file_2' name="lwfile"
                                           style="display: none;" onchange="uploadFile(this,2);">
                                    <input type="text" class="input-middle" id="name_2" readonly="readonly"
                                           <c:if test="${business.threeInOne eq 1}">disabled="disabled"</c:if>
                                           placeholder="请上传税务登记证" name="taxName" onclick="file_2.click();"
                                           value="${tax.name}">
                                    <input type="hidden" id="uri_2" name="taxUri" value="${tax.uri}">
                                    <div class="font-red " style="display: none;">请上传税务登记证</div>
                                    <label class="bt-bg-style bt-middle bg-light-blue ml10 <c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>"
                                           type="file" id="taxUpload"
                                           <c:if test='${business eq null || business.threeInOne ne 1}'>onclick="return $('#file_2').click();"</c:if>>浏览</label>
                                </div>
                                <!-- 上传成功出现 -->
                                <c:choose>
                                    <c:when test="${!empty tax.uri}">
                                        <i class="iconsuccesss ml7" id="img_icon_2"></i>
                                        <a href="http://${tax.domain}${tax.uri}" target="_blank"
                                           class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_2">查看</a>
                                        <span class="font-red <c:if test="${business eq null || business.threeInOne eq 0}">cursor-pointer</c:if> mt4"
                                              <c:if test="${business eq null || business.threeInOne eq 0}">onclick="del(2)"</c:if>
                                              id="img_del_2">删除</span>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="iconsuccesss ml7 none" id="img_icon_2"></i>
                                        <a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none"
                                           id="img_view_2">查看</a>
                                        <span class="font-red <c:if test="${business eq null || business.threeInOne eq 0}">cursor-pointer</c:if> mt4 none"
                                              <c:if test="${business eq null || business.threeInOne eq 0}">onclick="del(2)"</c:if>
                                              id="img_del_2">删除</span>
                                    </c:otherwise>
                                </c:choose>

                            </li>
                            <li>
                                <label class="f_left mt4 mr10 <c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>">设置有效期</label>
                                <div class="f_left">
                                    <div class='inputfloat'>
                                        <input type="text"
                                               class="Wdate input-smaller <c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>"
                                               placeholder="请选择日期"
                                               onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'taxEndTime\')}'})"
                                               name="taxStartTime"
                                               id="taxStartTime"
                                               value='<date:date value ="${tax.begintime} " format="yyyy-MM-dd"/>'
                                               <c:if test="${business.threeInOne eq 1}">disabled="disabled"</c:if>/>
                                        <input type="text"
                                               class="Wdate input-smaller <c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>"
                                               placeholder="请选择日期"
                                               onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'taxStartTime\')}'})"
                                               name="taxEndTime"
                                               id="taxEndTime"
                                               value='<date:date value ="${tax.endtime} " format="yyyy-MM-dd"/>'
                                               <c:if test="${business.threeInOne eq 1}">disabled="disabled"</c:if>/>
                                    </div>
                                    <div class="font-red " style="display: none;">开始时间不能为空</div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </li>
                <li>
                    <div class="infor_name sex_name" style="width: 150px">
                        <label>组织机构代码证</label>
                    </div>
                    <div class="f_left insertli " id="org">
                        <ul>
                            <li style="margin-bottom:0px;">
                                <div class="f_left">
                                    <input type="file" class="upload_file" id='file_3' name="lwfile"
                                           style="display: none;" onchange="uploadFile(this,3);">
                                    <input type="text" class="input-middle" id="name_3" readonly="readonly"
                                           <c:if test="${business.threeInOne eq 1}">disabled="disabled"</c:if>
                                           placeholder="请上传组织机构代码证" name="orgName" onclick="file_3.click();"
                                           value="${orga.name}">
                                    <input type="hidden" id="uri_3" name="orgaUri" value="${orga.uri}">
                                    <div class="font-red " style="display: none;">请上传组织机构代码证</div>
                                    <label class="bt-bg-style bt-middle bg-light-blue ml10 
                                    	<c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>" type="file"
                                           id="orgUpload"
                                           <c:if test='${business eq null || business.threeInOne ne 1}'>onclick="return $('#file_3').click();"</c:if>>浏览</label>
                                </div>
                                <!-- 上传成功出现 -->
                                <c:choose>
                                    <c:when test="${!empty orga.uri}">
                                        <i class="iconsuccesss ml7" id="img_icon_3"></i>
                                        <a href="http://${orga.domain}${orga.uri}" target="_blank"
                                           class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_3">查看</a>
                                        <span class="font-red <c:if test="${business eq null || business.threeInOne eq 0}">cursor-pointer</c:if> mt4"
                                              <c:if test="${business eq null || business.threeInOne eq 0}">onclick="del(3)"</c:if>
                                              id="img_del_3">删除</span>
                                    </c:when>
                                    <c:otherwise>
                                        <i class="iconsuccesss ml7 none" id="img_icon_3"></i>
                                        <a href="" target="_blank"
                                           class="font-blue <c:if test="${business eq null || business.threeInOne eq 0}">cursor-pointer</c:if> mr5 ml10 mt4 none"
                                           id="img_view_3">查看</a>
                                        <span class="font-red cursor-pointer mt4 none"
                                              <c:if test="${business eq null || business.threeInOne eq 0}">onclick="del(3)"</c:if>
                                              id="img_del_3">删除</span>
                                    </c:otherwise>
                                </c:choose>

                            </li>
                            <li style="margin-bottom:0px;">
                                <label class="f_left mt4 mr10 <c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>">设置有效期</label>
                                <div class="f_left">
                                    <div class='inputfloat'>
                                        <input class="Wdate input-smaller <c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>"
                                               type="text" placeholder="请选择日期"
                                               onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'orgaEndTime\')}'})"
                                               name="orgaStartTime"
                                               id="orgaStartTime"
                                               value='<date:date value ="${orga.begintime} " format="yyyy-MM-dd"/>'
                                               <c:if test="${business.threeInOne eq 1}">disabled="disabled"</c:if>/>
                                        <input class="Wdate input-smaller <c:if test='${business.threeInOne eq 1}'>bg-opcity</c:if>"
                                               type="text" placeholder="请选择日期"
                                               onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'orgaStartTime\')}'})"
                                               name="orgaEndTime"
                                               id="orgaEndTime"
                                               value='<date:date value ="${orga.endtime} " format="yyyy-MM-dd"/>'
                                               <c:if test="${business.threeInOne eq 1}">disabled="disabled"</c:if>/>
                                    </div>
                                    <div class="font-red" style="display: none;">开始时间不能为空</div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </li>
                <c:if test="${traderCustomer.customerNature eq 465}">
                    <li class="table-large">
                        <div class="infor_name mt0" style="width: 150px">
                            <label>医疗资质合一</label>
                        </div>
                            <%--<c:if  test="${twoMedicalList !=null}">--%>
                            <%--<c:forEach items="twoMedicalList" var="item" varStatus="st">--%>
                            <%--<c:if test="${st.index==0}">--%>
                            <%--<c:set value="${item.medicalQualification}" var="qualification" />--%>
                            <%--</c:if>--%>
                            <%--</c:forEach>--%>
                            <%--</c:if>--%>
                        <input type="hidden" id="qualification" name="1" value="${qualification}">
                        <div class="f_left inputfloat">
                            <input type="radio" name="medicalQualification" id="med1" value="1" onclick="medOne();"
                                   <c:if test="${qualification == '1'}">checked="checked"</c:if>/>
                            <label class="mr8">是</label>
                            <input type="radio" name="medicalQualification" id="med0" value="0" onclick="medZero();"
                                   <c:if test="${qualification ne '1'}">checked="checked"</c:if>/>
                            <label>否</label>
                        </div>
                    </li>

                    <li>
                        <div class="infor_name sex_name" style="width: 150px">
                            <label>二类医疗资质</label>
                        </div>
                        <div class="f_left insertli ">
                            <ul id="two_medical">
                                <li style="margin-bottom:0;">
                                    <c:choose>
                                        <c:when test="${!empty twoMedicalList }">
                                            <c:forEach items="${twoMedicalList }" var="twoMedical" varStatus="st">
                                                <c:if test="${st.index == 0}">
                                                    <c:set var="beginTime" value="${twoMedical.begintime}"></c:set>
                                                    <c:set var="endTime" value="${twoMedical.endtime}"></c:set>
                                                    <c:set var="sn" value="${twoMedical.sn}"></c:set>
                                                </c:if>
                                                <div class="mb8 c_4">
                                                    <div class="pos_rel f_left ">
                                                        <input type="file" class="upload_file" name="lwfile"
                                                               id="file_4_${st.index}" style="display: none;"
                                                               onchange="uploadFile(this,4);"/>
                                                        <c:choose>
                                                            <c:when test="${st.index == 0 }">
                                                                <input type="text" class="input-middle" id="name_4"
                                                                       readonly="readonly"
                                                                       placeholder="请上传二类医疗资质凭证" name="twoName"
                                                                       onclick="file_4_${st.index}.click();"
                                                                       value="${twoMedical.name}">
                                                                <input type="hidden" id="uri_4_${st.index}"
                                                                       name="twoUri" value="${twoMedical.uri}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="text" class="input-middle"
                                                                       id="name_4_${st.index}" readonly="readonly"
                                                                       placeholder="请上传二类医疗资质凭证" name="name_4"
                                                                       onclick="file_4_${st.index}.click();"
                                                                       value="${twoMedical.name}">
                                                                <input type="hidden" id="uri_4_${st.index}" name="uri_4"
                                                                       value="${twoMedical.uri}">
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <label class="bt-bg-style bt-middle bg-light-blue ml10"
                                                               id="twoUpload"
                                                               onclick="return $('#file_4_${st.index}').click();">浏览</label>
                                                        <div class="font-red " style="display: none;">请上传二类医疗资质凭证</div>
                                                    </div>

                                                    <c:choose>
                                                        <c:when test="${twoMedical.uri ne null && twoMedical.uri ne ''}">
                                                            <div class="f_left ">
                                                                <i class="iconsuccesss ml7" id="img_icon_4"></i>
                                                                <a href="http://${twoMedical.domain}${twoMedical.uri}"
                                                                   target="_blank"
                                                                   class="font-blue cursor-pointer mr5 ml10 mt4"
                                                                   id="img_view_4">查看</a>
                                                                <c:choose>
                                                                    <c:when test="${st.index == 0 }">
                                                                        <span class="font-red cursor-pointer mt4"
                                                                              onclick="del(4)" id="img_del_4">删除</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="font-red cursor-pointer mt4"
                                                                              onclick="delAttachment(this)"
                                                                              id="img_del_4">删除</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="f_left ">
                                                                <i class="iconsuccesss ml7 none" id="img_icon_4"></i>
                                                                <a href="http://${twoMedical.domain}${twoMedical.uri}"
                                                                   target="_blank"
                                                                   class="font-blue cursor-pointer mr5 ml10 mt4 none"
                                                                   id="img_view_4">查看</a>
                                                                <c:choose>
                                                                    <c:when test="${st.index == 0 }">
                                                                        <span class="font-red cursor-pointer mt4 none"
                                                                              onclick="del(4)" id="img_del_4">删除</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="font-red cursor-pointer mt4 none"
                                                                              onclick="delAttachment(this)"
                                                                              id="img_del_4">删除</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <div class="clear"></div>
                                                </div>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="mb8 c_4">
                                                <div class="f_left">
                                                    <input type="file" class="upload_file" name="lwfile" id="file_4"
                                                           style="display: none;" onchange="uploadFile(this,4);"/>
                                                    <input type="text" class="input-middle" id="name_4"
                                                           readonly="readonly"
                                                           placeholder="请上传二类备案凭证" name="twoName"
                                                           onclick="file_4.click();" value="${twoMedical.name}">
                                                    <input type="hidden" id="uri_4" name="twoUri"
                                                           value="${twoMedical.uri}">
                                                    <div class="font-red " style="display: none;">请上传二类备案凭证</div>
                                                </div>
                                                <label class="bt-bg-style bt-middle bg-light-blue ml10" id="twoUpload"
                                                       onclick="return $('#file_4').click();">浏览</label>
                                                <!-- 上传成功出现 -->
                                                <c:choose>
                                                    <c:when test="${!empty twoMedical.uri}">
                                                        <i class="iconsuccesss ml7" id="img_icon_4"></i>
                                                        <a href="http://${twoMedical.domain}${twoMedical.uri}"
                                                           target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4"
                                                           id="img_view_4">查看</a>
                                                        <span class="font-red cursor-pointer mt4" onclick="del(4)"
                                                              id="img_del_4">删除</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="iconsuccesss ml7 none" id="img_icon_4"></i>
                                                        <a href="" target="_blank"
                                                           class="font-blue cursor-pointer mr5 ml10 mt4 none"
                                                           id="img_view_4">查看</a>
                                                        <span class="font-red cursor-pointer mt4 none" onclick="del(4)"
                                                              id="img_del_4">删除</span>
                                                    </c:otherwise>
                                                </c:choose>
                                                <div class="font-red " style="display: none;">请上传二类备案凭证</div>
                                                <div class="clear"></div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="clear" id="conadd4">
                                        <span class="bt-border-style bt-small border-blue mt8"
                                              onclick="con_add(4,'请上传二类医疗资质凭证');">继续添加</span>
                                    </div>

                                    <div class="font-grey9" style="margin-top:7px;">
                                        1，图片格式只能JPG、PNG、JPEG、BMP格式<br>
                                        2，图片大小不超过5M
                                    </div>
                                </li>
                                <li style="margin-bottom:0;">
                                    <label class='f_left mt4 mr10'>设置有效期</label>
                                    <div class="f_left ">
                                        <div class='inputfloat J-date-wrap'>
                                            <input class="Wdate input-smaller J-date1" type="text" placeholder="请选择日期"
                                                   onFocus="WdatePicker({dchanged: changeDate(this),dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'twoEndTime\')}'})"
                                                   name="twoStartTime"
                                                   id="twoStartTime"
                                                   value='<date:date value ="${beginTime} " format="yyyy-MM-dd"/>'/>
                                            <input class="Wdate input-smaller J-date2" type="text" placeholder="请选择日期"
                                                   onFocus="WdatePicker({dchanged: changeDate(this),dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'twoStartTime\')}'})"
                                                   name="twoEndTime"
                                                   id="twoEndTime"
                                                   value='<date:date value ="${endTime} " format="yyyy-MM-dd"/>'/>
                                        </div>
                                        <div class="font-red " style="display: none;">开始时间不能为空</div>
                                    </div>
                                </li>
                                <li class="specialli" style="margin-bottom:0;">
                                    <label class="f_left mt4 mr10">许可证编号</label>
                                    <div class="f_left ">
                                        <input type="text" name="twoSn" class="input-middle" value="${sn}" id="twoSn"/>
                                        <div class="font-red " style="display: none;"></div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <!--
                    <li>
                    <div class="infor_name sex_name" style="width: 110px">
                    <label>二类医疗资质</label>
                    </div>
                    <div class="f_left insertli ">
                    <ul id="two_medical">
                    <li style="margin-bottom:0px;">
                    <div class="f_left">
                    <input type="file" class="upload_file" name="lwfile" id="file_4" style="display: none;" onchange="uploadFile(this,4);"/>
                    <input type="text" class="input-middle" id="name_4" readonly="readonly"
                    placeholder="请上传二类医疗资质" name="twoName" onclick="file_4.click();" value ="${twoMedical.name}" style="float:left;">
                    <input type="hidden" id="uri_4" name="twoUri" value="${twoMedical.uri}">
                    <div class="font-red " style="display: none;">请上传二类医疗资质</div>
                    <label class="bt-bg-style bt-middle bg-light-blue ml10" id="twoUpload" onclick="return $('#file_4').click();">浏览</label>
                    </div>

                    <c:choose>
                        <c:when test="${!empty twoMedical.uri}">
                            <i class="iconsuccesss ml7" id="img_icon_4"></i>
                            <a href="http://${twoMedical.domain}${twoMedical.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_4">查看</a>
                            <span class="font-red cursor-pointer mt4" onclick="del(4)" id="img_del_4">删除</span>
                        </c:when>
                        <c:otherwise>
                            <i class="iconsuccesss ml7 none" id="img_icon_4"></i>
                            <a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_4">查看</a>
                            <span class="font-red cursor-pointer mt4 none" onclick="del(4)" id="img_del_4">删除</span>
                        </c:otherwise>
                    </c:choose>

                    </li>
                    <li style="margin-bottom:0px;">
                    <label class='f_left mt4 mr10'>设置有效期</label>
                    <div class="f_left ">
                    <div class='inputfloat'>
                    <input class="Wdate input-smaller" type="text" placeholder="请选择日期"
                    onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'twoEndTime\')}'})" name="twoStartTime"
                    id="twoStartTime" value='<date:date value="${twoMedical.begintime} " format="yyyy-MM-dd"/>' />
                    <input class="Wdate input-smaller" type="text" placeholder="请选择日期"
                    onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'twoStartTime\')}'})" name="twoEndTime"
                    id="twoEndTime" value='<date:date value="${twoMedical.endtime} " format="yyyy-MM-dd"/>'/>
                    </div>
                    <div class="font-red " style="display: none;">开始时间不能为空</div>
                    </div>
                    </li>
                    <li class="specialli" style="margin-bottom:0px;">
                    <label class="f_left mt4 mr10">许可证编号</label>
                    <div class="f_left ">
                    <input type="text" name="twoSn" class="input-middle" value="${twoMedical.sn}" id="twoSn"/>
                    <div class="font-red " style="display: none;"></div>
                    </div>
                    </li>
                    </ul>
                    </div>
                    </li>
                    -->
                    <li>
                        <div class="infor_name sex_name" style="width: 150px">
                            <label class="mt1">选择二类医疗资质(旧国标)</label>
                        </div>
                        <div class="f_left medicalquality overflow-hidden" style='width:88%;'>
                            <input type="checkbox" name="twoMedicalTypeAll" onclick="clickAll('twoMedicalType')">
                            <label>全选</label>
                            <ul class="f_left" id="medical_ul">
                                <c:forEach items="${medicalTypes }" var="mt">
                                    <c:set var="contains" value="false"/>
                                    <%--<c:if test="${fn:contains(mt.relatedField,2)}">--%>
                                        <c:if test="${not empty two}">
                                            <c:forEach items="${two }" var="mc" varStatus="status">
                                                <c:if test="${mt.sysOptionDefinitionId eq mc.medicalCategoryId}">
                                                    <c:set var="contains" value="true"/>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                        <c:choose>
                                            <c:when test="${contains == true }">
                                                <li style="width:25%;float:left;" style="margin-bottom:0px;">
                                                    <input type="checkbox" name="twoMedicalType"
                                                           value="${mt.sysOptionDefinitionId }" checked="checked"
                                                           onclick="clickOne('twoMedicalType')">
                                                    <label>${mt.title}</label>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li style="width:25%;float:left;">
                                                    <input type="checkbox" name="twoMedicalType"
                                                           value="${mt.sysOptionDefinitionId }"
                                                           onclick="clickOne('twoMedicalType')">
                                                    <label>${mt.title}</label>
                                                </li>
                                            </c:otherwise>
                                        </c:choose>
                                    <%--</c:if>--%>
                                </c:forEach>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name sex_name" style="width: 150px">
                            <label class="mt1">选择二类医疗资质(新国标)</label>
                        </div>
                        <div class="f_left medicalquality overflow-hidden" style='width:88%;'>
                            <input type="checkbox" name="newTwoMedicalTypeAll" onclick="clickAll('newTwoMedicalType')">
                            <label>全选</label>
                            <ul class="f_left" id="medical_ul">
                                <c:forEach items="${newMedicalTypes }" var="mt">
                                    <c:set var="contains" value="false"/>
                                    <%--<c:if test="${fn:contains(mt.relatedField,2)}">--%>
                                    <c:if test="${not empty newTwo}">
                                        <c:forEach items="${newTwo}" var="mc" varStatus="status">
                                            <c:if test="${mt.sysOptionDefinitionId eq mc.medicalCategoryId}">
                                                <c:set var="contains" value="true"/>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                    <c:choose>
                                        <c:when test="${contains == true }">
                                            <li style="width:25%;float:left;" style="margin-bottom:0px;">
                                                <input type="checkbox" name="newTwoMedicalType"
                                                       value="${mt.sysOptionDefinitionId }" checked="checked"
                                                       onclick="clickOne('twoMedicalType')">
                                                <label>${mt.title}</label>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li style="width:25%;float:left;">
                                                <input type="checkbox" name="newTwoMedicalType"
                                                       value="${mt.sysOptionDefinitionId }"
                                                       onclick="clickOne('twoMedicalType')">
                                                <label>${mt.title}</label>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                    <%--</c:if>--%>
                                </c:forEach>
                            </ul>
                        </div>
                    </li>

                    <li>
                        <div class="infor_name sex_name" style="width: 150px">
                            <label>三类医疗资质</label>
                        </div>
                        <div class="f_left insertli ">
                            <ul id="three_medical">
                                <li style="margin-bottom:0px;">
                                    <c:choose>
                                        <c:when test="${!empty threeMedical }">
                                            <c:forEach items="${threeMedical }" var="threeMedicalVo" varStatus="st">
                                                <c:if test="${st.index == 0}">
                                                    <c:set var="beginTime1" value="${threeMedicalVo.begintime}"></c:set>
                                                    <c:set var="endTime1" value="${threeMedicalVo.endtime}"></c:set>
                                                    <c:set var="sn1" value="${threeMedicalVo.sn}"></c:set>
                                                </c:if>
                                                <div class="mb8 c_5">
                                                    <div class="pos_rel f_left ">
                                                        <input type="file" class="upload_file" name="lwfile"
                                                               id="file_5_${st.index}" style="display: none;"
                                                               onchange="uploadFile(this,5);"/>
                                                        <c:choose>
                                                            <c:when test="${st.index == 0 }">
                                                                <input type="text" class="input-middle" id="name_5"
                                                                       readonly="readonly"
                                                                       placeholder="请上传三类医疗资质凭证" name="threeName"
                                                                       onclick="file_5_${st.index}.click();"
                                                                       value="${threeMedicalVo.name}">
                                                                <input type="hidden" id="uri_5_${st.index}"
                                                                       name="threeUri" value="${threeMedicalVo.uri}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="text" class="input-middle"
                                                                       id="name_5_${st.index}" readonly="readonly"
                                                                       placeholder="请上传三类医疗资质凭证" name="name_5"
                                                                       onclick="file_5_${st.index}.click();"
                                                                       value="${threeMedicalVo.name}">
                                                                <input type="hidden" id="uri_5_${st.index}" name="uri_5"
                                                                       value="${threeMedicalVo.uri}">
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <label class="bt-bg-style bt-middle bg-light-blue ml10"
                                                               id="twoUpload"
                                                               onclick="return $('#file_5_${st.index}').click();">浏览</label>
                                                    </div>

                                                    <c:choose>
                                                        <c:when test="${threeMedicalVo.uri ne null && threeMedicalVo.uri ne ''}">
                                                            <div class="f_left ">
                                                                <i class="iconsuccesss ml7" id="img_icon_4"></i>
                                                                <a href="http://${threeMedicalVo.domain}${threeMedicalVo.uri}"
                                                                   target="_blank"
                                                                   class="font-blue cursor-pointer mr5 ml10 mt4"
                                                                   id="img_view_4">查看</a>
                                                                <c:choose>
                                                                    <c:when test="${st.index == 0 }">
                                                                        <span class="font-red cursor-pointer mt4"
                                                                              onclick="del(5)" id="img_del_4">删除</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="font-red cursor-pointer mt4"
                                                                              onclick="delAttachment(this)"
                                                                              id="img_del_4">删除</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="f_left ">
                                                                <i class="iconsuccesss ml7 none" id="img_icon_4"></i>
                                                                <a href="http://${threeMedicalVo.domain}${threeMedicalVo.uri}"
                                                                   target="_blank"
                                                                   class="font-blue cursor-pointer mr5 ml10 mt4 none"
                                                                   id="img_view_4">查看</a>
                                                                <c:choose>
                                                                    <c:when test="${st.index == 0 }">
                                                                        <span class="font-red cursor-pointer mt4 none"
                                                                              onclick="del(5)" id="img_del_4">删除</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="font-red cursor-pointer mt4 none"
                                                                              onclick="delAttachment(this)"
                                                                              id="img_del_4">删除</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <div class="clear"></div>
                                                </div>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="mb8 c_5">
                                                <div class="f_left">
                                                    <input type="file" class="upload_file" name="lwfile" id="file_5"
                                                           style="display: none;" onchange="uploadFile(this,5);"/>
                                                    <input type="text" class="input-middle" id="name_5"
                                                           readonly="readonly"
                                                           placeholder="请上传三类资质凭证" name="threeName"
                                                           onclick="file_5.click();" value="${threeMedicalVo.name}">
                                                    <input type="hidden" id="uri_5" name="threeUri"
                                                           value="${threeMedicalVo.uri}">
                                                    <div class="font-red " style="display: none;">请上传三类资质凭证</div>
                                                </div>
                                                <label class="bt-bg-style bt-middle bg-light-blue ml10" id="threeUpload"
                                                       onclick="return $('#file_5').click();">浏览</label>
                                                <!-- 上传成功出现 -->
                                                <c:choose>
                                                    <c:when test="${!empty threeMedicalVo.uri}">
                                                        <i class="iconsuccesss ml7" id="img_icon_4"></i>
                                                        <a href="http://${threeMedicalVo.domain}${threeMedicalVo.uri}"
                                                           target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4"
                                                           id="img_view_4">查看</a>
                                                        <span class="font-red cursor-pointer mt4" onclick="del(5)"
                                                              id="img_del_5">删除</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="iconsuccesss ml7 none" id="img_icon_4"></i>
                                                        <a href="" target="_blank"
                                                           class="font-blue cursor-pointer mr5 ml10 mt4 none"
                                                           id="img_view_5">查看</a>
                                                        <span class="font-red cursor-pointer mt4 none" onclick="del(5)"
                                                              id="img_del_4">删除</span>
                                                    </c:otherwise>
                                                </c:choose>
                                                <div class="clear"></div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                        <%--<div class="f_left">--%>
                                        <%--<input type="file" class="upload_file" name="lwfile" id="file_5" style="display: none;" onchange="uploadFile(this,5);"/>--%>
                                        <%--<input type="text" class="input-middle upload_file_tmp" id="name_5"  readonly="readonly" --%>
                                        <%--<c:if test="${twoMedical.medicalQualification eq 1}">disabled="disabled"</c:if>--%>
                                        <%--placeholder="请上传三类医疗资质" name="threeName" onclick="file_5.click();" value ="${threeMedical.name}">--%>
                                        <%--<input type="hidden" id="uri_5" name="threeUri" value="${threeMedical.uri}">--%>
                                        <%--<div class="font-red " style="display: none;">请上传三类医疗资质</div>--%>
                                        <%--<label class="bt-bg-style bt-middle bg-light-blue ml10--%>
                                        <%--<c:if test="${twoMedical.medicalQualification eq 1}">bg-opcity</c:if>" id="threeUpload" --%>
                                        <%--<c:if test="${twoMedical.medicalQualification ne 1}">onclick="return $('#file_5').click();"</c:if>>浏览</label>--%>
                                        <%--</div>--%>
                                        <%--<!-- 上传成功出现 -->--%>
                                        <%--<c:choose>--%>
                                        <%--<c:when test="${!empty threeMedical.uri}">--%>
                                        <%--<i class="iconsuccesss ml7" id="img_icon_5"></i>--%>
                                        <%--<a href="http://${threeMedical.domain}${threeMedical.uri}" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4" id="img_view_5">查看</a>--%>
                                        <%--<span class="font-red <c:if test="${twoMedical eq null ||twoMedical.medicalQualification eq 0}">cursor-pointer</c:if> mt4" --%>
                                        <%--<c:if test="${twoMedical eq null || twoMedical.medicalQualification ne 1}">onclick="del(5)"</c:if> id="img_del_5">删除</span>--%>
                                        <%--</c:when>--%>
                                        <%--<c:otherwise>--%>
                                        <%--<i class="iconsuccesss ml7 none" id="img_icon_5"></i>--%>
                                        <%--<a href="" target="_blank" class="font-blue cursor-pointer mr5 ml10 mt4 none" id="img_view_5">查看</a>--%>
                                        <%--<span class="font-red <c:if test="${twoMedical eq null ||twoMedical.medicalQualification eq 0}">cursor-pointer</c:if> mt4 none" --%>
                                        <%--<c:if test="${twoMedical eq null ||twoMedical.medicalQualification eq 0}">onclick="del(5)"</c:if> id="img_del_5">删除</span>--%>
                                        <%--</c:otherwise>--%>
                                        <%--</c:choose>--%>
                                    <div class="clear" id="conadd5">
                                        <span class="bt-border-style bt-small border-blue mt8" id="threeMeicalAdd"
                                              onclick="con_add(5,'请上传三类医疗资质');">继续添加</span>
                                    </div>
                                </li>
                                <li style="margin-bottom:0px;">
                                    <label class="f_left  mt4 mr10 <c:if test="${threeMedicalVo.medicalQualification eq 1}">bg-opcity</c:if>">设置有效期</label>
                                    <div class="f_left ">
                                        <div class='inputfloat'>
                                            <input class="Wdate input-smaller <c:if test="${threeMedicalVo.medicalQualification eq 1}">bg-opcity</c:if>"
                                                   type="text" placeholder="请选择日期"
                                                   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'threeEndTime\')}'})"
                                                   name="threeStartTime"
                                                   id="threeStartTime"
                                                   value='<date:date value ="${beginTime1} " format="yyyy-MM-dd"/>'
                                                   <c:if test="${threeMedicalVo.medicalQualification eq 1}">disabled="disabled"</c:if>/>
                                            <input class="Wdate input-smaller <c:if test="${threeMedicalVo.medicalQualification eq 1}">bg-opcity</c:if>"
                                                   type="text" placeholder="请选择日期"
                                                   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'threeStartTime\')}'})"
                                                   name="threeEndTime"
                                                   id="threeEndTime"
                                                   value='<date:date value ="${endTime1} " format="yyyy-MM-dd"/>'
                                                   <c:if test="${threeMedicalVo.medicalQualification eq 1}">disabled="disabled"</c:if>/>
                                        </div>
                                        <div class="font-red" style="display: none;">开始时间不能为空</div>
                                    </div>
                                </li>
                                <li class="specialli" style="margin-bottom:0px;">
                                    <label class="f_left mt4 mr10 <c:if test="${threeMedicalVo.medicalQualification eq 1}">bg-opcity</c:if>">许可证编号</label>
                                    <div class="f_left ">
                                        <input type="text" name="threeSn" class="input-middle" value="${sn1}"
                                               <c:if test="${threeMedicalVo.medicalQualification eq 1}">disabled="disabled"</c:if>/>
                                        <div class="font-red" style="display: none;">许可证编号不可为空</div>
                                    </div>
                                        <%--<div class="clear"></div>--%>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <div class="infor_name sex_name" style="width: 150px">
                            <label class="mt1">选择三类医疗资质(旧国标)</label>
                        </div>
                        <div class="f_left medicalquality overflow-hidden" style="width:88%;">
                            <input type="checkbox" name="threeMedicalTypeAll" onclick="clickAll('threeMedicalType')">
                            <label>全选</label>
                            <ul class="f_left" id="medical_ul">
                                <c:forEach items="${medicalTypes }" var="mt">
                                    <c:set var="contains" value="false"/>
                                    <%--<c:if test="${fn:contains(mt.relatedField,3)}">--%>
                                        <c:if test="${not empty three}">
                                            <c:forEach items="${three }" var="mc" varStatus="status">
                                                <c:if test="${mt.sysOptionDefinitionId eq mc.medicalCategoryId}">
                                                    <c:set var="contains" value="true"/>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                        <c:choose>
                                            <c:when test="${contains == true }">
                                                <li style="width:25%;float:left;">
                                                    <input type="checkbox" name="threeMedicalType"
                                                           value="${mt.sysOptionDefinitionId }" checked="checked"
                                                           onclick="clickOne('threeMedicalType')">
                                                    <label>${mt.title}</label>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li style="width:25%;float:left;">
                                                    <input type="checkbox" name="threeMedicalType"
                                                           value="${mt.sysOptionDefinitionId }"
                                                           onclick="clickOne('threeMedicalType')">
                                                    <label>${mt.title}</label>
                                                </li>
                                            </c:otherwise>
                                        </c:choose>
                                    <%--</c:if>--%>
                                </c:forEach>
                            </ul>


                        </div>
                    </li>
                    <li>
                        <div class="infor_name sex_name" style="width: 150px">
                            <label class="mt1">选择三类医疗资质(新国标)</label>
                        </div>
                        <div class="f_left medicalquality overflow-hidden" style="width:88%;">
                            <input type="checkbox" name="newThreeMedicalTypeAll"
                                   onclick="clickAll('newThreeMedicalType')">
                            <label>全选</label>
                            <ul class="f_left" id="medical_ul">
                                <c:forEach items="${newMedicalTypes}" var="mt">
                                    <c:set var="contains" value="false"/>
                                    <%--<c:if test="${fn:contains(mt.relatedField,3)}">--%>
                                    <c:if test="${not empty newThree}">
                                        <c:forEach items="${newThree}" var="mc" varStatus="status">
                                            <c:if test="${mt.sysOptionDefinitionId eq mc.medicalCategoryId}">
                                                <c:set var="contains" value="true"/>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                    <c:choose>
                                        <c:when test="${contains == true }">
                                            <li style="width:25%;float:left;">
                                                <input type="checkbox" name="newThreeMedicalType"
                                                       value="${mt.sysOptionDefinitionId }" checked="checked"
                                                       onclick="clickOne('threeMedicalType')">
                                                <label>${mt.title}</label>
                                            </li>
                                        </c:when>
                                        <c:otherwise>
                                            <li style="width:25%;float:left;">
                                                <input type="checkbox" name="newThreeMedicalType"
                                                       value="${mt.sysOptionDefinitionId }"
                                                       onclick="clickOne('threeMedicalType')">
                                                <label>${mt.title}</label>
                                            </li>
                                        </c:otherwise>
                                    </c:choose>
                                    <%--</c:if>--%>
                                </c:forEach>
                            </ul>


                        </div>
                    </li>
                    <li>
                        <div class="infor_name sex_name" style="width: 150px">
                            <label class="mt1">备注</label>
                        </div>
                        <div class="f_left medicalquality overflow-hidden" style="width:88%;">
                            <input type="text" style="width: 80%" name="comment" value="${comment}"/>
                        </div>
                    </li>
                </c:if>
                <c:if test="${traderCustomer.customerNature eq 466}">
                    <li>
                        <div class="infor_name sex_name" style="width: 150px">
                            <label>医疗机构执业许可证</label>
                        </div>
                        <div class="f_left insertli ">
                            <ul id="practice" style="margin-right:-10px;]">
                                <li style="margin-bottom:0;">
                                    <c:choose>
                                        <c:when test="${!empty practiceList }">
                                            <c:forEach items="${practiceList }" var="practice" varStatus="st">
                                                <c:if test="${st.index == 0}">
                                                    <c:set var="beginTime" value="${practice.begintime}"></c:set>
                                                    <c:set var="endTime" value="${practice.endtime}"></c:set>
                                                    <c:set var="sn" value="${practice.sn}"></c:set>
                                                </c:if>
                                                <div class="mb8">
                                                    <div class="pos_rel f_left">
                                                        <input type="file" class="upload_file" name="lwfile"
                                                               id="file_4_${st.index}" style="display: none;"
                                                               onchange="uploadFile(this,4);"/>
                                                        <c:choose>
                                                            <c:when test="${st.index == 0 }">
                                                                <input type="text" class="input-middle" id="name_4"
                                                                       readonly="readonly"
                                                                       placeholder="请上传医疗机构执业许可证" name="practiceName"
                                                                       onclick="file_4_${st.index}.click();"
                                                                       value="${practice.name}">
                                                                <input type="hidden" id="uri_4" name="practiceUri"
                                                                       value="${practice.uri}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="text" class="input-middle"
                                                                       id="name_4_${st.index}" readonly="readonly"
                                                                       placeholder="请上传医疗机构执业许可证" name="name_4"
                                                                       onclick="file_4_${st.index}.click();"
                                                                       value="${practice.name}">
                                                                <input type="hidden" id="uri_4_${st.index}" name="uri_4"
                                                                       value="${practice.uri}">
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <label class="bt-bg-style bt-middle bg-light-blue ml10"
                                                               id="twoUpload"
                                                               onclick="return $('#file_4_${st.index}').click();">浏览</label>
                                                    </div>

                                                    <c:choose>
                                                        <c:when test="${practice.uri ne null && practice.uri ne ''}">
                                                            <div class="f_left">
                                                                <i class="iconsuccesss ml7" id="img_icon_4"></i>
                                                                <a href="http://${practice.domain}${practice.uri}"
                                                                   target="_blank"
                                                                   class="font-blue cursor-pointer mr5 ml10 mt4"
                                                                   id="img_view_4">查看</a>
                                                                <c:choose>
                                                                    <c:when test="${st.index == 0 }">
                                                                        <span class="font-red cursor-pointer mt4"
                                                                              onclick="del(4)" id="img_del_4">删除</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="font-red cursor-pointer mt4"
                                                                              onclick="delAttachment(this)"
                                                                              id="img_del_4">删除</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="f_left">
                                                                <i class="iconsuccesss ml7 none" id="img_icon_4"></i>
                                                                <a href="http://${practice.domain}${practice.uri}"
                                                                   target="_blank"
                                                                   class="font-blue cursor-pointer mr5 ml10 mt4 none"
                                                                   id="img_view_4">查看</a>
                                                                <c:choose>
                                                                    <c:when test="${st.index == 0 }">
                                                                        <span class="font-red cursor-pointer mt4 none"
                                                                              onclick="del(4)" id="img_del_4">删除</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <span class="font-red cursor-pointer mt4 none"
                                                                              onclick="delAttachment(this)"
                                                                              id="img_del_4">删除</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <div class="clear"></div>
                                                </div>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="mb8">
                                                <div class="f_left">
                                                    <input type="file" class="upload_file" name="lwfile" id="file_4"
                                                           style="display: none;" onchange="uploadFile(this,4);"/>
                                                    <input type="text" class="input-middle" id="name_4"
                                                           readonly="readonly"
                                                           placeholder="请上传医疗机构执业许可证" name="practiceName"
                                                           onclick="file_4.click();" value="${practice.name}">
                                                    <input type="hidden" id="uri_4" name="practiceUri"
                                                           value="${practice.uri}">
                                                    <div class="font-red " style="display: none;">请上传医疗机构执业许可证</div>
                                                    <label class="bt-bg-style bt-middle bg-light-blue ml10"
                                                           id="twoUpload"
                                                           onclick="return $('#file_4').click();">浏览</label>
                                                </div>
                                                <!-- 上传成功出现 -->
                                                <div class="f_left">
                                                    <c:choose>
                                                        <c:when test="${!empty practice.uri}">
                                                            <i class="iconsuccesss ml7" id="img_icon_4"></i>
                                                            <a href="http://${practice.domain}${practice.uri}"
                                                               target="_blank"
                                                               class="font-blue cursor-pointer mr5 ml10 mt4"
                                                               id="img_view_4">查看</a>
                                                            <span class="font-red cursor-pointer mt4" onclick="del(4)"
                                                                  id="img_del_4">删除</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <i class="iconsuccesss ml7 none" id="img_icon_4"></i>
                                                            <a href="" target="_blank"
                                                               class="font-blue cursor-pointer mr5 ml10 mt4 none"
                                                               id="img_view_4">查看</a>
                                                            <span class="font-red cursor-pointer mt4 none"
                                                                  onclick="del(4)" id="img_del_4">删除</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="clear" id="conadd4">
                                        <span class="bt-border-style bt-small border-blue mt8"
                                              onclick="con_add(4,'请上传医疗机构执业许可证')">继续添加</span>
                                    </div>
                                </li>

                                <li style="margin-bottom:0;">
                                    <label class='f_left mt4 mr10'>设置有效期</label>
                                    <div class="f_left ">
                                        <div class='inputfloat'>
                                            <input class="Wdate input-smaller" type="text" placeholder="请选择日期"
                                                   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'practiceEndTime\')}'})"
                                                   name="practiceStartTime"
                                                   id="practiceStartTime"
                                                   value='<date:date value ="${beginTime} " format="yyyy-MM-dd"/>'/>
                                            <input class="Wdate input-smaller" type="text" placeholder="请选择日期"
                                                   onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'practiceStartTime\')}'})"
                                                   name="practiceEndTime"
                                                   id="practiceEndTime"
                                                   value='<date:date value ="${endTime} " format="yyyy-MM-dd"/>'/>
                                        </div>
                                        <div class="font-red " style="display: none;">开始时间不能为空</div>
                                    </div>
                                </li>
                                <li class="specialli" style="margin-bottom:0;">
                                    <label class="f_left mt4 mr10">许可证编号</label>
                                    <div class="f_left ">
                                        <input type="text" name="practiceSn" class="input-middle" value="${sn}"
                                               id="practiceSn"/>
                                        <div class="font-red " style="display: none;"></div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </li>
                </c:if>

            </ul>
            <div class="font-grey9 ml120 line20">
                友情提醒
                <br/> 1、上传文件格式可以是JPG、PNG、JPEG、BMP等格式；
                <br/>2、上传文件不要超过5MB；
                <br/>3、结束日期可以不填写，代表资质没有结束日期；
                <br/> 4、三证合一和医疗资质合一的客户无需再设置其他信息；
            </div>
            <div class="add-tijiao tcenter mt20">
                <c:if test="${isbelong and (traderCustomer.aptitudeStatus==null or traderCustomer.aptitudeStatus!=0)}">
                    <button type="submit" id='submit'>提交审核</button>
                </c:if>
                <c:if test="${traderCustomer.aptitudeStatus==0 and candidateUserMap['belong']}">
                    <span  class="pop-new-data J-pass" style="display:none;" layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?traderCustomerId=${traderCustomer.traderCustomerId}&taskId=${taskInfo.id}&pass=true&type=2"}'>
                    </span>
                    <span class="bt-bg-style bg-light-green bt-small mr10 J-layer" data-type="pass">审核通过</span>
                    <span class="bt-bg-style bg-light-orange bt-small mr10 J-layer" data-type="refuse">审核不通过</span>
                    <span  class="pop-new-data J-refuse" style="display:none;"
                           layerParams='{"width":"500px","height":"180px","title":"操作确认","link":"./complement.do?traderCustomerId=${traderCustomer.traderCustomerId}&taskId=${taskInfo.id}&pass=false&type=2"}'>
                        </span>
                </c:if>
            </div>
            <input type="hidden" name="busTraderCertificateId" value="${business.traderCertificateId}">
            <input type="hidden" name="taxTraderCertificateId" value="${tax.traderCertificateId}">
            <input type="hidden" name="orgaTraderCertificateId" value="${orga.traderCertificateId}">
            <input type="hidden" name="twoTraderCertificateId" value="${twoMedical.traderCertificateId}">
            <input type="hidden" name="threeTraderCertificateId" value="${twoMedicalVo.traderCertificateId}">
            <input type="hidden" name="practiceTraderCertificateId" value="${practice.traderCertificateId}">
            <input type="hidden" name="productTraderCertificateId" value="${product.traderCertificateId}">
            <input type="hidden" name="traderId" value="${traderCustomer.traderId}">
            <input type="hidden" name="traderCustomerId" value="${traderCustomer.traderCustomerId}">
            <input type="hidden" name="customerNature" id="customerNature" value="${traderCustomer.customerNature}">
            <input type="hidden" name="customerAptitude" id="customerAptitude" value="${traderCustomer.aptitudeStatus}">
            <input type="hidden" name="traderType" value="1">
            <input type="hidden" name="beforeParams" value='${beforeParams}'>
            <input type="hidden" name="taskId" value='${taskInfo.id}'>
            <input type="hidden" id="threeInOne" value='${threeInOne}'>
            <input type="hidden" name="formToken" value="${formToken}"/>
        </form>
    </div>
</div>
</body>
<script type="text/javascript" src="<%= basePath %>static/js/customer/edit_aptitude.js?rnd=<%=Math.random()%>"></script>
</html>

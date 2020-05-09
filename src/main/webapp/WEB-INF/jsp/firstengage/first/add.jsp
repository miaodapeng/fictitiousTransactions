<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        <c:if test="${null ne firstEngage.firstEngageId }">
           编辑首营信息
        </c:if>
        <c:if test="${null eq firstEngage.firstEngageId }">
           新增首营信息
        </c:if>
    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/firstengage/first/add.css?rnd=<%=Math.random()%>">
</head>

<body>
    <form action="${pageContext.request.contextPath}/firstengage/baseinfo/addFirstEngageInfo.do" id="form_submit" class="J-form" method="POST">
        <input type="hidden" name="firstEngageId" value="${firstEngage.firstEngageId }">
        <input type="hidden" name="formToken" value="${formToken}" />
        <div class="form-wrap">
            <div class="form-container base-form form-span-8">
                <c:if test="${null ne firstEngage.firstEngageId }">
                    <div class="form-title">编辑首营信息</div>
                </c:if>
                <c:if test="${null eq firstEngage.firstEngageId }">
                    <div class="form-title">新增首营信息</div>
                </c:if>
                <!-- 后台报错的区域 -->
                <c:forEach var="error" items="${firstEngage.errors}" varStatus="status">
                    <div class="vd-tip tip-red">
                        <i class="vd-tip-icon vd-icon icon-error2"></i>
                        <div class="vd-tip-cnt">${error}</div>
                    </div>
                </c:forEach>
                <div class="form-block">
                    <div class="form-block-title">注册证信息</div>
                    <div class="form-cnt J-block-1">
                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>注册证号/备案凭证号：</div>
                            <div class="form-fields">
                                <div class="form-col col-6">
                                    <input type="text" name="registration.registrationNumber" autocomplete="off" placeholder="请选择或输入注册证号/备案凭证号" class="input-text J-suggest-input" valid-max="128" value="${firstEngage.registration.registrationNumber }">
                                    <input type="hidden" name="registration.registrationNumberId" value="${firstEngage.registration.registrationNumberId }">
                                </div>
                            </div>
                        </div>

                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>管理类别：</div>
                            <div class="form-fields">
                                <div class="input-radio">
                                    <c:set var="list" value="${sysOptionMap['1090']}" />
                                    <c:forEach var="systemOption" items="${list }">
                                        <label class="input-wrap">
                                            <c:if test="${systemOption.sysOptionDefinitionId eq firstEngage.registration.manageCategoryLevel }">
                                                <input type="radio" name="registration.manageCategoryLevel" checked value="${systemOption.sysOptionDefinitionId }">
                                            </c:if>
                                            <c:if test="${systemOption.sysOptionDefinitionId ne firstEngage.registration.manageCategoryLevel }">
                                                <input type="radio" name="registration.manageCategoryLevel" value="${systemOption.sysOptionDefinitionId }">
                                            </c:if>
                                            <span class="input-ctnr"></span>${systemOption.title }
                                        </label>
                                    </c:forEach>
                                </div>
                                <div class="feedback-block" wrapfor="registration.manageCategoryLevel"></div>
                            </div>
                        </div>

                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>生产企业（中文注册人名称）：</div>
                            <div class="form-fields">
                                <div class="form-col col-6">
                                    <input type="text" name="registration.productCompany.productCompanyChineseName" class="input-text" valid-max="128"  value="${firstEngage.registration.productCompany.productCompanyChineseName }">
                                    <input type="hidden" name="registration.productCompany.productCompanyId" class="input-text">
                                </div>
                            </div>
                        </div>

                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>生产企业生产许可证号或备案凭证编号：</div>
                            <div class="form-fields">
                                <div class="form-col col-6">
                                    <input type="text" name="registration.productCompany.productCompanyLicence" class="input-text" valid-max="50"  value="${firstEngage.registration.productCompany.productCompanyLicence }">
                                </div>
                            </div>
                        </div>

                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>批准日期：</div>
                            <div class="form-fields">
                                <div class="form-col span-6 input-date">
                                    <input type="text" name="registration.issuingDateStr" placeholder="请选择" readonly class="input-text J-date J-date-1" value="${firstEngage.registration.issuingDateStr }">
                                </div>
                            </div>
                        </div>

                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>有效期至：</div>
                            <div class="form-fields">
                                <div class="form-col span-6 input-date">
                                    <input type="text" class="input-text J-date J-date-2" name="registration.effectiveDateStr" placeholder="请选择" readonly value="${firstEngage.registration.effectiveDateStr }">
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="form-cnt form-cnt-optional J-optional J-block-1">
                        <div class="form-item">
                            <div class="form-label">生产企业（英文注册人名称）：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <input type="text" name="registration.productCompany.productCompanyEnglishName" class="input-text" valid-max="128" value="${firstEngage.registration.productCompany.productCompanyEnglishName }">
                                </div>
                            </div>
                        </div>

                        <div class="form-item">
                            <div class="form-label">生产地址：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <input type="text" name="registration.productionAddress" valid-max="256" class="input-text" value="${firstEngage.registration.productionAddress }">
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">企业地址（注册人住所）：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <input type="text" name="registration.productCompany.productCompanyAddress" valid-max="256" class="input-text" value="${firstEngage.registration.productCompany.productCompanyAddress }">
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">产品名称（中文）：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <input type="text" name="registration.productChineseName" valid-max="256" placeholder="请填写产品名称，以“、”区分" class="input-text" value="${firstEngage.registration.productChineseName }">
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">产品名称（英文）：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <input type="text" name="registration.productEnglishName" valid-max="256" placeholder="请填写产品名称，以“、”区分" class="input-text" value="${firstEngage.registration.productEnglishName }">
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">审批部门：</div>
                            <div class="form-fields">
                                <div class="form-col col-4">
                                    <input type="text" name="registration.approvalDepartment" valid-max="128" class="input-text" value="${firstEngage.registration.approvalDepartment }">
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">规格、型号：</div>
                            <div class="form-fields">
                                <div class="form-col col-4">
                                    <input type="text" name="registration.model" valid-max="256" placeholder="请填写型号、规格，以“、”区分" class="input-text" value="${firstEngage.registration.model }">
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">代理人名称：</div>
                            <div class="form-fields">
                                <div class="form-col col-4">
                                    <input type="text" name="registration.registeredAgent" valid-max="256" class="input-text" value="${firstEngage.registration.registeredAgent }">
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">代理人住所：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <input type="text" name="registration.registeredAgentAddress" valid-max="256" class="input-text" value="${firstEngage.registration.registeredAgentAddress }">
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">注册商标：</div>
                            <div class="form-fields">
                                <div class="form-col col-4">
                                    <input type="text" name="registration.trademark" valid-max="64" class="input-text" value="${firstEngage.registration.trademark }">
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">邮编：</div>
                            <div class="form-fields">
                                <div class="form-col col-2">
                                    <input type="text" name="registration.zipCode" valid-max="32" class="input-text" value="${firstEngage.registration.zipCode }">
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">结构与组成：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <textarea name="registration.proPerfStruAndComp" valid-max="1024" class="input-textarea">${firstEngage.registration.proPerfStruAndComp }</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">适用范围：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <textarea name="registration.productUseRange" valid-max="1024" class="input-textarea">${firstEngage.registration.productUseRange }</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">其他内容：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <textarea name="registration.otherContents" valid-max="1024" class="input-textarea">${firstEngage.registration.otherContents }</textarea>

                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">备注：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <textarea name="registration.remarks" valid-max="1024" class="input-textarea">${firstEngage.registration.remarks }</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">产品标准：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <textarea name="registration.productStandards" valid-max="1024" class="input-textarea">${firstEngage.registration.productStandards }</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">预期用途（体外诊断试剂）：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <textarea name="registration.expectedUsage" valid-max="1024" class="input-textarea">${firstEngage.registration.expectedUsage }</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">主要组成成分（体外诊断试剂）：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <textarea name="registration.mainProPerfStruAndComp" valid-max="1024" class="input-textarea">${firstEngage.registration.mainProPerfStruAndComp }</textarea>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">变更日期：</div>
                            <div class="form-fields">
                                <div class="form-col span-6 input-date">
                                    <input type="text" name="registration.changeDateStr" class="input-text J-date" placeholder="请选择" readonly value="${firstEngage.registration.changeDateStr }">
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">变更情况：</div>
                            <div class="form-fields">
                                <div class="form-col col-8">
                                    <textarea name="registration.changeContents" valid-max="1024" class="input-textarea">${firstEngage.registration.changeContents }</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-optional J-toggle-show">
                        <span class="toggle-txt J-more">展开更多选填信息<i class="vd-icon icon-down"></i></span>
                        <span class="toggle-txt J-less" style="display: none;">收起选填信息<i class="vd-icon icon-up"></i></span>
                    </div>
                </div>
                <div class="form-block">
                    <div class="form-block-title">首营信息</div>
                    <div class="form-cnt">
                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>商品类型：</div>
                            <div class="form-fields J-prod-type">
                                <div class="input-radio">
                                    <c:set var="typeList" value="${sysOptionMap['1035']}" />
                                    <c:forEach var="goodsType" items="${typeList }">
                                        <c:if test="${goodsType.sysOptionDefinitionId eq 316}">
                                            <label class="input-wrap">
                                                <c:if test="${firstEngage.goodsType eq 316}">
                                                    <input type="radio" name="goodsType" checked value="316">
                                                </c:if>
                                                <c:if test="${firstEngage.goodsType ne 316}">
                                                    <input type="radio" name="goodsType" value="316">
                                                </c:if>
                                                <span class="input-ctnr"></span>${goodsType.title }
                                            </label>
                                        </c:if>
                                        <c:if test="${goodsType.sysOptionDefinitionId eq 317}">
                                            <label class="input-wrap">
                                                <c:if test="${firstEngage.goodsType eq 317}">
                                                    <input type="radio" name="goodsType" checked value="317">
                                                </c:if>
                                                <c:if test="${firstEngage.goodsType ne 317}">
                                                    <input type="radio" name="goodsType" value="317">
                                                </c:if>
                                                <span class="input-ctnr"></span>${goodsType.title }
                                            </label>
                                        </c:if>
                                        <c:if test="${goodsType.sysOptionDefinitionId eq 318}">
                                            <label class="input-wrap">
                                                <c:if test="${firstEngage.goodsType eq 318}">
                                                    <input type="radio" name="goodsType" checked value="318">
                                                </c:if>
                                                <c:if test="${firstEngage.goodsType ne 318}">
                                                    <input type="radio" name="goodsType" value="318">
                                                </c:if>
                                                <span class="input-ctnr"></span>${goodsType.title }
                                            </label>
                                        </c:if>
                                    </c:forEach>
                                </div>
                                <div class="feedback-block" wrapfor="goodsType"></div>
                            </div>
                        </div>
                        <div class="form-item ignore J-prod-date" style="display: none;">
                            <div class="form-label"><span class="must">*</span>产品有效期限：</div>
                            <div class="form-fields prod-date-wrap">
                                <div class="form-col col-2">
                                    <input type="text" name="effectiveDays" valid-max="5" class="input-text" value="${firstEngage.effectiveDays }">
                                </div>
                                <select name="effectiveDayUnit" class="J-select">
                                    <option <c:if test="${firstEngage.effectiveDayUnit eq 1}">selected</c:if> value="1">天</option>
                                    <option <c:if test="${firstEngage.effectiveDayUnit eq 2}">selected</c:if> value="2">月</option>
                                    <option <c:if test="${firstEngage.effectiveDayUnit eq 3}">selected</c:if> value="3">年</option>
                                </select>
                                <div class="feedback-block" wrapfor="effectiveDate"></div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>商品品牌：</div>
                            <div class="form-fields">
                                <div class="input-radio">
                                    <label class="input-wrap">
                                        <c:if test="${firstEngage.brandType eq 1 }">
                                            <input type="radio" name="brandType" checked id="" value="1">
                                        </c:if>
                                        <c:if test="${firstEngage.brandType ne 1 }">
                                            <input type="radio" name="brandType" id="" value="1">
                                        </c:if>
                                        <span class="input-ctnr"></span>国产品牌
                                    </label>
                                    <label class="input-wrap">
                                        <c:if test="${firstEngage.brandType eq 2 }">
                                            <input type="radio" name="brandType" checked id="" value="2">
                                        </c:if>
                                        <c:if test="${firstEngage.brandType ne 2 }">
                                            <input type="radio" name="brandType" id="" value="2">
                                        </c:if>
                                        <span class="input-ctnr"></span>进口品牌
                                    </label>
                                </div>
                                <div class="feedback-block" wrapfor="brandType"></div>
                            </div>
                        </div>
                        <div class="form-item J-inter-type">
                            <div class="form-label"><span class="must">*</span>国标类型：</div>
                            <div class="form-fields">
                                <div class="input-radio">
                                    <label class="input-wrap">
                                        <c:if test="${firstEngage.standardCategoryType eq 2 }">
                                            <input type="radio" name="standardCategoryType" checked value='2'>
                                        </c:if>
                                        <c:if test="${firstEngage.standardCategoryType ne 2 }">
                                            <input type="radio" name="standardCategoryType" value='2'>
                                        </c:if>
                                        <span class="input-ctnr"></span>旧国标
                                    </label>
                                    <label class="input-wrap">
                                        <c:if test="${firstEngage.standardCategoryType eq 1 }">
                                            <input type="radio" name="standardCategoryType" checked value="1">
                                        </c:if>
                                        <c:if test="${firstEngage.standardCategoryType ne 1 }">
                                            <input type="radio" name="standardCategoryType" value="1">
                                        </c:if>
                                        <span class="input-ctnr"></span>新国标
                                    </label>
                                </div>
                                <div class="feedback-block" wrapfor="standardCategoryType"></div>
                            </div>
                        </div>
                        <div class="form-item ignore J-inter-new" style="display: none;">
                            <div class="form-label"><span class="must">*</span>新国标分类：</div>
                            <div class="form-fields">
                                <div class="select J-lv-search-select">
                                    <div class="select-title">
                                        <div class="select-selected">
                                            <span class="J-text">请选择</span>
                                        </div>
                                        <div class="select-arrow">
                                            <i class="vd-icon icon-down"></i>
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" name="newStandardCategoryId" class="J-inter-value" value="${firstEngage.newStandardCategoryId }">
                            </div>
                        </div>
                        <div class="form-item ignore J-inter-old" style="display: none;">
                            <div class="form-label"><span class="must">*</span>旧国标分类：</div>
                            <div class="form-fields">
                                <div class="J-old-wrap"></div>
                                <input type="hidden" name="oldStandardCategoryId" class="J-old-value" value="${firstEngage.oldStandardCategoryId }">
                            </div>
                        </div>



                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>存储条件1：</div>
                            <div class="form-fields">
                                <div class="input-radio">
                                    <label class="input-wrap">
                                        <input type="radio" name="conditionOne" <c:if test="${firstEngage.conditionOne eq 983}">checked</c:if> value='983'>
                                        <span class="input-ctnr"></span>常温
                                    </label>
                                    <label class="input-wrap">
                                        <input type="radio" name="conditionOne" <c:if test="${firstEngage.conditionOne eq 984}">checked</c:if> value='984'>
                                        <span class="input-ctnr"></span>冷藏
                                    </label>
                                    <label class="input-wrap">
                                        <input type="radio" name="conditionOne" <c:if test="${firstEngage.conditionOne eq 985}">checked</c:if> value='985'>
                                        <span class="input-ctnr"></span>冷冻
                                    </label>
                                </div>
                                <div class="feedback-block" wrapfor="conditionOne"></div>
                            </div>
                        </div>

                        <div class="form-item J-temp-wrap" style="display: none;">
                            <div class="form-label"></div>
                            <div class="form-fields">
                                <div class="input-checkbox">
                                    <label>温度&nbsp;<input type="text" name="temperature" valid-max="10" class="input-text" value="${firstEngage.temperature }">&nbsp;摄氏度</label>
                                </div>
                                <div class="feedback-block" wrapfor="temperature"></div>
                            </div>
                        </div>

                        <div class="form-item">
                            <div class="form-label">存储条件2：</div>
                            <div class="form-fields">
                                <div class="input-checkbox">

                                    <c:if test="${not empty firstEngage.storageCondition}">
                                        <label class="input-wrap">
                                            <input type="checkbox" <c:forEach var="condition" items="${firstEngage.storageCondition }">
                                            <c:if test="${'阴凉' eq condition.name }">checked</c:if>
                                            </c:forEach>
                                            name="storageCondition[0].name" value="阴凉">
                                            <span class="input-ctnr"></span>阴凉
                                        </label>

                                        <label class="input-wrap">
                                            <input type="checkbox" <c:forEach var="condition" items="${firstEngage.storageCondition }">
                                            <c:if test="${'干燥' eq condition.name }">checked</c:if>
                                            </c:forEach>
                                            name="storageCondition[1].name" value="干燥">
                                            <span class="input-ctnr"></span>干燥
                                        </label>

                                        <label class="input-wrap">
                                            <input type="checkbox" <c:forEach var="condition" items="${firstEngage.storageCondition }">
                                            <c:if test="${'避光' eq condition.name }">checked</c:if>
                                            </c:forEach>
                                            name="storageCondition[2].name" value="避光">
                                            <span class="input-ctnr"></span>避光
                                        </label>

                                        <label class="input-wrap">
                                            <input type="checkbox" <c:forEach var="condition" items="${firstEngage.storageCondition }">
                                            <c:if test="${'防潮' eq condition.name }">checked</c:if>
                                            </c:forEach>
                                            name="storageCondition[3].name" value="防潮">
                                            <span class="input-ctnr"></span>防潮
                                        </label>
                                    </c:if>


                                    <c:if test="${empty firstEngage.storageCondition}">
                                        <label class="input-wrap">
                                            <input type="checkbox" name="storageCondition[0].name" value="阴凉">
                                            <span class="input-ctnr"></span>阴凉
                                        </label>

                                        <label class="input-wrap">
                                            <input type="checkbox" name="storageCondition[1].name" value="干燥">
                                            <span class="input-ctnr"></span>干燥
                                        </label>

                                        <label class="input-wrap">
                                            <input type="checkbox" name="storageCondition[2].name" value="避光">
                                            <span class="input-ctnr"></span>避光
                                        </label>

                                        <label class="input-wrap">
                                            <input type="checkbox" name="storageCondition[3].name" value="防潮">
                                            <span class="input-ctnr"></span>防潮
                                        </label>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-block">
                    <div class="form-block-title">其他信息 <span class="form-block-title-tip">图上传支持上传不超过5M的JPG、PNG、JEPG格式。</span></div>
                    <div class="form-cnt">
                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>注册证附件/备案凭证附件：</div>
                            <div class="form-fields">
                                <div class="J-upload"></div>
                                <input type="hidden" class="J-upload-data" value='${zczMapList}'>
                                <div class="form-fields-tip">-最多上传5张。</div>
                                <div class="feedback-block" wrapfor="upload0"></div>
                                <div class="feedback-block J-upload-error">
                                    <label class="error" for="" style="display: none;"></label>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>营业执照：</div>
                            <div class="form-fields">
                                <div class="J-upload"></div>
                                <input type="hidden" class="J-upload-data" value='${yzMapList}'>
                                <div class="form-fields-tip">-最多上传5张。</div>
                                <div class="feedback-block" wrapfor="upload1"></div>
                                <div class="feedback-block J-upload-error">
                                    <label class="error" for="" style="display: none;"></label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-cnt form-cnt-optional J-optional">
                        <div class="form-item">
                            <div class="form-label">说明书：</div>
                            <div class="form-fields">
                                <div class="J-upload"></div>
                                <input type="hidden" class="J-upload-data" value='${smsMapList}'>
                                <div class="form-fields-tip">-最多上传5张。</div>
                                <div class="feedback-block J-upload-error">
                                    <label class="error" for="" style="display: none;"></label>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">生产企业卫生许可证：</div>
                            <div class="form-fields">
                                <div class="J-upload"></div>
                                <input type="hidden" class="J-upload-data" value='${wsMapList}'>
                                <div class="form-fields-tip">-最多上传5张。</div>
                                <div class="feedback-block J-upload-error">
                                    <label class="error" for="" style="display: none;"></label>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">生产企业生产许可证：</div>
                            <div class="form-fields">
                                <div class="J-upload"></div>
                                <input type="hidden" class="J-upload-data" value='${scMapList}'>
                                <div class="form-fields-tip">-最多上传5张。</div>
                                <div class="feedback-block J-upload-error">
                                    <label class="error" for="" style="display: none;"></label>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">商标注册证：</div>
                            <div class="form-fields">
                                <div class="J-upload"></div>
                                <input type="hidden" class="J-upload-data" value='${sbMapList}'>
                                <div class="form-fields-tip">-最多上传5张。</div>
                                <div class="feedback-block J-upload-error">
                                    <label class="error" for="" style="display: none;"></label>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">注册登记表附件：</div>
                            <div class="form-fields">
                                <div class="J-upload"></div>
                                <input type="hidden" class="J-upload-data" value='${djbMapList}'>
                                <div class="form-fields-tip">-最多上传5张。</div>
                                <div class="feedback-block J-upload-error">
                                    <label class="error" for="" style="display: none;"></label>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">产品图片（单包装/大包装）：</div>
                            <div class="form-fields">
                                <div class="J-upload"></div>
                                <input type="hidden" class="J-upload-data" value='${cpMapList}'>
                                <div class="form-fields-tip">-最多上传5张。</div>
                                <div class="feedback-block J-upload-error">
                                    <label class="error" for="" style="display: none;"></label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-optional J-toggle-show">
                        <span class="toggle-txt J-more">展开更多选填信息<i class="vd-icon icon-down"></i></span>
                        <span class="toggle-txt J-less" style="display: none;">收起选填信息<i class="vd-icon icon-up"></i></span>
                    </div>
                </div>
                <div class="form-btn">
                    <div class="form-item">
                        <div class="form-fields">
                            <button class="btn btn-blue btn-large" type="submit">保存</button>
                            <c:if test="${null ne firstEngage.firstEngageId }">
                                <a href="./getFirstSearchDetail.do?firstEngageId=${firstEngage.firstEngageId }" class="btn btn-large">取消</a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <script type="text/json" class="J-old-data">
        ${oldStandCategoryList}
    </script>

    <script type="text/json" class="J-new-data">
        ${newStandCategoryList}
    </script>


    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/pikaday.2.1.0.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/util.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/select.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/upload.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/lv-select.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/inputSuggest.js?rnd=<%=Math.random()%>"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/dialogSearch.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/suggestSelect.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/firstengage/first/add.js?rnd=<%=Math.random()%>"></script>
</body>

</html>
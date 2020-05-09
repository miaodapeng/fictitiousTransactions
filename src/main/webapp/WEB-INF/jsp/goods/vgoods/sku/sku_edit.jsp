<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>
        <c:if test="${command.skuId ==null or command.skuId <=0}">新增SKU</c:if>
        <c:if test="${command.skuId >0}">修改SKU</c:if>

    </title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/vgoods/sku/sku_edit.css?rnd=<%=Math.random()%>">
</head>

<body>

    <form action="./saveSku.do" id="form_submit" class="J-form" method="POST">
        <input type="hidden" name="formToken" value="${formToken}">
        <input type="hidden" name="skuId" value="${command.skuId}">
        <input type="hidden" name="spuId" value="${command.spuId}">

        <!--1 器械  2 耗材-->
        <input type="hidden" name="skuType" class="J-sku-type" value="${command.skuType}">
        <div class="form-wrap">
            <div class="form-container base-form form-span-7">
                <div class="form-title">
                    <c:if test="${command.skuId ==null or command.skuId <=0}">新增SKU</c:if>
                    <c:if test="${command.skuId >0}">修改SKU</c:if>
                </div>

                <!-- 后台报错的区域 -->
                <c:forEach var="error" items="${command.errors}" varStatus="status">
                    <div class="vd-tip tip-red">
                        <i class="vd-tip-icon vd-icon icon-error2"></i>
                        <div class="vd-tip-cnt">${error}</div>
                    </div>
                </c:forEach>
                <c:forEach var="tip" items="${command.tips}" varStatus="status">
                    <div class="vd-tip tip-green">
                        <i class="vd-tip-icon vd-icon icon-yes1"></i>
                        <div class="vd-tip-cnt">${tip}</div>
                    </div>
                </c:forEach>
                <!-- end -->
                <div class="form-block form-info">
                    <div class="form-block-title">SPU信息</div>
                    <div class="table-wrap">
                        <table class="table table-base">
                            <colgroup>
                                <col>
                                <col>
                                <col>
                                <col>
                                <col>
                                <col>
                                <col>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>商品名称</th>
                                    <th>商品类型</th>
                                    <th>商品等级</th>
                                    <th>注册证号</th>
                                    <th>审核状态</th>
                                    <th>更新时间</th>
                                    <th>Wiki资料</th>
                                </tr>
                                <tr>
                                    <td class="J-spu-name" data-html="${coreSpuDto.spuShowName}">
                                        <a tabTitle='{"num":"vgoodsview${command.spuId}","link":"/goods/vgoods/viewSpu.do?spuId=${command.spuId}","title":"查看SPU"}' href="javascript:void(0);" class="">${coreSpuDto.spuShowName}</a>
                                    </td>
                                    <td>
                                        <c:forEach var="spuType" items="${spuTypeList}" varStatus="status">
                                            <c:if test="${coreSpuDto.spuType == spuType.sysOptionDefinitionId}"> ${spuType.title} </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>${coreSpuDto.spuLevelShow}</td>
                                    <td>${coreSpuDto.registrationNumber}</td>

                                    <td>${coreSpuDto.checkStatusShow}
                                    </td>

                                    <td>${coreSpuDto.modTimeShow}</td>
                                    <td>
                                        <c:if test="${not empty coreSpuDto.wikiHref}">
                                        <a href="${coreSpuDto.wikiHref}" target="_blank">查看Wiki</a>
                                        </c:if>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="form-block">
                    <div class="form-block-title">基本信息 </div>
                    <c:if test="${command.skuId>0}">
                        <div class="form-item">
                            <div class="form-label">订货号：</div>
                            <div class="form-fields">
                                <input type="hidden" name="skuNo" value="${skuGenerate.skuNo}">
                                <div class="fields-label">${skuGenerate.skuNo}</div>
                            </div>
                        </div>
                    </c:if>
                    <div class="form-item">
                        <div class="form-label">
                            <c:if test="${command.skuType==1}"> <span class="must">*</span> </c:if> 制造商型号：
                        </div>
                        <div class="form-fields">
                            <div class="form-col col-6">
                                <input type="text" maxlength="50"  class='input-text <c:if test="${command.skuType==1}">J-model</c:if>' name="model" value="${skuGenerate.model}">
                                <div class="form-fields-tip">
                                    - 由于开票原因,制造商型号不能超过40个字符。<br>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">
                            <c:if test="${command.skuType!=1}"> <span class="must">*</span> </c:if>规格：
                        </div>
                        <div class="form-fields">
                            <div class="form-col col-6">
                                <input   type="text" maxlength="50" class='input-text <c:if test="${command.skuType!=1}">J-model</c:if>' name="spec" value="${skuGenerate.spec}">
                                <div class="form-fields-tip">
                                    - 由于开票原因，规格不能超过40个字符。<br>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>商品名称：</div>
                        <div class="form-fields">
                            <div class="form-col col-6">
                                <input type="text"  maxlength="70"  class="input-text J-prod-name" name="skuName" value="${skuGenerate.skuName}">
                                <div class="form-fields-tip">
                                    - 由于开票原因，商品名称不能输入 * < > ',且不能超过68个字符。<br>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">物料编码：</div>
                        <div class="form-fields">
                            <div class="form-col col-6">
                                <input type="text" class="input-text" name="materialCode" value="${skuGenerate.materialCode}">
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">供应商型号：</div>
                        <div class="form-fields">
                            <div class="form-col col-6">
                                <input type="text" class="input-text" name="supplyModel" value="${skuGenerate.supplyModel}">
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">是否备货：</div>
                        <div class="form-fields">
                            <div class="input-radio">
                                <label class="input-wrap">
                                    <input type="radio" name="isStockup" value="1" <c:if test="${ skuGenerate.isStockup ==1  }"> checked </c:if> >
                                    <span class="input-ctnr"></span>是
                                </label>
                                <label class="input-wrap">
                                    <input type="radio" name="isStockup" value="0" <c:if test="${ skuGenerate.isStockup ==0  }"> checked </c:if> >
                                    <span class="input-ctnr"></span>否
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">Wiki链接：</div>
                        <div class="form-fields">
                            <div class="form-col col-6">
                                <input type="text" class="input-text" name="wikiHref" value="${skuGenerate.wikiHref}">
                            </div>
                        </div>
                    </div>
                    <div class="form-item">
                        <div class="form-label">检测报告：</div>
                        <div class="form-fields">
                            <input type="hidden" class="J-upload-data" value='${command.skuCheckFilesJson}'>
                            <div class="J-upload" type="skuCheckFiles"></div>
                            <div class="form-fields-tip">
                                - 最多上传5张图片；<br>
                                - 检测报告原件扫描件；<br>
                                - 图片格式为JPG、JPEG、PNG、BMP；<br>
                                - 图片大小不超过5M；
                            </div>
                            <div class="feedback-block J-upload-error">
                                <label for="" class="error" style="display: none;"></label>
                            </div>
                        </div>

                    </div>
                    <div class="form-item">
                        <div class="form-label">专利文件：</div>
                        <div class="form-fields">
                            <input type="hidden" class="J-upload-data" value='${command.skuPatentFilesJson}'>
                            <div class="J-upload" type="skuPatentFiles"></div>
                            <div class="form-fields-tip">
                                - 最多上传5张图片；<br>
                                - 专利文件原件扫描件；<br>
                                - 图片格式为JPG、JPEG、PNG、BMP；<br>
                                - 图片大小不超过5M；
                            </div>
                            <div class="feedback-block J-upload-error">
                                <label for="" class="error" style="display: none;"></label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-block">
                    <div class="form-block-title">商品属性</div>
                    <c:forEach items="${baseAttributeVoList}" var="baseAttribute" varStatus="index">

                        <div class="form-item">
                            <div class="form-label">${baseAttribute.baseAttributeName}：</div>
                            <div class="form-fields">
                                <div class="input-radio">
                                    <c:forEach items="${baseAttribute.attrValue}" var="attrValue">
                                        <label class="input-wrap">
                                            <input type="radio" name="baseAttributeValueId[${index.index}]" <c:if test="${ attrValue.selected  }"> checked </c:if> value="${attrValue.baseAttributeValueId}">
                                            <span class="input-ctnr"></span>${attrValue.attrValue}
                                        </label>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </div>

                <c:if test="${command.skuType==1}">
                    <div class="form-block">
                        <div class="form-block-title">参数信息</div>
                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>技术参数：</div>
                            <div class="form-fields J-sort-wrap J-tech-params" data-index="0">
                                <div class="sort-wrap J-sort-list">
                                    <div class="sort-item-wrap th-wrap cf">
                                        <div class="sort-item col-1">排序值</div>
                                        <div class="sort-item col-2">参数名称</div>
                                        <div class="sort-item col-3">参数值</div>
                                    </div>
                                    <c:if test="${not empty command.paramsName1}">
                                        <c:forEach items="${command.paramsName1}" var="params" varStatus="status">
                                            <c:if test="${not empty command.paramsName1[status.index] and not empty command.paramsValue1[status.index]}">
                                                <div class="sort-item-wrap J-sort-item cf">
                                                    <div class="sort-item col-1 item-center">
                                                        <input type="text" name="sort" class="input-text J-sort-num" maxlength="2">
                                                    </div>
                                                    <div class="sort-item col-2">
                                                        <input type="text" name="paramsName1"  value="${command.paramsName1[status.index]}" autocomplete="off" valid-max="30" class="input-text J-sort-name">
                                                        <div class="feedback-block"></div>
                                                    </div>
                                                    <div class="sort-item col-3">
                                                        <input type="text" name="paramsValue1" value="${command.paramsValue1[status.index]}" autocomplete="off" valid-max="30" class="input-text J-sort-value">
                                                    </div>
                                                    <div class="col-1">
                                                        <i class="vd-icon icon-recycle J-sort-del" style="display: inline;"></i>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                    <div class="sort-item-wrap J-sort-item cf">
                                        <div class="sort-item col-1 item-center">
                                            <input type="text" name="sort" class="input-text J-sort-num" maxlength="2">
                                        </div>
                                        <div class="sort-item col-2">
                                            <input type="text" name="paramsName1" autocomplete="off" valid-max="30" class="input-text J-sort-name">
                                            <div class="feedback-block"></div>
                                        </div>
                                        <div class="sort-item col-3">
                                            <input type="text" name="paramsValue1" autocomplete="off" valid-max="30" class="input-text J-sort-value">
                                        </div>
                                        <div class="col-1">
                                            <i class="vd-icon icon-recycle J-sort-del" style="display: inline;"></i>
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" name="paramsValid">
                                <div class="sort-add J-sort-add-option">
                                    <a href="javascript:void(0);" class="sort-add-btn J-sort-add">
                                        <i class="vd-icon icon-add"></i>新增参数
                                    </a>
                                    <a href="javascript:void(0);" class="sort-add-btn J-sort-import">复制文本导入参数</a>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">规格参数：</div>
                            <div class="form-fields J-sort-wrap" data-index="1">
                                <div class="sort-wrap J-sort-list">
                                    <div class="sort-item-wrap th-wrap cf">
                                        <div class="sort-item col-1">排序值</div>
                                        <div class="sort-item col-2">参数名称</div>
                                        <div class="sort-item col-3">参数值</div>
                                    </div>
                                    <c:if test="${not empty command.paramsName2}">
                                        <c:forEach items="${command.paramsName2}" var="params" varStatus="status">
                                            <c:if test="${not empty command.paramsName2[status.index] and not empty command.paramsValue2[status.index]}">
                                                <div class="sort-item-wrap J-sort-item cf">
                                                    <div class="sort-item col-1 item-center">
                                                        <input type="text" name="sort" class="input-text J-sort-num" maxlength="2">
                                                    </div>
                                                    <div class="sort-item col-2">
                                                        <input type="text" name="paramsName2"  value="${command.paramsName2[status.index]}" autocomplete="off" valid-max="30" class="input-text J-sort-name">
                                                        <div class="feedback-block"></div>
                                                    </div>
                                                    <div class="sort-item col-3">
                                                        <input type="text" name="paramsValue2" value="${command.paramsValue2[status.index]}" autocomplete="off" valid-max="30" class="input-text J-sort-value">
                                                    </div>
                                                    <div class="col-1">
                                                        <i class="vd-icon icon-recycle J-sort-del" style="display: inline;"></i>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                    <div class="sort-item-wrap J-sort-item cf">
                                        <div class="sort-item col-1 item-center">
                                            <input type="text" name="sort" class="input-text J-sort-num" maxlength="2">
                                        </div>
                                        <div class="sort-item col-2">
                                            <input type="text" name="paramsName2" autocomplete="off" valid-max="30" class="input-text J-sort-name">
                                            <div class="feedback-block"></div>
                                        </div>
                                        <div class="sort-item col-3">
                                            <input type="text" name="paramsValue2" autocomplete="off" valid-max="30" class="input-text J-sort-value">
                                        </div>
                                        <div class="col-1">
                                            <i class="vd-icon icon-recycle J-sort-del" style="display: inline;"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="sort-add J-sort-add-option">
                                    <a href="javascript:void(0);" class="sort-add-btn J-sort-add">
                                        <i class="vd-icon icon-add"></i>新增参数
                                    </a>
                                    <a href="javascript:void(0);" class="sort-add-btn J-sort-import">复制文本导入参数</a>
                                </div>
                            </div>
                        </div>
                        <div class="form-item">
                            <div class="form-label">性能参数：</div>
                            <div class="form-fields J-sort-wrap" data-index="2">
                                <div class="sort-wrap J-sort-list">
                                    <div class="sort-item-wrap th-wrap cf">
                                        <div class="sort-item col-1">排序值</div>
                                        <div class="sort-item col-2">参数名称</div>
                                        <div class="sort-item col-3">参数值</div>
                                    </div>
                                    <c:if test="${not empty command.paramsName3}">
                                        <c:forEach items="${command.paramsName3}" var="params" varStatus="status">
                                            <c:if test="${not empty command.paramsName3[status.index] and not empty command.paramsValue3[status.index]}">
                                                <div class="sort-item-wrap J-sort-item cf">
                                                    <div class="sort-item col-1 item-center">
                                                        <input type="text" name="sort" class="input-text J-sort-num" maxlength="2">
                                                    </div>
                                                    <div class="sort-item col-2">
                                                        <input type="text" name="paramsName3"  value="${command.paramsName3[status.index]}" autocomplete="off" valid-max="30" class="input-text J-sort-name">
                                                        <div class="feedback-block"></div>
                                                    </div>
                                                    <div class="sort-item col-3">
                                                        <input type="text" name="paramsValue3" value="${command.paramsValue3[status.index]}" autocomplete="off" valid-max="30" class="input-text J-sort-value">
                                                    </div>
                                                    <div class="col-1">
                                                        <i class="vd-icon icon-recycle J-sort-del" style="display: inline;"></i>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                    <div class="sort-item-wrap J-sort-item cf">
                                        <div class="sort-item col-1 item-center">
                                            <input type="text" name="sort" class="input-text J-sort-num" maxlength="2">
                                        </div>
                                        <div class="sort-item col-2">
                                            <input type="text" name="paramsName3" autocomplete="off" valid-max="30" class="input-text J-sort-name">
                                            <div class="feedback-block"></div>
                                        </div>
                                        <div class="sort-item col-3">
                                            <input type="text" name="paramsValue3" autocomplete="off" valid-max="30" class="input-text J-sort-value">
                                        </div>
                                        <div class="col-1">
                                            <i class="vd-icon icon-recycle J-sort-del" style="display: inline;"></i>
                                        </div>
                                    </div>
                                </div>
                                <div class="sort-add J-sort-add-option">
                                    <a href="javascript:void(0);" class="sort-add-btn J-sort-add">
                                        <i class="vd-icon icon-add"></i>新增参数
                                    </a>
                                    <a href="javascript:void(0);" class="sort-add-btn J-sort-import">复制文本导入参数</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>

                <div class="form-block">
                    <div class="form-block-title">物流和包装</div>
                    <div class="form-item">
                        <div class="form-label"><span class="must">*</span>SKU商品单位：</div>
                        <div class="form-fields">
                            <select class="J-select sku-unit-select" name="baseUnitId" id="baseUnitId">
                                <option value="">请选择单位</option>
                                <c:if test="${not empty unitList }">
                                    <c:forEach var="unit" items="${unitList}">
                                        <option value="${unit.unitId}" <c:if test="${ skuGenerate.baseUnitId == unit.unitId  }"> selected
                                </c:if> >${unit.unitName}</option>
                                </c:forEach>
                                </c:if>
                            </select>
                        </div>
                    </div>

                    <%--VDERP-2212 ERP新商品流-新增/编辑SKU（器械设备、配件），增加最小起订量字段--%>
                    <c:if test="${coreSpuDto.spuType == 316 || coreSpuDto.spuType == 1008}">
                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>最小起订量：</div>
                            <div class="form-fields">
                                <div class="form-col col-2">
                                    <input id="minOrder" type="text" class="input-text" name="minOrder" onchange="checkMinOrder()"
                                           value="<fmt:formatNumber  value="${skuGenerate.minOrder}"  pattern="0"/>" placeholder="">
                                    <span style="color:red;" id="minOrderMsg"></span>
                                </div>
                                <div class="form-col col-2">
                                    <span class="sku-unit-value"></span>
                                </div>
                                <div class="feedback-block" wrapfor="minOrder"></div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${command.skuType!=1}">
                        <div class="form-item">
                            <div class="form-label"><span class="must">*</span>商品最小单位：</div>
                            <div class="form-fields">
                                <select class="J-select J-sku-unit" name="unitId" id="unitId">
                                    <option value="">请选择单位</option>
                                    <c:if test="${not empty unitList }">
                                        <c:forEach var="unit" items="${unitList}">
                                            <option value="${unit.unitId}" <c:if test="${ skuGenerate.unitId ==unit.unitId  }"> selected
                                    </c:if> >${unit.unitName}</option>
                                    </c:forEach>
                    </c:if>
                    </select>
                </div>
            </div>

            <div class="form-item">
                <div class="form-label"><span class="must">*</span>内含最小商品数量：</div>
                <div class="form-fields">
                    <div class="form-col col-2">
                        <input type="text" class="input-text" name="changeNum" value="${ skuGenerate.changeNum}" placeholder="">
                    </div>
                    <div class="form-col col-2">
                        <span class="J-sku-unit-value"></span>
                    </div>
                    <div class="feedback-block" wrapfor="changeNum"></div>
                </div>
            </div>

            <div class="form-item">
                <div class="form-label"><span class="must">*</span>最小起订量：</div>
                <div class="form-fields">
                    <div class="form-col col-2">
                        <input id="minOrder" type="text" class="input-text" name="minOrder" onchange="checkMinOrder()"
                               value="<fmt:formatNumber value="${skuGenerate.minOrder}" pattern="0"/>" placeholder="">
                        <span style="color:red;" id="minOrderMsg"></span>
                    </div>
                    <div class="form-col col-2">
                        <span class="sku-unit-value"></span>
                    </div>
                    <div class="form-fields-tip">
                        -针对sku
                    </div>
                    <div class="feedback-block" wrapfor="minOrder"></div>
                </div>
            </div>

            </c:if>


            <div class="form-item">
                <div class="form-label">包装体积：</div>
                <div class="form-fields">
                    <div class="form-col col-2">
                        <input type="text" class="input-text defaultZero" name="packageLength" value="${skuGenerate.packageLength}" placeholder="长">
                    </div>
                    <div class="form-col col-2">
                        <input type="text" class="input-text defaultZero" name="packageWidth" value="${skuGenerate.packageWidth}" placeholder="宽">
                    </div>
                    <div class="form-col col-2">
                        <input type="text" class="input-text defaultZero" name="packageHeight" value="${skuGenerate.packageHeight}" placeholder="高">
                    </div>
                    <span class="unit">厘米</span>
                </div>
            </div>

            <c:if test="${command.skuType!=1}">
                <div class="form-item">
                    <div class="form-label">商品体积：</div>
                    <div class="form-fields">
                        <div class="form-col col-2">
                            <input type="text" class="input-text defaultZero" name="goodsLength" value="${skuGenerate.goodsLength}" placeholder="长">
                        </div>
                        <div class="form-col col-2">
                            <input type="text" class="input-text defaultZero" name="goodsWidth" value="${skuGenerate.goodsWidth}" placeholder="宽">
                        </div>
                        <div class="form-col col-2">
                            <input type="text" class="input-text defaultZero" name="goodsHeight" value="${skuGenerate.goodsHeight}" placeholder="高">
                        </div>
                        <span class="unit">厘米</span>
                    </div>
                </div>
            </c:if>

            <div class="form-item">
                <div class="form-label">毛重：</div>
                <div class="form-fields">
                    <div class="form-col col-2">
                        <input type="text" class="input-text defaultZero" name="grossWeight" value="${skuGenerate.grossWeight}">
                    </div>
                    <span class="unit">KG</span>
                </div>
            </div>
            <c:if test="${command.skuType!=1}">
                <div class="form-item">
                    <div class="form-label">净重：</div>
                    <div class="form-fields">
                        <div class="form-col col-2">
                            <input type="text" class="input-text defaultZero" name="netWeight" value="${skuGenerate.netWeight}">
                        </div>
                        <span class="unit">KG</span>
                    </div>
                </div>
            </c:if>
            <c:if test="${command.skuType==1}">
                <div class="form-item">
                    <div class="form-label">包装清单：</div>
                    <div class="form-fields">
                        <div class="form-col col-6">
                            <textarea class="input-textarea" name="packingList">${skuGenerate.packingList}</textarea>
                        </div>
                        <div class="form-fields-tip">
                            -例：手机*1，电源适配器*1，USB Type-c数据线*1，SIM卡插针*1，高透软胶保护套*1，三包凭证/入门指南*1<br>
                        </div>

                    </div>
                </div>
            </c:if>
        </div>
        <c:if test="${command.skuType!=1}">
            <div class="form-block">
                <div class="form-block-title">存储与效期</div>
                <div class="form-item">
                    <div class="form-label"><span class="must">*</span>存储条件1：</div>
                    <div class="form-fields">
                        <div class="input-radio">
                            <label class="input-wrap">
                                <input type="radio" name="storageConditionOne" value="1" <c:if test="${ skuGenerate.storageConditionOne ==1 }"> CHECKED
        </c:if>>
        <span class="input-ctnr"></span>常温
        </label>
        <label class="input-wrap">
            <input type="radio" name="storageConditionOne" value="2" <c:if test="${ skuGenerate.storageConditionOne ==2 }"> CHECKED </c:if>>
            <span class="input-ctnr"></span>冷冻
        </label>
        <label class="input-wrap">
            <input type="radio" name="storageConditionOne" value="3" <c:if test="${ skuGenerate.storageConditionOne ==3 }"> CHECKED </c:if>>
            <span class="input-ctnr"></span>冷藏
        </label>
        </div>
        <div class="feedback-block" wrapfor="storageConditionOne"></div>
        </div>
        </div>
        <div class="form-item">
            <div class="form-label"><span class="must">*</span>存储条件2：</div>
            <div class="form-fields">
                <div class="input-checkbox">
                    <label class="input-wrap">
                        <input type="checkbox" name="storageConditionTwo" value="1" <c:if test="${fn:contains(skuGenerate.storageConditionTwo, '1') }"> CHECKED </c:if>>
                        <span class="input-ctnr"></span>阴凉
                    </label>
                    <label class="input-wrap">
                        <input type="checkbox" name="storageConditionTwo" value="2" <c:if test="${fn:contains(skuGenerate.storageConditionTwo, '2') }"> CHECKED </c:if>>
                        <span class="input-ctnr"></span>干燥
                    </label>
                    <label class="input-wrap">
                        <input type="checkbox" name="storageConditionTwo" value="3" <c:if test="${fn:contains(skuGenerate.storageConditionTwo, '3') }"> CHECKED </c:if>>
                        <span class="input-ctnr"></span>避光
                    </label>
                    <label class="input-wrap">
                        <input type="checkbox" name="storageConditionTwo" value="4" <c:if test="${fn:contains(skuGenerate.storageConditionTwo, '4') }"> CHECKED </c:if>>
                        <span class="input-ctnr"></span>防潮
                    </label>
                </div>
                <div class="feedback-block" wrapfor="storageConditionTwo"></div>
            </div>
        </div>

        <div class="form-item">
            <div class="form-label">有效期：</div>
            <div class="form-fields">
                <div class="form-col col-2">
                    <input type="text" class="input-text" name="effectiveDays" value="${skuGenerate.effectiveDays}">
                </div>
                <select class="J-select" name="effectiveDayUnit">
                    <option value="1" <c:if test="${ skuGenerate.effectiveDayUnit==1 }"> selected </c:if> >天 </option>
                    <option value="2" <c:if test="${ skuGenerate.effectiveDayUnit==2 }"> selected </c:if> >月 </option>
                    <option value="3" <c:if test="${ skuGenerate.effectiveDayUnit==3 }"> selected </c:if> >年 </option>
                </select>
            </div>
        </div>
        </div>
        </c:if>
        <div class="form-block">
            <div class="form-block-title">售后说明</div>
            <div class="form-item">
                <div class="form-label">
                    <c:if test="${command.skuType==1}"> <span class="must">*</span></c:if>售后内容：
                </div>
                <div class="form-fields">
                    <div class="input-checkbox">
                        <c:if test="${command.skuType!=1}">
                        <input type="hidden" name="afterSaleContent" value="0">
                        </c:if>
                        <c:if test="${command.skuType==1}">
                            <label class="input-wrap">
                                <input type="checkbox" name="afterSaleContent" value="1" <c:if test="${fn:contains(skuGenerate.afterSaleContent, '1')}"> checked </c:if>>
                        <span class="input-ctnr"></span>包安装
                        </label>
                        <label class="input-wrap">
                            <input type="checkbox" name="afterSaleContent" value="2" <c:if test="${fn:contains(skuGenerate.afterSaleContent, '2')}"> checked </c:if>>
                            <span class="input-ctnr"></span>包培训
                        </label>
                        </c:if>

                            <label class="input-wrap">
                                <input type="checkbox" name="afterSaleContent" value="3" <c:if test="${fn:contains(skuGenerate.afterSaleContent, '3')}"> checked </c:if>>
                        <span class="input-ctnr"></span>物流点自提
                        </label>
                        <label class="input-wrap">
                            <input type="checkbox" name="afterSaleContent" value="4" <c:if test="${fn:contains(skuGenerate.afterSaleContent, '4')}"> checked </c:if>>
                            <span class="input-ctnr"></span>送货上门，含卸货上楼
                        </label>
                        <label class="input-wrap">
                            <input type="checkbox" name="afterSaleContent" value="5" <c:if test="${fn:contains(skuGenerate.afterSaleContent, '5')}"> checked </c:if>>
                            <span class="input-ctnr"></span>送货上门，含卸货到楼下
                        </label>

                    </div>
                    <div class="feedback-block" wrapfor="afterSaleContent"></div>
                </div>
            </div>

            <div class="form-item">
                <div class="form-label">
                    <c:if test="${command.skuType==1}"> <span class="must">*</span></c:if>质保年限：
                </div>
                <div class="form-fields">
                    <div class="form-col col-2">
                        <input type="text" class="input-text" name="qaYears" value="${skuGenerate.qaYears}">
                    </div>
                    <span class="unit">年</span>
                </div>
            </div>
            <c:if test="${command.skuType==1}">
                <div class="form-item">
                    <div class="form-label">质保期限规则：</div>
                    <div class="form-fields">
                        <div class="form-col col-6">
                            <textarea class="input-textarea" name="qaRule">${skuGenerate.qaRule}</textarea>
                        </div>
                    </div>
                </div>

                <div class="form-item">
                    <div class="form-label">质保外维修价格：</div>
                    <div class="form-fields">
                        <div class="form-col col-2">
                            <input type="text" class="input-text" name="qaOutPrice" value="${skuGenerate.qaOutPrice}">
                        </div>
                        <span class="unit">元</span>
                    </div>
                </div>
                <div class="form-item">
                    <div class="form-label">响应时间：</div>
                    <div class="form-fields">
                        <div class="form-col col-2">
                            <input type="text" class="input-text" name="qaResponseTime" value="${skuGenerate.qaResponseTime}">
                        </div>
                        <span class="unit">小时</span>
                    </div>
                </div>

                <div class="form-item">
                    <div class="form-label">有无备用机：</div>
                    <div class="form-fields">
                        <div class="input-radio">
                            <label class="input-wrap">
                                <input type="radio" name="hasBackupMachine" value="1" <c:if test="${ skuGenerate.hasBackupMachine ==1 }"> checked
            </c:if>>
            <span class="input-ctnr"></span>有
            </label>
            <label class="input-wrap">
                <input type="radio" name="hasBackupMachine" value="0" <c:if test="${ skuGenerate.hasBackupMachine ==0 }"> checked </c:if>>
                <span class="input-ctnr"></span>无
            </label>
        </div>
        </div>
        </div>

        <div class="form-item">
            <div class="form-label">供应商延保价格：</div>
            <div class="form-fields">
                <div class="form-col col-2">
                    <input type="text" class="input-text" name="supplierExtendGuaranteePrice" value="${skuGenerate.supplierExtendGuaranteePrice}">
                </div>
                <span class="unit">元/年</span>
            </div>
        </div>

        <div class="form-item">
            <div class="form-label">核心零部件价格：</div>
            <div class="form-fields">
                <input type="hidden" class="J-upload-data" value='${command.corePartPriceFileJson}'>
                <div class="J-file-upload" type="corePartPriceFile"></div>
                <div class="form-fields-tip">
                    - 可以上传各类文件，包括图片、word、excel、pdf等；<br>
                    - 单个最大限制10M，最多添加10个附件；<br>
                </div>
                <div class="feedback-block J-upload-error">
                    <label for="" class="error" style="display: none;"></label>
                </div>
            </div>
        </div>

        </c:if>
        </div>
        <div class="form-block">
            <div class="form-block-title">退换货说明</div>
            <div class="form-item">
                <div class="form-label"><span class="must">*</span>退货条件：</div>
                <div class="form-fields">
                    <div class="input-radio">
                        <label class="input-wrap">
                            <input type="radio" name="returnGoodsConditions" value="1" <c:if test="${ skuGenerate.returnGoodsConditions ==1 }"> checked </c:if>>
                            <span class="input-ctnr"></span>允许退货
                        </label>
                        <label class="input-wrap">
                            <input type="radio" name="returnGoodsConditions" value="0" <c:if test="${ skuGenerate.returnGoodsConditions ==0 }"> checked </c:if>>
                            <span class="input-ctnr"></span>不允许退货
                        </label>
                        <div class="feedback-block" wrapfor="returnGoodsConditions"></div>
                    </div>
                </div>
            </div>

            <div class="form-item">
                <div class="form-label"><span class="must">*</span>运费说明：</div>
                <div class="form-fields">
                    <div class="form-col col-6">
                        <textarea placeholder="请说明由哪方承担运费" class="input-textarea" name="freightIntroductions">${skuGenerate.freightIntroductions}</textarea>
                    </div>
                </div>
            </div>
            <div class="form-item">
                <div class="form-label">换货条件：</div>
                <div class="form-fields">
                    <div class="form-col col-6">
                        <textarea class="input-textarea" name="exchangeGoodsConditions">${skuGenerate.exchangeGoodsConditions}</textarea>
                    </div>
                </div>
            </div>
            <div class="form-item">
                <div class="form-label">换货方式：</div>
                <div class="form-fields">
                    <div class="form-col col-6">
                        <textarea class="input-textarea" name="exchangeGoodsMethod">${skuGenerate.exchangeGoodsMethod}</textarea>
                    </div>
                </div>
            </div>


        </div>

        <div class="form-block">
            <div class="form-block-title">商品说明</div>
            <div class="form-item">
                <div class="form-label">商品备注：</div>
                <div class="form-fields">
                    <div class="form-col col-6">
                        <textarea placeholder="500字以内" class="input-textarea" name="goodsComments">${skuGenerate.goodsComments}</textarea>
                    </div>
                </div>
            </div>
        </div>

        <div class="form-btn">
            <div class="form-fields">
                <button type="submit" class="btn btn-blue btn-large">提交</button>
            </div>
        </div>
        </div>
        </div>
    </form>
    <script type="text/tmpl" class="J-sort-tmpl">
        <div class="sort-item-wrap J-sort-item cf">
            <div class="sort-item col-1 item-center">
                <input type="text" name="sort" class="input-text J-sort-num" maxlength="2">
            </div>
            <div class="sort-item col-2">
                <input type="text" name="{{=itemName}}" autocomplete="off" valid-max="30" class="input-text J-sort-name" value="{{=name}}">
                <div class="feedback-block"></div>
            </div>
            <div class="sort-item col-3">
                <input type="text" name="{{=itemValue}}" autocomplete="off" valid-max="30" class="input-text J-sort-value" value="{{=value}}">
            </div>
            <div class="col-1">
                <i class="vd-icon icon-recycle J-sort-del"></i>
            </div>
        </div>
    </script>
    <script type="text/tmpl" class="J-import-tmpl">
        <div class="import-wrap form-span-4 base-form">
            <div class="form-item">
                <div class="form-label">参数文本：</div>
                <div class="form-fields">
                    <div class="form-col col-11">
                        <textarea name="" id="" cols="30" rows="10" class="input-textarea J-import-cnt" placeholder='填写“属性1:属性值1;属性2:属性值2;属性3:属性值3;”将进行自动拆分'></textarea>
                    </div>
                </div>
            </div>
        </div>
    </script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/util.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/select.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/upload.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
    <script src="${pageContext.request.contextPath}/static/new/js/pages/goods/vgoods/sku_edit.js?rnd=<%=Math.random()%>"></script>
</body>

</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>

<head>
  <title>
    <c:if test="${empty setMeal.setMealId}">
      新增套餐
    </c:if>
    <c:if test="${not empty setMeal.setMealId}">
      编辑套餐
    </c:if>
  </title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/common/global.css?rnd=<%=Math.random()%>">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/new/css/pages/goods/setMeal/addSetMeal.css?rnd=<%=Math.random()%>">
</head>

<body>
  <form action="./saveSetMeal.do" id="form_submit" class="J-form" method="POST">
    <input type="hidden" name="formToken" value="${formToken}" />
    <input name="setMealId" type="hidden" value="${setMeal.setMealId}">
    <div class="form-wrap">
      <div class="form-container base-form form-span-7">
        <div class="form-title">
          <c:if test="${empty setMeal.setMealId}">
            新增套餐
          </c:if>
          <c:if test="${not empty setMeal.setMealId}">
            编辑套餐
          </c:if>
        </div>
        <!-- 后台报错的区域 -->
        <c:if test="${not empty error}">
          <div class="vd-tip tip-red">
            <i class="vd-tip-icon vd-icon icon-error2"></i>
            <div class="vd-tip-cnt">${error}</div>
          </div>
        </c:if>
        <!-- end -->
        <div class="form-block">
          <div class="form-block-title">基本信息</div>
          <div class="form-item">
            <div class="form-label"><span class="must">*</span>套餐名称：</div>
            <div class="form-fields">
              <div class="form-col col-8">
                <input type="text" autocomplete="off" name="setMealName" valid-max="100" class="input-text" value="${setMeal.setMealName}">
              </div>
            </div>
          </div>
          <div class="form-item">
            <div class="form-label"><span class="must">*</span>套餐类型：</div>
            <div class="form-fields">
              <div class="input-radio">
                <label class="input-wrap">
                  <input type="radio" name="setMealType" value="1" <c:if test="${setMeal.setMealType == 1}">checked</c:if>>
                  <span class="input-ctnr"></span>商品配套信息（展示在商品详情）
                </label>
                <label class="input-wrap">
                  <input type="radio" name="setMealType" value="2" <c:if test="${setMeal.setMealType == 2}">checked</c:if>>
                  <span class="input-ctnr"></span>根据科室（显示在解决方案）
                </label>
                <label class="input-wrap">
                  <input type="radio" name="setMealType" value="3" <c:if test="${setMeal.setMealType == 3}">checked</c:if>>
                  <span class="input-ctnr"></span>根据疾病（显示在解决方案）
                </label>
                <label class="input-wrap">
                  <input type="radio" name="setMealType" value="4" <c:if test="${setMeal.setMealType == 4}">checked</c:if>>
                  <span class="input-ctnr"></span>其他
                </label>
              </div>
              <div class="feedback-block" wrapfor="setMealType"></div>
            </div>
          </div>
          <div class="form-item">
            <div class="form-label">套餐说明：</div>
            <div class="form-fields">
              <div class="form-col col-8">
                <textarea class="input-textarea" name="setMealDescript" id="setMealDescript" cols="30" rows="10">${setMeal.setMealDescript}</textarea>
              </div>
            </div>
          </div>
        </div>
        <div class="form-block">
          <div class="form-block-title">套餐明细</div>
          <div class="form-block-tip vd-tip tip-blue">
            <i class="vd-tip-icon vd-icon icon-info2"></i>
            <div class="vd-tip-cnt">已添加<span class="J-combo-num">${setMealSkuMappingVoList == null ? 0 : setMealSkuMappingVoList.size()}</span>条，最多添加99条。</div>
          </div>
          <div class="combo-prod-add">
            <a href="javascript:void(0);" class="btn btn-small J-add-prod">添加商品</a>
            <input type="hidden" name="details">
            <div class="feedback-block" wrapfor="departmentIds"></div>
          </div>
          <div class="combo-prod-list">
            <table class="table table-base">
              <colgroup>
                <col width="9%">
                <col width="14.5%">
                <col>
                <col width="15%">
                <col width="9%">
                <col width="15%">
                <col width="14.5%">
                <col width="10%">
              </colgroup>
              <tbody class="J-list-wrap">
                <tr>
                  <th>商品标记</th>
                  <th>科室</th>
                  <th>商品名称</th>
                  <th>用途</th>
                  <th>状态</th>
                  <th>备注</th>
                  <th>数量</th>
                  <th>操作</th>
                </tr>
                <tr class="J-list-nodata" style="display: none;">
                  <td colspan="7">
                    <div class="list-no-data">
                      <i class="vd-icon icon-info1"></i>没有匹配数据
                    </div>
                  </td>
                </tr>
                <c:forEach items="${setMealSkuMappingVoList}" var="setMealSkuMappingVo">
                  <tr class="J-prod-item">
                    <td>
                      <c:if test="${setMealSkuMappingVo.skuSign == 1}">主推</c:if>
                      <c:if test="${setMealSkuMappingVo.skuSign == 2}">备选</c:if>
                      <c:if test="${setMealSkuMappingVo.skuSign == 3}">配套</c:if>
                      <input type="hidden" name="skuSign" value="${setMealSkuMappingVo.skuSign}">
                      <input type="hidden" name="departmentIds" value="${setMealSkuMappingVo.departmentIds}">
                      <input type="hidden" name="departmentNames" value="${setMealSkuMappingVo.departmentNames}">
                      <input type="hidden" name="skuId" value="${setMealSkuMappingVo.skuId}">
                      <input type="hidden" name="skuName" value="${setMealSkuMappingVo.skuName}">
                      <input type="hidden" name="purpose" value="${setMealSkuMappingVo.purpose}">
                      <input type="hidden" name="remark" value="${setMealSkuMappingVo.remark}">
                      <input type="hidden" name="quantity" value="${setMealSkuMappingVo.quantity}">
                      <input type="hidden" name="objectJson" value='${setMealSkuMappingVo.objectJson}'>

                      <input type="hidden" name="skuUnitName" value="${setMealSkuMappingVo.skuUnitName}">
                      <input type="hidden" name="status" value="${setMealSkuMappingVo.status}">
                      <input type="hidden" name="checkStatus" value="${setMealSkuMappingVo.checkStatus}">
                    </td>
                    <td>
                      <div class="line-clamp2">
                        <span title="${setMealSkuMappingVo.departmentNames}">${setMealSkuMappingVo.departmentNames}</span>
                      </div>
                    </td>
                    <td>
                      <div class="line-clamp2">
                        <span title="${setMealSkuMappingVo.skuName}">${setMealSkuMappingVo.skuName}</span>
                      </div>
                    </td>
                    <td>
                      <div class="line-clamp2">
                        <span title="${setMealSkuMappingVo.purpose}">${setMealSkuMappingVo.purpose}</span>
                      </div>
                    </td>
                    <td>
                      <c:if test="${setMealSkuMappingVo.status == 1}">
                        <c:if test="${setMealSkuMappingVo.checkStatus == 1}">
                          <span class="status-yellow">审核中</span>
                        </c:if>
                        <c:if test="${setMealSkuMappingVo.checkStatus == 2}">
                          <span class="status-red">审核不通过</span>
                        </c:if>
                        <c:if test="${setMealSkuMappingVo.checkStatus == 3}">
                          <span class="status-green">审核通过</span>
                        </c:if>
                        <c:if test="${setMealSkuMappingVo.checkStatus == 4}">
                          <span class="status-red">已删除</span>
                        </c:if>
                      </c:if>
                      <c:if test="${setMealSkuMappingVo.status == 0}">
                        <span class="status-red">已删除</span>
                      </c:if>
                    </td>
                    <td>
                      <div class="line-clamp2">
                        <span title="${setMealSkuMappingVo.remark}">${setMealSkuMappingVo.remark}</span>
                      </div>
                    </td>
                    <td>
                      <span class="J-input-number" data-num="${setMealSkuMappingVo.quantity}"></span>
                      <span class="unit">${setMealSkuMappingVo.skuUnitName}</span>
                    </td>
                    <td>
                      <div class="option-select-wrap J-option-select-wrap">
                        <div class="option-select-btn J-edit-prod" data-item='${setMealSkuMappingVo.objectJson}'>编辑</div>
                        <div class="option-select-icon J-option-select-icon">
                          <i class="vd-icon icon-down"></i>
                        </div>
                        <div class="option-select-list">
                          <div class="option-select-item J-item-del" data-id="">删除</div>
                        </div>
                      </div>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
        <div class="form-btn">
          <div class="form-fields">
            <button type="submit" class="btn btn-blue btn-large">保存</button>
          </div>
        </div>
      </div>
    </div>
  </form>
  <script type="text/tmpl" class="J-prod-add-tmpl">
      <form class="J-dlg-form">
          <input type="hidden" name="status" value="{{=status}}">
          <input type="hidden" name="checkStatus" value="{{=checkStatus}}">
          <div class="dlg-form-wrap base-form form-span-5">
              <div class="form-item">
                  <div class="form-label"><span class="must">*</span>商品标记：</div>
                  <div class="form-fields">
                      <div class="input-radio">
                          <label class="input-wrap">
                              <input type="radio" name="skuSign" {{if(skuSign == 1){}}checked{{}}} value="1">
                              <span class="input-ctnr"></span>主推
                          </label>
                          <label class="input-wrap">
                              <input type="radio" name="skuSign" {{if(skuSign == 2){}}checked{{}}} value="2">
                              <span class="input-ctnr"></span>备选
                          </label>
                          <label class="input-wrap">
                              <input type="radio" name="skuSign" {{if(skuSign == 3){}}checked{{}}} value="3">
                              <span class="input-ctnr"></span>配套
                          </label>
                      </div>
                      <div class="feedback-block" wrapfor="skuSign">
                      </div>
                  </div>
              </div>
              <div class="form-item">
                  <div class="form-label"><span class="must">*</span>商品名称：</div>
                  <div class="form-fields">
                      <div class="J-suggest"></div>
                      <div class="form-fields-tip">-仅支持选择审核通过的商品</div>
                      <input type="hidden" name="skuId" class="J-prod-input" value="{{=skuId}}">
                  </div>
              </div>
              <div class="form-item J-department-block">
                  <div class="form-label">{{ if($('[name=setMealType]:checked').val()==2){ }}<span class="must">*</span>{{ } }}科室：</div>
                  <div class="form-fields">
                      <div class="input-checkbox J-department-wrap"></div>
                      <div class="feedback-block" wrapfor="departmentIds">
                      </div>
                  </div>
              </div>
              <div class="form-item">
                  <div class="form-label"><span class="must">*</span>数量：</div>
                  <div class="form-fields">
                      <span class="J-dlg-input-number" data-num="{{=quantity}}"></span>
                      <span class="unit J-sku-unit-txt">{{=skuUnitName}}</span>
                      <input type="hidden" class="J-sku-unit-value" name="skuUnitName" value="{{=skuUnitName}}">
                  </div>
              </div>
              <div class="form-item">
                  <div class="form-label"><span class="must">*</span>用途：</div>
                  <div class="form-fields">
                      <div class="form-col col-10">
                          <textarea class="input-textarea" name="purpose" id="" cols="30" rows="10">{{=purpose}}</textarea>
                      </div>
                  </div>
              </div>
              <div class="form-item">
                  <div class="form-label">备注：</div>
                  <div class="form-fields">
                      <div class="form-col col-10">
                          <textarea class="input-textarea" name="remark" id="" cols="30" rows="10">{{=remark}}</textarea>
                      </div>
                  </div>
              </div>
          </div>
      </form>
  </script>
  <script type="text/tmpl" class="J-prod-item-tmpl">
      <tr class="J-prod-item">
          <td>{{=['主推','备选','配套'][parseInt(data.skuSign) - 1]}}
            <input type="hidden" name="skuSign" value="{{=data.skuSign}}">
            <input type="hidden" name="departmentIds" value="{{=data.departmentIds}}">
            <input type="hidden" name="departmentNames" value="{{=data.departmentNames}}">
            <input type="hidden" name="skuId" value="{{=data.skuId}}">
            <input type="hidden" name="skuName" value="{{=data.skuName}}">
            <input type="hidden" name="purpose" value="{{=data.purpose}}">
            <input type="hidden" name="remark" value="{{=data.remark}}">
            <input type="hidden" name="quantity" value="{{=data.quantity}}">
            <input type="hidden" name="objectJson" value='{{=JSON.stringify(data)}}'>
            <input type="hidden" name="skuUnitName" value="{{=data.skuUnitName}}">
            <input type="hidden" name="status" value="{{=data.status}}">
            <input type="hidden" name="checkStatus" value="{{=data.checkStatus}}">
          </td>
          <td>
            <div class="line-clamp2">
              <span title="{{=data.departmentNames}}">{{=data.departmentNames}}</span>
            </div>
          </td>
          <td>
            <div class="line-clamp2">
              <span title="{{=data.skuName}}">{{=data.skuName}}</span>
            </div>
          </td>
          <td>
            <div class="line-clamp2">
              <span title="{{=data.purpose}}">{{=data.purpose}}</span>
            </div>
          </td>
          <td>
             {{ if(data.status == 1){ }}
                {{ if(data.checkStatus == 1){ }}
                  <span class="status-yellow">审核中</span>
                {{ }else if(data.checkStatus == 2){ }}
                  <span class="status-red">审核不通过</span>
                {{ }else if(data.checkStatus == 3){ }}
                  <span class="status-green">审核通过</span>
                {{ }else if(data.checkStatus == 4){ }}
                  <span class="status-red">已删除</span>
                {{ } }}
            {{ }else if(data.status == 0){ }}
              <span class="status-red">已删除</span>
            {{ } }}
          </td>
          <td>
            <div class="line-clamp2">
              <span title="{{=data.remark}}">{{=data.remark}}</span>
            </div>
          </td>
          <td>
              <span class="J-input-number" data-num="{{=data.quantity}}"></span>
              <span class="unit">{{=data.skuUnitName}}</span>
          </td>
          <td>
              <div class="option-select-wrap J-option-select-wrap">
                  <div class="option-select-btn J-edit-prod" data-item='{{=JSON.stringify(data)}}'>编辑</div>
                  <div class="option-select-icon J-option-select-icon">
                      <i class="vd-icon icon-down"></i>
                  </div>
                  <div class="option-select-list">
                      <div class="option-select-item J-item-del" data-id="">删除</div>
                  </div>
              </div>
          </td>
      </tr>
  </script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/global.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/util.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/select.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/inputNumber.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/jquery.validate.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/artDialog/2.0.0/artDialog.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/common/suggestSelect.js?rnd=<%=Math.random()%>"></script>
  <script src="${pageContext.request.contextPath}/static/new/js/pages/goods/setMeal/addSetMeal.js?rnd=<%=Math.random()%>"></script>

</body>

</html>
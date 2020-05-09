<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="订单打印" scope="application" />
<%@ include file="../../common/common.jsp"%>
<%-- <link href='<%=basePath%>static/css/order_print/system.css?rnd=<%=Math.random()%>'
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href='<%=basePath%>static/css/order_print/ui.theme.css?rnd=<%=Math.random()%>'
	type="text/css" media="all">
<link type="text/css" rel="stylesheet"
	href='<%=basePath%>static/css/order_print/layer.css?rnd=<%=Math.random()%>'
	id="skinlayercss"> --%>
<script type="text/javascript"
	src='<%=basePath%>static/js/jquery.PrintArea.js?rnd=<%=Math.random()%>'></script>
	<script type="text/javascript"
	src='<%=basePath%>static/js/order/saleorder/order_print.js?rnd=<%=Math.random()%>'></script>
	<link type="text/css" rel="stylesheet" href="<%=basePath%>static/css/general.css?rnd=<%=Math.random()%>">
    <link  type="text/css" rel="stylesheet" href="<%=basePath%>static/css/manage.css?rnd=<%=Math.random()%>" />
<body>
   <div id="print-contract">
        <div class="">
             <div class="contract-head">
                <c:if test="${ saleorderVo.customerType == 426}">
				    <img src="<%=basePath%>static/images/logo2.jpg" />
				  </c:if>
				  <c:if test="${ saleorderVo.customerType == 427 or saleorderVo.customerType==0}">
				    <img src="<%=basePath%>static/images/logo1.jpg" class="" />
				  </c:if>
                <div class="contract-number">
                  <div>合同号码: ${saleorderVo.saleorderNo }</div>
                  <div>制单日期: 
                  <c:if test="${saleorderVo.validTime ==0 }">
                  ${currTime}
                  </c:if>
                  <c:if test="${saleorderVo.validTime !=0}">
                  <date:date value ="${saleorderVo.validTime }" format="yyyy-MM-dd"/>
                  </c:if>
                  </div>
                </div>
            </div>
            <div class="contract-head-title">销售合同</div>
            <div class="contract-print-table">
                <table>
                  <tbody>
                    <tr class="jiayi">
                      <td><span>甲方：</span><span>${saleorderVo.traderName }</span></td>
                      <td><span>乙方：</span><span>${company.companyName }</span></td>
                    </tr>
                    <tr>
                      <td>
                        <div>                    
                                                                                        采购人员：${saleorderVo.name }<c:if test="${saleorderVo.sex eq 0}">&nbsp;女士</c:if><c:if test="${saleorderVo.sex eq 1}">&nbsp;先生</c:if>
                        </div>
                        <div>
                          <span>联系方式：</span>
                          <span>${saleorderVo.mobile }</span>
                        </div>
                         <div>
                          <span>开票信息：</span>
                          <span></span>
                        </div>
                         <div class="overflow-hidden">
                          <span style="width:70px;float:left;">注册地址/电话：</span>
                       
                         <span style="float:left;display:inline-block;max-width:-moz-calc(100% - 70px);max-width:-webkit-calc(100% - 70px);max-width:calc(100% - 70px);">${ saleorderVo.regAddress}/${ saleorderVo.regTel}</span>
                        </div>
                         <div>
                          <span>开户行：</span>
                          <span>${ saleorderVo.bank}</span>
                        </div>
                         <div>
                          <span>税号：</span>
                          <span>${ saleorderVo.taxNum}</span>
                        </div>
                        <div>
                          <span>账号：</span>
                          <span>${ saleorderVo.bankAccount}</span>
                        </div>
                      </td>
                      <td>
                        <div>
                          <span>销售人员：</span>
                          <span>${detail.realName }<c:if test="${detail.sex eq 0}">&nbsp;女士</c:if><c:if test="${detail.sex eq 1}">&nbsp;先生</c:if>
                          </span>
                        </div>
                        <div>
                          <span>联系方式：</span>
                          <span>${detail.telephone }/${detail.mobile }</span>
                        </div>
                        <div>
                          <span>付款信息：</span>
                          <span></span>
                        </div>
                        <div>
                          <span>开户行：</span>
                          <span>中国建设银行江苏南京中山南路支行</span>
                        </div>
                        <div>
                          <span>账号：</span>
                          <span>32001881236052503686 </span>
                        </div>
                        <div>
                          <span>税号：</span>
                          <span>91320100589439066H</span>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
                <div class="contract-print-title2 pl10">发票及货物接收信息</div>
                  <table>
                    <tbody>
                      <tr>
                        <td>
                          <div>
                            <span> 收票单位：</span>
                            <span>${saleorderVo.invoiceTraderName }</span>
                          </div>
                           <div>
                            <span>收票人：</span>
                            <span>${saleorderVo.invoiceTraderContactName }/${saleorderVo.invoiceTraderContactMobile }/${saleorderVo.invoiceTraderContactTelephone }</span>
                          </div>
                           <div>
                          </div>
                           <div>
                            <span>地址：</span>
                            <span>${saleorderVo.invoiceTraderArea} ${saleorderVo.invoiceTraderAddress }</span>
                          </div>
                        </td>
                        <td>
                          <div>
                            <span>收货单位：</span>
                            <span>${saleorderVo.takeTraderName }</span>
                          </div>
                          <div>
                            <span>收货人：</span>
                            <span>${saleorderVo.takeTraderContactName }/${saleorderVo.takeTraderContactMobile }/${saleorderVo.takeTraderContactTelephone }</span>
                          <div>
                            <span>地址：</span>
                            <span>${saleorderVo.takeTraderArea } ${saleorderVo.takeTraderAddress } </span>
                          </div>
                        </td>
                      </tr>
                    </tbody>
                </table>
                <div class="contract-tips">就甲方向乙方采购产品事宜，经友好协商，签订本合同，以资遵守。</div>
                <div class="contract-print-title2 pl10">一、产品信息：</div>
                <table class="print-product-table">
                    <tbody>
                      <tr class="font-bold tdsmall">
                        <td style="width:4%;">序号</td>
                        <td style="width:6%;">订货号</td>
                        <td style="width:26%;">产品名称</td>
                        <td style="">品牌</td>
                        <td style="">型号</td>
                        <td style="width:4%;">数量</td>
                        <td style="width:4%;">单位</td>
                        <td style="width:10%">单价(元）</td>
                        <td style="width:10%;">金额(元）</td>
                        <td style="width:6%;">货期<br/><span style="font-size:0.75em;">(工作日)</span></td>
                        <td>备注</td>
                      </tr>
                      <c:set var="count" value="1"></c:set>
						<c:forEach var="list" items="${saleorderGoodsList}" varStatus="num">
						<c:if test="${list.isDelete eq 0}">
						<tr>
							<td class="" style="width: 4%;"> ${count}</td>
							<c:set var="count" value="${count+1}"></c:set>
							<td style="width: 6%;">${list.sku }</td>
							<td style="width: 26%; text-align:left;" >${ list.goodsName}</td>
							<td style="" >${list.brandName }</td>
							<td style="">${list.model }</td>
							<td style="width: 4%;">${list.num }</td>
							<td style="width: 4%;">${list.unitName }</td>
							<td  style="width: 10%;">${list.prices }</td>
							<td  style="width: 10%;">${list.allPrice}</td>
							<td  style="width: 5%;">${list.deliveryCycle }</td>
							<td  >${list.goodsComments }</td>
					    </tr>
					    </c:if>
					</c:forEach>
                      <tr>
                        <td>合计</td>
                        <td colspan="8" style="overflow: hidden;">
                            <div class="total-count f_left">（大写）${chineseNumberTotalPrice }</div>
                            <div class="total-count-num f_right">${totalAmount}</div>
                        </td>
                        <td colspan="2"></td>
                      </tr>
                    </tbody>
                </table>
                <div class="contract-print-title2">二、付款方式&发票类型：</div>
                <div class="contract-details">
                       1、 付款方式：
                      <c:if test="${saleorderVo.paymentType == 419}">
						先款后货，预付100% ，预付金额${saleorderVo.prepaidAmount }元
						</c:if>
						<c:if test="${saleorderVo.paymentType == 420}">
						先货后款，预付80%，预付金额${saleorderVo.prepaidAmount }元，账期支付${saleorderVo.accountPeriodAmount }元
						</c:if>
						<c:if test="${saleorderVo.paymentType == 421}">
						先货后款，预付50%，预付金额${saleorderVo.prepaidAmount }元，账期支付${saleorderVo.accountPeriodAmount }元
						</c:if>
						<c:if test="${saleorderVo.paymentType == 422}">
						先货后款，预付30%，预付金额${saleorderVo.prepaidAmount }元，账期支付${saleorderVo.accountPeriodAmount }元
						</c:if>
						<c:if test="${saleorderVo.paymentType == 423}">
						先货后款，预付0%，预付金额${saleorderVo.prepaidAmount }元，账期支付${saleorderVo.accountPeriodAmount }元
						</c:if>
						<c:if test="${saleorderVo.paymentType == 424}">
						自定义，预付金额${saleorderVo.prepaidAmount }元，账期支付${saleorderVo.accountPeriodAmount }元，尾款${saleorderVo.retainageAmount }元
						，尾款期限${saleorderVo.retainageAmountMonth }月
						</c:if>
                      <br/>
                      2、 甲方应将货款支付至乙方指定银行账户，勿将货款以现金形式支付给乙方任何人员，否则乙方有权视甲方未支付该款项。<br/>
                      3、 发票类型：
                       <c:forEach var="list" items="${invoiceTypes}">
	                    <c:if test="${saleorderVo.invoiceType == list.sysOptionDefinitionId}">${list.title}</c:if>
	                  </c:forEach>
                </div>
                <div class="contract-print-title2">三、产品交付及安调、培训说明：</div>
                <div class="contract-details">
                      1、 关于运费：<c:forEach var="list" items="${yfTypes}">
	                    <c:if test="${saleorderVo.freightDescription == list.sysOptionDefinitionId}">${list.title}</c:if>
	                  </c:forEach><br/>
                      2、 安调、培训：<c:if test="${flag==1}">有</c:if><c:if test="${flag==-1}">无</c:if><br/>
                      3、 货期自乙方收到甲方预付款后开始计算。国家法定周末和节假日不计算在货期内。如果因为甲方未及时付款导致货期变动的，双方需重新约定交货期。<br/>
                      4、 货品达到甲方指定收货地点后，甲方承担收货义务之指定人员、公司员工或者关联方员工需对物流公司配送的货品当场开箱验收，验收范围包括但不限于：货品外部包装是否破损或存有瑕疵；货物的种类、型号、颜色、数量等外部形态是否与订单相符。甲方指定收货人在送货单上签字确认之后，将视为甲方接受所配送货品，乙方将只在产品质量性能方面承担责任。除指定收货人以外，甲方公司员工、关联方员工对货品的签收亦视为具有承认效果的物品接受。甲方的签收人员有义务根据乙方的要求将上述已签收送货单或扫描回传或原件寄回给乙方。甲方如发现货品外包装破损或者存有瑕疵，应当场向物流公司提出并尽快以书面形式通知乙方，如货物的种类、型号、颜色、数量等外部形态与订单不符，甲方需在三（3）个工作日内以书面形式通知乙方。逾期乙方将不再承担免费调换义务。甲方拒签收货物所带来的损失由甲方自己承担。
                </div>
                <div class="contract-print-title2">四、 质量保证：</div>
                <div class="contract-details">
                      1、 乙方保证供应的产品符合国家标准或相关行业标准，且为全新、原装产品。<br/>
                      2、 乙方按照产品说明书内的质保条件及质保期对产品提供质保承诺。质保时间自甲方收到货物时开始计算。<br/>
                      3、 在质量保证期内出现的非甲方人为因素损坏的产品质量问题，乙方按照国家或行业标准提供质保服务，提供维修或更换服务。如非质保期内或甲方人为因素导致的货品的损坏，在可修复前提下，乙方提供有偿维修服务。
                </div>
                <div class="contract-print-title2">五、退换货：</div>
                <div class="contract-details">
                      1、 甲方在收到货物十（10）个工作日内，1）由于产品质量原因导致无法使用，且经乙方确认不具有可修复性和可更换性，甲方可以要求退货。2）定制类产品出现以上质量问题，乙方仅提供尽可能的维修服务，不接受退货要求。<br/>
                      2、 如甲方订购的货品属于一次性使用产品、易耗品、或产品技术性能决定无法更换的，乙方不承担换货义务。<br/>
                      3、 甲方因经验不足、决策不周等单方面因素导致的选型、购买数量等失误，要求退还货物的，乙方不予接受。<br/>
                </div>
                <div class="contract-print-title2">六、知识产权：</div>
                <div class="contract-details">
                    1、	未经乙方事先书面同意，甲方不得在任何国家或地区，并通过任何形式，使用乙方及其关联方的商标、商号或将与乙方商标、商号相同或类似的内容注册为甲方的商标、商号、公司域名等。<br/>
                    2、	未经乙方事先书面同意，甲方不得对乙方及其关联方的企业名称、商标、商号、服务标志或标识进行任何商业使用和擅自作出任何变更，包括但不限于在广告、宣传资料、广告、办公地点等使用。<br/>
                    3、	对于所售产品的商标、专利、包装设计以及品牌方的商标、商号、域名等均属于品牌方的知识产权，未经品牌方授权许可，甲方不得擅自使用品牌方的商标、商号、域名，也不得侵犯产品相关的知识产权，否则，责任由甲方自行承担，且由此给乙方造成损失的，甲方还应向乙方承担相应的赔偿责任。<br/>
                </div>
                <div class="contract-print-title2"> 七、合同生效：</div>
                <div class="contract-details">
                      合同自双方签字盖章时生效，若在合同生效后14天内，甲方未付款或未执行任何实质性合同内容，合同将自动失效。     
                </div>
                <div class="contract-print-title2">八、合同执行</div>
                 <div class="contract-details">
                     1、 交货期以甲乙双方约定为准，交付时间以乙方交付货物给物流配送方时间为准。如果乙方或物流配送方遭遇不可抗拒的理由，如地震、战争、台风、游行等不可抗力（由相关政府部门出具证明文件），或物流配送方遗失货品（由物流配送方出具相关证明文件），则不属于迟交范畴。如果遇到厂家停产、海关政策调整等不可预测第三方因素导致乙方不能按时供货，乙方应当在十（10）个工作日内书面通知甲方，经双方协商一致，可以变更或终止合同。除以上不可抗拒的因素，乙方延迟交货给甲方造成损失的，从延迟交付的第十（10）个工作日起，承担延迟交付的责任，每工作日赔付金额为甲方已付合同金额的0.3%，赔付上限为甲方已付合同总金额的3%。<br/>
                      2、 本合同中产品，甲方承诺只销售、安装于____${saleorderVo.salesArea}_____（地理区域）_____
														<c:if test="${ saleorderVo.customerType == 426}">
														科研医疗
														</c:if>
														<c:if test="${ saleorderVo.customerType == 427 or saleorderVo.customerType==0}">
														临床医疗
														</c:if>
														_____（客户类型）。如有违反本条规定，则本合同中的产品不在售后保修范围之内，且甲方公司在贝登信用评级下调，直接影响双方接下来的合作。 注：信用等级高度关联贝登公司产品交易、金融授信等合作项目。 
                </div>
                <div class="contract-print-title2"> 九、 合同撤销：</div>
                <div class="contract-details">
                  合同生效后，除因产品停产、不可抗力原因外，甲方撤销合同的，甲方需向乙方支付合同总金额的百分之三（3%）作为违约赔偿金，同时甲方应当承担乙方为履行本合同已发生的成本及费用（包括但不限于产品采购成本等）。  
                </div>
                <div class="contract-print-title2"> 十、 合同争议处理：</div>
                <div class="contract-details">
                  对于合同争议，双方需本着友好精神协商解决，协商不成，可将争议提请乙方所在地人民法院诉讼。
                </div>
                 <div class="contract-print-title2">十一、合同效力：</div>
                <div class="contract-details">
                  本合同双方盖章或签字生效，传真件、扫描件与原件具有同等法律效力。  
                </div>
                <c:if test="${!empty saleorderVo.additionalClause}">
                 <div class="contract-print-title2">十一、补充条款：</div>
                <div class="contract-details">${saleorderVo.additionalClause }</div>
                </c:if>
                <div class="contract-print-sign">
                  <ul>
                      <li style="margin-left: 50px;padding:32px 45px 0px 45px;">
                        <div class="sign-name">甲方：${saleorderVo.traderName }</div>
                        <div class="sign-place">
                            <ul>
                              <li>
                                  <div></div>
                                  <div>授权人签字</div>
                              </li>
                              <li>
                                  <div></div>
                                  <div>日期</div>
                              </li>
                            </ul>
                        </div>
                      </li>
                       <li style="float: right; margin-right: 50px;overflow:visible;position: relative; padding:32px 0 28px 0;">
                        <c:if test="${saleorderVo.validStatus ==1}">
                        <img src="<%=basePath%>static/images/order_sign_b.png" style="position:absolute; top: 0;left: 0; z-index: 123;">
                       </c:if>
                        <div class="sign-name">乙方：南京贝登医疗股份有限公司</div>
                        <div class="sign-place">
                            <ul>
                              <li>
                                  <div style="margin-top:29px;">${detail.realName }</div>
                                  <div>授权人签字</div>
                              </li>
                              <li>
                                  <div style="margin-top:29px;">
                                  <c:if test="${saleorderVo.validTime ==0 }">
				                  ${currTime}
				                  </c:if>
				                  <c:if test="${saleorderVo.validTime !=0}">
				                  <date:date value ="${saleorderVo.validTime }" format="yyyy-MM-dd"/>
				                  </c:if>
                                  </div>
                                  <div>日期</div>
                              </li>
                            </ul>
                        </div>
                      </li>
                      <div class='clear'></div>
                  </ul>
                </div>

            </div>
        </div>
         <div class='tcenter mb15'>
		<span class=" bt-small bt-bg-style bg-light-blue"  id="btnPrint">打印</span>
    	</div> 
   </div>
   <script>
	$("#btnPrint").click(function() {
		$("#btnPrint").hide();
        if (window.ActiveXObject || "ActiveXObject" in window){
            $("#print-contract").printArea({
                mode:'popup'
            });
        }else{
            $("#print-contract").printArea();
        }
	    $("#btnPrint").show();
	});
</script>
<%@ include file="../../common/footer.jsp"%>
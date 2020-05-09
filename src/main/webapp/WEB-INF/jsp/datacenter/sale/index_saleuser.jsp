<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="title" value="销售人员分析" scope="application" />
<%@ include file="../../common/common.jsp"%>
<script type="text/javascript" src='<%= basePath %>static/js/datacenter/sale/index_saleuser.js?rnd=<%=Math.random()%>'></script>
<script type="text/javascript" src="<%= basePath %>static/libs/jquery/plugins/DatePicker/WdatePicker.js?rnd=<%=Math.random()%>"></script>
	<div class="main-container">
        <div class="form-list">
            <form method="post" action="" id="userSearch">
                <ul>
                    <li style="margin-bottom: 0px;">
                        <div class="form-tips" style="width: 84px;">
                            <lable>请选择销售人员</lable>
                        </div>
                        <div class="f_left ">
                            <div class="form-blanks">
                            	<select class="input-middle" name="userId" id="userId">
                            	<option value="-1">请选择</option>
								<c:forEach items="${userList}" var="list">
									<option value="${list.userId}" <c:if test="${list.userId == userId}">selected="selected"</c:if>>${list.username}</option>
								</c:forEach>
								</select>
                                <span class="bt-middle bg-light-blue bt-bg-style" onclick="userSearch();">查询</span>
                            </div>
                        </div>
                    </li>
                </ul>
            </form>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    基本信息
                </div>
            </div>
            <table class="table">
                <tbody>
                    <tr>
                        <td class="wid20">入职时间</td>
                        <td><date:date value="${userInfo.addTime}" format="yyyy-MM-dd"/></td>
                        <td class="wid20">所属部门</td><td>${userInfo.orgName}</td>
                    </tr>
                    <tr>
                        <td>客户总数</td>
                        <td></td>
                        <td>到款总额</td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="data-doc-exchange mb10">
            <ul>
                <li class="active">数据统计</li>
                <li><a href="${pageContext.request.contextPath}/datacenter/sale/saleuserstatistics.do?userId=${userId}">数据分析</a></li>
            </ul>
        </div>
        <div class="mb10">
            <input class="Wdate f_left input-smaller96 mr10" type="text" placeholder="请选择年份" onClick="WdatePicker({dateFmt:'yyyy'})">
            <span class="bt-border-style bt-small border-blue">查看</span>
        </div>
        <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    年度指标
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th class="wid12"></th>
                        <th>2017.01</th>
                        <th>2017.02</th>
                        <th>2017.03</th>
                        <th>2017.04</th>
                        <th>2017.05</th>
                        <th>2017.06</th>
                        <th>2017.07</th>
                        <th>2017.08</th>
                        <th>2017.09</th>
                        <th>2017.10</th>
                        <th>2017.11</th>
                        <th>2017.12</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>月度到款额 </td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>客户数 </td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>A级客户数</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>沟通客户数</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>沟通客户次数</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>沟通A级客户数</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>沟通A级客户次数</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>新增客户数</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>新增合作客户数</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>客单价</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>报价总数</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>报价总额</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>报价转化率</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>订单总数</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>订单总额</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>合同回传数</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>送货单回传数</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>销售商品数</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>录保卡数量</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="mt10 mb10">
            <input class="Wdate f_left input-smaller96 mr10" type="text" placeholder="请选择月份" onClick="WdatePicker({dateFmt:'yyyy-MM'})">
            <span class="bt-border-style bt-small border-blue">查看</span>
        </div>
        <div class="parts">
            <div >
                <div >
                    <div class="title-container">
                        <div class="table-title nobor">
                            月度指标
                        </div>
                    </div>
                    <table class="table">
                        <thead>
                            <tr>
                                <th class="wid12"></th>
                                <th>01</th>
                                <th>02</th>
                                <th>03</th>
                                <th>04</th>
                                <th>05</th>
                                <th>06</th>
                                <th>07</th>
                                <th>08</th>
                                <th>09</th>
                                <th>10</th>
                                <th>11</th>
                                <th>12</th>
                                <th>13</th>
                                <th>14</th>
                                <th>15</th>
                                <th>16</th>
                                <th>17</th>
                                <th>18</th>
                                <th>19</th>
                                <th>20</th>
                                <th>21</th>
                                <th>22</th>
                                <th>23</th>
                                <th>24</th>
                                <th>25</th>
                                <th>26</th>
                                <th>27</th>
                                <th>28</th>
                                <th>29</th>
                                <th>30</th>
                                <th>31</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>新增客户数 </td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>沟通客户数 </td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>沟通客户次数</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>沟通A级客户数</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>沟通A级客户次数</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>新增报价数</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>跟单报价数</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>新增订单数</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>跟单订单数</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="mt10 mb10">
            <input class="Wdate f_left input-smaller96 mr10" type="text" placeholder="请选择日期" onClick="WdatePicker()">
            <span class="bt-border-style bt-small border-blue">查看</span>
        </div>
         <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    当日商机
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th class="wid5">序号</th>
                        <th class="wid8">商机编号</th>
                        <th>客户名称</th>
                        <th>询价产品</th>
                        <th class="wid8">商机状态</th>
                        <th >商机关闭原因</th>
                        <th >商机关闭备注</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1</td>
                        <td class="brand-color1">
                        </td>
                        <td class="brand-color1">
                            南京贝登医疗股份有限公司
                        </td>
                        <td>分销</td>
                        <td></td>
                        <td>A</td>
                        <td>80000.00</td>
                    </tr>
                </tbody>
            </table>
        </div>
         <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    当日报价
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th class="wid5">序号</th>
                        <th class="wid12">报价单号</th>
                        <th>客户名称</th>
                        <th class="wid10">报价总额</th>
                        <th >报价单状态</th>
                        <th >失单原因</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1</td>
                        <td class="brand-color1">
                        </td>
                        <td class="brand-color1">
                            南京贝登医疗股份有限公司
                        </td>
                        <td>分销</td>
                        <td></td>
                        <td>A</td>
                        
                    </tr>
                </tbody>
            </table>
        </div>
         <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    当日订单
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th class="wid5">序号</th>
                        <th class="wid12">订单号</th>
                        <th>客户名称</th>
                        <th class="wid10">订单总额</th>
                        <th >订单状态</th>
                        <th >备注</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1</td>
                        <td class="brand-color1">
                        </td>
                        <td class="brand-color1">
                            南京贝登医疗股份有限公司
                        </td>
                        <td>分销</td>
                        <td></td>
                        <td>A</td>
                       
                    </tr>
                </tbody>
            </table>
        </div>
         <div class="parts">
            <div class="title-container">
                <div class="table-title nobor">
                    沟通客户
                </div>
            </div>
            <table class="table">
                <thead>
                    <tr>
                        <th class="wid5">序号</th>
                        <th class="wid16">沟通时间</th>
                        <th class="wid9">单号</th>
                        <th class="wid8">录音</th>
                        <th class="wid8">沟通方式</th>
                        <th class="wid8">沟通目的</th>
                        <th >沟通内容</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>1</td>
                        <td>
                            <span>2017.03.03 </span>
                            <span class="font-grey9">13:23-13:23</span>
                        </td>
                        <td class="brand-color1">
                            7654476544
                        </td>
                        <td class="brand-color1">
                            76544
                        </td>
                        <td>去电</td>
                        <td>营销和销售</td>
                        <td></td>
                       
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
<%@ include file="../../common/footer.jsp"%>
package com.meinian.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.meinian.model.vo.ReceiveAllMeinianCustomer;
import com.meinian.service.MeinianOderService;
import com.vedeng.common.constant.ErpConst;
import com.vedeng.common.controller.BaseController;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.util.ObjectUtils;
import com.vedeng.logistics.service.WarehouseInService;
import com.vedeng.system.model.SysOptionDefinition;
import com.vedeng.trader.model.TraderCustomer;
import net.sf.json.JsonConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/order/miannian")
public class MeinianOrderController extends BaseController {

    private static Logger LOGGER = LoggerFactory.getLogger(MeinianOrderController.class);

    @Autowired
    @Qualifier(value = "meinianOderService")
    private MeinianOderService meinianOderService;

    @Autowired
    @Qualifier(value = "warehouseInService")
    private WarehouseInService warehouseInService;

    //接口平台对接时申请的appKey，一个appKey对应一个 secret
    static String  appkey = null;
    //随机密码平台获取
    static String secret = null;
    //接口地址
    static String url = null;


    // 设置数据
    public void setValue(){

        //获取字典表中的集合
        List<SysOptionDefinition>  apiList = getSysOptionDefinitionList(901);
        //获取appkey,secret,url
        this.appkey = apiList.get(0).getComments();
        this.secret = apiList.get(1).getComments();
        this.url = apiList.get(2).getComments();
    }

    /**
     * 获取请求接口所需要的token
     * string token
     * @return
     * @date 2018 11 12 11:15
     */
    public String getToken(){
        setValue();
        //获取token
        JSONObject tokenmes = meinianOderService.getTokenFromMeiNian(appkey, secret, url);
        //判断是否获取到token
        if( null  == tokenmes){
            return null;
        }
        if (!tokenmes.get("status").equals("S")) {
            return  null;
        }
        return tokenmes.get("token").toString();
    }
    /**
     * 将美年查询订单起始时间（时间戳，最大日期为一个月）存储到本地库
     * @auther Bert
     * @return
     * @Date 2018.11.10 11.13
     */
    @ResponseBody
    @RequestMapping(value = "synchronizingMeinianOrder")
    public String synchronizingMeinianOrder() {
        JSONObject json = new JSONObject();
        // 获取token
        String token = getToken();
        //是否成功的获取到token
        if (!StringUtils.isNotBlank(token)) {
            return  ErpConst.SEND_DATA_FAIL;
        }

        //获取所有订单
        JSONObject result = meinianOderService.getOrderList(token, appkey, url);
        if (ObjectUtils.allNotNull(result)){
            JSONArray orders = result.getJSONArray("data");
            //进入service进行数据处理存储
            if (ObjectUtils.allNotNull(orders)){
                //将获取到的所有数据推送到dbcenter的进行保存
                ResultInfo mes = meinianOderService.sendMeinianOrders(orders);
                if (ObjectUtils.allNotNull(mes)){
                    return mes.getData().toString();
                }
            }
            return null;
        }
        return null;
    }

    /**
     * <b>Description:</b><br>
     * 获取美年的所有客户信息
     * @param : 无
     * @return : ResultInfo包含信息
     * @Note <b>Author:</b> Bert <br>
     * <b>Date:</b> 2018/11/12 21:44
     */
    @ResponseBody
    @RequestMapping(value = "getAllCustomers")
    public ResultInfo getAllCustomers() {
        /**
         * 获取token
         */
        String token = getToken();

        if (!StringUtils.isNotBlank(token)) {
            return new ResultInfo(ErpConst.ONE, "token获取失败", "token异常");
        }
        JSONObject customers = meinianOderService.getCustomers(token, appkey, url);
        //判空防止空指正异常
        if (ObjectUtils.allNotNull(customers)){
            Object data = customers.get("data");
            //判空防止空指正异常
            if (ObjectUtils.allNotNull(data)){
                //将json转成对象
                net.sf.json.JSONArray jsonarray = net.sf.json.JSONArray.fromObject(data);
                List<ReceiveAllMeinianCustomer> allCoustomerList = (List<ReceiveAllMeinianCustomer>)net.sf.json.JSONArray.toList(jsonarray, new ReceiveAllMeinianCustomer(), new JsonConfig());
                //循环遍历添加点击按钮
                if (CollectionUtils.isNotEmpty(allCoustomerList)){
                    allCoustomerList.stream().forEach( x ->{
                        x.setCheckbox("<input type= 'radio' name= 'kn' id = 'kn'/>");
                    });

                    return new ResultInfo(ErpConst.ZERO, "接收成功", allCoustomerList);
                }
            }
        }
        return new ResultInfo(ErpConst.ONE, "没有数据", null);
    }

    /**
     * 方法实现说明 弹出页面的跳转
     * @author Bert
     * @date 2018/11/8 19:21
     */
    @ResponseBody
    @RequestMapping(value = "/bingingView")
    public ModelAndView bingingView( String traderCustomerId){
        //将这个traderCustomerId带到下一层页面是为了处理更新操作
        ModelAndView bingingView = new ModelAndView("/trader/customer/view_boundmn");
        bingingView.addObject("traderCustomerId",traderCustomerId);
        return bingingView;
    }

    /**
     * 方法实现说明
     * 将美年的客户编年绑定到本地
     * @param traderCustomer , session
     * @return ResultInfo
     * @author Bert
     * @date 2018/11/9 13:58
     */
    @ResponseBody
    @RequestMapping(value = "/updateToMeiNianCode",method = RequestMethod.POST)
    public ResultInfo updateToMeiNianCode(TraderCustomer traderCustomer , HttpSession session){
        //返回结果
        Integer countTraderCustomerbyMeinianCode = meinianOderService.getCountTraderCustomerbyMeinianCode(traderCustomer.getMeiNianCode());

        ResultInfo resultInfo = null;
        if ( countTraderCustomerbyMeinianCode == ErpConst.ZERO){
            resultInfo = meinianOderService.updateToMeiNianCode(traderCustomer, session);
            //防止空指针异常
            if (ObjectUtils.allNotNull(resultInfo)) {
                //判断是否跟新成功
                if (resultInfo.getCode().equals(ErpConst.ZERO)){
                    return new ResultInfo(ErpConst.ZERO,"success");
                } else {
                    new ResultInfo(ErpConst.ONE,"fail");
                }
            }
        }
        return  new ResultInfo(ErpConst.ONE,"fail");


    }

}

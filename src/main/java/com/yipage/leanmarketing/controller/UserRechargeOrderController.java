package com.yipage.leanmarketing.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.annotation.NeedLoginAnnotation;
import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.UserRechargeOrder;
import com.yipage.leanmarketing.service.UserRechargeOrderService;
import com.yipage.leanmarketing.utils.AliPayUtil;
import com.yipage.leanmarketing.utils.WeiXinUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/18.
 */
@RestController
@RequestMapping("/user/recharge/order")
public class UserRechargeOrderController {


    @Resource
    private UserRechargeOrderService userRechargeOrderService;

    @PostMapping("add")
    @NeedLoginAnnotation
    public Result add(@RequestBody UserRechargeOrder userRechargeOrder) {
        return ResultGenerator.genSuccessResult(userRechargeOrderService.save(userRechargeOrder));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        userRechargeOrderService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody UserRechargeOrder userRechargeOrder) {
        userRechargeOrderService.update(userRechargeOrder);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        UserRechargeOrder userRechargeOrder = userRechargeOrderService.findById(id);
        return ResultGenerator.genSuccessResult(userRechargeOrder);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<UserRechargeOrder> list = userRechargeOrderService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 用户会员充值微信支付
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("pay")
    public Result pay(HttpServletRequest request, Integer rechargeOrderId) throws Exception {

        //根据订单id查找订单信息
        UserRechargeOrder userRechargeOrder = userRechargeOrderService.findById(rechargeOrderId);
        if (userRechargeOrder != null) {
            String spbill_create_ip = request.getRemoteAddr();
            String body = "用户会员充值";
            //微信回调地址
            String url = LeanMarkentingConstant.userWeiXiNotifiUrl;
            Map<String, Object> resMap = WeiXinUtil.pay(spbill_create_ip, userRechargeOrder.getPayMoney(), userRechargeOrder.getPayNumber(), body, userRechargeOrder.getOpenid(), url, "JSAPI");
            return ResultGenerator.genSuccessResult(resMap);
        } else {
            return ResultGenerator.genFailResult("找不到会员充值订单信息");
        }
    }

    /**
     * 会员充值微信支付(PC)
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("pc_pay")
    @NeedLoginAnnotation
    @ResponseBody
    public Result pcPay(HttpServletRequest request, Integer rechargeOrderId) throws Exception {

        //根据订单id查找订单信息
        UserRechargeOrder userRechargeOrder = userRechargeOrderService.findById(rechargeOrderId);
        if (userRechargeOrder != null) {
            String spbill_create_ip = request.getRemoteAddr();
            String body = "用户会员充值";
            //微信回调地址
            String url = LeanMarkentingConstant.userWeiXiNotifiUrl;
            Map<String, Object> resMap = WeiXinUtil.pay(spbill_create_ip, userRechargeOrder.getPayMoney(), userRechargeOrder.getPayNumber(), body, userRechargeOrder.getOpenid(), url, "NATIVE");
            return ResultGenerator.genSuccessResult(resMap);
        } else {
            return ResultGenerator.genFailResult("找不到会员充值订单信息");
        }
    }

    /**
     * 用户会员充值微信支付回调
     */
    @PostMapping("notifyUrl")
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> resMap = WeiXinUtil.notifyUrl(request, response);
        String return_code = (String) resMap.get("return_code");    //返回的状态码
        if (resMap != null) {

            if ("SUCCESS".equals(return_code)) {
                //说明支付成功
                String out_trade_no = (String) resMap.get("out_trade_no");  //订单编号
                //根据订单编号号查找订单
                UserRechargeOrder userRechargeOrder = new UserRechargeOrder();
                userRechargeOrder.setPayNumber(out_trade_no);
                List<UserRechargeOrder> list = userRechargeOrderService.select(userRechargeOrder);
                if (!CollectionUtils.isEmpty(list)) {
                    if (list.get(0).getPayState() == UserRechargeOrder.NOPAY) {
                        userRechargeOrderService.afterPaySucess(list.get(0));
                    }
                }
            }
        }
        response.getWriter().println(return_code);
    }

    /**
     * 会员充值支付宝扫码支付
     *
     * @return
     * @throws Exception
     */
    @GetMapping("pcAlipay")
    @NeedLoginAnnotation
    public void pcAlipay(HttpServletResponse response, Integer rechargeOrderId) throws Exception {

        //根据订单id查找订单信息
        UserRechargeOrder order = userRechargeOrderService.findById(rechargeOrderId);
        if (order != null) {
            String subject = "会员充值支付";
            String body = "支付宝支付";
            String returnUrl = LeanMarkentingConstant.userAliReturnUrl;
            String notifyUrl = LeanMarkentingConstant.userAliNotifyUrl;
            AliPayUtil.pay(body, subject, order.getPayNumber(), order.getPayMoney() + "", returnUrl, notifyUrl, response);
        }
    }
//    /**
//     * 文库支付宝支付回调
//     */
//    @RequestMapping("aliNotifyUrl")
//    public void aliNotifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//        Map<String, String> resMap = AliPayUtil.notifyUrl(request,response);
//
//        if (resMap != null) {
//            boolean signVerified = AlipaySignature.rsaCheckV1(resMap, LeanMarkentingConstant.aliPayPublicKey, LeanMarkentingConstant.charset,
//                    LeanMarkentingConstant.signType);
//            if (signVerified) {
//                //商户订单号
//                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
//                //交易状态
//                String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
//
//                if(trade_status.equals("TRADE_SUCCESS")){//付款成功
//                    //根据订单编号号查找订单
//                    UserRechargeOrder userRechargeOrder = new UserRechargeOrder();
//                    userRechargeOrder.setPayNumber(out_trade_no);
//                    userRechargeOrder.setPayment("支付宝支付");
//                    List<UserRechargeOrder> list = userRechargeOrderService.select(userRechargeOrder);
//                    if(!CollectionUtils.isEmpty(list)){
//
//                        if(list.get(0).getPayState() ==UserRechargeOrder.NOPAY){
//
//                            userRechargeOrderService.afterPaySucess(list.get(0));
//                        }
//                    }
//                }
//                response.getWriter().write("success");
//            } else {//验证失败
//                response.getWriter().write("fail");
//            }
//            response.getWriter().flush();
//            response.getWriter().close();
//        }
//    }

    /**
     * 试题支付宝支付回调页面
     */
    @RequestMapping("aliPayResult")
    public void aliPayResult(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String msg = "";
        Map<String, String> resMap = AliPayUtil.notifyUrl(request, response);
        String url = LeanMarkentingConstant.userAliReturnPage;

        if (resMap != null) {
            boolean signVerified = AlipaySignature.rsaCheckV1(resMap, LeanMarkentingConstant.aliPayPublicKey, LeanMarkentingConstant.charset,
                    LeanMarkentingConstant.signType);
            if (signVerified) {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                //根据订单编号号查找订单
                UserRechargeOrder userRechargeOrder = new UserRechargeOrder();
                userRechargeOrder.setPayNumber(out_trade_no);
                List<UserRechargeOrder> list = userRechargeOrderService.select(userRechargeOrder);
                if (!CollectionUtils.isEmpty(list)) {
                    if (list.get(0).getPayState() == UserRechargeOrder.NOPAY) {
                        list.get(0).setPayment("支付宝支付");
                        userRechargeOrderService.afterPaySucess(list.get(0));
                        msg = "系统订单：" + out_trade_no + "成功支付。";
                        url = url + "orderId=" + list.get(0).getId();
                    }
                } else {
                    msg = "订单号不存在";
                }
                response.getWriter().write("success");
            } else {//验证失败
                response.getWriter().write("fail");
            }
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<html><script>window.onload=()=>{window.location.href='" + url + "';}</script></html>");
        }
    }

}

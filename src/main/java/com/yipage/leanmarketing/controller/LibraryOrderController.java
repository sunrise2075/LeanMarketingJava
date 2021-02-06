package com.yipage.leanmarketing.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.annotation.NeedLoginAnnotation;
import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.LibraryOrder;
import com.yipage.leanmarketing.service.LibraryOrderService;
import com.yipage.leanmarketing.utils.AliPayUtil;
import com.yipage.leanmarketing.utils.MapUtil;
import com.yipage.leanmarketing.utils.WeiXinUtil;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/14.
 */
@Controller
@RequestMapping("/library/order")
public class LibraryOrderController {
    private static final Logger logger = LoggerFactory.getLogger(LibraryOrderController.class);
    @Resource
    private LibraryOrderService libraryOrderService;

    @PostMapping("add")
    @NeedLoginAnnotation
    @ResponseBody
    public Result add(@RequestBody LibraryOrder libraryOrder) {
        Integer libraryOrderId = libraryOrderService.save(libraryOrder);
        return ResultGenerator.genSuccessResult(libraryOrderId);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Result delete(@PathVariable Integer id) {
        libraryOrderService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    @ResponseBody
    public Result update(LibraryOrder libraryOrder) {
        libraryOrder.setUpdateTime(new Date());
        libraryOrderService.update(libraryOrder);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Result detail(@PathVariable Integer id) {
        LibraryOrder libraryOrder = libraryOrderService.findById(id);
        return ResultGenerator.genSuccessResult(libraryOrder);
    }

    @GetMapping("list")
    @ResponseBody
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit) {
        PageHelper.startPage(page, limit);
        List<LibraryOrder> list = libraryOrderService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 小程序文库订单微信支付
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("pay")
    @ResponseBody
    public Result pay(HttpServletRequest request, Integer libraryOrderId) throws Exception {

        //根据订单id查找订单信息
        LibraryOrder libraryOrder = libraryOrderService.findById(libraryOrderId);
        if (libraryOrder != null) {
            String spbill_create_ip = request.getRemoteAddr();
            String body = "小程序文库订单支付";
            //微信回调地址
            String url = LeanMarkentingConstant.libraryWeiXiNotifiUrl;
            Map<String, Object> resMap = WeiXinUtil.pay(spbill_create_ip, libraryOrder.getPayMoney(), libraryOrder.getPayNumber(), body, libraryOrder.getOpenid(), url, "JSAPI");
            return ResultGenerator.genSuccessResult(resMap);
        } else {
            return ResultGenerator.genFailResult("找不到文库订单信息");
        }
    }

    /**
     * 文库订单微信支付(PC端)
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("pc_pay")
    @NeedLoginAnnotation
    @ResponseBody
    public Result pcPay(HttpServletRequest request, Integer libraryOrderId) throws Exception {

        //根据订单id查找订单信息
        LibraryOrder libraryOrder = libraryOrderService.findById(libraryOrderId);
        if (libraryOrder != null) {
            //在session中获取用户信息
            String spbill_create_ip = request.getRemoteAddr();
            String body = "PC端文库订单微信支付";
            //微信回调地址
            String url = LeanMarkentingConstant.libraryWeiXiNotifiUrl;
            Map<String, Object> resMap = WeiXinUtil.pay(spbill_create_ip, libraryOrder.getPayMoney(), libraryOrder.getPayNumber(), body, libraryOrder.getOpenid(), url, "NATIVE");
            return ResultGenerator.genSuccessResult(resMap);
        } else {
            return ResultGenerator.genFailResult("找不到文库订单信息");
        }
    }

    /**
     * 文库微信支付回调
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
                LibraryOrder libraryOrder = new LibraryOrder();
                libraryOrder.setPayNumber(out_trade_no);
                List<LibraryOrder> list = libraryOrderService.select(libraryOrder);
                if (!CollectionUtils.isEmpty(list)) {

                    if (list.get(0).getPayState() == LibraryOrder.NOPAY) {
                        libraryOrderService.afterPaySucess(list.get(0));

                    }
                }
            }
        }
        response.getWriter().println(return_code);
    }

    /**
     * 文库支付宝扫码支付
     *
     * @return
     * @throws Exception
     */
    @NeedLoginAnnotation
    @GetMapping("pcAlipay")
    public void pcAlipay(HttpServletResponse response, Integer libraryOrderId) throws Exception {

        //根据订单id查找订单信息
        LibraryOrder order = libraryOrderService.findById(libraryOrderId);
        if (order != null) {
            String subject = "文库支付";
            String body = "支付宝支付";
            String returnUrl = LeanMarkentingConstant.libraryAliReturnUrl;
            String notifyUrl = LeanMarkentingConstant.libraryAliNotifyUrl;
            AliPayUtil.pay(body, subject, order.getPayNumber(), order.getPayMoney() + "", returnUrl, notifyUrl, response);
        }
    }
    /**
     * 文库支付宝支付回调
     */
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
//                    //根据订单编号查找订单
//                    LibraryOrder libraryOrder = new LibraryOrder();
//                    libraryOrder.setPayNumber(out_trade_no);
//                    List<LibraryOrder> list = libraryOrderService.select(libraryOrder);
//                    if(!CollectionUtils.isEmpty(list)){
//
//                        if(list.get(0).getPayState() == LibraryOrder.NOPAY){
//                            libraryOrderService.afterPaySucess(list.get(0));
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
     * 文库支付宝支付回调页面
     */
    @RequestMapping("aliPayResult")
    public void aliPayResult(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String msg = "";
        String url = "";
        Map<String, String> resMap = AliPayUtil.notifyUrl(request, response);


        if (resMap != null) {
            boolean signVerified = AlipaySignature.rsaCheckV1(resMap, LeanMarkentingConstant.aliPayPublicKey, LeanMarkentingConstant.charset,
                    LeanMarkentingConstant.signType);

            logger.info("-------------" + signVerified);

            if (signVerified) {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                //根据订单编号查找订单
                LibraryOrder libraryOrder = new LibraryOrder();
                libraryOrder.setPayNumber(out_trade_no);
                List<LibraryOrder> list = libraryOrderService.select(libraryOrder);
                if (!CollectionUtils.isEmpty(list)) {
                    if (list.get(0).getPayState() == LibraryOrder.NOPAY) {
                        try {
                            list.get(0).setPayment("支付宝支付");
                            url = list.get(0).getUrl() + "libraryOrderId=" + list.get(0).getId() + "&libraryId=" + list.get(0).getLibraryId();
                            libraryOrderService.afterPaySucess(list.get(0));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        msg = "系统订单：" + out_trade_no + "成功支付。";
                    }
                } else {
                    msg = "订单号不存在";
                }
            } else {//验证失败
                response.getWriter().write("fail");
            }
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<html><script>window.onload=()=>{window.location.href='" + url + "';}</script></html>");

        }
    }
}

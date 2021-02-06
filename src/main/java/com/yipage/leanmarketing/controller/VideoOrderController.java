package com.yipage.leanmarketing.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.annotation.NeedLoginAnnotation;
import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.VideoOrder;
import com.yipage.leanmarketing.service.VideoOrderService;
import com.yipage.leanmarketing.service.VideoService;
import com.yipage.leanmarketing.utils.AliPayUtil;
import com.yipage.leanmarketing.utils.WeiXinUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/12.
 */
@Controller
@RequestMapping("/video/order")
public class VideoOrderController {
    @Resource
    private VideoOrderService videoOrderService;

    @Resource
    private VideoService videoService;

    @PostMapping("add")
    @NeedLoginAnnotation
    @ResponseBody
    public Result add(@RequestBody VideoOrder videoOrder) {

        Integer result = videoOrderService.save(videoOrder);
        if (result == 0) {
            return ResultGenerator.genFailResult("生成订单错误");
        }
        return ResultGenerator.genSuccessResult(result);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Result delete(@PathVariable Integer id) {
        videoOrderService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping
    @ResponseBody
    public Result update(@RequestBody VideoOrder videoOrder) {
        videoOrderService.update(videoOrder);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Result detail(@PathVariable Integer id) {
        VideoOrder videoOrder = videoOrderService.findById(id);
        return ResultGenerator.genSuccessResult(videoOrder);
    }

    /**
     * 根据条件分页查订单
     *
     * @return
     */
    @GetMapping
    @ResponseBody
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<VideoOrder> list = videoOrderService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 根据条件查所有订单
     *
     * @return
     */
    @GetMapping("select")
    @ResponseBody
    public Result select() {
        VideoOrder videoOrder = new VideoOrder();
        List<VideoOrder> list = videoOrderService.select(videoOrder);
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 视频微信支付
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("pay")
    @ResponseBody
    public Result pay(HttpServletRequest request, Integer videoOrderId) throws Exception {

        //根据订单id查找订单信息
        VideoOrder videoOrder = videoOrderService.findById(videoOrderId);
        if (videoOrder != null) {
            String spbill_create_ip = request.getRemoteAddr();
            String body = "小程序视频支付";
            //微信回调地址
            String url = LeanMarkentingConstant.videoWeiXiNotifiUrl;

            //Map<String,Object > resMap =  WeiXinUtil.pay(spbill_create_ip,videoOrder.getPayMoney(),videoOrder.getPayNumber(),body,user.getWxid(),url);
            Map<String, Object> resMap = WeiXinUtil.pay(spbill_create_ip, videoOrder.getPayMoney(), videoOrder.getPayNumber(), body, videoOrder.getOpenid(), url, "JSAPI");
            return ResultGenerator.genSuccessResult(resMap);
        } else {
            return ResultGenerator.genFailResult("找不到视频订单信息");
        }
    }

    /**
     * 视频微信支付(PC)
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("pc_pay")
    @NeedLoginAnnotation
    @ResponseBody
    public Result pcPay(HttpServletRequest request, Integer videoOrderId) throws Exception {

        //根据订单id查找订单信息
        VideoOrder videoOrder = videoOrderService.findById(videoOrderId);
        if (videoOrder != null) {
            //在session中获取用户信息
            String spbill_create_ip = request.getRemoteAddr();
            String body = "PC端视频微信支付";
            //微信回调地址
            String url = LeanMarkentingConstant.videoWeiXiNotifiUrl;

            Map<String, Object> resMap = WeiXinUtil.pay(spbill_create_ip, videoOrder.getPayMoney(), videoOrder.getPayNumber(), body, videoOrder.getOpenid(), url, "NATIVE");
            return ResultGenerator.genSuccessResult(resMap);
        } else {
            return ResultGenerator.genFailResult("找不到视频订单信息");
        }
    }


    /**
     * 视频微信支付回调
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
                VideoOrder videoOrder = new VideoOrder();
                videoOrder.setPayNumber(out_trade_no);
                List<VideoOrder> videoOrderList = videoOrderService.select(videoOrder);
                if (!CollectionUtils.isEmpty(videoOrderList)) {

                    if (videoOrderList.get(0).getPayState() == VideoOrder.NOPAY) {

                        videoOrderService.afterPaySucess(videoOrderList.get(0));

                    }
                }
            }
        }
        response.getWriter().println(return_code);
    }

    /**
     * 视频支付宝扫码支付
     *
     * @return
     * @throws Exception
     */
    @GetMapping("pcAlipay")
    @NeedLoginAnnotation
    public void pcAlipay(HttpServletResponse response, Integer videoOrderId) throws Exception {

        //根据订单id查找订单信息
        VideoOrder order = videoOrderService.findById(videoOrderId);
        if (order != null) {
            String subject = "视频支付";
            String body = "支付宝支付";
            String returnUrl = LeanMarkentingConstant.videoAliReturnUrl;
            String notifyUrl = LeanMarkentingConstant.videoAliNotifyUrl;
            AliPayUtil.pay(body, subject, order.getPayNumber(), order.getPayMoney() + "", returnUrl, notifyUrl, response);
        }
    }
//    /**
//     * 视频支付宝支付回调
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
//                    VideoOrder videoOrder = new VideoOrder();
//                    videoOrder.setPayNumber(out_trade_no);
//                    List<VideoOrder> videoOrderList = videoOrderService.select(videoOrder);
//                    if(!CollectionUtils.isEmpty(videoOrderList)){
//
//                        if(videoOrderList.get(0).getPayState() ==VideoOrder.NOPAY){
//
//                            videoOrderService.afterPaySucess(videoOrderList.get(0));
//
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
     * 视频支付宝支付回调页面
     */
    @RequestMapping("aliPayResult")
    public void aliPayResult(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String msg = "";
        Integer videoId = null;
        Map<String, String> resMap = AliPayUtil.notifyUrl(request, response);

        if (resMap != null) {
            boolean signVerified = AlipaySignature.rsaCheckV1(resMap, LeanMarkentingConstant.aliPayPublicKey, LeanMarkentingConstant.charset,
                    LeanMarkentingConstant.signType);
            if (signVerified) {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                //根据订单编号查找订单
                //根据订单编号号查找订单
                VideoOrder videoOrder = new VideoOrder();
                videoOrder.setPayNumber(out_trade_no);
                List<VideoOrder> list = videoOrderService.select(videoOrder);
                if (list.get(0).getPayState() == VideoOrder.NOPAY) {
                    list.get(0).setPaymemt("支付宝支付");
                    videoId = list.get(0).getVideoId();
                    videoOrderService.afterPaySucess(list.get(0));
                    msg = "系统订单：" + out_trade_no + "成功支付。";
                } else {
                    msg = "订单号不存在";
                }

                response.getWriter().write("success");
            } else {//验证失败
                response.getWriter().write("fail");
            }
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<html><script>window.onload=()=>{window.location.href='" + LeanMarkentingConstant.videoAliReturnPage + videoId + "';}</script></html>");
        }
    }
}

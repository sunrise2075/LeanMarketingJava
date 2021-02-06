package com.yipage.leanmarketing.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.annotation.NeedLoginAnnotation;
import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.ExamOrder;
import com.yipage.leanmarketing.service.ExamOrderService;
import com.yipage.leanmarketing.service.ExamSubjectService;
import com.yipage.leanmarketing.utils.AliPayUtil;
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
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/13.
 */
@Controller
@RequestMapping("/exam/order")
public class ExamOrderController {

    private final Logger logger = LoggerFactory.getLogger(ExamOrder.class);
    @Resource
    private ExamOrderService examOrderService;
    @Resource
    private ExamSubjectService examSubjectService;

    @NeedLoginAnnotation
    @PostMapping("add")
    @ResponseBody
    public Result add(@RequestBody ExamOrder examOrder) {

        Integer result = examOrderService.save(examOrder);
        return ResultGenerator.genSuccessResult(result);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Result delete(@PathVariable Integer id) {
        examOrderService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping
    @ResponseBody
    public Result update(@RequestBody ExamOrder examOrder) {
        examOrderService.update(examOrder);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Result detail(@PathVariable Integer id) {
        ExamOrder examOrder = examOrderService.findById(id);
        return ResultGenerator.genSuccessResult(examOrder);
    }

    @GetMapping
    @ResponseBody
    public Result list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        PageHelper.startPage(page, size);
        List<ExamOrder> list = examOrderService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 小程序试题微信支付
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("pay")
    @ResponseBody
    public Result pay(HttpServletRequest request, Integer examOrderId) throws Exception {

        //根据订单id查找订单信息
        ExamOrder examOrder = examOrderService.findById(examOrderId);
        if (examOrder != null) {
            String spbill_create_ip = request.getRemoteAddr();
            String body = "小程序试题支付";
            //微信回调地址
            String url = LeanMarkentingConstant.examWeiXiNotifiUrl;
            Map<String, Object> resMap = WeiXinUtil.pay(spbill_create_ip, examOrder.getPayMoney(), examOrder.getPayNumber(), body, examOrder.getOpenid(), url, "JSAPI");
            return ResultGenerator.genSuccessResult(resMap);
        } else {
            return ResultGenerator.genFailResult("找不到试题订单信息");
        }
    }

    /**
     * 试题微信支付
     *
     * @param request
     * @return
     * @throws Exception
     */
    @NeedLoginAnnotation
    @GetMapping("pc_pay")
    @ResponseBody
    public Result pcPay(HttpServletRequest request, Integer examOrderId) throws Exception {

        //根据订单id查找订单信息
        ExamOrder examOrder = examOrderService.findById(examOrderId);
        if (examOrder != null) {
            String spbill_create_ip = request.getRemoteAddr();
            String body = "PC端试题微信支付";
            //微信回调地址
            String url = LeanMarkentingConstant.examWeiXiNotifiUrl;
            Map<String, Object> resMap = WeiXinUtil.pay(spbill_create_ip, examOrder.getPayMoney(), examOrder.getPayNumber(), body, examOrder.getOpenid(), url, "NATIVE");
            return ResultGenerator.genSuccessResult(resMap);
        } else {
            return ResultGenerator.genFailResult("找不到试题订单信息");
        }
    }

    /**
     * 试题支付宝扫码支付
     *
     * @return
     * @throws Exception
     */
    @NeedLoginAnnotation
    @GetMapping("pcAlipay")
    public void pcAlipay(HttpServletResponse response, Integer examOrderId) throws Exception {

        //根据订单id查找订单信息
        ExamOrder examOrder = examOrderService.findById(examOrderId);
        if (examOrder != null) {
            String subject = "试题支付";
            String body = "支付宝支付";
            String returnUrl = LeanMarkentingConstant.examAliReturnUrl;
            String notifyUrl = LeanMarkentingConstant.examAliNotifyUrl;
            AliPayUtil.pay(body, subject, examOrder.getPayNumber(), examOrder.getPayMoney() + "", returnUrl, notifyUrl, response);
        }
    }

    /**
     * 试题微信支付回调
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
                ExamOrder examOrder = new ExamOrder();
                examOrder.setPayNumber(out_trade_no);
                List<ExamOrder> list = examOrderService.select(examOrder);
                if (!CollectionUtils.isEmpty(list)) {

                    if (list.get(0).getPayState() == ExamOrder.NOPAY) {
                        examOrderService.afterPaySucess(list.get(0));
                    }
                }
            }
        }
        response.getWriter().println(return_code);
    }
//    /**
//     * 试题支付宝支付回调
//     */
//    @GetMapping("aliNotifyUrl")
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
//                    ExamOrder examOrder = new ExamOrder();
//                    examOrder.setPayNumber(out_trade_no);
//                    examOrder.setPayment("支付宝支付");
//                    List<ExamOrder> list = examOrderService.select(examOrder);
//                    if (!CollectionUtils.isEmpty(list)) {
//                        if (list.get(0).getPayState() == ExamOrder.NOPAY) {
//                            examOrderService.afterPaySucess(list.get(0));
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
        Integer subjectId = null;
        Integer examDuration = null;
        String url = "";
        Map<String, String> resMap = AliPayUtil.notifyUrl(request, response);

        if (resMap != null) {
            boolean signVerified = AlipaySignature.rsaCheckV1(resMap, LeanMarkentingConstant.aliPayPublicKey, LeanMarkentingConstant.charset,
                    LeanMarkentingConstant.signType);
            if (signVerified) {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                //根据订单编号号查找订单
                ExamOrder examOrder = new ExamOrder();
                examOrder.setPayNumber(out_trade_no);
                List<ExamOrder> list = examOrderService.select(examOrder);
                if (!CollectionUtils.isEmpty(list)) {
                    if (list.get(0).getPayState() == ExamOrder.NOPAY) {
                        subjectId = list.get(0).getSubjectId();

                        examDuration = examSubjectService.findById(list.get(0).getSubjectId()).getExamDuration();
                        list.get(0).setPayment("支付宝支付");
                        examOrderService.afterPaySucess(list.get(0));
                        msg = "系统订单：" + out_trade_no + "成功支付。";
                    }
                } else {
                    msg = "订单号不存在";
                }
                response.getWriter().write("success");
            } else {//验证失败
                response.getWriter().write("fail");
                msg = "回调签名失败";
            }
            url = LeanMarkentingConstant.examAliReturnPage + "subjectId=" + subjectId + "&examDuration=" + examDuration;
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<html><script>window.onload=()=>{window.location.href='" + url + "';}</script></html>");
        }
    }
}

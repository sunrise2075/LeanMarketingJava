package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.LessonCollageOrder;
import com.yipage.leanmarketing.service.LessonCollageOrderService;
import com.yipage.leanmarketing.utils.WeiXinUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2019/04/16.
 */
@Controller
@RequestMapping("/lesson/collage/order")
public class LessonCollageOrderController {
    @Resource
    private LessonCollageOrderService lessonCollageOrderService;

    /**
     * 自定义方法绑定请求参数的Date类型
     *
     * @param request
     * @param binder
     * @throws Exception
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
        binder.registerCustomEditor(Date.class, editor);
    }

    @PostMapping("add")
    @ResponseBody
    public Result add(@RequestBody LessonCollageOrder lessonCollageOrder) {
//        Integer orderId = lessonCollageOrderService.save(lessonCollageOrder);
//        return ResultGenerator.genSuccessResult(lessonCollageOrderService.findById(orderId));
        return lessonCollageOrderService.createOrder(lessonCollageOrder);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Result delete(@PathVariable Integer id) {
        lessonCollageOrderService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    @ResponseBody
    public Result update(@RequestBody LessonCollageOrder lessonCollageOrder) {
        lessonCollageOrderService.update(lessonCollageOrder);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Result detail(@PathVariable Integer id) {
        LessonCollageOrder lessonCollageOrder = lessonCollageOrderService.findById(id);
        return ResultGenerator.genSuccessResult(lessonCollageOrder);
    }

    @GetMapping
    @ResponseBody
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<LessonCollageOrder> list = lessonCollageOrderService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 小程序拼团微信支付
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("pay")
    @ResponseBody
    public Result pay(HttpServletRequest request, Integer orderId) throws Exception {

        //根据订单id查找订单信息
        LessonCollageOrder order = lessonCollageOrderService.findById(orderId);
        if (order != null) {
            String spbill_create_ip = request.getRemoteAddr();
            String body = "小程序拼团支付";
            //微信回调地址
            String url = LeanMarkentingConstant.lessonCollageWeiXiNotifiUrl;
            Map<String, Object> resMap = WeiXinUtil.pay(spbill_create_ip, order.getPrice(), order.getPayNumber(), body, order.getOpenid(), url, "JSAPI");
            return ResultGenerator.genSuccessResult(resMap);
        } else {
            return ResultGenerator.genFailResult("找不到试题订单信息");
        }
    }

    /**
     * 拼团微信支付回调
     */
    @PostMapping("notifyUrl")
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> resMap = WeiXinUtil.notifyUrl(request, response);

        String return_code = (String) resMap.get("return_code");    //返回的状态码
        if (resMap != null) {

            if ("SUCCESS".equals(return_code)) {

                String out_trade_no = (String) resMap.get("out_trade_no");  //订单编号
                //根据订单编号号查找订单
                LessonCollageOrder order = lessonCollageOrderService.findBy("payNumber", out_trade_no);

                if (order.getPayState() == LessonCollageOrder.NOPAY) {
                    lessonCollageOrderService.afterPaySucess(order);
                }
            }
        }
        response.getWriter().println(return_code);
    }

    @GetMapping("test")
    @ResponseBody
    public void test() {
        LessonCollageOrder order = lessonCollageOrderService.findBy("payNumber", "20190513021119967706");
        lessonCollageOrderService.afterPaySucess(order);
    }

}

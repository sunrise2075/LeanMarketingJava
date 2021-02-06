package com.yipage.leanmarketing.controller;

import com.alipay.api.internal.util.AlipaySignature;
import com.yipage.leanmarketing.annotation.NeedLoginAnnotation;
import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.Product;
import com.yipage.leanmarketing.model.ProductOrder;
import com.yipage.leanmarketing.service.ProductOrderService;
import com.yipage.leanmarketing.service.ProductService;
import com.yipage.leanmarketing.utils.AliPayUtil;
import com.yipage.leanmarketing.utils.HttpUtil;
import com.yipage.leanmarketing.utils.WeiXinUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/14.
 */
@Controller
@RequestMapping("/product/order")
public class ProductOrderController {
    @Resource
    private ProductOrderService productOrderService;
    @Resource
    private ProductService productService;
    @Resource
    private HttpServletRequest request;

    @NeedLoginAnnotation
    @PostMapping("add")
    @ResponseBody
    public Result add(@RequestBody ProductOrder productOrder) {
        return ResultGenerator.genSuccessResult(productOrderService.save(productOrder));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Result delete(@PathVariable Integer id) {
        productOrderService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    @ResponseBody
    public Result update(ProductOrder productOrder) {

        productOrderService.update(productOrder);
        return ResultGenerator.genSuccessResult();
    }


    @GetMapping("/{id}")
    @ResponseBody
    public Result detail(@PathVariable Integer id) {
        ProductOrder productOrder = productOrderService.findById(id);
        return ResultGenerator.genSuccessResult(productOrder);
    }

    @GetMapping("list")
    @ResponseBody
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "payState", required = false) Integer payState,
                    @RequestParam(value = "startTime", required = false) String startTime,
                    @RequestParam(value = "endTime", required = false) String endTime,
                    @RequestParam(value = "payNumber", required = false) String payNumber,
                    @RequestParam(value = "userName", required = false) String userName,
                    @RequestParam(value = "productName", required = false) String productName,
                    @RequestParam(value = "openid", required = false) String openid) {

        Map<String, Object> resMap = productOrderService.index(payState, page, limit, startTime, endTime, payNumber, userName, productName, openid);

        return resMap;
    }

    /**
     * 商品微信支付
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("pay")
    @ResponseBody
    public Result pay(HttpServletRequest request, Integer productOrderId) throws Exception {

        //根据订单id查找订单信息
        ProductOrder productOrder = productOrderService.findById(productOrderId);
        if (productOrder != null) {
            //在session中获取用户信息
            String spbill_create_ip = request.getRemoteAddr();
            String body = "小程序商品支付";
            //微信回调地址
            String url = LeanMarkentingConstant.productWeiXiNotifiUrl;
            Map<String, Object> resMap = WeiXinUtil.pay(spbill_create_ip, productOrder.getPayMoney(), productOrder.getPayNumber(), body, productOrder.getOpenid(), url, "JSAPI");
            return ResultGenerator.genSuccessResult(resMap);
        } else {
            return ResultGenerator.genFailResult("找不到商品订单信息");
        }
    }

    /**
     * 商品微信支付(PC)
     *
     * @param request
     * @return
     * @throws Exception
     */

    @NeedLoginAnnotation
    @GetMapping("pc_pay")
    @ResponseBody
    public Result pcPay(HttpServletRequest request, Integer productOrderId) throws Exception {

        //根据订单id查找订单信息
        ProductOrder productOrder = productOrderService.findById(productOrderId);
        if (productOrder != null) {
            //在session中获取用户信息
            String spbill_create_ip = request.getRemoteAddr();
            String body = "PC端商品微信支付";
            //微信回调地址
            String url = LeanMarkentingConstant.productWeiXiNotifiUrl;
            Map<String, Object> resMap = WeiXinUtil.pay(spbill_create_ip, productOrder.getPayMoney(), productOrder.getPayNumber(), body, productOrder.getOpenid(), url, "NATIVE");
            return ResultGenerator.genSuccessResult(resMap);
        } else {
            return ResultGenerator.genFailResult("找不到商品订单信息");
        }
    }

    /**
     * 商品微信支付回调
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
                ProductOrder productOrder = new ProductOrder();
                productOrder.setPayNumber(out_trade_no);
                List<ProductOrder> productOrderOrderList = productOrderService.select(productOrder);
                if (!CollectionUtils.isEmpty(productOrderOrderList)) {

                    if (productOrderOrderList.get(0).getPayState() == ProductOrder.NOPAY) {
                        //更新订单
                        ProductOrder updateOrder = new ProductOrder();
                        updateOrder.setId(productOrderOrderList.get(0).getId());
                        updateOrder.setPayTime(new Date());
                        updateOrder.setPayState(ProductOrder.NEEDSEND);
                        productOrderService.update(updateOrder);

                        //查找商品
                        Product p = productService.findById(productOrderOrderList.get(0).getProductId());
                        //更新商品销售量
                        if (p != null) {
                            Product product = new Product();
                            product.setId(p.getId());
                            product.setUpdateTime(new Date());
                            product.setSalesNum(p.getSalesNum() + productOrderOrderList.get(0).getProductNum());

                            productService.update(product);
                        }
                    }
                }
            }
        }
        response.getWriter().println(return_code);
    }

    /**
     * 商品支付宝扫码支付
     *
     * @return
     * @throws Exception
     */
    @GetMapping("pcAlipay")
    @NeedLoginAnnotation
    public void pcAlipay(HttpServletResponse response, Integer productOrderId) throws Exception {

        //根据订单id查找订单信息
        ProductOrder order = productOrderService.findById(productOrderId);
        if (order != null) {
            String subject = "商品支付";
            String body = "支付宝支付";
            String returnUrl = LeanMarkentingConstant.productAliReturnUrl;
            String notifyUrl = LeanMarkentingConstant.productAliNotifyUrl;
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
//                    ProductOrder productOrder = new ProductOrder();
//                    productOrder.setPayNumber(out_trade_no);
//                    List<ProductOrder> productOrderOrderList = productOrderService.select(productOrder);
//                    if(!CollectionUtils.isEmpty(productOrderOrderList)){
//
//                        if(productOrderOrderList.get(0).getPayState() ==ExamOrder.ISPAY){
//                            //更新订单
//                            ProductOrder updateOrder = new ProductOrder();
//                            updateOrder.setId(productOrderOrderList.get(0).getId());
//                            updateOrder.setPayTime(new Date());
//                            updateOrder.setPayState(ProductOrder.NEEDSEND);
//                            productOrderService.update(updateOrder);
//                            //查找商品
//                            Product p = productService.findById(productOrderOrderList.get(0).getProductId());
//                            //更新商品销售量
//                            if(p !=null){
//                                Product product = new Product();
//                                product.setId(p .getId());
//                                product.setUpdateTime(new Date());
//                                product.setSalesNum(p .getSalesNum()+productOrderOrderList.get(0).getProductNum());
//
//                                productService.update(product);
//                            }
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
     * 商品支付宝支付回调页面
     */
    @RequestMapping("aliPayResult")
    public void aliPayResult(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String msg = "";
        Map<String, String> resMap = AliPayUtil.notifyUrl(request, response);
        String url = LeanMarkentingConstant.productAliReturnPage;

        if (resMap != null) {
            boolean signVerified = AlipaySignature.rsaCheckV1(resMap, LeanMarkentingConstant.aliPayPublicKey, LeanMarkentingConstant.charset,
                    LeanMarkentingConstant.signType);
            if (signVerified) {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

                //根据订单编号号查找订单
                ProductOrder productOrder = new ProductOrder();
                productOrder.setPayNumber(out_trade_no);
                List<ProductOrder> list = productOrderService.select(productOrder);
                if (!CollectionUtils.isEmpty(list)) {
                    if (list.get(0).getPayState() == ProductOrder.NOPAY) {
                        //更新订单
                        ProductOrder updateOrder = new ProductOrder();
                        updateOrder.setId(list.get(0).getId());
                        updateOrder.setPayTime(new Date());
                        updateOrder.setPayment("支付宝支付");
                        updateOrder.setPayState(ProductOrder.NEEDSEND);
                        productOrderService.update(updateOrder);
                        //查找商品
                        Product p = productService.findById(list.get(0).getProductId());
                        //更新商品销售量
                        if (p != null) {
                            Product product = new Product();
                            product.setId(p.getId());
                            product.setUpdateTime(new Date());
                            product.setSalesNum(p.getSalesNum() + list.get(0).getProductNum());

                            productService.update(product);

                            url = url + "orderId=" + list.get(0).getId();
                        }
                        msg = "系统订单：" + out_trade_no + "成功支付。";
                    }
                } else {
                    msg = "订单号不存在";
                }
                //response.getWriter().write("success");
            } else {//验证失败
                //response.getWriter().write("fail");
            }
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<html><script>window.onload=()=>{window.location.href='" + url + "';}</script></html>");
        }
    }

    /***
     * 取消订单
     */
    @GetMapping("/cancelOrder/{productOrderId}")
    @ResponseBody
    public Result cancelOrder(@PathVariable Integer productOrderId) {

        ProductOrder productOrder = new ProductOrder();
        productOrder.setId(productOrderId);
        productOrder.setUpdateTime(new Date());
        productOrder.setPayState(ProductOrder.ISCANCEL);
        productOrderService.update(productOrder);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 确定收货
     */
    @GetMapping("/conformReceive/{productOrderId}")
    @ResponseBody
    public Result conformReceive(@PathVariable Integer productOrderId) {

        ProductOrder productOrder = new ProductOrder();
        productOrder.setId(productOrderId);
        productOrder.setUpdateTime(new Date());
        productOrder.setReceiveTime(new Date());
        productOrder.setPayState(ProductOrder.NOEVALUATE);
        productOrderService.update(productOrder);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("selectAllOrderStateNum")
    @ResponseBody
    public Result selectAllOrderStateNum(String openid) {

        return ResultGenerator.genSuccessResult(productOrderService.selectAllOrderStateNum(openid));
    }


    /**
     * 导出订单信息
     */
    @GetMapping("exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //第一步查找数据
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String payNumber = request.getParameter("payNumber");
        String userName = request.getParameter("userName");
        String productName = request.getParameter("productName");
        String state = request.getParameter("payState");
        Integer payState = null;
        if (StringUtils.isNotEmpty(state)) {
            payState = Integer.parseInt(state);
        }

        List<Map<String, Object>> list = productOrderService.exportExcel(payState, startTime, endTime, payNumber, userName, productName);

        //第二步数据转成excel

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 第一步：定义一个新的工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步：创建一个Sheet页
        XSSFSheet sheet = wb.createSheet("订单信息");

        sheet.setDefaultRowHeight((short) (2 * 256));//设置行高

        for (int i = 0; i < 11; i++) {
            sheet.setColumnWidth(i, 4000);//设置列宽
        }

        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);

        XSSFRow row = sheet.createRow(0);

        XSSFCell cell = row.createCell(0);
        cell.setCellValue("商品名称 ");
        cell = row.createCell(1);
        cell.setCellValue("商品单价 ");
        cell = row.createCell(2);
        cell.setCellValue("商品数量");
        cell = row.createCell(3);
        cell.setCellValue("用户名称");
        cell = row.createCell(4);
        cell.setCellValue("支付方式");
        cell = row.createCell(5);
        cell.setCellValue("支付订单号 ");
        cell = row.createCell(6);
        cell.setCellValue("支付状态");
        cell = row.createCell(7);
        cell.setCellValue("支付金额 ");
        cell = row.createCell(8);
        cell.setCellValue("支付时间");
        cell = row.createCell(9);
        cell.setCellValue("订单生成时间");
        cell = row.createCell(10);
        cell.setCellValue("订单更新时间");

        XSSFRow rows;
        XSSFCell cells;
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            // 第三步：在这个sheet页里创建一行
            rows = sheet.createRow(i + 1);
            // 第四步：在该行创建一个单元格
            cells = rows.createCell(0);
            // 第五步：在该单元格里设置值
            cells.setCellValue((String) map.get("productName"));
            cells = rows.createCell(1);
            if (map.get("productPrice") == null) {
                cells.setCellValue("0");
            } else {
                cells.setCellValue(map.get("productPrice") + "");
            }
            cells = rows.createCell(2);
            cells.setCellValue((Integer) map.get("productNum"));
            cells = rows.createCell(3);
            cells.setCellValue((String) map.get("userName"));
            cells = rows.createCell(4);
            cells.setCellValue((String) map.get("payment"));
            cells = rows.createCell(5);
            cells.setCellValue((String) map.get("payNumber"));
            cells = rows.createCell(6);
            payState = (Integer) map.get("payState");
            if (payState == 1) {
                //待付款状态
                cells.setCellValue("待付款");
            } else if (payState == 2) {
                //待收货状态
                cells.setCellValue("待收货");
            } else if (payState == 3) {
                //待评价状态
                cells.setCellValue("待评价");
            } else if (payState == 4) {
                //成功状态
                cells.setCellValue("成功");
            } else {
                //取消状态
                cells.setCellValue("取消");
            }
            cells = rows.createCell(7);
            cells.setCellValue(map.get("payMoney") + "");

            cells = rows.createCell(8);
            if (map.get("payTime") == null) {
                cells.setCellValue("");
            } else {
                cells.setCellValue(simpleDateFormat.format(map.get("payTime")));
            }


            cells = rows.createCell(9);
            cells.setCellValue(simpleDateFormat.format(map.get("createTime")));

            cells = rows.createCell(10);
            cells.setCellValue(simpleDateFormat.format(map.get("updateTime")));
        }

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            String fileName = "订单信息";
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("gb2312"), StandardCharsets.ISO_8859_1) + ".xlsx"); //支持中文文件名
            response.setContentType("multipart/form-data");
            OutputStream ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 订单发货
     */
    @PostMapping("send")
    @ResponseBody
    public Result send(ProductOrder productOrder) {

        Integer result = productOrderService.send(productOrder);

        if (result == 0) {  //说明更新失败了
            return ResultGenerator.genFailResult("发货失败,请重新发货");
        } else {
            return ResultGenerator.genFailResult("发货成功");
        }

    }

    /**
     * 查看物流
     */
    @GetMapping("getLogisticsInfo")
    @ResponseBody
    public String getLogisticsInfo(String courierNumber) {

        try {
            String host = LeanMarkentingConstant.host;
            String path = LeanMarkentingConstant.path;
            String method = "GET";
            String appcode = LeanMarkentingConstant.logisticsAppCode;
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "APPCODE " + appcode);
            Map<String, String> querys = new HashMap<>();
            querys.put("number", courierNumber);

            return HttpUtil.doGet(host, path, method, headers, querys);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}

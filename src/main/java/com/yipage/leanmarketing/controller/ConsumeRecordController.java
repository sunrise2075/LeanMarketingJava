package com.yipage.leanmarketing.controller;

import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.ConsumeRecord;
import com.yipage.leanmarketing.model.Dealer;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.service.ConsumeRecordService;
import com.yipage.leanmarketing.service.DealerService;
import com.yipage.leanmarketing.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/18.
 */
@RestController
@RequestMapping("/consume/record")
public class ConsumeRecordController {

    @Resource
    private ConsumeRecordService consumeRecordService;
    @Resource
    private UserService userService;
    @Resource
    private DealerService dealerService;


    @PostMapping("add")
    public Result add(@RequestBody ConsumeRecord consumeRecord) {

        consumeRecordService.save(consumeRecord);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        consumeRecordService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody ConsumeRecord consumeRecord) {
        consumeRecordService.update(consumeRecord);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ConsumeRecord consumeRecord = consumeRecordService.findById(id);
        return ResultGenerator.genSuccessResult(consumeRecord);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "address", required = false) String address,
                    @RequestParam(value = "type", required = false) Integer type,
                    @RequestParam(value = "superiorId", required = false) Integer superiorId,
                    @RequestParam(value = "dealerId", required = false) Integer dealerId) {

        if (dealerId != null) {
            Dealer dealer = dealerService.findById(dealerId);
            User user = userService.findBy("phone", dealer.getPhone());
            if (user != null) {
                superiorId = user.getId();
            } else {
                superiorId = -1;
            }
        }
        return consumeRecordService.list(page, limit, address, superiorId, type);
    }

    /**
     * 代理商的收益流水
     */
    @GetMapping("revenueStreams")
    public Map revenueStreams(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer limit,
                              @RequestParam(value = "year", required = false) Integer year,
                              @RequestParam(value = "month", required = false) Integer month,
                              @RequestParam(value = "day", required = false) Integer day,
                              @RequestParam(value = "type", required = false) Integer type,
                              @RequestParam(value = "address", required = false) String address) {

        //前端说传个5代表全部

        if (type != null && type == 5) {
            type = null;
        }
        Map<String, Object> revenueStreamsMap = consumeRecordService.revenueStreams(page, limit, year, month, day, type, address);

        return revenueStreamsMap;
    }

    /**
     * 导出消费记录
     */
    @GetMapping("exportExcel")
    @ResponseBody
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //第一步查找数据
        //List<User> list = userService.exportExcel(identity,memberLevel,address);
        Integer superiorId = null;
        Integer type = null;
        String address = request.getParameter("address");
        if (StringUtils.isNotEmpty(request.getParameter("superiorId"))) {
            superiorId = Integer.parseInt(request.getParameter("superiorId"));
        }
        if (StringUtils.isNotEmpty(request.getParameter("type"))) {
            type = Integer.parseInt(request.getParameter("type"));
        }
        Map<String, Object> map = consumeRecordService.list(null, null, address, superiorId, type);

//        Condition condition = new Condition(ConsumeRecord.class);
//        Example.Criteria criteria = condition.createCriteria();
//        if(StringUtils.isNotEmpty(address)){
//            criteria.andCondition("address like  '%"+address+"%'");
//        }
//        List<ConsumeRecord> list = consumeRecordService.findByCondition(condition);
        List<ConsumeRecord> list = (List<ConsumeRecord>) map.get("data");
        //第二步数据转成excel

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 第一步：定义一个新的工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步：创建一个Sheet页
        XSSFSheet sheet = wb.createSheet("消费记录信息");

        sheet.setDefaultRowHeight((short) (2 * 256));//设置行高

        for (int i = 0; i <= 7; i++) {
            sheet.setColumnWidth(i, 4000);//设置列宽
        }
        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);

        XSSFRow row = sheet.createRow(0);

        XSSFCell cell = row.createCell(0);
        cell.setCellValue("用户名称");
        cell = row.createCell(1);
//        cell.setCellValue("用户等级");
//        cell = row.createCell(2);
        cell.setCellValue("订单号");
        cell = row.createCell(2);
        cell.setCellValue("地址");
        cell = row.createCell(3);
        cell.setCellValue("消费类型");
        cell = row.createCell(4);
        cell.setCellValue("支付金额 ");
        cell = row.createCell(5);
        cell.setCellValue("支付时间 ");
        cell = row.createCell(6);
        cell.setCellValue("创建时间 ");
        cell = row.createCell(7);
        cell.setCellValue("更新时间 ");


        XSSFRow rows;
        XSSFCell cells;
        for (int i = 0; i < list.size(); i++) {
            ConsumeRecord model = list.get(i);
            // 第三步：在这个sheet页里创建一行
            rows = sheet.createRow(i + 1);
            // 第四步：在该行创建一个单元格
            // 第五步：在该单元格里设置值
            cells = rows.createCell(0);
            //查找用户
            User user = userService.findById(model.getUserId());
            if (user != null) {
                cells.setCellValue(user.getNickname());
            }
            cells = rows.createCell(1);
            cells.setCellValue(model.getOrderNumber());
            cells = rows.createCell(2);
            cells.setCellValue(model.getAddress());
            cells = rows.createCell(3);
            if (model.getType() == 1) {
                cells.setCellValue("视频消费");
            } else if (model.getType() == 2) {
                cells.setCellValue("文库消费");
            } else if (model.getType() == 3) {
                cells.setCellValue("考试消费");
            } else {
                cells.setCellValue("会员充值");
            }
            cells = rows.createCell(4);
            cells.setCellValue(model.getPayMoney() + "");
            cells = rows.createCell(5);
            cells.setCellValue(simpleDateFormat.format(model.getPayTime()));

            cells = rows.createCell(6);
            cells.setCellValue(simpleDateFormat.format(list.get(i).getCreateTime()));
            cells = rows.createCell(7);
            cells.setCellValue(simpleDateFormat.format(list.get(i).getUpdateTime()));
        }

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            String fileName = "消费记录信息";
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
     * 经销商的收益流水
     */
    @GetMapping("dealerRevenueStreams")
    public Map dealerRevenueStreams(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer limit,
                                    @RequestParam(value = "superiorId") Integer superiorId,
                                    @RequestParam(value = "year", required = false) Integer year,
                                    @RequestParam(value = "month", required = false) Integer month,
                                    @RequestParam(value = "day", required = false) Integer day,
                                    @RequestParam(value = "type", required = false) Integer type) {

        Map<String, Object> revenueStreamsMap = consumeRecordService.dealerRevenueStreams(page, limit, superiorId, year, month, day, type);

        return revenueStreamsMap;
    }


}

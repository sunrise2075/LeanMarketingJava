package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.LessonCollage;
import com.yipage.leanmarketing.model.LessonCollageRecord;
import com.yipage.leanmarketing.service.LessonCollageRecordService;
import com.yipage.leanmarketing.service.LessonCollageService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2019/04/15.
 */
@RestController
@RequestMapping("/lesson/collage/record")
public class LessonCollageRecordController {
    @Resource
    private LessonCollageRecordService lessonCollageRecordService;

    @Resource
    private LessonCollageService lessonCollageService;

    @PostMapping("add")
    public Result add(@RequestBody LessonCollageRecord lessonCollageRecord) {
        lessonCollageRecordService.save(lessonCollageRecord);
        return ResultGenerator.genSuccessResult();
    }

//    @GetMapping("deletes/{ids}")
//    public Result deletes(@PathVariable String ids) {
//        lessonCollageRecordService.deleteByIds(ids);
//        return ResultGenerator.genSuccessResult();
//    }

    @PostMapping("update")
    public Result update(LessonCollageRecord lessonCollageRecord) {
        lessonCollageRecordService.update(lessonCollageRecord);

        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("delete/{ids}")
    public Result delete(@PathVariable String ids) {

        lessonCollageRecordService.falseDelete(ids);

        return ResultGenerator.genSuccessResult();
    }


    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        LessonCollageRecord lessonCollageRecord = lessonCollageRecordService.findById(id);
        return ResultGenerator.genSuccessResult(lessonCollageRecord);
    }


    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer limit,
                    LessonCollageRecord record) {
        PageHelper.startPage(page, limit);

        Condition condition = new Condition(LessonCollageRecord.class);
        Example.Criteria criteria = condition.createCriteria();
        condition.setOrderByClause("create_time desc");

        criteria.andCondition("is_delete = 0");
        Integer userId = record.getUserId();
        if (userId != null) {
            criteria.andCondition("user_id = " + userId);
        }
        Integer isLeader = record.getIsLeader();
        if (isLeader != null) {
            criteria.andCondition("is_leader = " + isLeader);
        }
        Integer leaderId = record.getLeaderId();
        if (leaderId != null) {
            criteria.andCondition("leader_id = " + leaderId);
        }
        Integer status = record.getStatus();
        if (status != null) {
            criteria.andCondition("status = " + status);
        }
        Integer lessonId = record.getLessonId();
        if (lessonId != null) {
            criteria.andCondition("lesson_id = " + lessonId);
        }
        String lessonName = record.getLessonName();
        if (StringUtils.isNotEmpty(lessonName)) {
            criteria.andCondition("lesson_name like '%" + lessonName + "%'");
        }
        String recordNum = record.getRecordNum();
        if (StringUtils.isNotEmpty(recordNum)) {
            criteria.andCondition("record_num = '" + recordNum + "'");
        }
        String userName = record.getUserName();
        if (StringUtils.isNotEmpty(userName)) {
            criteria.andCondition("user_name like '%" + userName + "%'");
        }
        String address = record.getAddress();
        if (StringUtils.isNotEmpty(address)) {
            criteria.andCondition("address like '%" + address + "%'");
        }
        List<LessonCollageRecord> list = lessonCollageRecordService.findByCondition(condition);
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }


    /**
     * 获取参团记录信息
     */
    @GetMapping("getRecordInfo")
    public Result getRecordInfo(String recordNum) {

        Map<String, Object> map = new HashMap<>();

        LessonCollageRecord queryRecord = new LessonCollageRecord();
        queryRecord.setRecordNum(recordNum);
        //查找正在拼团的记录

        List<LessonCollageRecord> list = lessonCollageRecordService.select(queryRecord);

        map.put("lessonCollageRecordList", list);

        if (CollectionUtils.isEmpty(list)) {
            return ResultGenerator.genFailResult("服务器出错");
        }
        LessonCollage lessonCollage = lessonCollageService.findById(list.get(0).getLessonId());
        //查找成团总数
        LessonCollageRecord queryRecord2 = new LessonCollageRecord();
        queryRecord2.setLessonId(lessonCollage.getId());
        Integer count = lessonCollageRecordService.selectCount(queryRecord2);
        lessonCollage.setSum(count);
        map.put("lessonCollage", lessonCollage);

        return ResultGenerator.genSuccessResult(map);
    }

    /**
     * 导出拼课记录
     */
    @GetMapping("export")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, LessonCollageRecord record) throws Exception {

        //第一步查找数据
        Condition condition = new Condition(LessonCollageRecord.class);
        Example.Criteria criteria = condition.createCriteria();
        condition.setOrderByClause("create_time desc");

        criteria.andCondition("is_delete = 0");
        String lessonName = record.getLessonName();
        if (StringUtils.isNotEmpty(lessonName)) {
            criteria.andCondition("lesson_name like '%" + lessonName + "%'");
        }
        String userName = record.getUserName();
        if (StringUtils.isNotEmpty(userName)) {
            criteria.andCondition("user_name like '%" + userName + "%'");
        }
        String address = record.getAddress();
        if (StringUtils.isNotEmpty(address)) {
            criteria.andCondition("address like '%" + address + "%'");
        }
        List<LessonCollageRecord> list = lessonCollageRecordService.findByCondition(condition);
        //第二步数据转成excel

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 第一步：定义一个新的工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步：创建一个Sheet页
        XSSFSheet sheet = wb.createSheet("拼课记录");

        sheet.setDefaultRowHeight((short) (2 * 256));//设置行高

        for (int i = 0; i < 8; i++) {
            sheet.setColumnWidth(i, 4000);//设置列宽
        }

        XSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);

        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("课程名称");
        cell = row.createCell(1);
        cell.setCellValue("购买人");
        cell = row.createCell(2);
        cell.setCellValue("支付金额");
        cell = row.createCell(3);
        cell.setCellValue("开课时间");
        cell = row.createCell(4);
        cell.setCellValue("开课地址");
        cell = row.createCell(5);
        cell.setCellValue("拼团状态");
        cell = row.createCell(6);
        cell.setCellValue("创建时间");
        cell = row.createCell(7);
        cell.setCellValue("更新时间");

        XSSFRow rows;
        XSSFCell cells;
        for (int i = 0; i < list.size(); i++) {

            LessonCollageRecord model = list.get(i);
            // 第三步：在这个sheet页里创建一行
            rows = sheet.createRow(i + 1);
            // 第四步：在该行创建一个单元格
            cells = rows.createCell(0);
            // 第五步：在该单元格里设置值
            cells.setCellValue(model.getLessonName());
            cells = rows.createCell(1);
            cells.setCellValue(model.getUserName());
            cells = rows.createCell(2);
            cells.setCellValue(model.getPrice() + "");

            cells = rows.createCell(3);
            cells.setCellValue(simpleDateFormat.format(model.getBeginTime()));
            cells = rows.createCell(4);
            cells.setCellValue(model.getAddress());
            cells = rows.createCell(5);
            int status = model.getStatus();
            if (status == 1) {
                cells.setCellValue("拼团中");
            } else if (status == 2) {
                cells.setCellValue("拼团成功");
            } else {
                cells.setCellValue("拼团失败");
            }
            cells = rows.createCell(6);
            cells.setCellValue(simpleDateFormat.format(model.getCreateTime()));
            cells = rows.createCell(7);
            cells.setCellValue(simpleDateFormat.format(model.getUpdateTime()));
        }

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            String fileName = "拼团记录信息";
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

    @GetMapping("test")
    public void test() {

        lessonCollageRecordService.OverdueTimeLessonCollage();

    }

    @PostMapping("updateAndSendMessage")
    public void updateAndSendMessage(LessonCollageRecord lessonCollageRecord) {

        lessonCollageRecordService.updateAndSendMessage(lessonCollageRecord);

    }

}

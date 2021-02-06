package com.yipage.leanmarketing.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.LessonCollage;
import com.yipage.leanmarketing.model.LessonCollageRecord;
import com.yipage.leanmarketing.model.LessonCollageRule;
import com.yipage.leanmarketing.service.LessonCollageRecordService;
import com.yipage.leanmarketing.service.LessonCollageRuleService;
import com.yipage.leanmarketing.service.LessonCollageService;
import com.yipage.leanmarketing.utils.MapUtil;
import com.yipage.leanmarketing.utils.WeiXinUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2019/04/15.
 */
@RestController
@RequestMapping("/lesson/collage")
public class LessonCollageController {
    @Resource
    private LessonCollageService lessonCollageService;
    @Resource
    private LessonCollageRecordService lessonCollageRecordService;
    @Resource
    private LessonCollageRuleService lessonCollageRuleService;


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
    public Result add(LessonCollage lessonCollage) {
        lessonCollageService.save(lessonCollage);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        lessonCollageService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(LessonCollage lessonCollage) {
        lessonCollageService.update(lessonCollage);
        return ResultGenerator.genSuccessResult();
    }


    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        LessonCollage lessonCollage = lessonCollageService.findById(id);
        return ResultGenerator.genSuccessResult(lessonCollage);
    }

    @GetMapping("detail/{id}")
    public Map detail2(@PathVariable Integer id) {

        Map<String, Object> map = new HashMap<>();

        LessonCollage lessonCollage = lessonCollageService.findById(id);
        //查找成团总数
        LessonCollageRecord queryRecord = new LessonCollageRecord();
        queryRecord.setLessonId(lessonCollage.getId());
        Integer count = lessonCollageRecordService.selectCount(queryRecord);
        lessonCollage.setSum(count);

        map.put("lessonCollage", lessonCollage);

        //参团记录
        queryRecord.setIsLeader(LessonCollageRecord.IS_LEADER);
        queryRecord.setStatus(1);
        List<LessonCollageRecord> lessonCollageRecordList = lessonCollageRecordService.select(queryRecord);

        map.put("lessonCollageRecordList", lessonCollageRecordList);

        //规则
        LessonCollageRule lessonCollageRule = lessonCollageRuleService.findById(1);
        map.put("lessonCollageRule", lessonCollageRule);
        return map;
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer limit,
                    LessonCollage lessonCollage) {

        PageHelper.startPage(page, limit);

        Condition condition = new Condition(LessonCollage.class);
        Example.Criteria criteria = condition.createCriteria();
        condition.setOrderByClause("create_time desc");

        String name = lessonCollage.getName();
        if (StringUtils.isNotEmpty(name)) {
            criteria.andCondition("name like  '%" + name + "%'");
        }
        String province = lessonCollage.getProvince();
        if (StringUtils.isNotEmpty(province)) {
            criteria.andCondition("province ='" + province + "'");
        }
        String city = lessonCollage.getCity();
        if (StringUtils.isNotEmpty(city)) {
            criteria.andCondition("city ='" + city + "'");
        }
        String area = lessonCollage.getArea();
        if (StringUtils.isNotEmpty(area)) {
            criteria.andCondition("area ='" + area + "'");
        }

        List<LessonCollage> list = lessonCollageService.findByCondition(condition);
        if (!CollectionUtils.isEmpty(list)) {
            for (LessonCollage collage : list) {

                LessonCollageRecord queryRecord = new LessonCollageRecord();
                queryRecord.setLessonId(collage.getId());
                Integer count = lessonCollageRecordService.selectCount(queryRecord);
                collage.setSum(count);
            }
        }
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 获取二维码
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/smallProgramCode")
    public Result smallProgramCode(HttpServletRequest request, HttpServletResponse response,
                                   Integer lessonId,
                                   String page,
                                   Integer leaderId,
                                   String recordNum,
                                   @RequestParam(defaultValue = "2") Integer type,
                                   @RequestParam(defaultValue = "9") Integer route) throws IOException {

        //设置响应类型
        response.setContentType("image/png");
        String accessToken = WeiXinUtil.getAccessToken();
        //组装url
        String url = LeanMarkentingConstant.getminiqrQrUrl + accessToken;
        //组装参数
        Map<String, Object> paraMap = new HashMap();
        //二维码携带参数 不超过32位
        paraMap.put("scene", lessonId + "_" + leaderId + "_" + recordNum + "_" + type + "_" + route);
        //二维码跳转页面
        paraMap.put("page", page);
        paraMap.put("width", "250");

        //执行post 获取数据流
        String result = WeiXinUtil.doImgPost(request, url, paraMap);
        //输出图片到页面
        return ResultGenerator.genSuccessResult(result);
    }


}

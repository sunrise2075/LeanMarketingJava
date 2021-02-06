package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.annotation.NeedLoginAnnotation;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.model.VideoEvaluate;
import com.yipage.leanmarketing.service.UserService;
import com.yipage.leanmarketing.service.VideoEvaluateService;
import com.yipage.leanmarketing.utils.EmojiFilterUtil;
import com.yipage.leanmarketing.utils.MapUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by CodeGenerator on 2018/12/12.
 */
@RestController
@RequestMapping("/video/evaluate")
public class VideoEvaluateController {
    @Resource
    private VideoEvaluateService videoEvaluateService;
    @Resource
    private UserService userService;

    @PostMapping("add")
    @NeedLoginAnnotation
    public Result add(@RequestBody VideoEvaluate videoEvaluate) {
        User user = userService.findBy("wxid", videoEvaluate.getOpenid());
        if (user != null) {
            //处理特殊字符窜
            videoEvaluate.setDescription(EmojiFilterUtil.filterEmoji(videoEvaluate.getDescription()));
            videoEvaluate.setUserId(user.getId());
            videoEvaluate.setUserName(user.getNickname());
            videoEvaluate.setUserHeadPortrait(user.getHeadPortrait());
        }
        videoEvaluate.setCreateTime(new Date());
        videoEvaluate.setUpdateTime(new Date());
        videoEvaluateService.save(videoEvaluate);

        return ResultGenerator.genSuccessResult();
    }


    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        videoEvaluateService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(VideoEvaluate videoEvaluate) {
        videoEvaluateService.update(videoEvaluate);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        VideoEvaluate videoEvaluate = videoEvaluateService.findById(id);
        return ResultGenerator.genSuccessResult(videoEvaluate);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "videoId", required = false) Integer videoId) {
        PageHelper.startPage(page, limit, "create_time desc");
        VideoEvaluate evaluate = new VideoEvaluate();
        if (videoId != null) {
            evaluate.setVideoId(videoId);
        }
        List<VideoEvaluate> list = videoEvaluateService.select(evaluate);
        PageInfo pageInfo = new PageInfo(list);
        List<Map<String, Object>> list2 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (VideoEvaluate videoEvaluate : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("createTime", videoEvaluate.getCreateTime());
                map.put("description", videoEvaluate.getDescription());
                User user = userService.findBy("wxid", videoEvaluate.getOpenid());
                if (user != null) {
                    map.put("userName", user.getNickname());
                    map.put("userHeadPortrait", user.getHeadPortrait());
                }
                list2.add(map);
            }
        }
        pageInfo.setList(list2);
        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 按条件查询视频评价
     *
     * @return
     */
    @GetMapping("select")
    public Result select(@RequestParam(value = "videoId", required = false) Integer videoId,
                         @RequestParam(value = "userId", required = false) Integer userId) {

        VideoEvaluate videoEvaluate = new VideoEvaluate();
        if (videoId != null) {
            videoEvaluate.setVideoId(videoId);
        }
        if (userId != null) {
            videoEvaluate.setUserId(userId);
        }
        List<VideoEvaluate> list = videoEvaluateService.select(videoEvaluate);
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("selectOrderByCreateTime")
    public Result selectOrderByCreateTime(@RequestParam(value = "videoId", required = false) Integer videoId,
                                          @RequestParam(value = "userId", required = false) Integer userId) {

        VideoEvaluate videoEvaluate = new VideoEvaluate();
        if (videoId != null) {
            videoEvaluate.setVideoId(videoId);
        }
        if (userId != null) {
            videoEvaluate.setUserId(userId);
        }
        return ResultGenerator.genSuccessResult(videoEvaluateService.selectOrderByCreateTime(videoEvaluate));
    }
}

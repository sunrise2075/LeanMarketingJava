package com.yipage.leanmarketing.controller;

import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.Video;
import com.yipage.leanmarketing.model.VideoCategory;
import com.yipage.leanmarketing.model.VideoChapter;
import com.yipage.leanmarketing.model.VideoContents;
import com.yipage.leanmarketing.service.VideoCategoryService;
import com.yipage.leanmarketing.service.VideoChapterService;
import com.yipage.leanmarketing.service.VideoContentsService;
import com.yipage.leanmarketing.service.VideoService;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by CodeGenerator on 2018/12/12.
 */
@RestController
@RequestMapping("/video/contents")
public class VideoContentsController {
    @Resource
    private VideoContentsService videoContentsService;

    @Resource
    private VideoService videoService;

    @Resource
    private VideoChapterService videoChapterService;

    @Resource
    private VideoCategoryService videoCategoryService;

    @PostMapping("add")
    public Result add(VideoContents videoContents) {
        videoContents.setCreateTime(new Date());
        videoContents.setUpdateTime(new Date());
        videoContentsService.save(videoContents);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        videoContentsService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(VideoContents videoContents) {
        videoContentsService.update(videoContents);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Map<String, Object> map = new HashMap<>();
        VideoContents videoContents = videoContentsService.findById(id);
        if (videoContents != null) {
            map.put("videoContents", videoContents);
            VideoChapter chapter = videoChapterService.findById(videoContents.getChapterId());
            if (chapter != null) {
                map.put("chapter", chapter);
                Video video = videoService.findById(chapter.getVideoId());
                if (video != null) {
                    map.put("video", video);
                    VideoCategory videoCategory = videoCategoryService.findById(video.getCategoryId());
                    if (videoCategory != null) {
                        map.put("videoCategory", videoCategory);
                    }
                }
            }
        }
        return ResultGenerator.genSuccessResult(map);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "videoChapterName", required = false) String videoChapterName,
                    @RequestParam(value = "videoName", required = false) String videoName) {

        Map<String, Object> resMap = videoContentsService.listPagerToVideoContents(page, limit, videoChapterName, videoName);
//        PageHelper.startPage(page, limit);
//        List<VideoContents> list = videoContentsService.findAll();
//        List<Map<String,Object>> list2 = new ArrayList<>();
//        if(!CollectionUtils.isEmpty(list)){
//            for (VideoContents videoContents : list) {
//                Map<String,Object> map = new HashMap<>();
//                map.put("createTime",videoContents.getCreateTime());
//                map.put("updateTime",videoContents.getUpdateTime());
//                map.put("id",videoContents.getId());
//                map.put("name",videoContents.getName());
//                map.put("url",videoContents.getUrl());
//                map.put("chapterId",videoContents.getChapterId());
//                VideoChapter videoChapter = videoChapterService.findById(videoContents.getChapterId());
//                if(videoChapter!=null){
//                    map.put("videoChapterName",videoChapter.getName());
//                    Video video = videoService.findById(videoChapter.getVideoId());
//                    if(video!=null){
//                        map.put("videoTitle",video.getTitle());
//                    }
//                }
//                list2.add(map);
//            }
//        }
//        PageInfo pageInfo = new PageInfo(list2);
        return resMap;
    }

    @GetMapping("select")
    public Result select(@RequestParam(value = "videoId") Integer videoId) {

        Condition condition = new Condition(VideoChapter.class);
        Example.Criteria criteria = condition.createCriteria();
        //排序
        condition.setOrderByClause("sort");
        criteria.andCondition("video_id =" + videoId);

        List<VideoChapter> videoChapterList = videoChapterService.findByCondition(condition);
        List<Map<String, Object>> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(videoChapterList)) {
            for (VideoChapter videoChapter : videoChapterList) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", videoChapter.getName());
                map.put("id", videoChapter.getId());
                map.put("sort", videoChapter.getSort());


                Condition condition2 = new Condition(VideoContents.class);
                Example.Criteria criteria2 = condition2.createCriteria();
                //排序
                condition2.setOrderByClause("sort");
                criteria2.andCondition("chapter_id =" + videoChapter.getId());

                List<VideoContents> videoContentsList = videoContentsService.findByCondition(condition2);
                if (!CollectionUtils.isEmpty(videoContentsList)) {
                    map.put("videoContentsList", videoContentsList);
                }
                list.add(map);
            }
        }
        return ResultGenerator.genSuccessResult(list);
    }
}

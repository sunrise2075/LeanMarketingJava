package com.yipage.leanmarketing.controller;

import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.Video;
import com.yipage.leanmarketing.model.VideoCategory;
import com.yipage.leanmarketing.model.VideoChapter;
import com.yipage.leanmarketing.service.VideoCategoryService;
import com.yipage.leanmarketing.service.VideoChapterService;
import com.yipage.leanmarketing.service.VideoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/26.
 */
@RestController
@RequestMapping("/video/chapter")
public class VideoChapterController {
    @Resource
    private VideoChapterService videoChapterService;
    @Resource
    private VideoService videoService;

    @Resource
    private VideoCategoryService videoCategoryService;

    @PostMapping("add")
    public Result add(VideoChapter videoChapter) {
        videoChapter.setCreateTime(new Date());
        videoChapter.setUpdateTime(new Date());
        videoChapterService.save(videoChapter);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        videoChapterService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(VideoChapter videoChapter) {
        videoChapter.setUpdateTime(new Date());
        videoChapterService.update(videoChapter);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Map<String, Object> map = new HashMap<>();
        VideoChapter videoChapter = videoChapterService.findById(id);
        map.put("videoChapter", videoChapter);
        if (videoChapter != null) {
            Video video = videoService.findById(videoChapter.getVideoId());
            if (video != null) {
                map.put("video", video);
                VideoCategory videoCategory = videoCategoryService.findById(video.getCategoryId());
                map.put("videoCategory", videoCategory);
            }
        }
        return ResultGenerator.genSuccessResult(map);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "videoChapterName", required = false) String videoChapterName,
                    @RequestParam(value = "videoName", required = false) String videoName,
                    @RequestParam(value = "videoId", required = false) Integer videoId) {

//        PageHelper.startPage(page, limit);
//        List<VideoChapter> list = videoChapterService.findAll();
        Map<String, Object> resMap = videoChapterService.listPagerToVideoChapter(page, limit, videoChapterName, videoName, videoId);

//        List<Map<String,Object>> list2 = new ArrayList<>();
//        if(!CollectionUtils.isEmpty(list)){
//
//            for (VideoChapter videoChapter : list) {
//                Map<String,Object> map = new HashMap<>();
//                map.put("id",videoChapter.getId());
//                map.put("createTime",videoChapter.getCreateTime());
//                map.put("updateTime",videoChapter.getUpdateTime());
//                map.put("name",videoChapter.getName());
//                map.put("isHide",videoChapter.getIsHide());
//                Video video = videoService.findById(videoChapter.getVideoId());
//                if(video!= null){
//                    map.put("videoName",video.getTitle());
//                }
//                list2.add(map);
//            }
//
//        }
//        PageInfo pageInfo = new PageInfo(list2);
        return resMap;
    }

    /**
     * 按条件查找视频章节
     */
    @GetMapping("select")
    public Result list(@RequestParam(value = "videoId", required = false) Integer videoId) {

        VideoChapter chapter = new VideoChapter();
        if (videoId != null) {
            chapter.setVideoId(videoId);
        }
        List<VideoChapter> list = videoChapterService.select(chapter);

        return ResultGenerator.genSuccessResult(list);
    }
}

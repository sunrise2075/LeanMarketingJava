package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.annotation.NeedLoginAnnotation;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.model.UserCollectionVideo;
import com.yipage.leanmarketing.model.Video;
import com.yipage.leanmarketing.service.UserCollectionVideoService;
import com.yipage.leanmarketing.service.UserService;
import com.yipage.leanmarketing.service.VideoService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by CodeGenerator on 2018/12/10.
 */
@RestController
@NeedLoginAnnotation
@RequestMapping("/user/collection/video")
public class UserCollectionVideoController {
    @Resource
    private UserCollectionVideoService userCollectionVideoService;
    @Resource
    private VideoService videoService;
    @Resource
    private UserService userService;

    @PostMapping("add")
    public Result add(@RequestBody UserCollectionVideo userCollectionVideo) {

        userCollectionVideo.setCreateTime(new Date());
        userCollectionVideo.setUpdateTime(new Date());
        userCollectionVideoService.save(userCollectionVideo);
        return ResultGenerator.genSuccessResult(userCollectionVideo);
    }

    @DeleteMapping("/{ids}")
    public Result deletes(@PathVariable String ids) {
        userCollectionVideoService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 根据视频id和用户的openid删除收藏的视频
     *
     * @param videoId
     * @param openid
     * @return
     */
    @DeleteMapping("/{videoId}/{openid}")
    public Result delete(@PathVariable String openid,
                         @PathVariable Integer videoId) {
        //找到对应的收藏视频
        UserCollectionVideo userCollectionVideo = new UserCollectionVideo();
        userCollectionVideo.setOpenid(openid);
        userCollectionVideo.setVideoId(videoId);
        List<UserCollectionVideo> userCollectionVideos = userCollectionVideoService.select(userCollectionVideo);
        if (!CollectionUtils.isEmpty(userCollectionVideos)) {
            userCollectionVideoService.deleteByIds(userCollectionVideos.get(0).getId() + "");
        }
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes2(@PathVariable String ids) {
        userCollectionVideoService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

//    @GetMapping("/delete2/{videoId}/{openid}")
//    public Result delete2(@PathVariable String openid,
//                         @PathVariable Integer videoId){
//        //找到对应的收藏视频
//        UserCollectionVideo userCollectionVideo = new UserCollectionVideo();
//        userCollectionVideo.setOpenid(openid);
//        userCollectionVideo.setVideoId(videoId);
//        List<UserCollectionVideo> userCollectionVideos = userCollectionVideoService.select(userCollectionVideo);
//        if(!CollectionUtils.isEmpty(userCollectionVideos)){
//            userCollectionVideoService.deleteByIds(userCollectionVideos.get(0).getId()+"");
//        }
//        return ResultGenerator.genSuccessResult();
//    }

    @PutMapping
    public Result update(@RequestBody UserCollectionVideo userCollectionVideo) {
        userCollectionVideoService.update(userCollectionVideo);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        UserCollectionVideo userCollectionVideo = userCollectionVideoService.findById(id);
        return ResultGenerator.genSuccessResult(userCollectionVideo);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "openid", required = false) String openid) {

        //条件
        UserCollectionVideo userCollectionVideo = new UserCollectionVideo();
        User user = userService.findBy("wxid", openid);
        if (user != null) {
            userCollectionVideo.setOpenid(openid);
        }
        //排序
        String orderBy = "update_time desc";

        //分页
        PageHelper.startPage(page, limit, orderBy);

        //查询
        List<UserCollectionVideo> list = userCollectionVideoService.select(userCollectionVideo);

        //数据封装
        List<Map<String, Object>> resList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {

            //查出视频的信息
            for (UserCollectionVideo collectionVideo : list) {
                Map<String, Object> resMap = new HashMap<>();
                resMap.put("collectionVideoId", collectionVideo.getId());

                Video video = videoService.findById(collectionVideo.getVideoId());
                if (video != null) {
                    resMap.put("video", video);
                }
                resList.add(resMap);

                //查一下是否收藏了该视频
                UserCollectionVideo collectionVideo1 = new UserCollectionVideo();
                collectionVideo1.setOpenid(openid);
                collectionVideo1.setVideoId(video.getId());

            }
        }
        //获得结果集
        PageInfo pageInfo = new PageInfo(resList);
        return MapUtil.PageResult(pageInfo);
    }
}

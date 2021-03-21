package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.*;
import com.yipage.leanmarketing.service.*;
import com.yipage.leanmarketing.utils.MapUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/10.
 */
@RestController
@RequestMapping("/video")
public class VideoController {
    @Resource
    private VideoService videoService;
    @Resource
    private VideoChapterService videoChapterService;
    @Resource
    private VideoCategoryService videoCategoryService;
    @Resource
    private VideoEvaluateService videoEvaluateService;
    @Resource
    private VideoOrderService videoOrderService;
    @Resource
    private VideoContentsService videoContentsService;
    @Resource
    private UserService userService;
    @Resource
    private UserCollectionVideoService userCollectionVideoService;

    @Resource
    private WatchRecordService watchRecordService;

    @Resource
    private BlackService blackService;

    @Resource
    private BannerService bannerService;


    @PostMapping("add")
    public Result add(Video video) {
        videoService.save(video);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        videoService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("update")
    public Result update(Video video) {
        videoService.update(video);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam(value = "id") Integer id,
                         @RequestParam(value = "openid", required = false) String openid) throws Exception {
        Map<String, Object> resMap = new HashMap<>();
        Video video = videoService.findById(id);
        User user = null;
        if (openid != null) {
            user = userService.findBy("wxid", openid);
        } else {
            //查商品分类详情
            VideoCategory videoCategory = videoCategoryService.findById(video.getCategoryId());
            if (videoCategory != null) {
                resMap.put("videoCategory", videoCategory);
            }
        }
        if (video != null) {
            resMap.put("video", video);
            if (video.getIsFree() == Library.IS_FREE) {
                resMap.put("isBuy", 1);
            } else {
                resMap.put("isBuy", 2);
            }
            if (user != null) {

                //查找观看记录
                WatchRecord watchRecord = new WatchRecord();
                watchRecord.setVideoId(video.getId());
                Integer watchNum = watchRecordService.selectCount(watchRecord);
                resMap.put("watchNum", watchNum);

                if (video.getIsFree() == Video.IS_FREE) {
                    resMap.put("isBuy", 1);

                } else {
                    int menberLevel = user.getMemberLevel();
                    if (menberLevel == 1) {
                        //查找是否已经购买该视频
                        VideoOrder order = new VideoOrder();
                        order.setOpenid(user.getWxid());
                        order.setPayState(VideoOrder.ISPAY);
                        order.setVideoId(video.getId());
                        List<VideoOrder> orderList = videoOrderService.select(order);
                        if (!CollectionUtils.isEmpty(orderList)) {
                            resMap.put("isBuy", 1);
                        } else {
                            resMap.put("isBuy", 2);
                        }
                    } else {
                        if (menberLevel == 10) {  //添加了一个经销商,前端页面只有0-9
                            menberLevel = 1;
                        }
                        if (video.getCodes().contains(menberLevel + "")) {
                            resMap.put("isFree", Library.IS_FREE);
                            resMap.put("isBuy", 1);
                        } else {
                            //查找是否已经购买该视频
                            VideoOrder order = new VideoOrder();
                            order.setOpenid(user.getWxid());
                            order.setPayState(VideoOrder.ISPAY);
                            order.setVideoId(video.getId());
                            List<VideoOrder> orderList = videoOrderService.select(order);
                            if (!CollectionUtils.isEmpty(orderList)) {
                                resMap.put("isBuy", 1);
                            } else {
                                resMap.put("isBuy", 2);
                            }
                        }

                    }

                }
            }
            //查找是否已经收藏该视频
            UserCollectionVideo userCollectionVideo = new UserCollectionVideo();
            userCollectionVideo.setOpenid(openid);
            userCollectionVideo.setVideoId(id);
            List<UserCollectionVideo> collectionList = userCollectionVideoService.select(userCollectionVideo);
            if (!CollectionUtils.isEmpty(collectionList)) {
                resMap.put("isCollection", 1);
                resMap.put("userCollectionVideo", collectionList.get(0));
            } else {
                resMap.put("isCollection", 2);
            }
            //找到视频下面的第一个视频目录下面的第一个视频内容
            PageHelper.startPage(1, 1, "sort");
            VideoChapter videoChapter = videoChapterService.findBy("videoId", video.getId());

            if (videoChapter != null) {
                PageHelper.startPage(1, 1, "sort");
                VideoContents videoContents = videoContentsService.findBy("chapterId", videoChapter.getId());
                if (videoContents != null) {
                    resMap.put("videoContents", videoContents);
                }
            }

            List<VideoContents> videoContentsList = new ArrayList<>();
            //找到视频下面的所有视频内容
            Condition condition = new Condition(VideoChapter.class);
            Example.Criteria criteria = condition.createCriteria();
            condition.setOrderByClause("sort");
            criteria.andCondition("video_id =" + id);
            List<VideoChapter> videoChapterList = videoChapterService.findByCondition(condition);
            if (!CollectionUtils.isEmpty(videoChapterList)) {
                for (VideoChapter chapter : videoChapterList) {
                    Condition condition2 = new Condition(VideoContents.class);
                    Example.Criteria criteria2 = condition2.createCriteria();
                    condition2.setOrderByClause("sort");
                    criteria2.andCondition("chapter_id =" + chapter.getId());

                    List<VideoContents> list = videoContentsService.findByCondition(condition2);
                    for (VideoContents contents : list) {
                        videoContentsList.add(contents);
                    }
                }
            }
            resMap.put("videoContentsList", videoContentsList);

            VideoEvaluate videoEvaluate = new VideoEvaluate();
            videoEvaluate.setVideoId(video.getId());
            Integer total = videoEvaluateService.selectCount(videoEvaluate);
            resMap.put("total", total);

        }
        //找出最新的两个评价
        String orderBy = "create_time DESC";
        PageHelper.startPage(1, 2, orderBy);
        VideoEvaluate model = new VideoEvaluate();
        model.setVideoId(id);
        List<VideoEvaluate> list = videoEvaluateService.select(model);
        List<Map<String, Object>> list2 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (VideoEvaluate videoEvaluate : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("createTime", videoEvaluate.getCreateTime());
                //map.put("starNum",videoEvaluate.getStarNum());
                map.put("description", videoEvaluate.getDescription());
                User user1 = userService.findBy("wxid", videoEvaluate.getOpenid());
                if (user1 != null) {
                    map.put("userName", user1.getNickname());
                    map.put("userHeadPortrait", user1.getHeadPortrait());
                }
                list2.add(map);
            }
        }
        resMap.put("videoEvaluateList", list2);
        return ResultGenerator.genSuccessResult(resMap);
    }

    /**
     * 分页条件查询视频
     *
     * @param page
     * @param limit
     * @param categoryId
     * @param isFree
     * @param authorName
     * @return
     */
    @GetMapping("list")
    public Map list(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                    @RequestParam(value = "categoryId", required = false) Integer categoryId,
                    @RequestParam(value = "isFree", required = false) Integer isFree,
                    @RequestParam(value = "authorName", required = false) String authorName,
                    @RequestParam(value = "authorName2", required = false) String authorName2,
                    @RequestParam(value = "isHide", required = false) Integer isHide,
                    @RequestParam(value = "openid", required = false) String openid) {


        Condition condition = new Condition(Video.class);
        Example.Criteria criteria = condition.createCriteria();
        String orderBy = "create_time DESC";
        PageHelper.startPage(page, limit, orderBy);
        if (isHide != null) {
            criteria.andCondition("is_hide = " + isHide);
        }
        if (categoryId != null) {
            criteria.andCondition("category_id = " + categoryId);
        }
        if (isFree != null) {
            criteria.andCondition("is_free = " + isFree);
        }
        if (StringUtils.isNotEmpty(authorName)) {
            criteria.andCondition("author_name = '" + authorName + "'");
        }
        if (StringUtils.isNotEmpty(authorName2)) {
            criteria.andCondition("author_name like  '%" + authorName2 + "%'");
        }

        List<Video> list = videoService.findByCondition(condition);
        PageInfo pageInfo = new PageInfo(list);
        //查用户身份
        User user = null;
        if (openid != null) {
            user = userService.findBy("wxid", openid);
        }
        //封装数据
        List<Map<String, Object>> list2 = packageVideo(list, user);
        pageInfo.setList(list2);
        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 封装视频
     *
     * @param list
     * @param user
     * @return
     */
    private List<Map<String, Object>> packageVideo(List<Video> list, User user) {
        List<Map<String, Object>> list2 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Video video : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", video.getId());
                map.put("authorName", video.getAuthorName());
                map.put("authorImg", video.getAuthorImg());
                map.put("description", video.getDescription());
                map.put("salesNum", video.getSalesNum());
                map.put("authorIntroduce", video.getAuthorIntroduce());
                map.put("categoryId", video.getCategoryId());
                map.put("isShare", video.getIsShare());
                map.put("labels", video.getLabels());
                map.put("videos", video.getVideos());
                map.put("categoryName", video.getCategoryName());
                map.put("isHide", video.getIsHide());
                map.put("createTime", video.getCreateTime());
                map.put("codes", video.getCodes());
                map.put("updateTime", video.getUpdateTime());
                map.put("title", video.getTitle());
                map.put("coverImg", video.getCoverImg());
                map.put("isFree", video.getIsFree());
                if (video.getIsFree() == Library.IS_FREE) {
                    map.put("isBuy", 1);
                } else {
                    map.put("isBuy", 2);
                }
                map.put("price", video.getPrice());
                map.put("content", video.getContent());

                //查找观看记录
                WatchRecord watchRecord = new WatchRecord();
                watchRecord.setVideoId(video.getId());
                Integer watchNum = watchRecordService.selectCount(watchRecord);
                map.put("watchNum", watchNum);

                //评价数量
                VideoEvaluate videoEvaluate = new VideoEvaluate();
                videoEvaluate.setVideoId(video.getId());
                Integer evaluateNum = videoEvaluateService.selectCount(videoEvaluate);
                map.put("evaluateNum", evaluateNum);

                if (user != null) {
                    processUserPurchaseDisplay(user, video, map);
                }
                list2.add(map);
            }
        }
        return list2;
    }

    private void processUserPurchaseDisplay(User user, Video video, Map<String, Object> map) {
        if (video.getIsFree().equals(Video.IS_FREE)) {
            map.put("isBuy", 1);
        } else {
            //查看购买数量
            VideoOrder videoOrder = new VideoOrder();
            videoOrder.setVideoId(video.getId());
            videoOrder.setPayState(VideoOrder.ISPAY);
            Integer buyNum = videoOrderService.selectCount(videoOrder);
            map.put("buyNum", buyNum);

            if (StringUtils.isNotEmpty(video.getCodes())) {
                if (user != null) {
                    int menberLevel = user.getMemberLevel();
                    if (menberLevel == 1) {

                        //查找是否已经购买该视频
                        VideoOrder order = new VideoOrder();
                        order.setOpenid(user.getWxid());
                        order.setPayState(VideoOrder.ISPAY);
                        order.setVideoId(video.getId());
                        List<VideoOrder> orderList = videoOrderService.select(order);
                        if (!CollectionUtils.isEmpty(orderList)) {
                            map.put("isBuy", 1);
                        } else {
                            map.put("isBuy", 2);
                        }

                    } else {

                        if (menberLevel == 10) {  //添加了一个经销商,前端页面只有0-9
                            menberLevel = 1;
                        }
                        if (video.getCodes().contains(menberLevel + "")) {
                            map.put("isBuy", 1);
                        } else {

                            //查找是否已经购买该视频
                            VideoOrder order = new VideoOrder();
                            order.setOpenid(user.getWxid());
                            order.setPayState(VideoOrder.ISPAY);
                            order.setVideoId(video.getId());
                            List<VideoOrder> orderList = videoOrderService.select(order);
                            if (!CollectionUtils.isEmpty(orderList)) {
                                map.put("isBuy", 1);
                            } else {
                                map.put("isBuy", 2);
                            }

                        }

                    }

                }
            }
        }

    }

    /**
     * 视频首页
     */
    @GetMapping("index")
    public Result index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                        @RequestParam(value = "limit", defaultValue = "2") Integer limit,
                        @RequestParam(value = "openid", required = false) String openid,
                        @RequestParam(value = "type", required = false) Integer type) {

        Map<String, Object> resMap = new HashMap<>();
        //查用户身份
        User user = null;
        if (StringUtils.isNotEmpty(openid)) {
            user = userService.findBy("wxid", openid);
            //黑名单
            Black black = blackService.findBy("phone", user.getPhone());
            if (black != null) {
                return ResultGenerator.genBlackFailResult("该手机号存在于黑名单中");
            }
        }
        Condition bannerCondition = new Condition(Banner.class);
        Example.Criteria bannerCriteria = bannerCondition.createCriteria();
        bannerCriteria.andCondition("state =" + Banner.IS_USE);
        bannerCriteria.andCondition("type=" + 2);
        List<Banner> videoBannerList = bannerService.findByCondition(bannerCondition);
        if (!CollectionUtils.isEmpty(videoBannerList)) {

            if (type != null && type == 2) {  //来自pc端

                for (Banner banner : videoBannerList) {
                    banner.setImg(banner.getImgpc());
                }
            }
        }
        resMap.put("videoBannerList", videoBannerList);

        //视频分类
        Condition condition = new Condition(VideoCategory.class);
        Example.Criteria criteria = condition.createCriteria();
        condition.setOrderByClause("sort ,create_time desc");
        criteria.andCondition("is_hide =" + VideoCategory.IS_SHOW);
        List<VideoCategory> videoCategoryList = videoCategoryService.findByCondition(condition);
        resMap.put("videoCategoryList", videoCategoryList);

        //默认查第一个分类下面的视频
        if (categoryId == null) {
            if (!CollectionUtils.isEmpty(videoCategoryList)) {
                categoryId = videoCategoryList.get(0).getId();
            }
        }
        //公开体验视频
        List<Video> freeVideoList = videoService.findVideo(categoryId, Video.IS_FREE, Video.IS_SHOW, limit);
        List<Map<String, Object>> list2 = packageVideo(freeVideoList, null);
        resMap.put("freeVideoList", list2);

        //精选收费视频
        List<Video> chargeVideoList = videoService.findVideo(categoryId, Video.NO_FREE, Video.IS_SHOW, limit);
        resMap.put("chargeVideoList", packageVideo(chargeVideoList, user));

        return ResultGenerator.genSuccessResult(resMap);
    }

    /**
     * 按条件查找视频
     */
    @GetMapping("select")
    public Result list(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                       @RequestParam(value = "isFree", required = false) Integer isFree,
                       @RequestParam(value = "authorName", required = false) String authorName) {

        Video video = new Video();
        if (categoryId != null) {
            video.setCategoryId(categoryId);
        }
        if (isFree != null) {
            video.setIsFree(isFree);
        }
        if (authorName != null) {
            video.setAuthorName(authorName);
        }
        List<Video> list = videoService.select(video);

        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 按销售量前十的视频
     */
    @GetMapping("orderBySalesNum")
    public Result orderBySalesNum() {

        List<Video> list = videoService.orderBySalesNum();

        return ResultGenerator.genSuccessResult(list);
    }


    /**
     * 推荐视频(根据用户的标签记录)
     */
    @GetMapping("recommendedVideos")
    public Map recommendedVideos(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                 @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                 @RequestParam(value = "userId", required = false) Integer userId,
                                 @RequestParam(value = "isFree", required = false) Integer isFree) {

        Map<String, Object> map = videoService.recommendedVideos(page, limit, userId, isFree);
        User user = userService.findById(userId);
        List<Video> videos = (List<Video>) map.get("data");
        List<Map<String, Object>> list = packageVideo(videos, user);
        map.put("data", list);
        return map;
    }


    /**
     * 根据标签查找对应的视频
     */
    @GetMapping("getByLabels")
    public Map getByLabels(String labels) {

        return videoService.getByLabels(labels);
    }


}
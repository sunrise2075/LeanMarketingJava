package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.VideoCategoryMapper;
import com.yipage.leanmarketing.mapper.VideoMapper;
import com.yipage.leanmarketing.model.Video;
import com.yipage.leanmarketing.model.VideoCategory;
import com.yipage.leanmarketing.service.VideoService;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
@Service
@Transactional
public class VideoServiceImpl extends AbstractService<Video> implements VideoService {
    @Resource
    private VideoMapper videoMapper;
    @Resource
    private VideoCategoryMapper videoCategoryMapper;

    @Override
    public Integer save(Video model) {
        model.setUpdateTime(new Date());
        model.setCreateTime(new Date());

        VideoCategory videoCategory = videoCategoryMapper.selectByPrimaryKey(model.getCategoryId());
        if (videoCategory != null) {
            model.setCategoryName(videoCategory.getName());
        }
        return super.save(model);
    }

    @Override
    public List<Video> select(Video video) {
        return videoMapper.select(video);
    }

    /**
     * 按销售量前十的视频
     */
    @Override
    public List<Video> orderBySalesNum() {
        return videoMapper.orderBySalesNum();
    }

    /**
     * 只查两条数据
     *
     * @param categoryId
     * @param isFree
     * @param isShow
     * @return
     */
    @Override
    public List<Video> findVideo(Integer categoryId, Integer isFree, Integer isShow, Integer limit) {
        return videoMapper.findVideo(categoryId, isFree, isShow, limit);
    }

//    @Override
//    public Map<String, Object> recommendedVideos(Integer page, Integer limit, Integer userId) {
//
//        Map<String, Object> map = new HashMap<>(4);
//        int startIndex = (page-1)*limit;
//        List<Video> videoList = new ArrayList<>();
//        //查询数量
//        Long count = videoMapper.recommendedVideosCount(userId);
//        if (count>0){
//            videoList = videoMapper.recommendedVideos(startIndex,limit,userId);
//        }
//        map.put("count",count);
//        map.put("data",videoList);
//        map.put("code",200);
//        map.put("status",1);
//        return map;
//    }

    @Override
    public Map<String, Object> recommendedVideos(Integer page, Integer limit, Integer userId, Integer isFree) {

        Map<String, Object> map = new HashMap<>(4);
        int startIndex = (page - 1) * limit;
        List<Video> videoList = null;
        List<Video> videoList2 = null;
        //查询数量
        Long count = videoMapper.recommendedVideosCount(userId, isFree);
        if (count <= 0) {

            //按照观看数量进行排序
            count = videoMapper.getByWatchRecordCount(isFree);
            videoList = videoMapper.getByWatchRecord(startIndex, limit, isFree);

        } else {
            videoList = videoMapper.recommendedVideos(startIndex, limit, userId, isFree);
            if (count > 0 && count < 10) {
                Long count2;
                count2 = videoMapper.getByWatchRecordCount(isFree);

                if (page == 1) {

                    //第一页
                    if (limit > count.intValue()) {

                        limit = limit - count.intValue();
                        videoList2 = videoMapper.getByWatchRecord(startIndex, limit, isFree);
                        videoList.addAll(videoList2);
                        //去除重复元素
                        videoList = removerList(videoList);
                    }
                } else {
                    //其他页
                    startIndex = (page - 1) * limit + count.intValue();
                    videoList = videoMapper.getByWatchRecord(startIndex, limit, isFree);
                }
                //总条数
                count = count + count2;
            }
        }
        map.put("count", count);
        map.put("data", videoList);
        map.put("code", 200);
        map.put("status", 1);
        return map;
    }

//    @Override
//    public List<Video> getByLabels(String labels) {
//        String[] array = labels.split(",");
//        return videoMapper.getByLabels(array);
//    }

    @Override
    public Map getByLabels(String labels) {
        Map map = null;
        String[] array = labels.split(",");

        List<Integer> listAll = new ArrayList<>();

        if (array.length > 0) {
            map = new HashMap(array.length);
            for (int i = 0; i < array.length; i++) {

                List<Video> list = videoMapper.getByLabel(array[i]);

                if (!CollectionUtils.isEmpty(list)) {

                    Iterator<Video> videos = list.iterator();
                    while (videos.hasNext()) {

                        Video video = videos.next();

                        if (listAll.contains(video.getId())) {
                            list.remove(video);
                        }

                        listAll.add(video.getId());
                    }

                }
                //还需要去重
                map.put("list" + i, list);
            }
        }
        return map;
    }

    //除去重复元素的方法:
    public List removerList(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Object element = it.next();
            if (set.add(element)) {
                newList.add(element);
            }
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

}

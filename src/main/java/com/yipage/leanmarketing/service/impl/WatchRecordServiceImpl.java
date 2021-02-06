package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.core.ServiceException;
import com.yipage.leanmarketing.mapper.LabelRecordMapper;
import com.yipage.leanmarketing.mapper.UserMapper;
import com.yipage.leanmarketing.mapper.VideoMapper;
import com.yipage.leanmarketing.mapper.WatchRecordMapper;
import com.yipage.leanmarketing.model.LabelRecord;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.model.Video;
import com.yipage.leanmarketing.model.WatchRecord;
import com.yipage.leanmarketing.service.WatchRecordService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/17.
 */
@Service
@Transactional
public class WatchRecordServiceImpl extends AbstractService<WatchRecord> implements WatchRecordService {
    @Resource
    private WatchRecordMapper watchRecordMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private VideoMapper videoMapper;

    @Resource
    private LabelRecordMapper labelRecordMapper;

    @Override
    public Integer save(WatchRecord model) {

        User user = new User();
        user.setWxid(model.getOpenid());

        List<User> users = userMapper.select(user);
        if (!CollectionUtils.isEmpty(users)) {
            model.setUserName(users.get(0).getNickname());
            model.setUserId(users.get(0).getId());
        }

        //添加一条用户标签记录
        addOrUpdateLabelRecord(model);

        //查看是否已经观看过了
        WatchRecord record = new WatchRecord();
        record.setOpenid(model.getOpenid());
        record.setContentId(model.getContentId());

        List<WatchRecord> recordList = watchRecordMapper.select(record);


        if (CollectionUtils.isEmpty(recordList)) {  //没有观看过就添加数据

            model.setCreateTime(new Date());
            model.setUpdateTime(new Date());
            return watchRecordMapper.insertSelective(model);
        } else {  //更新观看记录
            WatchRecord watchRecord = new WatchRecord();
            watchRecord.setId(recordList.get(0).getId());
            watchRecord.setUpdateTime(new Date());
            watchRecord.setDuration(model.getDuration());
            return watchRecordMapper.updateByPrimaryKeySelective(watchRecord);

        }
    }


    @Override
    public List<WatchRecord> select(WatchRecord watchRecord) {
        return watchRecordMapper.select(watchRecord);
    }

    @Override
    public Integer selectCount(WatchRecord watchRecord) {
        return watchRecordMapper.selectCount(watchRecord);
    }

    /**
     * 添加或更新用户的标签记录
     *
     * @param model
     */
    private void addOrUpdateLabelRecord(WatchRecord model) {

        //获取视频
        Video video = videoMapper.selectByPrimaryKey(model.getVideoId());
        if (video == null) {
            throw new ServiceException("不存在此视频");
        }
        //找到对应的关联视频
        String videos = video.getVideos();
        if (StringUtils.isNotEmpty(videos)) {

            String[] arr = videos.split(",");

            for (String s : arr) {
                Integer videoId = Integer.parseInt(s);
                //查询用户标签记录里面存不存在此标签
                LabelRecord labelRecord = new LabelRecord();
                labelRecord.setTypeId(videoId);
                labelRecord.setType(LabelRecord.TYPE_VIDEO);

                //找到原来的记录
                LabelRecord originRecord = labelRecordMapper.selectOne(labelRecord);

                if (originRecord == null) {
                    //直接添加一条用户标签记录
                    LabelRecord addRecord = new LabelRecord();
                    addRecord.setType(LabelRecord.TYPE_VIDEO);
                    addRecord.setTypeId(videoId);
                    addRecord.setCount(1);
                    addRecord.setCreateTime(new Date());
                    addRecord.setUpdateTime(new Date());

                    labelRecordMapper.insertSelective(addRecord);


                } else {
                    //更新用户标签记录
                    LabelRecord updateRecord = new LabelRecord();
                    updateRecord.setId(originRecord.getId());
                    updateRecord.setCount(originRecord.getCount() + 1);
                    updateRecord.setUpdateTime(new Date());

                    labelRecordMapper.updateByPrimaryKeySelective(updateRecord);
                }
            }
        }
    }
}

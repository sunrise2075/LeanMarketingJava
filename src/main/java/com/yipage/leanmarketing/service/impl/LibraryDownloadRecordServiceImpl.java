package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.core.ServiceException;
import com.yipage.leanmarketing.mapper.LabelRecordMapper;
import com.yipage.leanmarketing.mapper.LibraryDownloadRecordMapper;
import com.yipage.leanmarketing.mapper.LibraryMapper;
import com.yipage.leanmarketing.model.LabelRecord;
import com.yipage.leanmarketing.model.Library;
import com.yipage.leanmarketing.model.LibraryDownloadRecord;
import com.yipage.leanmarketing.service.LibraryDownloadRecordService;
import org.apache.commons.lang3.StringUtils;
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
public class LibraryDownloadRecordServiceImpl extends AbstractService<LibraryDownloadRecord> implements LibraryDownloadRecordService {
    @Resource
    private LibraryDownloadRecordMapper libraryDownloadRecordMapper;

    @Resource
    private LibraryMapper libraryMapper;

    @Resource
    private LabelRecordMapper labelRecordMapper;


    @Override
    public Integer save(LibraryDownloadRecord model) {

        //添加一条用户标签记录
        addOrUpdatelabelRecord(model);

        return super.save(model);
    }


    @Override
    public List<LibraryDownloadRecord> select(LibraryDownloadRecord record) {
        return libraryDownloadRecordMapper.select(record);
    }

    @Override
    public Integer selectCount(LibraryDownloadRecord libraryDownloadRecord) {
        return libraryDownloadRecordMapper.selectCount(libraryDownloadRecord);
    }

    /**
     * 添加或更新用户的标签记录
     *
     * @param model
     */
    private void addOrUpdatelabelRecord(LibraryDownloadRecord model) {

        //获取文库信息
        Library library = libraryMapper.selectByPrimaryKey(model.getLibraryId());
        if (library == null) {
            throw new ServiceException("此文库已被删除");
        }
        //获取视频的标签
        String librarys = library.getLibrarys();
        if (StringUtils.isNotEmpty(librarys)) {

            String[] arr = librarys.split(",");

            for (String s : arr) {
                Integer libraryId = Integer.parseInt(s);
                //查询用户标签记录里面存不存在此标签
                LabelRecord labelRecord = new LabelRecord();
                labelRecord.setTypeId(libraryId);
                labelRecord.setType(LabelRecord.TYPE_LIBRARY);

                //找到原来的记录
                LabelRecord originRecord = labelRecordMapper.selectOne(labelRecord);

                if (originRecord == null) {
                    //直接添加一条用户标签记录
                    LabelRecord addRecord = new LabelRecord();
                    addRecord.setType(LabelRecord.TYPE_LIBRARY);
                    addRecord.setTypeId(libraryId);
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

package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.LibraryDownloadRecord;
import com.yipage.leanmarketing.service.LibraryDownloadRecordService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/17.
 */
@RestController
@RequestMapping("/library/download/record")
public class LibraryDownloadRecordController {
    @Resource
    private LibraryDownloadRecordService libraryDownloadRecordService;

    @PostMapping("add")
    public Result add(@RequestBody LibraryDownloadRecord libraryDownloadRecord) {
        libraryDownloadRecord.setCreateTime(new Date());
        libraryDownloadRecord.setUpdateTime(new Date());
        libraryDownloadRecordService.save(libraryDownloadRecord);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        libraryDownloadRecordService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        libraryDownloadRecordService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody LibraryDownloadRecord libraryDownloadRecord) {
        libraryDownloadRecordService.update(libraryDownloadRecord);
        return ResultGenerator.genSuccessResult();
    }

//    @GetMapping("/{id}")
//    public Result detail(@PathVariable Integer id) {
//        LibraryDownloadRecord libraryDownloadRecord = libraryDownloadRecordService.findById(id);
//        return ResultGenerator.genSuccessResult(libraryDownloadRecord);
//    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "openid", required = false) String openid) {
        String orderBy = "update_time desc";
        PageHelper.startPage(page, limit, orderBy);
        LibraryDownloadRecord record = new LibraryDownloadRecord();
        if (StringUtils.isNotEmpty(openid)) {
            record.setOpenid(openid);
        }
        List<LibraryDownloadRecord> list = libraryDownloadRecordService.select(record);
        PageInfo pageInfo = new PageInfo(list);
//        List<Map<String,Object>> list2 = new ArrayList<>();
//        if(!CollectionUtils.isEmpty(list)){
//            for (LibraryDownloadRecord libraryDownloadRecord : list) {
//                Map<String,Object> map = new HashMap<>();
//
//                Library library = libraryService.findById(libraryDownloadRecord.getLibraryId());
//                map.put("id",libraryDownloadRecord.getId());
//                map.put("url",libraryDownloadRecord.getUrl());
//                map.put("fileType",libraryDownloadRecord.getFileType());
//                map.put("fileSize",libraryDownloadRecord.getFileSize());
//                map.put("libraryId",libraryDownloadRecord.getLibraryId());
//                map.put("introduce",library.getIntroduce());
//                map.put("title",library.getTitle());
//                map.put("createTime",libraryDownloadRecord.getCreateTime());
//                map.put("updateTime",libraryDownloadRecord.getUpdateTime());
//                list2.add(map);
//            }
//        }
//        pageInfo.setList(list);
        return MapUtil.PageResult(pageInfo);
    }


}

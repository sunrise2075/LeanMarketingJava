package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.LibraryMapper;
import com.yipage.leanmarketing.model.Library;
import com.yipage.leanmarketing.service.LibraryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;


/**
 * Created by CodeGenerator on 2018/12/13.
 */
@Service
@Transactional
public class LibraryServiceImpl extends AbstractService<Library> implements LibraryService {
    @Resource
    private LibraryMapper libraryMapper;

    @Override
    public List<Library> select(Library library) {
        return libraryMapper.select(library);
    }

    @Override
    public List<Library> findByKeyWord(String title, String fileType, Integer categoryId) {
        return libraryMapper.findByKeyWord(title, fileType, categoryId);
    }

    @Override
    public Map<String, Object> recommendedLibrarys(Integer page, Integer limit, Integer userId, String fileType, Integer isFree) {

        Map<String, Object> map = new HashMap<>(4);
        int startIndex = (page - 1) * limit;
        List<Library> libraryList = null;
        List<Library> libraryList2 = null;
        //查询数量
        Long count = libraryMapper.recommendedLibrarysCount(userId, fileType, isFree);
        if (count <= 0) {

            //按照观看数量进行排序
            count = libraryMapper.getByDownLoadRecordCount(fileType, isFree);
            libraryList = libraryMapper.getByDownLoadRecord(startIndex, limit, fileType, isFree);

        } else {
            libraryList = libraryMapper.recommendedLibrarys(startIndex, limit, userId, fileType, isFree);
            if (count > 0 && count < 10) {
                Long count2;
                count2 = libraryMapper.getByDownLoadRecordCount(fileType, isFree);

                if (page == 1) {

                    //第一页
                    if (limit > count.intValue()) {

                        limit = limit - count.intValue();
                        libraryList2 = libraryMapper.getByDownLoadRecord(startIndex, limit, fileType, isFree);
                        libraryList.addAll(libraryList2);
                        //去除重复元素
                        libraryList = removerList(libraryList);
                    }
                } else {
                    //其他页
                    startIndex = (page - 1) * limit + count.intValue();
                    libraryList = libraryMapper.getByDownLoadRecord(startIndex, limit, fileType, isFree);
                }
                //总条数
                count = count + count2;
            }
        }
        map.put("count", count);
        map.put("data", libraryList);
        map.put("code", 200);
        map.put("status", 1);
        return map;
    }

    @Override
    public Map getByLabels(String labels) {
        Map map = null;
        String[] array = labels.split(",");

        List<Integer> listAll = new ArrayList<>();

        if (array.length > 0) {
            map = new HashMap(array.length);
            for (int i = 0; i < array.length; i++) {

                List<Library> list = libraryMapper.getByLabel(array[i]);

                if (!CollectionUtils.isEmpty(list)) {

                    Iterator<Library> librarys = list.iterator();
                    while (librarys.hasNext()) {

                        Library library = librarys.next();

                        if (listAll.contains(library.getId())) {
                            librarys.remove();
                        }

                        listAll.add(library.getId());
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

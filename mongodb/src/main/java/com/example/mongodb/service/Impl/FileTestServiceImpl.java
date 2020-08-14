package com.example.mongodb.service.Impl;

import com.example.mongodb.dao.FileDao;
import com.example.mongodb.entity.FileTest;
import com.example.mongodb.service.FileTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author wolf  VX:a815193474
 * @Data 2019-10-17 16:41
 */
@Service
public class FileTestServiceImpl implements FileTestService {
    @Autowired
    private FileDao fileDao;
    public void save(FileTest fileTest){
    fileDao.save(fileTest);
    }
    public FileTest find(String name){
        return fileDao.findByName(name);
    }
}

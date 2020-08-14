package com.example.mongodb.service.Impl;

import com.example.mongodb.dao.TestDao;
import com.example.mongodb.entity.Test;
import com.example.mongodb.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author wolf  VX:a815193474
 * @Data 2019-10-16 17:49
 */

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;
    public Test findByName(String id){
        return testDao.findByName(id);
    }
    public void Save(Test test){
        testDao.save(test);

    }

    @Override
    public List<Test> findAll() {
        return testDao.findAll();
    }

    @Override
    public boolean delete(String id) {
        try{
        testDao.deleteById(id);
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
        return true;

    }

}

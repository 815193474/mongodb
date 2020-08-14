package com.example.mongodb.service;

import com.example.mongodb.entity.Test;

import java.util.List;

/**
 * @Author wolf  VX:a815193474
 * @Data 2019-10-17 10:02
 */
public interface TestService {
    Test findByName(String id);
    void Save(Test test);
    List<Test> findAll();
    boolean delete(String id);

}

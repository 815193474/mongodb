package com.example.mongodb.dao;

import com.example.mongodb.entity.Test;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * @Author wolf  VX:a815193474
 * @Data 2019-10-16 17:48
 */
public interface TestDao extends MongoRepository<Test,Object> {

    Test findByName(String id);
}

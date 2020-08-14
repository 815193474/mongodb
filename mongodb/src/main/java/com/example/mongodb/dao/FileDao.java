package com.example.mongodb.dao;

import com.example.mongodb.entity.FileTest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Author wolf  VX:a815193474
 * @Data 2019-10-17 16:46
 */
public interface FileDao extends MongoRepository<FileTest,Object> {
    FileTest findByName(String name);
}

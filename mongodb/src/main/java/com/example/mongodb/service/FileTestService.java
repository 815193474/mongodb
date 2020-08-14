package com.example.mongodb.service;

import com.example.mongodb.entity.FileTest;

/**
 * @Author wolf  VX:a815193474
 * @Data 2019-10-17 16:40
 */
public interface FileTestService {
    void save(FileTest fileTest);
    FileTest find(String name);

}

package com.example.mongodb.utils;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;

/**
 * @Author wolf  VX:a815193474
 * @Data 2019-10-18 16:26
 */

/**
 * mongodb的配置
 * 解决新版本不支持获取GridFSDBFile
 */

@Configuration
public class mongoconf {

    @Autowired
    private MongoDbFactory mongoDbFactory;


    @Bean
    public GridFSBucket getGridFSBucket() {
        MongoDatabase db = mongoDbFactory.getDb();
        return GridFSBuckets.create(db);
    }


}

package com.example.mongodb.controller;

import com.example.mongodb.entity.FileTest;
import com.example.mongodb.entity.Test;
import com.example.mongodb.service.FileTestService;
import com.example.mongodb.service.TestService;

import com.mongodb.client.gridfs.GridFSBucket;

import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.net.URLEncoder;
import java.util.List;

/**
 * @Author wolf  VX:a815193474
 * @Data 2019-10-16 17:49
 */


@RestController
@RequestMapping(value = "/test")
@Api(tags = "测试接口")
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private FileTestService fileTestService;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;
    @Autowired
    private MongoTemplate mongoTemplate;



    @RequestMapping(value = "/find")
    public void find(String id) {
        System.out.println("=================");

        System.out.println(testService.findByName(id));
        System.out.println("=================");

    }

    @RequestMapping(value = "/save")
    public void Save(String name, Integer age, Double weight, Double height) {

        Test t = new Test();
        t.setAge(age);
        t.setHeight(height);
        t.setName(name);
        t.setWeight(weight);
        testService.Save(t);
        System.out.println("插入数据成功");
    }

    @ApiOperation(value = "查询单个",tags = "查询单个")
    @ResponseBody
    @GetMapping(value = "/findOne")
    public Test findOne(String name){
        return testService.findByName(name);
    }
    @ApiOperation(value = "按照id删除",tags = "按照id删除")
    @ResponseBody
    @RequestMapping(value = "/delete")
    public void delete(@RequestParam(value = "name") String name){
            Query query=new Query(Criteria.where("name").is(name));
            mongoTemplate.remove(query,Test.class);
            System.out.println("删除成功");
    }
    @RequestMapping(value = "/findAll")
    @ResponseBody
    public List<Test> findAll() {
        return testService.findAll();
    }

    @RequestMapping(value = "/savefile")
    public void SaveMin() throws Exception {
        File file = new File("C:\\Users\\Administrator\\Desktop\\陇政通.png");
        InputStream fileInputStream = new FileInputStream(file);
        StringBuffer sb = new StringBuffer();
        int line;
        while ((line = fileInputStream.read()) != -1) {
            sb.append(line);
        }
        fileInputStream.close();
        String name = "Test";
        FileTest fileTest = new FileTest();
        fileTest.setDetails(sb.toString());
        fileTest.setName(name);
        fileTestService.save(fileTest);

        System.out.println("save success");
    }


    @RequestMapping(value = "/down")
    public void down() throws Exception {
        FileTest fi = fileTestService.find("Test");
        StringBuffer s = new StringBuffer();
        s.append(fi.getDetails());
        File file=new File("C:\\Users\\Administrator\\Desktop\\123.png");

        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(s.toString());
        bufferedWriter.flush();
        System.out.println("复制完成");
    }

    @ApiOperation(tags = "保存文件" ,value ="保存文件" )

    @RequestMapping(value = "/SaveFile")
    @ResponseBody

    public ObjectId SaveFile(@RequestParam("file")MultipartFile multipartFile) throws Exception{
        //获得文件名
        String filename=multipartFile.getOriginalFilename().replace(",", "");
        //获得类型
        String contentType=multipartFile.getContentType();
        //获得输入流
        InputStream inputStream=multipartFile.getInputStream();
        //objectId接收gridFsTemplate的返回数据
        ObjectId objectId=gridFsTemplate.store(inputStream,filename,contentType);
        System.out.println(objectId);
        return objectId;

    }


    @ApiOperation(tags = "下载文件" ,value ="下载文件" )
    @RequestMapping(value = "/downfile")
    @ResponseBody
    public void downfile(@RequestParam(name = "file_id") String fileId,
                         HttpServletRequest request,
                         HttpServletResponse response) throws  IOException{

            System.out.println("开始下载文件");
            //创建query
        //Query query=Query.query(Criteria.where("_id").is(fileId));
        Query query=Query.query(Criteria.where("filename").is(fileId));
        //查询单个文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(query);

        if(gridFSFile==null){
            System.out.println("找不到文件");
            return ;
        }

        String filename=gridFSFile.getFilename();
        String contentType=gridFSFile.getMetadata().get("_contentType").toString();
        System.out.println(contentType);

        response.setContentType(contentType);
        //以附件的方式下载、转换字符串
        response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(filename, "UTF-8") + "\"");
        //利用GridFSDownloadStream的下载流接收gridFSBucket的下载流
        GridFSDownloadStream downloadStream=gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
       //将要下载的文件和下载流传给GridFsResource
        GridFsResource gridFsResource=new GridFsResource(gridFSFile,downloadStream);
        //通知浏览器端取得输出流
        OutputStream outputStream=response.getOutputStream();
        //从GridFsResource获得输入流
        InputStream inputStream=gridFsResource.getInputStream();
        //IOUtils将输入流转换为输出流
        IOUtils.copy(inputStream,outputStream);

        outputStream.flush();
        outputStream.close();
        inputStream.close();

    }

    //删除文件
    @ApiOperation(tags = "删除文件" ,value ="删除文件" )
    @RequestMapping(value = "/deleteFile")
    @ResponseBody
    public void del(@RequestParam(name = "file_id") String fileId ){
        Query query=new Query(Criteria.where("_id").is(fileId));
        gridFsTemplate.delete(query);
        System.out.println("success");

    }



}
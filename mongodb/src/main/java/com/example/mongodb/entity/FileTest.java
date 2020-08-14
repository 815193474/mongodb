package com.example.mongodb.entity;

/**
 * @Author wolf  VX:a815193474
 * @Data 2019-10-17 16:38
 */
public class FileTest {
    private String name;
    private String details;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "FileTest{" +
                "name='" + name + '\'' +
                ", details=" + details +
                '}';
    }
}

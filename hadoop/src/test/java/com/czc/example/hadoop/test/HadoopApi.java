package com.czc.example.hadoop.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

/**
 *
 * 步骤:
 * 1 创建Configuration
 * 2 获取FileSystem
 * 3 就是HDFS API的操作
 *
 *
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/4/24下午9:47
 */
public class HadoopApi {


    public static final String HDFS_PATH = "hdfs://cyl:8020";

    FileSystem fileSystem = null;

    Configuration conf = null;


    @Before
    public void setUp()throws Exception{
        System.out.println("---setup---");
       conf = new Configuration();
        fileSystem = FileSystem.get(new URI(HDFS_PATH), conf,"chen");
    }

    @Test
    public void mkdir()throws Exception{

        Path path = new Path("/test/chen1");
        boolean b = fileSystem.mkdirs(path);
        System.out.println(b);
    }


    @After
    public void tearDown(){
        System.out.println("---tearDown---");
        conf = null;
        fileSystem = null;
    }

    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration();
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://cyl:8020"), conf,"chen");
        Path path = new Path("/test/chen");
        boolean b = fileSystem.mkdirs(path);
        System.out.println(b);
    }
}

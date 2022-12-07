package com.czc.example.hadoop.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.spec.OAEPParameterSpec;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

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
        conf.set("dfs.replication","1");
        fileSystem = FileSystem.get(new URI(HDFS_PATH), conf,"chen");
    }

    /**
     * 创建目录
     * @throws Exception
     */
    @Test
    public void mkdir()throws Exception{

        Path path = new Path("/test/chen1");
        boolean b = fileSystem.mkdirs(path);
        System.out.println(b);
    }

    /**
     * 查看HDFS内容
     */
    @Test
    public void text()throws Exception{
        FSDataInputStream stream = fileSystem.open(new Path("/README.txt"));

        IOUtils.copyBytes(stream,System.out,1024);
    }

    @Test
    public void create()throws Exception{
        FSDataOutputStream stream = fileSystem.create(new Path("/test/chen/b.txt"));
        stream.write("dafafdsfasfdfasdfa\ndfafadsfdjf".getBytes(StandardCharsets.UTF_8));
        stream.flush();
        stream.close();
    }


    @Test
    public void rename() throws Exception{
        fileSystem.rename(new Path("/test/chen/b.txt"),new Path("/test/chen/b.txt.tmp"));
    }


    /**
     * 拷贝小文件到HDFS
     * @throws Exception
     */
    @Test
    public void copyFromLocalFile()throws Exception{
        fileSystem.copyFromLocalFile(new Path("/home/chen/soft/hadoop331/LICENSE.txt"),new Path("/test/chen"));
    }

    @Test
    public void copyBigFileToHdfs()throws Exception{
        Long length = 0L;
        FSDataOutputStream stream = fileSystem.create(new Path("/test/chen/jdk.tar"), new Progressable() {
            @Override
            public void progress() {

                try {
                    Runtime.getRuntime().exec("clear");
                    FileStatus fileStatus = fileSystem.getFileStatus(new Path("/test/chen/jdk.tar"));
                    System.out.println("文件大小:" + fileStatus.getLen());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        InputStream in = new BufferedInputStream(new FileInputStream(
                "/home/chen/soft/java/jdk-15.0.2_linux-x64_bin.tar.gz"));
        IOUtils.copyBytes(in,stream,4096);
    }



    @Test
    public void download() throws Exception{
        fileSystem.copyToLocalFile(new Path("/test/chen/LICENSE.txt"),new Path("/home/chen/"));
    }

    @Test
    public void getStatus()throws Exception{


        FileStatus fileStatus = fileSystem.getFileStatus(new Path("/test/chen/a.txt"));
        System.out.println(1);
    }


    @Test
    public void listFiles()throws Exception{
        RemoteIterator<LocatedFileStatus> files = fileSystem.listFiles(new Path("/test/chen"), true);
        while (files.hasNext()){
            LocatedFileStatus n = files.next();
            String isDir = n.isDirectory()?"目录":"文件";
            String permission = n.getPermission().toString();
            String name = n.getPath().toString();
            short rep = n.getReplication();
            long len = n.getLen();
            System.out.println(isDir + "    " + permission + "    " + name + "    " + len + "    " + rep);
        }

    }


    @Test
    public void getFileBlocks()throws Exception{
        FileStatus fileStatus = fileSystem.getFileStatus(new Path("/test/chen/jdk.tar"));
        BlockLocation[] blocks = fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
        for (BlockLocation block : blocks) {
            for (String name : block.getNames()) {
                System.out.println(name+ " : " + block.getOffset() + ":" + block.getLength());
            }
        }
    }

    @Test
    public void delete()throws Exception{
        fileSystem.delete(new Path("/test/chen/jdk.tar"),false);
    }


    @Test
    public void testReplication(){
        /**
         * 默认值为3
         */
        System.out.println(conf.get("dfs.replication"));


        System.out.println(conf.get("dfs.replication"));
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

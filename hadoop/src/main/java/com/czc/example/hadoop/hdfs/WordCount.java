package com.czc.example.hadoop.hdfs;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 金陵笑笑生
 * @description: 使用HDFS API完成wordcount
 *  统计HDFS上文件的wc，然后将统计的结果输出到HDFS
 *
 *  功能拆解
 *
 *   1) 读取HDFS文件
 *   2）业务处理
 *   3） 将处理结果缓存起来 => Context
 *   4） 将结果输出到HDFS
 * @date 2022/4/25下午10:42
 */
public class WordCount {

    public static void main(String[] args) throws Exception{
        Path path = new Path("/test/chen/a.txt");
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://cyl:8020"), new Configuration(),"chen");
        RemoteIterator<LocatedFileStatus> files = fileSystem.listFiles(new Path("/test/chen"), true);
        Map<String,Long> contextMap = new HashMap<>();
        while (files.hasNext()){
            LocatedFileStatus n = files.next();
            try(FSDataInputStream in = fileSystem.open(n.getPath());BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
                String line = "";
                while ((line = reader.readLine()) != null){

                    String[] s = line.split(" ");
                    for (String str : s) {
                        if(!contextMap.containsKey(str)){
                            contextMap.put(str,1L);
                        }else{
                            contextMap.put(str,contextMap.get(str) + 1);
                        }
                    }
                }
            }

        }



        Path output = new Path("/test/output");

        try(FSDataOutputStream out = fileSystem.create(new Path(output, new Path("out.txt")))){
            List<ImmutablePair<String, Long>> collect = contextMap.entrySet().stream().map(e -> ImmutablePair.of(e.getKey(), e.getValue())).collect(Collectors.toList());
            Collections.sort(collect,new Comparator<Map.Entry<String, Long>>() {
                @Override
                public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            });

            collect.forEach(kv -> {
                try {
                    out.write((kv.getKey() + ":" + kv.getValue() + "\n").getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }
}

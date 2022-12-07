package com.czc.example.hadoop.mr.wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 *
 * KEYIN : MAP任务读数据的KEY类型，是每行数据起始位置的偏移量，Long
 * VALUEIN：Map任务读数据的value类型，其实就是一行一行的字符串
 *
 * KEYOUT：map方法自定义实现输出的KEY类型
 * VALUEOUT:map方法自定义实现输出的value类型
 *
 *
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/5/3下午11:09
 */
public class WordCountMapper extends Mapper<LongWritable, Text,Text, IntWritable> {


    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        /**
         * 将value对应的行数据按照指定的分隔符分割
         */

        String[] words = value.toString().split("\t");
        for (String word : words) {
            context.write(new Text(word),new IntWritable(1));
        }
    }
}

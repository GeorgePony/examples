package com.czc.example.hadoop.mr.wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;


/**
 * @author 金陵笑笑生
 * @description: TODO
 * @date 2022/5/3下午11:18
 */
public class WordCountReducer extends Reducer<Text, IntWritable,Text,IntWritable> {

    /**
     * map的输出到reduce，是按照相同的key分发到一个reduce上执行
     *
     * （hello,1）(hello,2)(hello,1)  => (hello ,[1,2,1])
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {

        int count = 0 ;
        Iterator<IntWritable> iterator = values.iterator();
        while (iterator.hasNext()){
            IntWritable next = iterator.next();
            count += next.get();
        }
        context.write(key,new IntWritable(count));


    }
}

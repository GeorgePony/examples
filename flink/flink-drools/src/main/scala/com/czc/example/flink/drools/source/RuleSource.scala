package com.czc.example.flink.drools.source

import com.czc.example.flink.drools.entity.Rule
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

import scala.io.{BufferedSource, Source}


class RuleSource extends RichParallelSourceFunction[Rule]{
  var running:Boolean = true
  override def run(sourceContext: SourceFunction.SourceContext[Rule]): Unit = {

    while (running){



      var str = ""
      //1. 获取数据源文件对象.
      val source:BufferedSource = Source.fromFile("/home/chen/workspace/idea/flink/flink-drools/src/main/resources/rules/bookDiscount.drl","UTF-8")
      //2. 以行为单位读取数据.
      val lines:Iterator[String] = source.getLines()
      //3. 将读取到的数据封装到列表中.
      val list1:List[String] = lines.toList
      list1.foreach((e) => str+=(e + "\n"))
      //4. 千万别忘记关闭Source对象.
      source.close()
      sourceContext.collect(new Rule(str,1,"rule"))
      Thread.sleep(500)
    }
  }

  override def cancel(): Unit = {
    this.running = false
  }
}

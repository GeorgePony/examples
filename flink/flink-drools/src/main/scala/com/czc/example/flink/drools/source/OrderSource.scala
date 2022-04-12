package com.czc.example.flink.drools.source

import com.czc.example.flink.drools.entity.BuizOrder
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

import scala.util.Random

class OrderSource extends RichParallelSourceFunction[BuizOrder]{

  var running:Boolean = true

  override def run(sourceContext: SourceFunction.SourceContext[BuizOrder]): Unit = {
    val rand = new Random()
    val taskIdx = getRuntimeContext.getIndexOfThisSubtask
    while(running){
      Thread.sleep(100)
      sourceContext.collect(new BuizOrder(100 + rand.nextInt(300)))
    }
  }

  override def cancel(): Unit = {
    running = false
  }
}

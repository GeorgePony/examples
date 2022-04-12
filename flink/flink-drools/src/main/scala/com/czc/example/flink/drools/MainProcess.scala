package com.czc.example.flink.drools
import com.czc.example.flink.drools.function.RuleOrderFunction
import org.apache.flink.api.scala._
import com.czc.example.flink.drools.source.{OrderSource, RuleSource}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.{CheckpointingMode, TimeCharacteristic}

object MainProcess {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.enableCheckpointing(5000)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
    env.setParallelism(1)
    val orderSource = env.addSource(new OrderSource)
    val ruleSource = env.addSource(new RuleSource)
    orderSource.connect(ruleSource).flatMap(new RuleOrderFunction()).map(e => (e.originalPrice,e.realPrice)).print()
    env.execute("my job")


  }
}

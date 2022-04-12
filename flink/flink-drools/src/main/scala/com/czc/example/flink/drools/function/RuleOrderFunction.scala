package com.czc.example.flink.drools.function

import com.czc.example.flink.drools.entity.{BuizOrder, Rule}
import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction
import org.apache.flink.util.Collector
import org.drools.core.impl.KnowledgeBaseImpl
import org.kie.api.conf.EventProcessingOption
import org.kie.api.io.ResourceType
import org.kie.api.KieServices
import org.kie.api.runtime.KieSession
import org.kie.internal.utils.KieHelper

import java.util.concurrent.locks.ReentrantLock

class RuleOrderFunction extends RichCoFlatMapFunction[BuizOrder,Rule,BuizOrder]{

  var kieSession:KieSession = _
  var kieHelper:KieHelper = _
  var kieBase:KnowledgeBaseImpl = _
  val lock = new ReentrantLock()

  override def flatMap1(in1: BuizOrder, collector: Collector[BuizOrder]): Unit = {
    lock.lock()
    try{
      if(kieSession == null && kieBase != null){
        kieSession = kieBase.newKieSession()
      }
      if(kieSession != null){
        kieSession.insert(in1)
        kieSession.fireAllRules()
        collector.collect(in1)
      }else{
        println(2)
      }
    }catch {
      case e:Throwable => e.printStackTrace()
    }finally {
      lock.unlock()
    }
  }

  override def flatMap2(rule: Rule, collector: Collector[BuizOrder]): Unit = {
    kieHelper = new KieHelper()
    kieHelper.addContent(rule.drlStr,ResourceType.DRL)
    val config = KieServices.Factory.get().newKieBaseConfiguration()
    config.setOption(EventProcessingOption.STREAM)
    try {
      kieBase = kieHelper.build().asInstanceOf[KnowledgeBaseImpl]
      //        kieSession = kieBase.newStatefulSession()
    }catch {
      case e:Throwable => e.printStackTrace()
    }
    lock.lock()
    kieSession = null
    lock.unlock()



    //    val services = KieServices.Factory.get()
//    val container = services.getKieClasspathContainer()
//    kieSession = container.newKieSession()
//    println(kieSession)
  }
}

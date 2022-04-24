package com.czc.example.scala

/**
 * @description: Scala中的偏函数
 *
 *     "偏"字的含义是"偏科","残缺",该函数只对多个特定的值给出特定的返回，其他的值给出另一个特定的返回。
 *     可以类比于模式匹配
 * @author 金陵笑笑生
 * @date 2022/4/13下午9:58
 */
object PartialFunc {
  def main(args: Array[String]): Unit = {
    val func: PartialFunction[Int,String] = {
      case 1 => "一"
      case 2 => "二"
      case 3 => "三"
      case _ => "其他"
    }
    println(func(3))
  }
}

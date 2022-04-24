package com.czc.example.scala

/**
 * @description: Scala的基础操作
 * @author 金陵笑笑生
 * @date 2022/4/13下午10:42
 */
object Basic {
  def main(args: Array[String]): Unit = {

    /**
     * Blocks: 可以用{}将多个表达式结合在一起，最后一个表达式会称为整个语句块的结果
     */

    println({
      val x = 1 + 1
      x
    })


    /**
     * Function VS  Methods
     * Scala中的Function 以val定义
     */
    val add = (x:Int,y:Int) => x + y
    println(add(3,2))

    /**
     * Methods与Function 很像，有如下几点不同:
     * 1. 以def 定义
     * 2. 可以有多个参数列表（Currying 函数科里化）
     */

    def add1(x:Int,y:Int): Int ={
      x + y
    }

    def add2(x:Int,y:Int)(mulit:Int):Int = {
      (x + y) * mulit
    }

    println(add1(2,3))
    println(add2(3,4)(6))
  }

}

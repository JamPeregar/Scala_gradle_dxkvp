package homework.kvp.DZ8

object Task8 extends{
/*
*
*Напишите функцию, которая принимает на вход два множества Set[Int]
*  и возвращает их объединение в виде нового множества
*
Напишите функцию, которая принимает на вход множество Set[Int] и элемент,
* и возвращает новое множество, содержащее все элементы из исходного множества, кроме данного элемента

* Напишите функцию, которая принимает на вход два Set[Int]
*  и возвращает новое множество, содержащее только те элементы, которые присутствуют только в одном из исходных множеств

* Напишите функцию, которая принимает Map[String, Option[Int]],
*  и возвращает Map, где ключ - это строка, а значение - удвоенное значение из Option

* Напишите функцию, которая принимает на вход Map[String, Int]
*  и возвращает новый Map, в котором все значения увеличены на 1

* Напишите функцию, которая принимает на вход Map[String, Int]
* и возвращает Map'ы, в первой ключи > 0, в другой < 0

* Напишите функцию, которая принимает на вход два Map[String, Int]
*  и возвращает новый Map, содержащий только те ключи, которые есть в обоих исходных Map, а значения складываются

* Напишите функцию, которая принимает на вход два Map[String, Int]
*  и возвращает новый Map, содержащий только те ключи, у которых значения равны
* */

  def task1(s1:Set[Int], s2: Set[Int]): Set[Int] = s1.concat(s2)

  def task2(s1:Set[Int],d:Int): Set[Int] = s1 - d

  def task3(s1:Set[Int],s2:Set[Int]): Set[Int] = s1.diff(s2)

  def task4(m1:Map[String,Option[Int]]): Map[String,Option[Int]] = m1 .map {
      case (k,v) =>
        val v2 = v.getOrElse(0) * 2
        (k,Option(v2))
    }

  def task5(m1:Map[String,Int]): Map[String,Int] = m1 .map {
    case (k,v) =>
      val v2 = v + 1
      (k,v2)
  }

  def task6(m1:Map[String,Int]): (Map[String,Int],Map[String,Int]) = {
    val mr1 = m1 filter {
      case (k,v) =>
        v<0
    }
    val mr2 = m1 filter {
      case (k,v) =>
        v>0
    }
    (mr1, mr2)
  }

  def task7(m1:Map[String,Int],m2:Map[String,Int]): Map[String,Int] = {
    val res = for {
      k <- m1.keySet intersect m2.keySet
    }yield {
      (k,m1(k)+m2(k))
    }
    res.toMap
  }

  def task8(m1:Map[String,Int],m2:Map[String,Int]): Map[String,Int] = {
    //var 1
    val res1 = for {
      k1 <- m1.keySet
      k2 <- m2.keySet
    }yield {
      if (m1.get(k1) == m2.get(k2)) (k1,m1(k1))
      else ("",1)
    }
    val res2 = for {
      k2 <- m2.keySet
      k1 <- m1.keySet
    }yield {
      if (m1.get(k1) == m2.get(k2)) (k2,m2(k2))
      else ("",1)
    }
    //val res:Map[String,Int] = res1.toMap ++ res2.toMap
    res1.toMap.removed("") ++ res2.toMap.removed("")//??
  }

  def main(args: Array[String]): Unit = {
    println("//1")
    val s1 = Set(1,2,3)
    val s2 = Set(3,4,5)
    val res: Set[Int] = task1(s1,s2)
    println(res)
    println("//2")
    println(task2(s1,2))
    println("//3")
    println(task3(s1,s2))
    println("//4")
    val m1:Map[String,Option[Int]] = Map("Java" -> Option(1), "Scala" -> Option(2))
    println(task4(m1))
    println("//5")
    val m2:Map[String,Int] = Map("Java" -> 1, "Scala" -> -2, ("Min", -23),("Kate", 37), ("Jack", -54), "Ben" -> 1)
    val m22:Map[String,Int] = Map("Java" -> 1, "Scala" -> -2, "Barney" -> 37)
    println(task5(m2))
    println("//6")
    println(task6(m2))
    println(task6(m2)._1)
    println(task6(m2)._2)
    println("//7")
    println(task7(m2,m22))
    println("//8")
    println(task8(m2,m22))
  }

}

package homework.kvp.DZ7

import scala.{+:, :+}
import scala.collection.IterableOnce.iterableOnceExtensionMethods
import scala.util.Try


object TASK7_LEC extends App{


  //List
  val l1:List[Int] = List(1,2,3,4,5)
  println(s"The List is $l1")
  //l1.min // UnsupportedOperationException
  //l1.minOption
  try {
    val a = l1.maxOption //crash at empty or Nil List
    println(s"$l1 .maxOption is " + a)
    println("l1.minOption")
    println(l1.minOption)
    //println("l1.maxO")
    //println(l1.max)
  } catch  {
    case ex: Exception => println("ERROR AT MAXMIN TEST\n Stack: " + ex);
  }

  //l1.take(2) //return list/set/etc with this element
  //l1.takeRight(3) // NoSuchElementException
  try {
    println("l1.takeRight")
    val a1 = l1.takeRight(1)
    println(a1)
  } catch {
    case ex:Exception => println("ERR AT TAKE TEST\nStack: " + ex);
  }



  //l1.head   // return value
  //l1.headOption //return Some()
  //l1.last // NoSuchElementException
  //l1.lastOption
  try {
    println("l1.head")
    val a2 = l1.head
    println(a2)
    println("l1.lastOption")
    val a21 = l1.lastOption
    println(a2)
  } catch {
    case ex:Exception => println("ERR AT .HEAD .LAST TEST\nStack: " + ex)
  }

  //l1.tail // UnsupportedOperationException  return list without 1th, empty list when only 1 element, crash if empty list
  //l1.init // UnsupportedOperationException
  try {
    println("l1.tail .init")
    val a3:Option[List[Int]] = Option(l1.tail)//.orElse(Some(List.empty))
    println(a3)
  } catch  {
    case ex: Exception => println("ERROR AT TAIL TEST - " + ex)
  }

  //l1.drop(1)
  //l1.dropRight(1)
  //l1.dropWhile(_ < 2)
  try {
    println("l1.dropwhile el < 2")
    println(l1.dropWhile(_ < 2))
  } catch  {
    case ex: Exception => println("ERROR AT DROP TEST - " + ex)
  }


  //println(l1.takeWhile(_ == 1))
}

object TASK7 extends App{
  object WeekDay extends Enumeration {
    type WeekDay = Value
    val Mon, Tue, Wed, Thu, Fri, Sat, Sun = Value
  }
  object WeekDay2 extends Enumeration {
    type WeekDay = Value
    val Mon = Value(1,"Monday")
    val Tue = Value(2,"Tuesday")
    val Wed = Value(3,"Wednesday")
    val Thu = Value(4,"Thursday")
    val Fri = Value(5,"Friday")
    val Sat = Value(6,"Saturday")
    val Sun = Value(7,"Sunday")
  }


  import WeekDay._ //import all as *
  //import WeekDay2._ //import all as *

  def isWorkingDay(d: WeekDay) = ! (d == Sat || d == Sun)
  def getOtherDays(d1: WeekDay, d2: WeekDay): Set[WeekDay] = {
    WeekDay.values filter (i => (i.id > d1.id && i.id < d2.id) || (i < d1 && i > d2)) //foreach println
  }
  def getNextDay(d1: WeekDay): WeekDay = {
    //WeekDay.values.filter(i => i != d1 && i!=d2) foreach println
    // why not working if (WeekDay.maxId != d1.id) WeekDay.values.filter(i => i.id == (d1.id + 1)).head
    if (WeekDay.maxId-1 > d1.id) WeekDay.values.filter(i => i.id == (d1.id + 1)).head
    else WeekDay.Mon
  }
  def getNextDay2(d1: WeekDay2.WeekDay): WeekDay2.WeekDay = {
    if (WeekDay2.maxId-1 > d1.id) WeekDay2.values.filter(i => i.id == (d1.id + 1)).head
    else WeekDay2.Mon
  }

  WeekDay.values filter isWorkingDay foreach println //wow cool shit

  val monday = Mon
  val monday2 = WeekDay.withName("Mon")
  val day0: Boolean = isWorkingDay(Sat)
  val day1: Boolean = isWorkingDay(monday)
  val day2: Boolean = isWorkingDay(monday2)
  WeekDay.values.filter(i => isWorkingDay(i))

  println("Mondays:\n" + s"$Sat - $day0")
  println(s"$monday - $day1")
  println(s"$monday2 - $day2")

  println("TASK1")
  println(getOtherDays(Mon, Sat))
  println("TASK2")
  println(s"Last day id ${WeekDay.maxId}")
  println(getNextDay(Tue))
  println(getNextDay(Sat))
  println(getNextDay(Mon))
  println(getNextDay(Wed))
  println(getNextDay(Sun))

  //другой способ перечисления
  println("TASK2-v2")
  println(getNextDay2(WeekDay2.Tue))
  println(getNextDay2(WeekDay2.Sun))
  println(getNextDay2(WeekDay2.Mon))
  println(getNextDay2(WeekDay2.Wed))
  println(WeekDay.maxId > Sun.id)
  //Task3

}

object Task73 {
  def main(args: Array[String]): Unit = {

    def task3(l:List[Int]): List[Int] = {
      //l.distinct  // => List(1, 2, 3)
      l.toSet.toList //as above
    }
    println("\nResult:")
    //println(task3(List(1,2,2,3,4)))
    println(task3(List(1,2,2,3,4)).toList)
    val lst:List[Int] = List()
    lst :+ 1
    lst :+ 2
    println(lst :+ 2)
  }
}
/*
* Напишите функцию, которая принимает на вход список Option[Int]
* и возвращает среднее значение чисел (игнорируя None)
* */
object Task74 {
  def main(args: Array[String]): Unit = {
    val l:List[Option[Int]] = List(Option(1), Option(3),None)
    //4
    println("Task 4")
    println("1, 3, None - middle is " + task4(l))

    //5
    println("Task 5")
    val l1 = List(Option(1), Option(3),None, Option(10))
    println(task5(l1))
    //6
    println("Task 6")
    val l2 = List(Option("Java"), Option("JavaScript"),None, Option("Scala"))
    println(task6(l2))
    //7
    println("Task 7")
    val l31:List[String] =List("Java","Scala","Python")
    val l32:List[String] =List("C++","Python","C#")
    println(s"$l31\n$l32")
    println(task7(l31,l32))
    //8
    println("Task 8")
    val l41:List[Int] =List(1,2)
    val l42:List[Int] =List(3,4)
    println(task8(l41,l42))
  }

  def task4(l:List[Option[Int]]): Double = {
    /*var sum: Int = 0 //too smart
    l foreach(i => sum += i.getOrElse(0))  //flatMap (i=> i +: nl)
    sum/l.size ///l.flatten.sum*/
    val l2:List[Int] = l.flatten //deletes Option
    val a:Double = l2.sum/l2.size
    println(a)
    a
  }

  def task5(l:List[Option[Int]]): Double = l.flatten.sum

  def task6(l:List[Option[String]]): List[String] = l.flatten filter (str=>str.length>5)

  def task7(l1:List[String], l2:List[String]): List[String] = l1.intersect(l2)

  def task8(l1:List[Int], l2:List[Int]): List[String] = {
    val lst:List[String] = for {
      i <- l1
      j <-l2
    } yield(j.toString + i.toString)
    lst ++ lst map (str => str.reverse) //add 12 - 21
  }


}

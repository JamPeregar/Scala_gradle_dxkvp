package homework.kvp.DZ12

import scala.beans.BeanProperty

object Task12 extends App{

  def task1[T](t:List[T]): T = {
    //t.headOption
    t.head
  }

  def task2[T](t:List[T]): List[T] = {
    t.toSet.toList
  }
  def task3[T](t:List[T]): Int = {
    t.length
  }

  //4

  //case class Item(name:String)
  //case class Book(title:String, author:String)

  def task4[T,U](t:List[T], f:T=>U): List[U] = {
    t map f
  }

  //1
  println("//1")
  val ls1 = List(1,2,3,3,1,2,4,5,5,6,7)
  val ls1s = List("3d","2","wsd","3d")
  println(task1(ls1))
  println(task1(ls1s))
  //2
  println("//2")
  println(task2(ls1))
  println(task2(ls1s))
  //3
  println("//3")
  println(task3(ls1))
  println(task3(ls1s))
  //4
  println("//4")
  class Item(name:String) {
    override def toString: String = s"Item: $name"
  }

  case class Book(@BeanProperty title:String, @BeanProperty author:String) extends Item(title)

  val b1 = Book("Arra", "Nuue")
  val b2 = Book("BBra", "Nuue")
  val b3 = Book("Taopty", "Boqeryt")
  val books = List(b1,b2,b3) //List
  def titles(b:Book):String = { //Book=>String
    b.title
  }
  println(s"task4(books,titles) = ${task4(books,titles)}")

}

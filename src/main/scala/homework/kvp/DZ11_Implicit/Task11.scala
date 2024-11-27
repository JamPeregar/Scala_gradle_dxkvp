package homework.kvp.DZ11_Implicit

import scala.beans.BeanProperty

/*
*
* Создайте implicit class для Int, который добавляет метод для умножения числа на определенное значение.
* Например, метод для умножения числа на 10
*
Создайте implicit class для Int, который добавляет метод для проверки, является ли число четным
*
Напишите implicit val для хранения значения курса обмена валюты (например, USD to RUB)
* и используйте его для функции конвертации суммы денег из одной валюты в другую
*
Создайте case class Person с полями name:String и age:Int.
* Затем определите implicit val для объекта типа Ordering[Person]S,
* который сравнивает объекты Person по возрасту.
* Создайте List[Person], заполненный какими-либо экземплярами класса Person,
* напечатайте его на консоль, отсортируйте список и напечатайте отсортированный список на консоль
*
* */

object Task11 extends App{
//1
  implicit class IntOps(i: Int) {
    def opt: Int = i*10

    def checkChet: Boolean = i % 2 == 0



  }
  val x:Int = 2
  val y:Int = 9
  println("----1----")
  println(x.opt)
  println(y.opt)
  //2
  println(s"${x}.checkChet = ${x.checkChet}")
  println(s"${y}.checkChet = ${y.checkChet}")
  //3
  type Rub = Int
  implicit val DtoR:Rub = 100

  def doltorub(d:Int)(implicit dtoR: Rub ):Int = d*dtoR

  println(s"doltorub(5) = ${doltorub(5)}")

  //4
  import scala.math.Ordering.Implicits.seqOrdering

  //import scala.math.Ordered.orderingToOrdered
  case class Person(@BeanProperty name: String, @BeanProperty age: Int) {
    //def compare(that: Person): Int = this.age compare that.age
  }

  val p1 = Person("Bob",24)
  val p2 = Person("Stan",34)
  val p3 = Person("Alex",44)
  val p4 = Person("Matt",14)

  val lp:List[Person] = List(p1,p2,p3,p4)
  //val lp2 = lp.sortBy(a => a.age)
  //println(lp2)


  implicit val ord:Ordering[Person] = Ordering.by(a=>a.age)

  //val sorted = Ordered.orderingToOrdered(lp)

  println(lp.sorted)


}

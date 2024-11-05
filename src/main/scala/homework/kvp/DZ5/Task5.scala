package homework.kvp.DZ5

object Task5 extends App{

  val str1:Option[String] = Option("java")
  val str2:Option[String] = Option("no java")
  val num1:Option[Int] = Option(15)
  val num2:Option[Int] = Option(25)
  val num3:Option[Int] = Option.empty
  val f:Option[Int] = num2
  val f2:Option[Int] = num3

  val num12 = num1.flatMap {
    i => Option(i + 100)
  }

  func1(str1)
  func1(str2)
  func2(num1)

  val lst: List[Option[Int]] = List(num1, num2, num3, f ,f2, num12)
  func2(lst.head)
  lst.foreach(i => func2(i))

  //FUNCTIONS//

  def func1(n:Option[String]): Unit =  if (n.contains("java")) println("Yes") else println("No")

  def func2(n:Option[Int]): Unit = {
    n match {
      case Some(value) => {
        print(s"\n $value is ")
        if (value<12) println("child")
        else if (value<20) println("teen")
        else if (value<80) println("adult")
        else println("granny-granny person")
      }
      case None => println(s" $n is Unknown")
    }
  }
}

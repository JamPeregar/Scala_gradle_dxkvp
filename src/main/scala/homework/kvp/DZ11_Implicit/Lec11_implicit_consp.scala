package homework.kvp.DZ11_Implicit

import homework.kvp.DZ4.Task4

import scala.beans.BeanProperty

object Lec11_implicit_consp extends App{
  //implicit val
  //implicit def // func/преобразования
  //implicit class

  implicit val x:Int = 1

  implicit val y:Double = 1.0 //cant use 2 with same type

  def a(y:Int)(implicit a:Double) = a
  def a2(y:Int)(implicit a:Int) = a

  println(a(5))
  println(a2(5))

  //implicit val x2:Int = 1 //will be conflict with x above - no one placed
  def a3(y: Int)(implicit i:Int): Unit = {
    println(s"$y - y\n$i - i")
  }
  a3(1)
  println("//--------------//")
  //ctrl alt shift AND + - show implicits
  case class Example(str:String)
  def f1(prefx:String)(implicit e: Example): Unit = {
  println(s"$prefx $e")
  }

  implicit val e1: Example = Example("fjjcn 1")
  f1("1")
  f1("2")
  f1("3")(Example("Example 2"))

  def f2(x:Int)(implicit e:Example): Unit = println(s"$e - $x")

  f2(1)
  f1("Example 3") //, берёт из стр 20


}

object My_ImplicitDef extends App{
  case class A(i:Int)
  case class B(i:Int)

  implicit def a2b:A=>B = a => B(a.i)




  val a:A = A(1)
  val b:B = a //val b:B = a2b(a)
  //Через посредника и всё
}

object My_ImplicitClass extends App{
  implicit class A(i:Int) {
    def opt = Option(i)
  }
}

object ImplicitClass2 extends App{
  import My_ImplicitClass._

  val x: Int = 1
  println(x)
  val optX: Option[Int] = x.opt
  println(optX)
}

object ImplicitDefForImport2 {
  implicit def doubToInt(d: Double): Int = d.round.toInt //uses it if int required but double founded
  implicit def strToInt(s: String): Int = Integer.parseInt(s) //use it if int required but string number founded
}

object ImplicitDefHell2 extends App {

  import ImplicitDefForImport2._

  val jd: java.lang.Double = null
  println(jd) // null
  val sd: Double = jd
  println(sd) // 0
  val optSd: Option[Double] = Option(jd)
  println(optSd) // Some(0)
  val optSd1: Option[Double] = Option(jd).map(Double2double)
  println(optSd1) // None

  val d:Int = 1.69
  println(d)
  "17" / 2
  println(s"${"18" / 2}")

  implicit val s:String = "Hello World"

  def printContext(implicit ctx: String): Unit = println(ctx)

  printContext

  //implicit def someint(s:String): Int = 1 //useless implicit def rewrite strToInt
}

object ImplicitClassOps2 extends App {
  implicit class IntOps(i: Int) {
    def opt: Option[Int] = Option(i)
  }

  implicit class StrOps(s: String) {
    def orJava: String = Option(s).getOrElse("Java")
  }

  case class Lang(@BeanProperty name: String, @BeanProperty age: Int)


  val x: Int = 1

  val cx = x.opt    //implicit as case class?
  val io = IntOps(1)

  val java = Lang(null, 29)
  val scala = Lang("Scala".orJava,21)

  println(java.getName.orJava)
  println(scala.getName.orJava) //Found "Scala" anyway


}



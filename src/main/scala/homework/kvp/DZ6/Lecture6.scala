package homework.kvp.DZ6

import scala.util.{Failure, Success, Try} //всадники апокалипсиса. А ещё Трамп победил.

object OptionHW extends App{
  object ForCompSupport {
    def apply[A](a: A): ForCompSupport[A] =
      if (a == null) ForCompEmpty[A]() else ForCompNotEmpty(a)
  }
  trait ForCompSupport[A] {
    def isEmpty: Boolean = this == ForCompEmpty[A]()
    def get: A
    def map[B](f: A => B): ForCompSupport[B] =
      if (isEmpty) ForCompEmpty[B]() else ForCompNotEmpty(f(this.get))
    def flatMap[B](f: A => ForCompSupport[B]): ForCompSupport[B] =
      if (isEmpty) ForCompEmpty[B]() else f(this.get)
    def withFilter(p: A => Boolean): ForCompSupport[A] =
      if (isEmpty || p(this.get)) this else ForCompEmpty[A]()
  }
  case class ForCompEmpty[A]() extends ForCompSupport[A] {
    override def get: A = throw new Exception("ForCompEmpty")
  }
  case class ForCompNotEmpty[A](a: A) extends ForCompSupport[A] {
    override def get: A = a
  }

  val x1_1: ForCompSupport[String] = ForCompSupport[String](null)
  val x1_2: ForCompSupport[Int]    = ForCompSupport(1)
  //println(x1_1.get)
  println(x1_1)
  println(x1_2)

  val fst  = ForCompNotEmpty(2).map(i => i * 10)
  val fst2 = ForCompNotEmpty("1")
  val x = for {
    x <- fst // flatMap
    if x > 2 // withFilter
    x2 <- fst2 // map
  } yield x + x2 // "(2*2)" + "1" = "41" //String!!!
  println(x)
}

object LecTry extends App{
  val t1 = Try(throw new Exception("boom")) //Success anyway //Try[Nothing]
  println("t is - " + t1)
  println("t.isSuccess - " +t1.isSuccess)
  println("t.isFailure - " + t1.isFailure)

  val t2 = Try(1) //Success anyway
  println("t is - " + t2)
  println("t.isSuccess - " +t2.isSuccess)
  println("t.isFailure - " + t2.isFailure)

  def mayBeThrow(n:Int): Try[Int] = Try {
    if (n==1) 1
    else throw new Exception("boom")
  }
  def mayBeThrowold(): Try[Int] = Try {
    //if (n==1) 1
    1
    //throw new Exception("boom")
  }

  val x = mayBeThrow(1)
  val x2 = mayBeThrow(2)
  println("x variable = " + x)
  println("x2 variable = " + x2)
  x match {
    case Success(value) => println("Success! Value is " + value)
    case Failure(exception) => println("Failure! Exception is " + exception)
  }
  x2 match {
    case Success(value) => println("Success! Value is " + value)
    case Failure(exception) => println("Failure! Exception is " + exception)
  }
println("///////p///////////")
  val t3 = mayBeThrow(2)
  //val x0 = x.get           // bad, before check _.isSuccess
  val t4 = t3.getOrElse(1) // Failure => get default from getOrElse || get value from Success(value)
  println(t4)
  // Success[A] map => Success[B]
  // Failure[A] map => Failure[B]
  val t5: Try[String] = t3.map { i =>
        println(i)  //wont print on fail
    s"$i"
  }.recover { //recover is literally is
    case _ => "Some kind of error"
  }
  println(t5) // recover did stuff so its return Success with default value

  println("///////<p>//////")
  val x3: Try[Try[Int]] = x.map(_ => mayBeThrow(1))
  val x4: Try[Int]      = x.flatMap(_ => mayBeThrow(1))
  val x5: Try[Int]      = x.map(_ => mayBeThrow(1)).flatten //FLAAATTEN
  println(s"$x3\n$x4 - flatMap\n$x5 - FLAAATTEN")

  val x6 = x.filter(_ > 1)
  x.foreach(_ => println("foreach"))

  val x8 = for {
    x  <- mayBeThrow(1)
    x2 <- mayBeThrow(1)
  } yield x + x2 //name? ~= return x+x2
  println(x8)
}

object LecEither extends App{
  def checkurl(u:String): Either[String, Int] = {
    if (u.isEmpty) Left("Empty url!!!")
    else Right(1)
  }

  println("Result: " + checkurl("https://github.com/M0r71m3r/scalaCourses/tree/master/src/main/scala/lec2425/lecture"))
  println("Result: " + checkurl(""))

  val x1 = checkurl("") match {
    case Right(value) => println("RIGHT!!!"); 1; //print then return with ; between or {}
    case Left(value) => {
      println("Something wrong..")
      "error"
    }
  }
  //x1
  println("x is " + x1)
  println("<p>")
  val r1 = checkurl("")
  val b1 = r1.isLeft
  val b2 = r1.isRight
  println(s"result = $r1\n$b1\n $b2")
  println("</p>")

  val x3_bad: Either[String, Either[String, Int]] = checkurl("1").map { i =>
    checkurl("2").map { i2 =>
      i + i2
    }
  }

  val x3: Either[String, Int] = checkurl("1").map { i =>
    checkurl("2").map { i2 =>
      i + i2
    }
  }.flatten

  val x4: Either[String, Int] = checkurl("1").flatMap { i =>
    checkurl("2").map { i2 =>
      i + i2
    }
  }
  println(x3_bad)
  println(x3)
  println(x4)
  println("1111111111111111111111111111111111111111111111111111")
  println(checkurl("2").toOption) // Some(42)
  println(checkurl("").toOption) // None

}

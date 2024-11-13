package homework.kvp.DZ6

import scala.Console.{BLACK, RED, RED_B, RESET, YELLOW_B}
import scala.util.{Failure, Success, Try}

object Task6 extends App{
  def letseither(a:Double, b: Double): Either[String, Double] = {
    if (b==0) Left(s"${YELLOW_B + BLACK}divide by zero$RESET")  //Can be Right(Infinite)
    else Right(a/b)
  }

  def letstry(a:Double, b: Double): Try[Double] = Try {
    if (b==0) throw new ArithmeticException("divide by 0!!!!!!")
    else a/b
  }

  def letsoption(a:Double, b: Double): Option[Double] = {
    if (b==0) Option.empty
    else Option(a/b)
  }

  println(letseither(5,2))
  println(letseither(5,0))

  println(letstry(5,2))
  println(letstry(5,0))

  println(letsoption(5,2))
  println(letsoption(5,0))

  val problem = letstry(1,0)
  println(problem.getOrElse("Some kind of error")) //get - throw exception now; getOrElse - 0 (Failure - false)
  problem match {
    case Success(value) => println("Success")
    case Failure(ex) => println("Failed! reason - " + ex)
  }
  val problem2 = letstry(1,1)
  println(problem2.get) //get number
  problem2 match {
    case Success(value) => println("Success! Value - " + value)
    case Failure(ex) => println("Failed! reason - " + ex)
  }

}

object Test_some {
  def main(args: Array[String]): Unit = {
    val str:String = null

    val x1:Option[String] = Option(1).map( _ => str).map(x => x*2) //npe

  }
}

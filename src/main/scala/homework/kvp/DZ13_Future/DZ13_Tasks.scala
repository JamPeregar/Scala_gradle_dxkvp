package homework.kvp.DZ13_Future

import java.util.concurrent.Executors
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object DZ13_Tasks {
  /*
  * Напишите функцию, которая принимает список строк
  * и возвращает список Future, каждый из которых содержит длину соответствующей строки

  * Напишите функцию, которая принимает список Future[Int]
  *  и возвращает Future[Int], представляющий сумму всех значений в этих Future

  * Напишите функцию, которая принимает список Future[String]
  * и возвращает Future[List[String]], содержащий все значения из исходного списка,
  * преобразованные в верхний регистр

  * Напишите функцию, которая асинхронно вызывает две функции,
  * возвращающие Future[Int], и выводит на экран результат их суммы
  *
  * */

}

object ScalaFuture1 extends App{
  //тут всё main :/
  def task1(sl:List[String]): List[Future[Int]] = {
    sl.map(s=>Future(s.length))
  }

  def task2(sl:List[Future[Int]]): Future[Int] = {
    println("def thread: " + Thread.currentThread().getName)
    val x:Future[List[Int]] = Future.sequence(sl)
    val res = Await.result(x, Duration.Inf)
    Future {res.sum}

  }

  //1
  val p = Executors.newFixedThreadPool(5)
  implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(p) //fromExecutorService

  val strl = List("Java", "Scala", "Python", "Ruby", "JavaScript")
  val fsl = task1(strl) // task result

  //-----------------TESTING------------//, всё делает main :/
  println(fsl)
  val x:Future[List[Int]] = Future.sequence(fsl)
  val x2:Future[List[Int]] = Future.sequence(fsl)
  val x3:Future[List[Int]] = Future.sequence(fsl)

  x.onComplete {
    case Success(value) => println("OK! value is " + value + Thread.currentThread().getName)
    case Failure(e) => println("ERR - " + e.getMessage)
  }

  x2.onComplete {
    case Success(value) => println("OK! value2 is " + value + Thread.currentThread().getName)
    case Failure(e) => println("ERR - " + e.getMessage)
  }

  x3.onComplete {
    case Success(value) => println("OK! value3 is " + value + Thread.currentThread().getName)
    case Failure(e) => println("ERR - " + e.getMessage)
  }

  println(x)
  val res = Await.result(x, Duration.Inf)
  Await.result(x, Duration.Inf)
  Await.result(x2, Duration.Inf)
  Await.result(x3, Duration.Inf)
  println("Result: " + res)

  //--------------2---------------//
  println("//2")
  println(task2(fsl))
  //testing
  val fut1 = task2(fsl)
  val fut2 = task2(fsl)
  val fut3 = task2(fsl)
  fut1.onComplete {
    case Success(value) => println("OK! value is " + value + Thread.currentThread().getName); value
    case Failure(e) => println("ERR - " + e.getMessage)
  }
  fut2.onComplete {
    case Success(value) => println("OK! value is " + value + Thread.currentThread().getName); value
    case Failure(e) => println("ERR - " + e.getMessage)
  }
  fut3.onComplete {
    case Success(value) => println("OK! value is " + value + Thread.currentThread().getName); value
    case Failure(e) => println("ERR - " + e.getMessage)
  }
  Await.result(fut1, Duration.Inf)
  Await.result(fut2, Duration.Inf)
  Await.result(fut3, Duration.Inf)


  p.shutdown()
}

object ScalaFuture2 extends App{

  def task3(sl:List[Future[String]]): Future[List[String]] = {
    val x:Future[List[String]] = Future.sequence(sl)
    val res = Await.result(x, Duration.Inf)
    Future {res.map(_.toUpperCase)}
  }

  //1
  val p = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(p) //fromExecutorService

  val strl = List("Java", "Scala", "Python", "Ruby", "JavaScript")
  val fut3 = task3(strl.map(Future(_))) //result of task
  //test
  fut3.onComplete {
    case Success(value) => println("OK! value is " + value + Thread.currentThread().getName); value
    case Failure(e) => println("ERR - " + e.getMessage)
  }

  Await.result(fut3,Duration.Inf)

  p.shutdown()
}


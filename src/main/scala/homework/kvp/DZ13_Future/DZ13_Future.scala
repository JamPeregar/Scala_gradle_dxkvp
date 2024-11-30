package homework.kvp.DZ13_Future


import java.util.concurrent.{Executors, TimeUnit}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.{Failure, Random, Success}

object DZ13_Future extends App{
  //ассинхронность
  val p = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(p) //fromExecutorService
  val f = Future{42} //+(ec)
  val f2 = Future{42} //+(ec)
  val f3 = Future{42/2} //+(ec)
  val f4 = Future{
    for (i<-0 to 999) {
      //some logic
    }
    5
  } //+(ec)

  f.onComplete {
    case Success(value) => println("OK! value is " + value)
    case Failure(e) => println("ERR - " + e.getMessage)
  }

  f2.onComplete {
    case Success(value) => println("OK! value2 is " + value)
    case Failure(e) => println("ERR - " + e.getMessage)
  }

  f3.onComplete {
    case Success(value) => println("OK! value3 is " + value)
    case Failure(e) => println("ERR - " + e.getMessage)
  }
  f4.onComplete {
    case Success(value) => println("OK! value4 is " + value)
    case Failure(e) => println("ERR - " + e.getMessage)
  }

  Await.result(f,Duration.Inf) // Duration.apply(5,TimeUnit.SECONDS) - ждать (выполнение) 5 сек вместо Duration.Inf (бесконечно) или вылет (failure?)
  Await.result(f2,Duration.Inf)
  Await.result(f3,Duration.Inf)
  Await.result(f4,Duration.Inf)
  p.shutdown() //Прервать пул
}

//Подтему пропустил

object DZ13_FutureSeq extends App{
  val p = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(p) //fromExecutorService

  val ids:List[Future[Int]] = (1 to 100).toList.map(Future(_))

  val x:Future[List[Int]] = Future.sequence(ids)

  val res = Await.result(x, Duration.Inf)
  println("Result: " + res)
  p.shutdown()
}

object DZ13_FutureTraverseV1 extends App{
  val p = Executors.newFixedThreadPool(3)
  implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(p) //fromExecutorService

  val ids = (1 to 100).toList

  //освобождаются по завершению задачи
  val x = Future.traverse(ids) {
    id=> Future {
      val sleep = Random.nextInt(2000)
      println(s"id = ${id} - ${Thread.currentThread().getName}")
      id*2
    }
  }

  val res = Await.result(x,Duration.Inf)
  println(s"res = ${res}")
  p.shutdown()
}

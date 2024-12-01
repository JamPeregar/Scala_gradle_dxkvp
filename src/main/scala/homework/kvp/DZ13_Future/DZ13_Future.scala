package homework.kvp.DZ13_Future


import java.util.concurrent.{Executors, TimeUnit}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.{Failure, Random, Success, Try}

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
    case Success(value) => println("OK! value is " + value + Thread.currentThread().getName)
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
//surround try-finally? But it wont avoid ex

  Await.result(f,Duration.Inf) // Duration.apply(5,TimeUnit.SECONDS) - ждать (выполнение) 5 сек вместо Duration.Inf (бесконечно) или вылет (failure?)
  Await.result(f2,Duration.Inf)
  Await.result(f3,Duration.Inf)
  Await.result(f4,Duration.Inf)
  //p.shutdown() //Прервать пул

  //Другие
  val f8  = Future { 42 / 0 }
  val f7 = Future.successful("1")
  val f5 = Future.failed(new Exception("MYERROR2"))
  f7.onComplete {
    case Success(value) => println("Future.successful ! value is " + value)
    case Failure(e) => println("ERR - " + e.getMessage)
  }

  Await.result(f7, Duration.Inf)
  //Await.result(f5, Duration.Inf) //ex
  p.shutdown()
}

object WithoutFutureCoffee2 extends App {
  //can be replaced with Try[]
  val pool                                  = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(pool)

  def eval(s: String): String = {
    val r = Random.nextInt(5000)
    Thread.sleep(r)
    val cm = System.currentTimeMillis() / 1000
    println(s"$s $r $cm")
    s
  }

  def grind(beans: String)(implicit executor: ExecutionContext): Future[String]                      = Future { println("grind"); eval(beans) }
  def heatWater(water: String)(implicit executor: ExecutionContext): Future[String]                  = Future { println("hot"); eval(water) }
  def forthMilk(milk: String)(implicit executor: ExecutionContext): Future[String]                   = Future { println("forthMilk"); eval(milk) }
  def doEspresso(coffee: String, water: String)(implicit executor: ExecutionContext): Future[String] = Future { println("espresso"); eval(coffee + " " + water) }
  def combine(espresso: String, milk: String)(implicit executor: ExecutionContext): Future[String]   = Future { println("cappuccino"); eval(espresso + " " + milk) }

  //just loop of functions sending params to next function
  //uses implicits above (really not so much code visible)
  val x = for {
    g  <- grind("arabica")
    w  <- heatWater("0")
    m  <- forthMilk("milk")
    es <- doEspresso(g, w)
    c  <- combine(es, m)
  } yield c
  pool.shutdown()
}

object MapMe2 extends App {
  val pool                                  = Executors.newFixedThreadPool(4)
  implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(pool)

  val f = Future {
    println("PreFuture")
    42
  }.map { _ =>
    Future {
      Thread.sleep(5000)
      println("Post-Future")
      "1"
    }
  }
  // await на внешней future не дает await на внутренней future
  // используйте flatMap
  Await.result(f, Duration.Inf)
  println("After await")
  pool.shutdown()
}

//Lists
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

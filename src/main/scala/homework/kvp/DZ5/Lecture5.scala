package homework.kvp.DZ5
import scala.annotation.tailrec

object Lecture5 {
  def fibo_tail(n: Int): Int = {
    @tailrec
    def fibo_tail(n: Int, p: Int, c: Int): Int = {
      if (n == 0) {
        //code
        //code
        return -1;
        //code
        //code
      } // undefined
      if (n == 1) return p;
      fibo_tail(n - 1, c, p + c)
    }
    fibo_tail(n, 0, 1)
  }

  //good
  def fibo_tail1(n: Int): Int = {
    @tailrec
    def fibo_tail(n: Int, p: Int, c: Int): Int = {
      if (n == 0) -1
      else if (n == 1) p
      else fibo_tail(n - 1, c, p + c)
    }
    fibo_tail(n, 0, 1)
  }

  println(fibo_tail(5))
  println(fibo_tail1(5))
}

object ScalaOption extends App {
  // java: Optional<E>
  // Option(any) => Some(any) || None

  /**
   * Option(1)
   */
  val o1:Option[Int] = Option(1)

  val o2: Option[Int]     = None
  val o3: Option[Int]     = Some(1)
  val o4: Option[Nothing] = Option.empty
  val o4_1: Option[Int]   = Option.empty //[Int]
  val o4_2: Option[Int]   = Option.empty[Int] //same

  println(s"$o1: \nisEmpty: ${o1.isEmpty} \nisDefined: ${o1.isDefined}\nnonEmpty: ${o1.nonEmpty}")
  println(s"$o2: \nisEmpty: ${o2.isEmpty} \nisDefined: ${o2.isDefined}\nnonEmpty: ${o2.nonEmpty}")
  println(s"$o3: \nisEmpty: ${o3.isEmpty} \nisDefined: ${o3.isDefined}\nnonEmpty: ${o3.nonEmpty}")
  println(s"$o4: \nisEmpty: ${o4.isEmpty} \nisDefined: ${o4.isDefined}\nnonEmpty: ${o4.nonEmpty}")
  println(s"$o4_1: \nisEmpty: ${o4_1.isEmpty} \nisDefined: ${o4_1.isDefined}\nnonEmpty: ${o4_1.nonEmpty}")
  println(s"$o4_2: \nisEmpty: ${o4_2.isEmpty} \nisDefined: ${o4_2.isDefined}\nnonEmpty: ${o4_2.nonEmpty}")

  val f1: Option[Int] = o1.filter(i => i > 0) // Some(a) => if f in filter return true

  val f2_1: Option[Int] = o1.filter { i =>
    //    println("bbbbbbbbbbbbbbb!")
    !(i > 0)
  } // Some(a) => if f in filterNot return false
  val f2_2: Option[Int] = o2.filterNot { i =>
    //    println("aaaaaaaaaaaaaaa!")
    i > 0
  } // Some(a) => if f in filterNot return false
  //  println(f2_1)
  //  println(f2_2)
  val c1: Boolean = o1.contains(1) // Some(a) => true if a == 1
  // eq o1.exists(_ > 1)
  // !isEmpty && p(this.get)
  val e: Boolean = o1.exists(i => i > 1) // true if !isEmpty and f in exists return true
  // isEmpty || p(this.get)
  val forall: Boolean = o1.forall(i => i > 1) // true if isEmpty or f in forall return true

  val o1Value = o1.get
  // val o2Value = o2.get // java.util.NoSuchElementException: None.get
  val o1Value_1 = o1.getOrElse {
    println(1111)
    0
  }
  lazy val o2Value_1 = o2.getOrElse {
    println(2222)
    -1
  }
  println(o1Value_1)
  println(o2Value_1)
}

object ScalaOptionProd extends App {
  def loadById(id: Long): String = {
    println("load 1...")
    "id 1"
  }
  def loadByAnotherId(anotherId: Long): String = {
    println("load 2...")
    null
  }

  def load(id: Long)(anotherId: Long): Option[String] =
    //Option(loadById(id)).orElse(Option(loadByAnotherId(anotherId)))
    Option(loadByAnotherId(anotherId)).orElse(Option(loadById(id)))

  val x = load(1)(2)
  println(x)
}

object ScalaOptionSomeSome extends App {
  def loadById(id: Long): Option[String] = {
    Option("111")
  }

  val x:Option[Option[String]] = Option(loadById(2L))
  println(x.flatten)
}

object ScalaOptionMap extends App {
  val o1 = Option("o")
  val o2 = Option(0) // not print if None (.empty)

  val x1 = o1.map(_ + "a").map(_ + "a").map(_ + "a").map(_ + "a").map { a =>
    println(s"a = $a")
    a + "b"
  }
  val x2 = o2.map(_ + 1).map(_ + 1).map(_ + 1).map(_ + 1).map { i =>
    println(s"j = $i")
    i + 1
  }
  println(x1)
  println(x2) // None
}

///
object ScalaOptionMapSomeSome extends App {
  val o1 = Option(1)
  val x1 = o1.map { i =>
    println(s"i = $i")
    Option(i + 1)
  }.flatten
  val x2 = o1.flatMap { i =>
    println(s"i = $i")
    Option(i + 1)
  }

  // [] map ([[]]) .flatten => []
  // [] flatMap ([[]]) => []
  val a: Option[Int] => Int = _.getOrElse(0)
  println(x1)
  println(a(x1))

  // doesn`t work, fix me pls
    println("////<p>///")
  val op1: Option[Int] = Option.empty[Int]
  val a1: Option[Int] => Int  = n => n.getOrElse(0) //Option[Option[]] -- flatten --> Option[] -- getOrElse --> Int/String...
  // val x_hack = Option(1).flatten(a1)
    println(op1)
  println(a1)
}

object ScalaOptionSomeNull extends App {
  case class Person(id: Long, name: Option[String])
  val p = Person(1L, null)

  val x = Option(p).map(_.name)
  val x2 = Option(p).map(_.id)
  println(x2 + "\n" + x) // Some(1) Some(null)

  // for avoid Some(null)
  val x1 = Option(p).flatMap(p => Option(p.name))
  println(x1) // None
  //null --> None
  println(Some(null)) // Some(null)
  println(Option(null)) // None
}

object OptionForeach extends App {
  val o1 = Option(1)
  val x: Unit = o1.foreach { i =>
    println(i)
    println(i)
    println(i)
  }
}

object OptionMatch extends App {
  val o1: Option[Int] = Option.empty[Int]
  val o2: Option[String] = Option("2ed")
  o1 match {
    case Some(value) =>
      println(value)
    case None =>
      println("none!")
  }
  o2 match {
    case Some(value) =>
      println(value)
    case None =>
      println("none!")
  }
}


//from Collections
object OptionWhen extends App {
  // use where: if (con) Some(1) else None
  val o1 = Option.when(true)(1)
  val o2 = Option.when(1 < 0)(1)
  println(o1)
  println(o2)
}

object OptionIterableOnceMethods extends App {
  val o1 = Option(1)
  // todo in lec: list|seq|set|map
  Option.option2Iterable(o1).head
  o1.headOption //first elem (Option -> Some(n))
  o1.last //last "naked" element
  o1.lastOption //last Some() element
  o1.tail // List()
  o1.take(1) //first n elements
  o1.find(_ > 1)  //return option value if exist or None
  o1.collect(_ > 1) //return Some() if element in case function
  o1.count(_ > 1) //
  o1.sum
  o1.drop(1) //remove first n elements
  o1.dropRight(1)
  o1.dropWhile(_ >1)
  o1.fold(1)(_ + 1)
  // o1./:()()
  o1.foldLeft("1")((x,y) => s"$x $y ")
  // o1.:\()()
  o1.foldRight("1")((x,y) => s"x $y ")
  println(o1.last)
  /// println(o1.fold(0)((x, y) => x + y)) // 1 element cant in binary op
  val list = List(1, 2, 3, 4, 5) // sum=15
  val sum = list.fold(3)((x, y) => x + y)  //start value then function
  println(sum) //15 + 3


  def workWithIterable[A](l: A with Iterable[Int]): Int = l.head
  def workWithIterable1(l: Iterable[Int]): Int = l.head

  workWithIterable(o1)
  workWithIterable1(o1)
}


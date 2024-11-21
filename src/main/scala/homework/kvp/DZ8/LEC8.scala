package homework.kvp.DZ8



object LEC8{

  def main(args: Array[String]): Unit = {
    val s1:Set[Int] = Set(1,2,3,4,4) //removes dubs //Also maybe immutable
    println(s1)

    //map
    val m:Map[Int,String] = Map((1,"a"), (2,"b"),(1,"a")) //Immutable
    println(m)
    val m_mapped = m map {
      case(k,v) =>
        if (k==1) {
          val v2 = "HACK"
          (k,v2)
        }
        else
          k-> v
    }
    println(m_mapped)
    val m2 = m.updated(2,"neew")
    println("Updated - " + m2)
    println(m_mapped ++ m2) //aka .concat

    //tricks

    import scala.+:
    val lst:List[Int] = List(1)
    println(lst :+ 2) //append from end
    println(lst.prepended(2)) // to start
    println(2 +: lst) // to start
  }

}
//lec ~ copy
object ScalaSet extends App {

  val s1 = Set(1, 2, 3)
  val s2 = Set(1, 1, 2, 2, 3)
  val s3 = Set.empty[Int]
  val s22 = Set("a","a","b","c")

  case class SimpleInt(i: Int) {
    override def toString: String = s"SimpleInt($i)"
  }

  val s4 = Set(SimpleInt(1), SimpleInt(2), SimpleInt(2), SimpleInt(3))
  println(s4.mkString(", ")) // without case or eq + hashCode => has dup

  val s5 = s1 - 3 //removes 3
  println(s5 + " - s5")
  println(s2 + " - s2")
  println(s5 ++ s2 + " - s5 ++ s2")
  println(s22 + " - s22")
  println(s2 ++ s22 + " - s2 ++ s22") // HashSet + last string added in it
  val s6 = s1 - 5
  //  println(s6)
  val s7 = s22 - "a"
  // -- eq removedAll => see in ScalaCollectionTricksAliases
  val s8 = s1.removedAll(s5)
  println("-- aka removeall")
    println(s7)
    println(s8)
  printf("\n%s is value", s22)

  val s9 = s1 + 4
  println(s9)

  val s9_mapped = s9.map {
    case i if i == 2 => i * 2
    case i           => i
  }
  println(s9_mapped)
}

object ScalaMap extends App {
  val map1 = Map((1, "AAA"), (2, "BBB"), (3, "CCC"))
  val map2 = Map(1 -> "AAA", 2 -> "BBB", 3 -> "CCC")
  val mqp3 = Map.empty[Int, String]

  map1.keySet.foreach(println)
  map1.values.foreach(println)

  map1.map { x =>
    val newK = x._1 + 1
    val newV = x._2 ++ "!!!"
    newK -> newV
  }
  val x1 = map1.map { case (personId, personPhone) =>
    val newK = personId + 1
    val newV = personPhone ++ "!!!"
    (newK, newV)
  }

  val key = 1

  val getByKey: Option[String] = map1.get(key)
  //  println(getByKey)
  val getByKey1: Option[String] = map1.get(-1)
  //  println(getByKey1)

  val getByKey_1: String = map1(key)
  // java.util.NoSuchElementException: key not found: -1
  //val getByKey_2: String = map1(-1)

  // see in PartialFunction lec
  //  val pf: PartialFunction[Int, Int] = { case x => x * x }
  //  val map_orElseCheck = Map((1, true), (2, true), (3, true))
  //  val x = map_orElseCheck
  //    .apply(1) // java.util.NoSuchElementException: key not found: -1
  //    .orElse { pf }
  //  println(x(3)) // 9
  // "".orElse() // what? // cause see implicit lec

  val getByKey_3: String = map1.getOrElse(-1, "not found")
  //  println(getByKey_3)
  val getByKey_4: String = map1.applyOrElse(-1, (i: Int) => "not found")
  //  println(getByKey_4)

  val a = Map(1 -> "one")
  val b = a + (2 -> "two")
  println(a)
  println(b)

  val a_1 = Map(
    1 -> "one",
    2 -> "one",
    3 -> "one",
    4 -> "one",
  )
  println("-----------")
  val d = a_1 - 1 - 2
  println(d)
  val d_1 = a_1 -- List(1, 2)
  println(d_1)
  println("-----------")
  val up = a_1.updated(3, "!!!")
  println(up)
  val t    = 3 -> "!!!"
  val up_1 = a_1 + t
  println(up_1)
  val up_2 = a_1 ++ Map(t)
  println(up_2)
  val up_3 = a_1.concat(Map(t))
  println(up_3)
}

object ScalaCollectionTricksSomeNull extends App {
  val l = List(null, "sss", "aaa")
  println(l.headOption)                         // Some(null)
  println(l.headOption.flatMap(z => Option(z))) // None

  // Some(null) map ( _ * 2 ) => npe
  //  val x: Option[String] = Option("").map(x => null)
  //  println(x) // Some(null)

  // in db:
  val x: String = null
  // in dao load:
  //  val x1: Option[String] = Option(1).map(_ => x).map(x => x * 2) // npe
  val x1: Option[String] = Option(1).flatMap(id => Option(x)).map(x => x * 2)
  println(x1)
}

object ScalaCollectionTricksFlatten extends App {
  val l: List[Option[Int]] = List(Option(1), Option(2), None) // size = 3
  // List(Option(1)).flatten => List(List(1)).flatten => List(1)
  val l1: List[Int] = l.flatten // size = 2
  val a             = l1.sum / l1.size

  val l2: List[Option[Int]] = List(Option(1), Option(2), None)
  val l3: List[Int]         = l2.collect { case Some(i) => i }
  val a_1                   = l3.sum / l3.size
}

object ScalaCollectionTricksIterable extends App {
  // it.toList.map(_ % 2 == 0) => List(true, false, true, false)
  // it.map(_ % 2 == 0) => Set(true, false)
  def evenOrOdd(it: Iterable[Int]): Iterable[Boolean] = it.map(_ % 2 == 0)
  val ex                                              = Set(2, 1, 6, 3)
  val exSize                                          = evenOrOdd(ex)
  println(exSize)
  println(exSize.size)
}

object ScalaCollectionTricksAliases extends App {
  val l1 = List(1)
  val l2 = List(2)
  // eq l1.concat(l2)
  val l3 = l1 ++ l2

  // eq appended
  val l4 = l1 :+ 4
  println(l4)
  // eq prepended
  val l5 = 4 +: l1
  println(l5)

  // prepended
  // Node(0, *) -> Node(1, *) -> Node(2, *) -> Node(3, *) -> Node(4, Nil)
  // default
  // Node(1, *) -> Node(2, *) -> Node(3, *) -> Node(4, Nil)
  // eq appended
  // Node(1, *) -> Node(2, *) -> Node(3, *) -> Node(4, *) -> Node(5, Nil)

  val l6 = 1 :: Nil
  val l7 = List(1) ::: List(1, 2)
  println(l7)
}
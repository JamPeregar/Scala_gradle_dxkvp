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
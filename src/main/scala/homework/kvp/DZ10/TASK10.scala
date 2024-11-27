package homework.kvp.DZ10

object TASK10 extends App{

  def task12(n: Int): Boolean = {
    for (i<-1 to n) {
      if (i*i == n) return true
    }
    false
  }

  def task1: PartialFunction[Int,Int] = {
    case x if task12(x) => x
  }

  val l = List(1,2,3,4,5,56,6,9,-999,4)

  println(l.collect(task1))

}

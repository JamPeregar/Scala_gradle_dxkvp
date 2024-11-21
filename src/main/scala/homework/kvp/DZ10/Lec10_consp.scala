package homework.kvp.DZ10

import scala.collection.SeqView

object Lec10_consp extends App{
  //Mutable immutable Collections.
  val l1 = List(1,2,3,4,5).map(_+1).map(_*2) // every map is new list actually
  val immutl: List[Int] = l1.view.map(_+4).map(_+1).toList//lazy list ==.map(_+3)
  println(immutl)
  //List buffer - mutable list
  val mutablel = scala.collection.mutable.ListBuffer(1,2,3)
  println(mutablel)
  mutablel.prepend(10) //ad from left
  println(mutablel)
  //l1.prepended(12)

}

object Lec10_compositeF extends App {

  def addOne(i:Int): Int = i +1

  def prodTwo(i:Int): Int = i*2

  val i = 1

  val mi1 = prodTwo(addOne(i))
  println(mi1)
  val at = (addOne _).andThen(prodTwo)
  val mi1_1 = at(i)
  println(mi1_1)

  val mi2 = addOne(prodTwo(i))
  println(mi2)
  val comp = (addOne _).andThen(prodTwo) // _ - from comp(i)// i+1 then *2
  val mi2_1 = comp(i)
  println(mi2_1)
  //
  println(".compose")
  //как-тоо можно заменить на addOne.compose(prodTwo)...
  val comp2:Int => Int = (addOne _).compose(prodTwo) // i*2 then +1 !!!!!!!!!!!
  val mi2_2 = comp2(i)
  println(mi2_2)
}

object Lec10_PartialF extends App {
//case 1
  def squireRoot:PartialFunction[Double,Double] = {
    case x if x>=0 => Math.sqrt(x)
    //case _ => -1
  }

  //println(squireRoot(-1))
  val b = squireRoot.isDefinedAt(-1)
  println(b)
//case 2
  def applyDouble(d:Double) = {
    if (squireRoot.isDefinedAt(d)) squireRoot(d) else -1
  }
  println(applyDouble(-1))
  println(applyDouble(-4))
  println(applyDouble(4))

  //
  val l:List[Double] = List(-4,-3,-2,-1,0,1,2,3,4) //skip all with squireRoot.isDefinedAt(_)
  println(l.collect(squireRoot))
}

object Lec10_Partial_OrElse extends App {
  def minusOrZeroToPos:PartialFunction[Int,Int] = {
    case x if x<=0 => Math.abs(x)
  }

  def posToNeg:PartialFunction[Int,Int] = {
    case x if x>0 => x * -1
  }
//[Int,Int] - args and returns
  def swapSign:PartialFunction[Int,Int] = {
    posToNeg.orElse(minusOrZeroToPos) // not positive -> go to next function
  }

  println(swapSign(-1))
  println(swapSign(10))
  val a:Option[Int] = Option(1)
}



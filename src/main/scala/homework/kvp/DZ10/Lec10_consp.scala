package homework.kvp.DZ10

import scala.collection.SeqView
import scala.util.Try
import scala.util.Success
import scala.util.Failure

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

  def partialString:PartialFunction[String,String] = {
    case str if str.length>5 => str.take(5) // wont pass on exception, pass on "outcases" :/
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
  val l_str:List[String] = List("Java","Scalaaaa","Wiod") //skip all with squireRoot.isDefinedAt(_)
  println(l.collect(squireRoot))
  println(l_str.collect(partialString)) //take only length>5; returns 5 symbols
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


//----------------------------copy lec10-----------------------//


object MutableCollections extends App {
  val immutableList = List(1, 2, 3, 4)
  // view => lazy collection,
  // in the end .toList for strict collection and compute result
  // via view => map(_ + 1).map(_ + 1).map(_ + 1) => "map(_ + 3)"
  val immutableList1: List[Int] =
    immutableList.view.map(_ + 1).map(_ + 1).map(_ + 1).toList
  println(immutableList)  // 1, 2, 3, 4
  println(immutableList1) // 4, 5, 6, 7
  println("-------------------")
  val mutableList = scala.collection.mutable.ListBuffer(1, 2, 3, 4)
  println(mutableList)
  val mutableList1 = mutableList.prepend(1)
  println(mutableList1)
  println(mutableList)
}

object VarWithCollections extends App {
  var immutableList = List(1, 2, 3, 4)
  println(immutableList) // List(1, 2, 3, 4)
  // set copy of immutableList
  immutableList = immutableList.map(_ + 1)
  println(immutableList) // List(2, 3, 4, 5)
  println("----------")
  val mutableList = scala.collection.mutable.ListBuffer(1, 2, 3, 4)
  println(mutableList.map(_ + 1)) // ListBuffer(2, 3, 4, 5)
  println(mutableList)            // ListBuffer(1, 2, 3, 4)
}

object MutableCollectionsWithDef extends App {
  def mutateCol(
                 mc: scala.collection.mutable.ListBuffer[Int]
               ): scala.collection.mutable.ListBuffer[Int] = {
    mc.prepend(1)
  }
  val mutableList = scala.collection.mutable.ListBuffer(1, 2, 3, 4)
  println(mutableList)
  // more code
  println(mutateCol(mutableList))
  // more code
  println(mutableList)
}

object CompAndThenFunctions extends App {
  // def to val => addOne _
  val addOne: Int => Int  = _ + 1
  val prodTwo: Int => Int = _ * 2

  val i = 1

  val mi1 = prodTwo(addOne(i))
  println(mi1)
  // i = 1
  // addOne 1 andThen prodTwo
  // prodTwo(addOne(i))
  // addOne >> prodTwo >> prodTwo >> prodTwo
  val at: Int => Int = addOne.andThen(prodTwo).andThen(prodTwo).andThen(prodTwo)
  val mi1_1          = at(i)
  println(mi1_1)

  val mi2 = addOne(prodTwo(i))
  println(mi2)
  // i = 1
  // addOne compose prodTwo 1
  // addOne(prodTwo(i))
  val comp: Int => Int = addOne.compose(prodTwo)
  val mi2_1            = comp(i)
  println(mi2_1)

  val f1: Int => Int = (i: Int) => addOne(prodTwo(prodTwo(prodTwo(i))))
  // Int => MyObject
  // Int => MyObject1 => MyObject2 => MyObject
}

object PartialFunctions extends App {
  def sqrtRoot: PartialFunction[Double, Double] = {
    case x if x >= 0 => Math.sqrt(x)
  }
  def sqrtRootBad: PartialFunction[Double, Double] = {
    case x if x >= 0 => Math.sqrt(x)
    case _           => -1
  }

  println(Math.sqrt(-1))

  val b = sqrtRoot.isDefinedAt(-1)
  //  println(b)

  def applyDouble(d: Double) = {
    if (sqrtRoot.isDefinedAt(d)) sqrtRoot(d) else -1
  }

  //  println(applyDouble(-2))
  //  println(applyDouble(4))

  // println(sqrtRoot(-1)) // scala.MatchError

  val list = List(-1.0, 2.0, -3.0, 5.0, -6.0)
  println(list.collect(sqrtRoot))
  println(list.collect(sqrtRootBad))

}

object PartialFunctionsOrElse extends App {
  def negativeOrZeroToPos: PartialFunction[Int, Int] = {
    case x if x <= 0 => Math.abs(x)
  }
  def posToNeg: PartialFunction[Int, Int] = {
    case x if x > 0 => -1 * x
  }
  def swapSign: PartialFunction[Int, Int] = {
    posToNeg.orElse(negativeOrZeroToPos)
  }
  println(swapSign(-1))
  println(swapSign(2))

  def printIfPos: PartialFunction[Int, Unit] = {
    case x if x > 0 => println(s"x > 0 : $x")
  }
  def swapSignAndPrintIfPos: PartialFunction[Int, Unit] =
    swapSign.andThen(printIfPos)

  swapSignAndPrintIfPos(-4)
  swapSignAndPrintIfPos(4)
}



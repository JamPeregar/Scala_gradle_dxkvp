package homework.kvp.DZ7

import homework.kvp.DZ7.ScalaEnumByEnum.Colors
import homework.kvp.DZ7.ScalaEnumByEnum.Colors.RED
import homework.kvp.DZ7.ScalaEnumByEnumWithParams.ColorsV3

import scala.jdk.CollectionConverters.{IterableHasAsJava, IterableHasAsScala}


object ScalaEnumByEnum extends App {
  object Colors extends Enumeration {
    type Color = Value //abstract class Value extends Ordered[Value] with Serializable
    val RED, GREEN, BLUE, CYAN = Value
  }

  def isRed(c: Colors.Color) = c match {
    case Colors.RED =>
      true
    case _ =>
      false
    //    case lec2425.lecture.ScalaEnumByEnum.Colors.BLUE => ???
  }

  def isGreen(c:Colors.Color) = c match {
    case Colors.GREEN => true
    case _ => false
  }

  def isBlue(c:Colors.Color) = c match {
    case Colors.BLUE => true
    case _ => false
  }

  println(isRed(Colors.RED)) //true
  println(isGreen(Colors.GREEN)) //
  println(isGreen(Colors.BLUE)) //

  //  Colors.values.foreach(println) // see values method

  val g: Colors.Color = Colors.withName("GREEN")
  println(g)
  val b: Colors.Color = Colors.BLUE
  println(b + " is blue")
  // java.util.NoSuchElementException: No value found for 'BLACK'
  //val b = Colors.withName("BLACK")

  println(g.id)
  println(b.id)
}

object ScalaEnumByEnumWithOverride extends App {
  object ColorsV2 extends Enumeration {
    //type Color = Value //useless here
    val RED   = Value(1, "Red color")
    val GREEN = Value(3)
    val BLUE  = Value("Blue color")
  }
  val red   = ColorsV2.withName("Red color")
  val green = ColorsV2.withName("GREEN")
  val blue  = ColorsV2.withName("Blue color")
  val red2 = ColorsV2.RED //deafult RED. Have same id

  List(red, green, blue, red2).foreach { c =>
    println(s"$c, ${c.id}")
    //Red color, 1
    //GREEN, 3
    //Blue color, 4
  }
}

object ScalaEnumByEnumWithParams extends App {
  object ColorsV3 extends Enumeration {
    protected case class RGBColor(i: Int, r: Int = 0, g: Int = 0, b: Int = 0) extends super.Val(i) {
      def printRGBValue(): String = s"${this.getClass.getSimpleName} id $i rgb is $r;$g;$b"
    }

    type Color = RGBColor
    val RED    = RGBColor(1, r = 255)
    val BLACK  = RGBColor(2)
    val GREEN  = RGBColor(3, g = 255)
    val BLUE   = RGBColor(4, b = 255)
    val ORANGE = RED.copy(i = 5, g = 122, b = 122)

    val CYAN = RGBColor(6 ,g = 255, b = 255) //id 6 required or assertation error

    def valueToRGB: Value => RGBColor = _.asInstanceOf[RGBColor]
  }
  // copy without i => java.lang.AssertionError: assertion failed: Duplicate id: 1
  //  val orange: ColorsV3.Color = ColorsV3.RED.copy(g = 122, b = 122)
  //  orange.printRGBValue()

    val cyan1 = ColorsV3.CYAN
  cyan1.printRGBValue()
  println(s"$cyan1 data: ${cyan1.printRGBValue()}")

  // must have `toList` in methods seq
  //ColorsV3.values.toList.map(ColorsV3.valueToRGB).foreach { c =>
  //  c.printRGBValue()
  //}
  //ColorsV3.values foreach println

}

object ScalaEnumBySealed extends App {
  sealed trait ColorV4 { //[sealed]
    val r: Int
    val g: Int
    val b: Int
    def printRGBValue(): Unit = println(s"${this.getClass.getSimpleName} rgb is $r;$g;$b")
  }
  case class Red() extends ColorV4 {
    override val r: Int = 255
    override val g: Int = 0
    override val b: Int = 0
  }
  case class Green() extends ColorV4 {
    override val r: Int = 0
    override val g: Int = 255
    override val b: Int = 0
  }

  case class CYAN() extends ColorV4 {
    override val r: Int = 0
    override val g: Int = 255
    override val b: Int = 255
  }

  def isRed(c: ColorV4): Boolean = c match {
    case Red() => true
    case _ => false
    // case Green() => ???
    //compile check
  }

  val cyan: ColorV4 = CYAN()
  cyan.printRGBValue()
  println(s"is red: ${isRed(cyan)}")
}

object ScalaList extends App {
  import ScalaEnumByEnum.Colors.Color

  /**
   * List(1, 2, 3)
   */
  val l1: List[Color] = List(Colors.RED, Colors.BLUE, Colors.CYAN)
  val l2: List[Int] = 1 :: 2 :: 3 :: Nil
  val l3            = List.empty[Int]
  val l4            = Nil

  val f1              = (i: Int) => i + 2
  def f2(i: Int): Int = i * 2

  def fc(c:Color): String = {
    c.toString
  }

  def isRed(c:Color): String = c match {
    case RED => "Is REED"
    case _ => "unknown"
  }

  /**
   * (i: Int) => i + 2, i * 2
   */

  l1.isEmpty
  l1.nonEmpty
  l1.size

  l1.foreach(println)

  println("///Function with functions////////")
  val lf: List[Color => String] = List(fc, isRed)
  // javaList.stream().map(x -> x * x).toList
  val cyan: Colors.Color = Colors.CYAN
  val r = lf.map(f => f(cyan))
  println(r) //println

  val l5: List[List[Int]] = List(List(1), List(2), List(3))
  val l6: List[Int]       = l5.flatten

  //List(List(3, 4, 5), List(2, 4, 6))
  val l7 = lf.map(l1.map)
  // [f1, f2] map ( f => (1,2,3) map (i => f(i)) )
  println(l7)



  l1.exists(_ > Colors.RED) //== contains here
  l1.contains(1)
  l1.forall(_  == Colors.CYAN)
  l1.count(_  == Colors.BLUE)

  val str: String = l1.mkString("; ")

  val l12 = List(1,1,1)
  val f1_1: Option[Int] = l12.find(_ == 2)

  val l1_1: List[Int] = l12.filter(_ > 2)
  //Закладкаааааааа
  l12.sum
  l2.product

  l1.max // UnsupportedOperationException

  val a = l12.maxOption
  println("l12.maxOption is " + a)
  l1.min // UnsupportedOperationException
  l1.minOption

  l1.take(2)
  l1.takeRight(3) // NoSuchElementException
    l1.headOption
  l1.last // NoSuchElementException
  l1.lastOption

  l1.head
  l1.headOption
  l1.tail // UnsupportedOperationException
  l1.init // UnsupportedOperationException

  l1.drop(1)
  l1.dropRight(1)
  l12.dropWhile(_ < 2)

  println(l12.takeWhile(_ == 1))

  val (even, odd) = l12.partition(_ % 2 == 0)

  case class Colored(id: Long, color: Color)

  val c1 = Colored(1L, ScalaEnumByEnum.Colors.RED)
  val c2 = Colored(2L, ScalaEnumByEnum.Colors.GREEN)
  val c3 = Colored(3L, ScalaEnumByEnum.Colors.RED)
  val c4 = Colored(4L, ScalaEnumByEnum.Colors.BLUE)

  val lColored                         = List(c1, c2, c3, c4)
  val group: Map[Color, List[Colored]] = lColored.groupBy(_.color)
  println(group)

  lColored.maxBy(_.id) // UnsupportedOperationException
  lColored.maxByOption(_.id)
  lColored.minBy(_.id) // UnsupportedOperationException
  lColored.minByOption(_.id)
  println("-------------------------------")
  val lf2: List[Int => Int] = List(l12, l1_1)
  val r1 = lf2.foldLeft(0) { case (acc, f) =>
    f(acc)
  }
  println(r1)
  val r2 = lf2.foldRight(0) { case (f, acc) =>
    f(acc)
  }
  println(r2) //IS FOLD - for string only?

  val r3 = l12.fold(1)((acc, e) => acc * e) // eq product
  println(r3)
  val r4 = l12.foldLeft("1") { case (acc, e) =>
    acc * e
  }
  println(r4)

  val l1Set  = l1.toSet
  val l1Arry = l1.toArray

  val jColletion: java.lang.Iterable[Int]    = l12.asJava
  val jColletion1: java.util.Collection[Int] = l12.asJavaCollection
  val jTScala: Iterable[Int]            = jColletion.asScala

  l12 match {
    case Nil                      => println("empty")
    case head :: Nil              => println("uno element")
    case head :: tail if head > 1 => println("has tail and head > 1")
    case _ :: tail                => println("has tail")
    case _                        => println("not list") // runtime check on Iterable
  }
}

object ScalaListSpec  {

  def main(args: Array[String]): Unit = {
    var simpleListV1 = List(1, 2, 3)
    val simpleListV2 = List(3, 4, 5)

    val intersect = simpleListV1.intersect(simpleListV2)
    println(intersect) // List(3)
    val diff2 = simpleListV1.diff(simpleListV2)
    println(diff2) // List(1, 2)
    val concat = simpleListV1.concat(simpleListV2)
    println(concat) // List(1, 2, 3, 3, 4, 5)

    val x: List[String] = simpleListV1.collect { case 1 =>
      "1"
      //    case _ => "2"
    }
    println(x)
    // PartialFunction[Int, String] => не все инты в этой функции покрыты
    val x1: Option[String] = simpleListV1.collectFirst { case 1 =>
      "1"
      //    case _ => "2"
    }
    println(x1)

    val l: List[Option[Int]] = Nil
    val x_1: List[Int]       = l.collect { case Some(i) => i }
    val x_2: List[Int]       = l.flatMap(_.toList)
  }

}
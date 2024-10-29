package homework.kvp.DZ3

import scala.beans.BeanProperty

object Lecture_Classes extends App{

  val p1 = new Point(1, 1)
  //  println(p1.toString)
  val p2 = new Point(2, 4)
  //  println(p2.x)
  println(p2.distance(p1))
//class
  class Point(private val x:Int, val y:Int) {
    println(s"Point ($x:$y) created")

    def distance(other:Point): Double = {
      Math.sqrt(Math.pow((other.x-x),2) + Math.pow((other.y-y),2)) //формула расстояния
    }

    override def toString: String = s"($x, $y)"

  }

}

object ScalaClassesWithVar extends App {

  class Point(var x: Int, var y: Int) //declare in 1 line
  val p1 = new Point(1, 2)
  println(p1.x)
  p1.x = 3
  println(p1.x)
}

object ScalaClassesWithDefaultParam extends App {
  class Point(var x: Int = 0, var y: Int = 0) {
    var color: String = _

    def scaleTemp(str:String): Unit = color = str // some kind of setter
    override def toString: String = s"$x, $y"
  }
    val p1 = new Point()
    println(p1)
    val p4 = new Point(3, 4)
    println(p4)
  val p5 = new Point(3, 4)
  println(p5.color)
  p5.scaleTemp("Black")
  println(p5.color)
}

object ScalaClassesWithAuxiliaryConstructors extends App {
  //конструкторы почти как java
  class PointWithAuxiliaryConstructor(val x: Int, val y: Int) {
    //constructor
    def this() = this(0, 0)
    def this(newx: Int) = this(newx,0) // scnd throw ce, cause 2 eq sign
    def this(xOrY: Int, isX: Boolean) = this(if (isX) xOrY else 0, if (isX) 0 else xOrY)

    //other
    def showinfo(): Unit = {
      print(s"($x:$y)")
    }

  }
  val p0 = new PointWithAuxiliaryConstructor
  val p1 = new PointWithAuxiliaryConstructor(3,4)
  val p2 = new PointWithAuxiliaryConstructor(3)
  p0.showinfo()
  p1.showinfo()
  p2.showinfo()
}

object ScalaClassesWithAuxiliaryConstructorsV2 extends App {
  class X(x: Int)
  class Y(y: Int)
  class PointWithAuxiliaryConstructor(val x: X, val y: Y) {
    def this(x: X) = this(x, new Y(0))
    def this(y: Y) = this(new X(0), y)
  }

  // server call with params to return distance
  //  def toPoint(l: Double, p: Double) = ???
  //  toPoint(55, 37) // Москва
  //  toPoint(37, 55) // Иран
}

object ScalaClassesExtends extends App {
  class Animal(@BeanProperty var name: String, val color: String) {
    def printName(): Unit = {
      println(name)
    }
    def walk(): Unit = println("Animal walk")

    //BeanProperty создаёт геттеры и сеттеры автоматически

    def getColor(): String = {
      color
    }
  }

  val a1 = new Animal("1", "10")
  a1.setName("2")
  a1.getName
  a1.getColor // set not exists, cause val

  class Dog(name: String, age: Int) extends Animal(name, "black") {
    //cats will be black
    super.printName() //parent method

    def printInfo(): Unit = {
      println(s"$name, $age")
    }
    override def walk(): Unit = {
      println(s"$color dog walk: wuf!")
    }
  }
  class Cat(name: String, age: Int) extends Animal(name, "white") {
    //cats will be white
    super.printName() //parent constructor

    def printInfo(): Unit = {
      println(s"$name, $age")
    }
    override def walk(): Unit = {
      println(s"$color cat walk: mew!")
    }
  }
  val d1 = new Dog("Rex", 3)
  //  d1.printName
  //  d1.printInfo

  def printWalk(a: Animal): Unit = a.walk()

  val c1 = new Cat("Urza", 3)
  printWalk(d1)
  printWalk(c1)
}

object ScalaCaseClasses extends App {
  case class Point(x: Int, var y: Int)
  //  object Point {
  //    def apply(x: Int, y: Int): Point = new Point(1, 2)
  //  }
  class Point1(x: Int, var y: Int)

  //   extends via Point ce, case-to-case сделать незя
  case class p(i: Int, i1: Int) extends Point1(i, i1) {
    def this(i:Int) = this(i, 0)
    @BeanProperty var a:Int = 3

  }

  val p1 = Point(1, 2)
  val p2 = Point(4, 5)
  //p1.x = 3 => ce cause Reassignment to val
  //  p1.y = 3 // cause var y
  //  println(p1)
  //  val p2 = p1.copy(x = 2)
  //  println(p2)

  val x  = Point.unapply(null) //
  val x1 = Point.unapply(p1) // get tuple of class params??

  var pc = p(5,6) // 1/2 null is crash
  pc.a = 99 // wont affect on copy
  println(x)
  println(x1)

  println("////")
  println(p.unapply(pc)) //compare only constructor params?
  var p3 = pc.copy(3) //args unnecessary - copy args from instance by default constructor with optional args
  println(p.unapply(p3))
  //p3.a = 99
  println(p3.getA())
  //p.apply(pc)
}

object ScalaObjects extends App {
  System.out.println("1")
  LogAny.log(666)
  //  LogAny.x
  //  LogAny.y // is inaccessible from this place
  LogAny1.log(2)
}

object LogAny {
  println("->>>>>>>>>>>>>>>>>LogAny")
  val x                          = 1
  def log(a: Any): Unit          = println(s"INFO $x hahaha i wont use your data!")
}
object LogAny1 {
  println("->>>>>>>>>>>>>>>>>LogAny1")
  def log(a: Any): Unit = println(s"INFO $a")
}

object ScalaCompanionObjects extends App {
  // class, case class, trait, abstract class
  case class Point(x: Int, y: Int) {
    def toWest(): Unit = println("111")
  }
  // static methods in java class
  object Point {
    def createFromX(x: Int): Point = Point(x, 0)
    def createFromY(y: Int): Point = Point(0, y)
    def zeroPoint: Point           = Point(0, 0)

    def distance(p1: Point, p2: Point): Double = {
      /*import scala.math.sqrt
      val deltaX     = p1.x - p2.x
      val deltaXQuad = deltaX * deltaX

      val deltaY     = p1.y - p2.y
      val deltaYQuad = deltaY * deltaY
      */
      //Thats shorter
      Math.sqrt(Math.pow((p2.x-p1.x),2) + Math.pow((p2.y-p1.y),2)) //формула расстояния между точками
    }
  }
  val p1 = Point(1, 1)
  val p2 = Point(1, 2)

  println(Point.distance(p1, p2)) // Is like static method
  val p3    = Point.createFromX(1)
  val p4    = Point.createFromY(2)
  val pZero = Point.zeroPoint
  //  p2.copy()
  //  p2.toString()
  //  p2 == p1
  //  p1.hashCode()
}


object MoreCompanionObjects extends App {
  class Point()
  case class Point1(x:Int) extends Point
  case class Point2() extends Point

  object Point {
    //Just
    def toP(p:Point): Unit = {
      println(s"Its method from object. It wont see construct variables because its Point class -  ${p} object, not Point1/2")
    }
  }
  Point.toP(Point1(1))
  Point.toP(Point2())
}
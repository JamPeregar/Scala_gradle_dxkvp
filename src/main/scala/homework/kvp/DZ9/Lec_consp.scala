package homework.kvp.DZ9

object Lec_consp{
  //f высшего порядка. Haskel :(
  //func - val
  //method defined
  //1
  def hof1(i:Int,f:Int=>Int): Int = f(i)
  def quad(i:Int): Int = i*2 //can be replaced by any function with 1 Int arg

  //2 //they ~=
  def hof2_1(i:Int): Int => Int = (a:Int) => i*a
  def hof2(i:Int): Int => Int = a => {
    println(s"f: $i * a")
    i*a
  }
  //equal hof2_1
  def hof2_2(i:String): String=>String = {
    i ++ _
  }



  def main(args: Array[String]): Unit = {

    println(s"//1 hof1(5,quad) == quad(5)")
    println(hof1(5,quad))
    println(s"//2 def hof2(i:Int): Int => Int = (a:Int) => i*a")
    println(hof2(5))
    def res2: Int=>Int = hof2_1(2)
    val res3: Int => Int = hof2(2)
    println(res2(5))
    println(res3(5))
    println(res3(6))

    def res4: String=>String = hof2_2("Java")
    println(res4("-Scala"))
    println("//3")
    val l:List[Int] = List(1,2,3,4,5)
    println(l.map(res2)) //java : Object::quad if defined as static(?) ~~ Object::finc
    println(l.map(hof2(10))) //java : Object::quad if defined as static(?)


  }
}

object ScalaHigherOrderFunctionProd_my extends App {
  object ModifyListInt {
    // def add2(ints: List[Int])    = ints.map(i => i + 2)

    def addWord(ints: List[String]): List[String] = ints.map(_ + " - will written at our grave")

    def quad(ints: List[Int]): List[Int] =
      ints.map(i=>i*i)
  }
  object ModifyListIntV2 {
    private def applyFToList(ints: List[Int], f: Int => Int) = {
      ints.map(f)
    }
    def add2(ints: List[Int]): List[Int]    = applyFToList(ints, _ + 2)
    def quad(ints: List[Int]): List[Int]    = applyFToList(ints, i => i * i)
  }
  val l: List[String] = List("Joshn", "Scala", "Java", "Mess", "Five")
  val l2:List[Int] = for {
    str<-l
  } yield (str.length)
  println(ModifyListInt.addWord(l))
  println("--------------")
  println(ModifyListIntV2.add2(l2))
  println(l2) //orig length
}

object Carrirov extends App{
  //Замена одного из параметров функ на отложенный вариант
  def sum(x:Int)(y:Int): Int = {
    println("x= " + x + "; y= " + y)
    x+y
  }
  //Замена 1го из параметров функ на отложенный вариант
  def inc1: Int => Int = sum(1) //not CARRIROVANIE - DEFAULT VALUES MORE
  def inc2: Int => Int = sum(_)(1) //замена второго аргумента

  println("//1")
  println(sum(2)(3))
  println(inc1(5))
  println(inc2(6))
    //так же, но сплошными аргументами в скобках
  def sum1(x: Int, y: Int): Int = x + y

  def inc_3: Int => Int = sum1(1, _)
  println(inc_3(5))
}

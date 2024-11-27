package homework.kvp.DZ12

object Lec12_consp extends App{

  def smg[T](t:T): Unit = {
    println(s"$t - gen")
  }

  //UpperBorder
  case class ItemContainer[T<: Int](i: T) {
    def getT(): T = i
  }

  val c1 = ItemContainer(3)
  //add animal classes
  class Animal

  class Cat(name: String) extends Animal{

    def showName(): Unit = println(name)

    override def toString: String = s"Im a cat, $name"
  }

  class MyCat(name: String) extends Cat(name){

    override def toString: String = s"Im a home cat, $name"
  }
  //val c2 = ItemContainer("de") //ce type mismatch

  //LowerBorder

  trait List[+A] {
    //def prepend[B>:Cat](el:B): NonEmptyList[B] = NonEmptyList(el)
  }

  case class NonEmptyList[+A](head:A,tail:List[A]) extends List[A]

  object Nil extends List[Nothing]

  //val l1: NonEmptyList[Cat] = Nil.prepend(c1)
  //What....
  def testUp[B<:Cat](el:B): B = el
  def test1[B>:Cat](el:B): B = el


  val cat0 = new Animal()
  val cat1 = new Cat("Muska")
  val cat2 = new MyCat("Ishka")
  //upper border
  //println(testUp[Animal](cat0)) //error
  println(testUp[Cat](cat1))
  println(testUp[MyCat](cat2))
  //lower border
  println(test1[Animal](cat0))
  println(test1[Cat](cat1))
  //println(test1[MyCat](cat2)) //error
}

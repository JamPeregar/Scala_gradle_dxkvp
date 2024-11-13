package homework.kvp.DZ4


import scala.beans.BeanProperty
import io.AnsiColor._

trait NonSealedTrait
sealed trait SealedTrait

object Task4 extends App{

  var circle:Circle = Circle("Ivan", 2)
  var quad:Quad = Quad("Ben",5)
  var semi_quad:Semi_quad = Semi_quad("Emma", 5, 5)
  println(circle.square())
  println(quad.square())
  println(semi_quad.square())
  println("//")
  println(semi_quad.square())
  println(circle.square())
  println(quad.square())

  def any_square(f:FigureTrait): Unit = {
    f match {
      case Circle(name, r) => println(s"Circle $name square: ${f.square()}")
      case Quad(name, a) => println(s"$name square's square: ${f.square()}")
      case Semi_quad(name, a, b) => if (a==b) println(s"$name is square actually - both height and width = $a; square = ${f.square()}") else println(s"Semi_quad $name's square: ${f.square()}")
      case _ => println("Unknown")
    }
  }
  any_square(quad)
  any_square(semi_quad)
//---------------------
  sealed trait System_message {
    def getmsg(): String;
  }
  case class Error(name:String, msg:String) extends System_message {
    def getmsg(): String = {
      s"${RED}${name} - $msg${RESET}"
    }
  }
  case class Inform(name:String, msg:String) extends System_message {
    def getmsg(): String = {
      s"${YELLOW}$name - $msg${RESET}"
    }
  }
  case class Exception(name:String, msg:String) extends System_message {
    def getmsg(): String = {
      s"${RED_B + BLACK}$name - $msg${RESET}"
    }
  }

  object Logger {
    def logit(e: System_message): Unit = {
        e match {
          case Error(name, msg) => println(Error(name, msg).getmsg())
          case Inform(name, msg) => println(Inform(name, msg).getmsg())
          case Exception(name, msg) => println(Exception(name, msg).getmsg())
          //case _ => println("Unknown exception") //unnecessary ...
        }
    }
  }
  var err:Error = Error("Ivan Error", "Restart")
  var infa:Inform = Inform("Anna inform", "straighten your back")
  var ex:Exception = Exception("AriphmeticException", "Divide by zero")
  Logger.logit(err)
  Logger.logit(infa)
  Logger.logit(ex)


}

trait FigureTrait {
  def square(): Double;
}
case class Circle(@BeanProperty name:String, @BeanProperty r:Double) extends FigureTrait{

  override def square(): Double = {
    Math.pow(r,2)*3.14
  }
}

case class Semi_quad(@BeanProperty name:String, @BeanProperty l:Double, @BeanProperty w:Double) extends FigureTrait{

  override def square(): Double = {
    w*l
  }
}

case class Quad(@BeanProperty name:String, @BeanProperty a:Double) extends FigureTrait{

  override def square(): Double = {
    a*a
  }
}


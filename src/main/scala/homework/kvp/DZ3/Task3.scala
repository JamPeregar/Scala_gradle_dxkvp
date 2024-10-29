package homework.kvp.DZ3

import scala.beans.BeanProperty

object Task3 extends App{
  class Book(title: String = "title", author: String = "noname", date:String = "01.01.1997") {
    override def toString: String = {
      s"$title, $author, $date"
    }
  }

  object Book{
    def createBook(title:String, author:String, date:String): Book = {
      new Book(title,author,date)
    }
  }


//Task 2-4
  var book = new Book()
  println(book.toString)
  var b1 = Book.createBook("Sacred and Terrible Air", "Mazov", "02.12.2015")
  println(b1)

//Task 5
abstract class Figure(name:String) {
  def square(): Double;
}
  case class Circle(name:String, @BeanProperty r:Double) extends Figure(name){

    override def square(): Double = {
      Math.pow(r,2)*3.14
    }
  }

  case class Semi_quad(name:String, @BeanProperty l:Double, @BeanProperty w:Double) extends Figure(name){

    override def square(): Double = {
      w*l
    }
  }

  case class Quad(name:String, @BeanProperty a:Double) extends Figure(name){

    override def square(): Double = {
      a*a
    }
  }

  object Figure {
    def any_square(f:Figure): Double = {
      f.square()
    }
  }

  var circle:Circle = Circle("Circle", 2)
  var quad:Quad = Quad("Circle",5)
  var semi_quad:Semi_quad = Semi_quad("Circle", 2, 5)
  println(circle.square())
  println(quad.square())
  println(semi_quad.square())
  println("//Any square static function")
  println(Figure.any_square(semi_quad))
  println(Figure.any_square(circle))
  println(Figure.any_square(quad))

}


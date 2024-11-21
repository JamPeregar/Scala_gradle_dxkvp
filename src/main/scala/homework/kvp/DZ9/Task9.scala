package homework.kvp.DZ9

/*
* Напишите функцию, которая принимает список функций и значение,
* а затем возвращает список результатов применения каждой функции к этому значению.

* Напишите функцию высшего порядка, которая принимает список функций
* и возвращает новую функцию, которая применяет каждую функцию из списка к результату предыдущей функции.
*  Например, если дан список функций List((x: Int) => x + 1, ( x: Int) => x * 2),
* то результирующая функция должна выполнять следующее: f(x) = (x + 1) * 2

* Напишите функцию, которая принимает другую функцию двух аргументов
*  и возвращает каррированную версию этой функции
* ( т.е. функцию, которая принимает первый аргумент и возвращает функцию, принимающую второй аргумент)

* Напишите функцию, которая принимает три аргумента (String, Int, Double)
* и объединяет их в одну строку через пробел.
* Затем преобразуйте эту функцию в каррированную версию, которая принимает первый аргумент
* и возвращает функцию, принимающую оставшиеся два аргумента
* */

object Task9 {

  def task1_IntToIntF(lf:List[Int=>Int], i: Int): List[Int] = {
    for {
      f<-lf
    } yield {
      f(i)
    }
  }

  def task2(lf:List[Int=>Int]): Int=>Int = {
    (a: Int) => {
      var acc:Int = a
      for {
        f<-lf
      } {
        acc = f(acc)
      }
      acc
      //lf.map(f=>f(a))
    }
  }
  def task22(lf:List[Int=>Int]): Int=>Int = {
    (a: Int) => {
      lf.foldLeft(a)((acc, elem) => elem(acc)) //a - start arg; acc - collect value; elem - function from list
    }
  }

  def task3_hof(a:Int)(b:Int):Int = a + b //надо разделить аргументы ()

  def task4_d(s: String,i:Int,d:Double):String = s+i+"/"+d

  def main(args: Array[String]): Unit = {
    def f1(i:Int): Int = i+1
    def f2(i:Int): Int = i*2
    def f3(i:Int): Int = i*i

    val lf: List[Int=>Int] = List(f1, f2, f3)

    println(task1_IntToIntF(lf,10))
    val f4 = task22(lf)
    println(f4(2))

    //Task3
    def task3:Int=>Int = task3_hof(3) //принимает другую функцию двух аргументов и возвращает каррированную версию этой функции
    println(task3(2))

    //Task4
    def task4:String=>String = task4_d(_, 10, 10.0)

    println(task4("Java - "))
  }
}

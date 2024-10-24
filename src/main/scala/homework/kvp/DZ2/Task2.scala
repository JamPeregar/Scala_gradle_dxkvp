package homework.kvp.DZ2

import scala.annotation.tailrec

/*
2 лекция
Повторите (не копировать, а вдумчиво переписать) код с лекции, что-нибудь изменяя в плане параметров/реализаций
Напишите функцию для проверки числа на четность/нечетность, вывести в консоль 3 примера, вида "число - чет/нечет"
Напишите функцию, которая будет принимать на вход число и проверять, является ли оно положительным, отрицательным или нулём, вывести в консоль ответ.
Напишите 2 функции, которые с помощью цикла for выведет в консоль числа от 1 до 10 (включая и не включая 10).
Напишите функцию, которая с помощью цикла for выведет в консоль таблицу умножения на 5 до 10.
Написать функцию для вычисления чисел Фибоначчи(рекурсия и хвостовая рекурсия), вывести в консоль первые 10 чисел
 */

object Task2 extends App {
  //2
  chet(5)
  chet(2)
  chet(9)
  chet(100)
  //3
  someCheck3(0)
  someCheck3(10)
  someCheck3(-347678)
  //4
  forexclude(11)
  forinclude(11)

  //5
  multitable()
  //6
  fibo(10)
  println("\nTailrec")
  for (i<-1 to 10)println(fibo_tail(i))

//Functions
  def someCheck3(n:Int): Unit = {
    if (n == 0) {
      println(s"$n is 0")
    } else if (n>0) println(s"$n is +")
    else println(s"$n is -")
  }

  def chet(n:Int): Unit = {
    if (n % 2 == 0) {
      println(s"$n is Chet")
    } else println(s"$n is NE chet")
  }

  def forinclude(n:Int): Unit = {
      println(s"\ninclude $n")
    for(i<-0 to n) print(i)
    println
  }
  def forexclude(n:Int): Unit = {
    println(s"\nexclude $n")
    for(i<-0 until n) print(i)
    println
  }

  def multitable(): Unit = {
    for {
      acc <- 2 until 10 //можно ли добавить команду между циклами? for() {for() {}  printn}
      i <- 1 to 10
    } println(s"$acc * $i = ${acc*i}")
  }

  def fibo_tail(n: Int): Int = {
    @tailrec
    def fibo_tail(n: Int, p :Int, c: Int): Int ={
      if (n == 0) return -1; // undefined
      if (n == 1) return p;
      fibo_tail(n-1, c, p + c)
    }
    fibo_tail(n, 0, 1)
  }

  def fibo(num: Int,n1: BigInt = 0, n2: BigInt = 1): Unit = {
    //1 1 2 3 5 8 13 21
    if (num > 0) {
      println(s"${n1 + n2} ")
      fibo(num-1, n2, n1+n2)
    }

    //else fibo(n, n1 = n1+1, n2 = n2+1)
  }
//n = 10

}


object TourOfScala extends App {
  println("===================")
  class Adder(val number: Int) {
    def hoge(num: Int): Int = number + num
  }

  abstract class Monoid[A] {
    def add(x: A, y: A): A
    def unit: A
  }

  object ImplicitTest {
   implicit val stringMonoid: Monoid[String] = new Monoid[String] {
     def add(x: String, y: String): String = x concat y
     def unit: String = ""
   }
   implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
     def add(x: Int, y: Int): Int = x + y
     def unit: Int = 0
   }

    def sum[A](xs: List[A])(implicit m: Monoid[A]): A = {
      if (xs.isEmpty) m.unit
      else m.add(xs.head, sum(xs.tail))
    }

    println(sum(List(1, 2, 3)))
    println(sum(List("a", "b", "c")))
  }


  println("===================")
}
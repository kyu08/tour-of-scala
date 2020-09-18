object TourOfScala extends App {
  println("===================")
  val hoge = List(1,2,3,4,5,6,7,8,9,10)
  val res = hoge.foldLeft(0)((m, n) => m + n)
  println(res)

  println("===================")
}
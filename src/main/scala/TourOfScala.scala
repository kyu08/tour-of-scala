object TourOfScala extends App {
  println("===================")
  def hoge() =
    for(i <- 0 until 2; j <- 0 until 3) (i, j)
  val res = hoge()
  println(res)
  println("===================")
}
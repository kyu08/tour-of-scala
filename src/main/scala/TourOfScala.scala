object TourOfScala extends App {
  println("===================")
  case class Vec(x: Double, y: Double) {
    def add(that: Vec) = Vec(this.x + that.x, this.y + that.y)
  }

  val vector1 = Vec(1.0, 1.0)
  val vector2 = Vec(2.0, 2.0)

  val vector3 = vector1 add vector2
  println(vector3.x)  // 3.0
  println(vector3.y)  // 3.0
  println("===================")
}
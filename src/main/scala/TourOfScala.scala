object TourOfScala extends App {
  println("===================")
  case class Person(name: String, age: Int)
  val john = Person("john", 20)
  val taro = john.copy(age = 21)
  println(john)
  println(taro)
  println("===================")
}
object TourOfScala extends App {
  println("===================")
  abstract class AbsIterator {
    type T
    def hasNext(): Boolean
    def next(): T
  }

  class StringIterator(s: String) extends AbsIterator {
    type T = Char
    private var i = 0

    override def hasNext(): Boolean = i < s.length

    override def next(): Char = {
      val ch = s charAt i
      i += 1
      ch
    }
  }

  trait RichIterator extends AbsIterator {
    def foreach(f: T => Unit): Unit = while (hasNext) f(next())
  }

  class RichStringIter extends StringIterator("Scala") with RichIterator
  val richStringIter = new RichStringIter
  richStringIter foreach println

  println("===================")
}
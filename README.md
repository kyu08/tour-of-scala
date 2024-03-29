Tour of Scala(https://docs.scala-lang.org/ja/tour/tour-of-scala.html)

# 基本
## ブロック
`{}`で囲んだやつ
ブロックの最後に指揮の結果はブロック全体の結果になる
## 関数
```Scala
// 無名関数
(x: Int) => x + 1
// 名前をつけることもできる
val add = (x: Int) => x + 1
```

### 疑問
↓の形で関数を書いた時返り値の型はどう書けば...?
```Scala
val hoge = (x: Int) => x + 1
```
あと、↑の形だと複数パラメータもむりっぽい？
↓みたいなやつ
```Scala
val hoge = (x: Int)(y: Int) => x + y
```

## ケースクラス
値で比較される
ケースクラスは`new`キーワードなしでインスタンス化できる。
```Scala
val point = Point(1, 2)
val anotherPoint = Point(1, 2)

if (point == anotherPoint) {
    println("same")
} else {
    println("not same")
}
// "same"
```

## オブジェクト
オブジェクトはそれ自体が定義である単一のインスタンス。そのクラスのシングルトンと考えることもできる。
`object`キーワードをつかってオブジェクトを定義することができる。

```Scala
object IdFactory {
    private var counter = 0
    def create(): Int = {
        counter += 1
        counter
    }
}
```

名前を参照してオブジェクトにアクセスできる(静的メソッドみたいなかんじだよね)
```Scala
val newId: Int = IdFactory.create() // 1
```

## メインメソッド
メインメソッドがプログラムの始点になる。JVMは`main`と名付けられたメインメソッドが必要で、それは文字列の配列を1つ引数として受け取る。

# 統合された型
![type](https://docs.scala-lang.org/resources/images/tour/unified-types-diagram.svg)
## Scalaの型階層
`Any`は全ての型のスーパータイプ。`equals`, `hashCode`, `toString`などを定義している。
`AnyVal`は値型に相当する。9つの値型が存在し、それら`Double`、`Float`、`Long`、`Int`、`Short`、`Byte`、`Char`、`Unit`、`Boolean`は`null`非許容である。(!)

`AnyRef`は参照型を意味する。全ての値型でない型は参照型として定義される。(`List`,`Option`,`YourClass`)
ユーザ定義型は`AnyRef`のサブタイプになる。

![cast](https://docs.scala-lang.org/resources/images/tour/type-casting-diagram.svg)
精度が高い -> 低い の方向にはキャストできない。
`Nothing`はすべての型のサブタイプであり、ボトム型とも呼ばれる(TSの`never`?)
`Nothing`型を持つ値は存在しない。
`Null`はほとんどの場合、使われるべきではない。

# クラス
クラスは
- メソッド
- 値
- 変数
- オブジェクト
- 型
- トレイト
- クラス

を持ち、それらはまとめてメンバーと呼ばれる。(トレイト、クラスも持てるんだ)

## コンストラクター
デフォルト値を持たせた時に`y`だけ渡したいときはこんな感じ
```Scala
class Point(val x: Int = 0, val y: Int = 0)

val point = new Point(y=1)
``` 

## private member
デフォルトではメンバはパブリックになる。
プライマリコンストラクタ内の `val` や `var` がつかないパラメータはプライベートになる。
```Scala
class Point(x: Int)
```

# トレイト
抽象メソッドを実装するときは`override`って書く必要あり

# タプル
- immutable
- 各要素はそれぞれ型を持つ
- メソッドから複数の値を返す時に役に立つ
こう書く
```scala
val tuple = ("s", 10)
```

## 要素へのアクセス
```scala
println(tuple._1) // 添字は1スタート(!)
```

## タプルでのパターンマッチング
### 1. 
```scala
val (name, age) = tuple
```
### 2. 
```scala
val planets = List(("Mercury", 123), ("Venus", 122), ("Earth", 123123))
planets.foreach{
	case ("Earth", 123123) => println(s"our planet is $distance million hogehoge")
	case _ =>
}
```

### ちなみに
println で文字列の中で変数展開をしたい場合はこう。`string`だから`s`ってわけではないっぽい。(`Int`でも`s`だったので)
```scala
val vvv = "hogehogehoge"
val hoge = s"this is variable $vvv" // ${vvv} として同じように動作する。違いはわからん。
```

## タプルとケースクラス
この2つの使い分けで迷うかもしれないけど、ケースクラスには名前つき要素があるので可読性改善に繋がる可能性があるよ。たとえば↑の惑星の例も↓のようにした方が読みやすいよね
```scala
case class Planet(name: String, distance: Double)
```

# ミックスインを用いたクラス合成
ミックスイン -> クラスを構成するのに使われるトレイト
ミックスインをうまく使うととても柔軟にコードがかけるね

# 高階関数
```scala
val hoge = Seq(100, 200, 300)
val doubleHoge = hoge.map(h => h * 2)
// equals
val doubleHoge = hoge.map(_ * 2)
```
関数を返す関数
```scala
def urlBuilder(ssl: Boolean, domainName: String): (String, String) => String = {
  val schema = if (ssl) "https://" else "http://"
  (endpoint: String, query: String) => s"$schema$domainName/$endpoint?$query"
}
```

# ネストしたメソッド
メソッド内でメソッドを定義することもできるよ

# カリー化
## foldLeft
```scala
def foldLeft[B](z: B)(op: (b, A) => B): B
```

```scala
val numbers = List(1, 2, 3, 4, 5)
val res = numbers.foldLeft(0)((n, m) => n + m)
// equals
val res = numbers.foldLeft(0, (n: Int, m: Int) => n + m)
// equals
val res = numbers.foldLeft(0)(_ + _)
println(res)  // 15
```

複数パラメータリストを使うことでScalaの型インターフェースの利点を享受できるので`foldLeft(0)(_ + _)`のようにかける

### :+
リストの最後の要素を付け加えたリストを求める
```scala
list :+ 3 // List(1,4,7,3)
```

## 暗黙のパラメータ
### 疑問
ここわかりません！

# ケースクラス
ケースクラスはオブジェクトの生成をおこなう`apply`メソッドを標準で保有するので`new`キーワードはいらない。
パラメータありでケースクラスを作ると、パラメータはパブリックの`val`となる。

```scala
case class Book(isbn: String)
val frankenstein = Book("hogehogehoge")
```

## 比較
ケースクラスは参照ではなく、構造で比較される。

## コピー
`copy`メソッドを使うと、ケースクラスのインスタンスのシャローコピーをつくることができる。
基本的に、コンストラクターと同様の引数を受け取る。
引数は可変で、足りない部分は自分のインスタンスの値が使われる。

```scala
case class Person(name: String, age: Int)
val john = Person("john", 29)
val taro = john.copy(name = "taro")  // copyしたいパラメータは指定しなくてOK

```


### シャローコピーとディープコピー
シャローコピー -> 参照を共有
ディープコピー -> 参照は共有しない。別のオブジェクトとしてコピー

# パターンマッチング
### 変数展開 in 文字列
`hoge.hogehoge`の形の時は`{}`で囲まないとだめやで
```scala
case class Hoge(name: String, age: Int)
var hoge = Hoge("taro", 4)

s"{hoge.name}" // "taro"
s"hoge.name" // Hoge("taro", 4).name
```

## 型のみでもマッチングできるよ
```scala
...
hoge match {
  t: Toyota => t
  m: Matsuda => m
}

```
## シールドクラス
トレイト、クラスに`seald`をつけると、全てのサブタイプは同一ファイル内で宣言されなければならなくなる。
同一ファイルないでしか継承されないので読みやすい。

# シングルトンオブジェクト
トップレベルにあるオブジェクトはシングルトン
クラスのメンバやローカル変数としてのオブジェクトは`lazy val`のように参照された際に生成される。

## シングルトンオブジェクトの定義
**オブジェクトは値**である。
```scala
object Box
```

`package`宣言をすることでどこからでもimportできるようになる

```scala
// 定義元
package logging

object Logger {
  def info(message: String): Unit = println(message)
}

// 呼び出し(別ファイル)
import logging.Logger.info

class Project(name: String)

class Test {
  info("hogehoge")
}
```

## コンパニオンオブジェクト
クラスと同じ名前のオブジェクトは**コンパニオンオブジェクト**と呼ばれる。逆にそのクラスはコンパニオンクラスと呼ばれる。コンパニオンオブジェクトやコンパニオンクラスは自身のコンパニオンのプライベートメンバにアクセスできる。インスタンスから呼ばれる必要がないメソッドや変数はコンパニオンオブジェクトを使おう。

### 疑問
↓の`import Circle._`ってなに
```scala
import scala.math._

case class Circle(radius: Double) {
  import Circle._
  def area: Double = calculateArea(radius)
}

object Circle {
  private def calculateArea(radius: Double): Double = Pi * pow(radius, 2.0)
}

val circle1 = Circle(5.0)

circle1.area
```

### 複数行文字列
```scala
s"""
       |
       |waiwai
       |aa
       |aa
       |""".stripMargin
```

## Optionをつかったファクトリーメソッドの書き方
Option使うのいいなと思った
```scala
class Email(val username: String, val domainName: String)

object Email {
  def fromString(emailString: String): Option[Email] = {
    emailString.split('@') match {
      case Array(a, b) => Some(new Email(a, b))
      case _ => None
    }
  }
}


```

# 正規表現
`.r`メソッドを使うことでどんな文字列も正規表現に変換できる。

# 抽出子オブジェクト
`unapply`メソッドをもつ。
`apply`メソッドが引数をとってオブジェクトを返すように、`unapply`メソッドは1つのオブジェクトを受け取り、引数を返そうとする。(`apply`の逆みたいなイメージなのかな)
パターンマッチングと部分関数で最も頻繁に使われる。

```scala
object CustomerID {

  def apply(name: String) = s"$name--${Random.nextLong}"

  def unapply(customerID: String): Option[String] = {
    val stringArray: Array[String] = customerID.split("--")
    if (stringArray.tail.nonEmpty) Some(stringArray.head) else None
  }
}
```

## `unapply`メソッドの使い方
```scala
val customer2ID = CustomerID("nico")
val CustomerID(name) = customer2ID
// const { name } = john; みたいなことだと思う
// ここ以降で `name` が利用できるようになる
println(name) // "nico" 
```

## unapplyの戻り値の型に関する注意
- ただのテストであれば`Boolean`を返そう
- 

# For 内包表記
シーケンス内包表記を表現するための軽量な記法。
`for(enumerators) yield e`という形をとる。
`enumerators`はセミコロンで区切られたEnumeratorのリストをさす。
1つのenumeratorは、新しい変数を導き出すジェネレータかフィルタのどちらか。
内包表記はenumeratorが生成する束縛1つ1つについて本体`e`を評価し、これらの値のシーケンスを返す。

```scala
case class Person(name: String, age: Int)
val userBase = List(Person("john", 34), Person("taro", 4), Person("jiro", 66))
val adult = for(user <- userBase if (user.age > 10 && user.age < 60)) yield user.name
adult.foreach(u => println(u)) // john
```
`user <- userBase`が**ジェネレータ**で`if ~~`が**フィルター**。

## インクリメントのされ方
```scala
for (i <- 0 until n;
        j <- 0 until n if i + j == v)
   yield (i, j)
```

ってあったらjのインクリメントが終わったらiが1インクリメントされる。

`yield`は`return`みたいなやつなので、for式から値を返す必要がないときはかかなくてよい。

# 変位指定
複合型の間の継承関係とそれらの型パラメータ間の継承関係の相関です(?)

```scala
class Foo[+A] // 共変クラス
class Foo[-A] // 反変クラス
class Foo[A] // 非変クラス
```

## 共変
ジェネリッククラスの型パラメータ`A`は`class Foo[+A]`と書くと共変クラスにできる。このとき、`A`が`B`のサブタイプであるような`A`と`B`に対して、`List[A]`が`List[B]`のサブタイプであることを示す。
### たとえば
```scala
abstract class Animal{
  def name: String
}
case class Dog(name: String) extends Animal
case class Cat(name: String) extends Animal
```
のとき、`List[Cat]`も`List[Dog]`も`List[Animal]`の代わりにできる。 this is 共変。
## 反変
`class Foo[-A]`って書くと反変にできる。
**共変の反対の意味のサブタイプ関係**を作る。
`A`が`B`のサブタイプであるような`A`と`B`に対して、`Writer[B]`が`Writer[A]`のサブタイプであることを示す。
↑の`Cat`, `Dog`, `Animal`クラスを使って説明すると、

```scala
abstract class Printer[-A] {
  def print(value: A): Unit
}

class AnimalPrinter extends Printer[Animal] {
  def print(animal: Animal): Unit =
    println("THe animal's name is: " + animal.name)
}
class CatPrinter extends Printer[Cat] {
  def print(cat: Cat): Unit =
    println("THe cat's name is: " + cat.name)
}
```
`Printer[Cat]`はコンソールに任意の`Cat`をプリントする方法を知っていて、`Printer[Animal]`も、コンソールに任意の`Animal`をプリントする方法を知っている。ここでは、`Printer[Animal]`も任意の`Cat`をプリントする方法を知っている。逆の関係性は成り立たない。(`Printer[Cat]`は`Animal`をコンソールにプリントする方法を知らない)
したがって、必要であれば、`Printer[Animal]`を`Printer[Cat]`の代わりに使うことができる。これは、`Printer[A]`が反変であるからこそ可能。
```scala
object ContravarianceTest extends App {
  val myCat: Cat = Cat("Boots")
  
  def printMyCat(printer: Printer[Cat]): Unit = {
    printer.print(myCat)
  }

  val catPrinter: Printer[Cat] = new CatPrinter
  val animalPrinter: Printer[Animal] = new AnimalPrinter

  printMyCat(CatPrinter) // The cat's name is: Boots
  printMyCat(AnimalPrinter) // The animal's name is: Boots
}
```

## 非変
scalaのジェネリッククラスは標準では非変。これは、**共変でも反変でもない**ことを意味する。
つまり、`Container[Cat]`は`Container[Animal]`ではなく、逆もまた同様である。

仮に`Container`が共変であったとすると、
```scala
class Container[A](value: A) {
  private var _value: A = value
  def getValue: A = _value
  def setValue(value: A): Unit = {
    _value = value
  }
}

val catContainer: Container[Cat] = new Container(Cat("Felix"))
val animalContainer: Container[Animal] = catContainer
animalContainer.serValue(Dog("Spot"))
val cat: Cat = catContainer.getValue
```
みたいなこと(`Dog`を`Cat`に割り当ててしまう)が可能になってしまう。


# 上限型境界
型パラメータと抽象型メンバには**型境界による制約**をかけることができる。
型境界は書いた変数に入れられる具象型を制限して、時にはそれらの型のメンバについてより多くの情報を与える。
上限型境界 `T <: A`は型変数`T`が型`A`のサブタイプであるという宣言。

```scala
abstract class Animal {}
abstract class Pet extends Animal {}
class Cat extends Pet {}
class Dog extends Pet {}
class Lion extends Animal {}

class PetContainer[P <: Pet](p: P) {
  def pet: P = p
}

// ok
val dogContainer = new PetContainer[Dog](new Dog)
// ok
val catContainer = new PetContainer[Cat](new Cat)
// compile error
val lionContainer = new PetContainer[Lion](new Lion)
```

# 下限型境界
上限型境界は型を別の型のサブタイプに制限するけど、下限型境界は型が別の型のスーパータイプであることを宣言する。
`B >: A` -> パラメータ`B`または抽象型`B`が型`A`のスーパータイプであることを表す。

例よくわかんなかった

# 内部クラス
クラスの中に定義されたクラスのこと。
Graph class 内に Node class が定義されているとき、
```scala 
val graph1 = new Graph()
val node1: graph1.node = graph1.create
val graph2 = new Graph()
val node2: graph2.node = graph2.create
```

node1 と node2 は別のもの
同じものとして扱いたい場合は
```scala
// @Graph class
// before
List[Node]
// after
List[Graph#Node]
```
とする。

# 抽象型メンバー
トレイトや抽象クラスは抽象型メンバーをもつことができる。

# 複合型
複数の型のサブタイプであることは複合型を用いて表現できる。
複合型とはオブジェクトの型同士を重ねること。
そんなときはこう。
```scala
def cloneAndReset(obj: Cloneable with Resetable): Cloneable = {
  // ...
}
```

# 自分型
自分型は直接継承していなくてもトレイトがほかのトレイトにミックスインされていることを宣言する方法
これにより、依存先のメンバをimportなしで利用できる。
```scala
trait User {
  def username: String
}

trait Tweeter {
  // 自分型宣言
  this: User =>
  def tweet(tweetText: String) = println(s"$username: $tweetText") // username がつかえる！！！
}

class VerifiedTweeter(val ...) extends Tweeter with User {
  // ...
}
```
`VerifiedTweeter`において、 Tweeter も User も 継承する必要があることに注意。

# 暗黙のパラメータ
暗黙のパラメータリストをもちたいときにはパラメータリストの先頭に`implicit`キーワードで印をつける。
メソッドは暗黙のパラメータのリストを持つことができる。
もしそのパラメータリストの中のパラメータがいつものように渡らなければ、scalaは正しい型の暗黙値を受け取ることができるか確認し、可能であればそれを自動的に渡す。
scalaはこれらのパラメータを以下の2つ(のカテゴリ)の場所で探す
1. 暗黙のパラメータブロックを持つメソッドが呼び出されている箇所で直接アクセスできる暗黙の定義と暗黙のパラメータを探す
1. 次に、候補となる型に関連づけられたすべてのコンパニオンオブジェクトの中でimplicitと宣言されているメンバを探す

```scala
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
```

# 暗黙の変換
型`S`から型`T`への暗黙の変換は`S => T`という型のimplicit値や、その型に一致するimplicitメソッドで定義される。
暗黙の変換は**2つの状況で適用される。**
1. もし式`e`が型`S`であり、`S`が式の期待する型`T`に適合しない場合
1. 型`S`の`e`を使う表記`e.m`があり、セレクター`m`が`S`のメンバではない場合

1.のケースでは、`e`を渡せて、戻り値の型が`T`に適合するような変換`c`を探す。
2.のケースでは、`e`を渡せて、戻り値が`m`というメンバを持つような変換`c`を探す。(`hoge.m`をかえす？)

## 疑問
[それ以降](https://docs.scala-lang.org/ja/tour/implicit-conversions.html)よくわからなかった。

# ポリモーフィックメソッド
わかった

# 型推論
再帰的メソッドでは型推論ができないので型を書く必要がある。

## パラメータ
コンパイラはメソッドのパラメータ型を決して推論しない。(関数の定義部分に書いてあるからってこと？)
しかし、関数が引数として渡されている場合は、無名関数のパラメータ型を推論できる。
```scala
Seq(1, 2, 3).map(x => x * 2)
```
みたいなとき、Seqが`Int`型の要素を持っているので返り値が`Int`であると推論できる。

# 演算子
Scalaに演算子はない。すべてメソッド。
パラメータが1つであるメソッドなら、中置演算子として使える。
```scala
10.+(1)
// equals
10 + 1 // みやすい！
```
中置記法使った方が見やすいこともあるので考慮しよう。

# 名前渡しパラメータ
名前渡しパラメータは使用されるときまで評価されない。
対して値渡しパラメータの利点は1度しか評価されない点。
```scala
def cal(input: => Int) = input * 44
```
引きとして関数を渡した際に`println()`とかが正しいタイミングで表示されるために使ったりする
あとは、計算量が多かったり通信を伴う関係で時間がかかる場合に名前渡しパラメータを使うとパフォーマンス改善の面でメリットがあることがある。

def whileLoop(condition: => Boolean)(body: => Unit): Unit =
  if (condition) {
    body
    whileLoop(condition)(body)
  }

var i = 2

whileLoop (i > 0) {
  println(i)
  i -= 1
}  // prints 2 1

# アノテーション
メソッドが実行されたらコンパイラに警告を出力させる。
```scala
@deprecation("deprecation message", "warning!!!")
def hello = "hola"
hello
// [warn] 1 deprecation (since hogehogehoge); re-run with -deprecation for details
```

# パッケージとインポート
## パッケージの作成
Scala ファイルの先頭で1つ以上のパッケージ名を宣言することでパッケージが作成される。

```scala
package users

class User

```

**パッケージと Scala ファイルが含まれているディレクトリは同じ名前をつける習慣がある。**

こんな感じでネストさせることもできる。
```scala
package users {
  package administrators {
    class NormalUser
  }
  package normalusers {
    class NormalUser
  }
}
```

## インポート
`import`句は他のパッケージのメンバにアクセスするためのものである。**同じパッケージのメンバにアクセスする際は必要ない。**

```scala
import users._  // import everything from users package
import users.User  //  import User from users package
import users.{User, UserPreferences}  // import only selected member
import users.{UserPreferences => UPrefs}  // import A as B
```

`import`句はどこでも使える。

名前が競合するようなパッケージをプロジェクトのルートからインポートする必要があるときは、パッケージ名の前に`_root_`をつける。

```scala
package accounts

import _root_.users._

```

# パッケージオブジェクト
パッケージを通して利用される便利なコンテナとして使われる。
慣習として、パッケージオブジェクトのソースコードは通常`package.scala`に配置される。

1つのパッケージは1つのパッケージオブジェクトを持つことができる。

package宣言をしつつ定義することもできる(?)
```scala
package object fruit {
  val planted = List(Apple, Banana)
  // ...
}

```


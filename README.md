Tour of Scala(https://docs.scala-lang.org/ja/tour/tour-of-scala.html)

# 基本
## ブロック
`{}`で囲んだやつ
ブロックの最後に指揮の結果はブロック全体の結果になる
## 関数
```Scala
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













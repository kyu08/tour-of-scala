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









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
















# KJson
A full Json implementation in Kotlin

Originally part of Sprint, a Rest Client designed for Kotlin I decided to rip this out and make it it's own separate library

```Kotlin

val obj = json(
    "key" to "value",
    "key2" to "value2"
)

val arr = json [
    "value",
    "value2"
]

```

Getting values looks pretty too! (Thanks Kotlin!)

```Kotlin

val key = obj["key"]
val key2 = obj["key"] as String

val v = arr[0]
val v2 = arr[1] as String

```

Or let the library handle casting for you!

```Kotlin

val key = obj.getString("key")
val key2 = obj.getInt("key2")// Using the example from above this would return null as a String cannot be cast as an Int

```

You can put Boolean?, Int?, Double?, String?, JsonObject?, JsonArray?, List<Any?>?, and Map<String, Any?>? into either an  array or object.

Both have support for forEach, iterators, and JsonArray has an indices val

#Traversal
Lets say you have json structure as such
```Json

{
    "key" : {
        "key2" : "value"
    }
}

```

Pulling the value out is as easy as
```Kotlin

val value = obj.traverse("key:key2")

```
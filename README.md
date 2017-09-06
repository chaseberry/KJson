# KJson
A full Json implementation in Kotlin

Version 0.0.18 for Kotlin 1.0.0

To download through Gradle include this in your repositories
```Groovy
maven {
        url  "http://dl.bintray.com/chaseberry/maven" 
    }
```

Then add the following to your dependencies
```Groovy
compile 'edu.csh.chase.kjson:kjson:0.0.16'
```

I will be adding it to JCenter once I'm happy with everything, but for now it's in an early beta stage

#Features



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

All getter functions have two variations.

```Kotlin

get<Type>(key:String):Type?//Returns null if the value is not present, null, or cannot be cast to type

get<Type>(key:String, default:Type):Type//Returns default if the value is not present, null, or cannot be cast to type

```

You can put a Boolean?, Int?, Double?, String?, Long?, Float?, JsonObject?, JsonArray?, List<Any?>?, and Map<String, Any?>?, or anything that implements JsonSerializable into a JsonObject or Json Array.

Both have support for forEach, iterators, and JsonArray has an indices val.

#Traversal
Lets say you have json structure as such
```Json

{
    "key" : {
        "key2" : "value"
    }
}

```

Depth search for embedded JsonObjects or JsonArrays is done as such
```Kotlin

val value = obj.traverse("key:key2")
val valueAsString = obj.traverseString("key:key2")

```

#Delegates

JsonDelegates let you map vals/vars to keys in a JsonObject
```Kotlin

val key:String by JsonDelegates.objectVal(someJsonObject)

var v:Int by JsonDelegates.objectVar(someJsonObject)

```

This will use the name as the key in the object, and changes to the key are updated into the JsonObject

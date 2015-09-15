package edu.csh.chase.kjson

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty

public object JsonDelegates {

    public fun<T> objectVal(jsonObject: JsonObject): JsonObjectVal<T> = JsonObjectVal(jsonObject)

    private open class JsonObjectVal<T : Any?>(protected val jsonObject: JsonObject) : ReadOnlyProperty<Any, T> {

        override fun get(thisRef: Any, desc: PropertyMetadata): T {
            return jsonObject[desc.name] as T
        }

    }

    public fun <T>objectVar(jsonObject: JsonObject): JsonObjectVar<T> = JsonObjectVar(jsonObject)

    class JsonObjectVar<T : Any?>(jsonObject: JsonObject) : JsonObjectVal<T>(jsonObject), ReadWriteProperty<Any, T> {

        override fun set(thisRef: Any, desc: PropertyMetadata, value: T) {
            jsonObject[desc.name] = value
        }

    }

    public fun <T>notNullObjectVal(jsonObject: JsonObject, defaultValue: T): JsonObjectValNotNull<T>
            = JsonObjectValNotNull(jsonObject, defaultValue)

    private open class JsonObjectValNotNull<T : Any>(protected val jsonObject: JsonObject, val defaultValue: T) :
            ReadOnlyProperty<Any, T> {

        override fun get(thisRef: Any, desc: PropertyMetadata): T {
            return jsonObject[desc.name, defaultValue] as T
        }

    }

    public fun <T>notNullObjectVar(jsonObject: JsonObject, defaultValue: T): JsonObjectVarNotNull<T>
            = JsonObjectVarNotNull(jsonObject, defaultValue)

    private class JsonObjectVarNotNull<T : Any>(jsonObject: JsonObject, defaultValue: T) :
            JsonObjectValNotNull<T>(jsonObject, defaultValue), ReadWriteProperty<Any, T> {

        override fun set(thisRef: Any, desc: PropertyMetadata, value: T) {
            jsonObject[desc.name] = value
        }

    }


    private open class StringMVal(protected val `object`: JsonObject) : ReadOnlyProperty<Any, String?> {
        override fun get(thisRef: Any, desc: PropertyMetadata): String? {
            return `object`.getString(desc.name)
        }
    }

    private class StringMVar(`object`: JsonObject) : StringMVal(`object`), ReadWriteProperty<Any, String?> {
        override fun set(thisRef: Any, desc: PropertyMetadata, value: String?) {
            `object`[desc.name] = value
        }

    }

    private open class StringVal(protected val `object`: JsonObject, val default: String) : ReadOnlyProperty<Any, String> {
        override fun get(thisRef: Any, desc: PropertyMetadata): String {
            return `object`.getString(desc.name, default)
        }
    }

    private class StringVar(`object`: JsonObject, default: String) : StringVal(`object`, default), ReadWriteProperty<Any, String> {
        override fun set(thisRef: Any, desc: PropertyMetadata, value: String) {
            `object`[desc.name] = value
        }

    }

    public fun stringVal(`object`: JsonObject): StringMVal = StringMVal(`object`)

    public fun stringVal(`object`: JsonObject, default: String): StringVal = StringVal(`object`, default)

}
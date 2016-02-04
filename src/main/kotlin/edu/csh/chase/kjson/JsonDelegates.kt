package edu.csh.chase.kjson

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

public object JsonDelegates {

    open class JsonObjectVal<T : Any?>(protected val jsonObject: JsonObject) : ReadOnlyProperty<Any, T> {

        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            return jsonObject[property.name] as T
        }

    }

    public class JsonObjectVarNotNull<T : Any>(jsonObject: JsonObject, defaultValue: T) :
            JsonObjectValNotNull<T>(jsonObject, defaultValue), ReadWriteProperty<Any, T> {

        operator override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            jsonObject[property.name] = value
        }

    }

    class JsonObjectVar<T : Any?>(jsonObject: JsonObject) : JsonObjectVal<T>(jsonObject), ReadWriteProperty<Any, T> {
        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            jsonObject[property.name] = value
        }

    }

    public open class JsonObjectValNotNull<T : Any>(protected val jsonObject: JsonObject, val defaultValue: T) :
            ReadOnlyProperty<Any, T> {

        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            return jsonObject[property.name, defaultValue] as T
        }

    }

    public fun<T> objectVal(jsonObject: JsonObject): JsonObjectVal<T> = JsonObjectVal(jsonObject)

    public fun <T> objectVar(jsonObject: JsonObject): JsonObjectVar<T> = JsonObjectVar(jsonObject)

    public fun <T : Any> objectVal(jsonObject: JsonObject, defaultValue: T): JsonObjectValNotNull<T>
            = JsonObjectValNotNull(jsonObject, defaultValue)

    public fun <T : Any> objectVar(jsonObject: JsonObject, defaultValue: T): JsonObjectVarNotNull<T>
            = JsonObjectVarNotNull(jsonObject, defaultValue)

}
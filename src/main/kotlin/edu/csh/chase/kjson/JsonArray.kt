package edu.csh.chase.kjson

import java.io.IOException
import java.io.StringWriter
import java.io.Writer
import java.util.*

class JsonArray() : JsonBase(), Iterable<Any?> {

    private val array = ArrayList<Any?>()

    override val size: Int
        get() {
            return array.size
        }

    val indices: IntRange
        get() {
            return array.indices
        }

    constructor(tokener: JsonTokener) : this() {
        if (tokener.nextClean() != '[') {
            throw tokener.syntaxError("A JSONArray text must start with '['")
        }
        if (tokener.nextClean() != ']') {
            tokener.back()
            while (true) {
                if (tokener.nextClean() == ',') {
                    tokener.back()
                    array.add(null)
                } else {
                    tokener.back()
                    array.add(tokener.nextValue())
                }
                when (tokener.nextClean()) {
                    ',' -> {
                        if (tokener.nextClean() == ']') {
                            return
                        }
                        tokener.back()
                    }
                    ']' -> return
                    else -> throw tokener.syntaxError("Expected a ',' or ']'")
                }
            }
        }
    }

    constructor(jsonString: String) : this(JsonTokener(jsonString))

    constructor(list: Collection<Any?>) : this() {
        array.addAll(list.filter { it.isValidJsonType() })
    }

    constructor(vararg array: Any?) : this() {
        this.array.addAll(array.filter { it.isValidJsonType() })
    }

    private fun getValue(index: Int): Any? {
        if (index !in array.indices) {
            return null
        }
        return array[index]
    }

    private fun setValue(index: Int, value: Any?) {
        if (index !in array.indices) {
            return//Throws exception?
        }
        if (!value.isValidJsonType()) {
            throw JsonException("$value is not a valid JsonException")
        }
        array [index] = value
    }

    //Setters

    fun set(index: Int, value: Any?) {
        setValue(index, value)
    }

    //Putters

    fun put(value: Any?): JsonArray {
        if (!value.isValidJsonType()) {
            throw JsonException("$value is not a valid JsonException")
        }
        array.add(value)
        return this
    }

    fun putNotNull(value: Any?): JsonArray {
        if (value == null) {
            return this
        }
        return put(value)
    }

    //Getters

    operator fun get(index: Int): Any? {
        return getValue(index)
    }

    operator fun get(index: Int, default: Any): Any {
        return getValue(index) ?: return default
    }

    fun getBoolean(index: Int): Boolean? {
        return getValue(index) as? Boolean
    }

    /**
     * Grabs a value from this JsonArray and attempts to coerce it to a Boolean
     *
     * @param index The array index to grab the value from
     * @return The coerced Boolean, or null
     */
    fun coerceBoolean(index: Int): Boolean? = Coercers.toBoolean(get(index))

    fun getBoolean(index: Int, default: Boolean): Boolean {
        return getBoolean(index) ?: return default
    }

    /**
     * Grabs a value from this JsonArray and attempts to coerce it to a Boolean
     * If no value is present, or the value could not be coerced, it will return the provided default
     *
     * @param index The array index to grab the value from
     * @param default The default value to return
     * @return The coerced value, or default
     */
    fun coerceBoolean(index: Int, default: Boolean): Boolean = coerceBoolean(index) ?: default

    fun getInt(index: Int): Int? {
        return getValue(index) as? Int
    }

    fun getInt(index: Int, default: Int): Int {
        return getInt(index) ?: return default
    }

    fun getDouble(index: Int): Double? {
        return getValue(index) as? Double
    }

    fun getDouble(index: Int, default: Double): Double {
        return getDouble(index) ?: return default
    }

    fun getString(index: Int): String? {
        return getValue(index) as? String
    }

    fun getString(index: Int, default: String): String {
        return getString(index) ?: return default
    }

    fun getJsonObject(index: Int): JsonObject? {
        return getValue(index) as? JsonObject
    }

    fun getJsonObject(index: Int, default: JsonObject): JsonObject {
        return getJsonObject(index) ?: return default
    }

    fun getJsonArray(index: Int): JsonArray? {
        return getValue(index) as? JsonArray
    }

    fun getJsonArray(index: Int, default: JsonArray): JsonArray {
        return getJsonArray(index) ?: return default
    }

    /**
     * Gets the value from a given index if the value is a number
     *
     * @param index The index to pull the value from
     * @return The Float from the given index, null if no value or not a number
     */
    fun getFloat(index: Int): Float? {
        val v = get(index)
        return if (v is Number) {
            v.toFloat()
        } else {
            null
        }
    }

    /**
     * Gets the value from the given index if the value is a number
     *
     * @param index The index to pull a value from
     * @param default The default value is no value is found
     * @return The Float from the given index, default if no value or not a number
     */
    fun getFloat(index: Int, default: Float): Float {
        val v = get(index)
        return if (v is Number) {
            v.toFloat()
        } else {
            default
        }
    }

    /**
     * Gets the value from a given index if the value is a number
     *
     * @param index The index to pull the value from
     * @return The Float from the given index, null if no value or not a number
     */
    fun getLong(index: Int): Long? {
        val v = get(index)
        return if (v is Number) {
            v.toLong()
        } else {
            null
        }
    }

    /**
     * Gets the value from the given index if the value is a number
     *
     * @param index The index to pull a value from
     * @param default The default value is no value is found
     * @return The Float from the given index, default if no value or not a number
     */
    fun getLong(index: Int, default: Long): Long {
        val v = get(index)
        return if (v is Number) {
            v.toLong()
        } else {
            default
        }
    }

    //Other Functions

    /**
     * Gets an immutable copy of this JsonArray's internal array
     *
     * @return An immutable array
     */
    fun getInternalArray(): List<Any?> = Collections.unmodifiableList(array)

    fun remove(index: Int): Any? = array.remove(index)

    operator fun contains(index: Int): Boolean {
        return index in array.indices
    }

    override fun jsonSerialize(): String {
        return this.toString(false)
    }

    override fun iterator(): Iterator<Any?> {
        return array.iterator()
    }

    override fun equals(other: Any?): Boolean {
        return other is JsonArray && array == other.array
    }

    override fun toString(): String {
        return toString(false)
    }

    override fun toString(shouldIndent: Boolean, depth: Int): String {
        val sw = StringWriter()
        synchronized (sw.buffer) {
            return this.write(sw, shouldIndent, depth).toString()
        }
    }

    /**
     * Write the contents of the JSONArray as JSON text to a writer. For
     * compactness, no whitespace is added.
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     *
     * @return The writer.
     * @throws JSONException
     */
    fun write(writer: Writer): Writer {
        return this.write(writer, false)
    }


    /**
     * Write the contents of the JSONArray as JSON text to a writer. For
     * compactness, no whitespace is added.
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     *
     * @param indentFactor
     *            The number of spaces to add to each level of indentation.
     * @param indent
     *            The indention of the top level.
     * @return The writer.
     * @throws JSONException
     */
    fun write(writer: Writer, shouldIndent: Boolean, depth: Int = 1): Writer {
        try {
            var addComa = false

            writer.write("[")

            for (value in array) {

                if (addComa) {
                    writer.write(",")
                }
                if (shouldIndent) {
                    writer.write("\n")
                    writer.indent(depth)
                }
                writer.write(JsonValues.toString(value, shouldIndent, depth + 1))
                addComa = true
            }
            if (shouldIndent) {
                writer.write("\n")
                writer.indent(depth - 1)
            }
            writer.write("]")
            return writer
        } catch (e: IOException) {
            throw JsonException(e)
        }
    }

}
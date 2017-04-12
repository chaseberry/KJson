package edu.csh.chase.kjson

abstract class JsonBase : RawJsonSerializable {

    /**
     * A value that equals how large this JsonBase is
     */
    abstract val size: Int

    var delim = ":"

    abstract fun toString(shouldIndent: Boolean, depth: Int = 1): String

    /**
     * Attempts to walk down a chain of Objects and Arrays based on a compoundKey
     * Compound keys will be split by the delim(base of ":")
     * If it encounters a dead route it will return what it last found, including the array or object it's checking
     * An int will be parses as an array index, all else will be object keys
     *
     * @param compoundKey A compound key in the form of key:key2:key3 to dig down into
     * @return The value that was found by the provided key, or null if nothing was found
     */
    fun traverse(compoundKey: String): Any? {
        val key = compoundKey.split(delim).iterator()
        return when (this) {
            is JsonArray -> traverseArray(key, this)
            is JsonObject -> traverseObject(key, this)
            else -> null
        }
    }

    /**
     * Attempts to walk down a chain of Objects and Arrays based on a compoundKey
     * Compound keys will be split by the delim(base of ":")
     * If it encounters a dead route it will return what it last found, including the array or object it's checking
     * An int will be parses as an array index, all else will be object keys
     *
     * @param compoundKey A compound key in the form of key:key2:key3 to dig down into
     * @param default A default value to return if null was encountered
     * @return The value that was found by the provided key, or null if nothing was found
     */
    fun traverse(compoundKey: String, default: Any): Any {
        return traverse(compoundKey = compoundKey) ?: default
    }

    /**
     * Traverses a JsonBase for a Boolean?
     *
     * @param compoundKey The compound key to search for
     * @return A boolean if the key resolved or null if the value was not found or not a boolean
     */
    fun traverseBoolean(compoundKey: String): Boolean? = traverse(compoundKey) as? Boolean

    /**
     * Traverses a JsonBase for a Boolean
     *
     * @param compoundKey The compound key to search for
     * @param default A default value
     * @return A boolean if the key resolved or default if the value was not found or not a boolean
     */
    fun traverseBoolean(compoundKey: String, default: Boolean): Boolean = traverseBoolean(compoundKey) ?: default

    /**
     * Traverses a JsonBase for an Int?
     *
     * @param compoundKey The compound key to search for
     * @return An Int if the key resolved or null
     */
    fun traverseInt(compoundKey: String): Int? = traverse(compoundKey) as? Int

    /**
     * Traverses a JsonBase for an Int
     *
     * @param compoundKey The compound key to search for
     * @param default A default value
     * @return An Int if the key resolved or default if the value was not found or not an Int
     */
    fun traverseInt(compoundKey: String, default: Int): Int = traverseInt(compoundKey) ?: default

    /**
     * Traverses a JsonBase for a Double?
     *
     * @param compoundKey The compound key to search for
     * @return A Double if the key resolved or null
     */
    fun traverseDouble(compoundKey: String): Double? = traverse(compoundKey) as? Double

    /**
     * Traverses a JsonBase for a Double
     *
     * @param compoundKey The compound key to search for
     * @param default A default value
     * @return A Double if the key resolved or default if the value was not found or not a Double
     */
    fun traverseDouble(compoundKey: String, default: Double): Double = traverseDouble(compoundKey) ?: default

    /**
     * Traverses a JsonBase for a String?
     *
     * @param compoundKey The compound key to search for
     * @return A String if the key resolved or null
     */
    fun traverseString(compoundKey: String): String? = traverse(compoundKey) as? String

    /**
     * Traverses a JsonBase for a String
     *
     * @param compoundKey The compound key to search for
     * @param default A default value
     * @return A String if the key resolved or default if the value was not found or not a String
     */
    fun traverseString(compoundKey: String, default: String): String = traverseString(compoundKey) ?: default

    /**
     * Traverses a JsonBase for a JsonObject?
     *
     * @param compoundKey The compound key to search for
     * @return A JsonObject if the key resolved or null
     */
    fun traverseJsonObject(compoundKey: String): JsonObject? = traverse(compoundKey) as? JsonObject

    /**
     * Traverses a JsonBase for a JsonObject
     *
     * @param compoundKey The compound key to search for
     * @param default A default value
     * @return A JsonObject if the key resolved or default if the value was not found or not a JsonObject
     */
    fun traverseJsonObject(compoundKey: String, default: JsonObject): JsonObject = traverseJsonObject(compoundKey) ?: default

    /**
     * Traverses a JsonBase for a JsonArray?
     *
     * @param compoundKey The compound key to search for
     * @return A JsonArray if the key resolved or null
     */
    fun traverseJsonArray(compoundKey: String): JsonArray? = traverse(compoundKey) as? JsonArray

    /**
     * Traverses a JsonBase for a JsonArray
     *
     * @param compoundKey The compound key to search for
     * @param default A default value
     * @return A JsonArray if the key resolved or default if the value was not found or not a JsonArray
     */
    fun traverseJsonArray(compoundKey: String, default: JsonArray): JsonArray = traverseJsonArray(compoundKey) ?: default

    /**
     * Traverses a JsonBase for a Float?
     *
     * @param compoundKey The compound key to search for
     * @return A Float if the key resolved or null
     */
    fun traverseFloat(compoundKey: String): Float? {
        val num = traverse(compoundKey)
        if (num is Number) {
            return num.toFloat()
        }
        return null
    }

    /**
     * Traverses a JsonBase for a Float
     *
     * @param compoundKey The compound key to search for
     * @param default A default value
     * @return A Float if the key resolved or default if the value was not found or not a Float
     */
    fun traverseFloat(compoundKey: String, default: Float): Float {
        return traverseFloat(compoundKey) ?: default
    }

    /**
     * Traverses a JsonBase for a Long?
     *
     * @param compoundKey The compound key to search for
     * @return A Long if the key resolved or null
     */
    fun traverseLong(compoundKey: String): Long? = traverse(compoundKey) as? Long

    /**
     * Traverses a JsonBase for a Long
     *
     * @param compoundKey The compound key to search for
     * @param default A default value
     * @return A Long if the key resolved or default if the value was not found or not a Long
     */
    fun traverseLong(compoundKey: String, default: Long): Long = traverseLong(compoundKey) ?: default

    /**
     * Traverses this JsonBase with multiple keys
     *
     * @param keys A list of keys to check
     * @return The first found value or null if no key matched
     */
    @Deprecated("Might be removed in the future. To be determined")
    fun traverseMulti(vararg keys: String): Any? {
        for (key in keys) {
            return traverse(compoundKey = key) ?: continue
        }
        return null
    }

    /**
     * Traverses this JsonBase with a default value if a null was found
     *
     * @param default A default if null was found
     * @param keys A list of keys to check
     * @return The first found value or default if none worked
     */
    @Deprecated("Might be removed in the future. To be determined")
    fun traverseMultiWithDefault(default: Any, vararg keys: String): Any {
        return traverseMulti(keys = *keys) ?: default
    }

    private fun traverseArray(key: Iterator<String>, array: JsonArray): Any? {
        if (!key.hasNext()) {
            return array
        }

        val index = try {
            key.next().toInt()
        } catch(e: NumberFormatException) {
            return null
        }
        val value = array[index]
        return when (value) {
            is JsonArray -> traverseArray(key, value)
            is JsonObject -> traverseObject(key, value)
            else -> value
        }

    }

    private fun traverseObject(key: Iterator<String>, `object`: JsonObject): Any? {
        if (!key.hasNext()) {
            return `object`
        }
        val value = `object`[key.next()]
        return when (value) {
            is JsonObject -> traverseObject(key, value)
            is JsonArray -> traverseArray(key, value)
            else -> value
        }
    }

}
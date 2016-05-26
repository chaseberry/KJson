package edu.csh.chase.kjson

abstract class JsonBase : JsonSerializable {

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

    fun traverseBoolean(compoundKey: String): Boolean? {
        return traverse(compoundKey) as? Boolean
    }

    fun traverseBoolean(compoundKey: String, default: Boolean): Boolean {
        return traverseBoolean(compoundKey) ?: default
    }

    fun traverseInt(compoundKey: String): Int? {
        return traverse(compoundKey) as? Int
    }

    fun traverseInt(compoundKey: String, default: Int): Int {
        return traverseInt(compoundKey) ?: default
    }

    fun traverseDouble(compoundKey: String): Double? {
        return traverse(compoundKey) as? Double
    }

    fun traverseDouble(compoundKey: String, default: Double): Double {
        return traverseDouble(compoundKey) ?: default
    }

    fun traverseString(compoundKey: String): String? {
        return traverse(compoundKey) as? String
    }

    fun traverseString(compoundKey: String, default: String): String {
        return traverseString(compoundKey) ?: default
    }

    fun traverseJsonObject(compoundKey: String): JsonObject? {
        return traverse(compoundKey) as? JsonObject
    }

    fun traverseJsonObject(compoundKey: String, default: JsonObject): JsonObject {
        return traverseJsonObject(compoundKey) ?: default
    }

    fun traverseJsonArray(compoundKey: String): JsonArray? {
        return traverse(compoundKey) as? JsonArray
    }

    fun traverseJsonArray(compoundKey: String, default: JsonArray): JsonArray {
        return traverseJsonArray(compoundKey) ?: default
    }

    fun traverseFloat(compoundKey: String): Float? {
        val num = traverse(compoundKey)
        if (num is Number) {
            return num.toFloat()
        }
        return null
    }

    fun traverseFloat(compoundKey: String, default: Float): Float {
        return traverseFloat(compoundKey) ?: default
    }

    fun traverseLong(compoundKey: String): Long? {
        val num = traverse(compoundKey)
        if (num is Number) {
            return num.toLong()
        }
        return null
    }

    fun traverseLong(compoundKey: String, default: Long): Long {
        return traverseLong(compoundKey) ?: default
    }

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
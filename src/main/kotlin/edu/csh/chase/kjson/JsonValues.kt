package edu.csh.chase.kjson

public object JsonValues {

    fun toString(value: Any?, shouldIndent: Boolean = false, depth: Int = 1): String {
        return when (value) {
            null -> "null"
            is Collection<Any?> -> JsonArray(value.filter { it.isValidJsonType() }).toString(shouldIndent, depth)
            is Map<*, *> -> JsonObject(value.jsonMapFilter { it.value.isValidJsonType() }).toString(shouldIndent, depth)
            is String -> quote(value)
            is JsonBase -> value.toString(shouldIndent, depth)
            is JsonSerializable -> value.jsonSerialize()
            else -> value.toString()
        }
    }

    fun fromString(value: String): Any? {
        when (value.toLowerCase()) {
            "true" -> return true
            "false" -> return false
            "null" -> return null
        }
        if ((value[0] >= '0' && value[0] <= '9') || value[0] == '-') {
            try {
                //Is it a Double?
                if (value.contains('.') || value.contains('e') || value.contains('E')) {
                    val double = value.toDouble()
                    if (!double.isNaN() && !double.isInfinite()) {
                        return double
                    }
                    //Long or Int
                } else {
                    val long = value.toLong()
                    if (long.compareTo(long.toInt()) == 0) {
                        return long.toInt()
                    }
                    return long
                }
            } catch(exception: ClassCastException) {

            }
        }
        return value
    }
}
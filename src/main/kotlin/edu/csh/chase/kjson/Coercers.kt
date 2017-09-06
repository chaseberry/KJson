package edu.csh.chase.kjson

object Coercers {

    fun toBoolean(value: Any?): Boolean? {
        return when (value) {
            is Boolean? -> value
            else -> java.lang.Boolean.parseBoolean(value.toString())
        }
    }

    fun toInt(value: Any?): Int? {

        return when (value) {
            is Int? -> value
            is Number? -> value?.toInt()
            else -> {
                try {
                    value?.toString()?.toInt()
                } catch(e: NumberFormatException) {
                    null
                }
            }
        }
    }

    fun toDouble(value: Any?): Double? {
        return when (value) {
            is Double? -> value
            is Number? -> value?.toDouble()
            else -> {
                try {
                    value?.toString()?.toDouble()
                } catch(e: NumberFormatException) {
                    null
                }
            }
        }
    }

    fun toString(value: Any?): String? {
        return when (value) {
            is String? -> value
            else -> value?.toString()
        }
    }

    fun toLong(value: Any?): Long? {
        return when (value) {
            is Long? -> value
            is Number? -> value?.toLong()
            else -> {
                try {
                    value?.toString()?.toLong()
                } catch(e: NumberFormatException) {
                    null
                }
            }
        }
    }

    fun toFloat(value: Any?): Float? {
        return when (value) {
            is Float -> value
            is Number -> value.toFloat()
            else -> {
                try {
                    value?.toString()?.toFloat()
                } catch(e: NumberFormatException) {
                    null
                }
            }
        }
    }

}
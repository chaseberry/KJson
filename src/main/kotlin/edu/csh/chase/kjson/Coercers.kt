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
                    value.toString().toInt()
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
                    value.toString().toDouble()
                } catch(e: NumberFormatException) {
                    null
                }
            }
        }
    }

}
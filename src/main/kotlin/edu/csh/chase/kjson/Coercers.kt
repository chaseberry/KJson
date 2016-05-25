package edu.csh.chase.kjson

object Coercers {

    fun toInt(value: Any?): Int? {

        return when (value) {
            is Int -> value
            is Double -> value.toInt()
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
            is Double -> value
            is Int -> value.toDouble()
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
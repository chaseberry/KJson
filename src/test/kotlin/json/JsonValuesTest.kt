package json

import edu.csh.chase.kjson.JsonValues
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonValuesTest {

    @Test
    fun nullValue() {
        assertEquals("null", JsonValues.toString(null))
        assertEquals(null, JsonValues.fromString("null"))
    }

    @Test
    fun testBoolean() {
        assertEquals("true", JsonValues.toString(true))
        assertEquals(true, JsonValues.fromString("true"))

        assertEquals("false", JsonValues.toString(false))
        assertEquals(false, JsonValues.fromString("false"))
    }

    @Test
    fun testInts() {
        assertEquals("0", JsonValues.toString(0))
        assertEquals(5, JsonValues.fromString("5"))

        assertEquals("-123", JsonValues.toString(-123))
        assertEquals(-999, JsonValues.fromString("-999"))
    }

    @Test
    fun testDoubles() {
        assertEquals("0.0", JsonValues.toString(0.0))
        assertEquals(5.5, JsonValues.fromString("5.5"))

        assertEquals("-123.456", JsonValues.toString(-123.456))
        assertEquals(-999.888, JsonValues.fromString("-999.888"))

        assertEquals(1E3, JsonValues.fromString("1E3"))
    }

    @Test
    fun testStrings() {
        assertEquals("", JsonValues.fromString(""))
        assertEquals("\"\"", JsonValues.toString(""))

        assertEquals("str", JsonValues.fromString("str"))
        assertEquals("\"str\"", JsonValues.toString("str"))
    }

}
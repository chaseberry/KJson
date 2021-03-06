package json

import edu.csh.chase.kjson.jsonSerialize
import org.junit.Test
import org.junit.Assert.*

class JsonHelperFunctionTest {

    @Test fun booleanJsonSerialize() {
        assertEquals("true", true.jsonSerialize())
        assertEquals("false", false.jsonSerialize())
        assertEquals("null", (null as Boolean?).jsonSerialize())
    }

    @Test fun doubleJsonSerialize() {
        assertEquals("15.0", 15.0.jsonSerialize())
        assertEquals("23.5635", 23.5635.jsonSerialize())
        assertEquals("140000.0", 14E4.toString())
        assertEquals("null", (null as Double?).jsonSerialize())
    }

}
package json

import edu.csh.chase.kjson.Json
import org.junit.Assert.*
import org.junit.Test

class CoercersTest {

    @Test fun testObjectCoerceInt() {
        val json = Json("int" to "5",
                "string" to "test",
                "double" to 25.5)

        assertEquals(5, json.getInt("int", true))
        assertEquals(5, json.getInt("int", 4, true))

        assertEquals(null, json.getInt("null", true))
        assertEquals(1, json.getInt("null", 1, true))

        assertEquals(null, json.getInt("string", true))
        assertEquals(1, json.getInt("string", 1, true))

        assertEquals(25, json.getInt("double", true))
        assertEquals(25, json.getInt("double", 4, true))
    }


}
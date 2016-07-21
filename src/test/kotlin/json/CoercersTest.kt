package json

import edu.csh.chase.kjson.Json
import org.junit.Assert.*
import org.junit.Test

class CoercersTest {

    @Test fun testObjectCoerceInt() {
        val json = Json("int" to "5",
                "string" to "test",
                "double" to 25.5)

        assertEquals(5, json.coerceInt("int"))
        assertEquals(5, json.coerceInt("int", 4))

        assertEquals(null, json.coerceInt("null"))
        assertEquals(1, json.coerceInt("null", 1))

        assertEquals(null, json.coerceInt("string"))
        assertEquals(1, json.coerceInt("string", 1))

        assertEquals(25, json.coerceInt("double"))
        assertEquals(25, json.coerceInt("double", 4))
    }


}
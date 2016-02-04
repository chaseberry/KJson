package json

import edu.csh.chase.kjson.JsonArray
import edu.csh.chase.kjson.JsonObject
import edu.csh.chase.kjson.Json
import org.junit.Test
import org.junit.Assert.*

class JsonCreationTest {

    @Test fun testObjectCreationEmpty() {
        val obj = JsonObject()
        assertEquals(obj, Json ())
    }

    @Test fun testObjectCreation() {
        val obj = JsonObject("key" to "value")
        assertEquals(obj,
                Json (
                        "key" to "value"
                )
        )
    }


    @Test fun testArrayCreationEmpty() {
        val arr = JsonArray()
        assertEquals(arr, Json.get())
    }

    @Test fun testArrayCreation() {
        val arr = JsonArray().put("value")
        assertEquals(arr, Json[
                "value"
                ])
    }

}
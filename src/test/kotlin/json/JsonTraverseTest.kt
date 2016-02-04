package json

import edu.csh.chase.kjson.JsonArray
import edu.csh.chase.kjson.JsonObject
import edu.csh.chase.kjson.Json
import org.junit.Test
import org.junit.Assert.*


class JsonTraverseTest {

    @Test fun traverseBaseObjectTest() {
        val obj = JsonObject()
        obj["key"] = "value"
        obj["test"] = 15

        assertEquals("value", obj.traverse("key"))
        assertEquals("value", obj.traverse("key:test"))
        assertEquals(15, obj.traverse("test"))
        assertEquals(null, obj.traverse("invalid"))
        assertEquals("default", obj.traverse(compoundKey = "invalid", default = "default"))
    }

    @Test fun traverseBaseArrayTest() {
        val arr = JsonArray("value", 15)

        assertEquals("value", arr.traverse("0"))
        assertEquals("value", arr.traverse("0:1"))
        assertEquals(15, arr.traverse("1"))
        assertEquals(null, arr.traverse("3"))
        assertEquals(null, arr.traverse("invalid"))
        assertEquals("default", arr.traverse(compoundKey = "invalid", default = "default"))
        assertEquals("default", arr.traverse(compoundKey = "-1", default = "default"))
    }

    @Test fun traverseJsonObjectTest() {
        val obj = JsonObject().put("key", "value").put("test", 15)
        val secondObj = JsonObject().put("secondKey", "secondValue")
        obj["object"] = secondObj

        assertEquals("secondValue", obj.traverse("object:secondKey"))
        assertEquals("secondValue", obj.traverse("object:secondKey:invalidKey"))
    }

    @Test fun traverseJsonArrayTest() {
        val arr = JsonArray("value", 15, JsonArray("secondValue", 16))

        assertEquals("secondValue", arr.traverse("2:0"))
        assertEquals("secondValue", arr.traverse("2:0:0"))
        assertNull(arr.traverse("invalidKey"))
    }

    @Test fun traverseMultiJsonObjectTest() {
        val obj = Json(
                "key" to "value",
                "k" to "v",
                "k2" to "v2",
                "obj" to Json(
                        "k3" to "v3",
                        "k4" to "v4"
                )
        )
        assertEquals("v3", obj.traverseMulti("y", "obj:k3"))
        assertEquals("v3", obj.traverseMulti(*arrayOf("y", "obj:k3")))
        assertEquals("v3", obj.traverseMulti("obj:k3", "y"))
        assertEquals("v3", obj.traverseMulti(*arrayOf("obj:k3", "y")))

    }

}
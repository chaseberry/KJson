package json

import edu.csh.chase.kjson.JsonException
import edu.csh.chase.kjson.JsonObject
import org.junit.Assert.*
import org.junit.Test


class JsonObjectTest() {

    @Test fun emptyObjectTest() {
        val obj = JsonObject()
        assertEquals(0, obj.size)
        assertEquals("{}", obj.toString())
        assertEquals("{\n}", obj.toString(true))
    }

    @Test fun invalidJsonTest1() {
        val invalidJson = "{\"key\":\"value}"
        try {
            JsonObject(invalidJson)
        } catch(unterminatedString: JsonException) {
            return
        }
        assert(false) { "Should have thrown a Unterminated string JsonException" }
    }

    @Test fun JsonGetNullTest() {
        val obj = JsonObject()

        //Check default get
        assertNull(obj["invalidKey"])

        //Check String get
        assertNull(obj.getString("invalidKey"))

        //Check Boolean get
        assertNull(obj.getBoolean("invalidKey"))

    }

    @Test fun JsonDefaultGetTest() {
        val obj = JsonObject()

        //Check default String
        assertEquals("defaultValue", obj.getString("invalidKey", "defaultValue"))

        //Check default Boolean
        assertTrue(obj.getBoolean("value", true))
        assertFalse(obj.getBoolean("value", false))

    }

    @Test fun JsonObjectGetTest() {
        val obj = JsonObject()

        assertNull(obj["aKey"])
        assertEquals(0, obj["aKey", 0])

        obj["key"] = "value"

        assertEquals("value", obj["key"])
        assertEquals("value", obj["key", "defaultValue"])
        assertEquals("defaultValue", obj["invalidKey", "defaultValue"])

        obj["int"] = 1024
        assertEquals(1024, obj["int"] as Int)

    }

    @Test fun JsonGetTestString() {
        val jsonString = "{\"key\":\"value\"}"
        try {
            val obj = JsonObject(jsonString)
            assertEquals(1, obj.size)

            //Check the default get
            assertEquals("value", obj["key"] as String)
            assertEquals("value", obj["key", "defaultValue"] as String)

            //Check the String get
            assertEquals("value", obj.getString("key") as String)
            assertEquals("value", obj.getString("key", "defaultValue"))

            //Check cast to String
            assertNull(obj.getBoolean("value"))
            assertNull(obj.getInt("value"))
            assertNull(obj.getJsonObject("value"))

            assertEquals("{\"key\":\"value\"}", obj.toString())

        } catch(exception: JsonException) {
            assert(false) { "Creating valid Json threw exception ${exception.message}" }
        }
    }

    @Test fun JsonSetTest1() {
        val obj = JsonObject()
        assertEquals(0, obj.size)

        try {
            obj["string"] = "aString"
            obj["int"] = 10
        } catch(invalidValue: JsonException) {
            assert(false) { "Invalid value added to JsonObject ${invalidValue.message}" }
        }

        assertEquals(2, obj.size)

        try {
            obj["double"] = 15.0
            obj["double"] = Double.NEGATIVE_INFINITY
            assert(false) { "An infinite double was added to a JsonObject" }
        } catch(invalidDouble: JsonException) {

        }

        assertEquals(15.0, obj["double"] as Double, 0.0)

        try {
            obj["invalidType"] = "key" to "value"
            assert(false) { "An invalid type was added to a JsonObject" }
        } catch(invalidType: JsonException) {
        }

        assertEquals(3, obj.size)

    }

    @Test fun JsonPutTest1() {
        val obj = JsonObject()
        assertEquals(0, obj.size)

        try {
            obj.put("string", "aString")
            obj.put("int", 10)
        } catch(invalidValue: JsonException) {
            assert(false) { "Invalid value added to JsonObject ${invalidValue.message}" }
        }

        assertEquals(2, obj.size)

        try {
            obj.put("double",15.0)
            obj.put("double",Double.NEGATIVE_INFINITY)
            assert(false) { "An infinite double was added to a JsonObject" }
        } catch(invalidDouble: JsonException) {

        }

        assertEquals(15.0, obj["double"] as Double, 0.0)

        try {
            obj.put("invalidType", "key" to "value")
            assert(false) { "An invalid type was added to a JsonObject" }
        } catch(invalidType: JsonException) {
        }

        assertEquals(3, obj.size)

        try {
            obj.putNotNull("notNull", "ok")
            obj.putNotNull("null", null)
        } catch(invalidValue: JsonException) {
            assert(false) { "Invalid value added to JsonObject ${invalidValue.message}" }
        }

        assertEquals(4, obj.size) // only the "notNull" key/value should have been added
    }

    @Test fun JsonPutTest2() {
        val obj = JsonObject()
        assertEquals(0, obj.size)

        try {
            obj.put("string" to "aString", "int" to 10)
        } catch(invalidValue: JsonException) {
            assert(false) { "Invalid value added to JsonObject ${invalidValue.message}" }
        }

        assertEquals(2, obj.size)

        try {
            obj.put("double" to 15.0, "double" to Double.NEGATIVE_INFINITY)
            assert(false) { "An infinite double was added to a JsonObject" }
        } catch(invalidDouble: JsonException) {

        }

        assertEquals(15.0, obj["double"] as Double, 0.0)

        try {
            obj.put("invalidType", "key" to "value")
            assert(false) { "An invalid type was added to a JsonObject" }
        } catch(invalidType: JsonException) {
        }

        assertEquals(3, obj.size)

        try {
            obj.putNotNull("notNull" to "ok", "null" to null)
        } catch(invalidValue: JsonException) {
            assert(false) { "Invalid value added to JsonObject ${invalidValue.message}" }
        }

        assertEquals(4, obj.size) // only the "notNull" key/value should have been added
    }

}
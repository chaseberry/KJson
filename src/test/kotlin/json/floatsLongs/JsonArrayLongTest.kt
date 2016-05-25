package json.floatsLongs

import edu.csh.chase.kjson.Json
import org.junit.Test
import org.junit.Assert.*

class JsonArrayLongTest {

    @Test fun saveLongTest() {
        val arr = Json[1L]

        assertEquals(1L, arr.getLong(0))
        assertEquals("[1]", arr.toString())
    }

    @Test fun readLongTest() {
        val arr = Json.parseToArray("[1]")!!

        assertEquals(1L, arr.getLong(0))
    }

}
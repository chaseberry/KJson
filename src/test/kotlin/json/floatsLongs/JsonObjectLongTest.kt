package json.floatsLongs

import edu.csh.chase.kjson.Json
import org.junit.Assert
import org.junit.Test

class JsonObjectLongTest {

    @Test fun saveLongTest() {
        val obj = Json("l" to 1L)

        Assert.assertEquals(1L, obj.getLong("l"))
        Assert.assertEquals("{\"l\":1}", obj.toString())
    }

    @Test fun readLongTest() {
        val obj = Json.parseToObject("{\"l\":1}")!!

        Assert.assertEquals(1L, obj.getLong("l"))
    }


}
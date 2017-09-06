package json.floatsLongs

import edu.csh.chase.kjson.Json
import org.junit.Assert
import org.junit.Test

class JsonArrayFloatTest {

    @Test fun saveFloatTest() {
        val arr = Json[1F]

        Assert.assertEquals(1F, arr.coerceFloat(0))
        Assert.assertEquals("[1.0]", arr.toString())
    }

    @Test fun readFloatTest() {
        val arr = Json.parseToArray("[1.0]")!!

        Assert.assertEquals(1F, arr.coerceFloat(0))
    }


}
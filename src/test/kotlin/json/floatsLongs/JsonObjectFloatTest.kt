package json.floatsLongs

import edu.csh.chase.kjson.Json
import org.junit.Assert
import org.junit.Test

class JsonObjectFloatTest {

    @Test fun saveFloatTest() {
        val obj = Json("f" to 1F)

        Assert.assertEquals(1F, obj.coerceFloat("f"))
        Assert.assertEquals("{\"f\":1.0}", obj.toString())
    }

    @Test fun readFloatTest() {
        val obj = Json.parseToObject("{\"f\":1.0}")!!

        Assert.assertEquals(1F, obj.coerceFloat("f"))
    }


}
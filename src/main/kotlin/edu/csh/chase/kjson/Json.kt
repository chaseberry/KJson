package edu.csh.chase.kjson

public object Json {

    /**
     * Constructs a JsonArray from a list of elements
     * If any provided element is not a valid json type it will be skipped
     *
     * @param elements A list of Any? elements to put into a JsonArray
     * @return A JsonArray containing only valid elements from the provided list
     */
    operator fun get(vararg elements: Any?): JsonArray {
        return JsonArray(*elements)
    }


    /**
     * Constructs a JsonObject from a list of Pair<String, Any?>
     * Provided pairs with invalid json types will be ignored
     *
     * @param elements A list of key, value pairs
     * @return A JsonObject with only valid pairs from the provided lambda
     */
    operator fun invoke(vararg elements: Pair<String, Any?>): JsonObject {
        return JsonObject(*elements)
    }


    /**
     * A function that will attempt to create a JsonObject or JsonArray from a given String
     *
     * @param jsonString The String to attempt to be read as json
     * @return A valid JsonObject, JsonArray or null if it failed to parse the Json
     */
    fun parse(jsonString: String): JsonBase? {
        try {
            return JsonObject(jsonString)
        } catch(excep: JsonException) {

        }
        try {
            return JsonArray(jsonString)
        } catch(excep: JsonException) {

        }
        return null
    }

    /**
     * Parses a String directly into a JsonObject?
     * Just a convenience to prevent type checking
     *
     * @param jsonString The raw json String
     * @return JsonObject if it could be parsed, else null
     */
    fun parseToObject(jsonString: String): JsonObject? {
        return try {
            JsonObject(jsonString)
        } catch(excep: JsonException) {
            null
        }
    }

    /**
     * Parses a String directly into a JsonArray?
     * Just a convenience to prevent type checking
     *
     * @param jsonString The raw json String
     * @return JsonArray if it could be parsed, else null
     */
    fun parseToArray(jsonString: String): JsonArray? {
        return try {
            JsonArray(jsonString)
        } catch(excep: JsonException) {
            null
        }
    }

}
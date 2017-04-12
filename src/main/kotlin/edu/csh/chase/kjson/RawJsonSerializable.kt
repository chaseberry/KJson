package edu.csh.chase.kjson

interface RawJsonSerializable {

    /**
     * This must return a valid Json Value
     * Numbers are just a string of that number, ie "15"
     * Booleans are just a string of that boolean, ie "true"
     * Strings must be quoted and escaped, ie "\"aString\""
     *    A quote function is provided in the JsonHelperFunctions file
     * Null must be a string of null, ie "null"
     * Anything else should be mapped to a JsonBase and toString()'ed
     *
     * @return A valid Json String
     */
    fun rawJsonSerialize(): String

}
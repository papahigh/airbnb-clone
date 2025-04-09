package search

import org.json.JSONObject
import org.junit.jupiter.api.Assertions

fun assertJson(actual: JSONObject?, expected: String) {
    Assertions.assertEquals(actual.asJson(), expected.asJson())
}

fun JSONObject?.asJson() = this?.toString()
fun String.asJson() = JSONObject(this).toString()

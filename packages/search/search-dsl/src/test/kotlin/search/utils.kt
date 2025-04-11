package search

import org.intellij.lang.annotations.Language
import org.json.JSONObject
import org.junit.jupiter.api.Assertions

fun assertJson(actual: JSONObject?, @Language("JSON") expected: String) {
    Assertions.assertEquals(actual.asJson(), expected.asJson())
}

fun JSONObject?.asJson() = this?.toString(4)
fun String.asJson() = JSONObject(this).toString(4)

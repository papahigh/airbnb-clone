package search.compose

import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.compose.named
import search.dsl.query.term.ids


class NamedDslBuilderTest {

    val builder = named<IdsRequest> {
        name = "myName"
        then = ids {
            values = { it.ids }
        }
    }

    @Test
    fun `should build named dsl correctly`() {
        val output = builder.build(IdsRequest(listOf(1, 2, 3)))
        assertJson(output, """{ "myName": { "ids": { "values": [ 1, 2, 3 ], "boost": 1 } } }""")
    }

    @Test
    fun `should not build for null value`() {
        assertNull(builder.build(IdsRequest(null)))
    }

    data class IdsRequest(val ids: List<Int>? = null)
}
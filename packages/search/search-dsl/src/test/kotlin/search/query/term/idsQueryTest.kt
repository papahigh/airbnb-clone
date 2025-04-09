package search.query.term

import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.query.term.ids


class IdsQueryDslBuilderTest {

    val builder = ids<IdsRequest> {
        values = { it.ids }
        boost = 0.7
    }

    @Test
    fun `should handle list of ids`() {
        val output = builder.build(IdsRequest(listOf(1, 2, 3)))
        assertJson(output, """{ "ids": { "values": [ 1, 2, 3 ], "boost": 0.7 } }""")
    }

    @Test
    fun `should handle empty list`() {
        assertNull(builder.build(IdsRequest(listOf())))
    }

    @Test
    fun `should handle null values`() {
        assertNull(builder.build(IdsRequest(null)))
    }

    data class IdsRequest(val ids: List<Int>? = null)
}

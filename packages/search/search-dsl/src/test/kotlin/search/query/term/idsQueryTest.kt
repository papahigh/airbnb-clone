package search.query.term

import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.query.term.ids


class IdsQueryDslBuilderTest {

    data class Props(val ids: List<Int>? = null)

    val builder = ids<Props> {
        values = { it.ids }
        boost = 0.7
    }

    @Test
    fun `should handle list of ids`() {
        val output = builder.build(Props(listOf(1, 2, 3)))
        assertJson(output, """{ "ids": { "values": [ 1, 2, 3 ], "boost": 0.7 } }""")
    }

    @Test
    fun `should handle missing values`() {
        assertNull(builder.build(Props(null)))
        assertNull(builder.build(Props(listOf())))
        assertNull(builder.build(Props(emptyList())))
    }
}

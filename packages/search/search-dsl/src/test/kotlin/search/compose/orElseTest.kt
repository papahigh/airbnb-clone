package search.compose

import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.compose.orElse
import search.dsl.query.matchAll
import search.dsl.query.term.ids


class OrElseDslBuilderTest {

    data class Props(val ids: List<Int>? = null)

    val builder = ids<Props> { values = { it.ids } }.orElse(matchAll())

    @Test
    fun `should produce target dsl output`() {
        var output = builder.build(Props(listOf(1, 2, 3)))
        assertJson(output, """{ "ids": { "values": [ 1, 2, 3 ], "boost": 1 } }""")
    }

    @Test
    fun `should produce fallback dsl output`() {
        var output = builder.build(Props(null))
        assertJson(output, """{ "match_all" : {} }""")
    }
}

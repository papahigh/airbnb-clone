package search.compose

import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.compose.orElse
import search.dsl.query.term.ids
import search.dsl.query.matchAll


class OrElseDslBuilderTest {

    val target = ids<IdsRequest> { values = { it.ids } }
    val fallback = matchAll<IdsRequest>()

    @Test
    fun `should produce target dsl output`() {
        var output = target.orElse(fallback).build(IdsRequest(listOf(1, 2, 3)))
        assertJson(output, """{ "ids": { "values": [ 1, 2, 3 ], "boost": 1 } }""")
    }

    @Test
    fun `should produce fallback dsl output`() {
        var output = target.orElse(fallback).build(IdsRequest(null))
        assertJson(output, """{ "match_all" : {} }""")
    }

    data class IdsRequest(val ids: List<Int>? = null)
}

package search.query.term

import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.query.term.exists


class ExistsQueryDslBuilderTest {

    val builder = exists<Any> {
        field = "XYZ"
        boost = 0.1
    }

    @Test
    fun `should build an exists query`() {
        val output = builder.build(Any())
        assertJson(output, """{ "exists": { "field": "XYZ", "boost": 0.1 } }""")
    }
}

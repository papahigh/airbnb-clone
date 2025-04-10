package search.query.term

import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.query.term.terms
import kotlin.test.assertNull


class TermsQueryDslBuilderTest {
    class Props(val terms: List<String>?)

    val builder = terms<Props> {
        field = "XYZ"
        boost = 1.0
        values = { it.terms }
    }

    @Test
    fun `should build a terms query`() {
        assertJson(
            builder.build(Props(listOf("A", "B", "C"))),
            """
            {
                "terms": {
                    "XYZ": ["A", "B", "C"],
                    "boost": 1.0
                }
            }
            """
        )
    }

    @Test
    fun `should not build a terms query with no terms`() {
        assertNull(builder.build(Props(null)))
        assertNull(builder.build(Props(listOf())))
        assertNull(builder.build(Props(emptyList())))
    }
}

package search.query.term

import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.query.term.term
import kotlin.test.assertNull


class TermQueryDslBuilderTest {

    class Props(val query: String?)

    val builder = term<Props> {
        field = "ABC"
        value = { it.query }
        boost = 2.0
        caseInsensitive = true
    }

    @Test
    fun `should build a term query`() {
        assertJson(
            builder.build(Props("foo")),
            """
            { 
                "term": { 
                    "ABC": { 
                        "value": "foo",
                        "boost": 2.0,
                        "case_insensitive": true
                    }
                }
            }
            """
        )
    }

    @Test
    fun `should not build for null value`() {
        assertNull(builder.build(Props(null)))
    }
}

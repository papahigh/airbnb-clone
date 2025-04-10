package search.query.joining

import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.query.joining.nested
import search.dsl.query.term.term


class NestedQueryDslBuilderTest {

    val builder = nested<Any> {
        path = "comments"
        query = term {
            field = "tag"
            value = { "Elasticsearch" }
        }
    }


    @Test
    fun `should build nested query`() {
        assertJson(
            builder.build(Any()),
            """
            {
                "nested": {
                        "path": "comments",
                        "query": {
                            "term": {
                                "tag": {
                                    "value": "Elasticsearch",
                                    "boost": 1.0,
                                    "case_insensitive": false
                                }
                            }
                        },
                        "ignore_unmapped": false
                }
            }
            """
        )
    }
}

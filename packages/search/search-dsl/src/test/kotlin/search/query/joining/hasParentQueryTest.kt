package search.query.joining

import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.query.joining.hasParent
import search.dsl.query.term.term


class HasParentQueryDslBuilderTest {

    val builder = hasParent<Any> {
        type = "parent"
        query = term {
            field = "tag"
            value = { "Elasticsearch" }
        }
        score = true
        ignoreUnmapped = true
    }

    @Test
    fun `should build hasParent query`() {
        assertJson(
            builder.build(Any()),
            """
            {
                "has_parent": {
                    "parent_type": "parent",
                    "query": {
                        "term": {
                            "tag": {
                                "value": "Elasticsearch",
                                "boost": 1.0,
                                "case_insensitive": false
                            }
                        }
                    },
                    "score": true,
                    "ignore_unmapped": true
                }
            }
            """
        )
    }
}
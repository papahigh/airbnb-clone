package search.query.joining

import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.query.joining.hasChild
import search.dsl.query.matchAll


class HasChildQueryDslBuilderTest {

    class Props(val minChildren: Int? = null, val maxChildren: Int? = null)

    val builder = hasChild<Props> {
        query = matchAll()
        type = "blog_post"
        minChildren = { it.minChildren }
        maxChildren = { it.maxChildren }
        ignoreUnmapped = true
    }


    @Test
    fun `should build hasChild query`() {
        assertJson(
            builder.build(Props(1, 3)),
            """
            {
                "has_child": {
                    "type": "blog_post",
                    "query": {
                        "match_all": {}
                    },
                    "min_children": 1,
                    "max_children": 3,
                    "ignore_unmapped": true
                }
            }
            """
        )
    }

    @Test
    fun `should build hasChild query when props not set`() {
        assertJson(
            builder.build(Props(null, null)),
            """
            {
                "has_child": {
                    "type": "blog_post",
                    "query": {
                        "match_all": {}
                    },
                    "ignore_unmapped": true
                }
            }
            """
        )
    }
}
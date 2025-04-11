package search.request

import search.assertJson
import search.dsl.query.term.term
import search.dsl.request.searchRequest
import kotlin.test.Test


class SearchRequestDslBuilderTest {

    class Props(val from: Int? = null, val size: Int? = null, val term: String? = null)

    val builder = searchRequest<Props> {
        query = term {
            field = "keywords"
            value = { it.term }
        }
        from = { it.from }
        size = { it.size }
    }

    @Test
    fun `should build a search request`() {
        assertJson(
            builder.build(Props(from = 0, size = 10, term = "kotlin")),
            """
            {
              "query": { 
                "term": {
                  "keywords": { 
                    "value": "kotlin", 
                    "boost": 1.0, 
                    "case_insensitive": false
                  }
                }
              }, 
              "from": 0,
              "size": 10
            }
            """
        )
    }

    @Test
    fun `should build matchAll fallback`() {
        assertJson(
            builder.build(Props(from = 0, size = 10)),
            """
            {
              "query": { 
                "match_all" : {}
              }, 
              "from": 0,
              "size": 10
            }
            """
        )
    }

    @Test
    fun `should build not include pagination`() {
        assertJson(
            builder.build(Props()),
            """
            {
              "query": { 
                "match_all" : {}
              }
            }
            """
        )
    }

}

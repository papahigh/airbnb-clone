package search.query

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.query.matchAll
import search.dsl.query.matchNone


class MatchAllTest {

    @Nested
    inner class MatchAllQueryDslBuilderTest {
        val builder = matchAll<Any>()

        @Test
        fun `should build a match_all query`() {
            val output = builder.build(Any())
            assertJson(output, """{ "match_all": {} }""")
        }
    }

    @Nested
    inner class MatchNoneQueryDslBuilderTest {
        val builder = matchNone<Any>()

        @Test
        fun `should build a match_none query`() {
            val output = builder.build(Any())
            assertJson(output, """{ "match_none": {} }""")
        }
    }
}

package search.compose

import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.compose.branch
import search.dsl.query.matchAll
import search.dsl.query.matchNone


class BranchDslBuilderTest {

    @Test
    fun `should proceed with 'then' branch if condition is true`() {
        val builder = branch<Request> {
            testIf = { it.condition }
            thenDo = matchAll()
            orElse = matchNone()
        }

        val output = builder.build(Request(true))
        assertJson(output, """{ "match_all": {} }""")
    }

    @Test
    fun `should proceed with 'else' branch if condition is false`() {
        val builder = branch<Request> {
            testIf = { it.condition }
            thenDo = matchAll()
            orElse = matchNone()
        }

        val output = builder.build(Request(false))
        assertJson(output, """{ "match_none": {} }""")
    }

    @Test
    fun `should not emit if not configured properly`() {
        val builder = branch<Request> {}
        assertNull(builder.build(Request(true)))
    }

    class Request(val condition: Boolean)
}
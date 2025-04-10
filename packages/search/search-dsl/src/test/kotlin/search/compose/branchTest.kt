package search.compose

import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.compose.branch
import search.dsl.query.matchAll
import search.dsl.query.matchNone


class BranchDslBuilderTest {

    class Props(val condition: Boolean)

    @Test
    fun `should proceed with 'then' branch if condition is true`() {
        val builder = branch<Props> {
            testIf = { it.condition }
            thenDo = matchAll()
            orElse = matchNone()
        }

        val output = builder.build(Props(true))
        assertJson(output, """{ "match_all": {} }""")
    }

    @Test
    fun `should proceed with 'else' branch if condition is false`() {
        val builder = branch<Props> {
            testIf = { it.condition }
            thenDo = matchAll()
            orElse = matchNone()
        }

        val output = builder.build(Props(false))
        assertJson(output, """{ "match_none": {} }""")
    }

    @Test
    fun `should handle nested condition`() {
        class Props(val first: Boolean, val second: Boolean)

        val builder = branch<Props> {
            testIf = { it.first }
            thenDo = branch {
                testIf = { it.second }
                thenDo = matchNone()
                orElse = matchAll()
            }
            orElse = matchNone()
        }

        assertJson(builder.build(Props(first = true, second = false)), """{ "match_all": {} }""")
    }

    @Test
    fun `should not emit if not configured properly`() {
        val builder = branch<Props> {}
        assertNull(builder.build(Props(true)))
    }
}
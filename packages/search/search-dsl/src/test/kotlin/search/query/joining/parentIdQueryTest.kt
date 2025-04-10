package search.query.joining

import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.query.joining.parentId
import kotlin.test.assertNull


class ParentIdQueryDslBuilderTest {

    class Props(val parentId: Int? = null)

    val builder = parentId<Props> {
        type = "my-child"
        id = { it.parentId }
        ignoreUnmapped = true
    }

    @Test
    fun `should build parentId query`() {
        assertJson(
            builder.build(Props(123)),
            """
            {
                "parent_id": {
                    "type": "my-child",
                    "id": 123,
                    "ignore_unmapped": true
                }
            }
            """
        )
    }

    @Test
    fun `should not build query if missing props`() {
        assertNull(builder.build(Props()))
    }
}

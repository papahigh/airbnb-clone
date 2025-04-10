package search.query.term

import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.query.term.range
import kotlin.test.assertNull


class RangeQueryDslBuilderTest {

    class Props(val gte: Int? = null, val lte: Int? = null, val gt: Int? = null, val lt: Int? = null)

    val builder = range<Props> {
        gt = { it.gt }
        gte = { it.gte }
        lt = { it.lt }
        lte = { it.lte }
        field = "some.price.field"
        boost = 0.333
        format = "YYYY"
        timeZone = "Europe/Paris"
        relation = "INTERSECTS"
    }

    @Test
    fun `should build a range query`() {
        assertJson(
            builder.build(Props(gte = 10, lt = 25)),
            """
            {
                "range": {
                    "some.price.field": {
                        "lt": 25,
                        "gte": 10,
                        "format": "YYYY",
                        "boost": 0.333,
                        "time_zone": "Europe/Paris", 
                        "relation":"INTERSECTS"
                    }
                }
            }
            """
        )
    }

    @Test
    fun `should not build a range query when no bounds are provided`() {
        assertNull(builder.build(Props()))
    }
}

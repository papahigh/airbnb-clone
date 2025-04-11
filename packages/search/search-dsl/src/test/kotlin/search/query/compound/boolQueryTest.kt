package search.query.compound

import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.GeoBoundingBox
import search.dsl.GeoPoint
import search.dsl.compose.orElse
import search.dsl.query.compound.bool
import search.dsl.query.geo.geoBoundingBox
import search.dsl.query.matchAll
import search.dsl.query.term.range
import search.dsl.query.term.term


class BoolQueryDslBuilderTest {

    class Props(
        val term: String? = null,
        val ignore: String? = null,
        val bbox: GeoBoundingBox? = null,
        val priceFrom: Int? = null,
        val priceTo: Int? = null
    )

    val builder = bool<Props> {
        boost = 1.5
        minimumShouldMatch = 1
        should {
            term {
                field = "title"
                value = { it.term }
                boost = 2.2
            }
        }
        must {
            range {
                field = "price.field"
                gte = { it.priceFrom }
                lte = { it.priceTo }
            }
        }
        mustNot {
            term {
                field = "ignore"
                value = { it.ignore }
            }
        }
        filter {
            geoBoundingBox {
                field = "location"
                value = { it.bbox }
                ignoreUnmapped = true
            }
        }
    }.orElse { matchAll() }

    @Test
    fun `should build a bool query`() {
        assertJson(
            builder.build(
                Props(
                    term = "foo",
                    ignore = "bar",
                    priceFrom = 10,
                    priceTo = 20,
                    bbox = GeoBoundingBox(topLeft = GeoPoint(40.73, -74.1), bottomRight = GeoPoint(40.01, -71.12))
                )
            ),
            """
            {
              "bool": {
                "should": [
                  {
                    "term": {
                      "title": {
                        "boost": 2.2,
                        "value": "foo",
                        "case_insensitive": false
                      }
                    }
                  }
                ],
                "must": [
                  {
                    "range": {
                      "price.field": {
                        "gte": 10,
                        "lte": 20,
                        "boost": 1
                      }
                    }
                  }
                ],
                "must_not": [
                  {
                    "term": {
                      "ignore": {
                        "case_insensitive": false,
                        "boost": 1,
                        "value": "bar"
                      }
                    }
                  }
                ],
                "filter": [
                  {
                    "geo_bounding_box": {
                      "location": {
                        "top_left": {
                          "lon": -74.1,
                          "lat": 40.73
                        },
                        "bottom_right": {
                          "lon": -71.12,
                          "lat": 40.01
                        }
                      },
                      "ignore_unmapped": true
                    }
                  }
                ],
                "boost": 1.5,
                "minimum_should_match": 1
              }
            }
            """
        )
    }

    @Test
    fun `should build a match all fallback`() {
        assertJson(builder.build(Props()), """{ "match_all": {} }""")
    }
}

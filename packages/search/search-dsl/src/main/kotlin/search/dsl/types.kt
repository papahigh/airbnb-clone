package search.dsl

import org.json.JSONObject

/**
 * Defines a DSL builder contract for ElasticSearch compliant search DSL builder.
 */
interface DslBuilder<T> {

    fun build(input: T): JSONObject?

    companion object {
        fun <T> noop(): DslBuilder<T> = object : DslBuilder<T> {
            override fun build(input: T): JSONObject? = null
        }
    }
}

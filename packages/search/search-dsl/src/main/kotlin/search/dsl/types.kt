package search.dsl

import org.json.JSONObject

/**
 * Defines a DSL builder contract for ElasticSearch compliant JSON request.
 */
interface DslBuilder<Props> {

    fun build(props: Props): JSONObject?

    companion object {
        fun <Props> noop(): DslBuilder<Props> = object : DslBuilder<Props> {
            override fun build(props: Props): JSONObject? = null
        }
    }
}

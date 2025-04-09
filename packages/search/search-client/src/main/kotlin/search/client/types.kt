package search.client

interface SearchClient<T, R> {
    fun count(request: T): CountResponse
    fun search(request: T): SearchResponse<R>
}

class CountResponse

class SearchResponse<R>

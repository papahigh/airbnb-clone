package search.client

interface SearchClient<Props, Hit> {
    fun count(props: Props): CountResponse
    fun search(props: Props): SearchResponse<Hit>
}

class CountResponse

class SearchResponse<Hit>

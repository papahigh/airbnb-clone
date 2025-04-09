rootProject.name = "airbnb-clone"

include("main-backend")
include("packages:search")
include("packages:search:search-client")
include("packages:search:search-client-opensearch")
include("packages:search:search-dsl")
include("packages:storage")
include("packages:storage:pipeline")
include("packages:storage:pipeline-image")
include("packages:storage:storage")
include("packages:storage:storage-filesystem")

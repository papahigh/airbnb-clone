# pipeline-image

The **`pipeline-image`** module provides a flexible and extensible API for building image processing pipelines.
The module offers integration with [Deep Java Library](https://djl.ai/),
[ImageMagick](https://imagemagick.org/), and [metadata-extractor](https://github.com/drewnoakes/metadata-extractor).


## Kotlin API
This module exposes Kotlin DSL to define a sequence of processing steps
that can be applied to image artifacts. The steps include classification, 
metadata extraction, and general image manipulation using ImageMagick.

```kotlin
import pipeline.MediaType
import pipeline.image.classifyImage
import pipeline.image.extractMetadata
import pipeline.image.imageMagick
import pipeline.image.imagePipeline
import pipeline.tempBlob
import java.nio.file.Files
import java.nio.file.Path


val pipeline = imagePipeline {
    imageMagick {
        outputName("thumbnail_jpg")
        outputType(MediaType.IMAGE_JPEG)
        resize("300")
        quality("80%")
        strip()
    }
    imageMagick {
        outputName("thumbnail_webp")
        outputType(MediaType.IMAGE_WEBP)
        resize("200x200")
        quality("80%")
        strip()
    }
    classifyImage {
        outputName("classification")
        predictorFactory(zooModel::newPredictor)
        topK(10)
    }
    extractMetadata {}
}

var blob = tempBlob("dog.jpg") {
    content(Files.newInputStream(Path.of("dog.jpg")))
    mediaType(MediaType.IMAGE_JPEG)
}

pipeline.process(blob).use { output ->
    println(output.getArtifact("thumbnail_jpg"))
    println(output.getArtifact("thumbnail_webp"))
    println(output.getArtifact("classification"))
    println(output.getArtifact("metadata"))
}
```
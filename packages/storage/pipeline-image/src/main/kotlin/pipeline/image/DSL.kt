package pipeline.image

import pipeline.Pipeline
import pipeline.image.ImagePipeline.PipelineBuilder.*


@DslMarker
@Target(AnnotationTarget.TYPE)
annotation class ImagePipelineDslMarker

fun imagePipeline(init: @ImagePipelineDslMarker ImagePipeline.PipelineBuilder.() -> Unit): Pipeline {
    return ImagePipeline.builder().also(init).build()
}

fun ImagePipeline.PipelineBuilder.imageMagick(init: @ImagePipelineDslMarker ImageMagickBuilder.() -> Unit) {
    imageMagick().also(init).and()
}

fun ImagePipeline.PipelineBuilder.classifyImage(init: @ImagePipelineDslMarker ClassifyImageBuilder.() -> Unit) {
    classifyImage().also(init).and()
}

fun ImagePipeline.PipelineBuilder.extractMetadata(init: @ImagePipelineDslMarker ExtractMetadataBuilder.() -> Unit) {
    extractMetadata().also(init).and()
}

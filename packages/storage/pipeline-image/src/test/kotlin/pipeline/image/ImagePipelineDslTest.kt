package pipeline.image

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledIf
import pipeline.MediaType.IMAGE_JPEG
import pipeline.MediaType.IMAGE_WEBP
import pipeline.UnitOfWork.ArtifactNames
import pipeline.tempBlob
import java.nio.file.Files
import java.nio.file.Path


class ImagePipelineDslTest {

    @Test
    @DisabledIf(
        value = "pipeline.image.ImagePipelineTest#isDisabled",
        disabledReason = "ImageMagic is not available!"
    )
    fun testImagePipelineDsl() {
        ClassifyImageTest.TestPredictorFactory().use { factory ->

            val pipeline = imagePipeline {
                imageMagick {
                    outputName("THUMBNAIL_JPG")
                    outputType(IMAGE_JPEG)
                    resize("300")
                    quality("80%")
                    strip()
                }
                imageMagick {
                    outputName("THUMBNAIL_WEBP")
                    outputType(IMAGE_WEBP)
                    resize("300")
                    quality("80%")
                    strip()
                }
                imageMagick {
                    outputName("CLASSIFY_INPUT")
                    outputType(IMAGE_JPEG)
                    resize("256x256")
                    quality("80%")
                    strip()
                }
                classifyImage {
                    inputName("CLASSIFY_INPUT")
                    outputName("CLASSIFICATION")
                    predictorFactory(factory::create)
                    topK(10)
                }
                extractMetadata { }
            }

            var blob = tempBlob("DSCN0021.jpg") {
                content(Files.newInputStream(getResource("images/jpg/gps/DSCN0021.jpg")))
                mediaType(IMAGE_JPEG)
            }

            pipeline.process(blob).use { output ->
                assertAll(
                    { assertTrue(output.hasArtifact(ArtifactNames.INPUT_ARTIFACT_NAME)) },
                    { assertTrue(output.hasArtifact(ArtifactNames.METADATA_ARTIFACT_NAME)) },
                    { assertTrue(output.hasArtifact("THUMBNAIL_JPG")) },
                    { assertTrue(output.hasArtifact("THUMBNAIL_WEBP")) },
                    { assertTrue(output.hasArtifact("CLASSIFY_INPUT")) },
                    { assertTrue(output.hasArtifact("CLASSIFICATION")) }
                )
            }
        }
    }

    fun getResource(name: String): Path {
        val uri = ImagePipelineTest::class.java.getClassLoader().getResource(name)!!.toURI()
        return Path.of(uri)
    }
}
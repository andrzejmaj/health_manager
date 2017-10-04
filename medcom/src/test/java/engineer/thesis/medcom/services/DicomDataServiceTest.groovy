package engineer.thesis.medcom.services

import engineer.thesis.medcom.model.AttributeModules
import engineer.thesis.medcom.model.DicomSeries
import engineer.thesis.medcom.model.DicomStudy
import engineer.thesis.medcom.model.core.AttributeModule
import engineer.thesis.medcom.model.core.DicomAttribute
import engineer.thesis.medcom.model.core.DicomAttributeTag
import engineer.thesis.medcom.model.error.DataExtractionException
import org.dcm4che3.io.DicomInputStream
import spock.lang.Specification

import java.util.function.Consumer
import java.util.stream.Collectors

/**
 * @author MKlaman
 * @since 03.10.2017
 */
class DicomDataServiceTest extends Specification {

    private static final AttributeModule expectedStudyAttributes = DicomStudy.attributeModule
    private static final AttributeModule expectedSeriesAttributes = DicomSeries.attributeModule
    private static final AttributeModule expectedInstanceAttributes = AttributeModule.combine(
            AttributeModules.commonModule,
            AttributeModules.generalImageModule,
    )
    private static final String studyAttributeValue = 'study attr'
    private static final String seriesAttributeValue = 'series attr'
    private static final String instanceAttributeValue = 'instance attr'


    DicomAttributesReader dicomAttributesReaderMock
    DicomDataService dicomDataService

    void setup() {
        dicomAttributesReaderMock = Mock()
        dicomDataService = new DicomDataService(dicomAttributesReaderMock)
    }

    def '.extract should create dicom object with correctly distributed attributes'() {
        setup:
        def dicomInputStream = Mock(DicomInputStream)
        def mockAttributes = mockAttributes()
        1 * dicomAttributesReaderMock.read(_ as DicomInputStream, _ as Consumer<DicomAttribute>) >>
                { inputStream, Consumer<DicomAttribute> consumer ->
                    inputStream == dicomInputStream
                    mockAttributes.forEach(consumer) // reader passing attributes via callback
                }

        when:
        def result = dicomDataService.extract(dicomInputStream)

        then:
        result != null
        with(result.instance) {
            instanceUID == instanceAttributeValue
            attributes.size() == expectedInstanceAttributes.attributeTags.size()
            attributes.every {
                it.value == instanceAttributeValue
            }
        }
        with(result.series) {
            instanceUID == seriesAttributeValue
            attributes.size() == expectedSeriesAttributes.attributeTags.size()
            attributes.every {
                it.value == seriesAttributeValue
            }
        }
        with(result.study) {
            instanceUID == studyAttributeValue
            attributes.size() == expectedStudyAttributes.attributeTags.size()
            attributes.every {
                it.value == studyAttributeValue
            }
        }
    }

    def '.extract should fail on reader exception'() {
        setup:
        def dicomInputStream = Mock(DicomInputStream)
        dicomAttributesReaderMock.read(_, _) >> { throw new DataExtractionException('test exception') }

        when:
        dicomDataService.extract(dicomInputStream)

        then:
        thrown(DataExtractionException)
    }

    def '.extract should fail on dicom object creation exception when attributes are missing'() {
        setup:
        def mockAttributes = mockAttributes()
        mockAttributes.removeIf({ (it.getValue() == seriesAttributeValue) })
        def dicomInputStream = Mock(DicomInputStream)
        dicomAttributesReaderMock.read(_ as DicomInputStream, _ as Consumer<DicomAttribute>) >>
                { inputStream, Consumer<DicomAttribute> consumer ->
                    mockAttributes.forEach(consumer)
                }

        when:
        dicomDataService.extract(dicomInputStream)

        then:
        DataExtractionException ex = thrown()
        ex.cause instanceof IllegalArgumentException
        ex.cause.message.contains('missing required attribute')
    }

    List<DicomAttribute> mockAttributes() {
        def allAttributes = []
        allAttributes.addAll createAttributes(expectedStudyAttributes.attributeTags, studyAttributeValue)
        allAttributes.addAll createAttributes(expectedSeriesAttributes.attributeTags, seriesAttributeValue)
        allAttributes.addAll createAttributes(expectedInstanceAttributes.attributeTags, instanceAttributeValue)
        return allAttributes
    }

    List<DicomAttribute> createAttributes(Collection<DicomAttributeTag> tags, String value) {
        return tags
                .stream()
                .map({ tag -> new DicomAttribute(tag.code, value, tag.name) })
                .collect(Collectors.toList())
    }
}

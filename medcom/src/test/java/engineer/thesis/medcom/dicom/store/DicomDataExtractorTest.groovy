package engineer.thesis.medcom.dicom.store

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
class DicomDataExtractorTest extends Specification {

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
    DicomDataExtractor dicomDataExtractor

    void setup() {
        dicomAttributesReaderMock = Mock()
        dicomDataExtractor = new DicomDataExtractor(dicomAttributesReaderMock)
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
        def result = dicomDataExtractor.extract(dicomInputStream)

        then:
        result != null
        with(result.getInstance()) {
            getInstanceUID() == instanceAttributeValue
            getAttributes().size() == expectedInstanceAttributes.getAttributeTags().size()
            assertAllAttributesValue(getAttributes(), instanceAttributeValue)
        }
        with(result.getSeries()) {
            getInstanceUID() == seriesAttributeValue
            getAttributes().size() == expectedSeriesAttributes.getAttributeTags().size()
            assertAllAttributesValue(getAttributes(), seriesAttributeValue)
        }
        with(result.getStudy()) {
            getInstanceUID() == studyAttributeValue
            getAttributes().size() == expectedStudyAttributes.getAttributeTags().size()
            assertAllAttributesValue(getAttributes(), studyAttributeValue)
        }
    }

    def '.extract should fail on reader exception'() {
        setup:
        def dicomInputStream = Mock(DicomInputStream)
        dicomAttributesReaderMock.read(_, _) >> { throw new DataExtractionException('test exception') }

        when:
        dicomDataExtractor.extract(dicomInputStream)

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
        dicomDataExtractor.extract(dicomInputStream)

        then:
        IllegalArgumentException ex = thrown()
        ex.message.contains('missing required attribute')
    }

    def assertAllAttributesValue(Collection<DicomAttribute> attributes, String expectedValue) {
        attributes.forEach({ attribute ->
            assert attribute.getValue() == expectedValue
        })
    }

    List<DicomAttribute> mockAttributes() {
        def allAttributes = []
        allAttributes << createAttributes(expectedStudyAttributes.getAttributeTags(), studyAttributeValue)
        allAttributes << createAttributes(expectedSeriesAttributes.getAttributeTags(), seriesAttributeValue)
        allAttributes << createAttributes(expectedInstanceAttributes.getAttributeTags(), instanceAttributeValue)
        return allAttributes
    }

    List<DicomAttribute> createAttributes(Collection<DicomAttributeTag> tags, String value) {
        return tags
                .stream()
                .map({ tag -> new DicomAttribute(tag.getCode(), value, tag.getName()) })
                .collect(Collectors.toList())
    }
}

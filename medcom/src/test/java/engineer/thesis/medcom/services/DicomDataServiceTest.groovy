package engineer.thesis.medcom.services

import engineer.thesis.medcom.model.*
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
 * @link http://spockframework.org/spock/docs/1.1/all_in_one.html
 */
class DicomDataServiceTest extends Specification {

    private static final AttributeModule expectedPatientAttributes = DicomPatient.attributeModule

    private static final AttributeModule expectedModalityAttributes = DicomModality.attributeModule
            .subtract(expectedPatientAttributes)

    private static final AttributeModule expectedStudyAttributes = DicomStudy.attributeModule
            .subtract(expectedPatientAttributes, expectedModalityAttributes)

    private static final AttributeModule expectedSeriesAttributes = DicomSeries.attributeModule
            .subtract(expectedPatientAttributes, expectedModalityAttributes, expectedStudyAttributes)

    private static final AttributeModule expectedInstanceAttributes = AttributeModule.combine(
            AttributeModules.commonModule,
            AttributeModules.generalImageModule,
    ).subtract(expectedPatientAttributes, expectedModalityAttributes, expectedStudyAttributes, expectedSeriesAttributes)

    private static final String patientAttributeValue = 'patient attr'
    private static final String modalityAttributeValue = 'modality attr'
    private static final String studyAttributeValue = 'study attr'
    private static final String seriesAttributeValue = 'series attr'
    private static final String instanceAttributeValue = 'instance attr'


    DicomAttributesReader dicomAttributesReaderMock
    DicomDataService dicomDataService

    void setup() {
        dicomAttributesReaderMock = Mock()
        dicomDataService = new DicomDataService(dicomAttributesReaderMock)
    }

    def '.create should create dicom object with correctly distributed attributes'() {
        setup:
        def dicomInputStream = Mock(DicomInputStream)
        def mockAttributes = mockAttributes()
        1 * dicomAttributesReaderMock.read(_ as DicomInputStream, _ as Consumer<DicomAttribute>) >>
                { inputStream, Consumer<DicomAttribute> consumer ->
                    inputStream == dicomInputStream
                    mockAttributes.forEach(consumer) // reader passing attributes via callback
                }

        when:
        def dicomData = dicomDataService.create(dicomInputStream)

        then:
        dicomData != null
        with(dicomData.instance) {
            instanceUID == instanceAttributeValue
            attributes.size() == expectedInstanceAttributes.attributeTags.size()
            attributes.every {
                it.value == instanceAttributeValue
            }
        }
        with(dicomData.series) {
            instanceUID == seriesAttributeValue
            attributes.size() == expectedSeriesAttributes.attributeTags.size()
            attributes.every {
                it.value == seriesAttributeValue
            }
        }
        with(dicomData.study) {
            instanceUID == studyAttributeValue
            attributes.size() == expectedStudyAttributes.attributeTags.size()
            attributes.every {
                it.value == studyAttributeValue
            }
        }
        with(dicomData.modality) {
            applicationEntity == modalityAttributeValue
            type == modalityAttributeValue
            stationName == modalityAttributeValue
            attributes.size() == expectedModalityAttributes.attributeTags.size()
            attributes.every {
                it.value == modalityAttributeValue
            }
        }
        with(dicomData.patient) {
            pesel == patientAttributeValue
            attributes.size() == expectedPatientAttributes.attributeTags.size()
            attributes.every {
                it.value == patientAttributeValue
            }
        }
    }

    def '.create should fail on reader exception'() {
        setup:
        def dicomInputStream = Mock(DicomInputStream)
        dicomAttributesReaderMock.read(_, _) >> { throw new DataExtractionException('test exception') }

        when:
        dicomDataService.create(dicomInputStream)

        then:
        thrown(DataExtractionException)
    }

    def '.create should fail on dicom object creation exception when attributes are missing'() {
        setup:
        def mockAttributes = mockAttributes()
        mockAttributes.removeIf({ (it.getValue() == seriesAttributeValue) })
        def dicomInputStream = Mock(DicomInputStream)
        dicomAttributesReaderMock.read(_ as DicomInputStream, _ as Consumer<DicomAttribute>) >>
                { inputStream, Consumer<DicomAttribute> consumer ->
                    mockAttributes.forEach(consumer)
                }

        when:
        dicomDataService.create(dicomInputStream)

        then:
        DataExtractionException ex = thrown()
        ex.cause instanceof IllegalArgumentException
        ex.cause.message.contains('missing required attribute')
    }

    List<DicomAttribute> mockAttributes() {
        def allAttributes = []
        allAttributes.addAll createAttributes(expectedPatientAttributes.attributeTags, patientAttributeValue)
        allAttributes.addAll createAttributes(expectedModalityAttributes.attributeTags, modalityAttributeValue)
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

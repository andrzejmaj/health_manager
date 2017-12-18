package engineer.thesis.medcom.model

import engineer.thesis.medcom.model.core.DicomAttribute
import org.dcm4che3.data.Tag
import org.dcm4che3.data.UID
import spock.lang.Specification

import java.time.Month
import java.time.ZoneId

/**
 * @author MKlaman
 * @since 14.10.2017
 */
class DicomInstanceTest extends Specification {

    def 'Instance.Builder should accept any attribute'(DicomAttribute attribute) {
        given:
        def builder = DicomInstance.builder()

        expect:
        builder.accepts(attribute)

        where:
        attribute << [
                createAttribute(Tag.SOPClassUID, '1'),
                createAttribute(Tag.SOPInstanceUID, '2'),
                createAttribute(Tag.InstanceCreationDate, '3'),
                createAttribute(Tag.ImageBoxNumber, '4'),
                createAttribute(Tag.AbortFlag, '5'),
                createAttribute(Tag.StudyInstanceUID, '6'),
                createAttribute(Tag.PatientAge, '7'),
                createAttribute(Tag.SeriesInstanceUID, '8'),
                createAttribute(Tag.Modality, '9'),
                createAttribute(Tag.PatientAge, '10')
        ]

    }

    def 'Instance.Builder should create Instance with attributes with unique codes'() {
        given:
        def builder = DicomInstance.builder()

        when:
        builder.accept(createAttribute(Tag.SOPClassUID, 'SOPClassUID_1'))
        builder.accept(createAttribute(Tag.SOPInstanceUID, 'SOPInstanceUID_1'))
        builder.accept(createAttribute(Tag.SOPClassUID, 'SOPClassUID_2'))
        builder.accept(createAttribute(Tag.SOPInstanceUID, 'SOPInstanceUID_2'))
        builder.accept(createAttribute(Tag.SOPClassUID, 'SOPClassUID_3'))
        builder.accept(createAttribute(Tag.SOPInstanceUID, 'SOPInstanceUID_3'))
        def instance = builder.build()

        then:
        with(instance) {
            attributes.size() == 2
            getAttribute(Tag.SOPClassUID).get().value == 'SOPClassUID_1'
            getAttribute(Tag.SOPInstanceUID).get().value == 'SOPInstanceUID_1'
        }
    }

    def 'Instance should have fields extracted from attributes'() {
        given:
        def instance = DicomInstance.builder()
                .accept(createAttribute(Tag.SOPClassUID, UID.CTImageStorage))
                .accept(createAttribute(Tag.SOPInstanceUID, 'SOPInstanceUID'))
                .accept(createAttribute(Tag.InstanceCreationDate, '20170203'))
                .accept(createAttribute(Tag.InstanceCreationTime, '111530'))
                .build()

        expect:
        with(instance) {
            attributes.size() == 4

            getAttribute(Tag.SOPClassUID).get().value == UID.CTImageStorage
            sopClassName == 'CT Image Storage'

            getAttribute(Tag.SOPInstanceUID).get().value == 'SOPInstanceUID'
            instanceUID == 'SOPInstanceUID'

            getAttribute(Tag.InstanceCreationDate).get().value == '20170203'
            getAttribute(Tag.InstanceCreationTime).get().value == '111530'
        }
        with(instance.creationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) {
            year == 2017
            month == Month.FEBRUARY
            dayOfMonth == 3
            hour == 12 // TODO: that is probably wrong
            minute == 15
            second == 30
            nano == 0
        }
    }

    def 'Instance should create attributes map convertible to json'() {
        given:
        def instance = DicomInstance.builder()
                .accept(createAttribute(Tag.SOPClassUID, UID.CTImageStorage))
                .accept(createAttribute(Tag.SOPInstanceUID, 'SOPInstanceUID'))
                .accept(createAttribute(Tag.SeriesDescription, 'test description 1'))
                .accept(createAttribute(Tag.SeriesDescription, 'test description 2'))
                .build()


        expect:
        with(instance.getAttributesAsMap()) {
            size() == 3
            get('SOPClassUID') == UID.CTImageStorage
            get('SOPInstanceUID') == 'SOPInstanceUID'
            get('SeriesDescription') == 'test description 1'
        }
    }

    def 'Instance should lazily merge with other Instance'() {
        given:
        def instance1 = DicomInstance.builder()
                .accept(createAttribute(Tag.SOPClassUID, UID.CTImageStorage))
                .accept(createAttribute(Tag.SOPInstanceUID, 'base value'))
                .accept(createAttribute(Tag.SeriesDescription, 'base value'))
                .accept(createAttribute(Tag.ApplicatorDescription, 'base value'))
                .build()
        def instance2 = DicomInstance.builder()
                .accept(createAttribute(Tag.SOPClassUID, UID.CTImageStorage))
                .accept(createAttribute(Tag.SOPInstanceUID, 'new value'))
                .accept(createAttribute(Tag.SeriesDescription, 'new value'))
                .accept(createAttribute(Tag.AcquisitionContextDescription, 'new value'))
                .build()
        instance2.setSeriesInstanceUID('new value')

        when:
        instance1.lazyMerge(instance2)

        then:
        with(instance1) {
            attributes.size() == 5

            getAttribute(Tag.SOPInstanceUID).get().value == 'base value'
            instanceUID == 'base value'

            getAttribute(Tag.SeriesDescription).get().value == 'base value'
            getAttribute(Tag.ApplicatorDescription).get().value == 'base value'
            getAttribute(Tag.AcquisitionContextDescription).get().value == 'new value'

            seriesInstanceUID == 'new value'
        }

    }

    private DicomAttribute createAttribute(int code, String value) {
        new DicomAttribute(code, value)
    }
}

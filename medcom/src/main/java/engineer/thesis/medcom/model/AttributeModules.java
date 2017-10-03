package engineer.thesis.medcom.model;

import engineer.thesis.medcom.model.core.AttributeModule;
import org.dcm4che3.data.Tag;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
public final class AttributeModules {

    public final static AttributeModule generalStudyModule = AttributeModule.of(
            Tag.StudyInstanceUID,
            Tag.StudyID,
            Tag.StudyDate,
            Tag.StudyTime,
            Tag.StudyDescription,
            Tag.ReferringPhysicianName,
            Tag.PhysiciansOfRecord,
            Tag.NameOfPhysiciansReadingStudy,
            Tag.AccessionNumber,
            Tag.ReferencedStudySequence,
            Tag.ProcedureCodeSequence
    );

    public final static AttributeModule patientStudyModule = AttributeModule.of(
            Tag.AdmittingDiagnosesDescription,
            Tag.PatientAge,
            Tag.PatientSize,
            Tag.PatientWeight,
            Tag.AdditionalPatientHistory
    );

    public final static AttributeModule generalSeriesModule = AttributeModule.of(
            Tag.SeriesInstanceUID,
            Tag.SeriesNumber,
            Tag.SeriesDescription,
            Tag.Modality,
            Tag.SeriesDate,
            Tag.SeriesTime,
            Tag.Laterality,
            Tag.PerformingPhysicianName,
            Tag.OperatorsName,
            Tag.ProtocolName,
            Tag.BodyPartExamined,
            Tag.PatientPosition,
            Tag.SmallestPixelValueInSeries,
            Tag.LargestPixelValueInSeries,
            Tag.PerformedProcedureStepID,
            Tag.PerformedProcedureStepDescription
    );

    public final static AttributeModule commonModule = AttributeModule.of(
            Tag.SOPClassUID,
            Tag.SOPInstanceUID,
            Tag.SOPInstanceStatus,
            Tag.InstanceNumber,
            Tag.InstanceCreationDate,
            Tag.InstanceCreationTime,
            Tag.TimezoneOffsetFromUTC,
            Tag.InstanceCreatorUID,
            Tag.Manufacturer,
            Tag.ManufacturerModelName,
            Tag.DeviceSerialNumber,
            Tag.SoftwareVersions,
            Tag.InstitutionName,
            Tag.StationName,
            Tag.ContributionDescription
    );

    public final static AttributeModule generalImageModule = AttributeModule.of(
            Tag.InstanceNumber,
            Tag.ContentDate,
            Tag.ContentTime,
            Tag.ImageType,
            Tag.ImageComments,
            Tag.PatientOrientation,
            Tag.AcquisitionNumber,
            Tag.AcquisitionDate,
            Tag.ImagesInAcquisition,
            Tag.QualityControlImage,
            Tag.LossyImageCompression,
            Tag.LossyImageCompressionRatio
    );

    private AttributeModules(){}

}


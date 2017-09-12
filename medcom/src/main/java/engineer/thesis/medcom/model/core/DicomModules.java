package engineer.thesis.medcom.model.core;

import org.dcm4che3.data.Tag;

/**
 * @author MKlaman
 * @since 12.09.2017
 */
public final class DicomModules {

    public final static DicomModule generalStudyModule = DicomModule.of(
            Tag.StudyInstanceUID,
            Tag.StudyID,
            Tag.StudyDate,
            Tag.StudyTime,
            Tag.StudyDescription,
            Tag.ReferringPhysicianName,
            Tag.PhysiciansOfRecord,
            Tag.NameOfPhysiciansReadingStudy,
            Tag.AccessionNumber,
            Tag.ReferencedStudySequence, // TODO sequences ??
            Tag.ProcedureCodeSequence
    );

    public final static DicomModule patientStudyModule = DicomModule.of(
            Tag.AdmittingDiagnosesDescription,
            Tag.PatientAge,
            Tag.PatientSize,
            Tag.PatientWeight,
            Tag.AdditionalPatientHistory
    );

    public final static DicomModule generalSeriesModule = DicomModule.of(
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

    public final static DicomModule commonModule = DicomModule.of(
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

    public final static DicomModule generalImageModule = DicomModule.of(
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

    private DicomModules(){}

}


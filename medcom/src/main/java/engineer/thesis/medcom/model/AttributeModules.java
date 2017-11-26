package engineer.thesis.medcom.model;

import engineer.thesis.medcom.model.core.AttributeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dcm4che3.data.Tag;

/**
 * @author MKlaman
 * @link http://dicom.nema.org/Dicom/2013/output/chtml/part04/sect_I.4.html
 * @link http://dicomlookup.com/dicom-tables.asp
 * @since 12.09.2017
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AttributeModules {

    /**
     * patient level
     **/
    public final static AttributeModule patientModule = AttributeModule.of(
            Tag.PatientID,
            Tag.OtherPatientIDs,
            Tag.PatientName,
            Tag.OtherPatientNames,
            Tag.PatientBirthDate,
            Tag.PatientBirthTime,
            Tag.PatientSex,
            Tag.EthnicGroup,
            Tag.PatientComments,
            Tag.ReferencedPatientSequence
    );
    public final static AttributeModule clinicalTrialSubjectModule = AttributeModule.of(
            Tag.ClinicalTrialSiteName,
            Tag.ClinicalTrialProtocolID,
            Tag.ClinicalTrialProtocolName,
            Tag.ClinicalTrialSiteID,
            Tag.ClinicalTrialSiteName,
            Tag.ClinicalTrialSubjectID,
            Tag.ClinicalTrialSubjectReadingID
    );

    /**
     * modality level
     **/
    public final static AttributeModule generalEquipmentModule = AttributeModule.of(
            Tag.Manufacturer,
            Tag.InstitutionName,
            Tag.InstitutionAddress,
            Tag.StationName,
            Tag.InstitutionalDepartmentName,
            Tag.ManufacturerModelName,
            Tag.DeviceSerialNumber,
            Tag.SoftwareVersions,
            Tag.SpatialResolution,
            Tag.DateOfLastCalibration,
            Tag.TimeOfLastCalibration,
            Tag.PixelPaddingValue
    );
    public final static AttributeModule hardcopyEquipmentModule = AttributeModule.of(
            Tag.Modality,
            Tag.HardcopyCreationDeviceID,
            Tag.HardcopyDeviceManufacturer,
            Tag.HardcopyDeviceManufacturerModelName,
            Tag.HardcopyDeviceSoftwareVersion
    );
    public final static AttributeModule scImageEquipmentModule = AttributeModule.of(
            Tag.Modality,
            Tag.ConversionType,
            Tag.SecondaryCaptureDeviceID,
            Tag.SecondaryCaptureDeviceManufacturer,
            Tag.SecondaryCaptureDeviceManufacturerModelName,
            Tag.SecondaryCaptureDeviceSoftwareVersions,
            Tag.VideoImageFormatAcquired,
            Tag.DigitalImageFormatAcquired
    );
    public final static AttributeModule medcomModalityModule = AttributeModule.of(
            Tag.SourceApplicationEntityTitle
    );

    /**
     * study level
     **/
    public final static AttributeModule generalStudyModule = AttributeModule.of(
            Tag.StudyInstanceUID,
            Tag.StudyID,
            Tag.StudyDate,
            Tag.StudyTime,
            Tag.StudyDescription,
            Tag.ReferringPhysicianName,
            Tag.ReferringPhysicianIdentificationSequence,
            Tag.PhysiciansOfRecord,
            Tag.PhysiciansOfRecordIdentificationSequence,
            Tag.NameOfPhysiciansReadingStudy,
            Tag.PhysiciansReadingStudyIdentificationSequence,
            Tag.AccessionNumber,
            Tag.ReferencedStudySequence,
            Tag.ProcedureCodeSequence
    );
    public final static AttributeModule patientStudyModule = AttributeModule.of(
            Tag.AdmittingDiagnosesDescription,
            Tag.AdmittingDiagnosesCodeSequence,
            Tag.PatientAge,
            Tag.PatientSize,
            Tag.PatientWeight,
            Tag.Occupation,
            Tag.AdditionalPatientHistory
    );
    public final static AttributeModule clinicalTrialStudyModule = AttributeModule.of(
            Tag.ClinicalTrialTimePointID,
            Tag.ClinicalTrialTimePointDescription
    );

    /**
     * series level
     **/
    public final static AttributeModule generalSeriesModule = AttributeModule.of(
            Tag.SeriesInstanceUID,
            Tag.SeriesNumber,
            Tag.SeriesDescription,
            Tag.Modality,
            Tag.SeriesDate,
            Tag.SeriesTime,
            Tag.Laterality,
            Tag.PerformingPhysicianName,
            Tag.PerformingPhysicianIdentificationSequence,
            Tag.OperatorsName,
            Tag.OperatorIdentificationSequence,
            Tag.ProtocolName,
            Tag.BodyPartExamined,
            Tag.PatientPosition,
            Tag.SmallestPixelValueInSeries,
            Tag.LargestPixelValueInSeries,
            Tag.PerformedProcedureStepID,
            Tag.PerformedProcedureStepDescription,
            Tag.PerformedProcedureStepStartDate,
            Tag.PerformedProcedureStepStartTime,
            Tag.ReferencedPerformedProcedureStepSequence,
            Tag.ReferencedPerformedProtocolSequence,
            Tag.RequestAttributesSequence
    );
    public final static AttributeModule clinicalTrialSeriesModule = AttributeModule.of(
            Tag.ClinicalTrialCoordinatingCenterName
    );
    public final static AttributeModule crSeriesModule = AttributeModule.of(
            Tag.BodyPartExamined,
            Tag.ViewPosition,
            Tag.FilterType,
            Tag.CollimatorGridName,
            Tag.FocalSpots,
            Tag.PlateType,
            Tag.PhosphorType
    );
    public final static AttributeModule petSeriesModule = AttributeModule.of(
            Tag.Units,
            Tag.CountsSource,
            Tag.SeriesType,
            Tag.ReprojectionMethod,
            Tag.NumberOfRRIntervals,
            Tag.NumberOfTimeSlots,
            Tag.NumberOfTimeSlices,
            Tag.NumberOfSlices,
            Tag.CorrectedImage,
            Tag.RandomsCorrectionMethod,
            Tag.AttenuationCorrectionMethod,
            Tag.ScatterCorrectionMethod,
            Tag.DecayCorrection,
            Tag.ReconstructionDiameter,
            Tag.ConvolutionKernel,
            Tag.ReconstructionMethod,
            Tag.DetectorLinesOfResponseUsed,
            Tag.AcquisitionStartCondition,
            Tag.AcquisitionTerminationCondition,
            Tag.AcquisitionTerminationConditionData,
            Tag.FieldOfViewShape,
            Tag.FieldOfViewDimensions,
            Tag.GantryDetectorTilt,
            Tag.GantryDetectorSlew,
            Tag.TypeOfDetectorMotion,
            Tag.CollimatorType,
            Tag.CollimatorGridName,
            Tag.AxialAcceptance,
            Tag.AxialMash,
            Tag.TransverseMash,
            Tag.DetectorElementSize,
            Tag.CoincidenceWindowWidth,
            Tag.EnergyWindowRangeSequence,
            Tag.SecondaryCountsType
    );

    /**
     * small fraction of image level
     **/
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
}


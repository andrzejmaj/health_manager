package engineer.thesis.medcom.controllers;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
final class RequestMappings {

    static final class ARCHIVE {
        static final String GET_TREE = "/medcom/archive/tree";
        static final String GET_DICOM = "/medcom/patients/{patientId}/studies/{studyId}/series/{seriesId}/instances/{instanceId}";
    }

    static final class INSTANCES {
        static final String GET_ALL = "/medcom/instances";
        static final String GET_DETAILS = "/medcom/instances/{instanceId}";
        static final String GET_DICOM = "/medcom/instances/{instanceId}/dicom";
        static final String GET_RENDERED_IMAGE = "/medcom/instances/{instanceId}/rendered";
    }

    static final class SERIES {
        static final String GET_DETAILS = "/medcom/series/{seriesId}";
        static final String GET_INSTANCES_LIST = "/medcom/series/{seriesId}/instances";
    }

    static final class STUDIES {
        static final String GET_ALL = "/medcom/studies";
        static final String GET_DETAILS = "/medcom/studies/{studyId}";
        static final String GET_SERIES_LIST = "/medcom/studies/{studyId}/series";
    }

    static final class PATIENT {
        static final String GET_ALL = "/medcom/patients";
        static final String GET_STUDIES_LIST = "/medcom/patients/{patientId}/studies";
    }

    static final class MODALITY {
        static final String GET_ALL = "/medcom/modalities";
        static final String UPDATE = "/medcom/modalities/{aet}";
    }


    private RequestMappings() {
    }
}

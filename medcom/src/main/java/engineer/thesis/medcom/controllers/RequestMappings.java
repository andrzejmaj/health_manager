package engineer.thesis.medcom.controllers;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
final class RequestMappings {

    static final class ARCHIVE {
        static final String GET_TREE = "/medcom/archive/tree";
    }

    static final class INSTANCES {
        static final String GET_ALL = "/medcom/instances";
        static final String GET_INFO = "/medcom/instances/{instanceId}";
        static final String GET_DICOM = "/medcom/instances/{instanceId}/dicom";
        static final String GET_RENDERED_IMAGE = "/medcom/instances/{instanceId}/rendered";
    }

}

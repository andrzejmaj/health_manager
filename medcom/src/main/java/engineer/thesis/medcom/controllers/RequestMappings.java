package engineer.thesis.medcom.controllers;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
final class RequestMappings {

    static final class INSTANCES {
        static final String GET_ALL_INSTANCES = "/medcom/instances";
        static final String GET_INSTANCE_INFO = "/medcom/instances/{instanceId}";
        static final String GET_INSTANCE_DICOM = "/medcom/instances/{instanceId}/dicom";
        static final String GET_INSTANCE_IMAGE = "/medcom/instances/{instanceId}/rendered";
    }

}

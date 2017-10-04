package engineer.thesis.medcom.services;

import engineer.thesis.medcom.model.core.DicomAttribute;
import engineer.thesis.medcom.model.error.DataExtractionException;
import lombok.RequiredArgsConstructor;
import org.dcm4che3.data.*;
import org.dcm4che3.io.DicomInputHandler;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author MKlaman
 * @since 01.10.2017
 */
@Service
public class DicomAttributesReader {

    public List<DicomAttribute> read(DicomInputStream in) {
        List<DicomAttribute> attributes = new ArrayList<>();
        InputHandler handler = new InputHandler(attributes::add);

        readDataset(in, handler);
        return attributes;
    }

    public void read(DicomInputStream in, Consumer<DicomAttribute> onAttribute) {
        InputHandler handler = new InputHandler(onAttribute);
        readDataset(in, handler);
    }

    private void readDataset(DicomInputStream in, InputHandler handler) {
        try {
            in.setDicomInputHandler(handler);
            in.setIncludeBulkData(DicomInputStream.IncludeBulkData.NO);
            in.readDataset(-1, Tag.PixelData);
        } catch (IOException ex) {
            throw new DataExtractionException("error while reading dicom attributes", ex);
        }
    }

    @RequiredArgsConstructor
    private class InputHandler implements DicomInputHandler {

        private final Consumer<DicomAttribute> onAttribute;


        @Override
        public void readValue(DicomInputStream in, Attributes attributes) throws IOException {
            VR vr = in.vr();
            if (VR.SQ.equals(vr) || in.length() == -1) {
                return; // TODO handle sequence attributes
            }

            byte[] bytes = in.readValue();
            StringBuilder valueBuilder = new StringBuilder();
            vr.prompt(bytes, in.bigEndian(), attributes.getSpecificCharacterSet(), 255, valueBuilder);
            String value = valueBuilder.toString();

            onAttribute.accept(new DicomAttribute(in.tag(), value));
        }

        @Override
        public void readValue(DicomInputStream in, Sequence sequence) throws IOException {
        }

        @Override
        public void readValue(DicomInputStream in, Fragments fragments) throws IOException {
        }

        @Override
        public void startDataset(DicomInputStream in) throws IOException {
        }

        @Override
        public void endDataset(DicomInputStream in) throws IOException {
        }
    }

}

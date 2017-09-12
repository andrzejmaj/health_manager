package engineer.thesis.medcom.model.core;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DicomModule owns a set of dicom attribute tags
 *
 * @author MKlaman
 * @since 12.09.2017
 */
public class DicomModule {

    @Getter
    private final Set<DicomAttributeTag> attributeTags;

    private DicomModule(Set<DicomAttributeTag> attributeTags) {
        this.attributeTags = ImmutableSet.copyOf(attributeTags);
    }

    public DicomModule(DicomModule... modules) {
        this(Arrays.stream(modules)
                .map(DicomModule::getAttributeTags)
                .reduce(new HashSet<>(), (acc, attributes) -> {
                    acc.addAll(attributes);
                    return acc;
                }));
    }

    static DicomModule of(Integer... codes) {
        Set<DicomAttributeTag> attributes = Arrays.stream(codes)
                .map(DicomAttributeTag::of)
                .collect(Collectors.toSet());

        return new DicomModule(attributes);
    }

}

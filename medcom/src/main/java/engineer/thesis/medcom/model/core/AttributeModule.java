package engineer.thesis.medcom.model.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * AttributeModule responsibility is to define a set of DicomAttributeTags
 *
 * @author MKlaman
 * @since 12.09.2017
 */
public class AttributeModule {

    @Getter
    @JsonIgnore
    private final Set<DicomAttributeTag> attributeTags;

    private AttributeModule(Set<DicomAttributeTag> attributeTags) {
        this.attributeTags = ImmutableSet.copyOf(attributeTags);
    }

    public static AttributeModule of(Integer... attributeCodes) {
        Set<DicomAttributeTag> attributes = Arrays.stream(attributeCodes)
                .map(DicomAttributeTag::of)
                .collect(Collectors.toSet());

        return new AttributeModule(attributes);
    }

    public static AttributeModule combine(AttributeModule... modules) {
        Set<DicomAttributeTag> attributes = Arrays.stream(modules)
                .flatMap(module ->
                        module.getAttributeTags().stream())
                .collect(Collectors.toSet());

        return new AttributeModule(attributes);
    }

    public AttributeModule subtract(AttributeModule... others) {
        AttributeModule othersCombined = AttributeModule.combine(others);

        Set<DicomAttributeTag> attributes = attributeTags.stream()
                .filter(attribute ->
                        !othersCombined.contains(attribute.getCode()))
                .collect(Collectors.toSet());

        return new AttributeModule(attributes);
    }

    public boolean contains(int attributeCode) {
        return attributeTags.contains(DicomAttributeTag.of(attributeCode));
    }

}

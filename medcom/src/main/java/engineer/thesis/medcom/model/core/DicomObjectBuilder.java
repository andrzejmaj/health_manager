package engineer.thesis.medcom.model.core;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * @author MKlaman
 * @since 03.10.2017
 */
public abstract class DicomObjectBuilder<T extends DicomObject> implements Comparable<DicomObjectBuilder> {

    private static final Comparator<DicomObjectBuilder> priorityComparator =
            Comparator.<DicomObjectBuilder>comparingInt(DicomObjectBuilder::getPriority).reversed();

    protected Set<DicomAttribute> attributes = new HashSet<>();
    private Set<String> attributeNames = new HashSet<>();


    public abstract T build();

    public boolean accepts(DicomAttribute attribute) {
        // ignore unnamed attributes and duplicates
       return !attribute.getName().isEmpty() && !attributeNames.contains(attribute.getName());
    }

    public DicomObjectBuilder<T> accept(DicomAttribute attribute) {
        if (!accepts(attribute))
            throw new IllegalStateException("illegal attribute passed into builder: " + attribute.getName());

        attributes.add(attribute);
        attributeNames.add(attribute.getName());
        return this;
    }

    protected abstract int getPriority();

    @Override
    public int compareTo(DicomObjectBuilder other) {
        return priorityComparator.compare(this, other);
    }
}

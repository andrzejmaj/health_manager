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


    public abstract T build();

    public abstract boolean accepts(DicomAttribute attribute);

    public DicomObjectBuilder<T> attribute(DicomAttribute attribute) {
        if (!accepts(attribute))
            throw new IllegalStateException("illegal attribute passed into builder: " + attribute.getName());

        attributes.add(attribute);
        return this;
    }

    protected abstract int getPriority();

    @Override
    public int compareTo(DicomObjectBuilder other) {
        return priorityComparator.compare(this, other);
    }
}

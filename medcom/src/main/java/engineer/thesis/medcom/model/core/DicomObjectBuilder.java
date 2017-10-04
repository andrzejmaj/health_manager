package engineer.thesis.medcom.model.core;

import com.google.common.collect.ImmutableSet;
import engineer.thesis.medcom.model.DicomInstance;
import engineer.thesis.medcom.model.DicomSeries;
import engineer.thesis.medcom.model.DicomStudy;

import java.util.Comparator;
import java.util.List;

/**
 * @author MKlaman
 * @since 03.10.2017
 */
public abstract class DicomObjectBuilder<T extends DicomObject> implements Comparable<DicomObjectBuilder> {

    private static final Comparator<DicomObjectBuilder> priorityComparator =
            Comparator.<DicomObjectBuilder>comparingInt(DicomObjectBuilder::getPriority).reversed();

    protected ImmutableSet.Builder<DicomAttribute> attributes = new ImmutableSet.Builder<>();


    public abstract T build();

    public abstract boolean accepts(DicomAttribute attribute);

    public void attribute(DicomAttribute attribute){
        if(!accepts(attribute))
            throw new IllegalStateException("illegal attribute passed into builder: " + attribute.getName());

        attributes.add(attribute);
    }

    protected abstract int getPriority();

    @Override
    public int compareTo(DicomObjectBuilder other) {
        return priorityComparator.compare(this, other);
    }
}

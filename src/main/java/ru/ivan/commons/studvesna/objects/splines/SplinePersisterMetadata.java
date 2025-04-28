package ru.ivan.commons.studvesna.objects.splines;

import ru.ivan.commons.studvesna.api.ActionMetadata;
import java.util.Map;

public class SplinePersisterMetadata extends ActionMetadata {

    private final Map<String, Object> METADATA;

    public SplinePersisterMetadata( Map<String, Object> metadata) {
        this.METADATA = metadata;
    }

    @Override
    public Map<String, Object> getMetadata() {
        return METADATA;
    }

}

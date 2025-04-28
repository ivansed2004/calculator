package ru.ivan.commons.studvesna.objects.splines;

import ru.ivan.commons.studvesna.api.ActionMetadata;
import java.util.Map;

public class SplineSamplerMetadata extends ActionMetadata {

    private final Map<String, Object> METADATA;

    public SplineSamplerMetadata() {
        this( Map.of( "period", 1.929 ) );
    }

    public SplineSamplerMetadata( Map<String, Object> metadata ) {
        this.METADATA = metadata;
    }

    @Override
    public Map<String, Object> getMetadata() {
        return METADATA;
    }

}

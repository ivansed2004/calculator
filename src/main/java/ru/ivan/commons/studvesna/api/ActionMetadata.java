package ru.ivan.commons.studvesna.api;

import java.util.Map;

@FunctionalInterface
public interface ActionMetadata {

    Map<String, Object> getMetadata();

}

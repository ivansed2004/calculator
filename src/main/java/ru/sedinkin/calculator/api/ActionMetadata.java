package ru.sedinkin.calculator.api;

import java.util.Map;

@FunctionalInterface
public interface ActionMetadata {

    Map<String, Object> getMetadata();

}

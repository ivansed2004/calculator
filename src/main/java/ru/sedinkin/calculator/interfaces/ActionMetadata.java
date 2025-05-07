package ru.sedinkin.calculator.interfaces;

import java.util.Map;

@FunctionalInterface
public interface ActionMetadata {

    Map<String, Object> getMetadata();

}

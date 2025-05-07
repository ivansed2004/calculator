package ru.sedinkin.calculator.interfaces;

import java.util.Map;

@FunctionalInterface
public interface IntAction<I, O> {

    O perform(I input, Map<String, Object> metadata);

}

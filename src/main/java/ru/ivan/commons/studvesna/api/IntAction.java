package ru.ivan.commons.studvesna.api;

@FunctionalInterface
public interface IntAction<I, O> {

    O perform( I input, ActionMetadata metadata );

}

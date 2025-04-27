package ru.ivan.commons.studvesna.api;

@FunctionalInterface
public interface TerminalAction<I> {

    void perform( I input, ActionMetadata metadata );

}

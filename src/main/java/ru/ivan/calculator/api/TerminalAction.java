package ru.ivan.calculator.api;

import java.util.Map;

@FunctionalInterface
public interface TerminalAction<I> {

    void perform( I input, Map<String, Object> metadata );

}

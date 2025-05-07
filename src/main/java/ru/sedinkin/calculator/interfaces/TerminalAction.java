package ru.sedinkin.calculator.interfaces;

import java.util.Map;

@FunctionalInterface
public interface TerminalAction<I> {

    void perform( I input, Map<String, Object> metadata );

}

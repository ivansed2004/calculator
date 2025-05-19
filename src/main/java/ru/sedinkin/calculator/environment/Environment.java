package ru.sedinkin.calculator.environment;

import javax.swing.*;
import java.io.File;

public class Environment {

    // System separator
    // It's strongly prohibited to change its value!
    public static String SEP = File.separator;

    // Default path set initially
    public static String TARGET_DIRECTORY = "";

    // If 'always' set, requires GUI target directory selection.
    // If 'never' set, skips selection.
    public static String RUNTIME_TARGET_DIR_SELECTION = "";

    // Default view of dialog windows (when selecting the target directory and source files)
    public static void setSystemLookAndFeel() throws Exception {
        UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
    }

    // Keep it always private!
    private Environment() {}

}

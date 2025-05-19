package ru.sedinkin.calculator.environment;

import org.yaml.snakeyaml.Yaml;
import ru.sedinkin.calculator.Runner;

import java.util.Map;

import static ru.sedinkin.calculator.environment.Environment.RUNTIME_TARGET_DIR_SELECTION;
import static ru.sedinkin.calculator.environment.Environment.TARGET_DIRECTORY;

public class YamlParser {

    private static final Yaml YAML = new Yaml();

    private static final Map<String, Object> CONFIGS = YAML.load(
            Runner.class.getResourceAsStream("/config.yaml")
    );

    // Keep it always private!
    private YamlParser() {}

    public static void setDefaultTargetDirectory() {
        TARGET_DIRECTORY = (String) ((Map<String, Object>) CONFIGS.get("pre-calculation-settings"))
                .get("default-target-directory");
    }

    public static void parseRuntimeTargetDirectorySelection() {
        RUNTIME_TARGET_DIR_SELECTION = (String) ((Map<String, Object>) CONFIGS.get("pre-calculation-settings"))
                .get("runtime-target-directory-selection");
    }

}

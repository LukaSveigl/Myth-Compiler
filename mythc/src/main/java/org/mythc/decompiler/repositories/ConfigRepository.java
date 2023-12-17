package org.mythc.decompiler.repositories;

public class ConfigRepository {

    private static final ConfigRepository INSTANCE = new ConfigRepository();

    private String sourceFile;
    private String destinationFile;

    private ConfigRepository() {
    }

    public static ConfigRepository getInstance() {
        return INSTANCE;
    }

    public void initialize(String sourceFile, String destinationFile) {
        if (INSTANCE.sourceFile != null)
            throw new IllegalStateException("ConfigRepository already initialized");

        INSTANCE.sourceFile = sourceFile;
        INSTANCE.destinationFile = destinationFile;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public String getDestinationFile() {
        return destinationFile;
    }

}

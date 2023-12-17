package org.mythc.compiler.repositories;

import java.util.ArrayList;

/**
 * The {@code ConfigRepository} class is used to store the configuration of the compiler. It is a singleton class.
 */
public class ConfigRepository {

    /** The internal config repository instance. */
    private static final ConfigRepository INSTANCE = new ConfigRepository();
    /** The source file paths to compile. */
    private ArrayList<String> sourceFiles;
    /** The destination file path to write to. */
    private String destinationFile;
    /** Whether to enable verbose output. */
    private boolean verbose;
    /** Whether to treat warnings as errors. */
    private boolean werror;
    /** Whether to enable all warnings. */
    private boolean wall;
    /** The optimization level to use. */
    private OptimizationLevel optimizationLevel;

    /** Whether to enable debug mode. Only used for development. */
    private static final boolean DEBUG = true;

    /**
     * The optimization level to use.
     */
    public enum OptimizationLevel {
        O0, O1, O2, O3
    }

    /**
     * Constructs a new {@code ConfigRepository}.
     */
    private ConfigRepository() {
    }

    /**
     * Returns the internal config repository instance.
     *
     * @return the internal config repository instance.
     */
    public static ConfigRepository getInstance() {
        return INSTANCE;
    }

    /**
     * Initializes the config repository. If the config repository is already initialized, an
     * {@code IllegalStateException} is thrown.
     *
     * @param sourceFiles       The source file paths to compile.
     * @param destinationFile   The destination file path to write to.
     * @param verbose           Whether to enable verbose output.
     * @param werror            Whether to treat warnings as errors.
     * @param wall              Whether to enable all warnings.
     * @param optimizationLevel The optimization level to use. One of {@code 0}, {@code 1}, {@code 2}, or {@code 3}.
     *                          If an invalid optimization level is provided, an {@code IllegalArgumentException} is thrown.
     * @throws IllegalStateException    If the config repository is already initialized.
     * @throws IllegalArgumentException If an invalid optimization level is provided.
     */
    public void initialize(ArrayList<String> sourceFiles, String destinationFile, boolean verbose, boolean werror,
                           boolean wall, String optimizationLevel) {
        if (!isInitialized())
            throw new IllegalStateException("ConfigRepository already initialized");

        INSTANCE.sourceFiles = sourceFiles;
        INSTANCE.destinationFile = destinationFile;
        INSTANCE.verbose = verbose;
        INSTANCE.werror = werror;
        INSTANCE.wall = wall;
        INSTANCE.optimizationLevel = switch (optimizationLevel) {
            case "0" -> OptimizationLevel.O0;
            case "1" -> OptimizationLevel.O1;
            case "2" -> OptimizationLevel.O2;
            case "3" -> OptimizationLevel.O3;
            default -> throw new IllegalArgumentException("Invalid optimization level");
        };
    }

    public boolean isInitialized() {
        return INSTANCE.sourceFiles != null;
    }

    /**
     * Returns the list of source file paths to compile.
     *
     * @return the list of source file paths to compile.
     */
    public ArrayList<String> getSourceFiles() {
        return sourceFiles;
    }

    /**
     * Returns the destination file path to write to.
     *
     * @return the destination file path to write to.
     */
    public String getDestinationFile() {
        return destinationFile;
    }

    /**
     * Returns whether to enable verbose output.
     *
     * @return whether to enable verbose output.
     */
    public boolean isVerbose() {
        return verbose;
    }

    /**
     * Returns whether to treat warnings as errors.
     *
     * @return whether to treat warnings as errors.
     */
    public boolean isWerror() {
        return werror;
    }

    /**
     * Returns whether to enable all warnings.
     *
     * @return whether to enable all warnings.
     */
    public boolean isWall() {
        return wall;
    }

    /**
     * Returns the optimization level to use.
     *
     * @return the optimization level to use.
     */
    public OptimizationLevel getOptimizationLevel() {
        return optimizationLevel;
    }

}

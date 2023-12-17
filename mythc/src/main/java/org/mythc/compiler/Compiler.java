package org.mythc.compiler;

import org.mythc.compiler.common.logging.CompilerLogger;
import org.mythc.compiler.repositories.ConfigRepository;

public class Compiler {

    public Compiler() {
        ConfigRepository configRepository = ConfigRepository.getInstance();
        if (!configRepository.isInitialized()) {
            CompilerLogger.internalError("Config repository is not initialized.");
        }
    }

    public void compile() {
        System.out.println("Compiling...");
        for (String sourcePath : ConfigRepository.getInstance().getSourceFiles()) {
            System.out.println("Compiling " + sourcePath);
        }

    }
}

package org.mythc;

import java.util.ArrayList;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.*;

import org.mythc.compiler.Compiler;
import org.mythc.decompiler.Decompiler;

public class MythC {
    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("MythC").build()
                .description("MythC is a compiler for the Myth programming language.")
                .version("${prog} 0.0.1 (Internal Build)");
        Subparsers subparsers = parser.addSubparsers().dest("command").help("commands");

        // Construct the `compile` sub-parser, which is used to handle the flags of the `compile` command.
        Subparser compileParser = subparsers.addParser("compile").help("Compile a Myth program");
        compileParser.addArgument("-s", "--source")
                .required(true)
                .nargs("+")
                .help("Source file(s) to compile");
        compileParser.addArgument("-d", "--destination")
                .required(false)
                .setDefault("prog.myir")
                .help("Destination file");
        compileParser.addArgument("--verbose")
                .action(Arguments.storeTrue())
                .help("Verbose output");
        compileParser.addArgument("--werror")
                .action(Arguments.storeTrue())
                .help("Treat warnings as errors");
        compileParser.addArgument("--wall")
                .action(Arguments.storeTrue())
                .help("Enable all warnings");
        compileParser.addArgument("-O", "--optimize")
                .choices("0", "1", "2", "3")
                .setDefault("0")
                .help("Set optimization level");

        // Construct the `decompile` sub-parser, which is used to handle the flags of the `decompile` command.
        Subparser decompileParser = subparsers.addParser("decompile").help("Decompile a Myth program");
        decompileParser.addArgument("-s", "--source")
                .required(true)
                .help("Source file to decompile");
        decompileParser.addArgument("-d", "--destination")
                .required(false)
                .help("Destination file");

        parser.addArgument("--version").action(Arguments.version()).help("Show version information and exit");

        try {
            Namespace namespace = parser.parseArgs(args);
            String command = namespace.getString("command");

            if (command.equals("compile")) {
                org.mythc.compiler.repositories.ConfigRepository.getInstance().initialize(
                        new ArrayList<>(namespace.getList("source")),
                        namespace.getString("destination"),
                        namespace.getBoolean("verbose"),
                        namespace.getBoolean("werror"),
                        namespace.getBoolean("wall"),
                        namespace.getString("optimize")
                );

                Compiler compiler = new Compiler();
                compiler.compile();
            } else if (command.equals("decompile")) {
                org.mythc.decompiler.repositories.ConfigRepository.getInstance().initialize(
                        namespace.getString("source"),
                        namespace.getString("destination")
                );
                Decompiler decompiler = new Decompiler();
                decompiler.decompile();
            } else {
                parser.printHelp();
            }
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

    }

}
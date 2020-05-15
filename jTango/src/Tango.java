import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Tango {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String command;
        boolean showOutput = false;
        List<String> tangoArgs;
        Tokenizer tokenizer;
        while (true) {
            command = br.readLine();
            tangoArgs = new ArrayList(Arrays.asList(command.split(" ")));
            if (tangoArgs.get(0).equals("tango")) {
                tangoArgs.remove(0);
                if (tangoArgs.get(0).equals("-o")) {
                    showOutput = true;
                    tangoArgs.remove(0);
                }
                if (tangoArgs.size() > 1) {
                    continue;
                }
                String projectName = tangoArgs.get(0);
                File project = new File(String.format("../sample-projects/%s", projectName));
                if (project.isDirectory()) {
                    String headerRegex = String.format("^%s\\.tangoh$", projectName);
                    File file = null;
                    for (File source : project.listFiles()) {
                        if (source.getName().matches(headerRegex)) {
                            file = source;
                        }
                    }
                    try {
                        tokenizer = new Tokenizer(file, showOutput);
                        tokenizer.process();
                    }
                    catch (NullPointerException npx) {
                        System.out.println("No valid .tangoh file found. Check that the name of your .tangoh file matches the name of the project folder");
                        continue;
                    }

                    String stateRegex = String.format("^[A-Z][a-z]*\\.tango$");
                    for (File source : project.listFiles()) {
                        if (source.getName().matches(stateRegex)) {
                            file = source;
                            try {
                                tokenizer = new Tokenizer(file, showOutput);
                                tokenizer.process();
                            }
                            catch (NullPointerException npx) {
                                continue;
                            }
                        }
                    }
                }
                else {
                    System.out.printf("Project %s not found or is not directory.\n", projectName);
                }
            }
            else {
                System.out.printf("Unrecognized command: %s", command);
            }
        }
    }

    public static void error(String message, int exitCode) {
        System.err.println(message);
        System.exit(exitCode);
    }
}

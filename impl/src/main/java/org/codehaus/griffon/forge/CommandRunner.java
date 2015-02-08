package org.codehaus.griffon.forge;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CommandRunner {
    private String directory;
    private List<String> commandLineArguments;
    private boolean shellScript = false;

    private static String NEWLINE  = System.lineSeparator();

    private StringBuffer output = new StringBuffer();


    public void setShellScript(boolean shellScript) {
        this.shellScript = shellScript;
    }

    public CommandRunner(String command, boolean shellScript){
        this(command,shellScript, null);
    }

    public CommandRunner(String command, boolean shellScript, List<String> commandLineArguments) {
        if(commandLineArguments == null)
            commandLineArguments = new ArrayList<String>();

        setShellScript(shellScript);

        if(System.getProperty("os.name").toLowerCase().contains("win")){
            if(shellScript && !command.endsWith(".bat"))
                command = command + ".bat";
            commandLineArguments.add(0,command);
            commandLineArguments.add(0, "/c");
            commandLineArguments.add(0, "CMD.exe");
        }else{
            if(shellScript && !command.endsWith(".sh"))
                command = command + ".sh";
            commandLineArguments.add(0,command);
        }
        this.commandLineArguments = commandLineArguments;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String run(List<String> subsequentInputs) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(commandLineArguments);

        if(directory!= null)
            pb.directory(new File(directory));

        Process p = pb.start();

        StreamGobbler inputGobbler = new StreamGobbler(p.getInputStream()," OUT> ");
        inputGobbler.start();

        StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream()," ERR> ");
        errorGobbler.start();

        if(subsequentInputs != null){
            BufferedWriter outputStream = new BufferedWriter(
                    new OutputStreamWriter(p.getOutputStream()));
            for (String input : subsequentInputs) {
                output.append(input+NEWLINE);
                outputStream.write(input);
                outputStream.newLine();
                outputStream.flush();
            }
            outputStream.close();
        }
        System.out.println("Process Exit Value: " + p.waitFor());
        p.destroy();
        return output.toString();

    }

    class StreamGobbler extends Thread {
        InputStream is;
        String type = "";
        StreamGobbler(InputStream is) {
            this.is = is;
            this.type = "";
        }

        public StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null){
                    line = type + line;
                    System.out.println(line);
                    output.append(line+NEWLINE);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
                output.append(ioe.getMessage());
            }
        }
    }
}
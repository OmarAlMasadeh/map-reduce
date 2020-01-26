
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BashScriptExecuter {
    public static void CreateManager() throws IOException, InterruptedException {
        String[] command = {"/home/omar/IdeaProjects/MapReduce/src/main/resources/BashScripts/CreateManager.sh"};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.inheritIO();
        Process process = processBuilder.start();
    }
    public static void CreateMappers(int numberOfMappers) throws IOException {
        String[] command = {"/home/omar/IdeaProjects/MapReduce/src/main/resources/BashScripts/CreateMappers.sh",String.valueOf(numberOfMappers)};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.inheritIO();
        Process process = processBuilder.start();
    }
    public static void CreateReducers(int numberOfReducers) throws IOException {
        String[] command = {"/home/omar/IdeaProjects/MapReduce/src/main/resources/BashScripts/CreateReducers.sh", String.valueOf(numberOfReducers)};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.inheritIO();
        Process process = processBuilder.start();
    }
}

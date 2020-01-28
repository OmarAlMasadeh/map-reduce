import java.io.IOException;

public class BashScriptExecuter {
    /**
     * @throws IOException
     * runs a bash script that uses docker to create Manager
     */
    public static void CreateManager() throws IOException {
        String[] command = {"/home/omar/IdeaProjects/MapReduce/src/main/resources/BashScripts/CreateManager.sh"};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
    }
    /**
     * @param numberOfMappers number of Mappers to be created
     * @throws IOException
     * runs a bash script that uses docker to create Mappers
     */
    public static void CreateMappersReducers(int numberOfMappers,int numberOfReducers) throws IOException, InterruptedException {
        String[] command = {"/home/omar/IdeaProjects/MapReduce/src/main/resources/BashScripts/CreateMappers.sh",String.valueOf(numberOfMappers),String.valueOf(numberOfReducers)};
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
    }

}

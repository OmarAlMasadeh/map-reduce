
import java.io.IOException;

public class BashScriptExecuter {
    public static void CreateManager() throws IOException {
        String[] env = {"PATH=/bin:/usr/bin/"};
        String cmd = "sh ~/IdeaProjects/MapReduce/src/main/resources/BashScripts/CreateManager.sh";
        Process process = Runtime.getRuntime().exec(cmd, env);
    }
    public static void CreateMappers(int numberOfMappers) throws IOException {
        String[] env = {"PATH=/bin:/usr/bin/"};
        String cmd = "bash ~/IdeaProjects/MapReduce/src/main/resources/BashScripts/CreateMappers.sh "+numberOfMappers;
        Process process = Runtime.getRuntime().exec(cmd, env);
    }
    public static void CreateReducers(int numberOfReducers) throws IOException {
        String[] env = {"PATH=/bin:/usr/bin/"};
        String cmd = "bash ~/IdeaProjects/MapReduce/src/main/resources/BashScripts/CreateReducers.sh "+numberOfReducers;
        Process process = Runtime.getRuntime().exec(cmd, env);
    }
}

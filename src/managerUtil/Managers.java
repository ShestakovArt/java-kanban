package managerUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import until.LocalDateTimeAdapter;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class Managers {
    public TaskManager getDefault(){
        return new HttpTaskManager("http://localhost:8078/");
    }

    public TaskManager getFileBackedTasksManager(Path pathToFile){
        return new FileBackedTasksManager(pathToFile);
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return gsonBuilder.create();
    }
}

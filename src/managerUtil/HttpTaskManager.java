package managerUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import enums.TypeTask;
import http.KVTaskClient;
import templateTask.Task;

import java.util.Map;

public class HttpTaskManager extends FileBackedTasksManager{
    KVTaskClient kvTaskClient;
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();

    /**
     * Конструктор
     */
    public HttpTaskManager(String url) {
        super(null);
        kvTaskClient = new KVTaskClient(url);
    }

     public void load(){
        String dataTask = kvTaskClient.load("task");
        String dataEpic = kvTaskClient.load("epic");
        String dataSubtask = kvTaskClient.load("subtask");
        String dataHistory = kvTaskClient.load("history");
    }

    @Override
    public void save(){
        for(Map.Entry<Integer, Task> entry : getDataTask().entrySet()){
            String key = entry.getValue().getTypeTask();
            if (key.equals(TypeTask.TASK.getCode())){
                kvTaskClient.put("task", gson.toJson(entry.getValue()));
            }
            else if (key.equals(TypeTask.EPIC.getCode())){
                kvTaskClient.put("epic", gson.toJson(entry.getValue()));
            }
            else if (key.equals(TypeTask.SUBTASK.getCode())){
                kvTaskClient.put("subtask", gson.toJson(entry.getValue()));
            }
        }

        for(Task task : getHistory()){
            kvTaskClient.put("history", gson.toJson(task));
        }
    }
}

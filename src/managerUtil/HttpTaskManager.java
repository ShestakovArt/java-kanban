package managerUtil;

import com.google.gson.*;
import enums.TypeTask;
import http.KVTaskClient;
import templateTask.Epic;
import templateTask.Subtask;
import templateTask.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpTaskManager extends FileBackedTasksManager{
    KVTaskClient kvTaskClient;
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();

    /**
     * Конструктор
     */
    public HttpTaskManager() {
        super(null);
        kvTaskClient = new KVTaskClient();
        load();
    }

    @Override
    void save(){
        List<Task> taskList = new ArrayList<>();
        List<Epic> EpicList = new ArrayList<>();
        List<Subtask> subtaskList = new ArrayList<>();
        for(Map.Entry<Integer, Task> entry : getDataTask().entrySet()){
            String key = entry.getValue().getTypeTask();
            if (key.equals(TypeTask.TASK.getCode())){
                taskList.add(entry.getValue());
            }
            if (key.equals(TypeTask.EPIC.getCode())){
                EpicList.add((Epic) entry.getValue());
            }
            if (key.equals(TypeTask.SUBTASK.getCode())){
                subtaskList.add((Subtask) entry.getValue());
            }
        }
        if (taskList.size() > 0){
            kvTaskClient.put("task", gson.toJson(taskList));
        }
        if (EpicList.size() > 0){
            kvTaskClient.put("epic", gson.toJson(EpicList));
        }
        if (subtaskList.size() > 0){
            kvTaskClient.put("subtask", gson.toJson(subtaskList));
        }
        if (getHistory().size() > 0){
            kvTaskClient.put("history", gson.toJson(getHistory()));
        }
    }

    @Override
    void load(){
        String dataTask = kvTaskClient.load("task");
        //String dataTask = "[{\"status\":\"NEW\",\"id\":1,\"typeTask\":\"TASK\",\"duration\":0,\"nameTask\":\"Таска Инсомния 1\",\"description\":\"Описание таски\"},{\"status\":\"NEW\",\"id\":2,\"typeTask\":\"TASK\",\"duration\":0,\"nameTask\":\"Таска Инсомния 2\",\"description\":\"Описание таски\"}]";
        buildTasks(dataTask, false);
        String dataEpic = kvTaskClient.load("epic");
        //String dataEpic = "[{\"dataSubtask\":{\"5\":{\"idEpic\":3,\"status\":\"NEW\",\"id\":5,\"typeTask\":\"SUBTASK\",\"duration\":0,\"nameTask\":\"Сабка 1 Эпик Инсомния 1\",\"description\":\"Описание Сабки\"}},\"status\":\"NEW\",\"id\":3,\"typeTask\":\"EPIC\",\"duration\":0,\"nameTask\":\"Эпик Инсомния 1\",\"description\":\"Описание эпика\"},{\"dataSubtask\":{},\"status\":\"NEW\",\"id\":4,\"typeTask\":\"EPIC\",\"duration\":0,\"nameTask\":\"Эпик Инсомния 2\",\"description\":\"Описание эпика\"}]";
        buildTasks(dataEpic, false);
        String dataSubtask = kvTaskClient.load("subtask");
        //String dataSubtask = "[{\"idEpic\":3,\"status\":\"NEW\",\"id\":5,\"typeTask\":\"SUBTASK\",\"duration\":0,\"nameTask\":\"Сабка 1 Эпик Инсомния 1\",\"description\":\"Описание Сабки\"}]";
        buildTasks(dataSubtask, false);
        String dataHistory = kvTaskClient.load("history");
        //String dataHistory = "[{\"dataSubtask\":{\"5\":{\"idEpic\":3,\"status\":\"NEW\",\"id\":5,\"typeTask\":\"SUBTASK\",\"duration\":0,\"nameTask\":\"Сабка 1 Эпик Инсомния 1\",\"description\":\"Описание Сабки\"}},\"status\":\"NEW\",\"id\":3,\"typeTask\":\"EPIC\",\"duration\":0,\"nameTask\":\"Эпик Инсомния 1\",\"description\":\"Описание эпика\"},{\"status\":\"NEW\",\"id\":1,\"typeTask\":\"TASK\",\"duration\":0,\"nameTask\":\"Таска Инсомния 1\",\"description\":\"Описание таски\"}]\n";
        buildTasks(dataHistory, true);
        if(!dataTask.isEmpty() || !dataEpic.isEmpty() || !dataSubtask.isEmpty()){
            Task.setCounter(Collections.max(getDataTask().keySet()) + 1);
        }
    }

    private void buildTasks(String dataLoad, boolean historyFlag){
        if(!dataLoad.isEmpty()){
            JsonElement jsonElement = JsonParser.parseString(dataLoad);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement element : jsonArray){
                if(element.getAsJsonObject().get("typeTask").getAsString().equals(TypeTask.TASK.getCode())){
                    if(historyFlag){
                        getTask(Integer.parseInt(element.getAsJsonObject().get("id").toString()));
                    }
                    else{
                        addTask(gson.fromJson(element, Task.class));
                    }
                }
                if(element.getAsJsonObject().get("typeTask").getAsString().equals(TypeTask.EPIC.getCode())){
                    if(historyFlag){
                        getTask(Integer.parseInt(element.getAsJsonObject().get("id").toString()));
                    }
                    else{
                        addTask(gson.fromJson(element, Epic.class));
                    }
                }
                if(element.getAsJsonObject().get("typeTask").getAsString().equals(TypeTask.SUBTASK.getCode())){
                    if(historyFlag){
                        getTask(Integer.parseInt(element.getAsJsonObject().get("id").toString()));
                    }
                    else{
                        addTask(Integer.parseInt(element.getAsJsonObject().get("idEpic").toString()), gson.fromJson(element, Subtask.class));
                    }
                }
            }
        }
    }
}

package managerUtil;

import templateTask.Epic;
import templateTask.Subtask;
import templateTask.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> dataTask = new HashMap<>();
    private HashMap<Integer, Epic> dataEpic = new HashMap<>();
    private static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }

    @Override
    public void addTask(Task task){
        dataTask.put(task.getId(), task);
    }

    @Override
    public void addTask(Epic epic){
        addTask((Task) epic);
        dataEpic.put(epic.getId(), epic);
    }

    @Override
    public void addTask(Integer idEpic, Subtask subtask){
        addTask(subtask);
        subtask.setIdEpic(idEpic);
        getDataEpic().get(idEpic).addSubTask(subtask);
    }

    @Override
    public Task getTask(Integer id){
        getDefaultHistory().add(getDataTask().get(id));
        return getDataTask().get(id);
    }

//    @Override
//    public Epic getEpic(Integer id){
//        getDefaultHistory().add(getDataEpic().get(id));
//        return getDataEpic().get(id);
//    }
//
//    @Override
//    public Subtask getSubtask(Integer idEpic, Integer idSubtask){
//        getDefaultHistory().add(getDataEpic().get(idEpic).getSubtask(idSubtask));
//        return getDataEpic().get(idEpic).getSubtask(idSubtask);
//    }

    @Override
    public void updateTask(Task task){
        dataTask.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic){
        dataEpic.put(epic.getId(), epic);
    }

    @Override
    public void deleteTask(Integer idTask){
        dataTask.remove(idTask);
        getDefaultHistory().remove(idTask);
    }

    @Override
    public void deleteAllTask(){
        for (Map.Entry<Integer, Task> entry : dataTask.entrySet()) {
            getDefaultHistory().remove(entry.getKey());
        }
        dataTask.clear();
    }

    @Override
    public void deleteEpic(Integer idEpic){
        deleteAllSubtask(idEpic);
        dataEpic.remove(idEpic);
        getDefaultHistory().remove(idEpic);
    }

    @Override
    public void deleteAllEpic(){
        for (Map.Entry<Integer, Epic> entry : dataEpic.entrySet()) {
            deleteEpic(entry.getKey());
            getDefaultHistory().remove(entry.getKey());
        }
        dataEpic.clear();
    }

    @Override
    public void deleteSubtask(Integer idEpic, Integer idSubtask){
        dataEpic.get(idEpic).deleteSubtask(idSubtask);
        getDefaultHistory().remove(idSubtask);
    }

    @Override
    public void deleteAllSubtask(Integer idEpic){
        for (Map.Entry<Integer, Subtask> entry : dataEpic.get(idEpic).getDataSubtask().entrySet()) {
            getDefaultHistory().remove(entry.getKey());
        }
        dataEpic.get(idEpic).deleteAllSubtask();
    }

    @Override
    public HashMap<Integer, Task> getDataTask() {
        return dataTask;
    }

    @Override
    public HashMap<Integer, Epic> getDataEpic() {
        return dataEpic;
    }

    @Override
    public HashMap<Integer, Subtask> getSubtaskEpic(Integer idEpic){
        return dataEpic.get(idEpic).getDataSubtask();
    }


    @Override
    public List<Task> getHistory(){
        return  getDefaultHistory().getHistory();
    }
}

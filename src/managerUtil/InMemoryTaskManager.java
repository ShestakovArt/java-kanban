package managerUtil;

import templateTask.BaseTask;
import templateTask.Epic;
import templateTask.Subtask;
import templateTask.Task;

import java.util.HashMap;
import java.util.List;

import static managerUtil.Managers.getDefaultHistory;

public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> dataTask = new HashMap<>();
    private HashMap<Integer, Epic> dataEpic = new HashMap<>();

    @Override
    public void addTask(Task task){
        dataTask.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic){;
        dataEpic.put(epic.getId(), epic);
    }

    @Override
    public Task getTask(Integer id){
        getDefaultHistory().add(getDataTask().get(id));
        return getDataTask().get(id);
    }

    @Override
    public Epic getEpic(Integer id){
        getDefaultHistory().add(getDataEpic().get(id));
        return getDataEpic().get(id);
    }

    @Override
    public Subtask getSubtask(Integer idEpic, Integer idSubtask){
        getDefaultHistory().add(getDataEpic().get(idEpic).getSubtask(idSubtask));
        return getDataEpic().get(idEpic).getSubtask(idSubtask);
    }

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
    }

    @Override
    public void deleteAllTask(){
        dataTask.clear();
    }

    @Override
    public void deleteEpic(Integer idEpic){
        dataEpic.remove(idEpic);
    }

    @Override
    public void deleteAllEpic(){
        dataEpic.clear();
    }

    @Override
    public void deleteSubtask(Integer idEpic, Integer idSubtask){
        dataEpic.get(idEpic).deleteSubtask(idSubtask);
    }

    @Override
    public void deleteAllSubtask(Integer idEpic){
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
    public void addSubtaskForEpic(Integer idEpic, String nameTask, String description){
        dataEpic.get(idEpic).createSubtask(nameTask, description);
    }

    @Override
    public List<BaseTask> getHistory(){
        return  getDefaultHistory().getHistory();
    }
}

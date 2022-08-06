import status.Status;
import templateTask.Epic;
import templateTask.Subtask;
import templateTask.Task;

import java.util.HashMap;

public class ManageTask {
    private HashMap<Integer, Task> dataTask = new HashMap<>();
    private HashMap<Integer, Epic> dataEpic = new HashMap<>();

    private Integer idTask = 0;
    private Integer idEpic = 0;

    public Task createTask(String nameTask, String description){
        idTask +=1;
        Task task = new Task(nameTask, description, idTask, Status.NEW.getCode());
        dataTask.put(idTask, task);
        return task;
    }

    public Epic createEpic(String nameEpic, String description){
        idEpic +=1;
        Epic epic = new Epic(nameEpic, description, idEpic, Status.NEW.getCode());
        dataEpic.put(idEpic, epic);
        return epic;
    }

    public void addSubtaskEpic (Integer idEpic, String nameSubTask, String description){
        Epic epic = dataEpic.get(idEpic);
        epic.createSubtask(nameSubTask, description);
    }

    //добавляем имеющуюся задачу в эпик(ID задачи сменится на новый ID подзадачи, задача удаляется из списка задач)
    public void addSubtaskEpic (Integer idEpic, Task task){
        Epic epic = dataEpic.get(idEpic);
        epic.createSubtask(task);
        deleteTask(task.getId());
    }

    public void updateNameTask(Integer idTask, String nameTask){
        dataTask.get(idTask).setNameTask(nameTask);
    }

    public void updateDescriptionTask(Integer idTask, String description){
        dataTask.get(idTask).setDescription(description);
    }

    public void updateNameEpic(Integer idEpic, String nameEpic){
        dataEpic.get(idEpic).setNameTask(nameEpic);
    }

    public void updateDescriptionEpic(Integer idEpic, String description){
        dataEpic.get(idEpic).setDescription(description);
    }

    public void updateStatusTask(Integer idTask, Status status){
        dataTask.get(idTask).setStatusTask(status.getCode());
    }

    public void deleteTask(Integer idTask){
        dataTask.remove(idTask);
    }

    public void deleteAllTask(){
        dataTask.clear();
    }

    public void deleteEpic(Integer idEpic){
        dataEpic.remove(idTask);
    }

    public void deleteAllEpic(){
        dataEpic.clear();
    }

    public HashMap<Integer, Task> getDataTask() {
        return dataTask;
    }

    public HashMap<Integer, Epic> getDataEpic() {
        return dataEpic;
    }
}

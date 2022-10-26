package managerUtil;

import enums.TypeTask;
import templateTask.Epic;
import templateTask.Subtask;
import templateTask.Task;

import java.time.LocalDateTime;
import java.util.*;


public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> dataTask = new HashMap<>();
    private HashMap<Integer, Epic> dataEpic = new HashMap<>();
    private List<Task> taskList;
    protected static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }

    @Override
    public void setStartTimeForTack(Task task, LocalDateTime startTime, long duration){
        if(!CheckIntersectionsTime(startTime)){
            task.setStartTime(startTime);
            task.setDuration(duration);
        }
        else{
            System.out.println("Из-за пересечения, время начала для задачи не установлено");
        }
    }

    public List<Task> getPrioritizedTasks(){
        taskList = new ArrayList<>();
        Comparator<Task> comparator = Comparator.comparing(Task::getStartTime);
        for (Map.Entry<Integer, Task> entry : dataTask.entrySet()) {
            if(!entry.getValue().getTypeTask().equals(TypeTask.EPIC.getCode())){
                if(entry.getValue().getStartTime() != null){
                    taskList.add(entry.getValue());
                }
            }
        }
        taskList.sort(comparator);
        for (Map.Entry<Integer, Task> entry : dataTask.entrySet()) {
            if(!entry.getValue().getTypeTask().equals(TypeTask.EPIC.getCode())){
                if(entry.getValue().getStartTime() == null){
                    taskList.add(entry.getValue());
                }
            }
        }
        return taskList;
    }

    private boolean CheckIntersectionsTime(LocalDateTime startTime){
        boolean flag = false;
        if(startTime != null){
            List<Task> taskList = getPrioritizedTasks();
            for (Task tl : taskList){
                if(!tl.getTypeTask().equals(TypeTask.EPIC.getCode()) && tl.getStartTime() != null){
                    if(startTime.isAfter(tl.getStartTime()) || startTime.equals(tl.getStartTime())){
                        if (startTime.isBefore(tl.getEndTime())){
                            System.out.println(String.format("Пересечение времени выполнения с задачей: %s", tl));
                            flag = true;
                        }
                    }
                }
            }
        }
        return flag;
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
        dataEpic.get(idEpic).addSubTask(subtask);
    }

    @Override
    public Task getTask(Integer id){
        getDefaultHistory().add(getDataTask().get(id));
        return getDataTask().get(id);
    }

    @Override
    public void deleteTask(Integer idTask){
        if (getDefaultHistory().getHistory().size() > 0){
            getDefaultHistory().remove(idTask);
        }
        dataTask.remove(idTask);
    }

    @Override
    public void deleteAllTask(){
        for (Map.Entry<Integer, Task> entry : dataTask.entrySet()) {
            getDefaultHistory().remove(entry.getKey());
        }
        dataTask.clear();
        dataEpic.clear();
    }

    @Override
    public void deleteEpic(Integer idEpic){
        deleteAllSubtask(idEpic);
        dataEpic.remove(idEpic);
        deleteTask(idEpic);
        getDefaultHistory().remove(idEpic);
    }

    @Override
    public void deleteAllEpic(){
        for (Map.Entry<Integer, Epic> entry : dataEpic.entrySet()) {
            deleteEpic(entry.getKey());
            deleteTask(entry.getKey());
            getDefaultHistory().remove(entry.getKey());
        }
        dataEpic.clear();
    }

    @Override
    public void deleteSubtask(Integer idEpic, Integer idSubtask){
        deleteTask(idSubtask);
        dataEpic.get(idEpic).deleteSubtask(idSubtask);
        getDefaultHistory().remove(idSubtask);
    }

    @Override
    public void deleteAllSubtask(Integer idEpic){
        for (Map.Entry<Integer, Subtask> entry : dataEpic.get(idEpic).getDataSubtask().entrySet()) {
            deleteTask(entry.getKey());
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

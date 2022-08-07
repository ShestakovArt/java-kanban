import templateTask.Epic;
import templateTask.Subtask;
import templateTask.Task;

import java.util.HashMap;

public class ManageTask {
    private HashMap<Integer, Task> dataTask = new HashMap<>();
    private HashMap<Integer, Epic> dataEpic = new HashMap<>();

    /**
     * Добавить задачу
     * @param task объект Task
     */
    public void addTask(Task task){
        dataTask.put(task.getId(), task);
    }

    /**
     * Добавить эпик
     * @param epic объект Epic
     */
    public void addEpic(Epic epic){;
        dataEpic.put(epic.getId(), epic);
    }

    /**
     * Получение задачи по ID
     * @param id ID задачи
     * @return объект Task
     */
    public Task getTaskById(Integer id){
        return getDataTask().get(id);
    }

    /**
     * Получение эпика по ID
     * @param id ID эпика
     * @return объект Epic
     */
    public Epic getEpicById(Integer id){
        return getDataEpic().get(id);
    }

    /**
     * Обновление задачи
     * @param task обновленный объект Task
     */
    public void updateTask(Task task){
        dataTask.put(task.getId(), task);
    }

    /**
     * Обновление эпика
     * @param epic обновленный объект Epic
     */
    public void updateEpic(Epic epic){
        dataEpic.put(epic.getId(), epic);
    }

    /**
     * Метод для удаления конкретной задачи
     * @param idTask ID задачи
     */
    public void deleteTask(Integer idTask){
        dataTask.remove(idTask);
    }

    /**
     * Метод для удаления всех задач
     */
    public void deleteAllTask(){
        dataTask.clear();
    }

    /**
     * Метод для удаления конкретного эпика
     * @param idEpic ID эпика
     */
    public void deleteEpic(Integer idEpic){
        dataEpic.remove(idEpic);
    }

    /**
     * Метод для удаления всех эпиков
     */
    public void deleteAllEpic(){
        dataEpic.clear();
    }

    /**
     * Метод для удаления конкретной подзадачи эпика
     * @param idEpic ID эпика
     * @param idSubtask ID подзадачи
     */
    public void deleteSubtask(Integer idEpic, Integer idSubtask){
        dataEpic.get(idEpic).deleteSubtask(idSubtask);
    }

    /**
     * Метод для удаления всех подзадач эпика
     */
    public void deleteAllSubtask(Integer idEpic){
        dataEpic.get(idEpic).deleteAllSubtask();
    }

    /**
     * Метод для просмотра всех задач
     * @return список задач
     */
    public HashMap<Integer, Task> getDataTask() {
        return dataTask;
    }

    /**
     * Метод для просмотра всех эпиков
     * @return список эпиков
     */
    public HashMap<Integer, Epic> getDataEpic() {
        return dataEpic;
    }

    /**
     * Метод для получения списка подзадач определенного эпика
     * @param idEpic ID эпика
     * @return мапа с подзадачами
     */
    public HashMap<Integer, Subtask> getSubtaskEpic(Integer idEpic){
        return dataEpic.get(idEpic).getDataSubtask();
    }

    public void addSubtaskForEpic(Integer idEpic, String nameTask, String description){
        dataEpic.get(idEpic).createSubtask(nameTask, description);
    }
}

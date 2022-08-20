package managerUtil;

import templateTask.BaseTask;
import templateTask.Epic;
import templateTask.Subtask;
import templateTask.Task;

import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    /**
     * Добавить задачу
     * @param task объект Task
     */
    public void addTask(Task task);

    /**
     * Добавить эпик
     * @param epic объект Epic
     */
    public void addEpic(Epic epic);

    /**
     * Получение задачи по ID
     * @param id ID задачи
     */
    public Task getTask(Integer id);

    /**
     * Получение эпика по ID
     * @param id ID эпика
     */
    public Epic getEpic(Integer id);

    /**
     * Получение подзадачи эпика по ID
     * @param idEpic ID эпика
     * @param idSubtask ID подзадачи
     * @return
     */
    public Subtask getSubtask(Integer idEpic, Integer idSubtask);

    /**
     * Обновление задачи
     * @param task обновленный объект Task
     */
    public void updateTask(Task task);

    /**
     * Обновление эпика
     * @param epic обновленный объект Epic
     */
    public void updateEpic(Epic epic);

    /**
     * Метод для удаления конкретной задачи
     * @param idTask ID задачи
     */
    public void deleteTask(Integer idTask);
    /**
     * Метод для удаления всех задач
     */
    public void deleteAllTask();

    /**
     * Метод для удаления конкретного эпика
     * @param idEpic ID эпика
     */
    public void deleteEpic(Integer idEpic);

    /**
     * Метод для удаления всех эпиков
     */
    public void deleteAllEpic();

    /**
     * Метод для удаления конкретной подзадачи эпика
     * @param idEpic ID эпика
     * @param idSubtask ID подзадачи
     */
    public void deleteSubtask(Integer idEpic, Integer idSubtask);

    /**
     * Метод для удаления всех подзадач эпика
     */
    public void deleteAllSubtask(Integer idEpic);

    /**
     * Метод для просмотра всех задач
     */
    public HashMap<Integer, Task> getDataTask();

    /**
     * Метод для просмотра всех эпиков
     */
    public HashMap<Integer, Epic> getDataEpic();

    /**
     * Метод для получения списка подзадач определенного эпика
     * @param idEpic ID эпика
     */
    public HashMap<Integer, Subtask> getSubtaskEpic(Integer idEpic);

    /**
     * Метод для добавления подзадачи для Эпика
     * @param idEpic ID эпика
     * @param nameTask имя подзадачи
     * @param description описание подзадачи
     */
    public void addSubtaskForEpic(Integer idEpic, String nameTask, String description);

    /**
     * Метод для просмотра истории просмотров задач
     * @return
     */
    public List<BaseTask> getHistory();
}

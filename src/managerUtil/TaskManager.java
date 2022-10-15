package managerUtil;

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
    void addTask(Task task);
    void addTask(Epic task);
    void addTask(Integer idEpic, Subtask subtask);

    /**
     * Получение задачи по ID
     * @param id ID задачи
     */
    Task getTask(Integer id);

    /**
     * Метод для удаления конкретной задачи
     * @param idTask ID задачи
     */
    void deleteTask(Integer idTask);
    /**
     * Метод для удаления всех задач
     */
    void deleteAllTask();

    /**
     * Метод для удаления конкретного эпика
     * @param idEpic ID эпика
     */
    void deleteEpic(Integer idEpic);

    /**
     * Метод для удаления всех эпиков
     */
    void deleteAllEpic();

    /**
     * Метод для удаления конкретной подзадачи эпика
     * @param idEpic ID эпика
     * @param idSubtask ID подзадачи
     */
    void deleteSubtask(Integer idEpic, Integer idSubtask);

    /**
     * Метод для удаления всех подзадач эпика
     */
    void deleteAllSubtask(Integer idEpic);

    /**
     * Метод для просмотра всех задач
     */
    HashMap<Integer, Task> getDataTask();

    /**
     * Метод для просмотра всех эпиков
     */
    HashMap<Integer, Epic> getDataEpic();

    /**
     * Метод для получения списка подзадач определенного эпика
     * @param idEpic ID эпика
     */
    HashMap<Integer, Subtask> getSubtaskEpic(Integer idEpic);

    /**
     * Метод для просмотра истории просмотров задач
     * @return
     */
    List<Task> getHistory();
}

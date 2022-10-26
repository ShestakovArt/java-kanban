package managerUtil;

import templateTask.Task;

import java.util.List;

public interface HistoryManager {

    /**
     * Метод для добавления задачи в список просмотренных задач
     * @param task задача
     */
    void add(Task task);

    /**
     * Метод для просмотра истории просмотров задач
     * @return
     */
    List<Task> getHistory();

    /**
     *  Метод для удаления задачи из просмотра
     * @param id
     */
    void remove(int id);
}

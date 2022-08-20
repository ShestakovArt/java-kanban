package managerUtil;

import templateTask.BaseTask;

import java.util.List;

public interface HistoryManager {

    /**
     * Метод для добавления задачи в список просмотренных задач
     * @param task задача
     */
    public void add(BaseTask task);

    /**
     * Метод для просмотра истории просмотров задач
     * @return
     */
    public List<BaseTask> getHistory();
}

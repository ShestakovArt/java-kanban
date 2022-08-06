package templateTask;

import status.Status;
import java.util.HashMap;

public class Epic extends BaseTask{
    private HashMap<Integer, Task> dataSubtask = new HashMap<>();
    private int id;
    private static int counter;

    static {
        counter = 1;
    }

    public Epic(String nameTask, String description, String statusTask) {
        super(nameTask, description, statusTask);
        id = counter++;
    }


    public Task createSubtask(String nameSubtask, String description){
        Task subtask = new Subtask(nameSubtask, description, Status.NEW.getCode(), getId());
        dataSubtask.put(subtask.getId(), subtask);
        return subtask;
    }
    public Task createSubtask(Task task){
        Task subtask = new Subtask(task.getNameTask(), task.getDescription(), Status.NEW.getCode(), getId());
        dataSubtask.put(subtask.getId(), subtask);
        return subtask;
    }

    public void updateNameSubtask(Integer idSubtask, String nameTask){
        dataSubtask.get(idSubtask).setNameTask(nameTask);
    }

    public void updateDescriptionSubtask(Integer idSubtask, String description){
        dataSubtask.get(idSubtask).setDescription(description);
    }

    public HashMap<Integer, Task> getDataSubtask() {
        return dataSubtask;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Эпик{" +
                "Имя эпика='" + super.getNameTask() + '\'' +
                ", Описание='" + super.getDescription() + '\'' +
                ", ID=" + getId() +
                ", Статус='" + super.getStatusTask() + '\'' +
                ", Подзадачи " + dataSubtask +
                '}';
    }
}

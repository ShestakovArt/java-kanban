package templateTask;

import status.Status;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task{
    private HashMap<Integer, Task> dataSubtask = new HashMap<>();
    private Integer idSubtask = 0;

    public Epic(String nameTask, String description, int id, String statusTask) {
        super(nameTask, description, id, statusTask);
    }

    public Task createSubtask(String nameSubtask, String description){
        idSubtask +=1;
        Task subtask = new Subtask(nameSubtask, description, idSubtask, Status.NEW.getCode());
        dataSubtask.put(idSubtask, subtask);
        return subtask;
    }
    public Task createSubtask(Task task){
        idSubtask +=1;
        Task subtask = new Task(task.getNameTask(), task.getDescription(), idSubtask, Status.NEW.getCode());
        dataSubtask.put(idSubtask, subtask);
        return subtask;
    }

    public void updateNameSubtask(Integer idSubtask, String nameTask){
        dataSubtask.get(idSubtask).setNameTask(nameTask);
    }

    public void updateDescriptionSubtask(Integer idSubtask, String description){
        dataSubtask.get(idSubtask).setDescription(description);
    }

    @Override
    public String toString() {
        return "Эпик{" +
                "Имя эпика='" + super.getNameTask() + '\'' +
                ", Описание='" + super.getDescription() + '\'' +
                ", ID=" + super.getId() +
                ", Статус='" + super.getStatusTask() + '\'' +
                ", Подзадачи " + dataSubtask +
                '}';
    }
}

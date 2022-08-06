package templateTask;

public class Subtask extends Task{
    public Subtask(String nameTask, String description, int id, String statusTask) {
        super(nameTask, description, id, statusTask);
    }

    @Override
    public String toString() {
        return "Подзадача{" +
                "Имя задачи='" + super.getNameTask() + '\'' +
                ", Описание='" + super.getDescription() + '\'' +
                ", ID=" + super.getId() +
                ", Статус='" + super.getStatusTask() + '\'' +
                '}';
    }
}

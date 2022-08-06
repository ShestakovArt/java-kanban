package templateTask;

public class Subtask extends Task{
    int idEpic;
    private int id;
    private static int counter;

    static {
        counter = 1;
    }

    public Subtask(String nameTask, String description, String statusTask, int idEpic) {
        super(nameTask, description, statusTask);
        id = counter++;
        this.idEpic = idEpic;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Подзадача{" +
                "Имя задачи='" + super.getNameTask() + '\'' +
                ", Описание='" + super.getDescription() + '\'' +
                ", ID=" + getId() +
                ", Статус='" + super.getStatusTask() + '\'' +
                ", номер Эпика= " + idEpic +
                '}';
    }
}

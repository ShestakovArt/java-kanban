package templateTask;

public class Subtask extends BaseTask{
    int idEpic;
    private int id;
    private static int counter;

    static {
        counter = 1;
    }

    protected Subtask(String nameTask, String description) {
        super(nameTask, description);
        id = counter++;
    }

    protected int getIdEpic() {
        return idEpic;
    }

    protected void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }

    protected int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
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

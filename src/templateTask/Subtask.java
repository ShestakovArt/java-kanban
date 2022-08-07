package templateTask;

import status.Status;

public class Subtask extends BaseTask{
    private String status;
    int idEpic;
    private int id;

    protected Subtask(String nameTask, String description, int id) {
        super(nameTask, description);
        this.id = id;
        this.status = Status.NEW.getCode();
    }

    protected String getStatus() {
        return status;
    }

    protected void setStatus(String status) {
        this.status = status;
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
                ", Статус='" + getStatus() + '\'' +
                ", номер Эпика= " + idEpic +
                '}';
    }
}

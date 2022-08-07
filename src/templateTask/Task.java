package templateTask;

import status.Status;

public class Task extends BaseTask{
    private String status;
    private int id;
    private static int counter;

    static {
        counter = 1;
    }

    public Task(String nameTask, String description) {
        super(nameTask, description);
        this.status = Status.NEW.getCode();
        this.id = counter++;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Задача{" +
                "Имя задачи='" + nameTask + '\'' +
                ", Описание='" + description + '\'' +
                ", ID=" + id +
                ", Статус='" + getStatus() + '\'' +
                '}';
    }
}

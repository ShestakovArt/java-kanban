package templateTask;

public class Task extends BaseTask{
    private int id;
    private static int counter;

    static {
        counter = 1;
    }

    public Task(String nameTask, String description, String statusTask) {
        super(nameTask, description, statusTask);
        this.id = counter++;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "Задача{" +
                "Имя задачи='" + nameTask + '\'' +
                ", Описание='" + description + '\'' +
                ", ID=" + id +
                ", Статус='" + statusTask + '\'' +
                '}';
    }
}

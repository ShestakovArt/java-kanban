package templateTask;

import enums.Status;
import enums.TypeTask;

public class Task extends BaseTask{
    private String status;
    private Integer id;
    private static int counter;
    protected String typeTask = TypeTask.TASK.getCode();

    static {
        counter = 1;
    }

    public Task(String nameTask, String description) {
        super(nameTask, description);
        this.status = Status.NEW.getCode();
        this.id = counter++;
    }

    public Task(int id,String nameTask, String description) {
        super(nameTask, description);
        this.status = Status.NEW.getCode();
        this.id = id;
    }

    /**
     * Получить статус задачи
     * @return статус задачи
     */
    public String getStatus() {
        return status;
    }

    /**
     * Установить статус задачи
     * @param status статус задачи
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Получить ID задачи
     * @return ID задачи
     */
    public int getId() {
        return id;
    }

    /**
     * Установить ID задачи
     * @param id ID задачи
     */
    private void setId(int id) {
        this.id = id;
    }

    public String getTypeTask(){
        return typeTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s", getId(), getTypeTask(),nameTask, getStatus(),description);
    }
}

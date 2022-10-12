package templateTask;

import enums.Status;
import enums.TypeTask;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task extends BaseTask{
    private String status;
    private Integer id;
    private static int counter;
    protected String typeTask = TypeTask.TASK.getCode();
    private Duration duration = Duration.ofMinutes(0);
    private LocalDateTime startTime = LocalDateTime.now();

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

    public Task(int id,String nameTask, String description, String status) {
        super(nameTask, description);
        Status[] st = Status.values();
        for (Status s : st) {
            if(s.getCode().equals(status)){
                this.status = s.getCode();
            }
        }
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

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Task.counter = counter;
    }

    public Duration getDuration() {
        return duration;
    }

    /**
     * Устанавливаем продолжительность задачи, оценка того, сколько времени она займёт в минутах (число)
     * @param minutes
     */
    public void setDuration(long minutes) {
        this.duration = duration.plusMinutes(minutes);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Получаем дату окончания задачи
     * @return
     */
    public LocalDateTime getEndTime(){
        return startTime.plusMinutes(duration.toMinutes());
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

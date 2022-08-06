package templateTask;

public class Task {
    private String nameTask;
    private String description;
    private int id;
    private String statusTask;

    public Task(){
    }

    public Task(String nameTask, String description, int id, String statusTask) {
        this.nameTask = nameTask;
        this.description = description;
        this.id = id;
        this.statusTask = statusTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }


    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
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

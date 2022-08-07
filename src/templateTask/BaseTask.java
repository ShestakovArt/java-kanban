package templateTask;

public abstract class BaseTask {
    protected String nameTask;
    protected String description;

    public BaseTask(String nameTask, String description) {
        this.nameTask = nameTask;
        this.description = description;
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
}

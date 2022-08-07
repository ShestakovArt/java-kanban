package templateTask;
import status.Status;


public abstract class BaseTask {
    protected String nameTask;
    protected String description;
    protected String statusTask;

    public BaseTask(String nameTask, String description) {
        this.nameTask = nameTask;
        this.description = description;
        setStatusTask(Status.NEW.getCode());
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

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }
}

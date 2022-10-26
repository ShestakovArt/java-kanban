package templateTask;

public abstract class BaseTask {
    protected String nameTask;
    protected String description;


    /**
     * Получить имя задачи
     * @return имя задачи
     */
    public String getNameTask() {
        return nameTask;
    }

    /**
     * Установить имя задачи
     * @param nameTask имя задачи
     */
    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    /**
     * Получить описание задачи
     * @return описание задачи
     */
    public String getDescription() {
        return description;
    }

    /**
     * Установить описание задачи
     * @param description описание задачи
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BaseTask{" +
                "nameTask='" + nameTask + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

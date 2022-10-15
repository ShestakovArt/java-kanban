package templateTask;

import enums.TypeTask;

import java.time.format.DateTimeFormatter;

public class Subtask extends Task{
    int idEpic;

    public Subtask(String nameTask, String description) {
        super(nameTask, description);
        this.typeTask = TypeTask.SUBTASK.getCode();
    }

    public Subtask(String nameTask, String description, int idEpic) {
        super(nameTask, description);
        this.typeTask = TypeTask.SUBTASK.getCode();
        setIdEpic(idEpic);
    }

    public Subtask(int id, String nameTask, String description) {
        super(id, nameTask, description);
        this.typeTask = TypeTask.SUBTASK.getCode();
    }

    public Subtask(int id, String nameTask, String description, int idEpic) {
        super(id, nameTask, description);
        this.typeTask = TypeTask.SUBTASK.getCode();
        setIdEpic(idEpic);
    }

    public Subtask(int id,String nameTask, String description, String status, int idEpic) {
        super(id, nameTask, description, status);
        this.typeTask = TypeTask.SUBTASK.getCode();
        setIdEpic(idEpic);
    }

    /**
     * Получить ID эпика к которой относится подзадача
     * @return ID эпика
     */
    public int getIdEpic() {
        return idEpic;
    }

    /**
     * Установить ID эпика для подзадачи
     * @param idEpic ID эпика
     */
    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }

    @Override
    public String toString() {
        String formatStartTime = null;
        if(getStartTime() != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            formatStartTime = getStartTime().format(formatter);
        }
        return String.format("%d,%s,%s,%s,%s,%s,%s,%d", getId(), getTypeTask(),nameTask, getStatus(),description, formatStartTime, getDuration().toString(), getIdEpic());
    }
}

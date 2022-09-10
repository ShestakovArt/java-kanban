package templateTask;

public class Subtask extends Task{
    int idEpic;

    public Subtask(String nameTask, String description) {
        super(nameTask, description);
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
        return "Подзадача{" +
                "Имя задачи='" + super.getNameTask() + '\'' +
                ", Описание='" + super.getDescription() + '\'' +
                ", ID=" + getId() +
                ", Статус='" + getStatus() + '\'' +
                ", ID Эпика= " + idEpic +
                '}';
    }
}

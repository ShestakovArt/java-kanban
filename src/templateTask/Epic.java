package templateTask;

import status.Status;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class Epic extends BaseTask{
    private HashMap<Integer, Subtask> dataSubtask = new HashMap<>();
    private String status;
    private int id;
    private static int counter;
    private int idSubtask = 0;

    static {
        counter = 1;
    }

    public Epic(String nameTask, String description) {
        super(nameTask, description);
        this.status = Status.NEW.getCode();
        id = counter++;
    }

    /**
     * Метод устанавливает статус эпика, учитывая имеющиеся подзадачи
     */
    private void cheskAndSetStatus(){
        if(dataSubtask.size() > 0){
            int countNew = 0;
            int countDone = 0;
            Set<Integer> keys = dataSubtask.keySet();
            for(Integer key : keys){
                if (dataSubtask.get(key).getStatus().equals(Status.NEW.getCode())) {
                    countNew++;
                }
                if (Objects.equals(dataSubtask.get(key).getStatus(), Status.DONE.getCode())) {
                    countDone++;
                }
            }
            if(countNew == dataSubtask.size()) {
                setStatus(Status.NEW.getCode());
            }
            else if(countDone == dataSubtask.size()) {
                setStatus(Status.DONE.getCode());
            }
            else{
                setStatus(Status.IN_PROGRESS.getCode());
            }
        }
        else{
            setStatus(Status.NEW.getCode());
        }
    }

    /**
     * Метод для создания подзадачи в эпике
     * @param nameTask
     * @param description
     */
    public void createSubtask(String nameTask, String description){
        idSubtask++;
        Subtask subtask = new Subtask(nameTask, description, idSubtask);
        dataSubtask.put(subtask.getId(), subtask);
        subtask.setIdEpic(getId());
        cheskAndSetStatus();
    }

    /**
     * Получение подзадачи по ID
     * @param id ID подзадачи
     * @return подзадача
     */
    public Subtask getSubtask(Integer id){
        return getDataSubtask().get(id);
    }

    /**
     * Метод для изменения имени подзадачи
     * @param idSubtask ID подзадачи
     * @param name имя
     */
    public void updateNameSubtask(Integer idSubtask, String name){
        dataSubtask.get(idSubtask).setNameTask(name);
    }

    /**
     * Метод для изменения описания подзадачи
     * @param idSubtask ID подзадачи
     * @param description описание
     */
    public void updateDescriptionSubtask(Integer idSubtask, String description){
        dataSubtask.get(idSubtask).setDescription(description);
    }

    /**
     * Метод для изменения статуса подзадачи
     * @param idSubtask ID подзадачи
     * @param status статус
     */
    public void updateStatusSubtask(Integer idSubtask, String status){
        dataSubtask.get(idSubtask).setStatus(status);
        cheskAndSetStatus();
    }

    /**
     * Метод для получения списка подзадач
     * @return мапа с подзадачами
     */
    public HashMap<Integer, Subtask> getDataSubtask() {
        return dataSubtask;
    }

    /**
     * Метод для удаления конкретной подзадачи
     * @param idSubtask ID подзадачи
     */
    public void deleteSubtask(Integer idSubtask){
        dataSubtask.remove(idSubtask);
        cheskAndSetStatus();
    }

    /**
     * Метод для удаления всех подзадач
     */
    public void deleteAllSubtask(){
        dataSubtask.clear();
        cheskAndSetStatus();
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Эпик{" +
                "Имя эпика='" + super.getNameTask() + '\'' +
                ", Описание='" + super.getDescription() + '\'' +
                ", ID=" + getId() +
                ", Статус='" + getStatus() + '\'' +
                ", Количество подзадач=" + dataSubtask.size() +
                '}';
    }
}

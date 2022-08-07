package templateTask;

import status.Status;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class Epic extends BaseTask{
    private HashMap<Integer, Subtask> dataSubtask = new HashMap<>();
    private int id;
    private static int counter;

    static {
        counter = 1;
    }

    public Epic(String nameTask, String description) {
        super(nameTask, description);
        id = counter++;
        cheskAndSetStatus();
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
                if (dataSubtask.get(key).getStatusTask().equals(Status.NEW.getCode())) {
                    countNew++;
                }
                if (Objects.equals(dataSubtask.get(key).getStatusTask(), Status.DONE.getCode())) {
                    countDone++;
                }
            }
            if(countNew == dataSubtask.size()) {
                setStatusTask(Status.NEW.getCode());
            }
            else if(countDone == dataSubtask.size()) {
                setStatusTask(Status.DONE.getCode());
            }
            else{
                setStatusTask(Status.IN_PROGRESS.getCode());
            }
        }
        else{
            setStatusTask(Status.NEW.getCode());
        }
    }

    /**
     * Метод для создания подзадачи в эпике
     * @param nameTask
     * @param description
     */
    public void createSubtask(String nameTask, String description){
        Subtask subtask = new Subtask(nameTask, description);
        dataSubtask.put(subtask.getId(), subtask);
        subtask.setIdEpic(getId());
        cheskAndSetStatus();
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
        dataSubtask.get(idSubtask).setStatusTask(status);
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

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Эпик{" +
                "Имя эпика='" + super.getNameTask() + '\'' +
                ", Описание='" + super.getDescription() + '\'' +
                ", ID=" + getId() +
                ", Статус='" + super.getStatusTask() + '\'' +
                ", Количество подзадач=" + dataSubtask.size() +
                '}';
    }
}

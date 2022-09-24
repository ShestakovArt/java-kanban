package templateTask;

import enums.Status;
import enums.TypeTask;

import java.util.*;

public class Epic extends Task{
    private HashMap<Integer, Subtask> dataSubtask = new HashMap<>();

    public Epic(String nameTask, String description) {
        super(nameTask, description);
        this.typeTask = TypeTask.EPIC.getCode();
    }

    public Epic(int id, String nameTask, String description) {
        super(id, nameTask, description);
        this.typeTask = TypeTask.EPIC.getCode();
    }

    /**
     * Метод устанавливает статус эпика, учитывая имеющиеся подзадачи
     */
    private void cheskAndSetStatus(){
        if(dataSubtask.size() > 0){
            int countNew = 0;
            int countDone = 0;
            for(Map.Entry<Integer, Subtask> subtask : dataSubtask.entrySet()){
                if (subtask.getValue().getStatus().equals(Status.NEW.getCode())) {
                    countNew++;
                }
                if (Objects.equals(subtask.getValue().getStatus(), Status.DONE.getCode())) {
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
     * Получить статус задачи
     * @return статус задачи
     */
    @Override
    public String getStatus() {
        cheskAndSetStatus();
        return super.getStatus();
    }

    /**
     * Метод для добавления подзадачи в эпик
     * @param subtask подзадача(содержит название и описание)
     * @return подзадача с ID эпика в котором находится
     */
    public void addSubTask(Subtask subtask){
        dataSubtask.put(subtask.getId(), subtask);
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
    public void updateStatusSubtask(int idSubtask, String status){
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
}

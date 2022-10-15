package templateTask;

import enums.Status;
import enums.TypeTask;

import java.time.LocalDateTime;
import java.util.*;

public class Epic extends Task{
    private HashMap<Integer, Subtask> dataSubtask = new HashMap<>();
    private LocalDateTime endTime;

    public Epic(String nameTask, String description) {
        super(nameTask, description);
        this.typeTask = TypeTask.EPIC.getCode();
    }

    public Epic(int id, String nameTask, String description) {
        super(id, nameTask, description);
        this.typeTask = TypeTask.EPIC.getCode();
    }

    public Epic(int id,String nameTask, String description, String status) {
        super(id, nameTask, description, status);
        this.typeTask = TypeTask.EPIC.getCode();
    }

    /**
     * Метод устанавливает статус эпика, учитывая имеющиеся подзадачи
     */
    private void checkAndSetStatus(){
        if(dataSubtask.size() > 0){
            int countNew = 0;
            int countDone = 0;
            long minutes = 0;
            for(Map.Entry<Integer, Subtask> subtask : dataSubtask.entrySet()){
                if (subtask.getValue().getStatus().equals(Status.NEW.getCode())) {
                    countNew++;
                }
                if (Objects.equals(subtask.getValue().getStatus(), Status.DONE.getCode())) {
                    countDone++;
                }
                minutes = minutes + subtask.getValue().getDuration().toMinutes();
                if(subtask.getValue().getStartTime() != null){
                    if (super.getStartTime() != null) {
                        if (super.getStartTime().isAfter(subtask.getValue().getStartTime())){
                            super.setStartTime(subtask.getValue().getStartTime());
                        }
                        if(endTime.isBefore(subtask.getValue().getEndTime()) || endTime == null){
                            endTime = subtask.getValue().getEndTime();
                        }
                    }
                    if(super.getStartTime() == null){
                        super.setStartTime(subtask.getValue().getStartTime());
                        endTime = subtask.getValue().getEndTime();
                    }
                }
            }
            super.setDuration(minutes);
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
        checkAndSetStatus();
        return super.getStatus();
    }

    @Override
    public LocalDateTime getEndTime(){
        checkAndSetStatus();
        return endTime;
    }

    /**
     * Метод для добавления подзадачи в эпик
     * @param subtask подзадача(содержит название и описание)
     */
    public void addSubTask(Subtask subtask){
        subtask.setIdEpic(super.getId());
        dataSubtask.put(subtask.getId(), subtask);
        checkAndSetStatus();
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
        checkAndSetStatus();
    }

    /**
     * Метод для удаления всех подзадач
     */
    public void deleteAllSubtask(){
        dataSubtask.clear();
        checkAndSetStatus();
    }
}

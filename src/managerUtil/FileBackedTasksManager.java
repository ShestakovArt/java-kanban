package managerUtil;

import enums.Status;
import enums.TypeTask;
import exception.ManagerSaveException;
import templateTask.Epic;
import templateTask.Subtask;
import templateTask.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


public class FileBackedTasksManager extends InMemoryTaskManager{
    private static String filePath;
    private static String dataFile;

    public FileBackedTasksManager(String path) throws IOException {
        filePath = path;
        if (!Files.exists(Path.of(filePath))) {
            createFile();
        }
        dataFile = Files.readString(Path.of(filePath));
    }

    Task fromString(String value){
        String[] dataTask = value.split(",");
        return new Task(dataTask[2], dataTask[4]);
    }

    String getDataFile(){
        return dataFile;
    }

    void save(){
        try {
            if (Files.exists(Path.of(filePath))) {
                Files.delete(Path.of(filePath));
            }
            createFile();
            //Запись первой строчки в файл
            Writer fileWriter = new FileWriter(filePath, false);
            fileWriter.write("id,type,name,status,description,epicId\n");

            for(Map.Entry<Integer, Task> entry : getDataTask().entrySet()){
                fileWriter.write(entry.getValue().toString() + "\n");
            }
            fileWriter.write("\n");
            for(Task task : getHistory()){
                fileWriter.write(task.getId() + ",");
            }
            fileWriter.close();
        }
        catch (IOException e){
            try {
                throw new ManagerSaveException();
            } catch (ManagerSaveException ex) {
                ex.printStackTrace();
            }
        }
    }

    void createFile(){
        try{
            Files.createFile(Path.of(filePath));
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addTask(Task task){
        super.addTask(task);
        save();
    }

    @Override
    public Task getTask(Integer id){
        save();
        return super.getTask(id);
    }

    public static void main(String[] args) throws IOException {
        FileBackedTasksManager fm = new FileBackedTasksManager("src/fileRecousre/dataFile.csv");
        Task taskOne = new Task("Задача 1", "Описание з1");
        Task taskTwo = new Task("Задача 2", "Описание з2");
        Epic epicOne = new Epic("Эпик 1", "Описание эп1");
        Epic epicTwo = new Epic("Эпик 2", "Описание эп2");


        fm.addTask(taskOne);
        fm.addTask(taskTwo);
        fm.addTask(epicOne);
        Subtask subTaskOneEOne = new Subtask("Подзадача 1 эп 1","Описание п1 эп1");
        Subtask subTaskTwoEOne = new Subtask("Подзадача 2 эп 1","Описание п2 эп1");
        Subtask subTaskThreeEOne = new Subtask("Подзадача 3 эп 1","Описание п3 эп1");
        fm.addTask(epicOne.getId(), subTaskOneEOne);
        fm.addTask(epicOne.getId(), subTaskTwoEOne);
        fm.addTask(epicOne.getId(), subTaskThreeEOne);
        fm.addTask(epicTwo);
        fm.getTask(1).setStatus(Status.DONE.getCode());
        fm.getTask(6).setStatus(Status.IN_PROGRESS.getCode());
        var a1 = fm.getTask(6);
        System.out.println("\n-----Созданные объекты-----\n");
        for (Map.Entry<Integer, Task> task : fm.getDataTask().entrySet()){
            System.out.println(task.getValue());
        }

        fm.getTask(epicOne.getId());
        viewHistory(fm);

        fm.getTask(subTaskOneEOne.getId());
        viewHistory(fm);

        fm.getTask(epicTwo.getId());
        viewHistory(fm);

        fm.getTask(taskOne.getId());
        viewHistory(fm);

        fm.getTask(epicTwo.getId());
        viewHistory(fm);

        System.out.println();

        FileBackedTasksManager fm2 = new FileBackedTasksManager("src/fileRecousre/dataFile.csv");
        String a = fm2.getDataFile();
        String[] ab = a.split("\\n");
        for (int i = 1; i < ab.length; i++){
            if (i < (ab.length -2)){
                String[] task = ab[i].split(",");
                if(task[1].equals(TypeTask.TASK.getCode())){
                    fm2.addTask(new Task(Integer.parseInt(task[0]), task[2], task[4]));
                }
                if(task[1].equals(TypeTask.EPIC.getCode())){
                    fm2.addTask(new Epic(Integer.parseInt(task[0]), task[2], task[4]));
                }
                if(task[1].equals(TypeTask.SUBTASK.getCode())){
                    fm2.addTask(Integer.parseInt(task[5]), new Subtask(Integer.parseInt(task[0]), task[2], task[4]));
                }
            }
        }


        for (Map.Entry<Integer, Task> task : fm2.getDataTask().entrySet()){
            System.out.println(task.getValue());
        }

        System.out.println(fm.getDataTask());
        System.out.println(fm2.getDataTask());
        fm.getTask(epicOne.getId());
        viewHistory(fm);
    }

    static void viewHistory(FileBackedTasksManager fm){
        System.out.println("\n-----История просмотров-----\n");
        for (Task task : fm.getHistory()){
            System.out.println(task);
        }
        System.out.print("\nКоличество просмотров в истории просмотров ");
        System.out.print(fm.getHistory().size());
        System.out.println();
    }
}

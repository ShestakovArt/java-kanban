package managerUtil;

import enums.Status;
import enums.TypeTask;
import exception.ManagerSaveException;
import templateTask.Epic;
import templateTask.Subtask;
import templateTask.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;


public class FileBackedTasksManager extends InMemoryTaskManager{
    private static String filePath;
    private static String dataFile;

    /**
     * Конструктор
     * @param path путь файла с информацией
     * @throws IOException
     */
    public FileBackedTasksManager(String path) {
        try {
            filePath = path;
            if (!Files.exists(Path.of(filePath))) {  //Если нет файла, то создаем новый
                createFile();
            }
            else{
                loadFromFile();  //Загружаем данные из имеющегося файла
            }
        }
        catch (NullPointerException e){
            System.out.println("В путь до файла передано значение null");
        }
    }

    /**
     * Метод для загрузки информации из имеющегося файла
     */
    void loadFromFile(){
        try{
            dataFile = Files.readString(Path.of(filePath));
            String[] lineDataFile = dataFile.split("\\n");
            int maxID = 0;
            for (int i = 1; i < lineDataFile.length; i++){
                if (i < (lineDataFile.length -2)){
                    String[] task = lineDataFile[i].split(",");
                    if(Integer.parseInt(task[0]) > maxID){
                        maxID = Integer.parseInt(task[0]);
                    }
                    //в зависимости от типа добавляем задачи менеджеру задач
                    if(task[1].equals(TypeTask.TASK.getCode())){
                        super.addTask(new Task(Integer.parseInt(task[0]), task[2], task[4], task[3]));
                    }
                    if(task[1].equals(TypeTask.EPIC.getCode())){
                        super.addTask(new Epic(Integer.parseInt(task[0]), task[2], task[4], task[3]));
                    }
                    if(task[1].equals(TypeTask.SUBTASK.getCode())){
                        super.addTask(Integer.parseInt(task[5]), new Subtask(Integer.parseInt(task[0]), task[2], task[4], task[3], Integer.parseInt(task[5])));
                    }
                }
                //Тк по условию нам известно, что последняя строка содержит данные по истории просмотров,
                if (i == (lineDataFile.length - 1)){
                    String[] historyLine = lineDataFile[i].split(",");
                    for (String idTask : historyLine){
                        getDefaultHistory().add(getDataTask().get(Integer.parseInt(idTask)));
                    }
                }
            }
            /*
            Для того что бы после обработки файла, при создании новых задач формировался корректный ID,
            присваиваем значению счетчика максимальный ID из файла увеличенный на 1, дабы не затирались старые задачи новыми
             */
            Task.setCounter(maxID + 1);
        }
        catch (IOException e){
            System.out.println("Не возможно прочитать файл по указанному пути");
        }
    }

    String getDataFile(){
        return dataFile;
    }

    /**
     * Метод для сохранения информации в файл
     */
    void save(){
        try {
            //Запись первой строчки в файл
            Writer fileWriter = new FileWriter(filePath, false);
            fileWriter.write("id,type,name,status,description,epicId\n");

            //Построчно записываем данные по задачам
            for(Map.Entry<Integer, Task> entry : getDataTask().entrySet()){
                fileWriter.write(String.format("%s\n", entry.getValue().toString()));
            }
            fileWriter.write("\n");
            //Записываем данные по истории просмотров
            for(Task task : getHistory()){
                fileWriter.write(String.format("%d,",task.getId()));
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

    /**
     * Создание файла(
     */
    void createFile(){
        try{
            String dirPath = filePath.substring(0, filePath.lastIndexOf("/"));
            File dir = new File(dirPath);
            while (!dir.isDirectory()){
                dir.mkdir();
            }
            Files.createFile(Path.of(filePath));
        }
        catch (IOException e){
            System.out.println("Ошибка создания файла");
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

    /**
     * Метод по заданию для проверки
     * @param args
     */
    public static void main(String[] args){
        FileBackedTasksManager fileManager = new FileBackedTasksManager("src/fileRecousre/dataFile.csv");
        //Для посмотреть на имеющиеся задачи, если текущий файл уже существует
        for (Map.Entry<Integer, Task> task : fileManager.getDataTask().entrySet()){
            System.out.println(task.getValue());
        }

        //Создаем задачи
        Task taskOne = new Task("Задача 1", "Описание з1");
        Task taskTwo = new Task("Задача 2", "Описание з2");
        Epic epicOne = new Epic("Эпик 1", "Описание эп1");
        Epic epicTwo = new Epic("Эпик 2", "Описание эп2");


        fileManager.addTask(taskOne);
        fileManager.addTask(taskTwo);
        fileManager.addTask(epicOne);
        Subtask subTaskOneEOne = new Subtask("Подзадача 1 эп 1","Описание п1 эп1");
        Subtask subTaskTwoEOne = new Subtask("Подзадача 2 эп 1","Описание п2 эп1");
        Subtask subTaskThreeEOne = new Subtask("Подзадача 3 эп 1","Описание п3 эп1");
        fileManager.addTask(epicOne.getId(), subTaskOneEOne);
        fileManager.addTask(epicOne.getId(), subTaskTwoEOne);
        fileManager.addTask(epicOne.getId(), subTaskThreeEOne);
        fileManager.addTask(epicTwo);
        fileManager.getTask(1).setStatus(Status.DONE.getCode());
        fileManager.getTask(6).setStatus(Status.IN_PROGRESS.getCode());
        System.out.println("\n-----Созданные объекты-----\n");
        for (Map.Entry<Integer, Task> task : fileManager.getDataTask().entrySet()){
            System.out.println(task.getValue());
        }

        fileManager.getTask(epicOne.getId());
        viewHistory(fileManager);

        fileManager.getTask(subTaskOneEOne.getId());
        viewHistory(fileManager);

        fileManager.getTask(epicTwo.getId());
        viewHistory(fileManager);

        fileManager.getTask(taskOne.getId());
        viewHistory(fileManager);

        fileManager.getTask(epicTwo.getId());
        viewHistory(fileManager);

        System.out.println();

        FileBackedTasksManager fileManagerTwo = new FileBackedTasksManager("src/fileRecousre/dataFile.csv");
        //Для посмотреть на имеющиеся задачи, если текущий файл уже существует
        for (Map.Entry<Integer, Task> task : fileManagerTwo.getDataTask().entrySet()){
            System.out.println(task.getValue());
        }

        //Визуально смотрим на данные по задачам 2х менеджеров, для сравнения
        System.out.println(fileManager.getDataTask());
        System.out.println(fileManagerTwo.getDataTask());

        //Сверяем историю просмотров
        viewHistory(fileManager);
        viewHistory(fileManagerTwo);
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

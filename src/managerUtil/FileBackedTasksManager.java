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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;


public class FileBackedTasksManager extends InMemoryTaskManager{
    private static Path filePath;
    private static String dataFile;

    /**
     * Конструктор
     * @param path путь файла с информацией
     * @throws IOException
     */
    public FileBackedTasksManager(Path path) {
        if (path != null){
            this.filePath = path;
            if (!Files.exists(filePath)) {  //Если нет файла, то создаем новый
                createFile();
            }
            else{
                load();  //Загружаем данные из имеющегося файла
            }
        }
    }

    /**
     * Метод для загрузки информации
     */
    void load(){
        try{
            dataFile = Files.readString(filePath);
            String[] lineDataFile = dataFile.split("\\n");
            boolean havingHistory = lineDataFile[lineDataFile.length-1].matches("^\\b(\\d*,\\d*)*$");
            int conditionHistory = lineDataFile.length;
            if(havingHistory){
                conditionHistory = lineDataFile.length -2;
            }
            int maxID = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
            for (int i = 1; i < lineDataFile.length; i++){
                if (i < conditionHistory){
                    String[] task = lineDataFile[i].split(",");
                    if(Integer.parseInt(task[0]) > maxID){
                        maxID = Integer.parseInt(task[0]);
                    }
                    //в зависимости от типа добавляем задачи менеджеру задач
                    if(task[1].equals(TypeTask.TASK.getCode())){
                        Task taskForAddManager = new Task(Integer.parseInt(task[0]), task[2], task[4], task[3]);
                        if(!task[5].equals("null")){
                            taskForAddManager.setStartTime(LocalDateTime.parse(task[5], formatter));
                        }
                        taskForAddManager.setDuration(Duration.parse(task[6]).toMinutes());
                        super.addTask(taskForAddManager);
                        //super.addTask(new Task(Integer.parseInt(task[0]), task[2], task[4], task[3]));
                    }
                    if(task[1].equals(TypeTask.EPIC.getCode())){
                        Epic epicForAddManager = new Epic(Integer.parseInt(task[0]), task[2], task[4], task[3]);
                        super.addTask(epicForAddManager);
                        //super.addTask(new Epic(Integer.parseInt(task[0]), task[2], task[4], task[3]));
                    }
                    if(task[1].equals(TypeTask.SUBTASK.getCode())){
                        Subtask subtaskForAddManager = new Subtask(Integer.parseInt(task[0]), task[2], task[4], task[3], Integer.parseInt(task[7]));
                        if(!task[5].equals("null")){
                            subtaskForAddManager.setStartTime(LocalDateTime.parse(task[5], formatter));
                        }
                        subtaskForAddManager.setDuration(Duration.parse(task[6]).toMinutes());
                        super.addTask(Integer.parseInt(task[7]), subtaskForAddManager);
                        //super.addTask(Integer.parseInt(task[7]), new Subtask(Integer.parseInt(task[0]), task[2], task[4], task[3], Integer.parseInt(task[7])));
                    }
                }
                //Тк по условию нам известно, что последняя строка содержит данные по истории просмотров,
                if(havingHistory){
                    if (i == (lineDataFile.length - 1)){
                        String[] historyLine = lineDataFile[i].split(",");
                        if(historyLine.length > 0){
                            for (String idTask : historyLine){
                                getDefaultHistory().add(getDataTask().get(Integer.parseInt(idTask)));
                            }
                        }
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
        try (Writer fileWriter = new FileWriter(filePath.toString(), false)){
            //Запись первой строчки в файл
            fileWriter.write("id,type,name,status,description,startTime,duration,epicId\n");

            //Построчно записываем данные по задачам
            for(Map.Entry<Integer, Task> entry : getDataTask().entrySet()){
                fileWriter.write(String.format("%s\n", entry.getValue().toString()));
            }
            fileWriter.write("\n");
            //Записываем данные по истории просмотров
            for(Task task : getHistory()){
                fileWriter.write(String.format("%d,",task.getId()));
            }
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
            Path path = filePath;
            String dirPath = path.toString().substring(0, filePath.toString().lastIndexOf("\\"));
            File dir = new File(dirPath);
            while (!dir.isDirectory()){
                dir.mkdir();
            }
            Files.createFile(filePath);
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

    public void addTask(Integer epicID, Subtask subtask){
        super.addTask(epicID, subtask);
    }

    public void addTask(Epic epic){
        super.addTask(epic);
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
    public static void main(String[] args) throws InterruptedException {
        FileBackedTasksManager fileManager = new FileBackedTasksManager(Path.of("src\\fileRecourse\\dataFile.csv"));
        //Для просмотра на имеющиеся задачи, если текущий файл уже существует
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
        fileManager.getTask(1).setStartTime(LocalDateTime.now());
        fileManager.getTask(1).setDuration(30);
        fileManager.setStartTimeForTack(fileManager.getTask(6), LocalDateTime.of(2222, 10, 22, 22, 22), 300);
        fileManager.setStartTimeForTack(fileManager.getTask(5), LocalDateTime.of(2222, 10, 22, 22, 22), 30);
//        fileManager.getTask(6).setStartTime(LocalDateTime.of(2222, 10, 22, 22, 22));
//        fileManager.getTask(6).setDuration(300);
//        fileManager.getTask(5).setStartTime(LocalDateTime.of(2222, 10, 22, 22, 22));
//        fileManager.getTask(5).setDuration(30);
//        fileManager.getTask(5);
//        System.out.println(fileManager.getTask(1).getDuration());
//        System.out.println(fileManager.getTask(1).getStartTime());
//        System.out.println(fileManager.getTask(1).getEndTime());
        fileManager.addTask(epicTwo.getId(), new Subtask("Подзадача 4 эп 2","Описание п4 эп2"));
        System.out.println(fileManager.getTask(epicTwo.getId()).getEndTime());
        fileManager.getTask(1).setStatus(Status.DONE.getCode());
        fileManager.getTask(6).setStatus(Status.IN_PROGRESS.getCode());
        System.out.println("\n-----Созданные объекты-----\n");
        for (Map.Entry<Integer, Task> task : fileManager.getDataTask().entrySet()){
            System.out.println(task.getValue());
        }
        System.out.println(fileManager.getPrioritizedTasks());

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

        FileBackedTasksManager fileManagerTwo = new FileBackedTasksManager(Path.of("src\\fileRecourse\\dataFile.csv"));
        //Для просмотра имеющихся задач, если текущий файл уже существует
        for (Map.Entry<Integer, Task> task : fileManagerTwo.getDataTask().entrySet()){
            System.out.println(task.getValue());
        }

        //Визуально смотрим на данные по задачам 2х менеджеров, для сравнения
        System.out.println("\nВизуально смотрим на данные по задачам 2х менеджеров, для сравнения");
        System.out.println(fileManager.getDataTask());
        System.out.println(fileManagerTwo.getDataTask());

        //Сверяем историю просмотров
        System.out.println("\nСверяем историю просмотров");
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

import managerUtil.HistoryManager;
import managerUtil.Managers;
import managerUtil.TaskManager;
import status.Status;
import templateTask.BaseTask;
import templateTask.Epic;
import templateTask.Task;

public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");
        TaskManager manager = new Managers().getDefault();
        Task taskOne = new Task("Задача 1", "Описание з1");
        Task taskTwo = new Task("Задача 2", "Описание з2");
        Epic epicOne = new Epic("Эпик 1", "Описание эп1");
        Epic epicTwo = new Epic("Эпик 2", "Описание эп2");
        epicOne.createSubtask("Подзадача 1 эп 1","Описание п1 эп1");
        epicOne.createSubtask("Подзадача 2 эп 1","Описание п2 эп1");
        epicTwo.createSubtask("Подзадача 1 эп 2","Описание п1 эп2");

        manager.addTask(taskOne);
        manager.addTask(taskTwo);
        manager.addEpic(epicOne);
        manager.addEpic(epicTwo);
        manager.getEpic(1);
        manager.getEpic(2);
        manager.getTask(1);
        manager.getTask(2);
        System.out.println("\n-----Созданные объекты-----\n");
        System.out.println(manager.getDataEpic());
        System.out.println(manager.getDataTask());
        System.out.println(manager.getSubtaskEpic(1));
        System.out.println(manager.getSubtaskEpic(2));

        System.out.println("\n-----История просмотров созданных объектов-----\n");
        for (BaseTask task : manager.getHistory()){
            System.out.println(task);
        }
        System.out.println("\n-----Количество объектов в истории просмотров-----\n");
        System.out.println(manager.getHistory().size());

        taskOne.setStatus(Status.IN_PROGRESS.getCode());
        taskTwo.setStatus(Status.DONE.getCode());
        epicOne.updateStatusSubtask(1, Status.IN_PROGRESS.getCode());
        epicOne.updateStatusSubtask(2, Status.DONE.getCode());
        epicTwo.updateStatusSubtask(1, Status.DONE.getCode());
        System.out.println("\n-----Изменили статусы у задач и подзадач-----\n");
        System.out.println(manager.getDataEpic());
        System.out.println(manager.getDataTask());
        System.out.println(manager.getSubtaskEpic(1));
        System.out.println(manager.getSubtaskEpic(2));
        System.out.println();

        manager.getEpic(1);
        manager.getTask(1);
        manager.getTask(1);
        manager.getSubtask(1, 2);
        manager.getTask(2);
        manager.getSubtask(2, 1);
        manager.getEpic(1);
        manager.getTask(1);
        manager.getTask(1);
        manager.getSubtask(1, 2);
        manager.getTask(2);
        manager.getSubtask(2, 1);

        System.out.println("\n-----История просмотров задач-----\n");
        for (BaseTask task : manager.getHistory()){
            System.out.println(task);
        }
        System.out.println("\n-----Количество объектов в истории просмотров-----\n");
        System.out.println(manager.getHistory().size());


        manager.getTask(2);
        manager.deleteTask(2);
        manager.getEpic(1);
        manager.deleteEpic(1);
        System.out.println("\n-----Удалили задачу и эпик-----\n");
        System.out.println(manager.getDataEpic());
        System.out.println(manager.getDataTask());

        System.out.println("\n-----История просмотров задач-----\n");
        for (BaseTask task : manager.getHistory()){
            System.out.println(task);
        }
        System.out.println("\n-----Количество объектов в истории просмотров-----\n");
        System.out.println(manager.getHistory().size());

        System.out.println("\n-----Конец теста-----");
    }
}

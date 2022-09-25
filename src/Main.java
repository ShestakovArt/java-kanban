import managerUtil.Managers;
import managerUtil.TaskManager;
import templateTask.Epic;
import templateTask.Subtask;
import templateTask.Task;


import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args){

        System.out.println("Поехали!");
        TaskManager manager = new Managers().getDefault();
        Task taskOne = new Task("Задача 1", "Описание з1");
        Task taskTwo = new Task("Задача 2", "Описание з2");
        Epic epicOne = new Epic("Эпик 1", "Описание эп1");
        Epic epicTwo = new Epic("Эпик 2", "Описание эп2");

        manager.addTask(taskOne);
        manager.addTask(taskTwo);
        manager.addTask(epicOne);
        Subtask subTaskOneEOne = new Subtask("Подзадача 1 эп 1","Описание п1 эп1");
        Subtask subTaskTwoEOne = new Subtask("Подзадача 2 эп 1","Описание п2 эп1");
        Subtask subTaskThreeEOne = new Subtask("Подзадача 3 эп 1","Описание п3 эп1");
        manager.addTask(epicOne.getId(), subTaskOneEOne);
        manager.addTask(epicOne.getId(), subTaskTwoEOne);
        manager.addTask(epicOne.getId(), subTaskThreeEOne);
        manager.addTask(epicTwo);
        System.out.println("\n-----Созданные объекты-----\n");
        System.out.println(manager.getDataEpic());
        System.out.println(manager.getDataTask());
        System.out.println(manager.getSubtaskEpic(epicOne.getId()));

        manager.getTask(epicOne.getId());
        viewHistory(manager);

        manager.getTask(subTaskOneEOne.getId());
        viewHistory(manager);

        manager.getTask(epicTwo.getId());
        viewHistory(manager);

        manager.getTask(taskOne.getId());
        viewHistory(manager);

        manager.getTask(epicTwo.getId());
        viewHistory(manager);

        manager.getTask(subTaskTwoEOne.getId());
        viewHistory(manager);

        manager.getTask(taskTwo.getId());
        viewHistory(manager);

        manager.getTask(taskTwo.getId());
        viewHistory(manager);

        manager.getTask(subTaskThreeEOne.getId());
        viewHistory(manager);

        manager.getTask(subTaskTwoEOne.getId());
        viewHistory(manager);

        manager.getTask(epicOne.getId());
        viewHistory(manager);

        System.out.println();
        System.out.println("\n-----Действие-----");
        System.out.printf("Удалим: %s%n", manager.getTask(taskTwo.getId()));
        manager.deleteTask(taskTwo.getId());
        viewHistory(manager);

        System.out.println();
        System.out.println("\n-----Действие-----");
        System.out.printf("Удалим: %s%n", manager.getTask(epicOne.getId()));
        manager.deleteEpic(epicOne.getId());
        viewHistory(manager);

        System.out.println("\n-----Конец теста-----");
    }

    /**
     * Вывод истории просмотров
     * @param manager менеджер для работы с задачами
     */
    private static void viewHistory(TaskManager manager) {
        System.out.println("\n-----История просмотров-----\n");
        for (Task task : manager.getHistory()){
            System.out.println(task);
        }
        System.out.print("\nКоличество просмотров в истории просмотров ");
        System.out.print(manager.getHistory().size());

        Set<Task> uniqueTask = new HashSet<>(manager.getHistory());
        if(uniqueTask.size() == manager.getHistory().size()){
            System.out.print(" --> В истории просмотров НЕТ повторов\n");
        }
        else{
            System.out.print(" --> В истории просмотров ЕСТЬ повторы\n");
        }
    }
}

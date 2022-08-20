package managerUtil;

import templateTask.BaseTask;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    public static List<BaseTask> dataHistory = new ArrayList<>();

    @Override
    public void add(BaseTask task){
        if(dataHistory.size() == 10){
            dataHistory.remove(0);
        }
        dataHistory.add(task);
    }

    @Override
    public List<BaseTask> getHistory(){
        return dataHistory;
    }
}

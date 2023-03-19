package com.example.note_and_todo_app.todo.all;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskDao;
import com.example.note_and_todo_app.utils.Constants;

import java.util.*;

public class AllTaskViewModel extends ViewModel {
    MutableLiveData<List<TasksWithTitle>> allTaskLiveData = new MutableLiveData<>();
    private final TaskDao taskDao;

    @SuppressLint("StaticFieldLeak")
    private final Calendar today = Calendar.getInstance();
    private final Calendar tomorrow = Calendar.getInstance();

    public AllTaskViewModel(Context context) {
        taskDao = Database.getInstance(context).taskDao();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
    }

    public void fetchItems(String type) {
        if (type.equals(Constants.ALL_TASK)) {
            fetchAllTasks();
        } else {
            fetchDataNext7Days();
        }
    }

    private void fetchAllTasks() {
        List<TasksWithTitle> tasksWithTitle = new ArrayList<>();

        tasksWithTitle.add(new TasksWithTitle("Today", new ArrayList<>(), true, today));
        tasksWithTitle.add(new TasksWithTitle("Tomorrow", new ArrayList<>(), false, tomorrow));
        tasksWithTitle.add(new TasksWithTitle("Upcoming", new ArrayList<>()));
        tasksWithTitle.add(new TasksWithTitle("Someday", new ArrayList<>()));

        List<Task> tasks = taskDao.getAll();

        int currentMonth = today.get(Calendar.MONTH);
        int currentYear = today.get(Calendar.YEAR);

        for (Task task : tasks) {
            Calendar dueDate = Calendar.getInstance();
            dueDate.setTimeInMillis(task.getDueDate());
            int dueDateMonth = today.get(Calendar.MONTH);
            int dueDateYear = today.get(Calendar.YEAR);
            if (currentMonth == dueDateMonth && currentYear == dueDateYear) {
                if (dueDate.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                    int hoursDiff = dueDate.get(Calendar.HOUR_OF_DAY) - today.get(Calendar.HOUR_OF_DAY);
                    int minusDiff = dueDate.get(Calendar.MINUTE) - today.get(Calendar.MINUTE);
                    if (0 <= hoursDiff && hoursDiff <= 6 && 0 <= minusDiff) {
                        tasksWithTitle.get(2).addTask(task);
                    }
                    tasksWithTitle.get(0).addTask(task);
                    continue;
                } else if (dueDate.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH) + 1) {
                    tasksWithTitle.get(1).addTask(task);
                    continue;
                }
            }
            tasksWithTitle.get(3).addTask(task);
        }
        allTaskLiveData.postValue(tasksWithTitle);
    }

    private void fetchDataNext7Days() {
        List<Task> tasks = taskDao.getAll();
        Calendar from = getCurrentCalendar();
        Calendar to = getCurrentCalendar();
        to.add(Calendar.DAY_OF_MONTH, 7);

        Map<Integer, Integer> dateDiffInWeekMap = new HashMap<>();
        List<TasksWithTitle> tasksWithTitle = new ArrayList<>();
        Calendar nextDay = Calendar.getInstance();
        nextDay.setTimeInMillis(tomorrow.getTimeInMillis());
        tasksWithTitle.add(new TasksWithTitle("Today", new ArrayList<>(), true, today));
        tasksWithTitle.add(new TasksWithTitle("Tomorrow", new ArrayList<>(), false, tomorrow));
        int i = from.get(Calendar.DAY_OF_WEEK) + 1;
        int n = i + 5;
        int k = 0;
        dateDiffInWeekMap.put(i - 2, k++);
        dateDiffInWeekMap.put(i - 1, k++);
        for (; i < n; i++) {
            nextDay.add(Calendar.DAY_OF_MONTH, 1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(nextDay.getTimeInMillis());
            tasksWithTitle.add(new TasksWithTitle(Constants.daysOfWeek.get(i % 7), new ArrayList<>(), false, calendar));
            dateDiffInWeekMap.put(i % 7, k++);
            Log.i("TAG", Constants.daysOfWeek.get(i % 7) + " : " + calendar.get(Calendar.DAY_OF_MONTH));
        }

        for (Task task : tasks) {
            Calendar dueDate = Calendar.getInstance();
            dueDate.setTimeInMillis(task.getDueDate());
            if (from.getTimeInMillis() <= task.getDueDate() && task.getDueDate() <= to.getTimeInMillis()) {
                int dayOfWeek = dueDate.get(Calendar.DAY_OF_WEEK) - 1;
                Log.i("Day of week", dayOfWeek + "");
                if (dateDiffInWeekMap.get(dayOfWeek) != null) {
                    @SuppressWarnings("DataFlowIssue")
                    int index = dateDiffInWeekMap.get(dayOfWeek);
                    tasksWithTitle.get(index).addTask(task);
                }
            }
        }
        allTaskLiveData.postValue(tasksWithTitle);
    }

    private Calendar getCurrentCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        return calendar;
    }

    public void updateDatabase(Task task, int action) {
        if (action == Database.ACTION_UPDATE) {
            taskDao.update(task);
        } else if (action == Database.ACTION_DELETE) {
            taskDao.delete(task);
        } else if (action == Database.ACTION_INSERT) {
            taskDao.insert(task);
        }
    }
}

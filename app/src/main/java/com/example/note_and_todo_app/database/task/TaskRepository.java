package com.example.note_and_todo_app.database.task;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.note_and_todo_app.R;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.todo.all.TasksWithTitle;
import com.example.note_and_todo_app.utils.Constants;
import com.example.note_and_todo_app.utils.Utils;

import java.util.*;

public class TaskRepository {
    private final TaskDao taskDao;

    private final MutableLiveData<List<Task>> tasksLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<TasksWithTitle>> allTasksLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<TasksWithTitle>> tasks7DaysLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<TasksWithTitle>> taskNotifyScreenLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<TaskCategory>> categoriesLiveData = new MutableLiveData<>();

    private TaskRepository(Context context) {
        taskDao = Database.getInstance(context).taskDao();
    }

    private static TaskRepository INSTANCE;

    public static TaskRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TaskRepository(context);
        }
        return INSTANCE;
    }

    public MutableLiveData<List<Task>> fetchTaskByCategory(Long categoryId) {
        Database.databaseWriteExecutor.execute(() -> {
            List<Task> tasks = taskDao.getTaskByCategory(categoryId);
            tasksLiveData.postValue(tasks);
        });
        return tasksLiveData;
    }

    public void updateTask(Task task) {
        Database.databaseWriteExecutor.execute(() -> {
            taskDao.update(task);
        });
    }

    public void deleteTask(Task task) {
        Database.databaseWriteExecutor.execute(() -> {
            taskDao.delete(task);
        });
    }

    public MutableLiveData<List<TaskCategory>> fetchCategories() {
        Database.databaseWriteExecutor.execute(() -> {
            List<TaskCategory> categories = taskDao.getAllCategory();
            for (TaskCategory category : categories) {
                category.setNumberOfItem(taskDao.getNumberOfTask(category.getId()));
            }
            categoriesLiveData.postValue(categories);
        });
        return categoriesLiveData;
    }

    public MutableLiveData<List<TasksWithTitle>> fetchTasks7Days() {
        Database.databaseWriteExecutor.execute(() -> {
            Calendar today = Calendar.getInstance();
            Calendar tomorrow = Calendar.getInstance();
            tomorrow.add(Calendar.DAY_OF_MONTH, 1);

            List<Task> tasks = taskDao.getAll();
            Calendar from = Utils.getTodayAtStart();
            Calendar to = Utils.getTodayAtStart();
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
            tasks7DaysLiveData.postValue(tasksWithTitle);
        });
        return tasks7DaysLiveData;
    }

    public MutableLiveData<List<TasksWithTitle>> fetchAllTasks() {

        Database.databaseWriteExecutor.execute(() -> {
            Calendar today = Calendar.getInstance();
            Calendar tomorrow = Calendar.getInstance();
            tomorrow.add(Calendar.DAY_OF_MONTH, 1);

            List<TasksWithTitle> tasksWithTitle = new ArrayList<>();
            tasksWithTitle.add(new TasksWithTitle("Missed", new ArrayList<>(), true));
            tasksWithTitle.get(0).setTitleTextColor(R.color.red_80);
            tasksWithTitle.add(new TasksWithTitle("Today", new ArrayList<>(), true, Utils.getTodayAtEnd()));
            tasksWithTitle.add(new TasksWithTitle("Tomorrow", new ArrayList<>(), false, tomorrow));
            tasksWithTitle.add(new TasksWithTitle("Upcoming", new ArrayList<>()));
            tasksWithTitle.add(new TasksWithTitle("Someday", new ArrayList<>()));

            List<Task> tasks = taskDao.getAll();

            int currentMonth = today.get(Calendar.MONTH);
            int currentYear = today.get(Calendar.YEAR);
            Calendar dueDate = Calendar.getInstance();

            for (Task task : tasks) {

                if (task.getDueDate() < today.getTimeInMillis()) {
                    tasksWithTitle.get(0).getTasks().add(task);
                    continue;
                }
                dueDate.setTimeInMillis(task.getDueDate());
                int dueDateMonth = today.get(Calendar.MONTH);
                int dueDateYear = today.get(Calendar.YEAR);
                if (currentMonth == dueDateMonth && currentYear == dueDateYear) {
                    if (dueDate.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                        int hoursDiff = dueDate.get(Calendar.HOUR_OF_DAY) - today.get(Calendar.HOUR_OF_DAY);
                        int minusDiff = dueDate.get(Calendar.MINUTE) - today.get(Calendar.MINUTE);
                        if (0 <= hoursDiff && hoursDiff <= 6 && 0 <= minusDiff) {
                            tasksWithTitle.get(3).addTask(task);
                        }
                        tasksWithTitle.get(1).addTask(task);
                        continue;
                    } else if (dueDate.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH) + 1) {
                        tasksWithTitle.get(2).addTask(task);
                        continue;
                    }
                }
                tasksWithTitle.get(4).addTask(task);
            }
            if (tasksWithTitle.get(0).getTasks().isEmpty()) tasksWithTitle.remove(0);
            allTasksLiveData.postValue(tasksWithTitle);
        });

        return allTasksLiveData;
    }

    public MutableLiveData<List<TasksWithTitle>> fetchNotifyTasks() {
        Database.databaseWriteExecutor.execute(() -> {
            List<Task> tasks = taskDao.getAll();
            if (tasks.size() == 0) {
                taskNotifyScreenLiveData.postValue(Collections.emptyList());
                return;
            }

            Calendar today = Calendar.getInstance();
            Calendar tomorrow = Calendar.getInstance();
            tomorrow.add(Calendar.DAY_OF_MONTH, 1);

            List<TasksWithTitle> tasksWithTitle = new ArrayList<>();
            tasksWithTitle.add(new TasksWithTitle("Missed", new ArrayList<>(), true));
            tasksWithTitle.get(0).setTitleTextColor(R.color.red_80);
            tasksWithTitle.add(new TasksWithTitle("Today", new ArrayList<>(), true, Utils.getTodayAtEnd()));
            tasksWithTitle.add(new TasksWithTitle("Tomorrow", new ArrayList<>(), true, tomorrow));
            tasksWithTitle.add(new TasksWithTitle("Someday", new ArrayList<>()));

            int currentMonth = today.get(Calendar.MONTH);
            int currentYear = today.get(Calendar.YEAR);
            Calendar dueDate = Calendar.getInstance();

            for (Task task : tasks) {
                dueDate.setTimeInMillis(task.getDueDate());
                int dueDateMonth = today.get(Calendar.MONTH);
                int dueDateYear = today.get(Calendar.YEAR);
                if (task.getDueDate() < today.getTimeInMillis()) {
                    tasksWithTitle.get(0).addTask(task);
                    continue;
                }
                if (currentMonth == dueDateMonth && currentYear == dueDateYear) {
                    if (dueDate.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                        tasksWithTitle.get(1).addTask(task);
                        continue;
                    } else if (dueDate.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH) + 1) {
                        tasksWithTitle.get(2).addTask(task);
                        continue;
                    }
                }
                tasksWithTitle.get(3).addTask(task);
            }
            if (tasksWithTitle.get(0).getTasks().isEmpty()) tasksWithTitle.remove(0);
            taskNotifyScreenLiveData.postValue(tasksWithTitle);
        });
        return taskNotifyScreenLiveData;
    }

    public MutableLiveData<List<Task>> fetchTasksByTitle(String title) {
        Database.databaseWriteExecutor.execute(() -> {
            List<Task> tasks = taskDao.getTaskByTitle(title);
            tasksLiveData.postValue(tasks);
        });
        return tasksLiveData;
    }

    public void insert(Task... tasks) {
        Database.databaseWriteExecutor.execute(() -> {
            taskDao.insert(tasks);
        });
    }

    public void insert(TaskCategory... categories) {
        Database.databaseWriteExecutor.execute(() -> {
            taskDao.insertCategory(categories);
        });
    }

    public void update(Task... tasks) {
        Database.databaseWriteExecutor.execute(() -> {
            taskDao.update(tasks);
        });
    }

    public Boolean isHavingTaskWithState(TaskState state) {
        return taskDao.isHavingTaskWithState(state);
    }
}

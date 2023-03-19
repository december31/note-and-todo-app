package com.example.note_and_todo_app;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskDao;
import com.example.note_and_todo_app.database.task.TaskState;
import com.example.note_and_todo_app.databinding.ActivityNotifyBinding;
import com.example.note_and_todo_app.todo.TaskListener;
import com.example.note_and_todo_app.todo.all.TasksWithTitle;
import com.example.note_and_todo_app.todo.list.TaskListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotifyActivity extends AppCompatActivity implements TaskListener {
    private ActivityNotifyBinding binding;
    private final TaskListAdapter adapter = new TaskListAdapter(this);
    private final MutableLiveData<List<Task>> tasksListLiveData = new MutableLiveData<>();
    private final TaskDao taskDao = Database.getInstance(this).taskDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotifyBinding.inflate(LayoutInflater.from(this), null, false);
        setContentView(binding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
        }
        hideSystemUI();
        setupData();
        setupListener();
    }

    private void setupData() {
        tasksListLiveData.observe(this, tasks ->{
            if (tasks.size() == 0) {
                binding.noTaskContainer.setVisibility(View.VISIBLE);
            } else {
                binding.noTaskContainer.setVisibility(View.GONE);
                adapter.updateItems(tasks);
            }
        });
        binding.rv.setAdapter(adapter);
        fetchItems();
    }

    private void setupListener() {
        binding.doneButton.setOnClickListener(v -> finishAffinity());
        binding.closeButton.setOnClickListener(v -> finishAffinity());
    }

    private void fetchItems() {
        List<Task> tasks = new ArrayList<>();
        Calendar today = Calendar.getInstance();
        Calendar dueDate = Calendar.getInstance();
        for (Task task : taskDao.getAll()) {
            dueDate.setTimeInMillis(task.getDueDate());
            if (dueDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                    dueDate.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                    dueDate.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH) &&
                    task.getState() != TaskState.DONE
            ) {
                tasks.add(task);
            }
        }
        tasksListLiveData.postValue(tasks);
    }

    public void updateDatabase(Task task, int action) {
        if (action == Database.ACTION_UPDATE) {
            taskDao.update(task);
        } else if (action == Database.ACTION_DELETE) {
            taskDao.delete(task);
        }
    }

    private void hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), binding.getRoot());
        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void onStatusChange(Task task, int position) {
        task.setState(task.getState() != TaskState.DONE ? TaskState.DONE : TaskState.PROCESSING);
        updateDatabase(task, Database.ACTION_UPDATE);
        if (!binding.rv.isComputingLayout() && binding.rv.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
            adapter.updateItems(task, position);
        }
    }

    @Override
    public void deleteTask(Task task, int position) {
        updateDatabase(task, Database.ACTION_DELETE);
        fetchItems();
        adapter.deleteItem(position);
    }

    @Override
    public void createNewTasks(TasksWithTitle tasksWithTitle) {

    }
}
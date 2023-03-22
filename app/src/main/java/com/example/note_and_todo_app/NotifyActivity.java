package com.example.note_and_todo_app;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.example.note_and_todo_app.base.OnCreateDialogResult;
import com.example.note_and_todo_app.database.Database;
import com.example.note_and_todo_app.database.task.Task;
import com.example.note_and_todo_app.database.task.TaskDao;
import com.example.note_and_todo_app.database.task.TaskRepository;
import com.example.note_and_todo_app.database.task.TaskState;
import com.example.note_and_todo_app.databinding.ActivityNotifyBinding;
import com.example.note_and_todo_app.todo.TaskListener;
import com.example.note_and_todo_app.todo.all.AllTaskAdapter;
import com.example.note_and_todo_app.todo.all.TasksWithTitle;
import com.example.note_and_todo_app.todo.list.CreateTaskDialog;
import com.example.note_and_todo_app.todo.list.TaskListAdapter;
import com.example.note_and_todo_app.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class NotifyActivity extends AppCompatActivity implements TaskListener {
    private ActivityNotifyBinding binding;
    private final AllTaskAdapter adapter = new AllTaskAdapter(this);
    private MutableLiveData<List<TasksWithTitle>> tasksListLiveData = new MutableLiveData<>();
    private final TaskRepository repository = TaskRepository.getInstance(this);

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotifyBinding.inflate(LayoutInflater.from(this), null, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        hideSystemUI();
        setupData();
        setupListener();
    }

    private void setupData() {
        fetchItems();
        tasksListLiveData.observe(this, tasks ->{
            if (tasks.size() == 0) {
                binding.noTaskContainer.setVisibility(View.VISIBLE);
            } else {
                binding.noTaskContainer.setVisibility(View.GONE);
                adapter.updateItems(tasks);
            }
        });
        binding.rv.setAdapter(adapter);
    }

    private void setupListener() {
        binding.doneButton.setOnClickListener(v -> finishAffinity());
        binding.closeButton.setOnClickListener(v -> finishAffinity());
    }

    private void fetchItems() {
        tasksListLiveData = repository.fetchNotifyTasks();
    }

    public void updateDatabase(Task task, int action) {
        if (action == Database.ACTION_UPDATE) {
            repository.updateTask(task);
        } else if (action == Database.ACTION_DELETE) {
            repository.deleteTask(task);
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

    OnCreateDialogResult dialogResult = new OnCreateDialogResult() {
        @Override
        public void onConfirm() {
            fetchItems();
        }

        @Override
        public void onCancel() {

        }
    };

    @Override
    public void onStatusChange(Task task, int position) {
        updateDatabase(task, Database.ACTION_UPDATE);
    }

    @Override
    public void deleteTask(Task task, int position) {
        updateDatabase(task, Database.ACTION_DELETE);
    }

    @Override
    public void createNewTasks(TasksWithTitle tasksWithTitle) {
        if (tasksWithTitle.getCalendar() == null) {
            new CreateTaskDialog(dialogResult).show(getSupportFragmentManager(), "create task");
        } else {
            new CreateTaskDialog(dialogResult, tasksWithTitle.getCalendar()).show(getSupportFragmentManager(), "create task");
        }
    }
}
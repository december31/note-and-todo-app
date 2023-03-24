package com.example.note_and_todo_app.utils;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public final class Utils {
    public static Calendar getTodayAtStart() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        return calendar;
    }

    public static Calendar getTodayAtEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        return calendar;
    }

    private static RecyclerView.LayoutManager HORIZONTAL_LINEAR_LAYOUT_MANAGER;
    private static RecyclerView.LayoutManager VERTICAL_LINEAR_LAYOUT_MANAGER;
    private static RecyclerView.LayoutManager GRID_LAYOUT_MANAGER;

    public static RecyclerView.LayoutManager getHorizontalLinearLayoutManager(Context context) {
        if (HORIZONTAL_LINEAR_LAYOUT_MANAGER == null) {
            HORIZONTAL_LINEAR_LAYOUT_MANAGER = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        }
        return HORIZONTAL_LINEAR_LAYOUT_MANAGER;
    }

    public static RecyclerView.LayoutManager getHorizontalLinearLayoutManager(Context context, boolean isReverse) {
        if (HORIZONTAL_LINEAR_LAYOUT_MANAGER == null) {
            HORIZONTAL_LINEAR_LAYOUT_MANAGER = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, isReverse);
        }
        return HORIZONTAL_LINEAR_LAYOUT_MANAGER;
    }

    public static RecyclerView.LayoutManager getVerticalLinearLayoutManager(Context context) {
        if (VERTICAL_LINEAR_LAYOUT_MANAGER == null) {
            VERTICAL_LINEAR_LAYOUT_MANAGER = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        }
        return VERTICAL_LINEAR_LAYOUT_MANAGER;
    }

    public static RecyclerView.LayoutManager getVerticalLinearLayoutManager(Context context, boolean isReverse) {
        if (VERTICAL_LINEAR_LAYOUT_MANAGER == null) {
            VERTICAL_LINEAR_LAYOUT_MANAGER = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, isReverse);
        }
        return VERTICAL_LINEAR_LAYOUT_MANAGER;
    }

    public static RecyclerView.LayoutManager getGridLayoutManager(Context context, int spanCount) {
        if (GRID_LAYOUT_MANAGER == null) {
            GRID_LAYOUT_MANAGER = new GridLayoutManager(context, spanCount);
        }
        return GRID_LAYOUT_MANAGER;
    }

    public static RecyclerView.LayoutManager getGridLayoutManager(Context context) {
        if (GRID_LAYOUT_MANAGER == null) {
            GRID_LAYOUT_MANAGER = new GridLayoutManager(context, 2);
        }
        return GRID_LAYOUT_MANAGER;
    }
}

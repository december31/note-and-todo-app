package com.example.note_and_todo_app.utils;

import android.animation.LayoutTransition;
import com.example.note_and_todo_app.R;

import java.text.SimpleDateFormat;
import java.util.*;

public class Constants {
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_TITLE = "category_title";
    public static final Integer BANNER_ADS_HEIGHT = 50;

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_1 = new SimpleDateFormat("dd MMMM yyyy hh:mm", Locale.US);
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT_2 = new SimpleDateFormat("dd/MM hh:mm", Locale.US);

    public static final Long ANIMATION_DURATION = 400L;
    public static final Long ANIMATION_OFFSET = 50L;

    public static final String ALL_TASK = "all_task";
    public static final String NEXT_7_DAYS = "next_7_days";
    public static final String KEY = "key";
    public static final Long DEFAULT_CATEGORY_ID = -1L;

    public static final LayoutTransition DEFAULT_TRANSITION = new LayoutTransition();

    public static final List<String> daysOfWeek = new ArrayList<String>() {{
        add("Sunday");
        add("Monday");
        add("Tuesday");
        add("Wednesday");
        add("Thursday");
        add("Friday");
        add("Saturday");
    }};

    public static final int DEFAULT_COLOR_RES = R.color.black;

}

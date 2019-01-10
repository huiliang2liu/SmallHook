package com.witget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.time.TimeUtil;
import com.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import hook.com.utillib.R;


/**
 * com.witget
 * 2019/1/10 12:54
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class SelectDateView extends LinearLayout {
    private static final String TAG = "SelectDateView", YEAR = "year", MONTH = "month", DAY = "day";
    private static final List<String> MONTHS = new ArrayList<>();
    private List<String> years = new ArrayList<>();
    private List<String> days = new ArrayList<>();
    private int year, month = 1, day = 1, sysYear, sysMonth, sysDay,
            monthDay = -1;
    private RollerView rollreViewYear, rollreViewMonth, rollreViewDay;
    private boolean monthChange = false, yearChange = false;
    private DateChangeListener listener;


    static {
        for (int i = 1; i < 13; i++) {
            MONTHS.add("" + i);
        }
    }

    public SelectDateView(Context context) {
        super(context);
        init(null);
    }

    public SelectDateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SelectDateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public SelectDateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        year = sysYear = TimeUtil.getSysTime(TimeUtil.YEAR);
        month = sysMonth = TimeUtil.getSysTime(TimeUtil.MONTH);
        day = sysDay = TimeUtil.getSysTime(TimeUtil.DAY1);
        for (int i = -50; i < 51; i++) {
            years.add("" + (sysYear + i));
        }
        rollreViewYear = new RollerView(getContext());
        LayoutParams lpy = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        lpy.weight = 1;
        addView(rollreViewYear, lpy);

        rollreViewMonth = new RollerView(getContext());
        rollreViewMonth.setData(MONTHS);
        LayoutParams lpm = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        lpm.weight = 1;
        addView(rollreViewMonth, lpm);

        rollreViewDay = new RollerView(getContext());
        LayoutParams lpd = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        lpd.weight = 1;
        addView(rollreViewDay, lpd);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SelectDateView, -1, -1);
            year = a.getInt(R.styleable.SelectDateView_selectYear, year);
            month = a.getInt(R.styleable.SelectDateView_selectMonth, month);
            day = a.getInt(R.styleable.SelectDateView_selectDay, day);
            int id = a.getResourceId(R.styleable.SelectDateView_dateYears, 0);
            if (id != 0) {
                String[] yArray = getResources().getStringArray(id);
                if (yArray != null && yArray.length > 0) {
                    years.clear();
                    for (String ye : yArray) {
                        years.add(ye);
                    }
                }
            }
            int textColor = a.getColor(R.styleable.SelectDateView_dateTextColor, 0x000000);
            int lineColor = a.getColor(R.styleable.SelectDateView_dateLineColor, Color.rgb(0xbe, 0xbe, 0xbe));
            rollreViewYear.setTextColor(textColor);
            rollreViewYear.setLineColor(lineColor);

            rollreViewMonth.setTextColor(textColor);
            rollreViewMonth.setLineColor(lineColor);

            rollreViewDay.setTextColor(textColor);
            rollreViewDay.setLineColor(lineColor);
        }
        rollreViewYear.setData(years);
        monthDay = TimeUtil.monthDay(month, year);
        for (int i = 1; i < monthDay; i++) {
            days.add("" + i);
        }
        rollreViewDay.setData(days);
        rollreViewYear.setSelected("" + year);
        rollreViewMonth.setSelected("" + month);
        rollreViewDay.setSelected("" + day);
        rollreViewYear.setOnSelectListener(new RollerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                int y = Integer.parseInt(text);
                if (y == year)
                    return;
                LogUtil.e(TAG, "rollreViewYear listener");
                if ((year == sysYear && year - y > 100)
                        || ((year == sysYear - 200) && y - 100 > year)) {
                    rollreViewYear.setSelected("" + year);
                } else
                    year = Integer.parseInt(text);
                days.clear();
                int mD = TimeUtil.monthDay(month, year);
                for (int i = 1; i < mD + 1; i++) {
                    days.add("" + i);
                }
                rollreViewDay.setData(days);
                if (day == monthDay) {
                    monthDay = day = mD;
                }
                rollreViewDay.setSelected("" + day);
            }
        });
        rollreViewMonth.setOnSelectListener(new RollerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                int m = Integer.parseInt(text);
                if (m == month)
                    return;
                monthChange(m);
            }
        });
        rollreViewDay.setOnSelectListener(new RollerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                int d = Integer.parseInt(text);
                if (d != day) {
                    LogUtil.e(TAG, "rollreViewDay listener");
                    dayChange(d);
                }
                if (listener != null)
                    listener.dateChange();
            }
        });
    }

    private void monthChange(int m) {
        // TODO Auto-generated method stub
        synchronized (MONTH) {
            monthChange = m != month;
            LogUtil.e(TAG, "monthChange m=" + m + " month=" + month
                    + (monthChange ? "改变" : "没改变"));
            if (monthChange) {
                if (month == 12 && m == 1)
                    rollreViewYear.setSelected("" + (++year));
                else if (month == 1 && m == 12)
                    rollreViewYear.setSelected("" + (--year));
                month = m;
                monthDay = TimeUtil.monthDay(month, year);
                days.clear();
                for (int i = 1; i < monthDay + 1; i++) {
                    days.add("" + i);
                }
                rollreViewDay.setData(days);
                if (day != 1 && day > monthDay)
                    day = monthDay;
            }
            if (day > monthDay)
                day = monthDay;
            rollreViewDay.setSelected("" + day);
            month = m;
        }
    }

    private void dayChange(int d) {
        // TODO Auto-generated method stub
        synchronized (DAY) {
            if (d == 1 && day == monthDay) {
                if (++month > 12) {
                    rollreViewYear.setSelected("" + (++year));
                    month = 1;
                }
                rollreViewMonth.setSelected("" + month);
            } else if (d == monthDay && day == 1) {
                if (--month < 1) {
                    rollreViewYear.setSelected("" + (--year));
                    month = 12;
                }
                rollreViewMonth.setSelected("" + month);
            }
            day = d;
        }
    }

    public int year() {
        return year;
    }

    public int month() {
        return month;
    }

    public int day() {
        return day;
    }

    public void setDateChangeListener(DateChangeListener dateChangeListener) {
        this.listener = dateChangeListener;
    }

    public static interface DateChangeListener {
        void dateChange();
    }
}

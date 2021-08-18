
package com.example.calendar_material;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import androidx.annotation.ColorLong;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;

public class SundayDecorator implements DayViewDecorator {
    private final Calendar calendar= Calendar.getInstance();

    public SundayDecorator(){

    }
    @Override
    public boolean shouldDecorate(CalendarDay day) {

        day.copyTo(calendar);//calendar버전을 2.0.1로 하면 copyTo사용불가. 1.4.3으로 해야함
        int weekDay=calendar.get(Calendar.DAY_OF_WEEK);
        return weekDay==Calendar.SUNDAY;//SATURDAY로 바꿀시 토요일 바꾸기 가능
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.rgb(255,153,153)));
    }//칠할 색깔


}

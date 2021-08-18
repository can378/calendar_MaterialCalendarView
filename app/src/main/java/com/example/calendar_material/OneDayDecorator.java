package com.example.calendar_material;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.CalendarView;

import androidx.annotation.Nullable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.LocalDate;

import java.time.LocalDateTime;
import java.util.Date;

public class OneDayDecorator implements DayViewDecorator {
    private CalendarDay date;

    public OneDayDecorator(){
        date=CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));//두껍게
        view.addSpan(new RelativeSizeSpan(1.4f));//크기
        //view.addSpan(new ForegroundColorSpan(Color.BLACK));//색

    }

    /*왜 들어가는건지 모르겠음 쓰면 오류나고 지우면 딱히 지장은 없는듯....?????아마
    public void setDate(Date date){
        this.date=CalendarDay.from(date);
    }


     */

}

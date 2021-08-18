package com.example.calendar_material;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    public String fname=null;//저장할 날짜 정보 파일 이름

    public String str=null;//메모내용 받을 변수
    public EditText contextEditText;
    public Button cha_Btn,del_Btn,save_Btn;
    public TextView diaryTextView,textView2;




    public int fnameYear;
    public int fnameMonth;
    public int fnameDay;
    public HashSet set=new HashSet<>();



   


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //레이아웃조정
        diaryTextView=findViewById(R.id.diaryTextView);
        save_Btn=findViewById(R.id.save_Btn);
        del_Btn=findViewById(R.id.del_Btn);
        cha_Btn=findViewById(R.id.cha_Btn);
        textView2=findViewById(R.id.textView2);
        contextEditText=findViewById(R.id.contextEditText);



        //로그인 회원가입 액티비티에서 이름 받아오기 지금은 임의로 아이디 고정해놓음
        Intent intent=getIntent();
        String name=intent.getStringExtra("userName");
        final String userID=intent.getStringExtra("userID");


        MaterialCalendarView materialCalendarView=(MaterialCalendarView)findViewById(R.id.calendarView);//얘 자리 옮기면 에러 이유는 모름..
        materialCalendarView.setSelectedDate(CalendarDay.today());

        //materialCalendarView.addDecorator(new EventDecorator(Color.BLACK, Collections.singleton(CalendarDay.today())));//점표시. CalendarDay.today() = 오늘로 되어있음

        OneDayDecorator oneDayDecorator=new OneDayDecorator();//오늘 날짜 강조
        materialCalendarView.addDecorators(oneDayDecorator);

        materialCalendarView.addDecorator(new MySelectorDecorator(this));//선택할떄 모양
        materialCalendarView.addDecorator(new SundayDecorator());//일요일에 색칠하기
        materialCalendarView.addDecorator(new SaturdayDecorator());//토요일에 색칠하기


        if(set!=null){
            CalendarDay eventDay2 = CalendarDay.from(fnameYear,fnameMonth, fnameDay);
            set.add(eventDay2);
            materialCalendarView.addDecorator(new EventDecorator(Color.rgb(255,102,102),set));//51,153,153
        }


        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {

            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                //레이아웃 조정
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);

                int year=date.getYear();
                int month=date.getMonth();
                int dayday=date.getDay();


                diaryTextView.setText(String.format("%d/%d/%d",year,month,dayday));
                contextEditText.setText("");
                checkDay(year,month,dayday,userID);


            }
        });


        save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(fname);
                str=contextEditText.getText().toString();
                textView2.setText(str);//글자로 보여줌


                //레이아웃 조정
                save_Btn.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
                textView2.setVisibility(View.VISIBLE);


                CalendarDay eventDay2 = CalendarDay.from(fnameYear,fnameMonth, fnameDay);
                set.add(eventDay2);
                materialCalendarView.addDecorator(new EventDecorator(Color.rgb(255,102,102),set));//51,153,153


            }
        });

        


    }

    public void  checkDay(int cYear,int cMonth,int cDay,String userID){
        fname=""+userID+cYear+"-"+(cMonth+1)+""+"-"+cDay+".txt";//저장할 파일 이름설정
        FileInputStream fis=null;//FileStream fis 변수

        fnameYear=cYear;
        fnameMonth=cMonth;
        fnameDay=cDay;

        try{
            fis=openFileInput(fname);

            byte[] fileData=new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str=new String(fileData);

            //레이아웃설정 + 글자보이게
            contextEditText.setVisibility(View.INVISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(str);
            save_Btn.setVisibility(View.INVISIBLE);
            cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);


            cha_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contextEditText.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.INVISIBLE);
                    contextEditText.setText(str);
                    save_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);

                    textView2.setText(contextEditText.getText());

                }

            });
            del_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textView2.setVisibility(View.INVISIBLE);
                    contextEditText.setText("");
                    contextEditText.setVisibility(View.VISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);
                    cha_Btn.setVisibility(View.INVISIBLE);
                    del_Btn.setVisibility(View.INVISIBLE);

                    removeDiary(fname);
                }
            });
            if(textView2.getText()==null){
                textView2.setVisibility(View.INVISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.INVISIBLE);
                del_Btn.setVisibility(View.INVISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }




    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay){
        FileOutputStream fos=null;

        try{
            fos=openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content="";
            fos.write((content).getBytes());
            fos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay){
        FileOutputStream fos=null;

        try{
            fos=openFileOutput(readDay,MODE_NO_LOCALIZED_COLLATORS);
            String content=contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
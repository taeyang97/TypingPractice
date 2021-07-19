package com.example.typingpractice;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;

public class wordpractice extends MainActivity{
    TextView tvWordQ, tvWordA, tvWordQnum, tvWordType, tvWordTime, tvWordAccuracy;
    EditText edtword;
    Button btnwordInsert;

    //문제 갯수 카운터
    private int count=0;
    //맞춘 개수 카운터
    private int answercount=0;
    //문제 푸는시간
    private int wtime=0;
    private int wordaccuracy=0;
    private int wordtypingspeed=0;
    private int wordtypingSp;
    private int questionnum=10;
    private int num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_activity);
        tvWordQ=findViewById(R.id.tvWordQ);
        tvWordA=findViewById(R.id.tvWordA);
        edtword=findViewById(R.id.edtWord);
        btnwordInsert=findViewById(R.id.btnwordInsert);
        tvWordQnum=findViewById(R.id.tvWordQnum);
        tvWordType=findViewById(R.id.tvWordType);
        tvWordTime=findViewById(R.id.tvWordTime);
        tvWordAccuracy=findViewById(R.id.tvWordAccuracy);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        //단어 DB가져오기
        Intent intent =getIntent();
        ArrayList<String> wordPractice=intent.getStringArrayListExtra("wordPractice");
        int dif=intent.getIntExtra("dif", 1);
        if(dif==0){
            questionnum=10;
        }else if(dif==1){
            questionnum=20;
        }else
            questionnum=30;

        //문제낼 난수 생성
        Random rd=new Random();
        String[] wordArray=wordPractice.toArray(new String[wordPractice.size()]);
        String[] wordQ=new String[questionnum];
        //문제 배열에 저장
        for(int i=0;i<questionnum;i++){
            num=rd.nextInt(wordPractice.size());
            wordQ[i]=wordArray[num];
        }


        //문제 타이머
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                tvWordTime.setText(Integer.toString(wtime)+"초");
                wtime++;
            }
        };

        tvWordQnum.setText("문제 수 : 1 / "+questionnum);
        tvWordQ.setText(wordQ[0]);
        //1초마다 시간 재기
        timer.schedule(timerTask,0,1000);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        edtword.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                edtword.requestFocus();
                imm.showSoftInput(edtword, 0);
            }
        }, 100);

        //edittext에 쓰면 텍뷰에 반영시키기
        edtword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                wordtypingSp++;
                wordtypingspeed=(60*wordtypingSp)/(wtime+1);
                tvWordType.setText("평균 타수 : "+wordtypingspeed+"타 ");
                tvWordA.setText(edtword.getText().toString());
            }
        });

        //타자맞게 쳤는지 확인
        btnwordInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String a=null,b=null;
                    a=edtword.getText().toString();
                    b=wordQ[count].toString();
                    if(a != null && b != null && a.equals(b)){
                        answercount++;
                        wordaccuracy=(int)((double)(answercount)*100.0/(double)(questionnum));
                        if(wordaccuracy < 0){
                            wordaccuracy=0;
                        }
                    }
                    tvWordAccuracy.setText("정확도 : " + wordaccuracy + "% ");
                    count++;

                    //문제 수가 문제 갯수를 넘지 않도록 설정
                    if(count >= questionnum){
                        tvWordQnum.setText("문제 수 : "+questionnum+" / "+questionnum);
                    }else{
                        tvWordQnum.setText("문제 수 : "+(count+1)+" / "+questionnum);
                    }

                    tvWordQ.setText(wordQ[count]);
                    edtword.setText("");
                    tvWordA.setText("");
                }catch (ArrayIndexOutOfBoundsException e){
                    AlertDialog.Builder builder=new AlertDialog.Builder(wordpractice.this);
                    builder.setTitle("게임종료").setMessage("문제 수: "+questionnum+"\n정확도 : "+wordaccuracy+
                            "%\n평균 타수 : "+wordtypingspeed+"타");
                    builder.setPositiveButton("처음 화면으로", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setCancelable(false);
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();


                }

            }
        });
    }

    // 뒤로가기버튼 활성화
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder=new AlertDialog.Builder(wordpractice.this);
        builder.setTitle("처음화면으로").setMessage("처음화면으로 가시겠습니까?");

        builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}

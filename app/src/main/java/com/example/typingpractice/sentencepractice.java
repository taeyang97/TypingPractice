package com.example.typingpractice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class sentencepractice extends MainActivity{
    TextView tvSenAccuracy, tvSenType, tvSenTime, tvSenQnum, tvSenQ, tvSenA;
    EditText edtSen;
    Button btnSenInsert;

    //문제 갯수 카운터
    private int count=0;
    //맞춘 개수 카운터
    private int answercount=0;
    //문제 푸는시간
    private int stime=0;
    private int senaccuracy=0;
    private int sentypingspeed=0;
    private int sentypingSp;
    int questionnum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sentence_activity);
        tvSenAccuracy=findViewById(R.id.tvSenAccuracy);
        tvSenType=findViewById(R.id.tvSenType);
        tvSenTime=findViewById(R.id.tvSenTime);
        tvSenQnum=findViewById(R.id.tvSenQnum);
        tvSenQ=findViewById(R.id.tvSenQ);
        tvSenA=findViewById(R.id.tvSenA);
        edtSen=findViewById(R.id.edtSen);
        btnSenInsert=findViewById(R.id.btnSenInsert);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        //문제 타이머
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                tvSenTime.setText(Integer.toString(stime)+"초");
                stime++;
            }
        };

        //단어 DB가져오기
        Intent intent =getIntent();
        ArrayList<String> sentencePractice=intent.getStringArrayListExtra("sentencePractice");
        int dif=intent.getIntExtra("dif", 1);
        String[] sentenceArray=sentencePractice.toArray(new String[sentencePractice.size()]);
        if(dif==0){
            questionnum=10;
        }else if(dif==1){
            questionnum=20;
        }else
            questionnum=30;

        //문제낼 난수 생성
        Random rd=new Random();
        //문제갯수
        String[] sentenceQ=new String[questionnum];
        int num=0;

        //문제 배열에 저장
        for(int i=0;i<questionnum;i++){
            /*while(true){
                num=rd.nextInt(sentencePractice.size());
                if(sentenceArray[num] != " ")
                    break;
            }*/
            num=rd.nextInt(1006);
            sentenceQ[i]=sentenceArray[num];
        }

        tvSenQnum.setText("문제 수 : 1 / "+questionnum);
        tvSenQ.setText(sentenceQ[0].toString());

        //1초마다 시간 재기
        timer.schedule(timerTask,0,1000);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        edtSen.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                edtSen.requestFocus();
                imm.showSoftInput(edtSen, 0);
            }
        }, 100);

        //edittext에 쓰면 텍뷰에 반영시키기
        edtSen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                sentypingSp++;
                sentypingspeed=(60*sentypingSp)/(stime+1);
                tvSenType.setText("평균 타수 : "+sentypingspeed+"타 ");
                tvSenA.setText(edtSen.getText().toString());
            }
        });

        //타자맞게 쳤는지 확인
        btnSenInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String a=null,b=null;
                    a=edtSen.getText().toString();
                    b=sentenceQ[count].toString();
                    if(a != null && b != null && a.equals(b)){
                        answercount++;
                        senaccuracy=(int)((double)(answercount)*100.0/(double)(questionnum));
                        if(senaccuracy < 0){
                            senaccuracy=0;
                        }
                    }
                    tvSenAccuracy.setText("정확도 : " + senaccuracy + "% ");
                    count++;

                    //문제 수가 문제 갯수를 넘지 않도록 설정
                    if(count >= questionnum){
                        tvSenQnum.setText("문제 수 : "+questionnum+" / "+questionnum);
                    }else{
                        tvSenQnum.setText("문제 수 : "+(count+1)+" / "+questionnum);
                    }

                    tvSenQ.setText(sentenceQ[count]);
                    edtSen.setText("");
                    tvSenA.setText("");
                }catch (ArrayIndexOutOfBoundsException e){
                    AlertDialog.Builder builder=new AlertDialog.Builder(sentencepractice.this);
                    builder.setTitle("게임종료").setMessage("문제 수: "+questionnum+"\n정확도 : "+senaccuracy+
                            "%\n평균 타수 : "+sentypingspeed+"타");
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
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
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

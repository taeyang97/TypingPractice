package com.example.typingpractice;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class typinggame extends MainActivity{
    TextView tvGame1, tvGame2, tvGame3, tvGame4, tvGame5;
    TextView tvGameTime, tvInf, tvTime;
    EditText edtGame;
    Button btnGameInsert;
    int count=0;
    Animation ani1, ani2, ani3, ani4, ani5;
    int gtime=0;
    int gametime=0;
    int gamescore=0;
    long hard=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        tvGame1=findViewById(R.id.tvGame1);
        tvGame2=findViewById(R.id.tvGame2);
        tvGame3=findViewById(R.id.tvGame3);
        tvGame4=findViewById(R.id.tvGame4);
        tvGame5=findViewById(R.id.tvGame5);
        btnGameInsert=findViewById(R.id.btnGameInsert);
        edtGame=findViewById(R.id.edtGame);
        tvGameTime=findViewById(R.id.tvGameTime);
        tvInf=findViewById(R.id.tvInf);
        tvTime=findViewById(R.id.tvTime);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        tvGame1.setText("");
        tvGame2.setText("");
        tvGame3.setText("");
        tvGame4.setText("");
        tvGame5.setText("");

        //단어 DB가져오기
        Intent intent =getIntent();
        ArrayList<String> wordPractice=intent.getStringArrayListExtra("wordPractice");
        int dif=intent.getIntExtra("dif", 1);
        String[] wordArray=wordPractice.toArray(new String[wordPractice.size()]);
        if(dif==0){
            hard=6000;
        }else if(dif==1){
            hard=4000;
        }else
            hard=2000;
        
        //문제낼 난수 생성
        Random rd=new Random();

        int num=0;
        int qnum=100;
        String[] wordQ=new String[qnum];

        //문제 배열에 저장
        for(int i=0;i<qnum;i++){
            num=rd.nextInt(wordPractice.size());
            wordQ[i]=wordArray[num];
        }

        //문제 타이머
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                tvTime.setText(Integer.toString(gametime)+"초 ");
                gametime++;
            }
        };
        //1초마다 시간 재기
        timer.schedule(timerTask,0,1000);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        edtGame.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                edtGame.requestFocus();
                imm.showSoftInput(edtGame, 0);
            }
        }, 100);

        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                tvGameTime.setText(Integer.toString(gtime));
                gtime++;
            }
        };
        new Thread() {
            public void run() {
                while (true){
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(hard);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        ani1=AnimationUtils.loadAnimation(this, R.anim.move);
        ani1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if(!tvGame1.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(typinggame.this);
                    builder.setTitle("게임종료").setMessage("점수 : " + gamescore);
                    builder.setPositiveButton("처음 화면으로", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setCancelable(false);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        ani2=AnimationUtils.loadAnimation(this, R.anim.move);
        ani2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if(!tvGame2.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(typinggame.this);
                    builder.setTitle("게임종료").setMessage("점수 : " + gamescore);
                    builder.setPositiveButton("처음 화면으로", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setCancelable(false);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        ani3=AnimationUtils.loadAnimation(this, R.anim.move);
        ani3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if(!tvGame3.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(typinggame.this);
                    builder.setTitle("게임종료").setMessage("점수 : " + gamescore);
                    builder.setPositiveButton("처음 화면으로", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setCancelable(false);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        ani4=AnimationUtils.loadAnimation(this, R.anim.move);
        ani4.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if(!tvGame4.getText().toString().equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(typinggame.this);
                    builder.setTitle("게임종료").setMessage("점수 : " + gamescore);
                    builder.setPositiveButton("처음 화면으로", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setCancelable(false);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        ani5=AnimationUtils.loadAnimation(this, R.anim.move);
        ani5.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if(!tvGame5.getText().toString().equals("") ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(typinggame.this);
                    builder.setTitle("게임종료").setMessage("점수 : " + gamescore);
                    builder.setPositiveButton("처음 화면으로", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setCancelable(false);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        tvGameTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String a = null;
                    a = wordQ[count];
                    if (tvGame1.getText().toString().equals("")) {
                        tvGame1.setText(a);
                        count++;
                        tvGame1.setVisibility(View.VISIBLE);
                        tvGame1.startAnimation(ani1);
                    } else {
                        if (tvGame2.getText().toString().equals("")) {
                            tvGame2.setText(a);
                            count++;
                            tvGame2.setVisibility(View.VISIBLE);
                            tvGame2.startAnimation(ani2);
                        } else {
                            if (tvGame3.getText().toString().equals("")) {
                                tvGame3.setText(a);
                                count++;
                                tvGame3.setVisibility(View.VISIBLE);
                                tvGame3.startAnimation(ani3);
                            } else {
                                if (tvGame4.getText().toString().equals("")) {
                                    tvGame4.setText(a);
                                    count++;
                                    tvGame4.setVisibility(View.VISIBLE);
                                    tvGame4.startAnimation(ani4);
                                } else {
                                    if (tvGame5.getText().toString().equals("")) {
                                    tvGame5.setText(a);
                                    count++;
                                    tvGame5.setVisibility(View.VISIBLE);
                                    tvGame5.startAnimation(ani5);
                                    }else{

                                    }
                                }
                            }
                        }
                    }
                }catch (Exception e){

                }
            }
        });

        btnGameInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a=null;
                String b=null;
                a=edtGame.getText().toString();
                b=tvGame1.getText().toString();
                if(a.equals(b)){
                    tvGame1.setText("");
                    tvGame1.setVisibility(View.INVISIBLE);
                    b=null;
                    tvGame1.clearAnimation();
                    gamescore++;
                }
                b=tvGame2.getText().toString();
                if(a.equals(b)){
                    tvGame2.setText("");
                    tvGame2.setVisibility(View.INVISIBLE);
                    b=null;
                    tvGame2.clearAnimation();
                    gamescore++;
                }b=tvGame3.getText().toString();
                if(a.equals(b)){
                    tvGame3.setText("");
                    tvGame3.setVisibility(View.INVISIBLE);
                    b=null;
                    tvGame3.clearAnimation();
                    gamescore++;
                }b=tvGame4.getText().toString();
                if(a.equals(b)){
                    tvGame4.setText("");
                    tvGame4.setVisibility(View.INVISIBLE);
                    b=null;
                    tvGame4.clearAnimation();
                    gamescore++;
                }b=tvGame5.getText().toString();
                if(a.equals(b)){
                    tvGame5.setText("");
                    tvGame5.setVisibility(View.INVISIBLE);
                    b=null;
                    tvGame5.clearAnimation();
                    gamescore++;
                }
                tvInf.setText(" 점수 : "+gamescore+"점");
                edtGame.setText("");
            }
        });
    }

    // 뒤로가기버튼 활성화
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder=new AlertDialog.Builder(typinggame.this);
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
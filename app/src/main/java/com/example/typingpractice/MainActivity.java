package com.example.typingpractice;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tvTitle;
    ImageButton btnSound;
    Button btnTypeword, btnTypesentence, btnTypeGame, btnStatistics;
    boolean check;
    SQLiteDatabase sqlDB;
    ArrayList<String> wordPractice;
    ArrayList<String> sentencePractice;
    ArrayList<String> arraynum;
    ArrayList<String> arraystatistics;
    final CharSequence[] difficulty={"초급", "중급", "고급"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTypeword=findViewById(R.id.btnTypeword);
        btnTypesentence=findViewById(R.id.btnTypesentence);
        btnTypeGame=findViewById(R.id.btnTypeGame);
        wordPractice=new ArrayList<String>();
        sentencePractice=new ArrayList<String>();
        tvTitle=findViewById(R.id.tvTitle);

        if(!check){
            copyDB(this);
        }

        btnTypeword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB=SQLiteDatabase.openDatabase("/data/data/com.example.typingpractice/databases/typingpracticeDB.db",
                        null, SQLiteDatabase.OPEN_READONLY);
                Cursor cursor;
                cursor=sqlDB.rawQuery("SELECT word FROM  typingpracticeDB", null);
                while(cursor.moveToNext()){
                    wordPractice.add(cursor.getString(0));
                }
                Intent wordpractice=new Intent(getApplicationContext(), wordpractice.class);
                wordpractice.putStringArrayListExtra("wordPractice", wordPractice);
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("난이도를 고르세요")
                        .setItems(difficulty, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int dif=which;
                                wordpractice.putExtra("dif", dif);
                                startActivity(wordpractice);
                            }
                        }).setCancelable(false).show();
            }
        });

        btnTypesentence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB=SQLiteDatabase.openDatabase("/data/data/com.example.typingpractice/databases/typingpracticeDB.db",
                        null, SQLiteDatabase.OPEN_READONLY);
                Cursor cursor;
                cursor=sqlDB.rawQuery("SELECT sentence FROM  typingpracticeDB", null);
                while(cursor.moveToNext()){
                    sentencePractice.add(cursor.getString(0));
                }
                Intent sentencepractice=new Intent(getApplicationContext(), sentencepractice.class);
                sentencepractice.putStringArrayListExtra("sentencePractice", sentencePractice);
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("난이도를 고르세요")
                        .setItems(difficulty, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int dif=which;
                                sentencepractice.putExtra("dif", dif);
                                startActivity(sentencepractice);
                            }
                        }).setCancelable(false).show();
            }
        });

        btnTypeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB=SQLiteDatabase.openDatabase("/data/data/com.example.typingpractice/databases/typingpracticeDB.db",
                        null, SQLiteDatabase.OPEN_READONLY);
                Cursor cursor;
                cursor=sqlDB.rawQuery("SELECT word FROM  typingpracticeDB", null);
                while(cursor.moveToNext()){
                    wordPractice.add(cursor.getString(0));
                }
                Intent typinggame=new Intent(getApplicationContext(), typinggame.class);
                typinggame.putStringArrayListExtra("wordPractice", wordPractice);
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("난이도를 고르세요")
                        .setItems(difficulty, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int dif=which;
                                typinggame.putExtra("dif", dif);
                                startActivity(typinggame);
                            }
                        }).setCancelable(false).show();
            }
        });
    }

    public boolean isCheckDB(Context context){
        String filePath="/data/data/com.example.typingpractice/databases/typingpracticeDB.db";
        File file=new File(filePath);
        long newdbSize=0;
        long olddbSize=file.length();
        AssetManager manager=context.getAssets();
        try{
            InputStream inputs=manager.open("typingpracticeDB.db");
            newdbSize= inputs.available();
        }catch(IOException e){
            showToast("해당 파일을 읽을수가 없습니다.");
        }
        if(file.exists()){
            if(newdbSize != olddbSize){
                return false;
            }else{
                return true;
            }
        }return false;
    }

    //db복사함수
    public void copyDB(Context context){
        AssetManager manager=context.getAssets();
        String folderPath="/data/data/com.example.typingpractice/databases";
        String filePath="/data/data/com.example.typingpractice/databases/typingpracticeDB.db";
        File folder=new File(folderPath);
        File file=new File(filePath);
        FileOutputStream fileOs=null;
        BufferedOutputStream bufferOs=null;
        try{
            InputStream inputS=manager.open("typingpracticeDB.db");
            BufferedInputStream bufferIs=new BufferedInputStream(inputS);
            if(!folder.exists()){
                folder.mkdir();
            }
            if(file.exists()){
                file.delete();
                file.createNewFile();
            }
            fileOs=new FileOutputStream(file);
            bufferOs=new BufferedOutputStream(fileOs);
            int read=-1;
            int size=bufferIs.available();
            byte buffer[]=new byte[size];
            while((read=bufferIs.read(buffer, 0, size))!=-1){
                bufferOs.write(buffer, 0, read);
            }
            bufferOs.flush();
            bufferOs.close();
            fileOs.close();
            bufferIs.close();
            inputS.close();
        }catch(IOException e){
            showToast("해당 파일을 읽을수가 없습니다.");
        }

    }

    //토스트 메시지 함수
    void showToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    // 뒤로가기버튼 활성화
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("어플 종료").setMessage("어플을 종료하시겠습니까?");

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
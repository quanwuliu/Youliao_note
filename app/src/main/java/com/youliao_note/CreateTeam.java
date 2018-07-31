package com.youliao_note;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateTeam extends AppCompatActivity {

    private List<Notes_homepage.Notes> notesList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_title);

        Button button=(Button) findViewById(R.id.back);//取消按钮，点击就返回不保存操作
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        Button button1=(Button) findViewById(R.id.ok);//确定按钮
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(CreateTeam.this, "命名成功", Toast.LENGTH_SHORT).show();
            }
        });

        initNotes();//初始化数据
        ArrayAdapter<Notes_homepage.Notes> adapter=new ArrayAdapter<Notes_homepage.Notes>(CreateTeam.this,android.R.layout.simple_list_item_1,notesList);
        ListView listView=(ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>parent,View view,int position,long id){
                Notes_homepage.Notes notes=notesList.get(position);
            }
        });
    }



    private void initNotes(){
        Notes_homepage notes_homepage=new Notes_homepage();
        Notes_homepage.Notes note1=notes_homepage.new Notes("笔记1");
        notesList.add(note1);
        Notes_homepage notes_homepage2=new Notes_homepage();
        Notes_homepage.Notes note2=notes_homepage2.new Notes("笔记2");
        notesList.add(note2);
        Notes_homepage notes_homepage3=new Notes_homepage();
        Notes_homepage.Notes note3=notes_homepage3.new Notes("笔记3");
        notesList.add(note3);
    }
}


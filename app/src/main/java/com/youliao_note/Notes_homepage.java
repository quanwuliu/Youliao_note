package com.youliao_note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Notes_homepage extends AppCompatActivity {
   private List<Notes> notesList=new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//功能目录
        getMenuInflater().inflate(R.menu.functions,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.create_team:
                Intent intent_1=new Intent(Notes_homepage.this,CreateTeam.class);
                startActivity(intent_1);
                Toast.makeText(this, "输入新分组名称，点击确定即可", Toast.LENGTH_LONG).show();
                break;
            case R.id.batch_edit:
                //批量编辑逻辑有待加入
                Toast.makeText(this, "进入批量编辑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.display_summary:
                Toast.makeText(this, "已在显示摘要模式", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sort_by_date:
                //排序未实现
                Toast.makeText(this, "已按日期排序", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sort_by_title:
                //排序未实现
                Toast.makeText(this, "已按标题排序", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_homepage);

        Button button_1=(Button) findViewById(R.id.all);//全部按钮的点击事件
        button_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(Notes_homepage.this, "你点击了全部按钮", Toast.LENGTH_SHORT).show();
            }
        });
        Button button_2=(Button) findViewById(R.id.team);//分组按钮的点击事件
        button_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(Notes_homepage.this,Team.class);
                startActivity(intent);
            }
        });
        Button button_3=(Button) findViewById(R.id.download);//下载按钮的点击事件
        button_3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(Notes_homepage.this, "你点击了下载按钮", Toast.LENGTH_SHORT).show();
            }
        });
        initNotes();//初始化数据
        ArrayAdapter<Notes> adapter=new ArrayAdapter<Notes>(Notes_homepage.this,android.R.layout.simple_list_item_1,notesList);
        ListView listView=(ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>parent,View view,int position,long id){
                Notes notes=notesList.get(position);
                Toast.makeText(Notes_homepage.this, "你点击了"+notes.getContext(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initNotes(){
        Notes note1=new Notes("笔记1");
        notesList.add(note1);
        Notes note2=new Notes("笔记2");
        notesList.add(note2);
        Notes note3=new Notes("笔记3");
        notesList.add(note3);
    }

    public class Notes{//笔记类：包括笔记标题和图片的ID
        private String context;

        public Notes(String context){
            this.context=context;

        }
        public String getContext(){
            return context;
        }
        @Override
        public String toString() {
            return context;
        }
    }
}


package com.youliao_note;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.youliao_note.R.id.recycler_view;



public class Summary_notes extends AppCompatActivity {

    private  List<Notes> notesList=new ArrayList<>();

    public class RecycleViewDivider extends RecyclerView.ItemDecoration {//万能分割线

        private Paint mPaint;
        private Drawable mDivider;
        private int mDividerHeight = 2;//分割线高度，默认为1px
        private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
        private  final int[] ATTRS = new int[]{android.R.attr.listDivider};

        /**
         * 默认分割线：高度为2px，颜色为灰色
         *
         * @param context
         * @param orientation 列表方向
         */
        public RecycleViewDivider(Context context, int orientation) {
            if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
                throw new IllegalArgumentException("请输入正确的参数！");
            }
            mOrientation = orientation;

            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        /**
         * 自定义分割线
         *
         * @param context
         * @param orientation 列表方向
         * @param drawableId  分割线图片
         */
        public RecycleViewDivider(Context context, int orientation, int drawableId) {
            this(context, orientation);
            mDivider = ContextCompat.getDrawable(context, drawableId);
            mDividerHeight = mDivider.getIntrinsicHeight();
        }

        /**
         * 自定义分割线
         *
         * @param context
         * @param orientation   列表方向
         * @param dividerHeight 分割线高度
         * @param dividerColor  分割线颜色
         */
        public RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
            this(context, orientation);
            mDividerHeight = dividerHeight;
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(dividerColor);
            mPaint.setStyle(Paint.Style.FILL);
        }


        //获取分割线尺寸
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, mDividerHeight);
        }

        //绘制分割线
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        //绘制横向 item 分割线
        private void drawHorizontal(Canvas canvas, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                final int bottom = top + mDividerHeight;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }

        //绘制纵向 item 分割线
        private void drawVertical(Canvas canvas, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + layoutParams.rightMargin;
                final int right = left + mDividerHeight;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }
    }//万能分割线

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_notes);

        Button button_1=(Button) findViewById(R.id.all);//全部按钮的点击事件
        button_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(Summary_notes.this, "你点击了全部按钮", Toast.LENGTH_SHORT).show();
            }
        });
        Button button_2=(Button) findViewById(R.id.team);//分组按钮的点击事件
        button_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(Summary_notes.this,Team.class);
                startActivity(intent);
            }
        });
        Button button_3=(Button) findViewById(R.id.download);//下载按钮的点击事件
        button_3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(Summary_notes.this, "你点击了下载按钮", Toast.LENGTH_SHORT).show();
            }
        });

        initNotes();//初始化数据
        RecyclerView recyclerView=(RecyclerView) findViewById(recycler_view);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);//瀑布流布局:每行控制在显示一个
        recyclerView.setLayoutManager(layoutManager);
        NotesAdapter adapter=new NotesAdapter(notesList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL));

        //悬浮按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create_note=new Intent(Summary_notes.this,create_notes.class);
                startActivity(create_note);
            }
        });
    }


    private void initNotes(){//测试数据

            Notes note1=new Notes("笔记1",R.drawable.note1,"aaaaaaaaaaaaaaaaaaaaaaaa");
            notesList.add(note1);
            Notes note2=new Notes("笔记2",R.drawable.notes2,"bbbbbbbbbbbbbbbbbbbb");
            notesList.add(note2);
            Notes note3=new Notes("笔记3",R.drawable.notes3,"ccccccccccccccccccc");
            notesList.add(note3);
          /*  Notes note4=new Notes("笔记4",R.drawable.notes4);
            notesList.add(note4);*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//功能目录
        getMenuInflater().inflate(R.menu.functions,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.create_team:
                Intent intent_1=new Intent(Summary_notes.this,CreateTeam.class);
                startActivity(intent_1);
                Toast.makeText(this, "输入新分组名称，点击确定即可", Toast.LENGTH_LONG).show();
                break;
            case R.id.batch_edit:
                //批量编辑逻辑有待加入
                Intent intent2=new Intent(Summary_notes.this,Notes_homepage.class);
                startActivity(intent2);
                Toast.makeText(this, "进入批量编辑", Toast.LENGTH_SHORT).show();
                break;
            case R.id.display_summary:
                Intent intent=new Intent(Summary_notes.this,Notes_homepage.class);
                startActivity(intent);
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

    public class Notes{//笔记类：包括笔记内容和图片的ID
        private String context;
        private int imageId;
        private String title;

        public Notes(String context,int imageId,String title){
            this.context=context;
            this.imageId=imageId;
            this.title=title;
        }

        public String getContext(){
            return context;
        }

        public int getImageId(){
            return imageId;
        }

        public String getTitle(){return title;}
    }

    public class Text implements Serializable{//实现Serializable接口以在不同活动间传递数据
        String str;
        public void setStr(String str){
            this.str=str;
        }

        public String getStr() {
            return str;
        }
    }

    public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{

        private List<Notes> mNotesList;


        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView notesImage;
            TextView notesContext;
            TextView notesTitle;
            View notesView;//准备点击事件

            public ViewHolder(View view){
                super(view);
                notesView=view;//准备点击事件
                notesImage=(ImageView) view.findViewById(R.id.notes_image);
                notesContext=(TextView) view.findViewById(R.id.notes_context);
                notesTitle=(TextView) view.findViewById(R.id.notes_title);
            }
        }

        public NotesAdapter(List<Notes> notesList){
            mNotesList=notesList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){//点击事件
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item,parent,false);
            final ViewHolder holder=new ViewHolder(view);
            holder.notesView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position=holder.getAdapterPosition();
                    Notes notes=mNotesList.get(position);
                    Toast.makeText(v.getContext(), "你点击了"+notes.getContext(), Toast.LENGTH_SHORT).show();//此处应为笔记内容
                }
            });
            holder.notesImage.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position=holder.getAdapterPosition();
                    Notes notes=mNotesList.get(position);
                    Toast.makeText(v.getContext(), "你点击了"+notes.getContext(), Toast.LENGTH_SHORT).show();//此处应为笔记内容
                }
            });
            holder.notesTitle.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position=holder.getAdapterPosition();
                    Notes notes=mNotesList.get(position);
                    Toast.makeText(v.getContext(), "你点击了"+notes.getContext(), Toast.LENGTH_SHORT).show();//此处应为笔记内容
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder,int position){
            Notes notes=mNotesList.get(position);
            holder.notesContext.setText(notes.getContext());
            holder.notesImage.setImageResource(notes.getImageId());
            holder.notesTitle.setText(notes.getTitle());
        }

        @Override
        public int getItemCount(){
            return mNotesList.size();
        }
    }


}

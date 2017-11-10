package me.liujia95.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;

    private List<Integer> viewsLayout = new ArrayList<>();
    String[] viewsName;

    {
        viewsName = getResources().getStringArray(R.array.views_name);

        viewsLayout.add(R.layout.view_hexagon);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void click(int position) {
                Intent intent = new Intent(MainActivity.this, PageActivity.class);
                intent.putExtra("viewLayoutRes", viewsLayout.get(position));
                MainActivity.this.startActivity(intent);
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        String[] datas;
        View view;
        OnItemClickListener listener;

        public MyAdapter() {
            view = LayoutInflater.from(MainActivity.this).inflate(android.R.layout.simple_list_item_1, null, false);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.setData(datas[position]);
            holder.tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.click(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.length;
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView tv1;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void setData(String data) {
            tv1.setText(data);
        }
    }

    interface OnItemClickListener {
        void click(int position);
    }
}

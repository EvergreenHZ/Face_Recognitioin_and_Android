package com.example.huaizhi.xtpfinalhomework;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class XTPDisplay extends AppCompatActivity {
    private SwipeRefreshLayout swipeRefresh;
    RecyclerView recyclerView;
    XTPInfoAdapter adapter;
    private List<XTPInformation> xtpInformationsList = new ArrayList<>();
    public static final int UPDATE_LISTVIEW = 1;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
            swipeRefresh.setRefreshing(false);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xtpdisplay);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new XTPInfoAdapter(xtpInformationsList);
        recyclerView.setAdapter(adapter);
        initList();

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initList();
            }
        });


    }
    public void initList() {
        xtpInformationsList.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("visitRecord");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                String visit_time, name, objId;
                for (int i = list.size() - 1; i >= 0; i--) {  // avobject getObjectId
                    AVObject bean = list.get(i);
                    objId = bean.getObjectId();
                    visit_time = bean.getString("visittime").toString();
                    name = bean.getString("name");
                    xtpInformationsList.add(new XTPInformation(visit_time, name, objId));
                }
                Message message = new Message();
                message.what = UPDATE_LISTVIEW;
                handler.sendMessage(message);
            }
        });
    }
}

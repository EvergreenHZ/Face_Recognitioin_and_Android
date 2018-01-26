package com.example.huaizhi.xtpfinalhomework;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;

public class XTPDetail extends AppCompatActivity {

    private byte[] image;
    private Bitmap bitmap;
    private ImageView imageView;
    public static final int SHOW_IMAGE = 1;
    private String name, time;
    TextView textView_time, textView_visitor;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_IMAGE:
                    bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                    imageView.setImageBitmap(bitmap);
                    textView_time.setText(time);
                    textView_visitor.setText(name);
                    break;
                default:
                    Log.d("HELLO", "NO IMAGE FOUND");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xtpdetail);
        imageView = (ImageView)findViewById(R.id.image_view) ;
        textView_time = (TextView) findViewById(R.id.xvisit_time);
        textView_visitor = (TextView)findViewById(R.id.xvisitor);
        Intent intent= getIntent();
        String objId = intent.getStringExtra("id");
        queryCloud(objId);
    }

    private void queryCloud(String objId) {

        AVQuery<AVObject> avQuery = new AVQuery<>("visitRecord");
        avQuery.getInBackground(objId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                AVFile avFile = avObject.getAVFile("image");
                name = avObject.getString("name").toString();
                time = avObject.getString("visittime").toString();
                if(avFile!=null) {
                    avFile.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, AVException avException) {
                            image = bytes;
                            Message message = new Message();
                            message.what = SHOW_IMAGE;
                            handler.sendMessage(message);
                        }
                    });
                }
            }
        });
    }
}

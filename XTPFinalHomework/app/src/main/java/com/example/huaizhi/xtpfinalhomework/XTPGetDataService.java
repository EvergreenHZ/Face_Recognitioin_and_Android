/*package com.example.huaizhi.xtpfinalhomework;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class XTPGetDataService extends Service {
    public XTPGetDataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}*/

package com.example.huaizhi.xtpfinalhomework;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by huaizhi on 12/14/17.
 */

public class XTPGetDataService extends Service {

    //XTPTimerTask xtpTimerTask = new XTPTimerTask();
    private int last_num = -1;
    Timer timer;
    private int last = -11;
    public static final int UPDATE_LISTVIEW = 1;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Log.d("HELLO", "Warning! Warning! Warning!");
            Intent intent = new Intent(XTPGetDataService.this, XTPDisplay.class);
            PendingIntent pi = PendingIntent.getActivity(XTPGetDataService.this, 0, intent, 0);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification notification = new NotificationCompat.Builder(XTPGetDataService.this)
                    .setContentTitle("Warning")
                    .setContentText("Stranger found")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                            R.mipmap.ic_launcher))
                    .setLights(Color.GREEN, 1000, 1000)
                    .setContentIntent(pi)
                    .build();
            //                    .setVibrate(new long[] {0, 1000, 1000, 1000})
            manager.notify(1, notification);
        }
    };

    public XTPGetDataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        Log.d("HELLO", "service onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer.schedule(new XTPTimerTask(), 0, 5000);
        return super.onStartCommand(intent, flags, startId);
    }

    class XTPTimerTask extends TimerTask {
        public XTPTimerTask() {
            super();
        }

        @Override
        public void run() {
            AVQuery<AVObject> avQuery = new AVQuery<>("visitRecord");
            avQuery.whereGreaterThan("num", last);  // query specific records
            avQuery.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    String name;
                    if (list.size() != 0) {

                        int tmp_last = list.get(list.size() - 1).getInt("num") - 1;
                        if (tmp_last != last) {
                            last = tmp_last;
                            for (AVObject bean : list) {
                                name = bean.getString("name");
                                if (name.equals("unknown")) {
                                    Message message = new Message();
                                    message.what = UPDATE_LISTVIEW;
                                    handler.sendMessage(message);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}


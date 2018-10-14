package tw.alex.threadtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView textView1;
    private TextView textView2;
    private UIHandler uiHandler;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.message1);
        textView2 = findViewById(R.id.message2);
        uiHandler = new UIHandler();
        timer = new Timer();
    }

    public void test1(View view){
        new Thread(){
            @Override
            public void run() {
                doThread1();
            }
        }.start();
    }

    private void doThread1(){
        for(int i=0; i<100; i++){
            Message message = new Message();
            message.what = 1;
            Bundle data = new Bundle();
            data.putString("key", "i = " + i);
            message.setData(data);
            uiHandler.sendMessage(message);
            Log.v("alex","i= " +i);
            try {
                Thread.sleep(500);

            }catch (InterruptedException ie){

            }
        }
    }

    public void test2(View view) {
        timer.schedule(new MyTask(), 0,1000);


    }

    private class MyTask extends TimerTask{
        int i;
        @Override
        public void run() {
            Message message = new Message();
            message.what = 2;
            Bundle data = new Bundle();
            data.putString("key", "i = " + i);
            message.setData(data);
            uiHandler.sendMessage(message);
            Log.v("alex","do MyTask" +":" + i++);
        }
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {

                case 1:
                    String value = msg.getData().getString("key");
                    textView1.setText(value);
                    break;
                case 2:
                    String value2 = msg.getData().getString("key");
                    textView2.setText(value2);
                    break;


            }
           // super.handleMessage(msg);
        }
    }


}

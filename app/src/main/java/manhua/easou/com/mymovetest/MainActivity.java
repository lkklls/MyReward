package manhua.easou.com.mymovetest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int DELAYED_TIME = 10;
    Button button;
    DanmuContainerView rl_danmuView;
    List<Danmu> list=new ArrayList<>();
    boolean isFirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFirst) {
                    initData();
                    isFirst=false;
                }
            }
        });
        rl_danmuView = (DanmuContainerView) findViewById(R.id.rl_danmuView);

    }


    private void initData() {
        list.add(new Danmu("这是第1条弾幕"));
        list.add(new Danmu("这是第2条弾幕"));
        list.add(new Danmu("这是第3条弾幕"));
        list.add(new Danmu("这是第4条弾幕"));
        list.add(new Danmu("这是第5条弾幕"));
        list.add(new Danmu("这是第6条弾幕"));
        list.add(new Danmu("这是第7条弾幕"));
        list.add(new Danmu("这是第8条弾幕"));
        list.add(new Danmu("这是第9条弾幕"));
        list.add(new Danmu("这是第10条弾幕"));
        list.add(new Danmu("这是第11条弾幕"));
        list.add(new Danmu("这是第12条弾幕"));
        list.add(new Danmu("这是第13条弾幕"));
        list.add(new Danmu("这是第14条弾幕"));
        list.add(new Danmu("这是第15条弾幕"));
        list.add(new Danmu("这是第16条弾幕"));
        list.add(new Danmu("这是第17条弾幕"));
        list.add(new Danmu("这是第18条弾幕"));
        list.add(new Danmu("这是第19条弾幕"));
        list.add(new Danmu("这是第20条弾幕"));
        rl_danmuView.startDanmu(list);

    }

}

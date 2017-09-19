package me.andpay.ma.fastpay.sdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import me.andpay.ma.fastpay.sdk.FastPayEvent;
import me.andpay.ma.fastpay.sdk.FastPayEventListener;
import me.andpay.ma.fastpay.sdk.FastPaySdkConfig;
import me.andpay.ma.fastpay.sdk.FastPaySdkManager;

/**
 * Created by hzy on 17/8/28.
 */

public class DemoActivity extends Activity {

    EditText editText;

    private String env = "pro";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_demo_layout);
        /**
         * sdk 配置
         */
        FastPaySdkConfig fastPaySdkConfig = new FastPaySdkConfig();
        fastPaySdkConfig.setDebug(true);
        FastPaySdkManager.init(fastPaySdkConfig);

        editText = (EditText) findViewById(R.id.input);

        findViewById(R.id.pay_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 注册事件监听器
                 */
                FastPaySdkManager.registerFastPayEventListener(new FastPayEventListener() {
                    @Override
                    public void onFastPayEvent(FastPayEvent fastPayEvent) {
                        StringBuilder sb = new StringBuilder(fastPayEvent.getEventType()).append(" data:");
                        if(fastPayEvent.getEventData() != null){
                            for(String key:fastPayEvent.getEventData().keySet()){
                                sb.append(key+","+fastPayEvent.getEventData().get(key)+";");
                            }
                        }
                        Log.e("TAG",sb.toString());
                    }
                });
                /**
                 * sdk 启动参数
                 */
                Map<String,Object> params = new HashMap<>();
                if(editText.getText().length()>0){
                    params.put("mobileNo",editText.getText().toString());
                }
                params.put("env",env);
                /**
                 * 调起sdk
                 */
                FastPaySdkManager.startFastPayModule(DemoActivity.this,params);
            }
        });

        //环境切换 测试环境or生产环境
        findViewById(R.id.stage1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                env = "stage1";
            }
        });
        findViewById(R.id.pro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                env = "pro";
            }
        });
    }
}

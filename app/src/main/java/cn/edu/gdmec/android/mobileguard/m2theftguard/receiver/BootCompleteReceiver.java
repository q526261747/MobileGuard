package cn.edu.gdmec.android.mobileguard.m2theftguard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.edu.gdmec.android.mobileguard.App;

/**
 * Created by 52626 on 2017/10/22.
 */

public class BootCompleteReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        ((App)(context.getApplicationContext())).correctSIM();
    }
}

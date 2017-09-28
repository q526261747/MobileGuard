package cn.edu.gdmec.android.mobileguard.m1home.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import junit.runner.Version;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m1home.HomeActivity;
import cn.edu.gdmec.android.mobileguard.m1home.entity.VersionEntity;

/**
 * Created by 52626 on 2017/9/19.
 */

public class VersionUpdateUtils {
    private String mVersion;
    private Activity context;
    private VersionEntity versionEntity;

    private static final int MESSAGE_IO_ERROR = 102;
    private static final int MESSAGE_JSON_ERROR = 103;
    private static final int MESSAGE_SHOW_DIALOG = 104;
    private static final int MESSAGE_ENTERHOMR = 105;
    
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case MESSAGE_IO_ERROR:
                    Toast.makeText(context,"IO错误",Toast.LENGTH_LONG).show();
                    break;
                case MESSAGE_JSON_ERROR:
                    Toast.makeText(context,"IO错误",Toast.LENGTH_LONG).show();
                    break;
                case MESSAGE_SHOW_DIALOG:
                    showUPdateDialog(versionEntity);
                    break;
                case MESSAGE_ENTERHOMR:
                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                    context.finish();
                    break;
            }
        }
    };



    public VersionUpdateUtils(String mVersion, Activity context){
        this.mVersion = mVersion;
        this.context = context;
    }
    public void getCloudVersion(){
        try {
        HttpClient httpclient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),5000);
        HttpConnectionParams.setSoTimeout(httpclient.getParams(),5000);
        HttpGet httpGet = new HttpGet("http://android2017.duapp.com/upadateinfo.html");
        HttpResponse execute = httpclient.execute(httpGet);
            if(execute.getStatusLine().getStatusCode()==200){
                HttpEntity httpEntity = execute.getEntity();
                String result = EntityUtils.toString(httpEntity,"utf-8");
                JSONObject jsonObject = new JSONObject(result);
                versionEntity = new VersionEntity();
                versionEntity.versioncode = jsonObject.getString("code");
                versionEntity.desciption = jsonObject.getString("des");
                versionEntity.apkurl = jsonObject.getString("apkurl");
                if (!mVersion.equals(versionEntity.versioncode)){
                    //版本不同，需升级
                    handler.sendEmptyMessage(MESSAGE_SHOW_DIALOG);
                }
            }
        } catch (IOException e) {
            handler.sendEmptyMessage(MESSAGE_IO_ERROR);
            e.printStackTrace();
        } catch (JSONException e) {
            handler.sendEmptyMessage(MESSAGE_JSON_ERROR);
            e.printStackTrace();
        }

    }
    private void showUPdateDialog(final VersionEntity versionEntity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("检查到有新版本："+versionEntity.versioncode);
        builder.setMessage(versionEntity.desciption);
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                downloadNewApk(versionEntity.apkurl);
            }
        });
        builder.setNegativeButton("暂不升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                enterHome();
            }
        });
        builder.show();
    }
    private void enterHome(){
        handler.sendEmptyMessage(MESSAGE_ENTERHOMR);
    }
    private void downloadNewApk(String apkurl){
        DownloadUtils downloadUtils = new DownloadUtils();
        downloadUtils.downloadAPK(apkurl,"mobileguard.apk",context);
    }

}

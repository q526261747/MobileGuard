package cn.edu.gdmec.android.mobileguard.m2theftguard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by 52626 on 2017/10/10.
 */

public class InterPasswordDialog extends Dialog implements View.OnClickListener {
    /**标题栏*/
    private TextView mTitleTv;
    /**首次输入密码文本框*/
    private EditText mInterET;
    /**确认按钮*/
    private Button mOKBtn;
    /**确认按钮*/
    private Button mCancelBtn;
    /**回调接口*/
    private MyCallBack myCallBack;
    private Context context;
    public InterPasswordDialog(@NonNull Context context) {
        super(context, R.style.dialog_custom);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.inter_password_dialog);
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView(){
        mTitleTv = (TextView) findViewById(R.id.tv_interpwd_title);
        mInterET = (EditText) findViewById(R.id.et_inter_password);
        mOKBtn = (Button) findViewById(R.id.btn_confirm);
        mCancelBtn = (Button) findViewById(R.id.btn_dismiss);
        mOKBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
    }
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitleTv.setText(title);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_confirm:
                myCallBack.confirm();
                break;
            case R.id.btn_dismiss:
                myCallBack.cancle();
                break;
        }
    }
    public String getPassword(){
        return mInterET.getText().toString();
    }
    public void setCallBack(MyCallBack myCallBack){
        this.myCallBack = myCallBack;
    }
    public interface MyCallBack{
        void confirm();
        void cancle();

        void cancel();
    }
}

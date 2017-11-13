package cn.edu.gdmec.android.mobileguard.m4appmanager.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by 52626 on 2017/11/9.
 */

public class AppInfo {
    /**
     * 应用活动
     */
    public String activities;
    /**
     * 应用版本号
     */
    public String version;
    /**
     * 应用安装时间
     */
    public String installTime;
    /**
     * apk证书签署者信息
     */
    public String certifi;
    /**
     * 应用程序包名
     */
    public String permisstion;
    /**
     * 应用权限申请信息
     */
    public String packageName;
    /**
     * 应用程序图标
     */
    public Drawable icon;
    /**
     * 应用程序名称
     */
    public String appName;
    /**
     * 应用程序路径
     */
    public String apkPath;
    /**
     * 应用程序大小
     */
    public long appSize;
    /**
     * 是否是手机存储
     */
    public boolean isInRoom;
    /**
     * 是否是用户应用
     */
    public boolean isUserApp;
    /**
     * 是否选中，默认为false
     */
    public boolean isSelected = false;

    /**
     * 拿到App位置字符
     */
    public String getAppLocation(boolean isInRoom) {
        if (isInRoom) {
            return "手机内存";
        } else {
            return "外部存储";
        }
    }
}

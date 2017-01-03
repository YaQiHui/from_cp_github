package com.color.measurement.from_cp.Utils.ColorAssist;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

/**
 * 对话框工具类
 *
 * @author Administrator
 */
public class DialogUtils {

    /**
     * 显示对话框
     *
     * @param context
     * @param arrayRes
     * @param listener
     * @return
     */
    public static AlertDialog showDialog(Context context, int arrayRes, OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(arrayRes, listener);
        return builder.show();
    }

    /**
     * 显示对话框
     *
     * @param context
     * @param message        对话框消息
     * @param sureText       确定文本
     * @param cancelText     取消文本
     * @param sureListener   确定监听器
     * @param cancelListener 取消监听器
     * @param cancelable     是否可取消
     * @return
     */
    public static android.support.v7.app.AlertDialog showDialog(
            Context context, String message, String sureText,
            String cancelText, OnClickListener sureListener,
            OnClickListener cancelListener, boolean cancelable) {
        android.support.v7.app.AlertDialog dialog =
                new android.support.v7.app.AlertDialog.Builder(context).setTitle("提示").
                setMessage(message).setPositiveButton(sureText,
                sureListener).setNegativeButton(cancelText, cancelListener).
                setCancelable(cancelable).show();
        return dialog;
    }

    //弹出自定义对话框  view自定义的VIew
    public static android.support.v7.app.AlertDialog showZiDialog(
            Context context, View view, String sureText,
            String cancelText, OnClickListener sureListener,
            OnClickListener cancelListener, boolean cancelable) {
        android.support.v7.app.AlertDialog dialog =
                new android.support.v7.app.AlertDialog.Builder(context).setTitle("提示").
                        setView(view).setPositiveButton(sureText,
                        sureListener).setNegativeButton(cancelText, cancelListener).
                        setCancelable(cancelable).show();
        return dialog;
    }

}

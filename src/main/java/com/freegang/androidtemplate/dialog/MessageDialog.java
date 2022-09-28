package com.freegang.androidtemplate.dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.freegang.androidtemplate.R;
import com.freegang.androidtemplate.base.dialog.BaseDialog;
import com.freegang.androidtemplate.databinding.LayoutMessageDialogBinding;

/**
 * 普通消息Dialog
 */
public class MessageDialog extends BaseDialog<LayoutMessageDialogBinding> {
    private String title = "请选择";
    private boolean centerTitle = false;
    private String content = "这是一条消息.";
    private int contentColor = Color.parseColor("#ff333333");
    private String cancel = "取消";
    private int cancelColor = Color.parseColor("#ff666666");
    private String confirm = "确定";
    private int confirmColor = Color.parseColor("#ff46ADFB");
    //是否是单个按钮
    private boolean isSingleButton = false;
    //取消回调事件
    private OnCancelCallback onMessageDialogCancelCallback;
    //确定回调事件
    private OnConfirmCallback onMessageDialogConfirmCallback;

    /**
     * 标题内容
     *
     * @param title
     */
    public MessageDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 标题是否居中
     *
     * @param centerTitle
     */
    public MessageDialog setCenterTitle(boolean centerTitle) {
        this.centerTitle = centerTitle;
        return this;
    }

    /**
     * 消息正文内容
     *
     * @param content
     */
    public MessageDialog setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * 消息正文颜色
     *
     * @param contentColor
     */
    public MessageDialog setContentColor(int contentColor) {
        this.contentColor = contentColor;
        return this;
    }

    /**
     * 取消按钮, 文本内容
     *
     * @param cancel
     */
    public MessageDialog setCancel(String cancel) {
        this.cancel = cancel;
        return this;
    }

    /**
     * 取消按钮, 文本颜色
     *
     * @param cancelColor
     */
    public MessageDialog setCancelColor(int cancelColor) {
        this.cancelColor = cancelColor;
        return this;
    }

    /**
     * 确认按钮, 文本内容
     *
     * @param confirm
     */
    public MessageDialog setConfirm(String confirm) {
        this.confirm = confirm;
        return this;
    }

    /**
     * 确认按钮, 文本颜色
     *
     * @param confirmColor
     */
    public MessageDialog setConfirmColor(int confirmColor) {
        this.confirmColor = confirmColor;
        return this;
    }

    /**
     * 是否是单按钮, 如果为 true 则只会响应 confirm 相关的设置
     *
     * @param singleButton
     */
    public MessageDialog setSingleButton(boolean singleButton) {
        isSingleButton = singleButton;
        return this;
    }

    /**
     * 取消按钮点击, 回调事件
     *
     * @param onMessageDialogCancelCallback
     */
    public MessageDialog setOnCancelCallback(OnCancelCallback onMessageDialogCancelCallback) {
        this.onMessageDialogCancelCallback = onMessageDialogCancelCallback;
        return this;
    }

    /**
     * 确定按钮点击, 回调事件
     *
     * @param onMessageDialogConfirmCallback
     */
    public MessageDialog setOnConfirmCallback(OnConfirmCallback onMessageDialogConfirmCallback) {
        this.onMessageDialogConfirmCallback = onMessageDialogConfirmCallback;
        return this;
    }

    @Override
    protected LayoutMessageDialogBinding callCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutMessageDialogBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView(LayoutMessageDialogBinding binding) {
        binding.messageDialogTitle.setText(title);
        binding.messageDialogTitle.setTextAlignment(centerTitle ? View.TEXT_ALIGNMENT_CENTER : View.TEXT_ALIGNMENT_INHERIT);

        binding.messageDialogContent.setText(content);
        binding.messageDialogContent.setTextColor(contentColor);

        binding.messageDialogCancel.setText(cancel);
        binding.messageDialogCancel.setTextColor(cancelColor);

        binding.messageDialogConfirm.setText(confirm);
        binding.messageDialogConfirm.setTextColor(confirmColor);

        //如果是一个按钮
        if (isSingleButton) {
            binding.messageDialogCancel.setVisibility(View.GONE);
            binding.messageDialogConfirm.setBackgroundResource(R.drawable.dialog_single_button_background);
        }
    }

    @Override
    protected void initEvent(LayoutMessageDialogBinding binding) {
        binding.messageDialogCancel.setOnClickListener(v -> {
            call(onMessageDialogCancelCallback, it -> onMessageDialogCancelCallback.onCancel(this), this::dismiss);
        });
        binding.messageDialogConfirm.setOnClickListener(v -> {
            call(onMessageDialogConfirmCallback, it -> onMessageDialogConfirmCallback.onConfirm(this));
        });
    }

    public void show(@NonNull FragmentManager manager) {
        super.show(manager, "MessageDialog");
    }

    //取消回调事件
    public interface OnCancelCallback {
        void onCancel(MessageDialog dialog);
    }

    //确定回调事件
    public interface OnConfirmCallback {
        void onConfirm(MessageDialog dialog);
    }
}

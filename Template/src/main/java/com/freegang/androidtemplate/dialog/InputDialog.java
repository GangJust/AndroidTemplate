package com.freegang.androidtemplate.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.freegang.androidtemplate.R;
import com.freegang.androidtemplate.base.dialog.BaseDialog;
import com.freegang.androidtemplate.databinding.LayoutInputDialogBinding;

/**
 * 输入框Dialog
 */
public class InputDialog extends BaseDialog<LayoutInputDialogBinding, InputDialog.InputDialogEntity> {
    private InputDialogEntity entity = new InputDialogEntity();

    /**
     * 标题内容
     *
     * @param title
     */
    public InputDialog setTitle(@NonNull CharSequence title) {
        this.entity.title = title;
        return this;
    }

    /**
     * 标题是否居中
     *
     * @param centerTitle
     */
    public InputDialog setCenterTitle(boolean centerTitle) {
        this.entity.centerTitle = centerTitle;
        return this;
    }

    /**
     * 正文内容提示
     *
     * @param contentHint
     * @return
     */
    public InputDialog setContentHint(@NonNull CharSequence contentHint) {
        this.entity.contentHint = contentHint;
        return this;
    }

    /**
     * 正文内容
     *
     * @param content
     */
    public InputDialog setContent(@NonNull CharSequence content) {
        this.entity.content = content;
        return this;
    }

    public InputDialog setContentInputType(int inputType) {
        this.entity.inputType = inputType;
        return this;
    }

    /**
     * 正文颜色
     *
     * @param contentColor
     */
    public InputDialog setContentColor(int contentColor) {
        this.entity.contentColor = contentColor;
        return this;
    }

    /**
     * 取消按钮, 文本内容
     *
     * @param cancel
     */
    public InputDialog setCancel(@NonNull CharSequence cancel) {
        this.entity.cancel = cancel;
        return this;
    }

    /**
     * 取消按钮, 文本颜色
     *
     * @param cancelColor
     */
    public InputDialog setCancelColor(int cancelColor) {
        this.entity.cancelColor = cancelColor;
        return this;
    }

    /**
     * 确认按钮, 文本内容
     *
     * @param confirm
     */
    public InputDialog setConfirm(@NonNull CharSequence confirm) {
        this.entity.confirm = confirm;
        return this;
    }

    /**
     * 确认按钮, 文本颜色
     *
     * @param confirmColor
     */
    public InputDialog setConfirmColor(int confirmColor) {
        this.entity.confirmColor = confirmColor;
        return this;
    }

    /**
     * 是否是单按钮, 如果为 true 则只会响应 confirm 相关的设置
     *
     * @param singleButton
     */
    public InputDialog setSingleButton(boolean singleButton) {
        this.entity.isSingleButton = singleButton;
        return this;
    }

    /**
     * 取消按钮点击, 回调事件
     *
     * @param onInputDialogCancelCallback
     */
    public InputDialog setOnCancelCallback(@NonNull OnCancelCallback onInputDialogCancelCallback) {
        this.entity.onInputDialogCancelCallback = onInputDialogCancelCallback;
        return this;
    }

    /**
     * 确定按钮点击, 回调事件
     *
     * @param onInputDialogConfirmCallback
     */
    public InputDialog setOnConfirmCallback(@NonNull OnConfirmCallback onInputDialogConfirmCallback) {
        this.entity.onInputDialogConfirmCallback = onInputDialogConfirmCallback;
        return this;
    }

    @Override
    protected LayoutInputDialogBinding callCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInputDialogBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView(@NonNull LayoutInputDialogBinding binding) {
        binding.inputDialogTitle.setText(this.entity.title);
        binding.inputDialogTitle.setTextAlignment(this.entity.centerTitle ? View.TEXT_ALIGNMENT_CENTER : View.TEXT_ALIGNMENT_INHERIT);

        binding.inputDialogContent.setHint(this.entity.contentHint);
        binding.inputDialogContent.setText(this.entity.content);
        binding.inputDialogContent.setInputType(this.entity.inputType);
        binding.inputDialogContent.setTextColor(this.entity.contentColor);

        binding.inputDialogCancel.setText(this.entity.cancel);
        binding.inputDialogCancel.setTextColor(this.entity.cancelColor);

        binding.inputDialogConfirm.setText(this.entity.confirm);
        binding.inputDialogConfirm.setTextColor(this.entity.confirmColor);

        //如果是一个按钮
        if (this.entity.isSingleButton) {
            binding.inputDialogCancel.setVisibility(View.GONE);
            binding.inputDialogConfirm.setBackgroundResource(R.drawable.dialog_single_button_background);
        }
    }

    @Override
    protected void initEvent(@NonNull LayoutInputDialogBinding binding) {
        binding.inputDialogCancel.setOnClickListener(v -> {
            call(this.entity.onInputDialogCancelCallback, it -> it.onCancel(this), this::dismiss);
        });
        binding.inputDialogConfirm.setOnClickListener(v -> {
            call(this.entity.onInputDialogConfirmCallback, it -> it.onConfirm(binding.inputDialogContent.getText(), this));
        });
    }

    @Override
    protected InputDialogEntity saveState() {
        return entity;
    }

    @Override
    protected void restoreState(@NonNull InputDialogEntity entity) {
        this.entity = entity;
    }

    public void show(@NonNull FragmentManager manager) {
        super.show(manager, "InputDialog");
    }

    //取消回调事件
    public interface OnCancelCallback {
        void onCancel(InputDialog dialog);
    }

    //确定回调事件
    public interface OnConfirmCallback {
        void onConfirm(Editable editable, InputDialog dialog);
    }

    /// InputDialog 实体类
    protected static class InputDialogEntity extends BaseDialog.BaseDialogEntity {
        private static final long serialVersionUID = 3121241690534610292L;
        //标题文本
        private CharSequence title = "Tips";
        //标题是否居中
        private boolean centerTitle = false;
        //正文内容提示
        private CharSequence contentHint = "请输入一些东西.";
        //正文内容
        private CharSequence content = "";
        //正文内容类型
        private int inputType = InputType.TYPE_CLASS_TEXT;
        //文本内容颜色
        private int contentColor = Color.parseColor("#ff333333");
        //取消文本
        private CharSequence cancel = "取消";
        //取消文本颜色
        private int cancelColor = Color.parseColor("#ff666666");
        //确定文本
        private CharSequence confirm = "确定";
        //确定文本颜色
        private int confirmColor = Color.parseColor("#ff46ADFB");
        //是否是单个按钮(如果是, 则只响应确定按钮事件)
        private boolean isSingleButton = false;
        //取消回调事件
        private InputDialog.OnCancelCallback onInputDialogCancelCallback;
        //确定回调事件
        private InputDialog.OnConfirmCallback onInputDialogConfirmCallback;
    }
}

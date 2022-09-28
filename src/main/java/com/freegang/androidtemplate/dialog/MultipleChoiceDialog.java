package com.freegang.androidtemplate.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.freegang.androidtemplate.base.dialog.BaseDialog;
import com.freegang.androidtemplate.databinding.LayoutMultipleChoiceDialogBinding;
import com.freegang.androidtemplate.dialog.adapter.MultipleChoiceListAdapter;
import com.freegang.androidtemplate.dialog.bean.MultipleChoiceItem;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 多选Dialog
 */
public class MultipleChoiceDialog extends BaseDialog<LayoutMultipleChoiceDialogBinding> {
    private String title = "请选择";
    private boolean centerTitle = false;
    private String cancel = "取消";
    private int cancelColor = Color.parseColor("#ff666666");
    private String confirm = "确定";
    private int confirmColor = Color.parseColor("#ff46ADFB");
    //默认已选择的项, 下标数组, 如果某一个下标不存在, 则不做任何操作.
    private int[] selected;
    //选项列表
    private MultipleChoiceListAdapter multipleChoiceListAdapter = new MultipleChoiceListAdapter(new ArrayList<>());
    //取消选择回调事件
    private OnCancelCallback onCancelCallback;
    //完成选择回调事件
    private OnCompleteCallback onCompleteCallback;

    /**
     * `标题`文本内容
     *
     * @param title
     * @return
     */
    public MultipleChoiceDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * `标题`是否居中
     *
     * @param centerTitle
     * @return
     */
    public MultipleChoiceDialog setCenterTitle(boolean centerTitle) {
        this.centerTitle = centerTitle;
        return this;
    }

    /**
     * `取消`按钮, 文本内容
     *
     * @param cancel
     * @return
     */
    public MultipleChoiceDialog setCancel(String cancel) {
        this.cancel = cancel;
        return this;
    }

    /**
     * `取消`按钮, 文本颜色
     *
     * @param cancelColor
     * @return
     */
    public MultipleChoiceDialog setCancelColor(@ColorInt int cancelColor) {
        this.cancelColor = cancelColor;
        return this;
    }

    /**
     * `确定`按钮, 文本内容
     *
     * @param confirm
     * @return
     */
    public MultipleChoiceDialog setConfirm(String confirm) {
        this.confirm = confirm;
        return this;
    }

    /**
     * `确定`按钮, 文本颜色
     *
     * @param confirmColor
     * @return
     */
    public MultipleChoiceDialog setConfirmColor(@ColorInt int confirmColor) {
        this.confirmColor = confirmColor;
        return this;
    }

    /**
     * 设置默认选中的值, 下标数组, 只会选中合理存在的下标, 非法下标不做任何处理
     *
     * @param selected
     * @return
     */
    public MultipleChoiceDialog setSelected(int... selected) {
        this.selected = selected;
        return this;
    }

    /**
     * 设置列表项
     *
     * @param multipleChoiceList
     * @return
     */
    public MultipleChoiceDialog setMultipleChoiceList(ArrayList<MultipleChoiceItem> multipleChoiceList) {
        multipleChoiceListAdapter.setDataList(multipleChoiceList);
        return this;
    }

    /**
     * 设置列表项
     *
     * @param multipleChoiceList
     * @return
     */
    public MultipleChoiceDialog setMultipleChoiceList(MultipleChoiceItem... multipleChoiceList) {
        setMultipleChoiceList(new ArrayList<>(Arrays.asList(multipleChoiceList)));
        return this;
    }

    /**
     * `取消`点击, 回调事件
     *
     * @param onCancelCallback
     * @return
     */
    public MultipleChoiceDialog setOnCancelCallback(OnCancelCallback onCancelCallback) {
        this.onCancelCallback = onCancelCallback;
        return this;
    }

    /**
     * `确定`点击, 回调事件
     *
     * @param onCompleteCallback
     * @return
     */
    public MultipleChoiceDialog setOnCompleteCallback(OnCompleteCallback onCompleteCallback) {
        this.onCompleteCallback = onCompleteCallback;
        return this;
    }

    /**
     * `选项`被选择, 回调事件
     *
     * @param onSelectedListener
     */
    public MultipleChoiceDialog setOnSelectedListener(MultipleChoiceListAdapter.OnSelectedListener onSelectedListener) {
        multipleChoiceListAdapter.setOnSelectedListener(onSelectedListener);
        return this;
    }

    @Override
    protected LayoutMultipleChoiceDialogBinding callCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutMultipleChoiceDialogBinding.inflate(inflater, container, false);
    }

    @Override
    protected void initView(LayoutMultipleChoiceDialogBinding binding) {
        binding.multipleChoiceDialogTitle.setText(title);
        binding.multipleChoiceDialogTitle.setTextAlignment(centerTitle ? View.TEXT_ALIGNMENT_CENTER : View.TEXT_ALIGNMENT_INHERIT);

        binding.multipleChoiceDialogCancel.setText(cancel);
        binding.multipleChoiceDialogCancel.setTextColor(cancelColor);

        binding.multipleChoiceDialogConfirm.setText(confirm);
        binding.multipleChoiceDialogConfirm.setTextColor(confirmColor);

        multipleChoiceListAdapter.setSelected(selected);
        binding.multipleChoiceDialogList.setAdapter(multipleChoiceListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(binding.getRoot().getContext());
        binding.multipleChoiceDialogList.setLayoutManager(layoutManager);
    }

    @Override
    protected void initEvent(LayoutMultipleChoiceDialogBinding binding) {
        binding.multipleChoiceDialogCancel.setOnClickListener(v -> {
            call(onCancelCallback, it -> it.onCancel(this), this::dismiss);
        });
        //因为是多选, 所以无法直接响应列表项点击事件, 需要手动确定后统一返回选中项
        binding.multipleChoiceDialogConfirm.setOnClickListener(v -> {
            call(onCompleteCallback,
                    it -> it.onComplete(this,
                            multipleChoiceListAdapter.getSelected().choiceItems,
                            multipleChoiceListAdapter.getSelected().choiceIndexes
                    ),
                    this::dismiss
            );
        });
    }

    public void show(@NonNull FragmentManager manager) {
        super.show(manager, "MultipleChoiceDialog");
    }

    /// 多选取消回调事件
    public interface OnCancelCallback {
        void onCancel(@NonNull MultipleChoiceDialog dialog);
    }

    /// 多选确定回调事件
    public interface OnCompleteCallback {
        void onComplete(@NonNull MultipleChoiceDialog dialog, @NonNull ArrayList<MultipleChoiceItem> selected, ArrayList<Integer> indexes);
    }
}

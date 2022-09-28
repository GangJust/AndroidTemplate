package com.freegang.androidtemplate.base.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewbinding.ViewBinding;

import com.freegang.androidtemplate.base.TemplateCall;


public abstract class BaseDialog<V extends ViewBinding> extends DialogFragment implements TemplateCall {
    private V binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = callCreateView(inflater, container, savedInstanceState);
        initView(binding);
        initEvent(binding);
        // dialog定制, 取消标题、背景透明
        call(getDialog(), dialog -> {
            dialog.setCanceledOnTouchOutside(false);
            call(dialog.getWindow(), window -> {
                window.requestFeature(Window.FEATURE_NO_TITLE);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    window.setClipToOutline(true);
                }
            });
        });
        return binding.getRoot();
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        // dialog定制, 宽度占满、高度适应
        call(getDialog(), dialog -> {
            call(dialog.getWindow(), window -> {
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            });
        });
    }

    @CallSuper
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding = null;
        }
    }

    public V getBinding() {
        return binding;
    }

    protected abstract V callCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract void initView(V binding);

    protected abstract void initEvent(V binding);

    public <T> void call(T it, CallIt<T> callIt) {
        if (it != null) {
            callIt.call(it);
        }
    }

    public <T> void call(T it, CallIt<T> callIt, CallNull callNull) {
        if (it != null) {
            callIt.call(it);
        } else {
            callNull.call();
        }

    }
}

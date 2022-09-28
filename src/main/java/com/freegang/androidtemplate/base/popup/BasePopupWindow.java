package com.freegang.androidtemplate.base.popup;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import androidx.annotation.CallSuper;
import androidx.annotation.IntDef;

import com.freegang.androidtemplate.base.TemplateCall;


public abstract class BasePopupWindow extends PopupWindow implements TemplateCall {
    protected View parentView;

    public BasePopupWindow(Context context) {
        super(context);
        //取到Activity根View作为默认父布局
        if (!(context instanceof Activity)) {
            throw new IllegalArgumentException("`context` should be an instance of Activity");
        }
        parentView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);

        setFocusable(true); //设置可交互(响应返回键)

        initPopup(context); //初始化视图
    }

    public abstract void initPopup(Context context);

    public BasePopupWindow show() {
        return show(parentView);
    }

    public BasePopupWindow show(View parentView) {
        return show(parentView, Gravity.CENTER);
    }

    public BasePopupWindow show(View parentView, @PopupWindowGravity int gravity) {
        return show(parentView, gravity, 0, 0);
    }

    public BasePopupWindow show(View parentView, @PopupWindowGravity int gravity, int offsetX, int offsetY) {
        this.parentView = parentView;
        showAtLocation(parentView, gravity, offsetX, offsetY);
        onShowing();
        return this;
    }

    public BasePopupWindow show(@PopupWindowGravity int gravity) {
        return show(gravity, 0, 0);
    }

    public BasePopupWindow show(@PopupWindowGravity int gravity, int offsetX, int offsetY) {
        return show(parentView, gravity, offsetX, offsetY);
    }

    /**
     * 显示后回调, 可以在这里对默认组件进行修改
     */
    @CallSuper
    protected void onShowing() {
    }

    @CallSuper
    @Override
    public void dismiss() {
        super.dismiss();
        if (parentView != null) {
            parentView = null;
        }
        onComplete();
    }

    /**
     * 销毁后回调, 可以在这里进行后续操作
     */
    @CallSuper
    protected void onComplete() {
    }

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

    //定位注解
    @IntDef({
            Gravity.TOP,
            Gravity.BOTTOM,
            Gravity.START,
            Gravity.END,
            Gravity.CENTER_VERTICAL,
            Gravity.FILL_VERTICAL,
            Gravity.CENTER_HORIZONTAL,
            Gravity.FILL_HORIZONTAL,
            Gravity.CENTER,
            Gravity.FILL,
            Gravity.CLIP_VERTICAL,
            Gravity.CLIP_HORIZONTAL,
    })
    public @interface PopupWindowGravity {
    }
}

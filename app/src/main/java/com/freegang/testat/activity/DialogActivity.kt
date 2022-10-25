package com.freegang.testat.activity

import android.widget.TextView
import com.freegang.androidtemplate.dialog.ChoiceDialog
import com.freegang.androidtemplate.dialog.MessageDialog
import com.freegang.androidtemplate.dialog.MultipleChoiceDialog
import com.freegang.androidtemplate.dialog.bean.ChoiceItem
import com.freegang.androidtemplate.dialog.bean.MultipleChoiceItem
import com.freegang.androidtemplate.popup.BottomPopup
import com.freegang.androidtemplate.popup.LoadingPopup
import com.freegang.testat.base.BaseActivity
import com.freegang.testat.databinding.ActivityDialogBinding


class DialogActivity : BaseActivity<ActivityDialogBinding>() {

    override fun setBinding(): ActivityDialogBinding = ActivityDialogBinding.inflate(layoutInflater)

    override fun initView(binding: ActivityDialogBinding) {

    }

    override fun initEvent(binding: ActivityDialogBinding) {
        binding.showMessageDialog.setOnClickListener { v ->
            MessageDialog()
                .setCenterTitle(true)
                .setSingleButton(false)
                .setTitle("小提示")
                .setContent("A message dialog.")
                .setCancel("取消了")
                .setConfirm("确定吧")
                .setOnCancelCallback { dialog ->
                    dialog.dismiss()
                    showToast("取消")
                }
                .setOnConfirmCallback { dialog ->
                    dialog.dismiss()
                    showToast("确定")
                }
                .show(supportFragmentManager)
        }

        binding.showChoiceDialog.setOnClickListener { v ->
            ChoiceDialog()
                .setTitle("请选择一项")
                .setCancel("不选了")
                .setShowDivider(false)
                .setChoiceList(
                    ChoiceItem("张三", "value"),
                    ChoiceItem("李四", "value"),
                    ChoiceItem("王五", "value")
                )
                .setOnCancelCallback { dialog ->
                    dialog.dismiss()
                    showToast("取消")
                }
                .setOnSelectedCallback { dialog, item, index ->
                    showToast(item, index)
                    dialog.dismiss()
                }
                .show(supportFragmentManager)
        }

        binding.showMultipleChoiceDialog.setOnClickListener { v ->
            MultipleChoiceDialog()
                .setTitle("请选择多项")
                .setCancel("不选了")
                .setConfirm("选好了")
                .setSelected(2, 0, -9, 3)
                .setMultipleChoiceList(
                    MultipleChoiceItem("张三", "value"),
                    MultipleChoiceItem("李四", "value"),
                    MultipleChoiceItem("王五", "value")
                )
                .setOnCancelCallback { dialog ->
                    dialog.dismiss()
                    showToast("取消")
                }
                .setOnCompleteCallback { dialog, selected, indexes ->
                    showToast(selected, indexes)
                    dialog.dismiss()
                }
                .show(supportFragmentManager)
        }

        binding.showLoadingPopup.setOnClickListener { v ->
            LoadingPopup(this)
                .setDismissConfirm(true)
                .setLoadingMessage("请稍后...")
                .setLoadingDismissCallback { showToast("Loading关闭") }
                .showDelay(2000L)
        }

        binding.showBottomPopup.setOnClickListener {
            val textView = TextView(this)
            textView.text = "张三\n李四\n王五\n"

            BottomPopup(this)
                .setDefaultBottomContainerView(true)
                .setBottomView(textView)
                .show()
        }
    }
}
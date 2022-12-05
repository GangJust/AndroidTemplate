package com.freegang.testat.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.util.*

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
    private lateinit var binding: B
    private var messageToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setBinding()
        setContentView(binding.root)
        initView(binding)
        initEvent(binding)
    }

    abstract fun setBinding(): B

    abstract fun initView(binding: B)

    abstract fun initEvent(binding: B)


    protected fun showToast(msg: CharSequence) {
        if (messageToast == null) {
            messageToast = Toast.makeText(this, null, Toast.LENGTH_SHORT)
        }
        messageToast!!.setText(msg)
        messageToast!!.show()
    }
}
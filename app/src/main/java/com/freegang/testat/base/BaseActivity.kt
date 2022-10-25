package com.freegang.testat.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {
    private lateinit var binding: B

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


    protected fun showToast(vararg msg: Any) {
        Toast.makeText(this, "" + msg.contentDeepToString(), Toast.LENGTH_SHORT).show()
    }
}
package com.freegang.testat.activity

import android.content.Intent
import com.freegang.testat.base.BaseActivity
import com.freegang.testat.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun setBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initView(binding: ActivityMainBinding) {

    }

    override fun initEvent(binding: ActivityMainBinding) {
        binding.toDialogActivity.setOnClickListener { v ->
            startActivity(Intent(this, DialogActivity::class.java))
        }

        binding.toListActivity.setOnClickListener { v ->
            startActivity(Intent(this, ListActivity::class.java))
        }
    }
}
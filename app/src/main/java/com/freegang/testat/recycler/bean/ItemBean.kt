package com.freegang.testat.recycler.bean

import com.freegang.androidtemplate.base.recycler.BaseItem

data class ItemBean(var title: String) : BaseItem("key_" + System.currentTimeMillis())

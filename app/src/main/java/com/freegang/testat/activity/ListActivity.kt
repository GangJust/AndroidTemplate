package com.freegang.testat.activity

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.freegang.androidtemplate.recycler.divider.ColorDividerItemDecoration
import com.freegang.androidtemplate.recycler.divider.ColorDividerItemDecoration.VERTICAL
import com.freegang.testat.base.BaseActivity
import com.freegang.testat.databinding.ActivityListBinding
import com.freegang.testat.recycler.adapter.ItemAdapter
import com.freegang.testat.recycler.bean.ItemBean
import kotlin.random.Random

class ListActivity : BaseActivity<ActivityListBinding>() {

    private val itemAdapter = ItemAdapter(mutableListOf())

    override fun setBinding(): ActivityListBinding = ActivityListBinding.inflate(layoutInflater)

    override fun initView(binding: ActivityListBinding) {
        for (i in 0 until 100) {
            itemAdapter.add(ItemBean("第 $i 项"))
        }

        binding.recyclerView.adapter = itemAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(ColorDividerItemDecoration(VERTICAL))
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
    }

    override fun initEvent(binding: ActivityListBinding) {
        //末尾增加
        binding.addItem.setOnClickListener {
            itemAdapter.add(ItemBean("末尾${Random.nextInt(100)}"))
        }

        //末尾删除
        binding.removeItem.setOnClickListener {
            itemAdapter.remove(itemAdapter.itemCount - 1)
        }

        //中间添加
        binding.insertItem.setOnClickListener {
            itemAdapter.insert((itemAdapter.itemCount / 2), ItemBean("中间${Random.nextInt(100)}"))
        }

        //中间删除
        binding.removeCenter.setOnClickListener { v ->
            itemAdapter.remove(itemAdapter.itemCount / 2)
        }

        //末尾多增
        binding.addItemMuch.setOnClickListener {
            itemAdapter.add(
                ItemBean("末尾多增${Random.nextInt(100)}"),
                ItemBean("末尾多增${Random.nextInt(100)}"),
            )
        }

        //末尾多删
        binding.removeItemMuch.setOnClickListener {
            itemAdapter.remove(
                itemAdapter.itemCount - 1,
                itemAdapter.itemCount - 2,
            )
        }

        //中间多增
        binding.insertItemMuch.setOnClickListener {
            itemAdapter.insert(
                (itemAdapter.itemCount / 2),
                ItemBean("中间多增${Random.nextInt(100)}"),
                ItemBean("中间多增${Random.nextInt(100)}"),
            )
        }

        //中间多删
        binding.removeCenterMuch.setOnClickListener {
            itemAdapter.remove(
                (itemAdapter.itemCount / 2),
                (itemAdapter.itemCount / 2) - 1,
            )
        }

        ////

        //升序显示
        binding.sortAsce.setOnClickListener {
            itemAdapter.ascending()
        }

        //降序显示
        binding.sortDesc.setOnClickListener {
            itemAdapter.descending()
        }

        //列表翻转
        binding.sortReverse.setOnClickListener {
            itemAdapter.reverse()
        }

        //列表乱序
        binding.sortShuffle.setOnClickListener {
            itemAdapter.shuffle()
        }

        ///

        //可见项范围
        binding.getVisibleRange.setOnClickListener {
            showToast("${itemAdapter.firstVisibleItemViewPosition}", "${itemAdapter.lastVisibleItemViewPosition}")
        }

        //跳转到某项
        binding.toPosition.setOnClickListener {
            val nextInt = Random.nextInt(itemAdapter.itemCount)
            itemAdapter.scrollToPosition(nextInt)
            showToast("跳转到: $nextInt")
        }

        //跳转到列表顶部
        binding.toTop.setOnClickListener {
            itemAdapter.scrollToTop()
        }

        //跳转到列表底部
        binding.toBottom.setOnClickListener {
            itemAdapter.scrollToBottom()
        }

        //上一页
        binding.toPrePage.setOnClickListener {
            itemAdapter.scrollToPrePage()
            showToast("第${itemAdapter.pageNum}页")
        }

        //下一页
        binding.toNextPage.setOnClickListener {
            itemAdapter.scrollToNextPage()
            showToast("第${itemAdapter.pageNum}页")
        }

        //滑动到列表顶部
        binding.smoothToTop.setOnClickListener {
            itemAdapter.smoothScrollToTop()
        }

        //滑动到列表底部
        binding.smoothToBottom.setOnClickListener {
            itemAdapter.smoothScrollToBottom()
        }

        //滑到某项
        binding.smoothToPotion.setOnClickListener {
            val nextInt = Random.nextInt(itemAdapter.itemCount)
            itemAdapter.smoothScrollToPosition(nextInt)
            showToast("滑动到: $nextInt")
        }

        //滑动到上一页
        binding.smoothToPrePage.setOnClickListener {
            itemAdapter.smoothScrollToPrePage()
            showToast("第${itemAdapter.pageNum}页")
        }

        //滑动到下一页
        binding.smoothToNextPage.setOnClickListener {
            itemAdapter.smoothScrollToNextPage()
            showToast("第${itemAdapter.pageNum}页")
        }
    }
}
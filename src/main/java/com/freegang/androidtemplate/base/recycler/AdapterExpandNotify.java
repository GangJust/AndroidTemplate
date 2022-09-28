package com.freegang.androidtemplate.base.recycler;

import androidx.annotation.NonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Adapter 刷新通知扩展
 */
public interface AdapterExpandNotify<B extends BaseItem> {
    /**
     * 更新数据
     */
    void refresh();

    /**
     * 批量刷新, 通过position
     *
     * @param positions
     */
    void refreshItems(int... positions);

    /**
     * 批量刷新, 通过key
     *
     * @param keys
     */
    void refreshItemsByKey(@NonNull B... keys);

    /**
     * 设置数据源并刷新
     *
     * @param dataList
     */
    void setDataListAndRefresh(List<B> dataList);

    /**
     * 按照当前列表的默认顺序, 倒转列表
     */
    void reverse();

    /**
     * 随机打乱列表
     */
    void shuffle();

    /**
     * 随机打乱列表
     *
     * @param random
     */
    void shuffle(Random random);

    /**
     * 按照指定逻辑排序
     *
     * @param c
     */
    void sort(Comparator<? super B> c);

    /**
     * 新增项
     *
     * @param newItem
     */
    boolean add(B newItem);

    /**
     * 新增多项
     *
     * @param newItems
     * @return
     */
    void add(B... newItems);

    /**
     * 新增项, 到指定位置
     *
     * @param position
     * @param newItem
     */
    void insert(int position, B newItem);

    /**
     * 新增多项, 到指定位置
     *
     * @param position
     * @param newItem
     */
    void insert(int position, B... newItem);

    /**
     * 新增项, 到指定项前
     *
     * @param item
     * @param newItem
     */
    boolean insert(B item, B newItem);

    /**
     * 移除某项, 通过position
     *
     * @param position
     * @return
     */
    B remove(int position);

    /**
     * 移除某些项
     *
     * @param position
     * @return
     */
    boolean remove(int... position);

    /**
     * 移除一项
     *
     * @param item
     */
    boolean remove(B item);

    /**
     * 移除具有相同key的所有项
     *
     * @param key
     * @return
     */
    boolean removeByKey(String key);

    /**
     * 更新某项, 通过position
     *
     * @param position
     * @param newItem
     * @return
     */
    B update(int position, B newItem);

    /**
     * 更新某项, 替换指定item
     *
     * @param oldItem
     * @param newItem
     * @return
     */
    boolean update(B oldItem, B newItem);

    /**
     * 获取某项
     *
     * @param position
     * @return
     */
    B getItem(int position);

    /**
     * 获取具有相同key的所有项
     *
     * @param key
     * @return
     */
    List<B> getItems(String key);
}

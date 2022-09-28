package com.freegang.androidtemplate.base.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.freegang.androidtemplate.base.TemplateCall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * 通用Adapter基类
 *
 * @param <B>
 * @author Gang
 */
public abstract class BaseAdapter<B extends BaseItem, VH extends BaseAdapter.ViewHolder>
        extends RecyclerView.Adapter<VH>
        implements AdapterExpandNotify<B>, TemplateCall {

    private List<B> dataList;

    public BaseAdapter(List<B> dataList) {
        this.dataList = dataList;
    }

    public void setDataList(List<B> dataList) {
        this.dataList = dataList;
    }

    public List<B> getDataList() {
        return dataList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return callCreateViewHolder(inflater, parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        callBindViewHolder(holder, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @NonNull
    public abstract VH callCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent, int viewType);

    public abstract void callBindViewHolder(@NonNull VH holder, @NonNull B item, int position);

    public abstract static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            init();
        }

        public abstract void init();
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }

    @Override
    public void refreshItems(int... positions) {
        for (int position : positions) {
            notifyItemChanged(position);
        }
    }

    @Override
    public void refreshItemsByKey(@NonNull B... keys) {
        for (int i = 0; i < keys.length; i++) {
            String newKay = keys[i].getKey();
            for (int j = 0; j < dataList.size(); j++) {
                String oldKey = dataList.get(j).getKey();
                if (newKay.equals(oldKey)) {
                    notifyItemChanged(j);
                    //break;  //这里不应该break, 会存在多个key相同的情况
                }
            }
        }
    }

    @Override
    public void setDataListAndRefresh(List<B> dataList) {
        if (!this.dataList.equals(dataList)) {
            this.dataList = dataList == null ? new ArrayList<>() : dataList;
        }
        notifyDataSetChanged();
    }

    @Override
    public void reverse() {
        Collections.reverse(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(dataList);
        notifyDataSetChanged();
    }

    @Override
    public void shuffle(Random random) {
        Collections.shuffle(dataList, random);
        notifyDataSetChanged();
    }

    @Override
    public void sort(Comparator<? super B> c) {
        Collections.sort(dataList, c);
        notifyDataSetChanged();
    }

    @Override
    public B getItem(int position) {
        if (getItemCount() <= 0) return null;
        return dataList.get(position);
    }

    @Override
    public List<B> getItems(String key) {
        if (getItemCount() <= 0) return new ArrayList<>();

        List<B> resultList = new ArrayList<>();
        for (int index = 0; index < dataList.size(); index++) {
            String key1 = dataList.get(index).getKey();
            if (key1.equals(key)) resultList.add(dataList.get(index));
        }
        return resultList;
    }

    @Override
    public boolean add(B newItem) {
        boolean result = dataList.add(newItem);
        notifyItemInserted(dataList.size() - 1);
        return result;
    }

    @Override
    public void add(B... newItems) {
        for (B newItem : newItems) {
            dataList.add(newItem);
            notifyItemInserted(dataList.size() - 1);
        }
    }

    @Override
    public void insert(int position, B newItem) {
        dataList.add(position, newItem);
        notifyItemInserted(position);
    }

    @Override
    public void insert(int position, B... newItems) {
        for (B newItem : newItems) {
            dataList.add(position, newItem);
            notifyItemInserted(position++);
        }
    }

    @Override
    public boolean insert(B item, B newItem) {
        int indexOf = dataList.indexOf(item);
        if (indexOf != -1) {
            dataList.add(indexOf, newItem);
            notifyItemInserted(indexOf);
            return true;
        }
        return false;
    }

    @Override
    public B remove(int position) {
        if (getItemCount() <= 0) return null;

        B result = dataList.remove(position);
        notifyItemRemoved(position);
        return result;
    }

    @Override
    public boolean remove(int... positions) {
        if (getItemCount() <= 0) return false;

        for (int i : positions) {
            if (i < 0 || i >= getItemCount()) continue;
            dataList.remove(i);
            notifyItemRemoved(i);
        }
        return false;
    }

    @Override
    public boolean remove(B item) {
        if (getItemCount() <= 0) return false;

        int indexOf = dataList.indexOf(item);
        if (indexOf != -1) {
            dataList.remove(indexOf);
            notifyItemRemoved(indexOf);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeByKey(String key) {
        if (getItemCount() <= 0) return false;

        boolean result = false;
        for (int i = dataList.size() - 1; i >= 0; i--) {
            String key1 = dataList.get(i).getKey();
            if (key1.equals(key)) {
                dataList.remove(i);
                notifyItemRemoved(i);
                result = true;
            }
        }
        return result;
    }

    @Override
    public B update(int position, B newItem) {
        if (getItemCount() <= 0) return null;

        B oldItem = dataList.get(position);
        dataList.set(position, newItem);
        notifyItemChanged(position);
        return oldItem;
    }

    @Override
    public boolean update(B oldItem, B newItem) {
        if (getItemCount() <= 0) return false;

        int indexOf = dataList.indexOf(oldItem);
        if (indexOf != -1) {
            dataList.set(indexOf, newItem);
            notifyItemChanged(indexOf);
            return true;
        }
        return false;
    }

    //如果某一个对象不为空, 则回调call方法
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

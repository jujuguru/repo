package com.jlabs.sf.fragment.record;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlabs.sf.R;
import com.jlabs.sf.R.id;
import com.jlabs.sf.R.layout;
import com.jlabs.sf.util.Util;

public class RecordListViewAdapter extends BaseAdapter {
    private Context mContext = null;
    public ArrayList<RecordListData> mListData = new ArrayList<RecordListData>();
    
    public RecordListViewAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }
    

	@Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    public void addItem(Drawable icon, String mTitle, String mDate){
        RecordListData addInfo = null;
        addInfo = new RecordListData();
        addInfo.mIcon = icon;
        addInfo.mTitle = mTitle;
        addInfo.mDate = mDate;
        
        mListData.add(addInfo);
        dataChange();
    }
    
    public void remove(int position){
        mListData.remove(position);
        dataChange();
    }
    
    public void removeAll(){
        
    	mListData.removeAll(mListData);
    	dataChange();
    }
    
    /*
    public void sort(){
        Collections.sort(mListData, ListData.START_TIME_DESC_COMPARATOR);
        dataChange();
    }
    */
    public void dataChange(){
    	Collections.sort(mListData, RecordListData.START_TIME_DESC_COMPARATOR);
        
    	Collections.reverse(mListData); //역순으로 등록
    	this.notifyDataSetChanged();
    	
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, null);
            
            holder.mIcon = (ImageView) convertView.findViewById(R.id.mImage);
            holder.mText = (TextView) convertView.findViewById(R.id.mText);
            holder.mDate = (TextView) convertView.findViewById(R.id.mDate);
            
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        
        RecordListData mData = mListData.get(position);
        
        if (mData.mIcon != null) {
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageDrawable(mData.mIcon);
        }else{
            holder.mIcon.setVisibility(View.GONE);
        }
        
        holder.mText.setText(mData.mTitle);
        //holder.mDate.setText(mData.mDate);
        //실제 날짜는 변환해서 보여준다.
        holder.mDate.setText(Util.getViewDate(mData.mDate));
        
        return convertView;
    }
    
    private class ViewHolder {    
    	public ImageView mIcon;     
    	public TextView mText;     
    	public TextView mDate;
    }
}


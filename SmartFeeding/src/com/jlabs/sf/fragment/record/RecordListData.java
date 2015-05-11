package com.jlabs.sf.fragment.record;

import java.text.Collator;
import java.util.Comparator;

import android.graphics.drawable.Drawable;

public class RecordListData {
    /**
     * ����Ʈ ������ ��� ���� ��ü ����
     */
    // ������
    public Drawable mIcon;
    
    // ����
    public String mTitle;
    
    // ��¥ (ȭ�鿡 �������� ��¥, ������¥�� �и����� ������ �����)
    public String mDate;
    
    
    /**
     * ���۽ð����� ���� ����
     */
    //TODO : ���� > http://cafe.naver.com/tipssoft/1845 > ����
    public static final Comparator<RecordListData> START_TIME_DESC_COMPARATOR = new Comparator<RecordListData>() {
        private final Collator sCollator = Collator.getInstance();
        
        @Override
        public int compare(RecordListData mListDate_1, RecordListData mListDate_2) {
            return sCollator.compare(mListDate_1.mDate, mListDate_2.mDate);
        }
    };
}


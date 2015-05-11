package com.jlabs.sf.fragment.record;

import java.text.Collator;
import java.util.Comparator;

import android.graphics.drawable.Drawable;

public class RecordListData {
    /**
     * 리스트 정보를 담고 있을 객체 생성
     */
    // 아이콘
    public Drawable mIcon;
    
    // 제목
    public String mTitle;
    
    // 날짜 (화면에 보여지는 날짜, 실제날짜는 밀리세컨 단위로 저장됨)
    public String mDate;
    
    
    /**
     * 시작시간으로 역순 정렬
     */
    //TODO : 정렬 > http://cafe.naver.com/tipssoft/1845 > 참조
    public static final Comparator<RecordListData> START_TIME_DESC_COMPARATOR = new Comparator<RecordListData>() {
        private final Collator sCollator = Collator.getInstance();
        
        @Override
        public int compare(RecordListData mListDate_1, RecordListData mListDate_2) {
            return sCollator.compare(mListDate_1.mDate, mListDate_2.mDate);
        }
    };
}


package com.jlabs.sf.fragment.record;

import java.util.Calendar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jlabs.sf.Constants;
import com.jlabs.sf.R;
import com.jlabs.sf.R.drawable;
import com.jlabs.sf.R.id;
import com.jlabs.sf.R.layout;
import com.jlabs.sf.db.DBAdapter;
import com.jlabs.sf.util.Util;

public class RecordFragment extends Fragment  implements OnClickListener {

	public static final String ARG_PLANET_NUMBER = "planet_number";
	
	static int infiniteCount = Integer.MAX_VALUE;
	
	int prevPosition = infiniteCount -1;
	
	private static DBAdapter db = null;
	
	/* Main Content */
	private static ListView mListView = null;
	private static RecordListViewAdapter mAdapter = null; 
	
	Button left_button, right_button, day_dell_button, all_dell_button;
	
	
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	static ViewPager mViewPager;
	
    public RecordFragment() {
        // Empty constructor required for fragment subclasses
    	
    }
    
    public void onDestroy(){
		super.onDestroy();
		db.close();
		
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        /*
        int i = getArguments().getInt(ARG_PLANET_NUMBER);
        String planet = getResources().getStringArray(R.array.planets_array)[i];

        int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                        "drawable", getActivity().getPackageName());
        ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
        */
        getActivity().setTitle("기록하기");
        
        
        
        // Create the adapter that will return a fragment for each of the three
  		// primary sections of the activity.
  		
        FragmentManager fm = this.getChildFragmentManager();
        mSectionsPagerAdapter = new SectionsPagerAdapter(fm);
        
  		// Set up the ViewPager with the sections adapter.
  		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
  		mViewPager.setAdapter(mSectionsPagerAdapter);
  	
  		//맨 마지막 페이지로 이동
  		
  		mViewPager.setCurrentItem(infiniteCount -1, false);
  		//mViewPager.setCurrentItem(1, true);
  		
		
		//db 접속
		db = new DBAdapter(this.getActivity());
		db.open();
		
		//버튼 리스너 처리
        left_button = (Button) rootView.findViewById(R.id.left_Button);
        right_button = (Button) rootView.findViewById(R.id.Right_Button);
        //day_dell_button = (Button) rootView.findViewById(R.id.Day_Delete_Button);
        //all_dell_button = (Button) rootView.findViewById(R.id.Delete_Button);
        
        
        left_button.setOnClickListener(this);
        right_button.setOnClickListener(this);
        //day_dell_button.setOnClickListener(this);
        //all_dell_button.setOnClickListener(this);
        
		    
		return rootView;
    }
    
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	//ViewPager에 연결할 Adapter 생성
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		Calendar rightNow = Calendar.getInstance();
		
		String dateText = "";
		
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			
			
			
			
			
			if(prevPosition == position){
				//어플리케이션 로딩시 초기 호출
				//Toast.makeText(MainActivity.this,prevPosition+" : "+position, Toast.LENGTH_SHORT).show();
			}else if (prevPosition > position){
				//과거로 이동
				int interval = prevPosition - position;
				
				rightNow.add(Calendar.DATE, -interval); // 날짜 필드에서 일자간 차이만큼 뺀다. 
				
				//Toast.makeText(MainActivity.this,prevPosition+" :: "+position, Toast.LENGTH_SHORT).show();
			}else{
				//미래로 이동
				int interval = position - prevPosition ;
				
				rightNow.add(Calendar.DATE, +interval); // 날짜 필드에서 일자간 차이만큼 더한다. 
				
				//Toast.makeText(MainActivity.this,prevPosition+" ::: "+position, Toast.LENGTH_SHORT).show();
			}
			dateText = Util.getDate(rightNow);
			
			prevPosition = position;
			
			return PlaceholderFragment.newInstance(position + 1, dateText);
			
		}
		
		
		@Override
		public int getCount() {
			//무제한(큰수).
			return infiniteCount;
		}
		
		//TODO : 비효율적이라고? 메모리의 모든 fragment를 재생성? 좌우 이동이 많지 않으니까 current fragment만 재생성할테니 문제 없을 것 같은데..
		// 데이터가 많아 졌을때 (예를들면 1년치 데이터 365 * 20 = 7000 row 정도)메모리 사용량 및 속도 성능 테스트 실시 필요.
		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
	
		}
		
	}
	
	

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnClickListener {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
		
		private static final String ARG_SECTION_DATE = "section_date";
		
		Button section_prev, section_next;
		
		
		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber, String sectionDate) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			args.putString(ARG_SECTION_DATE, sectionDate);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_pager, container,
					false);
			//실제 화면 뿌리는 부분
			/*
			TextView textTile = (TextView)rootView.findViewById(R.id.section_label);
			textTile.setBackgroundColor(Color.DKGRAY);
			textTile.setGravity(Gravity.CENTER);
			//전달받은 Argument 사용하기
			int curIndex = this.getArguments().getInt(ARG_SECTION_NUMBER);
			textTile.setText("This page is : " + curIndex);
			textTile.setTextColor(Color.WHITE);
			*/
			TextView textDate = (TextView)rootView.findViewById(R.id.section_date);
			//textDate.setBackgroundColor(Color.YELLOW);
			textDate.setGravity(Gravity.CENTER);
			String curDate = this.getArguments().getString(ARG_SECTION_DATE);
			textDate.setText(curDate);
			
			mListView = (ListView)rootView.findViewById(R.id.mList);
			
	        
			//mAdapter = new ListViewAdapter(this);
			//fragment 상위 context 를 호출해야 한다.
	        mAdapter = new RecordListViewAdapter(getActivity().getBaseContext());
			mListView.setAdapter(mAdapter);
	        
	        
	        //Cursor cursor = db.getAllLogs();
	        Cursor cursor = db.getDayLogs(curDate);
	        
	        //Toast.makeText(this.getActivity(), "성능테스트DB조회 : " + curDate, Toast.LENGTH_SHORT).show();
	        
	        if(cursor.getCount() > 0){
	        	while(cursor.moveToNext()){
	        		
	        		String _type = cursor.getString(2);
	        		String _startDate = cursor.getString(3);
	        		
	        		int image = R.drawable.user1;
	        		if(_type.equals(Constants.LEFT_FEEDING)){
	        			image = R.drawable.user2;
	        		}
	        		mAdapter.addItem(getResources().getDrawable(image),
	        				_type,
	                        _startDate);
	        	}
	        }else{
	            
	        }
	        
	        cursor.close();
	        
	        /*
	        String ddd = this.getArguments().getString(ARG_SECTION_DATE);
			Log.v("com.jlabs.sf", "CALL CREATE : " + ddd);
			*/
			
	        mListView.setOnItemClickListener(new OnItemClickListener() {

	            @Override
	            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
	                //size 가 0인데 해당 position 의 데이터를 얻으려다 오류가 발생한다. 왜 size 가 0인가? => mAdapter 가 위에서 정의한 mAdapter 와 다름.
	            	//리스너에서는 상위의 호출한 객체를 참조하기 위해서 파라미터를 통해 접근한다. 리스너 처리의 기본이다.
	            	//ListData mData = mAdapter.mListData.get(position); ==> X(동작안함)
	            	RecordListData mData = (RecordListData)parent.getAdapter().getItem(position);
	            	
	                Toast.makeText(getActivity().getBaseContext(), mData.mTitle, Toast.LENGTH_SHORT).show();
	            }
	        });
	        
	        //버튼 리스너 처리
	        section_prev = (Button) rootView.findViewById(R.id.section_prev);
	        section_next = (Button) rootView.findViewById(R.id.section_next);
	        
	        
	        section_prev.setOnClickListener(this);
	        section_next.setOnClickListener(this);
	        
	        return rootView;
		}

		@Override
		public void onClick(View v) {
			// ViewPager내의 날짜 이동 버튼
			onDateButton(v);
			
		}
		
		public void onDateButton(View v){
			
			mViewPager = (ViewPager) v.getRootView().findViewById(R.id.pager);
			
			int cur = mViewPager.getCurrentItem();	//현재 아이템 포지션
			
			int id = v.getId();
			
			if(id == R.id.section_prev){
				if(cur > 0)				//첫 페이지가 아니면
					mViewPager.setCurrentItem(cur-1, true);	//이전 페이지로 이동
				else						//첫 페이지 이면
					Toast.makeText(this.getActivity().getApplicationContext(), "맨 처음 페이지 입니다.", Toast.LENGTH_SHORT).show();	//메시지 출력
				
			}else if(id == R.id.section_next){
				if(cur < infiniteCount-1)		//마지막 페이지가 아니면
					mViewPager.setCurrentItem(cur+1, true);	//다음 페이지로 이동
				else						//마지막 페이지 이면
					Toast.makeText(this.getActivity().getApplicationContext(), "맨 마지막 페이지 입니다.", Toast.LENGTH_SHORT).show();	//메시지 출력
				
			}
		}
		
	}
	
	/**
	 * 로깅 Fragment의 왼쪽, 오른쪽, 삭제 등 버튼 클릭
	 */
	public void onButton(View v){
		
		 
		
		String dateText = "";
		
		//Tag를 사용하여 현재의 fragment를 찾기
		//Fragment fragment = this.getFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":"+mViewPager.getCurrentItem());
		Fragment fragment = this.getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":"+mViewPager.getCurrentItem());
	    if (fragment != null) // could be null if not instantiated yet
	    {
	        if (fragment.getView() != null) {
	        	dateText = fragment.getArguments().getString("section_date");
	        }
	    }
	    //Toast.makeText(this.getActivity().getApplication(), "해당일은 : " + dateText, Toast.LENGTH_SHORT).show();
	    
	    String thatTime = Util.getThatTime(dateText);
	    
		int id = v.getId();
		if(id == R.id.left_Button){
			//테스트코드
			//Toast.makeText(getApplication(), "왼쪽", Toast.LENGTH_SHORT).show();
			if(db.insertLog("1", Constants.LEFT_FEEDING,thatTime, "memo") > 0){
				//mAdapter.addItem(getResources().getDrawable(R.drawable.ic_ok),Constants.LEFT_FEEDING, thatTime);
				mViewPager.getAdapter().notifyDataSetChanged();
				
			}
		}else if (id == R.id.Right_Button){
			//테스트코드
			//Toast.makeText(getApplication(), "오른쪽", Toast.LENGTH_LONG).show();
			if(db.insertLog("1", Constants.RIGHT_FEEDING,thatTime, "memo") > 0){
				//mAdapter.addItem(getResources().getDrawable(R.drawable.ic_list),Constants.RIGHT_FEEDING,thatTime);
				mViewPager.getAdapter().notifyDataSetChanged();
				
			}
		/*
		}else if (id == R.id.Day_Delete_Button){
			
			if(db.deleteDayLog(dateText)){
				Toast.makeText(this.getActivity().getApplication(), "해당 일자 삭제", Toast.LENGTH_SHORT).show();
				mViewPager.getAdapter().notifyDataSetChanged();

			}
		}else if (id == R.id.Delete_Button){
			if(db.deleteAllLog()){
				Toast.makeText(this.getActivity().getApplication(), "모두 삭제", Toast.LENGTH_SHORT).show();
				//Refresh 필요
				//mAdapter.removeAll();
				
				mViewPager.getAdapter().notifyDataSetChanged();

			}
		*/
		}
		
	}
	
	

	@Override
	public void onClick(View v) {
		onButton(v);
		
	}
	
	

}
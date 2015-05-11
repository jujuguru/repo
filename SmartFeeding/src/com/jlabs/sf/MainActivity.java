package com.jlabs.sf;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jlabs.sf.fragment.record.RecordFragment;
import com.jlabs.sf.fragment.statistics.StatisticsFragment;
import com.jlabs.sf.fragment.statistics.StatisticsTempFragment;
import com.jlabs.sf.navigationdrawer.CustomDrawerAdapter;
import com.jlabs.sf.navigationdrawer.DrawerItem;
import com.jlabs.sf.navigationdrawer.FragmentOne;
import com.jlabs.sf.navigationdrawer.FragmentThree;
import com.jlabs.sf.navigationdrawer.FragmentTwo;

/*
 <구현 참고 사이트>
 https://github.com/tjerkw/Android-SlideExpandableListView
 ListView 클릭시 아래 영역 확장하여 수정, 삭제 처리
  또는 이 기능 사용하지 않고 커스텀리스트뷰에서 LongClick 이나 더보기 기능을 통해서 처리 
*/


/**
 * 
 * @author root
 */
public class MainActivity extends Activity {
	
	/* Navigation Drawer */
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    
    CustomDrawerAdapter adapter;
    List<DrawerItem> dataList;
   
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_main);
		
		/*
		//navigation drawer
		mTitle = mDrawerTitle = getTitle();
		mPlanetTitles = getResources().getStringArray(R.array.planets_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		
		 // Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mPlanetTitles));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		*/
		
		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Add Drawer Item to dataList
		dataList.add(new DrawerItem(true)); // adding a spinner to the list

		dataList.add(new DrawerItem("기록하기", R.drawable.ic_action_record));
		dataList.add(new DrawerItem("통계", R.drawable.ic_action_gamepad));
		
		dataList.add(new DrawerItem(false, true)); // adding a header to the list
		dataList.add(new DrawerItem("커뮤니티", R.drawable.ic_action_group));
		dataList.add(new DrawerItem("공유하기", R.drawable.ic_action_share));
		dataList.add(new DrawerItem("별점주기", R.drawable.ic_action_star_selected));
		dataList.add(new DrawerItem("표준성장표", R.drawable.ic_action_about));
		dataList.add(new DrawerItem("아기재우기", R.drawable.ic_action_search));
		
		dataList.add(new DrawerItem(false, true));// adding a header to the list
		dataList.add(new DrawerItem("설정", R.drawable.ic_action_settings2));
		dataList.add(new DrawerItem("도움말 및 의견", R.drawable.ic_action_help));
		dataList.add(new DrawerItem("Upgrade to Pro", R.drawable.ic_action_camera));
		
		
		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(adapter);
		
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
	
		
		// enable ActionBar app icon to behave as action to toggle nav drawer
        // 타이틀 좌측의 홈버튼 활성화
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        /*
        if (savedInstanceState == null) {

			if (dataList.get(0).isSpinner() || dataList.get(0).isheader()) {
				SelectItem(2);
			} else if (dataList.get(1).isheader()) {
				SelectItem(2);
			} else {
				SelectItem(0);
			}
		}
		*/
        
        //최초에 보여질 Fragment 선택
        if (savedInstanceState == null) {
        	SelectItem(1); //1: 기록하기
        } 
	}
	
	public void onDestroy(){
		super.onDestroy();
		//db close();
		//db.close();
	}

	/**
	 * App이 실행될 때 xml을 조회하여 액션바(타이틀바)에 메뉴를 구성한다.
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
   
    /**
     * 타이틀바의 메뉴를 선택했을때 호출된다.
     * 
     * This hook is called whenever an item in your options menu is selected. 
     * The default implementation simply returns false to have the normal processing happen 
     * (calling the item's Runnable or sending a message to its Handler as appropriate). 
     * You can use this method for any items for which you would like to do processing without those other facilities. 
	 * Derived classes should call through to the base class for it to perform the default menu handling.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        /*
        case R.id.action_websearch:
            // create intent to perform web search for this planet
        	//Intent는 서로다른 Activity간 호출하거나 데이터를 주고받기 위해 사용한다
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;
        */
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        
    	@Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	/*
        	old_selectItem(position);
        	*/
        	if (dataList.get(position).getTitle() == null) {
				SelectItem(position);
			}
        }
    	
    }
    
    public void SelectItem(int position) {

		Fragment fragment = null;
		Bundle args = new Bundle();
		
		//설정하기 Activity 를 제외한 나머지 기능은 Fragment 로 구현해야만 Navigation Drawer 를 사용할 수 있다.
		// >> 설정도 Fragment 로 변경할까? >> 구글 가이드 또는 구글 앱 을 참고할 것
		
		switch (position) {
		
		case 1: //기록하기
			fragment = new RecordFragment();
			break;
		case 2: //통계
			fragment = new StatisticsFragment();
			break;
			
		case 3: //separator
			fragment = new FragmentOne();
			args.putString(FragmentOne.ITEM_NAME, dataList.get(position)
					.getItemName());
			args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(position)
					.getImgResID());
			break;
		case 4: //커뮤니티
			fragment = new FragmentTwo();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(position)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(position)
					.getImgResID());
			break;
		case 5: //공유하기
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(position)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
					.get(position).getImgResID());
			break;
		case 6: //별점주기
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(position)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
					.get(position).getImgResID());
			break;
		case 7: //표준성장표
			fragment = new FragmentTwo();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(position)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(position)
					.getImgResID());
			break;
		case 8: //아기재우기
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(position)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
					.get(position).getImgResID());
			break;
		case 9: //separator
			fragment = new FragmentOne();
			args.putString(FragmentOne.ITEM_NAME, dataList.get(position)
					.getItemName());
			args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(position)
					.getImgResID());
			break;
		case 10: //설정
			goSetting();
			//Fragment로 대체할까?
		case 11://도움말 및 의견
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(position)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
					.get(position).getImgResID());
			break;
		case 12: //Upgrade to Pro
			fragment = new FragmentOne();
			args.putString(FragmentOne.ITEM_NAME, dataList.get(position)
					.getItemName());
			args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(position)
					.getImgResID());
			break;
		default:
			break;
		}
		
		fragment.setArguments(args);
		FragmentManager frgManager = getFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

		mDrawerList.setItemChecked(position, true);
		setTitle(dataList.get(position).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}

    
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	



	//환경설정 Activity호출
	private void goSetting()
    {
        Intent i = new Intent(this, SettingsActivity.class);
        
        startActivity(i);
    }
		
	//xml 에 정의된 버튼클릭 리스너를 fragment로 전달
	public void onButton(View v){
		//commit2
	}
	
}


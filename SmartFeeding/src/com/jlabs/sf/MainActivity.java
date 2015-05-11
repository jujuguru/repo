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
 <���� ���� ����Ʈ>
 https://github.com/tjerkw/Android-SlideExpandableListView
 ListView Ŭ���� �Ʒ� ���� Ȯ���Ͽ� ����, ���� ó��
  �Ǵ� �� ��� ������� �ʰ� Ŀ���Ҹ���Ʈ�信�� LongClick �̳� ������ ����� ���ؼ� ó�� 
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

		dataList.add(new DrawerItem("����ϱ�", R.drawable.ic_action_record));
		dataList.add(new DrawerItem("���", R.drawable.ic_action_gamepad));
		
		dataList.add(new DrawerItem(false, true)); // adding a header to the list
		dataList.add(new DrawerItem("Ŀ�´�Ƽ", R.drawable.ic_action_group));
		dataList.add(new DrawerItem("�����ϱ�", R.drawable.ic_action_share));
		dataList.add(new DrawerItem("�����ֱ�", R.drawable.ic_action_star_selected));
		dataList.add(new DrawerItem("ǥ�ؼ���ǥ", R.drawable.ic_action_about));
		dataList.add(new DrawerItem("�Ʊ�����", R.drawable.ic_action_search));
		
		dataList.add(new DrawerItem(false, true));// adding a header to the list
		dataList.add(new DrawerItem("����", R.drawable.ic_action_settings2));
		dataList.add(new DrawerItem("���� �� �ǰ�", R.drawable.ic_action_help));
		dataList.add(new DrawerItem("Upgrade to Pro", R.drawable.ic_action_camera));
		
		
		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(adapter);
		
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
	
		
		// enable ActionBar app icon to behave as action to toggle nav drawer
        // Ÿ��Ʋ ������ Ȩ��ư Ȱ��ȭ
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
        
        //���ʿ� ������ Fragment ����
        if (savedInstanceState == null) {
        	SelectItem(1); //1: ����ϱ�
        } 
	}
	
	public void onDestroy(){
		super.onDestroy();
		//db close();
		//db.close();
	}

	/**
	 * App�� ����� �� xml�� ��ȸ�Ͽ� �׼ǹ�(Ÿ��Ʋ��)�� �޴��� �����Ѵ�.
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
     * Ÿ��Ʋ���� �޴��� ���������� ȣ��ȴ�.
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
        	//Intent�� ���δٸ� Activity�� ȣ���ϰų� �����͸� �ְ�ޱ� ���� ����Ѵ�
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
		
		//�����ϱ� Activity �� ������ ������ ����� Fragment �� �����ؾ߸� Navigation Drawer �� ����� �� �ִ�.
		// >> ������ Fragment �� �����ұ�? >> ���� ���̵� �Ǵ� ���� �� �� ������ ��
		
		switch (position) {
		
		case 1: //����ϱ�
			fragment = new RecordFragment();
			break;
		case 2: //���
			fragment = new StatisticsFragment();
			break;
			
		case 3: //separator
			fragment = new FragmentOne();
			args.putString(FragmentOne.ITEM_NAME, dataList.get(position)
					.getItemName());
			args.putInt(FragmentOne.IMAGE_RESOURCE_ID, dataList.get(position)
					.getImgResID());
			break;
		case 4: //Ŀ�´�Ƽ
			fragment = new FragmentTwo();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(position)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(position)
					.getImgResID());
			break;
		case 5: //�����ϱ�
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(position)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
					.get(position).getImgResID());
			break;
		case 6: //�����ֱ�
			fragment = new FragmentThree();
			args.putString(FragmentThree.ITEM_NAME, dataList.get(position)
					.getItemName());
			args.putInt(FragmentThree.IMAGE_RESOURCE_ID, dataList
					.get(position).getImgResID());
			break;
		case 7: //ǥ�ؼ���ǥ
			fragment = new FragmentTwo();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(position)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(position)
					.getImgResID());
			break;
		case 8: //�Ʊ�����
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
		case 10: //����
			goSetting();
			//Fragment�� ��ü�ұ�?
		case 11://���� �� �ǰ�
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
	



	//ȯ�漳�� Activityȣ��
	private void goSetting()
    {
        Intent i = new Intent(this, SettingsActivity.class);
        
        startActivity(i);
    }
		
	//xml �� ���ǵ� ��ưŬ�� �����ʸ� fragment�� ����
	public void onButton(View v){
		//commit2
	}
	
}


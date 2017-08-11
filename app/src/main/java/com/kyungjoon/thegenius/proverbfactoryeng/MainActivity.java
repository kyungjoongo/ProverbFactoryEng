package com.kyungjoon.thegenius.proverbfactoryeng;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener {
    private AdView mAdView;
    private ArrayAdapter<String> mAdapter;
    private AsyncTask<String, Void, String> mTask;
    private ListView mListView;
    private View mFooter;
    TextView mHiddenValue;
    int page = 1;

    //PreferenceUtils preferenceUtils=new PreferenceUtils();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loadActivity();


    }

    private void loadActivity() {
        // Do all of your work here


        setContentView(R.layout.main_layout);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mListView = (ListView) findViewById(R.id.listview02);
        // 간단한 리스트를 표시할 Adapter 생성
        mAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item);


        // 읽기 중의 푸터(바닥글)을 생성
        mFooter = getLayoutInflater().inflate(R.layout.progress_item, null);
        // ListView에 푸터를 설정
        mListView.addFooterView(mFooter);
        // 어댑터를 설정
        mListView.setAdapter(mAdapter);
        // 스크롤리스너를 설정
        mListView.setOnScrollListener(this);

        /*mTask = new ProverbListAsyncTask(this, mAdapter).execute("http://35.194.71.159:8080/rest/getListForPaging/1");*/

        mTask = new ProverbListAsyncTask(this, mAdapter).execute("http://35.194.71.159:8080/rest/getListForPagingEng/1");

        PreferenceUtils.savePreferences(MainActivity.this, 1);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6826082357124500~2215817278");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        // 마지막인 경우 다음 데이터를 읽음
        if (totalItemCount == firstVisibleItem + visibleItemCount) {
            additionalReading();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {
    }


    //#####################################################
    //스크롤이 제일밑에 까지 내여왔을때 추가 데이터를 읽어들인다.
    //#####################################################
    private void additionalReading() {
        // 이미 다 읽었으면 건너 뜀
        if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING) {
            return;
        }


        //다음페이지를 fetch한후에 페이지넘버를 증가시킨후 SharedPref에 저장
        String currentPage = PreferenceUtils.getPreferences(MainActivity.this);
        int nextPage = Integer.parseInt(currentPage) + 1;
        /*mTask = new ProverbListAsyncTask(this, mAdapter).execute("http://35.194.71.159:8080/rest/getListForPaging/" + Integer.toString(nextPage));*/
        mTask = new ProverbListAsyncTask(this, mAdapter).execute("http://35.194.71.159:8080/rest/getListForPagingEng/" + Integer.toString(nextPage));

        PreferenceUtils.savePreferences(MainActivity.this, nextPage);

    }

    TextView rowTextView;
    ImageView imageView1;
    ListView listView;
    ArrayAdapter<String> mAdapter2;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {



        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadActivity();
                    return true;

                case R.id.navigation_dashboard:
                    getOneProverbsViaHttpRequest("http://35.194.71.159:8080/rest/getOneJson");
                    return true;
                case R.id.navigation_notifications:

                    getOneProverbsViaHttpRequest("http://35.194.71.159:8080/rest/getOneJsonEng");
                    return true;
            }
            return false;
        }

    };

    public void  getOneProverbsViaHttpRequest (String url){


        listView = (ListView) findViewById(R.id.listview02);
        mAdapter2 = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter2);
        listView.removeFooterView(mFooter);
        mAdapter2.clear();
        mAdapter2.notifyDataSetChanged();



        LinearLayout parentLinearLayout = (LinearLayout) findViewById(R.id.parentLinearLayout);
        int count = parentLinearLayout.getChildCount();
        if ( count> 3){
            parentLinearLayout.removeView(rowTextView);
            parentLinearLayout.removeView(imageView1);
        }
        LinearLayout innerLinearLayout = new LinearLayout(MainActivity.this);

        rowTextView = new TextView(MainActivity.this);
        imageView1 = new ImageView(MainActivity.this);


        try {
            new HttpGetOneProverbAsyncTask(MainActivity.this, rowTextView,parentLinearLayout, innerLinearLayout, imageView1).execute(url);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

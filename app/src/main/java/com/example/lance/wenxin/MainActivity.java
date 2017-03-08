package com.example.lance.wenxin;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager  mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mDatas;

    private TextView mChatTextView;
    private TextView mFriendTextView;
    private TextView mContactTextView;
    private LinearLayout mChatLinearLayout;
    private BadgeView mBadgeView;

    private ImageView mTabline;
    private int mScreen1_3;

    private int mCurrentPageIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initTabLine();
        initView();
    }

    private void initTabLine() {
        mTabline = (ImageView) findViewById(R.id.id_iv_tabline);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        mScreen1_3 = outMetrics.widthPixels/3;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabline.getLayoutParams();
        lp.width = mScreen1_3;
        mTabline.setLayoutParams(lp);

    }

    private void initView() {
        mChatTextView = (TextView) findViewById(R.id.tv_chat);
        mFriendTextView = (TextView) findViewById(R.id.tv_frind);
        mContactTextView = (TextView) findViewById(R.id.tv_contact);
        mChatLinearLayout = (LinearLayout) findViewById(R.id.id_ll_chat);

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mDatas = new ArrayList<>();

        ChatMainTabFragment tab01 = new ChatMainTabFragment();
        friendMainTabFragment tab02 = new friendMainTabFragment();
        ContactMainTabFragment tab03 = new ContactMainTabFragment();

        mDatas.add(tab01);
        mDatas.add(tab02);
        mDatas.add(tab03);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mDatas.get(position);
            }

            @Override
            public int getCount() {
                return mDatas.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams)
                        mTabline.getLayoutParams();
                if(mCurrentPageIndex == 0 && position == 0){//0->1
                    lp.leftMargin = (int) (positionOffset * mScreen1_3
                                                +mCurrentPageIndex * mScreen1_3);
                }else if(mCurrentPageIndex == 1 && position == 0){//1->0
                   lp.leftMargin= (int) (mCurrentPageIndex *mScreen1_3
                           + (positionOffset-1)*mScreen1_3);
                }else if(mCurrentPageIndex == 1 && position == 1){//1->2
                    lp.leftMargin =(int) (mCurrentPageIndex * mScreen1_3 +
                            positionOffset * mScreen1_3);
                }else if(mCurrentPageIndex == 2 && position == 1){//2->1
                    lp.leftMargin =(int) (mCurrentPageIndex * mScreen1_3 +
                            (positionOffset-1) * mScreen1_3);
                }
                mTabline.setLayoutParams(lp);

            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position){
                    case 0:
                        if (mBadgeView != null){
                            mChatLinearLayout.removeView(mBadgeView);
                        }
                        mBadgeView = new BadgeView(MainActivity.this);
                        mBadgeView.setBadgeCount(7);
                        mChatLinearLayout.addView(mBadgeView);

                        mChatTextView.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 1:
                        mFriendTextView.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 2:
                        mContactTextView.setTextColor(Color.parseColor("#008000"));
                        break;
                    default:
                        break;
                }
                mCurrentPageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resetTextView() {
        mChatTextView.setTextColor(Color.BLACK);
        mFriendTextView.setTextColor(Color.BLACK);
        mContactTextView.setTextColor(Color.BLACK);
    }
}

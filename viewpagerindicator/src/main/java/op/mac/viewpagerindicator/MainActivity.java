package op.mac.viewpagerindicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private LinearLayout mTabLayout;
    private Fragment[] fragments;
    private FragmentPagerAdapter mAdapter;
    public final static int TAB_ITEM = 4;
    private int itemWidth;
    private LinearLayout mLinearLayout;
    private boolean isInitTabLayout = false;
    private CostomLinearLayout mArrowLayout;
    private HorizontalScrollView mScrollView;

    private int oldPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isInitTabLayout) {
            initData();
            initTabLayout();
        }
    }

    private void initData() {
        fragments = new Fragment[8];
        fragments[0] = new SimpleFragment("页面0");
        fragments[1] = new SimpleFragment("页面1");
        fragments[2] = new SimpleFragment("页面2");
        fragments[3] = new SimpleFragment("页面3");
        fragments[4] = new SimpleFragment("页面4");
        fragments[5] = new SimpleFragment("页面5");
        fragments[6] = new SimpleFragment("页面6");
        fragments[7] = new SimpleFragment("页面7");

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset < 1) {
                    if (positionOffset == 0.0) {
                        move(position, 1);
                    } else {
                        move(position, positionOffset);
                    }
                    Log.i("offset", positionOffset + "");
                    Log.i("position", position + "");
                }
            }

            @Override
            public void onPageSelected(int position) {
//                move(position, 1);
                Log.i("offset", "111111111111111");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTabLayout() {
        int width = px2dp(getWindowManager().getDefaultDisplay().getWidth());
        itemWidth = width / TAB_ITEM;

        for (int i = 0; i < fragments.length; i++) {
            TextView textView = new TextView(getApplicationContext());
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            llp.width = dp2px(itemWidth);
            textView.setLayoutParams(llp);
            textView.setText("加油" + i);
            textView.setGravity(Gravity.CENTER);
            textView.setTag(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    mViewPager.setCurrentItem(position);
                }
            });
            textView.setTextColor(getResources().getColor(android.R.color.black));
            mTabLayout.addView(textView);
        }

        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                int viewPagerHeight = mLinearLayout.getHeight() - mTabLayout.getHeight() - dp2px(32);
                Log.i("Linheight", mLinearLayout.getHeight() + "");
                Log.i("tabheight", mLinearLayout.getHeight() + "");
                Log.i("viewPagerHeight", viewPagerHeight + "");
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mViewPager.getLayoutParams();
                layoutParams.height = viewPagerHeight;

                mArrowLayout.scrollTo(-dp2px(itemWidth / 2), 0);
//                move(0);
            }
        });

        isInitTabLayout = true;

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (LinearLayout) findViewById(R.id.tab_layout);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        mArrowLayout = (CostomLinearLayout) findViewById(R.id.layout_arrow);
        mScrollView = (HorizontalScrollView) findViewById(R.id.scrollview);
    }

    public int px2dp(float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void move(int itemPosition, float offset) {
        int boundNum = fragments.length - TAB_ITEM + 1;
        if (itemPosition < boundNum) {
            if (itemPosition + offset > oldPosition) {
                mScrollView.scrollBy(dp2px(itemWidth * offset), 0);
            } else if (itemPosition < oldPosition) {
                mScrollView.scrollBy(-dp2px(itemWidth * offset), 0);
            }
            if (itemPosition == boundNum - 1) {
                mArrowLayout.scrollTo(-dp2px(itemWidth / 2), 0);
            }
        } else {
            int position = itemPosition % TAB_ITEM;
            mArrowLayout.scrollTo(-dp2px(itemWidth / 2 + itemWidth * position), 0);

        }
        if (offset != 1) {
            oldPosition = itemPosition;
            Log.i("itemPosition", itemPosition + "");
        }

    }

}

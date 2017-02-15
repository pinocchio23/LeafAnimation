package op.mac.stickynavlayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewpager;
    private FragmentPagerAdapter adaper;
    private Fragment [] fragments = new Fragment[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
    }

    private void init() {

        fragments[0] = TabFragment.newTabFragment();
        fragments[1] = TabFragment.newTabFragment();
        fragments[2] = TabFragment.newTabFragment();


        adaper = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Log.i("fragment", fragments[position].toString());
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        };
        viewpager.setAdapter(adaper);
        viewpager.setCurrentItem(0);
        viewpager.setTag(fragments);
    }

    private void initView() {
        viewpager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
    }
}

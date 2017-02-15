package op.mac.stickynavlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by mac on 2017/1/13.
 */
public class TabFragment extends Fragment {

    private ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tabfragment, container, false);
        scrollView = (ScrollView) view.findViewById(R.id.id_stickynavlayout_scrollview);
        return view;
    }

    public static Fragment newTabFragment(){
        TabFragment fragment = new TabFragment();
        return fragment;
    }

    public ScrollView getScrollView() {
        return scrollView;
    }
}

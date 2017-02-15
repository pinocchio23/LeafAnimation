package op.mac.stickynavlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mac on 2017/1/13.
 */
public class EditFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_edit, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("status", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("status", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("status", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("status", "onDestroy");
    }
}

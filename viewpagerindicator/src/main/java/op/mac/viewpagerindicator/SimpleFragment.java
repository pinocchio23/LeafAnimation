package op.mac.viewpagerindicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mac on 2017/1/19.
 */
public class SimpleFragment extends Fragment {
    private TextView mTextView;
    private String content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_simple_fragment, container, false);
        initView(view);
        Log.i("life", "OncreatView");
        return view;
    }

    private void initView(View view) {
        mTextView = (TextView) view.findViewById(R.id.textview);
    }

    public SimpleFragment(){}

    public SimpleFragment(String content){
        super();
        this.content = content;
    }

    private void setContentText(String content) {
        mTextView.setText(content);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setContentText(this.content);
    }
}

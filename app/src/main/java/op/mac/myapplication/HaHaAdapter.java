package op.mac.rx;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.List;

/**
 * Created by mac on 16/9/11.
 */
public class HaHaAdapter extends BaseAdapter {

    private List<LargeSizeBean> lists;
    private Activity activity;

    public HaHaAdapter(List<LargeSizeBean> lists, Activity activity) {
        this.lists = lists;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = activity.getLayoutInflater().inflate(R.layout.item, null);
        EditText editText = (EditText) view.findViewById(R.id.editText);
        EditText editText1 = (EditText) view.findViewById(R.id.editText2);
        EditText editText2 = (EditText) view.findViewById(R.id.editText3);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }
}

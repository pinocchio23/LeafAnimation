package op.mac.viewpagerindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.OverScroller;

/**
 * Created by mac on 2017/1/20.
 */
public class CostomLinearLayout extends LinearLayout {

    private OverScroller overScroller;

    public CostomLinearLayout(Context context) {
        super(context);
        overScroller = new OverScroller(context);
    }

    public CostomLinearLayout(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        overScroller = new OverScroller(context);
    }

    @Override
    public void computeScroll() {
        if(overScroller.computeScrollOffset()){
            scrollTo(overScroller.getCurrX(), overScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }
}

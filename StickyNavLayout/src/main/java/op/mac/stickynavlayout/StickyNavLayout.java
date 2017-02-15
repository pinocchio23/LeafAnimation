package op.mac.stickynavlayout;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;

/**
 * Created by mac on 2017/1/13.
 */
public class StickyNavLayout extends LinearLayout {
    private View topView;
    private ViewPager viewPager;
    private ScrollView scrollView;
    private Fragment[] fragments;

    private int topViewHeight;
    private boolean isTopHidden;
    private OverScroller overScroller;
    private VelocityTracker velocityTracker;
    private int mTouchSlop;

    private int maxVelocity, minVelocity;
    private float lastY;
    private boolean isDragging;


    public StickyNavLayout(Context context) {
        super(context);
    }

    public StickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        overScroller = new OverScroller(context);
        velocityTracker = VelocityTracker.obtain();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        maxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        minVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    public StickyNavLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = getMeasuredHeight();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topView = findViewById(R.id.id_stickynavlayout_topview);
        viewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        topViewHeight = topView.getMeasuredHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        int action = event.getAction();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!overScroller.isFinished()) {
                    overScroller.abortAnimation();
                }
                velocityTracker.clear();
                velocityTracker.addMovement(event);
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                isDragging = false;
                if (!overScroller.isFinished()) {
                    overScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - lastY;
                if (!isDragging && Math.abs(dy) > mTouchSlop) {
                    isDragging = true;
                }
                if (isDragging) {
                    scrollBy(0, (int) -dy);
                    lastY = y;
                }
                break;
            case MotionEvent.ACTION_UP:
                isDragging = false;
                velocityTracker.computeCurrentVelocity(1000, maxVelocity);
                int velocityY = (int) velocityTracker.getYVelocity();
                if(Math.abs(velocityY) > minVelocity){
                    fling(-velocityY);
                }
                Log.i("velocity", velocityY+"");
                velocityTracker.clear();
                break;
        }

        return super.onTouchEvent(event);
    }

    private void fling(int velocityY) {
        overScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, topViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if(y < 0){
            y = 0;
        }
        if(y > topViewHeight){
            y = topViewHeight;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }

        isTopHidden = getScrollY() == topViewHeight;

    }

    @Override
    public void computeScroll() {
        if(overScroller.computeScrollOffset()){
            scrollTo(0, overScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        int action = ev.getAction();
        float y = ev.getY();

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - lastY;
                getcurrentScrollView();
                if (Math.abs(dy) > mTouchSlop)
                {
                    isDragging = true;
                    if (!isTopHidden
                            || (scrollView.getScrollY() == 0 && isTopHidden && dy > 0))
                    {
                        return true;
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void getcurrentScrollView() {
        fragments = (Fragment[]) viewPager.getTag();
        if(fragments != null){
            TabFragment fragment = (TabFragment) fragments[viewPager.getCurrentItem()];
            scrollView = fragment.getScrollView();
        }
    }
}

package op.mac.leafanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LeafView mLeafView;

    private int mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLeafView = (LeafView) findViewById(R.id.leafview);

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    mLeafView.setProgress(mProgress);
                    mLeafView.postInvalidate();
                    if (mProgress == 100) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                        mProgress = 0;
                    } else {
                        mProgress += 5;
                    }
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

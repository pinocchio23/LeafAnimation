package op.mac.rx;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class MainActivity extends AppCompatActivity {
    private VideoView mVideoView;
    private DanmakuView danmakuView;

    private boolean isShowDanmaku;

    private Button send;
    private EditText content;

    private DanmakuContext danmakuContext;

    private BaseDanmakuParser baseDanmakuParser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Log.i("message--------------", "onCreate-------------");
        initView();
        playVideo();
        initDanmaku();
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == View.SYSTEM_UI_FLAG_VISIBLE) {
                    onWindowFocusChanged(true);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("message--------------", "onPause-------------");
        if (danmakuView != null && danmakuView.isPrepared()) {
            danmakuView.pause();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i("message--------------", "onResume-------------");
        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
            danmakuView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isShowDanmaku = false;
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }
    }

    private void initView() {
        mVideoView = (VideoView) findViewById(R.id.video_view);
        danmakuView = (DanmakuView) findViewById(R.id.danmaku_view);
        send = (Button) findViewById(R.id.btn_send);
        content = (EditText) findViewById(R.id.edit_content);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contentStr = content.getText().toString();
                if (contentStr != null && !contentStr.equals("")) {
                    addDanmu(contentStr, true);
                    content.setText("");
                }
            }
        });
    }

    private void playVideo() {
        String videoUrl = Environment.getExternalStorageDirectory()
                + "/DCIM/Camera/20161118_210201.mp4";
        Log.i("message--------------", videoUrl);
        File file = new File(videoUrl);
        if (file.exists()) {
            mVideoView.setMediaController(new MediaController(this));
            mVideoView.setVideoPath(file.getAbsolutePath());
            mVideoView.start();
            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    isShowDanmaku = false;
                    if (danmakuView != null) {
                        danmakuView.release();
                        danmakuView = null;
                    }
                }
            });
        }

    }

    private void initDanmaku() {
        //提高绘制效率
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {

                isShowDanmaku = true;
                danmakuView.start();
                makeSomeDanmu();

            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create();
        danmakuView.prepare(baseDanmakuParser, danmakuContext);
    }

    /**
     * 生成 弹幕
     */
    private void makeSomeDanmu() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isShowDanmaku) {
                    int time = new Random().nextInt(300);
                    String content = time + "5H" + time;
                    addDanmu(content, false);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    private void addDanmu(String content, boolean border) {
        if (danmakuView == null) return;
        BaseDanmaku baseDanmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        baseDanmaku.text = content;
        baseDanmaku.padding = 5;
        baseDanmaku.textSize = sp2px(20);
        baseDanmaku.textColor = Color.WHITE;
        baseDanmaku.setTime(danmakuView.getCurrentTime());
        if (border) {
            baseDanmaku.borderColor = Color.GREEN;
        }

        danmakuView.addDanmaku(baseDanmaku);
    }

    public int sp2px(float spValue) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT > 19) {
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN | View
                    .SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                    .SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View
                    .SYSTEM_UI_FLAG_LAYOUT_STABLE | View
                    .SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            getWindow().getDecorView().setSystemUiVisibility(option);
        }
    }
}

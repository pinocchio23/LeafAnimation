package op.mac.rx;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private String[] names = new String[5];
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        r1();
        r2();
    }

    private void r2() {
        imageView = (ImageView) findViewById(R.id.image);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(R.mipmap.wechat);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "可以滴,小皮", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Drawable drawable) {
                imageView.setImageDrawable(drawable);
            }
        });
    }


    private void r1() {
        names[0] = "James";
        names[1] = "Curry";
        names[2] = "Harden";
        names[3] = "Durant";
        names[4] = "Westbrook";

        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i("NBA------", s);
            }
        });
    }
}

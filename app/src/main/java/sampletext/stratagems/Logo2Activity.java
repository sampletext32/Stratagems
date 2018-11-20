package sampletext.stratagems;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Logo2Activity extends AppCompatActivity {

    ImageView logoImageView2;

    TextView loadingText;

    boolean abort;

    int dots = 0;

    Animation.AnimationListener animationListener = new Animation.AnimationListener() {

        Intent intent;

        @Override
        public void onAnimationStart(Animation animation) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            startActivity(intent);
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo2);
        Static.SetPortrait(this);

        logoImageView2 = findViewById(R.id.logoImageView2);
        loadingText = findViewById(R.id.loadingText);

        float animScaleStart = 1f;
        float animScaleEnd   = 1.05f;
        float animRelativeTo = 0.5f;

        Animation anim = new ScaleAnimation(
                animScaleStart, animScaleEnd,
                animScaleStart, animScaleEnd,
                Animation.RELATIVE_TO_SELF, animRelativeTo,
                Animation.RELATIVE_TO_SELF, animRelativeTo);
        anim.setFillAfter(true); //оставить результат, не сбрасывать на начало
        anim.setDuration(2000);

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            String dotsStr = "";
                            for (int i = 0; i < dots; i++) {
                                dotsStr += '.';
                            }
                            loadingText.setText("Loading" + dotsStr);
                        }
                    });
                    try {
                        Thread.sleep(300);
                        dots++;
                        dots = dots % 4;
                    }
                    catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }).start();
        anim.setAnimationListener(animationListener);
        logoImageView2.setAnimation(anim);
    }
}

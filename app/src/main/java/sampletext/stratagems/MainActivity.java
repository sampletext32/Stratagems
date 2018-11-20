package sampletext.stratagems;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    FrameLayout loaderContainer;

    ImageView loaderRotator, loaderBackShadow;

    TextView _backText;

    ViewFlipper _flipper;

    long duration;

    float startDegree = 0f;

    float endDegree = 360f;

    float pivotX;

    float pivotY;

    View.OnClickListener btnFlipFragmentOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (_flipper.getDisplayedChild() != _flipper.getChildCount() - 1) {
                _flipper.showNext();
            }
            else {
                _flipper.showPrevious();
            }
        }
    };

    View.OnTouchListener btnFlipFragmentOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_HOVER_ENTER:
                case MotionEvent.ACTION_DOWN:
                    _btnFlipFragment.setAlpha(0.5f);
                    break;
                case MotionEvent.ACTION_HOVER_EXIT:
                case MotionEvent.ACTION_UP:
                    _btnFlipFragment.setAlpha(0.35f);
                    break;

                default:
            }

            if (_btnFlipFragment.getAnimation() != null && _btnFlipFragment.getAnimation().hasEnded()) {
                Animation animIn = new RotateAnimation(startDegree, endDegree, pivotX, pivotY);
                animIn.setFillAfter(true); //оставить результат, не сбрасывать на начало
                animIn.setDuration(duration);

                _btnFlipFragment.setAnimation(animIn);
            }

            return false;
        }
    };

    //region Views
    private Button imLuckyButton, _btnFlipFragment;

    private SeekBar seekBar;

    private ViewPager pager;

    //endregion
    private PagerAdapter pagerAdapter;

    //region imluckyDialogOnShowListener
    private OnShowListener imluckyDialogOnShowListener = new OnShowListener() {

        @Override
        public void onShow(DialogInterface dialogInterface) {
            new Thread(new Runnable() {

                private DialogInterface myParam;

                Runnable init(DialogInterface myParam) {
                    this.myParam = myParam;
                    return this;
                }

                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        myParam.dismiss();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.init(dialogInterface)).start();
        }
    };
    //endregion

    //region imluckyOnDismissListener
    private OnDismissListener imluckyOnDismissListener = new OnDismissListener() {

        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            int rand;
            do {
                rand = Static.rnd.nextInt(Integer.MAX_VALUE) % pagerAdapter.getCount();
            }
            while (rand == pager.getCurrentItem());
            SetLocalPageIndex(rand);
        }
    };
    //endregion

    //region imluckyOnClickListener
    private View.OnClickListener imluckyOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            //ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
            //progressDialog.setCanceledOnTouchOutside(false);
            //progressDialog.setMessage("Подождите");
            //progressDialog.setIndeterminate(true);
            //progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.loader_rotate));
            //progressDialog.setOnDismissListener(imluckyOnDismissListener);
            //progressDialog.setOnShowListener(imluckyDialogOnShowListener);
            //progressDialog.show();

            ShowCustomLoader();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                imluckyOnDismissListener.onDismiss(null);
                                HideCustomLoader();
                            }
                        });
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    };
    //endregion

    //region imluckyOnTouchListener
    private OnTouchListener imluckyOnTouchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_HOVER_ENTER:
                case MotionEvent.ACTION_DOWN:
                    imLuckyButton.setAlpha(0.8f);
                    break;
                case MotionEvent.ACTION_HOVER_EXIT:
                case MotionEvent.ACTION_UP:
                    imLuckyButton.setAlpha(1.0f);
                    break;

                default:
            }
            return false;
        }
    };
    //endregion

    //region loadingOnShowListener
    private OnShowListener loadingOnShowListener = new OnShowListener() {

        @Override
        public void onShow(DialogInterface dialogInterface) {
            new Thread(new Runnable() {

                private DialogInterface myParam;

                Runnable init(DialogInterface myParam) {
                    this.myParam = myParam;
                    return this;
                }

                @Override
                public void run() {
                    try {
                        DataUnitsHolder.init(getApplicationContext());
                        Thread.sleep(500);
                        myParam.dismiss();
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.init(dialogInterface)).start();
        }
    };
    //endregion

    //region loadingOnDismissListener
    private OnDismissListener loadingOnDismissListener = new OnDismissListener() {

        @Override
        public void onDismiss(DialogInterface dialogInterface) {
            pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
            pager.setAdapter(pagerAdapter);

            seekBar.setMax((pagerAdapter.getCount() - 1)); // -1 because we display items from 1 to N (not from 0 to N-1)

            _backText.setText(DataUnitsHolder.get(pager.getCurrentItem()).get_backText());
        }
    };
    //endregion

    //region seekBarChangeListener
    private OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener() {

        int lastPos = 0; // last page index

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (lastPos != i)// checking to prevent recursive calls from seekbar to pager and backwords
            {
                lastPos = i;
                pager.setCurrentItem(i, true);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    //endregion

    //region pageChangeListener
    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

        int lastPos = 0;//last page index

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (lastPos != position) // checking to prevent recursive calls from seekbar to pager and backwords
            {
                lastPos = position;
                seekBar.setProgress(position);
                _backText.setText(DataUnitsHolder.get(pager.getCurrentItem()).get_backText());
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    //endregion

    private void SetLocalPageIndex(int index) {
        seekBar.setProgress(index);
        pager.setCurrentItem(index, true);
    }

    private void ShowLoadingDialog() {
        //ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        //progressDialog.setCanceledOnTouchOutside(false);
        //progressDialog.setMessage("Пожалуйста подождите...");
        //progressDialog.setIndeterminate(true);
        //progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.loader_rotate));
        //progressDialog.setOnDismissListener(loadingOnDismissListener);
        //progressDialog.setOnShowListener(loadingOnShowListener);
        //progressDialog.show();

        DataUnitsHolder.init(getApplicationContext());

        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        seekBar.setMax((pagerAdapter.getCount() - 1)); // -1 because we display items from 1 to N (not from 0 to N-1)

        _backText.setText(DataUnitsHolder.get(pager.getCurrentItem()).get_backText());
    }

    public void ShowCustomLoader() {
        Animation rotationAnimation = new RotateAnimation(0, 360, loaderRotator.getWidth() / 2f, loaderRotator.getHeight() / 2f);
        rotationAnimation.setFillAfter(false);
        rotationAnimation.setDuration(500);

        loaderRotator.setAnimation(rotationAnimation);
        loaderContainer.setVisibility(View.VISIBLE);
    }

    public void HideCustomLoader() {
        loaderContainer.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Static.SetPortrait(this);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.mainPager);
        imLuckyButton = findViewById(R.id.imLuckyButton);
        seekBar = findViewById(R.id.mainSeekBar);

        imLuckyButton.setOnClickListener(imluckyOnClickListener);
        imLuckyButton.setOnTouchListener(imluckyOnTouchListener);

        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        seekBar.getThumb().setColorFilter(
                getResources().getColor(R.color.myRed), PorterDuff.Mode.SRC_ATOP);
        seekBar.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.myRed), PorterDuff.Mode.SRC_ATOP);

        pager.addOnPageChangeListener(pageChangeListener);

        _backText = findViewById(R.id.backText);

        _flipper = findViewById(R.id.fragmentViewFlipper);

        duration = getResources().getInteger(R.integer.flipAnimDuration);

        _btnFlipFragment = findViewById(R.id.btnFlipFragment);
        _btnFlipFragment.getBackground().setColorFilter(getResources().getColor(R.color.myRed), PorterDuff.Mode.SRC_ATOP);
        _btnFlipFragment.setOnClickListener(btnFlipFragmentOnClickListener);
        _btnFlipFragment.setOnTouchListener(btnFlipFragmentOnTouchListener);

        ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);

        //region This Weird thing is needed to wait for app to calculate sizes
        viewGroup.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            View view;

            @Override
            public boolean onPreDraw() {
                if (view.getViewTreeObserver().isAlive())
                    view.getViewTreeObserver().removeOnPreDrawListener(this);

                //region This is actual size
                pivotX = _btnFlipFragment.getWidth() / 2f;
                pivotY = _btnFlipFragment.getHeight() / 2f;

                Animation animOut = new RotateAnimation(startDegree, startDegree, pivotX, pivotY);
                animOut.setFillAfter(true); //оставить результат, не сбрасывать на начало
                animOut.setDuration(duration);
                //endregion

                _btnFlipFragment.setAnimation(animOut);

                return false;
            }

            ViewTreeObserver.OnPreDrawListener setView(View v) {
                view = v;
                return this;
            }
        }.setView(viewGroup));
        //endregion

        loaderContainer = findViewById(R.id.loaderContainer);
        loaderRotator = findViewById(R.id.loaderRotator);
        //loaderBackShadow = findViewById(R.id.loaderBackShadow);
        //loaderBackShadow.setBackgroundColor(Color.parseColor("#AAAAAA"));
        //loaderBackShadow.setAlpha(0.2f);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        Static.DiagonalInches = (float) Math.sqrt(xInches * xInches + yInches * yInches);
        if (Static.DiagonalInches >= 6.5) {
            imLuckyButton.setTextSize(24);
            _backText.setTextSize(20);
        }
        else {
            imLuckyButton.setTextSize(16);
            _backText.setTextSize(14);
        }

        ShowLoadingDialog();
    }
}
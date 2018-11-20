package sampletext.stratagems;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import java.util.Calendar;
import java.util.Random;

public class Static {

    public static Random rnd            = new Random(Calendar.getInstance().get(Calendar.MILLISECOND));

    public static float  DiagonalInches = 0f;
    public static void SetPortrait(Activity a) {
        a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}

package mindtwisted.com.ratingcounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String RATING_KEY = "Rating";

    private TextView mTitleRoundedRatingView;
    private TextView mTitleRatingView;
    private TextView[] mRatingTextViews;
    private TextView mRatingTotalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitleRoundedRatingView = (TextView)findViewById(R.id.rating_rounded_text_view);
        mTitleRatingView = (TextView)findViewById(R.id.rating_text_view);
        mRatingTotalTextView = (TextView)findViewById(R.id.rating_total_text_view);

        mRatingTextViews = new TextView[] {
                (TextView)findViewById(R.id.rating_1_stars_text_view),
                (TextView)findViewById(R.id.rating_2_stars_text_view),
                (TextView)findViewById(R.id.rating_3_stars_text_view),
                (TextView)findViewById(R.id.rating_4_stars_text_view),
                (TextView)findViewById(R.id.rating_5_stars_text_view)};

        for(int index = 0; index < 5; index++) {
            mRatingTextViews[index].setText(String.valueOf(getIntPreference(RATING_KEY + index, 0)));
        }
        update();
    }

    public void onIncreaseRating(View view) {
        int rating = Integer.parseInt(view.getTag().toString());
        int index = rating - 1;
        int count = getIntPreference(RATING_KEY + index, 0) + 1;
        setIntPreference(RATING_KEY + index, count);
        mRatingTextViews[index].setText(String.valueOf(count));
        update();
    }

    public void onDecreaseRating(View view) {
        int rating = Integer.parseInt(view.getTag().toString());
        int index = rating - 1;
        int count = Math.max(0, getIntPreference(RATING_KEY + index, 0) - 1);
        setIntPreference(RATING_KEY + index, count);
        mRatingTextViews[index].setText(String.valueOf(count));
        update();
    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void update() {
        int rating1count = getIntPreference(RATING_KEY + "0", 0);
        int rating2count = getIntPreference(RATING_KEY + "1", 0);
        int rating3count = getIntPreference(RATING_KEY + "2", 0);
        int rating4count = getIntPreference(RATING_KEY + "3", 0);
        int rating5count = getIntPreference(RATING_KEY + "4", 0);
        float total = rating1count + rating2count + rating3count + rating4count + rating5count;
        if(total == 0) {
            mTitleRatingView.setText("-");
        }else {
            float rating = rating1count + (rating2count * 2) + (rating3count * 3) + (rating4count * 4) + (rating5count * 5);
            mTitleRatingView.setText(String.valueOf(rating / total));
            mTitleRoundedRatingView.setText(String.format("%.1f", (rating / total)));
        }
        mRatingTotalTextView.setText((int)total + (total == 1 ? " rating" : " ratings"));
    }

    /**
     * Set integer preference
     */
    public void setIntPreference(String key, int value) {
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Gets int preference
     */
    public int getIntPreference(String key, int defaultValue) {
        SharedPreferences preferences = getSharedPreferences();
        if(!preferences.contains(key)) {
            setIntPreference(key, defaultValue);
            return defaultValue;
        }
        return preferences.getInt(key, defaultValue);
    }

}

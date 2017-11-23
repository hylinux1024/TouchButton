package net.angrycode.touchbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import net.angrycode.library.TouchButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    TouchButton mTouchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTouchBtn = findViewById(R.id.touch_btn);
        mTouchBtn.setOnHoldListener(new TouchButton.OnHoldListener() {
            @Override
            public void onHold(boolean hold) {
                if (hold) {
                    Log.d(TAG, "holding the button");
                } else {
                    Log.d(TAG, "release the button");
                }
            }
        });
    }
}

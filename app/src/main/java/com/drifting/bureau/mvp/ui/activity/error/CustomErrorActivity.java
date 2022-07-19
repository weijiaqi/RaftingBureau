package com.drifting.bureau.mvp.ui.activity.error;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
import com.drifting.bureau.base.BaseActivity;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.di.component.AppComponent;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;

public class CustomErrorActivity extends BaseActivity {
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_custom_error;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false, false);
//        TextView errorDetailsText = findViewById(R.id.error_details);
//        errorDetailsText.setText(CustomActivityOnCrash.getStackTraceFromIntent(getIntent()));
        ShapeTextView restartButton = findViewById(R.id.restart_button);
        final CaocConfig config = CustomActivityOnCrash.getConfigFromIntent(getIntent());
        if (config == null) {
            //This should never happen - Just finish the activity_release_scheme to avoid a recursive crash.
            finish();
            return;
        }
        if (config.isShowRestartButton() && config.getRestartActivityClass() != null) {
            restartButton.setText("重启应用");
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomActivityOnCrash.restartApplication(CustomErrorActivity.this, config);
                }
            });
        } else {
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomActivityOnCrash.closeApplication(CustomErrorActivity.this, config);
                }
            });
        }
    }
}

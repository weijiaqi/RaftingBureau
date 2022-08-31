package com.drifting.bureau.mvp.ui.activity.error;

import android.os.Bundle;
<<<<<<< HEAD
=======
import android.view.View;
>>>>>>> origin/dev

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.drifting.bureau.R;
<<<<<<< HEAD
import com.drifting.bureau.base.BaseManagerActivity;
=======
import com.drifting.bureau.base.BaseActivity;
>>>>>>> origin/dev
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.di.component.AppComponent;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;

<<<<<<< HEAD
public class CustomErrorActivity extends BaseManagerActivity {
=======
public class CustomErrorActivity extends BaseActivity {
>>>>>>> origin/dev
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
        ShapeTextView restartButton = findViewById(R.id.restart_button);
        final CaocConfig config = CustomActivityOnCrash.getConfigFromIntent(getIntent());
        if (config == null) {
            //This should never happen - Just finish the activity_release_scheme to avoid a recursive crash.
            finish();
            return;
        }
        if (config.isShowRestartButton() && config.getRestartActivityClass() != null) {
            restartButton.setText("重启应用");
            restartButton.setOnClickListener(v -> CustomActivityOnCrash.restartApplication(CustomErrorActivity.this, config));
        } else {
            restartButton.setOnClickListener(v -> CustomActivityOnCrash.closeApplication(CustomErrorActivity.this, config));
        }
    }
}

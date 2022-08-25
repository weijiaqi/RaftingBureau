package com.drifting.bureau.view;

import com.google.ar.sceneform.ux.ArFragment;

public class CleanArFragment extends ArFragment {

    @Override
    public void onResume() {
        super.onResume();
        //开始就隐藏
        getInstructionsController().setVisible(false);
    }
}



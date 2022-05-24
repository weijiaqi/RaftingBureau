package com.drifting.bureau.mvp.ui.holder;

import android.content.Context;
import android.speech.tts.Voice;
import android.view.View;
import android.widget.RelativeLayout;

import com.drifting.bureau.R;
import com.drifting.bureau.mvp.model.entity.DeliveryDetailsEntity;
import com.drifting.bureau.mvp.ui.dialog.VoicePlayDialog;
import com.drifting.bureau.view.VoiceWave;
import com.jess.arms.base.BaseRecyclerHolder;

import java.util.List;

import butterknife.BindView;

public class DeliveryVoiceHolder extends BaseRecyclerHolder {
    @BindView(R.id.videoView)
    VoiceWave voiceWave;
    @BindView(R.id.rl_voice_play)
    RelativeLayout mRlVoicePlay;
    private VoicePlayDialog voicePlayDialog;
    private Context context;
    public DeliveryVoiceHolder(View itemView) {
        super(itemView);
        context=itemView.getContext();
    }

    public void setData(List<DeliveryDetailsEntity> mDatas, int position) {
        voiceWave.setDecibel(0);
        mRlVoicePlay.setOnClickListener(v -> {
            voicePlayDialog=new VoicePlayDialog(context);
            voicePlayDialog.show();
        });
    }
}

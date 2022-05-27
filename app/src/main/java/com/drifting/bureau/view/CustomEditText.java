package com.drifting.bureau.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;


import com.drifting.bureau.R;
import com.drifting.bureau.util.ToastUtil;
import com.hjq.toast.ToastUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author wjq
 * @date 2021/8/29
 */

@SuppressLint("AppCompatCustomView")
public class CustomEditText extends EditText {
    private String mEncoding = "GBK";
    private int mMaxByteLength;
    private String mPromptText;

    public CustomEditText(Context context) {
        super(context);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText);
        mMaxByteLength = ta.getInt(R.styleable.CustomEditText_length, 20);
        mPromptText = ta.getString(R.styleable.CustomEditText_prompt_text);
        ta.recycle();
    }

    private void init() {
        setFilters(new InputFilter[]{inputFilter});
    }

    public int getMaxByteLength() {
        return mMaxByteLength;
    }

    public void setMaxByteLength(int maxByteLength) {
        this.mMaxByteLength = maxByteLength;
    }

    public String getEncoding() {
        return mEncoding;
    }

    public void setEncoding(String encoding) {
        this.mEncoding = encoding;
    }

    public String getPromptText() {
        return mPromptText;
    }

    public void setPromptText(String mPromptText) {
        this.mPromptText = mPromptText;
    }

    private InputFilter inputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            try {
                int len = 0;
                boolean more = false;
                do {
                    SpannableStringBuilder builder =
                            new SpannableStringBuilder(dest).replace(dstart, dend, source.subSequence(start, end));
                    len = builder.toString().getBytes(mEncoding).length;
                    more = len > mMaxByteLength;
                    if (more) {
                        end--;
                        source = source.subSequence(start, end);
                        if (end == 0) {
                            if (!TextUtils.isEmpty(mPromptText)) {
                                ToastUtil.showToast(mPromptText);
                            } else {
                                ToastUtil.showToast("温馨提示:最大长度不超过" + mMaxByteLength);
                            }
                        }
                    }
                } while (more);
                return source;
            } catch (UnsupportedEncodingException e) {
                return "Exception";
            }
        }
    };
}

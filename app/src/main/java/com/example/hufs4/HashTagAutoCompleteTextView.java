package com.example.hufs4;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;

public class HashTagAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {
    public HashTagAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public HashTagAutoCompleteTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.autoCompleteTextViewStyle);
    }

    public HashTagAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    // 팝업창의 나열된 문구를 선택하면 호출
    @Override
    protected void replaceText(CharSequence text) {
        clearComposingText();

        HashTagSuggestAdapter adapter = (HashTagSuggestAdapter) getAdapter();
        HashTagSuggestAdapter.HashTagFilter filter = (HashTagSuggestAdapter.HashTagFilter) adapter.getFilter();

        // span은 인력된 전체 문자열
        Editable span = getText();

        // text는 리스트로부터 선택된 단어
        span.replace(filter.start, filter.end, text);
    }
}
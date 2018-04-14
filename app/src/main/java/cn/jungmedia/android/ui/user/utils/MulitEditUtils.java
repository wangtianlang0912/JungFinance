package com.jung.android.ui.user.utils;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/***
 *
 * @Copyright 2018
 *
 * @TODO
 *
 * @author niufei
 *
 *
 * @date 2018/3/17. 上午9:51
 *
 *
 */
public class MulitEditUtils {

    public static void associate(final EditText editText, View clearView) {

        editText.addTextChangedListener(new EditTextChange(clearView));
        clearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
    }

    static class EditTextChange implements TextWatcher {

        private View clearView;

        public EditTextChange(View clearView) {
            this.clearView = clearView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            clearView.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
        }
    }

}

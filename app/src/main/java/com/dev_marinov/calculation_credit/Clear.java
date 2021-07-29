package com.dev_marinov.calculation_credit;

import android.content.Context;

public class Clear extends MainActivity{

    Context mainActivity; // ссылка на MainActivity


    // констуктор
    Clear (Context mainActivity) {

        this.mainActivity = mainActivity;
    }

    public void Button_clear() {

    MainActivity M_m = ((MainActivity) mainActivity); // запись в переменную для визульного экономии места в коде

        M_m.radioButton_Credit.setChecked(true);
        M_m.radioButton_Hypothec.setChecked(false);
        M_m.editText_sum_credit.setText("");
        M_m.editText_sum_credit.requestFocus();
        M_m.editText_contribution.setText("");
        M_m.editText_interest_rate.setText("");
        M_m.textView_value_seeBar.setText("0");
        M_m.seekBar.setProgress(0);
        M_m.radioButton_Annuit.setChecked(true);
        M_m.radioButton_Diff.setChecked(false);
        if (M_m.cl_calculation.new_LinerLayout != null) {
            M_m.cl_calculation.new_LinerLayout.removeAllViews();
        }
    }
}

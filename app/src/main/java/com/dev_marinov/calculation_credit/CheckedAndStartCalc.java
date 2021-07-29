package com.dev_marinov.calculation_credit;

import android.content.Context;
import android.util.Log;
import android.view.View;

public class CheckedAndStartCalc extends MainActivity {

    Context mainActivity;

    CheckedAndStartCalc(Context mainActivity) {

        this.mainActivity = mainActivity;
    }

    // метод проверки на не заполненные поля и после заполнения выбор Аннуитентного или Дифференцировнного метода
    public void CheckedAndStartCalc_method() {

        // ВОПРОС. ПОЧЕМУ FINAL
        final MainActivity M_m = ((MainActivity) mainActivity); // запись в переменную для визульного экономии места в коде

        error = 0; // По умолчанию "0" ошибок нет

        M_m.error_sum_credit.setVisibility(View.GONE); // поле ВВЕДИТЕ СУММУ КРЕДИТА. поле не видимо
        if (M_m.editText_sum_credit.getText().toString().equals("")) //Если поле editText_sum_credit не заполнено, то сработает 1
        {
            error = 1;
            M_m.error_sum_credit.setVisibility(View.VISIBLE); // покажет ЗАПОЛНИТЕ ПОЛЕ
        }

        M_m.error_contribution.setVisibility(View.GONE); // текст ЗАПОЛНИТЕ ПОЛЕ не видно
        if (M_m.editText_contribution.getText().toString().equals("")) // ничего не введено
        {
            if (M_m.radioCheck_credit_or_ipoteka == 1) { // и если выбрано radioCheck_credit_or_ipoteka (как ипотека - это 1)
                error = 1;
                M_m.error_contribution.setVisibility(View.VISIBLE); // то появится текст ЗАПОЛНИТЕ ПОЛЕ
            }
        }

        M_m.error_interest_rate.setVisibility(View.GONE);
        if (M_m.editText_interest_rate.getText().toString().equals("")) {
            error = 1;
            (M_m).error_interest_rate.setVisibility(View.VISIBLE);
        }

        M_m.error_indicate_seek_bar.setVisibility(View.GONE);
        int progress = M_m.seekBar.getProgress();
        if (progress == 0) {
            error = 1;
            M_m.error_indicate_seek_bar.setVisibility(View.VISIBLE);
        }

        if (error == 0) ///Все ок
        {


            if (M_m.radioButton_Annuit.isChecked())  //Аннуитентные
            {
                Log.e("проверка ", "выбрано annuit");
                //  radioButton_Diff.setTextColor(getResources().getColor(android.R.color.black)); // и radioButton_Diff будет черный
                //  radioButton_Annuit.setTextColor(getResources().getColor(android.R.color.white)); // и radioButton_Annuit белый
                if (M_m.radioCheck_credit_or_ipoteka == 0) // Если выбран кредит
                {
                    M_m.cl_calculation.Calc_annuit_credit(M_m.editText_sum_credit.getText().toString(), M_m.editText_interest_rate.getText().toString(), progress, M_m.scrollView_main);
                } else //иначе выбрана ипотека
                {
                    M_m.cl_calculation.Calc_annuit_hypothec(M_m.editText_sum_credit.getText().toString(), M_m.editText_interest_rate.getText().toString(), M_m.editText_contribution.getText().toString(), progress, M_m.scrollView_main);
                }

            } else   //иначе выбрано Дифференцированные
            {
                Log.e("проверка ", "выбрано diff");
                // radioButton_Diff.setTextColor(getResources().getColor(android.R.color.white)); // и radioButton_Diff будет белый
                // radioButton_Annuit.setTextColor(getResources().getColor(android.R.color.black)); // и radioButton_Annuit чеерный
                if (M_m.radioCheck_credit_or_ipoteka == 0) //Кредит
                {
                    M_m.cl_calculation.Calc_diff_credit(M_m.editText_sum_credit.getText().toString(), M_m.editText_interest_rate.getText().toString(), progress);

                } else //Ипотека
                {
                    M_m.cl_calculation.Calc_diff_hypothec(M_m.editText_sum_credit.getText().toString(), M_m.editText_interest_rate.getText().toString(), M_m.editText_contribution.getText().toString(), progress);

                }
            }

            //  scrollView_main опускается вниз при нажати на кнопку рас
            M_m.scrollView_main.post(new Runnable() {
                public void run() {
                    M_m.scrollView_main.scrollTo(0, M_m.scrollView_main.getBottom());
                }
            });
        }

    }
}

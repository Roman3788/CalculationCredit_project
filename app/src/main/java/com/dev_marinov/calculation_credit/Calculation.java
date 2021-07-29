package com.dev_marinov.calculation_credit;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Locale;

public class Calculation  {
    LinearLayout container_result;
    LinearLayout new_LinerLayout;
    private Context mainActivity; // ссылка на MainActivity

    double term; // срок кредита
    double sum_credit; // сумма кредита
    double contribution; // первоначальный взнос (для radiobutton ipoteka)

    double percent_cost; //
    double monthly_payment; // ежемесячный платеж

    Calculation(Context mainActivity, LinearLayout container_result ) {
        this.mainActivity = mainActivity;
        this.container_result = container_result;
    }
    private BigDecimal roundUp(double value, int digits){ // метод для окргуления числа double
        return new BigDecimal(""+value).setScale(digits, BigDecimal.ROUND_HALF_UP);
    }

    public void Calc_annuit_credit (String edit_sum_credit, String edit_rate, int edit_term, final View scrollView_main) { // метода расчета АННУТИТЕНТНЫЙ

        container_result.removeAllViews(); // в начале каждого запуска производвиться очистка полей (LinearLayout)

        // создаем программно макет linerlayout
        new_LinerLayout = new LinearLayout(this.mainActivity);
        LinearLayout.LayoutParams Param_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        new_LinerLayout.setLayoutParams(Param_1); // присвоили параметры для нового макета linaerlayout
        new_LinerLayout.setOrientation(LinearLayout.VERTICAL);

        sum_credit = Double.parseDouble(edit_sum_credit);
        double rate = Double.parseDouble(edit_rate); // процентаная ставка годовых
        term = edit_term; // срок кредита (кол-во мес.)

        percent_cost = ((rate / 100) / 12); // вынесеный из формулы часто повторяющийся расчет процентов

        // расчет ежемесячного платежа
        monthly_payment = sum_credit * ((percent_cost + (percent_cost / (Math.pow((1 + percent_cost), term) - 1))));

        System.out.println("-----------------------------");
        System.out.printf("Сумма кредита: %.1f ₽ \n", sum_credit);

        System.out.printf("Ставка годовых: %.1f %% \n", rate);
        System.out.printf("Срок кредита: %.1f месяцев \n", term);
        System.out.printf("Ежемесячный платеж: %.2f ₽ \n", monthly_payment);
        System.out.printf("Размер переплаты: %.2f ₽ \n", term * monthly_payment - sum_credit);
        System.out.println("-----------------------------");

        TextView textViewTitle_result = new TextView(this.mainActivity);
        textViewTitle_result.setText("РЕЗУЛЬТАТЫ РАСЧЕТА");
        textViewTitle_result.setPadding(0,15,0,15);
        textViewTitle_result.setGravity(Gravity.CENTER);
        new_LinerLayout.addView(textViewTitle_result);

        // наименование поля отчета СУММА КРЕДИТА:
        TextView newTextView_name_sum_credit = new TextView(this.mainActivity);
        newTextView_name_sum_credit.setText(String.format("Сумма кредита: %s ₽", String.valueOf(roundUp(sum_credit, 0))));
        newTextView_name_sum_credit.setTextColor(ContextCompat.getColor(mainActivity, R.color.colorBlack));

        // наименование поля отчета СТАВКА ГОДОВЫХ:
        TextView newTextView_name_interest_rate = new TextView(this.mainActivity);
        newTextView_name_interest_rate.setText(MessageFormat.format("Ставка годовых: {0} %", rate));
        newTextView_name_interest_rate.setTextColor(ContextCompat.getColor(mainActivity,R.color.colorBlack));

        // наименование поля отчета СРОК КРЕДИТА:
        TextView newTextView_name_seek_bar = new TextView(this.mainActivity);
        newTextView_name_seek_bar.setText(MessageFormat.format("Срок кредита: {0} мес", term));
        newTextView_name_seek_bar.setTextColor(ContextCompat.getColor(mainActivity,R.color.colorBlack));

        // наименование поля отчета ЕЖЕМЕСЯЧНЫЙ ПЛАТЕЖ:
        TextView newTextView_name_monthly_payment = new TextView(this.mainActivity);
        newTextView_name_monthly_payment.setText(MessageFormat.format("Ежемесячный платеж: {0}",
                String.format("%s ₽", String.valueOf(roundUp(monthly_payment, 2)))));
        newTextView_name_monthly_payment.setTextColor(ContextCompat.getColor(mainActivity,R.color.colorBlack));

        // наименование поля отчета РАЗМЕР ПЕРЕПЛАТЫ
        TextView newTextView_name_overpayment = new TextView(this.mainActivity);
        newTextView_name_overpayment.setText(MessageFormat.format("Размер переплаты: {0}",
                String.format("%s ₽", String.valueOf(roundUp(term * monthly_payment - sum_credit, 2)))));
        newTextView_name_overpayment.setTextColor(ContextCompat.getColor(mainActivity,R.color.colorBlack));

        new_LinerLayout.addView(newTextView_name_sum_credit);
        new_LinerLayout.addView(newTextView_name_interest_rate);
        new_LinerLayout.addView(newTextView_name_seek_bar);
        new_LinerLayout.addView(newTextView_name_monthly_payment);
        new_LinerLayout.addView(newTextView_name_overpayment);

        container_result.addView(new_LinerLayout); // передаем в contener_result новый (new_LinerLayout) строку

        Button newButton_detail = new Button(this.mainActivity); // создал кнопку
        // параметры ширины и высоту кнопки
        LinearLayout.LayoutParams layoutParam_buttons = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 45); // длина высота кнопки
        newButton_detail.setPadding(0,5,0,5);
        layoutParam_buttons.setMargins(0,10,0,5);
        newButton_detail.setGravity(Gravity.CENTER); // разместил центру относительно родителя
        newButton_detail.setText("ПОКАЗАТЬ ДЕТАЛЬНЫЙ РАСЧЕТ");
        Drawable drawable_gradient_for_button = mainActivity.getResources().getDrawable(R.drawable.gradient_2);
        newButton_detail.setBackground(drawable_gradient_for_button);
        newButton_detail.setLayoutParams(layoutParam_buttons); // присвоил параметры

        // макет кнопки с параметрами
        LinearLayout newLinearLayot_for_newButton_detail = new LinearLayout(this.mainActivity);
        newLinearLayot_for_newButton_detail.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //newLinearLayot_for_newButton_detail.setPadding(0,0,0,0);
        newLinearLayot_for_newButton_detail.setGravity(Gravity.CENTER);
        newLinearLayot_for_newButton_detail.addView(newButton_detail);

        new_LinerLayout.addView(newLinearLayot_for_newButton_detail); // определил в макете

        newButton_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // расчет по месяцам процентной и кредитной части аннуитетного платежа
                double main_debt = 0; // основной долг
                int count_month = 0; // счетчик месяцев
                double loan_balance = 0; // остаток кредита
                double percent;

                for (int i = 0; i < term; i ++) {
                    count_month ++; // счетчик месяцев
                    System.out.printf("%d месяц \n", count_month);

                    TextView newTextView_count_month = new TextView((MainActivity) mainActivity); // новый textview для percent
                    new_LinerLayout.addView(newTextView_count_month); // добавил в новый линейный макет
                    newTextView_count_month.setPadding(0,10,0,0);
                    newTextView_count_month.setText(String.format(Locale.getDefault(), count_month + " месяц"));

                    //  scrollView_main опускается вниз при нажати на кнопку рас
                    scrollView_main.post(new Runnable() {
                        public void run() {
                            scrollView_main.scrollTo(0, scrollView_main.getBottom());
                        }
                    });

                    if (i < 1) { // условие для 1 месяца
                        percent = (sum_credit - main_debt) * percent_cost; // начисленные проценты
                        main_debt = monthly_payment - percent; // основной долг
                        System.out.printf("начисленные проценты: %.2f ₽\nосновной долг: %.2f ₽\n", percent, main_debt); // вывод в консоль

                        TextView newTextView_percent = new TextView((MainActivity) mainActivity); // новый textview для percent
                        new_LinerLayout.addView(newTextView_percent); // добавил в новый линейный макет

                        TextView newTextView_main_debt = new TextView((MainActivity) mainActivity); // новый textview для main_debt
                        new_LinerLayout.addView(newTextView_main_debt); // добавил в новый линейный макет

                        newTextView_percent.setText(String.format(Locale.getDefault(),"Начисленные проценты: %.2f ₽", percent)); // вывод в textview
                        newTextView_main_debt.setText(String.format(Locale.getDefault(),"Основной долг: %.2f ₽", main_debt)); // вывод в textview
                    }

                    if (i == 1) {  // условие для 2 месяца
                        loan_balance = sum_credit - main_debt; // остаток кредита (со второй месяца)
                        System.out.printf("остаток задолженности: %.2f рублей\n", loan_balance); // вывод в консоль

                        TextView newTextView_loan_balance = new TextView((MainActivity) mainActivity);
                        new_LinerLayout.addView(newTextView_loan_balance);

                        newTextView_loan_balance.setText(String.format(Locale.getDefault(), "Остаток задолженности: %.2f ₽", loan_balance)); // вывод в textview

                        percent = (sum_credit - main_debt) * percent_cost; // начисленные проценты
                        main_debt = monthly_payment - percent; // основной долг
                        System.out.printf("начисленные проценты: %.2f рублей\nосновной долг: %.2f рублей\n", percent, main_debt); // вывод в консоль

                        TextView newTextView_percent = new TextView((MainActivity) mainActivity); // новый textview для percent
                        new_LinerLayout.addView(newTextView_percent); // добавил в новый линейный макет

                        TextView newTextView_main_debt = new TextView((MainActivity) mainActivity); // новый textview для main_debt
                        new_LinerLayout.addView(newTextView_main_debt); // добавил в новый линейный макет

                        newTextView_percent.setText(String.format(Locale.getDefault(),"Начисленные проценты: %.2f ₽", percent)); // вывод в textview
                        newTextView_main_debt.setText(String.format(Locale.getDefault(),"Основной долг: %.2f ₽", main_debt)); // вывод в textview
                    }

                    if (i >= 2) {  // условие для всех последующих месяцев
                        loan_balance = loan_balance - main_debt; // остаток кредита (со второй месяца)
                        System.out.printf("остаток задолженности: %.2f рублей\n", loan_balance);

                        TextView newTextView_loan_balance = new TextView((MainActivity) mainActivity);
                        new_LinerLayout.addView(newTextView_loan_balance);
                        newTextView_loan_balance.setText(String.format(Locale.getDefault(), "Остаток задолженности: %.2f ₽", loan_balance)); // вывод в textview

                        percent = loan_balance * percent_cost; // начисленные проценты
                        main_debt = monthly_payment - percent; // основной долг
                        System.out.printf("начисленные проценты: %.2f рублей\nосновной долг: %.2f рублей\n", percent, main_debt);

                        TextView newTextView_percent = new TextView((MainActivity) mainActivity); // новый textview для percent
                        newTextView_percent.setText(String.format(Locale.getDefault(),"Начисленные проценты: %.2f ₽", percent)); // вывод в textview
                        new_LinerLayout.addView(newTextView_percent); // добавил в новый линейный макет

                        TextView newTextView_main_debt = new TextView((MainActivity) mainActivity); // новый textview для main_debt
                        newTextView_main_debt.setText(String.format(Locale.getDefault(),"Основной долг: %.2f ₽", main_debt)); // вывод в textview
                        new_LinerLayout.addView(newTextView_main_debt); // добавил в новый линейный макет
                    }
                }
            }
        });

    }

    public void Calc_diff_credit(String edit_sum_credit, String edit_rate, int edit_term) {

        container_result.removeAllViews(); // в начале каждого запуска производвиться очистка полей (LinearLayout)

        // создаем программно макет linerlayout
        new_LinerLayout = new LinearLayout(this.mainActivity);
        LinearLayout.LayoutParams Param_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        new_LinerLayout.setLayoutParams(Param_1); // присвоили параметры для нового макета linaerlayout
        new_LinerLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textViewTitle_result = new TextView(this.mainActivity);
        textViewTitle_result.setText("РЕЗУЛЬТАТЫ РАСЧЕТА");
        textViewTitle_result.setPadding(0,15,0,15);
        textViewTitle_result.setGravity(Gravity.CENTER);
        new_LinerLayout.addView(textViewTitle_result);

        sum_credit = Double.parseDouble(edit_sum_credit);
        final double copy_sum_credit = sum_credit; // запись суммы кредита
        term = edit_term; // срок кредита (кол-во мес.)
        double rate = Double.parseDouble(edit_rate); // процентаная ставка годовых

        double main_payment = sum_credit / term; // расчет размера основного платежа

        // Определим размер выплаты за каждый месяц периода кредитования
        monthly_payment = 0;
        double sum_monthly_payment = 0;
        percent_cost = ((rate / 100) / 12);
        int count = 0;
        double accrued_interest; // начисленный процент

        System.out.println("-----------------------------");
        Log.d("ВЫПОЛНИЛСЯ", "Calc_diff_credit");
        System.out.printf("Сумма кредита: %.1f ₽ \n", sum_credit);
        System.out.printf("Ставка годовых: %.1f %% \n", rate);
        System.out.printf("Срок кредита: %.1f мес \n", term);
        System.out.println("-----------------------------");

        // наименование поля отчета СУММА КРЕДИТА:
        TextView newTextView_name_sum_credit = new TextView(this.mainActivity);
        newTextView_name_sum_credit.setText(String.format("Сумма кредита: %s ₽", String.valueOf(roundUp(sum_credit, 0))));
        newTextView_name_sum_credit.setTextColor(Color.BLACK);

        // наименование поля отчета СТАВКА ГОДОВЫХ:
        TextView newTextView_name_interest_rate = new TextView(this.mainActivity);
        newTextView_name_interest_rate.setText(MessageFormat.format("Ставка годовых: {0} %", rate));
        newTextView_name_interest_rate.setTextColor(Color.BLACK);

        // наименование поля отчета СРОК КРЕДИТА:
        TextView newTextView_name_seek_bar = new TextView(this.mainActivity);
        newTextView_name_seek_bar.setText(MessageFormat.format("Срок кредита: {0} мес", term));
        newTextView_name_seek_bar.setTextColor(Color.BLACK);

        // наименование поля отчета РАЗМЕР ПЕРЕПЛАТЫ
        TextView newTextView_name_overpayment = new TextView(this.mainActivity);
        newTextView_name_overpayment.setTextColor(Color.BLACK);

        new_LinerLayout.addView(newTextView_name_sum_credit);
        new_LinerLayout.addView(newTextView_name_interest_rate);
        new_LinerLayout.addView(newTextView_name_seek_bar);

        new_LinerLayout.addView(newTextView_name_overpayment);

        TextView textViewTitleDetail_result = new TextView(this.mainActivity);
        textViewTitleDetail_result.setText("ДЕТАЛЬНЫЙ РАСЧЕТ");
        textViewTitleDetail_result.setPadding(0,15,0,15);
        textViewTitleDetail_result.setGravity(Gravity.CENTER);
        new_LinerLayout.addView(textViewTitleDetail_result);

        for (int i = 0; i < term; i ++) {
            count++;
            monthly_payment = main_payment + (copy_sum_credit - (main_payment * i)) * percent_cost;
            sum_monthly_payment += monthly_payment;

            System.out.printf("%d месяц \nОстаток займа: %.1f ₽\nРазмер основного платежа: %.1f ₽" +
                    "\nИтоговый платеж в месяц: %.1f ₽\n", count, sum_credit, main_payment, monthly_payment );

            TextView newTextView_count = new TextView(this.mainActivity);
            newTextView_count.setText(String.format(Locale.getDefault(),"%d мес", count));
            newTextView_count.setPadding(0,10,0,0);
            newTextView_count.requestFocus(View.FOCUS_DOWN);

            TextView newTextView_sum_credit = new TextView(this.mainActivity);
            newTextView_sum_credit.setText(String.format(Locale.getDefault(),"Остаток займа: %.2f ₽", sum_credit));

            TextView newTextView_main_payment = new TextView(this.mainActivity);
            newTextView_main_payment.setText(String.format(Locale.getDefault(),"Размер основного платежа: %.2f ₽", main_payment));

            TextView newTextView_monthly_payment = new TextView(this.mainActivity);
            newTextView_monthly_payment.setText(String.format(Locale.getDefault(),"Итоговый платеж в месяц: %.2f ₽", monthly_payment));
            newTextView_monthly_payment.setTextColor(Color.BLACK);

            new_LinerLayout.addView(newTextView_count);
            new_LinerLayout.addView(newTextView_sum_credit);
            new_LinerLayout.addView(newTextView_main_payment);
            new_LinerLayout.addView(newTextView_monthly_payment);

            sum_credit -= main_payment;
            accrued_interest = monthly_payment - main_payment;
            System.out.printf("По процентам: %.2f ₽\n", accrued_interest);

            TextView newTextView_accrued_interest = new TextView(this.mainActivity);
            newTextView_accrued_interest.setText(String.format(Locale.getDefault(),"По процентам: %.2f ₽", accrued_interest));
            new_LinerLayout.addView(newTextView_accrued_interest);

        }
        System.out.printf("Размер переплаты по кредиту: %.1f ₽" , (sum_monthly_payment - copy_sum_credit));

        // для созданного ранее newTextView_name_overpayment, чтобы графически выглядело лучше
        newTextView_name_overpayment.setText(String.format("Размер переплаты по кредиту: %s ₽", String.valueOf(roundUp((sum_monthly_payment - copy_sum_credit), 0))));

        container_result.addView(new_LinerLayout);

    }

     public void Calc_annuit_hypothec(String edit_sum_credit, String edit_rate, String edit_contribution, int edit_term,  final View scrollView_main) {

        container_result.removeAllViews(); // в начале каждого запуска производвиться очистка полей (LinearLayout)

        // создаем программно макет linerlayout
        new_LinerLayout = new LinearLayout(this.mainActivity);
        LinearLayout.LayoutParams Param_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        new_LinerLayout.setLayoutParams(Param_1); // присвоили параметры для нового макета linaerlayout
        new_LinerLayout.setOrientation(LinearLayout.VERTICAL);

        sum_credit = Double.parseDouble(edit_sum_credit);
        double rate = Double.parseDouble(edit_rate); // процентаная ставка годовых
        term = edit_term; // срок кредита (кол-во мес.)
        contribution = Double.parseDouble(edit_contribution);

        final double result_sumcr_contrib = sum_credit - contribution; // разница суммы кредита и первоначального взноса

        percent_cost = ((rate / 100) / 12); // вынесеный из формулы часто повторяющийся расчет процентов

        // расчет ежемесячного платежа
        monthly_payment = result_sumcr_contrib * ((percent_cost + (percent_cost / (Math.pow((1 + percent_cost), term) - 1))));

        System.out.println("-----------------------------");
        System.out.printf("Сумма ипотечного кредита: %.1f ₽ \n", sum_credit);
        // ипотека
        System.out.printf("Первоначальный взнос: %.1f ₽ \n", contribution);
        System.out.printf("Сумма кредита с учетом первоначального взноса: %.1f ₽\n", result_sumcr_contrib);
        System.out.printf("Ставка годовых: %.1f %% \n", rate);
        System.out.printf("Срок кредита: %.1f месяцев \n", term);
        System.out.printf("Ежемесячный платеж: %.2f ₽ \n", monthly_payment);
        System.out.printf("Размер переплаты: %.2f ₽ \n", term * monthly_payment - result_sumcr_contrib);
        System.out.println("-----------------------------");

        TextView textViewTitle_result = new TextView(this.mainActivity);
        textViewTitle_result.setText("РЕЗУЛЬТАТЫ РАСЧЕТА");
        textViewTitle_result.setPadding(0,15,0,15);
        textViewTitle_result.setGravity(Gravity.CENTER);
        new_LinerLayout.addView(textViewTitle_result);

        // наименование поля отчета СУММА КРЕДИТА:
        TextView newTextView_name_sum_credit = new TextView(this.mainActivity);
        newTextView_name_sum_credit.setText(String.format("Сумма ипотечного кредита: %s ₽", String.valueOf(roundUp(sum_credit, 0))));
        newTextView_name_sum_credit.setTextColor(ContextCompat.getColor(mainActivity, R.color.colorBlack));

        // наименование поля отчета ПЕРВОНАЧАЛЬНЫЙ ВЗНОС:
        TextView newTextView_name_contribution = new TextView(this.mainActivity);
        newTextView_name_contribution.setText(String.format("Первоначальный взнос: %s ₽", String.valueOf(roundUp(contribution, 0))));
        newTextView_name_contribution.setTextColor(ContextCompat.getColor(mainActivity, R.color.colorBlack));

        // наименование поля отчета СУММА КРЕДИТА С УЧЕТОМ ПЕРВОНАЧАЛЬНОГО ВЗНОСА:
        TextView newTextView_name_result_sumcr_contrib = new TextView(this.mainActivity);
        newTextView_name_result_sumcr_contrib.setText(String.format("Сумма ипотечного кредита уменьшенная: %s ₽", String.valueOf(roundUp(result_sumcr_contrib, 0))));
        newTextView_name_result_sumcr_contrib.setTextColor(ContextCompat.getColor(mainActivity, R.color.colorBlack));

        // наименование поля отчета СТАВКА ГОДОВЫХ:
        TextView newTextView_name_interest_rate = new TextView(this.mainActivity);
        newTextView_name_interest_rate.setText(MessageFormat.format("Ставка годовых: {0} %", rate));
        newTextView_name_interest_rate.setTextColor(ContextCompat.getColor(mainActivity,R.color.colorBlack));

        // наименование поля отчета СРОК КРЕДИТА:
        TextView newTextView_name_seek_bar = new TextView(this.mainActivity);
        newTextView_name_seek_bar.setText(MessageFormat.format("Срок ипотечного кредита: {0} мес", term));
        newTextView_name_seek_bar.setTextColor(ContextCompat.getColor(mainActivity,R.color.colorBlack));

        // наименование поля отчета ЕЖЕМЕСЯЧНЫЙ ПЛАТЕЖ:
        TextView newTextView_name_monthly_payment = new TextView(this.mainActivity);
        newTextView_name_monthly_payment.setText(MessageFormat.format("Ежемесячный платеж: {0}",
                String.format("%s ₽", String.valueOf(roundUp(monthly_payment, 2)))));
        newTextView_name_monthly_payment.setTextColor(ContextCompat.getColor(mainActivity,R.color.colorBlack));

        // наименование поля отчета РАЗМЕР ПЕРЕПЛАТЫ
        TextView newTextView_name_overpayment = new TextView(this.mainActivity);
        newTextView_name_overpayment.setText(MessageFormat.format("Размер переплаты: {0}",
                String.format("%s ₽", String.valueOf(roundUp(term * monthly_payment - result_sumcr_contrib, 2)))));
        newTextView_name_overpayment.setTextColor(ContextCompat.getColor(mainActivity,R.color.colorBlack));

        new_LinerLayout.addView(newTextView_name_sum_credit);
        new_LinerLayout.addView(newTextView_name_contribution);
        new_LinerLayout.addView(newTextView_name_result_sumcr_contrib);
        new_LinerLayout.addView(newTextView_name_interest_rate);
        new_LinerLayout.addView(newTextView_name_seek_bar);
        new_LinerLayout.addView(newTextView_name_monthly_payment);
        new_LinerLayout.addView(newTextView_name_overpayment);

        container_result.addView(new_LinerLayout); // передаем в contener_result новый (new_LinerLayout) строку

        Button newButton_detail = new Button(this.mainActivity); // создал кнопку

        Drawable drawable_gradient_for_button = mainActivity.getResources().getDrawable(R.drawable.gradient_2);
        newButton_detail.setBackground(drawable_gradient_for_button);
        LinearLayout.LayoutParams layoutParam_buttons = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 45); // длина высота кнопки
         newButton_detail.setPadding(0,5,0,5);
         layoutParam_buttons.setMargins(0,10,0,5);
        newButton_detail.setGravity(Gravity.CENTER); // разместил центру относительно родителя
        newButton_detail.setText("ПОКАЗАТЬ ДЕТАЛЬНЫЙ РАСЧЕТ");
        newButton_detail.setLayoutParams(layoutParam_buttons); // присвоил параметры

        LinearLayout newLinearLayot_for_newButton_detail = new LinearLayout(this.mainActivity);
        newLinearLayot_for_newButton_detail.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        newLinearLayot_for_newButton_detail.setGravity(Gravity.CENTER);
        newLinearLayot_for_newButton_detail.addView(newButton_detail);

        new_LinerLayout.addView(newLinearLayot_for_newButton_detail); // определил в макете

        newButton_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // расчет по месяцам процентной и кредитной части аннуитетного платежа
                double main_debt = 0; // основной долг
                int count_month = 0; // счетчик месяцев
                double loan_balance = 0; // остаток кредита
                double percent;

                for (int i = 0; i < term; i ++) {
                    count_month ++; // счетчик месяцев
                    System.out.printf("%d месяц \n", count_month);

                    TextView newTextView_count_month = new TextView((MainActivity) mainActivity); // новый textview для percent
                    new_LinerLayout.addView(newTextView_count_month); // добавил в новый линейный макет
                    newTextView_count_month.setPadding(0,10,0,0);
                    newTextView_count_month.setText(String.format(Locale.getDefault(), count_month + " месяц"));

                    //  scrollView_main опускается вниз при нажати на кнопку рас
                    scrollView_main.post(new Runnable() {
                        public void run() {
                            scrollView_main.scrollTo(0, scrollView_main.getBottom());
                        }
                    });

                    if (i < 1) { // условие для 1 месяца
                        percent = (result_sumcr_contrib - main_debt) * percent_cost; // начисленные проценты
                        main_debt = monthly_payment - percent; // основной долг
                        System.out.printf("начисленные проценты: %.2f ₽\nосновной долг: %.2f ₽\n", percent, main_debt); // вывод в консоль

                        TextView newTextView_percent = new TextView((MainActivity) mainActivity); // новый textview для percent
                        new_LinerLayout.addView(newTextView_percent); // добавил в новый линейный макет

                        TextView newTextView_main_debt = new TextView((MainActivity) mainActivity); // новый textview для main_debt
                        new_LinerLayout.addView(newTextView_main_debt); // добавил в новый линейный макет

                        newTextView_percent.setText(String.format(Locale.getDefault(),"Начисленные проценты: %.2f ₽", percent)); // вывод в textview
                        newTextView_main_debt.setText(String.format(Locale.getDefault(),"Основной долг: %.2f ₽", main_debt)); // вывод в textview
                    }

                    if (i == 1) {  // условие для 2 месяца
                        loan_balance = result_sumcr_contrib - main_debt; // остаток кредита (со второй месяца)
                        System.out.printf("остаток задолженности: %.2f рублей\n", loan_balance); // вывод в консоль


                        TextView newTextView_loan_balance = new TextView((MainActivity) mainActivity);
                        new_LinerLayout.addView(newTextView_loan_balance);

                        newTextView_loan_balance.setText(String.format(Locale.getDefault(), "Остаток задолженности: %.2f ₽", loan_balance)); // вывод в textview

                        percent = (result_sumcr_contrib - main_debt) * percent_cost; // начисленные проценты
                        main_debt = monthly_payment - percent; // основной долг
                        System.out.printf("начисленные проценты: %.2f рублей\nосновной долг: %.2f рублей\n", percent, main_debt); // вывод в консоль

                        TextView newTextView_percent = new TextView((MainActivity) mainActivity); // новый textview для percent
                        new_LinerLayout.addView(newTextView_percent); // добавил в новый линейный макет

                        TextView newTextView_main_debt = new TextView((MainActivity) mainActivity); // новый textview для main_debt
                        new_LinerLayout.addView(newTextView_main_debt); // добавил в новый линейный макет

                        newTextView_percent.setText(String.format(Locale.getDefault(),"Начисленные проценты: %.2f ₽", percent)); // вывод в textview
                        newTextView_main_debt.setText(String.format(Locale.getDefault(),"Основной долг: %.2f ₽", main_debt)); // вывод в textview
                    }

                    if (i >= 2) {  // условие для всех последующих месяцев
                        loan_balance = loan_balance - main_debt; // остаток кредита (со второй месяца)
                        System.out.printf("остаток задолженности: %.2f рублей\n", loan_balance);

                        TextView newTextView_loan_balance = new TextView((MainActivity) mainActivity);
                        new_LinerLayout.addView(newTextView_loan_balance);
                        newTextView_loan_balance.setText(String.format(Locale.getDefault(), "Остаток задолженности: %.2f ₽", loan_balance)); // вывод в textview

                        percent = loan_balance * percent_cost; // начисленные проценты
                        main_debt = monthly_payment - percent; // основной долг
                        System.out.printf("начисленные проценты: %.2f рублей\nосновной долг: %.2f рублей\n", percent, main_debt);

                        TextView newTextView_percent = new TextView((MainActivity) mainActivity); // новый textview для percent
                        newTextView_percent.setText(String.format(Locale.getDefault(),"Начисленные проценты: %.2f ₽", percent)); // вывод в textview
                        new_LinerLayout.addView(newTextView_percent); // добавил в новый линейный макет

                        TextView newTextView_main_debt = new TextView((MainActivity) mainActivity); // новый textview для main_debt
                        newTextView_main_debt.setText(String.format(Locale.getDefault(),"Основной долг: %.2f ₽", main_debt)); // вывод в textview
                        new_LinerLayout.addView(newTextView_main_debt); // добавил в новый линейный макет
                    }
                }
            }
        });


    }

    public void Calc_diff_hypothec(String edit_sum_credit, String edit_rate, String edit_contribution, int edit_term ) {

        container_result.removeAllViews(); // в начале каждого запуска производвиться очистка полей (LinearLayout)

        // создаем программно макет linerlayout
        new_LinerLayout = new LinearLayout(this.mainActivity);
        LinearLayout.LayoutParams Param_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        new_LinerLayout.setLayoutParams(Param_1); // присвоили параметры для нового макета linaerlayout
        new_LinerLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textViewTitle_result = new TextView(this.mainActivity);
        textViewTitle_result.setText("РЕЗУЛЬТАТЫ РАСЧЕТА");
        textViewTitle_result.setPadding(0,15,0,15);
        textViewTitle_result.setGravity(Gravity.CENTER);
        new_LinerLayout.addView(textViewTitle_result);

        sum_credit = Double.parseDouble(edit_sum_credit);
        final double copy_sum_credit = sum_credit; // запись суммы кредита
        contribution = Double.parseDouble(edit_contribution);
        term = edit_term; // срок кредита (кол-во мес.)
        double rate = Double.parseDouble(edit_rate); // процентаная ставка годовых

        // Определим размер выплаты за каждый месяц периода кредитования
        monthly_payment = 0;
        double sum_monthly_payment = 0;
        percent_cost = ((rate / 100) / 12);
        int count = 0;
        double accrued_interest; // начисленный процент

        final double result_sumcr_contrib = sum_credit - contribution; // разница суммы кредита и первоначального взноса

        double main_payment = result_sumcr_contrib / term; // расчет размера основного платежа

        System.out.println("-----------------------------");
        Log.d("ВЫПОЛНИЛСЯ", "Calc_diff_hypothec");
        System.out.printf("Сумма ипотечного кредита: %.1f ₽ \n", sum_credit);
        System.out.printf("Первоначальный взнос: %.1f ₽ \n", contribution);
        System.out.printf("Ставка годовых: %.1f %% \n", rate);
        System.out.printf("Срок кредита: %.1f мес \n", term);
        System.out.println("-----------------------------");

        // наименование поля отчета СУММА КРЕДИТА:
        TextView newTextView_name_sum_credit = new TextView(this.mainActivity);
        newTextView_name_sum_credit.setText(String.format("Сумма ипотечного кредита: %s ₽", String.valueOf(roundUp(sum_credit, 0))));
        newTextView_name_sum_credit.setTextColor(Color.BLACK);

        // наименование поля отчета ПЕРВОНАЧАЛЬНЫЙ ВЗНОС:
        TextView newTextView_name_contribution = new TextView(this.mainActivity);
        newTextView_name_contribution.setText(String.format("Первоначальный взнос: %s ₽", String.valueOf(roundUp(contribution, 0))));
        newTextView_name_contribution.setTextColor(ContextCompat.getColor(mainActivity, R.color.colorBlack));

        // наименование поля отчета СУММА КРЕДИТА С УЧЕТОМ ПЕРВОНАЧАЛЬНОГО ВЗНОСА:
        TextView newTextView_name_result_sumcr_contrib = new TextView(this.mainActivity);
        newTextView_name_result_sumcr_contrib.setText(String.format("Сумма ипотечного кредита уменьшенная: %s ₽", String.valueOf(roundUp(result_sumcr_contrib, 0))));
        newTextView_name_result_sumcr_contrib.setTextColor(ContextCompat.getColor(mainActivity, R.color.colorBlack));

        // наименование поля отчета СТАВКА ГОДОВЫХ:
        TextView newTextView_name_interest_rate = new TextView(this.mainActivity);
        newTextView_name_interest_rate.setText(MessageFormat.format("Ставка годовых: {0} %", rate));
        newTextView_name_interest_rate.setTextColor(Color.BLACK);

        // наименование поля отчета СРОК КРЕДИТА:
        TextView newTextView_name_seek_bar = new TextView(this.mainActivity);
        newTextView_name_seek_bar.setText(MessageFormat.format("Срок ипотечного кредита: {0} мес", term));
        newTextView_name_seek_bar.setTextColor(Color.BLACK);

        // наименование поля отчета РАЗМЕР ПЕРЕПЛАТЫ
        TextView newTextView_name_overpayment = new TextView(this.mainActivity);
        newTextView_name_overpayment.setTextColor(Color.BLACK);

        new_LinerLayout.addView(newTextView_name_sum_credit);
        new_LinerLayout.addView(newTextView_name_contribution);
        new_LinerLayout.addView(newTextView_name_result_sumcr_contrib);
        new_LinerLayout.addView(newTextView_name_interest_rate);
        new_LinerLayout.addView(newTextView_name_seek_bar);
//        new_LinerLayout.addView(newTextView_name_monthly_payment);
        new_LinerLayout.addView(newTextView_name_overpayment);

        TextView textViewTitleDetail_result = new TextView(this.mainActivity);
        textViewTitleDetail_result.setText("ДЕТАЛЬНЫЙ РАСЧЕТ");
        textViewTitleDetail_result.setPadding(0,15,0,15);
        textViewTitleDetail_result.setGravity(Gravity.CENTER);
        new_LinerLayout.addView(textViewTitleDetail_result);

        for (int i = 0; i < term; i ++) {
            count++;
            monthly_payment = main_payment + (result_sumcr_contrib - (main_payment * i)) * percent_cost;
            sum_monthly_payment += monthly_payment;

            System.out.printf("%d месяц \n", count);
            System.out.printf("Остаток займа: %.1f ₽\n", sum_credit);
            System.out.printf("Размер основного платежа: %.1f ₽ \n", main_payment);
            System.out.printf("Итоговый платеж в месяц: %.1f ₽\n", monthly_payment);

            TextView newTextView_count = new TextView(this.mainActivity);
            newTextView_count.setText(String.format(Locale.getDefault(),"%d мес", count));
            newTextView_count.setPadding(0,10,0,0);
            newTextView_count.requestFocus(View.FOCUS_DOWN);

            TextView newTextView_sum_credit = new TextView(this.mainActivity);
            newTextView_sum_credit.setText(String.format(Locale.getDefault(),"Остаток займа: %.2f ₽", sum_credit));

            TextView newTextView_main_payment = new TextView(this.mainActivity);
            newTextView_main_payment.setText(String.format(Locale.getDefault(),"Размер основного платежа: %.2f ₽", main_payment));

            TextView newTextView_monthly_payment = new TextView(this.mainActivity);
            newTextView_monthly_payment.setText(String.format(Locale.getDefault(),"Итоговый платеж в месяц: %.2f ₽", monthly_payment));
            newTextView_monthly_payment.setTextColor(Color.BLACK);

            new_LinerLayout.addView(newTextView_count);
            new_LinerLayout.addView(newTextView_sum_credit);
            new_LinerLayout.addView(newTextView_main_payment);
            new_LinerLayout.addView(newTextView_monthly_payment);

            sum_credit -= main_payment;
            accrued_interest = monthly_payment - main_payment;
            System.out.printf("По процентам: %.2f ₽\n", accrued_interest);

            TextView newTextView_accrued_interest = new TextView(this.mainActivity);
            newTextView_accrued_interest.setText(String.format(Locale.getDefault(),"По процентам: %.2f ₽", accrued_interest));
            new_LinerLayout.addView(newTextView_accrued_interest);

        }
        System.out.printf("Размер переплаты по кредиту: %.1f ₽" , (sum_monthly_payment - result_sumcr_contrib));

        // для созданного ранее newTextView_name_overpayment, чтобы графически выглядело лучше
        newTextView_name_overpayment.setText(String.format("Размер переплаты по кредиту: %s ₽", String.valueOf(roundUp((sum_monthly_payment - result_sumcr_contrib), 0))));

        container_result.addView(new_LinerLayout);
    }

}

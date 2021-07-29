package com.dev_marinov.calculation_credit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private ImageView imageView_help_1;
    private ImageView imageView_help_2;

    RadioButton radioButton_Credit; // выбор кредита
    RadioButton radioButton_Hypothec; // выбор ипотеки
    EditText editText_sum_credit; // поле ввода суммы кредита
    TextView textView_contribution;
    EditText editText_contribution; // поле ввода первоначального взноса
    EditText editText_interest_rate; // поле ввода процентной ставки
    TextView textView_value_seeBar; // читает прогресс у seekBar
    SeekBar seekBar; // бегунок seekBar
    RadioButton radioButton_Annuit; // выбор аннуитентного платежа
    RadioButton radioButton_Diff; // выбор дифференцированного платежа

    Button button_clear; // кнопка очистить
    Clear clear; // класс для очистки полей
    Button button_calc; // кнопка рассчитать
    CheckedAndStartCalc checkedAndStartCalc; // класс для проверки на не заполненые местаи старт метода calc

    int error; // переменная для проврки ошибки на постуые поля
    TextView error_sum_credit; // невидимое поле проверки ввода суммы кредита
    TextView error_contribution; // невидимое поле проверки ввода первоначальной суммы
    TextView error_interest_rate; // невидомое поле провекри ввода процентной ставки
    TextView error_indicate_seek_bar; // невидимое поле проверки ввода срока кредита
    LinearLayout container_result ; // контейнер с результатами
    Calculation cl_calculation; // класс для расчета кредита и опотеки
    ScrollView scrollView_main;
    TextView text_view_Mes; // текст "мес" у бегунка

    int radioCheck_credit_or_ipoteka = 0; // 0 - кредит, 1 - ипотека






    TextView textView_sum_credit; // переменная нужна только для получения default color textview, т.к. найти по другому код цвета не мог



    // метод инициализации всех объектов и переменных
    public void init() {

        // два imageView_help для создания AlertsDialog
        imageView_help_1 = (ImageView) findViewById(R.id.imageView_help_1);
        imageView_help_2 = (ImageView) findViewById(R.id.imageView_help_2);

        // создание SeekBar и передача его значения  в textView
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        // определение textView_value_seeBar и установка в стартовом положении значение ноль
        textView_value_seeBar = (TextView) findViewById(R.id.textView_value_seeBar);
        textView_value_seeBar.setText("0");

        // нашел видимые элементы в макете
        radioButton_Credit = (RadioButton) findViewById(R.id.radioButton_Credit);
        radioButton_Hypothec = (RadioButton) findViewById(R.id.radioButton_Hypothec);
        editText_sum_credit = (EditText) findViewById(R.id.editText_sum_credit);
        textView_contribution = (TextView) findViewById(R.id.textView_contribution);
        editText_contribution = (EditText) findViewById(R.id.editText_contribution);
        editText_interest_rate = (EditText) findViewById(R.id.editText_interest_rate);
        radioButton_Annuit = (RadioButton) findViewById(R.id.radioButton_Annuit);
        radioButton_Diff = (RadioButton) findViewById(R.id.radioButton_Diff);

        // нашел невидимые элементы в макете
        error_sum_credit = (TextView) findViewById(R.id.error_sum_credit);
        error_contribution = (TextView) findViewById(R.id.error_contribution);
        error_interest_rate = (TextView) findViewById(R.id.error_interest_rate);
        error_indicate_seek_bar = (TextView) findViewById(R.id.error_seek_bar);
        scrollView_main = (ScrollView) findViewById(R.id.scrollView_main);
        text_view_Mes = (TextView) findViewById(R.id.text_view_Mes);
        // макет для отображения результатов расчета
        container_result = (LinearLayout) findViewById(R.id.container_result);
        //
        cl_calculation = new Calculation(this, container_result);

        // для класса Clear
        button_clear = (Button) findViewById(R.id.button_clear);
        clear = new Clear(MainActivity.this);
        // для класса CheckedAndStartCalc
        button_calc = (Button) findViewById(R.id.button_calc);

        // переменная нужна только для получения default color textview, т.к. найти по другому код цвета не мог
        textView_sum_credit = findViewById(R.id.textView_sum_credit);

        checkedAndStartCalc = new CheckedAndStartCalc (MainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("test","onStart");
        // проверка инциализации приложения. Если польз долго не польз приложением (свернул)
        // и все переменные будут иметь null,т.к. андроид завершил процессы принудительно,
        // то при разворачивании заново запускается через onStart метод init
        if (container_result == null) {
            init();
        }


// переменная нужна только для получения default color textview, т.к. найти по другому код цвета не мог
        ColorStateList colorStateList_default_color_text = textView_sum_credit.getTextColors();

// ЧТОБЫ ОБЕ КНОПКИ RADIOBUTTON КРЕДИТ ИЛИ ИПОТЕКА БЫЛИ БЕЛЫМ ИЛИ ЧЕРНЫМ ЦВЕТОМ ПРИ САМОМ ПЕРВОМ СТАРТЕ
        if (radioButton_Credit.isChecked()) {
            radioButton_Credit.setTextColor(getResources().getColor(android.R.color.white));
            radioButton_Credit.setShadowLayer(1,1,1, Color.BLACK);

            // иначе кружок у радиобаттон аннуит активный цвет, а у дифф неактивный
            int colorCredit = getResources().getColor(R.color.colorWhite);
            ColorStateList colorStateList_Credit_on = ColorStateList.valueOf(colorCredit);
            radioButton_Credit.setButtonTintList(colorStateList_Credit_on);

            radioButton_Hypothec.setTextColor(colorStateList_default_color_text);
            radioButton_Hypothec.setShadowLayer(0,0,0,0);
            radioButton_Hypothec.setButtonTintList(colorStateList_default_color_text);
        }
        else {
            radioButton_Credit.setTextColor(colorStateList_default_color_text);
            radioButton_Credit.setShadowLayer(0,0,0,0);
            radioButton_Credit.setButtonTintList(colorStateList_default_color_text);

            radioButton_Hypothec.setTextColor(getResources().getColor(android.R.color.white));
            radioButton_Hypothec.setShadowLayer(1,1,1, Color.BLACK);

            // иначе кружок у радиобаттон аннуит активный цвет, а у дифф неактивный
            int colorHypothec = getResources().getColor(R.color.colorWhite);
            ColorStateList colorStateList_Hypothec_on = ColorStateList.valueOf(colorHypothec);
            radioButton_Hypothec.setButtonTintList(colorStateList_Hypothec_on);
        }

// ЧТОБЫ ОБЕ КНОПКИ RADIOBUTTON ANNUIT ИЛИ DIFF БЫЛИ БЕЛЫМ ИЛИ ЧЕРНЫМ ЦВЕТОМ ПРИ САМОМ ПЕРВОМ СТАРТЕ
        if (radioButton_Annuit.isChecked()) {
            radioButton_Annuit.setTextColor(getResources().getColor(android.R.color.white));
            radioButton_Annuit.setShadowLayer(1,1,1,Color.BLACK);

            // иначе кружок у радиобаттон аннуит активный цвет, а у дифф неактивный
            int colorAnnuit = getResources().getColor(R.color.colorWhite);
            ColorStateList colorStateList_Annuit_on = ColorStateList.valueOf(colorAnnuit);
            radioButton_Annuit.setButtonTintList(colorStateList_Annuit_on);

            radioButton_Diff.setTextColor(colorStateList_default_color_text);
            radioButton_Diff.setShadowLayer(0,0,0,0);
            radioButton_Diff.setButtonTintList(colorStateList_default_color_text);
        }
        else {
            radioButton_Diff.setTextColor(getResources().getColor(android.R.color.white));
            radioButton_Diff.setShadowLayer(1,1,1, Color.BLACK);

            // иначе кружок у радиобаттон дифф активный цвет, а у аннуит серый неактивный
            int colorDiff = getResources().getColor(R.color.colorWhite);
            ColorStateList colorStateList_Diff_on = ColorStateList.valueOf(colorDiff);
            radioButton_Diff.setButtonTintList(colorStateList_Diff_on);

            radioButton_Annuit.setTextColor(colorStateList_default_color_text);
            radioButton_Annuit.setShadowLayer(0,0,0,0);
            radioButton_Annuit.setButtonTintList(colorStateList_default_color_text);
        }

// установка серого цвета для edittext-ов
        editText_contribution.setBackgroundTintList(colorStateList_default_color_text);
       //editText_contribution.setCursorColor
        editText_interest_rate.setBackgroundTintList(colorStateList_default_color_text);
  //      editText_interest_rate.setTextColor(colorStateList_default_color_text);
        editText_sum_credit.setBackgroundTintList(colorStateList_default_color_text);

       // seekBar.setTe(getResources().getolor(R.color.colorWhite));
    }

    // сохранение данных
    @Override
    protected void onStop() {
        super.onStop();
        Log.e("test","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("test","onDestroy");
    }


    // ВОПРОС. ПОЧЕМУ НАДО ЗАМЕНИТЬ PROTECTED НА PUBLIC. ДЛЯ КОГО НУЖЕН ДОСТУП?
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        Drawable drawable_gradient = getResources().getDrawable(R.drawable.gradient);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.black));
        window.setBackgroundDrawable(drawable_gradient);



        init();
        // переменная нужна только для получения default color textview, т.к. найти по другому код цвета не мог
        final ColorStateList colorStateList_color_default_text = textView_sum_credit.getTextColors();

        // при изменении позиции radiobutton кредит или ипотека изменение цвета текста
        radioButton_Credit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) // выбран кредит
                {
                    textView_contribution.setVisibility(View.GONE); // невидимость текста первоначальный взнос
                    editText_contribution.setVisibility(View.GONE); // невидимость поля ввода первоначальный взнос
                    radioCheck_credit_or_ipoteka = 0;

                    radioButton_Credit.setTextColor(getResources().getColor(android.R.color.white)); // устновить цвет белый (активный)
                    radioButton_Credit.setShadowLayer(1,1,1, Color.BLACK); // установить тень (активный)

                    radioButton_Hypothec.setTextColor(colorStateList_color_default_text); // усатновить цвет по умолчанию (не активный цвет)
                    radioButton_Hypothec.setShadowLayer(0,0,0,0); // отменить тень у радио баттон ипотека

                    // иначе кружок у радиобаттон кредит активный цвет, а у ипотека неактивный
                    int colorCredit = getResources().getColor(R.color.colorWhite);
                    ColorStateList colorStateList_Credit_on = ColorStateList.valueOf(colorCredit);
                    radioButton_Credit.setButtonTintList(colorStateList_Credit_on);

                    radioButton_Hypothec.setButtonTintList(colorStateList_color_default_text);

                } else { // иначе выбрана ипотека
                    textView_contribution.setVisibility(View.VISIBLE); // видимость текста первоначальный взнос
                    editText_contribution.setVisibility(View.VISIBLE); // видимость поля ввода первоначальный взнос
                    radioCheck_credit_or_ipoteka = 1;

                    radioButton_Credit.setTextColor(colorStateList_color_default_text);  // усатновить цвет по умолчанию (не активный цвет)
                    radioButton_Credit.setShadowLayer(0,0,0,0);  // отменить тень у радио баттон кредит

                    radioButton_Hypothec.setTextColor(getResources().getColor(android.R.color.white)); // устновить цвет белый (активный)
                    radioButton_Hypothec.setShadowLayer(1,1,1, Color.BLACK); // установить тень (активный)

                    // иначе кружок у радиобаттон аннуит активный цвет, а у дифф неактивный
                    int colorHypothec = getResources().getColor(R.color.colorWhite);
                    ColorStateList colorStateList_Hypothec_on = ColorStateList.valueOf(colorHypothec);
                    radioButton_Hypothec.setButtonTintList(colorStateList_Hypothec_on);

                    radioButton_Credit.setButtonTintList(colorStateList_color_default_text);
                }
            }
        });

        // при изменении позиции radiobutton аннуитентные или дифференцированные изменение цвета текста
        radioButton_Annuit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // иначе текст у радиобаттон аннуит активный белый цвет, а у дифф неактивный
                    radioButton_Annuit.setTextColor(getResources().getColor(android.R.color.white));
                    radioButton_Annuit.setShadowLayer(1,1,1, Color.BLACK);

                    radioButton_Diff.setTextColor(colorStateList_color_default_text);
                    radioButton_Diff.setShadowLayer(0,0,0,0);

                    // иначе кружок у радиобаттон аннуит активный цвет, а у дифф неактивный
                    int colorAnnuit = getResources().getColor(R.color.colorWhite);
                    ColorStateList colorStateList_Annuit_on = ColorStateList.valueOf(colorAnnuit);
                    radioButton_Annuit.setButtonTintList(colorStateList_Annuit_on);

                    radioButton_Diff.setButtonTintList(colorStateList_color_default_text);

                }
                else {
                    // иначе текст у радиобаттон дифф активный белый цвет , у аннуит серый неактивный
                    radioButton_Diff.setTextColor(getResources().getColor(android.R.color.white));
                    radioButton_Diff.setShadowLayer(1,1,1,Color.BLACK);

                    radioButton_Annuit.setTextColor(colorStateList_color_default_text);
                    radioButton_Annuit.setShadowLayer(0,0,0,0);

                    // иначе кружок у радиобаттон дифф активный цвет, а у аннуит серый неактивный
                    int colorDiff = getResources().getColor(R.color.colorWhite);
                    ColorStateList colorStateList_Diff_on = ColorStateList.valueOf(colorDiff);
                    radioButton_Diff.setButtonTintList(colorStateList_Diff_on);

                    radioButton_Annuit.setButtonTintList(colorStateList_color_default_text);
                }
            }
        });



        // метод onClick для нескольких объектов
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_clear: clear.Button_clear(); break;
                    case R.id.button_calc: checkedAndStartCalc.CheckedAndStartCalc_method(); break;
                    case R.id.imageView_help_1: createImageAlertsDialog(R.string.click_image_txt_1); break;
                    case R.id.imageView_help_2: createImageAlertsDialog(R.string.click_image_txt_2); break;
                }
            }
        };
        // назначаем каждому объекту обработчик onClickListener
        button_clear.setOnClickListener(onClickListener);
        button_calc.setOnClickListener(onClickListener);
        imageView_help_1.setOnClickListener(onClickListener);
        imageView_help_2.setOnClickListener(onClickListener);

    }

        // метод для создания диалогового окна imageView_help_1 или imageView_help_2
    public void createImageAlertsDialog (int content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(content);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }

        // Вызывается, когда для текущего окна включен или отключен захват указателя.
        @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        }

    // три метода seekBar
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textView_value_seeBar.setTextColor(getResources().getColor(android.R.color.white));
        textView_value_seeBar.setShadowLayer(1,1,1, Color.BLACK);
        textView_value_seeBar.setText("" + progress); // показать прогресс seek bar во время перемещения
        text_view_Mes.setTextColor(getResources().getColor(android.R.color.white));
        text_view_Mes.setShadowLayer(1,1,1, Color.BLACK);



    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        textView_value_seeBar.setText(String.valueOf(seekBar.getProgress())); // передача данных во время остановки
    }

}

package ge.idevelopers.consumption;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    private TextView fuel_text;
    private TextView  distance ;
    private Button lang;
    private Button calculate;
    private TextView rate;
    private LanguageFragment fragment;
    private rezult_fragment frezult;
    private View v;
    private Typeface typeface;
    private LinearLayout main;
    private Button calc;
    private View line1;
    private View line2;
    private View line3;
    private LinearLayout first_linear;
    private LinearLayout second_linear;
    private boolean animation;
    private RelativeLayout text;
    private RelativeLayout rezult;
    private boolean is_shown;
    private EditText edit1;
    private EditText edit2;
    private Calculate cal;
    public static String answar;
    private boolean back;
    private int c=0;
    private Locale loc;
    private boolean is_first=false;
    private boolean rate_first=false;
    private InputMethodManager imm;
    private KeyListener originalKeyListener;
    InterstitialAd mInterstitialAd;
    boolean showAd=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        prefs = getSharedPreferences("ge.idevelopers.consumption", MODE_PRIVATE);


        c = prefs.getInt("numRun",0);
        if(c%2==0)
            showAd=true;
        c++;
        prefs.edit().putInt("numRun",c).commit();

        setLocal(prefs.getString("Language",""));

        setContentView(R.layout.activity_main);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        final AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });


        requestNewInterstitial();



        imm  = (InputMethodManager) getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
        typeface=Typeface.createFromAsset(getAssets(), "fonts/gugeshashvili_5_mthavruli.ttf");
        fuel_text = (TextView) findViewById(R.id.fuel_text);
        distance=(TextView) findViewById(R.id.fuel_text2);
        calculate=(Button)findViewById(R.id.calculate);
        lang=(Button)findViewById(R.id.lang);
        rate=(TextView)findViewById(R.id.rate);
        fragment=new LanguageFragment();
        frezult=new rezult_fragment();
        main=(LinearLayout)findViewById(R.id.main_layout);
        calc=(Button)findViewById(R.id.calculate);
        line1=(View)findViewById(R.id.line_1);
        line2=(View)findViewById(R.id.line_2);
        line3=(View)findViewById(R.id.line_3);
        first_linear=(LinearLayout)findViewById(R.id.first_line);
        second_linear=(LinearLayout)findViewById(R.id.second_line);
        animation=false;
        is_shown=false;
        back=false;


        cal=new Calculate();

        answar="";

        edit1=(EditText)findViewById(R.id.fuel_enter_text);
        edit2=(EditText)findViewById(R.id.fuel_enter_text2);


        edit2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return false;
            }
        });



        // *********fonts********
        distance.setTypeface(typeface);
        fuel_text.setTypeface(typeface);
        lang.setTypeface(typeface);
        calculate.setTypeface(typeface);
        rate.setTypeface(typeface);



        if(c==15)
        {


            c=0;
            prefs.edit().putInt("numRun",c).commit();
            alert();
        }



        //////  ********calculate********   \\\\\\\\

        calc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {




                    if(edit1.getText().toString().isEmpty() || edit2.getText().toString().isEmpty())
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setTitle(R.string.alert_empty_title);
                    builder1.setMessage(R.string.alert_empty_text);
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            R.string.eccept,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                }
                            });



                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
                else {
                    if(edit1.getText().toString().equals("0") || edit2.getText().toString().equals("0"))
                    {

                    }
                    else {
                        calculate();
                        animation = true;
                    }
                }
            }
        });

        main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return false;
            }
        });


    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (prefs.getBoolean("firstrun", true)) {

            setLocal("ka");
            lang.setVisibility(View.GONE);
            rate.setVisibility(View.GONE);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            rate_first=true;
            is_first=true;
            showFragment(v);

        }
       else {

            edit1.requestFocus();

//            mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (hasFocus) {
//                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.showSoftInput(mEditText, InputMethodManager.SHOW_IMPLICIT);
//                    }
//                }
//            });

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
            //imm.showSoftInputFromInputMethod(edit1.getWindowToken(),InputMethodManager.SHOW_FORCED);

        }



    }


    public void setLocal(String language) {
        loc=new Locale(language);
        Locale.setDefault(loc);
        Resources res=getResources();
        DisplayMetrics dm=res.getDisplayMetrics();
        Configuration conf=res.getConfiguration();
        conf.locale=loc;
        res.updateConfiguration(conf,dm);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Language", language);
        editor.commit();

    }




    public void calculate()
    {

        if(back==false)

        {

            if (!edit1.getText().toString().isEmpty())
                cal.setFuel(Double.parseDouble(edit1.getText().toString()));
            else
                cal.setFuel(0);
            if (!edit2.getText().toString().isEmpty())
                cal.setDistance(Double.parseDouble(edit2.getText().toString()));
            else
                cal.setDistance(0);
            if (cal.getFuel() != 0 && cal.getDistance() != 0) {
                answar = (String.valueOf(cal.answar()));

            }

            Animation move_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_down);
            //Animation move_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_down);
            AlphaAnimation hide = new AlphaAnimation(1f, 0f);
            hide.setDuration(1200);
            hide.setFillAfter(true);

            ////*****fragment****
            v = (View) findViewById(R.id.fConteiner);
            v.setVisibility(View.VISIBLE);


            getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.fade_in, 0, 0, R.anim.fade_out)
                    .add(R.id.fConteiner, frezult).commit();


            first_linear.startAnimation(hide);
            second_linear.startAnimation(hide);

            line2.startAnimation(hide);


            line1.startAnimation(move_down);
            line3.startAnimation(move_down);

            calc.setText(R.string.back);
            lang.setClickable(false);
            lang.setEnabled(false);
            edit1.setClickable(false);
            edit1.setEnabled(false);
            edit2.setClickable(false);
            edit2.setEnabled(false);

            back=true;

            lang.setVisibility(View.INVISIBLE);
        }
        else
        {
            if(showAd) {
                if (mInterstitialAd.isLoaded())
                    mInterstitialAd.show();
                    showAd=false;
            }
            onBackPressed();


        }

    }

    @Override
    public void onBackPressed() {
        if(is_first)
        {
            finish();
        }
        else
        if(animation==true)
        {

            Animation move_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_up);
            AlphaAnimation hide = new AlphaAnimation(0f, 1f);
            hide.setDuration(1200);
            hide.setFillAfter(true);

            first_linear.startAnimation(hide);
            second_linear.startAnimation(hide);
            line2.startAnimation(hide);


            line1.startAnimation(move_down);
            line3.startAnimation(move_down);

            animation=false;
            calc.setText(R.string.calculate);
            lang.setClickable(true);
            lang.setEnabled(true);
            edit1.setClickable(true);
            edit1.setEnabled(true);
            edit2.setClickable(true);
            edit2.setEnabled(true);

            cal.setFuel(0);
            cal.setDistance(0);

            edit1.setText("");
            edit2.setText("");

            back=false;

            animation=false;
            lang.setVisibility(View.VISIBLE);
            super.onBackPressed();

        }
        else {
            AlphaAnimation animation1 = new AlphaAnimation(0.7f, 1f);
            animation1.setDuration(1200);
            animation1.setFillAfter(true);
            main.startAnimation(animation1);


            AlphaAnimation animation2 = new AlphaAnimation(1f, 0f);
            animation2.setDuration(1200);
            animation2.setFillAfter(true);
            if(rate_first)
            {
                rate_first=false;
            }
            else
            rate.startAnimation(animation2);

            lang.setClickable(true);
            lang.setEnabled(true);
            edit1.setClickable(true);
            edit1.setEnabled(true);
            edit2.setClickable(true);
            edit2.setEnabled(true);
            calculate.setClickable(true);
            calculate.setEnabled(true);
            animation=false;
            super.onBackPressed();
        }
    }

    public void showFragment(View v)
    {
        v=(View)findViewById(R.id.fConteiner);
        v.setVisibility(View.VISIBLE);

        animation=false;

        getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.slide_up, 0, 0, R.anim.slide_down)
                .add(R.id.fConteiner, fragment).commit();

        AlphaAnimation animation1 = new AlphaAnimation(1f, 0.7f);
        animation1.setDuration(1200);
        animation1.setFillAfter(true);
        main.startAnimation(animation1);


        AlphaAnimation animation2 = new AlphaAnimation(0f, 1f);
        animation2.setDuration(1200);
        animation2.setFillAfter(true);
        if (rate_first)
        {

        }
        else
        rate.startAnimation(animation2);

        lang.setClickable(false);
        lang.setEnabled(false);
        edit1.setClickable(false);
        edit1.setEnabled(false);
        edit2.setClickable(false);
        edit2.setEnabled(false);
        calculate.setClickable(false);
        calculate.setEnabled(false);



    }

    public void alert()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setTitle(R.string.alert_title);
        builder1.setMessage(R.string.alert_text);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                R.string.eccept,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // open play *************store************



                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        }
                        catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                        }






                    }
                });

        builder1.setNegativeButton(
                R.string.later,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void playStore(View v)
    {
        // open play *************store************


        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        }
        catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }

    }

    public  void onImageClicked(View v)
    {
        prefs.edit().putBoolean("firstrun", false).commit();
        if(is_first)
        {
            lang.setVisibility(View.VISIBLE);
            rate.setVisibility(View.INVISIBLE);
        }

        switch (v.getId())
        {
            case R.id.eng:
                //// change first pahe language
                setLocal("en");
                fuel_text.setText("INDICATE QUANTITY OF FUEL");
                distance.setText("INDICATE PASSED DISTANCE");
                calculate.setText("CALCULATE");
                lang.setText("English");
                rate.setText("Rate Us");
                animation=false;
                is_first=false;
                onBackPressed();




                break;
            case R.id.geo:

                setLocal("ka");
                fuel_text.setText("მიუთითეთ საწვავის რაოდენობა");
                distance.setText("მიუთითეთ გავლილი მანძილი");
                calculate.setText("დათვლა");
                lang.setText("ქართული");
                rate.setText("შეგვაფასეთ");
                animation=false;
                is_first=false;
                onBackPressed();



                break;
            case R.id.rus:
                setLocal("ru");
                fuel_text.setText("УКАЖИТЕ ОБЬЕМ ТОПЛИВА");
                distance.setText("УКАЖИТЕ ПРОЙДЕННОЕ РОССТОЯНИЕ");
                calculate.setText("РАСЧИТАТЬ");
                lang.setText("РУССКИЙ");
                rate.setText("Оцените Нас");
                animation=false;
                is_first=false;
                onBackPressed();





                break;
        }


    }

}

package ge.idevelopers.consumption;


import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class rezult_fragment extends Fragment {
    TextView text;
    TextView rezult;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View v = inflater.inflate(R.layout.rezult_fragment, container, false);

        Typeface typeface= Typeface.createFromAsset(getContext().getAssets(), "fonts/gugeshashvili_5_mthavruli.ttf");
        text=(TextView) v.findViewById(R.id.rezult_text);
        rezult=(TextView) v.findViewById(R.id.rezult);

        text.setTypeface(typeface);

        rezult.setTypeface(typeface);

        rezult.setText(MainActivity.answar);


        return v;

    }



}
package ge.idevelopers.consumption;


import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LanguageFragment extends Fragment {
    TextView geo;
    TextView eng;
    TextView rus;

//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View v = inflater.inflate(R.layout.fragment_language, container, false);

        Typeface typeface= Typeface.createFromAsset(getContext().getAssets(), "fonts/gugeshashvili_5_mthavruli.ttf");
        geo=(TextView) v.findViewById(R.id.geo);
        eng=(TextView) v.findViewById(R.id.eng);
        rus=(TextView) v.findViewById(R.id.rus);
        geo.setTypeface(typeface);
        eng.setTypeface(typeface);
        rus.setTypeface(typeface);



        return v;

    }



}
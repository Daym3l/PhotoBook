package daym3l.photobook.com.photobook.about;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import daym3l.photobook.com.photobook.R;
import uk.co.senab.photoview.PhotoView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DirectionFragment extends Fragment {


    public DirectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_direction, container, false);
        PhotoView photoView = (PhotoView) v.findViewById(R.id.image);
        photoView.setImageResource(R.drawable.direccion);
        return v;
    }

}

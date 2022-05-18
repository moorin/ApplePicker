package todo.swu.applepicker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class FragmentOCR extends Fragment {
    Button galleryBtn;
    Button cameraBtn;
    ImageView ocrImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_ocr, container, false);
        galleryBtn = (Button)myView.findViewById(R.id.galleryBtn);
        cameraBtn = (Button)myView.findViewById(R.id.cameraBtn);
        ocrImageView = (ImageView)myView.findViewById(R.id.ocrImageView);

        galleryBtn.setOnClickListener(v->{
            Toast.makeText(getActivity(), "갤러리 버튼 누름", Toast.LENGTH_SHORT).show();
        });

        cameraBtn.setOnClickListener(v->{
            Toast.makeText(getActivity(), "카메라 버튼 누름", Toast.LENGTH_SHORT).show();
        });


        return myView;
    }
}
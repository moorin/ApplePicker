package todo.swu.applepicker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;


public class FragmentOCR extends Fragment {
    Button galleryBtn;
    Button cameraBtn;
    ImageView ocrImageView;

    //갤러리 호출

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_ocr, container, false);
        galleryBtn = (Button)myView.findViewById(R.id.galleryBtn);
        cameraBtn = (Button)myView.findViewById(R.id.cameraBtn);
        ocrImageView = (ImageView)myView.findViewById(R.id.ocrImageView);

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            //Toast.makeText(getActivity(), "갤러리 버튼 누름", Toast.LENGTH_SHORT).show();
            @Override
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        cameraBtn.setOnClickListener(v->{
            Toast.makeText(getActivity(), "카메라 버튼 누름", Toast.LENGTH_SHORT).show();
        });

        // Inflate the layout for this fragment
        return myView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
        switch (requestCode) {

            // 사진업로드 이벤트
            case REQUEST_CODE:
            // 사진 선택
                if(resultCode == Activity.RESULT_OK)
                {
                    try{
                        // Image 상대경로를 가져온다
                        Uri uri = data.getData();
                        // Image의 절대경로를 가져온다
                        String imagePath = getRealPathFromURI(uri);
                        //File변수에 File을 집어넣는다
                        destFile = new File(imagePath);
                        // 이미지 전송
                        SendImage();
                    } catch(Exception e) {
                        // 대기메시지 종료
                        activity.progressDismiss();
                    }
                }
                // 사진 선택 취소
                else if(resultCode == activity.RESULT_CANCELED)
                {
                    Toast.makeText(activity, "사진 선택 취소", Toast.LENGTH_LONG).show();
                }
                break;
            }
            */

        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getActivity().getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    ocrImageView.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



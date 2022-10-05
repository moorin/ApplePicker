package todo.swu.applepicker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButtonToggleGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OcrEditActivity extends AppCompatActivity {
    MaterialButtonToggleGroup toggle_btn_group;
    ConstraintLayout bottom_sheet;
    BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_edit);

        Intent receive_intent = getIntent();

        String jsonResponse = receive_intent.getStringExtra("jsonResponse");
        String imagePath = receive_intent.getStringExtra("imagePath");
        List<String> inferTextList = new ArrayList<String>();
        Log.e("test", "json 파싱 실행 전");
        // OCR 글자들 모음 List
        inferTextList = jsonParsing(jsonResponse);
        Log.e("test", "json 파싱 실행 후");
        Log.e(imagePath.toString(), "imagePath");
        Log.e(inferTextList.toString(), "inferTextList");

        // 이미지뷰에 이미지 띄우기
        File imgFile = new  File(imagePath);

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView myImage = (ImageView) findViewById(R.id.iv_ocr_image);
            myImage.setImageBitmap(myBitmap);
        }

        toggle_btn_group = (MaterialButtonToggleGroup) findViewById(R.id.toggle_btn_group_ocr_select);
        toggle_btn_group.addOnButtonCheckedListener((toggle_btn_group, checkedId, isChecked) -> {
            if (isChecked) {
                switch (checkedId) {
                    case R.id.btn_select_Task:
                        //BottomSheet에 Task 부분에 선택한 (바운딩 박스 안의)문장이 더해지게 함
                        break;

                    case R.id.btn_select_Memo:
                        //BottomSheet에 Memo 부분에 선택한 (바운딩 박스 안의)문장이 더해지게 함
                        break;
                }
            }
        });

        bottom_sheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);


        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //bottomSheet의 상태 변화에 따라 호출할 함수 지정
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //bottomSheet의 펼쳐진 정도에 따른 동작 지정
                // slideOffset 접힘 -> 펼쳐짐: 0.0 ~ 1.0
            }
        });

    }
    public String removeChar(String str, Integer n) {
        String front = str.substring(0, n);
        String back = str.substring(n+1, str.length());
        return front + back;
    }

    private List jsonParsing(String json)
    {
        List<String> inferTextList = new ArrayList<String>();
        Log.e("in jsonParsing Test", "1");
        try{
            JSONObject imagesJsonObject = new JSONObject(json);
            JSONArray imagesArray = imagesJsonObject.getJSONArray("images");

            String imagesJson = imagesArray.toString();
            imagesJson = removeChar(imagesJson, 0);
            int len = imagesJson.length()-1;
            imagesJson = removeChar(imagesJson, len);

            JSONObject fieldsJsonObject = new JSONObject(imagesJson);
            JSONArray fieldsArray = fieldsJsonObject.getJSONArray("fields");

            for(int i=0; i<fieldsArray.length(); i++)
            {
                JSONObject fieldsObject = fieldsArray.getJSONObject(i);
                String inferText = fieldsObject.getString("inferText");

                if(inferText.length()>=2)
                    inferTextList.add(fieldsObject.getString("inferText"));
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("in jsonParsing Test", "2");
        return inferTextList;
    }
}

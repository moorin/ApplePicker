package todo.swu.applepicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    Fragment fragmentDaily;
    Fragment fragmentSNS;
    Fragment fragmentOCR;
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentDaily = new FragmentDaily();
        fragmentSNS = new FragmentSNS();
        fragmentOCR = new FragmentOCR();
        tv_title = (TextView)findViewById(R.id.tv_title);


        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentDaily).commit();
        // 초기화면 설정

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.tab_daily:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentDaily).commit();
                            tv_title.setText("Daily");
                            return true;

                        case R.id.tab_sns:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentSNS).commit();
                            tv_title.setText("SNS");
                            return true;

                        case R.id.tab_ocr:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentOCR).commit();
                            tv_title.setText("OCR");
                            return true;
                    }
                    return false;
                });
    }
}

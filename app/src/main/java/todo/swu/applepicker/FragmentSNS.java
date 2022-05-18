package todo.swu.applepicker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentSNS extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<SNSPostItem> mPostItems = new ArrayList<>();
        for (int i=0; i<5; i++) {
            mPostItems.add(new SNSPostItem("USER_NAME "+(i+1),
                    "[5/13]   과제"+(i+1)+"을 달성하셨습니다", R.drawable.sns_example_photo,
                    "친구"+(i+1)+",  친구"+(i+2)));
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        View myView = inflater.inflate(R.layout.fragment_sns, container, false);
        RecyclerView recyclerView = (RecyclerView)myView.findViewById(R.id.recyclerView_post);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        SNSPostAdapter adapter = new SNSPostAdapter(mPostItems);
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return myView;
    }
}
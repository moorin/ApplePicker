package todo.swu.applepicker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class FragmentDaily extends Fragment {
    ImageButton iButton_calendar;
    EditText edit_dDay;
    EditText edit_comment;
    EditText edit_total_time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_daily, container, false);

        iButton_calendar = (ImageButton)myView.findViewById(R.id.iButton_calendar);
        edit_dDay = (EditText)myView.findViewById(R.id.edit_dDay);
        edit_comment = (EditText)myView.findViewById(R.id.edit_comment);
        edit_total_time = (EditText)myView.findViewById(R.id.edit_total_time);

        iButton_calendar.setOnClickListener(v->{

        });

        edit_dDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                edit_dDay.setTextColor(getResources().getColor(R.color.green_text));
                edit_dDay.setBackground(null);
            }
        });

        edit_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                edit_comment.setTextColor(getResources().getColor(R.color.green_text));
                edit_comment.setBackground(null);
            }
        });

        edit_total_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                edit_total_time.setTextColor(getResources().getColor(R.color.green_text));
                edit_total_time.setBackground(null);
            }
        });

        //Task RecyclerView 시작
        //리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<TaskItem> mTaskItems = new ArrayList<>();
        for (int i=0; i<5; i++) {
            mTaskItems.add(new TaskItem("과목 "+(i+1),
                    "과제"+(i+1), R.drawable.ic_green_apple));
        }

        //Task
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView taskRecyclerView = (RecyclerView)myView.findViewById(R.id.recyclerView_task);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        TaskAdapter taskAdapter = new TaskAdapter(mTaskItems);
        taskRecyclerView.setAdapter(taskAdapter);


        //Memo RecyclerView 시작
        //리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<MemoItem> mMemoItems = new ArrayList<>();
        for (int i=0; i<2; i++) {
            mMemoItems.add(new MemoItem((i+1)+"번째 메모"));
        }

        //Memo
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView memoRecyclerView = (RecyclerView)myView.findViewById(R.id.recyclerView_memo);
        memoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        MemoAdapter memoAdapter = new MemoAdapter(mMemoItems);
        memoRecyclerView.setAdapter(memoAdapter);


        // Inflate the layout for this fragment
        return myView;
    }
}
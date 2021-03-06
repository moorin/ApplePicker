package todo.swu.applepicker;

import android.annotation.SuppressLint;
import android.os.Bundle;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FragmentDaily extends Fragment {
    ImageButton iButton_calendar;
    TextView tv_date;
    EditText edit_dDay;
    EditText edit_comment;
    EditText edit_total_time;
    EditText edit_subject;
    EditText edit_part;

    ImageButton iButton_task_add;
    ImageButton iButton_memo_add;

    FirebaseFirestore db;
    Map<String, Object> dailyMap;
    static String currentDate;

    RecyclerView taskRecyclerView;
    ArrayList<TaskItem> taskItemList;
    ArrayList<MemoItem> memoItemList;
    TaskAdapter taskAdapter;
    MemoAdapter memoAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_daily, container, false);

        //??? ????????? ???????????? ????????? ?????????.
        iButton_calendar = (ImageButton) myView.findViewById(R.id.iButton_calendar);

        tv_date = (TextView) myView.findViewById(R.id.tv_date);
        edit_dDay = (EditText) myView.findViewById(R.id.edit_dDay);
        edit_comment = (EditText) myView.findViewById(R.id.edit_comment);
        edit_total_time = (EditText) myView.findViewById(R.id.edit_total_time);
        edit_subject = (EditText) myView.findViewById(R.id.edit_subject);
        edit_part = (EditText) myView.findViewById(R.id.edit_part);

        iButton_task_add = (ImageButton) myView.findViewById(R.id.iButton_task_add);
        iButton_memo_add = (ImageButton) myView.findViewById(R.id.iButton_memo_add);

        //Access a Firestore
        db = FirebaseFirestore.getInstance();

        //Task RecyclerView??? ????????? ????????? ????????? ??????.
        taskItemList = new ArrayList<TaskItem>();
        //RecyclerView??? LinearLayoutManager ?????? ??????, ????????? ??????
        taskRecyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView_task);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskAdapter = new TaskAdapter(taskItemList);
        taskRecyclerView.setAdapter(taskAdapter);

        //Memo RecyclerView??? ????????? ????????? ????????? ??????.
        memoItemList = new ArrayList<MemoItem>();
        //RecyclerView??? LinearLayoutManager ?????? ??????, ????????? ??????.
        RecyclerView memoRecyclerView = (RecyclerView) myView.findViewById(R.id.recyclerView_memo);
        memoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        memoAdapter = new MemoAdapter(memoItemList);
        memoRecyclerView.setAdapter(memoAdapter);

        //??????????????? ???????????? ????????? ????????? ????????? ?????????.
        initFragment();

        iButton_calendar.setOnClickListener(v -> {
            DialogFragment dateFragment = new DatePickerFragment();
            dateFragment.show(getActivity().getSupportFragmentManager(), "dateFragment");
        });

        edit_dDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String field = "dDay";
                updateDB(field, edit_dDay);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                edit_dDay.setTextColor(getActivity().getResources().getColor(R.color.green_text));
                edit_dDay.setBackground(null);
            }
        });

        edit_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String field = "comment";
                updateDB(field, edit_comment);
            }
            @Override
            public void afterTextChanged(Editable editable) {
                edit_comment.setTextColor(getActivity().getResources().getColor(R.color.green_text));
                edit_comment.setBackground(null);
            }
        });

        edit_total_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String field = "totalTime";
                updateDB(field, edit_total_time);
            }
            @SuppressLint("LongLogTag")
            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("edit_total_time??? afterTextChanged ?????????.", currentDate);
                edit_total_time.setTextColor(getActivity().getResources().getColor(R.color.green_text));
                edit_total_time.setBackground(null);
            }
        });

        edit_subject.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                edit_subject.setBackground(null);
            }
        });

        edit_part.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                edit_part.setBackground(null);
            }
        });


        iButton_task_add.setOnClickListener(v -> {
            Timestamp timestamp = Timestamp.now();
            taskItemList.add(new TaskItem(timestamp, edit_subject.getText().toString(),
                edit_part.getText().toString(), false));

            addTaskDB(timestamp);
            EventChangeListener();
            Log.e(TAG, "DB??? Task ????????? => ");

            edit_subject.setText(null);
            edit_part.setText(null);
        });

        iButton_memo_add.setOnClickListener(v -> {
            // ????????????????????? SimpleTextAdapter ?????? ??????.
        });

        // Inflate the layout for this fragment
        return myView;
    } //onCreateView End.


    //??????????????? ???????????? ????????? ????????? ????????? ?????????.
    @SuppressLint("LongLogTag")
    public void initFragment() {
        //?????? ?????? ?????? date ?????????.
        Log.e("?????? 2??? ????????????", "??????");
        tv_date.setText(getCurrentDate());
        Date now = new Date();
        String dateToday = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(now);
        currentDate = dateToday;
        Log.e(" onCreateView ?????? currentDate ??????", currentDate);

        //Create field
        dailyMap = new HashMap<>();
        dailyMap.put("date", dateToday);

        //?????? ????????? ????????? ????????? ???????????? EditText 3?????? setText()??????
        db.collection("daily")
            .document(dateToday)//????????? ????????? ???????????? ????????? ?????? ??????
            .get()
            .addOnSuccessListener(snapShotData -> {
                if (snapShotData.exists()) {//????????? ????????? ????????? ???????????? ?????? ?????? ?????? data ???????????? ????????? ?????????.
                    Log.e("????????? ????????? ????????? ???????????? ?????? ?????? ?????? data ???????????? ????????? ?????????.", dateToday);
                    String dDay = (String) snapShotData.getData().get("dDay");
                    String comment = (String) snapShotData.getData().get("comment");
                    String totalTime = (String) snapShotData.getData().get("totalTime");

                    edit_dDay.setText(dDay);
                    edit_comment.setText(comment);
                    edit_total_time.setText(totalTime);
                } else {//????????? ????????? ???????????? ???????????? ?????? ??????
                    //?????? ???????????? DB??? ?????????
                    Log.e("????????? ????????? ???????????? ???????????? ?????? ??????", dateToday);
                    db.collection("daily").document(dateToday)
                        .set(dailyMap)
                        .addOnSuccessListener(documentReference -> {
                            Log.e(TAG, "DocumentSnapshot added with ID: ");
                        }).addOnFailureListener(e -> {
                            Log.e(TAG, "Error adding document", e);
                        });
                }
            }).addOnFailureListener(e -> e.printStackTrace());

        //?????? ????????? ????????? ????????? ????????? RecyclerView 2?????? setText()??????
        Query query = db.collection("daily").document(dateToday)
            .collection("task").orderBy("createdAt");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        TaskItem obj = doc.toObject(TaskItem.class);
                        taskItemList.add(obj);
                    }
                    taskAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "Recyclerview ????????? else ??????");
                }
            }
        });
    }

    @SuppressLint("LongLogTag")
    public void processDatePickerResult(String year, String month, String day, String day_of_week, String datePicked) {
        tv_date.setText(month + "/" + day + "(" + day_of_week + ")");
        currentDate = datePicked;

        //Create field
        dailyMap = new HashMap<>();
        dailyMap.put("date", datePicked);

        //Edittext 3??? ??????
        db.collection("daily")
            .document(datePicked) //????????? ????????? ???????????? ????????? ?????? ??????
            .get()
            .addOnSuccessListener(snapShotData -> {
                if (snapShotData.exists()) {//????????? ????????? ????????? ???????????? ?????? ?????? ?????? data ???????????? ????????? ?????????.
                    Log.e("????????? ????????? ????????? ???????????? ?????? ?????? ?????? data ???????????? ????????? ?????????.", currentDate);
                    String dDay = snapShotData.getString("dDay");
                    String comment = snapShotData.getString("comment");
                    String totalTime = snapShotData.getString("totalTime");

                    edit_dDay.setText(dDay);
                    edit_comment.setText(comment);
                    edit_total_time.setText(totalTime);

                } else {//????????? ????????? ???????????? ???????????? ?????? ??????
                    //?????? ???????????? DB??? ????????? ?????????
                    Log.e("????????? ????????? ???????????? ???????????? ?????? ??????", currentDate);
                    db.collection("daily").document(datePicked)
                        .set(dailyMap)
                        .addOnSuccessListener(documentReference -> {
                            Log.e(TAG, "DocumentSnapshot added with ID: ");
                        }).addOnFailureListener(e -> {
                            Log.e(TAG, "Error adding document", e);
                        });
                }
            }).addOnFailureListener(e -> e.printStackTrace());

        //Task
        taskItemList = new ArrayList<TaskItem>();
        db.collection("daily").document(datePicked)
            .collection("task").orderBy("createdAt")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.e("Firestore Error", error.getMessage());
                    }
                    for (DocumentSnapshot dc : value.getDocuments()) {
                        TaskItem obj = dc.toObject(TaskItem.class);
                        taskItemList.add(obj);
                    }
                    //taskAdapter.notifyDataSetChanged();
                    taskAdapter = new TaskAdapter(taskItemList);
                    taskRecyclerView.setAdapter(taskAdapter);
                }
            });

    }

    //EditText 3?????? ???????????? ??????????????? DB ???????????????.
    @SuppressLint("LongLogTag")
    public void updateDB(String field, EditText editText) {
        //field, editText??? 3??? ?????? ???????????? ????????????.
        db.collection("daily").document(currentDate)
            .update(field, editText.getText().toString())
            .addOnSuccessListener(documentReference -> {
                Log.e(TAG, "DocumentSnapshot updated with ID: ");
            }).addOnFailureListener(e -> {
                Log.e(TAG, "Error updating document", e);
            });
    }

    //RecyclerView Task??? ??????&???????????? ??????????????? DB ???????????????.
    @SuppressLint("LongLogTag")
    public void addTaskDB(Timestamp timestamp) {
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("createdAt", timestamp);
        taskMap.put("subject", edit_subject.getText().toString());
        taskMap.put("part", edit_part.getText().toString());
        taskMap.put("achievement", false);

        //task ????????????.
        db.collection("daily").document(currentDate)
            .collection("task")
            .document(timestamp.toString()).set(taskMap)
            .addOnSuccessListener(documentReference -> {
                Log.e(TAG, "DocumentSnapshot added with ID: ");
            }).addOnFailureListener(e -> {
                Log.e(TAG, "Error adding document", e);
            });
    }


    //?????? ?????? ??????.
    public String getCurrentDate() {
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        int dayNum = now.get(Calendar.DAY_OF_WEEK);
        //?????? ??????
        String day_of_week = "";
        switch (dayNum) {
            case 1:
                day_of_week = "???";
                break;
            case 2:
                day_of_week = "???";
                break;
            case 3:
                day_of_week = "???";
                break;
            case 4:
                day_of_week = "???";
                break;
            case 5:
                day_of_week = "???";
                break;
            case 6:
                day_of_week = "???";
                break;
            case 7:
                day_of_week = "???";
                break;
        }
        return month + "/" + day + "(" + day_of_week + ")";
    }


    public void EventChangeListener() {
        taskItemList = new ArrayList<TaskItem>();
        db.collection("daily").document(currentDate)
            .collection("task").orderBy("createdAt")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Log.e("Firestore Error", error.getMessage());
                    }
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            TaskItem obj = dc.getDocument().toObject(TaskItem.class);
                            taskItemList.add(obj);
                        }
                        taskAdapter.notifyDataSetChanged();
                    }
                }
            });
    }


}
package todo.swu.applepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

    private ArrayList<MemoItem> mMemoList = null;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    MemoAdapter(ArrayList<MemoItem> list) {
        mMemoList = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_memo_item, parent, false);
        MemoAdapter.ViewHolder vh = new MemoAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(MemoAdapter.ViewHolder holder, int position) {
        //holder.onBind(mMemoList.get(position));
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText edit_memo;


        public ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조.
            edit_memo = (EditText)itemView.findViewById(R.id.edit_memo);

        }

        void onBind(MemoItem item){
//            edit_memo.setText(item.getUser_name());
        }
    }

    public void setPostList(ArrayList<MemoItem> list){
        this.mMemoList = list;
        notifyDataSetChanged();
    }

    // getItemCount() - 전체 데이터 개수 리턴.
    @Override
    public int getItemCount() {
        return mMemoList.size();
    }
}


package todo.swu.applepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private ArrayList<TaskItem> mTaskList = null;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    TaskAdapter(ArrayList<TaskItem> list) {
        mTaskList = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_task_item, parent, false);
        TaskAdapter.ViewHolder vh = new TaskAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder, int position) {
        //holder.onBind(mTaskList.get(position));
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_apple;
        //

        public ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조.
            iv_apple = (ImageView) itemView.findViewById(R.id.iv_apple);
            //

            iv_apple.setOnClickListener(view -> {
                view.setActivated(!view.isActivated());
            });
        }

        void onBind(TaskItem item) {
//            tv_username.setText(item.getUser_name());
//            tv_comment.setText(item.getComment());
//            iv_photo.setImageResource(item.getResourceId());
//            tv_friend_list.setText(item.getFriend_list());
        }
    }

    public void setPostList(ArrayList<TaskItem> list) {
        this.mTaskList = list;
        notifyDataSetChanged();
    }

    // getItemCount() - 전체 데이터 개수 리턴.
    @Override
    public int getItemCount() {
        return mTaskList.size();
    }
}

package todo.swu.applepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class SNSPostAdapter extends RecyclerView.Adapter<SNSPostAdapter.ViewHolder> {

    private ArrayList<SNSPostItem> mPostList = null;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    SNSPostAdapter(ArrayList<SNSPostItem> list) {
        mPostList = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SNSPostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_post_item, parent, false);
        SNSPostAdapter.ViewHolder vh = new SNSPostAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SNSPostAdapter.ViewHolder holder, int position) {
       holder.onBind(mPostList.get(position));
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_username;
        TextView tv_comment;
        ImageView iv_photo;
        TextView tv_friend_list;

        public ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조.
            tv_username = (TextView)itemView.findViewById(R.id.tv_username);
            tv_comment = (TextView)itemView.findViewById(R.id.tv_comment);
            iv_photo = (ImageView)itemView.findViewById(R.id.iv_photo);
            tv_friend_list = (TextView)itemView.findViewById(R.id.tv_friend_list);
        }

        void onBind(SNSPostItem item){
            tv_username.setText(item.getUser_name());
            tv_comment.setText(item.getComment());
            iv_photo.setImageResource(item.getResourceId());
            tv_friend_list.setText(item.getFriend_list());
        }
    }

    public void setPostList(ArrayList<SNSPostItem> list){
        this.mPostList = list;
        notifyDataSetChanged();
    }

    // getItemCount() - 전체 데이터 개수 리턴.
    @Override
    public int getItemCount() {
        return mPostList.size();
    }
}

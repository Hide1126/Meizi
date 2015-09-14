package info.meizi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import info.meizi.R;
import info.meizi.bean.Content;

/**
 * Created by Mr_Wrong on 15/9/14.
 */
public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.MyViewHolder> {
    private List<Content> mDatas;
    private LayoutInflater mInflater;

    /**
     * 点击事件接口
     */
    public interface OnitemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int pisition);
    }

    private OnitemClickListener mOnitemClickListener;

    public void setOnitemClickListener(OnitemClickListener onitemClickListener) {
        this.mOnitemClickListener = onitemClickListener;
    }

    public MeiziAdapter(Context context, List<Content> mDatas) {
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.meizi_item, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int i) {
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(mDatas.get(i).getUrl(),holder.imageView);
        //如果设置回调，设置点击事件
        if (mOnitemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnitemClickListener.onItemClick(holder.itemView, pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnitemClickListener.onItemLongClick(holder.itemView, pos);
                    removeData(pos);
                    return false;
                }
            });
        }
    }

    //删除数据
    public void removeData(int pos) {
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }

    //添加数据
    public void addData(int position,Content content) {
        mDatas.add(position, content);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.iv_item);
        }
    }
}


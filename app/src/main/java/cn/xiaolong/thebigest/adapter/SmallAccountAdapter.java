package cn.xiaolong.thebigest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.xiaolong.thebigest.R;
import cn.xiaolong.thebigest.entity.AccountInfo;

/**
 * <描述功能>
 *
 * @author xiaolong 719243738@qq.com
 * @version v1.0
 * @since 2018/10/8 11:23
 */
public class SmallAccountAdapter extends RecyclerSwipeAdapter<SmallAccountAdapter.ViewHolder> {
    private List<AccountInfo> accountInfos;
    private Context mContext;
    private View.OnClickListener mOnDeleteClickListener;
    private View.OnClickListener mOnCopyClickListener;
    private View.OnClickListener mOnEditClickListener;

    public SmallAccountAdapter(Context context, List<AccountInfo> accountInfos) {
        this.mContext = context;
        if (accountInfos == null) {
            this.accountInfos = new ArrayList<>();
            return;
        }
        this.accountInfos = accountInfos;
    }


    public void setData(List<AccountInfo> accountInfos) {
        this.accountInfos.clear();
        this.accountInfos.addAll(accountInfos);
        notifyDataSetChanged();
    }

    public void addItem(AccountInfo accountInfo) {
        accountInfos.add(accountInfo);
        notifyDataSetChanged();
    }

    public void replaceItem(AccountInfo oldAccountInfo, AccountInfo newAccountInfo) {
        int index = accountInfos.indexOf(oldAccountInfo);
        accountInfos.remove(oldAccountInfo);
        accountInfos.add(index, newAccountInfo);
        notifyDataSetChanged();
    }

    public void replaceItem(int position, AccountInfo newAccountInfo) {
        accountInfos.remove(position);
        accountInfos.add(position, newAccountInfo);
        notifyDataSetChanged();
    }

    public void remove(AccountInfo accountInfo) {
        int position = accountInfos.indexOf(accountInfo);
        accountInfos.remove(accountInfo);
        notifyItemRemoved(position);
        if (position != accountInfos.size()) { // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position, accountInfos.size() - position);
        }
    }

    public List<AccountInfo> getItems() {
        return accountInfos;
    }


    public void setOnDeleteClickListener(View.OnClickListener onDeleteClickListener) {
        mOnDeleteClickListener = onDeleteClickListener;
    }

    public void setOnCopyClickListener(View.OnClickListener onCopyClickListener) {
        this.mOnCopyClickListener = onCopyClickListener;
    }

    public void setOnEditClickListener(View.OnClickListener onEditClickListener) {
        this.mOnEditClickListener = onEditClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_small_account, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(accountInfos.get(position), position);
    }

    @Override
    public int getItemCount() {
        return accountInfos.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeLayout;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQQ;
        private TextView tvDelete;
        private TextView tvCopy;
        private TextView tvEdit;
        private SwipeLayout swipeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            tvQQ = itemView.findViewById(R.id.tvQQ);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            tvEdit = itemView.findViewById(R.id.tvEdit);
            tvCopy = itemView.findViewById(R.id.tvCopy);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
        }

        public void setData(AccountInfo data, int position) {
            tvQQ.setText("QQ昵称:" + data.QQ);
            if (!data.isValid) {
                tvQQ.setTextColor(mContext.getResources().getColor(R.color.red));
            }
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            mItemManger.bindView(itemView, position);
            tvQQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!data.isValid)
                    {
                        Toast.makeText(mContext,"该账号Cookie过期，请进行编辑或者删除，避免影响正常使用",Toast.LENGTH_LONG).show();
                    }
                }
            });
            tvDelete.setOnClickListener(v -> {
                if (mOnDeleteClickListener != null) {
                    v.setTag(data);
                    mOnDeleteClickListener.onClick(v);
                }
            });

            tvCopy.setOnClickListener(v -> {
                if (mOnCopyClickListener != null) {
                    v.setTag(data);
                    mOnCopyClickListener.onClick(v);
                }
            });
            tvEdit.setOnClickListener(v -> {
                if (mOnEditClickListener != null) {
                    v.setTag(position);
                    mOnEditClickListener.onClick(v);
                }
            });

        }
    }
}

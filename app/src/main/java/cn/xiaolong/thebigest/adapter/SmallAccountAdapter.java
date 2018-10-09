package cn.xiaolong.thebigest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class SmallAccountAdapter extends RecyclerView.Adapter<SmallAccountAdapter.ViewHolder> {
    private List<AccountInfo> accountInfos;
    private Context mContext;
    private View.OnClickListener mOnDeleteClickListener;
    private View.OnClickListener mOnItemClickListener;
    private View.OnClickListener mOnGetSidClick;

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

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnGetSidClick(View.OnClickListener onItemClickListener) {
        mOnGetSidClick = onItemClickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_small_account, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(accountInfos.get(position));
    }

    @Override
    public int getItemCount() {
        return accountInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQQ;
        private TextView tvDelete;
        private TextView tvGetSid;
        private TextView tvSid;
        public ViewHolder(View itemView) {
            super(itemView);
            tvQQ = itemView.findViewById(R.id.tvQQ);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            tvGetSid = itemView.findViewById(R.id.tvGetSid);
            tvSid = itemView.findViewById(R.id.tvSid);
        }

        public void setData(AccountInfo data) {
            tvQQ.setText("QQ:"+data.QQ);
//            String sid ="".equals(data.sid)?"请获取    ":data.sid;
            tvSid.setText("sid:"+data.sid);
            if(TextUtils.isEmpty(data.sid))
            {
                tvGetSid.setText("请获取");
            }else{
                tvGetSid.setText("重新获取");
            }
            tvDelete.setOnClickListener(v -> {
                if (mOnDeleteClickListener != null) {
                    v.setTag(data);
                    mOnDeleteClickListener.onClick(v);
                }
            });
            itemView.setOnClickListener(v -> {
                if (mOnDeleteClickListener != null) {
                    v.setTag(data);
                    mOnItemClickListener.onClick(v);
                }
            });
            tvGetSid.setOnClickListener(v -> {
                if (mOnGetSidClick != null) {
                    v.setTag(data);
                    mOnGetSidClick.onClick(v);
                }
            });

        }
    }
}

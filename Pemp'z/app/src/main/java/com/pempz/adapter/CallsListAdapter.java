package com.pempz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pempz.R;
import com.pempz.model.Call;
import com.pempz.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mael FOSSO on 6/21/2016.
 */
public class CallsListAdapter extends RecyclerView.Adapter<CallsListAdapter.ViewHolder>
        implements Filterable{



    private List<Call> original_items = new ArrayList<>();
    private List<Call> filtered_items = new ArrayList<>();
    private ItemFilter mFilter = new ItemFilter();

    private final int mBackground;

    private final TypedValue mTypeValued = new TypedValue();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public CallsListAdapter(Context context, List<Call> items) {
        original_items = items;
        filtered_items = items;

        mBackground = mTypeValued.resourceId;
        ctx = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_calls_item, parent, false);
        v.setBackgroundColor(mBackground);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Call call = filtered_items.get(position);
        SimpleDateFormat date_f = new SimpleDateFormat("dd MMM yy");
        SimpleDateFormat time_f = new SimpleDateFormat("HH:mm");

        holder.name.setText(call.getContact().getName());
        holder.phone.setText(call.getContact().getPhone());
        Picasso.with(ctx).load(ctx.getResources().getIdentifier(call.getContact().getPhoto(), null, ctx.getPackageName()))
                .resize(50, 50)
                .transform(new CircleTransform())
                .into(holder.photo);


        holder.time.setText(call.getTime());

    }

    @Override
    public int getItemCount() {
        return filtered_items.size();
    }

    public Call getItem(int position) {
        return filtered_items.get(position);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // @BindView(R.id.contact_item_name)
        public TextView name;
        // @BindView(R.id.contact_item_phone)
        public TextView phone;
        // @BindView(R.id.contact_item_photo)
        public ImageView photo;

        public TextView time;

        // @BindView(R.id.lyt_parent)
        public LinearLayout lyt_parent;

        public ViewHolder(View view) {
            super(view);
            // ButterKnife.bind(this, view);

            lyt_parent = (LinearLayout) view.findViewById(R.id.lyt_parent);

            name = (TextView) view.findViewById(R.id.name);
            phone = (TextView) view.findViewById(R.id.phone);
            photo = (ImageView) view.findViewById(R.id.photo);

            time = (TextView) view.findViewById(R.id.time);
        }
    }


    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            final List<Call> list = original_items;
            final List<Call> result_list = new ArrayList<>();

            for(int i = 0; i < list.size(); i++) {
                String str_name = list.get(i).getContact().getName();
                String str_phone  = list.get(i).getContact().getPhone();
//                String str_message = list.get(i).getPempz().getMessage();

                if (str_name.toLowerCase().contains(query) ||
                        str_phone.contains(query)) {

                    Log.d("contactlistadapter", list.get(i).getContact().getName() + " --- " + list.get(i).getContact().getPhone());
                    result_list.add(list.get(i));
                }
            }

            results.values = result_list;
            results.count = result_list.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            filtered_items = (List<Call>) results.values;
            notifyDataSetChanged();
        }
    }

}

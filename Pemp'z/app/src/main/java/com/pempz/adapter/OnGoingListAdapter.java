package com.pempz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pempz.R;
import com.pempz.data.Constant;
import com.pempz.model.OnGoing;
import com.pempz.model.OnGoing;
import com.pempz.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mael FOSSO on 6/17/2016.
 */
public class OnGoingListAdapter extends RecyclerView.Adapter<OnGoingListAdapter.ViewHolder>
        implements Filterable {


    private List<OnGoing> original_items = new ArrayList<>();
    private List<OnGoing> filtered_items = new ArrayList<>();
    private ItemFilter mFilter = new ItemFilter();

    private final int mBackground;

    private final TypedValue mTypeValued = new TypedValue();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onNbRecordingsClick(View view, int position);
        void onCancelClick(View view, int position);
        void onEditClick(View view, int position);
    }

    public OnGoingListAdapter(Context context, List<OnGoing> items) {
        original_items = items;
        filtered_items = items;

        mBackground = mTypeValued.resourceId;
        ctx = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_on_going_item, parent, false);
        v.setBackgroundColor(mBackground);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final OnGoing ongo = filtered_items.get(position);
        SimpleDateFormat date_f = new SimpleDateFormat("dd MMM yy");
        SimpleDateFormat time_f = new SimpleDateFormat("HH:mm");

        holder.name.setText(ongo.getContact().getName());
        holder.phone.setText(ongo.getContact().getPhone());
        Picasso.with(ctx).load(ctx.getResources().getIdentifier(ongo.getContact().getPhoto(), null, ctx.getPackageName()))
                .resize(50, 50)
                .transform(new CircleTransform())
                .into(holder.photo);
        holder.message.setText(ongo.getPempz().getMessage());
        holder.upTo.setText(
                Html.fromHtml(date_f.format(ongo.getPempz().getTo()) + "<br/>" + time_f.format(ongo.getPempz().getTo())) //ongo.getPempz().getTo().toString()
        );
        holder.nbRecordings.setText(
                String.valueOf(Constant.getRandomInt(10))
        );

        holder.nbRecordings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onNbRecordingsClick(view, position);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onEditClick(view, position);
            }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onCancelClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filtered_items.size();
    }

    public OnGoing getItem(int position) {
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

        public TextView message;

        public TextView upTo;

        // @BindView(R.id.lyt_parent)
        public LinearLayout lyt_parent;

        public Button nbRecordings;
        public ImageView cancel;
        public ImageView edit;

        public ViewHolder(View view) {
            super(view);
            // ButterKnife.bind(this, view);

            lyt_parent = (LinearLayout) view.findViewById(R.id.lyt_parent);

            name = (TextView) view.findViewById(R.id.name);
            phone = (TextView) view.findViewById(R.id.phone);
            photo = (ImageView) view.findViewById(R.id.photo);

            message = (TextView) view.findViewById(R.id.message);
            upTo = (TextView) view.findViewById(R.id.up_to);

            nbRecordings = (Button) view.findViewById(R.id.nb_recordings);
            cancel = (ImageView) view.findViewById(R.id.cancel);
            edit = (ImageView) view.findViewById(R.id.edit);
        }
    }


    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            final List<OnGoing> list = original_items;
            final List<OnGoing> result_list = new ArrayList<>();

            for(int i = 0; i < list.size(); i++) {
                String str_name = list.get(i).getContact().getName();
                String str_phone  = list.get(i).getContact().getPhone();
                String str_message = list.get(i).getPempz().getMessage();

                if (str_name.toLowerCase().contains(query) ||
                        str_phone.contains(query) || str_message.contains(query)) {

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
            filtered_items = (List<OnGoing>) results.values;
            notifyDataSetChanged();
        }
    }

}

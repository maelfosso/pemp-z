package com.pempz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.pempz.R;
import com.pempz.model.Contact;
import com.pempz.model.Pempz;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Mael FOSSO on 5/25/2016.
 */
public class PempzListAdapter extends RecyclerView.Adapter<PempzListAdapter.ViewHolder> {


    private List<Pempz> original_items = new ArrayList<>();
    private List<Pempz> filtered_items = new ArrayList<>();
    private ItemFilter mFilter = new ItemFilter();

    private OnItemClickListener mOnItemClickListener;

    Context context;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public PempzListAdapter(Context ctx, List<Pempz> items) {
        original_items = items;
        filtered_items = items;

        context = ctx;
    }

    public void setOnItemClickListener(final OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public PempzListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pempz_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PempzListAdapter.ViewHolder holder, final int position) {
        final Pempz pempz =  filtered_items.get(position);

        holder.message.setText(pempz.getMessage());

        SimpleDateFormat dn = new SimpleDateFormat("EEE");
        SimpleDateFormat mn = new SimpleDateFormat("MMM");
        DecimalFormat df = new DecimalFormat("00");

        Calendar f = Calendar.getInstance();
        f.setTime(pempz.getFrom());
        Calendar t = Calendar.getInstance();
        t.setTime(pempz.getTo());
        if (pempz.getFrom().getDate() == pempz.getTo().getDate()) {
            String date = dn.format(f.getTime()) + ", " + f.get(Calendar.DATE) + " " + mn.format(f.getTime()) + " " + f.get(Calendar.YEAR);
            String time = df.format(f.get(Calendar.HOUR_OF_DAY)) + ":" + df.format(f.get(Calendar.MINUTE)) + " - " + df.format(t.get(Calendar.HOUR_OF_DAY)) + ":" + df.format(t.get(Calendar.MINUTE));

            holder.date.setText(date);
            holder.time_range.setText(time);

            holder.to_lyt.setVisibility(View.GONE);
            holder.from_lyt.setVisibility(View.GONE);
        } else {
            String f_date = dn.format(f.getTime()) + ", " + f.get(Calendar.DATE) + " " + mn.format(f.getTime()) + " " + f.get(Calendar.YEAR);
            String f_time = df.format(f.get(Calendar.HOUR_OF_DAY)) + ":" + df.format(f.get(Calendar.MINUTE));
            holder.from_date.setText(f_date);
            holder.from_time.setText(f_time);

            String t_date = dn.format(t.getTime()) + ", " + t.get(Calendar.DATE) + " " + mn.format(t.getTime()) + " " + t.get(Calendar.YEAR);
            String t_time = df.format(t.get(Calendar.HOUR_OF_DAY)) + ":" + df.format(t.get(Calendar.MINUTE));
            holder.to_date.setText(t_date);
            holder.to_time.setText(t_time);

            holder.period_lyt.setVisibility(View.GONE);
        }

        holder.reuse.setTag(position);
        holder.desactivate.getTag(position);
        holder.delete.getTag(position);

        holder.details_lyt.setTag(position);
        holder.details_lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filtered_items.size();
    }

    public Pempz getItem(int position) {
        return filtered_items.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout details_lyt;
        public TextView message;

        public LinearLayout period_lyt;
        public TextView date;
        public TextView time_range;

        public LinearLayout from_lyt;
        public TextView from_date;
        public TextView from_time;

        public LinearLayout to_lyt;
        public TextView to_date;
        public TextView to_time;

        public ButtonFlat reuse;
        public ButtonFlat desactivate;
        public ButtonFlat delete;

        ViewHolder(View view) {
            super(view);

            details_lyt = (LinearLayout) view.findViewById(R.id.details_lyt);
            message = (TextView) view.findViewById(R.id.message);

            period_lyt = (LinearLayout) view.findViewById(R.id.period_lyt);
            date = (TextView) view.findViewById(R.id.date);
            time_range = (TextView) view.findViewById(R.id.time_range);

            from_lyt = (LinearLayout) view.findViewById(R.id.from_lyt);
            from_date = (TextView) view.findViewById(R.id.from_date);
            from_time = (TextView) view.findViewById(R.id.from_time);

            to_lyt = (LinearLayout) view.findViewById(R.id.to_lyt);
            to_date = (TextView) view.findViewById(R.id.to_date);
            to_time = (TextView) view.findViewById(R.id.to_time);

            reuse = (ButtonFlat) view.findViewById(R.id.reuse);
            desactivate = (ButtonFlat) view.findViewById(R.id.desactivate);
            delete = (ButtonFlat) view.findViewById(R.id.delete);
        }
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            final List<Pempz> list = original_items;
            final List<Pempz> result_list = new ArrayList<>();

            for(int i = 0; i < list.size(); i++) {
                String text = list.get(i).getMessage();
                if (text.contains(query)) {
                    result_list.add(list.get(i));
                }
            }

            results.count = result_list.size();
            results.values = result_list;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            filtered_items = (List<Pempz>)results.values;
            notifyDataSetChanged();
        }
    }
}

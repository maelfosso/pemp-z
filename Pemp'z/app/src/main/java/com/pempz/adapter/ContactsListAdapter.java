package com.pempz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pempz.R;
import com.pempz.model.Contact;
import com.pempz.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mael FOSSO on 5/10/2016.
 */
public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> implements Filterable {

    private List<Contact> original_items = new ArrayList<>();
    private List<Contact> filtered_items = new ArrayList<>();
    private ItemFilter mFilter = new ItemFilter();

    private final int mBackground;

    private final TypedValue mTypeValued = new TypedValue();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public ContactsListAdapter(Context context, List<Contact> items) {
        original_items = items;
        filtered_items = items;

        mBackground = mTypeValued.resourceId;
        ctx = context;
    }

    @Override
    public ContactsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contacts_item, parent, false);
        v.setBackgroundColor(mBackground);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Contact contact = filtered_items.get(position);

        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getPhone());
        Picasso.with(ctx).load(ctx.getResources().getIdentifier(contact.getPhoto(), null, ctx.getPackageName()))
                .resize(100, 100)
                .transform(new CircleTransform())
                .into(holder.photo);

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filtered_items.size();
    }

    public Contact getItem(int position) {
        return filtered_items.get(position);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // @BindView(R.id.contact_item_name)
        public TextView name;
        // @BindView(R.id.contact_item_phone)
        public TextView phone;
        // @BindView(R.id.contact_item_photo)
        public ImageView photo;
        // @BindView(R.id.lyt_parent)
        public LinearLayout lyt_parent;

        public ViewHolder(View view) {
            super(view);
            // ButterKnife.bind(this, view);

            lyt_parent = (LinearLayout) view.findViewById(R.id.lyt_parent);

            name = (TextView) view.findViewById(R.id.contact_item_name);
            phone = (TextView) view.findViewById(R.id.contact_item_phone);
            photo = (ImageView) view.findViewById(R.id.contact_item_photo);
        }
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            final List<Contact> list = original_items;
            final List<Contact> result_list = new ArrayList<>();

            for(int i = 0; i < list.size(); i++) {
                String str_name = list.get(i).getName();
                String str_phone  = list.get(i).getPhone();
                if (str_name.toLowerCase().contains(query) ||
                        str_phone.contains(query)) {

                    Log.d("contactlistadapter", list.get(i).getName() + " --- " + list.get(i).getPhone());
                    result_list.add(list.get(i));
                }
            }

            results.values = result_list;
            results.count = result_list.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            filtered_items = (List<Contact>) results.values;
            notifyDataSetChanged();
        }
    }

}

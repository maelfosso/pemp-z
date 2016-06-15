package com.pempz.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pempz.R;
import com.pempz.model.Contact;
import com.pempz.widget.CircleTransform;
import com.plumillonforge.android.chipview.ChipViewAdapter;
import com.squareup.picasso.Picasso;

/**
 * Created by Mael FOSSO on 6/15/2016.
 */
public class MainChipViewAdapter extends ChipViewAdapter {

    Context ctx;
    int type;

    public MainChipViewAdapter(Context context, int type) {
        super(context);

        this.ctx = context;
        this.type = type;
    }

    @Override
    public int getLayoutRes(int position) {

        if (type == 0) {
            return R.layout.chip_contact;
        } else {
            return R.layout.chip_contact_close;
        }
    }

    @Override
    public int getBackgroundRes(int position) {
        return 0;
    }

    @Override
    public int getBackgroundColor(int position) {
        return getColor(R.color.colorAccent);
    }

    @Override
    public int getBackgroundColorSelected(int position) {
        return 0;
    }

    @Override
    public void onLayout(View view, int position) {
        Contact contact = (Contact) getChip(position);

        TextView name = (TextView) view.findViewById(R.id.contact_name);
        TextView phone = (TextView) view.findViewById(R.id.contact_phone);
        ImageView photo = (ImageView) view.findViewById(R.id.contact_photo);

        name.setText(contact.getName());
        phone.setText(contact.getPhone());
        Picasso.with(ctx).load(ctx.getResources().getIdentifier(contact.getPhoto(), null, ctx.getPackageName()))
                .resize(25, 25)
                .transform(new CircleTransform())
                .into(photo);

    }
}

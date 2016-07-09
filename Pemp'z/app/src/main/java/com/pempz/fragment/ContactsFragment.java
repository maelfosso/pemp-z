package com.pempz.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pempz.PempzOfContactActivity;
import com.pempz.PempzActivity;
import com.pempz.R;
import com.pempz.adapter.ContactsListAdapter;
import com.pempz.data.Constant;
import com.pempz.model.Contact;
import com.pempz.widget.CircleTransform;
import com.squareup.picasso.Picasso;

public class ContactsFragment extends Fragment {

    // @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    // @BindView(R.id.progressBar)
    ProgressBar progressBar;


    ContactsListAdapter adapter;


    private OnFragmentInteractionListener mListener;


    public ContactsFragment() {
        // Required empty public constructor
    }

    public static OnGoingFragment newInstance() {
        OnGoingFragment fragment = new OnGoingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.hasFixedSize();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        // recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new ContactsListAdapter(this.getActivity(), Constant.getContactsData(this.getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ContactsListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), PempzOfContactActivity.class);
                intent.putExtra(PempzOfContactActivity.EXTRA_OBJ, adapter.getItem(position));
                startActivity(intent);
            }

            @Override
            public void onPhotoClick(View view, int position) {
                displayContactDetails(adapter.getItem(position));
            }

        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void displayContactDetails(final Contact contact) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_contact_details);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.name)).setText(contact.getName());
        ((TextView) dialog.findViewById(R.id.phone)).setText(contact.getPhone());
        ImageView photo = (ImageView) dialog.findViewById(R.id.photo);
        Picasso.with(getActivity()).load(getActivity().getResources().getIdentifier(contact.getPhoto(), null, getActivity().getPackageName()))
                .resize(100, 100)
                .transform(new CircleTransform())
                .into(photo);

        ((Button) dialog.findViewById(R.id.btn_pempz)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PempzActivity.class);
                intent.putExtra(PempzActivity.KEY_ACTION, "new");
                startActivity(intent);
            }
        });
        ((ImageView) dialog.findViewById(R.id.action_close)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((ImageView) dialog.findViewById(R.id.action_list)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PempzOfContactActivity.class);
                intent.putExtra(PempzOfContactActivity.EXTRA_OBJ, contact);
                startActivity(intent);
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Contact contact);
    }
}

package com.pempz.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.pempz.R;
import com.pempz.adapter.ContactsListAdapter;
import com.pempz.data.Constant;
import com.pempz.model.Contact;
import com.pempz.widget.DividerItemDecoration;

public class ContactsFragment extends Fragment implements ContactsListAdapter.OnItemClickListener {

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
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new ContactsListAdapter(this.getActivity(), Constant.getContactsData(this.getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        super.onViewCreated(view, savedInstanceState);
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

    @Override
    public void onItemClick(View view, int position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(adapter.getItem(position));
        }
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

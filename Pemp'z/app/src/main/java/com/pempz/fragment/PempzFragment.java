package com.pempz.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pempz.PempzDetailsActivity;
import com.pempz.R;
import com.pempz.adapter.PempzListAdapter;
import com.pempz.model.Contact;
import com.pempz.model.Pempz;
import com.pempz.widget.DividerItemDecoration;

import java.util.List;

public class PempzFragment extends Fragment implements PempzListAdapter.OnItemClickListener {
    public static String KEY_PEMPZ = "com.pempz.PEMPZ";

    private OnPempzFragmentListener mListener;

    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private PempzListAdapter adapter;
    private List<Pempz> items;

    View view;
    public PempzFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            items = ((Contact)getArguments().getSerializable(KEY_PEMPZ)).getPempz();

            Log.d("Pempz", "Size ... " + items.size());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pempz, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        // recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Log.d("Pempz", "OnCreateView --- Size ..." + items.size());
        adapter = new PempzListAdapter(getActivity(), ((Contact)getArguments().getSerializable(KEY_PEMPZ)).getPempz());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnPempzFragmentListener) {
            mListener = (OnPempzFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPempzFragmentListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onItemClick(View view, int position) {
        Pempz pempz = adapter.getItem(position);

        Intent i = new Intent(getActivity(), PempzDetailsActivity.class);
        i.putExtra(PempzDetailsActivity.EXTRA_OBJ, pempz);
        startActivity(i);
    }
}

package com.pempz.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pempz.PempzActivity;
import com.pempz.R;
import com.pempz.adapter.ContactsListAdapter;
import com.pempz.adapter.OnGoingListAdapter;
import com.pempz.adapter.RecordingsListAdapter;
import com.pempz.data.Constant;
import com.pempz.fragment.dialog.RecordListDialog;
import com.pempz.model.OnGoing;
import com.pempz.model.Record;
import com.pempz.widget.DividerItemDecoration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnGoingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OnGoingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OnGoingFragment extends Fragment
        implements OnGoingListAdapter.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    // @BindView(R.id.progressBar)
    ProgressBar progressBar;

    OnGoingListAdapter adapter;
    private OnFragmentInteractionListener mListener;

    public OnGoingFragment() {
        // Required empty public constructor
    }

    public static OnGoingFragment newInstance() {
        OnGoingFragment fragment = new OnGoingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        adapter = new OnGoingListAdapter(this.getActivity(), Constant.getOnGoingData(this.getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onNbRecordingsClick(View view, int position) {
        RecordListDialog dialog = new RecordListDialog();
        dialog.show(getActivity().getSupportFragmentManager(), "recordings_list");
        /*sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/




    }


    @Override
    public void onEditClick(View view, int position) {
        Intent i = new Intent(this.getActivity(), PempzActivity.class);
        i.putExtra(PempzActivity.KEY_ACTION, "Edit");
        i.putExtra(PempzActivity.KEY_PEMPZ, adapter.getItem(position));
        startActivity(i);
    }

    @Override
    public void onCancelClick(View view, int position) {

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
        void onFragmentInteraction(OnGoing ongo);
    }
}

package com.pempz.fragment.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pempz.R;
import com.pempz.adapter.RecordingsListAdapter;
import com.pempz.data.Constant;
import com.pempz.model.Record;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mael FOSSO on 7/9/2016.
 */
public class RecordListDialog extends DialogFragment implements
        RecordingsListAdapter.OnItemClickListener, View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener {

    SeekBar sb;
    TextView name;
    TextView elapsed;
    TextView durationTv;
    RecyclerView recyclerView;
    ImageView togglePP;
    ImageView next;
    ImageView previous;

    View before = null;

    MediaPlayer mp = new MediaPlayer();
    FileInputStream fis = null;
    Integer currentPosition = 0;
    Boolean isPaused = false;

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future future = null;
    Handler handler = new Handler();
    Runnable UpdateSongTime = new Runnable() {
        public void run() {
            if (mp.isPlaying()) {
                int startTime = mp.getCurrentPosition();
                elapsed.setText(String.format("%d:%02d",

                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                        toMinutes((long) startTime)))
                );
                sb.setProgress((int)startTime);
                handler.postDelayed(this, 100);
            }
        }
    };

    RecordingsListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_recordings, container, false);

        sb = (SeekBar) v.findViewById(R.id.progression);
        name = (TextView) v.findViewById(R.id.name);
        elapsed = (TextView) v.findViewById(R.id.elapsing_time);
        durationTv = (TextView) v.findViewById(R.id.duration);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        togglePP = (ImageView) v.findViewById(R.id.toggle_play_pause);
        next = (ImageView) v.findViewById(R.id.next);
        previous = (ImageView) v.findViewById(R.id.previous);


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        future = executorService.submit(UpdateSongTime);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.hasFixedSize();
        adapter = new RecordingsListAdapter(this.getActivity(), 5);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        previous.setOnClickListener(this);
        togglePP.setOnClickListener(this);
        next.setOnClickListener(this);
        sb.setOnSeekBarChangeListener(this);

        mp.setOnCompletionListener(this);

        super.onViewCreated(view, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        handler.removeCallbacksAndMessages(UpdateSongTime);
        future.cancel(true);

        if (mp.isPlaying()) {
            mp.stop();
        }
        mp.release();
    }

    @Override
    public void onRecordClick(View view, int position) {
        playRecordOnPosition(position);
        /*if (before == null) {
            before = view;
        } else {
            ((ImageView) before.findViewById(R.id.status)).setVisibility(View.GONE);
            before = view;
        }

        Record record = adapter.getItem(position);
        int duration = 0;
        currentPosition = position;

        if (mp.isPlaying() || isPaused) {
            mp.reset();
        }

        try {
            AssetFileDescriptor afd = getContext().getAssets().openFd("records/" + record.getFile());

            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.prepare();

            duration = mp.getDuration();

            mp.start();
        } catch (IOException ioe) {
            ioe.printStackTrace();

            Log.e("MEDIAPLAYER", "Error ---*** " + ioe.getMessage());
        } catch (Exception e) {
            e.printStackTrace();

            Log.e("MEDIAPLAUER", "Error ---**** " + e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();

                } catch (IOException ioe) {
                    ioe.printStackTrace();

                    Log.e("MEDIAPLAYER", "FIS Error ---*** " + ioe.getMessage());
                }
            }
        }

        sb.setMax(duration);
        // sb.setProgress(0);
        durationTv.setVisibility(View.VISIBLE);
        durationTv.setText(Constant.fromSecondToHM(duration));
        name.setText(record.getFile());
        togglePP.setImageResource(R.drawable.ic_pause);

        ImageView status = (ImageView) view.findViewById(R.id.status);
        status.setVisibility(View.VISIBLE);
        status.setImageResource(R.drawable.ic_play_arrow);

        handler.postDelayed(UpdateSongTime, 100);*/
    }

    private void playRecordOnPosition(int position) {
        View view = recyclerView.getChildAt(position);

        if (before == null) {
            before = view;
        } else {
            ((ImageView) before.findViewById(R.id.status)).setVisibility(View.GONE);
            before = view;
        }

        Record record = adapter.getItem(position);
        int duration = 0;
        currentPosition = position;

        if (mp.isPlaying() || isPaused) {
            mp.reset();
        }

        try {
            AssetFileDescriptor afd = getContext().getAssets().openFd("records/" + record.getFile());

            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.prepare();

            duration = mp.getDuration();

            mp.start();
        } catch (IOException ioe) {
            ioe.printStackTrace();

            Log.e("MEDIAPLAYER", "Error ---*** " + ioe.getMessage());
        } catch (Exception e) {
            e.printStackTrace();

            Log.e("MEDIAPLAUER", "Error ---**** " + e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();

                } catch (IOException ioe) {
                    ioe.printStackTrace();

                    Log.e("MEDIAPLAYER", "FIS Error ---*** " + ioe.getMessage());
                }
            }
        }

        sb.setMax(duration);
        durationTv.setVisibility(View.VISIBLE);
        durationTv.setText(Constant.fromSecondToHM(duration));
        name.setText(record.getFile());
        togglePP.setImageResource(R.drawable.ic_pause);

        ImageView status = (ImageView) view.findViewById(R.id.status);
        status.setVisibility(View.VISIBLE);
        status.setImageResource(R.drawable.ic_play_arrow);

        handler.postDelayed(UpdateSongTime, 100);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toggle_play_pause:
                if (!mp.isPlaying() && !isPaused) {

                    playRecordOnPosition(currentPosition);

                } else {

                    if (mp.isPlaying()) {
                        mp.pause();

                        isPaused = true;
                        togglePP.setImageResource(R.drawable.ic_play_arrow);
                    } else {
                        mp.start();

                        handler.postDelayed(UpdateSongTime, 100);

                        isPaused = false;
                        togglePP.setImageResource(R.drawable.ic_pause);
                    }

                }
                break;

            case R.id.next:
                playRecordOnPosition((currentPosition + 1) % adapter.getItemCount());
                break;

            case R.id.previous:
                playRecordOnPosition((currentPosition - 1) % adapter.getItemCount());
                break;
        }


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            mp.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d("ON completion", "Max Adapter --- " + (adapter.getItemCount() - 1));

        if (currentPosition == (adapter.getItemCount() - 1)) {
            togglePP.setImageResource(R.drawable.ic_play_arrow);
        } else {
            mp.reset();
            playRecordOnPosition((currentPosition + 1) % adapter.getItemCount());
        }
    }
}

package com.pempz.adapter;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pempz.R;
import com.pempz.data.Constant;
import com.pempz.model.Record;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Mael FOSSO on 7/6/2016.
 */
public class RecordingsListAdapter extends RecyclerView.Adapter<RecordingsListAdapter.ViewHolder> {

    List<Record> records = new ArrayList<>();

    int number;

    Context context;


    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onRecordClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public RecordingsListAdapter(Context ctx, int number) {
        context = ctx;

        try {
            List<String> paths = Arrays.asList(context.getAssets().list("records"));

            Log.d("REcordingsListAdapter", String.valueOf(paths.size()));

            for(String path: paths) {
                Record r = new Record();
                r.setFile(path);
                r.setCreated(new Date());

                records.add(r);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recording_item, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Record record = records.get(position);


        Integer duration = 0;

        final MediaPlayer mp = new MediaPlayer();
        FileInputStream fis = null;

        try {
            // fis = new FileInputStream(context.getAssets().openFd("/records/" + file));
            AssetFileDescriptor afd = context.getAssets().openFd("records/" + record.getFile());

            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.prepare();

            duration = mp.getDuration();
        } catch (IOException ioe) {
            ioe.printStackTrace();

            Log.e("MEDIAPLAYER", "Error ---*** " + ioe.getMessage());
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
        SimpleDateFormat date_f = new SimpleDateFormat("dd MMM yy");
        SimpleDateFormat time_f = new SimpleDateFormat("HH:mm");

        holder.duration.setText(Constant.fromSecondToHM(duration));
        holder.name.setText(record.getFile());
        holder.created.setText(date_f.format(record.getCreated()) + " " + time_f.format(record.getCreated()));
        holder.status.setVisibility(View.GONE);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onRecordClick(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public Record getItem(int position) {
        return records.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout;

        public TextView name;
        public ImageView status;

        public TextView created;
        public TextView duration;


        ViewHolder(View view) {
            super(view);

            layout = (LinearLayout) view.findViewById(R.id.recording_lyt);

            name = (TextView) view.findViewById(R.id.recording_name);
            duration = (TextView) view.findViewById(R.id.duration);

            status = (ImageView) view.findViewById(R.id.status);
            created = (TextView) view.findViewById(R.id.created);
        }
    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {

        }
    };
}

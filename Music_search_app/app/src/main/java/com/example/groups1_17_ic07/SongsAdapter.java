package com.example.groups1_17_ic07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SongsAdapter extends ArrayAdapter<SongClass> {
    ArrayList<SongClass> songClasses = new ArrayList<>();
    public SongsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SongClass> objects) {

        super(context, resource, objects);
        songClasses = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                SongClass songClass = songClasses.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.songsinfolayout,parent,false);
        }

        TextView SongName = convertView.findViewById(R.id.SongName);
        TextView ArtistName = convertView.findViewById(R.id.ArtistName);
        TextView AlbumName = convertView.findViewById(R.id.AlbumName);
        TextView DateName = convertView.findViewById(R.id.DateName);

        SongName.setText(songClass.track);
        ArtistName.setText(songClass.artist);
        AlbumName.setText(songClass.album);
        DateName.setText(songClass.date.toString());


        return convertView;
    }


}

package com.example.groups1_17_ic07;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText SongName;
    TextView Limit;
    SeekBar seekBar;
    RadioGroup radioGroup;
    ListView listView;
    ProgressBar progressBar;
    String sort="s_track_rating";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SongName = findViewById(R.id.SongName);
        seekBar = findViewById(R.id.seekBar);
        radioGroup = findViewById(R.id.radioGroup);
        listView = findViewById(R.id.listView);
        Limit = findViewById(R.id.Limit);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        Limit.setText("Limit: 5");
//        seekBar.setMin(5);
        seekBar.setMax(25);
        seekBar.setProgress(5);

        final Button search = findViewById(R.id.Search);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.SortTrackRating){
                    sort = "s_track_rating";
                    progressBar.setVisibility(View.VISIBLE);
                    try {
                        URL url = new URL("http://api.musixmatch.com/ws/1.1/track.search?apikey=a1a8aab86ef317f76e8aeefa50eb5174&q="+SongName.getText()+"&page_size="+seekBar.getProgress()+"&"+sort+"=desc");
                        new getMusic().execute(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                else if(checkedId == R.id.SongArtistRating){
                    sort = "s_artist_rating";
                    progressBar.setVisibility(View.VISIBLE);

                    try {
                        URL url = new URL("http://api.musixmatch.com/ws/1.1/track.search?apikey=a1a8aab86ef317f76e8aeefa50eb5174&q="+SongName.getText()+"&page_size="+seekBar.getProgress()+"&"+sort+"=desc");
                        new getMusic().execute(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(sort);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {





            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int i=progress;
                if(i<5) {
                    progress=5;
                    Limit.setText("Limit: "+progress);
                    seekBar.setProgress(progress);
                }else {
                    Limit.setText("Limit: "+progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SongName.getText().toString().equals("")){
                    SongName.setError("Please Enter Name");
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    try {
                        URL url = new URL("http://api.musixmatch.com/ws/1.1/track.search?apikey=a1a8aab86ef317f76e8aeefa50eb5174&q=" + SongName.getText().toString() + "&page_size=" + seekBar.getProgress() + "&" + sort + "=desc");
                        new getMusic().execute(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    public class getMusic extends AsyncTask<URL,Void, ArrayList<SongClass>> {


        @Override
        protected ArrayList<SongClass> doInBackground(URL... urls) {
            URL url = null;
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            ArrayList<SongClass> songClasses = new ArrayList<>();
            try {
                url = urls[0];
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                        System.out.println("Line Line" + line);

                    }
                    String json = stringBuilder.toString();
                    JSONObject root = new JSONObject(json);
                    JSONObject message = root.getJSONObject("message");
                    JSONObject songs = message.getJSONObject("body");
                    JSONArray tracklisobject = songs.getJSONArray("track_list");
                    System.out.println(root);
                    for (int i = 0; i < tracklisobject.length(); i++) {
                        JSONObject songsJSONObject = tracklisobject.getJSONObject(i);
                        JSONObject song = (JSONObject) songsJSONObject.get("track");
                        SongClass songClass = new SongClass(String.valueOf(song.get("track_name")), String.valueOf(song.get("artist_name")), String.valueOf(song.get("album_name")),String.valueOf(song.get("updated_time")),String.valueOf(song.get("track_share_url")));
                        songClasses.add(songClass);

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return songClasses;
        }

        @Override
        protected void onPostExecute(final ArrayList<SongClass> songClasses) {
            for (SongClass songClass: songClasses){

            }

            SongsAdapter adapter = new SongsAdapter(MainActivity.this,R.layout.songsinfolayout,songClasses);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String url = songClasses.get(position).url;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url)); startActivity(i);

                }
            });
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}

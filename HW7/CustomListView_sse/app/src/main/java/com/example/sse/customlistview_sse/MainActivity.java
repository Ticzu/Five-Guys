package com.example.sse.customlistview_sse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    private
    ListView lvEpisodes;
    ListAdapter lvAdapter;
    String[] links;
    SharedPreferences wmbPreference1;
    SharedPreferences.Editor editor;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        links = getResources().getStringArray(R.array.episodes_url);
        lvEpisodes = (ListView)findViewById(R.id.lvEpisodes);
        lvEpisodes.setClickable(true);
        lvAdapter = new MyCustomAdapter(this.getBaseContext());  //instead of passing the boring default string adapter, let's pass our own, see class MyCustomAdapter below!
        lvEpisodes.setAdapter(lvAdapter);
        lvEpisodes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "Item clicked : " + position, Toast.LENGTH_LONG).show();
                //                Uri uri = Uri.parse(links[position]);
                Intent intent = new Intent(getApplicationContext(), Webviewload.class );
                //                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                Bundle bundle = new Bundle();
                //Add your data to bundle
                bundle.putString("url", links[position]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
//        lvEpisodes.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                String [] links = getResources().getStringArray(R.array.episodes_url);
//                Object obj = lvEpisodes.getAdapter().getItem(position);
//                Uri uri = Uri.parse("http://memory-alpha.wikia.com/wiki/"+obj.toString());
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//            }
//
//            @SuppressWarnings("unused")
//            public void onClick(View v){
//            };
//        });






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);   //get rid of default behavior.

        // Inflate the menu; this adds items to the action bar
        getMenuInflater().inflate(R.menu.my_test_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.mnu_zero) {
            Toast.makeText(getBaseContext(), "Menu Zero.", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.mnu_one) {
            Toast.makeText(getBaseContext(), "Ring ring, Hi Mom.", Toast.LENGTH_LONG).show();
            return true;
        }


            return super.onOptionsItemSelected(item);  //if none of the above are true, do the default and return a boolean.
    }


}


//***************************************************************//
//create a  class that extends BaseAdapter
//you will be prompted to implement methods... choose yes.
//***************************************************************//


class MyCustomAdapter extends BaseAdapter {

    private
     String episodes[];             //this is the introductory way to store the List data.  The way it's usually done is by creating
     String episodeDescriptions[];  //the "better" way is to encapsulate the list items into an object, then create an arraylist of objects.
//     int episodeImages[];         //this approach is fine for now.

//    ArrayList<String> episodes;
//    ArrayList<String> episodeDescriptions;
    ArrayList<Integer> episodeImages;  //Well, we can use one arrayList too...
    RatingBar ratingBar;

    Context context;   //What does refer to?  Context enables access to application specific resources.  Eg, spawning & receiving intents, locating the various managers.

    public ArrayList<Float> ratingList;
    public String TAG = "list";
    public MyCustomAdapter(Context aContext) {
//initializing our data in the constructor.
//        episodes = (ArrayList<String>) Arrays.asList(aContext.getResources().getStringArray(R.array.episodes));  //retrieving list of episodes predefined in strings-array "episodes" in strings.xml
//        episodeDescriptions = (ArrayList<String>) Arrays.asList(aContext.getResources().getStringArray(R.array.episode_descriptions));  //Also casting to a friendly ArrayList.
        context = aContext;  //saving the context we'll need it again.

        episodes =aContext.getResources().getStringArray(R.array.episodes);  //retrieving list of episodes predefined in strings-array "episodes" in strings.xml
        episodeDescriptions = aContext.getResources().getStringArray(R.array.episode_descriptions);

        episodeImages = new ArrayList<Integer>();   //Could also use helper function below to autoextract drawable resources, but keeping things as simple as possible.
        episodeImages.add(R.drawable.st_spocks_brain);
        episodeImages.add(R.drawable.st_arena__kirk_gorn);
        episodeImages.add(R.drawable.st_this_side_of_paradise__spock_in_love);
        episodeImages.add(R.drawable.st_mirror_mirror__evil_spock_and_good_kirk);
        episodeImages.add(R.drawable.st_platos_stepchildren__kirk_spock);
        episodeImages.add(R.drawable.st_the_naked_time__sulu_sword);
        episodeImages.add(R.drawable.st_the_trouble_with_tribbles__kirk_tribbles);

        ratingList = new ArrayList<Float>(7);
        ratingList.add(0,3.0f);
        ratingList.add(1,3.0f);
        ratingList.add(2,3.0f);
        ratingList.add(3,3.0f);
        ratingList.add(4,3.0f);
        ratingList.add(5,3.0f);
        ratingList.add(6,3.0f);


    }

    @Override
    public int getCount() {
//        return episodes.size();  //all of the arrays are same length, so return length of any... ick!  But ok for now. :)
        return episodes.length;  //all of the arrays are same length, so return length of any... ick!  But ok for now. :)
    }

    @Override
    public Object getItem(int position) {
//        return episodes.get(position);  //In Case you want to use an ArrayList
        return episodes[position];  //really should be retuning entire set of row data, but it's up to us, and we aren't using.
    }

    @Override
    public long getItemId(int position) {
        return position;  //don't really use this, but have to do something since we had to implement (base is abstract).
    }

    private void savePreferences(ArrayList<Float> ratingList, float rating, int position) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("ratings" + position, rating);
//        ratingList.set(position,rating);
//        for(int i=0;i<ratingList.size();i++)
//            editor.putFloat("ratings" + i, ratingList.get(i));
        editor.commit();
    }

    private void loadSavedPreferences(int position) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        ratingBar.setRating(prefs.getFloat("ratings"+ position,0.0f));
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {  //convertView is Row, parent is the layout that has the row Views.
        //THIS IS WHERE THE ACTION HAPPENS.  Let's optimize a bit by checking to see if we need to inflate, or if it's already been inflated...
        View row;  //this will refer to the row to be inflated or displayed if it's already been displayed. (listview_row.xml)
        if (convertView == null){  //indicates this is the first time we are creating this row.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  //CRASH
            row = inflater.inflate(R.layout.listview_row, parent, false);
        }
        else
        {
            row = convertView;
        }

        ratingBar = (RatingBar)row.findViewById(R.id.rbEpisode);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                savePreferences(ratingList, rating, position);
            }
        });
        loadSavedPreferences(position);
        // 2. Now that we have a valid row instance, we need to get references to the views within that row.
        ImageView imgEpisode = (ImageView) row.findViewById(R.id.imgEpisode);  //notice we prefixed findViewByID with row, why?  row, is the container.
        TextView tvEpisodeTitle = (TextView) row.findViewById(R.id.tvEpisodeTitle);
        TextView tvEpisodeDescription = (TextView) row.findViewById(R.id.tvEpisodeDescription);

//        tvEpisodeTitle.setText(episodes.get(position));  //puts the predefined titles in the textview.
//
        tvEpisodeTitle.setText(episodes[position]);
        tvEpisodeDescription.setText(episodeDescriptions[position]);

        imgEpisode.setImageResource(episodeImages.get(position).intValue());

        return row;  //once the row is fully constructed, return it.  Hey whatif we had buttons, can we target onClick Events within the rows, yep!
    }



    ///Helper method to get the drawables...///
    ///this might prove useful later...///

//    public ArrayList<Drawable> getDrawables() {
//        Field[] drawablesFields =com.example.sse.customlistview_sse.R.drawable.class.getFields();
//        ArrayList<Drawable> drawables = new ArrayList<Drawable>();
//
//        String fieldName;
//        for (Field field : drawablesFields) {
//            try {
//                fieldName = field.getName();
//                Log.i("LOG_TAG", "com.your.project.R.drawable." + fieldName);
//                if (fieldName.startsWith("animals_"))  //only add drawable resources that have our prefix.
//                    drawables.add(context.getResources().getDrawable(field.getInt(null)));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}




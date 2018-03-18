package com.example.rahulshaw.allnews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rahulshaw.allnews.models.NewsModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimesOfIndia extends AppCompatActivity {

    ListView lvNews;


    HttpURLConnection connection = null;
    BufferedReader reader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times_of_india);

        lvNews = (ListView) findViewById(R.id.lvNews);

        final String[] urlArray = getResources().getStringArray(R.array.urlNews);

        // Create default options which will be used for every
//  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .defaultDisplayImageOptions(defaultOptions)
        .build();
        ImageLoader.getInstance().init(config);  // Do it on Application start

        new JSONTask().execute(urlArray[0]);

    }

    public class JSONTask extends AsyncTask<String, String, List<NewsModel> >{

        ProgressDialog pd = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(TimesOfIndia.this);
            pd.setTitle("Loading...");
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();

            long delayInMillis = 10000;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    pd.dismiss();
                }
            }, delayInMillis);
        }

        @Override
        protected List<NewsModel> doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream stream = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";

                    while ((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    String finalJson = buffer.toString();

                    JSONObject parentObject = new JSONObject(finalJson);
                    JSONArray parentArray = parentObject.getJSONArray("articles");

                    List<NewsModel> newsModelList = new ArrayList<>();

                    for(int i=0; i<parentArray.length();i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        NewsModel newsModel = new NewsModel();
                        newsModel.setAuthorName(finalObject.getString("author"));
                        String title[] = finalObject.getString("title").split(" - Times of ");
                        newsModel.setArticleName(title[0]);
                        newsModel.setDescription(finalObject.getString("description"));
                        newsModel.setUrlToNews(finalObject.getString("url"));
                        newsModel.setImage(finalObject.getString("urlToImage"));

                        if (finalObject.getString("author").equalsIgnoreCase("null")) {
                            newsModel.setAuthorName("Unknown");
                        }
                        newsModelList.add(newsModel);

                    }

                    return newsModelList ;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if(null != connection) {
                        connection.disconnect();
                    }
                    try {
                        if(null != reader) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return  null;
        }

        @Override
        protected void onPostExecute(List<NewsModel> result) {
            super.onPostExecute(result);

            pd.dismiss();

            NewsAdapter newsAdapter = new NewsAdapter(getApplicationContext(), R.layout.row, result);
            lvNews.setAdapter(newsAdapter);


        }

        @Override
        protected void onCancelled() {
            pd.dismiss();
            super.onCancelled();
        }
    }

    public class NewsAdapter extends ArrayAdapter{

        private List<NewsModel> newsModelList;
        private int resource;
        private LayoutInflater inflater;
        public NewsAdapter(Context context, int resource, List<NewsModel> objects) {
            super(context, resource, objects);
            newsModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){

            ViewHolder holder = null;
            if(convertView == null){
                convertView = inflater.inflate(resource, null);

                holder = new ViewHolder();
                holder.ivNewsImage = (ImageView) convertView.findViewById(R.id.ivNews);
                holder.tvHeadlines = (TextView) convertView.findViewById(R.id.tvHeadlines);
                holder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
                holder.tvAuthorName = (TextView) convertView.findViewById(R.id.tvAuthorName);
                holder.tvNewsLink = (TextView) convertView.findViewById(R.id.tvNews);

                convertView.setTag(holder);
            } else{
                holder = (ViewHolder) convertView.getTag();
            }

            final ProgressBar progressBar;

            progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);

            ImageLoader.getInstance().displayImage(newsModelList.get(position).getImage(), holder.ivNewsImage, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                }
            });
            holder.tvAuthorName.setText("Covered by - " + newsModelList.get(position).getAuthorName());
            holder.tvHeadlines.setText(newsModelList.get(position).getArticleName());
            holder.tvDescription.setText(newsModelList.get(position).getDescription());

            holder.tvNewsLink.setText("Read more");
            holder.tvNewsLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TimesOfIndia.this, DetailedNews.class);
                    intent.putExtra("url", newsModelList.get(position).getUrlToNews().toString());
                    startActivity(intent);

                }
            });

            return convertView;
        }

        class ViewHolder{
            private ImageView ivNewsImage;
            private TextView tvHeadlines;
            private TextView tvDescription;
            private TextView tvAuthorName;
            private TextView tvNewsLink;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_times_of_india, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_refresh){
            //new JSONTask().execute("https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=top&apiKey=5f9b844760b5410a842ec677d986d713");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



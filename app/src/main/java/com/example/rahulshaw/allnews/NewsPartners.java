package com.example.rahulshaw.allnews;

/**
 * Created by Rahul Shaw on 10-09-2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class NewsPartners extends AppCompatActivity{

    /*ImageView ivNewsPartner;
    TextView tvNewsPartner;*/
    ListView lvNewsPartner;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.list_news_partner);

            final String[] urlArray = getResources().getStringArray(R.array.urlNews);

            final String[] web = {
                    "Times of India",
                    "The hindu",
                    "Telegraph",
                    "The Economist"
            } ;
            final Integer[] imageId = {
                    R.mipmap.toi_icon,
                    R.drawable.unnamed,
                    R.drawable.the_telegraph_logo,
                    R.drawable.the_economist_logo
            };

            CustomList customListAdapter = new
                    CustomList(this, web, imageId);

            lvNewsPartner = (ListView) findViewById(R.id.lvNewsPartner);


            lvNewsPartner.setAdapter(customListAdapter);
            lvNewsPartner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(NewsPartners.this, "You Clicked at " +web[position], Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), TimesOfIndia.class);
                    //intent.putExtra("toiUrl", urlArray[0]);
                    startActivity(intent);
                }
            });
        }

    private class CustomList extends ArrayAdapter<String>{

        private final Activity context;
        private final String[] web;
        private final Integer[] imageId;
        private LayoutInflater inflater;
        public CustomList(Activity context,
                          String[] web, Integer[] imageId) {
            super(context, R.layout.news_partner, web);
            this.context = context;
            this.web = web;
            this.imageId = imageId;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.news_partner, null, true);

                holder = new ViewHolder();
                holder.tvNewsPartner = (TextView) convertView.findViewById(R.id.tvNewsPartners);
                holder.ivNewsPartner = (ImageView) convertView.findViewById(R.id.ivNewsPartners);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            //LayoutInflater inflater = context.getLayoutInflater();
            //TextView txtTitle = (TextView) convertView.findViewById(R.id.tvNewsPartners);

            /*ImageView imageView = (ImageView) convertView.findViewById(R.id.ivNewsPartners);*/
            holder.tvNewsPartner.setText(web[position]);

            holder.ivNewsPartner.setImageResource(imageId[position]);
            return convertView;
        }

        class ViewHolder{
            ImageView ivNewsPartner;
            TextView tvNewsPartner;
        }
    }
}

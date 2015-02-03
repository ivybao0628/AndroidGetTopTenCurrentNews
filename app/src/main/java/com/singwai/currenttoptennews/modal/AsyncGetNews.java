package com.singwai.currenttoptennews.modal;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.singwai.currenttoptennews.MainActivity;
import com.singwai.currenttoptennews.R;
import com.singwai.currenttoptennews.Utility.Utility;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Singwai Chan on 2/1/15.
 */
public class AsyncGetNews extends AsyncTask<Integer, Void, ArrayList<NewsItem>> {
    private Context context;
    private ProgressDialog dialog;

    public AsyncGetNews(Context context) {
        this.context = context;
        this.dialog = new ProgressDialog(context);
    }

    protected void onPreExecute() {
        //dialog.setMessage("Fetching data, please wait.");
        //dialog.setCanceledOnTouchOutside(false);
        //dialog.show();
    }

    @Override
    protected ArrayList<NewsItem> doInBackground(Integer... params) {
        ArrayList<NewsItem> result;
        Integer newsSection = params[0];
        result = USATodayNews.getNews(newsSection, context);

        for (int i = 0; i < result.size(); i++) {
            result.get(i).setImageLink(MicrosoftBingImageSearch.getImageLink(result.get(i).getTitle(), context));
        }
        for (int i = 0; i < result.size(); i++) {
            Log.e("USA NEWS RESULT ", result.get(i).getTitle());
            Log.e("USA NEWS RESULT ", result.get(i).getDescription());
            Log.e("USA NEWS RESULT ", result.get(i).getPubDate());
            Log.e("USA NEWS RESULT ", result.get(i).getLink());
            Log.e("USA NEWS RESULT ", result.get(i).getImageLink());
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<NewsItem> newsItems) {
        super.onPostExecute(newsItems);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        Log.e("Checking class", context.getClass() + "");
        ((MainActivity) context).addNewsFragment(newsItems);
        //Call Main activity to populate the
    }

    public static class USATodayNews {
        //API call = API_ENTRY + SECTION + ? + PARAMS.
        private static final String API_ENTRY = "http://api.usatoday.com/open/articles/topnews/";

        private static final String PARSE_KEY_STORIES = "stories";
        private static final String PARSE_KEY_DESCRIPTION = "description";
        private static final String PARSE_KEY_LINK = "link";
        private static final String PARSE_KEY_PUBLISH_DATE = "pubDate";
        private static final String PARSE_KEY_TITLE = "title";

        public static ArrayList<NewsItem> getNews(final Integer section, final Context context) {
            String resultString = "";
            String sectionString = context.getResources().getStringArray(R.array.news_section)[section];
            //ArrayList<NewsItem> result;
            HttpClient httpClient = new DefaultHttpClient();

            //Add all params array list
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("count", "10"));
            nameValuePairs.add(new BasicNameValuePair("encoding", "json"));
            nameValuePairs.add(new BasicNameValuePair("api_key", context.getString(R.string.USA_NEWS_API_KEY)));
            String paramsString = URLEncodedUtils.format(nameValuePairs, "UTF-8");

            Log.e("Get link result ", API_ENTRY + sectionString + "?" + paramsString);
            //Build Link
            HttpGet httpget = new HttpGet(API_ENTRY + sectionString + "?" + paramsString);
            //Execute and get the response.
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpget);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            HttpEntity entity = null;
            if (response != null) {
                entity = response.getEntity();
            }

            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        resultString += line;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Todo handle error here, for example server is down.
            return parseJSON(resultString);
        }

        //Input = USAToday's API JSON Result in string.
        private static ArrayList<NewsItem> parseJSON(final String JSONStringResult) {
            ArrayList<NewsItem> result = new ArrayList<>();
            if (!JSONValue.isValidJson(JSONStringResult)|| JSONStringResult== "") {
                return result;
                //throw new RuntimeException("Invalid JSON String");
            }

            Log.e ("Inspect JSON object", "JSON" + JSONStringResult);
            JSONObject jsonObject = (JSONObject) JSONValue.parse(JSONStringResult);
            JSONArray stories = (JSONArray) jsonObject.get(PARSE_KEY_STORIES);
            //Log.e ("all info" , "STORY ARRAY" + stories.toString());

            for (int i = 0; i < stories.size(); i++) {
                JSONObject entry = (JSONObject) stories.get(i);
                NewsItem temp = new NewsItem(
                        (String) entry.get(PARSE_KEY_TITLE),
                        (String) entry.get(PARSE_KEY_DESCRIPTION),
                        (String) entry.get(PARSE_KEY_LINK),
                        (String) entry.get(PARSE_KEY_PUBLISH_DATE)
                );
                result.add(temp);
            }


            return result;
        }

        private USATodayNews() {
        }

    }

    public static class MicrosoftBingImageSearch {
        private static final String API_ENTRY = "https://api.datamarket.azure.com/Bing/Search/v1/Image?Market=%27en-US%27&Adult=%27Moderate%27&ImageFilters=%27Size%3ASmall%27&$format=json&$top=1";

        private static final String PARSE_JSON_D = "d";
        private static final String PARSE_JSON_RESULTS = "results";
        private static final String PARSE_JSON_THUMBNAIL = "Thumbnail";
        private static final String PARSE_JSON_MEDIA_URL = "MediaUrl";


        //Input = title from USA TODAY's news. Search the first image from bing.
        public static String getImageLink(final String title, final Context context) {
            String resultString = "";
            String API_KEY = context.getString(R.string.MICROSOFT_ACCOUNT_KEYT);
            //For some reason post method doesn't work.
            //Only Get request work for this API.
            //Prepare Post request.

            HttpClient httpClient = new DefaultHttpClient();

            //Build Link
            //Clean link
            String queryString = Utility.cleanURL(title);

            //Check link
            Log.e("Bing Search ", queryString);
            Log.e("Bing Search ", API_ENTRY + "&Query='" + queryString + "'");
            HttpGet httpget = new HttpGet(API_ENTRY + "&Query=%27" + queryString + "%27");

            //HttpGet httpget = new HttpGet(APILink + SECTION[0] + "?" + paramsString);
            //Basic auth.
            String auth = API_KEY + ":" + API_KEY;
            String encodedAuth = Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
            //Log.e("", encodedAuth);
            httpget.addHeader("Authorization", "Basic " + encodedAuth);

            //Execute and get the response.
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpget);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        resultString += line;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return parseJSON(resultString, context);
        }

        private static String parseJSON(final String JSONStringResult, final Context context) {
            if (!JSONValue.isValidJson(JSONStringResult)) {
                throw new RuntimeException("Invalid JSON String");
            }
            Log.e("Bing JSON STRING ", JSONStringResult);
            JSONObject jsonObject = (JSONObject) JSONValue.parse(JSONStringResult);
            jsonObject = (JSONObject) jsonObject.get(PARSE_JSON_D);
            //Since we set out top = 1, we will only get one result.
            JSONArray jsonArray = ((JSONArray) jsonObject.get(PARSE_JSON_RESULTS));
            //Check see if there is a result for the string
            if (jsonArray.size() > 0) {
                jsonObject = (JSONObject) jsonArray.get(0);
                jsonObject = (JSONObject) jsonObject.get(PARSE_JSON_THUMBNAIL);
                String url = (String) jsonObject.get(PARSE_JSON_MEDIA_URL);
                return url;
            } else {
                //Return a placeholder image from string resource.
                return context.getString(R.string.image1);
            }

        }
    }

}

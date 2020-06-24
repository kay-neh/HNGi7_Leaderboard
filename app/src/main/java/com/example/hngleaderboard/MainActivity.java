package com.example.hngleaderboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Toolbar mainToolbar;
    List <Users> usersList;
    RecyclerView leaderboardRecyclerView;
    LeaderBoardAdapter adapter;
    ImageView top1Image,top2Image,top3Image;
    TextView top1Name,top1slackHandle,top1Point,top1Index;
    TextView top2Name,top2slackHandle,top2Point,top2Index;
    TextView top3Name,top3slackHandle,top3Point,top3Index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainToolbar = findViewById(R.id.main_toolbar);

        top1Image = findViewById(R.id.top1_image);
        top2Image = findViewById(R.id.top2_image);
        top3Image = findViewById(R.id.top3_image);

        top1Index = findViewById(R.id.top1_index);
        top2Index = findViewById(R.id.top2_index);
        top3Index = findViewById(R.id.top3_index);

        top1Name = findViewById(R.id.top1_name);
        top2Name = findViewById(R.id.top2_name);
        top3Name = findViewById(R.id.top3_name);

        top1slackHandle = findViewById(R.id.top1_slack_handle);
        top2slackHandle = findViewById(R.id.top2_slack_handle);
        top3slackHandle = findViewById(R.id.top3_slack_handle);

        top1Point =findViewById(R.id.top1_slack_point);
        top2Point =findViewById(R.id.top2_slack_point);
        top3Point =findViewById(R.id.top3_slack_point);

        leaderboardRecyclerView = findViewById(R.id.leaderboard_recycler_view);
        initAdapter();

        usersList = new ArrayList<>();
        int[] photos = {R.drawable.ic_man1,R.drawable.ic_man2,R.drawable.ic_man3,R.drawable.ic_man4,R.drawable.ic_woman1,R.drawable.ic_woman2,R.drawable.ic_woman3,R.drawable.ic_woman4};

        //Parse the local JSON file in raw directory
        try{
            JSONObject jsonRootObject = new JSONObject(readJson());

            JSONArray jsonArray = jsonRootObject.optJSONArray("leaderboard");

            if (jsonArray != null) {
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String name = jsonObject.optString("name");
                    String slackHandle = jsonObject.optString("Username");
                    String email = jsonObject.optString("Email");
                    String point = jsonObject.optString("Point");
                    usersList.add(new Users(String.valueOf(i+1),name,slackHandle,email,point, photos[new Random().nextInt(8)]));
                }
            }
            //create two sublists for displaying leaderboard
            List<Users> topThreeList = new ArrayList<>(usersList.subList(0, 3));
            List<Users> remainingList = new ArrayList<>(usersList.subList(3, usersList.size()));

            //Set data for top three
            top1Image.setImageResource(topThreeList.get(0).getPhoto());
            top1Index.setText(topThreeList.get(0).getIndex());
            top1Name.setText(topThreeList.get(0).getName());
            top1slackHandle.setText(topThreeList.get(0).getSlackHandle());
            top1Point.setText(topThreeList.get(0).getSlackPoint() + " Points");

            top2Image.setImageResource(topThreeList.get(1).getPhoto());
            top2Index.setText(topThreeList.get(1).getIndex());
            top2Name.setText(topThreeList.get(1).getName());
            top2slackHandle.setText(topThreeList.get(1).getSlackHandle());
            top2Point.setText(topThreeList.get(1).getSlackPoint() + " Points");

            top3Image.setImageResource(topThreeList.get(2).getPhoto());
            top3Index.setText(topThreeList.get(2).getIndex());
            top3Name.setText(topThreeList.get(2).getName());
            top3slackHandle.setText(topThreeList.get(2).getSlackHandle());
            top3Point.setText(topThreeList.get(2).getSlackPoint() + " Points");

            //set adapter list
            adapter.setLeaderBoardList(remainingList);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        mainToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.share){
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, composeLeaderboardmessage(usersList));
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                }
                return false;
            }
        });

    }

    public void initAdapter() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        leaderboardRecyclerView.setLayoutManager(llm);
        adapter = new LeaderBoardAdapter(this);
        leaderboardRecyclerView.setAdapter(adapter);
    }

    public String readJson() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.leaderboard);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
        return writer.toString();
    }

    public String composeLeaderboardmessage(List<Users> leaderBoard){
        String body = "";
        String message;
        for(int i = 0; i < leaderBoard.size(); i++){
            body += "#" + (i+1) + " " + leaderBoard.get(i).name + " " + leaderBoard.get(i).slackHandle + " " + leaderBoard.get(i).slackPoint + " Points" +"\n\n";
        }
        message = "HNGi7 LeaderBoard \n" + body;
        return message;
    }

}
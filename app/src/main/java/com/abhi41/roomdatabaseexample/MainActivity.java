package com.abhi41.roomdatabaseexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.abhi41.roomdatabaseexample.Adapter.TasksAdapter;
import com.abhi41.roomdatabaseexample.DataBase.AppDatabase;
import com.abhi41.roomdatabaseexample.Interface.TaskDao;
import com.abhi41.roomdatabaseexample.Model.HeroTask;
import com.abhi41.roomdatabaseexample.Model.HerosModel;
import com.abhi41.roomdatabaseexample.Model.Task;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floating_button_add;
    private RecyclerView recyclerView;
    private ArrayList<HerosModel> herosModelArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        floating_button_add = findViewById(R.id.floating_button_add);

        floating_button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
            }
        });

        getTask();
        //getWithoutList();
        apiFetchDetails();
        getAllHeroList();
    }

    private void getTask() {

        class GetTasks extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {

                List<Task> taskList = AppDatabase
                        .getInstance(getApplicationContext())
                        .taskDao()
                        .getAll();

                return taskList;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                TasksAdapter adapter = new TasksAdapter(MainActivity.this, tasks);
                recyclerView.setAdapter(adapter);
            }


        }
        GetTasks getTasks = new GetTasks();
        getTasks.execute();
    }

 /* private void getWithoutList(){
        class GetwithoutList extends AsyncTask<Void, Void, Integer> {
            @Override
            protected Integer doInBackground(Void... voids) {
                int task = DatabaseClient.getInstance(getApplicationContext())
                                            .getAppDatabase()
                                            .taskDao()
                                            .getUser(1);

                return task;
            }

            @Override
            protected void onPostExecute(Integer task) {
                super.onPostExecute(task);

                Log.d("task", String.valueOf(task));
            }
        }
      GetwithoutList GetwithoutList = new GetwithoutList();
      GetwithoutList.execute();
  }*/


    private void apiFetchDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://simplifiedcoding.net/demos/marvel/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    HerosModel herosModel = new HerosModel();

                    Gson gson = new Gson(); // adding json array into room database
                    Type teamListType = new TypeToken<ArrayList<HeroTask>>(){}.getType();
                    ArrayList<HeroTask> cityArrayList = gson.fromJson(response,teamListType);
                    insertHeroData(cityArrayList);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String strName = jsonObject.getString("name");
                        String realname = jsonObject.getString("realname");
                        String team = jsonObject.getString("team");
                        String imageurl = jsonObject.getString("imageurl");

                        herosModel.setName(strName);
                        herosModel.setRealname(realname);
                        herosModel.setImageurl(imageurl);

                        herosModelArrayList.add(herosModel);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(stringRequest);
    }


    private void insertHeroData(final ArrayList<HeroTask> heroTaskArrayList) {

        new AsyncTask<ArrayList<HeroTask>,Void,Void>()
        {

            @Override
            protected Void doInBackground(ArrayList<HeroTask>... arrayLists) {

                List<HeroTask>heroTaskArrayList =arrayLists[0];

                TaskDao taskDao = AppDatabase.getInstance(getApplicationContext()).taskDao();
                taskDao.truncateMyheros();
                taskDao.insertHeroListData(heroTaskArrayList);


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }.execute(heroTaskArrayList);

    }

    private void getAllHeroList()
    {
        class HeroList extends AsyncTask<Void,Void,List<HeroTask>>
        {

            @Override
            protected List<HeroTask> doInBackground(Void... voids) {

                List<HeroTask> heroTaskList = AppDatabase
                        .getInstance(getApplicationContext())
                        .taskDao().getAllHeroList();

                return heroTaskList;
            }

            @Override
            protected void onPostExecute(List<HeroTask> heroTaskList) {
                super.onPostExecute(heroTaskList);

                for (HeroTask heroTask : heroTaskList)
                {
                    Log.d("heroName",heroTask.getName());
                }

            }
        }
        HeroList heroList = new HeroList();
        heroList.execute();
    }


}
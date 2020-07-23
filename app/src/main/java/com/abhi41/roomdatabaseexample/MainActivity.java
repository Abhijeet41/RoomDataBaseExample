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
import com.abhi41.roomdatabaseexample.DatabaseClient.DatabaseClient;
import com.abhi41.roomdatabaseexample.Model.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floating_button_add;
    private RecyclerView recyclerView;

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
                startActivity(new Intent(MainActivity.this,AddTaskActivity.class));
            }
        });

        getTask();
        //getWithoutList();
    }

    private void getTask() {

        class GetTasks extends AsyncTask<Void,Void, List<Task>>{
            @Override
            protected List<Task> doInBackground(Void... voids) {

                List<Task> taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
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
}
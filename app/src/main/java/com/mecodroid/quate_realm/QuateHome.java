package com.mecodroid.quate_realm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.realm.Realm;
import io.realm.RealmResults;

public class QuateHome extends AppCompatActivity {
    Realm realm;
    QuateAdapter adapter;
    RealmResults<QuateModel> results;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quatehome);
        realm = Realm.getDefaultInstance();
        seuprecyclerview();
    }

    private void seuprecyclerview() {
        if (realm != null) {
            results = realm.where(QuateModel.class).findAll();
            recyclerView = findViewById(R.id.rv);
            adapter = new QuateAdapter(this, results);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } else {
            realm.close();
        }
    }

    public void add_notes(View view) {
        startActivity(new Intent(QuateHome.this, Quatesetting.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}

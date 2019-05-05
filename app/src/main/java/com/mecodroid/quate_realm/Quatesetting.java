package com.mecodroid.quate_realm;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class Quatesetting extends AppCompatActivity {
    Realm realm;
    EditText quatTitle, quatContent;
    boolean fromAdapter = false;
    int id;
    RealmResults<QuateModel> results;
    ImageView delt;
    String cont, tit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quatesetting);
        realm = Realm.getDefaultInstance();
        quatContent = findViewById(R.id.text_quate_content);
        quatTitle = findViewById(R.id.text_quate_title);
        delt = findViewById(R.id.imdelerte);
        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            fromAdapter = true;
            id = intent.getIntExtra("id", 0);
            results = realm.where(QuateModel.class).equalTo("id", id).findAll();
            for (QuateModel quate : results) {
                quatTitle.setText(quate.getQuateTitle());
                quatContent.setText(quate.getQuoteContent());
            }
        }
    }

    public void saved_notes(View view) {
        tit = quatTitle.getText().toString().trim();
        cont = quatContent.getText().toString().trim();
        if (fromAdapter) {
            if (!TextUtils.isEmpty(tit)) {
                for (QuateModel quate : results) {
                    realm.beginTransaction();
                    quate.setQuateTitle(tit);
                    quate.setQuoteContent(cont);
                    realm.commitTransaction();
                    finish();
                }
            } else {
                Toast.makeText(Quatesetting.this, "Please write Title", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (!TextUtils.isEmpty(tit)) {
                QuateModel quate = new QuateModel();
                quate.setId(getNextKey());
                quate.setQuateTitle(tit);
                quate.setQuoteContent(cont);
                realm.beginTransaction();
                realm.copyToRealm(quate);
                realm.commitTransaction();
                finish();
            } else {
                Toast.makeText(Quatesetting.this, "Please write Title", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void deleted_notes(View view) {
        if (fromAdapter) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Quote")
                    .setMessage("Are you sure to delete this Quote ?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delt.setEnabled(true);
                            realm.beginTransaction();
                            results.deleteAllFromRealm();
                            realm.commitTransaction();
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        } else {
            Toast.makeText(Quatesetting.this, "No Quote saved to delete", Toast.LENGTH_SHORT).show();
            delt.setEnabled(false);
        }
    }

    public void canceled_notes(View view) {
        finish();
    }

    public int getNextKey() {
        try {
            return realm.where(QuateModel.class).max("id").intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            return 0;
        }
    }
}
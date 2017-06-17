package com.developer.gavinejoyce.wedding;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ImageListing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_listing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b1 = getIntent().getExtras();
        String name="";
        if(b1!=null)
        {
            name = b1.getString("UserName");
        }

        ImageView img = (ImageView)findViewById(R.id.userphoto);

        Intent intent = getIntent();
        String image = intent.getStringExtra("UserPhoto");

        if(image!=null) {
            Uri uphoto = Uri.parse(image);
            Context context = img.getContext();
            Picasso.with(context).load(uphoto).into(img);
        }else{
            img.setImageDrawable(getResources().getDrawable(R.drawable.sample));
        }
        //Toast.makeText(this, ""+uphoto, Toast.LENGTH_SHORT).show();
        //img.setImageURI(uphoto);

        TextView t1 = (TextView)findViewById(R.id.user);
        t1.setText(name);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}

package com.developer.gavinejoyce.wedding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class ImageLisiting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_lisiting);
        Bundle b1 = getIntent().getExtras();
        String name="";
        if(b1!=null)
        {
            name = b1.getString("UserName");
        }

        TextView t1 = (TextView)findViewById(R.id.user);
        t1.setText(name);
        Toast.makeText(getApplicationContext(),"Welcome "+name,Toast.LENGTH_LONG).show();
    }
}

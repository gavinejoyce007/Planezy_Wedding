package com.developer.gavinejoyce.wedding;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button login;
    ImageButton cancel;
    FragmentManager fm = getFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    FragmentLoginOptions fragment = new FragmentLoginOptions();
    GoogleApiClient mGoogleApiClient;
    int RC_SIGN_IN = 4526;
    CallbackManager callbackManager;
    //GoogleSignInResult result;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.login_button);
        cancel = (ImageButton) findViewById(R.id.cancelbutton);
        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        LoginButton fbsignIn = (LoginButton) findViewById(R.id.fb_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        //signInButton.setSize(SignInButton.SIZE_STANDARD);


        callbackManager = CallbackManager.Factory.create();
        fbsignIn.setBackgroundDrawable(getResources().getDrawable(R.drawable.fblogin));
        fbsignIn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken atoken = loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(atoken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Bundle bFacebookData = getFacebookData(object);
                        if (bFacebookData != null) {
                            String fname = bFacebookData.getString("first_name");
                            String lname = bFacebookData.getString("last_name");
                            user = fname + lname;
                        }
                        Toast.makeText(getApplicationContext(), "+" + user, Toast.LENGTH_LONG).show();
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, this/* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
// perform setOnClickListener event on First Button
        //FrameLayout fl = (FrameLayout)findViewById(R.id.frameLay);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(R.layout.login_options_fragment);
                //loadFragment(fragment);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFragment(fragment);
            }
        });
    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        } catch (JSONException e) {
            Log.d("Facebook", "Error parsing JSON");
        }
        return null;
    }

    public void LoginFunc(View view){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
    }

    public void SignupFunc(View view){
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
    }

    private void loadFragment(Fragment fragment) {
        //getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,fragment).commit();
            //cancel.setVisibility(View.VISIBLE);
            ft.replace(R.id.frameLayout, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
    }

    public void deleteFragment(Fragment fragment)
    {
        cancel.setVisibility(View.GONE);
        ft.remove(fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
       // ft.commit();
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("Connection", "onConnectionFailed:" + connectionResult);
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("MainAct", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateUI(true,result);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false,result);
        }
    }
    private void updateUI(boolean sigIn,GoogleSignInResult result)
    {
        if(sigIn)
        {
            //GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto=null;
            Intent in = new Intent(MainActivity.this, ImageListing.class);
            try {
                personPhoto = acct.getPhotoUrl();
                in.putExtra("UserPhoto", personPhoto.toString());
            }catch(NullPointerException e)
            {
                e.printStackTrace();
            }
                Toast.makeText(getApplicationContext(), "Welcome " + personName, Toast.LENGTH_LONG).show();

                Bundle b1 = new Bundle();
                b1.putString("UserName", personName);

                in.putExtras(b1);
                startActivity(in);

        }
        else
        {
            Toast.makeText(getApplicationContext(),"SignIn Failure",Toast.LENGTH_LONG).show();
        }
    }

}

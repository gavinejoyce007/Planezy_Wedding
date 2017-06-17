package com.developer.gavinejoyce.wedding;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    private EditText username, emailid, phone, password;
    private TextInputLayout inputlayoutname,inputlayoutemail, inputlayoutphone, inputlayoutpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        inputlayoutname = (TextInputLayout) findViewById(R.id.layout_username);
        inputlayoutemail = (TextInputLayout)findViewById(R.id.layout_email);
        inputlayoutphone = (TextInputLayout)findViewById(R.id.layout_phonenumber) ;
        inputlayoutpassword = (TextInputLayout)findViewById(R.id.layout_password);
        username = (EditText)findViewById(R.id.username);
        emailid = (EditText)findViewById(R.id.emailid);
        phone = (EditText)findViewById(R.id.phone) ;
        password = (EditText)findViewById(R.id.password);
        username.addTextChangedListener(new MyTextWatcher(username));
        emailid.addTextChangedListener(new MyTextWatcher(emailid));
        phone.addTextChangedListener(new MyTextWatcher(phone));
        password.addTextChangedListener(new MyTextWatcher(password));
    }

    private boolean validateUserName(){
        if(username.getText().toString().trim().isEmpty()){
            inputlayoutname.setError(getString(R.string.error_invalid_username));
            requestFocus(username);
            return false;
        }
        else
            inputlayoutname.setErrorEnabled(false);
        return true;
    }

    private boolean validateEmailAddress() {
        String email = emailid.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputlayoutemail.setError(getString(R.string.error_invalid_email));
            requestFocus(emailid);
            return false;
        } else
            inputlayoutemail.setErrorEnabled(false);
        return true;
    }

    private static boolean isValidEmail(String email){
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validatePhone(){
        if(phone.getText().toString().trim().length()!=10){
            inputlayoutphone.setError(getString(R.string.error_invalid_phonenumber));
            requestFocus(phone);
            return false;
        }
        else
            inputlayoutphone.setErrorEnabled(false);
        return true;
    }

    private boolean validatePassword(){
        if(password.getText().toString().trim().isEmpty())
        {
            inputlayoutpassword.setError(getString(R.string.error_invalid_password));
            requestFocus(password);
            return false;
        }
        else
            inputlayoutpassword.setErrorEnabled(false);
        return true;
    }

    private void requestFocus(View view)
    {
        if(view.requestFocus()){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher{
        private View view;
        private MyTextWatcher(View view){
            this.view=view;
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count){
        }

        public void afterTextChanged(Editable s) {
            switch (view.getId()){
                case R.id.username:
                    validateUserName();
                    break;
                case R.id.emailid:
                    validateEmailAddress();
                    break;
                case R.id.phone:
                    validatePhone();
                    break;
                case R.id.password:
                    validatePassword();
                    break;
            }
        }
    }

    public void SignupSubmission(View view){
        if (validateEmailAddress() && validateUserName() && validatePhone()) {
            Intent intent = new Intent(this, ImageListing.class);
            startActivity(intent);
        }

    }
}

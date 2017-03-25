package com.mirego.cschat.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mirego.cschat.BuildConfig;
import com.mirego.cschat.CSChatApplication;
import com.mirego.cschat.Prefs;
import com.mirego.cschat.R;
import com.mirego.cschat.controller.RegisterController;
import com.mirego.cschat.models.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;

/**
 * Created by Carty on 2017-03-25.
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.login_root)
    ViewGroup root;

    @BindView(R.id.et_username)
    EditText etUsername;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.et_password2)
    EditText etPassword2;

    @BindView(R.id.et_url)
    EditText etUrl;

    @Inject
    RegisterController registerCon;

    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        ((CSChatApplication) getApplication()).component().inject(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.login_loading));

        if (BuildConfig.DEBUG) {
            etUsername.setText("horace");
            etPassword.setText("draught146");
            etPassword2.setText("draught146");
            etUrl.setText("https://qph.ec.quoracdn.net/main-qimg-c8781a4bb1f17e330b50cb35f851da05-c");
        }

    }

    @OnClick(R.id.btn_login_submit)
    void onLoginClicked() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if(!(etPassword.getText().toString().equals(etPassword2.getText().toString()))){
            Snackbar.make(root, R.string.register_error, LENGTH_SHORT).show();
        }else{
            progressDialog.show();
            registerCon.register(etUsername.getText().toString(), etPassword.getText().toString(), etUrl.getText().toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Consumer<User>() {
                        @Override
                        public void accept(@NonNull User user) throws Exception {
                            progressDialog.dismiss();
                            SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                            sharedPreferences.edit().putString(Prefs.KEY_USER_ID, user.getId()).apply();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            progressDialog.dismiss();
                            Snackbar.make(root, R.string.register_api_error, LENGTH_SHORT).show();
                        }
                    });
        }

    }

}

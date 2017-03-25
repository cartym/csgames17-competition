package com.mirego.cschat.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mirego.cschat.CSChatApplication;
import com.mirego.cschat.Prefs;
import com.mirego.cschat.R;
import com.mirego.cschat.controller.ConversationController;
import com.mirego.cschat.models.Conversation;
import com.mirego.cschat.models.User;
import com.mirego.cschat.viewdatas.ConversationViewData;

import java.util.List;

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
 * Created by Steve on 2017-03-25.
 */

public class NewConversationActivity extends BaseActivity {

    @BindView(R.id.nc_root)
    ViewGroup root;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.til_search)
    EditText tilSearch;

    @Inject
    ConversationController convCont;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_conversation);
//        ((CSChatApplication) getApplication()).component().inject(this);
//        ButterKnife.bind(this);

//        setSupportActionBar(toolbar);
//        toolbar.setTitle("Type in who to chat to");
//        configureConversationsRecyclerView();
    }

    @OnClick(R.id.button)
    public void onChatClick() {
        String user = tilSearch.getText().toString();
        if ((user != null) && !(user.equals(""))) {
            convCont.createConversation(user)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Consumer<List<ConversationViewData>>() {
                        @Override
                        public void accept(@NonNull List<ConversationViewData> convs) throws Exception {
//                            progressDialog.dismiss();
//                            SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
//                            sharedPreferences.edit().putString(Prefs.KEY_USER_ID, user.getId()).apply();
                            Snackbar.make(root, "It worked", LENGTH_SHORT).show();
//                            finish();
//                            startActivity(new Intent(LoginActivity.this, ConversationsActivity.class));
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
//                            progressDialog.dismiss();
                            Snackbar.make(root, R.string.login_error, LENGTH_SHORT).show();
                        }
                    });
        }
    }
}


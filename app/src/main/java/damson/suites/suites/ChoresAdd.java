package damson.suites.suites;

import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ChoresAdd extends AppCompatActivity {

    private EditText name_field;
    private EditText description_field;
    DBAddChoreRequest chore;
    public static List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_add);

        name_field = (EditText) findViewById(R.id.chores_list_add_Chore_Text);
        description_field = (EditText) findViewById(R.id.chores_list_add_Description_Text);

        //Button listeners
        Button addButton = (Button)findViewById(R.id.chores_list_add_Add_Button);
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                attemptAddItem();
            }
        });

        Button cancelButton = (Button)findViewById(R.id.chores_list_add_Cancel_Button);
        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                cancel();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    private void attemptAddItem(){
        //reset errors
        name_field.setError(null);
        description_field.setError(null);

        String name;
        String description;

        //Store values at time of add attempt
        if(name_field.getText().toString().length() != 0){
            name = name_field.getText().toString();
        }
        else{
            name = "";
        }
        if(description_field.getText().toString().length() != 0){
            description = description_field.getText().toString();
        }
        else{
            description = "";
        }

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(name)){
            name_field.setError("Please fill in the name of the chore");
            focusView = name_field;
            cancel = true;
        }
        if(TextUtils.isEmpty(description)){
            description_field.setError("Please fill in the description of the chore");
            focusView = description_field;
            cancel = true;
        }

        if(cancel) {
            focusView.requestFocus();
        }
        else{
            DBHelper help = new DBHelper(User.user);
            help.listUsersInASuite(Suite.suite.getId(), new AsyncResponseHandler<DBUserListResult>(){
               @Override
                public void onSuccess(DBUserListResult response, int statusCode, Header[] headers, byte[]errorResponse) {
                    ChoresAdd.users = response.getUserList();
               }
                @Override
                public void onFailure(int statusCode, Header[]headers, byte[] errorResponse, Throwable e){
                    return;
                }
                @Override
                public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e){
                    return;
                }
                @Override
                public void onFinish(){
                    return;
                }
            });
            chore = new DBAddChoreRequest(name, description, 0, users);
            help.addChoreToSuite(Suite.suite.getId(), chore, new AsyncResponseHandler<DBGenericResult>(){
                @Override
                public void onSuccess(DBGenericResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.chores_list_add_coordinator),
                            "You are not in the Suite you are putting items into", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    Log.e("ChoresAdd", "Not in suite.");
                }

                @Override
                public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                    // TODO: Set up a "please login again" page
                }
            });
        }
    }

    private void cancel(){
        finish();
    }
}

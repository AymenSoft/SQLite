package com.aymensoft.sqlite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.aymensoft.sqlite.SQLite.UsersDB;
import com.aymensoft.sqlite.adapters.UsersAdapter;
import com.aymensoft.sqlite.models.Users;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView LVUsers;
    EditText username;
    Button adduser, selectUsername;

    List<Users>users;
    UsersAdapter adapter;

    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LVUsers=(ListView)findViewById(R.id.lv_users);
        username=(EditText)findViewById(R.id.et_user);
        adduser=(Button)findViewById(R.id.btn_save);
        selectUsername=(Button)findViewById(R.id.btn_select_username);

        UsersDB usersDB = new UsersDB(this);
        usersDB.open();
        users = usersDB.GetAll();
        usersDB.close();
        adapter = new UsersAdapter(this, R.layout.item_users, users);
        LVUsers.setAdapter(adapter);

        selectUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersDB usersDB = new UsersDB(MainActivity.this);
                usersDB.open();
                users = usersDB.GetByUserName(username.getText().toString());
                usersDB.close();
                adapter = new UsersAdapter(MainActivity.this, R.layout.item_users, users);
                LVUsers.setAdapter(adapter);
            }
        });

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersDB usersDB = new UsersDB(MainActivity.this);
                usersDB.open();
                Users user = new Users();
                user.setUsername(username.getText().toString());
                usersDB.InsertTop(user);
                usersDB.close();
                users.add(user);
                adapter.notifyDataSetChanged();
            }
        });

        LVUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final AlertDialog.Builder alertDialogue = new AlertDialog.Builder(MainActivity.this);
                alertDialogue.setTitle("Delete");
                alertDialogue.setMessage("are you sure?");
                alertDialogue.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UsersDB db = new UsersDB(MainActivity.this);
                        db.open();
                        db.RemoveById(users.get(position).getUserid());
                        db.close();
                        users.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                alertDialogue.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialogue.show();
                return false;
            }
        });

        LVUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                userid=users.get(position).getUserid();
                UsersDB db = new UsersDB(MainActivity.this);
                db.open();
                Users user = new Users();
                user.setUsername(username.getText().toString());
                db.Update(user, userid);
                db.close();
                users.set(position,user);
                adapter.notifyDataSetChanged();
            }
        });

    }
}

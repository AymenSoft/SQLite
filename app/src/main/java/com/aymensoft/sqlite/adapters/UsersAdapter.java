package com.aymensoft.sqlite.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aymensoft.sqlite.R;
import com.aymensoft.sqlite.models.Users;

import java.util.List;

public class UsersAdapter extends ArrayAdapter<Users>{

    Context context;
    int resource;


    public UsersAdapter(Context context, int resource, List<Users> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        UserHolder holder = new UserHolder();
        if (view==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
            holder.username=(TextView)view.findViewById(R.id.username);
            view.setTag(holder);
        }else {
            holder=(UserHolder) view.getTag();
        }
        holder.username.setText(getItem(position).getUsername());
        return view;
    }

    class UserHolder{
        TextView username;
    }
}

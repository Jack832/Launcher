package com.example.test4.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bridgelabz4 on 29/12/15.
 */
public class Allapp extends Activity {
    private PackageManager packageManager;
    private List<Appinfo> app;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applist);
        loadapp();
        loadapplist();
        addclickl();
    }

    private void addclickl()
    {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent i = packageManager.getLaunchIntentForPackage(app.get(position).name.toString());
                Allapp.this.startActivity(i);
            }
        });
    }

    private void loadapplist()
    {
        list=(ListView)findViewById(R.id.app_item);
        ArrayAdapter<Appinfo> adapter= new ArrayAdapter<Appinfo>(this,R.layout.applist,app)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Viewholder ViewHold= null;
                if(convertView ==null)
                {
                    convertView=getLayoutInflater().inflate(R.layout.listitem,parent,false);
                    ViewHold=new Viewholder();
                    ViewHold.image=(ImageView)convertView.findViewById(R.id.icon);
                    ViewHold.label=(TextView)convertView.findViewById(R.id.label);
                    ViewHold.name=(TextView)convertView.findViewById(R.id.name);


                    convertView.setTag(ViewHold);
                }else
                {
                    ViewHold=(Viewholder)convertView.getTag();
                }
                Appinfo appinfo=app.get(position);
                if(appinfo != null)
                {
                    ViewHold.image.setImageDrawable(appinfo.icon);
                    ViewHold.name.setText(appinfo.name);
                    ViewHold.label.setText(appinfo.label);
                }
                return convertView;

            }
            final class Viewholder
            {
                ImageView image;
                TextView label;
                TextView  name;
            }
        };
        list.setAdapter(adapter);

    }

    private void loadapp()
    {
        packageManager =getPackageManager();
        app= new ArrayList<Appinfo>();
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> avalab= packageManager.queryIntentActivities(intent,0);
        for(ResolveInfo r1 :avalab)
        {
            Appinfo appinfo= new Appinfo();
            appinfo.label =r1.loadLabel(packageManager);
            appinfo.name=r1.activityInfo.packageName;
            appinfo.icon=r1.loadIcon(packageManager);
            app.add(appinfo);
        }
    }
}

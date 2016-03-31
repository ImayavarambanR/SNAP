package com.example.dhilip_25.helloworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Mahesh on 12/1/2015.
 */
public class ApplicationAdapter extends ArrayAdapter<ApplicationInfo> implements CompoundButton.OnCheckedChangeListener {
    private List<ApplicationInfo> appsList = null;
    private Context context;
    private PackageManager packageManager;
    SparseBooleanArray mCheckStates;
    CheckBox chkSelect;
    SharedPreferences.Editor editor;
    ApplicationInfo applicationInfo;
    int count;
    String Pname;
    AppInfoHolder holder;
    SharedPreferences appNamesOfSelected,sharedpref,appName;
    SharedPreferences.Editor editAppNamesOfSelected;
    int countOfSelected;

    public ApplicationAdapter(Context context, int textViewResourceId,
                              List<ApplicationInfo> appsList) {
        super(context, textViewResourceId, appsList);

        this.context = context;
        this.appsList = appsList;
        packageManager = context.getPackageManager();
        mCheckStates = new SparseBooleanArray();
        count=appsList.size();
    }

    @Override
    public int getCount() {
        return ((null != appsList) ? appsList.size() : 0);
    }

    @Override
    public ApplicationInfo getItem(int position) {
        return ((null != appsList) ? appsList.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SharedPreferences sharedPrefs = context.getSharedPreferences("shared",Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
        View view = convertView;
        holder=null;
        if (null == view) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.snippet_list_row, null);
            holder=new AppInfoHolder();
            holder.chkSelect = (CheckBox) view.findViewById(R.id.checkbox);
            holder.appName = (TextView) view.findViewById(R.id.app_name);
            holder.iconview = (ImageView) view.findViewById(R.id.app_icon);

            view.setTag(holder);
        }else{
            holder=(AppInfoHolder)view.getTag();
        }

        applicationInfo = appsList.get(position);
        if (null != applicationInfo) {
            holder.appName.setText(applicationInfo.loadLabel(packageManager));
            //holder.packageName.getText().toString();
            holder.iconview.setImageDrawable(applicationInfo.loadIcon(packageManager));
        }

        //Collections.sort(appsList, new ApplicationInfo.DisplayNameComparator(packageManager));
        holder.chkSelect.setTag(position);

        //View parentView = (View) view.getParent();
        //Pname=((TextView) parentView.findViewById(R.id.app_paackage)).getText().toString();
        //editor.putString("Package",Pname);
        holder.chkSelect.setChecked(sharedPrefs.getBoolean("CheckValue" + position, false));
        holder.chkSelect.setOnCheckedChangeListener(this);
        return view;
    }
    public boolean isChecked(int position){
        return mCheckStates.get(position, false);
    }

    public void setChecked(int position, boolean isChecked) {
        mCheckStates.put(position, isChecked);
    }

    public void toggle(int position) {
        setChecked(position, !isChecked(position));

    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView,
                                 boolean isChecked) {
        Boolean checkNameEquals=false;
        editor.putBoolean("CheckValue" + buttonView.getTag(), isChecked);
        if(isChecked){
            appNamesOfSelected = context.getSharedPreferences("appNamesOfSelected", Context.MODE_PRIVATE);
            sharedpref = context.getSharedPreferences("sharedpref", Context.MODE_PRIVATE);
            String name = sharedpref.getString("name"+buttonView.getTag()," ");
            countOfSelected = appNamesOfSelected.getInt("countOfSelected", -1);
            for(int i=0;i<=countOfSelected;i++){
                String checkName= appNamesOfSelected.getString("nameOfSelected"+i,"");
                if(name.equals(checkName)){
                    checkNameEquals = true;
                }
            }
            if(!checkNameEquals) {
                countOfSelected++;
                editAppNamesOfSelected = appNamesOfSelected.edit();
                editAppNamesOfSelected.putString("nameOfSelected" + countOfSelected, name);
                editAppNamesOfSelected.putInt("countOfSelected", countOfSelected);
                editAppNamesOfSelected.commit();
                String sample = appNamesOfSelected.getString("nameOfSelected" + countOfSelected, "");
                Log.e("select", "" + sample + " " + countOfSelected);
            }
        }else{
            appNamesOfSelected = context.getSharedPreferences("appNamesOfSelected", Context.MODE_PRIVATE);
            sharedpref = context.getSharedPreferences("sharedpref", Context.MODE_PRIVATE);
            String name = sharedpref.getString("name"+buttonView.getTag()," ");
            countOfSelected = appNamesOfSelected.getInt("countOfSelected", -1);
            for(int i=0;i<=countOfSelected;i++){
                String checkName= appNamesOfSelected.getString("nameOfSelected"+i,"");
                if(name.equals(checkName)){
                    int j=i+1;
                    for(;i<countOfSelected;i++){
                        String sample = appNamesOfSelected.getString("nameOfSelected" + j, "");
                        editAppNamesOfSelected = appNamesOfSelected.edit();
                        editAppNamesOfSelected.putString("nameOfSelected" + i, sample);
                        editAppNamesOfSelected.commit();
                        String SamName = appNamesOfSelected.getString("nameOfSelected" + i, "");
                        Log.e("unselect",""+SamName + i);
                        j++;
                    }
                    j--;
                    if(j==countOfSelected){
                        countOfSelected--;
                        editAppNamesOfSelected = appNamesOfSelected.edit();
                        editAppNamesOfSelected.putInt("countOfSelected",countOfSelected);
                        editAppNamesOfSelected.putString("nameOfSelected" + j, null);
                        editAppNamesOfSelected.commit();
                        Log.e("unselect1", "count of selected " + countOfSelected);
                        break;

                    }
                }
            }
        }
        editor.putBoolean("checkStatus",true);
        //editor.putString("name" + buttonView.getTag(), applicationInfo.packageName);
        // editor.putInt("Row" + buttonView.getTag(), (int) buttonView.getTag());
        editor.putInt("count",count);
        editor.commit();

        //mCheckStates.put((Integer) buttonView.getTag(), isChecked);

    }

    static class AppInfoHolder{
        CheckBox chkSelect;
        TextView appName;
        ImageView iconview;
    }
}


package com.singwai.currenttoptennews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.singwai.currenttoptennews.activity.NewsActivity;
import com.singwai.currenttoptennews.R;
import com.singwai.currenttoptennews.configutation.Configuration;

/**
 * Created by Singwai Chan on 2/1/15.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {


    private Configuration configuration;


    //Views
    private View rootView;
    private CheckBox mCheckBox;
    private NumberPicker mNumberPicker;
    private Spinner mSpinner;
    private Button mButton;
    private Button resetButton;

    //Grab all declare all non-view information here.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configuration = Configuration.get_instance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_setting, null);
        }

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        //Get All the views.
        mCheckBox = (CheckBox) rootView.findViewById(R.id.checkbox);
        mNumberPicker = (NumberPicker) rootView.findViewById(R.id.numberPickerAutoSwapTimer);
        mNumberPicker.setMaxValue(60);
        mNumberPicker.setMinValue(3);
        mSpinner = (Spinner) rootView.findViewById(R.id.spinnerNewsSection);

        mButton = (Button)rootView.findViewById(R.id.buttonGetNews);
        mButton.setOnClickListener(this);
        //Fill Configuration data;
        setConfigurationViews();

        return rootView;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonGetNews){
            //Save Configuration
            getConfigurationDataFromViews();
            Configuration.saveConfiguration();
            //Go to NewsActivity
            Intent i = new Intent(this.getActivity(), NewsActivity.class);
            this.getActivity().startActivity(i);
            //((HomeActivity) this.getActivity()).getLatestNews(configuration.getNewsSectionPosition());

        }
    }

    public void getConfigurationDataFromViews (){
        configuration.setAutoSwap(mCheckBox.isChecked());
        configuration.setAutoSwapTime(mNumberPicker.getValue());
        configuration.setNewsSectionPosition(mSpinner.getSelectedItemPosition());
    }

    public void setConfigurationViews() {
        Log.e("Filling date", " ");
        Configuration.print();
        mCheckBox.setChecked(configuration.getAutoSwap());
        mNumberPicker.setValue(configuration.getAutoSwapTime());
        mSpinner.setSelection(configuration.getNewsSectionPosition());
    }


}

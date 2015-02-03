package com.singwai.currenttoptennews.activity.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.singwai.currenttoptennews.MainActivity;
import com.singwai.currenttoptennews.R;
import com.singwai.currenttoptennews.configutation.Configuration;
import com.singwai.currenttoptennews.modal.AsyncGetNews;

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
        resetButton = (Button)rootView.findViewById(R.id.buttonResetNews);
        resetButton.setOnClickListener(this);
        fillConfiguration();

        return rootView;
    }

    public void fillConfiguration() {
        Log.e("Filling date", " ");
        Configuration.print();
        mCheckBox.setChecked(configuration.getAutoSwap());
        mNumberPicker.setValue(configuration.getAutoSwapTime());
        mSpinner.setSelection(configuration.getNewsSectionPosition());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonGetNews){
            //Save Configuration
            Configuration.print();
            getConfigurationDataFromViews();
            Configuration.saveConfiguration();
            Configuration.print();
            //Get News

            ((MainActivity) this.getActivity()).getLatestNews(configuration.getNewsSectionPosition());
        }
        else if (v.getId() == R.id.buttonResetNews){
            Log.e ("resetNews", "reset");
            ((MainActivity) this.getActivity()).removeNewsFragment();
        }
    }

    public void getConfigurationDataFromViews (){
        configuration.setAutoSwap(mCheckBox.isChecked());
        configuration.setAutoSwapTime(mNumberPicker.getValue());
        configuration.setNewsSectionPosition(mSpinner.getSelectedItemPosition());
    }


}

package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.google.android.material.slider.Slider;

public class SecondFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MyActivity";
    private static final String PALLET_ID = "Pallet ID";
    private static final String SLIDER_VALUE = "Slider value";
    Button mBtnBack, mBtnSet, mBtnMenu, mBtnSetWave, mBtnSetPalletID;
    View customLayout;
    AlertDialog mDialog;
    Slider mSlider;
    EditText mEdtPalletID;
    SharedPreferences prefs;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        onInitLayout(view);
        onInitData();
    }

    @SuppressLint("InflateParams")
    public void onInitLayout(View view) {
        customLayout = getLayoutInflater().inflate(R.layout.dialog_set_wave_strength, null, false);
        mSlider = customLayout.findViewById(R.id.slider);
        mBtnBack = customLayout.findViewById(R.id.btnBack);
        mBtnSet = customLayout.findViewById(R.id.btnSet);
        mBtnMenu = view.findViewById(R.id.btnMenu);
        mBtnSetWave = view.findViewById(R.id.btnSetRWave);
        mBtnSetPalletID = view.findViewById(R.id.btnSetPalletID);
        mEdtPalletID = view.findViewById(R.id.etPalletID);
        mBtnBack.setOnClickListener(this);
        mBtnSet.setOnClickListener(this);
        mBtnMenu.setOnClickListener(this);
        mBtnSetWave.setOnClickListener(this);
        mBtnSetPalletID.setOnClickListener(this);
    }

    public void onInitData() {
        float sliderValue = prefs.getFloat(SLIDER_VALUE, 0);
        mSlider.setValue(sliderValue);
        String storePalletID = prefs.getString(PALLET_ID, null);
        mEdtPalletID.setText(storePalletID);
    }

    public void onShowDialog(View customLayout) {
        if (customLayout.getParent() != null) {
            ((ViewGroup) customLayout.getParent()).removeView(customLayout); // <- fix error crash app when click btnSetWave second
        }
        AlertDialog.Builder mAlertBuilder = new AlertDialog.Builder(getContext());
        mAlertBuilder.setTitle(R.string.txt_title_set_wave_strength);
        mAlertBuilder.setView(customLayout);
        mDialog = mAlertBuilder.create();
        mDialog.show();
    }

    public void onSetValue() {
        SharedPreferences.Editor editor = prefs.edit();
        float valueSlider = mSlider.getValue();
        editor.putFloat(SLIDER_VALUE, valueSlider);
        editor.apply();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSet:
                onSetValue();
                mDialog.dismiss();
                break;
            case R.id.btnBack:
                mDialog.dismiss();
                break;
            case R.id.btnSetRWave:
                onShowDialog(customLayout);
                break;
            case R.id.btnSetPalletID:
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_ThreeFragment);
                break;
            case R.id.btnMenu:
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "save");
        prefs.edit().putString(PALLET_ID, mEdtPalletID.getText().toString()).commit();
    }
}
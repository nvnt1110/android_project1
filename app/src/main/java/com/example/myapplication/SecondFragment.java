package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.slider.Slider;

public class SecondFragment extends Fragment implements View.OnClickListener {
    Button mBtnBack, mBtnSet, mBtnMenu, mBtnSetWave;
    View customLayout;
    AlertDialog mDialog;
    Slider slider;

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
        onInitLayout(view);
    }

    public void onInitLayout(View view) {
        customLayout = getLayoutInflater().inflate(R.layout.dialog_set_wave_strength, null, false);
        slider = customLayout.findViewById(R.id.slider);
        mBtnBack = customLayout.findViewById(R.id.btnBack);
        mBtnSet = customLayout.findViewById(R.id.btnSet);
        mBtnMenu = view.findViewById(R.id.btnMenu);
        mBtnSetWave = view.findViewById(R.id.btnSetRWave);
        mBtnBack.setOnClickListener(this);
        mBtnSet.setOnClickListener(this);
        mBtnMenu.setOnClickListener(this);
        mBtnSetWave.setOnClickListener(this);
    }

    public void onShowDialog(View customLayout) {
        AlertDialog.Builder mAlertBuilder = new AlertDialog.Builder(getContext());
        mAlertBuilder.setTitle(R.string.txt_title_set_wave_strength);
        mAlertBuilder.setView(customLayout);
        mDialog = mAlertBuilder.create();
        mDialog.show();
    }

    public void onSetValue() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        float valueSlider = slider.getValue();
        editor.putFloat("valueSlider", valueSlider);
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
            case R.id.btnMenu:
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
                break;
        }
    }
}
package com.example.myapplication.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;

public class ThreeFragment extends Fragment implements View.OnClickListener {
    private static final String PALLET_ID = "Pallet ID";
    Button mBtnCancel, mBtnEnter;
    EditText mEdtPalletID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_three, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInitView(view);
    }

    public void onInitView(View view) {
        mBtnCancel = view.findViewById(R.id.btnCancel);
        mBtnEnter = view.findViewById(R.id.btnEnter);
        mEdtPalletID = view.findViewById(R.id.etPalletID);
        mBtnCancel.setOnClickListener(this);
        mBtnEnter.setOnClickListener(this);
    }

    public void onEnterPalletID() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
        String palletID = mEdtPalletID.getText().toString();
        editor.putString(PALLET_ID, palletID);
        editor.apply();
        NavHostFragment.findNavController(ThreeFragment.this)
                .navigate(R.id.action_ThreeFragment_to_SecondFragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                NavHostFragment.findNavController(ThreeFragment.this)
                        .navigate(R.id.action_ThreeFragment_to_SecondFragment);
                break;
            case R.id.btnEnter:
                onEnterPalletID();
                break;
        }
    }
}

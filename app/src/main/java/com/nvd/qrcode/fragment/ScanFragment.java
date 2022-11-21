package com.nvd.qrcode.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.nvd.qrcode.R;
import com.nvd.qrcode.activity.ActivityQrResultCode;

public class ScanFragment extends Fragment {

    View view;
    private CodeScanner mCodeScanner;
    private String strResultQr;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_scan, container, false);


        CodeScannerView scannerView = view.findViewById(R.id.scanner_view);




        mCodeScanner = new CodeScanner(requireContext(), scannerView);

        mCodeScanner.setDecodeCallback(result -> requireActivity().runOnUiThread(() -> {

            strResultQr = result.getText();

            Intent intent = new Intent(getContext(), ActivityQrResultCode.class);
            intent.putExtra("resultQr", strResultQr);
            startActivity(intent);
        }));



        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());




        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}

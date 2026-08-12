package com.example.skycapitalcarrentalapplication.ui;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowMetrics;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.skycapitalcarrentalapplication.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link BottomSheetDialogFragment} subclass.
 */
public class ContactFragment extends BottomSheetDialogFragment {


    public ContactFragment() {
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.fragment_contact, viewGroup, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            // target the default container in Material Bottom Sheet
            FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);

            if (bottomSheet != null && getActivity() != null) {
                BottomSheetBehavior<FrameLayout> bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
                WindowMetrics windowMetrics = getActivity().getWindowManager().getCurrentWindowMetrics();
                int heightPixels = windowMetrics.getBounds().height();

                // set the target height directly on the behavior's peek height
                int targetHeight = (int) (heightPixels * 0.60);
                bottomSheetBehavior.setPeekHeight(targetHeight);

                // Apply layout params using a post-runnable to avoid stuttering
                bottomSheet.post(() -> {
                    androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams params = (androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();

                    params.height = targetHeight;

                    // Setting the ContactFragment sheet display margin
                    int marginInPixels = (int) (16 * getActivity().getResources().getDisplayMetrics().density);
                    params.leftMargin = marginInPixels;
                    params.rightMargin = marginInPixels;

                    bottomSheet.setLayoutParams(params);

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                });
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // handle when the user clicks on the phone >>> opens up the dialer
        view.findViewById(R.id.contactPhone).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+18000001234"));
            startActivity(intent);
        });

        // handle when the user clicks on email >>> opens the user's email app, composing to this address
        view.findViewById(R.id.contactEmail).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:skycapitalcarrental@email.com"));
            startActivity(intent);
        });

        // handle when the user clicks on instagram >>> TODO: set up the action for instagram
        view.findViewById(R.id.contactInstagram).setOnClickListener(v -> dismiss());
    }
}
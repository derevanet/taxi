package net.dereva.taxi.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.dereva.taxi.R;
import net.dereva.taxi.interfaces.NoInternetDialogListener;

public class NoInternetDialog extends DialogFragment {

    private NoInternetDialogListener internetDialogListener;
    private CardView logo;
    private TextView title;
    private LinearLayout body;
    private Button button;

    public void setInternetDialogListener(NoInternetDialogListener internetDialogListener) {
        this.internetDialogListener = internetDialogListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            internetDialogListener = (NoInternetDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement NoInternetDialogListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_no_internet, null);

        logo = view.findViewById(R.id.dlg_no_connection_logo);
        title = view.findViewById(R.id.dlg_no_connection_title);
        body = view.findViewById(R.id.dlg_no_connection_body);
        button = view.findViewById(R.id.dlg_no_connection_btn);

        button = view.findViewById(R.id.dlg_no_connection_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                internetDialogListener.onDialogRetryInternetConnectionClick();
            }
        });

        setAnimations();

        return view;

    }

    private void setAnimations() {
        //total duration 2.5 sec

        logo.setAlpha(0);
        logo.animate()
                .alpha(1)
                .setDuration(1000);

        title.setAlpha(0);
        title.animate()
                .setStartDelay(500)
                .alpha(1)
                .setDuration(750);

        body.setAlpha(0);
        body.animate()
                .setStartDelay(1000)
                .alpha(1)
                .setDuration(1500);

        button.setTranslationY(1000);
        button.setAlpha(0);
        button.animate()
                .setStartDelay(1500)
                .alpha(1)
                .translationY(0)
                .setDuration(1000);
    }

}

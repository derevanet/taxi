package net.dereva.taxi.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import net.dereva.taxi.R;
import net.dereva.taxi.interfaces.NoInternetDialogListener;

public class NoInternetDialog extends DialogFragment {

    private NoInternetDialogListener internetDialogListener;

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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_no_internet, null))
                .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        internetDialogListener.onDialogRetryInternetConnectionClick();
                    }
                });
        return builder.create();
    }

}

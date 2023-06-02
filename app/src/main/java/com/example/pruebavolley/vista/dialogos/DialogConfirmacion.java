package com.example.pruebavolley.vista.dialogos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.pruebavolley.R;

public class DialogConfirmacion extends DialogFragment{
    private int textTitulo;
    private int textMensaje;

    public void setTitulo(int titulo) {
        textTitulo = titulo;
    }

    public void setMensaje(int mensaje) {
        textMensaje = mensaje;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        if (getActivity() != null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(textTitulo);
            builder.setMessage(textMensaje);
            builder.setPositiveButton(R.string.op_aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.onDlgConfirmacionPositiveClick(DialogConfirmacion.this);
                }
            });
            builder.setNegativeButton(R.string.op_cancelar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.onDlgConfirmacionNegativeClick(DialogConfirmacion.this);
                }
            });
            return builder.create();
        } else {
            return super.onCreateDialog(savedInstanceState);
        }
    }

    public interface DlgConfirmacionListener {
        void onDlgConfirmacionPositiveClick(DialogFragment dialog);

        void onDlgConfirmacionNegativeClick(DialogFragment dialog);
    }

    private DlgConfirmacionListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the DlgConfirmacionListener so we can send events to the host
            mListener = (DlgConfirmacionListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString() + " must implement DlgConfirmacionListener");
        }
    }
}

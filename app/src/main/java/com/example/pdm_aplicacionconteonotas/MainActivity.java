package com.example.pdm_aplicacionconteonotas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etNumeroEstudiantes;
    private CheckBox cBmostrarConteoEstudiantesPerdieron;

    private RadioButton rBMostrarNotaDefinitiva;
    private TextView tvResultado;
    private ArrayList<Integer> estudiantesQuePerdieron = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumeroEstudiantes = findViewById(R.id.etNumeroEstudiantes);
        cBmostrarConteoEstudiantesPerdieron = findViewById(R.id.cBmostrarConteoEstudiantesPerdieron);
        rBMostrarNotaDefinitiva = findViewById(R.id.rBMostrarNotaDefinitiva);
        tvResultado = findViewById(R.id.tvResultado);
    }

    public void calcularNotaDefinitiva(View view) {
        tvResultado.setText("");

        String numeroEstudiantesTxt = etNumeroEstudiantes.getText().toString();

        if (!numeroEstudiantesTxt.isEmpty()) {
            int numeroEstudiantes = Integer.parseInt(numeroEstudiantesTxt);

            if (numeroEstudiantes > 0) {
                mostrarDialogoNotas(1);
            } else {
                tvResultado.setText("Ingrese un número válido de estudiantes.");
            }
        } else {
            tvResultado.setText("El campo de número de estudiantes está vacío.");
        }
    }

    private void mostrarDialogoNotas(final int numeroEstudiante) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ingrese notas del estudiante " + numeroEstudiante);
        View view = getLayoutInflater().inflate(R.layout.dialog_notas, null);
        final EditText etNota1Dialog = view.findViewById(R.id.etNota1Dialog);
        final EditText etNota2Dialog = view.findViewById(R.id.etNota2Dialog);
        final EditText etNota3Dialog = view.findViewById(R.id.etNota3Dialog);
        final EditText etNota4Dialog = view.findViewById(R.id.etNota4Dialog);
        builder.setView(view);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                double nota1 = Double.parseDouble(etNota1Dialog.getText().toString());
                double nota2 = Double.parseDouble(etNota2Dialog.getText().toString());
                double nota3 = Double.parseDouble(etNota3Dialog.getText().toString());
                double nota4 = Double.parseDouble(etNota4Dialog.getText().toString());

                if (nota1 < 1 || nota1 > 5 || nota2 < 1 || nota2 > 5 || nota3 < 1 || nota3 > 5 || nota4 < 1 || nota4 > 5) {
                    tvResultado.setText("Las notas deben estar entre 1 y 5.");
                    return;
                }

                double notaFinal = (nota1 * 0.20) + (nota2 * 0.30) + (nota3 * 0.15) + (nota4 * 0.35);

                if (rBMostrarNotaDefinitiva.isChecked()) {
                    tvResultado.append("\nLa nota definitiva del estudiante " + numeroEstudiante + " es: " + String.format("%.2f", notaFinal));
                }

                if (notaFinal < 3.0) {
                    tvResultado.append("\nEste estudiante perdió la materia.");
                    estudiantesQuePerdieron.add(numeroEstudiante);
                }

                if (numeroEstudiante < Integer.parseInt(etNumeroEstudiantes.getText().toString())) {
                    mostrarDialogoNotas(numeroEstudiante + 1);
                } else {
                    if ( cBmostrarConteoEstudiantesPerdieron.isChecked()) {
                        mostrarConteoEstudiantesPerdieron();
                    }
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    private void mostrarConteoEstudiantesPerdieron() {
        tvResultado.append("\nNúmero de estudiantes que perdieron: " + estudiantesQuePerdieron.size());
    }
}

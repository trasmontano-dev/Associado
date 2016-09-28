package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CentralDeAtendimentoActivity extends AppCompatActivity {

    private LinearLayout layAtendimento;
    private LinearLayout layRedeCredenciada;
    private LinearLayout layOuvidoria;
    private LinearLayout layDeficienteAuditivo;
    private TextView tvAtendimento;
    private TextView tvDeficienteAuditivo;
    private TextView tvRedeCredenciada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central_de_atendimento);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);

        layAtendimento = (LinearLayout)findViewById(R.id.layAtendimento);
        layRedeCredenciada = (LinearLayout)findViewById(R.id.layRedeCredenciada);
        layOuvidoria = (LinearLayout)findViewById(R.id.layOuvidoria);
        layDeficienteAuditivo = (LinearLayout)findViewById(R.id.layDeficienteAuditivo);
        tvAtendimento = (TextView)findViewById(R.id.tvAtendimento);
        tvDeficienteAuditivo = (TextView)findViewById(R.id.tvDeficienteAuditivo);
        tvRedeCredenciada = (TextView)findViewById(R.id.tvRedeCredenciada);

        layAtendimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CentralDeAtendimentoActivity.this, EmailActivity.class);
                i.putExtra("tipo", "Atendimento");
                i.putExtra("email", tvAtendimento.getText());
                startActivity(i);
            }
        });

        layRedeCredenciada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CentralDeAtendimentoActivity.this, EmailActivity.class);
                i.putExtra("tipo", "Atendimento");
                i.putExtra("email", tvRedeCredenciada.getText());
                startActivity(i);
            }
        });

        layOuvidoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        layDeficienteAuditivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CentralDeAtendimentoActivity.this, EmailActivity.class);
                i.putExtra("tipo", "Deficiente Auditivo");
                i.putExtra("email", tvDeficienteAuditivo.getText());
                startActivity(i);
            }
        });
    }
}

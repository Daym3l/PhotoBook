package daym3l.photobook.com.photobook.about;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import daym3l.photobook.com.photobook.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_about_us);
        LinearLayout telefono = (LinearLayout) findViewById(R.id.ll_telefono);
        LinearLayout correo = (LinearLayout) findViewById(R.id.ll_correo);
        LinearLayout direccion = (LinearLayout) findViewById(R.id.ll_direccion);

        telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marcarNumero();
            }
        });

        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmail();
            }
        });

        direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void openEmail() {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("vnd.android.cursor.item/email");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getString(R.string.correo)});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "PhotoBook");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Gracias por preferirnos...");
        startActivity(Intent.createChooser(emailIntent, "Seleccione aplicación de correo"));

    }

    private void marcarNumero() {

        startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + getString(R.string.telf))));
    }

}
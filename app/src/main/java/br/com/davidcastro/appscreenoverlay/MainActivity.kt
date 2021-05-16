package br.com.davidcastro.appscreenoverlay

import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var mParams : WindowManager.LayoutParams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //verificando se o app tem permissão do usuário para sobreposição de tela
        //checking if the app has user permission to screen overlay
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, 12345)
            }
        }

        // definindo os parâmetros de layout para ser usado no popup
        // defining the layout parameters to be used in the popup
        mParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )

        //adicionando o popup como um novo layout no contexto e exibindo
        //adding the popup as a new layout in the context and displaying
        val popupView = LayoutInflater.from(this).inflate(R.layout.popup_layout, null)
        val wm = this.getSystemService(WINDOW_SERVICE) as WindowManager

        //abrir o popup
        //open the popup
        this.findViewById<Button>(R.id.btnOpen).setOnClickListener {
            wm.addView(popupView,mParams)
        }

        // usando o popupView como contexto para conseguir acessar o botão que está no layout dele
        // using popupView as a context to be able to access the button that is on its layout
        popupView.findViewById<Button>(R.id.btnClose).setOnClickListener {
            wm.removeView(popupView)
        }


    }

}
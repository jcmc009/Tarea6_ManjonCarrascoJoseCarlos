@file:Suppress("DEPRECATION")

package com.example.tarea6_manjoncarrascojosecarlos

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.tarea6_manjoncarrascojosecarlos.databinding.FragmentMapaBinding
import org.maplibre.android.MapLibre
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.annotations.MarkerOptions
import org.maplibre.android.annotations.IconFactory
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import android.annotation.SuppressLint
import org.maplibre.android.location.LocationComponentActivationOptions
import org.maplibre.android.location.modes.CameraMode
import org.maplibre.android.location.modes.RenderMode
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.Style

class MapaFragment : Fragment() {

    private var _binding: FragmentMapaBinding? = null
    private val binding get() = _binding!!
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Si cualquier permiso es aceptado (preciso o aproximado), continuamos
        if (permissions.values.any { it }) {
            cargarConfiguracionMapa()
        } else {
            gestionErrorPermiso()
        }
    }

    private fun checkLocationPermission() {
        val fine = Manifest.permission.ACCESS_FINE_LOCATION
        val coarse = Manifest.permission.ACCESS_COARSE_LOCATION

        when {
            // Caso A: El usuario ya aceptó el permiso previamente
            ContextCompat.checkSelfPermission(
                requireContext(),
                coarse
            ) == PackageManager.PERMISSION_GRANTED -> {
                cargarConfiguracionMapa()
            }

            // Caso B: El usuario rechazó antes; explicamos la necesidad
            shouldShowRequestPermissionRationale(fine) -> {
                mostrarDialogoExplicativo()
            }

            // Caso C: Primera solicitud: pedimos ambos niveles (Normativa de Android)
            else -> {
                locationPermissionRequest.launch(arrayOf(fine, coarse))
            }
        }
    }

    private fun mostrarDialogoExplicativo() {
        // Muestra un mensaje explicando por qué necesitas el permiso
        Toast.makeText(
            requireContext(),
            "La gincana de Astro Bot necesita tu ubicación para encontrar los núcleos.",
            Toast.LENGTH_LONG
        ).show()

        // Después de explicarlo, volvemos a lanzar la petición
        val fine = Manifest.permission.ACCESS_FINE_LOCATION
        val coarse = Manifest.permission.ACCESS_COARSE_LOCATION
        locationPermissionRequest.launch(arrayOf(fine, coarse))
    }

    private fun gestionErrorPermiso() {
        // El usuario ha denegado el permiso por completo
        Toast.makeText(
            requireContext(),
            "Sin ubicación no puedes jugar. Ve a Ajustes para darle permiso a la app.",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 1. Inicializar el motor SIEMPRE antes de inflar la vista
        MapLibre.getInstance(requireContext())

        // 2. Inflar la vista usando ViewBinding
        _binding = FragmentMapaBinding.inflate(inflater, container, false)

        // 3. Notificar al mapa que se ha creado
        binding.mapView.onCreate(savedInstanceState)

        // 4. Configurar el mapa
        // cargarConfiguracionMapa()
        checkLocationPermission()

        return binding.root
    }

    private fun cargarConfiguracionMapa() {
        binding.mapView.getMapAsync { map ->

            val style =
                "https://api.maptiler.com/maps/landscape-v4/style.json?key=xbbWXbxyIVv62NRTuWX2"

            map.setStyle(style) { loadedStyle ->
                habilitarUbicacionEnMapa(map, loadedStyle)

                map.setOnMarkerClickListener { marker ->
                    mostrarDialogoActividad(marker.title)
                    true
                }
                // Preparar icono personalizado

                // Obtener la imagen original
                val drawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.marcador)
                val bitmapOriginal = (drawable as BitmapDrawable).bitmap

                // Redimensionar el Bitmap
                val anchuraDeseada = 40
                val alturaDeseada = 40
                val bitmapRedimensionado =
                    Bitmap.createScaledBitmap(bitmapOriginal, anchuraDeseada, alturaDeseada, false)

                //Crear el icono
                val iconFactory = IconFactory.getInstance(requireContext())
                val customIcon = iconFactory.fromBitmap(bitmapRedimensionado)

                // Añadir las coordenadas y los marcadores

                // 1. Linares (Jaén)
                val linares = LatLng(38.0942, -3.6308)
                map.addMarker(MarkerOptions().position(linares).title("Linares").icon(customIcon))

                // 2. Sevilla
                val sevilla = LatLng(37.3891, -5.9845)
                map.addMarker(MarkerOptions().position(sevilla).title("Sevilla").icon(customIcon))

                // 3. Málaga
                val malaga = LatLng(36.7213, -4.4214)
                map.addMarker(MarkerOptions().position(malaga).title("Málaga").icon(customIcon))

                // 4. Córdoba
                val cordoba = LatLng(37.8882, -4.7794)
                map.addMarker(MarkerOptions().position(cordoba).title("Córdoba").icon(customIcon))

                // 5. Granada
                val granada = LatLng(37.1773, -3.5986)
                map.addMarker(MarkerOptions().position(granada).title("Granada").icon(customIcon))

                // 6. Almería
                val almeria = LatLng(36.8340, -2.4637)
                map.addMarker(MarkerOptions().position(almeria).title("Almería").icon(customIcon))

                // 7. Huelva
                val huelva = LatLng(37.2614, -6.9447)
                map.addMarker(MarkerOptions().position(huelva).title("Huelva").icon(customIcon))

                // 8. Cádiz
                val cadiz = LatLng(36.5271, -6.2886)
                map.addMarker(MarkerOptions().position(cadiz).title("Cádiz").icon(customIcon))

                // 9. Jaén
                val jaen = LatLng(37.7692, -3.7903)
                map.addMarker(MarkerOptions().position(jaen).title("Jaén").icon(customIcon))

                // 10. Jerez de la Frontera
                val jerez = LatLng(36.6850, -6.1260)
                map.addMarker(
                    MarkerOptions().position(jerez).title("Jerez de la Frontera").icon(customIcon)
                )

                // Mover cámara a Linares
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(linares, 7.0)
                )
                map.setOnMarkerClickListener { marker ->
                    mostrarDialogoActividad(marker.title)
                    true // Retornamos true para indicar que hemos consumido el evento
                }
            }

        }

    }

    @SuppressLint("MissingPermission")
    private fun habilitarUbicacionEnMapa(map: MapLibreMap, style: Style) {
        // 1. Obtenemos el componente de ubicación del mapa
        val locationComponent = map.locationComponent

        // 2. Lo configuramos y lo activamos con el estilo actual
        val activationOptions =
            LocationComponentActivationOptions.builder(requireContext(), style).build()
        locationComponent.activateLocationComponent(activationOptions)

        // 3. ¡Hacemos visible el punto azul!
        locationComponent.isLocationComponentEnabled = true

        // 4. (Opcional) Modo de cámara: TRACKING hace que la cámara siga al usuario.
        // Puedes comentarlo si prefieres que la cámara se quede en Linares (Zoom 7.0) como la tenías.
        locationComponent.cameraMode = CameraMode.TRACKING

        // 5. Modo de renderizado: COMPASS muestra la flechita indicando hacia dónde miras
        locationComponent.renderMode = RenderMode.COMPASS
        binding.switchUbicacion.setOnCheckedChangeListener { _, isChecked ->
            // Si el usuario cambia el interruptor, encendemos o apagamos el punto azul
            locationComponent.isLocationComponentEnabled = isChecked

            // Mensaje opcional para que el usuario sepa qué ha pasado
            val mensaje = if (isChecked) "Ubicación activada" else "Ubicación oculta"
            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarDialogoActividad(nombreCiudad: String?) {
        // Si por algún motivo el nombre de la ciudad es nulo, cancelamos
        if (nombreCiudad == null) return

        val preferencias =
            requireActivity().getSharedPreferences("GincanaMemoria", Context.MODE_PRIVATE)
        val yaCompletado = preferencias.getBoolean(nombreCiudad, false)

        if (yaCompletado) {
            Toast.makeText(
                requireContext(),
                "¡Ya has completado $nombreCiudad!",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // BUSCAMOS LOS DATOS ESPECÍFICOS DE ESTA CIUDAD
        // Si por algún error no existe en el diccionario, usamos una misión genérica de respaldo
        val misionActual = diccionarioMisiones[nombreCiudad] ?: DatosMision(
            "Misión desconocida. Localiza el núcleo genérico.",
            "1234"
        )

        val dialogView = layoutInflater.inflate(R.layout.dialog_actividad, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val tvTitulo = dialogView.findViewById<TextView>(R.id.tvTituloDialog)
        val tvEnunciado = dialogView.findViewById<TextView>(R.id.tvEnunciadoDialog)
        val etContrasena = dialogView.findViewById<EditText>(R.id.contrasenia)
        val btnFinalizar = dialogView.findViewById<Button>(R.id.btnFinalizar)

        //PERSONALIZAMOS LA VENTANA
        tvTitulo.text = "Misión en $nombreCiudad"
        tvEnunciado.text = misionActual.enunciado // Ponemos el texto único de la ciudad

        btnFinalizar.setOnClickListener {
            val contrasenaIntroducida =
                etContrasena.text.toString().trim() // .trim() quita espacios accidentales

            //  COMPROBAMOS CONTRA LA CONTRASEÑA ÚNICA DE LA CIUDAD
            // Usamos equals(..., ignoreCase = true) para que dé igual si escriben en mayúsculas o minúsculas
            if (contrasenaIntroducida.equals(misionActual.contrasena, ignoreCase = true)) {

                preferencias.edit().putBoolean(nombreCiudad, true).apply()
                Toast.makeText(
                    requireContext(),
                    "¡Núcleo de $nombreCiudad recuperado!",
                    Toast.LENGTH_LONG
                ).show()
                dialog.dismiss()

            } else {
                etContrasena.error = "Contraseña incorrecta. ¡Sigue buscando!"
            }
        }

        dialog.show()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Es vital destruir el mapa y vaciar el binding al destruir el fragmento
        binding.mapView.onDestroy()
        _binding = null
    }

    // Creamos una estructura para guardar la información de cada parada
    data class DatosMision(val enunciado: String, val contrasena: String)

    // Diccionario con las misiones tematizadas de Astro Bot para cada ciudad
    private val diccionarioMisiones = mapOf(
        "Linares" to DatosMision(
            "¡SOS! Un Bot minero está atrapado en el Pozo de San Vicente tras un ataque enemigo. Revisa el escáner de la nave nodriza para obtener el código de anulación del escudo.",
            "BOTMINERO99"
        ),
        "Sevilla" to DatosMision(
            "Los alienígenas han robado una pieza de la PS5 y la han ocultado en lo alto de la Giralda. Usa el DualSense para sentir la vibración y encontrar la clave holográfica.",
            "DUALSENSE5"
        ),
        "Málaga" to DatosMision(
            "Hemos detectado lecturas de un núcleo de memoria perdido en la Alcazaba. Resuelve el puzle de plataformas de la muralla para desbloquear el acceso.",
            "NUCLEOAZUL"
        ),
        "Córdoba" to DatosMision(
            "¡Alerta! Un jefe enemigo custodia una pieza del mando en la Mezquita. Esquiva sus ataques, busca su punto débil y extrae la contraseña de su placa base.",
            "JEFEDERROTADO"
        ),
        "Granada" to DatosMision(
            "Un grupo de Bots se ha escondido en los jardines de la Alhambra tras la caída de la nave. Sigue el rastro de las monedas de oro para encontrar el terminal de rescate.",
            "OROASTROBOT"
        ),
        "Almería" to DatosMision(
            "El desierto de Tabernas es perfecto para probar los nuevos propulsores láser. Completa el circuito de aros en tiempo récord para revelar el código del panel.",
            "LASERMAX"
        ),
        "Huelva" to DatosMision(
            "¡Cuidado! Hay enemigos pegajosos en el Muelle de las Carabelas. Usa el cañón de agua del mando para limpiar la zona y leer el código en la cubierta del barco.",
            "AGUALIMPIA"
        ),
        "Cádiz" to DatosMision(
            "La CPU principal está dañada y necesita refrigeración. Sumérgete en las aguas de Cádiz usando el traje de buceo de Astro y escanea la baliza submarina.",
            "BUCEOPS5"
        ),
        "Jaén" to DatosMision(
            "El Castillo de Santa Catalina esconde una sala de trofeos secreta de PlayStation. Rompe las cajas de cristal para encontrar el cartucho de memoria con la clave.",
            "TROFEOPLATINO"
        ),
        "Jerez de la Frontera" to DatosMision(
            "Hemos detectado una señal de auxilio proveniente de una bodega. ¡Es un Bot VIP de un juego clásico! Usa el gancho para rescatarlo y pídele el código de acceso.",
            "BOTVIPRESCATE"
        )
    )
}

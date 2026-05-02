@file:Suppress("DEPRECATION")

package com.example.tarea6_manjoncarrascojosecarlos

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

class MapaFragment : Fragment() {

    private var _binding: FragmentMapaBinding? = null
    private val binding get() = _binding!!

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
        cargarConfiguracionMapa()

        return binding.root
    }

    private fun cargarConfiguracionMapa() {
        binding.mapView.getMapAsync { map ->
            val style =
                "https://api.maptiler.com/maps/landscape-v4/style.json?key=xbbWXbxyIVv62NRTuWX2"

            map.setStyle(style) {
                // Coordenadas usando la clase de MapLibre
                // 1. Linares (Jaén)
                val linares = LatLng(38.0942, -3.6308)
                map.addMarker(MarkerOptions().position(linares).title("Linares"))

                // 2. Sevilla
                val sevilla = LatLng(37.3891, -5.9845)
                map.addMarker(MarkerOptions().position(sevilla).title("Sevilla"))

                // 3. Málaga
                val malaga = LatLng(36.7213, -4.4214)
                map.addMarker(MarkerOptions().position(malaga).title("Málaga"))

                // 4. Córdoba
                val cordoba = LatLng(37.8882, -4.7794)
                map.addMarker(MarkerOptions().position(cordoba).title("Córdoba"))

                // 5. Granada
                val granada = LatLng(37.1773, -3.5986)
                map.addMarker(MarkerOptions().position(granada).title("Granada"))

                // 6. Almería
                val almeria = LatLng(36.8340, -2.4637)
                map.addMarker(MarkerOptions().position(almeria).title("Almería"))

                // 7. Huelva
                val huelva = LatLng(37.2614, -6.9447)
                map.addMarker(MarkerOptions().position(huelva).title("Huelva"))

                // 8. Cádiz
                val cadiz = LatLng(36.5271, -6.2886)
                map.addMarker(MarkerOptions().position(cadiz).title("Cádiz"))

                // 9. Jaén
                val jaen = LatLng(37.7692, -3.7903)
                map.addMarker(MarkerOptions().position(jaen).title("Jaén"))

                // 10. Jerez de la Frontera
                val jerez = LatLng(36.6850, -6.1260)
                map.addMarker(MarkerOptions().position(jerez).title("Jerez de la Frontera"))

                // Mover cámara a Linares (Zoom 7.0 para ver Andalucía)
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(linares, 7.0)
                )
            }
        }
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
}
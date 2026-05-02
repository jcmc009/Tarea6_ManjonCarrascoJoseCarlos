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
                val madrid = LatLng(40.41, -3.70)

                // Añadir marcador usando la clase de MapLibre
                map.addMarker(
                    MarkerOptions()
                        .position(madrid)
                        .title("Madrid")
                )

                // Mover cámara (Nota: el zoom suele pasarse como Double, he puesto 12.0)
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(madrid, 12.0)
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
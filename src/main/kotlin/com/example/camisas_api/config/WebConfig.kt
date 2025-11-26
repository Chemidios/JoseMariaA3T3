package com.example.camisas_api.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**") // Aplica la configuración a todas las rutas bajo /api/
            .allowedOrigins(
                "http://localhost:8100",      // Tu frontend de Ionic/Angular en desarrollo
                "https://*.onrender.com"      // Permite cualquier subdominio de tu frontend desplegado en Render
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
            .allowedHeaders("*") // Todas las cabeceras están permitidas
            .allowCredentials(true) // Permite el envío de credenciales (ej. cookies)
    }
}

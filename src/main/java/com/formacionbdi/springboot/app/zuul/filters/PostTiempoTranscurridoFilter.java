package com.formacionbdi.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
/**
 * En esta clase definimos los métodos que implementan la clase que hereda ZuulFilter
 * En el metodo filterType, podemos observar que indicamos que es de tipo POST,
 * Esto indica que se ejecutará después de que el request haya sido enrutado. El uso más normal es para modificar la respuesta
 * En esta clase en el método run vamos a obtener el tiempo inicio que se nos envia en la Request (se calcula en el filtro PRE) y obtenemos también el timpo final de ejecucion
 * de la request, para saber el tiempo que tardado en ejecutarse el servicio solo tenemos que obtener la diferencia.
 * @author Alfonso
 *
 */
@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {

		return true;
	}

	@Override
	public Object run() throws ZuulException {

		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		log.info("Entrando a POST FILTER");

		//Obtenemos el valor en la clase PreTiempoTranscurridoFilter en el metodo run
		Long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
		
		Long tiempoFinal = System.currentTimeMillis();		
		//Obtenemos el tiempo transcurrio de la diferencia de tiempoFinal - tiempoInicio
		Long tiempoTranscurrido = tiempoFinal - tiempoInicio;
		
		log.info(String.format("Tiempo transcurrido en %s segundos", tiempoTranscurrido.doubleValue()/1000.00));
		
		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}

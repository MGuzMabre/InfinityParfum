package com.infinityparfum.Pago.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.infinityparfum.Pago.model.Pago;
import com.infinityparfum.Pago.service.PagoService;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public List<Pago> listarPagos() {
        return pagoService.obtenerTodos();
    }

    @PostMapping
    public Pago crearPago(@RequestBody Pago pago) {
        return pagoService.agregarPago(pago);
    }

    @PostMapping("/lista")
    public List<Pago> crearPagos(@RequestBody List<Pago> pagos) {
        return pagoService.agregarPagos(pagos);
    }

    @GetMapping("/{id}")
    public Pago obtenerPagoPorId(@PathVariable Long id) {
        return pagoService.buscarPorId(id);
    }
    
    @PutMapping("/{id}")
    public Pago actualizarPago(@PathVariable Long id, @RequestBody Pago pago) {
        return pagoService.actualizarPago(id, pago);
    }

    @DeleteMapping("/{id}")
    public void eliminarPago(@PathVariable Long id) {
        pagoService.eliminarPorId(id);
    }
}
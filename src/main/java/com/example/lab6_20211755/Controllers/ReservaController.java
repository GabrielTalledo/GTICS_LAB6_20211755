package com.example.lab6_20211755.Controllers;

import com.example.lab6_20211755.Entities.Reserva;
import com.example.lab6_20211755.Repositories.MesaRepository;
import com.example.lab6_20211755.Repositories.ReservaRepository;
import com.example.lab6_20211755.Repositories.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/reserva")
public class ReservaController {
    // Repositorios
    final UsuarioRepository usuarioRepository;
    final ReservaRepository reservaRepository;
    final MesaRepository mesaRepository;

    public ReservaController(UsuarioRepository usuarioRepository, ReservaRepository reservaRepository, MesaRepository mesaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.reservaRepository = reservaRepository;
        this.mesaRepository = mesaRepository;
    }

    // Métodos

    @GetMapping(value = {"", "/list"})
    public String listReserva(Model model) {
        model.addAttribute("listaReservas", reservaRepository.findAll());
        return "reserva/list";
    }

    @GetMapping("/new")
    public String nuevaReservaFrm(@ModelAttribute("reserva") Reserva reserva, Model model) {
        model.addAttribute("listaMesas",mesaRepository.obtenerMesasDisponibles());
        return "reserva/form";
    }

    @PostMapping("/save")
    public String guardarReserva(@ModelAttribute("reserva") @Valid Reserva reserva, BindingResult bindingResult,
                                  RedirectAttributes attr, Model model) {

        boolean validacion = true;
        if(reserva.getFechaFin() == null || reserva.getFechaInicio() == null){
            validacion = false;
        }else{
            validacion = reserva.getFechaFin().isAfter(reserva.getFechaInicio());
        }

        if (bindingResult.hasErrors() || !validacion) {
            model.addAttribute("listaMesas",mesaRepository.obtenerMesasDisponibles());
            model.addAttribute("msgError", "La fecha de Fin debe ser posterior a la fecha de Inicio");
            return "reserva/form";
        } else {
            if (reserva.getIdReserva() == 0) {
                attr.addFlashAttribute("msg", "Reserva agregada exitosamente");
                reservaRepository.save(reserva);}
            mesaRepository.disminuirCantidadDisponible(reserva.getMesa().getIdMesa());
        }

        return "redirect:/reserva/list";
    }


    @GetMapping("/delete")
    public String cancelarReserva(@RequestParam("id") int id,
                                      RedirectAttributes attr) {

        Optional<Reserva> optReserva = reservaRepository.findById(id);

        if (optReserva.isPresent()) {
            attr.addFlashAttribute("msg", "Reserva cancelada exitosamente");
            mesaRepository.aumentarCantidadDisponible(optReserva.get().getMesa().getIdMesa());
            reservaRepository.deleteById(id);
        }
        return "redirect:/reserva/list";
    }
}

package com.example.lab6_20211755.Controllers;

import com.example.lab6_20211755.Entities.Mesa;
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
@RequestMapping("/mesa")
public class MesaController {
    // Repositorios
    final UsuarioRepository usuarioRepository;
    final ReservaRepository reservaRepository;
    final MesaRepository mesaRepository;

    public MesaController(UsuarioRepository usuarioRepository, ReservaRepository reservaRepository, MesaRepository mesaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.reservaRepository = reservaRepository;
        this.mesaRepository = mesaRepository;
    }

    // Métodos

    @GetMapping(value = {"", "/list"})
    public String listMesa(Model model) {

        model.addAttribute("listaMesas", mesaRepository.findAll());

        return "mesa/list";
    }

    @GetMapping("/new")
    public String nuevaMesaFrm(@ModelAttribute("mesa") Mesa mesa) {
        return "mesa/form";
    }

    @GetMapping("/edit")
    public String editarMesaFrm(Model model, @RequestParam("id") int id,
                                       @ModelAttribute("mesa") Mesa mesa) {

        Optional<Mesa> optMesa = mesaRepository.findById(id);

        if (optMesa.isPresent()) {
            mesa = optMesa.get();
            model.addAttribute("mesa", mesa);
            return "mesa/form";
        } else {
            return "redirect:/mesa/list";
        }
    }

    @PostMapping("/save")
    public String guardarMesa(@ModelAttribute("mesa") @Valid Mesa mesa, BindingResult bindingResult,
                                     RedirectAttributes attr, Model model) {

        Mesa mesaAux = mesaRepository.findById(mesa.getIdMesa()).isPresent()?mesaRepository.findById(mesa.getIdMesa()).get():null;
        int diferencia = 0;

        if (bindingResult.hasErrors()) {
            return "mesa/form";
        } else {
            if (mesa.getIdMesa() == 0) {
                attr.addFlashAttribute("msg", "Mesa añadida exitosamente");
                mesaRepository.save(mesa);
            } else {
                assert mesaAux != null;
                if(mesaAux.getDisponibles() > mesa.getDisponibles()){
                    model.addAttribute("msgError","No puede modificar la cantidad disponible a un valor menor que la cantidad disponible actual.");
                    return "mesa/form";
                }
                attr.addFlashAttribute("msg", "Mesa actualizada exitosamente");
                mesaRepository.save(mesa);
            }
            return "redirect:/mesa/list";
        }
    }

    @GetMapping("/delete")
    public String borrarDispositivo(@RequestParam("id") int id,
                                    RedirectAttributes attr) {

        Optional<Mesa> optMesa = mesaRepository.findById(id);

        if (optMesa.isPresent()) {
            if(reservaRepository.encontrarReservaPorIdMesa(id).isEmpty()){
                attr.addFlashAttribute("msg", "Dispositivo eliminado exitosamente");
                mesaRepository.deleteById(id);
            }else{
                attr.addFlashAttribute("msg", "No puede eliminar una mesa con reservas activas");
            }
        }
        return "redirect:/mesa/list";
    }

}

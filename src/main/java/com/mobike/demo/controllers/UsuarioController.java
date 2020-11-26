package com.mobike.demo.controllers;

import com.mobike.demo.entity.Payment;
import com.mobike.demo.entity.Usuario;
import com.mobike.demo.services.IBikeService;
import com.mobike.demo.services.IPaymentService;
import com.mobike.demo.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UsuarioController {

  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  private IPaymentService medioPagoService;
  @Autowired
  private IBikeService bikeService;

  @GetMapping({"/", "/home"})
  public String list(Model model, Authentication authentication) {
    if (authentication != null) {
      Usuario usuario = usuarioService.findByUsername(authentication.getName());
      model.addAttribute("usuario", usuario);
    }
    model.addAttribute("title", "INICIO");

    return "userHome";
  }

  @GetMapping("/editar")
  public String edit(Model model, Authentication authentication) {
    model.addAttribute(usuarioService.findByUsername(authentication.getName()));
    model.addAttribute("title", "EDITAR MI PERFIL");
    return "userEdit";
  }

  @PostMapping("/editar")
  public String postEdit(@Valid Usuario usuario, BindingResult result, Model model, Authentication authentication) {
    usuarioService.save(usuario);
    model.addAttribute("title", "INICIO");
    return "userHome";
  }

  @GetMapping("/newPayment")
  public String getNewPayment(Model model) {
    model.addAttribute("title", "NUEVO MÉTODO DE PAGO");
    model.addAttribute("payment", new Payment());
    return "newPaymentForm";
  }

  @PostMapping("/newPayment")
  public String postNewPayment(@Valid Payment payment, BindingResult result, RedirectAttributes flash, Model model, Authentication authentication) {
    if (result.hasErrors()) {
      model.addAttribute("title", "NUEVO MEDIO DE PAGO *");
      return "newPaymentForm";
    }
    String mensajeFLash = (payment.getId() != null) ? "Medio de pago Registrado" : "Medio de pago Editado";
    Long id = usuarioService.findByUsername(authentication.getName()).getId();
    payment.setUser_id(id);
    medioPagoService.save(payment);
    flash.addFlashAttribute("success", mensajeFLash);
    return "redirect:/user/payments";
  }

  @GetMapping("/payments")
  public String paymentList(Model model) {
    model.addAttribute("payments", medioPagoService.findAll());
    return "payments";
  }

  @GetMapping("/deletePayment/{id}")
  public String delete(@PathVariable Long id, RedirectAttributes flash, Model model) {
    if (id > 0) {
      medioPagoService.delete(id);
      flash.addFlashAttribute("success", "Medio de Pago Eliminada");
    }
    model.addAttribute("payments", medioPagoService.findAll());
    return "redirect:/user/payments";
  }

  @GetMapping("/rents")
  public String rentList(Model model) {
    model.addAttribute("payments", medioPagoService.findAll());
    model.addAttribute("bikes", bikeService.findAvailables());
    return "rents";
  }

}

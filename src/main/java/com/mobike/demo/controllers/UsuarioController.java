package com.mobike.demo.controllers;

import com.mobike.demo.entity.Arriendo;
import com.mobike.demo.entity.Payment;
import com.mobike.demo.entity.Usuario;
import com.mobike.demo.services.IArriendoService;
import com.mobike.demo.services.IBikeService;
import com.mobike.demo.services.IPaymentService;
import com.mobike.demo.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("user")
public class UsuarioController {

  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private IUsuarioService usuarioService;
  @Autowired
  private IPaymentService medioPagoService;
  @Autowired
  private IBikeService bikeService;
  @Autowired
  private IArriendoService arriendoService;

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
    String passwordEncoded = passwordEncoder.encode(usuario.getPassword());
    usuario.setPassword((passwordEncoded));
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
  public String paymentList(Model model, Authentication authentication) {
    model.addAttribute("payments", medioPagoService.findByUser(usuarioService.findByUsername(authentication.getName()).getId()));
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
  public String rentList(Model model, Authentication authentication) {
    model.addAttribute("payments", medioPagoService.findByUser(usuarioService.findByUsername(authentication.getName()).getId()));
    model.addAttribute("bikes", bikeService.findAvailables());
    model.addAttribute("title", "Arrendar una BIKE");
    model.addAttribute("rents", arriendoService.findByUserId(usuarioService.findByUsername(authentication.getName()).getId()));
    Arriendo currentRent = arriendoService.findCurrentRent(usuarioService.findByUsername(authentication.getName()).getId());
    if (currentRent != null) {
      model.addAttribute("currentRent", currentRent);
      Long duration = Duration.between(currentRent.getCreatedAt(), LocalDateTime.now()).toMinutes();
      model.addAttribute("duration", duration);
    }


    return "rents";
  }

  @PostMapping("/newRent")
  public String postNewRent(RedirectAttributes flash, Model model, Authentication authentication) {
    String mensajeFLash = "Arriendo registrado correctamente";

    Long id = usuarioService.findByUsername(authentication.getName()).getId();
    Long bikeId = bikeService.findAvailables().get(0).getId();
    LocalDateTime createdAt = LocalDateTime.now();

    Arriendo arriendo = new Arriendo();
    arriendo.setUserId(id);
    arriendo.setBikeId(bikeId);
    arriendo.setCreatedAt(createdAt);
    arriendo.setEnabled(true);
    arriendoService.save(arriendo);
    List<Arriendo> rents = arriendoService.findByUserId(usuarioService.findByUsername(authentication.getName()).getId());

    model.addAttribute("rents", rents);

    flash.addFlashAttribute("success", mensajeFLash);
    return "redirect:/user/rents";
  }

  @PostMapping("/endRent")
  public String endRent(RedirectAttributes flash, Model model, Authentication authentication) {
    String mensajeFLash = "Arriendo terminado correctamente";

    arriendoService.endRent(arriendoService.findCurrentRent(usuarioService.findByUsername(authentication.getName()).getId()));
    model.addAttribute("rents", arriendoService.findByUserId(usuarioService.findByUsername(authentication.getName()).getId()));
    model.addAttribute("title", "Arriendos");
    flash.addFlashAttribute("success", mensajeFLash);
    return "redirect:/user/rents";
  }
}

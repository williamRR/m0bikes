package com.mobike.demo.controllers;

import com.mobike.demo.entity.Role;
import com.mobike.demo.entity.Usuario;
import com.mobike.demo.services.IAuthorityService;
import com.mobike.demo.services.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class IndexController {

  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private IUsuarioService iUsuarioService;
  @Autowired
  private IAuthorityService iAuthorityService;

  @GetMapping({"/", "", "/home"})
  public String index(Model model) {
    model.addAttribute("title", "MOBIKE");
    return "home";
  }

  @GetMapping("/register")
  public String registerForm(Model model) {
    model.addAttribute("usuario", new Usuario());
    model.addAttribute("title", "REGISTRO");
    return "registerForm";
  }

  @PostMapping("/register")
  public String registrarNuevo(@Valid Usuario usuario, BindingResult result, RedirectAttributes flash, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("title", "NUEVO USUARIO *");
      return "registerForm";
    }
    String mensajeFlash = (usuario.getId() != null) ? "Usuario Registrado" : "Usuario modificado";

    String passwordEncoded = passwordEncoder.encode(usuario.getPassword());
    usuario.setPassword((passwordEncoded));
    usuario.setEnabled(true);
    iUsuarioService.save(usuario);

    Long id = iUsuarioService.findByUsername(usuario.getUsername()).getId();
    Role role = new Role(id, "ROLE_USER");
    iAuthorityService.save(role);
    model.addAttribute("title", "INICIAR SESIÓN");

    flash.addFlashAttribute("success", mensajeFlash);
    return "redirect:/login";
  }

}

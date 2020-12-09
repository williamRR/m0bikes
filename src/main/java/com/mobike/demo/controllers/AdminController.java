package com.mobike.demo.controllers;

import com.mobike.demo.entity.Bike;
import com.mobike.demo.entity.Role;
import com.mobike.demo.entity.Usuario;
import com.mobike.demo.services.IAuthorityService;
import com.mobike.demo.services.IBikeService;
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

@Controller
@RequestMapping("admin")
public class AdminController {

  @Autowired
  private IUsuarioService iUsuarioService;
  @Autowired
  private IBikeService iBikeService;
  @Autowired
  private IAuthorityService iAuthorityService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping({"/", "/home"})
  public String home(Model model, Authentication authentication) {
    model.addAttribute("title", "HOME");
    model.addAttribute(iUsuarioService.findByUsername(authentication.getName()));
    return "adminHome";
  }

  @GetMapping("/users")
  public String userslist(Model model) {
    model.addAttribute("title", "USUARIOS");
    model.addAttribute("users", iUsuarioService.findAll());
    return "users";
  }

  @GetMapping("/bikes")
  public String bikeslist(Model model) {
    model.addAttribute("title", "BICICLETAS");
    model.addAttribute("bikes", iBikeService.findAll());
    return "bikes";
  }

  @GetMapping("/newUser")
  public String edit(Model model) {
    model.addAttribute("title", "NUEVO USUARIO");
    model.addAttribute("usuario", new Usuario());
    return "newUserForm";
  }

  @PostMapping("/newUser")
  public String postNewUser(@Valid Usuario usuario, BindingResult result, RedirectAttributes flash, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("title", "NUEVO USUARIO *");
      return "newUserForm";
    }


    model.addAttribute("title", "USUARIOS");
    String mensajeFlash = (usuario.getId() != null) ? "Usuario Modificado" : "Usuario Registrado";

    String passwordEncoded = passwordEncoder.encode(usuario.getPassword());
    usuario.setPassword((passwordEncoded));
    usuario.setEnabled(true);
    iUsuarioService.save(usuario);

    iUsuarioService.save(usuario);

    Long id = iUsuarioService.findByUsername(usuario.getUsername()).getId();
    Role role = new Role(id, "ROLE_USER");
    iAuthorityService.save(role);
    flash.addFlashAttribute("success", mensajeFlash);
    return "redirect:/admin/users";
  }

  @GetMapping("/newBike")
  public String getNewBike(Model model) {
    model.addAttribute("title", "NUEVA BICICLETA");
    model.addAttribute("bike", new Bike());
    return "newBikeForm";
  }

  @PostMapping("/newBike")
  public String postNewBike(@Valid Bike bike, BindingResult result, RedirectAttributes flash, Model model) {
    if (result.hasErrors()) {
      model.addAttribute("title", "NUEVA BICICLETA *");
      return "newBikeForm";
    }
    String mensajeFLash = (bike.getId() != null) ? "Bicicleta Editada" : "Bicicleta Registrada";
    model.addAttribute("title", "BICICLETAS");

    iBikeService.save(bike);
    flash.addFlashAttribute("success", mensajeFLash);
    return "redirect:/admin/bikes";
  }

  @GetMapping("/deleteBike/{id}")
  public String delete(@PathVariable Long id, RedirectAttributes flash, Model model) {
    if (id > 0) {
      iBikeService.delete(id);
      flash.addFlashAttribute("success", "Bicicleta Eliminada");
    }
    model.addAttribute("title", "BICICLETAS");

    return "redirect:/admin/bikes";
  }

  @GetMapping("/editBike/{id}")
  public String editBike(@PathVariable Long id, RedirectAttributes flash, Model model) {
    Bike bike = new Bike();
    if (id > 0) {
      bike = iBikeService.findOne(id);
      if (bike == null) {
        flash.addFlashAttribute("error", "El ID no existe en la bdd!");
        return "redirect:/admin/bikes";
      }

    } else {
      flash.addFlashAttribute("error", "El ID no puede ser cero!");
      return "redirect:/admin/bikes";
    }
    model.addAttribute("title", "Editar bicicleta");
    model.addAttribute("bike", bike);
    return "newBikeForm";
  }

  @GetMapping("/deleteUser/{id}")
  public String deleteUser(@PathVariable Long id, RedirectAttributes flash) {
    if (id > 0) {
      iUsuarioService.delete(id);
      flash.addFlashAttribute("success", "Usuario Eliminado");
    }
    return "redirect:/admin/users";
  }

  @GetMapping("/editUser/{id}")
  public String editUser(@PathVariable Long id, RedirectAttributes flash, Model model) {
    Usuario usuario = new Usuario();
    if (id > 0) {
      usuario = iUsuarioService.findOne(id);
      if (usuario == null) {
        flash.addFlashAttribute("error", "El ID no existe en la bdd!");
        return "redirect:/admin/users";
      }

    } else {
      flash.addFlashAttribute("error", "El ID no puede ser cero!");
      return "redirect:/admin/users";
    }
    model.addAttribute("title", "Editar Usuario");
    model.addAttribute("usuario", usuario);
    return "newUserForm";
  }

}

package web.crud_spring_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import web.crud_spring_boot.model.Role;
import web.crud_spring_boot.model.User;
import web.crud_spring_boot.service.RoleService;
import web.crud_spring_boot.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String adminPanel(Model model, Principal principal) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @GetMapping("/{id}")
    public String readUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("users", userService.readUser(id));
        return "read";
    }

    @GetMapping("admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());

        return "new";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("user") @Validated User user,
                           @RequestParam("addRole") List<String> addRole,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "new";

        Set<Role> roles = userService.getRolesForSet(addRole);

        user.setRoles(roles);
        userService.createUser(user);

        return "redirect:/admin";
    }

    @GetMapping("admin/{id}/edit")
    public String editUser(Model model, @PathVariable("id") Long id) {

        model.addAttribute("user", userService.readUser(id));
        model.addAttribute("allRoles", roleService.getAllRoles());

        return "admin";
    }

    @PatchMapping("admin/edit/{id}")
    public String updateUser(@ModelAttribute("user") @Validated User user,
                             @RequestParam("editName") String name,
                             @RequestParam("editSurname") String surname,
                             @RequestParam("editEmail") String email,
                             @RequestParam("editPassword") String password,
                             @RequestParam("editRole") List<String> editRole,
                             @PathVariable("id") Long id) {

        Set<Role> roles = userService.getRolesForSet(editRole);

        user = new User(name, surname, email, password);
        user.setRoles(roles);
        userService.editUser(id, user);

        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login";
    }
}

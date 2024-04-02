package pl.matek.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.matek.api.dto.CustomerRegisterDTO;
import pl.matek.api.dto.OwnerRegisterDTO;
import pl.matek.api.dto.mapper.CustomerRegisterMapper;
import pl.matek.api.dto.mapper.OwnerRegisterMapper;
import pl.matek.business.RegisterService;
import pl.matek.domain.CustomerRegister;
import pl.matek.domain.OwnerRegister;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class HomeController {

    static final String LOGIN = "/login";
    static final String LOGOUT = "/logout";
    static final String REGISTER_CUSTOMER = "/registerCustomer";
    static final String REGISTER_CUSTOMER_SAVE = "/registerCustomer/save";
    static final String REGISTER_OWNER = "/registerOwner";
    static final String REGISTER_OWNER_SAVE = "/registerOwner/save";

    private final PasswordEncoder passwordEncoder;
    private final RegisterService registerService;
    private final OwnerRegisterMapper ownerRegisterMapper;
    private final CustomerRegisterMapper customerRegisterMapper;
    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @GetMapping(value = LOGIN)
    public String loginUser() {
        return "login";
    }

    @GetMapping(value = LOGOUT)
    public String logoutUser(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        return "login";
    }

    @GetMapping(value = "/")
    public String homePage(Authentication authentication){
        Optional<String> firstRole = authentication.getAuthorities().stream()
                .map(Object::toString)
                .findFirst();
        String role = "error";
        if (firstRole.isPresent()){
            role = switch (firstRole.get()) {
                case "OWNER" -> "redirect:/owner";
                case "CUSTOMER" -> "redirect:/customer";
                default -> "error";
            };
        }
        return role;
    }

    @GetMapping(value = REGISTER_CUSTOMER)
    public String registerCustomer(Model model) {
        CustomerRegisterDTO customerRegisterDTO = CustomerRegisterDTO.buildDefaultData();
        model.addAttribute("CustomerDTO", customerRegisterDTO);
        return "registerCustomer";
    }

    @GetMapping(value = REGISTER_OWNER)
    public String registerOwner(Model model) {
        OwnerRegisterDTO ownerRegisterDTO = OwnerRegisterDTO.buildDefaultData();
        model.addAttribute("OwnerDTO", ownerRegisterDTO);
        return "registerOwner";
    }

    @PostMapping(value = REGISTER_OWNER_SAVE)
    public String registerOwnerSave(
            @Valid @ModelAttribute("OwnerDTO") OwnerRegisterDTO ownerRegisterDTO
    ) {
        ownerRegisterDTO.setPassword(passwordEncoder.encode(ownerRegisterDTO.getPassword()));
        OwnerRegister ownerRegister = ownerRegisterMapper.map(ownerRegisterDTO);
        registerService.ownerCreate(ownerRegister);
        return "login";
    }

    @PostMapping(value = REGISTER_CUSTOMER_SAVE)
    public String registerCustomerSave(
            //todo walidacja
            @ModelAttribute("CustomerDTO") CustomerRegisterDTO customerRegisterDTO
    ){
        customerRegisterDTO.setPassword(passwordEncoder.encode(customerRegisterDTO.getPassword()));
        CustomerRegister customerRegister = customerRegisterMapper.map(customerRegisterDTO);
        registerService.customerCreate(customerRegister);
        return "login";
    }
}

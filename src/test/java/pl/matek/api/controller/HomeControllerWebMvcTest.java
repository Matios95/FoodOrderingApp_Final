//package pl.matek.api.controller;
//
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.util.LinkedMultiValueMap;
//import pl.matek.api.dto.OwnerRegisterDTO;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Stream;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(controllers = HomeController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@AllArgsConstructor(onConstructor = @__(@Autowired))
//public class HomeControllerWebMvcTest {
//
//    private MockMvc mockMvc;
//
//    @Test
//    void viewLoginCorrectly() throws Exception {
//        //given
//        //when
//        //then
//        mockMvc.perform(get(HomeController.LOGIN))
//                .andExpect(status().isOk())
//                .andExpect(view().name("login"));
//    }
//
//    @Test
//    void viewRegisterCustomerCorrectly() throws Exception {
//        mockMvc.perform(get(HomeController.REGISTER_CUSTOMER))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("CustomerDTO"))
//                .andExpect(view().name("registerCustomer"));
//    }
//
//    @Test
//    void viewRegisterOwnerCorrectly() throws Exception {
//        mockMvc.perform(get(HomeController.REGISTER_OWNER))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("OwnerDTO"))
//                .andExpect(view().name("registerOwner"));
//    }
//
//
//    @ParameterizedTest
//    @MethodSource
//    void thatEmailValidation(Boolean status, String email) throws Exception {
//        //given
//        OwnerRegisterDTO ownerRegisterDTO = OwnerRegisterDTO.buildDefaultData().withEmail(email);
//        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//
//        Map<String, String> paramsMap = new HashMap<>();
//        paramsMap.put("name", ownerRegisterDTO.getName());
//        paramsMap.put("surname", ownerRegisterDTO.getSurname());
//        paramsMap.put("email", ownerRegisterDTO.getEmail());
//        paramsMap.put("password", ownerRegisterDTO.getPassword());
//
//        paramsMap.forEach(params::add);
//
//        //when
//        //then
//        if (status) {
//
//            mockMvc.perform(post(HomeController.REGISTER_OWNER_SAVE)
//                            .params(params))
//                    .andExpect(status().isOk())
//                    .andExpect(view().name("login"));
//
//            mockMvc.perform(post(HomeController.REGISTER_CUSTOMER_SAVE)
//                            .params(params))
//                    .andExpect(status().isOk())
//                    .andExpect(view().name("login"));
//        } else {
//
//            mockMvc.perform(post(HomeController.REGISTER_OWNER_SAVE)
//                            .params(params))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(view().name("error"));
//
//            mockMvc.perform(post(HomeController.REGISTER_CUSTOMER_SAVE)
//                            .params(params))
//                    .andExpect(status().isBadRequest())
//                    .andExpect(view().name("error"));
//        }
//    }
//
//
//    public static Stream<Arguments> thatEmailValidation() {
//        return Stream.of(
//                Arguments.of(false, "nowak.pl"),
//                Arguments.of(false, "@mail.pl"),
//                Arguments.of(false, "nowak@.pl"),
//                Arguments.of(false, ".plnowak@mail"),
//                Arguments.of(false, " nowak@mail.pl21"),
//                Arguments.of(false, "nowakmail.pl"),
//                Arguments.of(true, "nowak@mail.pl")
//        );
//    }
//}

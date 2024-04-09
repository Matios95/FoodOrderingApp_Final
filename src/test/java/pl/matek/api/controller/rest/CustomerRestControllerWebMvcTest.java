//package pl.matek.api.controller.rest;
//
//
//import lombok.AllArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import pl.matek.api.dto.mapper.FoodOrderingRequestMapperImpl;
//import pl.matek.business.FoodOrderingRequestService;
//import pl.matek.domain.FoodOrderingRequest;
//
//import java.time.OffsetDateTime;
//import java.util.Collections;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = CustomerRestController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@AllArgsConstructor(onConstructor = @__(@Autowired))
//public class CustomerRestControllerWebMvcTest {
//
//    private MockMvc mockMvc;
//
//    @MockBean
//    private FoodOrderingRequestService foodOrderingRequestService;
//    @MockBean
//    private FoodOrderingRequestMapperImpl foodOrderingRequestMapper;
//
//    @Test
//    void viewTallyCorrectlyTimeOk() throws Exception {
//        //given
//        FoodOrderingRequest foodOrderingRequest = FoodOrderingRequest.builder()
//                .foodOrderingRequestId(8)
//                .datetime(OffsetDateTime.now())
//                .completed(false)
//                .build();
//        when(foodOrderingRequestService.findById(anyInt()))
//                .thenReturn(foodOrderingRequest);
//        doNothing().when(foodOrderingRequestService).delete(anyInt());
//
//        // when, then
//        mockMvc.perform(get("/api/customer/tally/" + foodOrderingRequest.getFoodOrderingRequestId() + "/delete"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void viewTallyCorrectlyTimeIsLong() throws Exception {
//        //given
//        FoodOrderingRequest foodOrderingRequest = FoodOrderingRequest.builder()
//                .foodOrderingRequestId(8)
//                .datetime(OffsetDateTime.now().minusHours(1))
//                .completed(false)
//                .build();
//        when(foodOrderingRequestService.findById(anyInt()))
//                .thenReturn(foodOrderingRequest);
//        doNothing().when(foodOrderingRequestService).delete(anyInt());
//
//        //when, then
//        mockMvc.perform(get("/api/customer/tally/" + foodOrderingRequest.getFoodOrderingRequestId() + "/delete"))
//                .andExpect(status().is(409));
//    }
//
//    @Test
//    void viewTallyAllActiveEmptyList() throws Exception {
//        //given
//        when(foodOrderingRequestService.findAllActive())
//                .thenReturn(Collections.emptyList());
//        when(foodOrderingRequestMapper.map(any(FoodOrderingRequest.class)))
//                .thenCallRealMethod();
//
//        //when, then
//
//        mockMvc.perform(get("/api/" + CustomerRestController.TALLY_ALL_ACTIVE))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void viewTallyInfoIsIdProblem() throws Exception {
//        //given
//        //when, then
//        mockMvc.perform(get("/api/customer/tally//info"))
//                .andExpect(status().isNotFound());
//    }
//
//}

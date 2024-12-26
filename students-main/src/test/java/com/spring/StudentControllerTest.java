package com.spring;
import com.spring.controllers.StudentController;
import com.spring.entities.Students;
import com.spring.entities.Subject;
import com.spring.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Arrays;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Automatically injected MockMvc
    @Mock
    private StudentService service;  // Mocking the service
    @InjectMocks
    private StudentController controller;  // Injecting mocks into the controller
    private Students student;
    @BeforeEach
    public void setUp() {
        // Creating Subjects
        Subject s1 = new Subject(1,"Maths",80);
        Subject s2 = new Subject(2,"English", 90);
        // Creating a student
        student = new Students(1,"raju", 20, "Male", "22-3-2004", "computer science", 2020, 2024, Arrays.asList(s1, s2));
    }
    @Test
    public void testAddStudent() throws Exception {
        // Mock the service method to return the student when called
        when(service.addStudent(Mockito.any(Students.class))).thenReturn(student);
        // Perform the POST request and verify the result
        mockMvc.perform(MockMvcRequestBuilders.post("/students/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"raju\",\"age\":20,\"gender\":\"male\",\"dob\":\"22-3-2004\",\"course\":\"computer science\",\"courseStartYear\":2020,\"courseEndYear\":2024,\"subjects\":[{\"name\":\"Maths\",\"marks\":80},{\"name\":\"English\",\"marks\":90}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("raju"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.dob").value("22-3-2004"))
                .andExpect(jsonPath("$.course").value("computer science"))
                .andExpect(jsonPath("$.courseStartYear").value(2020))
                .andExpect(jsonPath("$.courseEndYear").value(2024))
                .andExpect(jsonPath("$.subjects[0].name").value("Maths"))
                .andExpect(jsonPath("$.subjects[0].marks").value(80))
                .andExpect(jsonPath("$.subjects[1].name").value("English"))
                .andExpect(jsonPath("$.subjects[1].marks").value(90));

    }
}


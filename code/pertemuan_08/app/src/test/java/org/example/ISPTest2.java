package org.example;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ISPTest2 {

    @Test
    void testLegacyStudentIsForcedToImplementIrrelevantMethods() {
        org.example.isp2a.Student student = new org.example.isp2a.Student();

        assertDoesNotThrow(student::viewCourse);
        assertThrows(UnsupportedOperationException.class, student::teachCourse);
        assertThrows(UnsupportedOperationException.class, student::manageSystem);
    }

    @Test
    void testRefactoredStudentOnlyViewsCourse() {
        org.example.isp2b.Student student = new org.example.isp2b.Student();

        assertDoesNotThrow(student::viewCourse);
        assertThrows(NoSuchMethodException.class,
                () -> org.example.isp2b.Student.class.getDeclaredMethod("teachCourse"));
        assertThrows(NoSuchMethodException.class,
                () -> org.example.isp2b.Student.class.getDeclaredMethod("manageSystem"));
    }

    @Test
    void testLecturerAndAdminExposeOnlyRelevantCapabilities() {
        org.example.isp2b.Lecturer lecturer = new org.example.isp2b.Lecturer();
        org.example.isp2b.Admin admin = new org.example.isp2b.Admin();

        assertDoesNotThrow(lecturer::viewCourse);
        assertDoesNotThrow(lecturer::teachCourse);
        assertDoesNotThrow(admin::viewCourse);
        assertDoesNotThrow(admin::manageSystem);
    }
}

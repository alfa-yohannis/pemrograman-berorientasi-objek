package org.example.isp2a;

public class Student implements User {
    @Override
    public void viewCourse() {
    }

    @Override
    public void teachCourse() {
        throw new UnsupportedOperationException("Mahasiswa tidak mengajar");
    }

    @Override
    public void manageSystem() {
        throw new UnsupportedOperationException("Mahasiswa tidak mengelola sistem");
    }
}

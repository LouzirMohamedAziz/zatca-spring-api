package com.zatca.invoice.zatcaspringapi.Configurations;

import java.io.File;

public class FileLoader {

    public File loadFile(String pathToFile) {
        File file = new File(pathToFile);
        return file;
    }

}

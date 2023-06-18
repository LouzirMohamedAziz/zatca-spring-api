package com.zatca.invoice.zatcaspringapi.Services;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.zatca.invoice.zatcaspringapi.Configurations.FileLoader;
import com.zatca.invoice.zatcaspringapi.Models.Validation;
import com.zatca.invoice.zatcaspringapi.Repositories.ValidationRepository;
import com.zatca.sdk.service.flow.ValidationProcessorImpl;
import com.zatca.sdk.service.validation.Result;

@Service
public class ValidationService {

    private final ValidationRepository validationRepository;

    @Autowired
    public ValidationService(ValidationRepository validationRepository) {
        this.validationRepository = validationRepository;
    }


    public Result validityCheck(Validation validation){
        validationRepository.save(validation);
        FileLoader fileLoader = new FileLoader();
        File file = fileLoader.loadFile(validation.getSignedInvoiceFile());
        ValidationProcessorImpl validationProcessorImpl = new ValidationProcessorImpl();
        try {
            return validationProcessorImpl.run(file);
        } catch (InvalidCanonicalizerException | CanonicalizationException | IOException | ParserConfigurationException
                | TransformerException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

}

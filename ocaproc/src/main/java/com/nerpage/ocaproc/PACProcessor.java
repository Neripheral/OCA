package com.nerpage.ocaproc;

import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;


@SupportedAnnotationTypes("com.nerpage.ocaproc.HasStandardModel")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor.class)
public class PACProcessor extends AbstractProcessor {
    public static final String SUFFIX_FILTER = "Controller";
    public static final String ANNOTATION_TEXT = "HasStandardModel";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {



        //processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Hello");
        return true;
    }
}
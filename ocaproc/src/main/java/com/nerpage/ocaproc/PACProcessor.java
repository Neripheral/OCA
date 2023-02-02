package com.nerpage.ocaproc;

import com.google.auto.service.AutoService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;


@SupportedAnnotationTypes("com.nerpage.ocaproc.HasStandardModel")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor.class)
public class PACProcessor extends AbstractProcessor {
    public static final String SUFFIX_FILTER = "Controller";
    public static final String ANNOTATION_TEXT = "HasStandardModel";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if(annotations.isEmpty())
            return false;

        List<Element> elements = getCorrectElements(annotations.iterator().next(), roundEnv);
        for(Element element : elements)
            processElement(element);

        /*StringBuilder builder = new StringBuilder()
                .append("package com.nerpage.ocaproc.generated;\n\n")
                .append("public class GeneratedClass {\n\n")
                .append("\tpublic String getMessage() {\n")
                .append("\t\t return \"");

        for(Element element : roundEnv.getElementsAnnotatedWith(HasStandardModel.class)){
            String objectType = element.getSimpleName().toString();

            builder.append(objectType).append("\n");
        }

        builder.append("\";\n")
                .append("\t}\n")
                .append("}\n");

        try { // write the file
            JavaFileObject source = processingEnv.getFiler().createSourceFile("com.nerpage.ocaproc.generated.GeneratedClass");


            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }*/

        //processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Hello");
        return true;
    }



    private void processElement(Element element){
        List<String> attributes = List.of(element.getAnnotation(HasStandardModel.class).value());

    }



    private List<Element> getCorrectElements(TypeElement annotation, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

        Map<Boolean, List<Element>> annotated = annotatedElements.stream().collect(
                Collectors.partitioningBy(element ->
                        element.getKind() == ElementKind.CLASS &&
                                element.getSimpleName().toString().contains(SUFFIX_FILTER)
                ));
        List<Element> classes = annotated.get(true);
        List<Element> others = annotated.get(false);

        others.forEach(element ->
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                        "@" + ANNOTATION_TEXT + " must be applied to a class ending with " + SUFFIX_FILTER +
                                " & is: " + element.getSimpleName().toString(),
                        element)
        );

        for(Element c : classes){
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    "Class with annotation @" + ANNOTATION_TEXT + " found: " + c.getSimpleName().toString());
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    "Values: " + String.join(" , ", c.getAnnotation(HasStandardModel.class)
                            .value()));
        }

        return classes;
    }
}
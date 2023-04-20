package io.github.sisobobo.athena.serializer;

public @interface Serialize {

    String name();

    boolean ignore() default false ;

}
